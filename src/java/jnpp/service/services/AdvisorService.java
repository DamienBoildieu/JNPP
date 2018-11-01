package jnpp.service.services;

import java.util.Date;
import java.util.List;
import jnpp.service.dto.advisor.AdvisorDTO;
import jnpp.service.dto.advisor.MessageDTO;
import jnpp.service.exceptions.entities.FakeClientException;

public interface AdvisorService extends IService {
   
    AdvisorDTO getAdvisor(String login) throws FakeClientException;
    
    MessageDTO sendMessage(String login, String message)
            throws FakeClientException;
    
    List<MessageDTO> receiveMessages(String login)
            throws FakeClientException;
    List<MessageDTO> receiveLastMessages(String login, int n)
            throws FakeClientException;
    List<MessageDTO> receiveLastMessages(String login, Date date)
            throws FakeClientException;
}
