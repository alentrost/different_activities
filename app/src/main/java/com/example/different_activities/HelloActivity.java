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

public class HelloActivity extends AppCompatActivity {

    private int count = 0;
    private TextView tvCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);

        WindowInsetsControllerCompat controller = WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());
        if (controller != null) {
            controller.hide(WindowInsetsCompat.Type.statusBars());
            controller.setSystemBarsBehavior(WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.hello_layout), (v, insets) -> {
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

        Button btnGoActivity1 = findViewById(R.id.btnGoActivity1);
        btnGoActivity1.setOnClickListener(v -> {
            Intent intent = new Intent(HelloActivity.this, MainActivity.class);
            intent.putExtra("count", count);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("count", count);
    }
}