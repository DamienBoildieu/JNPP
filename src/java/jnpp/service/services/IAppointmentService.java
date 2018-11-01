package jnpp.service.services;

import java.util.Date;
import java.util.List;

import jnpp.dao.entities.advisor.AppointmentEntity;
import jnpp.dao.entities.advisor.AppointmentEntity.Status;
import jnpp.dao.entities.advisor.AdvisorEntity;
import jnpp.dao.entities.clients.ClientEntity;
import jnpp.service.exceptions.owners.AdvisorOwnerException;
import jnpp.service.exceptions.entities.FakeAdvisorException;
import jnpp.service.exceptions.entities.FakeAppointmentException;
import jnpp.service.exceptions.entities.FakeClientException;
import jnpp.service.exceptions.owners.AppointmentOwnerException;

/** Service de gestion des rendez-vous.
 * @author Pierre Bourquat
 * @author Damien Boildieu */
public interface IAppointmentService extends IService {
    
    /** Retourne les rendez-vous d'un statut specifique d'un client.
     * Si le statut est null, retourne tous les rendez-vous.
     * @param client ClientEntity concerne.
     * @param status Statut de rendez-vous.
     * @return Liste de rendez-vous.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant. */
    List<AppointmentEntity> getAppointments(ClientEntity client, Status status)
            throws FakeClientException;
    
    /** Retourne les n derniers rendez-vous d'un statut specifique d'un client.
     * Si le statut est null, retourne tous les n derniers rendez-vous.
     * @param client ClientEntity concerne.
     * @param status Statut de rendez-vous.
     * @param n Nombre de rendez-vous.
     * @return Liste de rendez-vous.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant. */
    List<AppointmentEntity> getLastAppointments(ClientEntity client, Status status, int n) 
            throws FakeClientException;
    
    /** Retourne les derniers rendez-vous d'un statut specifique posterieurs a 
     * une date d'un client.
     * @param client ClientEntity concerne.
     * @param status Statut de rendez-vous.
     * @param date Date.
     * @return Liste de rendez-vous.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant. */
    List<AppointmentEntity> getLastAppointments(ClientEntity client, Status status, 
            Date date) throws FakeClientException;
    
    /** Effectue une demande de rendez-vous entre un client et un conseiller. 
     * @param client ClientEntity effectuant la demande.
     * @param advisor Conseiller.
     * @param date Date du rendez-vous.
     * @return Entite du rendez-vous.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant.
     * @throws FakeAdvisorException Exception levee si l'entite advisor ne 
     * fait pas reference a un conseillier existant.
     * @throws AdvisorOwnerException Exception levee si le client n'a pas pour 
     * conseiller le conseiller specifie. */
    AppointmentEntity makeAppointment(ClientEntity client, AdvisorEntity advisor, Date date) 
            throws FakeClientException, FakeAdvisorException,
            AdvisorOwnerException;
    
    /** Annule un rendez-vous.
     * @param client ClientEntity proprietaire du rendez-vous.
     * @param appointment Rendez-vous annule.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant.
     * @throws FakeAppointmentException Exception levee si le rendez-vous ne
     * fait pas reference a un rendez-vous existant.
     * @throws AppointmentOwnerException Exception levee si le client n'est 
     * proprietaire du rendez-vous. */
    void cancelAppointment(ClientEntity client, AppointmentEntity appointment)
            throws FakeClientException, FakeAppointmentException, 
            AppointmentOwnerException;
    
    /** Annule tous les rendez-vous d'un client. 
     * @param client ClientEntity concerne.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant. */
    void cancelAllAppointment(ClientEntity client)
            throws FakeClientException;
    
}
