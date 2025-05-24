package nhatquan.ntu.geographyquiz.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDatabaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "users.db";
    public static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "users";

    public UserDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createSQL = "CREATE TABLE " + TABLE_NAME + " (" +
                "username TEXT PRIMARY KEY," +
                "password TEXT," +
                "email TEXT," +
                "role TEXT)";
        db.execSQL(createSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Nếu sau này nâng version
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}