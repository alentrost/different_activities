package com.example.different_activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

public class MainActivity extends AppCompatActivity {

    private int count = 0;
    private TextView tvCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WindowInsetsControllerCompat controller = WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());
        if (controller != null) {
            controller.hide(WindowInsetsCompat.Type.statusBars());
            controller.setSystemBarsBehavior(WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets navBars = insets.getInsets(WindowInsetsCompat.Type.navigationBars());
            v.setPadding(navBars.left, 0, navBars.right, navBars.bottom);
            return insets;
        });

        tvCount = findViewById(R.id.tvCount);

        if (savedInstanceState != null) {
            count = savedInstanceState.getInt("count", 0);
        } else if (getIntent() != null && getIntent().hasExtra("count")) {
            count = getIntent().getIntExtra("count", 0);
        }
        tvCount.setText(String.valueOf(count));

        Button btnCount = findViewById(R.id.btnCount);
        btnCount.setOnClickListener(v -> {
            count++;
            tvCount.setText(String.valueOf(count));
        });

        Button btnGoActivity2 = findViewById(R.id.btnGoActivity2);
        btnGoActivity2.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HelloActivity.class);
            intent.putExtra("count", count);
            startActivity(intent);
        });

        Button btnGoActivity3 = findViewById(R.id.btnGoActivity3);
        btnGoActivity3.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ThirdActivity.class);
            intent.putExtra("count", count);
            startActivity(intent);
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        if (intent != null && intent.hasExtra("count")) {
            count = intent.getIntExtra("count", count);
            if (tvCount != null) {
                tvCount.setText(String.valueOf(count));
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("count", count);
    }
}