package jnpp.controller.banker;

import java.util.List;
import jnpp.service.dto.accounts.AccountDTO;
import jnpp.service.services.BankerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Contrôleur des comptes de la banque
 */
@Controller
public class AccountsController {

    /**
     * Le service des banquiers
     */
    @Autowired
    BankerService bankerService;

    /**
     * Vue sur les comptes ouvert dans la banque
     *
     * @return La vue des comptes
     */
    @RequestMapping(value = "banquier/comptes", method = RequestMethod.GET)
    protected ModelAndView accountsGet() {
        List<AccountDTO> accounts = bankerService.getAccounts();
        ModelAndView mv = new ModelAndView("banker/accounts_board");
        mv.addObject("accounts", accounts);
        return mv;
    }

}
