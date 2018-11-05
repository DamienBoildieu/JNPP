/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jnpp.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jnpp.controller.views.JNPPModelAndView;
import jnpp.controller.views.Translator;
import jnpp.controller.views.alerts.AlertEnum;
import jnpp.controller.views.alerts.AlertMessage;
import jnpp.controller.views.info.ViewInfo;
import jnpp.dao.entities.accounts.AccountEntity;
import jnpp.dao.entities.accounts.CurrentAccountEntity;
import jnpp.dao.entities.accounts.SavingAccountEntity;
import jnpp.service.exceptions.entities.FakeClientException;
import jnpp.service.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CLinkAccount {
    @Autowired
    private NotificationService notifService;
    
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
        if (CSession.getLanguage(session)!=Translator.Language.FR)
            CSession.setLanguage(session,Translator.Language.FR);
        if (!CSession.isConnected(session))
            return new ModelAndView("redirect:/index.htm");
        Boolean hasNotif = CSession.getHasNotif(session);
        if (!hasNotif) {  
            try {
                hasNotif = notifService.receiveUnseenNotifications(CSession.getClient(session).getLogin()).size()>0;
                CSession.setHasNotif(session, hasNotif);
            } catch (FakeClientException invalidClient) {
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Il semble y avoir une erreur dans votre session"));
                } else {
                    alerts = new ArrayList<AlertMessage>(); 
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Il semble y avoir une erreur dans votre session"));
                }
            }
        }
	//resumeService.resumeAccounts("");
        return new JNPPModelAndView("accounts/account", ViewInfo.createInfo(session, alerts));
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
        if (CSession.getLanguage(session)!=Translator.Language.FR)
            CSession.setLanguage(session,Translator.Language.FR);
        if (!CSession.isConnected(session))
            return new ModelAndView("redirect:/index.htm");
        Boolean hasNotif = CSession.getHasNotif(session);
        if (!hasNotif) {  
            try {
                hasNotif = notifService.receiveUnseenNotifications(CSession.getClient(session).getLogin()).size()>0;
                CSession.setHasNotif(session, hasNotif);
            } catch (FakeClientException invalidClient) {
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Il semble y avoir une erreur dans votre session"));
                } else {
                    alerts = new ArrayList<AlertMessage>(); 
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Il semble y avoir une erreur dans votre session"));
                }
            }
        }
	//resumeService.resumeAccounts("");
        return new JNPPModelAndView("accounts/openaccount", ViewInfo.createInfo(session, alerts));
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
        if (CSession.getLanguage(session)!=Translator.Language.FR)
            CSession.setLanguage(session,Translator.Language.FR);
        if (!CSession.isConnected(session))
            return new ModelAndView("redirect:/index.htm");
        Boolean hasNotif = CSession.getHasNotif(session);
        if (!hasNotif) {  
            try {
                hasNotif = notifService.receiveUnseenNotifications(CSession.getClient(session).getLogin()).size()>0;
                CSession.setHasNotif(session, hasNotif);
            } catch (FakeClientException invalidClient) {
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Il semble y avoir une erreur dans votre session"));
                } else {
                    alerts = new ArrayList<AlertMessage>(); 
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Il semble y avoir une erreur dans votre session"));
                }
            }
        }
	//resumeService.resumeAccounts("");
        List<AccountEntity> listAc = new ArrayList<AccountEntity>();
        CurrentAccountEntity current = new CurrentAccountEntity();
        current.setMoney(-20.d);
        current.setRib("134824");
        listAc.add(current);
        listAc.add(current);
        listAc.add(current);
        listAc.add(current);
        listAc.add(current);listAc.add(current);listAc.add(current);listAc.add(current);listAc.add(current);
        
        SavingAccountEntity saving = new SavingAccountEntity();
        saving.setMoney(500.d);
        saving.setRib("5946513");
        listAc.add(saving);
        ModelAndView view = new JNPPModelAndView("accounts/resume", ViewInfo.createInfo(session, alerts));
        view.addObject("listAccounts", listAc);
        view.addObject("accountsMap", Translator.getInstance().translateAccounts(CSession.getLanguage(session)));
        return view;
    }
}
