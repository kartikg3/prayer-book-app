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
			
			// Toggle favorite state on OFF
			prayer.setFavoriteState(false);
			
			// Untag all the prayers in prayerList with this ID as Favorite
			int prayer_id = prayer.getId();
			for (int i = 0 ; i < prayerList.size() ; i++) {
				for (int j = 0 ; j < prayerList.get(i).size() ; j++) {
					if (prayerList.get(i).get(j).getId() == prayer_id) {
						prayerList.get(i).get(j).setFavoriteState(false);
					}
				}
			}
			
		} else {
			
			// Toggle favorite state on ON
			prayer.setFavoriteState(true);
			
			// Tag all the prayers in prayerList with this ID as Favorite
			int prayer_id = prayer.getId();
			for (int i = 0 ; i < prayerList.size() ; i++) {
				for (int j = 0 ; j < prayerList.get(i).size() ; j++) {
					if (prayerList.get(i).get(j).getId() == prayer_id) {
						prayerList.get(i).get(j).setFavoriteState(true);
					}
				}
			}		
		}
		
		plAdapter.notifyDataSetChanged();
	}

}
