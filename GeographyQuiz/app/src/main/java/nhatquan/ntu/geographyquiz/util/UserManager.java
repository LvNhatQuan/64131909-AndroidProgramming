package nhatquan.ntu.geographyquiz.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import nhatquan.ntu.geographyquiz.database.UserDatabaseHelper;

public class UserManager {
    private SQLiteDatabase db;
    public UserManager(Context context) {
        UserDatabaseHelper helper = new UserDatabaseHelper(context);
        db = helper.getWritableDatabase();
    }

    public boolean addUser(User user) {
        if (userExists(user.getUsername())) return false;

        ContentValues values = new ContentValues();
        values.put("username", user.getUsername());
        values.put("password", user.getPassword());
        values.put("email", user.getEmail());
        values.put("role", user.getRole());

        long result = db.insert(UserDatabaseHelper.TABLE_NAME, null, values);
        return result != -1;
    }

    public User getUserByUsername(String username) {
        Cursor cursor = db.query(UserDatabaseHelper.TABLE_NAME,
                null, "username=?", new String[]{username},
                null, null, null);
        if (cursor.moveToFirst()) {
            User user = new User(
                    cursor.getString(cursor.getColumnIndexOrThrow("username")),
                    cursor.getString(cursor.getColumnIndexOrThrow("password")),
                    cursor.getString(cursor.getColumnIndexOrThrow("email")),
                    cursor.getString(cursor.getColumnIndexOrThrow("role"))
            );
            cursor.close();
            return user;
        }
        cursor.close();
        return null;
    }

    public boolean userExists(String username) {
        return getUserByUsername(username) != null;
    }
}
