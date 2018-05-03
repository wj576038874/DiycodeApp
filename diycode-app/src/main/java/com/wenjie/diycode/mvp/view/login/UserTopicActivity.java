package com.wenjie.diycode.mvp.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.gcssloop.diycode_sdk.api.topic.bean.Topic;
import com.gcssloop.diycode_sdk.api.user.bean.User;
import com.wenjie.diycode.R;
import com.wenjie.diycode.adapter.TopicAdapter;
import com.wenjie.diycode.mvp.presenter.topic.UserTopicPresenter;
import com.wenjie.diycode.mvp.view.topic.ITopicView;
import com.wenjie.diycode.mvp.view.topic.TopicDescActivity;
import com.wenjie.diycode.utils.DividerItemDecoration;
import com.wenjie.diycode.utils.ToastUtils;

import java.util.List;

public class UserTopicActivity extends AppCompatActivity implements ITopicView {

    private RecyclerView recyclerView;
    private LinearLayout progressBar;
    private Integer offset = 0;
    private UserTopicPresenter userTopicPresenter;
    private TopicAdapter topicAdapter;
    private Toolbar toolbar;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_topic);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        assert bundle != null;
        final User user = (User) bundle.getSerializable("user");
        userTopicPresenter = new UserTopicPresenter(this);
        progressBar = (LinearLayout) findViewById(R.id.loading_progress);
        recyclerView = (RecyclerView) findViewById(R.id.useryopic_recycleview);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swrl);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.c3, R.color.c5, R.color.c4);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                userTopicPresenter.refreshData(user.getLogin());
            }
        });
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(user.getLogin());
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        progressBar = (LinearLayout) findViewById(R.id.loading_progress);
        userTopicPresenter.getUserTopicList(user.getLogin());

    }

    @Override
    public Integer getLimit() {
        return 150;
    }

    @Override
    public Integer getOffset() {
        return offset;
    }

    @Override
    public void setTopics(final List<Topic> topics) {
        swipeRefreshLayout.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        topicAdapter = new TopicAdapter(this, topics);
        recyclerView.setAdapter(topicAdapter);
        topicAdapter.setOnItemClickListener(new TopicAdapter.OnItemClickListener() {
            @Override
            public void onClick(View itemview, int position) {
//                ToastUtils.showToast(getActivity() , topics.get(position).getTitle());
                Intent intent = new Intent(UserTopicActivity.this, TopicDescActivity.class);
                intent.putExtra("id", topics.get(position).getId());
                startActivity(intent);
            }
        });

        topicAdapter.setOnItemChildClickListener(new TopicAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(TopicAdapter topicAdapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.avatar:
                        ToastUtils.showToast(UserTopicActivity.this, "avatar");
                        break;
                    case R.id.username:
                        ToastUtils.showToast(UserTopicActivity.this, "username");
                        break;
                }
            }
        });
    }

    @Override
    public void setReferesh(List<Topic> topics) {
        ToastUtils.showToast(this, "刷新成功");
        swipeRefreshLayout.setRefreshing(false);
        topicAdapter.referesh(topics);
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
        swipeRefreshLayout.setVisibility(View.VISIBLE);
    }


    @Override
    public void setTopicsMore(List<Topic> topics) {

    }

    @Override
    public void showFailure() {
    }
}
