package com.kartikhariharan.prayerbookapp;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class FavoriteOnClickListener implements OnClickListener {
	
	private Context context;
	private List<List<Prayer>> prayerList;
	private Prayer prayer;
	private PrayerListAdapter plAdapter;
	
	public FavoriteOnClickListener(Context context, PrayerListAdapter plAdapter, List<List<Prayer>> prayerList, Prayer prayer) {
		
		this.context = context;
		this.prayerList = prayerList;
		this.prayer = prayer;
		this.plAdapter = plAdapter;
		
	}

	@Override
	public void onClick(View v) {
		if (prayer.isFavoriteState()) {
			prayer.setFavoriteState(false);
			
		} else {
			prayer.setFavoriteState(true);
		}
		
		plAdapter.notifyDataSetChanged();
	}

}
