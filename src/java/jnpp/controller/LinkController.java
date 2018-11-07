package jnpp.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import jnpp.controller.views.JNPPModelAndView;
import jnpp.controller.views.NotifView;
import jnpp.controller.views.Translator;
import jnpp.controller.views.alerts.AlertEnum;
import jnpp.controller.views.alerts.AlertMessage;
import jnpp.controller.views.info.ViewInfo;
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
public class LinkController {
    /**
     * Le service des notifications
     */
    @Autowired
    private NotificationService notifService;
    /**
     * Requête sur l'index
     * @param model le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @param rm objet dans lequel on ajoute les informations que l'on veut voir transiter lors des redirections
     * @return Une vue sur l'index
     * @throws Exception 
     */
    @RequestMapping(value = "index", method = RequestMethod.GET)
    protected ModelAndView linkToIndex(Model model, HttpServletRequest request, RedirectAttributes rm) throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>)model.asMap().get("alerts");
        if (session==null)
            session = request.getSession(true);
        if (SessionController.getLanguage(session)!=Translator.Language.FR)
            SessionController.setLanguage(session,Translator.Language.FR);
        if (SessionController.isConnected(session)) {
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
                        rm.addFlashAttribute("alerts", alerts);
                    }
                    return new ModelAndView("redirect:/disconnect.htm");
                }
            }
        }
        return new JNPPModelAndView("index", ViewInfo.createInfo(session, alerts));
    }
    /**
     * Requête sur la vue de validation d'inscription
     * @param model le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @return Une vue sur la validation d'inscription si l'utilisateur n'est pas connecté, redirection vers l'index sinon
     * @throws Exception 
     */
    @RequestMapping(value = "signupsuccess", method = RequestMethod.GET)
    protected ModelAndView linkToSignUpSuccess(Model model, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>)model.asMap().get("alerts");
        if (session==null)
            session = request.getSession(true);
        if (SessionController.getLanguage(session)!=Translator.Language.FR)
            SessionController.setLanguage(session,Translator.Language.FR);
        if (!SessionController.isConnected(session))
            return new JNPPModelAndView("signup/signupsuccess", ViewInfo.createInfo(session, alerts));
        return new ModelAndView("redirect:/index.htm");
    }
    /**
     * Requête sur la vue de validation de regénération de mot de passe
     * @param model le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @return La vue de validation de regénération de mot de passe si l'utilisateur n'est pas connecté, redirection vers l'index sinon
     * @throws Exception 
     */
    @RequestMapping(value = "passwordsuccess", method = RequestMethod.GET)
    protected ModelAndView linkToPasswordSuccess(Model model, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>)model.asMap().get("alerts");
        if (session==null)
            session = request.getSession(true);
        if (SessionController.getLanguage(session)!=Translator.Language.FR)
            SessionController.setLanguage(session,Translator.Language.FR);
        if (!SessionController.isConnected(session))
            return new JNPPModelAndView("manageuser/passwordsuccess", ViewInfo.createInfo(session, alerts));
        return new ModelAndView("redirect:/index.htm");
    }
}
