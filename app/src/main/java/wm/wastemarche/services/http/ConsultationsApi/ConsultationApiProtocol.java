package wm.wastemarche.services.http.ConsultationsApi;

import java.util.List;

import wm.wastemarche.model.Consultation;
import wm.wastemarche.services.http.ApiProtocol;

public interface ConsultationApiProtocol extends ApiProtocol {

    void consultationsLoaded(String page, List<Consultation> consultations);
}
