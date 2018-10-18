package jnpp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
        HttpSession session = request.getSession(false);
        if ((session == null) || (session.getAttribute("firstName") == null)) {
            String id = request.getParameter("account");
            String password = request.getParameter("password");
            if (userService.signIn(id, password)) {
                session = request.getSession(true);
                session.setAttribute("firstName", "user");
                session.setAttribute("lastName", "dom");
                return new ModelAndView("redirect:/resume.htm");
            }
        }
        return new ModelAndView("redirect:/index.htm");
    }

    @RequestMapping(value = "connect", method = RequestMethod.GET)
    protected ModelAndView linkToConnect(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(false);
        if ((session == null) || (session.getAttribute("firstName") == null)) {
            return new UnconnectedModelAndView("connect", new UnconnectedInfo());
        }
        return new ModelAndView("redirect:/index.htm");
    }

    @RequestMapping(value = "disconnect", method = RequestMethod.GET)
    ModelAndView disconnect(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(false);
        if (session != null) {
            userService.signOut("");
            if (session.getAttribute("firstName") != null) {
                session.removeAttribute("firstName");
            }
            if (session.getAttribute("lastName") != null) {
                session.removeAttribute("lastName");
            }
        }
        return new ModelAndView("redirect:/index.htm");
    }

    @RequestMapping(value = "signup", method = RequestMethod.GET)
    protected ModelAndView linkToSignUp(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(false);
        if ((session == null) || session.getAttribute("firstName") == null) // evaluation paresseuse
        {
            return new UnconnectedModelAndView("signup", new UnconnectedInfo());
        }
        return new ModelAndView("redirect:/index.htm");
    }

    @RequestMapping(value = "personalsignup", method = RequestMethod.GET)
    protected ModelAndView linkToPersonalSignUp(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        HttpSession session = request.getSession(false);
        if ((session == null) || session.getAttribute("firstName") == null) // evaluation paresseuse
        {
            return new UnconnectedModelAndView("personalsignup", new UnconnectedInfo());
        }
        return new ModelAndView("redirect:/index.htm");
    }

    @RequestMapping(value = "personalsignup", method = RequestMethod.POST)
    protected ModelAndView validatePersonalSignUp(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        HttpSession session = request.getSession(false);
        if ((session == null) || session.getAttribute("firstName") == null) // evaluation paresseuse
        {
            String id = request.getParameter("account");
            String password = request.getParameter("password");
            if (!userService.signUp(id, password)) {
                return new UnconnectedModelAndView("personalsignup", new UnconnectedInfo());
            }
        }
        return new ModelAndView("redirect:/index.htm");
    }
}
