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
public class OrdersController {

    @Autowired
    BankerService bankerService;

    @RequestMapping(value = "banker/get-orders", method = RequestMethod.GET)
    public ResponseEntity<?> getOrders() throws IOException {
        List<PaymentMeanDTO> purchases = bankerService.getPaymentMeans();
        String json = AbstractDTO.toJson(purchases);
        return new ResponseEntity(json, HttpStatus.OK);
    }

    @RequestMapping(value = "banker/upgrade-order", method = RequestMethod.POST)
    public ResponseEntity<?> upgradeOrder(@RequestBody String string) {
        try {
            JsonNode data = (new ObjectMapper()).readTree(string);
            String id = data.get("id").asText();
            PaymentMeanDTO purchase = bankerService.upgradePaymentMean(id);
            String json = purchase.toJson();
            return new ResponseEntity(json, HttpStatus.OK);
        } catch (FakePaymentMeanException e) {} 
        catch (IOException ex) {}
        return new ResponseEntity("", HttpStatus.BAD_REQUEST);
    }

}
