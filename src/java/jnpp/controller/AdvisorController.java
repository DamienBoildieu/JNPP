package jnpp.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import jnpp.controller.views.Translator;
import jnpp.controller.views.alerts.AlertEnum;
import jnpp.controller.views.alerts.AlertMessage;
import jnpp.service.exceptions.advisors.AvailableException;
import jnpp.service.exceptions.advisors.DateException;
import jnpp.service.exceptions.advisors.NoAdvisorException;
import jnpp.service.exceptions.entities.FakeClientException;
import jnpp.service.exceptions.owners.AppointmentOwnerException;
import jnpp.service.services.AdvisorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    /**
     * Requête du formulaire d'envoie de message
     *
     * @param model le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @param rm objet dans lequel on ajoute les informations que l'on veut voir
     * transiter lors des redirections
     * @return Une redirection vers la vue de message
     * @throws Exception Exception non controllees.
     */
    @RequestMapping(value = "message", method = RequestMethod.POST)
    protected ModelAndView sendMessage(Model model, HttpServletRequest request, RedirectAttributes rm) throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>) model.asMap().get("alerts");
        if (session == null) {
            session = request.getSession(true);
        }
        if (SessionController.getLanguage(session) != Translator.Language.FR) {
            SessionController.setLanguage(session, Translator.Language.FR);
        }
        if (SessionController.isConnected(session)) {
            try {
                String message = request.getParameter("content");
                advisorService.sendMessage(SessionController.getClient(session).getLogin(), message);
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.SUCCESS, "Le message a bien été envoyé"));
                } else {
                    alerts = new ArrayList<AlertMessage>();
                    alerts.add(new AlertMessage(AlertEnum.SUCCESS, "Le message a bien été envoyé"));
                    rm.addFlashAttribute("alerts", alerts);
                }
            } catch (FakeClientException clientException) {
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Il semble y avoir une erreur dans votre session"));
                } else {
                    alerts = new ArrayList<AlertMessage>();
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Il semble y avoir une erreur dans votre session"));
                    rm.addFlashAttribute("alerts", alerts);
                }
                return new ModelAndView("redirect:/disconnect.htm");
            } catch (NoAdvisorException e) {
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.SUCCESS, "Nous n'avez pas de conseiller."));
                } else {
                    alerts = new ArrayList<AlertMessage>();
                    alerts.add(new AlertMessage(AlertEnum.SUCCESS, "Nous n'avez pas de conseiller."));
                    rm.addFlashAttribute("alerts", alerts);
                }
            } finally {
                return new ModelAndView("redirect:/message.htm");
            }
        }
        return new ModelAndView("redirect:/index.htm"); //ne devrait pas arriver
    }

    /**
     * Requête du formulaire de prise de rendez-vous
     *
     * @param model le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @param rm objet dans lequel on ajoute les informations que l'on veut voir
     * transiter lors des redirections
     * @return Une redirection vers la vue de rendez-vous
     * @throws Exception Exception non controllees.
     */
    @RequestMapping(value = "makeappoint", method = RequestMethod.POST)
    protected ModelAndView makeAppoint(Model model, HttpServletRequest request, RedirectAttributes rm) throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>) model.asMap().get("alerts");
        if (session == null) {
            session = request.getSession(true);
        }
        if (SessionController.getLanguage(session) != Translator.Language.FR) {
            SessionController.setLanguage(session, Translator.Language.FR);
        }
        if (SessionController.isConnected(session)) {
            try {
                String date = request.getParameter("date");
                String time = request.getParameter("time");
                String completeDate = date + " " + time;
                Date appointDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(completeDate);
                advisorService.makeAppointment(SessionController.getClient(session).getLogin(), appointDate);
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.SUCCESS, "Le message a bien été envoyé"));
                } else {
                    alerts = new ArrayList<AlertMessage>();
                    alerts.add(new AlertMessage(AlertEnum.SUCCESS, "Le message a bien été envoyé"));
                    rm.addFlashAttribute("alerts", alerts);
                }
            } catch (DateException dateException) {
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "La date indiquée n'est pas valable"));
                } else {
                    alerts = new ArrayList<AlertMessage>();
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "La date indiquée n'est pas valable"));
                    rm.addFlashAttribute("alerts", alerts);
                }
            } catch (AvailableException availableException) {
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Votre conseiller n'est pas disponible sur ce créneau"));
                } else {
                    alerts = new ArrayList<AlertMessage>();
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Votre conseiller n'est pas disponible sur ce créneau"));
                    rm.addFlashAttribute("alerts", alerts);
                }
            } catch (FakeClientException clientException) {
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Il semble y avoir une erreur dans votre session"));
                } else {
                    alerts = new ArrayList<AlertMessage>();
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Il semble y avoir une erreur dans votre session"));
                    rm.addFlashAttribute("alerts", alerts);
                }
                return new ModelAndView("redirect:/disconnect.htm");
            } catch (NoAdvisorException e) {
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.SUCCESS, "Nous n'avez pas de conseiller."));
                } else {
                    alerts = new ArrayList<AlertMessage>();
                    alerts.add(new AlertMessage(AlertEnum.SUCCESS, "Nous n'avez pas de conseiller."));
                    rm.addFlashAttribute("alerts", alerts);
                }
            } finally {
                return new ModelAndView("redirect:/appoint.htm");
            }
        }
        return new ModelAndView("redirect:/index.htm"); //ne devrait pas arriver
    }

    /**
     * Requête du formulaire d'annulation de rendez-vous
     *
     * @param model le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @param rm objet dans lequel on ajoute les informations que l'on veut voir
     * transiter lors des redirections
     * @return Une redirection vers la vue des rendez-vous
     * @throws Exception Exception non controllees.
     */
    @RequestMapping(value = "cancelappoint", method = RequestMethod.POST)
    protected ModelAndView cancelAppoint(Model model, HttpServletRequest request, RedirectAttributes rm) throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>) model.asMap().get("alerts");
        if (session == null) {
            session = request.getSession(true);
        }
        if (SessionController.getLanguage(session) != Translator.Language.FR) {
            SessionController.setLanguage(session, Translator.Language.FR);
        }
        if (SessionController.isConnected(session)) {
            try {
                String idStr = request.getParameter("id");
                advisorService.cancelAppoint(SessionController.getClient(session).getLogin(), Long.parseLong(idStr));
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.SUCCESS, "Le rendez-vous a été annulé"));
                } else {
                    alerts = new ArrayList<AlertMessage>();
                    alerts.add(new AlertMessage(AlertEnum.SUCCESS, "Le rendez-vous a été annulé"));
                    rm.addFlashAttribute("alerts", alerts);
                }
            } catch (DateException dateException) {
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "La date indiquée n'est pas valable"));
                } else {
                    alerts = new ArrayList<AlertMessage>();
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "La date indiquée n'est pas valable"));
                    rm.addFlashAttribute("alerts", alerts);
                }
            } catch (AppointmentOwnerException ownerException) {
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "La date indiquée n'est pas valable"));
                } else {
                    alerts = new ArrayList<AlertMessage>();
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "La date indiquée n'est pas valable"));
                    rm.addFlashAttribute("alerts", alerts);
                }
            } catch (FakeClientException clientException) {
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Ce rendez-vous n'est pas le votre"));
                } else {
                    alerts = new ArrayList<AlertMessage>();
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Ce rendez-vous n'est pas le votre"));
                    rm.addFlashAttribute("alerts", alerts);
                }
                return new ModelAndView("redirect:/disconnect.htm");
            } finally {
                return new ModelAndView("redirect:/appoint.htm");
            }
        }
        return new ModelAndView("redirect:/index.htm"); //ne devrait pas arriver
    }
}
