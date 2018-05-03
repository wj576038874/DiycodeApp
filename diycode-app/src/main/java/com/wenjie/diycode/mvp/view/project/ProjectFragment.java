package com.wenjie.diycode.mvp.view.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gcssloop.diycode_sdk.api.project.bean.Project;
import com.wenjie.diycode.MainActivity;
import com.wenjie.diycode.R;
import com.wenjie.diycode.adapter.ProjectAdapter;
import com.wenjie.diycode.mvp.presenter.project.ProjectPresenter;
import com.wenjie.diycode.mvp.view.WebViewActivity;
import com.wenjie.diycode.utils.DividerItemDecoration;
import com.wenjie.diycode.utils.ToastUtils;
import com.wenjie.diycode.widget.CustomLoadMoreView;

import java.util.List;

/**
 * @ProjectName: StudyDemo
 * @PackageName: com.winfo.wenjie.slidingmenu
 * @FileName: com.winfo.wenjie.slidingmenu.ProjectFragment.java
 * @Author: wenjie
 * @Date: 2017-07-24 16:53
 * @Description: sd
 * @Version:
 */
public class ProjectFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener, IProjectView, MainActivity.OnFabClickLitener {

    private RecyclerView recyclerView;
    private ProjectAdapter projectAdapter;
    //    private SmartRefreshLayout refreshLayout;
    private boolean isInit = false;
    private boolean isLoadData = false;
    private ProjectPresenter projectPresenter;
    private Integer offset = 20;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_project, container, false);
        projectPresenter = new ProjectPresenter(this);
        progressBar = (ProgressBar) view.findViewById(R.id.loading_progress);
        recyclerView = (RecyclerView) view.findViewById(R.id.project_recyclerView);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.c3, R.color.c5, R.color.c4);
        swipeRefreshLayout.setOnRefreshListener(this);
//        refreshLayout = (SmartRefreshLayout) view.findViewById(R.id.project_refreshLayout);
//        refreshLayout.setOnRefreshListener(this);
//        refreshLayout.setOnLoadmoreListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        isInit = true;
//        initBus();
        return view;
    }

//    private void initBus() {
//        RxBus rxBus = RxBus.getIntanceBus();
//        rxBus.addSubscription(this , RxBus.getIntanceBus().doSubscribe(BaseEvent.class, new Consumer<BaseEvent>() {
//            @Override
//            public void accept(BaseEvent baseEvent) throws Exception {
//                refreshLayout.setVisibility(View.VISIBLE);
//                isLoadData = true;
//                refreshLayout.finishRefresh(true);
//                projectAdapter = new ProjectAdapter(getActivity(), (List<Project>) baseEvent.getT());
//                recyclerView.setAdapter(projectAdapter);
//                RxBus.getIntanceBus().unSubscribe(ProjectFragment.this);
//            }
//        }));
//    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isInit) {
            if (!isLoadData) {
                //加载数据
                projectPresenter.getProjectsList();
            }
            ((MainActivity) getActivity()).setOnFabClickLitener(this);
        }
    }

//    @Override
//    public void onRefresh(RefreshLayout refreshlayout) {
//        offset = 0;
//        projectPresenter.refreshData();
//    }

    @Override
    public void setProjectsList(final List<Project> projects) {
        progressBar.setVisibility(View.GONE);
//        refreshLayout.setVisibility(View.VISIBLE);
        isLoadData = true;
        projectAdapter = new ProjectAdapter(projects);
        projectAdapter.setLoadMoreView(new CustomLoadMoreView());
        projectAdapter.setOnLoadMoreListener(this, recyclerView);
//        projectAdapter.setEnableLoadMore(true);
        recyclerView.setAdapter(projectAdapter);

        projectAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("url", projects.get(position).getGithub());
                startActivity(intent);
            }
        });

    }

    @Override
    public void setRefreshData(List<Project> projects) {
//        refreshLayout.finishRefresh(true);
        swipeRefreshLayout.setRefreshing(false);
        projectAdapter.refreshDatas(projects);
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
    public void showFailure() {

    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
//        refreshLayout.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
//        refreshLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void setProjectsListMore(List<Project> projects) {
//        refreshLayout.finishLoadmore(true);
        offset += 20;
        projectAdapter.addData(projects);
        projectAdapter.loadMoreComplete();
    }

//    @Override
//    public void onLoadmore(RefreshLayout refreshlayout) {
//        offset += 20;
//        projectPresenter.getProjectsListMore();
//    }

    @Override
    public void onFabClick(View view) {
        recyclerView.smoothScrollToPosition(0);
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        projectAdapter.setEnableLoadMore(false);
        offset = 0;
        projectPresenter.refreshData();
    }

    @Override
    public void onLoadMoreRequested() {
        ToastUtils.showToast(getActivity() , "onLoadMoreRequested");
        swipeRefreshLayout.setEnabled(false);
        projectPresenter.getProjectsListMore();
    }
}
