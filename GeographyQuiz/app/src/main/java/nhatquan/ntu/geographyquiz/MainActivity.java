package nhatquan.ntu.geographyquiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import nhatquan.ntu.geographyquiz.adapter.TopicAdapter;
import nhatquan.ntu.geographyquiz.database.QuizDatabaseHelper;
import nhatquan.ntu.geographyquiz.model.Topic;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_ADD_QUESTION = 1001;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    RecyclerView recyclerTopics;
    EditText etSearch;

    List<Topic> topicList;
    List<Topic> filteredList;
    TopicAdapter adapter;

    String role;

    QuizDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        dbHelper = new QuizDatabaseHelper(this);

        if (dbHelper.getAllQuestions().isEmpty()) {
            dbHelper.insertSampleQuestions();
            Toast.makeText(this, "Đã nạp câu hỏi mẫu!", Toast.LENGTH_SHORT).show();
        }

        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        role = prefs.getString("role", "guest");
        if (role == null || role.isEmpty()) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            return;
        }

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);
        recyclerTopics = findViewById(R.id.recyclerTopics);
        etSearch = findViewById(R.id.etSearch);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        View headerView = navigationView.getHeaderView(0);
        TextView txtUserRole = headerView.findViewById(R.id.txtUserRole);
        String username = prefs.getString("username", "guest");
        txtUserRole.setText("Tài khoản: " + username);

        Menu navMenu = navigationView.getMenu();
        navMenu.setGroupVisible(R.id.adminGroup, "admin".equals(role));

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.share) {
                String shareBody = "Hye, I am Using best online quiz app" +
                        "http://play.google.com/store/apps/details?id=" + getPackageName();
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(intent);
            } else if (id == R.id.rate) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
            } else if (id == R.id.privacy) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("Add Privacy Policy Link")));
            } else if (id == R.id.nav_add_question) {
                Intent intent = new Intent(this, AddQuestionActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD_QUESTION);
            } else if (id == R.id.nav_delete_question) {
                startActivity(new Intent(this, DeleteQuestionActivity.class));
            } else if (id == R.id.nav_logout) {
                prefs.edit().clear().apply();
                Intent intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
            drawerLayout.closeDrawers();
            return true;
        });

        // Load danh sách chủ đề từ database lần đầu
        reloadTopicListFromDB();

        recyclerTopics.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new TopicAdapter(this, filteredList);
        recyclerTopics.setAdapter(adapter);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterTopics(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    private void reloadTopicListFromDB() {
        List<String> topicNames = dbHelper.getAllTopics();

        topicList = new ArrayList<>();
        for (String name : topicNames) {
            int drawableId = getDrawableForTopic(name);
            topicList.add(new Topic(name, drawableId));
        }
        filteredList = new ArrayList<>(topicList);
        if (adapter != null) {
            adapter.updateList(filteredList);
        }
    }

    private int getDrawableForTopic(String topicName) {
        switch (topicName) {
            case "Địa lý Việt Nam":
                return R.drawable.vietnam;
            case "Châu Á":
                return R.drawable.asia;
            case "Châu Âu":
                return R.drawable.europe;
            case "Châu Mỹ":
                return R.drawable.america;
            case "Châu Phi":
                return R.drawable.africa;
            default:
                return R.drawable.default_topic_icon; // icon mặc định
        }
    }

    private void filterTopics(String keyword) {
        keyword = keyword.toLowerCase().trim();
        filteredList.clear();

        if (keyword.isEmpty()) {
            filteredList.addAll(topicList);
        } else {
            for (Topic topic : topicList) {
                if (topic.getTitle().toLowerCase().contains(keyword)) {
                    filteredList.add(topic);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_ADD_QUESTION && resultCode == RESULT_OK) {
            reloadTopicListFromDB();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
