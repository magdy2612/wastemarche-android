package wm.wastemarche.services.http.authentication.LoginApi;

public interface LoginApiProtocol {
    void loginApiCompleted();

    void loginApiFailed(String error);

    void verifyAccount(String code);

    void forgotPasswordCompleted();
}
