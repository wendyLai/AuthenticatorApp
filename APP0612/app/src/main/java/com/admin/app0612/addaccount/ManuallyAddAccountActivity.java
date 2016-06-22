package com.admin.app0612.addaccount;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.admin.app0612.MainActivity;
import com.admin.app0612.MyBottomBtnsActivity;
import com.admin.app0612.R;
import com.admin.app0612.UserDBManager;
import com.admin.app0612.UserEntity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2016/6/13.
 */
public class ManuallyAddAccountActivity extends MyBottomBtnsActivity implements AdapterView.OnItemSelectedListener {
    private Spinner mSpinner;
    private ArrayAdapter<String> mSpinnerAdapter;
    private String[] mArrSpinnerOptions;
    private EditText mUserInput;
    private EditText mPinInput;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mySetPageContentView(R.layout.manually_add_account_page_content);
        mySetActionBarTitle(R.string.action_bar_title_manually_add_account);
        mySetNextBtnText(R.string.button_add);

        mSpinner = (Spinner) findViewById(R.id.add_account_dropdown);
        mUserInput = (EditText) findViewById(R.id.edit_user_name);
        mPinInput = (EditText) findViewById(R.id.edit_pin);

        //添加下拉菜单选项
        addOption();

        //自动弹出软键盘
        autoPopSoftKeyBoard(mUserInput);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        //Log.i("info", "当前的选择是" + mArrSpinnerOptions[i]);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    /**
     * 点击右边按钮事件
     * -既点击添加按钮事件
     */
    @Override
    protected void myOnNextPageBtnPressed() {
        super.myOnNextPageBtnPressed();
        //判断输入框是否有值
        //判断输入框是否正确(数据给后台由后台判断)
        String mUserName = mUserInput.getText().toString();
        String mPin = mPinInput.getText().toString();

        if (!mUserName.equals("") && !mPin.equals("")) {

            //将input中的数据转化为UserEntity对象
            UserEntity newUser = new UserEntity();
            newUser.setusername(mUserName);
            newUser.setPin(mPin);

            //将数据添加进usertb中
            UserDBManager manager = new UserDBManager(this);
            manager.add(newUser);
            manager.closeDB();

            //提示成功添加
            Toast.makeText(this,"成功添加用户",Toast.LENGTH_SHORT).show();

            //跳转activity
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }else {
            //提示添加失败
            Toast.makeText(this,"请重新输入",Toast.LENGTH_SHORT).show();
        }


    }

    /**
     * 自动弹出软键盘
     */
    private void autoPopSoftKeyBoard(final EditText inputView) {
        //延迟0.6秒弹出软键盘，以确保界面已加载完全
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                inputView.setFocusableInTouchMode(true);
                inputView.requestFocus();

                InputMethodManager inputMethodManager =
                        (InputMethodManager) inputView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.showSoftInput(inputView, 0);
            }
        }, 600);
    }

    /**
     * 添加下拉菜单选项
     */
    private void addOption() {
        mArrSpinnerOptions = new String[]{
                getResources().getString(R.string.add_account_dropdown_time),
                getResources().getString(R.string.add_account_dropdown_counter)};
        mSpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mArrSpinnerOptions);
        mSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(mSpinnerAdapter);
        mSpinner.setOnItemSelectedListener(this);
    }
}
