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

package com.github.rutvijkumar.imagesearch.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.github.rutvijkumar.imagesearch.R;
import com.github.rutvijkumar.imagesearch.models.ImageResult;
import com.loopj.android.image.SmartImageView;

public class ImageResultAdapter extends ArrayAdapter<ImageResult> {

	public ImageResultAdapter(Context context,
			List<ImageResult>results) {
		super(context,R.xml.item_image_result,results);
		
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageResult imageResult=this.getItem(position);
		SmartImageView smartImageView;
		if(convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(getContext());
			smartImageView=(SmartImageView)inflater.inflate(R.xml.item_image_result, parent);
			
		}else {
			smartImageView=(SmartImageView)convertView;
			smartImageView.setImageResource(android.R.color.transparent);
		}
		smartImageView.setImageUrl(imageResult.getThumbUrl());
		return super.getView(position, convertView, parent);
	}

}
