package com.wenjie.diycode.mvp.view.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gcssloop.diycode_sdk.api.user.bean.UserDetail;
import com.gcssloop.diycode_sdk.utils.CacheUtil;
import com.wenjie.diycode.R;
import com.wenjie.diycode.mvp.presenter.login.LoginPresenter;
import com.wenjie.diycode.utils.PreferenceUtils;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, ILoginView {

    private Toolbar toolbar;
    private Button btnLogin;
    private EditText etUserName;
    private EditText etpwd;
    private LoginPresenter loginPresenter;
    private CacheUtil cacheUtil;
    private TextView register;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        dialog = new ProgressDialog(this);
        dialog.setMessage("登陆中...");
        btnLogin = (Button) findViewById(R.id.login);
        etUserName = (EditText) findViewById(R.id.username);
        etpwd = (EditText) findViewById(R.id.password);
        register = (TextView) findViewById(R.id.sign_up);


        btnLogin.setOnClickListener(this);
        loginPresenter = new LoginPresenter(this);
        register.setOnClickListener(this);
        cacheUtil = new CacheUtil(this.getApplicationContext());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                loginPresenter.login();
                break;
            case R.id.sign_up:
//                loginPresenter.getMe();
                break;
        }
    }

    @Override
    public String getUserName() {
        return etUserName.getText().toString().trim();
    }

    @Override
    public String getPwd() {
        return etpwd.getText().toString().trim();
    }

    @Override
    public void loginSuccess(UserDetail userDetail) {
        //登陆成功
//        ToastUtils.showToast(this, userDetail.toString());
        PreferenceUtils.setString(this, "userName", userDetail.getName());
        PreferenceUtils.setString(this, "url", userDetail.getAvatar_url());
        PreferenceUtils.setString(this, "company", userDetail.getCompany());
        PreferenceUtils.setString(this, "level", userDetail.getLevel());
        PreferenceUtils.setString(this, "zhuye", userDetail.getWebsite());

        Intent intent = new Intent();
        intent.putExtra("user", userDetail);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void showLoading() {
        dialog.show();
    }

    @Override
    public void hideLoading() {
        dialog.dismiss();
    }
}
