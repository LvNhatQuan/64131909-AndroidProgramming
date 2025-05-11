package xuanbao.edu.geographyquiz;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ResultActivity extends AppCompatActivity {
    TextView txtScore;
    Button btnBackHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result);

        txtScore = findViewById(R.id.txtScore);
        btnBackHome = findViewById(R.id.btnBackHome);

        int score = getIntent().getIntExtra("score", 0);
        txtScore.setText("Số điểm của bạn: " + score);

        btnBackHome.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}