package co.desgemini.v2extouch;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.json.JSONObject;

/**
 * Created by Des Gemini on 3/22/15.
 */
public abstract class ForumInfo {
    public String tableName;
    public abstract void parseJsonAndInsert(SQLiteDatabase db, JSONObject jsonObject);

    public void insert2db(SQLiteDatabase db, ContentValues contentValues) {
        try {
            db.insert(tableName, null, contentValues);
        } catch (Exception e) {
            Log.e("dong", e.toString());
        }
    }
}
