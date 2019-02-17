package jnpp.controller.client;

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

import jnpp.controller.client.views.Translator;
import jnpp.controller.client.views.alerts.AlertEnum;
import jnpp.controller.client.views.alerts.AlertMessage;
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
            advisorService.makeAppointment(login, appointDate);
            return new ResponseEntity(HttpStatus.CREATED);
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
        @RequestBody String body)
            throws IOException {
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
    
    /**
     * Requête du formulaire d'envoie de message
     *
     * @param model   le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @param rm      objet dans lequel on ajoute les informations que l'on veut
     *                voir transiter lors des redirections
     * @return Une redirection vers la vue de message
     * @throws Exception Exception non controllees.
     */
    @RequestMapping(value = "message", method = RequestMethod.POST)
    protected ModelAndView sendMessage(Model model, HttpServletRequest request,
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
            try {
                String message = request.getParameter("content");
                advisorService.sendMessage(
                        SessionController.getClient(session).getLogin(),
                        message);
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.SUCCESS,
                            "Le message a bien été envoyé"));
                } else {
                    alerts = new ArrayList<AlertMessage>();
                    alerts.add(new AlertMessage(AlertEnum.SUCCESS,
                            "Le message a bien été envoyé"));
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
            } catch (NoAdvisorException e) {
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.SUCCESS,
                            "Nous n'avez pas de conseiller."));
                } else {
                    alerts = new ArrayList<AlertMessage>();
                    alerts.add(new AlertMessage(AlertEnum.SUCCESS,
                            "Nous n'avez pas de conseiller."));
                    rm.addFlashAttribute("alerts", alerts);
                }
            } finally {
                return new ModelAndView("redirect:/message.htm");
            }
        }
        return new ModelAndView("redirect:/index.htm"); // ne devrait pas
                                                        // arriver
    }  
}
