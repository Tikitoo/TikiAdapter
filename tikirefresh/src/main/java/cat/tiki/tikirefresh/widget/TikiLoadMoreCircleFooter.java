package cat.tiki.tikirefresh.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import cat.tiki.tikirefresh.R;


/**
 * Created by Tikitoo on 2019-11-07.
 */

public class TikiLoadMoreCircleFooter extends LinearLayout {

    protected View rootView;
    protected ProgressBar mLoadmoreProgressBarView;
    protected RelativeLayout mLoadmoreLayout;

    public TikiLoadMoreCircleFooter(Context context) {
        this(context, null);
    }

    public TikiLoadMoreCircleFooter(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TikiLoadMoreCircleFooter(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.lib_widget_zz_circle_loadmore_layout, this);
        initView(this);
    }

    private void initView(View rootView) {
        mLoadmoreProgressBarView = (ProgressBar) rootView.findViewById(R.id.lib_widget_zz_circle_loadmore_progress_bar_view);
        mLoadmoreLayout = (RelativeLayout) rootView.findViewById(R.id.lib_widget_zz_circle_loadmore_layout);
        setGravity(Gravity.CENTER);
    }

    public void startRefreshing() {
        if(mLoadmoreProgressBarView != null) {
            mLoadmoreProgressBarView.setVisibility(VISIBLE);
        }
    }



    public void show() {
        startRefreshing();
    }

    public void stopRefreshing() {
        if (mLoadmoreProgressBarView != null) {
            mLoadmoreProgressBarView.setVisibility(INVISIBLE);
        }
    }

    public void dismiss() {
        stopRefreshing();
    }
}
