package vn.LeVanNhatQuan;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ActivityCau1 extends AppCompatActivity {
    EditText editText1;
    EditText editText2;
    EditText editTextKQ;
    Button buttonCong;
    void TimDK(){
        editText1 = (EditText) findViewById(R.id.edtA);
        editText2 = (EditText) findViewById(R.id.edtB);
        editTextKQ = (EditText) findViewById(R.id.edtKQ);
        buttonCong = (Button) findViewById(R.id.btnCong);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cau1);
        TimDK();
        buttonCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhepCong();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    void PhepCong(){
        String so1 = editText1.getText().toString();
        String so2 = editText2.getText().toString();
        float n1 = Float.parseFloat(so1);
        float n2 = Float.parseFloat(so2);
        float sum = n1 + n2;
        String kq = String.valueOf(sum);
        editTextKQ.setText(kq);
    }
}