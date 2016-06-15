package com.admin.app0612;

import android.os.Bundle;
import android.widget.Button;

/**
 * Created by Administrator on 2016/6/12.
 */
public class HowItWork1Activity extends MyBottomBtnsActivity {
    private Button nextPageBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mySetPageContentView(R.layout.how_it_work1_page_content);
        mySetActionBarDisabled(true);
    }

    @Override
    protected void myOnNextPageBtnPressed() {
        super.myOnNextPageBtnPressed();
        myStartPageActivity(HowItWork2Activity.class);
    }
}
