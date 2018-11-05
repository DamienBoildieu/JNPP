package jnpp.controller.banker;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jnpp.service.dto.clients.LoginDTO;
import jnpp.service.services.BankerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginsController {
    
    @Autowired
    BankerService bankerService;
    
    @RequestMapping(value = "banquier/identifiants", method = RequestMethod.GET)
    protected ModelAndView loginsGet(Model model, HttpServletRequest request, 
            HttpServletResponse response, RedirectAttributes rm) {
        List<LoginDTO> logins = bankerService.getClientLogins();
        ModelAndView mv = new ModelAndView("banker/logins_board");
        mv.addObject("logins", logins);
        return mv;            
    }
    
}
