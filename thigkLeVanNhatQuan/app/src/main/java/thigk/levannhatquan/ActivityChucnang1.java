package thigk.levannhatquan;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ActivityChucnang1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chucnang1);

        Button checkButton = findViewById(R.id.checkButton);
        EditText monthInput = findViewById(R.id.monthInput);
        EditText yearInput = findViewById(R.id.yearInput);
        TextView resultText = findViewById(R.id.resultText);

        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int day = 30;
                    int month = Integer.parseInt(monthInput.getText().toString());
                    int year = Integer.parseInt(yearInput.getText().toString());

                    String kq;

                    if (day == 30 && month == 4 && year == 1975) {
                        kq = "ĐÚNG";
                    } else {
                        kq = "SAI";
                    }
                    resultText.setText(kq);
                    resultText.setTextColor(kq.equals("ĐÚNG") ? Color.GREEN : Color.RED);

                } catch (NumberFormatException e) {
                    resultText.setText("Vui lòng nhập số hợp lệ");
                    resultText.setTextColor(Color.RED);
                }
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}