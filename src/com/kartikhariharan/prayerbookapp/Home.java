package com.kartikhariharan.prayerbookapp;

import android.app.ActionBar;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ExpandableListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.kartikhariharan.prayerbookapp.PrayerListAdapter;

public class Home extends Activity {
	
	ExpandableListView exlvHomeListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		//requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.home);
		//getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.actionbar);
		
		ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
		
		TextView tvTitleLine02 = (TextView) findViewById(R.id.tvTitleLine02);
		Typeface font = Typeface.createFromAsset(getAssets(), "font/p22-corinthia.ttf");
		tvTitleLine02.setTypeface(font);
		
		exlvHomeListView = (ExpandableListView) findViewById(R.id.exlvHomeListView);
		exlvHomeListView.setAdapter(new PrayerListAdapter(this));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		
		// Get the SearchView and set the searchable configuration
	    SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
	    // Assumes current activity is the searchable activity
	    //searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
	    searchView.setIconifiedByDefault(false);
	    searchView.setQueryHint(getResources().getText(R.string.search_hint));
		
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(item);
	}
	
	

}
