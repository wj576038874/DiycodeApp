package com.wenjie.diycode.mvp.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.just.library.IWebLayout;
import com.wenjie.diycode.R;
import com.wenjie.diycode.utils.MenuUtil;


public class WebView2Activity extends AppCompatActivity {

    private Toolbar mToolbar;
    private String url;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view2);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        assert bundle != null;
        url = bundle.getString("url");
        mToolbar = (Toolbar) this.findViewById(R.id.toolbar);
        webView = (WebView) findViewById(R.id.webview);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        webView.loadUrl(url);
    }


    private WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            //do you  work
            Log.i("Info", "BaseWebActivity onPageStarted");
        }
    };

    private WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            //do you work
//            Log.i("Info","progress:"+newProgress);
        }
    };


    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        return super.onKeyDown(keyCode, event);
    }

    private class WebLayout implements IWebLayout {

        private Activity mActivity;
        private WebView mWebView = null;

        public WebLayout(Activity activity) {
            this.mActivity = activity;
//            mWebView = (WebView) findViewById(R.id.webView);
        }

        @NonNull
        @Override
        public ViewGroup getLayout() {
            return null;
        }

        @Nullable
        @Override
        public WebView getWeb() {
            return null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_web, menu);
        MenuUtil.setIconVisible(menu, true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_share) {
            Intent share_intent = new Intent();
            share_intent.setAction(Intent.ACTION_SEND);//设置分享行为
            share_intent.setType("text/plain");//设置分享内容的类型
            share_intent.putExtra(Intent.EXTRA_SUBJECT, "");//添加分享内容标题
            share_intent.putExtra(Intent.EXTRA_TEXT, url);//添加分享内容
            //创建分享的Dialog
            share_intent = Intent.createChooser(share_intent, "请选择分享方式");
            startActivity(share_intent);
            return true;
        } else if (id == R.id.action_browser) {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri uri = Uri.parse(url);
            intent.setData(uri);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
