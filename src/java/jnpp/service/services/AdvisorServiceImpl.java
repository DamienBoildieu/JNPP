package jnpp.service.services;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
import jnpp.service.exceptions.duplicates.DuplicateAppointmentException;
import jnpp.service.exceptions.entities.FakeAppointmentException;
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
        if (login == null) throw new IllegalArgumentException();
        ClientEntity client = clientDAO.find(login);
        if (client == null) throw new FakeClientException();
        AdvisorEntity advisor = client.getAdvisor();
        return new AdvisorDTO(advisor);
    }

    @Override
    public MessageDTO sendMessage(String login, String message) throws FakeClientException {
        if (login == null || message == null || message.length() == 0) 
            throw new IllegalArgumentException();
        ClientEntity client = clientDAO.find(login);
        if (client == null) throw new FakeClientException();
        MessageEntity entity = new MessageEntity(client, client.getAdvisor(), 
                MessageEntity.Direction.CLIENT_TO_ADVISOR, Date.from(Instant.now()), message);
        messageDAO.save(entity);
        return new MessageDTO(entity);
    }

    @Override
    public List<MessageDTO> receiveMessages(String login) throws FakeClientException {
        if (login == null) throw new IllegalArgumentException();
        ClientEntity client = clientDAO.find(login);
        if (client == null) throw new FakeClientException();
        List<MessageEntity> messages = messageDAO.findAllByLogin(login);
        return messageEntityToDTO(messages);
    }

    @Override
    public List<MessageDTO> receiveLastMessages(String login, int n) throws FakeClientException {
        if (login == null || n < 1) throw new IllegalArgumentException();
        ClientEntity client = clientDAO.find(login);
        if (client == null) throw new FakeClientException();
        List<MessageEntity> messages = messageDAO.findNByLogin(login, n);
        return messageEntityToDTO(messages);    
    }

    @Override
    public List<MessageDTO> receiveLastMessages(String login, Date date) throws FakeClientException {
        if (login == null || date == null) throw new IllegalArgumentException();
        ClientEntity client = clientDAO.find(login);
        if (client == null) throw new FakeClientException();
        List<MessageEntity> messages = messageDAO.findRecentByLogin(login, date);
        return messageEntityToDTO(messages);        
    }

    @Override
    public AppointmentDTO makeAppointment(String login, Date date) throws FakeClientException, DateException, DuplicateAppointmentException, AvailableException {
        if (login == null || date == null) throw new IllegalArgumentException();
        ClientEntity client = clientDAO.find(login);
        if (client == null) throw new FakeClientException();
        Date limit = Date.from(Instant.now().plusSeconds(MAKE_APPOINTMENT_DELTA));
        if (date.before(limit)) throw new DateException();
        Date min = Date.from(date.toInstant().minusSeconds(APPOINTMENT_DURATION));
        Date max = Date.from(date.toInstant().plusSeconds(APPOINTMENT_DURATION));
        AdvisorEntity advisor = client.getAdvisor();
        if (appointmentDAO.countByAdvisorIdInMinMax(advisor.getId(), min, max) > 0 )
            throw new AvailableException();
        AppointmentEntity appointment = new AppointmentEntity(date, client, advisor);
        appointmentDAO.save(appointment);
        return new AppointmentDTO(appointment);
    }

    @Override
    public void cancelAppoint(String login, Long id) throws FakeClientException, FakeAppointmentException, AppointmentOwnerException, DateException {
        if (login == null || id == null) throw new IllegalArgumentException();
        ClientEntity client = clientDAO.find(login);
        if (client == null) throw new FakeClientException();
        AppointmentEntity appointment = appointmentDAO.find(id);
        if (appointment == null) throw new FakeAppointmentException();
        if (!client.equals(appointment.getClient())) throw new AppointmentOwnerException();
        Date date = appointment.getDate();
        Date limit = Date.from(Instant.now().plusSeconds(CANCEL_APPOINTMENT_DELTA));
        if (limit.after(date)) throw new DateException();
        appointmentDAO.delete(appointment);
    }

    @Override
    public List<AppointmentDTO> getAppoinments(String login) throws FakeClientException {
        if (login == null) throw new IllegalArgumentException();
        ClientEntity client = clientDAO.find(login);
        if (client == null) throw new FakeClientException();
        List<AppointmentEntity> appointments = appointmentDAO.findAllByLogin(login);
        return appointmentEntityToDTO(appointments);
    }

    @Override
    public List<AppointmentDTO> getAppoinments(String login, Date date) throws FakeClientException {
        if (login == null || date == null) throw new IllegalArgumentException();
        ClientEntity client = clientDAO.find(login);
        if (client == null) throw new FakeClientException();
        List<AppointmentEntity> appointments = appointmentDAO.findRecentByLogin(login, date);
        return appointmentEntityToDTO(appointments);    
    }
    
    private static List<MessageDTO> messageEntityToDTO(List<MessageEntity> movements) {
        List<MessageDTO> dtos = new ArrayList<MessageDTO>(movements.size());
        Iterator<MessageEntity> it = movements.iterator();
        while (it.hasNext()) dtos.add(new MessageDTO((it.next())));
        return dtos;
    }   
    
    private static List<AppointmentDTO> appointmentEntityToDTO(List<AppointmentEntity> movements) {
        List<AppointmentDTO> dtos = new ArrayList<AppointmentDTO>(movements.size());
        Iterator<AppointmentEntity> it = movements.iterator();
        while (it.hasNext()) dtos.add(new AppointmentDTO((it.next())));
        return dtos;
    } 
    
}
