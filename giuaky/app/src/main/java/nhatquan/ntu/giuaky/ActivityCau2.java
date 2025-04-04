package nhatquan.ntu.giuaky;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;

public class ActivityCau2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cau2);

        // Data for ListView
        String[] languages = {"Tiếng Anh", "Tiếng Việt", "Tiếng Nhật", "Tiếng Trung", "Tiếng Hàn", "Tiếng Pháp", "Tiếng Đức"};

        // Bind and set up ListView
        ListView listView = findViewById(R.id.lvIndexLanguage);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Arrays.asList(languages));
        listView.setAdapter(adapter);

        // Set click listener
        listView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedLanguage = adapter.getItem(position);
            Toast.makeText(this, "Bạn đã chọn: " + selectedLanguage, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ActivityCau2.this, ItemActivity.class);
            intent.putExtra("selected_item", selectedLanguage);
            startActivity(intent);
        });
    }
}
