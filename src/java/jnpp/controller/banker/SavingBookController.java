package jnpp.controller.banker;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jnpp.service.dto.accounts.SavingBookDTO;
import jnpp.service.exceptions.duplicates.DuplicateSavingbookException;
import jnpp.service.services.AccountService;
import jnpp.service.services.BankerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SavingBookController {
        
    @Autowired
    AccountService accountService;
    
    @Autowired
    BankerService bankerService;
    
    @RequestMapping(value = "banquier/livrets", method = RequestMethod.GET)
    protected ModelAndView savingBookGet(Model model, HttpServletRequest request, 
            HttpServletResponse response, RedirectAttributes rm) 
            throws Exception {
        List<SavingBookDTO> savingbooks = accountService.getSavingBooks();
        ModelAndView mv = new ModelAndView("banker/savingbooks_board");
        mv.addObject("savingbooks", savingbooks);
        return mv;       
    }
    
    @RequestMapping(value = "banquier/livrets", method = RequestMethod.POST)
    protected ModelAndView savingBookPost(Model model, HttpServletRequest request,
            HttpServletResponse response, RedirectAttributes rm) 
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
