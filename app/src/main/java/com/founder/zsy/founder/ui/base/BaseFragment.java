package com.founder.zsy.founder.ui.base;

import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.founder.zsy.founder.R;

public class BaseFragment extends Fragment {

    private Dialog progressDialog;

    protected void showDialog(String content){

        if(progressDialog == null){
            progressDialog = new Dialog(getActivity(), R.style.progress_dialog);
        }

        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView msg = (TextView) progressDialog.findViewById(R.id.id_tv_loading);
        msg.setText(content);
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    protected void closeDialog(){
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }


}
