package brscherer.github.com.mystuff;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import brscherer.github.com.mystuff.dao.DB;

public class GetStuffActivity extends AppCompatActivity {
    private ListView stuffList;
    private Button btnBack;
    public DB dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getstuff);

        stuffList = (ListView) findViewById(R.id.stuffList);
        btnBack = findViewById(R.id.btnBack);
        dbHelper = new DB(this);

        getStuff();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetStuffActivity.super.onBackPressed();
                finish();
            }
        });

        stuffList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = adapterView.getItemAtPosition(i).toString();
                Cursor data = dbHelper.getStuffId(name);
                int stuffId = -1;
                while (data.moveToNext()){
                    stuffId = data.getInt(0);
                }
                if (stuffId > -1) {
                    Intent editStuff = new Intent(GetStuffActivity.this, EditStuffActivity.class);
                    editStuff.putExtra("id", stuffId);
                    editStuff.putExtra("name", name);
                    startActivity(editStuff);
                } else {
                    Toast.makeText(GetStuffActivity.this, "No ID for this name", Toast.LENGTH_SHORT).show();
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
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        stuffList.setAdapter(adapter);

    }


}
