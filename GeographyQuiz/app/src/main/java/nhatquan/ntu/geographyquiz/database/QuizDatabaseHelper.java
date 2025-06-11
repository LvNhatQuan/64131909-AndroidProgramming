package nhatquan.ntu.geographyquiz.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import nhatquan.ntu.geographyquiz.model.Question;

public class QuizDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "quiz.db";
    private static final int DB_VERSION = 2;
    private final Context context;

    public QuizDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
        SQLiteDatabase db = getWritableDatabase();
        if (getAllQuestions().isEmpty()) {
            insertSampleQuestions();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE questions (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "question TEXT, " +
                "option1 TEXT, " +
                "option2 TEXT, " +
                "option3 TEXT, " +
                "option4 TEXT, " +
                "correctOption INTEGER, " +
                "topic TEXT, " +
                "subtopic TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS questions");
        onCreate(db);
    }


    public void insertQuestion(String question, String[] options, int correctOption, String topic, String subtopic) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("question", question);
        values.put("option1", options[0]);
        values.put("option2", options[1]);
        values.put("option3", options[2]);
        values.put("option4", options[3]);
        values.put("correctOption", correctOption);
        values.put("topic", topic);
        values.put("subtopic", subtopic);
        db.insert("questions", null, values);
    }

    public ArrayList<Question> getQuestions(String topic, String subtopic) {
        ArrayList<Question> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM questions WHERE topic = ? AND subtopic = ?", new String[]{topic, subtopic});
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String questionText = cursor.getString(cursor.getColumnIndexOrThrow("question"));
                String[] options = {
                        cursor.getString(cursor.getColumnIndexOrThrow("option1")),
                        cursor.getString(cursor.getColumnIndexOrThrow("option2")),
                        cursor.getString(cursor.getColumnIndexOrThrow("option3")),
                        cursor.getString(cursor.getColumnIndexOrThrow("option4"))
                };
                int correctOption = cursor.getInt(cursor.getColumnIndexOrThrow("correctOption"));
                String sub = cursor.getString(cursor.getColumnIndexOrThrow("subtopic"));

                Question q = new Question();
                q.setId(id);
                q.setQuestionText(questionText);
                q.setOptions(options);
                q.setCorrectOption(correctOption);
                q.setSubTopic(sub);
                list.add(q);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public List<Question> getAllQuestions() {
        List<Question> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM questions", null);

        if (cursor.moveToFirst()) {
            do {
                Question q = new Question();
                q.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                q.setTopic(cursor.getString(cursor.getColumnIndexOrThrow("topic")));
                q.setSubTopic(cursor.getString(cursor.getColumnIndexOrThrow("subtopic")));
                q.setQuestionText(cursor.getString(cursor.getColumnIndexOrThrow("question")));

                String[] options = {
                        cursor.getString(cursor.getColumnIndexOrThrow("option1")),
                        cursor.getString(cursor.getColumnIndexOrThrow("option2")),
                        cursor.getString(cursor.getColumnIndexOrThrow("option3")),
                        cursor.getString(cursor.getColumnIndexOrThrow("option4"))
                };
                q.setOptions(options);
                q.setCorrectOption(cursor.getInt(cursor.getColumnIndexOrThrow("correctOption")));

                list.add(q);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return list;
    }


    public void deleteQuestion(int questionId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("questions", "id = ?", new String[]{String.valueOf(questionId)});
        db.close();
    }

    public List<String> getAllTopics() {
        List<String> topics = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT DISTINCT topic FROM questions", null);
        if (cursor.moveToFirst()) {
            do {
                topics.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return topics;
    }

    public List<Question> getQuestionsByTopic(String topic) {
        List<Question> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM questions WHERE topic = ?", new String[]{topic});

        if (cursor.moveToFirst()) {
            do {
                Question q = new Question();
                q.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                q.setTopic(cursor.getString(cursor.getColumnIndexOrThrow("topic")));
                q.setSubTopic(cursor.getString(cursor.getColumnIndexOrThrow("subtopic")));
                q.setQuestionText(cursor.getString(cursor.getColumnIndexOrThrow("question")));

                String[] options = {
                        cursor.getString(cursor.getColumnIndexOrThrow("option1")),
                        cursor.getString(cursor.getColumnIndexOrThrow("option2")),
                        cursor.getString(cursor.getColumnIndexOrThrow("option3")),
                        cursor.getString(cursor.getColumnIndexOrThrow("option4"))
                };
                q.setOptions(options);

                q.setCorrectOption(cursor.getInt(cursor.getColumnIndexOrThrow("correctOption")));

                list.add(q);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return list;
    }

    public void insertSampleQuestions() {
        if (!getAllQuestions().isEmpty()) return;

        // Chủ đề: Địa lý Việt Nam
        // Khí hậu
        insertQuestion("Miền khí hậu nào có mùa đông lạnh ở Việt Nam?",
                new String[]{"Miền Trung", "Miền Nam", "Cả nước", "Miền Bắc"}, 3,
                "Địa lý Việt Nam", "Khí hậu");

        insertQuestion("Khí hậu Việt Nam thuộc loại nào?",
                new String[]{"Cận nhiệt đới", "Nhiệt đới ẩm gió mùa", "Ôn đới", "Hàn đới"}, 1,
                "Địa lý Việt Nam", "Khí hậu");

        insertQuestion("Miền Nam Việt Nam có mấy mùa rõ rệt?",
                new String[]{"2 mùa", "3 mùa", "4 mùa", "Không rõ mùa"}, 0,
                "Địa lý Việt Nam", "Khí hậu");
        insertQuestion("Gió mùa đông bắc ảnh hưởng rõ rệt nhất ở khu vực nào của Việt Nam?",
                new String[]{"Miền Trung", "Miền Nam", "Miền Bắc", "Tây Nguyên"}, 2,
                "Địa lý Việt Nam", "Khí hậu");

        insertQuestion("Thời tiết mùa hè ở miền Bắc Việt Nam thường như thế nào?",
                new String[]{"Nóng và khô", "Mát mẻ", "Nóng ẩm, mưa nhiều", "Lạnh và khô"}, 2,
                "Địa lý Việt Nam", "Khí hậu");

        insertQuestion("Hiện tượng El Nino gây ra điều gì ở Việt Nam?",
                new String[]{"Mưa nhiều", "Nhiệt độ thấp", "Khô hạn kéo dài", "Mùa đông kéo dài"}, 2,
                "Địa lý Việt Nam", "Khí hậu");

        insertQuestion("Tây Nguyên có kiểu khí hậu nào đặc trưng?",
                new String[]{"Nhiệt đới khô", "Ôn đới", "Nhiệt đới gió mùa", "Nhiệt đới ẩm có mùa khô"}, 3,
                "Địa lý Việt Nam", "Khí hậu");

        insertQuestion("Lượng mưa trung bình năm của Việt Nam dao động khoảng bao nhiêu?",
                new String[]{"500–800 mm", "1000–2000 mm", "2000–3000 mm", "Trên 4000 mm"}, 1,
                "Địa lý Việt Nam", "Khí hậu");

        insertQuestion("Khu vực nào ở Việt Nam chịu ảnh hưởng mạnh của gió Tây khô nóng?",
                new String[]{"Miền Bắc", "Bắc Trung Bộ", "Tây Bắc", "Nam Bộ"}, 1,
                "Địa lý Việt Nam", "Khí hậu");

        insertQuestion("Mùa mưa ở Nam Bộ Việt Nam thường bắt đầu từ tháng mấy?",
                new String[]{"Tháng 1", "Tháng 3", "Tháng 5", "Tháng 11"}, 2,
                "Địa lý Việt Nam", "Khí hậu");

        // Kinh tế
        insertQuestion("Vùng nào phát triển công nghiệp mạnh nhất ở nước ta?",
                new String[]{"Tây Nguyên", "Đông Nam Bộ", "Đồng bằng sông Hồng", "Miền núi phía Bắc"}, 1,
                "Địa lý Việt Nam", "Kinh tế");

        insertQuestion("Nông sản chính của Việt Nam là gì?",
                new String[]{"Lúa gạo", "Ngô", "Cà phê", "Trà"}, 0,
                "Địa lý Việt Nam", "Kinh tế");

        insertQuestion("Ngành nào sau đây thuộc lĩnh vực dịch vụ?",
                new String[]{"Trồng trọt", "Chăn nuôi", "Khai thác", "Du lịch"}, 3,
                "Địa lý Việt Nam", "Kinh tế");

        // Tài nguyên
        insertQuestion("Việt Nam có khoáng sản nào sau đây?",
                new String[]{"Than đá", "Dầu khí", "Bauxite", "Tất cả các đáp án trên"}, 3,
                "Địa lý Việt Nam", "Tài nguyên");

        insertQuestion("Biển Đông của Việt Nam có nguồn tài nguyên nào?",
                new String[]{"Dầu khí", "Thủy sản", "Muối", "Tất cả các đáp án trên"}, 3,
                "Địa lý Việt Nam", "Tài nguyên");

        insertQuestion("Rừng Việt Nam cung cấp tài nguyên gì?",
                new String[]{"Gỗ", "Dược liệu", "Động vật hoang dã", "Tất cả các đáp án trên"}, 3,
                "Địa lý Việt Nam", "Tài nguyên");

        // Chủ đề: Châu Á
        // Đông Á
        insertQuestion("Đâu là quốc gia đông dân nhất khu vực Đông Á?",
                new String[]{"Trung Quốc", "Hàn Quốc", "Nhật Bản", "Triều Tiên"}, 0,
                "Châu Á", "Đông Á");

        insertQuestion("Khu vực Đông Á có khí hậu chủ yếu là gì??",
                new String[]{"Ôn đới lục địa", "Nhiệt đới gió mùa", "Nhiệt đới xavan", "Cận nhiệt đới khô"}, 1,
                "Châu Á", "Đông Á");

        insertQuestion("Quốc gia nào ở Đông Á nổi tiếng với nhiều núi lửa và động đất?",
                new String[]{"Trung Quốc", "Nhật Bản", "Hàn Quốc", "Mông Cổ"}, 1,
                "Châu Á", "Đông Á");

        // Đông Nam Á
        insertQuestion("Quốc gia nào không thuộc Đông Nam Á?",
                new String[]{"Việt Nam", "Thái Lan", "Ấn Độ" , "Malaysia"}, 2,
                "Châu Á", "Đông Nam Á");

        insertQuestion("Sông Mekong chảy qua bao nhiêu quốc gia Đông Nam Á?",
                new String[]{"2", "3", "4", "5"}, 3,
                "Châu Á", "Đông Nam Á");

        // Nam Á
        insertQuestion("Thủ đô của Ấn Độ là gì?",
                new String[]{"New Delhi", "Mumbai", "Bangalore", "Kolkata"}, 0,
                "Châu Á", "Nam Á");

        insertQuestion("Thủ đô của Pakistan là thành phố nào?",
                new String[]{"Karachi", "Islamabad", "Lahore", "Rawalpindi"}, 1,
                "Châu Á", "Nam Á");

        // Chủ đề: Châu Âu
        // Tây Âu
        insertQuestion("Thủ đô của Pháp là gì?",
                new String[]{"Paris", "Lyon", "Nice", "Marseille"}, 0,
                "Châu Âu", "Tây Âu");

        // Đông Âu
        insertQuestion("Ba Lan thuộc khu vực nào?",
                new String[]{"Đông Âu", "Tây Âu", "Bắc Âu", "Trung Âu"}, 0,
                "Châu Âu", "Đông Âu");

        // Đông Âu
        insertQuestion("Quốc gia nào sau đây không thuộc Bắc Âu?",
                new String[]{"Đức", "Na Uy", "Thụy Điển", "Phần Lan"}, 0,
                "Châu Âu", "Bắc Âu");

        // Chủ đề: Châu Mỹ
        // Bắc Mỹ
        insertQuestion("Hoa Kỳ thuộc khu vực nào của châu Mỹ?",
                new String[]{"Bắc Mỹ", "Nam Mỹ", "Trung Mỹ", "Caribe"}, 0,
                "Châu Mỹ", "Bắc Mỹ");

        // Nam Mỹ
        insertQuestion("Brazil nằm ở khu vực nào?",
                new String[]{"Nam Mỹ", "Bắc Mỹ", "Trung Mỹ", "Caribe"}, 0,
                "Châu Mỹ", "Nam Mỹ");

        // Trung Mỹ
        insertQuestion("Quốc gia nào sau đây nằm ở Trung Mỹ?",
                new String[]{"Panama", "Argentina", "Canada", "Chile"}, 0,
                "Châu Mỹ", "Trung Mỹ");

        // Chủ đề: Châu Phi
        // Bắc Phi
        insertQuestion("Ai Cập nằm ở đâu trên bản đồ châu Phi?",
                new String[]{"Bắc Phi", "Nam Phi", "Tây Phi", "Trung Phi"}, 0,
                "Châu Phi", "Bắc Phi");

        // Nam Phi
        insertQuestion("Nam Phi nằm ở đâu?",
                new String[]{"Nam Phi", "Bắc Phi", "Đông Phi", "Tây Phi"}, 0,
                "Châu Phi", "Nam Phi");
    }
}