package jnpp.controller.banker;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jnpp.service.dto.accounts.ShareDTO;
import jnpp.service.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MovementsController {
    
    @Autowired
    AccountService accountService;
    
    @RequestMapping(value = "banquier/transactions", method = RequestMethod.GET)
    protected ModelAndView movementsGet(Model model, HttpServletRequest request, 
            HttpServletResponse response, RedirectAttributes rm) {
        List<ShareDTO> shares = accountService.getShares();
        ModelAndView mv = new ModelAndView("banker/movements_board");
        mv.addObject("shares", shares);
        return mv;         
    }
        
}
