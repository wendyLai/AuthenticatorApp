package com.admin.app0612.addaccount;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.admin.app0612.MainActivity;
import com.admin.app0612.MyBottomBtnsActivity;
import com.admin.app0612.R;

/**
 * Created by Administrator on 2016/6/13.
 */
public class ManuallyAddAccountActivity extends MyBottomBtnsActivity implements AdapterView.OnItemSelectedListener {
    private Spinner mSpinner;
    private ArrayAdapter<String> mSpinnerAdapter;
    private String[] mArrSpinnerOptions;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mySetPageContentView(R.layout.manually_add_account_page_content);
        mySetActionBarTitle(R.string.action_bar_title_manually_add_account);
        mySetNextBtnText(R.string.button_add);

        mSpinner = (Spinner) findViewById(R.id.add_account_dropdown);
        mArrSpinnerOptions = new String[]{
                getResources().getString(R.string.add_account_dropdown_time),
                getResources().getString(R.string.add_account_dropdown_counter)};
        mSpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mArrSpinnerOptions);
        mSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(mSpinnerAdapter);
        mSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Log.i("info","当前的选择是"+mArrSpinnerOptions[i]);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    protected void myOnNextPageBtnPressed() {
        super.myOnNextPageBtnPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
