package nhatquan.ntu.geographyquiz;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import nhatquan.ntu.geographyquiz.adapter.DeleteQuestionAdapter;
import nhatquan.ntu.geographyquiz.database.QuizDatabaseHelper;
import nhatquan.ntu.geographyquiz.model.Question;

public class DeleteQuestionActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DeleteQuestionAdapter adapter;
    QuizDatabaseHelper dbHelper;
    List<Object> itemList;
    Spinner spinnerTopic;
    List<String> topicList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_delete_question);

        //khởi tạo view
        recyclerView = findViewById(R.id.recyclerDelete);
        spinnerTopic = findViewById(R.id.spinnerTopic);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //database
        dbHelper = new QuizDatabaseHelper(this);

        // Lấy danh sách topic
        topicList = dbHelper.getAllTopics();
        if (topicList.isEmpty()) {
            topicList.add("Không có chủ đề");
        }

        // Load topics vào spinner
        ArrayAdapter<String> topicAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, topicList);
        topicAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTopic.setAdapter(topicAdapter);

        // Xử lý khi chọn topic
        spinnerTopic.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedTopic = topicList.get(position);
                loadQuestionsByTopic(selectedTopic);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void loadQuestionsByTopic(String topic) {
        List<Question> questionList = dbHelper.getQuestionsByTopic(topic);
        if (questionList.isEmpty()) {
            Toast.makeText(this, "Không có câu hỏi trong chủ đề này", Toast.LENGTH_SHORT).show();
        }

        itemList = buildItemList(questionList);

        adapter = new DeleteQuestionAdapter(this, itemList, question -> {
            dbHelper.deleteQuestion(question.getId());
            Toast.makeText(this, "Đã xoá câu hỏi", Toast.LENGTH_SHORT).show();
            loadQuestionsByTopic(topic);
        });

        recyclerView.setAdapter(adapter);
    }

    private List<Object> buildItemList(List<Question> questions) {
        Map<String, List<Question>> map = new LinkedHashMap<>();
        for (Question q : questions) {
            if (!map.containsKey(q.getSubTopic())) {
                map.put(q.getSubTopic(), new ArrayList<>());
            }
            map.get(q.getSubTopic()).add(q);
        }

        List<Object> result = new ArrayList<>();
        for (String subtopic : map.keySet()) {
            result.add(subtopic);
            result.addAll(map.get(subtopic));
        }
        return result;
    }
}