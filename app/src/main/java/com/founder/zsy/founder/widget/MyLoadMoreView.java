package com.founder.zsy.founder.widget;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.founder.zsy.founder.R;

public class MyLoadMoreView extends LoadMoreView {
    @Override
    public int getLayoutId() {
        return R.layout.load_more_view;
    }

    @Override
    public void convert(BaseViewHolder holder) {
        super.convert(holder);
    }

    @Override
    protected int getLoadingViewId() {
        return R.id.lay_load_more_loading_view;
    }

    @Override
    protected int getLoadFailViewId() {
        return R.id.lay_load_more_load_fail_view;
    }

    @Override
    protected int getLoadEndViewId() {
        return R.id.tv_load_more_no_more;
    }

    @Override
    public boolean isLoadEndGone() {
        return false;
    }
}
