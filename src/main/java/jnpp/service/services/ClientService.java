package jnpp.service.services;

import java.util.Date;

import jnpp.service.dto.IdentityDTO;
import jnpp.service.dto.clients.ClientDTO;
import jnpp.service.exceptions.ClosureException;
import jnpp.service.exceptions.clients.AgeException;
import jnpp.service.exceptions.clients.InformationException;
import jnpp.service.exceptions.duplicates.DuplicateClientException;
import jnpp.service.exceptions.entities.FakeClientException;

public interface ClientService {

    /**
     * Connecte un client.
     *
     * @param login    Identifiant du client.
     * @param password Mot de passe du client.
     * @return DTO du client si l'identifiant et le mot de passe sont corrects,
     *         null sinon.
     */
    ClientDTO signIn(String login, String password);

    /**
     * Deconnecte un client.
     *
     * @param login Login du client.
     * @throws FakeClientException Exception levee le login ne fait pas
     *                             reference a un client existant.
     */
    void signOut(String login) throws FakeClientException;

    /**
     * Inscrit un particulier.
     *
     * @param gender    Genre du particuler.
     * @param firstname Prenom du particulier.
     * @param lastname  Nom de famille du particulier.
     * @param birthday  Date ne naissance du particulier.
     * @param email     Email du particulier.
     * @param number    Numero de l'adresse du particulier.
     * @param street    Rue de l'adresse du particulier.
     * @param city      Ville de l'adresse du particulier.
     * @param state     Pays de l'adresse du particulier.
     * @param phone     Numero de telephone du particulier.
     * @throws DuplicateClientException Exception levee si un particulier du
     *                                  meme nom existe deja.
     * @throws AgeException             Exception levee si le particluer n'est
     *                                  pas majeur.
     * @throws InformationException     Exception levee si l'une des
     *                                  informations n'est pas valide.
     */
    void signUp(IdentityDTO.Gender gender, String firstname, String lastname,
            Date birthday, String email, Integer number, String street,
            String city, String state, String phone)
            throws DuplicateClientException, AgeException, InformationException;

    /**
     * Mes a jour les informations d'un client. Seul les arguments non null sont
     * modifies.
     *
     * @param login  Login du client.
     * @param email  Email du client.
     * @param number Numero de l'adresse du client.
     * @param street Rue de l'adresse du client.
     * @param city   Ville de l'adresse du client.
     * @param state  Pays de l'adresse du client.
     * @param phone  Numero de telephone du client.
     * @return DTO du client.
     * @throws InformationException Exception levee si l'une des informations
     *                              n'est pas valide.
     * @throws FakeClientException  Exception levee le login ne fait pas
     *                              reference a un client existant.
     */
    ClientDTO update(String login, String email, Integer number, String street,
            String city, String state, String phone)
            throws InformationException, FakeClientException;

    /**
     * Inscript un professionel.
     *
     * @param name           Nom du professionel.
     * @param ownerGender    Genre du gerant du professionel.
     * @param ownerFirstname Prenom du gerant du professionel.
     * @param ownerLastname  Nom du gerant du professionel.
     * @param email          Email du professionel.
     * @param number         Numero de l'adresse du professionel.
     * @param street         Rue de l'adresse du professionel.
     * @param city           Ville de l'adresse du professionel.
     * @param state          Pays de l'adresse du professionel.
     * @param phone          Numero de telephone du professionel..
     * @throws DuplicateClientException Exception levee si un professionel du
     *                                  meme nom existe deja.
     * @throws InformationException     Exception levee si l'une des
     *                                  informations n'est pas valide.
     */
    void signUp(String name, IdentityDTO.Gender ownerGender,
            String ownerFirstname, String ownerLastname, String email,
            Integer number, String street, String city, String state,
            String phone) throws DuplicateClientException, InformationException;

    /**
     * Ferme un compte client. Un client ne peut fermer son compte que si il n'a
     * plus aucun compte bancaire.
     *
     * @param login    Login du client.
     * @param password Mot de passe du client.
     * @throws ClosureException    Exception levee si le compte ne peut etre
     *                             ferme.
     * @throws FakeClientException Exception levee le login ne fait pas
     *                             reference a un client existant.
     */
    void close(String login, String password)
            throws ClosureException, FakeClientException;

    /**
     * Mes a jour le profile d'un client.
     *
     * @param login       Login du client.
     * @param oldPassword Ancien mot de passe du client.
     * @param newPassword Nouveau mot de passe du client.
     * @return true si le profile a ete modifie, false sinon.
     * @throws FakeClientException Exception levee le login ne fait pas
     *                             reference a un client existant.
     */
    boolean updatePassword(String login, String oldPassword, String newPassword)
            throws FakeClientException;

    /**
     * RAZ du mot de passe d'un particulier. Verifie que les informations font
     * references a un particulier. Si les informations sont bonnes, genere un
     * nouveau mot de passe aleatoirement pour un particulier et envoit un mail
     * contenant le nouveau mot de passe.
     *
     * @param login     Login du particulier
     * @param gender    Genre du particuler.
     * @param firstname Prenom du particulier.
     * @param lastname  Nom de famille du particulier.
     * @param email     Email du particulier.
     * @return true si le mot de passe a ete modifie, false sinon.
     * @throws FakeClientException Exception levee le login ne fait pas
     *                             reference a un client existant.
     */
    boolean resetPassword(String login, IdentityDTO.Gender gender,
            String firstname, String lastname, String email)
            throws FakeClientException;

    /**
     * RAZ du mot de passe d'un professionel. Verifie que les informations font
     * references a un professionel. Si les informations sont bonnes, genere un
     * nouveau mot de passe aleatoirement pour un professionel et envoit un mail
     * contenant le nouveau mot de passe.
     *
     * @param login          Login du professionel.
     * @param name           Nom du professionel.
     * @param ownerGender    Genre du gerant du professionel.
     * @param ownerFirstname Prenom du gerant du professionel.
     * @param ownerLastname  Nom du gerant du professionel.
     * @param email          Email du professionel.
     * @return true si le mot de passe a ete modifie, false sinon.
     * @throws FakeClientException Exception levee le login ne fait pas
     *                             reference a un client existant.
     */
    boolean resetPassword(String login, String name,
            IdentityDTO.Gender ownerGender, String ownerFirstname,
            String ownerLastname, String email) throws FakeClientException;

}
