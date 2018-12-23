package jnpp.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jnpp.controller.views.JNPPModelAndView;
import jnpp.controller.views.Translator;
import jnpp.controller.views.alerts.AlertEnum;
import jnpp.controller.views.alerts.AlertMessage;
import jnpp.controller.views.info.ViewInfo;
import jnpp.service.dto.accounts.AccountDTO;
import jnpp.service.dto.accounts.CurrencyDTO;
import jnpp.service.dto.accounts.ShareAccountDTO;
import jnpp.service.dto.accounts.ShareDTO;
import jnpp.service.dto.accounts.ShareTitleDTO;
import jnpp.service.dto.clients.ClientDTO;
import jnpp.service.exceptions.accounts.CurrencyException;
import jnpp.service.exceptions.accounts.NoCurrentAccountException;
import jnpp.service.exceptions.accounts.NoShareAccountException;
import jnpp.service.exceptions.entities.FakeAccountException;
import jnpp.service.exceptions.entities.FakeClientException;
import jnpp.service.exceptions.entities.FakeShareException;
import jnpp.service.exceptions.entities.FakeShareTitleException;
import jnpp.service.exceptions.movements.AccountTypeException;
import jnpp.service.exceptions.movements.AmountException;
import jnpp.service.exceptions.movements.DebitAuthorizationException;
import jnpp.service.exceptions.movements.OverdraftException;
import jnpp.service.exceptions.owners.AccountOwnerException;
import jnpp.service.services.AccountService;
import jnpp.service.services.MovementService;
import jnpp.service.services.NotificationService;

/**
 * Le contrôleur des transactions
 */
@Controller
public class MovementController {

    /**
     * La monnaie par défaut
     */
    private static CurrencyDTO DEFAULT_CURRENCY = CurrencyDTO.EURO;
    /**
     * Le service des comptes bancaire
     */
    @Autowired
    private AccountService accountService;
    /**
     * Le service des transactions
     */
    @Autowired
    private MovementService movementService;
    /**
     * Le service des notifications
     */
    @Autowired
    private NotificationService notifService;

