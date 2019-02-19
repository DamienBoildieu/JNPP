package jnpp.controller.client;

import java.io.IOException;
import java.util.List;
import jnpp.service.dto.AbstractDTO;
import jnpp.service.dto.paymentmeans.BankCardDTO;
import jnpp.service.dto.paymentmeans.CheckbookDTO;
import jnpp.service.exceptions.entities.FakeAccountException;
import jnpp.service.exceptions.entities.FakeClientException;
import jnpp.service.exceptions.movements.AccountTypeException;
import jnpp.service.exceptions.owners.AccountOwnerException;
import jnpp.service.services.PaymentMeanService;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author damien
 */
@Controller
public class PaymentMeanController {
    @Autowired
    private PaymentMeanService paymentMeanService;
      
    @RequestMapping(value = "clientCards", method = RequestMethod.GET)
    public ResponseEntity<?> getCards(@RequestHeader("authorization") String autho) {
        String login = SessionController.decodeLogin(autho);
        try {
            List<BankCardDTO> cards = paymentMeanService.getBankCards(login);
            return new ResponseEntity(AbstractDTO.toJson(cards), HttpStatus.OK);
        } catch (FakeClientException ex) {
            return new ResponseEntity("Il semble y avoir une erreur dans votre session", 
                HttpStatus.CONFLICT);
        }
    }
    
    @RequestMapping(value = "cards/{accountRib}", method = RequestMethod.GET)
    public ResponseEntity<?> getCardsByRib(@RequestHeader("authorization") String autho,
            @PathVariable String accountRib) {
        String login = SessionController.decodeLogin(autho);
        try {
            List<BankCardDTO> cards = paymentMeanService.getBankCards(login, accountRib);
            return new ResponseEntity(AbstractDTO.toJson(cards), HttpStatus.OK);
        } catch (FakeClientException ex) {
            return new ResponseEntity("Il semble y avoir une erreur dans votre session", 
                HttpStatus.CONFLICT);
        } catch (AccountOwnerException ex) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("Content-Type", "application/text; charset=UTF-8");
            return new ResponseEntity("Vous n'êtes pas le propriétaire de ce compte", responseHeaders, 
                HttpStatus.BAD_REQUEST);
        }
    }
    
    @RequestMapping(value = "clientCheckBooks", method = RequestMethod.GET)
    public ResponseEntity<?> getCheckBooks(@RequestHeader("authorization") String autho) {
        String login = SessionController.decodeLogin(autho);
        try {
            List<CheckbookDTO> cards = paymentMeanService.getCheckBooks(login);
            return new ResponseEntity(AbstractDTO.toJson(cards), HttpStatus.OK);
        } catch (FakeClientException ex) {
            return new ResponseEntity("Il semble y avoir une erreur dans votre session", 
                HttpStatus.CONFLICT);
        }
    }
    
    @RequestMapping(value = "checkBooks/{accountRib}", method = RequestMethod.GET)
    public ResponseEntity<?> getCheckBooks(@RequestHeader("authorization") String autho,
            @PathVariable String accountRib) {
        String login = SessionController.decodeLogin(autho);
        try {
            List<CheckbookDTO> cards = paymentMeanService.getCheckBooks(login, accountRib);
            return new ResponseEntity(AbstractDTO.toJson(cards), HttpStatus.OK);
        } catch (FakeClientException ex) {
            return new ResponseEntity("Il semble y avoir une erreur dans votre session", 
                HttpStatus.CONFLICT);
        } catch (AccountOwnerException ex) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("Content-Type", "application/text; charset=UTF-8");
            return new ResponseEntity("Vous n'êtes pas le propriétaire de ce compte", responseHeaders, 
                HttpStatus.BAD_REQUEST);
        } catch (FakeAccountException ex) {
            return new ResponseEntity("Ce compte n'existe pas", HttpStatus.BAD_REQUEST);
        }
    }
    
    @RequestMapping(value = "commandCard", method = RequestMethod.POST)
    public ResponseEntity<?> commandCard(@RequestHeader("authorization") String autho,
        @RequestBody String body) {
        String login = SessionController.decodeLogin(autho);
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode data = mapper.readTree(body);
            String rib = data.get("rib").asText();
            return new ResponseEntity(paymentMeanService.commandBankCard(login, rib).toJson(),
                HttpStatus.CREATED);
        } catch (FakeClientException ex) {
            return new ResponseEntity("Il semble y avoir une erreur dans votre session", 
                HttpStatus.CONFLICT);
        } catch (AccountTypeException ex) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("Content-Type", "application/text; charset=UTF-8");
            return new ResponseEntity("Ce compte ne peut pas être associé à une carte bancaire", responseHeaders, 
                HttpStatus.BAD_REQUEST);
        } catch (AccountOwnerException ex) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("Content-Type", "application/text; charset=UTF-8");
            return new ResponseEntity("Vous n'êtes pas le propriétaire de ce compte", responseHeaders, 
                HttpStatus.BAD_REQUEST);
        } catch (IOException ex) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("Content-Type", "application/text; charset=UTF-8");
            return new ResponseEntity("Une erreur est présente dans le formulaire", responseHeaders, 
                HttpStatus.BAD_REQUEST);
        }
    }
    
    @RequestMapping(value = "commandCheckBook", method = RequestMethod.POST)
    public ResponseEntity<?> commandCheckBook(@RequestHeader("authorization") String autho,
        @RequestBody String body) {
        String login = SessionController.decodeLogin(autho);
        ObjectMapper mapper = new ObjectMapper();
        try {   
            JsonNode data = mapper.readTree(body);
            String rib = data.get("rib").asText();
            return new ResponseEntity(paymentMeanService.commandCheckbook(login, rib).toJson(),
                HttpStatus.CREATED);
        } catch (FakeClientException ex) {
            return new ResponseEntity("Il semble y avoir une erreur dans votre session", 
                HttpStatus.CONFLICT);
        } catch (AccountTypeException ex) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("Content-Type", "application/text; charset=UTF-8");
            return new ResponseEntity("Ce compte ne peut pas être associé à une carte bancaire", responseHeaders, 
                HttpStatus.BAD_REQUEST);
        } catch (AccountOwnerException ex) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("Content-Type", "application/text; charset=UTF-8");
            return new ResponseEntity("Vous n'êtes pas le propriétaire de ce compte", responseHeaders, 
                HttpStatus.BAD_REQUEST);
        } catch (IOException ex) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("Content-Type", "application/text; charset=UTF-8");
            return new ResponseEntity("Une erreur est présente dans le formulaire", responseHeaders, 
                HttpStatus.BAD_REQUEST);
        }
    }
}
