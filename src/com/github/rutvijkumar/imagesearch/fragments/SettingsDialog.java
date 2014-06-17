/***

The MIT License (MIT)
Copyright © 2014 Rutvijkumar Shah
 
Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
associated documentation files (the “Software”), to deal in the Software without restriction,
including without limitation the rights to use, copy, modify, merge, publish, distribute,
sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:
 
The above copyright notice and this permission notice shall be included in all copies or substantial
portions of the Software.
 
THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

***/

package com.github.rutvijkumar.imagesearch.fragments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.rutvijkumar.imagesearch.R;
import com.github.rutvijkumar.imagesearch.activities.SearchScreenActivity;
import com.github.rutvijkumar.imagesearch.api.SearchFilter;
import com.github.rutvijkumar.imagesearch.api.types.ImageColor;
import com.github.rutvijkumar.imagesearch.api.types.ImageFileType;
import com.github.rutvijkumar.imagesearch.api.types.ImageSize;
import com.github.rutvijkumar.imagesearch.api.types.ImageType;

public class SettingsDialog extends DialogFragment  {

	private Spinner _imageSize;
	private Spinner _imageColor;
	private Spinner _imageFileType;
	private Spinner _imageType;
	private EditText _site;
	private TextView _cancle;
	private TextView _search;
	private SearchFilter _filter=null;
	
	private static String[] colors= {"Any","Black","Blue","Brown","Gray","Green","Orange","Pink","Purple","Red","Teal","White","Yellow"};
	
	public static HashMap<String, Integer> colorMap=new LinkedHashMap<String,Integer>();
	
	private void initColorMap() {
			
	     		 colorMap.put("Black",R.color.Black);
	    	     colorMap.put("Blue",R.color.Blue);
	    	     colorMap.put("Brown",R.color.Brown);
	    	     colorMap.put("Gray",R.color.Gray);
	    	     colorMap.put("Green",R.color.Green);
	    	     colorMap.put("Orange",R.color.Orange);
	    	     colorMap.put("Pink",R.color.Pink);
	    	     colorMap.put("Purple",R.color.Purple);
	    	     colorMap.put("Red",R.color.Red);
	    	     colorMap.put("Teal",R.color.Teal);
	    	     colorMap.put("White",R.color.White);
	    	     colorMap.put("Yellow",R.color.Yellow);
	    	     
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		initColorMap();
		View view = inflater.inflate(R.layout.filter_dialog, container);
		
		_imageSize=(Spinner) view.findViewById(R.id.spinner_imageSize);
		_imageColor=(Spinner) view.findViewById(R.id.spinner_imageColor);
		
		_imageFileType=(Spinner) view.findViewById(R.id.spinner_imageFileType);
		_imageType=(Spinner) view.findViewById(R.id.spinner_imageType);
		_site=(EditText)view.findViewById(R.id.et_site);
		_cancle=(TextView)view.findViewById(R.id.btnCancel);
		_search=(TextView)view.findViewById(R.id.btnSearch);
		_filter=(SearchFilter)getArguments().getSerializable("filter");
		
		List<String> list =new ArrayList<String>(Arrays.asList(colors));
		ColorSpinnerAdapter dataAdapter = new ColorSpinnerAdapter(getActivity(),list);
		_imageColor.setAdapter(dataAdapter);
		
		setActionsListeners();
		setSelectedPosition();
		getDialog().setTitle("Search Filters");
		return view;
	}

	private void setActionsListeners() {
		_cancle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dismiss();
			}
		});
		_search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				saveFilters();
				
			}
		});
	}
	
	private void setSelectedPosition() {
		
		ImageSize size = _filter.getImageSize();
		ImageColor color=_filter.getImageColor();
		ImageFileType fileType=_filter.getImageFileType();
		ImageType type=_filter.getImageType();
		String site=_filter.getSite();
		
		if(size!=null) {
			_imageSize.setSelection(ImageSize.getPosition(size.toString()));
		}
		if(color !=null) {
			_imageColor.setSelection(ImageColor.getPosition(color.toString()));
		}
		if(fileType!=null) {
			_imageFileType.setSelection(ImageFileType.getPosition(fileType.toString()));
		}
		if(type!=null) {
			_imageType.setSelection(ImageType.getPosition(type.toString()));
		}
		if(site!=null) {
			_site.setText(site);
		}
		
	}
	
	private void saveFilters() {
		
		SearchFilter filter=new SearchFilter();
		filter.imageSize(ImageSize.getValueOf(_imageSize.getSelectedItem().toString()));
		filter.imageColor(ImageColor.getValueOf(_imageColor.getSelectedItem().toString()));
		filter.imageFileType(ImageFileType.getValueOf(_imageFileType.getSelectedItem().toString()));
		filter.imageType(ImageType.getValueOf(_imageType.getSelectedItem().toString()));
		String siteVal=_site.getText().toString();
		if(siteVal !=null || !siteVal.isEmpty()) {
			filter.site(siteVal);
		}
		
		SearchScreenActivity callingActivity = (SearchScreenActivity) getActivity();
        callingActivity.OnFilterChanged(filter);
        dismiss();
		
	}
	public static SettingsDialog newInstance(String title) {
		SettingsDialog frag = new SettingsDialog();
		Bundle args = new Bundle();
		frag.setArguments(args);
		return frag;
	}
	
	 class ColorSpinnerAdapter extends ArrayAdapter<String> {
	    public ColorSpinnerAdapter(Context context, List<String> values) {
	       super(context, android.R.layout.simple_spinner_dropdown_item, values);
	    }

	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	       // Get the data item for this position
	       String itemVal = getItem(position);    
	       // Check if an existing view is being reused, otherwise inflate the view
	       if (convertView == null) {
	          convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
	       }
	       TextView txView=(TextView) convertView;
	       String colorId=colors[position];
	       if(!colorId.equals("Any")) {
	    	   int colorVal=getResources().getColor(colorMap.get(colorId));
		       txView.setTextColor(colorVal);
		       txView.setBackgroundColor(colorVal);
	       }
	       
	       return convertView;
	   }
	}
}
