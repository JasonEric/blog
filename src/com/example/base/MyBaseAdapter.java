package com.example.base;

import java.util.List;

import com.example.blog.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * @author shengzh
 * @date 2015-1-7
 * @Description:�Զ����BaseAdapter������adapter�ĸ��ࡣ
 * @Description:���������� 
 * @Description:T-->�����������ͣ���List<T> 
 * @Description:Q-->���������ͣ���ListView GridView
 */
public abstract class MyBaseAdapter<T, Q> extends BaseAdapter {

	public Context context;
	public List<T> list;
	public Q view; 
	/**
	 * 
	 * @param context
	 * @param list ���������
	 * @param view ����������
	 */
	public MyBaseAdapter(Context context, List<T> list, Q view) {
		this.context = context;
		this.list = list;
		this.view = view;
	}

	public MyBaseAdapter(Context context, List<T> list) {
		this.context = context;
		this.list = list;

	}

	/**
	 * update
	 * 
	 * @param list
	 */
	public void updateListView(List<T> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
}
