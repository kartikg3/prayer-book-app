package com.kartikhariharan.prayerbookapp.adapters;

import java.util.ArrayList;
import java.util.List;

import com.kartikhariharan.prayerbookapp.Category;
import com.kartikhariharan.prayerbookapp.FavoriteOnClickListener;
import com.kartikhariharan.prayerbookapp.Prayer;
import com.kartikhariharan.prayerbookapp.PrayerDetailExpandOnClickListener;
import com.kartikhariharan.prayerbookapp.R;
import com.kartikhariharan.prayerbookapp.ShareOnClickListener;
import com.kartikhariharan.prayerbookapp.R.drawable;
import com.kartikhariharan.prayerbookapp.R.id;
import com.kartikhariharan.prayerbookapp.R.layout;

import android.animation.LayoutTransition;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class PrayerListAdapter extends BaseExpandableListAdapter {
	
	private Context context;
	private int lastExpandedGroupPosition;
	
	private int lastExpandedPrayer[] = {-1, -1};
	
	private List<List<Prayer>> prayerList;
	private List<Category> categoryList;
	
	public PrayerListAdapter(Context context, List<Category> categoryList, List<List<Prayer>> prayerList) {
		this.context = context;
		this.lastExpandedGroupPosition = -1;
		this.prayerList = prayerList;
		this.categoryList = categoryList;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		
		View row = null;
		
		Prayer prayer = prayerList.get(groupPosition).get(childPosition);
		
		if (!prayer.isExpandedState()) {
		
			if (row == null) {
				LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				row = inflater.inflate(R.layout.row_prayer_title_list, parent, false);
			}
			
			TextView tvChild = (TextView) row.findViewById(R.id.tvPrayerTitle);
			
			//tvChild.setText(childList[groupPosition][childPosition]);
			tvChild.setText(prayer.getTitle());
			//tvChild.setTypeface(Typeface.createFromAsset(((Activity) this.context).getAssets(), "font/helvetica_neue_ce_35_thin.ttf"));
			
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
			
			//tvChild.setText(childList[groupPosition][childPosition]);
			tvChild.setText(prayer.getTitle());
			//tvChild.setTypeface(Typeface.createFromAsset(((Activity) this.context).getAssets(), "font/helvetica_neue_ce_35_thin.ttf"));
			
			TextView tvContent = (TextView) row.findViewById(R.id.tvPrayerContent);
			tvContent.setText(prayer.getContent());
			//tvContent.setTypeface(Typeface.createFromAsset(((Activity) this.context).getAssets(), "font/helvetica_neue_ce_35_thin.ttf"));
			
			ImageButton indicator = (ImageButton) row.findViewById(R.id.ivPrayerListIndicator);
			
			indicator.setOnClickListener(new PrayerDetailExpandOnClickListener(context, this, prayerList, prayer, groupPosition, childPosition) {});
			
			if (indicator != null) {
				indicator.setBackgroundResource(R.drawable.collapse_blue_01);
			}
			
			ImageButton favoritesButton = (ImageButton) row.findViewById(R.id.btnFavorite);
			if (prayer.isFavoriteState()) {
				favoritesButton.setBackgroundResource(R.drawable.star_yellow_01);
			} else {
				favoritesButton.setBackgroundResource(R.drawable.star_gray_01);
			}
			
			favoritesButton.setOnClickListener(new FavoriteOnClickListener(context, this, prayerList, prayer) {});
			
			ImageButton shareButton = (ImageButton) row.findViewById(R.id.btnShare);
			
			String promoString = "- Sent via PrayerBookApp";
			String textToShare = String.format("%s\n\n%s\n\n%s", prayer.getTitle(), prayer.getContent(), promoString);
			
			shareButton.setOnClickListener(new ShareOnClickListener(context, textToShare) {
				
			});
			
		}
		
		return row;
	}

	@Override
	public int getChildrenCount(int groupPosition) {		
		//return childList[groupPosition].length;
		return prayerList.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		
		//return parentList[groupPosition];
		return categoryList.get(groupPosition).getName();
	}

	@Override
	public int getGroupCount() {
		
		//return parentList.length;
		return categoryList.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		
		View row = null;
		TextView tvTitleLine02;
		Typeface font;
		if (groupPosition == 0) {
			// Styles for the header group
			
			row = null;
			
			//if (row == null) {
			if (row == null) {
				LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				row = inflater.inflate(R.layout.row_header, parent, false);
			}	
			
			tvTitleLine02 = (TextView) row.findViewById(R.id.tvTitleLine02);
			font = Typeface.createFromAsset(((Activity) this.context).getAssets(), "font/p22-corinthia.ttf");
			tvTitleLine02.setTypeface(font);
			
			row.setClickable(false);
			
			return row;
			
		} else {
			
			 //row = convertView;
			
			if (row == null) {
				LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				row = inflater.inflate(R.layout.row_category_list, parent, false);
			}
			
			TextView tvGroup = (TextView) row.findViewById(R.id.tvPrayerListTitle);
			
			if (tvGroup != null) {
				//tvGroup.setText(parentList[groupPosition]);
				tvGroup.setText(categoryList.get(groupPosition).getName());
			}
			
			ImageView indicator = (ImageView) row.findViewById(R.id.ivPrayerListGroupIndicator);
			
			if (indicator != null) {
				if (isExpanded) {
					indicator.setImageResource(R.drawable.minus_yellow_01);
				} else {
					indicator.setImageResource(R.drawable.plus_yellow_01);
				}
			}
			
			return row;
		}
	}
	
	@Override
    public void onGroupExpanded(int groupPosition){
        //collapse the old expanded group, if not the same
        //as new group to expand
		
		Activity mActivity = (Activity) context;
		ExpandableListView listView = (ExpandableListView) mActivity.findViewById(R.id.exlvHomeListView);		
		
        if(groupPosition != lastExpandedGroupPosition){
            listView.collapseGroup(lastExpandedGroupPosition);
        }

        super.onGroupExpanded(groupPosition);  
        lastExpandedGroupPosition = groupPosition;
        
    }
	
	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
	
	public void clickPrayer(int groupPosition, int childPosition) {
		
		Prayer clickedPrayer = prayerList.get(groupPosition).get(childPosition);
		
		notifyDataSetChanged();
		
		if ( clickedPrayer.isExpandedState() ) {
			
			clickedPrayer.setExpandedState(false);
			
		} else {
			
			clickedPrayer.setExpandedState(true);
			
		}
		
		// Collapse all other prayers
		for (int i = 0 ; i < prayerList.size() ; i++) {
			for (int j = 0 ; j < prayerList.get(i).size() ; j++) {
				if (i == groupPosition && j == childPosition) {
					continue;
				} else {
					prayerList.get(i).get(j).setExpandedState(false);
				}
			}
		}
		
		this.lastExpandedPrayer[0] = groupPosition;
		this.lastExpandedPrayer[1] = childPosition;
		
	}
	
	public void refresh(){
		notifyDataSetChanged();
	}

	public int getLastExpandedGroupPosition() {
		return lastExpandedGroupPosition;
	}

	public void setLastExpandedGroupPosition(int lastExpandedGroupPosition) {
		this.lastExpandedGroupPosition = lastExpandedGroupPosition;
	}

	public int[] getLastExpandedPrayer() {
		return lastExpandedPrayer;
	}

	public void setLastExpandedPrayer(int[] lastExpandedPrayer) {
		this.lastExpandedPrayer = lastExpandedPrayer;
	}
	
	public void setLastExpandedPrayer(int groupPosition, int childPosition) {
		this.lastExpandedPrayer[0] = groupPosition;
		this.lastExpandedPrayer[1] = childPosition;
	}

}
