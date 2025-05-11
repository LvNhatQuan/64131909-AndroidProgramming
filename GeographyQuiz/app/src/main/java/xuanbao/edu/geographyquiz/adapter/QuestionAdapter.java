package xuanbao.edu.geographyquiz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import xuanbao.edu.geographyquiz.R;
import xuanbao.edu.geographyquiz.model.Question;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {
    public interface OnDeleteClickListener {
        void onDeleteClick(Question question);
    }

    Context context;
    List<Question> questions;
    OnDeleteClickListener deleteClickListener;

    public QuestionAdapter(Context context, List<Question> questions, OnDeleteClickListener listener) {
        this.context = context;
        this.questions = questions;
        this.deleteClickListener = listener;
    }

    @NonNull
    @Override
    public QuestionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_question_delete, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionAdapter.ViewHolder holder, int position) {
        Question q = questions.get(position);
        holder.txtQuestion.setText(q.getQuestionText());
        holder.txtAnswer.setText("Đáp án đúng: " +  q.getOptions()[q.getCorrectOption()]);

        holder.btnDelete.setOnClickListener(v -> deleteClickListener.onDeleteClick(q));
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtQuestion, txtAnswer;
        Button btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtQuestion = itemView.findViewById(R.id.txtQuestion);
            txtAnswer = itemView.findViewById(R.id.txtAnswer);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
