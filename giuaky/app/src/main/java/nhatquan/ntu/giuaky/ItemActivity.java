package nhatquan.ntu.giuaky;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        // Get selected language from intent
        String selectedItem = getIntent().getStringExtra("selected_item");

        // Bind and set text for the TextView
        TextView tvSelectedItem = findViewById(R.id.tvSelectedItem);
        if (selectedItem != null) {
            tvSelectedItem.setText("Bạn đã chọn: " + selectedItem);
        } else {
            tvSelectedItem.setText("Không có mục nào được chọn.");
        }
    }
}
