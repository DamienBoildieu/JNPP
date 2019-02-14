package jnpp.controller.banker;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import jnpp.service.dto.AbstractDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import jnpp.service.dto.AddressDTO;
import jnpp.service.dto.IdentityDTO;
import jnpp.service.dto.accounts.AccountDTO;
import jnpp.service.dto.accounts.ShareDTO;
import jnpp.service.dto.advisor.AdvisorDTO;
import jnpp.service.exceptions.duplicates.DuplicateAdvisorException;
import jnpp.service.services.BankerService;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

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

    private static final String DEFAULT_EMAIL = "conseiller@jnpp.fr";
    private static final String DEFAULT_PHONE = "0123456789";

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
    @RequestMapping(value = "banker/advisors", method = RequestMethod.GET)
    protected ResponseEntity<?> get() throws IOException {
        List<AdvisorDTO> advisors = bankerService.getAdvisors();
        String json = AbstractDTO.toJson(advisors);
        return new ResponseEntity(json, HttpStatus.OK);
    }
    
    /**
     * Ajout d'un conseiller
     *
     * @param request la requête
     * @return la vue des conseillers
     */    
    @RequestMapping(value = "banker/advisors", method = RequestMethod.POST)
    protected ResponseEntity<?> post(@RequestBody String string) 
            throws Exception {
        JsonNode data = (new ObjectMapper()).readTree(string);
        String firstname = data.get("firstname").asText();
        String lastname = data.get("lastname").asText();
        try {
            AdvisorDTO advisor = bankerService.addAdvisor(DEFAULT_GENDER, 
                    firstname, lastname, DEFAULT_EMAIL, DEFAULT_PHONE, 
                    DEFAULT_ADDRESS.getNumber(), DEFAULT_ADDRESS.getStreet(), 
                    DEFAULT_ADDRESS.getCity(), DEFAULT_ADDRESS.getState());
            String json = advisor.toJson();
            return new ResponseEntity(json, HttpStatus.OK);
        } catch (Exception e) {}
        return new ResponseEntity("Bad arguments", HttpStatus.BAD_REQUEST);
    }

}
