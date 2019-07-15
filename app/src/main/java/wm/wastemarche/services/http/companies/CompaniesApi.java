package wm.wastemarche.services.http.companies;

import android.os.Handler;
import android.os.Message;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import wm.wastemarche.model.Company;
import wm.wastemarche.services.http.ConnectionManager;
import wm.wastemarche.services.http.ErrorCode;
import wm.wastemarche.services.http.Response;

public class CompaniesApi {

    private final CompaniesApiProtocol delegate;

    private class ResponseHandler extends Handler {
        @Override
        public void handleMessage(final Message msg) {
            super.handleMessage(msg);

            if(msg.arg1 == ErrorCode.NO_ERROR) {
                parse((String) msg.obj);
            }
            else {
                delegate.companiesFialed(ErrorCode.getLoclizedMessage(msg.arg1));
            }
        }
    }

    public CompaniesApi(final CompaniesApiProtocol delegate) {
        this.delegate = delegate;
    }

    public void getCompaniesTypes() {
        final ConnectionManager conn = new ConnectionManager();
        conn.makeGETRequest("/methods", new HashMap<String, String>(0), new ResponseHandler());
    }

    private void parse(final String response) {
        final JSONObject json = Response.parse(response);
        final JSONObject data = Response.getPropertyObject("data", json);
        final List<Company> companies = Company.fromArray(Response.getPropertyArray("methods", data));
        delegate.companiesTypesloaded(companies);
    }
}
