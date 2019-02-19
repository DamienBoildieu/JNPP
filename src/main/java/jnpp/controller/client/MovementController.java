package jnpp.controller.client;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jnpp.service.dto.accounts.CurrencyDTO;
import jnpp.service.exceptions.accounts.CurrencyException;
import jnpp.service.exceptions.accounts.NoCurrentAccountException;
import jnpp.service.exceptions.accounts.NoShareAccountException;
import jnpp.service.exceptions.entities.FakeAccountException;
import jnpp.service.exceptions.entities.FakeClientException;
import jnpp.service.exceptions.entities.FakeShareException;
import jnpp.service.exceptions.entities.FakeShareTitleException;
import jnpp.service.exceptions.movements.AccountTypeException;
import jnpp.service.exceptions.movements.AmountException;
import jnpp.service.exceptions.movements.DebitAuthorizationException;
import jnpp.service.exceptions.movements.OverdraftException;
import jnpp.service.exceptions.owners.AccountOwnerException;
import jnpp.service.services.MovementService;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * Le contrôleur des transactions
 */
@Controller
public class MovementController {

    /**
     * La monnaie par défaut
     */
    private static final CurrencyDTO DEFAULT_CURRENCY = CurrencyDTO.EURO;
    /**
     * Le service des transactions
     */
    @Autowired
    private MovementService movementService;
    
