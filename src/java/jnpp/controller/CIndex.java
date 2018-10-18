/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jnpp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jnpp.common.ConnectedInfo;
import jnpp.common.ConnectedModelAndView;
import jnpp.common.UnconnectedInfo;
import jnpp.common.UnconnectedModelAndView;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author damien
 */
@Controller
public class CIndex {

    @RequestMapping(value = "index", method = RequestMethod.GET)
    protected ModelAndView linkToIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
	HttpSession session = request.getSession(false);
	if ((session == null) || (session.getAttribute("firstName") == null)) // evaluation paresseuse
	    return new UnconnectedModelAndView("index", new UnconnectedInfo());
	return new ConnectedModelAndView("index", new ConnectedInfo((String) session.getAttribute("firstName"),
		(String) session.getAttribute("lastName")));
    }
}
