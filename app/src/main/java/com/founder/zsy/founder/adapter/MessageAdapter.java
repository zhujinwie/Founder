package com.founder.zsy.founder.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.founder.zsy.founder.R;
import com.founder.zsy.founder.bean.LocationBean;

import java.util.List;

public class MessageAdapter extends BaseQuickAdapter<LocationBean,BaseViewHolder> {

    public MessageAdapter(@Nullable List<LocationBean> data) {
        super(R.layout.item_message, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LocationBean item) {


        helper.setText(R.id.time_tv,"时间:   "+ (TextUtils.isEmpty(item.getTime())?"####":item.getTime()));
        helper.setText(R.id.la_tv, "纬度:   "+(TextUtils.isEmpty(item.getLa())?"####":item.getLa()));
        helper.setText(R.id.ln_tv,"经度：  "+(TextUtils.isEmpty(item.getLn())?"####":item.getLn()));
        helper.setText(R.id.desc_tv,"位置描述：  "+(TextUtils.isEmpty(item.getPoi())?"未知":item.getPoi()));
        switch(item.getType()){

            case 0:
                helper.setText(R.id.type_tv,"定位类型:  "+"GPS");
                break;
            case 1:
                helper.setText(R.id.type_tv,"定位类型： "+"网络定位");
                break;
            case 2:
                helper.setText(R.id.type_tv,"定位类型:   "+"离线定位(离线定位一样有效)");
            default:
                helper.setText(R.id.type_tv,"定位类型:   "+"定位失败！数据不可用，请检查GPS和网络");
                break;
        }
    }
}
