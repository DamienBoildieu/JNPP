/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jnpp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jnpp.common.CSession;
import jnpp.common.ConnectedInfo;
import jnpp.common.ConnectedModelAndView;
import jnpp.common.UnconnectedInfo;
import jnpp.common.UnconnectedModelAndView;
import jnpp.stubs.AdvisorStub;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author damien
 */
@Controller
public class CLink {
    @RequestMapping(value = "index", method = RequestMethod.GET)
    protected ModelAndView linkToIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
        CSession session = CSession.getInstance();
        if (!session.hasSession())
            return new UnconnectedModelAndView("index", new UnconnectedInfo());
        return new ConnectedModelAndView("index", new ConnectedInfo(session.getFirstName(), session.getLastName()));
    }
    
    @RequestMapping(value = "connect", method = RequestMethod.GET)
    protected ModelAndView linkToConnect(HttpServletRequest request, HttpServletResponse response) throws Exception {
        CSession session = CSession.getInstance();
        if (!session.hasSession())
            return new UnconnectedModelAndView("connect", new UnconnectedInfo());
        return new ModelAndView("redirect:/index.htm");
    }
    
    @RequestMapping(value = "signup", method = RequestMethod.GET)
    protected ModelAndView linkToSignUp(HttpServletRequest request, HttpServletResponse response) throws Exception {
        CSession session = CSession.getInstance();
        if (!session.hasSession())
            return new UnconnectedModelAndView("signup", new UnconnectedInfo());
        return new ModelAndView("redirect:/index.htm");
    }
    
    @RequestMapping(value = "personalsignup", method = RequestMethod.GET)
    protected ModelAndView linkToPersonalSignUp(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        CSession session = CSession.getInstance();
        if (!session.hasSession())
            return new UnconnectedModelAndView("personalsignup", new UnconnectedInfo());
        return new ModelAndView("redirect:/index.htm");
    }
    
    @RequestMapping(value = "advisor", method = RequestMethod.GET)
    protected ModelAndView linkToAdvisor(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        CSession session = CSession.getInstance();
        if (session.hasSession()) {
            ModelAndView view =  new ConnectedModelAndView("advisor", new ConnectedInfo(session.getFirstName(), session.getLastName()));
            AdvisorStub advisor = new AdvisorStub("Toto", "Tata", "jnpp", "05499878464");
            view.addObject("advisor", advisor);
            return view;
        }
        return new ModelAndView("redirect:/index.htm");
    }
}
