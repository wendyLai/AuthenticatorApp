package com.admin.app0612.setting;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by Administrator on 2016/6/12.
 */
public class SettingFragment extends PreferenceFragment {

    private int resId;

    public SettingFragment() {
        this.resId = 0;
    }

    public SettingFragment(int resId) {
        this.resId = resId;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(resId);
    }

}
