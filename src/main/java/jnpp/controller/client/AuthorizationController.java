package jnpp.controller.client;

import java.io.IOException;

import jnpp.service.dto.AbstractDTO;
import jnpp.service.exceptions.duplicates.DuplicateDebitAuthorizationException;
import jnpp.service.exceptions.entities.FakeClientException;
import jnpp.service.exceptions.entities.FakeDebitAuthorizationException;
import jnpp.service.exceptions.movements.AccountTypeException;
import jnpp.service.exceptions.owners.AccountOwnerException;
import jnpp.service.services.DebitAuthorizationService;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class AuthorizationController {
    /**
     * Le service des autorisations de débits
     */
    @Autowired
    private DebitAuthorizationService authorizationService;
    
    @RequestMapping(value = "clientAuthorizations", method = RequestMethod.GET)
    public ResponseEntity<?> getClientAuthorizations(@RequestHeader("authorization") String autho) 
        throws IOException {
        String login = SessionController.decodeLogin(autho);
        try {
            return new ResponseEntity(AbstractDTO.toJson(authorizationService.getDebitAuthorizations(login)), HttpStatus.OK);
        } catch (FakeClientException ex) {
            return new ResponseEntity("Il semble y avoir une erreur dans votre session",
                HttpStatus.FORBIDDEN);
        }
    }
    
    @RequestMapping(value = "addAuthorization", method = RequestMethod.POST)
    public ResponseEntity<?> addAuthorization(@RequestHeader("authorization") String autho,
            @RequestBody String body)
            throws IOException {
        String login = SessionController.decodeLogin(autho);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode data = mapper.readTree(body);

        String ribFrom = data.get("ribFrom").asText();
        String ribTo = data.get("ribTo").asText();

        try {
            return new ResponseEntity(authorizationService.createDebitAuthorization(login, ribFrom, ribTo).toJson(),
                HttpStatus.CREATED);

        } catch (FakeClientException ex) {
            return new ResponseEntity("Il semble y avoir une erreur dans votre session",
                HttpStatus.FORBIDDEN);
        } catch (AccountOwnerException ex) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("Content-Type", "application/text; charset=UTF-8");
            return new ResponseEntity("Vous devez être proprietaire du compte autorisé à être debité",
                responseHeaders, HttpStatus.BAD_REQUEST);
        } catch (DuplicateDebitAuthorizationException ex) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("Content-Type", "application/text; charset=UTF-8");
            return new ResponseEntity("Vous avez déjà autorisé ce compte à debiter le votre",
                responseHeaders, HttpStatus.BAD_REQUEST);
        } catch (AccountTypeException ex) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("Content-Type", "application/text; charset=UTF-8");
            return new ResponseEntity("Votre compte n'est pas débitable ou le compte cible ne peut être débiteur",
                responseHeaders, HttpStatus.BAD_REQUEST);
        }
    }
    
    @RequestMapping(value = "deleteAuthorization", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAuthorization(@RequestHeader("authorization") String autho,
            @RequestBody String body)
            throws IOException {
        String login = SessionController.decodeLogin(autho);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode data = mapper.readTree(body);

        String ribFrom = data.get("ribFrom").asText();
        String ribTo = data.get("ribTo").asText();

        try {
            authorizationService.deleteDebitAuthorization(login, ribFrom, ribTo);
            return new ResponseEntity("",HttpStatus.OK);       
        } catch (FakeClientException ex) {
            return new ResponseEntity("Il semble y avoir une erreur dans votre session",
                HttpStatus.FORBIDDEN);
        } catch (FakeDebitAuthorizationException ex) {
            return new ResponseEntity("Il semble y avoir une erreur avec vos autorisations",
                HttpStatus.BAD_REQUEST);
        } catch (AccountOwnerException ex) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("Content-Type", "application/text; charset=UTF-8");
            return new ResponseEntity("Vous devez être proprietaire du compte autorisé à être debiter",
                responseHeaders, HttpStatus.BAD_REQUEST);
        }
    }
}
