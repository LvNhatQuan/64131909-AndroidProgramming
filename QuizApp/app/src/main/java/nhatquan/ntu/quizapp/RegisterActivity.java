package nhatquan.ntu.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText edtUsernameRegister, edtEmailRegister, edtPasswordRegister, edtConfirmPasswordRegister;
    private MaterialButton btnRegister;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        // Ánh xạ view
        edtUsernameRegister = findViewById(R.id.edtUsernameRegister);
        edtEmailRegister = findViewById(R.id.edtEmailRegister);
        edtPasswordRegister = findViewById(R.id.edtPasswordRegister);
        edtConfirmPasswordRegister = findViewById(R.id.edtComfirmPasswordRegister);
        btnRegister = findViewById(R.id.btnRegister);

        mAuth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(v -> {
            String username = edtUsernameRegister.getText().toString().trim();
            String email = edtEmailRegister.getText().toString().trim();
            String password = edtPasswordRegister.getText().toString().trim();
            String confirmPassword = edtConfirmPasswordRegister.getText().toString().trim();

            if (username.isEmpty()) {
                edtUsernameRegister.setError("Vui lòng nhập tên tài khoản");
                edtUsernameRegister.requestFocus();
                return;
            }

            if (email.isEmpty()) {
                edtEmailRegister.setError("Vui lòng nhập email");
                edtEmailRegister.requestFocus();
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                edtEmailRegister.setError("Email không hợp lệ");
                edtEmailRegister.requestFocus();
                return;
            }

            if (password.isEmpty()) {
                edtPasswordRegister.setError("Vui lòng nhập mật khẩu");
                edtPasswordRegister.requestFocus();
                return;
            }

            if (password.length() < 6) {
                edtPasswordRegister.setError("Mật khẩu ít nhất 6 ký tự");
                edtPasswordRegister.requestFocus();
                return;
            }

            if (!password.equals(confirmPassword)) {
                edtConfirmPasswordRegister.setError("Mật khẩu xác nhận không khớp");
                edtConfirmPasswordRegister.requestFocus();
                return;
            }

            registerUser(email, password);
        });
    }

    private void registerUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(RegisterActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();

                        // Có thể lưu thêm thông tin username vào database nếu muốn

                        // Chuyển về màn hình đăng nhập hoặc màn hình chính
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();

                    } else {
                        Toast.makeText(RegisterActivity.this, "Đăng ký thất bại: " + task.getException().getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });
    }
}
