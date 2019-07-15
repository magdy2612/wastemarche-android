package wm.wastemarche.services.http.authentication.LoginApi;

import android.os.Handler;
import android.os.Message;

import java.util.HashMap;
import java.util.Map;

import wm.wastemarche.services.http.ConnectionManager;
import wm.wastemarche.services.http.ErrorCode;

public class ForgotPasswordApi {
    private final LoginApiProtocol delegate;

    private class ResponseHandler extends Handler {
        @Override
        public void handleMessage(final Message msg) {
            super.handleMessage(msg);

            if(msg.arg1 == ErrorCode.NO_ERROR) {
                delegate.forgotPasswordCompleted();
            }
            else {
                delegate.loginApiFailed(ErrorCode.getLoclizedMessage(msg.arg1));
            }
        }
    }

    public ForgotPasswordApi(final LoginApiProtocol delegate) {
        this.delegate = delegate;
    }

    public void start(final String email) {
        final Map<String, String> fields = new HashMap<>(0);
        fields.put("contact_phone", email);
        final ConnectionManager conn = new ConnectionManager();
        conn.makePOSTRequest("/forgetpassword", fields, new HashMap<String, String>(0), new ResponseHandler());
    }
}
