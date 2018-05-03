package com.wenjie.diycode;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.gcssloop.diycode_sdk.api.user.bean.UserDetail;
import com.gcssloop.diycode_sdk.utils.CacheUtil;
import com.wenjie.diycode.app.DiycodeApplication;
import com.wenjie.diycode.mvp.presenter.login.LoginPresenter;
import com.wenjie.diycode.mvp.view.login.ILoginView;
import com.wenjie.diycode.mvp.view.login.LoginActivity;
import com.wenjie.diycode.utils.DataCache;
import com.wenjie.diycode.utils.ToastUtils;

/**
 * @ProjectName: StudyDemo
 * @PackageName: com.winfo.wenjie.slidingmenu
 * @FileName: com.winfo.wenjie.slidingmenu.LeftSlidingMenuFragment.java
 * @Author: wenjie
 * @Date: 2017-07-24 16:42
 * @Description:
 * @Version:
 */
public class LeftSlidingMenuFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener,ILoginView {

    private NavigationView navigationView;
    private ImageView imgUser;
    private TextView username;
    private DataCache mCache;
    private LoginPresenter loginPresenter;
    private CacheUtil cacheUtil;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_left_fragment, container, false);
        loginPresenter = new LoginPresenter(this);
        navigationView = (NavigationView) view.findViewById(R.id.navigation_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);
        imgUser = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.img_user);
        username = (TextView) navigationView.getHeaderView(0).findViewById(R.id.username);
        mCache = new DataCache(getActivity());

        cacheUtil = new CacheUtil(DiycodeApplication.getContext());
        if(cacheUtil.getToken() != null){//登陆了
            UserDetail me = mCache.getMe();
            if (me == null) {
                loginPresenter.getMe();
            }else{
                username.setText(me.getName());
                Glide.with(getActivity()).load("https://diycode.b0.upaiyun.com/user/large_avatar/5463_1502539773.jpg").apply(RequestOptions.bitmapTransform(new RoundedCorners(3))).into(imgUser);
                imgUser.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //去个人中心
                        ToastUtils.showToast(getActivity() , "个人中心");
                    }
                });
            }

        }else{//未登录
            mCache.removeMe();
            username.setText("(未登录)");
            imgUser.setImageResource(R.mipmap.ic_launcher);
            imgUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent, 1);
                }
            });
        }
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && data != null) {
            Bundle bundle = data.getExtras();
            UserDetail userDetail = (UserDetail) bundle.getSerializable("user");
            assert userDetail != null;
            mCache.saveMe(userDetail);
            username.setText(userDetail.getName());
            Glide.with(getActivity()).load("https://diycode.b0.upaiyun.com/user/large_avatar/5463_1502539773.jpg").apply(RequestOptions.bitmapTransform(new RoundedCorners(3))).into(imgUser);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_mytiezi:
                ToastUtils.showToast(getActivity(), "帖子");
                break;
            case R.id.nav_myshoucang:
                ToastUtils.showToast(getActivity(), "收藏");
                break;
            case R.id.nav_about:
                ToastUtils.showToast(getActivity(), "关于");
                break;
            case R.id.nav_setting:
                cacheUtil.clearToken();
                ToastUtils.showToast(getActivity(), "退出登陆");
                CacheUtil cacheUtil = new CacheUtil(DiycodeApplication.getContext());
                cacheUtil.clearToken();
                imgUser.setImageResource(R.mipmap.ic_launcher);
                username.setText("未登录");
                break;
        }
        return true;
    }

    @Override
    public String getUserName() {
        return null;
    }

    @Override
    public String getPwd() {
        return null;
    }

    @Override
    public void loginSuccess(UserDetail userDetail) {
        mCache.saveMe(userDetail);
        username.setText(userDetail.getName());
        Glide.with(getActivity()).load("https://diycode.b0.upaiyun.com/user/large_avatar/5463_1502539773.jpg").apply(RequestOptions.bitmapTransform(new RoundedCorners(3))).into(imgUser);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }


    public interface OnShowContent {
        void shwoContent();
    }

    public OnShowContent onShowContent;

    public void setOnShowContent(OnShowContent onShowContent) {
        this.onShowContent = onShowContent;
    }
}
