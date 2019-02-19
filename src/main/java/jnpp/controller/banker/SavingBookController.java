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

import jnpp.service.dto.accounts.SavingBookDTO;
import jnpp.service.dto.accounts.ShareDTO;
import jnpp.service.exceptions.duplicates.DuplicateSavingbookException;
import jnpp.service.services.AccountService;
import jnpp.service.services.BankerService;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Contrôleur de la vue de création de livrets
 */
@Controller
public class SavingBookController {

    /**
     * Service des comptes bancaire
     */
    @Autowired
    AccountService accountService;
    /**
     * Service banquier
     */
    @Autowired
    BankerService bankerService;

    /**
     * Vue sur le formulaire de création de livrets
     *
     * @return Vue.
     * @throws Exception Exception non controllees.
     */    
    @RequestMapping(value = "banker/books", method = RequestMethod.GET)
    protected ResponseEntity<?> get() throws IOException {
        List<SavingBookDTO> savingbooks = accountService.getSavingBooks();
        String json = AbstractDTO.toJson(savingbooks);
        return new ResponseEntity(json, HttpStatus.OK);   
    }

    /**
     * Création d'un livret
     *
     * @param request la requête
     * @return Le formulaire de créations de livrets
     * @throws Exception Exception non controllees.
     */   
    @RequestMapping(value = "banker/books", method = RequestMethod.POST)
    protected ResponseEntity<?> post(@RequestBody String string) 
            throws IOException {
        JsonNode data = (new ObjectMapper()).readTree(string);
        String name = data.get("name").asText();
        Double moneyRate = data.get("moneyRate").asDouble();
        Double timeRate = data.get("timeRate").asDouble();
        try {
            SavingBookDTO book = bankerService.addSavingbook(name, moneyRate,
                    timeRate);
            String json = book.toJson();
            return new ResponseEntity(json, HttpStatus.OK);
        } catch (Exception e) {}
        return new ResponseEntity("Bad arguments", HttpStatus.BAD_REQUEST);
    }

}
