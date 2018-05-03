package com.wenjie.diycode;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.mypopsy.drawable.DrawerArrowDrawable;
import com.mypopsy.drawable.ToggleDrawable;
import com.wenjie.diycode.mvp.view.project.ProjectFragment;
import com.wenjie.diycode.mvp.view.sites.NewSitesFragment;
import com.wenjie.diycode.mvp.view.topic.TopicFragment;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends SlidingFragmentActivity implements View.OnClickListener, LeftSlidingMenuFragment.OnShowContent {

    private FloatingActionButton fab;
    protected SlidingMenu mSlidingMenu;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<Fragment> fragmentList;
    private ImageButton imgBtnMenu;
    private ToggleDrawable toggleDrawable;
    private Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        initSlidingMenu();
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setLogo(R.mipmap.ic_logo);
//        toolbar.setTitle("");
//        setSupportActionBar(toolbar);
        mTabLayout = (TabLayout) findViewById(R.id.mTablayout);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        imgBtnMenu = (ImageButton) findViewById(R.id.ivTitleBtnLeft);
        toggleDrawable = new DrawerArrowDrawable(this, 0, R.style.MyDrawerArrowDrawable);
        imgBtnMenu.setImageDrawable(toggleDrawable);

        mTabLayout.addTab(mTabLayout.newTab().setText("社区"));
        mTabLayout.addTab(mTabLayout.newTab().setText("项目"));
        mTabLayout.addTab(mTabLayout.newTab().setText("酷站"));

        fragmentList = new ArrayList<>();
        fragmentList.add(new TopicFragment());
        fragmentList.add(new ProjectFragment());
        fragmentList.add(new NewSitesFragment());

        PageAdapter pageAdapter = new PageAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(pageAdapter);
        mViewPager.setOffscreenPageLimit(3);

        fab = (FloatingActionButton) findViewById(R.id.fab);

        initEvent();
    }


    private void initEvent() {
        fab.setOnClickListener(this);
        imgBtnMenu.setOnClickListener(this);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);//
                } else {
                    mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);//
                }
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private void initSlidingMenu() {

        setBehindContentView(R.layout.main_left_layout);
        FragmentTransaction mFragementTransaction = getSupportFragmentManager()
                .beginTransaction();
        LeftSlidingMenuFragment mFrag = new LeftSlidingMenuFragment();
        mFrag.setOnShowContent(this);
        mFragementTransaction.replace(R.id.main_left_fragment, mFrag);
        mFragementTransaction.commit();
        // customize the SlidingMenu
        mSlidingMenu = getSlidingMenu();
        mSlidingMenu.setMode(SlidingMenu.LEFT);//设置菜单在左边滑动出来
        mSlidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);//设置菜单的偏移量也就是菜单的宽度
        mSlidingMenu.setFadeDegree(0.35f);//阻尼效果
        mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);//
        mSlidingMenu.setShadowDrawable(R.drawable.shadow);//设置菜单被拉出时逐渐显示的背景颜色
        mSlidingMenu.setFadeEnabled(true);//
        mSlidingMenu.setBehindScrollScale(0.333f);//设置SlidingMenu与下方视图的移动的速度比，当为1时同时移动，取值0-1
        mSlidingMenu.setDeviceScreenWidth(getMetricsWidth(this));//菜单滑动比例的监听
        mSlidingMenu.setOnScaleListener(new SlidingMenu.OnScaleListener() {
            @Override
            public void onScale(float scale) {
                toggleDrawable.setProgress(scale);
            }
        });
    }

    /**
     * @param context 上下文
     * @return int
     * @brief 获取手机屏幕尺寸 宽度
     */
    public int getMetricsWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }


    @Override
    public void shwoContent() {
        mSlidingMenu.showContent(true);
    }

    public interface OnFabClickLitener {
        void onFabClick(View view);
    }

    public OnFabClickLitener onFabClickLitener;

    public void setOnFabClickLitener(OnFabClickLitener onFabClickLitener) {
        this.onFabClickLitener = onFabClickLitener;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                onFabClickLitener.onFabClick(view);
                break;
            case R.id.ivTitleBtnLeft:
                mSlidingMenu.showMenu(true);
                break;
        }
    }

    private class PageAdapter extends FragmentStatePagerAdapter {

        PageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }
}
