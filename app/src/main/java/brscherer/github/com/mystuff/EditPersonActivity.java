package brscherer.github.com.mystuff;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import brscherer.github.com.mystuff.dao.DB;
import brscherer.github.com.mystuff.model.Person;

public class EditPersonActivity extends AppCompatActivity {
    private Button btnSave;
    private Button btnCancel;
    private Button btnDelete;
    private EditText nameField;
    private EditText emailField;
    private DB dbHelper;

    private String selectedName;
    private String selectedEmail;
    private int selectedId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editperson);

        btnCancel = findViewById(R.id.btnCancel);
        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);
        nameField = findViewById(R.id.nameField);
        emailField = findViewById(R.id.emailField);

        dbHelper = new DB(this);

        Intent intent = getIntent();

        selectedId = intent.getIntExtra("id",-1);
        selectedName = intent.getStringExtra("name");
        selectedEmail = intent.getStringExtra("email");

        if (selectedName != "" && selectedName != null) {
            nameField.setText(selectedName);
        }

        if (selectedEmail != "" && selectedEmail != null) {
            emailField.setText(selectedEmail);
        }

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditPersonActivity.super.onBackPressed();
                finish();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameField.getText().toString();
                String email = emailField.getText().toString();

                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email)) {
                    dbHelper.updatePerson(name, email, selectedId, selectedName, selectedEmail);
                    nameField.setText("");
                    emailField.setText("");
                }else{
                    Toast.makeText(EditPersonActivity.this, "Empty Field", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.deletePerson(selectedId, selectedName);
                Toast.makeText(EditPersonActivity.this, "Person Deleted", Toast.LENGTH_SHORT).show();
                EditPersonActivity.super.onBackPressed();
                finish();
            }
        });
    }
}
