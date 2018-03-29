package com.founder.zsy.founder.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.founder.zsy.founder.R;

import java.util.List;

/**
 * Created by zhaoshengyang on 2017/10/17.
 */

public class RecyclerAdapter extends BaseQuickAdapter<String,BaseViewHolder> {
    public RecyclerAdapter( @Nullable List<String> data) {
        super(R.layout.recycler_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.text,item);
    }
}
