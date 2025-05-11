package nhatquan.ntu.quizapp;

import android.content.Intent;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import nhatquan.ntu.quizapp.util.SubTopicUtil;


public class SubTopicActivity extends AppCompatActivity {
    ListView listView;
    String[] subTopics;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sub_topic);


        listView = findViewById(R.id.listViewSubTopics);

        // Lấy tên chủ đề từ Intent
        String topic = getIntent().getStringExtra("topic");

        // Tuỳ theo topic, hiển thị danh sách con phù hợp
        subTopics = SubTopicUtil.getSubtopics(topic);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, subTopics);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(SubTopicActivity.this, QuizActivity.class);
            intent.putExtra("topic", topic);
            intent.putExtra("subTopic", subTopics[position]);
            startActivity(intent);
        });
    }
}