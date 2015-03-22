package co.desgemini.v2extouch;

import android.util.Log;

/**
 * Created by Des Gemini on 3/22/15.
 */
public class ForumInfoFactory {
    public <T extends ForumInfo> T createForumInfo(Class<T> c) {
        ForumInfo forumInfo = null;
        try {
            forumInfo = (ForumInfo) Class.forName(c.getName()).newInstance();
        } catch (Exception e) {
            Log.e("dong", e.toString());
        }
        return (T) forumInfo;
    }
}
