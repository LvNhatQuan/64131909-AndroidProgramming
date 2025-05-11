package nhatquan.ntu.quiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import nhatquan.ntu.QuizApp.database.QuizDatabaseHelper;
import xuanbao.edu.geographyquiz.model.Topic;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    RecyclerView recyclerTopics;
    List<Topic> topicList;
    String role;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        // Khởi tạo database và chèn câu hỏi mẫu nếu chưa có
        QuizDatabaseHelper dbHelper = new QuizDatabaseHelper(this);
        if (dbHelper.getAllQuestions().isEmpty()) {
            dbHelper.insertSampleQuestions();
            Toast.makeText(this, "Đã nạp câu hỏi mẫu!", Toast.LENGTH_SHORT).show();
        }

        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        role = prefs.getString("role", "guest"); // "guest" là mặc định nếu không tìm thấy
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

        // Set toolbar
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Set tên tài khoản trong header
        View headerView = navigationView.getHeaderView(0);
        TextView txtUserRole = headerView.findViewById(R.id.txtUserRole);
        String username = prefs.getString("username", "guest");
        txtUserRole.setText("Tài khoản: " + username);

        // Phân quyền admin
        Menu navMenu = navigationView.getMenu();
        navMenu.setGroupVisible(R.id.adminGroup, "admin".equals(role));

        // Xử lý sự kiện menu
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_add_question) {
                startActivity(new Intent(this, AddQuestionActivity.class));
            } else if (id == R.id.nav_delete_question) {
                startActivity(new Intent(this, DeleteQuestionActivity.class));
            } else if (id == R.id.nav_logout) {
                // Xóa role đã lưu trong SharedPreferences
                prefs.edit().clear().apply();

                // Chuyển về LoginActivity và xóa backstack
                Intent intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
            drawerLayout.closeDrawers();
            return true;
        });

        // Danh sách các topic với hình ảnh
        topicList = new ArrayList<>();
        topicList.add(new Topic("Địa lý Việt Nam", R.drawable.vietnam));
        topicList.add(new Topic("Châu Á", R.drawable.asia));
        topicList.add(new Topic("Châu Âu", R.drawable.europe));
        topicList.add(new Topic("Châu Mỹ", R.drawable.america));
        topicList.add(new Topic("Châu Phi", R.drawable.africa));

        recyclerTopics.setLayoutManager(new GridLayoutManager(this, 2));
        TopicAdapter adapter = new TopicAdapter(this, topicList);
        recyclerTopics.setAdapter(adapter);
    }
}