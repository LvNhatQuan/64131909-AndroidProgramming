package nhatquan.ntu.giuaky;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityCau1 extends AppCompatActivity {
    private EditText edtA, edtB, edtKQ;
    private Button btnCong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cau1);

        // Bind views
        edtA = findViewById(R.id.edtA);
        edtB = findViewById(R.id.edtB);
        edtKQ = findViewById(R.id.edtKQ);
        btnCong = findViewById(R.id.btnCong);

        // Set button action
        btnCong.setOnClickListener(v -> calculateSum());
    }

    private void calculateSum() {
        try {
            float a = Float.parseFloat(edtA.getText().toString());
            float b = Float.parseFloat(edtB.getText().toString());
            edtKQ.setText(String.valueOf(a + b));
        } catch (NumberFormatException e) {
            edtKQ.setText("Invalid input");
        }
    }
}