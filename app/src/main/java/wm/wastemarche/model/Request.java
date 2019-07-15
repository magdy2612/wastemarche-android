package wm.wastemarche.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import wm.wastemarche.services.http.Response;

public class Request {
    public final int id;
    public final int item_id;
    public final int bid_id;
    public final int type;
    public int price;
    public String currency;
    public String payment_term;
    public String offer_validity;
    public int quantity;
    public String unit;
    public final String comment;
    public final String disposal_method;
    public final String transportation;
    public final String transportation_details;
    public final String vehicle_description;
    public final String disposal_certificate;
    public final String file;
    public final int viewed;
    public final int status;
    public final String created_at;
    public final String updated_at;
    public final Item item;
    public final Bid bid;

    public Request(final JSONObject json) {
        id = Response.getPropertyInt("id", json);
        item_id = Response.getPropertyInt("item_id", json);
        bid_id = Response.getPropertyInt("bid_id", json);
        type = Response.getPropertyInt("type", json);
        price = Response.getPropertyInt("price", json);
        currency = Response.getPropertyString("currency", json);
        payment_term = Response.getPropertyString("payment_term", json);
        offer_validity = Response.getPropertyString("offer_validity", json);
        quantity = Response.getPropertyInt("quantity", json);
        unit = Response.getPropertyString("unit", json);
        comment = Response.getPropertyString("comment", json);
        disposal_method = Response.getPropertyString("disposal_method", json);
        transportation = Response.getPropertyString("transportation", json);
        transportation_details = Response.getPropertyString("transportation_details", json);
        vehicle_description = Response.getPropertyString("vehicle_description", json);
        disposal_certificate = Response.getPropertyString("disposal_certificate", json);
        file = Response.getPropertyString("file", json);
        viewed = Response.getPropertyInt("viewed", json);
        status = Response.getPropertyInt("status", json);
        created_at = Response.getPropertyString("created_at", json);
        updated_at = Response.getPropertyString("updated_at", json);

        final JSONObject itemObj = Response.getPropertyObject("item", json);
        item = itemObj == null ? new Item(new JSONObject()) : new Item(itemObj);

        final JSONObject bidObj = Response.getPropertyObject("bid", json);
        bid = bidObj == null ? new Bid(new JSONObject()) : new Bid(bidObj);
    }

    public static List<Request> fromArray(final JSONArray array) {
        final List<Request> list = new ArrayList<>(0);
        for(int i=0,len=array.length();i<len;i++) {
            try {
                list.add(new Request(array.getJSONObject(i)));
            } catch (final JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public String asUrlEncoded() {
        final StringBuilder ret = new StringBuilder(0);
        final String[] fields = {"type", "price", "currency", "payment_term", "offer_validity", "quantity", "unit", "bid_id"};

        for(int i=0,len=fields.length;i<len;i++){
            try {
                ret.append(fields[i] + '=' + URLEncoder.encode(getClass().getDeclaredField(fields[i]).toString()) + '&');
            } catch (final NoSuchFieldException e) {
            }
        }

        return ret.toString();
    }
}
