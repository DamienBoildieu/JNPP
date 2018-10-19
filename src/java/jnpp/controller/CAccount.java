package jnpp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jnpp.common.CSession;

import jnpp.common.ConnectedInfo;
import jnpp.common.ConnectedModelAndView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CAccount {
    /*@Autowired
    private ISUer resumeService;*/

    public CAccount() {
    }

    @RequestMapping(value = "resume", method = RequestMethod.GET)
    protected ModelAndView linktoResume(HttpServletRequest request, HttpServletResponse response) throws Exception {
        CSession session = CSession.getInstance();
        if (!session.hasSession())
            return new ModelAndView("redirect:/index.htm");
	//resumeService.resumeAccounts("");
        return new ConnectedModelAndView("resume", new ConnectedInfo(session.getFirstName(), session.getLastName()));
    }
}
