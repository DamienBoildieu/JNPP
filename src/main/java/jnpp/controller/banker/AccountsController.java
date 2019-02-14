package jnpp.controller.banker;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import jnpp.service.dto.AbstractDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jnpp.service.dto.accounts.AccountDTO;
import jnpp.service.services.BankerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Contrôleur des comptes de la banque
 */
@Controller
public class AccountsController {

    /**
     * Le service des banquiers
     */
    @Autowired
    BankerService bankerService;

    /**
     * Vue sur les comptes ouvert dans la banque
     *
     * @return La vue des comptes
     */
    @RequestMapping(value = "banker/accounts", method = RequestMethod.GET)
    protected ResponseEntity<?> get() throws IOException {
        List<AccountDTO> accounts = bankerService.getAccounts();
        String json = AbstractDTO.toJson(accounts);
        return new ResponseEntity(json, HttpStatus.OK);
    }

}
