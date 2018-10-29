package jnpp.controller.old;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jnpp.service.IClientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;


/** Controller pour rajouter un autowired sur le service, sinon le service 
 * n'est pas instancie. */
@Controller
public class AutowiredRequieredController extends AbstractController {
    
    @Autowired
    IClientService clientService; 
    
    public AutowiredRequieredController() {
    }
    
    protected ModelAndView handleRequestInternal(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
}
