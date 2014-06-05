package com.kartikhariharan.prayerbookapp;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Window;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.kartikhariharan.prayerbookapp.PrayerListAdapter;

public class Home extends Activity {
	
	ExpandableListView exlvHomeListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.home);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.actionbar);
		
		TextView tvTitleLine02 = (TextView) findViewById(R.id.tvTitleLine02);
		Typeface font = Typeface.createFromAsset(getAssets(), "font/p22-corinthia.ttf");
		tvTitleLine02.setTypeface(font);
		
		exlvHomeListView = (ExpandableListView) findViewById(R.id.exlvHomeListView);
		exlvHomeListView.setAdapter(new PrayerListAdapter(this));
	}

}
