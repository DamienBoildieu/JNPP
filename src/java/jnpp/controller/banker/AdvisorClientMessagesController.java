package jnpp.controller.banker;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jnpp.service.dto.advisor.AdvisorDTO;
import jnpp.service.dto.advisor.MessageDTO;
import jnpp.service.dto.clients.LoginDTO;
import jnpp.service.exceptions.advisors.NoAdvisorException;
import jnpp.service.exceptions.entities.FakeClientException;
import jnpp.service.services.AdvisorService;
import jnpp.service.services.BankerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AdvisorClientMessagesController {
    
    @Autowired
    AdvisorService advisorService;
    @Autowired
    BankerService bankerService;
    
    @RequestMapping(value = "conseiller/client/messages", method = RequestMethod.GET)
    protected ModelAndView advisorClientMessagesGet(Model model, HttpServletRequest request, 
            HttpServletResponse response, RedirectAttributes rm) {
        String login = request.getParameter("login");
        if (login == null || login.length() == 0)
            return new ModelAndView("redirect:/conseillers.htm");   
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
    
    @RequestMapping(value = "conseiller/client/messages", method = RequestMethod.POST)
    protected ModelAndView advisorClientMessagesPost(Model model, HttpServletRequest request, 
            HttpServletResponse response, RedirectAttributes rm) {
        String login = request.getParameter("login");
        String content = request.getParameter("content");
        if (login == null || login.length() == 0 || content == null 
                || content.length() == 0)
            return new ModelAndView("redirect:/conseiller/client/messages.htm?login=" + login); 
        try {
            bankerService.sendMessage(login, content);
            return new ModelAndView("redirect:/conseiller/client/messages.htm?login=" + login); 
        } catch (FakeClientException ex) {
        } catch (NoAdvisorException ex) {
        }
        return new ModelAndView("redirect:/conseillers.htm"); 
    }
    
}
