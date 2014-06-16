package com.github.rutvijkumar.imagesearch.activities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.widget.ShareActionProvider;
import android.widget.Toast;

import com.github.rutvijkumar.imagesearch.R;
import com.github.rutvijkumar.imagesearch.models.ImageResult;
import com.github.rutvijkumar.imagesearch.ui.ZoomInZoomOut;
import com.loopj.android.image.SmartImageTask.OnCompleteListener;
import com.loopj.android.image.SmartImageView;

public class ImageDisplayActivity extends Activity {

	private ShareActionProvider shareActionProvider;
	private SmartImageView ivImage;
	private ImageResult result;
	private Uri fileUrl;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_image_display);
		result = (ImageResult) getIntent().getParcelableExtra("fullImageInfo");
		ivImage = (SmartImageView) findViewById(R.id.fullViewImage);
		
		ivImage.setImageUrl(result.getImageUrl(),R.drawable.image_not_available,R.drawable.loading,new OnCompleteListener() {

			public void onComplete() {
				//Do Nothing
			}
			@Override
			public void onComplete(Bitmap bitmap) {
				// TODO Auto-generated method stub
				fileUrl=getImageUri(bitmap);
				ivImage.setOnTouchListener(new ZoomInZoomOut());
			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.share_image_menu, menu);
		shareActionProvider = (ShareActionProvider) menu.findItem(
				R.id.menu_item_share).getActionProvider();
		shareActionProvider.setShareIntent(getDefaultShareIntent());
		return super.onCreateOptionsMenu(menu);
	}


	private Uri getImageUri(Bitmap bitmap) {
		FileOutputStream out = null;
		Uri fileUri = null;
		if (bitmap != null) {
		
			try {
				String fileName = "imgSearchTemp" + UUID.randomUUID() + ".png";
				File file = new File(
						Environment
								.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
						fileName);
				out = new FileOutputStream(file);
				bitmap.compress(Bitmap.CompressFormat.PNG, 80, out);
				out.flush();
				out.close();
				fileUri = Uri.fromFile(file);
				Toast.makeText(this, "Size="+(file.length()/1024)+" KB", Toast.LENGTH_LONG).show();
			} catch (IOException e) {
				Log.e("IMAGE_DISPAY", e.getLocalizedMessage(), e);
			}
		}
		return fileUri;
	}

	/** Returns a share intent */
	private Intent getDefaultShareIntent() {
		Intent shareIntent = new Intent(Intent.ACTION_SEND);
		shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
		//shareIntent.setType("image/*");
		shareIntent.setType("image/png");
		shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Attached Image");
		shareIntent.putExtra(Intent.EXTRA_TEXT,"  ");
		shareIntent.putExtra(Intent.EXTRA_STREAM, fileUrl);
		return shareIntent;

	}
}
