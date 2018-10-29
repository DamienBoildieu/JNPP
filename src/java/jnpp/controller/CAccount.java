package jnpp.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jnpp.controller.views.alerts.AlertMessage;
import jnpp.controller.views.info.ConnectedInfo;
import jnpp.controller.views.JNPPModelAndView;
import jnpp.controller.views.Translator;
import jnpp.controller.views.info.ViewInfo;
import jnpp.dao.entities.accounts.Account;
import jnpp.dao.entities.accounts.CurrentAccount;
import jnpp.dao.entities.accounts.SavingAccount;

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
        if (session==null)
            session = request.getSession(true);
        if (CSession.getLanguage(session)!=Translator.Language.FR)
            CSession.setLanguage(session,Translator.Language.FR);
        if (!CSession.isConnected(session))
            return new ModelAndView("redirect:/index.htm");
	//resumeService.resumeAccounts("");
        List<Account> listAc = new ArrayList<Account>();
        CurrentAccount current = new CurrentAccount();
        current.setMoney(-20.d);
        current.setRib("134824");
        listAc.add(current);
        SavingAccount saving = new SavingAccount();
        saving.setMoney(500.d);
        saving.setRib("5946513");
        listAc.add(saving);
        ModelAndView view = new JNPPModelAndView("accounts/resume", ViewInfo.createInfo(session, alerts));
        view.addObject("listAccounts", listAc);
        view.addObject("accountsMap", Translator.getInstance().translateAccounts(CSession.getLanguage(session)));
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
        if (session==null)
            session = request.getSession(true);
        if (CSession.getLanguage(session)!=Translator.Language.FR)
            CSession.setLanguage(session,Translator.Language.FR);
        if (!CSession.isConnected(session))
            return new ModelAndView("redirect:/index.htm");
	//resumeService.resumeAccounts("");
        return new JNPPModelAndView("accounts/account", ViewInfo.createInfo(session, alerts));
    }
    
}
