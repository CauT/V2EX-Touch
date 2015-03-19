package co.desgemini.v2extouch;

import org.json.JSONObject;

/**
 * Created by Des Gemini on 3/7/15.
 */
public class ForumPost {
    private int id;
    private int date;
    private JSONObject member;
    private String username;
    private String title;
    private String url;
    private String content;
    private String content_rendered;
    private int replyNum;

    public void loadData(JSONObject jo) {
        try {
            id = jo.getInt("id");
            date = jo.getInt("last_modified");
            member = jo.getJSONObject("member");
            username = member.getString("username");
            title = jo.getString("title");
            url = jo.getString("url");
            content = jo.getString("content");
            content_rendered = jo.getString("content_rendered");
            replyNum = jo.getInt("replies");
        } catch (Exception e) {
            Exception tmp = e;
        }
    }
    public String getTitle() {
        return this.title;
    }
    public String getAuthor() {
        return this.username;
    }
    public int getDate() {
        return this.date;
    }
    public int getReplyNum() {
        return this.replyNum;
    }
}