package jnpp.controller.banker;

import java.io.IOException;
import java.util.List;
import jnpp.service.dto.AbstractDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import jnpp.service.dto.accounts.SavingBookDTO;
import jnpp.service.exceptions.duplicates.DuplicateSavingbookException;
import jnpp.service.services.AccountService;
import jnpp.service.services.BankerService;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class SavingbookController {

    @Autowired
    AccountService accountService;
    @Autowired
    BankerService bankerService;
   
    @RequestMapping(value = "banker/get-savingbooks", method = RequestMethod.GET)
    public ResponseEntity<?> getSavingbooks() {
        List<SavingBookDTO> savingbooks = accountService.getSavingBooks();
        String json = AbstractDTO.toJson(savingbooks);
        return new ResponseEntity(json, HttpStatus.OK);   
    }

    @RequestMapping(value = "banker/add-savingbook", method = RequestMethod.POST)
    public ResponseEntity<?> addSavingbook(@RequestBody String string) {
        try {
            JsonNode data = (new ObjectMapper()).readTree(string);
            String name = data.get("name").asText();
            Double moneyRate = data.get("moneyRate").asDouble();
            Double timeRate = data.get("timeRate").asDouble();
            SavingBookDTO book = bankerService.addSavingbook(name, moneyRate,
                    timeRate);
            String json = book.toJson();
            return new ResponseEntity(json, HttpStatus.OK);
        } catch (IOException e) {}
        catch (DuplicateSavingbookException e) {}
        return new ResponseEntity("", HttpStatus.BAD_REQUEST);
    }

}
