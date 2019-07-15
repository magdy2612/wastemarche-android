package wm.wastemarche.services.http.authentication.RegisterApi;

import wm.wastemarche.services.http.ApiProtocol;

public interface RegisterApiProtocol extends ApiProtocol {

    void registerApiFailed(String error);

    void registerApiCompleterWithError(String error);

    void registerApiCompleted();

    void registerApiSuccess();
}
