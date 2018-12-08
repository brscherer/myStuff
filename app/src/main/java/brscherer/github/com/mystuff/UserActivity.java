package brscherer.github.com.mystuff;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserActivity extends AppCompatActivity {
    private Button btnAddStuff;
    private Button btnGetStuff;
    private Button btnAddPerson;
    private Button btnBlacklist;
    private Button btnBorrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        btnAddStuff = findViewById(R.id.btnAddStuff);
        btnGetStuff = findViewById(R.id.btnGetStuff);
        btnAddPerson = findViewById(R.id.btnAddPerson);
        btnBlacklist = findViewById(R.id.btnBlacklist);
        btnBorrow = findViewById(R.id.btnBorrow);

        btnAddStuff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, NewStuffActivity.class);
                startActivity(intent);
            }
        });

        btnGetStuff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, GetStuffActivity.class);
                startActivity(intent);
            }
        });

        btnAddPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, NewPersonActivity.class);
                startActivity(intent);
            }
        });

        btnBlacklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, BorrowListActivity.class);
                startActivity(intent);
            }
        });

        btnBorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, BorrowActivity.class);
                startActivity(intent);
            }
        });
    }
}