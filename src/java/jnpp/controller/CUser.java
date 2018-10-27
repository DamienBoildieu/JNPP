package jnpp.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jnpp.common.AlertEnum;
import jnpp.common.AlertMessage;
import jnpp.common.CSession;

import jnpp.common.UnconnectedInfo;
import jnpp.common.UnconnectedModelAndView;
import jnpp.service.ISUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Classe contrôlant la gestion des utilisateurs
 */
@Controller
public class CUser {
    /**
     * Le service des utilisateurs
     */
    @Autowired
    ISUser userService;
    /**
     * Requête du formulaire de connexion, essaie de connecter l'utilisateur
     * @param model le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @param response la réponse
     * @param rm objet dans lequel on ajoute les informations que l'on veut voir transiter lors des redirections
     * @return Une redirection vers le menu utilisateur si la connexion a réussie, une redirection vers le formulaire de connexion si elle a échouée,
     * une redireciton vers l'index si l'utilisateur était déjà connecté
     * @throws Exception 
     */
    @RequestMapping(value = "connect", method = RequestMethod.POST)
    protected ModelAndView connect(Model model, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rm) throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>)model.asMap().get("alerts");
        if (!CSession.hasSession(session)) {
            String id = request.getParameter("account");
            String password = request.getParameter("password");
            if (this.userService.signIn(id, password)) {
                session = request.getSession(true);
                CSession.setFirstName(session, "user");
                CSession.setLastName(session, "dom");
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.SUCCESS, "Connexion réussie"));
                } else {
                    alerts = new ArrayList<AlertMessage>(); 
                    alerts.add(new AlertMessage(AlertEnum.SUCCESS, "Connexion réussie"));
                    rm.addFlashAttribute("alerts", alerts);
                }
                
                return new ModelAndView("redirect:/home.htm");
            } else {
                AlertMessage error = new AlertMessage(AlertEnum.ERROR, "Nom d'utilisateur ou mot de passe incorrect");
                if (alerts != null) {
                    alerts.add(error);
                    return new UnconnectedModelAndView("manageuser/connect", new UnconnectedInfo(alerts));
                } else {
                    return new UnconnectedModelAndView("manageuser/connect", new UnconnectedInfo(error));
                } 
            }
        }
        return new ModelAndView("redirect:/index.htm"); //ne devrait pas pouvoir arriver
    }
    /**
     * Requête de déconnexion
     * @param model le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @param response la réponse
     * @param rm objet dans lequel on ajoute les informations que l'on veut voir transiter lors des redirections
     * @return Déconnecte si l'utilisateur était connecté, redirige toujours vers l'index
     * @throws Exception 
     */
    @RequestMapping(value = "disconnect", method = RequestMethod.GET)
    ModelAndView disconnect(Model model, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rm) throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>)model.asMap().get("alerts");
        if (CSession.hasSession(session)) {
            if (this.userService.signOut("")) {
                CSession.clearSession(session);
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.SUCCESS, "Déconnexion réussie"));
                } else {
                    alerts = new ArrayList<AlertMessage>(); 
                    alerts.add(new AlertMessage(AlertEnum.SUCCESS, "Déconnexion réussie"));
                    rm.addFlashAttribute("alerts", alerts);
                }
            } else {
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Erreur lors de la déconnexion"));
                } else {
                    alerts = new ArrayList<AlertMessage>(); 
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Erreur lors de la déconnexion"));
                    rm.addFlashAttribute("alerts", alerts);
                }
            }
        }
        return new ModelAndView("redirect:/index.htm");
    }
    /**
     * Requête du formulaire d'inscription d'un particulier
     * @param model le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @param response la réponse
     * @param rm objet dans lequel on ajoute les informations que l'on veut voir transiter lors des redirections
     * @return Une redirection vers l'index si l'inscription a réussit ou si l'utilisateur était connecté,
     * reste sur la page d'inscription si elle a échouée,
     * @throws Exception 
     */
    @RequestMapping(value = "personalsignup", method = RequestMethod.POST)
    protected ModelAndView validatePersonalSignUp(Model model, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rm)
            throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>)model.asMap().get("alerts");
        if (!CSession.hasSession(session)) {
            String id = request.getParameter("account");
            String password = request.getParameter("password");
            if (!userService.signUp(id, password)) {
                AlertMessage error = new AlertMessage(AlertEnum.ERROR, "Identifiant indisponible");
                if (alerts != null)  {
                    alerts.add(error);
                    return new UnconnectedModelAndView("signup/personalsignup", new UnconnectedInfo(alerts));
                } else {
                    return new UnconnectedModelAndView("signup/personalsignup", new UnconnectedInfo(error));
                }
            } else {
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.SUCCESS, "Inscription réussie"));
                } else {
                    alerts = new ArrayList<AlertMessage>(); 
                    alerts.add(new AlertMessage(AlertEnum.SUCCESS, "Inscription réussie"));
                    rm.addFlashAttribute("alerts", alerts);    
                }
                return new ModelAndView("redirect:/index.htm");
            }
        }
        return new ModelAndView("redirect:/index.htm"); //ne devrait pas arriver
    }
    /**
     * Requête du formulaire d'inscription d'un professionnel
     * @param model le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @param response la réponse
     * @param rm objet dans lequel on ajoute les informations que l'on veut voir transiter lors des redirections
     * @return Une redirection vers l'index si l'inscription a réussit ou si l'utilisateur était connecté,
     * reste sur la page d'inscription si elle a échouée,
     * @throws Exception 
     */
    @RequestMapping(value = "professionalsignup", method = RequestMethod.POST)
    protected ModelAndView validateProfessionalSignUp(Model model, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rm)
            throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>)model.asMap().get("alerts");
        if (!CSession.hasSession(session)) {
            String id = request.getParameter("account");
            String password = request.getParameter("password");
            if (!userService.signUp(id, password)) {
                AlertMessage error = new AlertMessage(AlertEnum.ERROR, "Identifiant indisponible");
                if (alerts != null)  {
                    alerts.add(error);
                    return new UnconnectedModelAndView("signup/professionalsignup", new UnconnectedInfo(alerts));
                } else {
                    return new UnconnectedModelAndView("signup/professionalsignup", new UnconnectedInfo(error));
                }
            } else {
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.SUCCESS, "Inscription réussie"));
                } else {
                    alerts = new ArrayList<AlertMessage>(); 
                    alerts.add(new AlertMessage(AlertEnum.SUCCESS, "Inscription réussie"));
                    rm.addFlashAttribute("alerts", alerts);    
                }
                return new ModelAndView("redirect:/index.htm");
            }
        }
        return new ModelAndView("redirect:/index.htm"); //ne devrait pas arriver
    }
}
