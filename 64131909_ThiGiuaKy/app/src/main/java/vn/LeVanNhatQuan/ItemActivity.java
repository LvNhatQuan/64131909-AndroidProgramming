package vn.LeVanNhatQuan;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ItemActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        if (getIntent().hasExtra("selected_item")) {
            String selectedItem = getIntent().getStringExtra("selected_item");
            TextView tvItem = findViewById(R.id.tvItem);
            tvItem.setText("Ngôn ngữ đã chọn: " + selectedItem);

            ImageView ivIcon = findViewById(R.id.ivIcon);
            ivIcon.setVisibility(View.GONE);
        }
        else if (getIntent().hasExtra("LANGUAGE_NAME")) {
            String name = getIntent().getStringExtra("LANGUAGE_NAME");
            String imageName = getIntent().getStringExtra("LANGUAGE_IMAGE");

            TextView tvItem = findViewById(R.id.tvItem);
            ImageView ivIcon = findViewById(R.id.ivIcon);

            tvItem.setText(name);

            int imageId = getResources().getIdentifier(
                    imageName,
                    "mipmap",
                    getPackageName()
            );
            ivIcon.setImageResource(imageId);
            ivIcon.setVisibility(View.VISIBLE);
        }
    }
}