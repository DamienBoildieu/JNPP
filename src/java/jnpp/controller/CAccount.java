package jnpp.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jnpp.controller.views.JNPPModelAndView;
import jnpp.controller.views.Translator;
import jnpp.controller.views.alerts.AlertEnum;
import jnpp.controller.views.alerts.AlertMessage;
import jnpp.controller.views.info.ViewInfo;
import jnpp.dao.entities.accounts.AccountEntity;
import jnpp.dao.entities.accounts.CurrentAccountEntity;
import jnpp.dao.entities.accounts.SavingAccountEntity;
import jnpp.service.dto.IdentityDTO;
import jnpp.service.dto.clients.ClientDTO;
import jnpp.service.dto.clients.PrivateDTO;
import jnpp.service.exceptions.accounts.ClientTypeException;
import jnpp.service.exceptions.accounts.UnknownIdentityException;
import jnpp.service.exceptions.clients.InformationException;
import jnpp.service.exceptions.duplicates.DuplicateAccountException;
import jnpp.service.exceptions.entities.FakeClientException;
import jnpp.service.exceptions.entities.FakeSavingBookException;
import jnpp.service.services.AccountService;
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
public class CAccount {
    @Autowired
    private AccountService accountService;
     
    /**
     * Demande d'ouverture de compte courant
     * @param model le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @param response la réponse
     * @param rm objet dans lequel on ajoute les informations que l'on veut voir transiter lors des redirections
     * @return La vue des comptes
     * @throws Exception 
     */
    @RequestMapping(value = "opencurrentaccount", method = RequestMethod.POST)
    private ModelAndView openCurrentAccount(Model model, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rm)
            throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>)model.asMap().get("alerts");
        if (session==null)
            session = request.getSession(true);
        if (CSession.getLanguage(session)!=Translator.Language.FR)
            CSession.setLanguage(session,Translator.Language.FR);
        if (CSession.isConnected(session)) {
            //Call service
            try {
                accountService.openCurrentAccount(CSession.getClient(session).getLogin());
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
            } finally {
                return new ModelAndView("redirect:/resume.htm");
            }
        }
        return new ModelAndView("redirect:/index.htm"); //ne devrait pas arriver
    }
    /**
     * Demande d'ouverture de livret
     * @param model le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @param response la réponse
     * @param rm objet dans lequel on ajoute les informations que l'on veut voir transiter lors des redirections
     * @return La vue des comptes
     * @throws Exception 
     */
    @RequestMapping(value = "opensavingaccount", method = RequestMethod.POST)
    private ModelAndView openSavingAccount(Model model, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rm)
            throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>)model.asMap().get("alerts");
        if (session==null)
            session = request.getSession(true);
        if (CSession.getLanguage(session)!=Translator.Language.FR)
            CSession.setLanguage(session,Translator.Language.FR);
        if (CSession.isConnected(session)) {
            //Call service
            try {
                String bookName = request.getParameter("book");
                accountService.openSavingAccount(CSession.getClient(session).getLogin(), bookName);
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
            } finally {
                return new ModelAndView("redirect:/resume.htm");
            }
        }
        return new ModelAndView("redirect:/index.htm"); //ne devrait pas arriver
    }
    /**
     * Demande d'ouverture de compte joint
     * @param model le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @param response la réponse
     * @param rm objet dans lequel on ajoute les informations que l'on veut voir transiter lors des redirections
     * @return La vue des comptes
     * @throws Exception 
     */
    @RequestMapping(value = "openjointaccount", method = RequestMethod.POST)
    private ModelAndView openJointAccount(Model model, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rm)
            throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>)model.asMap().get("alerts");
        if (session==null)
            session = request.getSession(true);
        if (CSession.getLanguage(session)!=Translator.Language.FR)
            CSession.setLanguage(session,Translator.Language.FR);
        if (CSession.isConnected(session)) {
            //Call service
            try {
                List<IdentityDTO> identities = new ArrayList<IdentityDTO>();
                ClientDTO  client = CSession.getClient(session);
                if (client.getType()==ClientDTO.Type.PROFESIONAL) {
                    throw new ClientTypeException();
                }
                identities.add(((PrivateDTO)client).getIdentity());
                String nbClientsStr = request.getParameter("nbClients");
                Integer nbClients = Integer.parseInt(nbClientsStr);
                for (int i=1; i<=nbClients; i++) {
                    String lastName = request.getParameter("lastName"+String.valueOf(i));
                    String firstName = request.getParameter("firstName"+String.valueOf(i));
                    String genderStr = request.getParameter("gender"+String.valueOf(i));
                    IdentityDTO.Gender gender = IdentityDTO.Gender.MALE;
                    if (genderStr.equals(IdentityDTO.Gender.MALE.name())) {
                        gender = IdentityDTO.Gender.MALE;
                    } else if (genderStr.equals(IdentityDTO.Gender.FEMALE.name())) {
                        gender = IdentityDTO.Gender.FEMALE;
                    }
                    identities.add(new IdentityDTO(gender, firstName, lastName));
                }
                accountService.openJointAccount(CSession.getClient(session).getLogin(), identities);
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
     * @param model le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @param response la réponse
     * @param rm objet dans lequel on ajoute les informations que l'on veut voir transiter lors des redirections
     * @return La vue des comptes
     * @throws Exception 
     */
    @RequestMapping(value = "openshareaccount", method = RequestMethod.POST)
    private ModelAndView openShareAccount(Model model, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rm)
            throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>)model.asMap().get("alerts");
        if (session==null)
            session = request.getSession(true);
        if (CSession.getLanguage(session)!=Translator.Language.FR)
            CSession.setLanguage(session,Translator.Language.FR);
        if (CSession.isConnected(session)) {
            //Call service
            try {
                accountService.openShareAccount(CSession.getClient(session).getLogin());
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
            } finally {
                return new ModelAndView("redirect:/resume.htm");
            }
        }
        return new ModelAndView("redirect:/index.htm"); //ne devrait pas arriver
    }
}
