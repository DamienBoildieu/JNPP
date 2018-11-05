package jnpp.controller.banker;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MovementsController {
    
    @RequestMapping(value = "banquier/transactions", method = RequestMethod.GET)
    protected ModelAndView movementsGet(Model model, HttpServletRequest request, 
            HttpServletResponse response, RedirectAttributes rm) {
        ModelAndView mv = new ModelAndView("banker/movements_board");
        return mv;         
    }
        
}
