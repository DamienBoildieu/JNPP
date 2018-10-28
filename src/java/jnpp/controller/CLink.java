package jnpp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jnpp.common.AlertMessage;
import jnpp.common.CSession;
import jnpp.common.ConnectedInfo;
import jnpp.common.JNPPModelAndView;
import jnpp.common.UnconnectedInfo;
import jnpp.stubs.AccountStub;
import jnpp.stubs.AdvisorStub;

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
        if (!CSession.hasSession(session))
            return new JNPPModelAndView("index", new UnconnectedInfo(alerts));
        return new JNPPModelAndView("index", new ConnectedInfo(CSession.getFirstName(session), CSession.getLastName(session), alerts));
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
        if (!CSession.hasSession(session))
            return new JNPPModelAndView("manageuser/connect", new UnconnectedInfo(alerts));
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
        if (!CSession.hasSession(session))
            return new JNPPModelAndView("signup/signup", new UnconnectedInfo(alerts));
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
        if (!CSession.hasSession(session))
            return new JNPPModelAndView("signup/personalsignup", new UnconnectedInfo(alerts));
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
        if (!CSession.hasSession(session))
            return new JNPPModelAndView("signup/professionalsignup", new UnconnectedInfo(alerts));
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
        if (CSession.hasSession(session)) {
            ModelAndView view =  new JNPPModelAndView("manageuser/advisor", new ConnectedInfo(CSession.getFirstName(session), CSession.getLastName(session), alerts));
            AdvisorStub advisor = new AdvisorStub("Toto", "Tata", "jnpp", "05499878464");
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
    protected ModelAndView linktoResume(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>)model.asMap().get("alerts");
        if (!CSession.hasSession(session))
            return new ModelAndView("redirect:/index.htm");
        ModelAndView view = new JNPPModelAndView("manageuser/home", new ConnectedInfo(CSession.getFirstName(session), CSession.getLastName(session), alerts));
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
    protected ModelAndView linktoPassword(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>)model.asMap().get("alerts");
        if (!CSession.hasSession(session))
            return new JNPPModelAndView("manageuser/password", new UnconnectedInfo(alerts));
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
    protected ModelAndView linktoNotifs(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>)model.asMap().get("alerts");
        if (CSession.hasSession(session))
            return new JNPPModelAndView("manageuser/notifs", new UnconnectedInfo(alerts));
        return new ModelAndView("redirect:/index.htm");
    }
}
