package com.wenjie.diycode.mvp.view.sites;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.gcssloop.diycode_sdk.api.sites.bean.Sites;
import com.wenjie.diycode.MainActivity;
import com.wenjie.diycode.R;
import com.wenjie.diycode.adapter.SitesAdapter;
import com.wenjie.diycode.mvp.presenter.sites.SitesPresenter;
import com.wenjie.diycode.mvp.view.WebViewActivity;
import com.wenjie.diycode.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * @ProjectName: StudyDemo
 * @PackageName: com.winfo.wenjie.slidingmenu
 * @FileName: com.winfo.wenjie.slidingmenu.SitesFragment.java
 * @Author: wenjie
 * @Date: 2017-07-24 16:53
 * @Description:
 * @Version:
 */
public class SitesFragment extends Fragment implements ISitesFragment, MainActivity.OnFabClickLitener {

    private RecyclerView sitesRecycleView;
    private boolean isInit = false;
    private boolean isLoadData = false;
    private SitesPresenter sitesPresenter;
    private List<Object> items = new ArrayList<>();
    private SitesAdapter sitesAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sites, container, false);
        sitesPresenter = new SitesPresenter(this);
        progressBar = (ProgressBar) view.findViewById(R.id.loading_progress);
        sitesRecycleView = (RecyclerView) view.findViewById(R.id.news_recyclerView);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl);
//        swipeRefreshLayout.setProgressViewOffset(false , -100 , 120);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.c3, R.color.c5, R.color.c4);
//        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.c1);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                sitesPresenter.refresh();
            }
        });
        isInit = true;
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isInit) {
            if (!isLoadData) {
                //加载数据
                sitesPresenter.getSites();
            }
            ((MainActivity) getActivity()).setOnFabClickLitener(this);
        }
    }

    @Override
    public void showFailure() {
    }

    @Override
    public void setDatas(final List<Sites> sites) {
        progressBar.setVisibility(View.GONE);
        swipeRefreshLayout.setVisibility(View.VISIBLE);
        isLoadData = true;
        //数据获取之后  将数据循环遍历，放进items集合中，至于服务器返回什么格式的数据，我想看下实体类就应该明白了
        for (int i = 0; i < sites.size(); i++) {
            items.add(sites.get(i));
            for (int k = 0; k < sites.get(i).getSites().size(); k++) {
                items.add(sites.get(i).getSites().get(k));
            }
        }
        //实例化适配器将遍历好的数据放进适配器中
        sitesAdapter = new SitesAdapter(getActivity(), items);
        //new一个布局管理器，这里是用GridLayoutManager，要区分3列
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);//多少列
        //下面这个方法很重要，根据position获取当前这条数据是标题还是数据项，来设置他的跨列
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                //适配器中有这么一个方法，根据position获取当前这条数据是标题还是数据项，来设置他的跨列
                switch (sitesAdapter.getItemViewType(position)) {
                    case SitesAdapter.SITES://标题的话跨多少列 这个值要跟整个列数相等 如果大于会出错，小于布局会乱
                        return 3;
                    case SitesAdapter.SITE://数据项
                        return 1;//不跨列，就是分成三列显示
                    default:
                        return -1;
                }
            }
        });
        sitesRecycleView.setLayoutManager(gridLayoutManager);
//        sitesRecycleView.addItemDecoration(new DividerItemDecoration(getActivity() , GridLayoutManager.VERTICAL));
        sitesRecycleView.setAdapter(sitesAdapter);

        //item的点击事件，这里实现，进行具体的操作
        sitesAdapter.setOnItemClickListener(new SitesAdapter.OnItemClickListener() {
            @Override
            public void onClick(View itemview, int position) {
                switch (sitesAdapter.getItemViewType(position)) {
                    case SitesAdapter.SITE:
//                        ToastUtils.showToast(getActivity() , ((CoolSites.Site) items.get(position)).getName());
                        Intent intent = new Intent(getActivity(), WebViewActivity.class);
                        intent.putExtra("url", ((Sites.Site) items.get(position)).getUrl());
                        startActivity(intent);
                        break;
                    case SitesAdapter.SITES:
                        ToastUtils.showToast(getActivity(), ((Sites) items.get(position)).getName());
                        break;
                }
            }
        });
    }


    @Override
    public void refresh(List<Sites> sites) {
        ToastUtils.showToast(getActivity(), "刷新成功");
        swipeRefreshLayout.setRefreshing(false);
        List<Object> items = new ArrayList<>();
        //数据获取之后  将数据循环遍历，放进items集合中，至于服务器返回什么格式的数据，我想看下实体类就应该明白了
        for (int i = 0; i < sites.size(); i++) {
            items.add(sites.get(i));
            for (int k = 0; k < sites.get(i).getSites().size(); k++) {
                items.add(sites.get(i).getSites().get(k));
            }
        }
        sitesAdapter.refreshDatas(items);
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
    public void onFabClick(View view) {
        sitesRecycleView.smoothScrollToPosition(0);
    }


}
