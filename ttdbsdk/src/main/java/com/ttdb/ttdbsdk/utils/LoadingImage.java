package com.ttdb.ttdbsdk.utils;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import com.ttdb.ttdbsdk.R;

import static android.os.Build.VERSION_CODES.N;

public class

LoadingImage {
    private Dialog progressDialog;
    private Context context;
    public LoadingImage(Context context) {
        this.context=context;
    }

    @SuppressWarnings("ConstantConditions")
    public void buildProgressDialog(String msgStr) {
        if (progressDialog == null) {
            progressDialog = new Dialog(context, R.style.progress_dialog);
        }
        progressDialog.setContentView(R.layout.ttdb_activity_animal);
       progressDialog.setCancelable(true);

        progressDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent);
        TextView msg = (TextView) progressDialog
                .findViewById(R.id.id_tv_loadingmsg);
        msg.setText(msgStr);
        progressDialog.show();
    }

    public void cancelProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

}
