package thigk.levannhatquan;

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
        setContentView(R.layout.activity_chucnang3);

        // Khởi tạo dữ liệu
        languageList = getLanguageData();

        // Thiết lập RecyclerView
        recyclerView = findViewById(R.id.RecycleListView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        languageAdapter = new LanguageAdapter(this, languageList);
        recyclerView.setAdapter(languageAdapter);
    }

    private ArrayList<Cachmang> getLanguageData() {
        ArrayList<Cachmang> languages = new ArrayList<>();
        languages.add(new Cachmang("anh1", "Tiếng Anh", "Ngôn ngữ phổ biến nhất thế giới"));
        languages.add(new Cachmang("anh2", "Tiếng Nhật", "Ngôn ngữ của đất nước mặt trời mọc"));
        languages.add(new Cachmang("anh3", "Tiếng Việt", "Ngôn ngữ quốc gia Việt Nam"));
        return languages;
    }
}