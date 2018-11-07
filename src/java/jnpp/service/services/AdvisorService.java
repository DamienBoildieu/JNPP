package jnpp.service.services;

import java.util.Date;
import java.util.List;
import jnpp.service.dto.advisor.AdvisorDTO;
import jnpp.service.dto.advisor.AppointmentDTO;
import jnpp.service.dto.advisor.MessageDTO;
import jnpp.service.exceptions.advisors.AvailableException;
import jnpp.service.exceptions.advisors.DateException;
import jnpp.service.exceptions.advisors.NoAdvisorException;
import jnpp.service.exceptions.duplicates.DuplicateAppointmentException;
import jnpp.service.exceptions.entities.FakeClientException;
import jnpp.service.exceptions.owners.AppointmentOwnerException;

public interface AdvisorService {

    /**
     * Retourne le conseiller d'un client.
     * @param login Login du client. 
     * @return DTO du client. null si le client n'a pas de conseiller.
     * @throws FakeClientException Exception levee le login ne fait pas reference 
     * a un client existant.
     */
    AdvisorDTO getAdvisor(String login) throws FakeClientException;

    /**
     * Envoit un message a son conseiller.
     * @param login Login du client envoyant le message.
     * @param message Contenu du message.
     * @return DTO du message.
     * @throws FakeClientException Exception levee le login ne fait pas reference 
     * a un client existant.
     * @throws NoAdvisorException Exception levee si le client n' pas de 
     * conseiller.
     */
    MessageDTO sendMessage(String login, String message)
            throws FakeClientException, NoAdvisorException;

    /**
     * Retourne les messages d'un client.
     * @param login Login du client.
     * @return Liste de DTO de messages.
     * @throws FakeClientException Exception levee le login ne fait pas reference 
     * a un client existant.
     */
    List<MessageDTO> receiveMessages(String login)
            throws FakeClientException;

    /**
     * Retourne des messages d'un client.
     * @param login Login du client.
     * @param n Nombre de messages.
     * @return Liste de DTO de messages.
     * @throws FakeClientException Exception levee le login ne fait pas reference 
     * a un client existant.
     */
    List<MessageDTO> receiveLastMessages(String login, int n)
            throws FakeClientException;
    
    /**
     * Retourne les messages d'un client posterieurs a une date.
     * @param login Login du client.
     * @param date Date.
     * @return Liste de DTO de messages.
     * @throws FakeClientException Exception levee le login ne fait pas reference 
     * a un client existant.
     */
    List<MessageDTO> receiveLastMessages(String login, Date date)
            throws FakeClientException;

    /**
     * Prend un rendez-vous avec son conseiller.
     * @param login Login du client.
     * @param date Date du rendez-vous.
     * @return DTO du rendez-vous.
     * @throws FakeClientException Exception levee le login ne fait pas reference 
     * a un client existant.
     * @throws DateException Exception levee si la date n'est pas valide.
     * @throws AvailableException Exception levee si le conseiller n'est pas 
     * disponible.
     * @throws NoAdvisorException Exception levee si le client n' pas de 
     * conseiller.
     */
    AppointmentDTO makeAppointment(String login, Date date)
            throws FakeClientException, DateException, AvailableException, NoAdvisorException;

    /**
     * Annule un rendez-vous avec son conseiller.
     * @param login Login du client.
     * @param id Identifiant du rendez-vous.
     * @throws FakeClientException Exception levee le login ne fait pas reference 
     * a un client existant.
     * @throws AppointmentOwnerException Exception levee si le client n'est pas
     * proprietaire du rendez-vous a annuler.
     * @throws DateException Exception levee si le rendez-vous a annuler est dans 
     * le passe.
     */
    void cancelAppoint(String login, Long id)
            throws FakeClientException, AppointmentOwnerException, DateException;

    /**
     * Retourn les rendez-vous d'un client.
     * @param login Login du client.
     * @return Liste de DTO de rendez-vous.
     * @throws FakeClientException Exception levee le login ne fait pas reference 
     * a un client existant.
     */
    List<AppointmentDTO> getAppoinments(String login)
            throws FakeClientException;

    /**
     * Retourn les rendez-vous d'un client posterieur a une date.
     * @param login Login du client.
     * @param date Date.
     * @return Liste de DTO de rendez-vous.
     * @throws FakeClientException Exception levee le login ne fait pas reference 
     * a un client existant.
     */
    List<AppointmentDTO> getAppoinments(String login, Date date)
            throws FakeClientException;

}
