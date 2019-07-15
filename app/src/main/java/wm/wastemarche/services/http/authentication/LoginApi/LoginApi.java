package wm.wastemarche.services.http.authentication.LoginApi;

import android.os.Handler;
import android.os.Message;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import wm.wastemarche.model.User;
import wm.wastemarche.services.datacenter.DataCenter;
import wm.wastemarche.services.http.ConnectionManager;
import wm.wastemarche.services.http.ErrorCode;
import wm.wastemarche.services.http.Response;

public class LoginApi {

    private final LoginApiProtocol delegate;

    private class ResponseHandler extends Handler {
        @Override
        public void handleMessage(final Message msg) {
            super.handleMessage(msg);

            if(msg.arg1 == ErrorCode.NO_ERROR) {
                parse((String) msg.obj);
            }
            else {
                parseError((String) msg.obj);
            }
        }
    }

    public LoginApi(final LoginApiProtocol delegate) {
        this.delegate = delegate;
    }

    public void start(final String login, final String password) {
        final Map<String, String> fields = new HashMap<>(0);
        fields.put("contact_phone", login);
        fields.put("password", password);
        final ConnectionManager conn = new ConnectionManager();
        conn.makePOSTRequest("/login", fields, null, new ResponseHandler());
    }

    private void parse(final String response) {
        final JSONObject json = Response.parse(response);
        if( json != null ) {
            final JSONObject success = Response.getPropertyObject("success", json);
            DataCenter.token = Response.getPropertyString("token", success);
            DataCenter.user = new User(success);
            delegate.loginApiCompleted();
        }
        else {
            delegate.loginApiFailed(ErrorCode.getLoclizedMessage(ErrorCode.PARSING_ERROR));
        }
    }

    private void parseError(final String res) {
        final JSONObject json = Response.parse(res);
        final String code = Response.getPropertyString("code", json);
        if( code.isEmpty() ) {
            delegate.loginApiFailed("Invalid data");
        }
        else {
            delegate.verifyAccount(code);
        }
    }
}
