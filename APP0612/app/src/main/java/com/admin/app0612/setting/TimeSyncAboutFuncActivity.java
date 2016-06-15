package com.admin.app0612.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.admin.app0612.MyBasicActivity;
import com.admin.app0612.R;

/**
 * Created by Administrator on 2016/6/14.
 */
public class TimeSyncAboutFuncActivity extends MyBasicActivity{
    private Button mBtnOK;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mySetActionBarDisabled(true);

        setContentView(R.layout.timesync_about_function_activity);

        mBtnOK= (Button)findViewById(R.id.btn_ok);
        mBtnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
