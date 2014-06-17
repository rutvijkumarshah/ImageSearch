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
import java.util.Random;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.etsy.android.grid.util.DynamicHeightImageView;
import com.github.rutvijkumar.imagesearch.R;
import com.github.rutvijkumar.imagesearch.models.ImageResult;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class ImageResultAdapterStaggerd extends ArrayAdapter<ImageResult> {

	private static final String TAG = "ImageResultAdapterStaggerd";

	private final LayoutInflater mLayoutInflater;
	private final Random mRandom;
	private static final SparseArray<Double> sPositionHeightRatios = new SparseArray<Double>();

	private ImageLoader imgLoader=null;
	public ImageResultAdapterStaggerd(Context context, List<ImageResult> results) {

		super(context, R.xml.staggerd_item_image, results);
		this.mLayoutInflater = LayoutInflater.from(context);
		this.mRandom = new Random();
		initImageLoader();
	}

	private void initImageLoader() {
		imgLoader=ImageLoader.getInstance();
		imgLoader.init(ImageLoaderConfiguration.createDefault(getContext()));
	}

	@Override
	public View getView(final int position, View convertView,
			final ViewGroup parent) {

		ViewHolder vh;
		if (convertView == null) {
			convertView = mLayoutInflater.inflate(R.xml.staggerd_item_image,
					parent, false);
			vh = new ViewHolder();
			vh.imgView = (DynamicHeightImageView) convertView
					.findViewById(R.id.imgView);

			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}

		double positionHeight = getPositionRatio(position);

		vh.imgView.setHeightRatio(positionHeight);
		imgLoader.displayImage(getItem(position).getThumbUrl(), vh.imgView);
		//setImage(getItem(position).getImageUrl(), vh.imgView);
		return convertView;
	}

	static class ViewHolder {
		DynamicHeightImageView imgView;
	}

	private double getPositionRatio(final int position) {
		double ratio = sPositionHeightRatios.get(position, 0.0);
		// if not yet done generate and stash the columns height
		// in our real world scenario this will be determined by
		// some match based on the known height and width of the image
		// and maybe a helpful way to get the column height!
		if (ratio == 0) {
			ratio = getRandomHeightRatio();
			sPositionHeightRatios.append(position, ratio);
			//Log.d(TAG, "getPositionRatio:" + position + " ratio:" + ratio);
		}
		return ratio;
	}

	private double getRandomHeightRatio() {
		return (mRandom.nextDouble() / 2.0) + 1.0; // height will be 1.0 - 1.5
													// the width
	}
}
