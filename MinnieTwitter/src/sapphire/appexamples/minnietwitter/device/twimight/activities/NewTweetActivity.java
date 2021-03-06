/*******************************************************************************
 * Copyright (c) 2011 ETH Zurich.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Paolo Carta - Implementation
 *     Theus Hossmann - Implementation
 *     Dominik Schatzmann - Message specification
 ******************************************************************************/

package sapphire.appexamples.minnietwitter.device.twimight.activities;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import dalvik.agate.PolicyManagementModule;

import sapphire.appexamples.minnietwitter.device.twimight.R;
import sapphire.appexamples.minnietwitter.device.twimight.data.StatisticsDBHelper;
import sapphire.appexamples.minnietwitter.device.twimight.location.LocationHelper;
import sapphire.appexamples.minnietwitter.device.twimight.net.twitter.Tweets;
import sapphire.appexamples.minnietwitter.device.twimight.net.twitter.TwitterService;
import sapphire.appexamples.minnietwitter.device.twimight.util.Constants;
import sapphire.appexamples.minnietwitter.device.twimight.util.SDCardHelper;
import sapphire.appexamples.minnietwitter.device.twimight.views.CameraSurfaceView;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
// end WITH_SAPPHIRE_AGATE

/**
 * The activity to write a new tweet.
 * @author thossmann
 * @author pcarta
 */
public class NewTweetActivity extends Activity implements PictureCallback {

	private static final String TAG = "TweetActivity";

	private boolean useLocation;
	private EditText text;
	private TextView characters;
	private Button cancelButton;
	private Button sendButton;

	private long isReplyTo;

	// the following are all to deal with location
	private ImageButton locationButton;

	private boolean locationChecked;
	private TextWatcher textWatcher;

	//uploading photos
	private static final int PICK_FROM_CAMERA = 1;
	private static final int PICK_FROM_FILE = 2;
	private String tmpPhotoPath; //path storing photos on SDcard
	private String finalPhotoPath; //path storing photos on SDcard
	private String finalPhotoName; //file name of uploaded photo
	private Uri tmpPhotoUri; //uri storing temp photos
	private Uri photoUri; //uri storing photos
	private ImageView mImageView; //to display the photo to be uploaded

	private boolean hasMedia = false;
	private ImageButton uploadFromGallery;
	private ImageButton uploadFromCamera;
	private ImageButton deletePhoto;
	private ImageButton previewPhoto;
	private ImageButton photoButton;
	private Button shutterButton;
	private Bitmap photo = null;
	private LinearLayout photoLayout;

	private FrameLayout surfacePreview;
	private LinearLayout takePhotoLayout;
	private CameraSurfaceView cameraSurfaceView;

	//SDcard helper
	private SDCardHelper sdCardHelper;

