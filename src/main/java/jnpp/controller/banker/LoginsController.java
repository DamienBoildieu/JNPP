package jnpp.controller.banker;

import java.util.List;
import jnpp.service.dto.AbstractDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import jnpp.service.dto.clients.LoginDTO;
import jnpp.service.services.BankerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Controller
public class LoginsController {
    
    @Autowired
    BankerService bankerService;

    @RequestMapping(value = "banker/get-logins", method = RequestMethod.GET)
    public ResponseEntity<?> getLogins() {
        List<LoginDTO> logins = bankerService.getClientLogins();
        String json = AbstractDTO.toJson(logins);
        return new ResponseEntity(json, HttpStatus.OK);   
    }

}
