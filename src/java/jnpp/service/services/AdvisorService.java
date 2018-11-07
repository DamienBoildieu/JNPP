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

    AdvisorDTO getAdvisor(String login) throws FakeClientException;

    MessageDTO sendMessage(String login, String message)
            throws FakeClientException, NoAdvisorException;

    List<MessageDTO> receiveMessages(String login)
            throws FakeClientException;

    List<MessageDTO> receiveLastMessages(String login, int n)
            throws FakeClientException;

    List<MessageDTO> receiveLastMessages(String login, Date date)
            throws FakeClientException;

    AppointmentDTO makeAppointment(String login, Date date)
            throws FakeClientException, DateException, AvailableException, NoAdvisorException;

    void cancelAppoint(String login, Long id)
            throws FakeClientException, AppointmentOwnerException, DateException;

    List<AppointmentDTO> getAppoinments(String login)
            throws FakeClientException;

    List<AppointmentDTO> getAppoinments(String login, Date date)
            throws FakeClientException;

}
