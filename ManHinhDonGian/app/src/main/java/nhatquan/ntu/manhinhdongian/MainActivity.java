package nhatquan.ntu.manhinhdongian;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private Button nutMH2;
    private Button nutMH3;

    private void TimDieuKhien() {
        nutMH2 = findViewById(R.id.btnMH2);
        nutMH3 = findViewById(R.id.btnMH3);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Tìm điều khiển nút bấm
        TimDieuKhien();

        // Gắn bộ lắng nghe sự kiện cho nutMH2
        nutMH2.setOnClickListener(view -> {
            Intent intentMH2 = new Intent(MainActivity.this, MH2Activity.class);
            startActivity(intentMH2);
        });

        // Gắn bộ lắng nghe sự kiện cho nutMH3
        nutMH3.setOnClickListener(view -> {
            Intent intentMH3 = new Intent(MainActivity.this, MH3Activity.class);
            startActivity(intentMH3);
        });

        // Xử lý padding với Insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
