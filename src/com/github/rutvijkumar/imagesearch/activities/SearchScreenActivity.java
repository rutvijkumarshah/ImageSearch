package com.github.rutvijkumar.imagesearch.activities;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Toast;

import com.etsy.android.grid.StaggeredGridView;
import com.github.rutvijkumar.imagesearch.R;
import com.github.rutvijkumar.imagesearch.adapters.ImageResultAdapter;
import com.github.rutvijkumar.imagesearch.api.CallBack;
import com.github.rutvijkumar.imagesearch.api.ImageProvider;
import com.github.rutvijkumar.imagesearch.api.ImageProviders;
import com.github.rutvijkumar.imagesearch.api.SearchFilter;
import com.github.rutvijkumar.imagesearch.fragments.SettingsDialog;
import com.github.rutvijkumar.imagesearch.helpers.EndlessScrollListener;
import com.github.rutvijkumar.imagesearch.models.ImageResult;

public class SearchScreenActivity extends FragmentActivity {

	private List<ImageResult> imageResults = new ArrayList<ImageResult>();
	private ImageResultAdapter adapter;
	//private GridView imagesGridView;
	private StaggeredGridView imagesGridView;
	private String searchKeyword;
	private SearchFilter filter=new SearchFilter();
	private SearchView searchView;

	
		@Override
    protected void onNewIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            newSearch(query);
        }
    }
	

	/****
	 * Todo
	 * @param context
	 */
	public static void closeKeyboard(Context context) {
	        try {
	            InputMethodManager inputManager = (InputMethodManager) ((Activity) context).getSystemService(Context.INPUT_METHOD_SERVICE);
	            inputManager.hideSoftInputFromWindow(((FragmentActivity) context).getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	        }
	        catch (Exception e) {
	            // TODO:ERROR CLOSING KEYBOARD
	        }
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS); 
		setContentView(R.layout.activity_search_screen);
		imagesGridView = (StaggeredGridView) findViewById(R.id.img_resultgrid);
		//imagesGridView = (GridView) findViewById(R.id.img_resultgrid);
		adapter = new ImageResultAdapter(this, imageResults);
		imagesGridView.setAdapter(adapter);
		imagesGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent fullDisplayIntent = new Intent(getApplicationContext(),
						ImageDisplayActivity.class);
				ImageResult imResult = imageResults.get(position);
				fullDisplayIntent.putExtra("fullImageInfo", imResult);
				startActivity(fullDisplayIntent);
			}
		});
		imagesGridView.setOnScrollListener(new EndlessScrollListener() {

			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				search(page);
			}
		});
	    Intent intent = getIntent();
	    if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
	      String query = intent.getStringExtra(SearchManager.QUERY);
	      Toast.makeText(this, "Voice Search : "+query, Toast.LENGTH_LONG).show();
	      newSearch(query);
	    }


	}

	private void search(int start) {
		
		setProgressBarIndeterminateVisibility(true);

		ImageProvider imageProvider = ImageProviders
				.getDefaultImageProvider(this.filter);
		imageProvider.searchImages(this.searchKeyword, start, new CallBack() {

			@Override
			public void onResult(boolean isSuccessful,
					List<ImageResult> images, JSONArray failureInfo) {
				setProgressBarIndeterminateVisibility(false);

				if (isSuccessful) {
					if (images != null && !images.isEmpty()) {
						adapter.addAll(images);
					}
				}else {
					Toast.makeText(SearchScreenActivity.this, "Network Connection is not available", Toast.LENGTH_LONG).show();
				}

			}
		});
	}

	public void OnFilterChanged(SearchFilter filter) {
		this.filter=filter;
		if(this.searchKeyword !=null && !this.searchKeyword.isEmpty()) {
			//Refresh Search
			newSearch(this.searchKeyword,true);
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == R.id.action_filter) {
			showFiltersDialoge();
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.searchview_in_menu, menu);
		MenuItem searchItem = menu.findItem(R.id.action_search);
		searchView = (SearchView) searchItem.getActionView();
		setupSearchView(searchView);
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
	    searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
	    
	 
		return true;
	}

	private void showFiltersDialoge() {
		FragmentManager fm = getSupportFragmentManager();
		SettingsDialog settingsDialog = SettingsDialog
				.newInstance("Search Filters");
		Bundle args=new Bundle();
		args.putSerializable("filter", filter);
		settingsDialog.setArguments(args);
		settingsDialog.show(fm, "filter_dialog");
	}

	private void newSearch(String keyword) {
		newSearch(keyword,false);

	}
	
	private void newSearch(String keyword,boolean isFilterChanged) {
		if (isFilterChanged || !keyword.equals(this.searchKeyword)) {
			adapter.clear();
			imageResults.clear();
			this.searchKeyword = keyword;
			search(0);
		}

	}

	private void setupSearchView(final SearchView searchView) {
		

		searchView.setOnQueryTextListener(new OnQueryTextListener() {

			@Override
			public boolean onQueryTextSubmit(String query) {
				boolean isValidSubmit = false;
				if (query != null && !query.isEmpty()) {
					newSearch(query);
					isValidSubmit = true;
				}

				return isValidSubmit;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				// TODO Auto-generated method stub
				return false;
			}
		});
	}

}
