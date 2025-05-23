package nhatquan.ntu.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_screen);

        // Set up padding for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Buttons
        Button btnLogin = findViewById(R.id.button);
        Button btnSignup = findViewById(R.id.button2);

        // Set click listener for Login button
        btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(ScreenActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        // Set click listener for Signup button
        btnSignup.setOnClickListener(v -> {
            Intent intent = new Intent(ScreenActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}