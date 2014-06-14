package com.github.rutvijkumar.imagesearch.activities;

import android.app.Activity;
import android.os.Bundle;

import com.github.rutvijkumar.imagesearch.R;
import com.github.rutvijkumar.imagesearch.models.ImageResult;
import com.loopj.android.image.SmartImageView;

public class ImageDisplayActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_display);
		ImageResult imResult=(ImageResult)getIntent().getParcelableExtra("fullImageInfo");
		SmartImageView ivImage=(SmartImageView)findViewById(R.id.fullViewImage);
		ivImage.setImageUrl(imResult.getImageUrl());
	}
}
