package brscherer.github.com.mystuff;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import java.util.ArrayList;

import brscherer.github.com.mystuff.dao.DB;

public class BorrowActivity extends AppCompatActivity {
    private Spinner personList;
    private Spinner stuffList;
    private Button btnBorrow;
    private Button btnCancel;
    public DB dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow);

        stuffList = findViewById(R.id.stuffList);
        personList = findViewById(R.id.personList);
        btnBorrow = findViewById(R.id.btnBorrow);
        btnCancel = findViewById(R.id.btnCancel);
        dbHelper = new DB(this);

        getStuff();
        getPerson();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BorrowActivity.super.onBackPressed();
                finish();
            }
        });

        btnBorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stuff = stuffList.getSelectedItem().toString();
                String person = personList.getSelectedItem().toString();

                if (!TextUtils.isEmpty(stuff) && !TextUtils.isEmpty(person)) {
                    addBorrow(person, stuff);
                }else{
                    Toast.makeText(BorrowActivity.this, "Empty Field", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getStuff() {
        Cursor stuffs = dbHelper.getStuff();
        ArrayList<String> list = new ArrayList<>();
        while(stuffs.moveToNext()) {
            list.add(stuffs.getString(1));
        }
        SpinnerAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, list);
        stuffList.setAdapter(adapter);
    }

    private void getPerson() {
        Cursor person = dbHelper.getPerson();
        ArrayList<String> list = new ArrayList<>();
        while(person.moveToNext()) {
            list.add(person.getString(1));
        }
        SpinnerAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, list);
        personList.setAdapter(adapter);
    }

    public void addBorrow (String person, String stuff) {
        boolean insertData = dbHelper.borrowStuff(
                dbHelper.getPersonByName(person),
                dbHelper.getStuffByName(stuff)
        );

        if (insertData) {
            Toast.makeText(BorrowActivity.this, "Added Borrow", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(BorrowActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }
}
