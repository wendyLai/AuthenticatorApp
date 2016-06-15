package com.admin.app0612;

import android.os.Bundle;

/**
 * Created by Administrator on 2016/6/12.
 */
public class HowItWork2Activity extends MyBottomBtnsActivity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mySetPageContentView(R.layout.how_it_work2_page_content);
        mySetActionBarDisabled(true);
    }

    @Override
    protected void myOnNextPageBtnPressed() {
        super.myOnNextPageBtnPressed();
        myStartPageActivity(HowItWork3Activity.class);
    }
}
