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

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gcssloop.diycode_sdk.api.sites.bean.Sites;
import com.wenjie.diycode.MainActivity;
import com.wenjie.diycode.R;
import com.wenjie.diycode.adapter.NewSiteAdapter;
import com.wenjie.diycode.entity.CoolSites;
import com.wenjie.diycode.entity.SubCoolSites;
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
public class NewSitesFragment extends Fragment implements ISitesFragment, MainActivity.OnFabClickLitener {

    private RecyclerView sitesRecycleView;
    private boolean isInit = false;
    private boolean isLoadData = false;
    private SitesPresenter sitesPresenter;
    private List<CoolSites> items = new ArrayList<>();
    private NewSiteAdapter sitesAdapter;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;

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
        for (int i = 0; i < sites.size(); i++) {
            items.add(new CoolSites(true, sites.get(i).getName()));
            for (int k = 0; k < sites.get(i).getSites().size(); k++) {
                SubCoolSites subCoolSites = new SubCoolSites();
                subCoolSites.setName(sites.get(i).getSites().get(k).getName());
                subCoolSites.setUrl(sites.get(i).getSites().get(k).getUrl());
                subCoolSites.setAvatar_url(sites.get(i).getSites().get(k).getAvatar_url());
                items.add(new CoolSites(subCoolSites));
            }
        }
        sitesRecycleView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        sitesAdapter = new NewSiteAdapter(R.layout.item_site, R.layout.item_sites, items);
        sitesRecycleView.setAdapter(sitesAdapter);
        sitesAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                CoolSites coolSites = items.get(position);
                if (coolSites.isHeader) {
                    ToastUtils.showToast(getActivity(), coolSites.header);
                } else {
                    Intent intent = new Intent(getActivity(), WebViewActivity.class);
                    intent.putExtra("url", coolSites.t.getUrl());
                    startActivity(intent);
                }
            }
        });
        sitesAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
    }


    @Override
    public void refresh(List<Sites> sites) {
        swipeRefreshLayout.setRefreshing(false);
        List<CoolSites> items = new ArrayList<>();
        for (int i = 0; i < sites.size(); i++) {
            items.add(new CoolSites(true, sites.get(i).getName()));
            for (int k = 0; k < sites.get(i).getSites().size(); k++) {
                SubCoolSites subCoolSites = new SubCoolSites();
                subCoolSites.setName(sites.get(i).getSites().get(k).getName());
                subCoolSites.setUrl(sites.get(i).getSites().get(k).getUrl());
                subCoolSites.setAvatar_url(sites.get(i).getSites().get(k).getAvatar_url());
                items.add(new CoolSites(subCoolSites));
            }
        }
        sitesAdapter.refresh(items);
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
