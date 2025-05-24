package nhatquan.ntu.geographyquiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import nhatquan.ntu.geographyquiz.util.User;
import nhatquan.ntu.geographyquiz.util.UserManager;

public class LoginActivity extends AppCompatActivity {

    EditText edtUsername, edtPassword, edtEmail;
    Button btnLogin;
    TextView txtRegister;
    TextView txtForgotPassword;
    CheckBox checkboxRememberMe;
    SharedPreferences prefs;

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
        txtForgotPassword = findViewById(R.id.txtForgotPassword);
        checkboxRememberMe = findViewById(R.id.checkboxRememberMe);

        prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);

        boolean isRemembered = prefs.getBoolean("rememberMe", false);
        if (isRemembered) {
            edtUsername.setText(prefs.getString("saved_username", ""));
            edtPassword.setText(prefs.getString("saved_password", ""));
            edtEmail.setText(prefs.getString("saved_email", ""));
            checkboxRememberMe.setChecked(true);
        }

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

        txtForgotPassword.setOnClickListener(v -> {
            Intent intent = new Intent(this, ForgotPasswordActivity.class);
            startActivity(intent);
        });
    }

    private void saveLogin(String role, String username) {
        SharedPreferences.Editor editor = prefs.edit();
        if (checkboxRememberMe.isChecked()) {
            editor.putBoolean("rememberMe", true);
            editor.putString("saved_username", edtUsername.getText().toString());
            editor.putString("saved_password", edtPassword.getText().toString());
            editor.putString("saved_email", edtEmail.getText().toString());
        } else {
            editor.remove("rememberMe");
            editor.remove("saved_username");
            editor.remove("saved_password");
            editor.remove("saved_email");
        }
        editor.putString("role", role);
        editor.putString("username", username);
        editor.apply();

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("role", role);
        startActivity(intent);
        finish();
    }
}
