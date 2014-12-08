package com.kartikhariharan.prayerbookapp.activities;

import java.util.ArrayList;
import java.util.List;

import com.kartikhariharan.prayerbookapp.DataBaseHelper;
import com.kartikhariharan.prayerbookapp.Prayer;
import com.kartikhariharan.prayerbookapp.R;
import com.kartikhariharan.prayerbookapp.R.id;
import com.kartikhariharan.prayerbookapp.R.layout;
import com.kartikhariharan.prayerbookapp.R.menu;
import com.kartikhariharan.prayerbookapp.R.string;
import com.kartikhariharan.prayerbookapp.adapters.PrayerSearchAdapter;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

public class SearchableActivity extends Activity {
	
static final String DB_NAME = "prayers.db";
    
    //A good practice is to define database field names as constants
	static final String CATEGORY_TABLE_NAME = "CATEGORY";	
	static final String CATEGORY_ID = "CATEGORY_id";
	static final String CATEGORY_TITLE = "TITLE";
	
	private static final String PRAYER_TABLE_NAME = "PRAYER";
	private static final String PRAYER_ID = "PRAYER_id";
	static final String PRAYER_TITLE = "TITLE";
	static final String PRAYER_CONTENT = "CONTENT";
	private static final String IS_FAVORITE = "IS_FAVORITE";
	
	static final String CATEGORY_PRAYER_MAP_TABLE_NAME = "CATEGORY_PRAYER_MAP";
	static final String CATEGORY_MAP_ID = "CATEGORY_ID";
	static final String PRAYER_MAP_ID = "PRAYER_ID";
	
	private SQLiteDatabase database;
	List<Prayer> prayerList;
	
	private ListView lvSearchResults;
	
	SearchView searchView;
    SearchManager searchManager;
    
    private String query;
    
    TextView tvResultsLabel;
    TextView tvResultsCount;
	
	public SearchableActivity() {
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		Log.d("DEBUG", "Entered search");
		
		setContentView(R.layout.search_results_listview);
		
		tvResultsLabel = (TextView) findViewById(R.id.tvResultsLabel);
		tvResultsCount = (TextView) findViewById(R.id.tvResultsCount);
		
		prayerList = new ArrayList<Prayer>();
		
		ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    
	    actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE|ActionBar.DISPLAY_SHOW_HOME|ActionBar.DISPLAY_HOME_AS_UP);
	    
	    setLvSearchResults((ListView) findViewById(R.id.lvSearchResults));
        
	    DataBaseHelper dbOpenHelper = new DataBaseHelper(this, SearchableActivity.DB_NAME);
        setDatabase(dbOpenHelper.openDataBase());
        
        if (Intent.ACTION_SEARCH.equals(getIntent().getAction())) {
	    	
		    setQuery(getIntent().getStringExtra(SearchManager.QUERY));
		    
	    }
        
	    handleIntent(getIntent());
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.search_menu, menu);
		
		// Get the SearchView and set the searchable configuration
	    searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
	    searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
	    
		// Assumes current activity is the searchable activity
	    searchView.setSearchableInfo(searchManager.getSearchableInfo( getComponentName() ));
	    searchView.setIconifiedByDefault(false);
	    searchView.setQueryHint(getResources().getText(R.string.search_hint));
	    	    	    
	    if (getQuery() != null) {
	    	searchView.setQuery(getQuery(), false);
	    }
	    
	    int searchSrcTextId = getResources().getIdentifier("android:id/search_src_text", null, null);  
	    EditText searchEditText = (EditText) searchView.findViewById(searchSrcTextId);  
	    searchEditText.setTextColor(Color.DKGRAY);
		
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
	    setIntent(intent);
	    handleIntent(intent);
	}

	private void handleIntent(Intent intent) {
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
	    	
		    this.setQuery(intent.getStringExtra(SearchManager.QUERY));	    
		    
		    doSearch(this.getQuery());
		    
	    }
	}

	private void doSearch(String query) {
		
		prayerList = new ArrayList<Prayer>();
		
		Cursor prayerCursor;	
		
		prayerCursor = getDatabase().query(SearchableActivity.getPrayerTableName(),
				new String[] {SearchableActivity.getPrayerId(), SearchableActivity.PRAYER_TITLE, SearchableActivity.PRAYER_CONTENT, SearchableActivity.getIsFavorite()},
				String.format("%s LIKE %s OR %s LIKE %s",
						SearchableActivity.PRAYER_CONTENT, "'%" + query + "%'",
						SearchableActivity.PRAYER_TITLE, "'%" + query + "%'"),
				null, null, null, SearchableActivity.getPrayerId());
		
		tvResultsCount.setText(String.format("%d", prayerCursor.getCount()));
		
		prayerCursor.moveToFirst();
		if (!prayerCursor.isAfterLast()) {
			do {
				
				int id = prayerCursor.getInt(0);
				String title = prayerCursor.getString(1);
				String content = prayerCursor.getString(2);
				boolean favoriteState = prayerCursor.getInt(3) > 0 ? true : false;								
				
				prayerList.add(new Prayer(id, title, content, false, favoriteState));
				
			} while (prayerCursor.moveToNext());
		}
		
		ArrayAdapter<Prayer> adapter = new PrayerSearchAdapter(this, prayerList);
		getLvSearchResults().setAdapter(adapter);
		
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		int lastExpandedPosition = savedInstanceState.getInt("LAST_EXPANDED_GROUP");
		if (lastExpandedPosition != -1) {
			
			((PrayerSearchAdapter) getLvSearchResults().getAdapter()).expandPrayer(lastExpandedPosition);
			
		}
	}


	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("LAST_EXPANDED_GROUP", ((PrayerSearchAdapter) getLvSearchResults().getAdapter()).getLastExpandedPosition());
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {
		
		case R.id.menu_item_about:
			Intent aboutIntent = new Intent(this, AboutActivity.class);
			startActivity(aboutIntent);
			break;
		
		default:
			return super.onOptionsItemSelected(item);
		
		}
		
		return super.onOptionsItemSelected(item);
		
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public ListView getLvSearchResults() {
		return lvSearchResults;
	}

	public void setLvSearchResults(ListView lvSearchResults) {
		this.lvSearchResults = lvSearchResults;
	}

	public SQLiteDatabase getDatabase() {
		return database;
	}

	public void setDatabase(SQLiteDatabase database) {
		this.database = database;
	}

	public static String getPrayerTableName() {
		return PRAYER_TABLE_NAME;
	}

	public static String getPrayerId() {
		return PRAYER_ID;
	}

	public static String getIsFavorite() {
		return IS_FAVORITE;
	}
	
}
