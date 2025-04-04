package thigk.levannhatquan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ActivityChucnang2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chucnang2);

        // Tạo dữ liệu cho ListView
        ArrayList<String> nguonDL = new ArrayList<String>();
        nguonDL.add("Tiến về Sài Gòn");
        nguonDL.add(" phóng Miền nam");
        nguonDL.add("Đất nước trọn niềm vui ");
        nguonDL.add("Tiếng Trung");
        nguonDL.add("Bài ca thống nhất");
        nguonDL.add("Mùa xuân trên thành phố HCm");

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

                Toast.makeText(ActivityChucnang2.this,
                        "Bạn đã chọn: " + selectedItem,
                        Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(ActivityChucnang2.this, Item3Activity.class);
                intent.putExtra("selected_item", selectedItem);
                startActivity(intent);
            }
        });
    }
}