package com.founder.zsy.founder.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.founder.zsy.founder.R;
import com.founder.zsy.founder.bean.base.PolicyEntity;

import java.util.List;

/**
 * Created by zhaoshengyang on 2017/10/17.
 */

public class RecyclerAdapter extends BaseQuickAdapter<PolicyEntity,BaseViewHolder> {
    public RecyclerAdapter( @Nullable List<PolicyEntity> data) {
        super(R.layout.item_policy, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PolicyEntity item) {

        helper.setText(R.id.item_number,item.getInsureNum());
        helper.setText(R.id.item_policy_type,item.getInstypeName());
        helper.setText(R.id.item_insurer_name,item.getInsurerName());
        //helper.setText(R.id.item_insurer_id,item.getInsurerID());
        helper.setText(R.id.item_insured_name,item.getInsuredName());
        //helper.setText(R.id.item_insured_id,item.getInsuredId());
        helper.setText(R.id.item_agent_name,item.getAgentName());
        helper.setText(R.id.item_pay_to_date,item.getPayToDate());
        helper.setText(R.id.item_premium,item.getPremiun());
        helper.setText(R.id.item_account,item.getRenewalCard());
        helper.setText(R.id.item_last_time,item.getLastPayResult());
        helper.setText(R.id.item_last_time_reason,item.getFailReason());

    }
}
