package co.desgemini.v2extouch;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

public class MainBrowser extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, OnRefreshListener<ListView>{

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private ViewPager mViewPager;
//    private static final String[] STRINGS = { "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance",
//            "Ackawi", "Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre",
//            "Allgauer Emmentaler", "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
//            "Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre",
//            "Allgauer Emmentaler" };
    private ForumPost[] HotTopics = new ForumPost[10];
    private Context context;
    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_browser);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        mViewPager = (ViewPager) findViewById(R.id.vp_list);
        mViewPager.setAdapter(new ListViewPagerAdapter());
    }

    private class ListViewPagerAdapter extends PagerAdapter {

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            Context context = container.getContext();

            PullToRefreshListView plv = (PullToRefreshListView) LayoutInflater.from(context).inflate(
                    R.layout.layout_listview_in_viewpager, container, false);
            ListAdapter adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1,
                    Arrays.asList(
                            HotTopics[0] != null ? HotTopics[0].toString() : "Get Nothing"
                    ));
            plv.setAdapter(adapter);

            plv.setOnRefreshListener(MainBrowser.this);

            // Now just add ListView to ViewPager and return it
            container.addView(plv, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

            return plv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public int getCount() {
            return 3;
        }

    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.daily_topics);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main_browser, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main_browser, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainBrowser) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }
    @Override
    public void onRefresh(PullToRefreshBase<ListView> refreshView) {
//        ExecutorService exec = Executors.newCachedThreadPool();
//        try {
            //注意每次需new一个实例,新建的任务只能执行一次,否则会出现异常
            MyTask mTask = new MyTask();
            mTask.execute();
//            execute.setEnabled(false);
//            cancel.setEnabled(true);
//        } catch (Exception e) {
//            showToast(getApplicationContext(), e.toString());
//        }
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

    private class MyTask extends AsyncTask<String, Integer, String> {
//        TextView textView = new TextView("");
        String TAG = "dong";
        //onPreExecute方法用于在执行后台任务前做一些UI操作
        @Override
        protected void onPreExecute() {
            Log.i(TAG, "onPreExecute() called");
//            textView.setText("loading...");
        }

        //doInBackground方法内部执行后台任务,不可在此方法内修改UI
        @Override
        protected String doInBackground(String... params) {
            Log.i(TAG, "doInBackground(Params... params) called");
//            try {
//                HttpClient client = new DefaultHttpClient();
//                HttpGet get = new HttpGet(params[0]);
//                HttpResponse response = client.execute(get);
//                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//                    HttpEntity entity = response.getEntity();
//                    InputStream is = entity.getContent();
//                    long total = entity.getContentLength();
//                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                    byte[] buf = new byte[1024];
//                    int count = 0;
//                    int length = -1;
//                    while ((length = is.read(buf)) != -1) {
//                        baos.write(buf, 0, length);
//                        count += length;
//                        //调用publishProgress公布进度,最后onProgressUpdate方法将被执行
//                        publishProgress((int) ((count / (float) total) * 100));
//                        //为了演示进度,休眠500毫秒
//                        Thread.sleep(500);
//                    }
//                    return new String(baos.toByteArray(), "gb2312");
//                }
//            } catch (Exception e) {
//                Log.e(TAG, e.getMessage());
//            }
//            return null;
            String result = null;
            InputStreamReader inputStream = null;
            try {
                URL url = new URL("http://www.v2ex.com/api/topics/hot.json");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(6*1000);
                if (conn.getResponseCode() != 200)
                    throw new RuntimeException("请求url失败");
                inputStream = new InputStreamReader(conn.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStream);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line + '\n');
                }
                conn.disconnect();
                result = sb.toString();
            } catch (Exception e) {
                Exception ee = e;
//            MalformedURLException
//            showToast(context, "An exception was thrown");
//                showToast(context, e.toString());
            }
            finally {
                try{if(inputStream != null)inputStream.close();}catch(Exception squish){}
            }
            HotTopics = transformToJson(result);
            return null;
        }

        //onProgressUpdate方法用于更新进度信息
        @Override
        protected void onProgressUpdate(Integer... progresses) {
            Log.i(TAG, "onProgressUpdate(Progress... progresses) called");
//            progressBar.setProgress(progresses[0]);
//            textView.setText("loading..." + progresses[0] + "%");
        }

        //onPostExecute方法用于在执行完后台任务后更新UI,显示结果
        @Override
        protected void onPostExecute(String result) {
            Log.i(TAG, "onPostExecute(Result result) called");
//            textView.setText(result);
//            execute.setEnabled(true);
//            cancel.setEnabled(false);
            mViewPager.invalidate();
            if (HotTopics[0] == null)
                showToast(context, "fuck");
        }

        //onCancelled方法用于在取消执行中的任务时更改UI
        @Override
        protected void onCancelled() {
            Log.i(TAG, "onCancelled() called");
//            textView.setText("cancelled");
//            progressBar.setProgress(0);
//
//            execute.setEnabled(true);
//            cancel.setEnabled(false);
        }
        public final ForumPost[] transformToJson(String result) {
            JSONArray jArray;
            try {
                jArray = new JSONArray(result);
                for (int i=0; i<jArray.length(); i++) {
                    JSONObject tmp = (JSONObject) jArray.get(i);
                    HotTopics[i] = new ForumPost();
                    HotTopics[i].loadData(tmp);
                }
            } catch (Exception e) {
                String s = e.toString();
                // Oops
            }
            return HotTopics;
        }
    }
}
