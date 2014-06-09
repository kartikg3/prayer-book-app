package com.kartikhariharan.prayerbookapp;

import android.view.View;
import android.view.View.OnClickListener;

public class PrayerDetailExpandOnClickListener implements OnClickListener {
	
	int groupPosition;
	int childPosition;
	
	public PrayerDetailExpandOnClickListener(int groupPosition, int childPosition) {

		this.groupPosition = groupPosition;
		this.childPosition = childPosition;
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
