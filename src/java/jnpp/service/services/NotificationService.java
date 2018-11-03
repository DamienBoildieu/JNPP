package jnpp.service.services;

import java.util.Date;
import java.util.List;
import jnpp.service.dto.notifications.NotificationDTO;
import jnpp.service.exceptions.entities.FakeClientException;
import jnpp.service.exceptions.entities.FakeNotificationException;
import jnpp.service.exceptions.owners.NotificationOwnerException;

public interface NotificationService {

    List<NotificationDTO> receiveNotifications(String login) throws FakeClientException;
    List<NotificationDTO> receiveUnseenNotifications(String login)
            throws FakeClientException;
    List<NotificationDTO> receiveUnseenNotifications(String login, int n)
            throws FakeClientException;
    List<NotificationDTO> receiveUnseenNotifications(String login, Date date)
            throws FakeClientException;
    
    void seeAllNotications(String login)
            throws FakeClientException;
    void seeNotification(String login, Long id)
            throws FakeClientException, FakeNotificationException,
            NotificationOwnerException;
    void seeNotifications(String login, List<Long> ids)
            throws FakeClientException, FakeNotificationException,
            NotificationOwnerException;
    
}
