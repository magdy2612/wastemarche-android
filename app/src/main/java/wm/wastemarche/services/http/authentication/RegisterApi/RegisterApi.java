package wm.wastemarche.services.http.authentication.RegisterApi;

import android.os.Handler;
import android.os.Message;

import java.util.HashMap;
import java.util.Map;

import wm.wastemarche.services.http.ConnectionManager;
import wm.wastemarche.services.http.ErrorCode;

public class RegisterApi {

    private final RegisterApiProtocol delegate;

    private class ResponseHandler extends Handler {
        @Override
        public void handleMessage(final Message msg) {
            if(msg.arg1 == ErrorCode.NO_ERROR) {
                delegate.registerApiSuccess();
                parse((String) msg.obj);
            }
            else {
                delegate.registerApiFailed(ErrorCode.getLoclizedMessage(msg.arg1));
            }
        }
    }

    public RegisterApi(final RegisterApiProtocol delegate) {
        this.delegate = delegate;
    }

    public void start(final String name, final String email, final String mobile, final String password, final String about) {
        final Map<String, String> headers = new HashMap<>(0);
        headers.put("Accept", "application/json");
        final Map<String, String> fields = new HashMap<>(0);

        fields.put("name", name);
        //fields.put("email", email);
        fields.put("contact_phone", mobile);
        fields.put("password", password);
        fields.put("password_confirmation", password);
        fields.put("about", about);

        final ConnectionManager conn = new ConnectionManager();
        conn.makePOSTRequest("/register", fields, headers, new ResponseHandler());
    }

    private void parse(final String response) {
    }
}
