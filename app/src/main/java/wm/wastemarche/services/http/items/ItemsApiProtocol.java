package wm.wastemarche.services.http.items;

import org.json.JSONObject;

import java.util.List;

import wm.wastemarche.model.Item;
import wm.wastemarche.services.http.ApiProtocol;

public interface ItemsApiProtocol extends ApiProtocol {

    void itemsLoaded(String method, String self, String page, List<Item> items);

    void itemLoaded(Item item);

    void itemCreated(Item item);
}
