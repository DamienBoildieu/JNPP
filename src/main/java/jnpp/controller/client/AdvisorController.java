package jnpp.controller.client;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jnpp.service.dto.AbstractDTO;
import jnpp.service.dto.advisor.AdvisorDTO;
import jnpp.service.exceptions.advisors.AvailableException;
import jnpp.service.exceptions.advisors.DateException;
import jnpp.service.exceptions.advisors.NoAdvisorException;
import jnpp.service.exceptions.entities.FakeClientException;
import jnpp.service.exceptions.owners.AppointmentOwnerException;
import jnpp.service.services.AdvisorService;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * Contrôleur du conseiller
 */
@Controller
public class AdvisorController {

    /**
     * Le service du conseiller
     */
    @Autowired
    private AdvisorService advisorService;

    
    @RequestMapping(value = "clientAdvisor", method = RequestMethod.GET)
    public ResponseEntity<?> getClientAdvisor(@RequestHeader("authorization") String autho) 
        throws IOException {
        String login = SessionController.decodeLogin(autho);       
        try {
            AdvisorDTO advisor = advisorService.getAdvisor(login);
            System.out.println(advisor.toJson());
            if (advisor!=null)
                return new ResponseEntity(advisor.toJson(), HttpStatus.OK);
            else 
                return new ResponseEntity("Vous n'avez pas de conseiller",
                HttpStatus.BAD_REQUEST);
        } catch (FakeClientException ex) {
            return new ResponseEntity("Il semble y avoir une erreur dans votre session",
                HttpStatus.FORBIDDEN);
        }
    }
    
    @RequestMapping(value = "clientAppoints", method = RequestMethod.GET)
    public ResponseEntity<?> getClientAppoints(@RequestHeader("authorization") String autho) 
        throws IOException {
        String login = SessionController.decodeLogin(autho);       
        try {
            return new ResponseEntity(AbstractDTO.toJson(advisorService.getAppoinments(login)), HttpStatus.OK);
        } catch (FakeClientException ex) {
            return new ResponseEntity("Il semble y avoir une erreur dans votre session",
                HttpStatus.FORBIDDEN);
        }
    }
    
    @RequestMapping(value = "clientMessages", method = RequestMethod.GET)
    public ResponseEntity<?> getClientMessages(@RequestHeader("authorization") String autho) 
        throws IOException {
        String login = SessionController.decodeLogin(autho);       
        try {
            return new ResponseEntity(AbstractDTO.toJson(advisorService.receiveMessages(login)), HttpStatus.OK);
        } catch (FakeClientException ex) {
            return new ResponseEntity("Il semble y avoir une erreur dans votre session",
                HttpStatus.FORBIDDEN);
        }
    }
    
    @RequestMapping(value = "makeAppoint", method = RequestMethod.POST)
    public ResponseEntity<?> makeAppoint(@RequestHeader("authorization") String autho,
        @RequestBody String body) throws IOException {
        String login = SessionController.decodeLogin(autho); 
        ObjectMapper mapper = new ObjectMapper();
        JsonNode data = mapper.readTree(body);
                
        String date = data.get("date").asText();
        try {
            Date appointDate = new SimpleDateFormat("dd/MM/yyyy HH:mm")
                    .parse(date);
            return new ResponseEntity(advisorService.makeAppointment(login, appointDate).toJson(),
                HttpStatus.CREATED);
        } catch (ParseException ex) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("Content-Type", "application/text; charset=UTF-8");
            return new ResponseEntity("Une erreur est présente dans le formulaire",
                responseHeaders, HttpStatus.BAD_REQUEST);
        } catch (DateException dateException) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("Content-Type", "application/text; charset=UTF-8");
            return new ResponseEntity("La date indiquée n'est pas valable",
                responseHeaders, HttpStatus.BAD_REQUEST);            
        } catch (AvailableException availableException) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("Content-Type", "application/text; charset=UTF-8");
            return new ResponseEntity("Votre conseiller n'est pas disponible sur ce créneau",
                responseHeaders, HttpStatus.BAD_REQUEST); 
        } catch (FakeClientException clientException) {
            return new ResponseEntity("Il semble y avoir une erreur dans votre session",
                HttpStatus.CONFLICT);
        } catch (NoAdvisorException e) {
            return new ResponseEntity("Vous n'avez pas de conseiller", HttpStatus.BAD_REQUEST);
        }      
    }
    
    
    @RequestMapping(value = "cancelAppoint", method = RequestMethod.DELETE)
    public ResponseEntity<?> cancelAppoint(@RequestHeader("authorization") String autho,
        @RequestBody String body) throws IOException {
        String login = SessionController.decodeLogin(autho); 
        ObjectMapper mapper = new ObjectMapper();
        JsonNode data = mapper.readTree(body);
        
        long id = data.get("id").asLong();
        try {
            advisorService.cancelAppoint(login, id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (DateException dateException) {
            return new ResponseEntity("Il est trop tard pour annuler ce rendez-vous",
                HttpStatus.BAD_REQUEST);
        } catch (AppointmentOwnerException ownerException) {
            return new ResponseEntity("Ce rendez-vous n'est pas le votre",
                HttpStatus.BAD_REQUEST);
        } catch (FakeClientException clientException) {
            return new ResponseEntity("Il semble y avoir une erreur dans votre session",
                HttpStatus.CONFLICT);
        }
    }
    
    @RequestMapping(value = "sendMessage", method = RequestMethod.POST)
    protected ResponseEntity<?> sendMessage(@RequestHeader("authorization") String autho,
        @RequestBody String body) throws IOException {
        String login = SessionController.decodeLogin(autho); 
        ObjectMapper mapper = new ObjectMapper();
        JsonNode data = mapper.readTree(body);
        
        String message = data.get("content").asText();
        try {
            return new ResponseEntity(advisorService.sendMessage(login, message).toJson(),
                HttpStatus.CREATED);
        } catch (FakeClientException clientException) {
            return new ResponseEntity("Il semble y avoir une erreur dans votre session",
                HttpStatus.CONFLICT);
        } catch (NoAdvisorException e) {
            return new ResponseEntity("Vous n'avez pas de conseiller", HttpStatus.BAD_REQUEST);
        }
    }  
}
