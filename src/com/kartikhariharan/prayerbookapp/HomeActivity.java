package com.kartikhariharan.prayerbookapp;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.kartikhariharan.prayerbookapp.PrayerListAdapter;

public class HomeActivity extends Activity {
	
	ExpandableListView exlvHomeListView;
	
	private static final String DB_NAME = "prayers.db";
    
    //A good practice is to define database field names as constants
	private static final String CATEGORY_TABLE_NAME = "CATEGORY";	
	private static final String CATEGORY_ID = "CATEGORY_id";
	private static final String CATEGORY_TITLE = "TITLE";
	
	private static final String PRAYER_TABLE_NAME = "PRAYER";
	private static final String PRAYER_ID = "PRAYER_id";
	private static final String PRAYER_TITLE = "TITLE";
	private static final String PRAYER_CONTENT = "CONTENT";
	private static final String IS_FAVORITE = "IS_FAVORITE";
	
	private static final String CATEGORY_PRAYER_MAP_TABLE_NAME = "CATEGORY_PRAYER_MAP";
	private static final String CATEGORY_MAP_ID = "CATEGORY_ID";
	private static final String PRAYER_MAP_ID = "PRAYER_ID";
	
	private SQLiteDatabase database;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		//requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.home);
		
		//getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.actionbar);
		
		ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
		
		exlvHomeListView = (ExpandableListView) findViewById(R.id.exlvHomeListView);
		
		//Our key helper
        DataBaseHelper dbOpenHelper = new DataBaseHelper(this, DB_NAME);
        database = dbOpenHelper.openDataBase();        
        //That’s it, the database is open!
		
		List<List<Prayer>> prayerList = new ArrayList<List<Prayer>>();
		List<Category> categoryList = new ArrayList<Category>();

		categoryList = populateCategoryList(categoryList);
		prayerList = populatePrayerList(prayerList, categoryList);
		/*categoryList.add(new Category(1,
				""));
		
		categoryList.add(new Category(2,
				"Daily Prayers"));*/
		
		exlvHomeListView.setAdapter(new PrayerListAdapter(this, categoryList, prayerList));
		
		exlvHomeListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				// Do nothing when header is clicked
				
				if (groupPosition == 0) {
					
					return true;
					
				} else {
					
					return false;
					
				}
				
			}
		});
		
		exlvHomeListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				
				((PrayerListAdapter) parent.getExpandableListAdapter()).testFunction(groupPosition, childPosition);
				return false;
				
			}			
			
		});
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
	    
	    int searchSrcTextId = getResources().getIdentifier("android:id/search_src_text", null, null);  
	    EditText searchEditText = (EditText) searchView.findViewById(searchSrcTextId);  
	    searchEditText.setTextColor(Color.DKGRAY);
		
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(item);
	}
	
	public List<Category> populateCategoryList (List<Category> categoryList) {
		// Method to populate the category list from the data source
		
		Cursor categoryCursor = database.query(CATEGORY_TABLE_NAME,
				new String[] {CATEGORY_ID, CATEGORY_TITLE},
				null, null, null, null, CATEGORY_ID);
		
		categoryCursor.moveToFirst();
		
		if (!categoryCursor.isAfterLast()) {
			
			do {
				
				int id = categoryCursor.getInt(0);
				String title = categoryCursor.getString(1);
				
				categoryList.add(new Category(id, title));
				
			} while (categoryCursor.moveToNext());
			
		}
		
		return categoryList;
	}
	
	public List<List<Prayer>> populatePrayerList (List<List<Prayer>> prayerList, List<Category> categoryList) {
		// Method to populate the prayer list from the data source
		
		Cursor prayerCursor;	
		
		Cursor categoryCursor = database.query(CATEGORY_TABLE_NAME,
				new String[] {CATEGORY_ID, CATEGORY_TITLE},
				null, null, null, null, CATEGORY_ID);
		
		Cursor mapCursor;
		
		categoryCursor.moveToFirst();
		if (!categoryCursor.isAfterLast()) {
			
			int i = 0;
			
			do {
					int category_id = categoryCursor.getInt(0);
					
					mapCursor = database.query(CATEGORY_PRAYER_MAP_TABLE_NAME,
							new String[] {CATEGORY_MAP_ID, PRAYER_MAP_ID},
							"CATEGORY_ID="+category_id, null, null, null, CATEGORY_MAP_ID);
				
					mapCursor.moveToFirst();
					if (!mapCursor.isAfterLast()) {						
						
						do {
							
							prayerCursor = database.query(PRAYER_TABLE_NAME,
										new String[] {PRAYER_ID, PRAYER_TITLE, PRAYER_CONTENT, IS_FAVORITE},
										"PRAYER_id="+mapCursor.getInt(1), null, null, null, PRAYER_ID);	
							
							
							prayerCursor.moveToFirst();
							if (!prayerCursor.isAfterLast()) {
								
								prayerList.add(new ArrayList<Prayer>());
								
								do {
									
									int id = prayerCursor.getInt(0);
									String title = prayerCursor.getString(1);
									String content = prayerCursor.getString(2);
									boolean favoriteState = prayerCursor.getInt(3) > 0 ? true : false;
									
									//categoryList.add(new Category(id, title));									
									
									prayerList.get(i).add(new Prayer(id, title,	content, false, favoriteState));									
									
								} while (prayerCursor.moveToNext());
								
							}
							
						} while (mapCursor.moveToNext());
						
					}
					
					i++;
				
			} while (categoryCursor.moveToNext());
			
		}
		
		return prayerList;
	}

}
