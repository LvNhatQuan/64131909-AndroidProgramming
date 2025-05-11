package nhatquan.ntu.quizapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import nhatquan.ntu.quizapp.util.User;
import nhatquan.ntu.quizapp.util.UserManager;

public class LoginActivity extends AppCompatActivity {

    EditText edtUsername, edtPassword, edtEmail;
    Button btnLogin;
    TextView txtRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        edtEmail = findViewById(R.id.edtEmail);
        btnLogin = findViewById(R.id.btnLogin);
        txtRegister = findViewById(R.id.txtRegister);

        btnLogin.setOnClickListener(v -> {
            String username = edtUsername.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();

            UserManager userManager = new UserManager(this);

            if (username.equals("admin") && password.equals("admin") && email.equals("admin@gmail.com")) {
                // Admin mặc định
                saveLogin("admin", "admin");
            } else if (username.equals("bao") && password.equals("bao") && email.equals("bao@gmail.com")) {
                // Guest mặc định
                saveLogin("guest", "bao");
            }

            // Kiểm tra với thông tin người dùng đã đăng ký
            else {
                User user = userManager.getUserByUsername(username);
                if (user != null && user.getPassword().equals(password) && user.getEmail().equals(email)) {
                    saveLogin(user.getRole(), user.getUsername());
                } else {
                    Toast.makeText(this, "Sai thông tin đăng nhập!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        txtRegister.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });
    }
    private void saveLogin(String role, String username) {
        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        prefs.edit().putString("role", role).putString("username", username).apply();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("role", role);
        startActivity(intent);
        finish();
    }
}