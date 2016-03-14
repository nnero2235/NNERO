package com.nnero.nnero.views.state;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.nnero.nnero.R;

/**
 * Created by nnero on 16/3/11.
 *
 * 一种包裹布局 ：用于处理 网络请求页面 要显示loading empty error界面的情况
 *
 * 使用该布局包裹  只能有一个子View
 */
public class ContentLayout extends FrameLayout {

    public static final int LAYOUT_LOADING = 0;
    public static final int LAYOUT_CONTENT = 1;
    public static final int LAYOUT_NO_DATA = 2;
    public static final int LAYOUT_NETWORK_ERROR = 3; //包括没网

    private View mStateView;//状态View
    private View mContentView;//内容View

    private View mLoadingView;
    private LoadingView mRealLoadingView;
    private View mNoDataView;
    private View mNoNetworkView;
    private View mNoNetworkRefreshView;

    private OnNoNetworkRefreshListener mOnNetworkRefreshListener;

    public ContentLayout(Context context) {
        super(context);
        init();
    }

    public ContentLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        mLoadingView = LayoutInflater.from(getContext()).inflate(R.layout.base_progress_round_view,null);
        mNoDataView = LayoutInflater.from(getContext()).inflate(R.layout.base_no_data_view,null);
        mNoNetworkView = LayoutInflater.from(getContext()).inflate(R.layout.base_no_network_view,null);
        mNoNetworkRefreshView = mNoNetworkView.findViewById(R.id.no_network_refresh);
        mRealLoadingView = (LoadingView) mLoadingView.findViewById(R.id.progress_round);

        mNoNetworkRefreshView.setOnClickListener(mRefreshListener);
        mRealLoadingView.hide();//防止还没显示 就开始不断绘制了
    }

    /**
     * 设置layout层级 content始终在第一层 layout最多只有2层
     * 第二层 可以是 loading nodata 是动态add的 隐藏的时候 removeview
     * @param level
     */
    public void setLayoutLevel(int level){
        if(mStateView != null)
            removeViewAt(1);
        switch (level){
        case LAYOUT_LOADING:
            mStateView = mLoadingView;
            addView(mStateView);
            mRealLoadingView.show();
            mContentView.setVisibility(View.GONE);
            break;
        case LAYOUT_CONTENT:
            mStateView = null;
            mRealLoadingView.hide();
            mContentView.setVisibility(View.VISIBLE);
            break;
        case LAYOUT_NO_DATA:
            mStateView = mNoDataView;
            addView(mStateView);
            mRealLoadingView.hide();
            mNoDataView.setVisibility(View.VISIBLE);
            mContentView.setVisibility(View.GONE);
            break;
        case LAYOUT_NETWORK_ERROR:
            mStateView = mNoNetworkView;
            addView(mStateView);
            mRealLoadingView.hide();
            mNoNetworkView.setVisibility(View.VISIBLE);
            mContentView.setVisibility(View.GONE);
            break;
        }
    }

    public void setOnNoNetworkListener(OnNoNetworkRefreshListener l){
        this.mOnNetworkRefreshListener = l;
    }

    public void setLoadingView(LoadingView v){
        mRealLoadingView = v;
    }

    public void setNoDataView(View v){
        mNoDataView = v;
    }

    public void setNoNetworkView(View v){
        mNoNetworkView = v;
    }

    /**
     * 该方法必须调用 否则 无法获取contentView
     * @param v
     */
    public void setContentView(View v){
        mContentView = v;
    }

    private OnClickListener mRefreshListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if(mOnNetworkRefreshListener != null){
                mOnNetworkRefreshListener.onRefresh();
            }
        }
    };

    public interface OnNoNetworkRefreshListener{
        void onRefresh();
    }
}
