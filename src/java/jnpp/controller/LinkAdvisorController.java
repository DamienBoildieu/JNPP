/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jnpp.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jnpp.controller.views.AppointView;
import jnpp.controller.views.JNPPModelAndView;
import jnpp.controller.views.Translator;
import jnpp.controller.views.alerts.AlertEnum;
import jnpp.controller.views.alerts.AlertMessage;
import jnpp.controller.views.info.ViewInfo;
import jnpp.service.dto.advisor.AdvisorDTO;
import jnpp.service.dto.advisor.AppointmentDTO;
import jnpp.service.dto.advisor.MessageDTO;
import jnpp.service.exceptions.entities.FakeClientException;
import jnpp.service.services.AdvisorService;
import jnpp.service.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class LinkAdvisorController {
    @Autowired
    NotificationService notifService;
    @Autowired
    AdvisorService advisorService;
    /**
     * Requête sur la vue du conseiller d'un utilisateur
     * @param model le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @param response la réponse
     * @return Une vue sur les informations du conseiller si l'utilisateur est connecté, une redirection vers l'index sinon
     * @throws Exception 
     */
    @RequestMapping(value = "advisor", method = RequestMethod.GET)
    protected ModelAndView linkToAdvisor(Model model, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rm)
            throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>)model.asMap().get("alerts");
        if (session==null)
            session = request.getSession(true);
        if (SessionController.getLanguage(session)!=Translator.Language.FR)
            SessionController.setLanguage(session,Translator.Language.FR);
        if (SessionController.isConnected(session)) {
            Boolean hasNotif = SessionController.getHasNotif(session);
            if (!hasNotif) {  
                try {
                    hasNotif = notifService.receiveUnseenNotifications(SessionController.getClient(session).getLogin()).size()>0;
                    SessionController.setHasNotif(session, hasNotif);
                } catch (FakeClientException invalidClient) {
                    if (alerts != null) {
                        alerts.add(new AlertMessage(AlertEnum.ERROR, "Il semble y avoir une erreur dans votre session"));
                    } else {
                        alerts = new ArrayList<AlertMessage>(); 
                        alerts.add(new AlertMessage(AlertEnum.ERROR, "Il semble y avoir une erreur dans votre session"));
                        
                    }
                    return new ModelAndView("redirect:/disconnect.htm");
                }
            }
            try {
                AdvisorDTO advisor = advisorService.getAdvisor(SessionController.getClient(session).getLogin());
                ModelAndView view =  new JNPPModelAndView("advisor/advisor", ViewInfo.createInfo(session, alerts));
                view.addObject("advisor", advisor);
                return view;
            } catch (FakeClientException invalidClient) {
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Il semble y avoir une erreur dans votre session"));
                } else {
                    alerts = new ArrayList<AlertMessage>(); 
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Il semble y avoir une erreur dans votre session"));
                    rm.addFlashAttribute("alerts", alerts);   
                }
                return new ModelAndView("redirect:/disconnect.htm");
            }
            
        }
        return new ModelAndView("redirect:/index.htm");
    }
    /**
     * Requête sur la vue du chat avec un conseiller
     * @param model le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @param response la réponse
     * @return Une vue sur les informations du conseiller si l'utilisateur est connecté, une redirection vers l'index sinon
     * @throws Exception 
     */
    @RequestMapping(value = "message", method = RequestMethod.GET)
    protected ModelAndView linkToMessage(Model model, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rm)
            throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>)model.asMap().get("alerts");
        if (session==null)
            session = request.getSession(true);
        if (SessionController.getLanguage(session)!=Translator.Language.FR)
            SessionController.setLanguage(session,Translator.Language.FR);
        if (SessionController.isConnected(session)) {
            Boolean hasNotif = SessionController.getHasNotif(session);
            if (!hasNotif) {  
                try {
                    hasNotif = notifService.receiveUnseenNotifications(SessionController.getClient(session).getLogin()).size()>0;
                    SessionController.setHasNotif(session, hasNotif);
                } catch (FakeClientException invalidClient) {
                    if (alerts != null) {
                        alerts.add(new AlertMessage(AlertEnum.ERROR, "Il semble y avoir une erreur dans votre session"));
                    } else {
                        alerts = new ArrayList<AlertMessage>(); 
                        alerts.add(new AlertMessage(AlertEnum.ERROR, "Il semble y avoir une erreur dans votre session"));
                        rm.addFlashAttribute("alerts", alerts);   
                    }
                    return new ModelAndView("redirect:/disconnect.htm");
                }
            }
            try {
                List<MessageDTO> messages = advisorService.receiveMessages(SessionController.getClient(session).getLogin());
                ModelAndView view = new JNPPModelAndView("advisor/message", ViewInfo.createInfo(session, alerts));
                view.addObject("messages", messages);
                return view;
            } catch (FakeClientException invalidClient) {
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Il semble y avoir une erreur dans votre session"));
                } else {
                    alerts = new ArrayList<AlertMessage>(); 
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Il semble y avoir une erreur dans votre session"));
                    rm.addFlashAttribute("alerts", alerts);   
                }
                return new ModelAndView("redirect:/disconnect.htm");
            }
        }
        return new ModelAndView("redirect:/disconnect.htm");
    }
    /**
     * Requête sur la vue des renez-vous
     * @param model le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @param response la réponse
     * @return Une vue sur les informations du conseiller si l'utilisateur est connecté, une redirection vers l'index sinon
     * @throws Exception 
     */
    @RequestMapping(value = "appoint", method = RequestMethod.GET)
    protected ModelAndView linkToAppoint(Model model, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rm)
            throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>)model.asMap().get("alerts");
        if (session==null)
            session = request.getSession(true);
        if (SessionController.getLanguage(session)!=Translator.Language.FR)
            SessionController.setLanguage(session,Translator.Language.FR);
        if (SessionController.isConnected(session)) {
            Boolean hasNotif = SessionController.getHasNotif(session);
            if (!hasNotif) {  
                try {
                    hasNotif = notifService.receiveUnseenNotifications(SessionController.getClient(session).getLogin()).size()>0;
                    SessionController.setHasNotif(session, hasNotif);
                } catch (FakeClientException invalidClient) {
                    if (alerts != null) {
                        alerts.add(new AlertMessage(AlertEnum.ERROR, "Il semble y avoir une erreur dans votre session"));
                    } else {
                        alerts = new ArrayList<AlertMessage>(); 
                        alerts.add(new AlertMessage(AlertEnum.ERROR, "Il semble y avoir une erreur dans votre session"));
                        rm.addFlashAttribute("alerts", alerts);   
                    }
                    return new ModelAndView("redirect:/disconnect.htm");
                }
            }
            try {
                List<AppointView> appoints = new ArrayList<AppointView>();
                for (AppointmentDTO appoint : advisorService.getAppoinments(SessionController.getClient(session).getLogin())) {
                    appoints.add(new AppointView(appoint));
                }
                ModelAndView view = new JNPPModelAndView("advisor/appoint", ViewInfo.createInfo(session, alerts));
                view.addObject("appoints", appoints);
                return view;
            } catch (FakeClientException invalidClient) {
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Il semble y avoir une erreur dans votre session"));
                } else {
                    alerts = new ArrayList<AlertMessage>(); 
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Il semble y avoir une erreur dans votre session"));
                    rm.addFlashAttribute("alerts", alerts);   
                }
                return new ModelAndView("redirect:/disconnect.htm");
            }
        }
        return new ModelAndView("redirect:/disconnect.htm");
    }
}
