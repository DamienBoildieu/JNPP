/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jnpp.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jnpp.common.AlertMessage;
import jnpp.common.CSession;
import jnpp.common.ConnectedInfo;
import jnpp.common.ConnectedModelAndView;
import jnpp.common.UnconnectedInfo;
import jnpp.common.UnconnectedModelAndView;
import jnpp.stubs.AccountStub;
import jnpp.stubs.AdvisorStub;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    protected ModelAndView linkToIndex(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>)model.asMap().get("alerts");
        if (!CSession.hasSession(session))
            return new UnconnectedModelAndView("index", new UnconnectedInfo(alerts));
        return new ConnectedModelAndView("index", new ConnectedInfo(CSession.getFirstName(session), CSession.getLastName(session), alerts));
    }
    
    @RequestMapping(value = "connect", method = RequestMethod.GET)
    protected ModelAndView linkToConnect(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>)model.asMap().get("alerts");
        if (!CSession.hasSession(session))
            return new UnconnectedModelAndView("manageuser/connect", new UnconnectedInfo(alerts));
        return new ModelAndView("redirect:/index.htm");
    }
    
    @RequestMapping(value = "signup", method = RequestMethod.GET)
    protected ModelAndView linkToSignUp(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>)model.asMap().get("alerts");
        if (!CSession.hasSession(session))
            return new UnconnectedModelAndView("signup/signup", new UnconnectedInfo(alerts));
        return new ModelAndView("redirect:/index.htm");
    }
    
    @RequestMapping(value = "personalsignup", method = RequestMethod.GET)
    protected ModelAndView linkToPersonalSignUp(Model model, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>)model.asMap().get("alerts");
        if (!CSession.hasSession(session))
            return new UnconnectedModelAndView("signup/personalsignup", new UnconnectedInfo(alerts));
        return new ModelAndView("redirect:/index.htm");
    }
    
    @RequestMapping(value = "professionalsignup", method = RequestMethod.GET)
    protected ModelAndView linkToProfessionalSignUp(Model model, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>)model.asMap().get("alerts");
        if (!CSession.hasSession(session))
            return new UnconnectedModelAndView("signup/professionalsignup", new UnconnectedInfo(alerts));
        return new ModelAndView("redirect:/index.htm");
    }
    
    @RequestMapping(value = "advisor", method = RequestMethod.GET)
    protected ModelAndView linkToAdvisor(Model model, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>)model.asMap().get("alerts");
        if (CSession.hasSession(session)) {
            ModelAndView view =  new ConnectedModelAndView("manageuser/advisor", new ConnectedInfo(CSession.getFirstName(session), CSession.getLastName(session), alerts));
            AdvisorStub advisor = new AdvisorStub("Toto", "Tata", "jnpp", "05499878464");
            view.addObject("advisor", advisor);
            return view;
        }
        return new ModelAndView("redirect:/index.htm");
    }
    
    @RequestMapping(value = "home", method = RequestMethod.GET)
    protected ModelAndView linktoResume(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>)model.asMap().get("alerts");
        if (!CSession.hasSession(session))
            return new ModelAndView("redirect:/index.htm");
        ModelAndView view = new ConnectedModelAndView("manageuser/home", new ConnectedInfo(CSession.getFirstName(session), CSession.getLastName(session), alerts));
        return view;
    }
    
    @RequestMapping(value = "password", method = RequestMethod.GET)
    protected ModelAndView linktoPassword(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>)model.asMap().get("alerts");
        if (!CSession.hasSession(session))
            return new UnconnectedModelAndView("manageuser/password", new UnconnectedInfo(alerts));
        return new ModelAndView("redirect:/index.htm");
    }
}
