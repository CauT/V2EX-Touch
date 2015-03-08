package co.desgemini.v2extouch;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by desgemini on 3/7/15.
 */
public class WebConnection {
    private ForumPost[] HotTopics = new ForumPost[10];

    public ForumPost[] refreshHotTopics(Context context) {
        return transformToJson(getData(context));
    }

    public final String getData(Context context) {
        String result = null;
        InputStreamReader inputStream = null;
        try {
            URL url = new URL("http://www.v2ex.com/api/topics/hot.json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            inputStream = new InputStreamReader(conn.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStream);
            StringBuilder sb = null;
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line + '\n');
            }
            conn.disconnect();
            result = sb.toString();
        } catch (Exception e) {
//            MalformedURLException
            showToast(context, "An exception was thrown");
            showToast(context, e.toString());
        }
        finally {
            try{if(inputStream != null)inputStream.close();}catch(Exception squish){}
        }
        return result;
    }

    public final ForumPost[] transformToJson(String result) {
        JSONArray jArray;
        try {
            jArray = new JSONArray(result);
            for (int i=0; i<jArray.length(); i++) {
                HotTopics[i].loadData((JSONObject) jArray.get(i));
            }
        } catch (Exception e) {
            // Oops
        }
        return HotTopics;
    }

    public final void showToast(final Context context,
                                       final CharSequence text) {
        final TextView tv = new TextView(context);
        tv.setText(text);
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(Color.WHITE);
        tv.setBackgroundResource(android.R.drawable.toast_frame);
        final Toast t = new Toast(context);
        t.setView(tv);
        t.setDuration(Toast.LENGTH_SHORT);
        t.show();
    }

}