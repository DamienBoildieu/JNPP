package jnpp.controller.banker;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import jnpp.service.dto.AbstractDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jnpp.service.dto.clients.LoginDTO;
import jnpp.service.services.BankerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Contrôleur de la vue des identifiants des clients
 */
@Controller
public class LoginsController {

    /**
     * Service banquier
     */
    @Autowired
    BankerService bankerService;

    /**
     * Vue des identifiants des clients de la banque
     *
     * @return la vue des identifiants des clients
     */
    @RequestMapping(value = "banker/logins", method = RequestMethod.GET)
    protected ResponseEntity<?> get() throws IOException {
        List<LoginDTO> logins = bankerService.getClientLogins();
        String json = AbstractDTO.toJson(logins);
        return new ResponseEntity(json, HttpStatus.OK);   
    }

}
