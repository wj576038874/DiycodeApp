package com.wenjie.diycode.mvp.view.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.gcssloop.diycode_sdk.api.topic.bean.Topic;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wenjie.diycode.R;
import com.wenjie.diycode.adapter.TopicAdapter;
import com.wenjie.diycode.mvp.presenter.login.MyTopicPresenter;
import com.wenjie.diycode.utils.PreferenceUtils;

import java.util.List;

public class MyTopicActivity extends AppCompatActivity implements IMyTopicView, OnRefreshListener, OnLoadmoreListener {

    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private Integer offset = 0;
    private LinearLayout progressBar;
    private MyTopicPresenter myTopicPresenter;
    private TopicAdapter topicAdapter;
    private SmartRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_topic);
        myTopicPresenter = new MyTopicPresenter(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("我的收藏");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.topic_refreshLayout);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadmoreListener(this);
        refreshLayout.setEnableRefresh(false);
        recyclerView = (RecyclerView) findViewById(R.id.mytpicrecycleview);
        progressBar = (LinearLayout) findViewById(R.id.loading_progress);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        myTopicPresenter.getUserCollectionTopicList(PreferenceUtils.getString(this, "userName"));
    }

    @Override
    public void showLoading() {
        refreshLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        refreshLayout.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setDatas(List<Topic> datas) {
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setVisibility(View.VISIBLE);
        topicAdapter = new TopicAdapter(this, datas);
        recyclerView.setAdapter(topicAdapter);
    }

    @Override
    public void refresh(List<Topic> datas) {
        refreshLayout.finishRefresh(true);
        topicAdapter.referesh(datas);
    }

    @Override
    public Integer getLimit() {
        return 20;
    }

    @Override
    public Integer getOffset() {
        return offset;
    }

    @Override
    public void setLoadMore(List<Topic> datas) {
        refreshLayout.finishLoadmore(true);
        topicAdapter.loadMore(datas);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        offset += 20;
        myTopicPresenter.loadmore(PreferenceUtils.getString(this, "userName"));
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        offset = 0;
        myTopicPresenter.refresh(PreferenceUtils.getString(this, "userName"));
    }
}
