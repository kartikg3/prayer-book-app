package com.kartikhariharan.prayerbookapp;

import java.util.List;

import com.kartikhariharan.prayerbookapp.adapters.PrayerSearchAdapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.ListView;

public class SearchPrayerDetailExpandOnClickListener implements OnClickListener {
	
	private Context context;
	private List<Prayer> prayerList;
	protected PrayerSearchAdapter plAdapter;
	private Prayer prayer;
	protected int position;
	
	public SearchPrayerDetailExpandOnClickListener(Context context, PrayerSearchAdapter plAdapter, List<Prayer> prayerList, int position) {
		this.context = context;
		this.prayerList = prayerList;
		this.plAdapter = plAdapter;
		this.position = position;
		this.prayer = prayerList.get(position);
	}

	@Override
	public void onClick(View v) {
		
		
		
		if ( prayer.isExpandedState() ) {
			
			prayer.setExpandedState(false);
			
		} else {
			
			prayer.setExpandedState(true);
			
			
		}		
		
		//plAdapter.nnotifyDataSetChanged();
	}

}
