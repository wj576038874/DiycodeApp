package com.wenjie.diycode;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.gcssloop.diycode_sdk.api.user.bean.UserDetail;
import com.gcssloop.diycode_sdk.utils.CacheUtil;
import com.wenjie.diycode.mvp.view.login.LoginActivity;
import com.wenjie.diycode.mvp.view.login.MyTopicActivity;
import com.wenjie.diycode.mvp.view.project.ProjectFragment;
import com.wenjie.diycode.mvp.view.sites.NewSitesFragment;
import com.wenjie.diycode.mvp.view.topic.NewTopicFragment;
import com.wenjie.diycode.utils.MenuUtil;
import com.wenjie.diycode.utils.PreferenceUtils;
import com.wenjie.diycode.utils.ToastUtils;
import com.wenjie.diycode.widget.GlideRoundTransform;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    private FloatingActionButton fab;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<Fragment> fragmentList;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private ImageView imageView;
    private TextView tvUserName;
    private TextView tvZhuye;


    private CacheUtil cacheUtil;
    private RequestOptions options;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        initData();

        initEvent();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mTabLayout = (TabLayout) findViewById(R.id.mTablayout);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        imageView = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.img_user);
        tvUserName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.username);
        tvZhuye = (TextView) navigationView.getHeaderView(0).findViewById(R.id.zhuye);
        fab = (FloatingActionButton) findViewById(R.id.fab);
    }

    private void initData() {
        options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .priority(Priority.HIGH)
                .transform(new GlideRoundTransform());
        cacheUtil = new CacheUtil(this);
        if (cacheUtil.getToken() == null) {
            imageView.setImageResource(R.mipmap.ic_launcher);
            tvUserName.setText("用户未登录");
        } else {
            Glide.with(this).load("https://diycode.b0.upaiyun.com/user/avatar/5404_1502159094.jpg").apply(options).into(imageView);
            tvUserName.setText(PreferenceUtils.getString(this, "userName"));
            tvZhuye.setText(PreferenceUtils.getString(this, "zhuye"));
        }
        toolbar.setLogo(R.mipmap.ic_logo);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        mTabLayout.addTab(mTabLayout.newTab().setText("社区"));
        mTabLayout.addTab(mTabLayout.newTab().setText("项目"));
        mTabLayout.addTab(mTabLayout.newTab().setText("酷站"));

        fragmentList = new ArrayList<>();
//        fragmentList.add(new TopicFragment());
        fragmentList.add(new NewTopicFragment());
        fragmentList.add(new ProjectFragment());
        fragmentList.add(new NewSitesFragment());
//        fragmentList.add(new SitesFragment());

        PageAdapter pageAdapter = new PageAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(pageAdapter);
        mViewPager.setOffscreenPageLimit(3);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawerLayout.addDrawerListener(drawerToggle);//设置侧滑菜单的监听
        drawerToggle.syncState();//将左上角的图标和侧滑监听进行联动 达到动画效果显示
    }

    private void initEvent() {
        imageView.setOnClickListener(this);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);
        fab.setOnClickListener(this);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_mytiezi:
                ToastUtils.showToast(this, "帖子");
                break;
            case R.id.nav_myshoucang:
                ToastUtils.showToast(this, "收藏");
                if (cacheUtil.getToken() == null) {
                    startActivity(new Intent(this, LoginActivity.class));
                } else {
                    startActivity(new Intent(this, MyTopicActivity.class));
                }
                break;
            case R.id.nav_about:
                ToastUtils.showToast(this, "关于");
                break;
            case R.id.nav_setting:
                ToastUtils.showToast(this, "设置");
                break;
        }
        return true;
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
            case R.id.img_user:
                if (cacheUtil.getToken() == null) {
                    startActivityForResult(new Intent(this, LoginActivity.class), 1);
                } else {
                    ToastUtils.showToast(this, PreferenceUtils.getString(this, "userName"));
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            UserDetail userDetail = (UserDetail) data.getExtras().getSerializable("user");
            assert userDetail != null;

            Glide.with(this).load("https://diycode.b0.upaiyun.com/user/avatar/5404_1502159094.jpg").apply(options).into(imageView);
            tvUserName.setText(userDetail.getName());
            tvZhuye.setText(userDetail.getWebsite());
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuUtil.setIconVisible(menu, true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_notification) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    private long mExitTime;
//
//    // 按两次返回键 退出
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            if ((System.currentTimeMillis() - mExitTime) > 2000) {
//                ToastUtils.showToast(MainActivity.this, "再按一次退出程序");
//                mExitTime = System.currentTimeMillis();
//            } else {
//                finish();
//            }
//        }
//        return super.onKeyDown(keyCode, event);
//    }
}
