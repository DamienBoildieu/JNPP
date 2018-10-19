package jnpp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jnpp.common.CSession;

import jnpp.common.UnconnectedInfo;
import jnpp.common.UnconnectedModelAndView;
import jnpp.service.ISUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CUser {

    @Autowired
    ISUser userService;

    @RequestMapping(value = "connect", method = RequestMethod.POST)
    protected ModelAndView connect(HttpServletRequest request, HttpServletResponse response) throws Exception {
        CSession session = CSession.getInstance();
        if (!session.hasSession()) {
            String id = request.getParameter("account");
            String password = request.getParameter("password");
            if (this.userService.signIn(id, password)) {
                session.setSession(request.getSession(true));
                session.setFirstName("user");
                session.setLastName("dom");
                return new ModelAndView("redirect:/resume.htm");
            }
        }
        return new ModelAndView("redirect:/index.htm");
    }

    @RequestMapping(value = "disconnect", method = RequestMethod.GET)
    ModelAndView disconnect(HttpServletRequest request, HttpServletResponse response) throws Exception {
        CSession session = CSession.getInstance();
        if (session.hasSession()) {
            this.userService.signOut("");
            session.clearSession();
        }
        return new ModelAndView("redirect:/index.htm");
    }

    @RequestMapping(value = "personalsignup", method = RequestMethod.POST)
    protected ModelAndView validatePersonalSignUp(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        CSession session = CSession.getInstance();
        if (!session.hasSession()) {
            String id = request.getParameter("account");
            String password = request.getParameter("password");
            if (!userService.signUp(id, password)) {
                return new UnconnectedModelAndView("personalsignup", new UnconnectedInfo());
            }
        }
        return new ModelAndView("redirect:/index.htm");
    }
}
