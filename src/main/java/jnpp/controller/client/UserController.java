package jnpp.controller.client;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
    public ResponseEntity<?> connect(@RequestBody String userString) 
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
    
    @RequestMapping(value = "genders", method = RequestMethod.GET)
    public ResponseEntity<?> getGenders() 
        throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return new ResponseEntity(mapper.writeValueAsString(IdentityDTO.Gender.values()), HttpStatus.OK);
    }
    
    @RequestMapping(value = "privateSignUp", method = RequestMethod.POST)
    public ResponseEntity<?> privateSignUp(@RequestBody String body) 
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
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("Content-Type", "application/text; charset=UTF-8");
            return new ResponseEntity("Une erreur est présente dans le formulaire",
                responseHeaders, HttpStatus.BAD_REQUEST);
        } catch (DuplicateClientException ex) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("Content-Type", "application/text; charset=UTF-8");
            return new ResponseEntity("Ce client est déjà enregistré", responseHeaders,
                HttpStatus.CONFLICT);             
        } catch (AgeException ex) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("Content-Type", "application/text; charset=UTF-8");
            return new ResponseEntity("Un client ne peut pas être mineur", responseHeaders,
                HttpStatus.BAD_REQUEST);
        } catch (InformationException ex) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("Content-Type", "application/text; charset=UTF-8");
            return new ResponseEntity("Une erreur est présente dans le formulaire",
                responseHeaders, HttpStatus.BAD_REQUEST);                
        }
    }
    
    @RequestMapping(value = "proSignUp", method = RequestMethod.POST)
    public ResponseEntity<?> proSignUp(@RequestBody String body) 
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
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("Content-Type", "application/text; charset=UTF-8");
            return new ResponseEntity("Ce client est déjà enregistré", responseHeaders, 
                HttpStatus.CONFLICT);             
        } catch (InformationException ex) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("Content-Type", "application/text; charset=UTF-8");
            return new ResponseEntity("Une erreur est présente dans le formulaire",
                responseHeaders, HttpStatus.BAD_REQUEST);                
        }
    }

    @RequestMapping(value = "privatePassword", method = RequestMethod.PUT)
    public ResponseEntity<?> privateResetPassword(@RequestBody String body) 
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
        else {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("Content-Type", "application/text; charset=UTF-8");
            return new ResponseEntity("Aucun compte associé a ces informations n'est enregistré chez nous",
                responseHeaders, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "professionalpassword", method = RequestMethod.PUT)
    public ResponseEntity<?> professionalResetPassword(@RequestBody String body)
        throws IOException {     
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
        try {
            if (clientService.resetPassword(id, company, gender, firstName, lastName, email))
                return new ResponseEntity(HttpStatus.OK);
            else {
                HttpHeaders responseHeaders = new HttpHeaders();
                responseHeaders.add("Content-Type", "application/text; charset=UTF-8");
                return new ResponseEntity("Aucun compte associé a ces informations n'est enregistré chez nous",
                        responseHeaders, HttpStatus.BAD_REQUEST);
            }
        } catch (FakeClientException ex) {
            return new ResponseEntity("Il semble y avoir une erreur dans votre session",
                HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "updateUserInfo", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUserInfo(@RequestHeader("authorization") String autho,
        @RequestBody String body) throws IOException {
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
                HttpStatus.CONFLICT);
        } catch (InformationException invalidFormat) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("Content-Type", "application/text; charset=UTF-8");
            return new ResponseEntity("Une erreur est présente dans le formulaire",
                responseHeaders, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "updateUserPassword", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUserPassword(@RequestHeader("authorization") String autho,
        @RequestBody String body) throws IOException {
        String login = SessionController.decodeLogin(autho);
        String password = SessionController.decodePassword(autho);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode data = mapper.readTree(body);
        String newPassword = data.get("newPassword").asText();
        // Call service
        try {
            if (clientService.updatePassword(login, password, newPassword)) {
                HttpHeaders responseHeaders = new HttpHeaders();
                responseHeaders.add("Authorization", SessionController.encodeAuthData(login,newPassword));
                return new ResponseEntity(responseHeaders, HttpStatus.OK);
            }
            else
                return new ResponseEntity("Les mots de passe ne sont pas corrects",
                    HttpStatus.BAD_REQUEST);
        } catch (FakeClientException invalidClient) {
            return new ResponseEntity("Il semble y avoir une erreur dans votre session",
                HttpStatus.CONFLICT);
        }
    }  
    
    @RequestMapping(value = "deleteUser", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@RequestHeader("authorization") String autho) throws IOException {
        String login = SessionController.decodeLogin(autho);
        String password = SessionController.decodePassword(autho);
        try {
            clientService.close(login, password);
            return new ResponseEntity("",HttpStatus.OK);
        } catch (ClosureException ex) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("Content-Type", "application/text; charset=UTF-8");
            return new ResponseEntity("Le compte ne peut pas être fermé à l'heure actuelle",
                    responseHeaders, HttpStatus.CONFLICT);
        } catch (FakeClientException ex) {
            return new ResponseEntity("Il semble y avoir une erreur dans votre session", HttpStatus.CONFLICT);
        }
    }
}
