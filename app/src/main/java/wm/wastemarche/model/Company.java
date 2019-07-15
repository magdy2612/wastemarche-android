package wm.wastemarche.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import wm.wastemarche.services.datacenter.DataCenter;
import wm.wastemarche.services.http.Response;

public class Company {
    public final int id;
    public final String title;
    public final int admin_id;
    public final String deleted_at;
    public final String created_at;
    public final String updated_at;

    public Company(final JSONObject json) {
        id = Response.getPropertyInt("id", json);
        title = DataCenter.lang.equals("en") ? Response.getPropertyString("title", json) : Response.getPropertyString("title_ar", json);
        admin_id = Response.getPropertyInt("admin_id", json);
        deleted_at = Response.getPropertyString("deleted_at", json);
        created_at = Response.getPropertyString("created_at", json);
        updated_at = Response.getPropertyString("updated_at", json);
    }

    public static List<Company> fromArray(final JSONArray array) {
        final List<Company> list = new ArrayList<>(0);
        for(int i=0,len=array.length();i<len;i++) {
            try {
                list.add(new Company(array.getJSONObject(i)));
            } catch (final JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
