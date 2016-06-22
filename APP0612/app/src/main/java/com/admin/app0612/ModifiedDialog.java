package com.admin.app0612;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;

/**
 * Created by Administrator on 2016/5/13.
 */
public class ModifiedDialog {

    private Context mContext;
    private final String mOldUserName;
    private AlertDialog mDialog;


    public ModifiedDialog(Context context, String oldUserName) {
        this.mContext = context;
        this.mOldUserName = oldUserName;
    }

    /**
     * 显示对话框
     */
    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        builder.setTitle("重命名");
        final EditText input = new EditText(mContext);
        input.setText(mOldUserName);
        builder.setView(input);

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String newUserName = input.getText().toString();
                //更新用户数据列表
                updateUserList(newUserName);

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        mDialog = builder.create();
        mDialog.show();
}

    /**
     * 更新用户列表
     */
    public void updateUserList(String newUserName) {

    }
}
