package com.exapmle.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.base.BaseViewHolder;
import com.example.base.MyBaseAdapter;
import com.example.blog.R;

public class ListViewAdapter extends MyBaseAdapter<String, ListView> {

	public ListViewAdapter(Context context, List<String> list, ListView view) {
		super(context, list, view);
		list=new ArrayList<String>();
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView==null) {
			convertView=LayoutInflater.from(context).inflate(R.layout.listview_item, null);
		}
//		return BaseViewHolder.get(convertView, R.id.textView1);
		TextView textView=BaseViewHolder.get(convertView,  R.id.textView1);
		textView.setText(list.get(position));
		return convertView;
	}
}
