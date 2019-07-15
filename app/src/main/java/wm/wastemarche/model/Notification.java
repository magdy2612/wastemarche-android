package wm.wastemarche.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import wm.wastemarche.services.http.Response;

public class Notification {

    public final int id;
    public final String description;
    public final int user_id;
    public final int item_id;
    public final int bid_id;
    public final int request_id;
    public final int thread_id;
    public final int review_id;
    public final String created_at;
    public final String updated_at;
    public final User user;

    public Notification(final JSONObject json) {
        id = Response.getPropertyInt("id", json);
        description = Response.getPropertyString("description", json);
        user_id = Response.getPropertyInt("user_id", json);
        item_id = Response.getPropertyInt("item_id", json);
        bid_id = Response.getPropertyInt("bid_id", json);
        request_id = Response.getPropertyInt("request_id", json);
        thread_id = Response.getPropertyInt("thread_id", json);
        review_id = Response.getPropertyInt("review_id", json);
        created_at = Response.getPropertyString("created_at", json);
        updated_at = Response.getPropertyString("updated_at", json);

        final JSONObject userObj = Response.getPropertyObject("user", json);
        user = userObj == null ? new User(new JSONObject()) : new User(userObj);
    }

    public static List<Notification> fromArray(final JSONArray array) {
        final List<Notification> list = new ArrayList<>(0);
        for(int i=0,len=array.length();i<len;i++) {
            try {
                list.add(new Notification(array.getJSONObject(i)));
            } catch (final JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
