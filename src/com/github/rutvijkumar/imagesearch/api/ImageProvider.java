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

package com.github.rutvijkumar.imagesearch.api;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.github.rutvijkumar.imagesearch.models.ImageResult;


public abstract class ImageProvider {
	
	protected SearchFilter filter;
	
	public ImageProvider(SearchFilter filter) {
		this.filter=filter;
	}
	public void updateSearchFilter(SearchFilter filter) {
		this.filter=filter;
	}
	public void searchImages(String keyword,int start, CallBack callback) {
		Log.d("IMAGE_PROVIDER","start="+start);
		String url =generateSearchURL(keyword, start);
		Log.d("IMAGE_PROVIDER","url="+url);
		process(url,callback);
	}
	protected List<ImageResult> parseImageResults(JSONArray jsonResults) throws JSONException {
		final int resultLength=jsonResults.length();
		ArrayList<ImageResult> results=new ArrayList<ImageResult>(resultLength);
		for(int i=0;i < resultLength;i++) {
			results.add(parseJsonObject(jsonResults.getJSONObject(i)));
		}
		
		return results;
	}
	abstract protected ImageResult parseJsonObject(JSONObject jsonObj) throws JSONException;
	abstract protected String generateSearchURL(String keyword,int start);
	abstract protected void process(String url,CallBack callback);
}
