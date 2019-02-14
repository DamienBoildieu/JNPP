package jnpp.controller.client;

import java.util.ArrayList;
import java.util.Iterator;
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

import jnpp.controller.client.views.JNPPModelAndView;
import jnpp.controller.client.views.MovementView;
import jnpp.controller.client.views.Translator;
import jnpp.controller.client.views.alerts.AlertEnum;
import jnpp.controller.client.views.alerts.AlertMessage;
import jnpp.controller.client.views.info.ViewInfo;
import jnpp.service.dto.IdentityDTO;
import jnpp.service.dto.accounts.AccountDTO;
import jnpp.service.dto.accounts.DebitAuthorizationDTO;
import jnpp.service.dto.accounts.SavingBookDTO;
import jnpp.service.dto.accounts.ShareAccountDTO;
import jnpp.service.dto.clients.ClientDTO;
import jnpp.service.dto.movements.MovementDTO;
import jnpp.service.dto.paymentmeans.BankCardDTO;
import jnpp.service.dto.paymentmeans.CheckbookDTO;
import jnpp.service.exceptions.entities.FakeClientException;
import jnpp.service.services.AccountService;
import jnpp.service.services.DebitAuthorizationService;
import jnpp.service.services.NotificationService;
import jnpp.service.services.PaymentMeanService;

/**
 * Le contrôleur des liens vers les pages de gestion des comptes utilisateur
 */
@Controller
public class LinkAccountController {

    /**
     * Le service de notifications
     */
    @Autowired
    private NotificationService notifService;
    /**
     * Le service des comptes utilisateur
     */
    @Autowired
    private AccountService accountService;
    /**
     * Le service des moyens de paiement
     */
    @Autowired
    private PaymentMeanService paymentMeanService;
    /**
     * Le service des autorisations de débits
     */
    @Autowired
    private DebitAuthorizationService authorizationService;

