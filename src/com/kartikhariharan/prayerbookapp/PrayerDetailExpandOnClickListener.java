package com.kartikhariharan.prayerbookapp;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class PrayerDetailExpandOnClickListener implements OnClickListener {
	
	private Context context;
	private List<List<Prayer>> prayerList;
	private Prayer prayer;
	private PrayerListAdapter plAdapter;
	private int groupPosition;
	private int childPosition;
	
	public PrayerDetailExpandOnClickListener(Context context, PrayerListAdapter plAdapter, List<List<Prayer>> prayerList, Prayer prayer, int groupPosition, int childPosition) {
		
		this.context = context;
		this.prayerList = prayerList;
		this.prayer = prayer;
		this.plAdapter = plAdapter;
		this.groupPosition = groupPosition;
		this.childPosition = childPosition;
		
	}

	@Override
	public void onClick(View v) {
		
		if ( prayer.isExpandedState() ) {
			
			prayer.setExpandedState(false);
			
		} else {
			
			prayer.setExpandedState(true);
			
		}		
		
		plAdapter.notifyDataSetChanged();
	}

}
