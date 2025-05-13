package nhatquan.ntu.geographyquiz;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import nhatquan.ntu.geographyquiz.util.User;
import nhatquan.ntu.geographyquiz.util.UserManager;

public class RegisterActivity extends AppCompatActivity {
    EditText edtUserNameRegister, edtEmailRegister, edtPasswordRegister;
    Button btnRegister;
    UserManager userManager;
    TextView txtLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        edtUserNameRegister = findViewById(R.id.edtUsernameRegister);
        edtEmailRegister = findViewById(R.id.edtEmailRegister);
        edtPasswordRegister = findViewById(R.id.edtPasswordRegister);
        btnRegister = findViewById(R.id.btnRegister);
        txtLogin = findViewById(R.id.txtLogin);

        userManager = new UserManager(this);

        btnRegister.setOnClickListener(v -> {
            String username = edtUserNameRegister.getText().toString().trim();
            String email = edtEmailRegister.getText().toString().trim();
            String password = edtPasswordRegister.getText().toString().trim();

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (userManager.userExists(username)) {
                Toast.makeText(this, "Tên người dùng đã tồn tại!", Toast.LENGTH_SHORT).show();
                return;
            }

            User newUser = new User(username, password, email, "user");
            userManager.addUser(newUser);
            Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

        txtLogin.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });
    }
}