package wm.wastemarche.ui.activities.intro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import wm.wastemarche.R;
import wm.wastemarche.model.Company;
import wm.wastemarche.services.http.authentication.RegisterApi.RegisterApi;
import wm.wastemarche.services.http.authentication.RegisterApi.RegisterApiProtocol;
import wm.wastemarche.services.http.companies.CompaniesApi;
import wm.wastemarche.services.http.companies.CompaniesApiProtocol;

public class RegisterActivity extends AppCompatActivity implements RegisterApiProtocol, CompaniesApiProtocol {

    private static final String TAG = "RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final CompaniesApi companiesApi = new CompaniesApi(this);
        companiesApi.getCompaniesTypes();
    }

    public void onRegisterButtonClicks(final View view) {
        final String name = ((TextView) findViewById(R.id.nameTextField)).getText().toString();
        final String mobile = ((TextView) findViewById(R.id.mobileTextField)).getText().toString();
        final String pasword = ((TextView) findViewById(R.id.passwordTextField)).getText().toString();
        final String cPassword = ((TextView) findViewById(R.id.confirmPasswordTextField)).getText().toString();
        final String companyType = ((Spinner) findViewById(R.id.companyTpeSpinner)).getSelectedItem().toString();

        if (!pasword.equalsIgnoreCase(cPassword)) {
            registerApiCompleterWithError("Password mismatch");
            return;
        }

        if (name.isEmpty()) {
            registerApiCompleterWithError("name is empty");
            return;
        }

        if (mobile.isEmpty()) {
            registerApiCompleterWithError("mobile is empty");
            return;
        }

        final RegisterApi registerApi = new RegisterApi(this);
        registerApi.start(name, "", mobile, pasword, companyType);
    }

    public void onLoginButtonClicks(final View view) {
        final Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void apiError(final int errorCode) {
        Log.d(TAG, "apiError() called with: errorCode = [" + errorCode + "]");
    }

    @Override
    public void registerApiFailed(final String error) {
        Log.d(TAG, "registerApiFailed() called with: error = [" + error + "]");
    }

    @Override
    public void registerApiCompleterWithError(final String error) {
        Log.d(TAG, "registerApiCompleterWithError() called with: error = [" + error + "]");
    }

    @Override
    public void registerApiCompleted() {
        Log.d(TAG, "registerApiCompleted() called");
        Toast.makeText(getApplicationContext(), R.string.alert_done, Toast.LENGTH_LONG).show();
        onLoginButtonClicks(null);
    }

    @Override
    public void registerApiSuccess() {
        Log.d(TAG, "registerApiSuccess() called");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(RegisterActivity.this, ValidateActivity.class));
            }
        });
    }

    @Override
    public void companiesTypesloaded(final List<Company> companies) {
        final String[] items = new String[companies.size()];
        for (int i = 0, len = companies.size(); i < len; i++) {
            items[i] = companies.get(i).title;
        }
        final Spinner dropdown = findViewById(R.id.companyTpeSpinner);
        final SpinnerAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

    }

    @Override
    public void companiesFialed(final String error) {

    }
}