    /**
     * Requête sur la vue d'un compte
     *
     * @param model   le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @param rm      objet dans lequel on ajoute les informations que l'on veut
     *                voir transiter lors des redirections
     * @return Une vue sur un compte de l'utilisateur si il est connecté,
     *         redirection vers l'index sinon
     * @throws Exception Exception non controllees.
     */
    @RequestMapping(value = "account", method = RequestMethod.GET)
    protected ModelAndView linktoAccount(Model model,
            HttpServletRequest request, RedirectAttributes rm)
            throws Exception {
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
        String id = request.getParameter("id");
        List<AccountDTO> accounts = accountService
                .getAccounts(SessionController.getClient(session).getLogin());
        Iterator<AccountDTO> ite = accounts.iterator();
        AccountDTO account = null;
        ModelAndView view = null;
        while (ite.hasNext()) {
            account = ite.next();
            if (account.getRib().equals(id)) {
                switch (account.getType()) {
                case CURRENT:
                    view = new JNPPModelAndView("accounts/currentaccount",
                            ViewInfo.createInfo(session, alerts));
                    view.addObject("accountsMap",
                            Translator.getInstance().translateAccounts(
                                    SessionController.getLanguage(session)));
                    view.addObject("currencyMap",
                            Translator.getInstance().translateCurrency(
                                    SessionController.getLanguage(session)));
                    List<MovementView> movementsCurrent = new ArrayList<MovementView>();
                    for (MovementDTO movement : accountService.getMovements(
                            SessionController.getClient(session).getLogin(),
                            id)) {
                        movementsCurrent.add(new MovementView(movement, id));
                    }
                    view.addObject("movements", movementsCurrent);
                    try {
                        List<BankCardDTO> cards = paymentMeanService
                                .getBankCards(SessionController
                                        .getClient(session).getLogin(), id);
                        view.addObject("cards", cards);
                        List<CheckbookDTO> checkbooks = paymentMeanService
                                .getCheckBooks(SessionController
                                        .getClient(session).getLogin(), id);
                        view.addObject("checkbooks", checkbooks);
                        view.addObject("statusMap", Translator.getInstance()
                                .translatePaymentMeanStatus(SessionController
                                        .getLanguage(session)));
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
                    break;
                case JOINT:
                    view = new JNPPModelAndView("accounts/jointaccount",
                            ViewInfo.createInfo(session, alerts));
                    view.addObject("accountsMap",
                            Translator.getInstance().translateAccounts(
                                    SessionController.getLanguage(session)));
                    view.addObject("currencyMap",
                            Translator.getInstance().translateCurrency(
                                    SessionController.getLanguage(session)));
                    List<MovementView> movementsJoint = new ArrayList<MovementView>();
                    for (MovementDTO movement : accountService.getMovements(
                            SessionController.getClient(session).getLogin(),
                            id)) {
                        movementsJoint.add(new MovementView(movement, id));
                    }
                    view.addObject("movements", movementsJoint);
                    try {
                        List<BankCardDTO> cards = paymentMeanService
                                .getBankCards(SessionController
                                        .getClient(session).getLogin(), id);
                        view.addObject("cards", cards);
                        List<CheckbookDTO> checkbooks = paymentMeanService
                                .getCheckBooks(SessionController
                                        .getClient(session).getLogin(), id);
                        view.addObject("checkbooks", checkbooks);
                        view.addObject("statusMap", Translator.getInstance()
                                .translatePaymentMeanStatus(SessionController
                                        .getLanguage(session)));
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
                    break;
                case SAVING:
                    view = new JNPPModelAndView("accounts/savingaccount",
                            ViewInfo.createInfo(session, alerts));
                    view.addObject("accountsMap",
                            Translator.getInstance().translateAccounts(
                                    SessionController.getLanguage(session)));
                    view.addObject("currencyMap",
                            Translator.getInstance().translateCurrency(
                                    SessionController.getLanguage(session)));
                    List<MovementView> movementsSaving = new ArrayList<MovementView>();
                    for (MovementDTO movement : accountService.getMovements(
                            SessionController.getClient(session).getLogin(),
                            id)) {
                        movementsSaving.add(new MovementView(movement, id));
                    }
                    view.addObject("movements", movementsSaving);
                    break;
                case SHARE:

                    ShareAccountDTO accountDTO = accountService.getShareAccount(
                            SessionController.getClient(session).getLogin());

                    view = new JNPPModelAndView("accounts/shareaccount",
                            ViewInfo.createInfo(session, alerts));
                    view.addObject("accountsMap",
                            Translator.getInstance().translateAccounts(
                                    SessionController.getLanguage(session)));
                    List<MovementView> movementsShare = new ArrayList<MovementView>();
                    for (MovementDTO movement : accountService.getMovements(
                            SessionController.getClient(session).getLogin(),
                            id)) {
                        movementsShare.add(new MovementView(movement, id));
                    }
                    view.addObject("movements", movementsShare);
                    view.addObject("account", accountDTO);
                    break;
                default:
                    throw new AssertionError(account.getType().name());
                }
                if (account.getType() != AccountDTO.Type.SHARE) {
                    view.addObject("account", account);
                }

                return view;
            }
        }
        return new ModelAndView("redirect:/resume.htm");
    }

    /**
     * Requête sur la vue d'un compte
     *
     * @param model   le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @param rm      objet dans lequel on ajoute les informations que l'on veut
     *                voir transiter lors des redirections
     * @return Une vue sur un compte de l'utilisateur si il est connecté,
     *         redirection vers l'index sinon
     * @throws Exception Exception non controllees.
     */
    @RequestMapping(value = "openaccount", method = RequestMethod.GET)
    protected ModelAndView linktoOpenAccount(Model model,
            HttpServletRequest request, RedirectAttributes rm)
            throws Exception {
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
        ModelAndView view = null;
        switch (client.getType()) {
        case PRIVATE:
            view = new JNPPModelAndView("accounts/openprivateaccount",
                    ViewInfo.createInfo(session, alerts));
            String nbClientsStr = request.getParameter("nbClients");
            Integer nbClients = 1;
            if (nbClientsStr != null) {
                nbClients = Integer.parseInt(nbClientsStr);
                if (nbClients < 1) {
                    nbClients = 1;
                }
            }
            view.addObject("nbClients", nbClients);
            List<SavingBookDTO> books = accountService.getSavingBooks();
            view.addObject("books", books);
            view.addObject("genders", IdentityDTO.Gender.values());
            view.addObject("gendersMap", Translator.getInstance()
                    .translateGenders(SessionController.getLanguage(session)));
            break;
        case PROFESIONAL:
            view = new JNPPModelAndView("accounts/openproaccount",
                    ViewInfo.createInfo(session, alerts));
            break;
        default:
            throw new AssertionError(client.getType().name());
        }
        return view;
    }

    /**
     * Requête sur la vue de la liste des comptes
     *
     * @param model   le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @param rm      objet dans lequel on ajoute les informations que l'on veut
     *                voir transiter lors des redirections
     * @return Une vue sur la liste des comptes de l'utilisateur si il est
     *         connecté, redirection vers l'index sinon
     * @throws Exception Exception non controllees.
     */
    @RequestMapping(value = "resume", method = RequestMethod.GET)
    protected ModelAndView linktoResume(Model model, HttpServletRequest request,
            RedirectAttributes rm) throws Exception {
        List<AlertMessage> alerts = (List<AlertMessage>) model.asMap()
                .get("alerts");
        HttpSession session = request.getSession();
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
        List<AccountDTO> accounts = accountService
                .getAccounts(SessionController.getClient(session).getLogin());
        ModelAndView view = new JNPPModelAndView("accounts/resume",
                ViewInfo.createInfo(session, alerts));
        view.addObject("accounts", accounts);
        view.addObject("accountsMap", Translator.getInstance()
                .translateAccounts(SessionController.getLanguage(session)));
        view.addObject("currencyMap", Translator.getInstance()
                .translateCurrency(SessionController.getLanguage(session)));
        return view;
    }

    /**
     * Requête sur la vue des autorisations de débit
     *
     * @param model   le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @param rm      objet dans lequel on ajoute les informations que l'on veut
     *                voir transiter lors des redirections
     * @return Une vue sur la liste des autorisations de débits
     * @throws Exception Exception non controllees.
     */
    @RequestMapping(value = "authorization", method = RequestMethod.GET)
    private ModelAndView linkToAuthorization(Model model,
            HttpServletRequest request, RedirectAttributes rm) {

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

        List<DebitAuthorizationDTO> authorizations;
        List<String> ribs = new ArrayList<String>();

        try {
            authorizations = authorizationService
                    .getDebitAuthorizations(client.getLogin());

            List<AccountDTO> accounts = accountService
                    .getAccounts(client.getLogin());
            for (AccountDTO account : accounts) {
                if (account.getType() == AccountDTO.Type.CURRENT
                        || account.getType() == AccountDTO.Type.JOINT) {
                    ribs.add(account.getRib());
                }
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
        }

        ModelAndView mv = new JNPPModelAndView("accounts/authorization",
                ViewInfo.createInfo(session, alerts));

        mv.addObject("authorizations", authorizations);
        mv.addObject("ribs", ribs);

        return mv;
    }
}
