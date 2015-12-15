package com.orong.activity;

import com.orong.R;

import android.os.Bundle;

public class AboutActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		initivReabck(this);
		setTitle(this, R.string.aboutus);
		
	}
}
