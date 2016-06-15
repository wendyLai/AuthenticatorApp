package com.admin.app0612.setting;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.admin.app0612.MyBasicActivity;
import com.admin.app0612.R;

/**
 * Created by Administrator on 2016/6/13.
 */
public class TimeSyncActivity extends MyBasicActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity);
        mySetActionBarTitle(R.string.action_bar_title_setting_timesync);

        //加载fragment
        addFragmentView();
    }

    private void addFragmentView() {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        SettingFragment settingFrag = new SettingFragment(R.xml.preference_timeasync);
        transaction.replace(R.id.setting_content,settingFrag);
        transaction.commit();
    }
}
