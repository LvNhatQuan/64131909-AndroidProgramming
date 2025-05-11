package nhatquan.ntu.quizapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;

import nhatquan.ntu.quizapp.database.QuizDatabaseHelper;
import nhatquan.ntu.quizapp.util.SubTopicUtil;


public class AddQuestionActivity extends AppCompatActivity {

    Spinner spinnerTopic, spinnerSubTopic, spinnerCorrectAnswer;
    EditText editQuestion, editOptionA, editOptionB, editOptionC, editOptionD;
    Button btnAdd;
    QuizDatabaseHelper dbHelper;

    String[] topics = {"Địa lý Việt Nam", "Châu Á", "Châu Âu", "Châu Mỹ", "Châu Phi"};
    String[] correctAnswers = {"Chọn đáp án đúng","A", "B", "C", "D"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        // Nhận topic nếu có
        String passedTopic = getIntent().getStringExtra("topic");
        setContentView(R.layout.activity_add_question);

        spinnerTopic = findViewById(R.id.spinnerTopic);
        spinnerSubTopic = findViewById(R.id.spinnerSubTopic);
        spinnerCorrectAnswer = findViewById(R.id.spinnerCorrectAnswer);
        editQuestion = findViewById(R.id.editQuestion);
        editOptionA = findViewById(R.id.editOptionA);
        editOptionB = findViewById(R.id.editOptionB);
        editOptionC = findViewById(R.id.editOptionC);
        editOptionD = findViewById(R.id.editOptionD);
        btnAdd = findViewById(R.id.btnAdd);

        dbHelper = new QuizDatabaseHelper(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, topics);
        spinnerTopic.setAdapter(adapter);
        if (passedTopic != null) {
            int index = java.util.Arrays.asList(topics).indexOf(passedTopic);
            if (index >= 0) {
                spinnerTopic.setSelection(index);
                String[] relatedSubtopics = SubTopicUtil.getSubtopics(topics[index]);
                ArrayAdapter<String> subAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_dropdown_item, relatedSubtopics);
                spinnerSubTopic.setAdapter(subAdapter);
            }
        }

        ArrayAdapter<String> correctAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, correctAnswers);
        spinnerCorrectAnswer.setAdapter(correctAdapter);

        btnAdd.setOnClickListener(v -> {
            String topic = spinnerTopic.getSelectedItem().toString();
            String subtopic = spinnerSubTopic.getSelectedItem().toString();
            String question = editQuestion.getText().toString().trim();
            String optionA = editOptionA.getText().toString().trim();
            String optionB = editOptionB.getText().toString().trim();
            String optionC = editOptionC.getText().toString().trim();
            String optionD = editOptionD.getText().toString().trim();
            String correct = spinnerCorrectAnswer.getSelectedItem().toString();
            if (correct.equals("Chọn đáp án đúng")) {
                Toast.makeText(this, "Vui lòng chọn đáp án đúng", Toast.LENGTH_SHORT).show();
                return;
            }

            if (question.isEmpty() || optionA.isEmpty() || optionB.isEmpty() ||
                    optionC.isEmpty() || optionD.isEmpty()) {
                Toast.makeText(this, "Vui lòng điền đầy đủ tất cả các trường", Toast.LENGTH_SHORT).show();
                return;
            }

            // Mảng đáp án
            String[] options = {optionA, optionB, optionC, optionD};

            // Xác định đáp án đúng (0 = A, 1 = B, ...)
            int correctOption = -1;
            switch (correct) {
                case "A":
                    correctOption = 0;
                    break;
                case "B":
                    correctOption = 1;
                    break;
                case "C":
                    correctOption = 2;
                    break;
                case "D":
                    correctOption = 3;
                    break;
            }

            if (correctOption == -1) {
                Toast.makeText(this, "Đáp án đúng không hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            }

            dbHelper.insertQuestion(question, options, correctOption, topic, subtopic);
            Toast.makeText(this, "Đã thêm câu hỏi vào chủ đề: " + topic, Toast.LENGTH_SHORT).show();


            // Reset form
            editQuestion.setText("");
            editOptionA.setText("");
            editOptionB.setText("");
            editOptionC.setText("");
            editOptionD.setText("");
            spinnerCorrectAnswer.setSelection(0);
        });

        spinnerTopic.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedTopic = topics[position];
                String[] relatedSubtopics = SubTopicUtil.getSubtopics(selectedTopic);
                ArrayAdapter<String> subAdapter = new ArrayAdapter<>(AddQuestionActivity.this,
                        android.R.layout.simple_spinner_dropdown_item, relatedSubtopics);
                spinnerSubTopic.setAdapter(subAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }
}