package wm.wastemarche.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import wm.wastemarche.services.http.Response;

public class Image {
    public final int id;
    public final String title;
    public String image_name;
    public final int imageable_id;
    //"imageable_type": "App\\Models\\Item",
    public final String created_at;
    public final String updated_at;
    public final int admin_id;

    public Image(final JSONObject json) {
        id = Response.getPropertyInt("id", json);
        title = Response.getPropertyString("title", json);
        image_name = Response.getPropertyString("image_name", json);
        imageable_id = Response.getPropertyInt("imageable_id", json);
        created_at = Response.getPropertyString("created_at", json);
        updated_at = Response.getPropertyString("updated_at", json);
        admin_id = Response.getPropertyInt("admin_id", json);
    }

    public static List<Image> fromArray(final JSONArray array) {
        final List<Image> list = new ArrayList<>(0);
        for(int i=0,len = array.length();i<len;i++) {
            try {
                list.add(new Image(array.getJSONObject(i)));
            } catch (final JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
