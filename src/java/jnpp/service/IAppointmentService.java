package jnpp.service;

import java.util.Date;
import java.util.List;

import jnpp.dao.entities.Appointment;
import jnpp.dao.entities.Appointment.Status;
import jnpp.dao.entities.clients.Advisor;
import jnpp.dao.entities.clients.Client;
import jnpp.service.exceptions.WrongAdvisorException;
import jnpp.service.exceptions.entities.UnknownAdvisorException;
import jnpp.service.exceptions.entities.UnknownAppointmentException;
import jnpp.service.exceptions.entities.UnknownClientException;

/** Service de gestion des rendez-vous.
 * @author Pierre Bourquat
 * @author Damien Boildieu */
public interface IAppointmentService {
    
    /** Retourne les rendez-vous d'un statut specifique d'un client.
     * Si le statut est null, retourne tous les rendez-vous.
     * @param client Client concerne.
     * @param status Statut de rendez-vous.
     * @return Liste de rendez-vous.
     * @throws UnknownClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant. */
    public List<Appointment> getAppointments(Client client, Status status)
            throws UnknownClientException;
    
    /** Retourne les n derniers rendez-vous d'un statut specifique d'un client.
     * Si le statut est null, retourne tous les n derniers rendez-vous.
     * @param client Client concerne.
     * @param status Statut de rendez-vous.
     * @param n Nombre de rendez-vous.
     * @return Liste de rendez-vous.
     * @throws UnknownClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant. */
    public List<Appointment> getLastAppointments(Client client, Status status, 
            int n) throws UnknownClientException;
    
    /** Retourne les derniers rendez-vous d'un statut specifique posterieurs a 
     * une date d'un client.
     * @param client Client concerne.
     * @param status Statut de rendez-vous.
     * @param date Date.
     * @return Liste de rendez-vous.
     * @throws UnknownClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant. */
    public List<Appointment> getLastAppointments(Client client, Status status, 
            Date date) throws UnknownClientException;
    
    /** Effectue une demande de rendez-vous entre un client et un conseiller. 
     * @param client Client effectuant la demande.
     * @param advisor Conseiller.
     * @param date Date du rendez-vous.
     * @return Entite du rendez-vous.
     * @throws UnknownClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant.
     * @throws UnknownAdvisorException Exception levee si l'entite advisor ne 
     * fait pas reference a un conseillier existant.
     * @throws WrongAdvisorException Exception levee si le client n'a pas pour 
     * conseiller le conseiller specifie. */
    public Appointment makeAppointment(Client client, Advisor advisor, 
            Date date) throws UnknownClientException, UnknownAdvisorException,
            WrongAdvisorException;
    
    /** Annule un rendez-vous.
     * @param appointment Rendez-vous annule.
     * @throws UnknownAppointmentException Exception levee si le rendez-vous ne
     * fait pas reference a un rendez-vous existant. */
    public void cancelAppointment(Appointment appointment)
            throws UnknownAppointmentException;
    
    /** Annule tous les rendez-vous d'un client. 
     * @param client Client concerne.
     * @throws UnknownClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant. */
    public void cancelAllAppointment(Client client)
            throws UnknownClientException;
    
}
