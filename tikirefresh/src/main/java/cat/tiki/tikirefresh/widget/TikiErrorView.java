package cat.tiki.tikirefresh.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Paint;
import android.os.Build;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;

import cat.tiki.tikirefresh.R;


/**
 * Created by Tikitoo on 2019-11-07.
 */
public class TikiErrorView extends LinearLayout {
    protected ImageView libWidgetIvImage;
    protected TextView libWidgetTvText;
    protected View rootView;
    protected TextView mTvLink;
    private Callback callback;
    private TipCallback mTipCallback;
    private boolean isClickGone = true;

    public TikiErrorView(Context context) {
        this(context, null);
    }

    public TikiErrorView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public TikiErrorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TikiErrorView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    private void initView(Context context) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.lib_widget_errorview, this);
        libWidgetIvImage = (ImageView) rootView.findViewById(R.id.lib_widget_iv_image);
        libWidgetTvText = (TextView) rootView.findViewById(R.id.lib_widget_tv_text);
        mTvLink = (TextView) rootView.findViewById(R.id.lib_widget_tv_link);
        TextPaint paint = mTvLink.getPaint();
        if (paint != null) {
            paint.setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
            paint.setAntiAlias(true);//抗锯齿
        }
        setListener();
        setOrientation(LinearLayout.VERTICAL);
    }

    public void setTvLinkStr(CharSequence str) {
        if (mTvLink != null) {
            mTvLink.setText(str);
            if (TextUtils.isEmpty(str)) {
                mTvLink.setVisibility(INVISIBLE);
            } else {
                mTvLink.setVisibility(VISIBLE);
            }
        }
    }

    public void setShowTipView(boolean flag) {
        if (flag) {
            mTvLink.setVisibility(VISIBLE);
            mTvLink.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTipCallback != null) {
                        mTipCallback.onClickListener();
                    }
                }
            });
        } else {
            mTvLink.setVisibility(GONE);
        }
    }

    public void setClickGone(boolean clickGone) {
        isClickGone = clickGone;
    }

    private void setListener() {
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    if (isClickGone) {
                        setVisibility(GONE);
                    }
                    callback.onRetryClickListener();
                }
            }
        });

    }

    public TikiErrorView callback(@Nullable Callback callback) {
        this.callback = callback;
        return this;
    }

    public TikiErrorView callback(@Nullable TipCallback callback) {
        this.mTipCallback = callback;
        return this;
    }



    public TikiErrorView errorImage(@DrawableRes int res) {
        if (libWidgetIvImage != null) {
            libWidgetIvImage.setImageResource(res);
        }
        return this;
    }

    public TikiErrorView errorText(@Nullable String text) {
        if (libWidgetTvText != null) {
            libWidgetTvText.setText(text);
        }
        return this;
    }

    public static interface Callback {

        /**
         * 重试点击事件
         */
        void onRetryClickListener();
    }

    public static interface TipCallback {

        /**
         * 重试点击事件
         */
        void onClickListener();
    }
}
