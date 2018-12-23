package jnpp.controller.banker;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import jnpp.service.dto.paymentmeans.PaymentMeanDTO;
import jnpp.service.exceptions.entities.FakePaymentMeanException;
import jnpp.service.services.BankerService;

/**
 * Contrôleur des commandes
 */
@Controller
public class PaymentMeansController {

    /**
     * Service banquer
     */
    @Autowired
    BankerService bankerService;

    /**
     * Vue des commandes
     *
     * @return la vue des commandes
     */
    @RequestMapping(value = "banquier/commandes", method = RequestMethod.GET)
    protected ModelAndView paymentMeansGet() {
        List<PaymentMeanDTO> paymentmeans = bankerService.getPaymentMeans();
        ModelAndView mv = new ModelAndView("banker/paymentmeans_board");
        mv.addObject("paymentmeans", paymentmeans);
        return mv;
    }

    /**
     * Changement de statut d'une commande
     *
     * @param request la requête
     * @return La vue des commandes
     */
    @RequestMapping(value = "banquier/commandes", method = RequestMethod.POST)
    protected ModelAndView paymentMeansPost(HttpServletRequest request) {
        String id = request.getParameter("id");
        if (id != null && id.length() > 0) {
            try {
                bankerService.upgradePaymentMean(id);
            } catch (FakePaymentMeanException e) {
            }
        }
        return new ModelAndView("redirect:/banquier/commandes.htm");
    }

}
