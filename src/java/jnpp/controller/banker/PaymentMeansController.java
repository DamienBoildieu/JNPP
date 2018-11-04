package jnpp.controller.banker;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jnpp.service.dto.paymentmeans.BankCardDTO;
import jnpp.service.dto.paymentmeans.CheckbookDTO;
import jnpp.service.dto.paymentmeans.PaymentMeanDTO;
import jnpp.service.exceptions.entities.FakePaymentMeanException;
import jnpp.service.services.BankerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PaymentMeansController {

    @Autowired
    BankerService bankerService;
    
    @RequestMapping(value = "commandes", method = RequestMethod.GET)
    protected ModelAndView paymentMeansGet(Model model, HttpServletRequest request, 
            HttpServletResponse response, RedirectAttributes rm) {
        List<PaymentMeanDTO> paymentmeans = bankerService.getPaymentMeans();
        ModelAndView mv = new ModelAndView("banker/paymentmeans_board");
        mv.addObject("paymentmeans", paymentmeans);
        return mv;
    }
     
    @RequestMapping(value = "commandes", method = RequestMethod.POST)
    protected ModelAndView paymentMeansPost(Model model, HttpServletRequest request, 
            HttpServletResponse response, RedirectAttributes rm) {
        String id = request.getParameter("id");
        if (id != null && id.length() > 0) {
            try {
                bankerService.upgradePaymentMean(id);
            } catch (FakePaymentMeanException e) {
            }            
        }
        return new ModelAndView("redirect:/commandes.htm");
    }  
    
}
