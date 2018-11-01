package jnpp.service.services;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Resource;
import jnpp.dao.entities.advisor.AdvisorEntity;
import jnpp.dao.entities.advisor.MessageEntity;
import jnpp.dao.entities.clients.ClientEntity;
import jnpp.dao.repositories.ClientDAO;
import jnpp.dao.repositories.MessageDAO;
import jnpp.service.dto.advisor.AdvisorDTO;
import jnpp.service.dto.advisor.MessageDTO;
import jnpp.service.exceptions.entities.FakeClientException;
import org.springframework.stereotype.Service;

@Service("AdvisorServiceImpl")
public class AdvisorServiceImpl implements AdvisorService {

    @Resource
    ClientDAO clientDAO;
    @Resource
    MessageDAO messageDAO;
       
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
        return entityToDTO(messages);
    }

    @Override
    public List<MessageDTO> receiveLastMessages(String login, int n) throws FakeClientException {
        if (login == null || n < 1) throw new IllegalArgumentException();
        ClientEntity client = clientDAO.find(login);
        if (client == null) throw new FakeClientException();
        List<MessageEntity> messages = messageDAO.findNByLogin(login, n);
        return entityToDTO(messages);    
    }

    @Override
    public List<MessageDTO> receiveLastMessages(String login, Date date) throws FakeClientException {
        if (login == null || date == null) throw new IllegalArgumentException();
        ClientEntity client = clientDAO.find(login);
        if (client == null) throw new FakeClientException();
        List<MessageEntity> messages = messageDAO.findRecentByLogin(login, date);
        return entityToDTO(messages);        
    }
    
    private static List<MessageDTO> entityToDTO(List<MessageEntity> movements) {
        List<MessageDTO> dtos = new ArrayList<MessageDTO>(movements.size());
        Iterator<MessageEntity> it = movements.iterator();
        while (it.hasNext()) dtos.add(new MessageDTO((it.next())));
        return dtos;
    } 
    
}
