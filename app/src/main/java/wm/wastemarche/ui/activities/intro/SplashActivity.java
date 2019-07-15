package wm.wastemarche.ui.activities.intro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                final Intent intent = new Intent(SplashActivity.this, LanguageSelectionActivity.class);
                startActivity(intent);
                finish();
            }
        }, 500);
    }
}
