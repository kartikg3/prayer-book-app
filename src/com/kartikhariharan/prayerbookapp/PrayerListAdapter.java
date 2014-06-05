package com.kartikhariharan.prayerbookapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

public class PrayerListAdapter extends BaseExpandableListAdapter {
	
	private Context context;
	private int lastExpandedGroupPosition;
	
	String[] parentList = {
			"Daily Prayers",
			"Family Prayers",
			"Motivation Prayers",
			"Novena Prayers",
			"My Favourites"
		};
	
	String[][] childList = {
			
			{
				"Our Father",
				"Hail Mary",
				"Apostles Creed",
				"Morning Prayer",
				"Night Prayer",
				"While Travelling"
			},
			{
				"Our Father",
				"Hail Mary",
				"Apostles Creed",
				"Morning Prayer",
				"Night Prayer",
				"While Travelling"
			},
			{
				"Our Father",
				"Hail Mary",
				"Apostles Creed",
				"Morning Prayer",
				"Night Prayer",
				"While Travelling"
			},
			{
				"Our Father",
				"Hail Mary",
				"Apostles Creed",
				"Morning Prayer",
				"Night Prayer",
				"While Travelling"
			},
			{
				"Our Father",
				"Hail Mary",
				"Apostles Creed",
				"Morning Prayer",
				"Night Prayer",
				"While Travelling"
			}
	};
	
	public PrayerListAdapter(Context context) {
		this.context = context;
		this.lastExpandedGroupPosition = -1;
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

		TextView tvChild = new TextView(context);
		
		tvChild.setText(childList[groupPosition][childPosition]);
		tvChild.setTextSize(18);
		tvChild.setPadding(80, 45, 0, 45);
		tvChild.setTextColor(Color.parseColor("#111111"));
		tvChild.setBackgroundColor(Color.parseColor("#EEEEEE"));
		
		return tvChild;
	}

	@Override
	public int getChildrenCount(int groupPosition) {		
		return childList[groupPosition].length;
	}

	@Override
	public Object getGroup(int groupPosition) {
		
		return parentList[groupPosition];
	}

	@Override
	public int getGroupCount() {
		
		return parentList.length;
	}

	@Override
	public long getGroupId(int groupPosition) {
		
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		
		View row = convertView;

		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.row_prayer_list, parent, false);
		}
		
		TextView tvGroup = (TextView) row.findViewById(R.id.tvPrayerListTitle);
		
		tvGroup.setText(parentList[groupPosition]);
		
		ImageView indicator = (ImageView) row.findViewById(R.id.ivPrayerListGroupIndicator);
		
		if (isExpanded) {
			indicator.setImageResource(R.drawable.minus_yellow_01);
		} else {
			indicator.setImageResource(R.drawable.plus_yellow_01);
		}
		
		return row;
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
		// TODO Auto-generated method stub
		return false;
	}

}
