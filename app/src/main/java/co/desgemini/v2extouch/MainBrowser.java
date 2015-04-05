package co.desgemini.v2extouch;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainBrowser extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    // migrate the sample of list view
    private PullToRefreshListView mPullRefreshListView;
    private TopicAdapter mTopicAdapter;
    private ConnectionDetector mConnectionDetector;
    private ForumDatabaseHelper forumDatabaseHelper;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        forumDatabaseHelper = new ForumDatabaseHelper(getApplicationContext(), "v2ex_touch.db3", 1);
        ArrayList<ForumPost> HotTopicsArray = new ArrayList<>();
        setContentView(R.layout.activity_main_browser);

        mConnectionDetector = new ConnectionDetector(getApplicationContext());
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        // Main View
        mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);

        // Set a listener to be invoked when the list should be refreshed.
        mPullRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

                // Update the LastUpdatedLabel
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                // Do work to refresh the list here.
                if (mConnectionDetector.isConnectingToInternet()) {
                    RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
                    JsonArrayRequest jar = new JsonArrayRequest("https://www.v2ex.com/api/topics/hot.json",
                            new Response.Listener<JSONArray>() {
                                @Override
                                public void onResponse(JSONArray response) {
                                    Log.d("Response", response.toString());
                                    try {
                                        MainBrowser.this.getHotTopicsFromWeb(response);
                                    } catch (Exception e) {
                                        Log.e("v2ex_touch", e.toString());
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.d("Error.Response", error.toString());
                                }
                            }
                    );
                    mQueue.add(jar);
                } else {
                    MainBrowser.this.showToast(getApplicationContext(), "无网络连接");
                }
            }
        });

        ListView actualListView = mPullRefreshListView.getRefreshableView();

        // Need to use the Actual ListView when registering for Context Menu
        registerForContextMenu(actualListView);
        mTopicAdapter = new TopicAdapter(getApplicationContext(), HotTopicsArray);

        // You can also just use setListAdapter(mAdapter) or
        actualListView.setAdapter(mTopicAdapter);
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

    private ArrayList<ForumPost> getHotTopicsFromWeb(JSONArray response) {
        ArrayList<ForumPost> forumPostArrayList = new ArrayList<>();
        try {
            ForumPost tmpForumPost;
            ForumInfoFactory forumInfoFactory = new ForumInfoFactory();
            TopicInfo tmpTopic = forumInfoFactory.createForumInfo(TopicInfo.class);
            NodeInfo tmpNode = forumInfoFactory.createForumInfo(NodeInfo.class);
            MemberInfo tmpMember = forumInfoFactory.createForumInfo(MemberInfo.class);
            for (int i = 0; i < response.length(); i++) {
                tmpTopic.parseJsonAndInsert(forumDatabaseHelper.getWritableDatabase(), (JSONObject) response.get(i));
                tmpNode.parseJsonAndInsert(forumDatabaseHelper.getWritableDatabase(), (JSONObject) response.get(i));
                tmpMember.parseJsonAndInsert(forumDatabaseHelper.getWritableDatabase(), (JSONObject) response.get(i));
                tmpForumPost = new ForumPost();
                tmpForumPost.loadData((JSONObject) response.get(i));
                forumPostArrayList.add(tmpForumPost);
            }
            mPullRefreshListView.onRefreshComplete();
            mTopicAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            Log.v("v2ex_touch", e.toString());
        }
        return forumPostArrayList;
    }

    private ArrayList<ForumPost> getHotTopicsFromDB() {
        ArrayList<ForumPost> forumPostArrayList = new ArrayList<>();
        SQLiteDatabase db = forumDatabaseHelper.getReadableDatabase();
        // sql query command: select * from member natural join node natural join topic order by last_modified desc
        Cursor cursor = db.rawQuery(
                "select * from member natural join node natural join topic order by last_modified desc",
                new String[]{});
        String str = cursor.getString(cursor.getColumnIndex("title"));
        Log.v("v2ex_touch", str);
        cursor.close();
        return forumPostArrayList;
    }

    @Override
    public void onDestroy() {
        forumDatabaseHelper.close();
        super.onDestroy();
    }
}
