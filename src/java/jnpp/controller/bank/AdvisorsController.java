package jnpp.controller.bank;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jnpp.service.dto.AddressDTO;
import jnpp.service.dto.IdentityDTO;
import jnpp.service.dto.advisor.AdvisorDTO;
import jnpp.service.dto.clients.LoginDTO;
import jnpp.service.exceptions.duplicates.DuplicateAdvisorException;
import jnpp.service.exceptions.duplicates.DuplicateShareException;
import jnpp.service.services.BankerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AdvisorsController {
    
    private static final IdentityDTO.Gender DEFAULT_GENDER = IdentityDTO.Gender.MALE;
    private static final AddressDTO DEFAULT_ADDRESS = 
            new AddressDTO(1, "Grand rue ", "Poitiers", "France");
    private static final AdvisorDTO DEFAULT_ADVISOR = new AdvisorDTO(
            new IdentityDTO(DEFAULT_GENDER, "", ""), "conseiller@jnpp.fr", 
            "0123456789", DEFAULT_ADDRESS);
    
    @Autowired
    BankerService bankerService;
    
    @RequestMapping(value = "conseillers", method = RequestMethod.GET)
    protected ModelAndView advisorsGet(Model model, HttpServletRequest request, 
            HttpServletResponse response, RedirectAttributes rm) {
        List<AdvisorDTO> advisors = bankerService.getAdvisors();
        ModelAndView mv = new ModelAndView("banker/advisors_board");
        mv.addObject("advisors", advisors);
        mv.addObject("default_advisor", DEFAULT_ADVISOR);
        return mv;            
    }

    @RequestMapping(value = "conseillers", method = RequestMethod.POST)
    protected ModelAndView advisorsPOST(Model model, HttpServletRequest request, 
            HttpServletResponse response, RedirectAttributes rm) {
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String officeAdress = request.getParameter("office_address");
        if (firstname != null && firstname.length() > 0 && lastname != null 
                && lastname.length() > 0 && email != null && email.length() > 0 
                && phone != null && phone.length() > 0 && officeAdress != null 
                && officeAdress.length() > 0)
            try {
                AddressDTO address = parseAddress(officeAdress);
                bankerService.addAdvisor(DEFAULT_GENDER, firstname, lastname, 
                        email, phone, address.getNumber(), address.getStreet(), 
                        address.getCity(), address.getState());
            } catch (IllegalArgumentException e) {e.printStackTrace();
            } catch (DuplicateAdvisorException e) {e.printStackTrace();}
        return new ModelAndView("redirect:/conseillers.htm");       
    }
    
    private static AddressDTO parseAddress(String address) {
        return DEFAULT_ADDRESS;
    }
    
}
