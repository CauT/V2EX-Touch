package co.desgemini.v2extouch;

import android.content.ContentValues;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.json.JSONObject;

/**
 * Created by Des Gemini on 3/22/15.
 */
public class NodeInfo extends ForumInfo {
    public NodeInfo() {
        this.tableName = "node";
    }

    public void parseJsonAndInsert(SQLiteDatabase db, JSONObject outerJsonObject) {
        int id;
        int topics;
        String name;
        String title;
        String title_alternative;
        String url;
        String avatar_mini;
        String avatar_normal;
        String avatar_large;
        ContentValues contentValues = new ContentValues();
        try {
            JSONObject jsonObject = outerJsonObject.getJSONObject("node");
            id = jsonObject.getInt("id");
            contentValues.put("node_id", id);
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
        } catch (SQLiteConstraintException e) {
            Log.v("v2ex_touch", "node already exist!");
        } catch (Exception e) {
            Log.e("dong", this.toString() + " : " + e.toString());
        }
    }
}
