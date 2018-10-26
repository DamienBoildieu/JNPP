package jnpp.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jnpp.common.AlertMessage;
import jnpp.common.CSession;
import jnpp.stubs.AccountStub;
import jnpp.common.ConnectedInfo;
import jnpp.common.ConnectedModelAndView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    protected ModelAndView linktoResume(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<AlertMessage> alerts = (List<AlertMessage>)model.asMap().get("alerts");
        HttpSession session = request.getSession();
        if (!CSession.hasSession(session))
            return new ModelAndView("redirect:/index.htm");
	//resumeService.resumeAccounts("");
        List<AccountStub> listAc = new ArrayList<AccountStub>();
        listAc.add(new AccountStub("56132", "compte courant", -20));
        listAc.add(new AccountStub("5946513", "Livret A", 500));
        ModelAndView view = new ConnectedModelAndView("accounts/resume", new ConnectedInfo(CSession.getFirstName(session), CSession.getLastName(session), alerts));
        view.addObject("listAccounts", listAc);
        return view;
    }
    
    @RequestMapping(value = "account", method = RequestMethod.GET)
    protected ModelAndView linktoAccount(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>)model.asMap().get("alerts");
        if (!CSession.hasSession(session))
            return new ModelAndView("redirect:/index.htm");
	//resumeService.resumeAccounts("");
        return new ConnectedModelAndView("accounts/account", new ConnectedInfo(CSession.getFirstName(session), CSession.getLastName(session), alerts));
    }
    
}
