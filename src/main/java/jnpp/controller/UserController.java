package jnpp.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jnpp.controller.views.JNPPModelAndView;
import jnpp.controller.views.Translator;
import jnpp.controller.views.alerts.AlertEnum;
import jnpp.controller.views.alerts.AlertMessage;
import jnpp.controller.views.info.ViewInfo;
import jnpp.service.dto.IdentityDTO;
import jnpp.service.dto.clients.ClientDTO;
import jnpp.service.dto.clients.PrivateDTO;
import jnpp.service.dto.clients.ProfessionalDTO;
import jnpp.service.exceptions.ClosureException;
import jnpp.service.exceptions.clients.AgeException;
import jnpp.service.exceptions.clients.InformationException;
import jnpp.service.exceptions.duplicates.DuplicateClientException;
import jnpp.service.exceptions.entities.FakeClientException;
import jnpp.service.services.ClientService;
import jnpp.service.services.NotificationService;
import org.codehaus.jackson.JsonNode;
import org.springframework.http.ResponseEntity;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;

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
    /**
     * Le service de notifications
     */
    @Autowired
    private NotificationService notifService;

    @RequestMapping(value = "connect", method = RequestMethod.POST)
    public ResponseEntity<?> connect(@RequestBody String userString, HttpServletRequest request) 
        throws IOException {
        HttpSession session = request.getSession();
        if (null==session)
            session = request.getSession(true);
        if (SessionController.isConnected(session))
            return new ResponseEntity("Vous devez etre deconnecte", HttpStatus.FORBIDDEN);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode data = mapper.readTree(userString);
        String id = data.get("username").asText();
        String password = data.get("password").asText();
        ClientDTO client = this.clientService.signIn(id, password);
        if (client!=null) {
            SessionController.setClient(session, client);
            switch (client.getType()) {
                case PRIVATE:
                    IdentityDTO identity = ((PrivateDTO)client).getIdentity();
                    return new ResponseEntity(identity.getFirstname()+" "+identity.getLastname(),
                        HttpStatus.OK);
                case PROFESIONAL:
                    String name = ((ProfessionalDTO)client).getName();
                    return new ResponseEntity(name, HttpStatus.OK);
                default:
                    return new ResponseEntity("Une erreur a eu lieu sur le serveur, "
                        + "veuillez contacter un administrateur", HttpStatus.INTERNAL_SERVER_ERROR);           
            }
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
        HttpSession session = request.getSession();
        if (null!=session && SessionController.isConnected(session))
            return new ResponseEntity("Vous devez etre deconnecte", HttpStatus.FORBIDDEN);
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
            return new ResponseEntity("Utilisateur cree", HttpStatus.CREATED);
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
        HttpSession session = request.getSession();
        if (null!=session && SessionController.isConnected(session))
            return new ResponseEntity("Vous devez etre deconnecte", HttpStatus.FORBIDDEN);        
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
            return new ResponseEntity("Utilisateur cree", HttpStatus.CREATED);
        } catch (DuplicateClientException ex) {
            return new ResponseEntity("Ce client est deja enregistre", HttpStatus.BAD_REQUEST);             
        } catch (InformationException ex) {
            return new ResponseEntity("Une erreur est presente dans le formulaire",
                HttpStatus.BAD_REQUEST);                
        }
    }
    
    @RequestMapping(value = "disconnect", method = RequestMethod.GET)
    public ResponseEntity<?> disconnect(HttpServletRequest request) 
    		throws IOException {
        HttpSession session = request.getSession();
        if (null==session)
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        if (SessionController.isConnected(session)) {
            SessionController.deleteSession(session);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.FORBIDDEN);
    }

    @RequestMapping(value = "privatePassword", method = RequestMethod.POST)
    public ResponseEntity<?> privateResetPassword(@RequestBody String body, HttpServletRequest request) 
            throws Exception {
        HttpSession session = request.getSession();
        if (null!=session && SessionController.isConnected(session))
            return new ResponseEntity<String>("Vous devez etre deconnecte", HttpStatus.FORBIDDEN);        
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
            return new ResponseEntity("Demande de nouveau mot de passe acceptee",
                HttpStatus.OK);
        else 
            return new ResponseEntity("Aucun compte associe a ces informations n'est enregistre chez nous",
                HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "professionalpassword", method = RequestMethod.POST)
    protected ResponseEntity<?> professionalResetPassword(@RequestBody String body, HttpServletRequest request)
            throws Exception {
        HttpSession session = request.getSession();
        if (null!=session && SessionController.isConnected(session))
            return new ResponseEntity<String>("Vous devez etre deconnecte", HttpStatus.FORBIDDEN);        
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
            return new ResponseEntity("Demande de nouveau mot de passe acceptee",
                    HttpStatus.OK);
        else 
            return new ResponseEntity("Aucun compte associe a ces informations n'est enregistre chez nous",
                    HttpStatus.BAD_REQUEST);
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

    /**
     * Requête du formulaire de changement d'information d'un utilisateur
     *
     * @param model   le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @param rm      objet dans lequel on ajoute les informations que l'on veut
     *                voir transiter lors des redirections
     * @return La vue d'information client
     * @throws Exception Exception non controllees.
     */
    @RequestMapping(value = "editinfo", method = RequestMethod.POST)
    private ModelAndView validateInfo(Model model, HttpServletRequest request,
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
        if (SessionController.isConnected(session)) {
            // Get parameters
            String email = request.getParameter("email");
            String streetNbrStr = request.getParameter("streetNbr");
            String street = request.getParameter("street");
            String city = request.getParameter("city");
            String country = request.getParameter("country");
            String phone = request.getParameter("phone");

            Integer streetNbr = Integer.parseInt(streetNbrStr);
            // Call service
            try {
                ClientDTO client = clientService.update(
                        SessionController.getClient(session).getLogin(), email,
                        streetNbr, street, city, country, phone);
                SessionController.setClient(session, client);
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
            } catch (InformationException invalidFormat) {
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.ERROR,
                            "Une erreur est présente dans le formulaire"));
                } else {
                    alerts = new ArrayList<AlertMessage>();
                    alerts.add(new AlertMessage(AlertEnum.ERROR,
                            "Une erreur est présente dans le formulaire"));
                    rm.addFlashAttribute("alerts", alerts);
                }
                return new ModelAndView("redirect:/userinfo.htm");
            }
        }
        return new ModelAndView("redirect:/index.htm"); // ne devrait pas
                                                        // arriver
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
