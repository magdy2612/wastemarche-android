package wm.wastemarche.services.http.categories;

import android.os.Handler;
import android.os.Message;

import org.json.JSONObject;

import java.util.List;

import wm.wastemarche.model.Category;
import wm.wastemarche.services.http.ConnectionManager;
import wm.wastemarche.services.http.ErrorCode;
import wm.wastemarche.services.http.HttpConstants;
import wm.wastemarche.services.http.Response;

public class CategoriesApi {
    private final CategoriesApiProtocol delegate;

    private class ResponseHandler extends Handler {
        @Override
        public void handleMessage(final Message msg) {
            super.handleMessage(msg);

            if(msg.arg1 == ErrorCode.NO_ERROR) {
                parse((String) msg.obj);
            }
            else {
                delegate.categoriesFialed(ErrorCode.getLoclizedMessage(msg.arg1));
            }
        }
    }

    public CategoriesApi(final CategoriesApiProtocol delegate) {
        this.delegate = delegate;
    }

    public void getCategories() {
        final ConnectionManager conn = new ConnectionManager();
        conn.makeGETRequest("/v1/categories", HttpConstants.defaultHeaders(), new CategoriesApi.ResponseHandler());
    }

    private void parse(final String response) {
        final JSONObject json = Response.parse(response);
        final JSONObject data = Response.getPropertyObject("data", json);
        final List<Category> categories= Category.fromArray(Response.getPropertyArray("categories", data));
        delegate.categoriesLoaded(categories);
    }
}
