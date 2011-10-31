package com.jonschwartzsoftware.fencethehead;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class FenceTheHeadActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Thread timer = new Thread() {
			public void run() {
				try {
					sleep(1500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					Intent i = new Intent("com.jonschwartzsoftware.fencethehead.PLAY");
					startActivity(i);
				}
			}
		};
		timer.start();
	}
}