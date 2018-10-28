package jnpp.service;

import java.util.Date;
import java.util.List;

import jnpp.dao.entities.accounts.Account;
import jnpp.dao.entities.clients.Client;
import jnpp.dao.entities.movements.Movement;
import jnpp.service.exceptions.entities.UnknownAccountException;
import jnpp.service.exceptions.entities.UnknownClientException;

/** Service de gestion d'historique de transactions.
 * @author Pierre Bourquat
 * @author Damien Boildieu */
public interface IMovementLogService {
    
    /** Retourne toutes les transactions d'un client.
     * @param client Client concerne.
     * @return Liste de transactions.
     * @throws UnknownClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant. */
    public List<Movement> getMovements(Client client)
            throws UnknownClientException;
    
    /** Retourne les n dernieres transactions d'un client.
     * @param client Client concerne.
     * @param n Nombre de transaction.
     * @return Liste de transactions.
     * @throws UnknownClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant. */
    public List<Movement> getMovements(Client client, int n)
            throws UnknownClientException;
    
    /** Retourne les transactions posterieurs a une data d'un client.
     * @param client Client concerne.
     * @param date Date
     * @return Liste de transactions.
     * @throws UnknownClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant. */
    public List<Movement> getMovements(Client client, Date date)
            throws UnknownClientException;
    
    /** Retourne toutes les transactions d'un compte bancaire.
     * @param account Compte bancaire concerne.
     * @return Liste de transactions.
     * @throws UnknownAccountException Exception levee si l'entite account ne 
     * fait pas reference a un compte bancaire existant. */
    public List<Movement> getMovements(Account account)
            throws UnknownAccountException;
    
    /** Retourne les n dernieres transactions d'un compte bancaire.
     * @param account Compte bancaire concerne.
     * @param n Nombre de transaction.
     * @return Liste de transactions.
     * @throws UnknownAccountException Exception levee si l'entite account ne 
     * fait pas reference a un compte bancaire existant. */
    public List<Movement> getMovements(Account account, int n)
            throws UnknownAccountException;
    
    /** Retourne les transactions posterieurs a une data d'un compte bancaire.
     * @param account Compte bancaire concerne.
     * @param date Date
     * @return Liste de transactions.
     * @throws UnknownAccountException Exception levee si l'entite client ne 
     * fait pas reference a un client existant. */
    public List<Movement> getMovements(Account account, Date date)
            throws UnknownAccountException;
    
}
