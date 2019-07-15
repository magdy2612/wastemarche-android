package wm.wastemarche.services.http.transportations;

import java.util.List;

import wm.wastemarche.model.Transportation;
import wm.wastemarche.services.http.ApiProtocol;

public interface TransportationsApiProtocol extends ApiProtocol {

    void transportationsLoaded(String page, String method_id, List<Transportation> transportations);

    void transportationLoaded(Transportation transportation);

    void transportationCreated();
}
