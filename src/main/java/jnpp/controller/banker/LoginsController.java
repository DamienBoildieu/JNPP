package jnpp.controller.banker;

import java.util.List;
import jnpp.service.dto.clients.LoginDTO;
import jnpp.service.services.BankerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Contrôleur de la vue des identifiants des clients
 */
@Controller
public class LoginsController {

    /**
     * Service banquier
     */
    @Autowired
    BankerService bankerService;

    /**
     * Vue des identifiants des clients de la banque
     *
     * @return la vue des identifiants des clients
     */
    @RequestMapping(value = "banquier/identifiants", method = RequestMethod.GET)
    protected ModelAndView loginsGet() {
        List<LoginDTO> logins = bankerService.getClientLogins();
        ModelAndView mv = new ModelAndView("banker/logins_board");
        mv.addObject("logins", logins);
        return mv;
    }

}
