/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aaw.jnpp.controllers;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.aaw.jnpp.common.ConnectedInfo;
import org.aaw.jnpp.common.ConnectedModelAndView;
import org.aaw.jnpp.common.UnconnectedInfo;
import org.aaw.jnpp.common.UnconnectedModelAndView;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author damien
 */
@Controller
public class SignUpController {
  public SignUpController() {
  }
  
  @RequestMapping(value="signup", method = RequestMethod.GET)
  protected ModelAndView linkToConnect(
          HttpServletRequest request,
          HttpServletResponse response) throws Exception {
    HttpSession session=request.getSession(false);
    if (session == null)
      return new UnconnectedModelAndView("signup", new UnconnectedInfo());
    else if (session.getAttribute("firstName")==null)
      return new UnconnectedModelAndView("signup", new UnconnectedInfo());
    return new ConnectedModelAndView("index", new ConnectedInfo((String)session.getAttribute("firstName"), (String)session.getAttribute("lastName")));
  }
}
