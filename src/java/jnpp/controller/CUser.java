package jnpp.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jnpp.controller.views.JNPPModelAndView;

import jnpp.controller.views.alerts.AlertEnum;
import jnpp.controller.views.alerts.AlertMessage;
import jnpp.controller.views.Translator;
import jnpp.controller.views.info.UnconnectedInfo;
import jnpp.dao.entities.clients.Client;
import jnpp.dao.entities.clients.Gender;
import jnpp.dao.entities.clients.Private;
import jnpp.dao.entities.clients.Professional;
import jnpp.service.IClientService;
import jnpp.service.exceptions.clients.BeOfAgeException;
import jnpp.service.exceptions.clients.DuplicatedClientException;
import jnpp.service.exceptions.clients.InvalidInformationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Classe contrôlant la gestion des utilisateurs
 */
@Controller
public class CUser {
    /**
     * Le service des utilisateurs
     */
    @Autowired
    IClientService clientService;
    /**
     * Requête du formulaire de connexion, essaie de connecter l'utilisateur
     * @param model le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @param response la réponse
     * @param rm objet dans lequel on ajoute les informations que l'on veut voir transiter lors des redirections
     * @return Une redirection vers le menu utilisateur si la connexion a réussie, une redirection vers le formulaire de connexion si elle a échouée,
     * une redireciton vers l'index si l'utilisateur était déjà connecté
     * @throws Exception 
     */
    @RequestMapping(value = "connect", method = RequestMethod.POST)
    protected ModelAndView connect(Model model, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rm) throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>)model.asMap().get("alerts");
        if (session==null)
            session = request.getSession(true);
        if (CSession.getLanguage(session)!=Translator.Language.FR)
            CSession.setLanguage(session,Translator.Language.FR);
        if (!CSession.isConnected(session)) {
            String id = request.getParameter("account");
            String password = request.getParameter("password");
            Client client = this.clientService.signIn(id, password);
            if (client!=null) {
                CSession.setTypeClient(session, client.getType());
                CSession.setHasNotif(session, false);
                switch (client.getType()) {
                    case PRIVATE:
                        Private privateClient = (Private)client;
                        CSession.setFirstName(session, privateClient.getIdentity().getFirstname());
                        CSession.setLastName(session, privateClient.getIdentity().getLastname());
                        break;
                    case PROFESIONAL:
                        Professional pro = (Professional)client;
                        CSession.setCompanyName(session, pro.getName());
                        break;
                    default:
                        throw new AssertionError(client.getType().name());                
                }
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.SUCCESS, "Connexion réussie"));
                } else {
                    alerts = new ArrayList<AlertMessage>(); 
                    alerts.add(new AlertMessage(AlertEnum.SUCCESS, "Connexion réussie"));
                    rm.addFlashAttribute("alerts", alerts);
                }
                
                return new ModelAndView("redirect:/home.htm");
            } else {
                AlertMessage error = new AlertMessage(AlertEnum.ERROR, "Nom d'utilisateur ou mot de passe incorrect");
                if (alerts != null) {
                    alerts.add(error);
                    return new JNPPModelAndView("manageuser/connect", new UnconnectedInfo(alerts));
                } else {
                    return new JNPPModelAndView("manageuser/connect", new UnconnectedInfo(error));
                } 
            }
        }
        return new ModelAndView("redirect:/index.htm"); //ne devrait pas pouvoir arriver
    }
    /**
     * Requête de déconnexion
     * @param model le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @param response la réponse
     * @param rm objet dans lequel on ajoute les informations que l'on veut voir transiter lors des redirections
     * @return Déconnecte si l'utilisateur était connecté, redirige toujours vers l'index
     * @throws Exception 
     */
    @RequestMapping(value = "disconnect", method = RequestMethod.GET)
    ModelAndView disconnect(Model model, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rm) throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>)model.asMap().get("alerts");
        if (session==null)
            session = request.getSession(true);
        if (CSession.getLanguage(session)!=Translator.Language.FR)
            CSession.setLanguage(session,Translator.Language.FR);
        //this.userService.signOut(null);
        boolean disconnect = true;
        if (CSession.isConnected(session)) {
            if (disconnect) {
                CSession.clearSession(session);
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.SUCCESS, "Déconnexion réussie"));
                } else {
                    alerts = new ArrayList<AlertMessage>(); 
                    alerts.add(new AlertMessage(AlertEnum.SUCCESS, "Déconnexion réussie"));
                    rm.addFlashAttribute("alerts", alerts);
                }
            } else {
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Erreur lors de la déconnexion"));
                } else {
                    alerts = new ArrayList<AlertMessage>(); 
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Erreur lors de la déconnexion"));
                    rm.addFlashAttribute("alerts", alerts);
                }
            }
        }
        return new ModelAndView("redirect:/index.htm");
    }
    /**
     * Requête du formulaire d'inscription d'un particulier
     * @param model le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @param response la réponse
     * @param rm objet dans lequel on ajoute les informations que l'on veut voir transiter lors des redirections
     * @return Une redirection vers l'index si l'inscription a réussit ou si l'utilisateur était connecté,
     * reste sur la page d'inscription si elle a échouée,
     * @throws Exception 
     */
    @RequestMapping(value = "personalsignup", method = RequestMethod.POST)
    protected ModelAndView validatePersonalSignUp(Model model, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rm)
            throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>)model.asMap().get("alerts");
        if (session==null)
            session = request.getSession(true);
        if (CSession.getLanguage(session)!=Translator.Language.FR)
            CSession.setLanguage(session,Translator.Language.FR);
        if (!CSession.isConnected(session)) {
            //Get parameters
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String genderStr = request.getParameter("gender");
            String birthdayStr = request.getParameter("birthday");
            String email = request.getParameter("email");
            String streetNbrStr = request.getParameter("streetNbr");
            String street = request.getParameter("street");
            String city = request.getParameter("city");
            String country = request.getParameter("country");  
            String phone = request.getParameter("phone");
            Gender gender;
            if (genderStr.equals(Gender.MALE.name())) {
                gender = Gender.MALE;
            } else if (genderStr.equals(Gender.FEMALE.name())) {
                gender = Gender.FEMALE;
            } else {
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Sexe invalide"));
                } else {
                    alerts = new ArrayList<AlertMessage>(); 
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Sexe invalide"));
                    rm.addFlashAttribute("alerts", alerts);    
                }
                return new JNPPModelAndView("signup/personalsignup", new UnconnectedInfo(alerts));
            }
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date birthday = format.parse(birthdayStr);
            Integer streetNbr = Integer.parseInt(streetNbrStr);
            //Call service
            try {
                clientService.signUp(gender, firstName, lastName, birthday, email, streetNbr, street,
                        city, country, phone);
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.SUCCESS, "Inscription réussie"));
                } else {
                    alerts = new ArrayList<AlertMessage>(); 
                    alerts.add(new AlertMessage(AlertEnum.SUCCESS, "Inscription réussie"));
                    rm.addFlashAttribute("alerts", alerts);    
                }
                return new ModelAndView("redirect:/index.htm");
            } catch (DuplicatedClientException dupliactedClient) {
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Ce client est déjà enregistré"));
                } else {
                    alerts = new ArrayList<AlertMessage>(); 
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Ce client est déjà enregistré"));
                    rm.addFlashAttribute("alerts", alerts);    
                }
                return new JNPPModelAndView("signup/personalsignup", new UnconnectedInfo(alerts));
            } catch (BeOfAgeException age) {
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Un client ne peut pas être mineur"));
                } else {
                    alerts = new ArrayList<AlertMessage>(); 
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Un client ne peut pas être mineur"));
                    rm.addFlashAttribute("alerts", alerts);    
                }
                return new JNPPModelAndView("signup/personalsignup", new UnconnectedInfo(alerts));
            } catch (InvalidInformationException invalidFormat) {
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Une erreur est présente dans le formulaire"));
                } else {
                    alerts = new ArrayList<AlertMessage>(); 
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Une erreur est présente dans le formulaire"));
                    rm.addFlashAttribute("alerts", alerts);    
                }
                return new JNPPModelAndView("signup/personalsignup", new UnconnectedInfo(alerts));
            }
        }
        return new ModelAndView("redirect:/index.htm"); //ne devrait pas arriver
    }
    /**
     * Requête du formulaire d'inscription d'un professionnel
     * @param model le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @param response la réponse
     * @param rm objet dans lequel on ajoute les informations que l'on veut voir transiter lors des redirections
     * @return Une redirection vers l'index si l'inscription a réussit ou si l'utilisateur était connecté,
     * reste sur la page d'inscription si elle a échouée,
     * @throws Exception 
     */
    @RequestMapping(value = "professionalsignup", method = RequestMethod.POST)
    protected ModelAndView validateProfessionalSignUp(Model model, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rm)
            throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>)model.asMap().get("alerts");
        if (session==null)
            session = request.getSession(true);
        if (CSession.getLanguage(session)!=Translator.Language.FR)
            CSession.setLanguage(session,Translator.Language.FR);
        if (!CSession.isConnected(session)) {
            //Get parameters
            String companyName = request.getParameter("company");
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String genderStr = request.getParameter("gender");
            String email = request.getParameter("email");
            String streetNbrStr = request.getParameter("streetNbr");
            String street = request.getParameter("street");
            String city = request.getParameter("city");
            String country = request.getParameter("country");  
            String phone = request.getParameter("phone");
            Gender gender;
            if (genderStr.equals(Gender.MALE.name())) {
                gender = Gender.MALE;
            } else if (genderStr.equals(Gender.FEMALE.name())) {
                gender = Gender.FEMALE;
            } else {
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Sexe invalide"));
                } else {
                    alerts = new ArrayList<AlertMessage>(); 
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Sexe invalide"));
                    rm.addFlashAttribute("alerts", alerts);    
                }
                return new JNPPModelAndView("signup/professionalsignup", new UnconnectedInfo(alerts));
            }
            Integer streetNbr = Integer.parseInt(streetNbrStr);
            //call service
            try {
                clientService.signUp(companyName, gender, firstName, lastName, email, streetNbr, street,
                        city, country, phone);
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.SUCCESS, "Inscription réussie"));
                } else {
                    alerts = new ArrayList<AlertMessage>(); 
                    alerts.add(new AlertMessage(AlertEnum.SUCCESS, "Inscription réussie"));
                    rm.addFlashAttribute("alerts", alerts);    
                }
                return new ModelAndView("redirect:/index.htm");
            } catch (DuplicatedClientException dupliactedClient) {
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Ce client est déjà enregistré"));
                } else {
                    alerts = new ArrayList<AlertMessage>(); 
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Ce client est déjà enregistré"));
                    rm.addFlashAttribute("alerts", alerts);    
                }
                return new JNPPModelAndView("signup/professionalsignup", new UnconnectedInfo(alerts));
            } catch (InvalidInformationException invalidFormat) {
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Une erreur est présente dans le formulaire"));
                } else {
                    alerts = new ArrayList<AlertMessage>(); 
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Une erreur est présente dans le formulaire"));
                    rm.addFlashAttribute("alerts", alerts);    
                }
                return new JNPPModelAndView("signup/professionalsignup", new UnconnectedInfo(alerts));
            }
        }
        return new ModelAndView("redirect:/index.htm"); //ne devrait pas arriver
    }
}
