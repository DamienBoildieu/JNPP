/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;


import common.UnconnectedInfo;
import common.UnconnectedModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    return new UnconnectedModelAndView("signup", new UnconnectedInfo());
  }
}
