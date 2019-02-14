package jnpp.controller.banker;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import jnpp.service.dto.AbstractDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import jnpp.service.dto.accounts.CurrencyDTO;
import jnpp.service.dto.accounts.ShareDTO;
import jnpp.service.dto.clients.ClientDTO;
import jnpp.service.exceptions.duplicates.DuplicateShareException;
import jnpp.service.services.AccountService;
import jnpp.service.services.BankerService;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

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
    @RequestMapping(value = "banker/shares", method = RequestMethod.GET)
    protected ResponseEntity<?> sharesGet(HttpServletRequest request) 
            throws IOException {
        List<ShareDTO> shares = accountService.getShares();
        String json = AbstractDTO.toJson(shares);
        return new ResponseEntity(json, HttpStatus.OK);   
    }
    
    /**
     * Requête de création d'une action
     *
     * @param request la requête
     * @return La vue des actions
     * @throws Exception Exception non controllees.
     */
    @RequestMapping(value = "banker/shares", method = RequestMethod.POST)
    protected ResponseEntity<?> sharesPost(@RequestBody String string, 
            HttpServletRequest request) throws Exception {
        JsonNode data = (new ObjectMapper()).readTree(string);
        String name = data.get("name").asText();
        Double value = data.get("value").asDouble();
        try {
            ShareDTO share = bankerService.addShare(name, value, 
                    DEFAULT_CURRENCY);
            String json = share.toJson();
            return new ResponseEntity(json, HttpStatus.OK);
        } catch (Exception e) {}
        return new ResponseEntity("Bad arguments", HttpStatus.BAD_REQUEST);
    }

}
