package nhatquan.ntu.geographyquiz.model;

public class Question {
    private int id;
    private String topic;
    private String questionText;
    private String[] options;
    private int correctOption;
    private String subTopic;
    public Question() {
    }

    public Question(int id, String topic, String questionText, String[] options, int correctOption, String subTopic) {
        this.id = id;
        this.topic = topic;
        this.questionText = questionText;
        this.options = options;
        this.correctOption = correctOption;
        this.subTopic = subTopic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public int getCorrectOption() {
        return correctOption;
    }

    public void setCorrectOption(int correctOption) {
        this.correctOption = correctOption;
    }

    public String getSubTopic() {
        return subTopic;
    }

    public void setSubTopic(String subTopic) {
        this.subTopic = subTopic;
    }
}