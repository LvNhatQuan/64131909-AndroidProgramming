package nhatquan.ntu.ontap_gk;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ActivityCau1 extends AppCompatActivity {

private EditText edtA, edtB, edtKq;
private Button btnCong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cau1);
        edtA = findViewById(R.id.edtA);
        edtB = findViewById(R.id.edtB);
        edtKq = findViewById(R.id.edtKq);
        btnCong = findViewById(R.id.btnCong);

        btnCong.setOnClickListener(v -> calculateSum());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void calculateSum() {
        try {
            float a = Float.parseFloat(edtA.getText().toString());
            float b = Float.parseFloat(edtB.getText().toString());
            edtKq.setText(String.valueOf(a + b ));
        }catch (NumberFormatException e){
            edtKq.setText("Invalid input");
        }
    }
}