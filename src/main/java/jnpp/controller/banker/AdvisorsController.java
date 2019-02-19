package jnpp.controller.banker;

import java.io.IOException;
import java.util.List;
import jnpp.service.dto.AbstractDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import jnpp.service.dto.AddressDTO;
import jnpp.service.dto.IdentityDTO;
import jnpp.service.dto.advisor.AdvisorDTO;
import jnpp.service.dto.clients.LoginDTO;
import jnpp.service.exceptions.duplicates.DuplicateAdvisorException;
import jnpp.service.exceptions.entities.FakeAdvisorException;
import jnpp.service.services.BankerService;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdvisorsController {


    private static final IdentityDTO.Gender DEFAULT_GENDER = IdentityDTO.Gender.MALE;
    private static final AddressDTO DEFAULT_ADDRESS = new AddressDTO(1,
            "Grand rue ", "Poitiers", "France");
    private static final String DEFAULT_EMAIL = "conseiller@jnpp.fr";
    private static final String DEFAULT_PHONE = "0123456789";

    @Autowired
    BankerService bankerService;

    @RequestMapping(value = "banker/get-advisors", method = RequestMethod.GET)
    public ResponseEntity<?> getAdvisors() {
        List<AdvisorDTO> advisors = bankerService.getAdvisors();
        String json = AbstractDTO.toJson(advisors);
        return new ResponseEntity(json, HttpStatus.OK);
    }
    
    @RequestMapping(value = "banker/get-advisor-clients", method = RequestMethod.GET)
    public ResponseEntity<?> getAdvisorClient(
            @RequestParam(value = "firstname") String firstname,
            @RequestParam(value = "lastname") String lastname) {
        try {
            List<LoginDTO> clients = bankerService.getAdvisorLogins(firstname,
                    lastname);
            String json = AbstractDTO.toJson(clients);
            return new ResponseEntity(json, HttpStatus.OK);
        } catch (FakeAdvisorException e) {}
        return new ResponseEntity("", HttpStatus.BAD_REQUEST);
    }
       
    @RequestMapping(value = "banker/add-advisor", method = RequestMethod.POST)
    public ResponseEntity<?> post(@RequestBody String string) {
        try {
            JsonNode data = (new ObjectMapper()).readTree(string);
            String firstname = data.get("firstname").asText();
            String lastname = data.get("lastname").asText();
            AdvisorDTO advisor = bankerService.addAdvisor(DEFAULT_GENDER, 
                    firstname, lastname, DEFAULT_EMAIL, DEFAULT_PHONE, 
                    DEFAULT_ADDRESS.getNumber(), DEFAULT_ADDRESS.getStreet(), 
                    DEFAULT_ADDRESS.getCity(), DEFAULT_ADDRESS.getState());
            String json = advisor.toJson();
            return new ResponseEntity(json, HttpStatus.OK);
        } catch (IOException e) {} 
        catch (DuplicateAdvisorException e) {}
        return new ResponseEntity("", HttpStatus.BAD_REQUEST);
    }

}
