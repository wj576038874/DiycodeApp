package com.wenjie.diycode.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.internal.ProgressDrawable;
import com.scwang.smartrefresh.layout.internal.pathview.PathsView;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.wenjie.diycode.R;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * @ProjectName: Toolbar_drawerLayout
 * @PackageName: com.smartrefreshlayout
 * @FileName: com.smartrefreshlayout.MyClassicsFooter.java
 * @Author: wenjie
 * @Date: 2017-07-21 15:05
 * @Description:
 * @Version:
 */
public class MyClassicsFooter extends LinearLayout implements RefreshFooter {

    public static String REFRESH_FOOTER_PULLUP = "上拉加载更多";
    public static String REFRESH_FOOTER_RELEASE = "释放立即加载";
    public static String REFRESH_FOOTER_LOADING = "正在加载...";
    public static String REFRESH_FOOTER_FINISH = "加载完成";
    public static String REFRESH_FOOTER_ALLLOADED = "—我是有底线的—";

    private TextView mBottomText;
    private ImageView mProgressView;
    private ProgressDrawable mProgressDrawable;
    private SpinnerStyle mSpinnerStyle = SpinnerStyle.Translate;
    private boolean mLoadmoreFinished = false;
    private PathsView mArrowView;

    //<editor-fold desc="LinearLayout">
    public MyClassicsFooter(Context context) {
        super(context);
        this.initView(context, null, 0);
    }