	//LOGS
	LocationHelper locHelper ;
	long timestamp;		
	ConnectivityManager cm;
	/** 
	 * Called when the activity is first created. 
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tweet);

		cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);			
		locHelper = LocationHelper.getInstance(this);		

		//SDCard helper
		sdCardHelper = new SDCardHelper();		
		setupBasicButtons();

		characters = (TextView) findViewById(R.id.tweet_characters);
		characters.setText(Integer.toString(Constants.TWEET_LENGTH));

		text = (EditText) findViewById(R.id.tweetText);		

		// Did we get some extras in the intent?
		Intent i = getIntent();
		if(i.hasExtra("text")){
			text.setText(Html.fromHtml("<i>"+i.getStringExtra("text")+"</i>"));
		}
		if(text.getText().length()==0){
			sendButton.setEnabled(false);
		}

		if(text.getText().length()>Constants.TWEET_LENGTH){
			text.setText(text.getText().subSequence(0, Constants.TWEET_LENGTH));
			text.setSelection(text.getText().length());
			characters.setTextColor(Color.RED);
		}

		characters.setText(Integer.toString(Constants.TWEET_LENGTH-text.getText().length()));

		if(i.hasExtra("isReplyTo")){
			isReplyTo = i.getLongExtra("isReplyTo", 0);
		}

		// This makes sure we do not enter more than 140 characters	
		textWatcher = new TextWatcher(){
			public void afterTextChanged(Editable s){
				int nrCharacters = Constants.TWEET_LENGTH-text.getText().length();

				if(nrCharacters < 0){
					text.setText(text.getText().subSequence(0, Constants.TWEET_LENGTH));
					text.setSelection(text.getText().length());
					nrCharacters = Constants.TWEET_LENGTH-text.getText().length();
				}

				if(nrCharacters <= 0){
					characters.setTextColor(Color.RED);
				} else {
					characters.setTextColor(Color.BLACK);
				}

				if(nrCharacters == Constants.TWEET_LENGTH){
					sendButton.setEnabled(false);
				} else {
					sendButton.setEnabled(true);
				}

				characters.setText(Integer.toString(nrCharacters));

			}
			public void  beforeTextChanged(CharSequence s, int start, int count, int after){}
			public void  onTextChanged (CharSequence s, int start, int before,int count) {} 
		};
		text.addTextChangedListener(textWatcher);
		text.setSelection(text.getText().length());	

		setupImageRelatedButtons();	
	}

	private void setupImageRelatedButtons(){

		//uploading photos
		tmpPhotoPath = Tweets.PHOTO_PATH + "/" + "tmp";
		finalPhotoPath = Tweets.PHOTO_PATH + "/" + LoginActivity.getTwitterId();
		mImageView = new ImageView(this);

		photoLayout = (LinearLayout) findViewById(R.id.linearLayout_photo_view);
		photoLayout.setVisibility(View.GONE);

		takePhotoLayout = (LinearLayout) findViewById(R.id.linearLayout_take_photo);
		takePhotoLayout.setVisibility(View.GONE);

		surfacePreview = (FrameLayout) findViewById(R.id.camera_preview);

		// used to take photo
		shutterButton = (Button) findViewById(R.id.shutter_button);
		shutterButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				takePicture();
			}
		});

		uploadFromGallery = (ImageButton) findViewById(R.id.upload_from_gallery);
		uploadFromGallery.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				uploadFromGallery();
			}
		});

		uploadFromCamera = (ImageButton) findViewById(R.id.upload_from_camera);
		uploadFromCamera.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				uploadFromCamera();
			}
		});

		previewPhoto = (ImageButton) findViewById(R.id.preview_photo);
		previewPhoto.setOnClickListener(new OnClickListener(){
			public void onClick(View v){

				mImageView = new ImageView(NewTweetActivity.this);
				mImageView.setImageBitmap(photo);
				AlertDialog.Builder photoPreviewDialog = new AlertDialog.Builder(NewTweetActivity.this);
				photoPreviewDialog.setView(mImageView);
				photoPreviewDialog.setNegativeButton("close",null);
				photoPreviewDialog.show();
			}
		});

		deletePhoto = (ImageButton) findViewById(R.id.delete_photo);
		deletePhoto.setOnClickListener(new OnClickListener(){
			public void onClick(View v){

				sdCardHelper.deleteFile(tmpPhotoUri.getPath());
				hasMedia = false;
				setButtonStatus(true,false);
			}		
		});

		String[] filePaths = {tmpPhotoPath, finalPhotoPath};
		if(sdCardHelper.checkSDState(filePaths)){

			sdCardHelper.clearTempDirectory(tmpPhotoPath);
			setButtonStatus(true,false);
		}
		else setButtonStatus(false,false);
	}


	private void setupBasicButtons() {

		cancelButton = (Button) findViewById(R.id.tweet_cancel);
		cancelButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {				
				finish();		
			}

		});

		sendButton = (Button) findViewById(R.id.tweet_send);
		sendButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				new SendTweetTask().execute();
			}

		});

		photoButton = (ImageButton) findViewById(R.id.tweet_photo);		
		photoButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if(photoLayout.getVisibility() == View.GONE){
					photoLayout.setVisibility(View.VISIBLE);
					//photoButton.setImageResource(R.drawable.ic_menu_gallery_on);
				}
				else{
					photoLayout.setVisibility(View.GONE);
					//photoButton.setImageResource(R.drawable.ic_menu_gallery);
				}
			}
		});

		// User settings: do we use location or not?

		useLocation = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("prefUseLocation", Constants.TWEET_DEFAULT_LOCATION);

		locationButton = (ImageButton) findViewById(R.id.tweet_location);
		locationChecked = false;

		if(useLocation){
			locationButton.setImageResource(R.drawable.ic_menu_mylocation_on);
			locationChecked = true;
		}

		locationButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if(!locationChecked){

					locHelper.registerLocationListener();
					Toast.makeText(NewTweetActivity.this, getString(R.string.location_on), Toast.LENGTH_SHORT).show();
					locationButton.setImageResource(R.drawable.ic_menu_mylocation_on);
					locationChecked = true;

				} else {

					locHelper.unRegisterLocationListener();
					Toast.makeText(NewTweetActivity.this, getString(R.string.location_off), Toast.LENGTH_SHORT).show();
					locationButton.setImageResource(R.drawable.ic_menu_mylocation);
					locationChecked = false;
				}
			}
		});
	}

	/**
	 * set button status with different operations
	 * 
	 * @param statusUpload
	 * @param statusDelete
	 */
	private void setButtonStatus(boolean statusUpload, boolean statusDelete){
		uploadFromGallery.setEnabled(statusUpload);
		uploadFromCamera.setEnabled(statusUpload);
		deletePhoto.setEnabled(statusDelete);
		previewPhoto.setEnabled(statusDelete);
		if(statusUpload){
			uploadFromGallery.setImageResource(R.drawable.ic_menu_slideshow);
			uploadFromCamera.setImageResource(R.drawable.ic_camera);
		}else{
			uploadFromGallery.setImageResource(R.drawable.ic_menu_slideshow_off);
			uploadFromCamera.setImageResource(R.drawable.ic_camera_off);
		}
		if(statusDelete){
			deletePhoto.setImageResource(R.drawable.ic_menu_delete);
			previewPhoto.setImageResource(R.drawable.ic_menu_zoom);
		}else{
			deletePhoto.setImageResource(R.drawable.ic_menu_delete_off);
			previewPhoto.setImageResource(R.drawable.ic_menu_zoom_off);
		}
	}

