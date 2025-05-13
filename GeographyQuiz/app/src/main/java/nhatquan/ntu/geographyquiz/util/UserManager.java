package nhatquan.ntu.geographyquiz.util;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private static final String PREFS_NAME = "user_prefs";
    private static final String KEY_USERS = "users";

    private SharedPreferences prefs;

    public UserManager(Context context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void addUser(User user) {
        List<User> users = getAllUsers();
        users.add(user);
        saveUsers(users);
    }

    public User getUserByUsername(String username) {
        for (User user : getAllUsers()) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public boolean userExists(String username) {
        return getUserByUsername(username) != null;
    }

    public List<User> getAllUsers() {
        String json = prefs.getString(KEY_USERS, "[]");
        List<User> users = new ArrayList<>();
        try {
            JSONArray arr = new JSONArray(json);
            for (int i = 0; i < arr.length(); i++) {
                users.add(User.fromJSON(arr.getString(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return users;
    }

    private void saveUsers(List<User> users) {
        JSONArray arr = new JSONArray();
        for (User u : users) {
            arr.put(u.toJSON());
        }
        prefs.edit().putString(KEY_USERS, arr.toString()).apply();
    }
}
