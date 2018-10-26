package brscherer.github.com.mystuff;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class UserActivity extends AppCompatActivity {
    private TextView tvUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        tvUser = findViewById(R.id.tvUser);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            tvUser.setText("Welcome to myStuff!");
        }
    }
}