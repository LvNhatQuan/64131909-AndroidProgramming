package thigk.levannhatquan;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ActivityChucnang3 extends AppCompatActivity {
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

    private ArrayList<Language> getLanguageData() {
        ArrayList<Language> languages = new ArrayList<>();
        languages.add(new Language("anh1", "Mùa xuân trên thành phố HCM", "Mùa xuân"));
        languages.add(new Language("anh2", "Bài ca thống nhất", "Thống nhất"));
        languages.add(new Language("anh3", "Đất nước trọn niềm vui", "Niềm vui"));
        return languages;
    }
}