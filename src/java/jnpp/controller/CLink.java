package jnpp.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jnpp.controller.views.alerts.AlertMessage;
import jnpp.controller.views.JNPPModelAndView;
import jnpp.controller.views.NotifView;
import jnpp.controller.views.Translator;
import jnpp.controller.views.alerts.AlertEnum;
import jnpp.controller.views.info.ViewInfo;
import jnpp.dao.entities.advisor.MessageEntity;
import jnpp.dao.entities.advisor.AdvisorEntity;
import jnpp.dao.entities.clients.ClientEntity;
import jnpp.dao.entities.Gender;
import jnpp.dao.entities.Identity;
import jnpp.dao.entities.clients.PrivateEntity;
import jnpp.dao.entities.notifications.MessageNotificationEntity;
import jnpp.dao.entities.notifications.NotificationEntity;
import jnpp.service.exceptions.entities.FakeClientException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jnpp.service.services.NotificationService;

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
                    hasNotif = notifService.receiveUnseenNotifications(CSession.getClient(session)).size()>0;
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
     * Requête sur la vue de connexion
     * @param model le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @param response la réponse
     * @return Une vue sur la connexion si l'utilisateur n'est pas connecté, redirection vers l'index sinon
     * @throws Exception 
     */
    @RequestMapping(value = "connect", method = RequestMethod.GET)
    protected ModelAndView linkToConnect(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>)model.asMap().get("alerts");
        if (session==null)
            session = request.getSession(true);
        if (CSession.getLanguage(session)!=Translator.Language.FR)
            CSession.setLanguage(session,Translator.Language.FR);
        if (!CSession.isConnected(session))
            return new JNPPModelAndView("manageuser/connect", ViewInfo.createInfo(session, alerts));
        return new ModelAndView("redirect:/index.htm");
    }
    /**
     * Requête sur la vue d'inscription
     * @param model le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @param response la réponse
     * @return Une vue sur les différentes inscriptions possibles si l'utilisateur n'est pas connecté, redirection vers l'index sinon
     * @throws Exception 
     */
    @RequestMapping(value = "signup", method = RequestMethod.GET)
    protected ModelAndView linkToSignUp(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>)model.asMap().get("alerts");
        if (session==null)
            session = request.getSession(true);
        if (CSession.getLanguage(session)!=Translator.Language.FR)
            CSession.setLanguage(session,Translator.Language.FR);
        if (!CSession.isConnected(session))
            return new JNPPModelAndView("signup/signup", ViewInfo.createInfo(session, alerts));
        return new ModelAndView("redirect:/index.htm");
    }
    /**
     * Requête sur la vue d'inscription d'un particulier
     * @param model le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @param response la réponse
     * @return Une vue sur l'inscription d'un particulier si l'utilisateur n'est pas connecté, redirection vers l'index sinon
     * @throws Exception 
     */
    @RequestMapping(value = "privatesignup", method = RequestMethod.GET)
    protected ModelAndView linkToPersonalSignUp(Model model, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>)model.asMap().get("alerts");
        if (session==null)
            session = request.getSession(true);
        if (CSession.getLanguage(session)!=Translator.Language.FR)
            CSession.setLanguage(session,Translator.Language.FR);
        if (!CSession.isConnected(session)) {
            ModelAndView view = new JNPPModelAndView("signup/privatesignup", ViewInfo.createInfo(session, alerts));
            view.addObject("genders", Gender.values());
            view.addObject("gendersMap", Translator.getInstance().translateGenders(CSession.getLanguage(session)));
            return view;
        }
        return new ModelAndView("redirect:/index.htm");
    }
    /**
     * Requête sur la vue d'inscription d'un professionnel
     * @param model le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @param response la réponse
     * @return Une vue sur l'inscription d'un professionnel si l'utilisateur n'est pas connecté, redirection vers l'index sinon
     * @throws Exception 
     */
    @RequestMapping(value = "professionalsignup", method = RequestMethod.GET)
    protected ModelAndView linkToProfessionalSignUp(Model model, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>)model.asMap().get("alerts");
        if (session==null)
            session = request.getSession(true);
        if (CSession.getLanguage(session)!=Translator.Language.FR)
            CSession.setLanguage(session,Translator.Language.FR);
        if (!CSession.isConnected(session)) {
            ModelAndView view = new JNPPModelAndView("signup/professionalsignup", ViewInfo.createInfo(session, alerts));
            view.addObject("genders", Gender.values());
            view.addObject("gendersMap", Translator.getInstance().translateGenders(CSession.getLanguage(session)));
            return view;
        }
        return new ModelAndView("redirect:/index.htm");
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
                    hasNotif = notifService.receiveUnseenNotifications(CSession.getClient(session)).size()>0;
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
     * Requête sur la vue du menu d'un utilisateur connecté
     * @param model le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @param response la réponse
     * @return Une vue sur le menu de l'utilisateur si il est connecté, redirection vers l'index sinon
     * @throws Exception 
     */
    @RequestMapping(value = "home", method = RequestMethod.GET)
    protected ModelAndView linkToResume(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
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
                hasNotif = notifService.receiveUnseenNotifications(CSession.getClient(session)).size()>0;
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
        ModelAndView view = new JNPPModelAndView("manageuser/home", ViewInfo.createInfo(session, alerts));
        return view;
    }
    /**
     * Requête sur la vue de perte de mot de passe
     * @param model le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @param response la réponse
     * @return Une vue sur le formulaire de choix de mot passe si l'utilisateur n'est pas connecté, redirection vers l'index sinon
     * @throws Exception 
     */
    @RequestMapping(value = "password", method = RequestMethod.GET)
    protected ModelAndView linkToPassword(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>)model.asMap().get("alerts");
        if (session==null)
            session = request.getSession(true);
        if (CSession.getLanguage(session)!=Translator.Language.FR)
            CSession.setLanguage(session,Translator.Language.FR);
        if (!CSession.isConnected(session))
            return new JNPPModelAndView("manageuser/password", ViewInfo.createInfo(session, alerts));
        return new ModelAndView("redirect:/index.htm");
    }
    /**
     * Requête sur la vue de perte de mot de passe pour les particuliers
     * @param model le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @param response la réponse
     * @return Une vue sur le formulaire de regénération de mot de passe pour les particuliers si l'utilisateur n'est pas connecté, redirection vers l'index sinon
     * @throws Exception 
     */
    @RequestMapping(value = "privatepassword", method = RequestMethod.GET)
    protected ModelAndView linkToPrivatePassword(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>)model.asMap().get("alerts");
        if (session==null)
            session = request.getSession(true);
        if (CSession.getLanguage(session)!=Translator.Language.FR)
            CSession.setLanguage(session,Translator.Language.FR);
        if (!CSession.isConnected(session))
            return new JNPPModelAndView("manageuser/privatepassword", ViewInfo.createInfo(session, alerts));
        return new ModelAndView("redirect:/index.htm");
    }
    /**
     * Requête sur la vue de perte de mot de passe pour les professionnels
     * @param model le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @param response la réponse
     * @return Une vue sur le formulaire de regénération de mot de passe pour les particuliers si l'utilisateur n'est pas connecté, redirection vers l'index sinon
     * @throws Exception 
     */
    @RequestMapping(value = "professionalpassword", method = RequestMethod.GET)
    protected ModelAndView linkToProfessionalPassword(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>)model.asMap().get("alerts");
        if (session==null)
            session = request.getSession(true);
        if (CSession.getLanguage(session)!=Translator.Language.FR)
            CSession.setLanguage(session,Translator.Language.FR);
        if (!CSession.isConnected(session))
            return new JNPPModelAndView("manageuser/professionalpassword", ViewInfo.createInfo(session, alerts));
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
                List<NotificationEntity> notifs = notifService.receiveNotifications(CSession.getClient(session));
                List<NotifView> notifsView = new ArrayList<NotifView>();
                for (NotificationEntity notif : notifs) {
                    notifsView.add(new NotifView(notif));
                }
                ModelAndView view = new JNPPModelAndView("manageuser/notifs", ViewInfo.createInfo(session, alerts));
                view.addObject("notifs", notifsView);
                notifService.seeAllNotications(CSession.getClient(session));
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
    /**
     * Requête sur la vue des informations de l'utilisateur
     * @param model le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @param response la réponse
     * @return Une vue sur les informations de l'utilisateur si il est connecté, redirection vers l'index sinon
     * @throws Exception 
     */
    @RequestMapping(value = "userinfo", method = RequestMethod.GET)
    protected ModelAndView linkToUserInfo(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
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
                hasNotif = notifService.receiveUnseenNotifications(CSession.getClient(session)).size()>0;
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
        ClientEntity client = CSession.getClient(session);
        ModelAndView view = null;
        switch (client.getType()) {
            case PRIVATE:
                view = new JNPPModelAndView("manageuser/privateinfo", ViewInfo.createInfo(session, alerts));
                String birthday = new SimpleDateFormat("yyyy-MM-dd").format(((PrivateEntity)client).getBirthday());
                view.addObject("birthday", birthday);
                break;
            case PROFESIONAL:
                view = new JNPPModelAndView("manageuser/professionalinfo", ViewInfo.createInfo(session, alerts));
                break;
            default:
                throw new AssertionError(client.getType().name());     
        }
        view.addObject("gendersMap", Translator.getInstance().translateGenders(CSession.getLanguage(session)));
        
        view.addObject("client", client);
        return view;
    }
}
