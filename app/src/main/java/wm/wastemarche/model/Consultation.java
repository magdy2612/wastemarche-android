package wm.wastemarche.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import wm.wastemarche.services.http.Response;

public class Consultation {

    public final int id;
    public final String title;
    public final String main_image;
    public final String unique_name;

    public Consultation(final JSONObject json) {
        id = Response.getPropertyInt("id", json);
        title = Response.getPropertyString("title", json);
        main_image = Response.getPropertyString("main_image", json);
        unique_name = Response.getPropertyString("unique_name", json);
    }

    public static List<Consultation> fromArray(final JSONArray array) {
        final List<Consultation> list = new ArrayList<>(0);
        for(int i=0,len=array.length();i<len;i++) {
            try {
                list.add(new Consultation(array.getJSONObject(i)));
            } catch (final JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
