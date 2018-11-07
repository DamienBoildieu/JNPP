package jnpp.controller;

import java.text.SimpleDateFormat;
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
 * La classe contrôlant les liens vers les pages de gestion d'un utilisateur
 */
@Controller
public class LinkUserController {

    /**
     * Le service de notifications
     */
    @Autowired
    private NotificationService notifService;

    /**
     * Requête sur la vue de connexion
     *
     * @param model le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @return Une vue sur la connexion si l'utilisateur n'est pas connecté,
     * redirection vers l'index sinon
     * @throws Exception Exception non controllees.
     */
    @RequestMapping(value = "connect", method = RequestMethod.GET)
    protected ModelAndView linkToConnect(Model model, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>) model.asMap().get("alerts");
        if (session == null) {
            session = request.getSession(true);
        }
        if (SessionController.getLanguage(session) != Translator.Language.FR) {
            SessionController.setLanguage(session, Translator.Language.FR);
        }
        if (!SessionController.isConnected(session)) {
            return new JNPPModelAndView("manageuser/connect", ViewInfo.createInfo(session, alerts));
        }
        return new ModelAndView("redirect:/index.htm");
    }

    /**
     * Requête sur la vue d'inscription
     *
     * @param model le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @return Une vue sur les différentes inscriptions possibles si
     * l'utilisateur n'est pas connecté, redirection vers l'index sinon
     * @throws Exception Exception non controllees.
     */
    @RequestMapping(value = "signup", method = RequestMethod.GET)
    protected ModelAndView linkToSignUp(Model model, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>) model.asMap().get("alerts");
        if (session == null) {
            session = request.getSession(true);
        }
        if (SessionController.getLanguage(session) != Translator.Language.FR) {
            SessionController.setLanguage(session, Translator.Language.FR);
        }
        if (!SessionController.isConnected(session)) {
            return new JNPPModelAndView("signup/signup", ViewInfo.createInfo(session, alerts));
        }
        return new ModelAndView("redirect:/index.htm");
    }

    /**
     * Requête sur la vue d'inscription d'un particulier
     *
     * @param model le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @return Une vue sur l'inscription d'un particulier si l'utilisateur n'est
     * pas connecté, redirection vers l'index sinon
     * @throws Exception Exception non controllees.
     */
    @RequestMapping(value = "privatesignup", method = RequestMethod.GET)
    protected ModelAndView linkToPersonalSignUp(Model model, HttpServletRequest request)
            throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>) model.asMap().get("alerts");
        if (session == null) {
            session = request.getSession(true);
        }
        if (SessionController.getLanguage(session) != Translator.Language.FR) {
            SessionController.setLanguage(session, Translator.Language.FR);
        }
        if (!SessionController.isConnected(session)) {
            ModelAndView view = new JNPPModelAndView("signup/privatesignup", ViewInfo.createInfo(session, alerts));
            view.addObject("genders", IdentityDTO.Gender.values());
            view.addObject("gendersMap", Translator.getInstance().translateGenders(SessionController.getLanguage(session)));
            return view;
        }
        return new ModelAndView("redirect:/index.htm");
    }

    /**
     * Requête sur la vue d'inscription d'un professionnel
     *
     * @param model le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @return Une vue sur l'inscription d'un professionnel si l'utilisateur
     * n'est pas connecté, redirection vers l'index sinon
     * @throws Exception Exception non controllees.
     */
    @RequestMapping(value = "professionalsignup", method = RequestMethod.GET)
    protected ModelAndView linkToProfessionalSignUp(Model model, HttpServletRequest request)
            throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>) model.asMap().get("alerts");
        if (session == null) {
            session = request.getSession(true);
        }
        if (SessionController.getLanguage(session) != Translator.Language.FR) {
            SessionController.setLanguage(session, Translator.Language.FR);
        }
        if (!SessionController.isConnected(session)) {
            ModelAndView view = new JNPPModelAndView("signup/professionalsignup", ViewInfo.createInfo(session, alerts));
            view.addObject("genders", IdentityDTO.Gender.values());
            view.addObject("gendersMap", Translator.getInstance().translateGenders(SessionController.getLanguage(session)));
            return view;
        }
        return new ModelAndView("redirect:/index.htm");
    }

    /**
     * Requête sur la vue du menu d'un utilisateur connecté
     *
     * @param model le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @param rm objet dans lequel on ajoute les informations que l'on veut voir
     * transiter lors des redirections
     * @return Une vue sur le menu de l'utilisateur si il est connecté,
     * redirection vers l'index sinon
     * @throws Exception Exception non controllees.
     */
    @RequestMapping(value = "home", method = RequestMethod.GET)
    protected ModelAndView linkToResume(Model model, HttpServletRequest request, RedirectAttributes rm) throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>) model.asMap().get("alerts");
        if (session == null) {
            session = request.getSession(true);
        }
        if (SessionController.getLanguage(session) != Translator.Language.FR) {
            SessionController.setLanguage(session, Translator.Language.FR);
        }
        if (!SessionController.isConnected(session)) {
            return new ModelAndView("redirect:/index.htm");
        }
        Boolean hasNotif = SessionController.getHasNotif(session);
        if (!hasNotif) {
            try {
                hasNotif = notifService.receiveUnseenNotifications(SessionController.getClient(session).getLogin()).size() > 0;
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
        ModelAndView view = new JNPPModelAndView("manageuser/home", ViewInfo.createInfo(session, alerts));
        return view;
    }

    /**
     * Requête sur la vue des informations de l'utilisateur
     *
     * @param model le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @param rm objet dans lequel on ajoute les informations que l'on veut voir
     * transiter lors des redirections
     * @return Une vue sur les informations de l'utilisateur si il est connecté,
     * redirection vers l'index sinon
     * @throws Exception Exception non controllees.
     */
    @RequestMapping(value = "userinfo", method = RequestMethod.GET)
    protected ModelAndView linkToUserInfo(Model model, HttpServletRequest request, RedirectAttributes rm) throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>) model.asMap().get("alerts");
        if (session == null) {
            session = request.getSession(true);
        }
        if (SessionController.getLanguage(session) != Translator.Language.FR) {
            SessionController.setLanguage(session, Translator.Language.FR);
        }
        if (!SessionController.isConnected(session)) {
            return new ModelAndView("redirect:/index.htm");
        }
        Boolean hasNotif = SessionController.getHasNotif(session);
        if (!hasNotif) {
            try {
                hasNotif = notifService.receiveUnseenNotifications(SessionController.getClient(session).getLogin()).size() > 0;
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
        ClientDTO client = SessionController.getClient(session);
        ModelAndView view = null;
        switch (client.getType()) {
            case PRIVATE:
                view = new JNPPModelAndView("manageuser/privateinfo", ViewInfo.createInfo(session, alerts));
                String birthday = new SimpleDateFormat("yyyy-MM-dd").format(((PrivateDTO) client).getBirthday());
                view.addObject("birthday", birthday);
                break;
            case PROFESIONAL:
                view = new JNPPModelAndView("manageuser/professionalinfo", ViewInfo.createInfo(session, alerts));
                break;
            default:
                throw new AssertionError(client.getType().name());
        }
        view.addObject("gendersMap", Translator.getInstance().translateGenders(SessionController.getLanguage(session)));

        view.addObject("client", client);
        return view;
    }

    /**
     * Requête sur la vue de perte de mot de passe
     *
     * @param model le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @return Une vue sur le formulaire de choix de mot passe si l'utilisateur
     * n'est pas connecté, redirection vers l'index sinon
     * @throws Exception Exception non controllees.
     */
    @RequestMapping(value = "password", method = RequestMethod.GET)
    protected ModelAndView linkToPassword(Model model, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>) model.asMap().get("alerts");
        if (session == null) {
            session = request.getSession(true);
        }
        if (SessionController.getLanguage(session) != Translator.Language.FR) {
            SessionController.setLanguage(session, Translator.Language.FR);
        }
        if (!SessionController.isConnected(session)) {
            return new JNPPModelAndView("manageuser/password", ViewInfo.createInfo(session, alerts));
        }
        return new ModelAndView("redirect:/index.htm");
    }

    /**
     * Requête sur la vue de perte de mot de passe pour les particuliers
     *
     * @param model le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @return Une vue sur le formulaire de regénération de mot de passe pour
     * les particuliers si l'utilisateur n'est pas connecté, redirection vers
     * l'index sinon
     * @throws Exception Exception non controllees.
     */
    @RequestMapping(value = "privatepassword", method = RequestMethod.GET)
    protected ModelAndView linkToPrivatePassword(Model model, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>) model.asMap().get("alerts");
        if (session == null) {
            session = request.getSession(true);
        }
        if (SessionController.getLanguage(session) != Translator.Language.FR) {
            SessionController.setLanguage(session, Translator.Language.FR);
        }
        if (!SessionController.isConnected(session)) {
            return new JNPPModelAndView("manageuser/privatepassword", ViewInfo.createInfo(session, alerts));
        }
        return new ModelAndView("redirect:/index.htm");
    }

    /**
     * Requête sur la vue de perte de mot de passe pour les professionnels
     *
     * @param model le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @return Une vue sur le formulaire de regénération de mot de passe pour
     * les particuliers si l'utilisateur n'est pas connecté, redirection vers
     * l'index sinon
     * @throws Exception Exception non controllees.
     */
    @RequestMapping(value = "professionalpassword", method = RequestMethod.GET)
    protected ModelAndView linkToProfessionalPassword(Model model, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>) model.asMap().get("alerts");
        if (session == null) {
            session = request.getSession(true);
        }
        if (SessionController.getLanguage(session) != Translator.Language.FR) {
            SessionController.setLanguage(session, Translator.Language.FR);
        }
        if (!SessionController.isConnected(session)) {
            return new JNPPModelAndView("manageuser/professionalpassword", ViewInfo.createInfo(session, alerts));
        }
        return new ModelAndView("redirect:/index.htm");
    }

    /**
     * Requête sur la vue des notifications
     *
     * @param model le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @param rm objet dans lequel on ajoute les informations que l'on veut voir
     * transiter lors des redirections
     * @return Une vue sur la liste des notifications si l'utilisateur est
     * connecté, redirection vers l'index sinon
     * @throws Exception Exception non controllees.
     */
    @RequestMapping(value = "notifs", method = RequestMethod.GET)
    protected ModelAndView linkToNotifs(Model model, HttpServletRequest request, RedirectAttributes rm) throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>) model.asMap().get("alerts");
        if (session == null) {
            session = request.getSession(true);
        }
        if (SessionController.getLanguage(session) != Translator.Language.FR) {
            SessionController.setLanguage(session, Translator.Language.FR);
        }
        if (SessionController.isConnected(session)) {
            try {
                List<NotificationDTO> notifs = notifService.receiveNotifications(SessionController.getClient(session).getLogin());
                List<NotifView> notifsView = new ArrayList<NotifView>();
                for (NotificationDTO notif : notifs) {
                    notifsView.add(new NotifView(notif));
                }
                notifService.seeAllNotications(SessionController.getClient(session).getLogin());
                SessionController.setHasNotif(session, false);
                ModelAndView view = new JNPPModelAndView("manageuser/notifs", ViewInfo.createInfo(session, alerts));
                view.addObject("notifs", notifsView);
                return view;
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
        return new ModelAndView("redirect:/index.htm");
    }
}
