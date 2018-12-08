package brscherer.github.com.mystuff;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import brscherer.github.com.mystuff.dao.DB;

public class BorrowListActivity extends AppCompatActivity {
    private ListView listView;
    private Button btnBack;
    public DB dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrowlist);

        listView = findViewById(R.id.listView);
        btnBack = findViewById(R.id.btnBack);
        dbHelper = new DB(this);


        getBorrow();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BorrowListActivity.super.onBackPressed();
                finish();
            }
        });
    }

    private void getBorrow() {
        Cursor borrow = dbHelper.getBorrow();
        ArrayList<String> list = new ArrayList<>();
        while(borrow.moveToNext()) {
            list.add("date: " + borrow.getString(1) + " | " + "stuff: " + borrow.getString(2) + " | " + "person: " + borrow.getString(3));
        }
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);

    }

}
