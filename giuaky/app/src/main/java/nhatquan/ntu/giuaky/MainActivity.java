package nhatquan.ntu.giuaky;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Button btnCau1 = findViewById(R.id.btn1);
        Button btnCau2 = findViewById(R.id.btn2);
        Button btnCau3 = findViewById(R.id.btn3);
        Button btnCau4 = findViewById(R.id.btn4);

        btnCau1.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ActivityCau1.class);
            startActivity(intent);
        });
        btnCau2.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ActivityCau2.class);
            startActivity(intent);
        });
        btnCau3.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ActivityCau3.class);
            startActivity(intent);
        });
        btnCau4.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ActivityCau4.class);
            startActivity(intent);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}