    @RequestMapping(value = "transfert", method = RequestMethod.POST)
    private ResponseEntity<?> transfert(@RequestHeader("authorization") String autho,
        @RequestBody String body) throws IOException {
        String login = SessionController.decodeLogin(autho);   
  
        ObjectMapper mapper = new ObjectMapper();
        JsonNode data = mapper.readTree(body);
        
        String ribFrom = data.get("ribFrom").asText();
        String ribTo = data.get("ribTo").asText();
        Double amount = data.get("amount").asDouble();
        String label = data.get("label").asText();

        try {
            movementService.transfertMoney(login, ribFrom, ribTo, amount, DEFAULT_CURRENCY, label);
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (FakeClientException ex) {
            return new ResponseEntity("Il semble y avoir une erreur dans votre session",
                HttpStatus.CONFLICT);
        } catch (FakeAccountException ex) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("Content-Type", "application/text; charset=UTF-8");
            return new ResponseEntity("Ce rib n'est associé à aucun compte",
                responseHeaders, HttpStatus.BAD_REQUEST);
        } catch (AccountOwnerException ex) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("Content-Type", "application/text; charset=UTF-8");
            return new ResponseEntity("Vous n'êtes pas le propriétaire du compte effectuant le virement",
                responseHeaders, HttpStatus.BAD_REQUEST);
        } catch (AccountTypeException ex) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("Content-Type", "application/text; charset=UTF-8");
            return new ResponseEntity("Les débits ne sont pas autorisés sur ce compte",
                responseHeaders, HttpStatus.BAD_REQUEST);
        } catch (CurrencyException ex) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("Content-Type", "application/text; charset=UTF-8");
            return new ResponseEntity("Ce compte utilise une devise différente du votre",
                responseHeaders, HttpStatus.BAD_REQUEST);
        } catch (OverdraftException ex) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("Content-Type", "application/text; charset=UTF-8");
            return new ResponseEntity("Les dépassements ne sont pas autorisés sur ce compte",
                responseHeaders, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "debit", method = RequestMethod.POST)
    private ResponseEntity<?> debit(@RequestHeader("authorization") String autho,
        @RequestBody String body) throws IOException {
        String login = SessionController.decodeLogin(autho);   
  
        ObjectMapper mapper = new ObjectMapper();
        JsonNode data = mapper.readTree(body);
        
        String ribFrom = data.get("ribFrom").asText();
        String ribTo = data.get("ribTo").asText();
        Double amount = data.get("amount").asDouble();
        String label = data.get("label").asText();

        try {
            movementService.debitMoney(login, ribFrom, ribTo, amount, DEFAULT_CURRENCY, label);
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (FakeClientException ex) {
            return new ResponseEntity("Il semble y avoir une erreur dans votre session",
                HttpStatus.CONFLICT);
        } catch (FakeAccountException ex) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("Content-Type", "application/text; charset=UTF-8");
            return new ResponseEntity("Ce rib n'est associé à aucun compte",
                responseHeaders, HttpStatus.BAD_REQUEST);
        } catch (AccountOwnerException ex) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("Content-Type", "application/text; charset=UTF-8");
            return new ResponseEntity("Le compte à débiter n'appartient à personne",
                responseHeaders, HttpStatus.BAD_REQUEST);
        } catch (AccountTypeException ex) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("Content-Type", "application/text; charset=UTF-8");
            return new ResponseEntity("Les débits ne sont pas autorisés sur ce compte",
                responseHeaders, HttpStatus.BAD_REQUEST);
        } catch (CurrencyException ex) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("Content-Type", "application/text; charset=UTF-8");
            return new ResponseEntity("Ce compte utilise une devise différente du votre",
                responseHeaders, HttpStatus.BAD_REQUEST);
        } catch (OverdraftException ex) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("Content-Type", "application/text; charset=UTF-8");
            return new ResponseEntity("Les dépassements ne sont pas autorisés sur ce compte",
                responseHeaders, HttpStatus.BAD_REQUEST);
        } catch (DebitAuthorizationException ex) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("Content-Type", "application/text; charset=UTF-8");
            return new ResponseEntity("Vous n'etes pas autorise a debiter ce compte",
                responseHeaders, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "purchase", method = RequestMethod.POST)
    private ResponseEntity<?> purchase(@RequestHeader("authorization") String autho,
        @RequestBody String body) throws IOException {
        String login = SessionController.decodeLogin(autho);   
  
        ObjectMapper mapper = new ObjectMapper();
        JsonNode data = mapper.readTree(body);
        int amount = data.get("amount").asInt();
        String share = data.get("share").asText();
        String label = data.get("label").asText();
        try {
            movementService.purchaseShareTitles(login, share, amount, label);
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (FakeClientException ex) {
            return new ResponseEntity("Il semble y avoir une erreur dans votre session",
                HttpStatus.CONFLICT);
        } catch (NoCurrentAccountException ex) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("Content-Type", "application/text; charset=UTF-8");
            return new ResponseEntity("Vous devez posséder un compte courant pour effectuer cette action",
                responseHeaders, HttpStatus.BAD_REQUEST);
        } catch (NoShareAccountException ex) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("Content-Type", "application/text; charset=UTF-8");
            return new ResponseEntity("Vous devez posséder un compte titres pour effectuer cette action",
                responseHeaders, HttpStatus.BAD_REQUEST);
        } catch (FakeShareException ex) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("Content-Type", "application/text; charset=UTF-8");
            return new ResponseEntity("Cette action de bourse n'existe pas",
                responseHeaders, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "sale", method = RequestMethod.POST)
    private ResponseEntity<?> sale(@RequestHeader("authorization") String autho,
        @RequestBody String body) throws IOException {
        String login = SessionController.decodeLogin(autho);   
  
        ObjectMapper mapper = new ObjectMapper();
        JsonNode data = mapper.readTree(body);
        
        int amount = data.get("amount").asInt();
        String share = data.get("share").asText();
        String label = data.get("label").asText();
        try {
            movementService.saleShareTitles(login, share, amount, label);
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (FakeClientException ex) {
            return new ResponseEntity("Il semble y avoir une erreur dans votre session",
                HttpStatus.CONFLICT);
        } catch (NoCurrentAccountException ex) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("Content-Type", "application/text; charset=UTF-8");
            return new ResponseEntity("Vous devez posséder un compte courant pour effectuer cette action",
                responseHeaders, HttpStatus.BAD_REQUEST);
        } catch (NoShareAccountException ex) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("Content-Type", "application/text; charset=UTF-8");
            return new ResponseEntity("Vous devez posséder un compte titres pour effectuer cette action",
                responseHeaders, HttpStatus.BAD_REQUEST);
        } catch (FakeShareTitleException ex) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("Content-Type", "application/text; charset=UTF-8");
            return new ResponseEntity("Vous devez posséder ces actions", responseHeaders, 
                HttpStatus.BAD_REQUEST);
        } catch (AmountException ex) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("Content-Type", "application/text; charset=UTF-8");
            return new ResponseEntity("Vous ne possédez pas assez d'actions",
                responseHeaders, HttpStatus.BAD_REQUEST);
        }
    }
}
