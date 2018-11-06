package jnpp.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jnpp.controller.views.JNPPModelAndView;
import jnpp.controller.views.Translator;
import jnpp.controller.views.alerts.AlertEnum;
import jnpp.controller.views.alerts.AlertMessage;
import jnpp.controller.views.info.ViewInfo;
import jnpp.service.dto.accounts.AccountDTO;
import jnpp.service.dto.accounts.ShareAccountDTO;
import jnpp.service.dto.accounts.ShareDTO;
import jnpp.service.dto.accounts.ShareTitleDTO;
import jnpp.service.dto.clients.ClientDTO;
import jnpp.service.exceptions.entities.FakeClientException;
import jnpp.service.services.AccountService;
import jnpp.service.services.MovementService;
import jnpp.service.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MovementController {
    
    @Autowired
    AccountService accountService;
    @Autowired
    MovementService movementService;
    @Autowired
    NotificationService notifService;
    
    @RequestMapping(value = "movement", method = RequestMethod.GET)
    private ModelAndView movement(Model model, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rm)
            throws Exception {
        
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>)model.asMap().get("alerts");
        if (session==null)
            session = request.getSession(true);
        if (SessionController.getLanguage(session)!=Translator.Language.FR)
            SessionController.setLanguage(session,Translator.Language.FR);
        if (!SessionController.isConnected(session))
            return new ModelAndView("redirect:/index.htm");
        Boolean hasNotif = SessionController.getHasNotif(session);
        if (!hasNotif) {  
            try {
                hasNotif = notifService.receiveUnseenNotifications(SessionController.getClient(session).getLogin()).size()>0;
                SessionController.setHasNotif(session, hasNotif);
            } catch (FakeClientException invalidClient) {
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Il semble y avoir une erreur dans votre session"));
                } else {
                    alerts = new ArrayList<AlertMessage>(); 
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Il semble y avoir une erreur dans votre session"));
                }
            }
        }
        
        ClientDTO client = SessionController.getClient(session);
        
        List<String> ribMoneyAccount = new ArrayList<String>();
        List<String> ribShareAccount = new ArrayList<String>();
        
        List<AccountDTO> accounts = accountService.getAccounts(client.getLogin());
        for (AccountDTO account : accounts) {
            if (account.getType() == AccountDTO.Type.SHARE) {
                ribShareAccount.add(account.getRib());
            } else {
                ribMoneyAccount.add(account.getRib());
            }
        }
        
        List<String> sharesToPurchase = new ArrayList<String>();
        
        List<ShareDTO> shares = accountService.getShares();
        for (ShareDTO share : shares) {
            sharesToPurchase.add(share.getName());
        }
        
        List<String> sharesToSale = new ArrayList<String>();
        
        ShareAccountDTO shareAccount = accountService.getShareAccount(client.getLogin());
        if (shareAccount != null) {
            for (ShareTitleDTO shareTitle : shareAccount.getShareTitles()) {
                sharesToSale.add(shareTitle.getShare().getName());
            }
        }
        
        ModelAndView mv = new JNPPModelAndView("movements/movement", 
                ViewInfo.createInfo(session, alerts));
        
        mv.addObject("ribMoneyAccount", ribMoneyAccount);
        mv.addObject("ribShareAccount", ribShareAccount);
        mv.addObject("sharesToPurchase", sharesToPurchase);
        mv.addObject("sharesToSale", sharesToSale);
        
        return mv;
    }
    
    
    @RequestMapping(value = "transfert", method = RequestMethod.POST)
    private ModelAndView transfert(Model model, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rm)
            throws Exception {
        
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>)model.asMap().get("alerts");
        if (session==null)
            session = request.getSession(true);
        if (SessionController.getLanguage(session)!=Translator.Language.FR)
            SessionController.setLanguage(session,Translator.Language.FR);
        if (!SessionController.isConnected(session))
            return new ModelAndView("redirect:/index.htm");
            
        
        return new ModelAndView("redirect:/movement.htm");
    }
    
    
    @RequestMapping(value = "debit", method = RequestMethod.POST)
    private ModelAndView debit(Model model, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rm)
            throws Exception {
        
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>)model.asMap().get("alerts");
        if (session==null)
            session = request.getSession(true);
        if (SessionController.getLanguage(session)!=Translator.Language.FR)
            SessionController.setLanguage(session,Translator.Language.FR);
        if (!SessionController.isConnected(session))
            return new ModelAndView("redirect:/index.htm");
            
        
        return new ModelAndView("redirect:/movement.htm");
    }
    
    
    @RequestMapping(value = "purchase", method = RequestMethod.POST)
    private ModelAndView purchase(Model model, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rm)
            throws Exception {
        
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>)model.asMap().get("alerts");
        if (session==null)
            session = request.getSession(true);
        if (SessionController.getLanguage(session)!=Translator.Language.FR)
            SessionController.setLanguage(session,Translator.Language.FR);
        if (!SessionController.isConnected(session))
            return new ModelAndView("redirect:/index.htm");
            
        
        return new ModelAndView("redirect:/movement.htm");
    }
    
    
    @RequestMapping(value = "sale", method = RequestMethod.POST)
    private ModelAndView sale(Model model, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rm)
            throws Exception {
        
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>)model.asMap().get("alerts");
        if (session==null)
            session = request.getSession(true);
        if (SessionController.getLanguage(session)!=Translator.Language.FR)
            SessionController.setLanguage(session,Translator.Language.FR);
        if (!SessionController.isConnected(session))
            return new ModelAndView("redirect:/index.htm");
            
        
        return new ModelAndView("redirect:/movement.htm");
    }
    
}
