package jnpp.service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Resource;
import jnpp.dao.entities.clients.Client;
import jnpp.dao.entities.notifications.Notification;
import jnpp.dao.repositories.IClientDAO;
import jnpp.dao.repositories.INotificationDAO;
import jnpp.service.exceptions.entities.FakeClientException;
import jnpp.service.exceptions.entities.FakeNotificationException;
import jnpp.service.exceptions.owners.NotificationOwnerException;
import org.springframework.stereotype.Service;

@Service("NotificationService")
public class NotificationService implements INotificationService {

    @Resource
    IClientDAO clientDAO;
    @Resource
    INotificationDAO notificationDAO;
    
    @Override
    public List<Notification> receiveNotifications(Client client) throws FakeClientException {
        if (client == null) throw new IllegalArgumentException();
        checkFake(client);
        return notificationDAO.findAll(client.getId());
    }

    @Override
    public List<Notification> receiveUnseenNotifications(Client client) throws FakeClientException {
        if (client == null) throw new IllegalArgumentException();
        checkFake(client);
        return notificationDAO.findAllUnseen(client.getId());
    }

    @Override
    public List<Notification> receiveUnseenNotifications(Client client, int n) throws FakeClientException {
        if (client == null || n < 1) throw new IllegalArgumentException();
        checkFake(client);
        return notificationDAO.findAllUnseen(client.getId(), n);
    }

    @Override
    public List<Notification> receiveUnseenNotifications(Client client, Date date) throws FakeClientException {
        if (client == null || date != null) throw new IllegalArgumentException();
        checkFake(client);
        return notificationDAO.findAllUnseen(client.getId(), date);    
    }

    @Override
    public void seeAllNotications(Client client) throws FakeClientException {
        if (client == null) throw new IllegalArgumentException();
        checkFake(client);
        notificationDAO.updateAllToSeen(client.getId());
    }

    @Override
    public void seeNotification(Client client, Notification notification) throws FakeClientException, FakeNotificationException, NotificationOwnerException {
        if (client == null && notification == null) throw new IllegalArgumentException();
        checkFake(client);
        checkFake(notification);
        notification = notificationDAO.find(notification.getId());
        if (!client.getId().equals(notification.getClient().getId()))
            throw new NotificationOwnerException();
        notification.setSeen(true);
        notificationDAO.save(notification);
    }

    @Override
    public void seeNotifications(Client client, List<Notification> notifications) throws FakeClientException, FakeNotificationException, NotificationOwnerException {
        checkFake(client);
        checkFake(notifications);
        Iterator<Notification> it = notifications.iterator();
        while (it.hasNext()) {
            Notification notification = it.next();
            if (!client.getId().equals(notification.getClient().getId()))
                throw new NotificationOwnerException();
        }
        while (it.hasNext()) {
            Notification notification = it.next();
            notificationDAO.find(notification.getId());
            notification.setSeen(true);
            notificationDAO.save(notification);
        }
    }
    
    private void checkFake(Client client) throws FakeClientException {
        if (CHECK_FAKE_ENTITY && clientDAO.isFake(client)) 
            throw new FakeClientException();
    }
    
    private void checkFake(Notification notification) throws FakeNotificationException {
        if (CHECK_FAKE_ENTITY && notificationDAO.isFake(notification))
            throw new FakeNotificationException();
    }
    
    private void checkFake(List<Notification> notifications) throws FakeNotificationException {
        if (CHECK_FAKE_ENTITY) {
            Iterator<Notification> it = notifications.iterator();
            while (it.hasNext()) {
                if (notificationDAO.isFake(it.next()))
                    throw new FakeNotificationException();
            }
        }
    }
    
}
