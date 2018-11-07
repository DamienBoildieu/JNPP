package jnpp.controller.banker;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import jnpp.service.dto.advisor.AdvisorDTO;
import jnpp.service.dto.advisor.MessageDTO;
import jnpp.service.dto.clients.LoginDTO;
import jnpp.service.exceptions.advisors.NoAdvisorException;
import jnpp.service.exceptions.entities.FakeClientException;
import jnpp.service.services.AdvisorService;
import jnpp.service.services.BankerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Contrôleur des messages d'un conseiller
 */
@Controller
public class AdvisorClientMessagesController {

    /**
     * Le service du conseiller
     */
    @Autowired
    AdvisorService advisorService;
    /**
     * Le service banquier
     */
    @Autowired
    BankerService bankerService;

    /**
     * Vue sur les messages d'un conseiller
     *
     * @param request la requête
     * @return la vue des messages d'un conseiller
     */
    @RequestMapping(value = "banquier/conseiller/client/messages", method = RequestMethod.GET)
    protected ModelAndView advisorClientMessagesGet(HttpServletRequest request) {
        String login = request.getParameter("login");
        if (login == null || login.length() == 0) {
            return new ModelAndView("redirect:/conseillers.htm");
        }
        try {
            AdvisorDTO advisor = advisorService.getAdvisor(login);
            LoginDTO client = bankerService.getLogin(login);
            List<MessageDTO> messages = advisorService.receiveMessages(login);
            ModelAndView mv = new ModelAndView("banker/advisor_client_message_board");
            mv.addObject("advisor", advisor);
            mv.addObject("client", client);
            mv.addObject("messages", messages);
            return mv;
        } catch (FakeClientException e) {
        } catch (IllegalArgumentException e) {
        }
        return new ModelAndView("redirect:/conseillers.htm");
    }

    /**
     * Requête d'envoie d'un message par un conseiller
     *
     * @param request la requête
     * @return la vue des messages d'un conseiller
     */
    @RequestMapping(value = "banquier/conseiller/client/messages", method = RequestMethod.POST)
    protected ModelAndView advisorClientMessagesPost(HttpServletRequest request) {
        String login = request.getParameter("login");
        String content = request.getParameter("content");
        if (login == null || login.length() == 0 || content == null
                || content.length() == 0) {
            return new ModelAndView("redirect:/banquier/conseiller/client/messages.htm?login=" + login);
        }
        try {
            bankerService.sendMessage(login, content);
            return new ModelAndView("redirect:/banquier/conseiller/client/messages.htm?login=" + login);
        } catch (FakeClientException ex) {
        } catch (NoAdvisorException ex) {
        }
        return new ModelAndView("redirect:/banquier/conseillers.htm");
    }

}
