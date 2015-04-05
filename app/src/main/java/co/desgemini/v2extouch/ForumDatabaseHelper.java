package co.desgemini.v2extouch;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Des Gemini on 3/21/15.
 */
public class ForumDatabaseHelper extends SQLiteOpenHelper{
    public ForumDatabaseHelper(Context context, String name, int version) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TOPIC_TABLE = "create table topic(id integer primary key" +
                ", node_id integer" +
                ", member_id integer" +
                ", title varchar(1000)" +
                ", url varchar(100)" +
                ", content varchar(10000)" +
                ", content_rendered varchar(10000)" +
                ", replies integer" +
                ", created integer" +
                ", last_modified integer" +
                ", last_touched integer" +
                ")";
        String CREATE_MEMBER_TABLE = "create table member(member_id integer primary key" +
                ", username varchar(100)" +
                ", tagline varchar(1000)" +
                ", avatar_mini varchar(1000)" +
                ", avatar_normal varchar(1000)" +
                ", avatar_large varchar(1000)" +
                ")";
        String CREATE_NODE_TABLE = "create table node(node_id integer primary key" +
                ", name varchar(100)" +
                ", title varchar(100)" +
                ", title_alternative varchar(100)" +
                ", url varchar(100)" +
                ", topics integer" +
                ", avatar_mini varchar(100)" +
                ", avatar_normal varchar(100)" +
                ", avatar_large varchar(100)" +
                ")";
        db.execSQL(CREATE_TOPIC_TABLE);
        db.execSQL(CREATE_MEMBER_TABLE);
        db.execSQL(CREATE_NODE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println(oldVersion + " -> " + newVersion);
    }
}
