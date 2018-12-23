package jnpp.service.services;

import java.util.List;
import jnpp.service.dto.IdentityDTO;
import jnpp.service.dto.accounts.AccountDTO;
import jnpp.service.dto.accounts.CurrencyDTO;
import jnpp.service.dto.accounts.SavingBookDTO;
import jnpp.service.dto.accounts.ShareDTO;
import jnpp.service.dto.advisor.AdvisorDTO;
import jnpp.service.dto.advisor.MessageDTO;
import jnpp.service.dto.clients.LoginDTO;
import jnpp.service.dto.movements.DebitDTO;
import jnpp.service.dto.movements.DepositDTO;
import jnpp.service.dto.movements.PurchaseDTO;
import jnpp.service.dto.movements.SaleDTO;
import jnpp.service.dto.movements.TransfertDTO;
import jnpp.service.dto.paymentmeans.PaymentMeanDTO;
import jnpp.service.exceptions.accounts.NoCurrentAccountException;
import jnpp.service.exceptions.advisors.NoAdvisorException;
import jnpp.service.exceptions.duplicates.DuplicateAdvisorException;
import jnpp.service.exceptions.duplicates.DuplicateSavingbookException;
import jnpp.service.exceptions.duplicates.DuplicateShareException;
import jnpp.service.exceptions.entities.FakeAccountException;
import jnpp.service.exceptions.entities.FakeAdvisorException;
import jnpp.service.exceptions.entities.FakeClientException;
import jnpp.service.exceptions.entities.FakePaymentMeanException;
import jnpp.service.exceptions.entities.FakeShareException;
import jnpp.service.exceptions.entities.FakeShareTitleException;
import jnpp.service.exceptions.movements.AccountTypeException;
import jnpp.service.exceptions.movements.AmountException;
import jnpp.service.exceptions.movements.DebitAuthorizationException;
import jnpp.service.exceptions.movements.OverdraftException;

public interface BankerService {

    /**
     * Ajoute une action.
     *
     * @param name Nom de l'action.
     * @param value Valeur de l'action.
     * @param currency Devise de l'action.
     * @return DTO de l'action.
     * @throws DuplicateShareException Exception levee si une action du meme nom
     * existe deja.
     */
    ShareDTO addShare(String name, Double value, CurrencyDTO currency)
            throws DuplicateShareException;

    /**
     * Ajout un livret.
     *
     * @param name Nom du livret.
     * @param moneyRate Taux du livret.
     * @param timeRate Nombre de calcul du livret par annee.
     * @return DTO du livret.
     * @throws DuplicateSavingbookException Exception levee si un livret du meme
     * nom existe deja.
     */
    SavingBookDTO addSavingbook(String name, Double moneyRate, Double timeRate)
            throws DuplicateSavingbookException;

    /**
     * Retourne les logins des clients.
     *
     * @return Liste de DTO de login.
     */
    List<LoginDTO> getClientLogins();

    /**
     * Retourne les conseillers.
     *
     * @return Liste de DTO de conseillers.
     */
    List<AdvisorDTO> getAdvisors();

    /**
     * Ajoute un conseiller.
     *
     * @param gender Genre du conseiller.
     * @param firstname Prenom du conseiller.
     * @param lastname Nom de famille du conseiller.
     * @param email Email du conseiller.
     * @param phone Telephone du conseiller.
     * @param number Numero de l'adresse du bureau du conseiller.
     * @param street Rue de l'adresse du bureau du conseiller.
     * @param city Ville de l'adresse du bureau du conseiller.
     * @param state Pays de l'adresse du bureau du conseiller.
     * @return DTO du conseiller.
     * @throws DuplicateAdvisorException Exception levee si un conseiller du
     * meme nom existe deja.
     */
    AdvisorDTO addAdvisor(IdentityDTO.Gender gender, String firstname,
            String lastname, String email, String phone, Integer number,
            String street, String city, String state)
            throws DuplicateAdvisorException;

    /**
     * Retourne les logins des clients d'un conseiller.
     *
     * @param firstname Prenom du conseiller.
     * @param lastname Nom du conseiller.
     * @return List de DTO de login.
     * @throws FakeAdvisorException Exception levee si l'identite ne fait pas
     * reference a un conseiller existant
     */
    List<LoginDTO> getAdvisorLogins(String firstname, String lastname)
            throws FakeAdvisorException;

    /**
     * Retourne un DTO login d'un client.
     *
     * @param login Lofin du client.
     * @return DTO de login.
     * @throws FakeClientException Exception levee le login ne fait pas
     * reference a un client existant.
     */
    LoginDTO getLogin(String login)
            throws FakeClientException;

    /**
     * Un conseiller envoit un message a un de ses clients.
     *
     * @param login Login du client.
     * @param content Contenu du client.
     * @return DTO du message.
     * @throws FakeClientException Exception levee le login ne fait pas
     * reference a un client existant.
     * @throws NoAdvisorException Exception levee si le client n'a pas de
     * conseiller.
     */
    MessageDTO sendMessage(String login, String content)
            throws FakeClientException, NoAdvisorException;

