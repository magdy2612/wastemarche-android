package wm.wastemarche.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import wm.wastemarche.services.http.Response;

public class Transportation {

    public final int id;
    public int category_id;
    public String title;
    public int method_id;
    public final boolean is_consultation;
    public int quantity;
    public String unit;
    public String packaging;
    public final int transportation;
    public final String slug;
    public String price;
    //send_to
    //consultant
    public final String summary;
    public String content;
    public final String main_image;
    public final String meta_description;
    public final String meta_keywords;
    public final int visits;
    public final String created_at;
    public final int published;
    public final int featured;
    public final User user;
    public final Category category;
    public final List<Request> requests;
    public final List<Image> images;
    public String pickupAddress;
    public String deliveryAddress;

    public Transportation(final JSONObject json) {
        id = Response.getPropertyInt("id", json);
        category_id = Response.getPropertyInt("category_id", json);
        title = Response.getPropertyString("title", json);
        method_id = Response.getPropertyInt("method_id", json);
        is_consultation = Response.getPropertyBoolean("is_consultation", json);
        quantity = Response.getPropertyInt("quantity", json);
        unit = Response.getPropertyString("unit", json);
        packaging = Response.getPropertyString("packaging", json);
        transportation = Response.getPropertyInt("transportation", json);
        slug = Response.getPropertyString("slug", json);
        price = Response.getPropertyString("price", json);
        summary = Response.getPropertyString("summary", json);
        content = Response.getPropertyString("content", json);
        main_image = Response.getPropertyString("main_image", json);
        meta_description = Response.getPropertyString("meta_description", json);
        meta_keywords = Response.getPropertyString("meta_keywords", json);
        visits = Response.getPropertyInt("visits", json);
        created_at = Response.getPropertyString("created_at", json);
        published = Response.getPropertyInt("published", json);
        featured = Response.getPropertyInt("featured", json);

        final JSONObject userObj = Response.getPropertyObject("user", json);
        user = userObj == null ? new User(new JSONObject()) : new User(userObj);

        final JSONObject categoryObj = Response.getPropertyObject("category", json);
        category = categoryObj == null ? new Category(new JSONObject()) : new Category(categoryObj);

        requests = Request.fromArray(Response.getPropertyArray("requests", json));
        images = Image.fromArray(Response.getPropertyArray("images", json));
    }

    public static List<Transportation> fromArray(final JSONArray array) {
        final List<Transportation> list = new ArrayList<>(0);
        for(int i=0,len=array.length();i<len;i++) {
            try {
                list.add(new Transportation(array.getJSONObject(i)));
            } catch (final JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public String getMethod() {
        if( method_id == 4 ) return "Transport Waste";
        if( method_id == 5 ) return "Dispose Waste";
        return "";
    }

    public Map<String, String> getFields() {
        final Map<String, String> fields = new HashMap<>(0);
        fields.put("category_id", ""+category_id);
        fields.put("title", title);
        fields.put("method_id", String.valueOf(method_id));
        fields.put("quantity", String.valueOf(quantity));
        fields.put("unit", unit);
        fields.put("packaging", packaging);
        fields.put("transportation", "1");
        fields.put("price", price);
        fields.put("summary", summary);
        fields.put("content", content);

        fields.put("pick_up_address", pickupAddress);
        fields.put("delivery_address", deliveryAddress);

        fields.put("contact_name", user.name);
        fields.put("contact_phone", user.contact_phone);
        fields.put("contact_email", user.email);
        fields.put("show_company_name", "1");
        return fields;
    }

}
