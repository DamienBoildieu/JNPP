package jnpp.controller.banker;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jnpp.service.dto.accounts.CurrencyDTO;
import jnpp.service.dto.accounts.ShareDTO;
import jnpp.service.exceptions.accounts.NoCurrentAccountException;
import jnpp.service.exceptions.entities.FakeAccountException;
import jnpp.service.exceptions.entities.FakeShareException;
import jnpp.service.exceptions.entities.FakeShareTitleException;
import jnpp.service.exceptions.movements.AccountTypeException;
import jnpp.service.exceptions.movements.AmountException;
import jnpp.service.exceptions.movements.DebitAuthorizationException;
import jnpp.service.exceptions.movements.OverdraftException;
import jnpp.service.services.AccountService;
import jnpp.service.services.BankerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MovementsController {

    private static final CurrencyDTO DEFAULT_CURRENCY = CurrencyDTO.EURO;

    @Autowired
    AccountService accountService;

    @Autowired
    BankerService bankerService;

    @RequestMapping(value = "banquier/transactions", method = RequestMethod.GET)
    protected ModelAndView movementsGet(Model model, HttpServletRequest request,
            HttpServletResponse response, RedirectAttributes rm) {
        List<ShareDTO> shares = accountService.getShares();
        ModelAndView mv = new ModelAndView("banker/movements_board");
        mv.addObject("shares", shares);
        return mv;
    }

    @RequestMapping(value = "banquier/transactions/depot", method = RequestMethod.POST)
    protected ModelAndView movementDepositPost(Model model, HttpServletRequest request,
            HttpServletResponse response, RedirectAttributes rm) {
        String rib = request.getParameter("rib");
        String amount = request.getParameter("amount");
        String label = request.getParameter("label");
        if (rib == null || rib.length() == 0 || amount == null || amount.length() == 0) {
            return new ModelAndView("redirect:/banquier/transactions.htm");
        }
        if (label == null) label = "";
        try {
            bankerService.deposit(rib, Double.valueOf(amount), DEFAULT_CURRENCY, label);
        } catch (FakeAccountException ex) {
        } catch (AccountTypeException ex) {
        }
        return new ModelAndView("redirect:/banquier/transactions.htm");
    }

    @RequestMapping(value = "banquier/transactions/transfert_debit", method = RequestMethod.POST)
    protected ModelAndView movementTransfertDebitPost(Model model, HttpServletRequest request,
            HttpServletResponse response, RedirectAttributes rm) {
        String ribFrom = request.getParameter("rib_from");
        String ribTo = request.getParameter("rib_to");
        String amount = request.getParameter(("amount"));
        String transfert = request.getParameter("transfert");
        String debit = request.getParameter("debit");
        String label = request.getParameter("label");
        if (ribFrom == null || ribFrom.length() == 0 || ribTo == null
                || ribTo.length() == 0 || amount == null || amount.length() == 0) {
            return new ModelAndView("redirect:/banquier/transactions.htm");
        }
        if (label == null) label = "";
        if (transfert != null && transfert.length() > 0) {
            try {
                bankerService.transfert(ribFrom, ribTo, Double.valueOf(amount), DEFAULT_CURRENCY, label);
            } catch (FakeAccountException ex) {
            } catch (AccountTypeException ex) {
            } catch (OverdraftException ex) {
            }
        } else if (debit != null && debit.length() > 0) {
            try {
                bankerService.debit(ribFrom, ribTo, Double.valueOf(amount), DEFAULT_CURRENCY, label);
            } catch (FakeAccountException ex) {
            } catch (AccountTypeException ex) {
            } catch (DebitAuthorizationException ex) {
            } catch (OverdraftException ex) {
            }
        }
        return new ModelAndView("redirect:/banquier/transactions.htm");
    }

    @RequestMapping(value = "banquier/transactions/achat_vente", method = RequestMethod.POST)
    protected ModelAndView movementPurchaseSalePost(Model model, HttpServletRequest request,
            HttpServletResponse response, RedirectAttributes rm) {
        String rib = request.getParameter("rib");
        String amount = request.getParameter("amount");
        String share = request.getParameter("share");
        String purchase = request.getParameter("purchase");
        String sale = request.getParameter("sale");
        String label = request.getParameter("label");
        if (rib == null || rib.length() == 0 || amount == null || amount.length() == 0 || 
                share == null || share.length() == 0) {
            return new ModelAndView("redirect:/banquier/transactions.htm");
        }
        if (label == null) label = "";
        if (purchase != null && purchase.length() > 0) {
            try {
                bankerService.purchase(rib, share, Integer.valueOf(amount), label);
            } catch (FakeAccountException ex) {
            } catch (FakeShareException ex) {
            } catch (NoCurrentAccountException ex) {
            } catch (AccountTypeException ex) {
            }
        } else if (sale != null && sale.length() > 0) {
            try {
                bankerService.sale(rib, share, Integer.valueOf(amount), label);
            } catch (FakeAccountException ex) {
            } catch (FakeShareException ex) {
            } catch (NoCurrentAccountException ex) {
            } catch (AccountTypeException ex) {
            } catch (AmountException ex) {
            } catch (FakeShareTitleException ex) {
            }
        }
        return new ModelAndView("redirect:/banquier/transactions.htm");
    }

}
