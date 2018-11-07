package jnpp.controller.banker;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import jnpp.service.dto.clients.LoginDTO;
import jnpp.service.exceptions.entities.FakeAdvisorException;
import jnpp.service.services.BankerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Contrôleur des clients d'un conseiller
 */
@Controller
public class AdvisorClientsController {
    /**
     * Le service banquier
     */
    @Autowired
    BankerService bankerService;
    /**
     * Vue de la liste des clients d'un conseiller
     * @param request la requête
     * @return La vue des clients d'un conseiller
     */
    @RequestMapping(value = "banquier/conseiller/clients", method = RequestMethod.GET)
    protected ModelAndView advisorClientsGet(HttpServletRequest request) {
        String firstname = request.getParameter("prenom");
        String lastname = request.getParameter("nom");
        if (firstname == null || firstname.length() == 0 || lastname == null  
                || lastname.length() == 0) 
            return new ModelAndView("redirect:/banquier/conseillers.htm"); 
        try {
            List<LoginDTO> clients = bankerService.getAdvisorLogins(firstname, lastname);
            ModelAndView mv = new ModelAndView("banker/advisor_clients_board");
            mv.addObject("advisor_firstname", firstname);
            mv.addObject("advisor_lastname", lastname);
            mv.addObject("clients", clients);
            return mv;  
        } catch (FakeAdvisorException e) {
        } catch (IllegalArgumentException e) {}
        return new ModelAndView("redirect:/banquier/conseillers.htm");       
    }
    
}
