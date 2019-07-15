package wm.wastemarche.ui.activities.intro;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import wm.wastemarche.R;
import wm.wastemarche.services.http.authentication.LoginApi.ForgotPasswordApi;
import wm.wastemarche.services.http.authentication.LoginApi.LoginApiProtocol;

public class ForgotPasswordActivity extends AppCompatActivity implements LoginApiProtocol {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
    }

    public void onCancelButtonClicks(final View view) {
        finish();
    }

    public void onSendButtonClicks(final View view) {
        final String email = ((EditText) findViewById(R.id.emailTextField)).getText().toString();
        final ForgotPasswordApi forgotPasswordApi = new ForgotPasswordApi(this);
        forgotPasswordApi.start(email);
    }

    @Override
    public void loginApiCompleted() {
    }

    @Override
    public void loginApiFailed(final String error) {
    }

    @Override
    public void verifyAccount(final String code) {

    }

    @Override
    public void forgotPasswordCompleted() {
        Toast.makeText(getApplicationContext(), "Code sent.", Toast.LENGTH_LONG).show();
        finish();
    }
}
