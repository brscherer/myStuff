package brscherer.github.com.mystuff;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import brscherer.github.com.mystuff.dao.DB;
import brscherer.github.com.mystuff.model.Stuff;

public class NewStuffActivity extends AppCompatActivity {
    private Button btnSave;
    private Button btnCancel;
    private EditText textField;
    private DB dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newstuff);

        btnCancel = findViewById(R.id.btnCancel);
        btnSave = findViewById(R.id.btnSave);
        textField = findViewById(R.id.textField);

        dbHelper = new DB(this);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewStuffActivity.super.onBackPressed();
                finish();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stuff = textField.getText().toString();
                if (!TextUtils.isEmpty(stuff)) {
                    addStuff(stuff);
                    textField.setText("");
                }else{
                    Toast.makeText(NewStuffActivity.this, "Empty Field", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void addStuff (String stuff) {
        boolean insertData = dbHelper.addStuff(new Stuff(stuff));

        if (insertData) {
            Toast.makeText(NewStuffActivity.this, "Added Stuff", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(NewStuffActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }
}