	/**
	 * onResume
	 */
	@Override
	public void onResume(){
		super.onResume();
		if(useLocation){
			locHelper.registerLocationListener();
		}
	}

	/**
	 * onPause
	 */
	@Override
	public void onPause(){
		super.onPause();
		locHelper.unRegisterLocationListener();
	}

	/**
	 * On Destroy
	 */
	@Override
	public void onDestroy(){
		super.onDestroy();
		Log.d(TAG, "onDestroy");
		if(hasMedia){
			sdCardHelper.deleteFile(tmpPhotoUri.getPath());
			hasMedia = false;
		}
		if (locHelper!= null) 
			locHelper.unRegisterLocationListener();	

		locationButton.setOnClickListener(null);
		locationButton = null;

		cancelButton.setOnClickListener(null);
		cancelButton = null;

		sendButton.setOnClickListener(null);
		sendButton = null;

		text.removeTextChangedListener(textWatcher);		
		textWatcher = null;

		TwimightBaseActivity.unbindDrawables(findViewById(R.id.showNewTweetRoot));
	}

	/**	
	 * Checks whether we are in disaster mode and inserts the content values into the content provider.
	 *
	 * @author pcarta
	 *
	 */
	private class SendTweetTask extends AsyncTask<Void, Void, Boolean>{

		Uri insertUri = null;
		StatisticsDBHelper statsDBHelper;	

