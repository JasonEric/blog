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
     * �����ޱ���ʱ���Լ������view</br>
     * requireTitleΪflase��ʱ��Ҫ��дsetMainView</br>
     * requireTitleΪtrueʱ������Ҫ��д�η���
     */
    void setRootView();

    /** ��ʼ������ */
    void initData();

//    /** ���߳��г�ʼ������ */
//    void initDataFromThread();

    /** ��ʼ���ؼ� */
    void initView();

    /** ����¼��ص����� */
//    void viewClick(View v);
	/**
	 * 
	 * @return R.layout.��Ĳ���
	 */
    int setMainView();
}
