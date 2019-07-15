package wm.wastemarche.ui.activities.intro;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import wm.wastemarche.R;
import wm.wastemarche.ui.activities.Helper;

public class ValidateActivity extends AppCompatActivity {

    public static String sharedCode;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate);

        if( sharedCode != null ) {
            Helper.EditTextText(getWindow().getDecorView().findViewById(android.R.id.content), R.id.code, sharedCode);
        }
    }

    public void onSendButtonClicks(final View view) {
    }

    public void onCancelButtonClicks(final View view) {
    }
}
