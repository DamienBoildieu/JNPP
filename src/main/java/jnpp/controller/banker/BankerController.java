package jnpp.controller.banker;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Contrôleur de l'accueil de la vue banquier
 */
@Controller
public class BankerController {

    /**
     * Vue accueil des banquiers
     *
     * @return la vue accueil des banquiers
     * @throws Exception Exception non controllees.
     */
    @RequestMapping(value = "banquier", method = RequestMethod.GET)
    protected ModelAndView bankerGet() throws Exception {
        ModelAndView mv = new ModelAndView("banker/banker_home");
        return mv;
    }

}
