package com.kartikhariharan.prayerbookapp.activities;

import com.kartikhariharan.prayerbookapp.R;
import com.kartikhariharan.prayerbookapp.R.id;
import com.kartikhariharan.prayerbookapp.R.layout;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class AboutActivity extends Activity {

	public AboutActivity() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		
		ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(false);
	    actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE|ActionBar.DISPLAY_SHOW_HOME);
		
		TextView tvTitleLine02 = (TextView) findViewById(R.id.tvTitleLine02);
		Typeface font = Typeface.createFromAsset(((Activity) this).getAssets(), "font/p22-corinthia.ttf");
		tvTitleLine02.setTypeface(font);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}
	
}
