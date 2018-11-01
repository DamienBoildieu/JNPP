package jnpp.service.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Resource;
import jnpp.dao.entities.clients.ClientEntity;
import jnpp.dao.entities.movements.MovementEntity;
import jnpp.dao.entities.notifications.NotificationEntity;
import jnpp.service.exceptions.entities.FakeClientException;
import jnpp.service.exceptions.entities.FakeNotificationException;
import jnpp.service.exceptions.owners.NotificationOwnerException;
import org.springframework.stereotype.Service;
import jnpp.dao.repositories.ClientDAO;
import jnpp.dao.repositories.NotificationDAO;
import jnpp.service.dto.movements.MovementDTO;
import jnpp.service.dto.notifications.NotificationDTO;

@Service("NotificationService")
public class NotificationServiceImpl implements NotificationService {

    @Resource
    ClientDAO clientDAO;
    @Resource
    NotificationDAO notificationDAO;
    
    @Override
    public List<NotificationDTO> receiveNotifications(String login) throws FakeClientException {
        if (login == null) throw new IllegalArgumentException();
        ClientEntity client = clientDAO.find(login);
        if (client == null) throw new FakeClientException();
        List<NotificationEntity> notifications = notificationDAO.findAllByLogin(login);
        return entityToDTO(notifications);
    }
    
    
    

    @Override
    public List<NotificationEntity> receiveUnseenNotifications(ClientEntity client) throws FakeClientException {
        if (client == null) throw new IllegalArgumentException();
        checkFake(client);
        return notificationDAO.findAllUnseen(client.getLogin());
    }

    @Override
    public List<NotificationEntity> receiveUnseenNotifications(ClientEntity client, int n) throws FakeClientException {
        if (client == null || n < 1) throw new IllegalArgumentException();
        checkFake(client);
        return notificationDAO.findAllUnseen(client.getLogin(), n);
    }

    @Override
    public List<NotificationEntity> receiveUnseenNotifications(ClientEntity client, Date date) throws FakeClientException {
        if (client == null || date != null) throw new IllegalArgumentException();
        checkFake(client);
        return notificationDAO.findAllUnseen(client.getLogin(), date);    
    }

    @Override
    public void seeAllNotications(ClientEntity client) throws FakeClientException {
        if (client == null) throw new IllegalArgumentException();
        checkFake(client);
        notificationDAO.updateAllToSeen(client.getLogin());
    }

    @Override
    public void seeNotification(ClientEntity client, NotificationEntity notification) throws FakeClientException, FakeNotificationException, NotificationOwnerException {
        if (client == null && notification == null) throw new IllegalArgumentException();
        checkFake(client);
        checkFake(notification);
        //TODO merdique
        notification = notificationDAO.find(notification.getId());
        if (!client.getLogin().equals(notification.getClient().getLogin()))
            throw new NotificationOwnerException();
        notification.setSeen(true);
        notificationDAO.save(notification);
    }

    @Override
    public void seeNotifications(ClientEntity client, List<NotificationEntity> notifications) throws FakeClientException, FakeNotificationException, NotificationOwnerException {
        checkFake(client);
        checkFake(notifications);
        Iterator<NotificationEntity> it = notifications.iterator();
        //TODO merdique
        while (it.hasNext()) {
            NotificationEntity notification = it.next();
            if (!client.getLogin().equals(notification.getClient().getLogin()))
                throw new NotificationOwnerException();
        }
        while (it.hasNext()) {
            NotificationEntity notification = it.next();
            notificationDAO.find(notification.getId());
            notification.setSeen(true);
            notificationDAO.save(notification);
        }
    }
    
    private void checkFake(ClientEntity client) throws FakeClientException {
        if (CHECK_FAKE_ENTITY && clientDAO.isFake(client)) 
            throw new FakeClientException();
    }
    
    private void checkFake(NotificationEntity notification) throws FakeNotificationException {
        if (CHECK_FAKE_ENTITY && notificationDAO.isFake(notification))
            throw new FakeNotificationException();
    }
    
    private void checkFake(List<NotificationEntity> notifications) throws FakeNotificationException {
        if (CHECK_FAKE_ENTITY) {
            Iterator<NotificationEntity> it = notifications.iterator();
            while (it.hasNext()) {
                if (notificationDAO.isFake(it.next()))
                    throw new FakeNotificationException();
            }
        }
    }
    
    private static List<NotificationDTO> entityToDTO(List<NotificationEntity> movements) {
        List<NotificationDTO> dtos = new ArrayList<NotificationDTO>(movements.size());
        Iterator<NotificationEntity> it = movements.iterator();
        while (it.hasNext()) dtos.add(NotificationDTO.newDTO((it.next())));
        return dtos;
    } 
    
}
