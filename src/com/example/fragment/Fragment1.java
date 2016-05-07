package com.example.fragment;

import com.example.blog.R;
import com.example.blog.TestActivity;
import com.example.interfaces.FragmentInterface;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Fragment1 extends Fragment {
	private TextView textView;
	private String date;
	public FragmentInterface callback;
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		callback=(TestActivity)activity;
		
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.fragment1, container,false);
		 textView=(TextView)view.findViewById(R.id.textView_in_fragment1);
		textView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				date=getArguments().getString("text");
				// TODO Auto-generated method stub
//				textView.setText();
				Toast.makeText((TestActivity)getActivity(), date, Toast.LENGTH_SHORT).show();
				callback.FragmentCallback("data deliver from fragment");
			}
		});
		return view;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
	}
}
