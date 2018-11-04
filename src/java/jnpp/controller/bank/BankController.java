package jnpp.controller.bank;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jnpp.service.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class BankController {
    
    @RequestMapping(value = "banquier", method = RequestMethod.GET)
    protected ModelAndView bankHomeGet(Model model, HttpServletRequest request, 
            HttpServletResponse response, RedirectAttributes rm) 
            throws Exception {
        ModelAndView mv = new ModelAndView("bank/banker_home");
        return mv;          
    }
    
}
