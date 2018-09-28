/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import common.ConnectedInfo;
import common.ConnectedModelAndView;
import common.UnconnectedInfo;
import common.UnconnectedModelAndView;
import dao.UserEntity;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import services.Connect;

/**
 *
 * @author damien
 */
@Controller
public class ConnectController {
  
  @Autowired
  private Connect connectService;
  
  public ConnectController() {
  }
  
  @RequestMapping(value="connect", method = RequestMethod.POST)
  protected ModelAndView connect(
          HttpServletRequest request,
          HttpServletResponse response) throws Exception {
    HttpSession session=request.getSession(false);
    if (session == null) {
      String id = request.getParameter("id");
      String password = request.getParameter("password");
      UserEntity user =  connectService.connect(id, password);
      session = request.getSession(true);
      session.setAttribute("firstName", user.getFirstName());
      session.setAttribute("lastName", user.getLastName());
      ModelAndView resume = new ConnectedModelAndView("resume", new ConnectedInfo(user.getFirstName(), user.getLastName()));
      resume.addObject("firstName", user.getFirstName());
      return resume;
    }
    return new ConnectedModelAndView("index", new ConnectedInfo((String)session.getAttribute("firstName"), (String)session.getAttribute("lastName")));
  }
  
  @RequestMapping(value="connect", method = RequestMethod.GET)
  protected ModelAndView linkToConnect(
          HttpServletRequest request,
          HttpServletResponse response) throws Exception {
    HttpSession session=request.getSession(false);
    if (session == null) {
      return new UnconnectedModelAndView("connect", new UnconnectedInfo());
    }
    return new ConnectedModelAndView("index", new ConnectedInfo((String)session.getAttribute("firstName"), (String)session.getAttribute("lastName")));
  }
}
