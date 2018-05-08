package com.wenjie.diycode.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gcssloop.diycode_sdk.api.topic.bean.Topic;
import com.sackcentury.shinebuttonlib.ShineButton;
import com.wenjie.diycode.R;
import com.wenjie.diycode.utils.TimeUtil;

import java.util.List;

//import xyz.hanks.library.SmallBang;

/**
 * @ProjectName: StudyDemo
 * @PackageName: com.winfo.wenjie.slidingmenu
 * @FileName: com.winfo.wenjie.slidingmenu.TopicAdapter.java
 * @Author: wenjie
 * @Date: 2017-08-01 15:48
 * @Description:
 * @Version:
 */
public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.CustomeHolder> {

    private Context context;
    private List<Topic> datas;
    private RequestOptions options;
//    private SmallBang mSmallBang;
    private OnItemChildClickListener onItemChildClickListener;
    private OnItemClickListener onItemClickListener;

    public TopicAdapter(Context context, List<Topic> datas) {
        this.context = context;
        this.datas = datas;
        options = new RequestOptions().placeholder(R.mipmap.ic_launcher).error(R.mipmap.failure);
//        mSmallBang = SmallBang.attach2Window((Activity) context);
//        mSmallBang.setColors(new int[]{ContextCompat.getColor(context, R.color.c1), ContextCompat.getColor(context, R.color.c5), ContextCompat.getColor(context, R.color.colorPrimaryDark), ContextCompat.getColor(context, R.color.c3)});
    }

    @Override
    public TopicAdapter.CustomeHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final CustomeHolder customeHolder = new CustomeHolder(LayoutInflater.from(context).inflate(R.layout.item_topic, parent, false));
        customeHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onClick(customeHolder.itemView, customeHolder.getLayoutPosition());
                }
            }
        });
        customeHolder.like.setOnCheckStateChangeListener(new ShineButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(View view, boolean checked) {
                Toast.makeText(context, checked + "", Toast.LENGTH_SHORT).show();
            }
        });

        customeHolder.avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemChildClickListener.onItemChildClick(TopicAdapter.this, view, customeHolder.getLayoutPosition());
            }
        });

        customeHolder.username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemChildClickListener.onItemChildClick(TopicAdapter.this, view, customeHolder.getLayoutPosition());
            }
        });
//        customeHolder.like.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mSmallBang.bang(view);
//            }
//        });
//
//        customeHolder.star.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mSmallBang.bang(view);
//            }
//        });
        return customeHolder;
    }

    @Override
    public void onBindViewHolder(final TopicAdapter.CustomeHolder holder, int position) {
        Topic topic = datas.get(position);
        Glide.with(context).load(topic.getUser().getAvatar_url())
                .apply(options)
                .into(holder.avatar);
        holder.title.setText(topic.getTitle());
        holder.username.setText(topic.getUser().getName());
        long updateTime = TimeUtil.string2time(topic.getUpdated_at());
        holder.time.setText(TimeUtil.getTimeElapse(updateTime));
        holder.nodeName.setText(topic.getNode_name());
        holder.state.setText("评论 " + topic.getReplies_count());
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClick(View itemview, int position);
    }


    public interface OnItemChildClickListener {
        void onItemChildClick(TopicAdapter topicAdapter, View view, int position);
    }

    public void setOnItemChildClickListener(OnItemChildClickListener onItemChildClickListener) {
        this.onItemChildClickListener = onItemChildClickListener;
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class CustomeHolder extends RecyclerView.ViewHolder {

        ImageView avatar;
        TextView username;
        TextView time;
        TextView title;
        TextView nodeName;
        TextView state;
        ShineButton like;
        ShineButton star;

        CustomeHolder(View itemView) {
            super(itemView);
            avatar = (ImageView) itemView.findViewById(R.id.avatar);
            username = (TextView) itemView.findViewById(R.id.username);
            time = (TextView) itemView.findViewById(R.id.time);
            title = (TextView) itemView.findViewById(R.id.title);
            nodeName = (TextView) itemView.findViewById(R.id.node_name);
            state = (TextView) itemView.findViewById(R.id.state);
            like = (ShineButton) itemView.findViewById(R.id.like);
            like.init((Activity) context);
            star = (ShineButton) itemView.findViewById(R.id.star);
            star.init((Activity) context);
        }
    }

    /**
     * 加载更多数据
     *
     * @param topics
     */
    public void loadMore(List<Topic> topics) {
        this.datas.addAll(topics);
        notifyDataSetChanged();
    }

    /**
     * 刷新数据
     *
     * @param topics
     */
    public void referesh(List<Topic> topics) {
        this.datas = topics;
        notifyDataSetChanged();
    }
}
