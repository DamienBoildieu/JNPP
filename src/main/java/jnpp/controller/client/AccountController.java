package jnpp.controller.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jnpp.service.dto.AbstractDTO;
import jnpp.service.dto.IdentityDTO;
import jnpp.service.dto.accounts.AccountDTO;
import jnpp.service.dto.movements.MovementDTO;
import jnpp.service.exceptions.ClosureException;
import jnpp.service.exceptions.accounts.ClientTypeException;
import jnpp.service.exceptions.accounts.CloseRequestException;
import jnpp.service.exceptions.accounts.UnknownIdentityException;
import jnpp.service.exceptions.duplicates.DuplicateAccountException;
import jnpp.service.exceptions.entities.FakeClientException;
import jnpp.service.exceptions.entities.FakeSavingBookException;
import jnpp.service.exceptions.owners.AccountOwnerException;
import jnpp.service.services.AccountService;
import jnpp.service.services.DebitAuthorizationService;
import jnpp.service.services.PaymentMeanService;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * Classe de contrôleur des requêtes sur des comptes
 */
@Controller
public class AccountController {

    /**
     * Service de gestion des comptes utilisateurs
     */
    @Autowired
    private AccountService accountService;
    /**
     * Service d'autorisation de prélèvements
     */
    @Autowired
    private DebitAuthorizationService authorizationService;
    /**
     * Service de moyens de paiement
     */
    @Autowired
    private PaymentMeanService paymentMeanService;

    
    @RequestMapping(value = "clientAccounts", method = RequestMethod.GET)
    public ResponseEntity<?> getClientAccounts(@RequestHeader("authorization") String autho) {
        String login = SessionController.decodeLogin(autho);       
        try {
            List<AccountDTO> accounts = accountService.getAccounts(login);
            for (ListIterator<AccountDTO> it = accounts.listIterator(); it.hasNext();) {    
                AccountDTO account = it.next();
                if (account.getType().equals(AccountDTO.Type.SHARE)) {
                    it.remove();
                    break;
                }
            }
            AccountDTO account = accountService.getShareAccount(login);
            if (account!=null)
                accounts.add(account);
            return new ResponseEntity(AbstractDTO.toJson(accounts), HttpStatus.OK);
        } catch (FakeClientException ex) {
            return new ResponseEntity("Il semble y avoir une erreur dans votre session",
                HttpStatus.FORBIDDEN);
        }
    }
    
    @RequestMapping(value = "savingBooks", method = RequestMethod.GET)
    public ResponseEntity<?> getSavingBooks() {
        return new ResponseEntity(AbstractDTO.toJson(accountService.getSavingBooks()), HttpStatus.OK);
    }
    
