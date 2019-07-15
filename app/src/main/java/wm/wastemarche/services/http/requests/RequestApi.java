package wm.wastemarche.services.http.requests;

import android.os.Handler;
import android.os.Message;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import wm.wastemarche.model.Request;
import wm.wastemarche.services.http.ConnectionManager;
import wm.wastemarche.services.http.HttpConstants;
import wm.wastemarche.services.http.Response;

public class RequestApi {

    private final RequestApiProtocol delegate;

    public RequestApi(final RequestApiProtocol delegate) {
        this.delegate = delegate;
    }

    public void getRequests(final String page) {
        final String method = "/v1/requests?page=" + page;

        final ConnectionManager conn = new ConnectionManager();
        conn.makeGETRequest(method, HttpConstants.defaultHeaders(), new Handler() {
            @Override
            public void handleMessage(final Message msg) {
                final JSONObject json = Response.parseResponse(msg, delegate);
                if( json == null ) {return; }

                final JSONObject data = Response.getPropertyObject("data", json);
                final JSONObject requests = Response.getPropertyObject("requests", data);
                final List<Request> requestsList = Request.fromArray(Response.getPropertyArray("data", requests));
                delegate.requestsLoaded(page, requestsList);
            }
        });
    }

    public void getRequest(final String requestId) {
        final String method = "/v1/requests/" + requestId;

        final ConnectionManager conn = new ConnectionManager();
        conn.makeGETRequest(method, HttpConstants.defaultHeaders(), new Handler() {
            @Override
            public void handleMessage(final Message msg) {
                final JSONObject json = Response.parseResponse(msg, delegate);
                if( json == null ) {return; }

                final JSONObject data = Response.getPropertyObject("data", json);
                final Request request = new Request(Response.getPropertyObject("request", data));
                delegate.requestLoaded(request);
            }
        });
    }

    public void createNewRequestForExtraInfo(final String itemId, final String bidId, final String subject, final String message) {
        final String method = "/v1/requests";
        final Map<String, String> fields = new HashMap<>(0);
        fields.put("subject", subject);
        fields.put("message", message);
        fields.put("type", "1");
        if( itemId != null ) {
            fields.put("item_id", itemId);
        }
        else {
            fields.put("bid_id", bidId);
        }

        final ConnectionManager conn = new ConnectionManager();
        conn.makePOSTRequest(method, fields, HttpConstants.defaultHeaders(), new Handler() {
            @Override
            public void handleMessage(final Message msg) {
                final JSONObject json = Response.parseResponse(msg, delegate);
                if( json == null ) {return; }

                delegate.requestExtraInfoCompleted();
            }
        });
    }

    public void createNewRequestForOffer(final String itemId, final String bidId, final Request request) {
        final String method = "/v1/requests";
        final Map<String, String> fields = new HashMap<>(0);
        fields.put("price", String.valueOf(request.price));
        fields.put("currency", request.currency);
        fields.put("payment_term", request.payment_term);
        fields.put("offer_validity", request.offer_validity);
        fields.put("quantity", String.valueOf(request.quantity));
        fields.put("unit", request.unit);
        fields.put("type", "2");
        if( itemId != null ) {
            fields.put("item_id", itemId);
        }
        else {
            fields.put("bid_id", bidId);
        }

        final ConnectionManager conn = new ConnectionManager();
        conn.makePOSTRequest(method, fields, HttpConstants.defaultHeaders(), new Handler() {
            @Override
            public void handleMessage(final Message msg) {
                final JSONObject json = Response.parseResponse(msg, delegate);
                if( json == null ) {return; }

                delegate.sendOfferCompleted();
            }
        });
    }

    public void updateRequest(final Request request) {
    }

    public void deleteRequest(final String requestId) {
    }
}
