package vn.LeVanNhatQuan;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ActivityCau3 extends AppCompatActivity {
    private LanguageAdapter languageAdapter;
    private ArrayList<Language> languageList;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cau3);

        // Khởi tạo dữ liệu
        languageList = getLanguageData();

        // Thiết lập RecyclerView
        recyclerView = findViewById(R.id.RecycleListView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        languageAdapter = new LanguageAdapter(this, languageList);
        recyclerView.setAdapter(languageAdapter);
    }

    private ArrayList<Language> getLanguageData() {
        ArrayList<Language> languages = new ArrayList<>();
        languages.add(new Language("english_lg", "Tiếng Anh", "Ngôn ngữ phổ biến nhất thế giới"));
        languages.add(new Language("japan_lg", "Tiếng Nhật", "Ngôn ngữ của đất nước mặt trời mọc"));
        languages.add(new Language("vietnam_lg", "Tiếng Việt", "Ngôn ngữ quốc gia Việt Nam"));
        languages.add(new Language("chinese_lg", "Tiếng Trung", "Ngôn ngữ có nhiều người sử dụng nhất"));
        languages.add(new Language("korean_lg", "Tiếng Hàn", "Ngôn ngữ của xứ sở kim chi"));
        return languages;
    }
}