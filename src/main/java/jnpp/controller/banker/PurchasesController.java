package jnpp.controller.banker;

import java.io.IOException;
import java.util.List;
import jnpp.service.dto.AbstractDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import jnpp.service.dto.paymentmeans.PaymentMeanDTO;
import jnpp.service.exceptions.entities.FakePaymentMeanException;
import jnpp.service.services.BankerService;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class PurchasesController {

    @Autowired
    BankerService bankerService;

    @RequestMapping(value = "banker/get-purchases", method = RequestMethod.GET)
    protected ResponseEntity<?> getPurchases() throws IOException {
        List<PaymentMeanDTO> purchases = bankerService.getPaymentMeans();
        String json = AbstractDTO.toJson(purchases);
        return new ResponseEntity(json, HttpStatus.OK);
    }

    @RequestMapping(value = "banker/upgrade-purchase", method = RequestMethod.POST)
    protected ResponseEntity<?> upgradePurchase(@RequestBody String string) 
            throws IOException {
        JsonNode data = (new ObjectMapper()).readTree(string);
        String id = data.get("id").asText();
        try {
            PaymentMeanDTO purchase = bankerService.upgradePaymentMean(id);
            String json = purchase.toJson();
            return new ResponseEntity(json, HttpStatus.OK);
        } catch (FakePaymentMeanException e) {}
        return new ResponseEntity("Bad arguments", HttpStatus.BAD_REQUEST);
    }

}
