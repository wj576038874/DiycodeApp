package com.wenjie.diycode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gcssloop.diycode_sdk.api.topic.bean.Topic;
import com.wenjie.diycode.adapter.SwipeAdapter;
import com.wenjie.diycode.mvp.presenter.topic.ITopicPresenter;
import com.wenjie.diycode.mvp.presenter.topic.TopicPresenter;
import com.wenjie.diycode.mvp.view.topic.ITopicView;
import com.wenjie.diycode.utils.ToastUtils;
import com.wenjie.diycode.widget.DividerItemDecoration;

import java.util.List;

public class SwipeMenuDelActivity extends AppCompatActivity implements ITopicView {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_menu_del);
        ITopicPresenter topicPresenter = new TopicPresenter(this);
        recyclerView = (RecyclerView) findViewById(R.id.swipeDel_recycleView);
        recyclerView.setLayoutManager(linearLayoutManager = new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL, R.drawable.divider_bg));
        topicPresenter.getTopicsList();

    }

    @Override
    public Integer getLimit() {
        return 20;
    }

    @Override
    public Integer getOffset() {
        return 0;
    }

    @Override
    public void setTopics(final List<Topic> topics) {
        SwipeAdapter topicAdapter = new SwipeAdapter(this, topics);
        recyclerView.setAdapter(topicAdapter);

        //注意事项，设置item点击，不能对整个holder.itemView设置咯，只能对第一个子View，即原来的content设置，这算是局限性吧。
        topicAdapter.setOnItemClickListener(new SwipeAdapter.OnItemClickListener() {
            @Override
            public void onClick(View itemview, int position) {
                ToastUtils.showToast(SwipeMenuDelActivity.this, topics.get(position).getTitle());
            }
        });

        topicAdapter.setOnItemChildClickListener(new SwipeAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(SwipeAdapter swipeAdapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.avatar:
                        break;
                    case R.id.username:
                        break;
                    case R.id.btnDelete:
                        topics.remove(position);
                        swipeAdapter.notifyItemRemoved(position);
                        break;
                    case R.id.btnTop:
                        if (position > 0 && position < topics.size()) {
                            Topic topic = topics.get(position);
                            topics.remove(topic);
                            swipeAdapter.notifyItemInserted(0);
                            topics.add(0, topic);
                            swipeAdapter.notifyItemRemoved(position + 1);
                            if (linearLayoutManager.findFirstVisibleItemPosition() == 0) {
                                recyclerView.scrollToPosition(0);
                            }
                            //notifyItemRangeChanged(0,holder.getAdapterPosition()+1);
                        }
                        break;
                }
            }
        });
    }

    @Override
    public void setReferesh(List<Topic> topics) {

    }

    @Override
    public void setTopicsMore(List<Topic> topics) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showFailure() {

    }
}
