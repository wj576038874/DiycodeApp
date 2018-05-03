package com.wenjie.diycode.mvp.view.topic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.gcssloop.diycode_sdk.api.topic.bean.Topic;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wenjie.diycode.MainActivity;
import com.wenjie.diycode.R;
import com.wenjie.diycode.adapter.TopicAdapter;
import com.wenjie.diycode.mvp.presenter.topic.ITopicPresenter;
import com.wenjie.diycode.mvp.presenter.topic.TopicPresenter;
import com.wenjie.diycode.utils.DividerItemDecoration;

import java.util.List;


public class TopicFragment extends Fragment implements ITopicView, OnRefreshListener, OnLoadmoreListener, MainActivity.OnFabClickLitener {

    private RecyclerView recyclerView;
    private TopicAdapter topicAdapter;
    private ITopicPresenter topicPresenter;
    private SmartRefreshLayout refreshLayout;
    private Integer offset = 0;
    private boolean isInit = false;
    private ProgressBar progressBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_topic, container, false);
        refreshLayout = (SmartRefreshLayout) view.findViewById(R.id.topic_refreshLayout);
        progressBar = (ProgressBar) view.findViewById(R.id.loading_progress);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadmoreListener(this);
        topicPresenter = new TopicPresenter(this);
        recyclerView = (RecyclerView) view.findViewById(R.id.topic_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        ((MainActivity) getActivity()).setOnFabClickLitener(this);
        topicPresenter.getTopicsList();
        isInit = true;
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isInit) {
            ((MainActivity) getActivity()).setOnFabClickLitener(this);
        }
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
    public void setTopics(final List<Topic> topics) {
        refreshLayout.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        topicAdapter = new TopicAdapter(getActivity(), topics);
        recyclerView.setAdapter(topicAdapter);
        topicAdapter.setOnItemClickListener(new TopicAdapter.OnItemClickListener() {
            @Override
            public void onClick(View itemview, int position) {
//                ToastUtils.showToast(getActivity() , topics.get(position).getTitle());
                Intent intent = new Intent(getActivity() , TopicDescActivity.class);
                intent.putExtra("id" , topics.get(position).getId());
                startActivity(intent);
            }
        });

        topicAdapter.setOnItemChildClickListener(new TopicAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(TopicAdapter topicAdapter, View view, int position) {
                switch (view.getId()){
                    case R.id.avatar:
                        break;
                    case R.id.username:
                        break;
                }
            }
        });
    }

    @Override
    public void setReferesh(List<Topic> topics) {
        refreshLayout.finishRefresh(true);
        topicAdapter.referesh(topics);
    }


    @Override
    public void setTopicsMore(List<Topic> topics) {
        refreshLayout.finishLoadmore(true);
        topicAdapter.loadMore(topics);
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
    public void showFailure() {
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        offset = 0;
        topicPresenter.refreshData();
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        offset += 20;
        topicPresenter.getTopicsListMore();
    }

    @Override
    public void onFabClick(View view) {
        recyclerView.smoothScrollToPosition(0);
    }

}
