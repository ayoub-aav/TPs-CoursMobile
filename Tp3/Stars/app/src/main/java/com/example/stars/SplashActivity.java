package com.example.stars;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SplashActivity extends AppCompatActivity {
    ImageView logo;
    TextView appName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        logo = findViewById(R.id.logo);
        appName = findViewById(R.id.app_name);

        logo.setScaleX(0.1f);
        logo.setScaleY(0.1f);

        logo.animate()
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(2000)
                .withEndAction(() -> {
                    logo.animate()
                            .translationYBy(-50f)
                            .setDuration(1000)
                            .withEndAction(() -> {
                                appName.setVisibility(TextView.VISIBLE);

                                logo.postDelayed(() -> {
                                    Intent intent = new Intent(SplashActivity.this, ListActivity.class);
                                    startActivity(intent);
                                    finish();
                                }, 1500);
                            }).start();
                }).start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.finish();
    }
}