    /**
     * Retourne les moyens de paiements.
     *
     * @return Liste de DTO de moyens de paiements.
     */
    List<PaymentMeanDTO> getPaymentMeans();

    /**
     * Change le statut d'un moyen de paiement.
     *
     * @param id Identifiant du moyne de paiement.
     * @return DTO du moyen de paiment.
     * @throws FakePaymentMeanException Exception levees si l'identifiant ne
     * fait pas reference a un moyen de paiement existant.
     */
    PaymentMeanDTO upgradePaymentMean(String id) throws FakePaymentMeanException;

    /**
     * Retoure une liste des comptes bancaires.
     *
     * @return List de DTO des comptes.
     */
    List<AccountDTO> getAccounts();

    /**
     * Effectue un depot.
     *
     * @param rib Rib du compte destination du depot.
     * @param amount Quantite d'argent a deposer.
     * @param currency Devise de l'argent a deposer.
     * @param label Libelle de la transaction bancaire.
     * @return DTO du depot.
     * @throws FakeAccountException Exception levee si le rib ne fait pas
     * reference a un compte existant.
     * @throws AccountTypeException Exception levee si le compte ne peut pas
     * supporter cette operation.
     */
    DepositDTO deposit(String rib, Double amount, CurrencyDTO currency, String label)
            throws FakeAccountException, AccountTypeException;

    /**
     * Effectur un transfert.
     *
     * @param ribFrom Rib du compte d'origine du transfert.
     * @param ribTo Rib du compte destination du transfert.
     * @param amount Quantite d'argent a transferer.
     * @param currency Devise de l'argent a transferer.
     * @param label Libelle de la transaction bancaire.
     * @return DTO du transfert.
     * @throws FakeAccountException Exception levee si le rib ne fait pas
     * reference a un compte existant.
     * @throws AccountTypeException Exception levee si le compte ne peut pas
     * supporter cette operation.
     * @throws OverdraftException Exception levee si le compte d'origine ne peut
     * pas supporter les decouverts.
     */
    TransfertDTO transfert(String ribFrom, String ribTo, Double amount, CurrencyDTO currency, String label)
            throws FakeAccountException, AccountTypeException, OverdraftException;

    /**
     * Effectur un debit.
     *
     * @param ribFrom Rib du compte d'origine du debit.
     * @param ribTo Rib du compte destination du debit.
     * @param amount Quantite d'argent a debiter.
     * @param currency Devise de l'argent a debiter.
     * @param label Libelle de la transaction bancaire.
     * @return DTO du debit.
     * @throws FakeAccountException Exception levee si le rib ne fait pas
     * reference a un compte existant.
     * @throws AccountTypeException Exception levee si le compte ne peut pas
     * supporter cette operation.
     * @throws DebitAuthorizationException Exception levee si le compte
     * d'origine ne peut pas debiter le compte destination.
     * @throws OverdraftException Exception levee si le compte d'origine ne peut
     * pas supporter les decouverts.
     */
    DebitDTO debit(String ribFrom, String ribTo, Double amount, CurrencyDTO currency, String label)
            throws FakeAccountException, AccountTypeException, DebitAuthorizationException, OverdraftException;

    /**
     * Achete des actions.
     *
     * @param rib Rib du compte bancaire.
     * @param name Nom de l'action a acheter.
     * @param amount Quantite d'action a acheter.
     * @param label Libelle de la transaction bancaire.
     * @return DTO de l'achat.
     * @throws FakeAccountException Exception levee si le rib ne fait pas
     * reference a un compte existant.
     * @throws FakeShareException Exceptin levee si le nom de l'action ne fait
     * pas reference a une action existante.
     * @throws NoCurrentAccountException Exception levee si le client
     * proprietaire du compte n'a pas de compte courant.
     * @throws AccountTypeException Exception levee si le compte ne peut pas
     * supporter cette operation.
     */
    PurchaseDTO purchase(String rib, String name, Integer amount, String label)
            throws FakeAccountException, FakeShareException, NoCurrentAccountException, AccountTypeException;

    /**
     * Vend des actions.
     *
     * @param rib Rib du compte bancaire.
     * @param name Nom de l'action a vendre.
     * @param amount Quantite d'action a vendre.
     * @param label Libelle de la transaction bancaire.
     * @return DTO de l'a vente.
     * @throws FakeAccountException Exception levee si le rib ne fait pas
     * reference a un compte existant.
     * @throws FakeShareException Exceptin levee si le nom de l'action ne fait
     * pas reference a une action existante.
     * @throws NoCurrentAccountException Exception levee si le client
     * proprietaire du compte n'a pas de compte courant.
     * @throws AccountTypeException Exception levee si le compte ne peut pas
     * supporter cette operation.
     * @throws AmountException Exception levee si la quantite d'action a vendre
     * est superieur a nombre de titres d'actions poseedes.
     * @throws FakeShareTitleException Exception levee si le rib et le nom de
     * l'action de font pas reference a un titre d'action existant.
     */
    SaleDTO sale(String rib, String name, Integer amount, String label)
            throws FakeAccountException, FakeShareException, NoCurrentAccountException, AccountTypeException, AmountException, FakeShareTitleException;

}
