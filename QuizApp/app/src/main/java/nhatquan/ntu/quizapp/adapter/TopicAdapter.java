package nhatquan.ntu.quizapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import nhatquan.ntu.quizapp.R;
import nhatquan.ntu.quizapp.SubTopicActivity;
import nhatquan.ntu.quizapp.models.Topic;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.TopicViewHolder> {
    private Context context;
    private List<Topic> topicList;

    public TopicAdapter(Context context, List<Topic> topicList) {
        this.context = context;
        this.topicList = topicList;
    }

    @NonNull
    @Override
    public TopicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_topic, parent, false);
        return new TopicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopicViewHolder holder, int position) {
        Topic topic = topicList.get(position);
        holder.txtTopic.setText(topic.getTitle());
        holder.imgTopic.setImageResource(topic.getImageResId());

        // Lấy role từ SharedPreferences
        SharedPreferences prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        String role = prefs.getString("role", "guest");

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, SubTopicActivity.class);
            intent.putExtra("topic", topic.getTitle());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return topicList.size();
    }

    public static class TopicViewHolder extends RecyclerView.ViewHolder {
        ImageView imgTopic;
        TextView txtTopic;

        public TopicViewHolder(@NonNull View itemView) {
            super(itemView);
            imgTopic = itemView.findViewById(R.id.imgTopic);
            txtTopic = itemView.findViewById(R.id.txtTopic);
        }
    }
}
