package wm.wastemarche.services.http.requests;

import java.util.List;

import wm.wastemarche.model.Request;
import wm.wastemarche.services.http.ApiProtocol;

public interface RequestApiProtocol extends ApiProtocol {

    void requestsLoaded(String page, List<Request> requests);

    void requestLoaded(Request request);

    void requestExtraInfoCompleted();

    void sendOfferCompleted();
}
