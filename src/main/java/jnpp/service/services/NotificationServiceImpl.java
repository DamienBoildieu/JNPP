package jnpp.service.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import jnpp.dao.entities.clients.ClientEntity;
import jnpp.dao.entities.notifications.NotificationEntity;
import jnpp.dao.repositories.ClientDAO;
import jnpp.dao.repositories.NotificationDAO;
import jnpp.service.dto.notifications.NotificationDTO;
import jnpp.service.exceptions.entities.FakeClientException;
import jnpp.service.exceptions.entities.FakeNotificationException;
import jnpp.service.exceptions.owners.NotificationOwnerException;

@Service("NotificationService")
public class NotificationServiceImpl implements NotificationService {

    @Resource
    ClientDAO clientDAO;
    @Resource
    NotificationDAO notificationDAO;

    
    @Override
    public NotificationDTO getNotification(String login, Long id) throws FakeClientException,
            FakeNotificationException, NotificationOwnerException {
        if (login == null || id == null) {
            throw new IllegalArgumentException();
        }
        ClientEntity client = clientDAO.find(login);
        if (client == null) {
            throw new FakeClientException();
        }
        NotificationEntity notification = notificationDAO.find(id);
        if (notification == null) {
            throw new FakeNotificationException();
        }
        if (!client.equals(notification.getClient())) {
            throw new NotificationOwnerException();
        }
        return notification.toDTO();
    }
    
    @Override
    public List<NotificationDTO> receiveNotifications(String login)
            throws FakeClientException {
        if (login == null) {
            throw new IllegalArgumentException();
        }
        ClientEntity client = clientDAO.find(login);
        if (client == null) {
            throw new FakeClientException();
        }
        List<NotificationEntity> notifications = notificationDAO
                .findAllByLogin(login);
        return NotificationEntity.toDTO(notifications);
    }

    @Override
    public List<NotificationDTO> receiveUnseenNotifications(String login)
            throws FakeClientException {
        if (login == null) {
            throw new IllegalArgumentException();
        }
        ClientEntity client = clientDAO.find(login);
        if (client == null) {
            throw new FakeClientException();
        }
        List<NotificationEntity> notifications = notificationDAO
                .findUnseenByLogin(login);
        return NotificationEntity.toDTO(notifications);
    }

    @Override
    public List<NotificationDTO> receiveUnseenNotifications(String login, int n)
            throws FakeClientException {
        if (login == null) {
            throw new IllegalArgumentException();
        }
        ClientEntity client = clientDAO.find(login);
        if (client == null) {
            throw new FakeClientException();
        }
        List<NotificationEntity> notifications = notificationDAO
                .findNUnseenByLogin(login, n);
        return NotificationEntity.toDTO(notifications);
    }

    @Override
    public List<NotificationDTO> receiveUnseenNotifications(String login,
            Date date) throws FakeClientException {
        if (login == null) {
            throw new IllegalArgumentException();
        }
        ClientEntity client = clientDAO.find(login);
        if (client == null) {
            throw new FakeClientException();
        }
        List<NotificationEntity> notifications = notificationDAO
                .findUnseenRecentByLogin(login, date);
        return NotificationEntity.toDTO(notifications);
    }

    @Override
    public void seeAllNotications(String login) throws FakeClientException {
        if (login == null) {
            throw new IllegalArgumentException();
        }
        ClientEntity client = clientDAO.find(login);
        if (client == null) {
            throw new FakeClientException();
        }
        notificationDAO.setAllSeenByLogin(login);
    }

    @Override
    public void seeNotification(String login, Long id)
            throws FakeClientException, FakeNotificationException,
            NotificationOwnerException {
        if (login == null || id == null) {
            throw new IllegalArgumentException();
        }
        ClientEntity client = clientDAO.find(login);
        if (client == null) {
            throw new FakeClientException();
        }
        NotificationEntity notification = notificationDAO.find(id);
        if (notification == null) {
            throw new FakeNotificationException();
        }
        if (!client.equals(notification.getClient())) {
            throw new NotificationOwnerException();
        }
        notification.setSeen(true);
        notificationDAO.update(notification);
    }

    @Override
    public void seeNotifications(String login, List<Long> ids)
            throws FakeClientException, FakeNotificationException,
            NotificationOwnerException {
        if (login == null || ids == null) {
            throw new IllegalArgumentException();
        }
        ClientEntity client = clientDAO.find(login);
        if (client == null) {
            throw new FakeClientException();
        }
        List<NotificationEntity> notifications = new ArrayList<NotificationEntity>(
                ids.size());
        Iterator<Long> itl = ids.iterator();
        while (itl.hasNext()) {
            Long id = itl.next();
            if (id == null) {
                throw new IllegalArgumentException();
            }
            NotificationEntity notification = notificationDAO.find(id);
            if (notification == null) {
                throw new FakeNotificationException();
            }
            if (!client.equals(notification.getClient())) {
                throw new NotificationOwnerException();
            }
            if (!notification.getSeen()) {
                notifications.add(notification);
            }
        }
        Iterator<NotificationEntity> itn = notifications.iterator();
        while (itn.hasNext()) {
            NotificationEntity notification = itn.next();
            notification.setSeen(true);
            notificationDAO.update(notification);
        }
    }

}
