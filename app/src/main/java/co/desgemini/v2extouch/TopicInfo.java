package co.desgemini.v2extouch;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.json.JSONObject;

/**
 * Created by Des Gemini on 3/22/15.
 */
public class TopicInfo extends ForumInfo {
    private int id;
    private String title;
    private String url;
    private String content;
    private String content_rendered;
    private String replies;
    private String created;
    private String last_modified;
    private String last_touched;

    public void parseJsonAndInsert(SQLiteDatabase db, JSONObject jsonObject) {
        ContentValues contentValues = new ContentValues();
        try {
            id = jsonObject.getInt("id");
            contentValues.put("id", id);
            title = jsonObject.getString("title");
            contentValues.put("title", title);
            url = jsonObject.getString("url");
            contentValues.put("url", url);
            content = jsonObject.getString("content");
            contentValues.put("content", content);
            content_rendered = jsonObject.getString("content_rendered");
            contentValues.put("content_rendered", content_rendered);
            replies = jsonObject.getString("replies");
            contentValues.put("replies", replies);
            created = jsonObject.getString("created");
            contentValues.put("created", created);
            last_modified = jsonObject.getString("last_modified");
            contentValues.put("last_modified", last_modified);
            last_touched = jsonObject.getString("last_touched");
            contentValues.put("last_touched", last_touched);
            insert2db(db, contentValues);
        } catch (Exception e) {
            Log.e("dong", e.toString());
        }
    }
}
