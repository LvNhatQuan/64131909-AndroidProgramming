package nhatquan.ntu.adminquiz;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import nhatquan.ntu.adminquiz.databinding.ActivityUploadCategoryBinding;

public class UploadCategoryActivity extends AppCompatActivity {
    ActivityUploadCategoryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_upload_category);

    }
}