    package jnpp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jnpp.controller.views.JNPPModelAndView;
import jnpp.controller.views.Translator;
import jnpp.controller.views.alerts.AlertEnum;
import jnpp.controller.views.alerts.AlertMessage;
import jnpp.controller.views.info.ViewInfo;
import jnpp.service.dto.IdentityDTO;
import jnpp.service.dto.accounts.AccountDTO;
import jnpp.service.dto.accounts.DebitAuthorizationDTO;
import jnpp.service.dto.clients.ClientDTO;
import jnpp.service.dto.clients.PrivateDTO;
import jnpp.service.exceptions.ClosureException;
import jnpp.service.exceptions.accounts.ClientTypeException;
import jnpp.service.exceptions.accounts.CloseRequestException;
import jnpp.service.exceptions.accounts.NoCurrentAccountException;
import jnpp.service.exceptions.accounts.NoShareAccountException;
import jnpp.service.exceptions.accounts.UnknownIdentityException;
import jnpp.service.exceptions.duplicates.DuplicateAccountException;
import jnpp.service.exceptions.entities.FakeClientException;
import jnpp.service.exceptions.entities.FakeSavingBookException;
<<<<<<< HEAD
import jnpp.service.exceptions.entities.FakeShareTitleException;
import jnpp.service.exceptions.movements.AmountException;
import jnpp.service.exceptions.owners.AccountOwnerException;
import jnpp.service.services.AccountService;
import jnpp.service.services.DebitAuthorizationService;
import jnpp.service.services.NotificationService;
=======
import jnpp.service.exceptions.movements.AccountTypeException;
import jnpp.service.exceptions.owners.AccountOwnerException;
import jnpp.service.services.AccountService;
import jnpp.service.services.PaymentMeanService;
>>>>>>> 23b486be5a8c0ad5a7a072b7596013c6a8a9d6bc
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Classe de contrôleur des requêtes sur des comptes
 */
@Controller
public class AccountController {

