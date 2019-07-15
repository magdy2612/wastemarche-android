package wm.wastemarche.services.http.items;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import wm.wastemarche.model.Item;
import wm.wastemarche.services.http.ConnectionManager;
import wm.wastemarche.services.http.HttpConstants;
import wm.wastemarche.services.http.Response;

public class ItemsApi {
    private final ItemsApiProtocol delegate;

    public ItemsApi(final ItemsApiProtocol delegate) {
        this.delegate = delegate;
    }

    public void getItems(final String methodId, final String self, final String page) {
        final String method = "/v1/items?method_id=" + methodId + "&self=" + self + "&page=" + page;

        final ConnectionManager conn = new ConnectionManager();
        conn.makeGETRequest(method, HttpConstants.defaultHeaders(), new Handler() {
            @Override
            public void handleMessage(final Message msg) {
                final JSONObject json = Response.parseResponse(msg, delegate);
                if( json == null ) {return; }

                final JSONObject data = Response.getPropertyObject("data", json);
                final JSONObject items = Response.getPropertyObject("items", data);
                final List<Item> itemsList  = Item.fromArray(Response.getPropertyArray("data", items));
                delegate.itemsLoaded(methodId, self, page, itemsList);
            }
        });
    }

    public void getItem(final String itemId) {
        final String method = "/v1/items/" + itemId;

        final ConnectionManager conn = new ConnectionManager();
        conn.makeGETRequest(method, HttpConstants.defaultHeaders(), new Handler() {
            @Override
            public void handleMessage(final Message msg) {
                final JSONObject json = Response.parseResponse(msg, delegate);
                if( json == null ) {return; }

                final JSONObject data = Response.getPropertyObject("data", json);
                final Item item = new Item(Response.getPropertyObject("items", data));
                delegate.itemLoaded(item);
            }
        });
    }

    public void createNewItem(final Item item, final List<Bitmap> bitmaps) {
        final String method = "/v1/items";
        final ConnectionManager conn = new ConnectionManager();
        conn.makePostRequestWithFiles(method, item.getFields(), bitmaps, HttpConstants.postHeaders(), new Handler() {
            @Override
            public void handleMessage(final Message msg) {
                final JSONObject json = Response.parseResponse(msg, delegate);
                if( json == null ) {return; }
                try {
                    delegate.itemCreated(new Item(json.getJSONObject("data").getJSONObject("items")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void updateItem(final Item item, final List<Bitmap> bitmaps) {
        final String method = "/v1/items/"+item.id;
        final ConnectionManager conn = new ConnectionManager();
        conn.makePutRequestWithFiles(method, item.getFields(), bitmaps, HttpConstants.postHeaders(), new Handler() {
            @Override
            public void handleMessage(final Message msg) {
                final JSONObject json = Response.parseResponse(msg, delegate);
                if( json == null ) {return; }
                try {
                    delegate.itemCreated(new Item(json.getJSONObject("data").getJSONObject("items")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void deleteItem(final String itemId) {
    }
}
