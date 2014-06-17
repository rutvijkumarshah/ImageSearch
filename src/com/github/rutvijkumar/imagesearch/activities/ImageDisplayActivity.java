package com.github.rutvijkumar.imagesearch.activities;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ShareActionProvider;
import android.widget.Toast;

import com.github.rutvijkumar.imagesearch.R;
import com.github.rutvijkumar.imagesearch.models.ImageResult;
import com.github.rutvijkumar.imagesearch.ui.ZoomInZoomOut;
import com.github.rutvijkumar.imagesearch.util.Util;
import com.loopj.android.image.SmartImageTask.OnCompleteListener;
import com.loopj.android.image.SmartImageView;

public class ImageDisplayActivity extends Activity {

	private ShareActionProvider shareActionProvider;
	private Uri fileUrl;
	private String appName = null;
	private Bitmap bitmap = null;

	private final String IMG_BITMAP = "IMG_BITMAP";
	private final String IMG_URL = "IMG_URL";
	private final String APP_NAME = "APP_NAME";

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putParcelable(IMG_BITMAP, bitmap);
		outState.putParcelable(IMG_URL, fileUrl);
		outState.putString(APP_NAME, appName);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

		boolean isSavedState = (savedInstanceState != null);

		ImageResult result = null;
		appName = getResources().getString(R.string.app_name);
		setContentView(R.layout.activity_image_display);

		result = (ImageResult) getIntent().getParcelableExtra("fullImageInfo");
		final SmartImageView ivImage = (SmartImageView) findViewById(R.id.fullViewImage);
		if (isSavedState) {
			// screen rotated, no need to make Network call using already loaded
			// Bitmap
			bitmap = (Bitmap) savedInstanceState.getParcelable(IMG_BITMAP);
			fileUrl = (Uri) savedInstanceState.getParcelable(IMG_URL);
			appName = savedInstanceState.getString(APP_NAME);
			ivImage.setImageBitmap(bitmap);

		} else {

			if (!Util.isNetworkAvailable(this)) {
				Toast.makeText(this, R.string.toast_nw_unavailable,
						Toast.LENGTH_LONG).show();
				return;
			} else {
				// Freshly created Activity and Network Connection is available
				setProgressBarIndeterminateVisibility(true);
				ivImage.setImageUrl(result.getImageUrl(),
						R.drawable.image_not_available,
						android.R.color.transparent, new OnCompleteListener() {

							public void onComplete() {
								// Do Nothing
							}

							@Override
							public void onComplete(Bitmap bitMap) {
								fileUrl = getImageUri(bitMap);
								if (shareActionProvider != null) {
									/***
									 * in case bit map is very small it quickly
									 * loads so by that time shareActionProvider
									 * is not initialized
									 */
									shareActionProvider
											.setShareIntent(getDefaultShareIntent());
								}
								bitmap = bitMap;
								ivImage.setOnTouchListener(new ZoomInZoomOut());
								setProgressBarIndeterminateVisibility(false);	
							}

						});
			}

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.share_image_menu, menu);
		shareActionProvider = (ShareActionProvider) menu.findItem(
				R.id.menu_item_share).getActionProvider();
		shareActionProvider.setShareIntent(getDefaultShareIntent());
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_download) {
			showDownload();
		}
		return super.onOptionsItemSelected(item);
	}

	/****
	 * All displayed file are download implicitly in order to make them
	 * shareable with other applications. In case of download action just need
	 * to display path of downloaded file
	 * 
	 * 
	 */
	private void showDownload() {
		Toast.makeText(this, getResources().getString(R.string.toast_file_downloaded_at) + fileUrl,
				Toast.LENGTH_LONG).show();
	}

	private String getFileName(String title, Bitmap bitmap) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 80, bos);
		bos.flush();
		String md5Sum = Util.getMD5Sum(bos);
		StringBuilder sb = new StringBuilder();
		sb.append(title).append("_").append(md5Sum).append(".").append("png");

		return sb.toString();
	}

	private Uri getImageUri(Bitmap bitmap) {
		FileOutputStream out = null;
		Uri fileUri = null;
		if (bitmap != null) {
			try {

				String fileName = getFileName(appName, bitmap);
				File file = new File(
						Environment
								.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
						fileName);
				if (file.exists()) {
					fileUri = Uri.fromFile(file);
				} else {
					out = new FileOutputStream(file);
					bitmap.compress(Bitmap.CompressFormat.PNG, 80, out);
					out.flush();
					out.close();
					fileUri = Uri.fromFile(file);
				}
				Toast.makeText(this, Util.getFileSizeString(file.length()),
						Toast.LENGTH_LONG).show();
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
		shareIntent.setType("image/png");
		shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Attached Image");
		shareIntent.putExtra(Intent.EXTRA_TEXT, "  ");
		shareIntent.putExtra(Intent.EXTRA_STREAM, fileUrl);
		return shareIntent;

	}
}
