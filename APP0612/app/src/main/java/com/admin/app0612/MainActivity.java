package com.admin.app0612;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends MyBasicActivity implements View.OnClickListener{

    private LinearLayout contentNoAccounts;
    private LinearLayout contentAccountsPresent;
    private List<String> userList = new ArrayList<String>();
    private Button btnHowItWorks;
    private Button btnAddAccount;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        mySetActionBarTitle(R.string.action_bar_title_main);

        initElement();

        /**
         * 当前无用户时
         */
        if (userList.isEmpty()){
            //显示无用户的样式
            contentAccountsPresent.setVisibility(View.GONE);
            //设置按钮监听事件
            btnAddAccount.setOnClickListener(this);
            btnHowItWorks.setOnClickListener(this);
        }


        /**
         * 当前存在用户时
         */
        if (!userList.isEmpty()){
            //显示有用户的样式
            contentNoAccounts.setVisibility(View.GONE);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.add_account:
                addAccount();
                break;
            case R.id.how_it_works:
                howItWorks();
                break;
            case R.id.settings:
                Setting();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * 控件初始化
     */
    private void initElement() {
        contentNoAccounts = (LinearLayout) findViewById(R.id.content_no_accounts);
        contentAccountsPresent = (LinearLayout) findViewById(R.id.content_accounts_present);
        btnHowItWorks=(Button)findViewById(R.id.btn_how_it_works);
        btnAddAccount=(Button)findViewById(R.id.btn_add_account);
    }

    /**
     * 动作----设置
     * 既跳转到设置页面
     */
    private void Setting() {
        Intent intent = new Intent(this,SettingActivity.class);
        startActivity(intent);
    }

    /**
     * 动作----展示如何工作
     * 既跳转到如何工作页面
     */
    private void howItWorks() {
        Intent intent = new Intent(this,HowItWork1Activity.class);
        startActivity(intent);
    }

    /**
     * 动作----添加账户
     * 既跳转到添加账户页面
     */
    private void addAccount() {
        Intent intent = new Intent(this, AddAccountActivity.class);
        startActivity(intent);
    }

    /**
     * 点击事件
     */
    @Override
    public void onClick(View view) {
       switch (view.getId()){
           case R.id.btn_how_it_works:
               //Toast.makeText(this,"点击了btn_how_it_works",Toast.LENGTH_SHORT).show();
               howItWorks();
               break;
           case R.id.btn_add_account:
               //Toast.makeText(this,"点击了btn_add_account",Toast.LENGTH_SHORT).show();
               addAccount();
               break;
       }
    }
}
