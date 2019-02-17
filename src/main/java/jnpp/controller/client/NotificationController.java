/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jnpp.controller.client;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jnpp.service.dto.AbstractDTO;

import jnpp.service.dto.notifications.NotificationDTO;
import jnpp.service.exceptions.entities.FakeClientException;
import jnpp.service.exceptions.entities.FakeNotificationException;
import jnpp.service.exceptions.owners.NotificationOwnerException;
import jnpp.service.services.NotificationService;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class NotificationController {
    
    @Autowired
    NotificationService notifService;
    
    @RequestMapping(value = "hasNotifs", method = RequestMethod.GET)
    public ResponseEntity<?> hasNotifs(@RequestHeader("authorization") String autho) 
        throws IOException {
        String login = SessionController.decodeLogin(autho);
        try {
            List<NotificationDTO> notifs = notifService.receiveUnseenNotifications(login);
            String responseBody = "{\"hasNotifs\":" + String.valueOf(!notifs.isEmpty()) + "}";
            System.out.println(responseBody);
            return new ResponseEntity(responseBody, HttpStatus.OK);
        } catch (FakeClientException ex) {
            return new ResponseEntity("Il semble y avoir une erreur dans votre session",
                HttpStatus.CONFLICT);
        }    
    }
    
    @RequestMapping(value = "notifs", method = RequestMethod.GET)
    public ResponseEntity<?> getNotifs(@RequestHeader("authorization") String autho) 
        throws IOException {
        String login = SessionController.decodeLogin(autho);
        try {
            return new ResponseEntity(
                AbstractDTO.toJson(notifService.receiveNotifications(login)), HttpStatus.OK);
        } catch (FakeClientException ex) {
            return new ResponseEntity("Il semble y avoir une erreur dans votre session",
                HttpStatus.CONFLICT);
        }    
    }
    
    @RequestMapping(value = "seeNotif", method = RequestMethod.PUT)
    public ResponseEntity<?> seeNotif(@RequestHeader("authorization") String autho,
            @RequestBody String body) 
        throws IOException {
        String login = SessionController.decodeLogin(autho);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode data = mapper.readTree(body);
        
        long id =  data.get("id").asLong();
        try {
            notifService.seeNotification(login, id);
            return new ResponseEntity(notifService.getNotification(login, id).toJson(), HttpStatus.OK);
        } catch (FakeClientException ex) {
            return new ResponseEntity("Il semble y avoir une erreur dans votre session",
                HttpStatus.CONFLICT);
        } catch (FakeNotificationException ex) {
            return new ResponseEntity("Cette notification n'existe pas",
                HttpStatus.BAD_REQUEST);
        } catch (NotificationOwnerException ex) {
            return new ResponseEntity("Cette notification ne vous appartient pas",
                HttpStatus.BAD_REQUEST);
        }    
    }  
    
    @RequestMapping(value = "seeAllNotifs", method = RequestMethod.PUT)
    public ResponseEntity<?> seeAllNotifs(@RequestHeader("authorization") String autho) 
        throws IOException {
        String login = SessionController.decodeLogin(autho);
        try {
            notifService.seeAllNotications(login);
            return new ResponseEntity(
                AbstractDTO.toJson(notifService.receiveNotifications(login)), HttpStatus.OK);
        } catch (FakeClientException ex) {
            return new ResponseEntity("Il semble y avoir une erreur dans votre session",
                HttpStatus.CONFLICT);
        }    
    }   
}
