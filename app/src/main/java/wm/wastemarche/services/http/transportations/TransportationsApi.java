package wm.wastemarche.services.http.transportations;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import wm.wastemarche.model.Transportation;
import wm.wastemarche.services.http.ConnectionManager;
import wm.wastemarche.services.http.HttpConstants;
import wm.wastemarche.services.http.Response;

public class TransportationsApi {

    private final TransportationsApiProtocol delegate;

    public TransportationsApi(final TransportationsApiProtocol delegate) {
        this.delegate = delegate;
    }

    public void getTransportations(final String page, final String method_id) {
        final String method = "/v1/transportations?page=" + page + "&method_id=" + method_id;

        final ConnectionManager conn = new ConnectionManager();
        conn.makeGETRequest(method, HttpConstants.defaultHeaders(), new Handler() {
            @Override
            public void handleMessage(final Message msg) {
                final JSONObject json = Response.parseResponse(msg, delegate);
                if( json == null ) {return; }

                final JSONObject data = Response.getPropertyObject("data", json);
                final JSONObject transportations = Response.getPropertyObject("transportations", data);
                final List<Transportation> transportationList = Transportation.fromArray(Response.getPropertyArray("data", transportations));
                delegate.transportationsLoaded(page, method_id, transportationList);
            }
        });
    }

    public void getTransportation(final String transportationId) {
        final String method = "/v1/transportations/" + transportationId;

        final ConnectionManager conn = new ConnectionManager();
        conn.makeGETRequest(method, HttpConstants.defaultHeaders(), new Handler() {
            @Override
            public void handleMessage(final Message msg) {
                final JSONObject json = Response.parseResponse(msg, delegate);
                if( json == null ) {return; }

                final JSONObject data = Response.getPropertyObject("data", json);
                final Transportation  transportation= new Transportation(Response.getPropertyObject("transportations", data));
                delegate.transportationLoaded(transportation);
            }
        });
    }

    public void createNewTransportation(final Transportation transportation, final Bitmap bitmap) {
        final String method = "/v1/transportations";
        final List<Bitmap> bitmaps = new ArrayList<>(0);
        bitmaps.add(bitmap);

        final ConnectionManager conn = new ConnectionManager();
        conn.makePostRequestWithFiles(method, transportation.getFields(), bitmaps, HttpConstants.postHeaders(), new Handler() {
            @Override
            public void handleMessage(final Message msg) {
                final JSONObject json = Response.parseResponse(msg, delegate);
                if( json == null ) {return; }
                delegate.transportationCreated();
            }
        });
    }

    public void updateTransportation(final Transportation transportation) {
    }

    public void deleteTransportation(final String transportationId) {
    }

}
