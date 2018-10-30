package jnpp.service;

import java.util.Date;
  
import jnpp.dao.entities.clients.Client;
import jnpp.dao.entities.clients.Gender;
import jnpp.dao.entities.clients.Private;
import jnpp.dao.entities.clients.Professional;
import jnpp.service.exceptions.entities.FakeClientException;
import jnpp.service.exceptions.clients.AgeException;
import jnpp.service.exceptions.ClosureException;
import jnpp.service.exceptions.duplicates.DuplicateClientException;
import jnpp.service.exceptions.clients.InformationException;

/** Service de gestion des clients.
 * @author Pierre Bourquat
 * @author Damien Boildieu */
public interface IClientService extends IService {
    
    /** Connecte un client.
     * @param login Identifiant du client.
     * @param password Mot de passe du client.
     * @return L'entite du client si l'identifiant et le mot de passe sont 
     * correctes, null sinon. */
    Client signIn(String login, String password);
    
    /** Deconnecte un client.
     * @param client Client se deconnectant.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant. */
    void signOut(Client client)
            throws FakeClientException;
    
    /** Inscrit un particulier.
     * Le particulier recoit par defaut des notifications.
     * L'identifiant et le mot de passe du particulier sont generes par le 
     * service.
     * @param gender Genre du particulier.
     * @param firstname Prenom du particulier.
     * @param lastname Nom de famille du particulier.
     * @param birthday Date de naissance.
     * @param email Adresse mail du particulier.
     * @param number Numero de l'adresse du particulier.
     * @param street Rue de l'adresse du particulier.
     * @param city Ville de l'adresse du particulier.
     * @param state Pays de l'adresse du particulier.
     * @param phone Numero de telephone du particulier.
     * @throws DuplicateClientException Exception levee si un particulier 
     * ayant la meme identite est deja inscrit. 
     * @throws AgeException Exception levee si le particulier est mineur.
     * @throws InformationException Exception levee si une des 
     * informations specifiees n'est pas valide. */
    void signUp(Gender gender, String firstname, String lastname,
            Date birthday, String email, Integer number, String street, 
            String city, String state, String phone) 
            throws DuplicateClientException, AgeException,
            InformationException;
    
    /** Inscrit un professionel.
     * Le professionel recoit par defaut des notifications.
     * L'identifiant et le mot de passe du professionel sont generes par le 
     * service.
     * @param name Nom de l'entreprise.
     * @param ownerGender Genre du gerant de l'entreprise.
     * @param ownerFirstname Prenom du gerant de l'entreprise.
     * @param ownerLastname Nom de famille du gerant de l'entreprise.
     * @param email Adresse mail de l'entreprise.
     * @param number Numero de l'adresse de l'entreprise.
     * @param street Rue de l'adresse de l'entreprise.
     * @param city Ville de l'adresse de l'entreprise.
     * @param state Pays de l'adresse de l'entreprise.
     * @param phone Numero de telephone de l'entreprise.
     * @throws DuplicateClientException Exception levee si une entreprise du 
     * meme nom est deja inscrite.
     * @throws InformationException Exception levee si une des 
     * informations specifiees n'est pas valide. */
    void signUp(String name, Gender ownerGender, 
            String ownerFirstname, String ownerLastname, String email, 
            Integer number, String street, String city, String state, 
            String phone) 
            throws DuplicateClientException, InformationException;
    
    /** Mes a jour les informations d'un client. Les parametres non null sont 
     * modifies.
     * @param client L'entite du client mis à jour.
     * @param email Nouvel adresse mail.
     * @param number Nouveau numero de rue.
     * @param street Nouveau nom de rue.
     * @param city Nouveau nom de ville.
     * @param state Nouveau nom de pays.
     * @param phone Nouveau numero de telephone.
     * @return L'entite du client mis a jour.
     * @throws InformationException Exception levee si une des nouvelles 
     * informations du client n'est pas valide.
     * @throws FakeClientException Exception levee si l'entite particulier ne 
     * fait pas reference a un client existant. */
    Client update(Client client,  String email, 
            Integer number, String street, String city, String state, 
            String phone)
            throws FakeClientException, InformationException; 
    
    /** Ferme un compte client.
     * Le compte d'un client ayant de l'argent ou des actions ne peut ferme.
     * @param client Client dont le compte est ferme.
     * @throws ClosureException Exception levee si le compte ne peut pas etre
     * ferme.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant. */
    void close(Client client) 
            throws ClosureException, FakeClientException;
    
    /** Retourne l'identifiant d'un client.
     * @param client Client concerne.
     * @return Identifiant du client.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant. */
    String getLogin(Client client) throws FakeClientException;
    
    /** Modifie le mot de passe d'un client.
     * @param client Client concerne.
     * @param oldPassword Ancien mot de passe.
     * @param newPassword Nouveau mot de passe.
     * @return True si l'ancien mot de passe est correcte, false sinon.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant. */
    boolean updatePassword(Client client, String oldPassword, 
            String newPassword) throws FakeClientException;
    
    /** Genere un nouveau mot de passe pour un particulier identifie par son 
     * nom et son adresse mail.
     * @param login Idendifiant du particulier.
     * @param firstname Prenom du particulier.
     * @param lastname Nom de famille du particulier.
     * @param email Adresse mail du particulier.
     * @return False si les informations du particulier ne font pas reference a 
     * un particulier existant. True si les informations sont bonnes et que le 
     * mot de passe a ete reinitialise. */
    boolean resetPassword(String login, String firstname, 
            String lastname, String email);
    
    /** Genere un nouveau mot de passe pour un professionel identifie par son 
     * nom, le nom de son gerant et son email.
     * @param login Identifiant du professionel.
     * @param name Nom du professionel.
     * @param ownerFirstname Prenom du gerant du professionel.
     * @param ownerLastname Nom de famille du gerant du professionel.
     * @param email Adresse mail du professionel.
     * @return False si les informations du professionel ne font pas reference 
     * a un professionel existant. True si les informations sont bonnes et que 
     * le mot de passe a ete reinitialise. */
    boolean resetPassword(String login, String name, 
            String ownerFirstname, String ownerLastname, String email);
    
}
