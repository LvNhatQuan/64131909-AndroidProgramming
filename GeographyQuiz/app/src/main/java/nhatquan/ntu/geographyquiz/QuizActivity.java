package nhatquan.ntu.geographyquiz;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.os.CountDownTimer;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import nhatquan.ntu.geographyquiz.database.QuizDatabaseHelper;
import nhatquan.ntu.geographyquiz.model.Question;

public class QuizActivity extends AppCompatActivity {

    TextView txtQuestion, timerTextView;
    Button btnA, btnB, btnC, btnD;

    ArrayList<Question> questionList = new ArrayList<>();
    int currentQuestion = 0;
    int score = 0;

    String topic, subtopic;
    QuizDatabaseHelper dbHelper;
    CountDownTimer countDownTimer;
    int timePerQuestion = 60000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quiz);

        timerTextView = findViewById(R.id.timerTextView);
        txtQuestion = findViewById(R.id.txtQuestion);
        btnA = findViewById(R.id.btnA);
        btnB = findViewById(R.id.btnB);
        btnC = findViewById(R.id.btnC);
        btnD = findViewById(R.id.btnD);

        topic = getIntent().getStringExtra("topic");
        subtopic = getIntent().getStringExtra("subTopic");

        dbHelper = new QuizDatabaseHelper(this);
        questionList = dbHelper.getQuestions(topic, subtopic);

        showQuestion();

        btnA.setOnClickListener(v -> checkAnswer(0));
        btnB.setOnClickListener(v -> checkAnswer(1));
        btnC.setOnClickListener(v -> checkAnswer(2));
        btnD.setOnClickListener(v -> checkAnswer(3));
    }


    private void showQuestion() {
        if (currentQuestion < questionList.size()) {
            Question q = questionList.get(currentQuestion);
            txtQuestion.setText(q.getQuestionText());
            btnA.setText(q.getOptions()[0]);
            btnB.setText(q.getOptions()[1]);
            btnC.setText(q.getOptions()[2]);
            btnD.setText(q.getOptions()[3]);

            startTimer();
        } else {
            Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
            intent.putExtra("score", score);
            startActivity(intent);
            finish();
        }
    }

    private void checkAnswer(int selectedOption) {
        if (countDownTimer != null) {
            countDownTimer.cancel(); // Hủy đếm khi người dùng bấm
        }

        if (questionList.get(currentQuestion).getCorrectOption() == selectedOption) {
            score ++;
            Toast.makeText(this, "Đúng!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Sai!", Toast.LENGTH_SHORT).show();
        }
        currentQuestion++;
        showQuestion();
    }

    private void startTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel(); // Reset nếu đang đếm
        }

        countDownTimer = new CountDownTimer(timePerQuestion, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long seconds = millisUntilFinished / 1000;
                timerTextView.setText("Thời gian: " + seconds + "s");
            }

            @Override
            public void onFinish() {
                Toast.makeText(QuizActivity.this, "Hết giờ!", Toast.LENGTH_SHORT).show();
                currentQuestion++;
                showQuestion();
            }
        };

        countDownTimer.start();
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}