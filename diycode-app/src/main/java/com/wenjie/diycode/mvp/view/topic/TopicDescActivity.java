package com.wenjie.diycode.mvp.view.topic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.gcssloop.diycode_sdk.api.topic.bean.TopicContent;
import com.wenjie.diycode.R;
import com.wenjie.diycode.mvp.presenter.topic.TopicDescPresenter;
import com.wenjie.diycode.utils.TimeUtil;

import org.sufficientlysecure.htmltextview.HtmlTextView;

public class TopicDescActivity extends AppCompatActivity implements ITopicDesc {

    private int id;
    private TopicDescPresenter topicDescPresenter;
    private ImageView userimg;
    private TextView title, username, time , mTitleTextView;
    private HtmlTextView htmlTextView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_desc);
        topicDescPresenter = new TopicDescPresenter(this);

        toolbar = (Toolbar) findViewById(R.id.topic_toolbar);
        toolbar.setTitle("");
        mTitleTextView = (TextView) this.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        assert getSupportActionBar()!= null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        htmlTextView = (HtmlTextView) findViewById(R.id.html_text);
        userimg = (ImageView) findViewById(R.id.topic_img);
        username = (TextView) findViewById(R.id.topic_username);
        time = (TextView) findViewById(R.id.topic_time);
        title = (TextView) findViewById(R.id.topic_title);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        assert bundle != null;
        id = bundle.getInt("id");
        topicDescPresenter.getTopicContent();
    }

    @Override
    public void setData(TopicContent topicContent) {
        long updateTime = TimeUtil.string2time(topicContent.getUpdated_at());
        time.setText(TimeUtil.getTimeElapse(updateTime));
        username.setText(topicContent.getUser().getLogin() + "(" + topicContent.getUser().getName() + ")");
        Glide.with(this).load(topicContent.getUser().getAvatar_url()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(userimg);
        title.setText(topicContent.getTitle());
        htmlTextView.setHtml(topicContent.getBody_html());
    }

    @Override
    public int getId() {
        return id;
    }
}
