package co.desgemini.v2extouch;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.json.JSONObject;

/**
 * Created by Des Gemini on 3/22/15.
 */
public class NodeInfo extends ForumInfo {
    private int id;
    private String name;
    private String title;
    private String title_alternative;
    private String url;
    private int topics;
    private String avatar_mini;
    private String avatar_normal;
    private String avatar_large;

    public void parseJsonAndInsert(SQLiteDatabase db, JSONObject jsonObject) {
        ContentValues contentValues = new ContentValues();
        try {
            id = jsonObject.getInt("id");
            contentValues.put("id", id);
            topics = jsonObject.getInt("topics");
            contentValues.put("topics", topics);
            name = jsonObject.getString("name");
            contentValues.put("name", name);
            url = jsonObject.getString("url");
            contentValues.put("url", url);
            title = jsonObject.getString("title");
            contentValues.put("title", title);
            title_alternative = jsonObject.getString("title_alternative");
            contentValues.put("title_alternative", title_alternative);
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
