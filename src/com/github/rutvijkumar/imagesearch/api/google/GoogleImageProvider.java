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

package com.github.rutvijkumar.imagesearch.api.google;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.Uri;
import android.util.Log;

import com.github.rutvijkumar.imagesearch.api.CallBack;
import com.github.rutvijkumar.imagesearch.api.ImageProvider;
import com.github.rutvijkumar.imagesearch.api.SearchFilter;
import com.github.rutvijkumar.imagesearch.api.types.ImageColor;
import com.github.rutvijkumar.imagesearch.api.types.ImageFileType;
import com.github.rutvijkumar.imagesearch.api.types.ImageSize;
import com.github.rutvijkumar.imagesearch.api.types.ImageType;
import com.github.rutvijkumar.imagesearch.models.ImageResult;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class GoogleImageProvider extends ImageProvider {

	private static String LOG_TAG="GOOGLE_IMG_SEARCH";
	private static final String BASE_URL="https://ajax.googleapis.com/ajax/services/search/images?v=1.0";
	private static final int PAGE_SIZE=8;

	public GoogleImageProvider(SearchFilter filter) {
		super(filter);
	}

	private String params(int start) {
		StringBuilder urlBuilder=new StringBuilder();
		ImageColor imgColor=filter.getImageColor();
		ImageFileType imageFileType = filter.getImageFileType();
		ImageSize imageSize = filter.getImageSize();
		ImageType imageType = filter.getImageType();
		String site = filter.getSite();
		
		urlBuilder.append("rsz="+PAGE_SIZE);
		urlBuilder.append("&start="+start*7+"&");
		
		if(imgColor !=null) {
			urlBuilder.append("imgcolor="+imgColor.toString().toLowerCase());
			urlBuilder.append("&");
		}
		
		if(imageFileType!=null) {
			urlBuilder.append("as_filetype="+imageFileType.toString().toLowerCase());
			urlBuilder.append("&");
		}
		
		if(imageSize!=null) {
			urlBuilder.append("imgsz="+imageSize.toString().toLowerCase());
			urlBuilder.append("&");
		}
		
		if(imageType!=null) {
			urlBuilder.append("imgtype="+imageType.toString().toLowerCase());
			urlBuilder.append("&");
		}
		
		if(site!=null) {
			urlBuilder.append("as_sitesearch="+site);
		}
		
		return urlBuilder.toString();
	}
	
	protected String generateSearchURL(String keyword, int start) {
		
		return BASE_URL+"&"+params(start)+"&q="+Uri.encode(keyword);
	}

	protected ImageResult parseJsonObject(JSONObject json) throws JSONException {
		ImageResult img=new ImageResult();
		img.setImageUrl(json.getString("url"));
		img.setThumbUrl(json.getString("tbUrl"));
		img.setTitle(json.getString("title"));
		return img;
	}
	
	
	
	@Override
	protected void process(final String url, final CallBack callback) {
		AsyncHttpClient client=new AsyncHttpClient();
		client.get(url, new JsonHttpResponseHandler() {
			
			@Override
			public void onSuccess(final JSONObject response) {
				
				JSONArray imageResults=null;
				List<ImageResult> results=null;
				try {
					
					if(response.has("responseData")) {
						JSONObject resData=response.getJSONObject("responseData");
						if(resData!=null && resData.has("results")) {
							imageResults=resData.getJSONArray("results");
							results = parseImageResults(imageResults);
						}
					}
					callback.onResult(true, results, null);
				} catch (JSONException e) {
					Log.e(LOG_TAG,"URL="+url);
					Log.e(LOG_TAG,"Response=+"+response.toString());
					Log.e(LOG_TAG, "Exception while parsing google image response", e);
				}
			}
			
			@Override
			public void onFailure(int statusCode, Throwable e,
					JSONArray errorResponse) {
				//While testing I found onFailure is not getting fired at all 
				//even after simulating no network connection with airplane mode I dont see this call back getting executed.
				 callback.onResult(false,null, errorResponse);
			}
		});
	}

	@Override
	protected int maxPagesSupported() {
		// TODO Auto-generated method stub
		return 8;
	}

}
