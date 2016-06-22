package com.admin.app0612.timesync;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.admin.app0612.R;

/**
 * Created by Administrator on 2016/6/16.
 */
public class SyncNowActivity extends Activity {

    private Dialog mProgressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showInProgressDialog();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    /**
     * 显示同步进度对话框
     */
    private void showInProgressDialog() {
        mProgressDialog = ProgressDialog.show(
                this,
                getString(R.string.timesync_sync_now_progress_dialog_title),
                getString(R.string.timesync_sync_now_progress_dialog_details),
                true,
                true);
        mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                //退出对话框
                dismissInProgressDialog();
                finish();
            }
        });
    }

    /**
     * 退出同步进度对话框
     */
    private void dismissInProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }
}
