package jnpp.service;

import java.util.Date;

import jnpp.dao.entities.clients.Client;
import jnpp.dao.entities.clients.Gender;
import jnpp.dao.entities.clients.Private;
import jnpp.dao.entities.clients.Professional;
import jnpp.service.exceptions.entities.UnknownClientException;
import jnpp.service.exceptions.clients.BeOfAgeException;
import jnpp.service.exceptions.clients.ClosureException;
import jnpp.service.exceptions.clients.DuplicatedClientException;
import jnpp.service.exceptions.clients.InvalidInformationException;
import jnpp.service.exceptions.clients.InvalidUpdateException;

/** Service gerant la connexion, la deconnexion, l'inscripion, la mise a jour 
 * des informations et la fermeture d'un compte client.
 * @author Pierre Bourquat
 * @author Damien Boildieu */
public interface IClientService {
    
    /** Connecte un client.
     * @param login Identifiant du client.
     * @param password Mot de passe du client.
     * @return L'entite du client si l'identifiant et le mot de passe sont 
     * correctes, null sinon. */
    public Client signIn(String login, String password);
    
    /** Deconnecte un client.
     * @param client Client se deconnectant.
     * @throws UnknownClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant. */
    public void signOut(Client client)
            throws UnknownClientException;
    
    /** Inscrit un particulier.
     * Le particulier recoit par defaut des notifications.
     * L'identifiant et le mot de passe du particulier sont generes par le 
     * service.
     * @param gender Genre du particulier.
     * @param firstname Prenom du particulier.
     * @param lastname Nom de famille du particulier.
     * @param birthday Date de naissance.
     * @param email Adresse mail du particulier.
     * @param address Adresse physique du particulier.
     * @param phone Numero de telephone du particulier.
     * @return L'entite du particulier inscrit.
     * @throws DuplicatedClientException Exception levee si un particulier 
     * ayant la meme identite est deja inscrit. 
     * @throws BeOfAgeException Exception levee si le particulier est mineur.
     * @throws InvalidInformationException Exception levee si une des 
     * informations specifiees n'est pas valide. */
    public Private signUp(Gender gender, String firstname, String lastname,
            Date birthday, String email, String address, String phone) 
            throws DuplicatedClientException, BeOfAgeException,
            InvalidInformationException;
    
    /** Inscrit un professionel.
     * Le professionel recoit par defaut des notifications.
     * L'identifiant et le mot de passe du professionel sont generes par le 
     * service.
     * @param name Nom de l'entreprise.
     * @param ownerGender Genre du gerant de l'entreprise.
     * @param ownerFirstname Prenom du gerant de l'entreprise.
     * @param ownerLastname Nom de famille du gerant de l'entreprise.
     * @param email Adresse mail de l'entreprise.
     * @param address Adresse physique de l'entreprise.
     * @param phone Numero de telephone de l'entreprise.
     * @return L'entite de l'entreprise inscrite.
     * @throws DuplicatedClientException Exception levee si une entreprise du 
     * meme nom est deja inscrite.
     * @throws InvalidInformationException Exception levee si une des 
     * informations specifiees n'est pas valide. */
    public Professional singUp(String name, Gender ownerGender, 
            String ownerFirstname, String ownerLastname, String email, 
            String address, String phone) 
            throws DuplicatedClientException, InvalidInformationException;
    
    /** Mes a jour les informations du client.
     * @param client L'entite du client mis à jour.
     * @param information Une entite de client contenant les informations a 
     * modifier. Si cette entite ne contient que des champs null, le client ne 
     * sera pas modifie. Pour modifier uniquement la date de naissance par 
     * exemple, il faut que l'entite information n'ait que des champs null sauf 
     * le champ correspondant a la date de naissance.
     * @return L'entite du client mis a jour.
     * @throws DuplicatedClientException Exception levee si une mise a jour de 
     * l'identite du client declanche une duplication de client.
     * @throws InvalidInformationException Exception levee si une des nouvelles 
     * informations du client n'est pas valide.
     * @throws InvalidUpdateException Exception levee si la mise a jour d'un 
     * champ n'est pas autorisee.
     * @throws UnknownClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant. */
    public Client update(Client client, Client information)
            throws DuplicatedClientException, InvalidInformationException,
            InvalidUpdateException, UnknownClientException;
    
    /** Ferme un compte client.
     * Le compte d'un client ayant de l'argent ou des actions ne peut ferme.
     * @param client Client dont le compte est ferme.
     * @throws ClosureException Exception levee si le compte ne peut pas etre
     * ferme.
     * @throws UnknownClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant. */
    public void close(Client client) 
            throws ClosureException, UnknownClientException;
    
}
