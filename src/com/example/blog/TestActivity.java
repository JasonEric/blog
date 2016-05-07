package com.example.blog;

import com.example.fragment.Fragment1;
import com.example.interfaces.FragmentInterface;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TestActivity extends FragmentActivity implements FragmentInterface {
	public FragmentManager fragmentManager;
	public Button button;
	FragmentTransaction fragmentTransaction;
	Fragment1 fragment1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_activity);
		//getSupportFragmentManager??§Þ??FragmentActivity????
		fragmentTransaction = getSupportFragmentManager().beginTransaction();
		Bundle data = new Bundle();
		data.putString("text", "This is the data deliver from activity");
		fragment1 = new Fragment1();
		fragment1.setArguments(data);
		fragmentTransaction.add(R.id.fragment1, fragment1);
		fragmentTransaction.commit();
		// TODO Auto-generated method stub

		button = (Button) findViewById(R.id.button);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			
			}
		});
	}

	@Override
	public void FragmentCallback(String string) {
		// TODO Auto-generated method stub
//		System.out.println(string+"------");
		button.setText(string);
		
	}

}
