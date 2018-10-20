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

@Controller
public class CUser {

    @Autowired
    ISUser userService;

    @RequestMapping(value = "connect", method = RequestMethod.POST)
    protected ModelAndView connect(Model model, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rm) throws Exception {
        CSession session = CSession.getInstance();
        List<AlertMessage> alerts = (List<AlertMessage>)model.asMap().get("alerts");
        if (!session.hasSession()) {
            String id = request.getParameter("account");
            String password = request.getParameter("password");
            if (this.userService.signIn(id, password)) {
                session.setSession(request.getSession(true));
                session.setFirstName("user");
                session.setLastName("dom");
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.SUCCESS, "Connexion réussie"));
                } else {
                    alerts = new ArrayList<AlertMessage>(); 
                    alerts.add(new AlertMessage(AlertEnum.SUCCESS, "Connexion réussie"));
                    rm.addFlashAttribute("alerts", alerts);
                }
                
                return new ModelAndView("redirect:/resume.htm");
            } else {
                AlertMessage error = new AlertMessage(AlertEnum.ERROR, "Nom d'utilisateur ou mot de passe incorrect");
                if (alerts != null) {
                    alerts.add(error);
                    return new UnconnectedModelAndView("connect", new UnconnectedInfo(alerts));
                } else {
                    return new UnconnectedModelAndView("connect", new UnconnectedInfo(error));
                } 
            }
        }
        return new ModelAndView("redirect:/index.htm");
    }

    @RequestMapping(value = "disconnect", method = RequestMethod.GET)
    ModelAndView disconnect(Model model, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rm) throws Exception {
        CSession session = CSession.getInstance();
        List<AlertMessage> alerts = (List<AlertMessage>)model.asMap().get("alerts");
        if (session.hasSession()) {
            if (this.userService.signOut("")) {
                session.clearSession();
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

    @RequestMapping(value = "personalsignup", method = RequestMethod.POST)
    protected ModelAndView validatePersonalSignUp(Model model, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rm)
            throws Exception {
        CSession session = CSession.getInstance();
        List<AlertMessage> alerts = (List<AlertMessage>)model.asMap().get("alerts");
        if (!session.hasSession()) {
            String id = request.getParameter("account");
            String password = request.getParameter("password");
            if (!userService.signUp(id, password)) {
                AlertMessage error = new AlertMessage(AlertEnum.ERROR, "Identifiant indisponible");
                if (alerts != null)  {
                    alerts.add(error);
                    return new UnconnectedModelAndView("personalsignup", new UnconnectedInfo(alerts));
                } else {
                    return new UnconnectedModelAndView("personalsignup", new UnconnectedInfo(error));
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
        return new ModelAndView("redirect:/index.htm");
    }
}
