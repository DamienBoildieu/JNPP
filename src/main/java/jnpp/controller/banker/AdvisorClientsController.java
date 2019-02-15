package jnpp.controller.banker;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import jnpp.service.dto.AbstractDTO;
import jnpp.service.dto.advisor.AdvisorDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import jnpp.service.dto.clients.LoginDTO;
import jnpp.service.exceptions.entities.FakeAdvisorException;
import jnpp.service.services.BankerService;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Contrôleur des clients d'un conseiller
 */
@Controller
public class AdvisorClientsController {

    /**
     * Le service banquier
     */
    @Autowired
    BankerService bankerService;

    /**
     * Vue de la liste des clients d'un conseiller
     *
     * @param request la requête
     * @return La vue des clients d'un conseiller
     */
    @RequestMapping(value = "banker/advisor/clients", method = RequestMethod.GET)
    protected ResponseEntity<?> get(
            @RequestParam(value = "firstname") String firstname,
            @RequestParam(value = "lastname") String lastname)
            throws IOException {
        try {
            List<LoginDTO> clients = bankerService.getAdvisorLogins(firstname,
                    lastname);
            String json = AbstractDTO.toJson(clients);
            return new ResponseEntity(json, HttpStatus.OK);
        } catch (Exception e) {}
        return new ResponseEntity("Bad arguments", HttpStatus.BAD_REQUEST);
    }

}
