package co.desgemini.v2extouch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Des Gemini on 3/17/15.
 */
public class TopicAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<ForumPost> mTopicViewList;

    public TopicAdapter(Context context, List<ForumPost> topicViewList) {
        mInflater = LayoutInflater.from(context);
        mTopicViewList = topicViewList;
    }

    @Override
    public int getCount() {
        return mTopicViewList.size();
    }

    @Override
    public Object getItem(int position) {
        return mTopicViewList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder holder;
        if(convertView == null) {
            view = mInflater.inflate(R.layout.topic_view, parent, false);
            holder = new ViewHolder();
            holder.author = (TextView)view.findViewById(R.id.topic_author);
            holder.title = (TextView)view.findViewById(R.id.topic_title);
            holder.date = (TextView)view.findViewById(R.id.topic_date);
            holder.replyNum = (TextView)view.findViewById(R.id.topic_reply_num);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder)view.getTag();
        }

        ForumPost tmpForumPost = mTopicViewList.get(position);
        holder.author.setText(tmpForumPost.getAuthor());
        holder.title.setText(tmpForumPost.getTitle());
        holder.date.setText(String.valueOf(tmpForumPost.getDate()));
        holder.replyNum.setText(String.valueOf(tmpForumPost.getReplyNum()));

        return view;
    }

    private class ViewHolder {
        public TextView author, title, date, replyNum;
    }
}
