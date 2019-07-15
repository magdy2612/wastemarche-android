package wm.wastemarche.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import wm.wastemarche.services.http.Response;

public class Thread {
    public final int id;
    public final int sender_id;
    public final int recipient_id;
    public final int item_id;
    public final int bid_id;
    public final boolean is_consultation;
    public final String subject;
    public final String created_at;
    public final String updated_at;
    public final List<Message> messages;
    public final Item item;
    public final Bid bid;

    public Thread(final JSONObject json) {
        id = Response.getPropertyInt("id", json);
        sender_id = Response.getPropertyInt("sender_id", json);
        recipient_id = Response.getPropertyInt("recipient_id", json);
        item_id = Response.getPropertyInt("item_id", json);
        bid_id = Response.getPropertyInt("bid_id", json);
        is_consultation = Response.getPropertyBoolean("is_consultation", json);
        subject = Response.getPropertyString("subject", json);
        created_at = Response.getPropertyString("created_at", json);
        updated_at = Response.getPropertyString("updated_at", json);
        messages = Message.fromArray(Response.getPropertyArray("messages", json));

        final JSONObject itemObj = Response.getPropertyObject("item", json);
        item = itemObj == null ? new Item(new JSONObject()) : new Item(itemObj);

        final JSONObject bidObj = Response.getPropertyObject("bid", json);
        bid = bidObj == null ? new Bid(new JSONObject()) : new Bid(bidObj);
    }

    public static List<Thread> fromArray(final JSONArray array) {
        final List<Thread> list = new ArrayList<>(0);
        for(int i=0,len=array.length();i<len;i++) {
            try {
                list.add(new Thread(array.getJSONObject(i)));
            } catch (final JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
