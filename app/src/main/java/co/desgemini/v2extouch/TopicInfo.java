package co.desgemini.v2extouch;

import android.content.ContentValues;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.json.JSONObject;

/**
 * Created by Des Gemini on 3/22/15.
 */
public class TopicInfo extends ForumInfo {
    public TopicInfo() {
        this.tableName = "topic";
    }
    public void parseJsonAndInsert(SQLiteDatabase db, JSONObject jsonObject) {
        int id;
        int node_id;
        int member_id;
        String title;
        String url;
        String content;
        String content_rendered;
        String replies;
        String created;
        String last_modified;
        String last_touched;
        ContentValues contentValues = new ContentValues();
        try {
            id = jsonObject.getInt("id");
            contentValues.put("id", id);
            node_id = jsonObject.getJSONObject("node").getInt("id");
            contentValues.put("node_id", node_id);
            member_id = jsonObject.getJSONObject("member").getInt("id");
            contentValues.put("member_id", member_id);
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
        } catch (SQLiteConstraintException e) {
            Log.v("v2ex_touch", "node already exist!");
        } catch (Exception e) {
            Log.e("dong", this.toString() + " : " + e.toString());
        }
    }
}
