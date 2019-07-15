package wm.wastemarche.services.http.bids;

import android.os.Handler;
import android.os.Message;

import org.json.JSONObject;

import java.util.List;

import wm.wastemarche.model.Bid;
import wm.wastemarche.services.http.ConnectionManager;
import wm.wastemarche.services.http.HttpConstants;
import wm.wastemarche.services.http.Response;

public class BidsApi {
    private final BidsApiProtocol delegate;

    public BidsApi(final BidsApiProtocol delegate) {
        this.delegate = delegate;
    }

    public void getBids(final String page) {
        final String method = "/v1/bids?page=" + page;

        final ConnectionManager conn = new ConnectionManager();
        conn.makeGETRequest(method, HttpConstants.defaultHeaders(), new Handler() {
            @Override
            public void handleMessage(final Message msg) {
                final JSONObject json = Response.parseResponse(msg, delegate);
                if( json == null ) {return; }

                final JSONObject data = Response.getPropertyObject("data", json);
                final JSONObject bids = Response.getPropertyObject("bids", data);
                final List<Bid> bidsList = Bid.fromArray(Response.getPropertyArray("data", bids));
                delegate.bidsLoaded(page, bidsList);
            }
        });
    }

    public void getBid(final String bidId) {
        final String method = "/v1/bids/" + bidId;

        final ConnectionManager conn = new ConnectionManager();
        conn.makeGETRequest(method, HttpConstants.defaultHeaders(), new Handler() {
            @Override
            public void handleMessage(final Message msg) {
                final JSONObject json = Response.parseResponse(msg, delegate);
                if( json == null ) {return; }

                final JSONObject data = Response.getPropertyObject("data", json);
                final Bid bid = new Bid(Response.getPropertyObject("bids", data));
                delegate.bidLoaded(bid);
            }
        });
    }

    public void createNewBid(final Bid bid) {
    }

    public void updateBid(final Bid bid) {
    }

    public void deleteBid(final String bidId) {
    }
}
