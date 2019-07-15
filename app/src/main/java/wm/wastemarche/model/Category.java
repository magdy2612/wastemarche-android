package wm.wastemarche.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import wm.wastemarche.services.datacenter.DataCenter;
import wm.wastemarche.services.http.Response;

public class Category {
    public final int id;
    public final String title;
    public final String slug;
    public final int published;
    public final List<Category> child_category;
    public Category parent;

    public Category(final JSONObject json) {
        id = Response.getPropertyInt("id", json);
        title = DataCenter.lang.equals("en")  ? Response.getPropertyString("title", json) : Response.getPropertyString("title_ar", json);
        slug = Response.getPropertyString("slug", json);
        published = Response.getPropertyInt("published", json);
        child_category = Category.fromArray(Response.getPropertyArray("child_category", json));
        for(int i=0,l=child_category.size();i<l;i++) {
            child_category.get(i).parent = this;
        }
    }

    public static List<Category> fromArray(final JSONArray array) {
        final List<Category> list = new ArrayList<>(0);
        for(int i=0,len=array.length();i<len;i++) {
            try {
                list.add(new Category(array.getJSONObject(i)));
            } catch (final JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
