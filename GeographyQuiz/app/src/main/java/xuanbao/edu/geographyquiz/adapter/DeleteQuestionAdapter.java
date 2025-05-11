package xuanbao.edu.geographyquiz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import xuanbao.edu.geographyquiz.R;
import xuanbao.edu.geographyquiz.model.Question;

public class DeleteQuestionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_SUBTOPIC = 0;
    private static final int VIEW_TYPE_QUESTION = 1;

    private Context context;
    private List<Object> items;
    private OnDeleteClickListener deleteClickListener;

    public interface OnDeleteClickListener {
        void onDeleteClick(Question question);
    }

    public DeleteQuestionAdapter(Context context, List<Object> items, OnDeleteClickListener listener) {
        this.context = context;
        this.items = items;
        this.deleteClickListener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof String) {
            return VIEW_TYPE_SUBTOPIC;
        } else {
            return VIEW_TYPE_QUESTION;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_SUBTOPIC) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_topic, parent, false);
            return new SubtopicViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_question_delete, parent, false);
            return new QuestionViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SubtopicViewHolder) {
            String subtopic = (String) items.get(position);
            ((SubtopicViewHolder) holder).txtTopic.setText(subtopic);
            ((SubtopicViewHolder) holder).imgTopic.setVisibility(View.GONE); // ẩn ảnh cho gọn
        } else if (holder instanceof QuestionViewHolder) {
            Question question = (Question) items.get(position);
            ((QuestionViewHolder) holder).txtQuestion.setText(question.getQuestionText());
            String correctAnswer = question.getOptions()[question.getCorrectOption()];
            ((QuestionViewHolder) holder).txtAnswer.setText("Đáp án đúng: " + correctAnswer);
            ((QuestionViewHolder) holder).btnDelete.setOnClickListener(v -> {
                deleteClickListener.onDeleteClick(question);
            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class SubtopicViewHolder extends RecyclerView.ViewHolder {
        TextView txtTopic;
        ImageView imgTopic;

        SubtopicViewHolder(View itemView) {
            super(itemView);
            txtTopic = itemView.findViewById(R.id.txtTopic);
            imgTopic = itemView.findViewById(R.id.imgTopic);
        }
    }

    static class QuestionViewHolder extends RecyclerView.ViewHolder {
        TextView txtQuestion, txtAnswer;
        Button btnDelete;

        QuestionViewHolder(View itemView) {
            super(itemView);
            txtQuestion = itemView.findViewById(R.id.txtQuestion);
            txtAnswer = itemView.findViewById(R.id.txtAnswer);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
