/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jnpp.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jnpp.controller.views.JNPPModelAndView;
import jnpp.controller.views.MovementView;
import jnpp.controller.views.Translator;
import jnpp.controller.views.alerts.AlertEnum;
import jnpp.controller.views.alerts.AlertMessage;
import jnpp.controller.views.info.ViewInfo;
import jnpp.dao.entities.accounts.AccountEntity;
import jnpp.dao.entities.accounts.CurrentAccountEntity;
import jnpp.dao.entities.accounts.SavingAccountEntity;
import jnpp.service.dto.IdentityDTO;
import jnpp.service.dto.accounts.AccountDTO;
import jnpp.service.dto.accounts.SavingBookDTO;
import jnpp.service.dto.clients.ClientDTO;
import jnpp.service.dto.movements.MovementDTO;
import jnpp.service.exceptions.entities.FakeClientException;
import jnpp.service.services.AccountService;
import jnpp.service.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LinkAccountController {
    @Autowired
    private NotificationService notifService;
    @Autowired
     private AccountService accountService;
    
    /**
     * Requête sur la vue d'un compte
     * @param model le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @param response la réponse
     * @return Une vue sur un compte de l'utilisateur si il est connecté, redirection vers l'index sinon
     * @throws Exception 
     */
    @RequestMapping(value = "account", method = RequestMethod.GET)
    protected ModelAndView linktoAccount(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>)model.asMap().get("alerts");
        if (session==null)
            session = request.getSession(true);
        if (SessionController.getLanguage(session)!=Translator.Language.FR)
            SessionController.setLanguage(session,Translator.Language.FR);
        if (!SessionController.isConnected(session))
            return new ModelAndView("redirect:/index.htm");
        Boolean hasNotif = SessionController.getHasNotif(session);
        if (!hasNotif) {  
            try {
                hasNotif = notifService.receiveUnseenNotifications(SessionController.getClient(session).getLogin()).size()>0;
                SessionController.setHasNotif(session, hasNotif);
            } catch (FakeClientException invalidClient) {
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Il semble y avoir une erreur dans votre session"));
                } else {
                    alerts = new ArrayList<AlertMessage>(); 
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Il semble y avoir une erreur dans votre session"));
                }
            }
        }
        String id = request.getParameter("id");
        List<AccountDTO> accounts = accountService.getAccounts(SessionController.getClient(session).getLogin());
        Iterator<AccountDTO> ite = accounts.iterator();
        AccountDTO account = null;
        ModelAndView view = null;
        while (ite.hasNext()) {
            account = ite.next();
            if (account.getRib().equals(id)) {
                switch (account.getType()) {
                    case CURRENT:
                        view = new JNPPModelAndView("accounts/currentaccount", ViewInfo.createInfo(session, alerts));
                        view.addObject("accountsMap", Translator.getInstance().translateAccounts(SessionController.getLanguage(session)));
                        view.addObject("currencyMap", Translator.getInstance().translateCurrency(SessionController.getLanguage(session)));
                        List<MovementView> movementsCurrent = new ArrayList<MovementView>();
                        for (MovementDTO movement : accountService.getMovements(SessionController.getClient(session).getLogin(), id)) {
                            movementsCurrent.add(new MovementView(movement, id));
                        }
                        view.addObject("movements",movementsCurrent);
                        break;
                    case JOINT:
                        view = new JNPPModelAndView("accounts/jointaccount", ViewInfo.createInfo(session, alerts));
                        view.addObject("accountsMap", Translator.getInstance().translateAccounts(SessionController.getLanguage(session)));
                        view.addObject("currencyMap", Translator.getInstance().translateCurrency(SessionController.getLanguage(session)));
                        List<MovementView> movementsJoint = new ArrayList<MovementView>();
                        for (MovementDTO movement : accountService.getMovements(SessionController.getClient(session).getLogin(), id)) {
                            movementsJoint.add(new MovementView(movement, id));
                        }
                        view.addObject("movements",movementsJoint);
                        break;
                    case SAVING:
                        view = new JNPPModelAndView("accounts/savingaccount", ViewInfo.createInfo(session, alerts));
                        view.addObject("accountsMap", Translator.getInstance().translateAccounts(SessionController.getLanguage(session)));
                        view.addObject("currencyMap", Translator.getInstance().translateCurrency(SessionController.getLanguage(session)));
                        List<MovementView> movementsSaving = new ArrayList<MovementView>();
                        for (MovementDTO movement : accountService.getMovements(SessionController.getClient(session).getLogin(), id)) {
                            movementsSaving.add(new MovementView(movement, id));
                        }
                        view.addObject("movements",movementsSaving);
                        break;
                    case SHARE:                       
                        view = new JNPPModelAndView("accounts/shareaccount", ViewInfo.createInfo(session, alerts));
                        view.addObject("accountsMap", Translator.getInstance().translateAccounts(SessionController.getLanguage(session)));
                        break;
                    default:
                        throw new AssertionError(account.getType().name());                    
                }
                view.addObject("account", account);
                return view;
            }
        }
	//resumeService.resumeAccounts("");
        return new ModelAndView("redirect:/resume.htm");
    }
    
    /**
     * Requête sur la vue d'un compte
     * @param model le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @param response la réponse
     * @return Une vue sur un compte de l'utilisateur si il est connecté, redirection vers l'index sinon
     * @throws Exception 
     */
    @RequestMapping(value = "openaccount", method = RequestMethod.GET)
    protected ModelAndView linktoOpenAccount(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>)model.asMap().get("alerts");
        if (session==null)
            session = request.getSession(true);
        if (SessionController.getLanguage(session)!=Translator.Language.FR)
            SessionController.setLanguage(session,Translator.Language.FR);
        if (!SessionController.isConnected(session))
            return new ModelAndView("redirect:/index.htm");
        Boolean hasNotif = SessionController.getHasNotif(session);
        if (!hasNotif) {  
            try {
                hasNotif = notifService.receiveUnseenNotifications(SessionController.getClient(session).getLogin()).size()>0;
                SessionController.setHasNotif(session, hasNotif);
            } catch (FakeClientException invalidClient) {
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Il semble y avoir une erreur dans votre session"));
                } else {
                    alerts = new ArrayList<AlertMessage>(); 
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Il semble y avoir une erreur dans votre session"));
                }
            }
        }
        ClientDTO client = SessionController.getClient(session);
        ModelAndView view = null;
        switch (client.getType()) {
            case PRIVATE:
                view = new JNPPModelAndView("accounts/openprivateaccount", ViewInfo.createInfo(session, alerts));
                String nbClientsStr = request.getParameter("nbClients");
                Integer nbClients = 1;
                if (nbClientsStr != null) {
                    nbClients = Integer.parseInt(nbClientsStr);
                    if (nbClients < 1) {
                        nbClients = 1;
                    }
                }
                view.addObject("nbClients", nbClients);
                List<SavingBookDTO> books = accountService.getSavingBooks();
                view.addObject("books", books);
                view.addObject("genders", IdentityDTO.Gender.values());
                view.addObject("gendersMap", Translator.getInstance().translateGenders(SessionController.getLanguage(session)));
                break;
            case PROFESIONAL:
                view = new JNPPModelAndView("accounts/openproaccount", ViewInfo.createInfo(session, alerts));
                break;
            default:
                throw new AssertionError(client.getType().name());
        }       
        return view;
    }
    /**
     * Requête sur la vue de la liste des comptes
     * @param model le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @param response la réponse
     * @return Une vue sur la liste des comptes de l'utilisateur si il est connecté, redirection vers l'index sinon
     * @throws Exception 
     */
    @RequestMapping(value = "resume", method = RequestMethod.GET)
    protected ModelAndView linktoResume(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<AlertMessage> alerts = (List<AlertMessage>)model.asMap().get("alerts");
        HttpSession session = request.getSession();
        if (session==null)
            session = request.getSession(true);
        if (SessionController.getLanguage(session)!=Translator.Language.FR)
            SessionController.setLanguage(session,Translator.Language.FR);
        if (!SessionController.isConnected(session))
            return new ModelAndView("redirect:/index.htm");
        Boolean hasNotif = SessionController.getHasNotif(session);
        if (!hasNotif) {  
            try {
                hasNotif = notifService.receiveUnseenNotifications(SessionController.getClient(session).getLogin()).size()>0;
                SessionController.setHasNotif(session, hasNotif);
            } catch (FakeClientException invalidClient) {
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Il semble y avoir une erreur dans votre session"));
                } else {
                    alerts = new ArrayList<AlertMessage>(); 
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Il semble y avoir une erreur dans votre session"));
                }
            }
        }
        List<AccountDTO> accounts = accountService.getAccounts(SessionController.getClient(session).getLogin());
        ModelAndView view = new JNPPModelAndView("accounts/resume", ViewInfo.createInfo(session, alerts));
        view.addObject("accounts", accounts);
        view.addObject("accountsMap", Translator.getInstance().translateAccounts(SessionController.getLanguage(session)));
        view.addObject("currencyMap", Translator.getInstance().translateCurrency(SessionController.getLanguage(session)));
        return view;
    }
    
    
}
