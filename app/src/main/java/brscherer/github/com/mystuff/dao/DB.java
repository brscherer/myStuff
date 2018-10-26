package brscherer.github.com.mystuff.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import brscherer.github.com.mystuff.model.User;

public class DB extends SQLiteOpenHelper {
    public static final String DB_NAME = "local.db";
    public static final int DB_VERSION = 1;

    public static final String USERS_TABLE = "users";

    public static final String ID = "id";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";

    public static final String CREATE_USERS_TABLE_ =
            "CREATE TABLE  " + USERS_TABLE + "(" +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    EMAIL + " TEXT NOT NULL," +
                    PASSWORD + " TEXT );";
    public DB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create table
        db.execSQL(CREATE_USERS_TABLE_);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE);
        // Create tables again
        onCreate(db);
    }

    public User queryUser(String email, String password) {

        SQLiteDatabase db = this.getReadableDatabase();
        User user = null;

        Cursor cursor = db.query(USERS_TABLE, new String[]{ID,
                        EMAIL, PASSWORD}, EMAIL + "=? and " + PASSWORD + "=?",
                new String[]{email, password}, null, null, null, "1");
        if (cursor != null)
            cursor.moveToFirst();
        if (cursor != null && cursor.getCount() > 0) {
            user = new User(cursor.getString(1), cursor.getString(2));
        }
        // return user
        return user;
    }

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(EMAIL, user.getEmail());
        values.put(PASSWORD, user.getPassword());

        // Inserting Row
        db.insert(USERS_TABLE, null, values);
        db.close(); // Closing database connection

    }
}
