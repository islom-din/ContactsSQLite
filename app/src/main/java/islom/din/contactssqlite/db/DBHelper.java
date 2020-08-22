package islom.din.contactssqlite.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "contactsDB";
    public static final String TABLE_NAME = "contacts";

    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "first_name";
    public static final String KEY_LASTNAME = "last_name";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_EMAIL = "email";


    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("  CREATE TABLE " + TABLE_NAME + "(" +
                      KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                      KEY_NAME + " TEXT," +
                      KEY_LASTNAME + " TEXT," +
                      KEY_PHONE + " TEXT," +
                      KEY_EMAIL + " TEXT" + ")"   );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