    public MyClassicsFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initView(context, attrs, 0);
    }

    public MyClassicsFooter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        DensityUtil density = new DensityUtil();

        setGravity(Gravity.CENTER);
        setMinimumHeight(density.dip2px(60));

        mProgressDrawable = new ProgressDrawable();
        mProgressDrawable.setColor(0xff666666);
        mProgressView = new ImageView(context);
        mProgressView.setImageDrawable(mProgressDrawable);
        LayoutParams lpPathView = new LayoutParams(density.dip2px(20), density.dip2px(20));
        lpPathView.rightMargin = density.dip2px(20);
        addView(mProgressView, lpPathView);

        mArrowView = new PathsView(context);
        mArrowView.parserColors(0xff666666);
        mArrowView.parserPaths("M20,12l-1.41,-1.41L13,16.17V4h-2v12.17l-5.58,-5.59L4,12l8,8 8,-8z");
        addView(mArrowView, lpPathView);

        mBottomText = new AppCompatTextView(context, attrs, defStyleAttr);
        mBottomText.setTextColor(0xff666666);
        mBottomText.setTextSize(16);
        mBottomText.setText(REFRESH_FOOTER_PULLUP);

        addView(mBottomText, WRAP_CONTENT, WRAP_CONTENT);



        if (!isInEditMode()) {
            mProgressView.setVisibility(GONE);
        }else{
            mArrowView.setVisibility(GONE);
        }

        TypedArray ta = context.obtainStyledAttributes(attrs, com.scwang.smartrefresh.layout.R.styleable.ClassicsFooter);

        mSpinnerStyle = SpinnerStyle.values()[ta.getInt(com.scwang.smartrefresh.layout.R.styleable.ClassicsFooter_srlClassicsSpinnerStyle, mSpinnerStyle.ordinal())];

        if (ta.hasValue(com.scwang.smartrefresh.layout.R.styleable.ClassicsFooter_srlPrimaryColor)) {
            int primaryColor = ta.getColor(com.scwang.smartrefresh.layout.R.styleable.ClassicsFooter_srlPrimaryColor, 0);
            if (ta.hasValue(com.scwang.smartrefresh.layout.R.styleable.ClassicsFooter_srlAccentColor)) {
                int accentColor = ta.getColor(com.scwang.smartrefresh.layout.R.styleable.ClassicsFooter_srlAccentColor, 0);
                setPrimaryColors(primaryColor, accentColor);
            } else {
                setPrimaryColors(primaryColor);
            }
        } else if (ta.hasValue(com.scwang.smartrefresh.layout.R.styleable.ClassicsFooter_srlAccentColor)) {
            int accentColor = ta.getColor(com.scwang.smartrefresh.layout.R.styleable.ClassicsFooter_srlAccentColor, 0);
            setPrimaryColors(0, accentColor);
        }

        ta.recycle();
    }

    //</editor-fold>

    //<editor-fold desc="RefreshFooter">

    @Override
    public void onInitialized(RefreshKernel layout, int height, int extendHeight) {

    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

    }

    @Override
    public void onPullingUp(float percent, int offset, int footerHeight, int extendHeight) {

    }

    @Override
    public void onPullReleasing(float percent, int offset, int headHeight, int extendHeight) {

    }


    @Override
    public void onStartAnimator(RefreshLayout layout, int headHeight, int extendHeight) {
        if (!mLoadmoreFinished) {
            mProgressView.setVisibility(VISIBLE);
            mProgressDrawable.start();
        }
    }

    @Override
    public int onFinish(RefreshLayout layout, boolean success) {
        if (!mLoadmoreFinished) {
            mProgressDrawable.stop();
//            mProgressView.setVisibility(GONE);
            mBottomText.setText(REFRESH_FOOTER_FINISH);
            mProgressView.setImageResource(R.mipmap.finish);
            return 800;
        }
        return 0;
    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    /**
     * ClassicsFooter 没有主题色
     * ClassicsFooter has no primary colors
     */
    @Override
    public void setPrimaryColors(int... colors) {
        if (mSpinnerStyle == SpinnerStyle.FixedBehind) {
            if (colors.length > 1) {
                setBackgroundColor(colors[0]);
                mBottomText.setTextColor(colors[1]);
                mProgressDrawable.setColor(colors[1]);
            } else if (colors.length > 0) {
                setBackgroundColor(colors[0]);
                if (colors[0] == 0xffffffff) {
                    mBottomText.setTextColor(0xff666666);
                    mProgressDrawable.setColor(0xff666666);
                } else {
                    mBottomText.setTextColor(0xffffffff);
                    mProgressDrawable.setColor(0xffffffff);
                }
            }
        }
    }

    /**
     * 设置数据全部加载完成，将不能再次触发加载功能
     */
    @Override
    public boolean setLoadmoreFinished(boolean finished) {
        if (mLoadmoreFinished != finished) {
            mLoadmoreFinished = finished;
            if (finished) {
                mBottomText.setText(REFRESH_FOOTER_ALLLOADED);
            } else {
                mBottomText.setText(REFRESH_FOOTER_PULLUP);
            }
            mProgressDrawable.stop();
            mProgressView.setVisibility(GONE);
        }
        return true;
    }

    @NonNull
    public View getView() {
        return this;
    }

    @Override
    public SpinnerStyle getSpinnerStyle() {
        return mSpinnerStyle;
    }

    @Override
    public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {
        if (!mLoadmoreFinished) {
            switch (newState) {
                case None:
                    restoreRefreshLayoutBackground();
                case PullToUpLoad:
                    mBottomText.setText(REFRESH_FOOTER_PULLUP);
                    mProgressView.setVisibility(GONE);
                    mArrowView.setVisibility(VISIBLE);
                    mArrowView.animate().rotation(0);
                    break;
                case Loading:
                    mBottomText.setText(REFRESH_FOOTER_LOADING);
                    mProgressView.setImageDrawable(mProgressDrawable);
                    mArrowView.setVisibility(GONE);
                    break;
                case ReleaseToLoad:
                    mProgressView.setVisibility(GONE);
                    mBottomText.setText(REFRESH_FOOTER_RELEASE);
                    replaceRefreshLayoutBackground(refreshLayout);
                    mArrowView.animate().rotation(180);
                    break;
            }
        }
    }
    //</editor-fold>

    //<editor-fold desc="private">
    private Runnable restoreRunable;

    private void restoreRefreshLayoutBackground() {
        if (restoreRunable != null) {
            restoreRunable.run();
            restoreRunable = null;
        }
    }

    private void replaceRefreshLayoutBackground(final RefreshLayout refreshLayout) {
        if (restoreRunable == null && mSpinnerStyle == SpinnerStyle.FixedBehind) {
            restoreRunable = new Runnable() {
                Drawable drawable = refreshLayout.getLayout().getBackground();

                @Override
                public void run() {
                    refreshLayout.getLayout().setBackgroundDrawable(drawable);
                }
            };
            refreshLayout.getLayout().setBackgroundDrawable(getBackground());
        }
    }
    //</editor-fold>

    //<editor-fold desc="API">
    public MyClassicsFooter setSpinnerStyle(SpinnerStyle style) {
        this.mSpinnerStyle = style;
        return this;
    }

    public MyClassicsFooter setAccentColor(int accentColor) {
        mBottomText.setTextColor(accentColor);
        mProgressDrawable.setColor(accentColor);
        return this;
    }
    //</editor-fold>
}
