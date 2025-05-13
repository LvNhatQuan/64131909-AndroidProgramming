package nhatquan.ntu.geographyquiz;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class ScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_screen); // Ensure this points to your correct layout

        // Set the status bar color (optional)
        setStatusBarColor();

        // Delay to show splash screen for 2 seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start LoginActivity after 2 seconds
                Intent intent = new Intent(ScreenActivity.this, ScreenActivity2.class);
                startActivity(intent);
                finish();  // Close ScreenActivity to prevent going back to it
            }
        }, 2000);  // 2 seconds delay
    }

    private void setStatusBarColor() {
        Window window = getWindow();
        int statusBarColor = ContextCompat.getColor(this, R.color.lightblue);
        window.setStatusBarColor(statusBarColor);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            View decorView = window.getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }
}
