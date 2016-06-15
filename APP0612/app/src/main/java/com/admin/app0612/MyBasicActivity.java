package com.admin.app0612;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Administrator on 2016/6/13.
 */
public class MyBasicActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //默认显示actionBar
        mySetActionBarDisabled(false);
    }

    /**
     * 是否显示actionBar
     */
    protected void mySetActionBarDisabled(boolean isActionBarGone){
        ActionBar actionBar = getActionBar();
        if (isActionBarGone){
            actionBar.hide();
        }else {
            actionBar.show();
        }
    }

    /**
     * 设置actionBar的title文字
     */
    protected void mySetActionBarTitle(int resId){
        ActionBar actionBar = getActionBar();
        if (actionBar.isShowing()){
            actionBar.setTitle(resId);
        }
    }

}
