package jnpp.controller.bank;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jnpp.service.dto.accounts.CurrencyDTO;
import jnpp.service.dto.accounts.ShareDTO;
import jnpp.service.exceptions.duplicates.DuplicateShareException;
import jnpp.service.services.AccountService;
import jnpp.service.services.banker.BankerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SharesController {
    
    @Autowired
    AccountService accountService;
    
    @Autowired
    BankerService bankerService;
    
    @RequestMapping(value = "actions", method = RequestMethod.GET)
    protected ModelAndView sharesBoardGet(Model model, HttpServletRequest request, 
            HttpServletResponse response, RedirectAttributes rm) 
            throws Exception {
        List<ShareDTO> shares = accountService.getShares();
        ModelAndView mv = new ModelAndView("bank/shares_board");
        mv.addObject("shares", shares);
        return mv;       
    }
    
    @RequestMapping(value = "actions", method = RequestMethod.POST)
    protected ModelAndView sharesBoardPost(Model model, HttpServletRequest request,
            HttpServletResponse response, RedirectAttributes rm) 
            throws Exception {
        String name = request.getParameter("name");
        String value =request.getParameter("value");
        if (name != null && value != null)
            try {
                bankerService.addShare(name,  Double.valueOf(value), CurrencyDTO.EURO);
            } catch (IllegalArgumentException e) {
            } catch (DuplicateShareException e) {}
        return new ModelAndView("redirect:/actions.htm");
    }
    
}
