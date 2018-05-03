package com.wenjie.diycode.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gcssloop.diycode_sdk.api.topic.bean.Topic;
import com.gcssloop.diycode_sdk.api.user.bean.User;
import com.sackcentury.shinebuttonlib.ShineButton;
import com.wenjie.diycode.R;
import com.wenjie.diycode.utils.TimeUtil;
import com.wenjie.diycode.widget.GlideRoundTransform;

import java.util.List;


/**
 * @ProjectName: DiycodeApp
 * @PackageName: com.wenjie.diycode.adapter
 * @FileName: com.wenjie.diycode.adapter.NewTopicAdapter.java
 * @Author: wenjie
 * @Date: 2017-08-25 14:52
 * @Description:
 * @Version:
 */
public class NewTopicAdapter extends BaseItemDraggableAdapter<Topic, BaseViewHolder> {

    private RequestOptions options;

    public NewTopicAdapter(@LayoutRes int layoutResId, @Nullable List<Topic> data) {
        super(layoutResId, data);
        options = new RequestOptions().placeholder(R.mipmap.ic_launcher).error(R.mipmap.failure).transform(new GlideRoundTransform());
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, Topic topic) {
        User user = topic.getUser();
        viewHolder.setText(R.id.title, topic.getTitle());
        viewHolder.setText(R.id.username, topic.getUser().getName());
        long updateTime = TimeUtil.string2time(topic.getUpdated_at());
        viewHolder.setText(R.id.time, TimeUtil.getTimeElapse(updateTime));
        viewHolder.setText(R.id.node_name, topic.getNode_name());
        viewHolder.setText(R.id.state, "评论 " + topic.getReplies_count());
        Glide.with(mContext).load(topic.getUser().getAvatar_url())
                .apply(options)
//                .apply(RequestOptions.bitmapTransform(new GlideRoundTransform()))
                .into((ImageView) viewHolder.getView(R.id.avatar));
        viewHolder.addOnClickListener(R.id.node_name);//item中子view的点击事件
        viewHolder.addOnClickListener(R.id.avatar);
        viewHolder.addOnClickListener(R.id.username);
//        viewHolder.getView(R.id.username).setTag(user);
//        viewHolder.getView(R.id.avatar).setTag(user);
        ShineButton shineButton = viewHolder.getView(R.id.like);
        shineButton.setOnCheckStateChangeListener(new ShineButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(View view, boolean checked) {
                Toast.makeText(mContext, checked + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 加载更多数据
     *
     * @param topics
     */
    public void loadMore(List<Topic> topics) {
        this.mData.addAll(topics);
        notifyDataSetChanged();
    }

    /**
     * 刷新数据
     *
     * @param topics
     */
    public void referesh(List<Topic> topics) {
//        this.mData = topics;
//        notifyDataSetChanged();
        setNewData(topics);
    }
}