		@Override
		protected Boolean doInBackground(Void... params) {
			boolean result=false;
			long start = System.currentTimeMillis();

			//Statistics
			statsDBHelper = new StatisticsDBHelper(getApplicationContext());
			statsDBHelper.open();
			timestamp = System.currentTimeMillis();

			if(hasMedia){
				try {
					finalPhotoName = "twimight" + String.valueOf(timestamp) + ".jpg";
					photoUri = Uri.fromFile(sdCardHelper.getFileFromSDCard(finalPhotoPath, finalPhotoName));//photoFileParent, photoFilename));
					String fromFile = tmpPhotoUri.getPath();
					String toFile = photoUri.getPath();
					//if (TwimightBaseActivity.D) Log.i(TAG, fromFile);
					//if (TwimightBaseActivity.D) Log.i(TAG, toFile);
					if(sdCardHelper.copyFile(fromFile, toFile)){
						//Log.w(TAG, "File copy successfuly to " + photoUri.getPath());
						//if (TwimightBaseActivity.D) Log.i(TAG, "file copy successfuly to " + photoUri.getPath());
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					if (TwimightBaseActivity.D) Log.d("photo", "exception!!!!!");
					e.printStackTrace();
				}
			}
			// if no connectivity, notify user that the tweet will be send later		
			ContentValues cv = createContentValues(); 

			if(PreferenceManager.getDefaultSharedPreferences(NewTweetActivity.this).getBoolean("prefDisasterMode", false) == true){
				// our own tweets go into the my disaster tweets buffer
				cv.put(Tweets.COL_BUFFER, Tweets.BUFFER_TIMELINE|Tweets.BUFFER_MYDISASTER);

				insertUri = getContentResolver().insert(Uri.parse("content://" + Tweets.TWEET_AUTHORITY + "/" + Tweets.TWEETS + "/" 
						+ Tweets.TWEETS_TABLE_TIMELINE + "/" + Tweets.TWEETS_SOURCE_DISASTER), cv);
				getContentResolver().notifyChange(Tweets.TABLE_TIMELINE_URI, null);
			} else {
				// our own tweets go into the timeline buffer
				cv.put(Tweets.COL_BUFFER, Tweets.BUFFER_TIMELINE);
				//we publish on twitter directly only normal tweets
				cv.put(Tweets.COL_FLAGS, Tweets.FLAG_TO_INSERT);	

				insertUri = getContentResolver().insert(Uri.parse("content://" + Tweets.TWEET_AUTHORITY + "/" + Tweets.TWEETS + "/" + 
						Tweets.TWEETS_TABLE_TIMELINE + "/" + Tweets.TWEETS_SOURCE_NORMAL), cv);
				getContentResolver().notifyChange(Tweets.TABLE_TIMELINE_URI, null);
				//getContentResolver().notifyChange(insertUri, null);
				ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
				if(cm.getActiveNetworkInfo()==null || !cm.getActiveNetworkInfo().isConnected()){
					result=true;
				}
			}
			if (locHelper.getCount() > 0 && cm.getActiveNetworkInfo()!= null) {	
				//Log.i(TAG,"writing log");
				statsDBHelper.insertRow(locHelper.getLocation(), cm.getActiveNetworkInfo().getTypeName(), 
						StatisticsDBHelper.TWEET_WRITTEN, null, timestamp);
				locHelper.unRegisterLocationListener();
				//Log.i(TAG, String.valueOf(hasMedia));
			}
			long stop = System.currentTimeMillis();
			System.out.println("Time to put photo in file: " + (stop - start) + " ms");
			return result;
		}

		@Override
		protected void onPostExecute(Boolean result){
			long start = System.currentTimeMillis();
			if (result)
				Toast.makeText(NewTweetActivity.this, getString(R.string.no_connection4), Toast.LENGTH_SHORT).show();

			if(insertUri != null){
				// schedule the tweet for uploading to twitter
				Intent i = new Intent(NewTweetActivity.this, TwitterService.class);
				i.putExtra("synch_request", TwitterService.SYNCH_TWEET);
				i.putExtra("rowId", new Long(insertUri.getLastPathSegment()));
				startService(i);
			}
			finish();
			long stop = System.currentTimeMillis();
			System.out.println("Time to start service to send tweet: " + (stop - start) + " ms");
		}
	}

