package wm.wastemarche.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import wm.wastemarche.services.http.Response;

public class Bid {

    public final int id;
    public final String title;
    public final String slug;
    public final String active_from;
    public final String active_to;
    public final String summary;
    public final String description;
    public final String terms_and_condition;
    public final String main_image;
    public final int user_id;
    public final int status;
    public final String created_at;
    public final String updated_at;
    public final User user;

    public Bid(final JSONObject json) {
        id = Response.getPropertyInt("id", json);
        title = Response.getPropertyString("title", json);
        slug = Response.getPropertyString("slug", json);
        active_from = Response.getPropertyString("active_from", json);
        active_to = Response.getPropertyString("active_to", json);
        summary = Response.getPropertyString("summary", json);
        description = Response.getPropertyString("description", json);
        terms_and_condition = Response.getPropertyString("terms_and_condition", json);
        main_image = Response.getPropertyString("main_image", json);
        user_id = Response.getPropertyInt("user_id", json);
        status = Response.getPropertyInt("status", json);
        created_at = Response.getPropertyString("created_at", json);
        updated_at = Response.getPropertyString("updated_at", json);

        final JSONObject userObj = Response.getPropertyObject("user", json);
        user = userObj == null ? new User(new JSONObject()) : new User(userObj);
    }

    public static List<Bid> fromArray(final JSONArray array) {
        final List<Bid> list = new ArrayList<>(0);
        for(int i=0,len=array.length();i<len;i++) {
            try {
                list.add(new Bid(array.getJSONObject(i)));
            } catch (final JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
