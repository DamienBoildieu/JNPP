package jnpp.controller.banker;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import jnpp.service.dto.AbstractDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import jnpp.service.dto.advisor.AdvisorDTO;
import jnpp.service.dto.advisor.MessageDTO;
import jnpp.service.dto.clients.LoginDTO;
import jnpp.service.exceptions.advisors.NoAdvisorException;
import jnpp.service.exceptions.entities.FakeClientException;
import jnpp.service.services.AdvisorService;
import jnpp.service.services.BankerService;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MessagesController {

    private static final SimpleDateFormat DATE_FORMAT = 
            new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    
    @Autowired
    AdvisorService advisorService;
    @Autowired
    BankerService bankerService;

    @RequestMapping(value = "banker/get-discusion", method = RequestMethod.GET)
    public ResponseEntity<?> getDiscusion(
            @RequestParam(value = "login") String login) {
        try {
            AdvisorDTO advisor = advisorService.getAdvisor(login);
            LoginDTO client = bankerService.getLogin(login);
            List<MessageDTO> messages = advisorService.receiveMessages(login);
            String json = "{\"advisor\":" + advisor.toJson()
                    + ",\"client\":" + client.toJson()
                    + ",\"messages\":" + AbstractDTO.toJson(messages) + "}";
            return new ResponseEntity(json, HttpStatus.OK);
        } catch (FakeClientException e) {}
        return new ResponseEntity("", HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "banker/get-messages", method = RequestMethod.GET)
    public ResponseEntity<?> getMessages(
            @RequestParam(value = "login") String login) {
        try {
            List<MessageDTO> messages = advisorService.receiveMessages(login);
            String json = AbstractDTO.toJson(messages);
            return new ResponseEntity(json, HttpStatus.OK);
        } catch (FakeClientException e) {}
        return new ResponseEntity("", HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "banker/get-messages-since", 
            method = RequestMethod.GET)
    public ResponseEntity<?> getMessagesSince(
            @RequestParam(value = "login") String login,
            @RequestParam(value = "timestamp") String timestamp) {
        try {
            Date date = DATE_FORMAT.parse(timestamp);
            List<MessageDTO> messages
                    = advisorService.receiveLastMessages(login, date);
            String json = AbstractDTO.toJson(messages);
            return new ResponseEntity(json, HttpStatus.OK);
        } catch (FakeClientException e) {
        } catch (ParseException e) {}
        return new ResponseEntity("", HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "banker/send-message", method = RequestMethod.POST)
    public ResponseEntity<?> sendMessage(@RequestBody String string) {
        try {
            JsonNode data = (new ObjectMapper()).readTree(string);
            String login = data.get("login").asText();
            String content = data.get("content").asText();
            MessageDTO message = bankerService.sendMessage(login, content);
            String json = message.toJson();
            return new ResponseEntity(json, HttpStatus.OK);
        } catch (NoAdvisorException e) {} 
        catch (FakeClientException e) {} 
        catch (IOException ex) {}
        catch (NullPointerException e) {}
        return new ResponseEntity("", HttpStatus.BAD_REQUEST);
    }

}
