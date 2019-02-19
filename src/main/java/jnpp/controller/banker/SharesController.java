package jnpp.controller.banker;

import java.io.IOException;
import java.util.List;
import jnpp.service.dto.AbstractDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import jnpp.service.dto.accounts.CurrencyDTO;
import jnpp.service.dto.accounts.ShareDTO;
import jnpp.service.exceptions.duplicates.DuplicateShareException;
import jnpp.service.services.AccountService;
import jnpp.service.services.BankerService;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class SharesController {

    private static final CurrencyDTO DEFAULT_CURRENCY = CurrencyDTO.EURO;

    @Autowired
    AccountService accountService;
    @Autowired
    BankerService bankerService;


    @RequestMapping(value = "banker/get-shares", method = RequestMethod.GET)
    public ResponseEntity<?> getShares() {
        List<ShareDTO> shares = accountService.getShares();
        String json = AbstractDTO.toJson(shares);
        return new ResponseEntity(json, HttpStatus.OK);   
    }
    
    @RequestMapping(value = "banker/add-share", method = RequestMethod.POST)
    public ResponseEntity<?> addShare(@RequestBody String string) {
        try {
            JsonNode data = (new ObjectMapper()).readTree(string);
            String name = data.get("name").asText();
            Double value = data.get("value").asDouble();
            ShareDTO share = bankerService.addShare(name, value, 
                    DEFAULT_CURRENCY);
            String json = share.toJson();
            return new ResponseEntity(json, HttpStatus.OK);
        } catch (IOException e) {} 
        catch (DuplicateShareException e) {}
        return new ResponseEntity("", HttpStatus.BAD_REQUEST);
    }

}
