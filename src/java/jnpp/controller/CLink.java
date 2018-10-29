package jnpp.controller;

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
import jnpp.controller.views.info.ViewInfo;
import jnpp.dao.entities.Message;
import jnpp.dao.entities.clients.Advisor;
import jnpp.dao.entities.clients.Client;
import jnpp.dao.entities.clients.Gender;
import jnpp.dao.entities.clients.Identity;
import jnpp.dao.entities.clients.Private;
import jnpp.dao.entities.notifications.MessageNotification;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Classe contrôlant les différents liens qui ne requièrent pas de traitement particulier
 */
@Controller
public class CLink {
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
    @RequestMapping(value = "personalsignup", method = RequestMethod.GET)
    protected ModelAndView linkToPersonalSignUp(Model model, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>)model.asMap().get("alerts");
        if (session==null)
            session = request.getSession(true);
        if (CSession.getLanguage(session)!=Translator.Language.FR)
            CSession.setLanguage(session,Translator.Language.FR);
        if (!CSession.isConnected(session)) {
            ModelAndView view = new JNPPModelAndView("signup/personalsignup", ViewInfo.createInfo(session, alerts));
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
            ModelAndView view =  new JNPPModelAndView("advisor/advisor", ViewInfo.createInfo(session, alerts));
            Advisor advisor = new Advisor();
            Identity advisorIdentity = new Identity();
            advisorIdentity.setFirstname("toto");
            advisorIdentity.setLastname("tate");
            advisorIdentity.setGender(Gender.MALE);
            advisor.setIdentity(advisorIdentity);
            view.addObject("advisor", advisor);
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
     * Requête sur la vue des notifications
     * @param model le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @param response la réponse
     * @return Une vue sur la liste des notifications si l'utilisateur est connecté, redirection vers l'index sinon
     * @throws Exception 
     */
    @RequestMapping(value = "notifs", method = RequestMethod.GET)
    protected ModelAndView linkToNotifs(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>)model.asMap().get("alerts");
        if (session==null)
            session = request.getSession(true);
        if (CSession.getLanguage(session)!=Translator.Language.FR)
            CSession.setLanguage(session,Translator.Language.FR);
        if (CSession.isConnected(session)) {
            MessageNotification mes = new MessageNotification();
            mes.setDate(new GregorianCalendar(2018, Calendar.OCTOBER, 20).getTime());
            Message text = new Message();
            text.setContent("totot");
            mes.setMessage(text);
            List<NotifView> notifs = new ArrayList<NotifView>();
            notifs.add(new NotifView(mes));
            ModelAndView view = new JNPPModelAndView("manageuser/notifs", ViewInfo.createInfo(session, alerts));
            view.addObject("notifs", notifs);
            return view;
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
        Private client = new Private();
        Identity identity = new Identity();
        identity.setFirstname("to");
        identity.setLastname("ta");
        identity.setGender(Gender.MALE);
        client.setIdentity(identity);
        client.setEmail("bala@to.fr");
        client.setBirthday(new GregorianCalendar(2018, Calendar.OCTOBER, 20).getTime());
        client.setPhone("0549908657");
        ModelAndView view = null;
        switch (client.getType()) {
            case PRIVATE:
                view = new JNPPModelAndView("manageuser/personalinfo", ViewInfo.createInfo(session, alerts));
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
