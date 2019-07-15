package wm.wastemarche.model;

import org.json.JSONObject;

import wm.wastemarche.services.http.Response;

public class User {
    public final int id;
    public String name;
    public String email;
    public String contact_phone;

    public User(final JSONObject json) {
        id = Response.getPropertyInt("id", json);
        name = Response.getPropertyString("name", json);
        email = Response.getPropertyString("email", json);
        contact_phone = Response.getPropertyString("contact_phone", json);
    }
}
