package wm.wastemarche.services.http.notifications;

import java.util.List;

import wm.wastemarche.model.Notification;
import wm.wastemarche.services.http.ApiProtocol;

public interface NotificationApiProtocol extends ApiProtocol {

    void notificationsLoaded(String page, List<Notification> notifications);

    void notificationLoaded(Notification notification);
}