    @RequestMapping(value = "shares", method = RequestMethod.GET)
    public ResponseEntity<?> getShares() {
        return new ResponseEntity(AbstractDTO.toJson(accountService.getShares()), HttpStatus.OK);
    }
    /*
     * Recupere les informations d'un compte ainsi que les mouvements qui y sont associes
     */
    @RequestMapping(value = "account/{accountRib}", method = RequestMethod.GET)
    public ResponseEntity<?> getAccount(@RequestHeader("authorization") String autho,
            @PathVariable String accountRib) {
        try {
            String login = SessionController.decodeLogin(autho);
            List<AccountDTO> accounts = accountService.getAccounts(login);
            Iterator<AccountDTO> ite = accounts.iterator();
            
            while (ite.hasNext()) {
                AccountDTO account = ite.next();
                if (account.getRib().equals(accountRib)) {
                    List<MovementDTO> movements = accountService.getMovements(login,
                            accountRib);
                    if (account.getType().equals(AccountDTO.Type.SHARE))
                        account = accountService.getShareAccount(login);
                    String responseBody = "{ \"account\" : " + account.toJson() +
                            ", \"movements\" : " + AbstractDTO.toJson(movements) + "}";
                    return new ResponseEntity(responseBody, HttpStatus.OK);

                }
            }
        } catch (FakeClientException ex) {
            return new ResponseEntity("Il semble y avoir une erreur dans votre session", 
                HttpStatus.CONFLICT);
        } catch (AccountOwnerException ex) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("Content-Type", "application/text; charset=UTF-8");
            return new ResponseEntity("Vous n'êtes pas le propriétaire de ce compte", responseHeaders, 
                HttpStatus.BAD_REQUEST);
        }
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/text; charset=UTF-8");
        return new ResponseEntity("Ce compte ne correspond pas à un de vos comptes", responseHeaders, 
                HttpStatus.BAD_REQUEST);
    }
    
    @RequestMapping(value = "openCurrentAccount", method = RequestMethod.POST)
    public ResponseEntity<?> openCurrentAccount(@RequestHeader("authorization") String autho) {
        String login = SessionController.decodeLogin(autho); 
        try {
            accountService.openCurrentAccount(login);
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (DuplicateAccountException duplicate) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("Content-Type", "application/text; charset=UTF-8");
            return new ResponseEntity("Vous possédez déjà un compte courant", responseHeaders, 
                HttpStatus.BAD_REQUEST);
        } catch (FakeClientException clientException) {
            return new ResponseEntity("Il semble y avoir une erreur dans votre session", HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "openSavingAccount", method = RequestMethod.POST)
    public ResponseEntity<?> openSavingAccount(@RequestHeader("authorization") String autho,
        @RequestBody String body) {
        String login = SessionController.decodeLogin(autho); 
        ObjectMapper mapper = new ObjectMapper();
        JsonNode data;
        try {
            data = mapper.readTree(body);
            String bookName = data.get("bookName").asText();
            accountService.openSavingAccount(login, bookName);
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (IOException ex) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("Content-Type", "application/text; charset=UTF-8");
            return new ResponseEntity("Une erreur est présente dans le formulaire", responseHeaders, 
                HttpStatus.BAD_REQUEST);        
        } catch (DuplicateAccountException duplicate) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("Content-Type", "application/text; charset=UTF-8");
            return new ResponseEntity("Vous possédez déjà un compte courant", responseHeaders, 
                HttpStatus.BAD_REQUEST);
        } catch (FakeClientException clientException) {
            return new ResponseEntity("Il semble y avoir une erreur dans votre session", 
                HttpStatus.CONFLICT);
        } catch (FakeSavingBookException bookException) {
            return new ResponseEntity("Ce livret n'existe pas", HttpStatus.BAD_REQUEST);
        } catch (ClientTypeException typeClient) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("Content-Type", "application/text; charset=UTF-8");
            return new ResponseEntity("Vous ne pouvez pas créer de livret", responseHeaders, 
                HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "openJointAccount", method = RequestMethod.POST)
    public ResponseEntity<?> openJointAccount(@RequestHeader("authorization") String autho,
        @RequestBody String body) {
        String login = SessionController.decodeLogin(autho);
        ObjectMapper mapper = new ObjectMapper();
        // Call service
        try {
            JsonNode data = mapper.readTree(body);
            List<IdentityDTO> identities = new ArrayList<IdentityDTO>();
            if (data.isArray()) {
                for (final JsonNode elem : data) {
                    String firstName = elem.get("firstname").asText();
                    String lastName = elem.get("lastname").asText();
                    String genderStr = elem.get("gender").asText();
                    IdentityDTO.Gender gender = IdentityDTO.Gender.valueOf(genderStr);
                    if (null==gender)
                        return new ResponseEntity("Sexe invalide", HttpStatus.BAD_REQUEST);
                    identities.add(new IdentityDTO(gender, firstName, lastName));
                }
            }
            if (identities.size()<2)
                return new ResponseEntity("Il faut au moins deux personnes pour ouvrir un compte joint", HttpStatus.BAD_REQUEST);
            accountService.openJointAccount(login, identities);
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (FakeClientException clientException) {
            return new ResponseEntity("Il semble y avoir une erreur dans votre session", 
                HttpStatus.CONFLICT);
        } catch (ClientTypeException typeClient) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("Content-Type", "application/text; charset=UTF-8");
            return new ResponseEntity("Vous ne pouvez pas créer de compte joint", responseHeaders, 
                HttpStatus.FORBIDDEN);
        } catch (UnknownIdentityException unknowIdentity) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("Content-Type", "application/text; charset=UTF-8");
            return new ResponseEntity("Une des personnes que vous avez indiquées "
                + "n'est pas inscrite chez nous en tant que particulier", responseHeaders, 
                HttpStatus.BAD_REQUEST);
        } catch (IOException ex) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("Content-Type", "application/text; charset=UTF-8");
            return new ResponseEntity("Une erreur est présente dans le formulaire", responseHeaders, 
                HttpStatus.BAD_REQUEST);        
        }
    }

    @RequestMapping(value = "openShareAccount", method = RequestMethod.POST)
    public ResponseEntity<?> openShareAccount(@RequestHeader("authorization") String autho) {
        String login = SessionController.decodeLogin(autho);
        // Call service
        try {
            accountService.openShareAccount(login);
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (DuplicateAccountException duplicate) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("Content-Type", "application/text; charset=UTF-8");
            return new ResponseEntity("Vous possédez déjà un compte titres", responseHeaders, 
                HttpStatus.BAD_REQUEST);
        } catch (FakeClientException clientException) {
            return new ResponseEntity("Il semble y avoir une erreur dans votre session", 
                HttpStatus.CONFLICT);
        }
    }
    
    @RequestMapping(value = "closeAccount", method = RequestMethod.DELETE)
    public ResponseEntity<?> closeAccount(@RequestHeader("authorization") String autho,
        @RequestBody String body) {
        String login = SessionController.decodeLogin(autho);
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode data = mapper.readTree(body);
            String rib = data.get("rib").asText();
            accountService.closeAccount(login, rib);
            return new ResponseEntity("",HttpStatus.OK);
        } catch (AccountOwnerException ownerException) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("Content-Type", "application/text; charset=UTF-8");
            return new ResponseEntity("Vous n'êtes pas le propriétaire de ce compte", responseHeaders, 
                HttpStatus.BAD_REQUEST);
        } catch (ClosureException closureException) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("Content-Type", "application/text; charset=UTF-8");
            return new ResponseEntity("Ce compte ne peut pas être fermé", responseHeaders, 
                HttpStatus.BAD_REQUEST);
        } catch (CloseRequestException requestException) {
            return new ResponseEntity(HttpStatus.OK);
        } catch (FakeClientException clientException) {
            return new ResponseEntity("Il semble y avoir une erreur dans votre session", 
                HttpStatus.CONFLICT);
        } catch (IOException ex) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("Content-Type", "application/text; charset=UTF-8");
            return new ResponseEntity("Une erreur est présente dans le formulaire", responseHeaders, 
                HttpStatus.BAD_REQUEST);    
        }
    }
}
