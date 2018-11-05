package jnpp.controller.banker;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jnpp.service.dto.accounts.AccountDTO;
import jnpp.service.services.BankerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AccountsController {
    
    @Autowired
    BankerService bankerService;
    
    @RequestMapping(value = "banquier/comptes", method = RequestMethod.GET)
    protected ModelAndView accountsGet(Model model, HttpServletRequest request, 
            HttpServletResponse response, RedirectAttributes rm) {
        List<AccountDTO> accounts = bankerService.getAccounts();
        ModelAndView mv = new ModelAndView("banker/accounts_board");
        mv.addObject("accounts", accounts);
        return mv;   
    }
    
}
