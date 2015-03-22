package co.desgemini.v2extouch;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.json.JSONObject;

/**
 * Created by Des Gemini on 3/22/15.
 */
public class MemberInfo extends ForumInfo {
    private int id;
    private String username;
    private String tagline;
    private String avatar_mini;
    private String avatar_normal;
    private String avatar_large;

    public void parseJsonAndInsert(SQLiteDatabase db, JSONObject jsonObject) {
        ContentValues contentValues = new ContentValues();
        try {
            id = jsonObject.getInt("id");
            contentValues.put("id", id);
            username = jsonObject.getString("username");
            contentValues.put("username", username);
            tagline = jsonObject.getString("tagline");
            contentValues.put("tagline", tagline);
            avatar_mini = jsonObject.getString("avatar_mini");
            contentValues.put("avatar_mini", avatar_mini);
            avatar_normal = jsonObject.getString("avatar_normal");
            contentValues.put("avatar_normal", avatar_normal);
            avatar_large= jsonObject.getString("avatar_large");
            contentValues.put("avatar_large", avatar_large);
            insert2db(db, contentValues);
        } catch (Exception e) {
            Log.e("dong", e.toString());
        }
    }
}
