package jnpp.controller.old;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jnpp.service.services.AccountService;
import jnpp.service.services.AdvisorService;
import jnpp.service.services.ClientService;
import jnpp.service.services.DebitAuthorizationService;
import jnpp.service.services.MovementService;
import jnpp.service.services.NotificationService;
import jnpp.service.services.PaymentMeanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;


/** Controller pour rajouter un autowired sur le service, sinon le service 
 * n'est pas instancie. */
@Controller
public class AutowiredRequieredController extends AbstractController {
    
    @Autowired
    AccountService accountService;
    @Autowired
    AdvisorService advisorService;
    @Autowired
    ClientService clientService; 
    @Autowired
    DebitAuthorizationService debitAuthorizationService;
    @Autowired
    MovementService movementService;
    @Autowired
    NotificationService notificationService;
    @Autowired
    PaymentMeanService paymentMeanService;
    
    public AutowiredRequieredController() {
    }
    
    protected ModelAndView handleRequestInternal(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
}
