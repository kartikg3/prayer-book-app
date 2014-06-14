package com.kartikhariharan.prayerbookapp;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.DataSetObserver;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class PrayerSearchAdapter extends ArrayAdapter<Prayer> {
	
	private List<Prayer> prayerList;
	Context context;
	
	public PrayerSearchAdapter(Context context, List<Prayer> prayerList) {
		super(context, R.layout.row_prayer_title_list, prayerList);
		this.context = context;
		this.prayerList = prayerList;		
	}

	public int getCount() {
		return prayerList.size();
	}

	public Prayer getItem(int arg0) {
		return prayerList.get(arg0);
	}

	public long getItemId(int position) {
		return position;
	}

	public int getItemViewType(int arg0) {
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		
		View row = null;
		
		Prayer prayer = prayerList.get(position);
		
		if (!prayer.isExpandedState()) {
		
			if (row == null) {
				LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				row = inflater.inflate(R.layout.row_prayer_title_list, parent, false);
			}
			
			TextView tvChild = (TextView) row.findViewById(R.id.tvPrayerTitle);
			
			tvChild.setText(prayer.getTitle());
			
			ImageView indicator = (ImageView) row.findViewById(R.id.ivPrayerListIndicator);
			
			if (indicator != null) {				
				indicator.setImageResource(R.drawable.plus_blue_01);
			}
		
		} else {
			
			if (row == null) {
				LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				row = inflater.inflate(R.layout.row_prayer_detail_list, parent, false);
			}
			
			TextView tvChild = (TextView) row.findViewById(R.id.tvPrayerTitle);
			
			tvChild.setText(prayer.getTitle());
			
			TextView tvContent = (TextView) row.findViewById(R.id.tvPrayerContent);
			
			String content = prayer.getContent();
			String query =((SearchableActivity) context).query;
			content = content.replaceAll("\n", "<br/>");
			content = content.replaceAll( "(?i)"+query ,
					String.format("<font color='#BB0000'>%s</font>", query));
			
			tvContent.setText(Html.fromHtml(content));
			
			ImageButton indicator = (ImageButton) row.findViewById(R.id.ivPrayerListIndicator);
			
			indicator.setOnClickListener(new SearchPrayerDetailExpandOnClickListener(context, this, prayerList, position) {
				@Override
				public void onClick(View v) {
					Prayer prayer = prayerList.get(this.position);
					if ( prayer.isExpandedState() ) {
						
						prayer.setExpandedState(false);
						
					} else {
						
						prayer.setExpandedState(true);
						
					}
					
					ArrayAdapter<Prayer> newPlAdapter = new PrayerSearchAdapter(context, prayerList);
					((SearchableActivity) context).lvSearchResults.setAdapter(newPlAdapter);
									
				}
			});
			
			if (indicator != null) {
				indicator.setBackgroundResource(R.drawable.collapse_blue_01);
			}
			
			ImageButton favoritesButton = (ImageButton) row.findViewById(R.id.btnFavorite);
			if (prayer.isFavoriteState()) {
				favoritesButton.setBackgroundResource(R.drawable.star_yellow_01);
			} else {
				favoritesButton.setBackgroundResource(R.drawable.star_gray_01);
			}
			
			//favoritesButton.setOnClickListener(new SearchFavoriteOnClickListener(context, this, prayerList, prayer) {});
			favoritesButton.setOnClickListener(new SearchPrayerDetailExpandOnClickListener(context, this, prayerList, position) {
				@Override
				public void onClick(View v) {
					Prayer prayer = prayerList.get(this.position);					
					SearchableActivity searchableActivity = (SearchableActivity) context;
					ContentValues values = new ContentValues();
					if ( prayer.isFavoriteState() ) {
						
						// Update in database						
						values.put(SearchableActivity.IS_FAVORITE, 0);
						searchableActivity.database.update(SearchableActivity.PRAYER_TABLE_NAME,
								values,
								String.format("%s=%d",
										SearchableActivity.PRAYER_ID,
										prayer.getId()),
								null);
						prayer.setFavoriteState(false);
						
					} else {
						
						// Update in database
						values.put(SearchableActivity.IS_FAVORITE, 1);
						searchableActivity.database.update(SearchableActivity.PRAYER_TABLE_NAME,
								values,
								String.format("%s=%d",
										SearchableActivity.PRAYER_ID,
										prayer.getId()),
								null);
						prayer.setFavoriteState(true);
						
					}
					
					ArrayAdapter<Prayer> newPlAdapter = new PrayerSearchAdapter(context, prayerList);
					((SearchableActivity) context).lvSearchResults.setAdapter(newPlAdapter);
							     
									
				}
			});
			
			ImageButton shareButton = (ImageButton) row.findViewById(R.id.btnShare);
			
			String promoString = "- Sent via PrayerBookApp";
			String textToShare = String.format("%s\n\n%s\n\n%s", prayer.getTitle(), prayer.getContent(), promoString);
			
			shareButton.setOnClickListener(new ShareOnClickListener(context, textToShare) {
				
			});
			
		}
		
		row.setOnClickListener(new SearchPrayerDetailExpandOnClickListener(context, this, prayerList, position) {
			@Override
			public void onClick(View v) {
				Prayer prayer = prayerList.get(this.position);
				if ( prayer.isExpandedState() ) {
					
					prayer.setExpandedState(false);
					
				} else {
					
					prayer.setExpandedState(true);
					
				}
				
				// Collapse all other prayers
				for (int i = 0 ; i < prayerList.size() ; i++) {
					if (prayer.getId() == prayerList.get(i).getId()) {
						continue;
					} else {
						prayerList.get(i).setExpandedState(false);
					}
				}
				
				ArrayAdapter<Prayer> newPlAdapter = new PrayerSearchAdapter(context, prayerList);
				((SearchableActivity) context).lvSearchResults.setAdapter(newPlAdapter);
				
				// Creating the expand animation for the item
				/*
				LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				LinearLayout linearLayout = (LinearLayout) v.findViewById(R.id.llSearchResults);
			    ExpandAnimation expandAni = new ExpandAnimation(linearLayout, 500);
			    */

			    // Start the animation on the toolbar
			    //v.startAnimation(expandAni);
			}
		});
		
		return row;
		
	}

	@Override
	public int getViewTypeCount() {
		return 1;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public void registerDataSetObserver(DataSetObserver observer) {

	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {

	}

	@Override
	public boolean areAllItemsEnabled() {
		return true;
	}

	@Override
	public boolean isEnabled(int position) {
		return true;
	}

}
