package com.admin.app0612;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2016/6/12.
 */
public class MyBottomBtnsActivity extends MyBasicActivity {

    private LinearLayout mPageContent;
    private Button mBackPageBtn;
    private Button mNextPageBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_btns_activity);


        mPageContent = (LinearLayout) findViewById(R.id.how_it_works_page_content);

        mBackPageBtn = (Button) findViewById(R.id.btn_back);
        mNextPageBtn = (Button) findViewById(R.id.btn_next);

        mBackPageBtn.setOnClickListener(new MyOnClickListener());
        mNextPageBtn.setOnClickListener(new MyOnClickListener());

    }

    /**
     * 加载页面主要内容方法
     */
    protected void mySetPageContentView(int resId) {
        View view = getLayoutInflater().inflate(resId, null);
        mPageContent.removeAllViews();
        mPageContent.addView(view);
    }

    /**
     * 右按钮点击事件
     */
    protected void myOnNextPageBtnPressed() {
    }

    /**
     * 页面跳转事件
     */
    protected void myStartPageActivity(Class<? extends Activity> activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }

    /**
     * 修改右按钮显示文字
     */
    protected void mySetNextBtnText(int resId) {
        String text = getResources().getString(resId);
        mNextPageBtn.setText(text);
    }

    /**
     * 右按钮无法点击
     */
    protected void mySetNextBtnDisabled(boolean isNextBtnGone) {
        if (isNextBtnGone) {
            mNextPageBtn.setVisibility(View.GONE);
        } else {
            mNextPageBtn.setVisibility(View.VISIBLE);
        }
    }

    private class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_back:
                    //Toast.makeText(this, "点击了回退按钮", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                    break;
                case R.id.btn_next:
                    //Toast.makeText(this, "点击了前进按钮", Toast.LENGTH_SHORT).show();
                    myOnNextPageBtnPressed();
                    break;
            }
        }
    }
}
