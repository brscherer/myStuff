package brscherer.github.com.mystuff;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import brscherer.github.com.mystuff.dao.DB;
import brscherer.github.com.mystuff.model.Person;

public class NewPersonActivity extends AppCompatActivity {
    private Button btnSave;
    private Button btnCancel;
    private Button btnViewPersons;
    private EditText nameField;
    private EditText emailField;
    private ListView personsList;
    private DB dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newperson);

        btnCancel = findViewById(R.id.btnCancel);
        btnSave = findViewById(R.id.btnSave);
        btnViewPersons = findViewById(R.id.btnViewPersons);
        nameField = findViewById(R.id.nameField);
        emailField = findViewById(R.id.emailField);
        personsList = findViewById(R.id.personsList);

        dbHelper = new DB(this);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewPersonActivity.super.onBackPressed();
                finish();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameField.getText().toString();
                String email = emailField.getText().toString();

                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email)) {
                    addPerson(name, email);
                    nameField.setText("");
                    emailField.setText("");
                }else{
                    Toast.makeText(NewPersonActivity.this, "Empty Field", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnViewPersons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPerson();
            }
        });

        personsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = adapterView.getItemAtPosition(i).toString().split(" \\| ")[0];
                String email = adapterView.getItemAtPosition(i).toString().split(" \\| ")[1];

                Cursor data = dbHelper.getPersonId(name);
                int personId = -1;
                while (data.moveToNext()){
                    personId = data.getInt(0);
                }
                if (personId > -1) {
                    Intent editStuff = new Intent(NewPersonActivity.this, EditPersonActivity.class);
                    editStuff.putExtra("id", personId);
                    editStuff.putExtra("name", name);
                    editStuff.putExtra("email", email);
                    startActivity(editStuff);
                } else {
                    Toast.makeText(NewPersonActivity.this, "No ID for this person", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void addPerson (String person, String email) {
        boolean insertData = dbHelper.addPerson(new Person(person, email));

        if (insertData) {
            Toast.makeText(NewPersonActivity.this, "Added Person", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(NewPersonActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    private void getPerson() {
        Cursor persons = dbHelper.getPerson();
        ArrayList<String> list = new ArrayList<>();
        while(persons.moveToNext()) {
            list.add(persons.getString(1) + " | " + persons.getString(2));
        }
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        personsList.setAdapter(adapter);

    }
}
