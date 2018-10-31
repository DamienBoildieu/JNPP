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
import jnpp.controller.views.info.ViewInfo;
import jnpp.dao.entities.clients.Client;
import jnpp.dao.entities.clients.Gender;
import jnpp.dao.entities.clients.Private;
import jnpp.dao.entities.clients.Professional;
import jnpp.service.IClientService;
import jnpp.service.exceptions.clients.AgeException;
import jnpp.service.exceptions.duplicates.DuplicateClientException;
import jnpp.service.exceptions.clients.InformationException;
import jnpp.service.exceptions.entities.FakeClientException;

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
                CSession.setHasNotif(session, false);
                CSession.setClient(session, client);
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
    @RequestMapping(value = "privatesignup", method = RequestMethod.POST)
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
                    rm.addFlashAttribute("alerts", alerts);    
                }
                return new JNPPModelAndView("signup/privatesignup", ViewInfo.createInfo(session, alerts));
            } catch (AgeException age) {
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Un client ne peut pas être mineur"));
                } else {
                    alerts = new ArrayList<AlertMessage>(); 
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Un client ne peut pas être mineur"));
                    rm.addFlashAttribute("alerts", alerts);    
                }
                return new JNPPModelAndView("signup/privatesignup", ViewInfo.createInfo(session, alerts));
            } catch (InformationException invalidFormat) {
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Une erreur est présente dans le formulaire"));
                } else {
                    alerts = new ArrayList<AlertMessage>(); 
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Une erreur est présente dans le formulaire"));
                    rm.addFlashAttribute("alerts", alerts);    
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
            } catch (InformationException invalidFormat) {
                return new JNPPModelAndView("signup/professionalsignup", ViewInfo.createInfo(session, alerts));
                /* TODO
                if (alerts != null) {
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Une erreur est présente dans le formulaire"));
                } else {
                    alerts = new ArrayList<AlertMessage>(); 
                    alerts.add(new AlertMessage(AlertEnum.ERROR, "Une erreur est présente dans le formulaire"));
                    rm.addFlashAttribute("alerts", alerts);    
                }
                return new JNPPModelAndView("signup/professionalsignup", ViewInfo.createInfo(session, alerts));
                */
            }
        }
        return new ModelAndView("redirect:/index.htm"); //ne devrait pas arriver
    }
    
    /**
     * Requête du formulaire de perte de mot de passe d'un particulier
     * @param model le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @param response la réponse
     * @param rm objet dans lequel on ajoute les informations que l'on veut voir transiter lors des redirections
     * @return Une redirection vers le menu utilisateur si la demande a réussie, une redirection vers le formulaire de mot de passe si elle a échouée,
     * une redireciton vers l'index si l'utilisateur était déjà connecté
     * @throws Exception 
     */
    @RequestMapping(value = "privatepassword", method = RequestMethod.POST)
    protected ModelAndView privateResetPassword(Model model, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rm) throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>)model.asMap().get("alerts");
        if (session==null)
            session = request.getSession(true);
        if (CSession.getLanguage(session)!=Translator.Language.FR)
            CSession.setLanguage(session,Translator.Language.FR);
        if (!CSession.isConnected(session)) {
            String id = request.getParameter("id");
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String email = request.getParameter("email");
            if (clientService.resetPassword(id, firstName, lastName, email)) {
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
                    rm.addFlashAttribute("alerts", alerts);    
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
     * @param response la réponse
     * @param rm objet dans lequel on ajoute les informations que l'on veut voir transiter lors des redirections
     * @return Une redirection vers le menu utilisateur si la demande a réussie, une redirection vers le formulaire de mot de passe si elle a échouée,
     * une redireciton vers l'index si l'utilisateur était déjà connecté
     * @throws Exception 
     */
    @RequestMapping(value = "professionalpassword", method = RequestMethod.POST)
    protected ModelAndView professionalResetPassword(Model model, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rm) throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>)model.asMap().get("alerts");
        if (session==null)
            session = request.getSession(true);
        if (CSession.getLanguage(session)!=Translator.Language.FR)
            CSession.setLanguage(session,Translator.Language.FR);
        if (!CSession.isConnected(session)) {
            String id = request.getParameter("id");
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String company = request.getParameter("company");
            String email = request.getParameter("email");
            if (clientService.resetPassword(id, company, firstName, lastName, email)) {
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
                    rm.addFlashAttribute("alerts", alerts);    
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
     * @param response la réponse
     * @param rm objet dans lequel on ajoute les informations que l'on veut voir transiter lors des redirections
     * @return La vue d'information client
     * @throws Exception 
     */
    @RequestMapping(value = "changepassword", method = RequestMethod.POST)
    protected ModelAndView changePassword(Model model, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rm, String view) throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>)model.asMap().get("alerts");
        if (session==null)
            session = request.getSession(true);
        if (CSession.getLanguage(session)!=Translator.Language.FR)
            CSession.setLanguage(session,Translator.Language.FR);
        if (CSession.isConnected(session)) {
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
                clientService.updatePassword(CSession.getClient(session), old, newp);
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
                return new ModelAndView("redirect:/userinfo.htm");
            }
        }
        return new ModelAndView("redirect:/index.htm"); //ne devrait pas pouvoir arriver
    }
    /**
     * Requête du formulaire de changement d'information d'un utilisateur
     * @param model le model contient les alertes si il y a eu un redirect
     * @param request la requête
     * @param response la réponse
     * @param rm objet dans lequel on ajoute les informations que l'on veut voir transiter lors des redirections
     * @return La vue d'information client
     * @throws Exception 
     */
    @RequestMapping(value = "editinfo", method = RequestMethod.POST)
    private ModelAndView validateInfo(Model model, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rm, String view)
            throws Exception {
        HttpSession session = request.getSession();
        List<AlertMessage> alerts = (List<AlertMessage>)model.asMap().get("alerts");
        if (session==null)
            session = request.getSession(true);
        if (CSession.getLanguage(session)!=Translator.Language.FR)
            CSession.setLanguage(session,Translator.Language.FR);
        if (CSession.isConnected(session)) {
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
                Client client = clientService.update(CSession.getClient(session), email, streetNbr, street, city, country, phone);
                CSession.setClient(session, client);
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
                return new ModelAndView("redirect:/userinfo.htm");
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
}
