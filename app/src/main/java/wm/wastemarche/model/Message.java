package wm.wastemarche.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import wm.wastemarche.services.http.Response;

public class Message {
    public final int id;
    public final int thread_id;
    public final String message;
    public final int recipient_id;
    public final int sender_id;
    public final boolean isRead;
    public final String created_at;
    public final String updated_at;

    public Message(final JSONObject json) {
        id = Response.getPropertyInt("id", json);
        thread_id = Response.getPropertyInt("thread_id", json);
        message = Response.getPropertyString("message", json);
        recipient_id = Response.getPropertyInt("recipient_id", json);
        sender_id = Response.getPropertyInt("sender_id", json);
        isRead = Response.getPropertyBoolean("isRead", json);
        created_at = Response.getPropertyString("created_at", json);
        updated_at = Response.getPropertyString("updated_at", json);
    }

    public static List<Message> fromArray(final JSONArray array) {
        final List<Message> list = new ArrayList<>(0);
        for(int i=0,len=array.length();i<len;i++) {
            try {
                list.add(new Message(array.getJSONObject(i)));
            } catch (final JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
