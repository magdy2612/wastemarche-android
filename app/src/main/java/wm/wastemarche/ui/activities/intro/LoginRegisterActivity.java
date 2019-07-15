package wm.wastemarche.ui.activities.intro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import wm.wastemarche.R;

public class LoginRegisterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);
    }

    public void onLoginButtonClicks(final View view) {
        final Intent intent = new Intent(LoginRegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void onRegisterButtonClicks(final View view) {
        final Intent intent = new Intent(LoginRegisterActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}
