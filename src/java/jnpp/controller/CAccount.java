package jnpp.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jnpp.common.AlertMessage;
import jnpp.common.CSession;
import jnpp.common.ConnectedInfo;
import jnpp.common.JNPPModelAndView;
import jnpp.stubs.AccountStub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Classe de contrôleur des requêtes sur des comptes
 */
@Controller
public class CAccount {
    /*@Autowired
    private ISUer resumeService;*/

    /**
     * Requête sur la vue de la liste des comptes
     * @param model le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @param response la réponse
     * @return Une vue sur la liste des comptes de l'utilisateur si il est connecté, redirection vers l'index sinon
     * @throws Exception 
     */
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
        ModelAndView view = new JNPPModelAndView("accounts/resume", new ConnectedInfo(CSession.getFirstName(session), CSession.getLastName(session), alerts, false));
        view.addObject("listAccounts", listAc);
        return view;
    }
    /**
     * Requête sur la vue d'un compte
     * @param model le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @param response la réponse
     * @return Une vue sur un compte de l'utilisateur si il est connecté, redirection vers l'index sinon
     * @throws Exception 
     */
    @RequestMapping(value = "account", method = RequestMethod.GET)
    protected ModelAndView linktoAccount(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>)model.asMap().get("alerts");
        if (!CSession.hasSession(session))
            return new ModelAndView("redirect:/index.htm");
	//resumeService.resumeAccounts("");
        return new JNPPModelAndView("accounts/account", new ConnectedInfo(CSession.getFirstName(session), CSession.getLastName(session), alerts, false));
    }
    
}
