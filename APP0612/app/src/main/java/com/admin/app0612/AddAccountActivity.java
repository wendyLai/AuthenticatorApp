package com.admin.app0612;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.admin.app0612.addaccount.ManuallyAddAccountActivity;

import java.util.List;

/**
 * Created by Administrator on 2016/6/12.
 */
public class AddAccountActivity extends MyBottomBtnsActivity implements View.OnClickListener {

    private Button mManuallyAddAccountBtn;
    private Button mScanBarcodetBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mySetPageContentView(R.layout.add_account_page_content);
        mySetNextBtnDisabled(true);

        mySetActionBarTitle(R.string.action_bar_title_add_account);

        mScanBarcodetBtn = (Button) findViewById(R.id.btn_scan_barcode);
        mManuallyAddAccountBtn = (Button) findViewById(R.id.btn_manually_add_account);

        mScanBarcodetBtn.setOnClickListener(this);
        mManuallyAddAccountBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_scan_barcode:
                scanBarcode();
                break;
            case R.id.btn_manually_add_account:
                manuallyAddAccount();
                break;
        }
    }

    /**
     * 手动添加用户
     */
    private void manuallyAddAccount() {
        Log.i("info", "手动添加用户");
        Intent intent = new Intent(this, ManuallyAddAccountActivity.class);
        startActivity(intent);
    }

    /**
     * 启动扫描二维码
     */
    private void scanBarcode() {
        String zXingPackageName = Zxing.packagename;
        boolean hasZXing = isPackageInstalled(this, zXingPackageName);
        if (hasZXing) {
            doStartApplicationWithPackageName(this, zXingPackageName);
        } else {
            //openBrowerDownload();
        }
    }

    /**
     * 启动扫描二维码-
     * 判断设备中是否安装过指定包
     */
    private boolean isPackageInstalled(Context mcontext, String packagename) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = mcontext.getPackageManager().getPackageInfo(packagename, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        if (packageInfo == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 启动扫描二维码-
     * 下载ZXING
     */
    private void openBrowerDownload() {
        Uri uri = Uri.parse(Zxing.downloaduri);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }


    /**
     * 启动扫描二维码-
     * 获取包对应的class，并且跳转到到该应用中
     */
    private void doStartApplicationWithPackageName(Context mcontext, String packagename) {
        PackageInfo packageInfo = null;

        try {
            packageInfo = mcontext.getPackageManager().getPackageInfo(packagename, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if (packageInfo == null) {
            return;
        }

        // 创建一个类别为 CATEGORY_LAUNCHER 的该包名的 Intent
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(packageInfo.packageName);

        // 通过 getPackageManager()的 queryIntentActivities 方法遍历
        List<ResolveInfo> resolveinfoList = mcontext.getPackageManager()
                .queryIntentActivities(resolveIntent, 0);

        ResolveInfo resolveinfo = resolveinfoList.iterator().next();
        if (resolveinfo != null) {
            // packagename = 参数 packname
            String packageName = resolveinfo.activityInfo.packageName;
            // 这个就是我们要找的该 APP 的LAUNCHER 的 Activity[组织形式： packagename.mainActivityname]
            String className = resolveinfo.activityInfo.name;
            // LAUNCHER Intent
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);

            // 设置 ComponentName参数 1:packagename 参数2:MainActivity 路径
            ComponentName componentName = new ComponentName(packageName, className);

            intent.setComponent(componentName);
            mcontext.startActivity(intent);
        }
    }
}