    /**
     * Requête vers la vue des transactions
     *
     * @param model   le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @param rm      objet dans lequel on ajoute les informations que l'on veut
     *                voir transiter lors des redirections
     * @return La vue des transactions
     * @throws Exception Exception non controllees.
     */
    @RequestMapping(value = "movement", method = RequestMethod.GET)
    private ModelAndView movement(Model model, HttpServletRequest request,
            RedirectAttributes rm) throws Exception {

        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>) model.asMap()
                .get("alerts");
        if (session == null) {
            session = request.getSession(true);
        }
        if (SessionController.getLanguage(session) != Translator.Language.FR) {
            SessionController.setLanguage(session, Translator.Language.FR);
        }
        if (!SessionController.isConnected(session)) {
            return new ModelAndView("redirect:/index.htm");
        }
        Boolean hasNotif = SessionController.getHasNotif(session);
        if (!hasNotif) {
            try {
                hasNotif = notifService
                        .receiveUnseenNotifications(
                                SessionController.getClient(session).getLogin())
                        .size() > 0;
                SessionController.setHasNotif(session, hasNotif);
            } catch (FakeClientException invalidClient) {
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.ERROR,
                            "Il semble y avoir une erreur dans votre session"));
                } else {
                    alerts = new ArrayList<AlertMessage>();
                    alerts.add(new AlertMessage(AlertEnum.ERROR,
                            "Il semble y avoir une erreur dans votre session"));
                    rm.addFlashAttribute("alerts", alerts);
                }
                return new ModelAndView("redirect:/disconnect.htm");
            }
        }

        ClientDTO client = SessionController.getClient(session);

        List<String> ribMoneyAccount = new ArrayList<String>();
        List<String> ribShareAccount = new ArrayList<String>();

        List<AccountDTO> accounts = accountService
                .getAccounts(client.getLogin());
        for (AccountDTO account : accounts) {
            if (account.getType() == AccountDTO.Type.SHARE) {
                ribShareAccount.add(account.getRib());
            } else {
                ribMoneyAccount.add(account.getRib());
            }
        }

        List<String> sharesToPurchase = new ArrayList<String>();

        List<ShareDTO> shares = accountService.getShares();
        for (ShareDTO share : shares) {
            sharesToPurchase.add(share.getName());
        }

        List<String> sharesToSale = new ArrayList<String>();

        ShareAccountDTO shareAccount = accountService
                .getShareAccount(client.getLogin());
        if (shareAccount != null) {
            for (ShareTitleDTO shareTitle : shareAccount.getShareTitles()) {
                sharesToSale.add(shareTitle.getShare().getName());
            }
        }

        ModelAndView mv = new JNPPModelAndView("movements/movement",
                ViewInfo.createInfo(session, alerts));

        mv.addObject("ribMoneyAccount", ribMoneyAccount);
        mv.addObject("ribShareAccount", ribShareAccount);
        mv.addObject("sharesToPurchase", sharesToPurchase);
        mv.addObject("sharesToSale", sharesToSale);

        return mv;
    }

    /**
     * Requête des transferts
     *
     * @param model   le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @param rm      objet dans lequel on ajoute les informations que l'on veut
     *                voir transiter lors des redirections
     * @return La vue des transactions
     * @throws Exception Exception non controllees.
     */
    @RequestMapping(value = "transfert", method = RequestMethod.POST)
    private ModelAndView transfert(Model model, HttpServletRequest request,
            RedirectAttributes rm) throws Exception {

        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>) model.asMap()
                .get("alerts");
        if (session == null) {
            session = request.getSession(true);
        }
        if (SessionController.getLanguage(session) != Translator.Language.FR) {
            SessionController.setLanguage(session, Translator.Language.FR);
        }
        if (!SessionController.isConnected(session)) {
            return new ModelAndView("redirect:/index.htm");
        }

        ClientDTO client = SessionController.getClient(session);

        String ribFrom = request.getParameter("ribFrom");
        String ribTo = request.getParameter("ribTo");
        Double amount = Double.valueOf(request.getParameter("amount"));
        String label = request.getParameter("label");
        if (label == null) {
            label = "";
        }

        try {
            movementService.transfertMoney(client.getLogin(), ribFrom, ribTo,
                    amount, DEFAULT_CURRENCY, label);
            if (alerts != null) {
                alerts.add(new AlertMessage(AlertEnum.SUCCESS,
                        "Transfert réussi."));
            } else {
                alerts = new ArrayList<AlertMessage>();
                alerts.add(new AlertMessage(AlertEnum.SUCCESS,
                        "Transfert réussi."));
                rm.addFlashAttribute("alerts", alerts);
            }
        } catch (FakeClientException ex) {
            if (alerts != null) {
                alerts.add(new AlertMessage(AlertEnum.ERROR,
                        "Il semble y avoir une erreur dans votre session"));
            } else {
                alerts = new ArrayList<AlertMessage>();
                alerts.add(new AlertMessage(AlertEnum.ERROR,
                        "Il semble y avoir une erreur dans votre session"));
                rm.addFlashAttribute("alerts", alerts);
            }
            return new ModelAndView("redirect:/disconnect.htm");
        } catch (FakeAccountException ex) {

        } catch (AccountOwnerException ex) {
        } catch (AccountTypeException ex) {
            if (alerts != null) {
                alerts.add(new AlertMessage(AlertEnum.ERROR,
                        "Les transferts ne sont pas autorisés sur ce compte."));
            } else {
                alerts = new ArrayList<AlertMessage>();
                alerts.add(new AlertMessage(AlertEnum.ERROR,
                        "Les transferts ne sont pas autorisés sur ce compte."));
                rm.addFlashAttribute("alerts", alerts);
            }
        } catch (CurrencyException ex) {
            if (alerts != null) {
                alerts.add(new AlertMessage(AlertEnum.ERROR,
                        "Ce compte utilise une devise differente."));
            } else {
                alerts = new ArrayList<AlertMessage>();
                alerts.add(new AlertMessage(AlertEnum.ERROR,
                        "Ce compte utilise une devise differente."));
                rm.addFlashAttribute("alerts", alerts);
            }
        } catch (OverdraftException ex) {
            if (alerts != null) {
                alerts.add(new AlertMessage(AlertEnum.ERROR,
                        "Les depassements ne sont pas autorisés sur ce compte."));
            } else {
                alerts = new ArrayList<AlertMessage>();
                alerts.add(new AlertMessage(AlertEnum.ERROR,
                        "Les depassements ne sont pas autorisés sur ce compte."));
                rm.addFlashAttribute("alerts", alerts);
            }
        }

        return new ModelAndView("redirect:/movement.htm");
    }

    /**
     * Requête des débits
     *
     * @param model   le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @param rm      objet dans lequel on ajoute les informations que l'on veut
     *                voir transiter lors des redirections
     * @return La vue des transactions
     * @throws Exception Exception non controllees.
     */
    @RequestMapping(value = "debit", method = RequestMethod.POST)
    private ModelAndView debit(Model model, HttpServletRequest request,
            RedirectAttributes rm) throws Exception {

        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>) model.asMap()
                .get("alerts");
        if (session == null) {
            session = request.getSession(true);
        }
        if (SessionController.getLanguage(session) != Translator.Language.FR) {
            SessionController.setLanguage(session, Translator.Language.FR);
        }
        if (!SessionController.isConnected(session)) {
            return new ModelAndView("redirect:/index.htm");
        }

        ClientDTO client = SessionController.getClient(session);

        String ribFrom = request.getParameter("ribFrom");
        String ribTo = request.getParameter("ribTo");
        Double amount = Double.valueOf(request.getParameter("amount"));
        String label = request.getParameter("label");
        if (label == null) {
            label = "";
        }

        try {
            movementService.debitMoney(client.getLogin(), ribFrom, ribTo,
                    amount, DEFAULT_CURRENCY, label);
            if (alerts != null) {
                alerts.add(
                        new AlertMessage(AlertEnum.SUCCESS, "Debit réussi."));
            } else {
                alerts = new ArrayList<AlertMessage>();
                alerts.add(
                        new AlertMessage(AlertEnum.SUCCESS, "Debit réussi."));
                rm.addFlashAttribute("alerts", alerts);
            }
        } catch (FakeClientException ex) {
            if (alerts != null) {
                alerts.add(new AlertMessage(AlertEnum.ERROR,
                        "Il semble y avoir une erreur dans votre session"));
            } else {
                alerts = new ArrayList<AlertMessage>();
                alerts.add(new AlertMessage(AlertEnum.ERROR,
                        "Il semble y avoir une erreur dans votre session"));
                rm.addFlashAttribute("alerts", alerts);
            }
            return new ModelAndView("redirect:/disconnect.htm");
        } catch (FakeAccountException ex) {

        } catch (AccountOwnerException ex) {
        } catch (AccountTypeException ex) {
            if (alerts != null) {
                alerts.add(new AlertMessage(AlertEnum.ERROR,
                        "Le debits ne sont pas autorisés sur ce compte."));
            } else {
                alerts = new ArrayList<AlertMessage>();
                alerts.add(new AlertMessage(AlertEnum.ERROR,
                        "Les debits ne sont pas autorisés sur ce compte."));
                rm.addFlashAttribute("alerts", alerts);
            }
        } catch (CurrencyException ex) {
            if (alerts != null) {
                alerts.add(new AlertMessage(AlertEnum.ERROR,
                        "Ce compte utilise une devise differente."));
            } else {
                alerts = new ArrayList<AlertMessage>();
                alerts.add(new AlertMessage(AlertEnum.ERROR,
                        "Ce compte utilise une devise differente."));
                rm.addFlashAttribute("alerts", alerts);
            }
        } catch (OverdraftException ex) {
            if (alerts != null) {
                alerts.add(new AlertMessage(AlertEnum.ERROR,
                        "Les depassements ne sont pas autorisés sur ce compte."));
            } else {
                alerts = new ArrayList<AlertMessage>();
                alerts.add(new AlertMessage(AlertEnum.ERROR,
                        "Les depassements ne sont pas autorisés sur ce compte."));
                rm.addFlashAttribute("alerts", alerts);
            }
        } catch (DebitAuthorizationException ex) {
            if (alerts != null) {
                alerts.add(new AlertMessage(AlertEnum.ERROR,
                        "Vous n'etes pas autorise a debiter ce compte."));
            } else {
                alerts = new ArrayList<AlertMessage>();
                alerts.add(new AlertMessage(AlertEnum.ERROR,
                        "Vous n'etes pas autorise a debiter ce compte."));
                rm.addFlashAttribute("alerts", alerts);
            }
        }

        return new ModelAndView("redirect:/movement.htm");
    }

    /**
     * Requête des achats d'actions
     *
     * @param model   le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @param rm      objet dans lequel on ajoute les informations que l'on veut
     *                voir transiter lors des redirections
     * @return La vue des transactions
     * @throws Exception Exception non controllees.
     */
    @RequestMapping(value = "purchase", method = RequestMethod.POST)
    private ModelAndView purchase(Model model, HttpServletRequest request,
            RedirectAttributes rm) throws Exception {

        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>) model.asMap()
                .get("alerts");
        if (session == null) {
            session = request.getSession(true);
        }
        if (SessionController.getLanguage(session) != Translator.Language.FR) {
            SessionController.setLanguage(session, Translator.Language.FR);
        }
        if (!SessionController.isConnected(session)) {
            return new ModelAndView("redirect:/index.htm");
        }
        String amountStr = request.getParameter("amount");
        String share = request.getParameter("share");
        String label = request.getParameter("label");
        if (label == null) {
            label = "";
        }
        try {
            movementService.purchaseShareTitles(
                    SessionController.getClient(session).getLogin(), share,
                    Integer.parseInt(amountStr), label);
        } catch (FakeClientException ex) {
            if (alerts != null) {
                alerts.add(new AlertMessage(AlertEnum.ERROR,
                        "Il semble y avoir une erreur dans votre session"));
                rm.addFlashAttribute("alerts", alerts);
            } else {
                alerts = new ArrayList<AlertMessage>();
                alerts.add(new AlertMessage(AlertEnum.ERROR,
                        "Il semble y avoir une erreur dans votre session"));
            }
            return new ModelAndView("redirect:/disconnect.htm");
        } catch (NoCurrentAccountException ex) {
            if (alerts != null) {
                alerts.add(new AlertMessage(AlertEnum.ERROR,
                        "Vous devez posséder un compte courant pour effectuer cette action"));
            } else {
                alerts = new ArrayList<AlertMessage>();
                alerts.add(new AlertMessage(AlertEnum.ERROR,
                        "Vous devez posséder un compte courant pour effectuer cette action"));
                rm.addFlashAttribute("alerts", alerts);
            }
        } catch (NoShareAccountException ex) {
            if (alerts != null) {
                alerts.add(new AlertMessage(AlertEnum.ERROR,
                        "Vous devez posséder un compte titres pour effectuer cette action"));
            } else {
                alerts = new ArrayList<AlertMessage>();
                alerts.add(new AlertMessage(AlertEnum.ERROR,
                        "Vous devez posséder un compte titres pour effectuer cette action"));
                rm.addFlashAttribute("alerts", alerts);
            }
        } catch (FakeShareException ex) {
            if (alerts != null) {
                alerts.add(new AlertMessage(AlertEnum.ERROR,
                        "Cette action de bourse n'existe pas"));
            } else {
                alerts = new ArrayList<AlertMessage>();
                alerts.add(new AlertMessage(AlertEnum.ERROR,
                        "Cette action de bourse n'existe pas"));
                rm.addFlashAttribute("alerts", alerts);
            }
        }
        return new ModelAndView("redirect:/movement.htm");
    }

    /**
     * Requête des ventes d'actions
     *
     * @param model   le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @param rm      objet dans lequel on ajoute les informations que l'on veut
     *                voir transiter lors des redirections
     * @return La vue des transactions
     * @throws Exception Exception non controllees.
     */
    @RequestMapping(value = "sale", method = RequestMethod.POST)
    private ModelAndView sale(Model model, HttpServletRequest request,
            RedirectAttributes rm) throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>) model.asMap()
                .get("alerts");
        if (session == null) {
            session = request.getSession(true);
        }
        if (SessionController.getLanguage(session) != Translator.Language.FR) {
            SessionController.setLanguage(session, Translator.Language.FR);
        }
        if (!SessionController.isConnected(session)) {
            return new ModelAndView("redirect:/index.htm");
        }
        String amountStr = request.getParameter("amount");
        String share = request.getParameter("share");
        String label = request.getParameter("label");
        if (label == null) {
            label = "";
        }
        try {
            movementService.saleShareTitles(
                    SessionController.getClient(session).getLogin(), share,
                    Integer.parseInt(amountStr), label);
        } catch (FakeClientException ex) {
            if (alerts != null) {
                alerts.add(new AlertMessage(AlertEnum.ERROR,
                        "Il semble y avoir une erreur dans votre session"));
            } else {
                alerts = new ArrayList<AlertMessage>();
                alerts.add(new AlertMessage(AlertEnum.ERROR,
                        "Il semble y avoir une erreur dans votre session"));
                rm.addFlashAttribute("alerts", alerts);
            }
            return new ModelAndView("redirect:/disconnect.htm");
        } catch (NoCurrentAccountException ex) {
            if (alerts != null) {
                alerts.add(new AlertMessage(AlertEnum.ERROR,
                        "Vous devez posséder un compte courant pour effectuer cette action"));
            } else {
                alerts = new ArrayList<AlertMessage>();
                alerts.add(new AlertMessage(AlertEnum.ERROR,
                        "Vous devez posséder un compte courant pour effectuer cette action"));
                rm.addFlashAttribute("alerts", alerts);
            }
        } catch (NoShareAccountException ex) {
            if (alerts != null) {
                alerts.add(new AlertMessage(AlertEnum.ERROR,
                        "Vous devez posséder un compte titres pour effectuer cette action"));
            } else {
                alerts = new ArrayList<AlertMessage>();
                alerts.add(new AlertMessage(AlertEnum.ERROR,
                        "Vous devez posséder un compte titres pour effectuer cette action"));
                rm.addFlashAttribute("alerts", alerts);
            }
        } catch (FakeShareTitleException ex) {
            if (alerts != null) {
                alerts.add(new AlertMessage(AlertEnum.ERROR,
                        "Vous ne possédez pas ces actions"));
            } else {
                alerts = new ArrayList<AlertMessage>();
                alerts.add(new AlertMessage(AlertEnum.ERROR,
                        "Vous ne possédez pas ces actions"));
                rm.addFlashAttribute("alerts", alerts);
            }
        } catch (AmountException ex) {
            if (alerts != null) {
                alerts.add(new AlertMessage(AlertEnum.ERROR,
                        "Vous ne possédez pas assez d'actions"));
            } else {
                alerts = new ArrayList<AlertMessage>();
                alerts.add(new AlertMessage(AlertEnum.ERROR,
                        "Vous ne possédez pas assez d'actions"));
                rm.addFlashAttribute("alerts", alerts);
            }
        }
        return new ModelAndView("redirect:/movement.htm");
    }

}
