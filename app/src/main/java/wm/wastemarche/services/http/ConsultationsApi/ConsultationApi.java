package wm.wastemarche.services.http.ConsultationsApi;

import android.os.Handler;
import android.os.Message;

import org.json.JSONObject;

import java.util.List;

import wm.wastemarche.model.Consultation;
import wm.wastemarche.services.http.ConnectionManager;
import wm.wastemarche.services.http.HttpConstants;
import wm.wastemarche.services.http.Response;

public class ConsultationApi {
    private final ConsultationApiProtocol delegate;

    public ConsultationApi(final ConsultationApiProtocol delegate) {
        this.delegate = delegate;
    }

    public void getConsultations(final String page) {
        final String method = "/v1/consultants?page=" + page;

        final ConnectionManager conn = new ConnectionManager();
        conn.makeGETRequest(method, HttpConstants.defaultHeaders(), new Handler() {
            @Override
            public void handleMessage(final Message msg) {
                final JSONObject json = Response.parseResponse(msg, delegate);
                if( json == null ) {return; }

                final JSONObject data = Response.getPropertyObject("data", json);
                final List<Consultation> consultantsList = Consultation.fromArray(Response.getPropertyArray("consultants", data));
                delegate.consultationsLoaded(page, consultantsList);
            }
        });
    }
}
