package com.wenjie.diycode.mvp.view.topic;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.gcssloop.diycode_sdk.api.topic.bean.Topic;
import com.gcssloop.diycode_sdk.api.user.bean.User;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wenjie.diycode.MainActivity;
import com.wenjie.diycode.R;
import com.wenjie.diycode.adapter.NewTopicAdapter;
import com.wenjie.diycode.mvp.presenter.topic.TopicPresenter;
import com.wenjie.diycode.mvp.view.login.UserTopicActivity;
import com.wenjie.diycode.utils.DividerItemDecoration;
import com.wenjie.diycode.utils.ToastUtils;

import java.util.List;


public class NewTopicFragment extends Fragment implements ITopicView, OnRefreshListener, OnLoadmoreListener, MainActivity.OnFabClickLitener {

    private RecyclerView recyclerView;
    private NewTopicAdapter topicAdapter;
    private TopicPresenter topicPresenter;
    private SmartRefreshLayout refreshLayout;
    //    private LoadingImageView loadingImageView;
    private ProgressBar progressBar;
    private Integer offset = 0;
    private boolean isInit = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_topic, container, false);
        topicPresenter = new TopicPresenter(this);
        refreshLayout = (SmartRefreshLayout) view.findViewById(R.id.topic_refreshLayout);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadmoreListener(this);
        progressBar = (ProgressBar) view.findViewById(R.id.loading_progress);
        recyclerView = (RecyclerView) view.findViewById(R.id.topic_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        ((MainActivity) getActivity()).setOnFabClickLitener(this);
//        loadingImageView = (LoadingImageView) view.findViewById(R.id.loadingiv);
//        loadingImageView.setMaskOrientation(1);
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
        progressBar.setVisibility(View.GONE);
        refreshLayout.setVisibility(View.VISIBLE);
        topicAdapter = new NewTopicAdapter(R.layout.item_topic, topics);
        recyclerView.setAdapter(topicAdapter);
        topicAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(), TopicDescActivity.class);
                intent.putExtra("id", topics.get(position).getId());
                startActivity(intent);
            }
        });

        topicAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent;
                User user = topics.get(position).getUser();
                switch (view.getId()) {
                    case R.id.avatar:
//                        ToastUtils.showToast(getActivity(), "avatar");
                        intent = new Intent(getActivity(), UserTopicActivity.class);
                        intent.putExtra("user", user);
                        startActivity(intent);
                        break;
                    case R.id.node_name:
                        ToastUtils.showToast(getActivity(), "node_name");
                        break;
                    case R.id.username:
//                        ToastUtils.showToast(getActivity(), "username=" + user.getLogin());
                        intent = new Intent(getActivity(), UserTopicActivity.class);
                        intent.putExtra("user", user);
                        startActivity(intent);
                        break;
                }
            }
        });
        topicAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);

        /**
         * 滑动 和 拖拽
         */
        ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(topicAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        topicAdapter.enableDragItem(itemTouchHelper , R.id.title , true);
        topicAdapter.setOnItemDragListener(new OnItemDragListener() {
            @Override
            public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {

            }

            @Override
            public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {

            }

            @Override
            public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {

            }
        });

        topicAdapter.enableSwipeItem();
        topicAdapter.setOnItemSwipeListener(new OnItemSwipeListener() {
            @Override
            public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {
                ToastUtils.showToast(getActivity(),  "onItemSwipeStart");
            }

            @Override
            public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {
                ToastUtils.showToast(getActivity(),  "clearView");
            }

            @Override
            public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {
                ToastUtils.showToast(getActivity(),  "onItemSwiped");
            }

            @Override
            public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {
                ToastUtils.showToast(getActivity(),  "onItemSwipeMoving");
            }
        });

    }

    @Override
    public void setReferesh(List<Topic> topics) {
        refreshLayout.finishRefresh(true);
        topicAdapter.referesh(topics);
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        refreshLayout.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
        refreshLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showFailure() {

    }


    @Override
    public void setTopicsMore(List<Topic> topics) {
        topicAdapter.loadMore(topics);
        refreshLayout.finishLoadmore(true);
        if (topicAdapter.getItemCount() > 60) {
            refreshLayout.setLoadmoreFinished(true);//将不会再次触发加载更多事件
        }
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
