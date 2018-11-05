package jnpp.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jnpp.controller.views.JNPPModelAndView;
import jnpp.controller.views.NotifView;
import jnpp.controller.views.Translator;
import jnpp.controller.views.alerts.AlertEnum;
import jnpp.controller.views.alerts.AlertMessage;
import jnpp.controller.views.info.ViewInfo;
import jnpp.dao.entities.accounts.AccountEntity;
import jnpp.dao.entities.accounts.CurrentAccountEntity;
import jnpp.dao.entities.accounts.SavingAccountEntity;
import jnpp.service.dto.IdentityDTO;
import jnpp.service.dto.clients.ClientDTO;
import jnpp.service.dto.clients.PrivateDTO;
import jnpp.service.dto.notifications.NotificationDTO;
import jnpp.service.exceptions.entities.FakeClientException;
import jnpp.service.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Classe contrôlant les différents liens qui ne requièrent pas de traitement particulier
 */
@Controller
public class CLink {
    @Autowired
    private NotificationService notifService;
    /**
     * Requête sur l'index
     * @param model le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @param response la réponse
     * @return Une vue sur l'index
     * @throws Exception 
     */
    @RequestMapping(value = "index", method = RequestMethod.GET)
    protected ModelAndView linkToIndex(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>)model.asMap().get("alerts");
        if (session==null)
            session = request.getSession(true);
        if (CSession.getLanguage(session)!=Translator.Language.FR)
            CSession.setLanguage(session,Translator.Language.FR);
        if (CSession.isConnected(session)) {
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
        }
        return new JNPPModelAndView("index", ViewInfo.createInfo(session, alerts));
    }
    /**
     * Requête sur la vue de validation d'inscription
     * @param model le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @param response la réponse
     * @return Une vue sur la validation d'inscription si l'utilisateur n'est pas connecté, redirection vers l'index sinon
     * @throws Exception 
     */
    @RequestMapping(value = "signupsuccess", method = RequestMethod.GET)
    protected ModelAndView linkToSignUpSuccess(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>)model.asMap().get("alerts");
        if (session==null)
            session = request.getSession(true);
        if (CSession.getLanguage(session)!=Translator.Language.FR)
            CSession.setLanguage(session,Translator.Language.FR);
        if (!CSession.isConnected(session))
            return new JNPPModelAndView("signup/signupsuccess", ViewInfo.createInfo(session, alerts));
        return new ModelAndView("redirect:/index.htm");
    }
    /**
     * Requête sur la vue du conseiller d'un utilisateur
     * @param model le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @param response la réponse
     * @return Une vue sur les informations du conseiller si l'utilisateur est connecté, une redirection vers l'index sinon
     * @throws Exception 
     */
    @RequestMapping(value = "advisor", method = RequestMethod.GET)
    protected ModelAndView linkToAdvisor(Model model, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>)model.asMap().get("alerts");
        if (session==null)
            session = request.getSession(true);
        if (CSession.getLanguage(session)!=Translator.Language.FR)
            CSession.setLanguage(session,Translator.Language.FR);
        if (CSession.isConnected(session)) {
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
            ModelAndView view =  new JNPPModelAndView("advisor/advisor", ViewInfo.createInfo(session, alerts));
            return view;
        }
        return new ModelAndView("redirect:/index.htm");
    }
    /**
     * Requête sur la vue de validation de regénération de mot de passe
     * @param model le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @param response la réponse
     * @return La vue de validation de regénération de mot de passe si l'utilisateur n'est pas connecté, redirection vers l'index sinon
     * @throws Exception 
     */
    @RequestMapping(value = "passwordsuccess", method = RequestMethod.GET)
    protected ModelAndView linkToPasswordSuccess(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>)model.asMap().get("alerts");
        if (session==null)
            session = request.getSession(true);
        if (CSession.getLanguage(session)!=Translator.Language.FR)
            CSession.setLanguage(session,Translator.Language.FR);
        if (!CSession.isConnected(session))
            return new JNPPModelAndView("manageuser/passwordsuccess", ViewInfo.createInfo(session, alerts));
        return new ModelAndView("redirect:/index.htm");
    }
    /**
     * Requête sur la vue des notifications
     * @param model le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @param response la réponse
     * @return Une vue sur la liste des notifications si l'utilisateur est connecté, redirection vers l'index sinon
     * @throws Exception 
     */
    @RequestMapping(value = "notifs", method = RequestMethod.GET)
    protected ModelAndView linkToNotifs(Model model, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rm) throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>)model.asMap().get("alerts");
        if (session==null)
            session = request.getSession(true);
        if (CSession.getLanguage(session)!=Translator.Language.FR)
            CSession.setLanguage(session,Translator.Language.FR);
        if (CSession.isConnected(session)) {
            try {
                List<NotificationDTO> notifs = notifService.receiveNotifications(CSession.getClient(session).getLogin());
                List<NotifView> notifsView = new ArrayList<NotifView>();
                for (NotificationDTO notif : notifs) {
                    notifsView.add(new NotifView(notif));
                }
                ModelAndView view = new JNPPModelAndView("manageuser/notifs", ViewInfo.createInfo(session, alerts));
                view.addObject("notifs", notifsView);
                notifService.seeAllNotications(CSession.getClient(session).getLogin());
                CSession.setHasNotif(session, false);
                return view;
            } catch (FakeClientException invalidClient) {
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Il semble y avoir une erreur dans votre session"));
                } else {
                    alerts = new ArrayList<AlertMessage>(); 
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Il semble y avoir une erreur dans votre session"));
                    rm.addFlashAttribute("alerts", alerts);   
                }
                return new ModelAndView("redirect:/index.htm");
            }
        }
        return new ModelAndView("redirect:/index.htm");
    }
}
