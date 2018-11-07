package jnpp.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import jnpp.controller.views.JNPPModelAndView;
import jnpp.controller.views.Translator;
import jnpp.controller.views.alerts.AlertEnum;
import jnpp.controller.views.alerts.AlertMessage;
import jnpp.controller.views.info.ViewInfo;
import jnpp.service.dto.IdentityDTO;
import jnpp.service.dto.clients.ClientDTO;
import jnpp.service.exceptions.ClosureException;
import jnpp.service.exceptions.clients.AgeException;
import jnpp.service.exceptions.clients.InformationException;
import jnpp.service.exceptions.duplicates.DuplicateClientException;
import jnpp.service.exceptions.entities.FakeClientException;
import jnpp.service.services.ClientService;
import jnpp.service.services.NotificationService;
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
public class UserController {
    /**
     * Le service des utilisateurs
     */
    @Autowired
    private ClientService clientService;
    /**
     * Le service de notifications
     */
    @Autowired
    private NotificationService notifService;
    /**
     * Requête du formulaire de connexion, essaie de connecter l'utilisateur
     * @param model le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @param rm objet dans lequel on ajoute les informations que l'on veut voir transiter lors des redirections
     * @return Une redirection vers le menu utilisateur si la connexion a réussie, une redirection vers le formulaire de connexion si elle a échouée,
     * une redireciton vers l'index si l'utilisateur était déjà connecté
     * @throws Exception 
     */
    @RequestMapping(value = "connect", method = RequestMethod.POST)
    protected ModelAndView connect(Model model, HttpServletRequest request, RedirectAttributes rm) throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>)model.asMap().get("alerts");
        if (session==null)
            session = request.getSession(true);
        if (SessionController.getLanguage(session)!=Translator.Language.FR)
            SessionController.setLanguage(session,Translator.Language.FR);
        if (!SessionController.isConnected(session)) {
            String id = request.getParameter("account");
            String password = request.getParameter("password");
            ClientDTO client = this.clientService.signIn(id, password);
            if (client!=null) {
                boolean hasNotif = notifService.receiveUnseenNotifications(client.getLogin()).size()>0;
                SessionController.setHasNotif(session, hasNotif);
                SessionController.setClient(session, client);
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
                    return new JNPPModelAndView("manageuser/connect",ViewInfo.createInfo(session, alerts));
                } else {
                    return new JNPPModelAndView("manageuser/connect", ViewInfo.createInfo(session, error));
                } 
            }
        }
        return new ModelAndView("redirect:/index.htm"); //ne devrait pas pouvoir arriver
    }
    /**
     * Requête de déconnexion
     * @param model le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @param rm objet dans lequel on ajoute les informations que l'on veut voir transiter lors des redirections
     * @return Déconnecte si l'utilisateur était connecté, redirige toujours vers l'index
     * @throws Exception 
     */
    @RequestMapping(value = "disconnect", method = RequestMethod.GET)
    ModelAndView disconnect(Model model, HttpServletRequest request, RedirectAttributes rm) throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>)model.asMap().get("alerts");
        if (session==null)
            session = request.getSession(true);
        if (SessionController.getLanguage(session)!=Translator.Language.FR)
            SessionController.setLanguage(session,Translator.Language.FR);
        boolean disconnect = true;
        if (SessionController.isConnected(session)) {
            if (disconnect) {
                SessionController.clearSession(session);
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
     * @param rm objet dans lequel on ajoute les informations que l'on veut voir transiter lors des redirections
     * @return Une redirection vers l'index si l'inscription a réussit ou si l'utilisateur était connecté,
     * reste sur la page d'inscription si elle a échouée,
     * @throws Exception 
     */
    @RequestMapping(value = "privatesignup", method = RequestMethod.POST)
    protected ModelAndView validatePersonalSignUp(Model model, HttpServletRequest request, RedirectAttributes rm)
            throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>)model.asMap().get("alerts");
        if (session==null)
            session = request.getSession(true);
        if (SessionController.getLanguage(session)!=Translator.Language.FR)
            SessionController.setLanguage(session,Translator.Language.FR);
        if (!SessionController.isConnected(session)) {
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
            IdentityDTO.Gender gender;
            if (genderStr.equals(IdentityDTO.Gender.MALE.name())) {
                gender = IdentityDTO.Gender.MALE;
            } else if (genderStr.equals(IdentityDTO.Gender.FEMALE.name())) {
                gender = IdentityDTO.Gender.FEMALE;
            } else {
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Sexe invalide"));
                } else {
                    alerts = new ArrayList<AlertMessage>(); 
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Sexe invalide"));
                }
                return new JNPPModelAndView("signup/privatesignup", ViewInfo.createInfo(session, alerts));
            }
            Date birthday = new SimpleDateFormat("yyyy-MM-dd").parse(birthdayStr);
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
                return new ModelAndView("redirect:/signupsuccess.htm");
            } catch (DuplicateClientException dupliactedClient) {
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Ce client est déjà enregistré"));
                } else {
                    alerts = new ArrayList<AlertMessage>(); 
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Ce client est déjà enregistré"));
                }
                return new JNPPModelAndView("signup/privatesignup", ViewInfo.createInfo(session, alerts));
            } catch (AgeException age) {
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Un client ne peut pas être mineur"));
                } else {
                    alerts = new ArrayList<AlertMessage>(); 
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Un client ne peut pas être mineur"));
                }
                return new JNPPModelAndView("signup/privatesignup", ViewInfo.createInfo(session, alerts));
            } catch (InformationException invalidFormat) {
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Une erreur est présente dans le formulaire"));
                } else {
                    alerts = new ArrayList<AlertMessage>(); 
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Une erreur est présente dans le formulaire"));
                }
                return new JNPPModelAndView("signup/privatesignup", ViewInfo.createInfo(session, alerts));
            }
        }
        return new ModelAndView("redirect:/index.htm"); //ne devrait pas arriver
    }
    /**
     * Requête du formulaire d'inscription d'un professionnel
     * @param model le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @param rm objet dans lequel on ajoute les informations que l'on veut voir transiter lors des redirections
     * @return Une redirection vers l'index si l'inscription a réussit ou si l'utilisateur était connecté,
     * reste sur la page d'inscription si elle a échouée,
     * @throws Exception 
     */
    @RequestMapping(value = "professionalsignup", method = RequestMethod.POST)
    protected ModelAndView validateProfessionalSignUp(Model model, HttpServletRequest request, RedirectAttributes rm)
            throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>)model.asMap().get("alerts");
        if (session==null)
            session = request.getSession(true);
        if (SessionController.getLanguage(session)!=Translator.Language.FR)
            SessionController.setLanguage(session,Translator.Language.FR);
        if (!SessionController.isConnected(session)) {
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
            IdentityDTO.Gender gender;
            if (genderStr.equals(IdentityDTO.Gender.MALE.name())) {
                gender = IdentityDTO.Gender.MALE;
            } else if (genderStr.equals(IdentityDTO.Gender.FEMALE.name())) {
                gender = IdentityDTO.Gender.FEMALE;
            } else {
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Sexe invalide"));
                } else {
                    alerts = new ArrayList<AlertMessage>(); 
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Sexe invalide"));
                }
                return new JNPPModelAndView("signup/professionalsignup", ViewInfo.createInfo(session, alerts));
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
                return new ModelAndView("redirect:/signupsuccess.htm");
            } catch (DuplicateClientException dupliactedClient) {
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Ce client est déjà enregistré"));
                } else {
                    alerts = new ArrayList<AlertMessage>(); 
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Ce client est déjà enregistré"));
                    rm.addFlashAttribute("alerts", alerts);    
                }
                return new JNPPModelAndView("signup/professionalsignup", ViewInfo.createInfo(session, alerts));
            } catch (InformationException invalidFormat) {
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Une erreur est présente dans le formulaire"));
                } else {
                    alerts = new ArrayList<AlertMessage>(); 
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Une erreur est présente dans le formulaire"));
                }
                return new JNPPModelAndView("signup/professionalsignup", ViewInfo.createInfo(session, alerts));
            }
        }
        return new ModelAndView("redirect:/index.htm"); //ne devrait pas arriver
    }
    
    /**
     * Requête du formulaire de perte de mot de passe d'un particulier
     * @param model le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @param rm objet dans lequel on ajoute les informations que l'on veut voir transiter lors des redirections
     * @return Une redirection vers le menu utilisateur si la demande a réussie, une redirection vers le formulaire de mot de passe si elle a échouée,
     * une redireciton vers l'index si l'utilisateur était déjà connecté
     * @throws Exception 
     */
    @RequestMapping(value = "privatepassword", method = RequestMethod.POST)
    protected ModelAndView privateResetPassword(Model model, HttpServletRequest request, RedirectAttributes rm) throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>)model.asMap().get("alerts");
        if (session==null)
            session = request.getSession(true);
        if (SessionController.getLanguage(session)!=Translator.Language.FR)
            SessionController.setLanguage(session,Translator.Language.FR);
        if (!SessionController.isConnected(session)) {
            String id = request.getParameter("id");
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String email = request.getParameter("email");
            IdentityDTO.Gender TODO_gender = IdentityDTO.Gender.MALE;
            if (clientService.resetPassword(id, TODO_gender, firstName, lastName, email)) {
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.SUCCESS, "Demande de nouveau mot de passe accepté"));
                } else {
                    alerts = new ArrayList<AlertMessage>(); 
                    alerts.add(new AlertMessage(AlertEnum.SUCCESS, "Demande de nouveau mot de passe accepté"));
                    rm.addFlashAttribute("alerts", alerts);    
                }
                return new ModelAndView("redirect:/passwordsuccess.htm");
            } else {
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Aucun compte associé à ces informations n'est enregistré chez nous"));
                } else {
                    alerts = new ArrayList<AlertMessage>(); 
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Aucun compte associé à ces informations n'est enregistré chez nous"));
                }
                return new JNPPModelAndView("manageuser/privatepassword", ViewInfo.createInfo(session, alerts));
            }

        }
        return new ModelAndView("redirect:/index.htm"); //ne devrait pas pouvoir arriver
    }
    /**
     * Requête du formulaire de perte de mot de passe d'un professionnel
     * @param model le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @param rm objet dans lequel on ajoute les informations que l'on veut voir transiter lors des redirections
     * @return Une redirection vers le menu utilisateur si la demande a réussie, une redirection vers le formulaire de mot de passe si elle a échouée,
     * une redireciton vers l'index si l'utilisateur était déjà connecté
     * @throws Exception 
     */
    @RequestMapping(value = "professionalpassword", method = RequestMethod.POST)
    protected ModelAndView professionalResetPassword(Model model, HttpServletRequest request, RedirectAttributes rm) throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>)model.asMap().get("alerts");
        if (session==null)
            session = request.getSession(true);
        if (SessionController.getLanguage(session)!=Translator.Language.FR)
            SessionController.setLanguage(session,Translator.Language.FR);
        if (!SessionController.isConnected(session)) {
            String id = request.getParameter("id");
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String company = request.getParameter("company");
            String email = request.getParameter("email");
            IdentityDTO.Gender TODO_gender = IdentityDTO.Gender.MALE;
            if (clientService.resetPassword(id, company, TODO_gender, firstName, lastName, email)) {
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.SUCCESS, "Demande de nouveau mot de passe accepté"));
                } else {
                    alerts = new ArrayList<AlertMessage>(); 
                    alerts.add(new AlertMessage(AlertEnum.SUCCESS, "Demande de nouveau mot de passe accepté"));
                    rm.addFlashAttribute("alerts", alerts);    
                }
                return new ModelAndView("redirect:/passwordsuccess.htm");
            } else {
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Aucun compte associé à ces informations n'est enregistré chez nous"));
                } else {
                    alerts = new ArrayList<AlertMessage>(); 
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Aucun compte associé à ces informations n'est enregistré chez nous"));
                }
                return new JNPPModelAndView("manageuser/professionalpassword", ViewInfo.createInfo(session, alerts));
            }

        }
        return new ModelAndView("redirect:/index.htm"); //ne devrait pas pouvoir arriver
    }
    /**
     * Requête de changement de mot de passe
     * @param model le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @param rm objet dans lequel on ajoute les informations que l'on veut voir transiter lors des redirections
     * @return La vue d'information client
     * @throws Exception 
     */
    @RequestMapping(value = "changepassword", method = RequestMethod.POST)
    protected ModelAndView changePassword(Model model, HttpServletRequest request, RedirectAttributes rm) throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>)model.asMap().get("alerts");
        if (session==null)
            session = request.getSession(true);
        if (SessionController.getLanguage(session)!=Translator.Language.FR)
            SessionController.setLanguage(session,Translator.Language.FR);
        if (SessionController.isConnected(session)) {
            String old = request.getParameter("oldpsswd");
            String newp = request.getParameter("newpsswd");
            String confp = request.getParameter("confirmpsswd");
            if (!newp.equals(confp)) {
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Les deux champs de nouveau mot de passe doivent correspondre"));
                } else {
                    alerts = new ArrayList<AlertMessage>(); 
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Les deux champs de nouveau mot de passe doivent correspondre"));
                    rm.addFlashAttribute("alerts", alerts);    
                }
                
                return new ModelAndView("redirect:/userinfo.htm");
            }
            try {
                clientService.updatePassword(SessionController.getClient(session).getLogin(), old, newp);
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.SUCCESS, "Mise à jour réussie"));
                } else {
                    alerts = new ArrayList<AlertMessage>(); 
                    alerts.add(new AlertMessage(AlertEnum.SUCCESS, "Mise à jour réussie"));
                    rm.addFlashAttribute("alerts", alerts);    
                }
                return new ModelAndView("redirect:/userinfo.htm");
            } catch (FakeClientException invalidClient) {
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Il semble y avoir une erreur dans votre session"));
                } else {
                    alerts = new ArrayList<AlertMessage>(); 
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Il semble y avoir une erreur dans votre session"));
                    rm.addFlashAttribute("alerts", alerts);    
                }
                return new ModelAndView("redirect:/disconnect.htm");
            }
        }
        return new ModelAndView("redirect:/index.htm"); //ne devrait pas pouvoir arriver
    }
    /**
     * Requête du formulaire de changement d'information d'un utilisateur
     * @param model le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @param rm objet dans lequel on ajoute les informations que l'on veut voir transiter lors des redirections
     * @return La vue d'information client
     * @throws Exception 
     */
    @RequestMapping(value = "editinfo", method = RequestMethod.POST)
    private ModelAndView validateInfo(Model model, HttpServletRequest request, RedirectAttributes rm)
            throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>)model.asMap().get("alerts");
        if (session==null)
            session = request.getSession(true);
        if (SessionController.getLanguage(session)!=Translator.Language.FR)
            SessionController.setLanguage(session,Translator.Language.FR);
        if (SessionController.isConnected(session)) {
            //Get parameters
            String email = request.getParameter("email");
            String streetNbrStr = request.getParameter("streetNbr");
            String street = request.getParameter("street");
            String city = request.getParameter("city");
            String country = request.getParameter("country");  
            String phone = request.getParameter("phone");

            Integer streetNbr = Integer.parseInt(streetNbrStr);
            //Call service
            try {
                ClientDTO client = clientService.update(SessionController.getClient(session).getLogin(), email, streetNbr, street, city, country, phone);
                SessionController.setClient(session, client);
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.SUCCESS, "Mise à jour réussie"));
                } else {
                    alerts = new ArrayList<AlertMessage>(); 
                    alerts.add(new AlertMessage(AlertEnum.SUCCESS, "Mise à jour réussie"));
                    rm.addFlashAttribute("alerts", alerts);    
                }
                return new ModelAndView("redirect:/userinfo.htm");
            } catch (FakeClientException invalidClient) {
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Il semble y avoir une erreur dans votre session"));
                } else {
                    alerts = new ArrayList<AlertMessage>(); 
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Il semble y avoir une erreur dans votre session"));
                    rm.addFlashAttribute("alerts", alerts);    
                }
                return new ModelAndView("redirect:/disconnect.htm");
            } catch (InformationException invalidFormat) {
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Une erreur est présente dans le formulaire"));
                } else {
                    alerts = new ArrayList<AlertMessage>(); 
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Une erreur est présente dans le formulaire"));
                    rm.addFlashAttribute("alerts", alerts);    
                }
                return new ModelAndView("redirect:/userinfo.htm");
            }
        }
        return new ModelAndView("redirect:/index.htm"); //ne devrait pas arriver
    }
    /**
     * Requête de fermeture de compte client
     * @param model le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @param rm objet dans lequel on ajoute les informations que l'on veut voir transiter lors des redirections
     * @return L'index si réussite, la vue userInfo sinon
     * @throws Exception 
     */
    @RequestMapping(value = "closeuser", method = RequestMethod.POST)
    protected ModelAndView closeUser(Model model, HttpServletRequest request, RedirectAttributes rm, String view) throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>)model.asMap().get("alerts");
        if (session==null)
            session = request.getSession(true);
        if (SessionController.getLanguage(session)!=Translator.Language.FR)
            SessionController.setLanguage(session,Translator.Language.FR);
        if (SessionController.isConnected(session)) {
            try {
                String password = request.getParameter("psswd");
                String confirm = request.getParameter("confirm");
                if (!password.equals(confirm)) {
                    if (alerts != null) {
                        alerts.add(new AlertMessage(AlertEnum.ERROR, "Les deux champs de mot de passe doivent correspondre"));
                    } else {
                        alerts = new ArrayList<AlertMessage>();                         
                        alerts.add(new AlertMessage(AlertEnum.ERROR, "Les deux champs de mot de passe doivent correspondre"));
                        rm.addFlashAttribute("alerts", alerts);    
                    }
                
                    return new ModelAndView("redirect:/userinfo.htm");
                }
                clientService.close(SessionController.getClient(session).getLogin(), password);
                SessionController.clearSession(session);
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.SUCCESS, "Votre compte a bien été cloturé"));
                } else {
                    alerts = new ArrayList<AlertMessage>(); 
                    alerts.add(new AlertMessage(AlertEnum.SUCCESS, "Votre compte a bien été cloturé"));
                    rm.addFlashAttribute("alerts", alerts);    
                }
                return new ModelAndView("redirect:/index.htm");
            } catch (ClosureException closure) {
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Votre compte client ne peut être cloturé, assuerz-vous d'avoir fermé tout vos comptes"));
                } else {
                    alerts = new ArrayList<AlertMessage>(); 
                    alerts.add(new AlertMessage(AlertEnum.SUCCESS, "Votre compte client ne peut être cloturé, assuerz-vous d'avoir fermé tout vos comptes"));
                    rm.addFlashAttribute("alerts", alerts);    
                }
                return new ModelAndView("redirect:/userInfo.htm");
            } catch (FakeClientException invalidClient) {
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Il semble y avoir une erreur dans votre session"));
                } else {
                    alerts = new ArrayList<AlertMessage>(); 
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Il semble y avoir une erreur dans votre session"));
                    rm.addFlashAttribute("alerts", alerts);    
                }
                return new ModelAndView("redirect:/disconnect.htm");
            }
        }
        return new ModelAndView("redirect:/index.htm"); //ne devrait pas pouvoir arriver
    }
}
