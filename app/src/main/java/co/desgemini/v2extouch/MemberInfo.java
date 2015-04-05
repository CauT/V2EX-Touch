package co.desgemini.v2extouch;

import android.content.ContentValues;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.json.JSONObject;

/**
 * Created by Des Gemini on 3/22/15.
 */
public class MemberInfo extends ForumInfo {
    public MemberInfo() {
        this.tableName = "member";
    }

    public void parseJsonAndInsert(SQLiteDatabase db, JSONObject outerJsonObject) {
        int id;
        String username;
        String tagline;
        String avatar_mini;
        String avatar_normal;
        String avatar_large;

        ContentValues contentValues = new ContentValues();
        try {
            JSONObject jsonObject = outerJsonObject.getJSONObject("member");
            id = jsonObject.getInt("id");
            contentValues.put("member_id", id);
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
        } catch (SQLiteConstraintException e) {
            Log.v("v2ex_touch", "node already exist!");
        } catch (Exception e) {
            Log.e("dong", this.toString() + " : " + e.toString());
        }
    }
}
