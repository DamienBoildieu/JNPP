package jnpp.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jnpp.controller.views.Translator;
import jnpp.controller.views.alerts.AlertEnum;
import jnpp.controller.views.alerts.AlertMessage;
import jnpp.service.dto.IdentityDTO;
import jnpp.service.dto.clients.ClientDTO;
import jnpp.service.exceptions.ClosureException;
import jnpp.service.exceptions.clients.AgeException;
import jnpp.service.exceptions.clients.InformationException;
import jnpp.service.exceptions.duplicates.DuplicateClientException;
import jnpp.service.exceptions.entities.FakeClientException;
import jnpp.service.services.ClientService;
import org.codehaus.jackson.JsonNode;
import org.springframework.http.ResponseEntity;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * Classe contrôlant la gestion des utilisateurs
 */
@Controller
public class UserController {

    /**
     * Le service des utilisateurs
     */
    @Autowired
    private ClientService clientService;

    @RequestMapping(value = "connect", method = RequestMethod.POST)
    public ResponseEntity<?> connect(@RequestBody String userString, HttpServletRequest request) 
        throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode data = mapper.readTree(userString);
        String id = data.get("username").asText();
        String password = data.get("password").asText();
        ClientDTO client = this.clientService.signIn(id, password);
        if (client!=null) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("Authorization", SessionController.encodeAuthData(id,password));
            return new ResponseEntity(client.toJson(), responseHeaders ,HttpStatus.OK);
        } else
            return new ResponseEntity("Erreur dans l'identifiant ou le mot de passe", 
                HttpStatus.BAD_REQUEST);
    }
    
    @RequestMapping(value = "getGenders", method = RequestMethod.GET)
    public ResponseEntity<?> getGenders(HttpServletRequest request) 
        throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return new ResponseEntity(mapper.writeValueAsString(IdentityDTO.Gender.values()), HttpStatus.OK);
    }
    
    @RequestMapping(value = "privateSignUp", method = RequestMethod.POST)
    public ResponseEntity<?> privateSignUp(@RequestBody String body, HttpServletRequest request) 
        throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode data = mapper.readTree(body);
        try {
            String firstName = data.get("firstName").asText();
            String lastName = data.get("lastName").asText();
            String genderStr = data.get("gender").asText();
            String birthdayStr = data.get("birthday").asText();
            String email = data.get("email").asText();
            int streetNbr = data.get("streetNbr").asInt();
            String street = data.get("street").asText();
            String city = data.get("city").asText();
            String country = data.get("country").asText();
            String phone =  data.get("phone").asText();

            IdentityDTO.Gender gender = IdentityDTO.Gender.valueOf(genderStr);
            if (null==gender)
                return new ResponseEntity("Sexe invalide", HttpStatus.BAD_REQUEST);
            Date birthday = new SimpleDateFormat("yyyy-MM-dd")
                    .parse(birthdayStr);
            clientService.signUp(gender, firstName, lastName, birthday,
                    email, streetNbr, street, city, country, phone);
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (ParseException ex) {
            return new ResponseEntity("Une erreur est presente dans le formulaire",
                HttpStatus.BAD_REQUEST);
        } catch (DuplicateClientException ex) {
            return new ResponseEntity("Ce client est deja enregistre",
                HttpStatus.BAD_REQUEST);             
        } catch (AgeException ex) {
            return new ResponseEntity("Un client ne peut pas être mineur",
                HttpStatus.BAD_REQUEST);
        } catch (InformationException ex) {
            return new ResponseEntity("Une erreur est presente dans le formulaire",
                HttpStatus.BAD_REQUEST);                
        }
    }
    
    @RequestMapping(value = "proSignUp", method = RequestMethod.POST)
    public ResponseEntity<?> proSignUp(@RequestBody String body, HttpServletRequest request) 
        throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode data = mapper.readTree(body);
        try {
            String company = data.get("firstName").asText();
            String firstName = data.get("firstName").asText();
            String lastName = data.get("lastName").asText();
            String genderStr = data.get("gender").asText();
            String email = data.get("email").asText();
            int streetNbr = data.get("streetNbr").asInt();
            String street = data.get("street").asText();
            String city = data.get("city").asText();
            String country = data.get("country").asText();
            String phone =  data.get("phone").asText();

            IdentityDTO.Gender gender = IdentityDTO.Gender.valueOf(genderStr);
            if (null==gender)
                return new ResponseEntity("Sexe invalide", HttpStatus.BAD_REQUEST);
            clientService.signUp(company,gender, firstName, lastName,
                    email, streetNbr, street, city, country, phone);
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (DuplicateClientException ex) {
            return new ResponseEntity("Ce client est deja enregistre", HttpStatus.BAD_REQUEST);             
        } catch (InformationException ex) {
            return new ResponseEntity("Une erreur est presente dans le formulaire",
                HttpStatus.BAD_REQUEST);                
        }
    }

    @RequestMapping(value = "privatePassword", method = RequestMethod.PUT)
    public ResponseEntity<?> privateResetPassword(@RequestBody String body, HttpServletRequest request) 
        throws Exception {    
        ObjectMapper mapper = new ObjectMapper();
        JsonNode data = mapper.readTree(body);
        String id = data.get("id").asText();
        String firstName = data.get("firstName").asText();
        String lastName = data.get("lastName").asText();
        String email = data.get("email").asText();
        String genderStr = data.get("gender").asText();
        IdentityDTO.Gender gender = IdentityDTO.Gender.valueOf(genderStr);
        if (null==gender)
            return new ResponseEntity<String>("Sexe invalide", HttpStatus.BAD_REQUEST);
        if (clientService.resetPassword(id, gender, firstName, lastName, email))
            return new ResponseEntity(HttpStatus.OK);
        else 
            return new ResponseEntity("Aucun compte associe a ces informations n'est enregistre chez nous",
                HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "professionalpassword", method = RequestMethod.PUT)
    public ResponseEntity<?> professionalResetPassword(@RequestBody String body, HttpServletRequest request)
        throws Exception {     
        ObjectMapper mapper = new ObjectMapper();
        JsonNode data = mapper.readTree(body);
        String id = data.get("id").asText();
        String firstName = data.get("firstName").asText();
        String lastName = data.get("lastName").asText();
        String company = data.get("company").asText();
        String email = data.get("email").asText();
        String genderStr = data.get("gender").asText();
        IdentityDTO.Gender gender = IdentityDTO.Gender.valueOf(genderStr);
        if (null==gender)
            return new ResponseEntity<String>("Sexe invalide", HttpStatus.BAD_REQUEST);
        if (clientService.resetPassword(id, company, gender, firstName, lastName, email)) 
            return new ResponseEntity(HttpStatus.OK);
        else 
            return new ResponseEntity("Aucun compte associe a ces informations n'est enregistre chez nous",
                    HttpStatus.BAD_REQUEST);
    }

    
    @RequestMapping(value = "getUserInfo", method = RequestMethod.GET)
    public ResponseEntity<?> getUserInfo(@RequestHeader("authorization") String autho, HttpServletRequest request)
            throws Exception {
        System.out.println(autho);
        return new ResponseEntity("ok", HttpStatus.OK);
    }
    
    /**
     * Requête de changement de mot de passe
     *
     * @param model   le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @param rm      objet dans lequel on ajoute les informations que l'on veut
     *                voir transiter lors des redirections
     * @return La vue d'information client
     * @throws Exception Exception non controllees.
     */
    @RequestMapping(value = "changepassword", method = RequestMethod.POST)
    protected ModelAndView changePassword(Model model,
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
        if (SessionController.isConnected(session)) {
            String old = request.getParameter("oldpsswd");
            String newp = request.getParameter("newpsswd");
            String confp = request.getParameter("confirmpsswd");
            if (!newp.equals(confp)) {
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.ERROR,
                            "Les deux champs de nouveau mot de passe doivent correspondre"));
                } else {
                    alerts = new ArrayList<AlertMessage>();
                    alerts.add(new AlertMessage(AlertEnum.ERROR,
                            "Les deux champs de nouveau mot de passe doivent correspondre"));
                    rm.addFlashAttribute("alerts", alerts);
                }

                return new ModelAndView("redirect:/userinfo.htm");
            }
            try {
                clientService.updatePassword(
                        SessionController.getClient(session).getLogin(), old,
                        newp);
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.SUCCESS,
                            "Mise à jour réussie"));
                } else {
                    alerts = new ArrayList<AlertMessage>();
                    alerts.add(new AlertMessage(AlertEnum.SUCCESS,
                            "Mise à jour réussie"));
                    rm.addFlashAttribute("alerts", alerts);
                }
                return new ModelAndView("redirect:/userinfo.htm");
            } catch (FakeClientException invalidClient) {
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
        }
        return new ModelAndView("redirect:/index.htm"); // ne devrait pas
                                                        // pouvoir arriver
    }

    @RequestMapping(value = "updateUserInfo", method = RequestMethod.PUT)
    private ResponseEntity<?> updateUserInfo(@RequestHeader("authorization") String autho,
        @RequestBody String body, HttpServletRequest request) throws Exception {
        String login = SessionController.decodeLogin(autho);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode data = mapper.readTree(body);
        String email = data.get("email").asText();
        JsonNode address = data.get("address");
        int streetNbr = address.get("number").asInt();
        String street = address.get("street").asText();
        String city = address.get("city").asText();
        String country = address.get("state").asText();
        String phone = data.get("phone").asText();

        // Call service
        try {
            ClientDTO client = clientService.update(login, email, streetNbr, street, city, country, phone);
            return new ResponseEntity(client.toJson(), HttpStatus.OK);
        } catch (FakeClientException invalidClient) {
            return new ResponseEntity("Il semble y avoir une erreur dans votre session",
                HttpStatus.BAD_REQUEST);
        } catch (InformationException invalidFormat) {
            return new ResponseEntity("Une erreur est présente dans le formulaire",
                HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Requête de fermeture de compte client
     *
     * @param model   le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @param rm      objet dans lequel on ajoute les informations que l'on veut
     *                voir transiter lors des redirections
     * @param view    Nom de vue.
     * @return L'index si réussite, la vue userInfo sinon
     * @throws Exception Exception non controllees.
     */
    @RequestMapping(value = "closeuser", method = RequestMethod.POST)
    protected ModelAndView closeUser(Model model, HttpServletRequest request,
            RedirectAttributes rm, String view) throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>) model.asMap()
                .get("alerts");
        if (session == null) {
            session = request.getSession(true);
        }
        if (SessionController.getLanguage(session) != Translator.Language.FR) {
            SessionController.setLanguage(session, Translator.Language.FR);
        }
        if (SessionController.isConnected(session)) {
            try {
                String password = request.getParameter("psswd");
                String confirm = request.getParameter("confirm");
                if (!password.equals(confirm)) {
                    if (alerts != null) {
                        alerts.add(new AlertMessage(AlertEnum.ERROR,
                                "Les deux champs de mot de passe doivent correspondre"));
                    } else {
                        alerts = new ArrayList<AlertMessage>();
                        alerts.add(new AlertMessage(AlertEnum.ERROR,
                                "Les deux champs de mot de passe doivent correspondre"));
                        rm.addFlashAttribute("alerts", alerts);
                    }

                    return new ModelAndView("redirect:/userinfo.htm");
                }
                clientService.close(
                        SessionController.getClient(session).getLogin(),
                        password);
                SessionController.clearSession(session);
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.SUCCESS,
                            "Votre compte a bien été cloturé"));
                } else {
                    alerts = new ArrayList<AlertMessage>();
                    alerts.add(new AlertMessage(AlertEnum.SUCCESS,
                            "Votre compte a bien été cloturé"));
                    rm.addFlashAttribute("alerts", alerts);
                }
                return new ModelAndView("redirect:/index.htm");
            } catch (ClosureException closure) {
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.ERROR,
                            "Votre compte client ne peut être cloturé, assuerz-vous d'avoir fermé tout vos comptes"));
                } else {
                    alerts = new ArrayList<AlertMessage>();
                    alerts.add(new AlertMessage(AlertEnum.SUCCESS,
                            "Votre compte client ne peut être cloturé, assuerz-vous d'avoir fermé tout vos comptes"));
                    rm.addFlashAttribute("alerts", alerts);
                }
                return new ModelAndView("redirect:/userInfo.htm");
            } catch (FakeClientException invalidClient) {
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
        }
        return new ModelAndView("redirect:/index.htm"); // ne devrait pas
                                                        // pouvoir arriver
    }
}
