package com.founder.zsy.founder.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.founder.zsy.founder.R;
import com.founder.zsy.founder.util.StatusBarCompat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {
    @BindView(R.id.toolbar2)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        StatusBarCompat.compat(this, getResources().getColor(R.color.bgred));
        ButterKnife.bind(this);
        toolbarTitle.setText("详情");
        toolbar.setNavigationIcon(R.mipmap.left_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
