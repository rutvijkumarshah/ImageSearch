package com.github.rutvijkumar.imagesearch.activities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.widget.ShareActionProvider;

import com.github.rutvijkumar.imagesearch.R;
import com.github.rutvijkumar.imagesearch.models.ImageResult;
import com.loopj.android.image.SmartImageView;

public class ImageDisplayActivity extends Activity {

	private ShareActionProvider shareActionProvider;
	SmartImageView ivImage;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_display);
		ImageResult imResult=(ImageResult)getIntent().getParcelableExtra("fullImageInfo");
		ivImage=(SmartImageView)findViewById(R.id.fullViewImage);
		ivImage.setImageUrl(imResult.getImageUrl());
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share_image_menu, menu);
        shareActionProvider = (ShareActionProvider) menu.findItem(R.id.menu_item_share).getActionProvider();
        shareActionProvider.setShareIntent(getDefaultShareIntent()); 
        return super.onCreateOptionsMenu(menu);
	}

	private Uri getImageUri() {
		Bitmap bitMap=((BitmapDrawable)ivImage.getDrawable()).getBitmap();
		Uri fileUrl = null;
	    try {
	    	String fileName = "imgSearchTemp"+ UUID.randomUUID() +".png";
	        File file =  new File(Environment.getExternalStoragePublicDirectory(  
	            Environment.DIRECTORY_DOWNLOADS), fileName);
	        FileOutputStream out = new FileOutputStream(file);
	        bitMap.compress(Bitmap.CompressFormat.PNG, 90, out);
	        out.close();
	        fileUrl = Uri.fromFile(file);
	    } catch (IOException e) {
	      Log.e("IMAGE_DISPAY",e.getLocalizedMessage(),e);
	    }
	    return fileUrl;
	}
	/** Returns a share intent */
    private Intent getDefaultShareIntent(){
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("image/*");
        shareIntent.putExtra(Intent.EXTRA_STREAM, getImageUri());
        return shareIntent;
        
    }
}
