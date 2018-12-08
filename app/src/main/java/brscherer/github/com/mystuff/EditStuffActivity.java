package brscherer.github.com.mystuff;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import brscherer.github.com.mystuff.dao.DB;
import brscherer.github.com.mystuff.model.Stuff;

public class EditStuffActivity extends AppCompatActivity {
    private Button btnSave;
    private Button btnCancel;
    private Button btnDelete;
    private EditText textField;
    private DB dbHelper;

    private String selectedName;
    private int selectedId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editstuff);

        btnCancel = findViewById(R.id.btnCancel);
        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);
        textField = findViewById(R.id.textField);

        dbHelper = new DB(this);

        Intent intent = getIntent();

        selectedId = intent.getIntExtra("id",-1);
        selectedName = intent.getStringExtra("name");

        if (selectedName != "" && selectedName != null) {
            textField.setText(selectedName);
        }

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditStuffActivity.super.onBackPressed();
                finish();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stuff = textField.getText().toString();
                if (!TextUtils.isEmpty(stuff)) {
                   dbHelper.updateStuff(stuff, selectedId, selectedName);
                   Toast.makeText(EditStuffActivity.this, "Stuff updated", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(EditStuffActivity.this, "Empty Field", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.deleteStuff(selectedId, selectedName);
                Toast.makeText(EditStuffActivity.this, "Stuff Deleted", Toast.LENGTH_SHORT).show();
                EditStuffActivity.super.onBackPressed();
                finish();
            }
        });
    }
}
