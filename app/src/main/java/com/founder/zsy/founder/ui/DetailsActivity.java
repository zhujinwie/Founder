package com.founder.zsy.founder.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.founder.zsy.founder.R;
import com.founder.zsy.founder.adapter.RecyclerAdapter;
import com.founder.zsy.founder.bean.TotalEntity;
import com.founder.zsy.founder.bean.base.PolicyEntity;
import com.founder.zsy.founder.ui.homepage.HomeContract;
import com.founder.zsy.founder.ui.homepage.HomePresenter;
import com.founder.zsy.founder.util.StatusBarCompat;
import com.founder.zsy.founder.widget.MyLoadMoreView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DetailsActivity extends AppCompatActivity implements HomeContract.View{

    @BindView(R.id.toolbar2)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @BindView(R.id.srl)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.rcv)
    RecyclerView recyclerView;

    public static final String EXTRA_NAME="name";
    public static final String EXTRA_CODE="code";
    private int page=1;
    private int pageSize=1;
    private String name,policyNum;
    private HomePresenter presenter;
    private Unbinder binder;
    private RecyclerAdapter adapter;
    private View emptyView,errorView;
    private boolean isRefreshing,isLoadMoreFailed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        StatusBarCompat.compat(this, getResources().getColor(R.color.bgred));
        binder=ButterKnife.bind(this);
        toolbarTitle.setText("详情");
        toolbar.setNavigationIcon(R.mipmap.left_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        presenter=new HomePresenter();
        presenter.attachView(this);

        adapter=new RecyclerAdapter(new ArrayList<PolicyEntity>());

        setUpList();
        initDataAndView();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binder.unbind();
        presenter.detachView();
    }

    //初始化 刷新加载 设置
    private void setUpList(){

        LayoutInflater inflater=LayoutInflater.from(this);

        ViewGroup group= (ViewGroup) recyclerView.getParent();

        emptyView=inflater.inflate(R.layout.item_empty_view,group,false);
        TextView emptyTv=emptyView.findViewById(R.id.msg_tv_empty);
        emptyTv.setText(getEmptyMsg());

        errorView=inflater.inflate(R.layout.item_error_view,group,false);
        TextView errorTv=errorView.findViewById(R.id.msg_tv_error);
        errorTv.setText(getErrorMsg(""));
        TextView retryTv=errorView.findViewById(R.id.retry_tv_error);
        retryTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshLayout.setRefreshing(true);
                refreshData(true);
            }
        });

        refreshLayout.setColorSchemeResources(R.color.purple_500,R.color.blue_500,R.color.pink_500);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData(true);
            }
        });

        adapter.setLoadMoreView(new MyLoadMoreView());
        adapter.setPreLoadNumber(10);//预加载数
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                refreshData(false);
            }
        },recyclerView);

        refreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
                refreshData(true);
            }
        },200);

        adapter.openLoadAnimation(1);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);



    }


    //初始化数据
    private void initDataAndView(){

        Intent intent=getIntent();
        policyNum=intent.getStringExtra(EXTRA_CODE);
        name=intent.getStringExtra(EXTRA_NAME);

    }

    //数据更新逻辑
    private void refreshData(boolean isRefresh){
        isRefreshing=isRefresh;
        if(isRefresh){
            //初次请求
            page=1;
        }else{
            if(isLoadMoreFailed) isLoadMoreFailed=false;
            page++;
        }
        Map<String,String> params=new HashMap<>();
        if(policyNum !=null && !policyNum.equals("-1")){
            params.put("insureNo", policyNum);
            presenter.getPolicy(params);
        }else if(name != null && !name.equals("-1")){
            params.put("page",page+"");
            params.put("page_size",pageSize+"");
            params.put("insurerName",name);
            presenter.getPolicy02(params);
        }
    }

    //加载判定
    private void setData(List<PolicyEntity> datas){

        Log.d("Test","datas.length="+datas.size());
        refreshLayout.setRefreshing(false);
        adapter.isUseEmpty(true);
        if(isRefreshing){
            //首次刷新
            adapter.setNewData(datas);
            //首次刷新判断是否结束
            if(datas == null || datas.size()==0){
                //无内容
                adapter.setEmptyView(emptyView);
                adapter.notifyDataSetChanged();
            }else if(datas.size() < pageSize){
                //数据到底了
                adapter.loadMoreEnd();
            }else {
                //加载完毕
                adapter.loadMoreComplete();
            }
        }else{
            //加载更多
            if(datas == null || datas.size() == 0){
                adapter.loadMoreEnd();
            }else{
                adapter.addData(datas);
                adapter.loadMoreComplete();
            }
        }


    }


    @Override
    public void getPolicySuccess(TotalEntity totalEntity) {
        Log.d("Test","getPolicySuccess -- > "+totalEntity);
        if(totalEntity == null || totalEntity.getStatus() == 1){
            Log.d("Test","获得空数据。。");
            showError(0);
        }
        else if(totalEntity.getStatus() == 2){
            Log.d("Test","错误的请求!");
            showError(1);
        }
        else{
            List<PolicyEntity> datas=totalEntity.getBus();

            if(datas == null || datas.size() ==0){
                Log.d("Test","没有更多数据了!");
                adapter.loadMoreEnd();
                return;
            }
            setData(datas);
        }
    }

    @Override
    public void showError(int code) {

        if(isRefreshing){

            adapter.setEmptyView(errorView);
            adapter.notifyDataSetChanged();
        }else{

            adapter.loadMoreFail();
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void onComplete() {
        refreshLayout.setRefreshing(false);
    }

    public String getEmptyMsg(){

        return "没有更多保单信息了！";
    }

    public String getErrorMsg(String str){

        return str;
    }




}
