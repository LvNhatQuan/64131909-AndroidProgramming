package xuanbao.edu.geographyquiz.util;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
    private String username;
    private String password;
    private String email;
    private String role;

    public User(String username, String password, String email, String role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    // Getters
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
    public String getRole() { return role; }

    // Dùng cho lưu JSON
    public String toJSON() {
        return "{\"username\":\"" + username + "\",\"password\":\"" + password + "\",\"email\":\"" + email + "\",\"role\":\"" + role + "\"}";
    }

    public static User fromJSON(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            return new User(
                    obj.getString("username"),
                    obj.getString("password"),
                    obj.getString("email"),
                    obj.getString("role")
            );
        } catch (JSONException e) {
            return null;
        }
    }
}
