package co.desgemini.v2extouch;

import org.json.JSONObject;

/**
 * Created by apple on 3/7/15.
 */
public class ForumPost {
    private int id;
    private String title;
    private String url;
    private String content;
    private String content_rendered;
    private int replies;

    public void loadData(JSONObject jo) {
        try {
            id = jo.getInt("id");
            title = jo.getString("title");
            url = jo.getString("url");
            content = jo.getString("content");
            content_rendered = jo.getString("content_rendered");
            replies = jo.getInt("replies");
        } catch (Exception e) {
        }
    }
}