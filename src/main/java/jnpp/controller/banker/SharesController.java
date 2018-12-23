package jnpp.controller.banker;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import jnpp.service.dto.accounts.CurrencyDTO;
import jnpp.service.dto.accounts.ShareDTO;
import jnpp.service.exceptions.duplicates.DuplicateShareException;
import jnpp.service.services.AccountService;
import jnpp.service.services.BankerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Vue sur la création d'actions
 */
@Controller
public class SharesController {

    /**
     * Devie par défaut
     */
    private static final CurrencyDTO DEFAULT_CURRENCY = CurrencyDTO.EURO;
    /**
     * Service des comptes bancaire
     */
    @Autowired
    AccountService accountService;
    /**
     * Service banquier
     */
    @Autowired
    BankerService bankerService;

    /**
     * Vue du formulaire de création d'actions
     *
     * @return Le formulaire de création d'actions
     * @throws Exception Exception non controllees.
     */
    @RequestMapping(value = "banquier/actions", method = RequestMethod.GET)
    protected ModelAndView sharesGet()
            throws Exception {
        List<ShareDTO> shares = accountService.getShares();
        ModelAndView mv = new ModelAndView("banker/shares_board");
        mv.addObject("shares", shares);
        return mv;
    }

    /**
     * Requête de création d'une action
     *
     * @param request la requête
     * @return La vue des actions
     * @throws Exception Exception non controllees.
     */
    @RequestMapping(value = "banquier/actions", method = RequestMethod.POST)
    protected ModelAndView sharesPost(HttpServletRequest request)
            throws Exception {
        String name = request.getParameter("name");
        String value = request.getParameter("value");
        if (name != null && name.length() > 0 && value != null && value.length() > 0) {
            try {
                bankerService.addShare(name, Double.valueOf(value), DEFAULT_CURRENCY);
            } catch (IllegalArgumentException e) {
            } catch (DuplicateShareException e) {
            }
        }
        return new ModelAndView("redirect:/banquier/actions.htm");
    }

}
