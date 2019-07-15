package wm.wastemarche.services.http.notifications;

import android.os.Handler;
import android.os.Message;

import org.json.JSONObject;

import java.util.List;

import wm.wastemarche.model.Notification;
import wm.wastemarche.services.http.ConnectionManager;
import wm.wastemarche.services.http.HttpConstants;
import wm.wastemarche.services.http.Response;

public class NotificationApi {
    private final NotificationApiProtocol delegate;

    public NotificationApi(final NotificationApiProtocol delegate) {
        this.delegate = delegate;
    }

    public void getNotifications(final String page) {
        final String method = "/v1/notifications?page=" + page;

        final ConnectionManager conn = new ConnectionManager();
        conn.makeGETRequest(method, HttpConstants.defaultHeaders(), new Handler() {
            @Override
            public void handleMessage(final Message msg) {
                final JSONObject json = Response.parseResponse(msg, delegate);
                if( json == null ) {return; }

                final JSONObject data = Response.getPropertyObject("data", json);
                final JSONObject notifications = Response.getPropertyObject("notifications", data);
                final List<Notification> notificationsList = Notification.fromArray(Response.getPropertyArray("data", notifications));
                delegate.notificationsLoaded(page, notificationsList);
            }
        });
    }

    public void getNotification(final String notificationId) {
        final String method = "/v1/notifications/" + notificationId;

        final ConnectionManager conn = new ConnectionManager();
        conn.makeGETRequest(method, HttpConstants.defaultHeaders(), new Handler() {
            @Override
            public void handleMessage(final Message msg) {
                final JSONObject json = Response.parseResponse(msg, delegate);
                if( json == null ) {return; }

                final JSONObject data = Response.getPropertyObject("data", json);
                final Notification notification = new Notification(Response.getPropertyObject("notifications", data));
                delegate.notificationLoaded(notification);
            }
        });
    }
}
