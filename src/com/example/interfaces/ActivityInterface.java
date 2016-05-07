package com.example.interfaces;

import android.view.View;

/**
 * 
 * @author shengzh
 * @date 2015-1-12
 * @description
 */
public interface ActivityInterface {
    /**
     * 设置无标题时，自己定义的view</br>
     * requireTitle为flase的时候，要重写setMainView</br>
     * requireTitle为true时，不需要重写次方法
     */
    void setRootView();

    /** 初始化数据 */
    void initData();

//    /** 在线程中初始化数据 */
//    void initDataFromThread();

    /** 初始化控件 */
    void initView();

    /** 点击事件回调方法 */
//    void viewClick(View v);
	/**
	 * 
	 * @return R.layout.你的布局
	 */
    int setMainView();
}