    @Autowired
    private AccountService accountService;
<<<<<<< HEAD
    @Autowired
    NotificationService notifService;
    @Autowired
    DebitAuthorizationService authorizationService;

=======
    @Autowired 
    private PaymentMeanService paymentMeanService;
    
>>>>>>> 23b486be5a8c0ad5a7a072b7596013c6a8a9d6bc
    /**
     * Demande d'ouverture de compte courant
     *
     * @param model le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @param response la réponse
     * @param rm objet dans lequel on ajoute les informations que l'on veut voir
     * transiter lors des redirections
     * @return La vue des comptes
     * @throws Exception
     */
    @RequestMapping(value = "opencurrentaccount", method = RequestMethod.POST)
    private ModelAndView openCurrentAccount(Model model, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rm)
            throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>) model.asMap().get("alerts");
        if (session == null) {
            session = request.getSession(true);
        }
        if (SessionController.getLanguage(session) != Translator.Language.FR) {
            SessionController.setLanguage(session, Translator.Language.FR);
        }
        if (SessionController.isConnected(session)) {
            //Call service
            try {
                accountService.openCurrentAccount(SessionController.getClient(session).getLogin());
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.SUCCESS, "Votre compte courant est ouvert"));
                } else {
                    alerts = new ArrayList<AlertMessage>();
                    alerts.add(new AlertMessage(AlertEnum.SUCCESS, "Votre compte courant est ouvert"));
                    rm.addFlashAttribute("alerts", alerts);
                }
            } catch (DuplicateAccountException duplicate) {
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Vous possédez déjà un compte courant"));
                } else {
                    alerts = new ArrayList<AlertMessage>();
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Vous possédez déjà un compte courant"));
                    rm.addFlashAttribute("alerts", alerts);
                }
            } catch (FakeClientException clientException) {
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Il semble y avoir une erreur dans votre session"));
                } else {
                    alerts = new ArrayList<AlertMessage>();
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Il semble y avoir une erreur dans votre session"));
                    rm.addFlashAttribute("alerts", alerts);
                }
                return new ModelAndView("redirect:/disconnect.htm");
            } finally {
                return new ModelAndView("redirect:/resume.htm");
            }
        }
        return new ModelAndView("redirect:/index.htm"); //ne devrait pas arriver
    }

    /**
     * Demande d'ouverture de livret
     *
     * @param model le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @param response la réponse
     * @param rm objet dans lequel on ajoute les informations que l'on veut voir
     * transiter lors des redirections
     * @return La vue des comptes
     * @throws Exception
     */
    @RequestMapping(value = "opensavingaccount", method = RequestMethod.POST)
    private ModelAndView openSavingAccount(Model model, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rm)
            throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>) model.asMap().get("alerts");
        if (session == null) {
            session = request.getSession(true);
        }
        if (SessionController.getLanguage(session) != Translator.Language.FR) {
            SessionController.setLanguage(session, Translator.Language.FR);
        }
        if (SessionController.isConnected(session)) {
            //Call service
            try {
                String bookName = request.getParameter("bookName");
                accountService.openSavingAccount(SessionController.getClient(session).getLogin(), bookName);
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.SUCCESS, "Votre livret est ouvert"));
                } else {
                    alerts = new ArrayList<AlertMessage>();
                    alerts.add(new AlertMessage(AlertEnum.SUCCESS, "Votre livret est ouvert"));
                    rm.addFlashAttribute("alerts", alerts);
                }
            } catch (DuplicateAccountException duplicate) {
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Vous possédez déjà un livret de ce type"));
                } else {
                    alerts = new ArrayList<AlertMessage>();
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Vous possédez déjà un livret de ce type"));
                    rm.addFlashAttribute("alerts", alerts);
                }
            } catch (FakeClientException clientException) {
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Il semble y avoir une erreur dans votre session"));
                } else {
                    alerts = new ArrayList<AlertMessage>();
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Il semble y avoir une erreur dans votre session"));
                    rm.addFlashAttribute("alerts", alerts);
                }
                return new ModelAndView("redirect:/disconnect.htm");
            } catch (FakeSavingBookException bookException) {
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Ce livret n'existe pas"));
                } else {
                    alerts = new ArrayList<AlertMessage>();
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Ce livret n'existe pas"));
                    rm.addFlashAttribute("alerts", alerts);
                }
            } catch (ClientTypeException typeClient) {
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Vous ne pouvez pas créer de livret"));
                } else {
                    alerts = new ArrayList<AlertMessage>();
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Vous ne pouvez pas créer de livret"));
                    rm.addFlashAttribute("alerts", alerts);
                }
            }
            return new ModelAndView("redirect:/resume.htm");
        }
        return new ModelAndView("redirect:/index.htm"); //ne devrait pas arriver
    }

    /**
     * Demande d'ouverture de compte joint
     *
     * @param model le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @param response la réponse
     * @param rm objet dans lequel on ajoute les informations que l'on veut voir
     * transiter lors des redirections
     * @return La vue des comptes
     * @throws Exception
     */
    @RequestMapping(value = "openjointaccount", method = RequestMethod.POST)
    private ModelAndView openJointAccount(Model model, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rm)
            throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>) model.asMap().get("alerts");
        if (session == null) {
            session = request.getSession(true);
        }
        if (SessionController.getLanguage(session) != Translator.Language.FR) {
            SessionController.setLanguage(session, Translator.Language.FR);
        }
        if (SessionController.isConnected(session)) {
            //Call service
            try {
                List<IdentityDTO> identities = new ArrayList<IdentityDTO>();
                ClientDTO client = SessionController.getClient(session);
                if (client.getType() == ClientDTO.Type.PROFESIONAL) {
                    throw new ClientTypeException();
                }
                identities.add(((PrivateDTO) client).getIdentity());
                String nbClientsStr = request.getParameter("nbClients");
                Integer nbClients = Integer.parseInt(nbClientsStr);
                for (int i = 1; i <= nbClients; i++) {
                    String lastName = request.getParameter("lastName" + String.valueOf(i));
                    String firstName = request.getParameter("firstName" + String.valueOf(i));
                    String genderStr = request.getParameter("gender" + String.valueOf(i));
                    IdentityDTO.Gender gender = IdentityDTO.Gender.MALE;
                    if (genderStr.equals(IdentityDTO.Gender.MALE.name())) {
                        gender = IdentityDTO.Gender.MALE;
                    } else if (genderStr.equals(IdentityDTO.Gender.FEMALE.name())) {
                        gender = IdentityDTO.Gender.FEMALE;
                    }
                    identities.add(new IdentityDTO(gender, firstName, lastName));
                }
                accountService.openJointAccount(SessionController.getClient(session).getLogin(), identities);
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.SUCCESS, "Votre compte joint est ouvert"));
                } else {
                    alerts = new ArrayList<AlertMessage>();
                    alerts.add(new AlertMessage(AlertEnum.SUCCESS, "Votre compte joint est ouvert"));
                    rm.addFlashAttribute("alerts", alerts);
                }
            } catch (FakeClientException clientException) {
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Il semble y avoir une erreur dans votre session"));
                } else {
                    alerts = new ArrayList<AlertMessage>();
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Il semble y avoir une erreur dans votre session"));
                    rm.addFlashAttribute("alerts", alerts);
                }
                return new ModelAndView("redirect:/disconnect.htm");
            } catch (ClientTypeException typeClient) {
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Vous ne pouvez pas créer de compte joint"));
                } else {
                    alerts = new ArrayList<AlertMessage>();
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Vous ne pouvez pas créer de compte joint"));
                    rm.addFlashAttribute("alerts", alerts);
                }
            } catch (UnknownIdentityException unknowIdentity) {
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Une des personnes que vous avez indiquées n'est pas inscrite chez nous"));
                } else {
                    alerts = new ArrayList<AlertMessage>();
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Une des personnes que vous avez indiquées n'est pas inscrite chez nous"));
                    rm.addFlashAttribute("alerts", alerts);
                }
            }
            return new ModelAndView("redirect:/resume.htm");

        }
        return new ModelAndView("redirect:/index.htm"); //ne devrait pas arriver
    }

    /**
     * Demande d'ouverture de compte d'actions
     *
     * @param model le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @param response la réponse
     * @param rm objet dans lequel on ajoute les informations que l'on veut voir
     * transiter lors des redirections
     * @return La vue des comptes
     * @throws Exception
     */
    @RequestMapping(value = "openshareaccount", method = RequestMethod.POST)
    private ModelAndView openShareAccount(Model model, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rm)
            throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>) model.asMap().get("alerts");
        if (session == null) {
            session = request.getSession(true);
        }
        if (SessionController.getLanguage(session) != Translator.Language.FR) {
            SessionController.setLanguage(session, Translator.Language.FR);
        }
        if (SessionController.isConnected(session)) {
            //Call service
            try {
                accountService.openShareAccount(SessionController.getClient(session).getLogin());
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.SUCCESS, "Votre compte titres est ouvert"));
                } else {
                    alerts = new ArrayList<AlertMessage>();
                    alerts.add(new AlertMessage(AlertEnum.SUCCESS, "Votre compte titres est ouvert"));
                    rm.addFlashAttribute("alerts", alerts);
                }
            } catch (DuplicateAccountException duplicate) {
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Vous possédez déjà un compte titres"));
                } else {
                    alerts = new ArrayList<AlertMessage>();
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Vous possédez déjà un compte titres"));
                    rm.addFlashAttribute("alerts", alerts);
                }
            } catch (FakeClientException clientException) {
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Il semble y avoir une erreur dans votre session"));
                } else {
                    alerts = new ArrayList<AlertMessage>();
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Il semble y avoir une erreur dans votre session"));
                    rm.addFlashAttribute("alerts", alerts);
                }
                return new ModelAndView("redirect:/disconnect.htm");
            } finally {
                return new ModelAndView("redirect:/resume.htm");
            }
        }
        return new ModelAndView("redirect:/index.htm"); //ne devrait pas arriver
    }

    /**
     * Demande de fermeture de comptes
     *
     * @param model le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @param response la réponse
     * @param rm objet dans lequel on ajoute les informations que l'on veut voir
     * transiter lors des redirections
     * @return La vue des comptes
     * @throws Exception
     */
    @RequestMapping(value = "closeaccount", method = RequestMethod.POST)
    private ModelAndView closeAccount(Model model, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rm)
            throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>) model.asMap().get("alerts");
        if (session == null) {
            session = request.getSession(true);
        }
        if (SessionController.getLanguage(session) != Translator.Language.FR) {
            SessionController.setLanguage(session, Translator.Language.FR);
        }
        if (SessionController.isConnected(session)) {
            //Call service
            try {
                String rib = request.getParameter("rib");
                accountService.closeAccount(SessionController.getClient(session).getLogin(), rib);
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.SUCCESS, "Le compte a bien été fermé"));
                } else {
                    alerts = new ArrayList<AlertMessage>();
                    alerts.add(new AlertMessage(AlertEnum.SUCCESS, "Le compte a bien été fermé"));
                    rm.addFlashAttribute("alerts", alerts);
                }
            } catch (AccountOwnerException ownerException) {
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Vous n'êtes pas propriétaire de ce compte"));
                } else {
                    alerts = new ArrayList<AlertMessage>();
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Vous n'êtes pas propriétaire de ce compte"));
                    rm.addFlashAttribute("alerts", alerts);
                }
            } catch (ClosureException closureException) {
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Ce compte ne peut pas être fermé"));
                } else {
                    alerts = new ArrayList<AlertMessage>();
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Ce compte ne peut pas être fermé"));
                    rm.addFlashAttribute("alerts", alerts);
                }
            } catch (CloseRequestException requestException) {
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.SUCCESS, "La demande de fermeture a été prise en compte, en attente de la demande des autres propriétaires"));
                } else {
                    alerts = new ArrayList<AlertMessage>();
                    alerts.add(new AlertMessage(AlertEnum.SUCCESS, "La demande de fermeture a été prise en compte, en attente de la demande des autres propriétaires"));
                    rm.addFlashAttribute("alerts", alerts);
                }
            } catch (FakeClientException clientException) {
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Il semble y avoir une erreur dans votre session"));
                } else {
                    alerts = new ArrayList<AlertMessage>();
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Il semble y avoir une erreur dans votre session"));
                    rm.addFlashAttribute("alerts", alerts);
                }
                return new ModelAndView("redirect:/disconnect.htm");
            }
            return new ModelAndView("redirect:/resume.htm");
        }
        return new ModelAndView("redirect:/index.htm"); //ne devrait pas arriver
    }

    @RequestMapping(value = "authorization", method = RequestMethod.GET)
    private ModelAndView authorizationGet(Model model, HttpServletRequest request,
            HttpServletResponse response, RedirectAttributes rm) {

        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>) model.asMap().get("alerts");
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
                hasNotif = notifService.receiveUnseenNotifications(SessionController.getClient(session).getLogin()).size() > 0;
                SessionController.setHasNotif(session, hasNotif);
            } catch (FakeClientException invalidClient) {
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Il semble y avoir une erreur dans votre session"));
                } else {
                    alerts = new ArrayList<AlertMessage>();
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Il semble y avoir une erreur dans votre session"));
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

            List<AccountDTO> accounts = accountService.getAccounts(client.getLogin());
            for (AccountDTO account : accounts) {
                if (account.getType() == AccountDTO.Type.CURRENT
                        || account.getType() == AccountDTO.Type.JOINT) {
                    ribs.add(account.getRib());
                }
            }

        } catch (FakeClientException ex) {
            if (alerts != null) {
                alerts.add(new AlertMessage(AlertEnum.ERROR, "Il semble y avoir une erreur dans votre session"));
            } else {
                alerts = new ArrayList<AlertMessage>();
                alerts.add(new AlertMessage(AlertEnum.ERROR, "Il semble y avoir une erreur dans votre session"));
            }
            return new ModelAndView("redirect:/disconnect.htm");
        }

        ModelAndView mv = new JNPPModelAndView("accounts/authorization",
                ViewInfo.createInfo(session, alerts));

        mv.addObject("authorizations", authorizations);
        mv.addObject("ribs", ribs);

        return mv;
    }

    @RequestMapping(value = "addDebitAuthorization", method = RequestMethod.POST)
    private ModelAndView addDebitAuthorization(Model model, HttpServletRequest request,
            HttpServletResponse response, RedirectAttributes rm)
            throws Exception {
        
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>) model.asMap().get("alerts");
        if (session == null) {
            session = request.getSession(true);
        }
        if (SessionController.getLanguage(session) != Translator.Language.FR) {
            SessionController.setLanguage(session, Translator.Language.FR);
        }
        if (!SessionController.isConnected(session)) {
            return new ModelAndView("redirect:/index.htm");
        }
        
        

        return new ModelAndView("redirect:/authorization.htm");
    }

    /**
     * Commande de carte bancaire
     * @param model le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @param response la réponse
     * @param rm objet dans lequel on ajoute les informations que l'on veut voir transiter lors des redirections
     * @return La vue du compte qui a commande la carte
     * @throws Exception 
     */
    @RequestMapping(value = "commandcard", method = RequestMethod.POST)
    private ModelAndView commandCard(Model model, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rm)
            throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>)model.asMap().get("alerts");
        if (session==null)
            session = request.getSession(true);
        if (SessionController.getLanguage(session)!=Translator.Language.FR)
            SessionController.setLanguage(session,Translator.Language.FR);
        if (!SessionController.isConnected(session)) {
            return new ModelAndView("redirect:/index.htm");
        }
        String rib = request.getParameter("rib");
        //Call service
        try {
            paymentMeanService.commandBankCard(SessionController.getClient(session).getLogin(), rib);
            if (alerts != null) {
                alerts.add(new AlertMessage(AlertEnum.SUCCESS, "Votre carte est commandé"));
            } else {
                alerts = new ArrayList<AlertMessage>(); 
                alerts.add(new AlertMessage(AlertEnum.SUCCESS, "Votre carte est commandé"));
                rm.addFlashAttribute("alerts", alerts);    
            }
        } catch (AccountTypeException accountException) {
            if (alerts != null) {
                alerts.add(new AlertMessage(AlertEnum.ERROR, "Ce compte ne peut pas être lié à une carte"));
            } else {
                alerts = new ArrayList<AlertMessage>(); 
                alerts.add(new AlertMessage(AlertEnum.ERROR, "Ce compte ne peut pas être lié à une carte"));
                rm.addFlashAttribute("alerts", alerts);    
            }
        } catch (AccountOwnerException ownerException) {
            if (alerts != null) {
                alerts.add(new AlertMessage(AlertEnum.ERROR, "Vous n'avez pas les droits nécessaires pour cette action"));
            } else {
                alerts = new ArrayList<AlertMessage>(); 
                alerts.add(new AlertMessage(AlertEnum.ERROR, "Vous n'avez pas les droits nécessaires pour cette action"));
                rm.addFlashAttribute("alerts", alerts);    
            }
        } catch (FakeClientException clientException) {
            if (alerts != null) {
                alerts.add(new AlertMessage(AlertEnum.ERROR, "Il semble y avoir une erreur dans votre session"));
            } else {
                alerts = new ArrayList<AlertMessage>(); 
                alerts.add(new AlertMessage(AlertEnum.ERROR, "Il semble y avoir une erreur dans votre session"));
                rm.addFlashAttribute("alerts", alerts);
            }
            return new ModelAndView("redirect:/disconnect.htm");
        } 
            return new ModelAndView("redirect:/account.htm?id="+rib);
        
    }
    /**
     * Commande de chéquier
     * @param model le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @param response la réponse
     * @param rm objet dans lequel on ajoute les informations que l'on veut voir transiter lors des redirections
     * @return La vue du compte qui a commande la carte
     * @throws Exception 
     */
    @RequestMapping(value = "commandcheckbook", method = RequestMethod.POST)
    private ModelAndView commandCheckBook(Model model, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rm)
            throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>)model.asMap().get("alerts");
        if (session==null)
            session = request.getSession(true);
        if (SessionController.getLanguage(session)!=Translator.Language.FR)
            SessionController.setLanguage(session,Translator.Language.FR);
        if (!SessionController.isConnected(session)) {
            return new ModelAndView("redirect:/index.htm");
        }
        String rib = request.getParameter("rib");
        //Call service
        try {
            paymentMeanService.commandCheckbook(SessionController.getClient(session).getLogin(), rib);
            if (alerts != null) {
                alerts.add(new AlertMessage(AlertEnum.SUCCESS, "Votre carte est commandé"));
            } else {
                alerts = new ArrayList<AlertMessage>(); 
                alerts.add(new AlertMessage(AlertEnum.SUCCESS, "Votre carte est commandé"));
                rm.addFlashAttribute("alerts", alerts);    
            }
        } catch (AccountTypeException accountException) {
            if (alerts != null) {
                alerts.add(new AlertMessage(AlertEnum.ERROR, "Ce compte ne peut pas être lié à une carte"));
            } else {
                alerts = new ArrayList<AlertMessage>(); 
                alerts.add(new AlertMessage(AlertEnum.ERROR, "Ce compte ne peut pas être lié à une carte"));
                rm.addFlashAttribute("alerts", alerts);    
            }
        } catch (AccountOwnerException ownerException) {
            if (alerts != null) {
                alerts.add(new AlertMessage(AlertEnum.ERROR, "Vous n'avez pas les droits nécessaires pour cette action"));
            } else {
                alerts = new ArrayList<AlertMessage>(); 
                alerts.add(new AlertMessage(AlertEnum.ERROR, "Vous n'avez pas les droits nécessaires pour cette action"));
                rm.addFlashAttribute("alerts", alerts);    
            }
        } catch (FakeClientException clientException) {
            if (alerts != null) {
                alerts.add(new AlertMessage(AlertEnum.ERROR, "Il semble y avoir une erreur dans votre session"));
            } else {
                alerts = new ArrayList<AlertMessage>(); 
                alerts.add(new AlertMessage(AlertEnum.ERROR, "Il semble y avoir une erreur dans votre session"));
                rm.addFlashAttribute("alerts", alerts);
            }
            return new ModelAndView("redirect:/disconnect.htm");
        } finally {
            return new ModelAndView("redirect:/account.htm?id="+rib);
        }
    }

}
