package jnpp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import jnpp.service.ISUser;

@Controller
public class CSignup {
    
    @Autowired
    ISUser service;
    
    // lA method signup est appelee quand action signup.htm en POST
    @RequestMapping(value = "signup", method = RequestMethod.POST)
    public ModelAndView signup(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        String login = request.getParameter("login");
        String password = request.getParameter("password");
        
        if (login != null && password != null && 
            service.signUp(login, password)) {
            return new ModelAndView("redirect:/index.htm");
            //return new ModelAndView("index");
        } else {
            return new ModelAndView("signup");
        }
    }
    
    @RequestMapping(value = "signin", method = RequestMethod.POST)
    public ModelAndView signin(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        
        if (login != null && password != null && 
            service.signIn(login, password)) {
            return new ModelAndView("index");
        } else {
            return new ModelAndView("signup");
        }
    }
    
    // lA method linktoSignup est appelee quand action signup.htm en GET
    @RequestMapping(value = "signup", method = RequestMethod.GET)
    public ModelAndView linktoSignup(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return new ModelAndView("signup");
    }
    
}