	/**
	 * Prepares the content values of the tweet for insertion into the DB.
	 * @return
	 */
	private ContentValues createContentValues() {
		ContentValues tweetContentValues = new ContentValues();

		tweetContentValues.put(Tweets.COL_TEXT, text.getText().toString());
		tweetContentValues.put(Tweets.COL_TEXT_PLAIN, text.getText().toString());
		tweetContentValues.put(Tweets.COL_USER, LoginActivity.getTwitterId());
		tweetContentValues.put(Tweets.COL_SCREENNAME, LoginActivity.getTwitterScreenname(this));
		if (isReplyTo > 0) {
			tweetContentValues.put(Tweets.COL_REPLYTO, isReplyTo);
		}	
		// set the current timestamp
		tweetContentValues.put(Tweets.COL_CREATED, System.currentTimeMillis());
		if(useLocation){
			Location loc = locHelper.getLocation();
			if(loc!=null){
				tweetContentValues.put(Tweets.COL_LAT, loc.getLatitude());
				tweetContentValues.put(Tweets.COL_LNG, loc.getLongitude());
			}
		}
		//if there is a photo, put the path of photo in the cv
		if (hasMedia){
			tweetContentValues.put(Tweets.COL_MEDIA, finalPhotoName);
			//Log.i(TAG, Tweets.COL_MEDIA + ":" + finalPhotoName);
		}
		return tweetContentValues;
	}

	//methods photo uploading

	/**
	 * upload photo from camera
	 */
	/*	private void uploadFromCamera() {

		if((tmpPhotoUri = sdCardHelper.createTmpPhotoStoragePath(tmpPhotoPath)) != null){
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

			intent.putExtra(MediaStore.EXTRA_OUTPUT, tmpPhotoUri);

			try {
				intent.putExtra("return-data", true);
				startActivityForResult(intent, PICK_FROM_CAMERA);
			} 
			catch (ActivityNotFoundException e) {
				e.printStackTrace();
			}
		}
		else{
			Log.i(TAG, "path for storing photos cannot be created!");
			setButtonStatus(false, false);
		}
	}
	 */

	// begin WITH_SAPPHIRE_AGATE
	private void uploadFromCamera() {
		if((tmpPhotoUri = sdCardHelper.createTmpPhotoStoragePath(tmpPhotoPath)) != null){
			takePhotoLayout.setVisibility(View.VISIBLE);
			cameraSurfaceView = new CameraSurfaceView(this);
			surfacePreview.addView(cameraSurfaceView);
		}
		else{
			Log.i(TAG, "path for storing photos cannot be created!");
			setButtonStatus(false, false);
		}

	}
	// end WITH_SAPPHIRE_AGATE

	private void takePicture() {
		shutterButton.setEnabled(false);
		cameraSurfaceView.takePicture(this);
	}

	@Override
	public void onPictureTaken(byte[] data, Camera camera) {
		//System.out.println("Policy on image = 0x" + Integer.toHexString(PolicyManagementModule.getPolicyByteArray(data)));

		photo = makeBitmap(data, 50 * 1024);
		//System.out.println("Policy on photo = 0x" + Integer.toHexString(photo.getPolicy()));
		photo = rotate(photo, -90);
		mImageView.setImageBitmap(photo);

		//System.out.println("Size of picture: " + data.length);

		// Save photo to file
		OutputStream outputStream = null;
		try {
			outputStream = getContentResolver().openOutputStream(tmpPhotoUri);
			outputStream.write(data);
			outputStream.close();
		} catch (IOException ex) {
			// ignore exception
			Log.e(TAG, "Could not open temp file to store photo");
		} finally {
			// close silently
			if (outputStream != null)
				try {
					outputStream.close();
				} catch (Throwable t) {
					// do nothing
				}
		}

		surfacePreview.removeView(cameraSurfaceView);
		takePhotoLayout.setVisibility(View.GONE);
		shutterButton.setEnabled(true);
		
		setButtonStatus(false,true);
		hasMedia = true;
	}

