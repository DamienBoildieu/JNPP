package jnpp.controller.banker;

import java.util.List;
import jnpp.service.dto.AbstractDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import jnpp.service.dto.accounts.AccountDTO;
import jnpp.service.services.BankerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Controller
public class AccountsController {

    @Autowired
    BankerService bankerService;

    @RequestMapping(value = "banker/get-accounts", method = RequestMethod.GET)
    protected ResponseEntity<?> getAccounts() {
        List<AccountDTO> accounts = bankerService.getAccounts();
        String json = AbstractDTO.toJson(accounts);
        return new ResponseEntity(json, HttpStatus.OK);
    }

}
