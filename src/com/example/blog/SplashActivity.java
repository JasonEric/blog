package com.example.blog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.LinearLayout;

public class SplashActivity extends Activity {
	private LinearLayout splash;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		splash = (LinearLayout) findViewById(R.id.splash);
		splash.setBackgroundResource(R.drawable.ic_launcher);
		Animation alphaAnimation = new AlphaAnimation(0.5f, 1.0f);
		alphaAnimation.setDuration(2000);
		splash.startAnimation(alphaAnimation);

		splash.getAnimation().setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {

				startActivity(new Intent(SplashActivity.this,
						MainActivity.class));
				finish();
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
	}
}
