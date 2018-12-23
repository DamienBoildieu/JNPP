package jnpp.controller.banker;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import jnpp.service.dto.AddressDTO;
import jnpp.service.dto.IdentityDTO;
import jnpp.service.dto.advisor.AdvisorDTO;
import jnpp.service.exceptions.duplicates.DuplicateAdvisorException;
import jnpp.service.services.BankerService;

/**
 * Contrôleur des conseillers
 */
@Controller
public class AdvisorsController {

    /**
     * Sexe par défaut d'un conseiller
     */
    private static final IdentityDTO.Gender DEFAULT_GENDER = IdentityDTO.Gender.MALE;
    /**
     * Adresse par défaut d'un conseiller
     */
    private static final AddressDTO DEFAULT_ADDRESS = new AddressDTO(1,
            "Grand rue ", "Poitiers", "France");
    /**
     * Conseiller par défaut
     */
    private static final AdvisorDTO DEFAULT_ADVISOR = new AdvisorDTO(
            new IdentityDTO(DEFAULT_GENDER, "", ""), "conseiller@jnpp.fr",
            "0123456789", DEFAULT_ADDRESS);

    /**
     * Service banquier
     */
    @Autowired
    BankerService bankerService;

    /**
     * Vue sur la liste des conseillers et le formulaire de création de
     * conseiller
     *
     * @return la vue de la liste des conseilelrs
     */
    @RequestMapping(value = "banquier/conseillers", method = RequestMethod.GET)
    protected ModelAndView advisorsGet() {
        List<AdvisorDTO> advisors = bankerService.getAdvisors();
        ModelAndView mv = new ModelAndView("banker/advisors_board");
        mv.addObject("advisors", advisors);
        mv.addObject("default_advisor", DEFAULT_ADVISOR);
        return mv;
    }

    /**
     * Ajout d'un conseiller
     *
     * @param request la requête
     * @return la vue des conseillers
     */
    @RequestMapping(value = "banquier/conseillers", method = RequestMethod.POST)
    protected ModelAndView advisorsPOST(HttpServletRequest request) {
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String officeAdress = request.getParameter("office_address");
        if (firstname != null && firstname.length() > 0 && lastname != null
                && lastname.length() > 0 && email != null && email.length() > 0
                && phone != null && phone.length() > 0 && officeAdress != null
                && officeAdress.length() > 0) {
            try {
                AddressDTO address = parseAddress(officeAdress);
                bankerService.addAdvisor(DEFAULT_GENDER, firstname, lastname,
                        email, phone, address.getNumber(), address.getStreet(),
                        address.getCity(), address.getState());
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (DuplicateAdvisorException e) {
                e.printStackTrace();
            }
        }
        return new ModelAndView("redirect:/banquier/conseillers.htm");
    }

    /**
     * Parse une adresse
     *
     * @param address l'adresse par défaut
     * @return l'adresse par défaut
     */
    private static AddressDTO parseAddress(String address) {
        return DEFAULT_ADDRESS;
    }

}
