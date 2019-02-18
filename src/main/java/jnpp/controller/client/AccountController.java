package jnpp.controller.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jnpp.controller.client.views.Translator;
import jnpp.controller.client.views.alerts.AlertEnum;
import jnpp.controller.client.views.alerts.AlertMessage;
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
import jnpp.service.exceptions.movements.AccountTypeException;
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
    public ResponseEntity<?> getClientAccounts(@RequestHeader("authorization") String autho) 
        throws IOException {
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
    public ResponseEntity<?> getSavingBooks() throws IOException {
        return new ResponseEntity(AbstractDTO.toJson(accountService.getSavingBooks()), HttpStatus.OK);
    }
    
    @RequestMapping(value = "shares", method = RequestMethod.GET)
    public ResponseEntity<?> getShares() 
        throws IOException {
        return new ResponseEntity(AbstractDTO.toJson(accountService.getShares()), HttpStatus.OK);
    }
    
    @RequestMapping(value = "openCurrentAccount", method = RequestMethod.POST)
    public ResponseEntity<?> openCurrentAccount(@RequestHeader("authorization") String autho)
        throws IOException {
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
        @RequestBody String body) throws IOException {
        String login = SessionController.decodeLogin(autho); 
        ObjectMapper mapper = new ObjectMapper();
        JsonNode data = mapper.readTree(body);
        String bookName = data.get("bookName").asText();
        // Call service
        try {
            accountService.openSavingAccount(login, bookName);
            return new ResponseEntity(HttpStatus.CREATED);
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
        @RequestBody String body) throws IOException {
        String login = SessionController.decodeLogin(autho);
        ObjectMapper mapper = new ObjectMapper();
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
        // Call service
        try {
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
        }
    }

    @RequestMapping(value = "openShareAccount", method = RequestMethod.POST)
    public ResponseEntity<?> openShareAccount(@RequestHeader("authorization") String autho)
        throws IOException {
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

    @RequestMapping(value = "account/{accountRib}", method = RequestMethod.GET)
    public ResponseEntity<?> getAccount(@RequestHeader("authorization") String autho,
            @PathVariable String accountRib) throws IOException {
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
    
    @RequestMapping(value = "closeAccount", method = RequestMethod.DELETE)
    public ResponseEntity<?> closeAccount(@RequestHeader("authorization") String autho,
        @RequestBody String body) throws IOException {
        String login = SessionController.decodeLogin(autho);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode data = mapper.readTree(body);
        String rib = data.get("rib").asText();
        try {
            accountService.closeAccount(login, rib);
            return new ResponseEntity(HttpStatus.OK);
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
        }
    }

    
    
    /**
     * Commande de carte bancaire
     *
     * @param model   le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @param rm      objet dans lequel on ajoute les informations que l'on veut
     *                voir transiter lors des redirections
     * @return La vue du compte qui a commande la carte
     * @throws Exception Exception non controllees.
     */
    @RequestMapping(value = "commandcard", method = RequestMethod.POST)
    private ModelAndView commandCard(Model model, HttpServletRequest request,
            RedirectAttributes rm) throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>) model.asMap()
                .get("alerts");
        if (session == null) {
            session = request.getSession(true);
        }
        if (SessionController.getLanguage(session) != Translator.Language.FR) {
            SessionController.setLanguage(session, Translator.Language.FR);
        }
        if (!SessionController.isConnected(session)) {
            return new ModelAndView("redirect:/index.htm");
        }
        String rib = request.getParameter("rib");
        // Call service
        try {
            paymentMeanService.commandBankCard(
                    SessionController.getClient(session).getLogin(), rib);
            if (alerts != null) {
                alerts.add(new AlertMessage(AlertEnum.SUCCESS,
                        "Votre carte est commandé"));
            } else {
                alerts = new ArrayList<AlertMessage>();
                alerts.add(new AlertMessage(AlertEnum.SUCCESS,
                        "Votre carte est commandé"));
                rm.addFlashAttribute("alerts", alerts);
            }
        } catch (AccountTypeException accountException) {
            if (alerts != null) {
                alerts.add(new AlertMessage(AlertEnum.ERROR,
                        "Ce compte ne peut pas être lié à une carte"));
            } else {
                alerts = new ArrayList<AlertMessage>();
                alerts.add(new AlertMessage(AlertEnum.ERROR,
                        "Ce compte ne peut pas être lié à une carte"));
                rm.addFlashAttribute("alerts", alerts);
            }
        } catch (AccountOwnerException ownerException) {
            if (alerts != null) {
                alerts.add(new AlertMessage(AlertEnum.ERROR,
                        "Vous n'avez pas les droits nécessaires pour cette action"));
            } else {
                alerts = new ArrayList<AlertMessage>();
                alerts.add(new AlertMessage(AlertEnum.ERROR,
                        "Vous n'avez pas les droits nécessaires pour cette action"));
                rm.addFlashAttribute("alerts", alerts);
            }
        } catch (FakeClientException clientException) {
            if (alerts != null) {
                alerts.add(new AlertMessage(AlertEnum.ERROR,
                        "Il semble y avoir une erreur dans votre session"));
            } else {
                alerts = new ArrayList<AlertMessage>();
                alerts.add(new AlertMessage(AlertEnum.ERROR,
                        "Il semble y avoir une erreur dans votre session"));
                rm.addFlashAttribute("alerts", alerts);
            }
            return new ModelAndView("redirect:/disconnect.htm");
        }
        return new ModelAndView("redirect:/account.htm?id=" + rib);

    }

    /**
     * Commande de chéquier
     *
     * @param model   le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @param rm      objet dans lequel on ajoute les informations que l'on veut
     *                voir transiter lors des redirections
     * @return La vue du compte qui a commande la carte
     * @throws Exception Exception non controllees.
     */
    @RequestMapping(value = "commandcheckbook", method = RequestMethod.POST)
    private ModelAndView commandCheckBook(Model model,
            HttpServletRequest request, RedirectAttributes rm)
            throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>) model.asMap()
                .get("alerts");
        if (session == null) {
            session = request.getSession(true);
        }
        if (SessionController.getLanguage(session) != Translator.Language.FR) {
            SessionController.setLanguage(session, Translator.Language.FR);
        }
        if (!SessionController.isConnected(session)) {
            return new ModelAndView("redirect:/index.htm");
        }
        String rib = request.getParameter("rib");
        // Call service
        try {
            paymentMeanService.commandCheckbook(
                    SessionController.getClient(session).getLogin(), rib);
            if (alerts != null) {
                alerts.add(new AlertMessage(AlertEnum.SUCCESS,
                        "Votre carte est commandé"));
            } else {
                alerts = new ArrayList<AlertMessage>();
                alerts.add(new AlertMessage(AlertEnum.SUCCESS,
                        "Votre carte est commandé"));
                rm.addFlashAttribute("alerts", alerts);
            }
        } catch (AccountTypeException accountException) {
            if (alerts != null) {
                alerts.add(new AlertMessage(AlertEnum.ERROR,
                        "Ce compte ne peut pas être lié à une carte"));
            } else {
                alerts = new ArrayList<AlertMessage>();
                alerts.add(new AlertMessage(AlertEnum.ERROR,
                        "Ce compte ne peut pas être lié à une carte"));
                rm.addFlashAttribute("alerts", alerts);
            }
        } catch (AccountOwnerException ownerException) {
            if (alerts != null) {
                alerts.add(new AlertMessage(AlertEnum.ERROR,
                        "Vous n'avez pas les droits nécessaires pour cette action"));
            } else {
                alerts = new ArrayList<AlertMessage>();
                alerts.add(new AlertMessage(AlertEnum.ERROR,
                        "Vous n'avez pas les droits nécessaires pour cette action"));
                rm.addFlashAttribute("alerts", alerts);
            }
        } catch (FakeClientException clientException) {
            if (alerts != null) {
                alerts.add(new AlertMessage(AlertEnum.ERROR,
                        "Il semble y avoir une erreur dans votre session"));
            } else {
                alerts = new ArrayList<AlertMessage>();
                alerts.add(new AlertMessage(AlertEnum.ERROR,
                        "Il semble y avoir une erreur dans votre session"));
                rm.addFlashAttribute("alerts", alerts);
            }
            return new ModelAndView("redirect:/disconnect.htm");
        } finally {
            return new ModelAndView("redirect:/account.htm?id=" + rib);
        }
    }

}
