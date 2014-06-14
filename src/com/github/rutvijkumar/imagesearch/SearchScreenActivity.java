package com.github.rutvijkumar.imagesearch;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

import com.github.rutvijkumar.imagesearch.adapters.ImageResultAdapter;
import com.github.rutvijkumar.imagesearch.api.CallBack;
import com.github.rutvijkumar.imagesearch.api.ImageProvider;
import com.github.rutvijkumar.imagesearch.api.ImageProviders;
import com.github.rutvijkumar.imagesearch.api.SearchFilter;
import com.github.rutvijkumar.imagesearch.models.ImageResult;

public class SearchScreenActivity extends Activity {

	private List<ImageResult> imageResults=new ArrayList<ImageResult>();
	private  ImageResultAdapter adapter;
	private GridView imagesGridView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_screen);
		imagesGridView=(GridView)findViewById(R.id.img_resultgrid);
		adapter=new ImageResultAdapter(this, imageResults);
		imagesGridView.setAdapter(adapter);
		imagesGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent fullDisplayIntent=new Intent(getApplicationContext(),ImageDisplayActivity.class);
				ImageResult imResult=imageResults.get(position);
				fullDisplayIntent.putExtra("fullImageInfo", imResult);
				startActivity(fullDisplayIntent);
			}
		});
		
	}
	
	private void search(String keyword) {
		ImageProvider imageProvider = ImageProviders.getDefaultImageProvider(new SearchFilter());
		imageProvider.searchImages(keyword, 0, 8, new CallBack() {
			
			@Override
			public void onResult(boolean isSuccessful, List<ImageResult> images,
					JSONArray failureInfo) {
				if(isSuccessful) {
					if(images!=null && !images.isEmpty()) {
						imageResults.clear();
						adapter.addAll(images);
					}
				}
				
			}
		});
	}
	
	   @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        super.onCreateOptionsMenu(menu);

	        MenuInflater inflater = getMenuInflater();
	        inflater.inflate(R.menu.searchview_in_menu, menu);
	        MenuItem searchItem = menu.findItem(R.id.action_search);
	        SearchView mSearchView = (SearchView) searchItem.getActionView();
	        setupSearchView(mSearchView);
	        return true;
	    }
	  


	private void setupSearchView(SearchView searchView) {
	
		searchView.setOnQueryTextListener(new OnQueryTextListener() {
			
			@Override
			public boolean onQueryTextSubmit(String query) {
				search(query);
				return true;
			}
			
			@Override
			public boolean onQueryTextChange(String newText) {
				// TODO Auto-generated method stub
				return false;
			}
		});
	}
	

}
