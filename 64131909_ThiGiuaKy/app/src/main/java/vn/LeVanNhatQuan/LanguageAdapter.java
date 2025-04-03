package vn.LeVanNhatQuan;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.LanguageViewHolder> {
    private Context context;
    private ArrayList<Language> languageList;

    public LanguageAdapter(Context context, ArrayList<Language> languageList) {
        this.context = context;
        this.languageList = languageList;
    }

    @NonNull
    @Override
    public LanguageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_language, parent, false);
        return new LanguageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LanguageViewHolder holder, int position) {
        Language language = languageList.get(position);

        // Hiển thị dữ liệu
        holder.tvName.setText(language.getName());
        holder.tvDescription.setText(language.getDescription());

        // Load ảnh từ resource
        int imageId = context.getResources().getIdentifier(
                language.getImageName(),
                "mipmap",
                context.getPackageName()
        );
        holder.ivImage.setImageResource(imageId);

        // Xử lý sự kiện click (2 điểm)
        holder.itemView.setOnClickListener(v -> {
            // Hiển thị Toast
            Toast.makeText(context, "Đã chọn: " + language.getName(), Toast.LENGTH_SHORT).show();

            // Chuyển sang ItemActivity (1 điểm)
            Intent intent = new Intent(context, ItemActivity.class);
            intent.putExtra("LANGUAGE_NAME", language.getName());
            intent.putExtra("LANGUAGE_DESC", language.getDescription());
            intent.putExtra("LANGUAGE_IMAGE", language.getImageName());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return languageList.size();
    }

    public static class LanguageViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;
        TextView tvName;
        TextView tvDescription;

        public LanguageViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.ivLanguage);
            tvName = itemView.findViewById(R.id.tvLanguageName);
            tvDescription = itemView.findViewById(R.id.tvLanguageDesc);
        }
    }
}