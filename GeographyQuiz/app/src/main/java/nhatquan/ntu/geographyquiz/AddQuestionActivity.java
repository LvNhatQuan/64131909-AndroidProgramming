package nhatquan.ntu.geographyquiz;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import nhatquan.ntu.geographyquiz.database.QuizDatabaseHelper;
import nhatquan.ntu.geographyquiz.util.SubTopicUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddQuestionActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_PICK_IMAGE = 2001;

    Spinner spinnerTopic, spinnerSubTopic, spinnerCorrectAnswer;
    EditText editQuestion, editOptionA, editOptionB, editOptionC, editOptionD;
    Button btnAdd, btnAddTopic, btnAddSubTopic, btnSelectTopicImage;
    ImageView imgTopicPreview;
    QuizDatabaseHelper dbHelper;

    List<String> topicList;
    List<String> subTopicList;

    String[] correctAnswers = {"Chọn đáp án đúng", "A", "B", "C", "D"};

    Uri selectedImageUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
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

        btnAddTopic = findViewById(R.id.btnAddTopic);
        btnAddSubTopic = findViewById(R.id.btnAddSubTopic);
        btnSelectTopicImage = findViewById(R.id.btnSelectTopicImage);
        imgTopicPreview = findViewById(R.id.imgTopicPreview);

        dbHelper = new QuizDatabaseHelper(this);

        topicList = dbHelper.getAllTopics();
        if (topicList.isEmpty()) {
            topicList = new ArrayList<>(Arrays.asList("Địa lý Việt Nam", "Châu Á", "Châu Âu", "Châu Mỹ", "Châu Phi"));
        }

        ArrayAdapter<String> topicAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, topicList);
        spinnerTopic.setAdapter(topicAdapter);

        btnAddTopic.setOnClickListener(v -> showAddDialog("Thêm chủ đề mới", topicList, topicAdapter, spinnerTopic));

        String firstTopic = topicList.get(0);
        String[] relatedSubtopics = SubTopicUtil.getSubtopics(firstTopic);
        subTopicList = new ArrayList<>(Arrays.asList(relatedSubtopics));

        ArrayAdapter<String> subTopicAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, subTopicList);
        spinnerSubTopic.setAdapter(subTopicAdapter);

        btnAddSubTopic.setOnClickListener(v -> showAddDialog("Thêm tiểu mục mới", subTopicList, subTopicAdapter, spinnerSubTopic));

        ArrayAdapter<String> correctAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, correctAnswers);
        spinnerCorrectAnswer.setAdapter(correctAdapter);

        spinnerTopic.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
                String selectedTopic = topicList.get(position);
                String[] relatedSubtopics = SubTopicUtil.getSubtopics(selectedTopic);
                subTopicList.clear();
                subTopicList.addAll(Arrays.asList(relatedSubtopics));
                subTopicAdapter.notifyDataSetChanged();
                spinnerSubTopic.setSelection(0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        btnSelectTopicImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
        });

        btnAdd.setOnClickListener(v -> {
            if (spinnerTopic.getSelectedItem() == null || spinnerSubTopic.getSelectedItem() == null) {
                Toast.makeText(this, "Vui lòng chọn chủ đề và tiểu mục", Toast.LENGTH_SHORT).show();
                return;
            }
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

            String[] options = {optionA, optionB, optionC, optionD};

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

            // TODO: Lưu selectedImageUri nếu muốn

            dbHelper.insertQuestion(question, options, correctOption, topic, subtopic);
            Toast.makeText(this, "Đã thêm câu hỏi vào chủ đề: " + topic, Toast.LENGTH_SHORT).show();

            setResult(RESULT_OK);
            finish();
        });
    }

    private void showAddDialog(String title, List<String> list, ArrayAdapter<String> adapter, Spinner spinner) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("Thêm", (dialog, which) -> {
            String newItem = input.getText().toString().trim();
            if (newItem.isEmpty()) {
                Toast.makeText(this, "Tên không được để trống", Toast.LENGTH_SHORT).show();
                return;
            }
            if (list.contains(newItem)) {
                Toast.makeText(this, "Tên đã tồn tại", Toast.LENGTH_SHORT).show();
                return;
            }
            list.add(newItem);
            adapter.notifyDataSetChanged();
            spinner.setSelection(list.indexOf(newItem));
            Toast.makeText(this, "Đã thêm: " + newItem, Toast.LENGTH_SHORT).show();
        });

        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            imgTopicPreview.setImageURI(selectedImageUri);
        }
    }
}