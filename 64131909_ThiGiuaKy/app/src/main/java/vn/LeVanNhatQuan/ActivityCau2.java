package vn.LeVanNhatQuan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ActivityCau2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cau2);

        // Tạo dữ liệu cho ListView
        ArrayList<String> nguonDL = new ArrayList<String>();
        nguonDL.add("Tiếng Anh");
        nguonDL.add("Tiếng Việt");
        nguonDL.add("Tiếng Nhật");
        nguonDL.add("Tiếng Trung");
        nguonDL.add("Tiếng Hàn");
        nguonDL.add("Tiếng Pháp");
        nguonDL.add("Tiếng Đức");

        ListView listViewNN = findViewById(R.id.lvIndexLanguage);

        ArrayAdapter<String> NgonNguAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                nguonDL
        );
        listViewNN.setAdapter(NgonNguAdapter);

        listViewNN.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = NgonNguAdapter.getItem(position);

                Toast.makeText(ActivityCau2.this,
                        "Bạn đã chọn: " + selectedItem,
                        Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(ActivityCau2.this, ItemActivity.class);
                intent.putExtra("selected_item", selectedItem);
                startActivity(intent);
            }
        });
    }
}