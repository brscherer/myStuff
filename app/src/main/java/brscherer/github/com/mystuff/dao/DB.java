package brscherer.github.com.mystuff.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

import brscherer.github.com.mystuff.model.Person;
import brscherer.github.com.mystuff.model.Stuff;
import brscherer.github.com.mystuff.model.User;

public class DB extends SQLiteOpenHelper {
    public static final String DB_NAME = "local.db";
    public static final int DB_VERSION = 1;

    public static final String CREATE_USERS_TABLE_ =
            "CREATE TABLE users (id  INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "email TEXT NOT NULL, " +
                    "password TEXT);";

    public static final String CREATE_STUFF_TABLE_ =
            "CREATE TABLE stuff (id  INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT NOT NULL);";

    public static final String CREATE_PERSON_TABLE_ =
            "CREATE TABLE person (id  INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT NOT NULL, " +
                    "email TEXT NOT NULL);";

    public static final String CREATE_BORROW_TABLE_ =
            "CREATE TABLE borrow (id  INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "borrowDate DATE NOT NULL, " +
                    "stuffName TEXT NOT NULL, " +
                    "personName TEXT NOT NULL);";

    public DB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USERS_TABLE_);
        db.execSQL(CREATE_STUFF_TABLE_);
        db.execSQL(CREATE_PERSON_TABLE_);
        db.execSQL(CREATE_BORROW_TABLE_);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + "users");
        db.execSQL("DROP TABLE IF EXISTS " + "stuff");
        db.execSQL("DROP TABLE IF EXISTS " + "person");
        db.execSQL("DROP TABLE IF EXISTS " + "borrow");
        onCreate(db);
    }

    public User queryUser(String email, String password) {

        SQLiteDatabase db = this.getReadableDatabase();
        User user = null;

        Cursor cursor = db.query("users", new String[]{"id",
                        "email", "password"}, "email = ? and password = ?",
                new String[]{email, password}, null, null, null, "1");
        if (cursor != null)
            cursor.moveToFirst();
        if (cursor != null && cursor.getCount() > 0) {
            user = new User(cursor.getString(1), cursor.getString(2));
        }
        return user;
    }

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("email", user.getEmail());
        values.put("password", user.getPassword());

        db.insert("users", null, values);

    }

    public boolean addStuff(Stuff stuff) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", stuff.getName());

        long result = db.insert("stuff", null, values);

        if (result == -1){
            return false;
        } else {
            return true;
        }
    }

    public void deleteStuff (int id, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM stuff WHERE id = '" + id + "' AND name = '" + name + "'";
        db.execSQL(query);
    }

    public void updateStuff (String newName, int id, String oldName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE stuff SET name = '" + newName + "' WHERE id = '" + id + "' AND name = '" + oldName + "'";
        db.execSQL(query);
    }

    public Cursor getStuff () {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM stuff";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getStuffId (String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT id FROM stuff WHERE name = '" + name + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Stuff getStuffByName (String name) {
        Stuff stuff = new Stuff();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM stuff WHERE name = ?";
        Cursor data = db.rawQuery(query, new String[] { name });

        if(data.moveToFirst()) {
            stuff.setId(data.getLong(0));
            stuff.setName(data.getString(1));
        }
        data.close();

        return stuff;
    }


    public boolean addPerson(Person person) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", person.getName());
        values.put("email", person.getEmail());

        long result = db.insert("person", null, values);

        if (result == -1){
            return false;
        } else {
            return true;
        }
    }
    public void deletePerson (int id, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM person WHERE id = '" + id + "' AND name = '" + name + "'";
        db.execSQL(query);
    }

    public void updatePerson (String newName, String newEmail, int id, String oldName, String oldEmail) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE person SET name = '" + newName + "', email = '" + newEmail + "' WHERE id = '" + id + "' AND name = '" + oldName + "'";
        db.execSQL(query);
    }


    public Cursor getPerson () {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM person";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getPersonId (String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT id FROM person WHERE name = '" + name + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Person getPersonByName (String name) {
        Person person = new Person();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM person WHERE name = ?";
        Cursor data = db.rawQuery(query, new String[] { name });

        if(data.moveToFirst()) {
            person.setId(data.getLong(0));
            person.setName(data.getString(1));
            person.setEmail(data.getString(2));
        }
        data.close();

        return person;
    }

    public boolean borrowStuff(Person person, Stuff stuff) {
        SQLiteDatabase db = this.getWritableDatabase();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        ContentValues values = new ContentValues();
        values.put("borrowDate", dateFormat.format(date));
        values.put("stuffName", stuff.getName());
        values.put("personName", person.getName());

        long result = db.insert("borrow", null, values);

        if (result == -1){
            return false;
        } else {
            return true;
        }
    }

    public Cursor getBorrow () {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM borrow";
        Cursor data = db.rawQuery(query, null);
        return data;
    }
}
