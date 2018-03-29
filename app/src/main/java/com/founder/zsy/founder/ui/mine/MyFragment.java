package com.founder.zsy.founder.ui.mine;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.founder.zsy.founder.R;
import com.founder.zsy.founder.adapter.RecyclerAdapter;
import com.founder.zsy.founder.bean.LoginBean;
import com.founder.zsy.founder.ui.LoginActivity;
import com.founder.zsy.founder.util.SharedPreferencesUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends Fragment implements MineContract.View {


    @BindView(R.id.img)
    CircleImageView img;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    Unbinder unbinder;
    private RecyclerAdapter adapter;
    private List<String> list;

    public MyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        initData();
        return view;
    }

    private void initData() {
        if (SharedPreferencesUtils.contains(getContext(), "nick")) {
            name.setText((String) SharedPreferencesUtils.get(getContext(), "nick", ""));
            SharedPreferencesUtils.put(getContext(),"type","1");
        }else {
            SharedPreferencesUtils.put(getContext(),"type","0");
        }

        list = new ArrayList<>();
        adapter = new RecyclerAdapter(list);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        unbinder.unbind();
    }

    @OnClick(R.id.parent)
    public void onViewClicked() {
        if (name.getText().toString().equals(getString(R.string.name))) {
            startActivity(new Intent(getContext(), LoginActivity.class));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(String data) {
        list.add(data);
        adapter.notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread2(LoginBean bean) {
        if (bean.getKey().equals("success")) {
            name.setText((String) SharedPreferencesUtils.get(getContext(), "nick", ""));
            SharedPreferencesUtils.put(getContext(),"type","1");
        }
    }

    @Override
    public void uploadSuccess() {

    }

    @Override
    public void onError() {

    }
}
