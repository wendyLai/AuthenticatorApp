package com.admin.app0612;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.admin.app0612.setting.SettingFragment;

/**
 * Created by Administrator on 2016/6/12.
 */
public class SettingActivity extends MyBasicActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity);
        mySetActionBarTitle(R.string.action_bar_title_setting);

        //加载fragment
        addFragmentView();
    }

    private void addFragmentView() {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        SettingFragment settingFrag = new SettingFragment(R.xml.preference);
        transaction.replace(R.id.setting_content,settingFrag);
        transaction.commit();
    }
}
