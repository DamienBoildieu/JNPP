package jnpp.controller.banker;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import jnpp.service.dto.accounts.CurrencyDTO;
import jnpp.service.dto.accounts.SavingBookDTO;
import jnpp.service.dto.accounts.ShareDTO;
import jnpp.service.exceptions.accounts.NoCurrentAccountException;
import jnpp.service.exceptions.duplicates.DuplicateSavingbookException;
import jnpp.service.exceptions.entities.FakeAccountException;
import jnpp.service.exceptions.entities.FakeShareException;
import jnpp.service.exceptions.entities.FakeShareTitleException;
import jnpp.service.exceptions.movements.AccountTypeException;
import jnpp.service.exceptions.movements.AmountException;
import jnpp.service.exceptions.movements.DebitAuthorizationException;
import jnpp.service.exceptions.movements.OverdraftException;
import jnpp.service.services.AccountService;
import jnpp.service.services.BankerService;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class OperationsController {

    private static final CurrencyDTO DEFAULT_CURRENCY = CurrencyDTO.EURO;

    @Autowired
    AccountService accountService;
    @Autowired
    BankerService bankerService;

    @RequestMapping(value = "banker/deposit", method = RequestMethod.POST)
    public ResponseEntity<?> deposit(@RequestBody String string) {
        try {
            JsonNode data = (new ObjectMapper()).readTree(string);
            data = data.get("deposit");
            String rib = data.get("rib").asText();
            Double amount = data.get("amount").asDouble();
            String label = data.get("label").asText();
            bankerService.deposit(rib, amount, DEFAULT_CURRENCY, label);
            return new ResponseEntity("", HttpStatus.OK);
        } catch (NullPointerException e) {} 
        catch (FakeAccountException e) {} 
        catch (AccountTypeException e) {} 
        catch (IOException e) {}
        return new ResponseEntity("", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "banker/transfert", method = RequestMethod.POST)
    public ResponseEntity<?> transfert(@RequestBody String string) {
        try {
            JsonNode data = (new ObjectMapper()).readTree(string);
            data = data.get("transfert");
            String ribFrom = data.get("ribFrom").asText();
            String ribTo = data.get("ribTo").asText();
            Double amount = data.get("amount").asDouble();
            String label = data.get("label").asText();
            bankerService.transfert(ribFrom, ribTo, amount, DEFAULT_CURRENCY, 
                    label);
            return new ResponseEntity("", HttpStatus.OK);
        } catch (NullPointerException e) {} 
        catch (FakeAccountException e) {} 
        catch (AccountTypeException e) {} 
        catch (IOException e) {} 
        catch (OverdraftException e) {}
        return new ResponseEntity("", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "banker/debit", method = RequestMethod.POST)
    public ResponseEntity<?> debit(@RequestBody String string) {
        try {
            JsonNode data = (new ObjectMapper()).readTree(string);
            data = data.get("debit");
            String ribFrom = data.get("ribFrom").asText();
            String ribTo = data.get("ribTo").asText();
            Double amount = data.get("amount").asDouble();
            String label = data.get("label").asText();
            bankerService.debit(ribFrom, ribTo, amount, DEFAULT_CURRENCY, 
                    label);
            return new ResponseEntity("", HttpStatus.OK);
        } catch (NullPointerException e) {} 
        catch (FakeAccountException e) {} 
        catch (AccountTypeException e) {} 
        catch (IOException e) {} 
        catch (OverdraftException e) {} 
        catch (DebitAuthorizationException e) {}
        return new ResponseEntity("", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "banker/purchase", method = RequestMethod.POST)
    public ResponseEntity<?> purchase(@RequestBody String string) {
        try {
            JsonNode data = (new ObjectMapper()).readTree(string);
            data = data.get("purchase");
            String rib = data.get("rib").asText();
            String share = data.get("share").asText();
            Integer amount = data.get("amount").asInt();
            String label = data.get("label").asText();
            bankerService.purchase(rib, share, amount, label);
            return new ResponseEntity("", HttpStatus.OK);
        } catch (NullPointerException e) {} 
        catch (FakeAccountException e) {} 
        catch (AccountTypeException e) {} 
        catch (IOException e) {} 
        catch (FakeShareException e) {} 
        catch (NoCurrentAccountException e) {}
        return new ResponseEntity("", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "banker/sale", method = RequestMethod.POST)
    public ResponseEntity<?> sale(@RequestBody String string) {
        try {
            JsonNode data = (new ObjectMapper()).readTree(string);
            data = data.get("sale");
            String rib = data.get("rib").asText();
            String share = data.get("share").asText();
            Integer amount = data.get("amount").asInt();
            String label = data.get("label").asText();
            bankerService.sale(rib, share, amount, label);
            return new ResponseEntity("", HttpStatus.OK);
        } catch (NullPointerException e) {} 
        catch (FakeAccountException e) {} 
        catch (AccountTypeException e) {} 
        catch (IOException e) {} 
        catch (FakeShareException e) {} 
        catch (NoCurrentAccountException e) {} 
        catch (AmountException e) {} 
        catch (FakeShareTitleException e) {}
        return new ResponseEntity("", HttpStatus.BAD_REQUEST);
    }
    
}
