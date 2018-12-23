package jnpp.service.services;

import jnpp.service.dto.accounts.CurrencyDTO;
import jnpp.service.dto.movements.DebitDTO;
import jnpp.service.dto.movements.PurchaseDTO;
import jnpp.service.dto.movements.SaleDTO;
import jnpp.service.dto.movements.TransfertDTO;
import jnpp.service.exceptions.accounts.CurrencyException;
import jnpp.service.exceptions.accounts.NoCurrentAccountException;
import jnpp.service.exceptions.accounts.NoShareAccountException;
import jnpp.service.exceptions.entities.FakeAccountException;
import jnpp.service.exceptions.entities.FakeClientException;
import jnpp.service.exceptions.entities.FakeShareException;
import jnpp.service.exceptions.entities.FakeShareTitleException;
import jnpp.service.exceptions.movements.AccountTypeException;
import jnpp.service.exceptions.movements.AmountException;
import jnpp.service.exceptions.movements.DebitAuthorizationException;
import jnpp.service.exceptions.movements.OverdraftException;
import jnpp.service.exceptions.owners.AccountOwnerException;

public interface MovementService {

    /**
     * Transfert de l'argent d'un compte a un autre.
     *
     * @param login Login du client effectuant le transfert.
     * @param ribFrom Rib du compte d'orgine du transfert.
     * @param ribTo Rib du compte destination du transfert.
     * @param amount Quantite d'argent a transferer.
     * @param currency Devise de l'argent a transferer.
     * @param label Libelle de la transaction.
     * @return DTO de la transaction.
     * @throws FakeClientException Exception levee le login ne fait pas
     * reference a un client existant.
     * @throws FakeAccountException Exception levee si le rib ne fait pas
     * reference a un compte existant.
     * @throws AccountOwnerException Exception levee le client fermant le compte
     * n'est pas proprietaire du compte.
     * @throws AccountTypeException Exception levee si l'un des comptes ne peut
     * pas supporter cette operation.
     * @throws CurrencyException Exception levee si la devise passee ne
     * correspond pas a celle du compte origine.
     * @throws OverdraftException Exception levee si le compte d'origine ne peut
     * pas supporter les decouverts.
     */
    TransfertDTO transfertMoney(String login, String ribFrom, String ribTo,
            Double amount, CurrencyDTO currency, String label)
            throws FakeClientException, FakeAccountException, AccountOwnerException,
            AccountTypeException, CurrencyException, OverdraftException;

    /**
     * Debite de l'argent d'un compte a un autre.
     *
     * @param login Login du client effectuant le debit.
     * @param ribFrom Rib du compte d'orgine du debit.
     * @param ribTo Rib du compte destination du debit.
     * @param amount Quantite d'argent a debiter.
     * @param currency Devise de l'argent a debiter.
     * @param label Libelle de la transaction.
     * @return DTO de la transaction.
     * @throws FakeClientException Exception levee le login ne fait pas
     * reference a un client existant.
     * @throws FakeAccountException Exception levee si le rib ne fait pas
     * reference a un compte existant.
     * @throws AccountOwnerException Exception levee le client fermant le compte
     * n'est pas proprietaire du compte.
     * @throws AccountTypeException Exception levee si l'un des comptes ne peut
     * pas supporter cette operation.
     * @throws DebitAuthorizationException Exception levee si le compte orgine
     * n'est pas autorise a debiter le compte destination.
     * @throws CurrencyException Exception levee si la devise passee ne
     * correspond pas a celle du compte origine.
     * @throws OverdraftException Exception levee si le compte destination ne
     * peut pas supporter les decouverts.
     */
    DebitDTO debitMoney(String login, String ribFrom, String ribTo,
            Double amount, CurrencyDTO currency, String label)
            throws FakeClientException, FakeAccountException, AccountOwnerException,
            AccountTypeException, DebitAuthorizationException, CurrencyException, OverdraftException;

    /**
     * Achete des titres d'actions. Aucune verification sur l'existence des
     * titres a acheter. Le compte courant du client est debite pour payer les
     * actions achetes.
     *
     * @param login Login ducclient effectuant l'achat.
     * @param name Nom de l'action a acheter.
     * @param amount Quantite de titre d'action a acheter.
     * @param label Libelle de la transaction.
     * @return DTO de la transaction.
     * @throws FakeClientException Exception levee le login ne fait pas
     * reference a un client existant.
     * @throws NoCurrentAccountException Exception levee si le client n'a pas de
     * compte courant.
     * @throws NoShareAccountException Exception levee si le client n'a pas de
     * d'actions.
     * @throws FakeShareException Exceptin levee si le nom de l'action ne fait
     * pas reference a une action existante.
     */
    PurchaseDTO purchaseShareTitles(String login, String name,
            Integer amount, String label)
            throws FakeClientException, NoCurrentAccountException,
            NoShareAccountException, FakeShareException;

    /**
     * Vend des actions. L'argent de vente est transferer vers le compte courant
     * du client.
     *
     * @param login Login ducclient effectuant la vente.
     * @param name Nom de l'action a vendre.
     * @param amount Quantite de titre d'action a vendre.
     * @param label Libelle de la transaction.
     * @return DTO de la transaction.
     * @throws FakeClientException Exception levee le login ne fait pas
     * reference a un client existant.
     * @throws NoCurrentAccountException Exception levee si le client n'a pas de
     * compte courant.
     * @throws NoShareAccountException Exception levee si le client n'a pas de
     * d'actions.
     * @throws FakeShareTitleException Exception levee si le rib et le nom de
     * l'action de font pas reference a un titre d'action existant.
     * @throws AmountException Exception levee si la quantite d'action a vendre
     * est superieur a nombre de titres d'actions poseedes.
     */
    SaleDTO saleShareTitles(String login, String name,
            Integer amount, String label)
            throws FakeClientException, NoCurrentAccountException,
            NoShareAccountException, FakeShareTitleException, AmountException;

}
