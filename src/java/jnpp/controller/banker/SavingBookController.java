package jnpp.controller.banker;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import jnpp.service.dto.accounts.SavingBookDTO;
import jnpp.service.exceptions.duplicates.DuplicateSavingbookException;
import jnpp.service.services.AccountService;
import jnpp.service.services.BankerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Contrôleur de la vue de création de livrets
 */
@Controller
public class SavingBookController {
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
     * Vue sur le formulaire de création de livrets
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "banquier/livrets", method = RequestMethod.GET)
    protected ModelAndView savingBookGet() 
            throws Exception {
        List<SavingBookDTO> savingbooks = accountService.getSavingBooks();
        ModelAndView mv = new ModelAndView("banker/savingbooks_board");
        mv.addObject("savingbooks", savingbooks);
        return mv;       
    }
    /**
     * Création d'un livret
     * @param request la requête
     * @return Le formulaire de créations de livrets
     * @throws Exception 
     */
    @RequestMapping(value = "banquier/livrets", method = RequestMethod.POST)
    protected ModelAndView savingBookPost(HttpServletRequest request) 
            throws Exception {
        String name = request.getParameter("name");
        String moneyRate = request.getParameter("money_rate");
        String timeRate = request.getParameter("time_rate");
        if (name != null && name.length() > 0 && moneyRate != null && moneyRate.length() > 0 
                && timeRate != null && timeRate.length() > 0)
            try {
                bankerService.addSavingbook(name, Double.valueOf(moneyRate), Double.valueOf(timeRate));
            } catch (IllegalArgumentException e) {
            } catch (DuplicateSavingbookException e) {}
        return new ModelAndView("redirect:/banquier/livrets.htm");
    }
    
}
