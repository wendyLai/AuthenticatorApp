package com.admin.app0612;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends MyBasicActivity implements View.OnClickListener {

    private LinearLayout mContentNoAccounts;
    private LinearLayout mContentAccountsPresent;
    private List<Map<String, Object>> mUserList = new ArrayList<>();
    private SimpleAdapter mUserAdapter;
    private Button mBtnHowItWorks;
    private Button mBtnAddAccount;
    private ListView mUserListView;
    private ClipboardManager myClipBoard;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        mySetActionBarTitle(R.string.action_bar_title_main);

        //控件初始化
        initElement();

        //实例化系统剪切板的ClipboardManager对象
        myClipBoard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        //得到用户列表数据，以及adapter，显示数据列表
        mUserAdapter = new SimpleAdapter(
                this, getData(), R.layout.user_row,
                new String[]{"username", "pin"},
                new int[]{R.id.user_value, R.id.pin_value});
        mUserListView.setAdapter(mUserAdapter);

        //选择首页显示用户列表页面\添加用户页面
        chooseShowContent();

        //当有用户列表时，注册上下文菜单
        if (mContentAccountsPresent.getVisibility() != View.GONE) {
            registerForContextMenu(mUserListView);
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


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        //得到当前选择的项的index
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int itemIndex = menuInfo.position;

        switch (item.getItemId()) {
            case R.id.context_menu_copy:
                //复制二次验证码
                String pinText = mUserList.get(itemIndex).get("pin").toString();
                copyPin(pinText);
                break;
            case R.id.context_menu_rename:
                //重命名用户名
                String oldUserName = mUserList.get(itemIndex).get("username").toString();
                //新建对话框并显示,处理重命名操作
                renameUserDialog(oldUserName, itemIndex);
                break;
            case R.id.context_menu_delete:
                //删除用户
                deleteUserDialog(itemIndex);
                break;
            default:
                break;
        }

        return super.onContextItemSelected(item);
    }

    /**
     * 控件初始化
     */
    private void initElement() {
        mContentNoAccounts = (LinearLayout) findViewById(R.id.content_no_accounts);
        mContentAccountsPresent = (LinearLayout) findViewById(R.id.content_accounts_present);
        mBtnHowItWorks = (Button) findViewById(R.id.btn_how_it_works);
        mBtnAddAccount = (Button) findViewById(R.id.btn_add_account);
        mUserListView = (ListView) findViewById(R.id.user_list);
    }

    /**
     * 获取数据
     */
    private List<Map<String, Object>> getData() {

        UserDBManager manager = new UserDBManager(this);

        List<UserEntity> users = manager.query();
        mUserList = manager.setObjectToMap(users);

        manager.closeDB();

        return mUserList;
    }

    /**
     * 选择显示内容---用户列表或者添加用户页面
     */
    private void chooseShowContent() {
        if (mUserList.isEmpty()) {
            //显示无用户的样式
            mContentAccountsPresent.setVisibility(View.GONE);
            //设置无用户时的按钮监听事件
            mBtnAddAccount.setOnClickListener(this);
            mBtnHowItWorks.setOnClickListener(this);

        } else {
            //显示有用户的样式
            mContentNoAccounts.setVisibility(View.GONE);
        }
    }

    /**
     * 动作----设置
     * 既跳转到设置页面
     */
    private void Setting() {
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }

    /**
     * 动作----展示如何工作
     * 既跳转到如何工作页面
     */
    private void howItWorks() {
        Intent intent = new Intent(this, HowItWork1Activity.class);
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
        switch (view.getId()) {
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

    /**
     * 粘贴剪切板里的数据
     */
    private String pastePin() {
        ClipData data = myClipBoard.getPrimaryClip();
        ClipData.Item item = data.getItemAt(0);
        String text = item.getText().toString();
        //Log.i("info", "paste" + text);
        return text;
    }

    /**
     * 点击context的复制选项，复制二次验证码
     */
    private void copyPin(String pin) {
        ClipData data = ClipData.newPlainText("pin", pin);
        myClipBoard.setPrimaryClip(data);
        //Log.i("info", "copy" + pin);
    }

    /**
     * 点击context的重命名选项，弹出对话框
     */
    private void renameUserDialog(final String oldText, final int userIndex) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("重命名");
        final EditText input = new EditText(this);
        //将用户名称显示出来
        input.setText(oldText);
        //将光标移到最后
        input.setSelection(oldText.length());
        builder.setView(input);

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String newUserName = input.getText().toString();
                //更新数据库
                UserDBManager manager = new UserDBManager(MainActivity.this);
                manager.modifiedUserName(oldText, newUserName);
                manager.closeDB();

                //更新用户数据列表
                mUserList.get(userIndex).put("username", newUserName);
                mUserAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.create().show();
    }

    /**
     * 点击context的删除选项，弹出对话框
     */
    private void deleteUserDialog(final int userIndex) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("确认是否删除");

        TextView warnTV = new TextView(this);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View textContent = inflater.inflate(R.layout.remove_user_warning_dialog, null);
        builder.setView(textContent);
        //得到要删除的项的username（更新数据库时的参数）
        final String delUserName = mUserList.get(userIndex).get("username").toString();

        builder.setPositiveButton("删除账户", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //更新数据库
                UserDBManager manager = new UserDBManager(MainActivity.this);
                manager.deleteUser(delUserName);
                manager.closeDB();
                mUserList.remove(userIndex);
                mUserAdapter.notifyDataSetChanged();
                //Log.i("info","list="+mUserList);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.create().show();
    }

}