	public static Bitmap makeBitmap(byte[] jpegData, int maxNumOfPixels) {
		try {
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeByteArray(jpegData, 0, jpegData.length,
					options);
			if (options.mCancel || options.outWidth == -1
					|| options.outHeight == -1) {
				return null;
			}
			options.inSampleSize = computeSampleSize(
					options, -1, maxNumOfPixels);
			options.inJustDecodeBounds = false;

			options.inDither = false;
			options.inPreferredConfig = Bitmap.Config.ARGB_8888;
			return BitmapFactory.decodeByteArray(jpegData, 0, jpegData.length,
					options);
		} catch (OutOfMemoryError ex) {
			Log.e(TAG, "Got oom exception ", ex);
			return null;
		}
	}

	public static int computeSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength,
				maxNumOfPixels);

		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}

		return roundedSize;
	}

	private static int computeInitialSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;

		int lowerBound = (maxNumOfPixels < 0) ? 1 :
			(int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength < 0) ? 128 :
			(int) Math.min(Math.floor(w / minSideLength),
					Math.floor(h / minSideLength));

		if (upperBound < lowerBound) {
			// return the larger one when there is no overlapping zone.
			return lowerBound;
		}

		if (maxNumOfPixels < 0 && minSideLength < 0) {
			return 1;
		} else if (minSideLength < 0) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}

	// Rotates the bitmap by the specified degree.
	// If a new bitmap is created, the original bitmap is recycled.
	public static Bitmap rotate(Bitmap b, int degrees) {
		return rotateAndMirror(b, degrees, false);
	}

	// Rotates and/or mirrors the bitmap. If a new bitmap is created, the
	// original bitmap is recycled.
	public static Bitmap rotateAndMirror(Bitmap b, int degrees, boolean mirror) {
		if ((degrees != 0 || mirror) && b != null) {
			Matrix m = new Matrix();
			// Mirror first.
			// horizontal flip + rotation = -rotation + horizontal flip
			if (mirror) {
				m.postScale(-1, 1);
				degrees = (degrees + 360) % 360;
				if (degrees == 0 || degrees == 180) {
					m.postTranslate(b.getWidth(), 0);
				} else if (degrees == 90 || degrees == 270) {
					m.postTranslate(b.getHeight(), 0);
				} else {
					throw new IllegalArgumentException("Invalid degrees=" + degrees);
				}
			}
			if (degrees != 0) {
				// clockwise
				m.postRotate(degrees,
						(float) b.getWidth() / 2, (float) b.getHeight() / 2);
			}

			try {
				Bitmap b2 = Bitmap.createBitmap(
						b, 0, 0, b.getWidth(), b.getHeight(), m, true);
				if (b != b2) {
					b.recycle();
					b = b2;
				}
			} catch (OutOfMemoryError ex) {
				// We have no memory to rotate. Return the original bitmap.
			}
		}
		return b;
	}

	/**
	 * upload photo by taking a picture
	 */
	private void uploadFromGallery(){
		if((tmpPhotoUri = sdCardHelper.createTmpPhotoStoragePath(tmpPhotoPath)) != null){
			Intent intent = new Intent();
			intent.setType("image/*");
			intent.setAction(Intent.ACTION_GET_CONTENT);
			startActivityForResult(Intent.createChooser(intent, getString(R.string.picker)), PICK_FROM_FILE);
		}
		else{
			Log.i(TAG, "Path for storing photos cannot be created!");
			setButtonStatus(false, false);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_OK) return;
		setButtonStatus(false,true);
		hasMedia = true;
		switch (requestCode) {
		case PICK_FROM_FILE:

			//display the photo
			Uri mImageGalleryUri = data.getData();

			//get the real path for chosen photo
			mImageGalleryUri = Uri.parse(sdCardHelper.getRealPathFromUri( (Activity) NewTweetActivity.this, mImageGalleryUri));

			//copy the photo from gallery to tmp directory

			String fromFile = mImageGalleryUri.getPath();
			String toFile = tmpPhotoUri.getPath();
			if(sdCardHelper.copyFile(fromFile, toFile)){
				photo = sdCardHelper.decodeBitmapFile(toFile);
				mImageView.setImageBitmap(photo);
			}
			break;
		}
	}
}
