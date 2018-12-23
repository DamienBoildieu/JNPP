package jnpp.service.services;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import jnpp.dao.entities.advisor.AdvisorEntity;
import jnpp.dao.entities.advisor.AppointmentEntity;
import jnpp.dao.entities.advisor.MessageEntity;
import jnpp.dao.entities.clients.ClientEntity;
import jnpp.dao.repositories.AppointmentDAO;
import jnpp.dao.repositories.ClientDAO;
import jnpp.dao.repositories.MessageDAO;
import jnpp.service.dto.advisor.AdvisorDTO;
import jnpp.service.dto.advisor.AppointmentDTO;
import jnpp.service.dto.advisor.MessageDTO;
import jnpp.service.exceptions.advisors.AvailableException;
import jnpp.service.exceptions.advisors.DateException;
import jnpp.service.exceptions.advisors.NoAdvisorException;
import jnpp.service.exceptions.entities.FakeClientException;
import jnpp.service.exceptions.owners.AppointmentOwnerException;
import org.springframework.stereotype.Service;

@Service("AdvisorService")
public class AdvisorServiceImpl implements AdvisorService {

    public static final long CANCEL_APPOINTMENT_DELTA = 3600;
    public static final long MAKE_APPOINTMENT_DELTA = 3600;
    public static final long APPOINTMENT_DURATION = 3600;

    @Resource
    ClientDAO clientDAO;
    @Resource
    MessageDAO messageDAO;
    @Resource
    AppointmentDAO appointmentDAO;

    @Override
    public AdvisorDTO getAdvisor(String login) throws FakeClientException {
        if (login == null) {
            throw new IllegalArgumentException();
        }
        ClientEntity client = clientDAO.find(login);
        if (client == null) {
            throw new FakeClientException();
        }
        AdvisorEntity advisor = client.getAdvisor();
        return (advisor != null) ? advisor.toDTO() : null;
    }

    @Override
    public MessageDTO sendMessage(String login, String message) throws FakeClientException, NoAdvisorException {
        if (login == null || message == null || message.length() == 0) {
            throw new IllegalArgumentException();
        }
        ClientEntity client = clientDAO.find(login);
        if (client == null) {
            throw new FakeClientException();
        }
        if (client.getAdvisor() == null) {
            throw new NoAdvisorException();
        }
        MessageEntity entity = new MessageEntity(client, client.getAdvisor(),
                MessageEntity.Direction.CLIENT_TO_ADVISOR, Date.from(Instant.now()), message);
        entity = messageDAO.save(entity);
        return entity.toDTO();
    }

    @Override
    public List<MessageDTO> receiveMessages(String login) throws FakeClientException {
        if (login == null) {
            throw new IllegalArgumentException();
        }
        ClientEntity client = clientDAO.find(login);
        if (client == null) {
            throw new FakeClientException();
        }
        List<MessageEntity> messages = messageDAO.findAllByLogin(login);
        return MessageEntity.toDTO(messages);
    }

    @Override
    public List<MessageDTO> receiveLastMessages(String login, int n) throws FakeClientException {
        if (login == null || n < 1) {
            throw new IllegalArgumentException();
        }
        ClientEntity client = clientDAO.find(login);
        if (client == null) {
            throw new FakeClientException();
        }
        List<MessageEntity> messages = messageDAO.findNByLogin(login, n);
        return MessageEntity.toDTO(messages);
    }

    @Override
    public List<MessageDTO> receiveLastMessages(String login, Date date) throws FakeClientException {
        if (login == null || date == null) {
            throw new IllegalArgumentException();
        }
        ClientEntity client = clientDAO.find(login);
        if (client == null) {
            throw new FakeClientException();
        }
        List<MessageEntity> messages = messageDAO.findRecentByLogin(login, date);
        return MessageEntity.toDTO(messages);
    }

    @Override
    public AppointmentDTO makeAppointment(String login, Date date) throws FakeClientException, DateException, AvailableException, NoAdvisorException {
        if (login == null || date == null) {
            throw new IllegalArgumentException();
        }
        ClientEntity client = clientDAO.find(login);
        if (client == null) {
            throw new FakeClientException();
        }
        if (client.getAdvisor() == null) {
            throw new NoAdvisorException();
        }
        Date limit = Date.from(Instant.now().plusSeconds(MAKE_APPOINTMENT_DELTA));
        if (date.before(limit)) {
            throw new DateException();
        }
        Date min = Date.from(date.toInstant().minusSeconds(APPOINTMENT_DURATION));
        Date max = Date.from(date.toInstant().plusSeconds(APPOINTMENT_DURATION));
        AdvisorEntity advisor = client.getAdvisor();
        if (appointmentDAO.countByAdvisorIdInMinMax(advisor.getId(), min, max) > 0) {
            throw new AvailableException();
        }
        AppointmentEntity appointment = new AppointmentEntity(date, client, advisor);
        appointment = appointmentDAO.save(appointment);
        return appointment.toDTO();
    }

    @Override
    public void cancelAppoint(String login, Long id) throws FakeClientException, AppointmentOwnerException, DateException {
        if (login == null || id == null) {
            throw new IllegalArgumentException();
        }
        ClientEntity client = clientDAO.find(login);
        if (client == null) {
            throw new FakeClientException();
        }
        AppointmentEntity appointment = appointmentDAO.find(id);
        if (appointment == null || !client.equals(appointment.getClient())) {
            throw new AppointmentOwnerException();
        }
        Date date = appointment.getDate();
        Date limit = Date.from(Instant.now().plusSeconds(CANCEL_APPOINTMENT_DELTA));
        if (limit.after(date)) {
            throw new DateException();
        }
        appointmentDAO.delete(appointment);
    }

    @Override
    public List<AppointmentDTO> getAppoinments(String login) throws FakeClientException {
        if (login == null) {
            throw new IllegalArgumentException();
        }
        ClientEntity client = clientDAO.find(login);
        if (client == null) {
            throw new FakeClientException();
        }
        List<AppointmentEntity> appointments = appointmentDAO.findAllByLogin(login);
        return AppointmentEntity.toDTO(appointments);
    }

    @Override
    public List<AppointmentDTO> getAppoinments(String login, Date date) throws FakeClientException {
        if (login == null || date == null) {
            throw new IllegalArgumentException();
        }
        ClientEntity client = clientDAO.find(login);
        if (client == null) {
            throw new FakeClientException();
        }
        List<AppointmentEntity> appointments = appointmentDAO.findRecentByLogin(login, date);
        return AppointmentEntity.toDTO(appointments);
    }

}
