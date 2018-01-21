package com.example.fariahuq.pocketaid;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import  com.example.fariahuq.pocketaid.model.Alarm;
import  com.example.fariahuq.pocketaid.model.AlarmMsg;
import  com.example.fariahuq.pocketaid.model.AlarmTime;
import  com.example.fariahuq.pocketaid.model.DbHelper;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author appsrox.com
 *
 */
public class AddAlarmActivity extends Activity {
	
//	private static final String TAG = "AddAlarmActivity";
	
	private EditText msgEdit;
	private CheckBox soundCb;
	private DatePicker datePicker;
	private TimePicker timePicker;
	private TextView fromdateText;
	private TextView todateText;
	private TextView attimeText;
	
	private ViewSwitcher vs;
	private RadioGroup rg;
	private RelativeLayout rl3;
	private RelativeLayout rl4;
	
	private Spinner spinner1;
	private Spinner spinner2;
	private Spinner spinner3;
	
	private EditText minsEdit;
	private EditText hoursEdit;
	private EditText daysEdit;
	private EditText monthsEdit;
	private EditText yearsEdit;
	private TextView Testing;
	
	private SQLiteDatabase db;
	
	private static final int DIALOG_FROMDATE = 1;
	private static final int DIALOG_TODATE = 2;
	private static final int DIALOG_ATTIME = 3;
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat();
	
	private AdapterView.OnItemSelectedListener spinnerListener = new OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
        	if (spinner1.getSelectedItemPosition() > 0 && spinner2.getSelectedItemPosition() > 0)
        		spinner1.setSelection(0);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parentView) {
        }
    };



    //camera
    private static final int ACTION_TAKE_PHOTO_B = 1;

    private static final String BITMAP_STORAGE_KEY = "viewbitmap";
    private static final String IMAGEVIEW_VISIBILITY_STORAGE_KEY = "imageviewvisibility";
    private ImageView mImageView;
    private Bitmap mImageBitmap;

    private String mCurrentPhotoPath;

    private static final String JPEG_FILE_PREFIX = "IMG_";
    private static final String JPEG_FILE_SUFFIX = ".jpg";

    private AlbumStorageDirFactory mAlbumStorageDirFactory = null;


    /* Photo album for this application */
    private String getAlbumName() {
        return getString(R.string.album_name);
    }


    private File getAlbumDir() {
        File storageDir = null;

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {

            storageDir = mAlbumStorageDirFactory.getAlbumStorageDir(getAlbumName());

            if (storageDir != null) {
                if (! storageDir.mkdirs()) {
                    if (! storageDir.exists()){
                        Log.d("CameraSample", "failed to create directory");
                        return null;
                    }
                }
            }

        } else {
            Log.v(getString(R.string.app_name), "External storage is not mounted READ/WRITE.");
        }

        return storageDir;
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("MMdd_HHmmss").format(new Date());
        String imageFileName = JPEG_FILE_PREFIX + "raju" +timeStamp + "_";
        File albumF = getAlbumDir();
        File imageF = File.createTempFile(imageFileName, JPEG_FILE_SUFFIX, albumF);
        return imageF;
    }

    private File setUpPhotoFile() throws IOException {

        File f = createImageFile();
        mCurrentPhotoPath = f.getAbsolutePath();

        return f;
    }

    private void setPic() {

		/* There isn't enough memory to open up more than a couple camera photos */
		/* So pre-scale the target bitmap into which the file is decoded */

		/* Get the size of the ImageView */
        Log.i("camera",mCurrentPhotoPath);
        int targetW = mImageView.getWidth();
        int targetH = mImageView.getHeight();

		/* Get the size of the image */
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

		/* Figure out which way needs to be reduced less */
        int scaleFactor = 1;
        if ((targetW > 0) || (targetH > 0)) {
            scaleFactor = Math.min(photoW/targetW, photoH/targetH);
        }

		/* Set bitmap options to scale the image decode target */
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

		/* Decode the JPEG file into a Bitmap */
        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);

		/* Associate the Bitmap to the ImageView */
        mImageView.setImageBitmap(bitmap);
        //mVideoUri = null;
        mImageView.setVisibility(View.VISIBLE);
        //mVideoView.setVisibility(View.INVISIBLE);
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    private void dispatchTakePictureIntent(int actionCode) {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        switch(actionCode) {
            case ACTION_TAKE_PHOTO_B:
                File f = null;

                try {
                    f = setUpPhotoFile();
                    mCurrentPhotoPath = f.getAbsolutePath();
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                } catch (IOException e) {
                    e.printStackTrace();
                    f = null;
                    mCurrentPhotoPath = null;
                }
                break;

            default:
                break;
        } // switch

        startActivityForResult(takePictureIntent, actionCode);
    }

    private void handleBigCameraPhoto() {

        if (mCurrentPhotoPath != null) {
            setPic();
            galleryAddPic();
            mCurrentPhotoPath = null;
        }

    }

    Button.OnClickListener mTakePicOnClickListener =
            new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dispatchTakePictureIntent(ACTION_TAKE_PHOTO_B);
                }
            };

    //camera



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("New Reminder");
        setContentView(R.layout.activity_add_alarm);

        findViews();
        db = RemindMe.db;
        
        rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch(checkedId) {
				case R.id.radio0:
					rl3.setVisibility(View.VISIBLE);
					rl4.setVisibility(View.GONE);
					break;
				case R.id.radio1:
					rl4.setVisibility(View.VISIBLE);
					rl3.setVisibility(View.GONE);
					break;					
				}
			}
		});
        
        spinner1.setOnItemSelectedListener(spinnerListener);
        spinner2.setOnItemSelectedListener(spinnerListener);

        mImageView = (ImageView) findViewById(R.id.imageView1);
        mImageBitmap = null;

        Button picBtn = (Button) findViewById(R.id.btnIntend);
        setBtnListenerOrDisable(
                picBtn,
                mTakePicOnClickListener,
                MediaStore.ACTION_IMAGE_CAPTURE
        );
        mAlbumStorageDirFactory = new BaseAlbumDirFactory();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ACTION_TAKE_PHOTO_B: {
                if (resultCode == RESULT_OK) {
                    handleBigCameraPhoto();
                }
                break;
            } // ACTION_TAKE_PHOTO_B

		/*case ACTION_TAKE_PHOTO_S: {
			if (resultCode == RESULT_OK) {
				handleSmallCameraPhoto(data);
			}
			break;
		} // ACTION_TAKE_PHOTO_S

		case ACTION_TAKE_VIDEO: {
			if (resultCode == RESULT_OK) {
				handleCameraVideo(data);
			}
			break;
		} // ACTION_TAKE_VIDEO*/
        } // switch
    }
    
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("vs", vs.getDisplayedChild());
//		outState.putInt("date", datePicker.getDayOfMonth());
//		outState.putInt("month", datePicker.getMonth());
//		outState.putInt("year", datePicker.getYear());
		outState.putInt("hour", timePicker.getCurrentHour());
//		outState.putInt("min", timePicker.getCurrentMinute());
		outState.putCharSequence("fromdate", fromdateText.getText());
		outState.putCharSequence("todate", todateText.getText());
		outState.putCharSequence("attime", attimeText.getText());
		outState.putCharSequence("testing", Testing.getText());
        outState.putParcelable(BITMAP_STORAGE_KEY, mImageBitmap);
        //outState.putParcelable(VIDEO_STORAGE_KEY, mVideoUri);
        outState.putBoolean(IMAGEVIEW_VISIBILITY_STORAGE_KEY, (mImageBitmap != null) );
        //outState.putBoolean(VIDEOVIEW_VISIBILITY_STORAGE_KEY, (mVideoUri != null) );
	}	

	@Override
	protected void onRestoreInstanceState(Bundle state) {
		super.onRestoreInstanceState(state);
		vs.setDisplayedChild(state.getInt("vs"));
//		datePicker.updateDate(state.getInt("year"), state.getInt("month"), state.getInt("date"));
		timePicker.setCurrentHour(state.getInt("hour"));
//		timePicker.setCurrentMinute(state.getInt("min"));
		fromdateText.setText(state.getCharSequence("fromdate"));
		todateText.setText(state.getCharSequence("todate"));
		attimeText.setText(state.getCharSequence("attime"));
		Testing.setText(state.getCharSequence("testing"));
        mImageBitmap = state.getParcelable(BITMAP_STORAGE_KEY);
        //mVideoUri = savedInstanceState.getParcelable(VIDEO_STORAGE_KEY);
        mImageView.setImageBitmap(mImageBitmap);
        mImageView.setVisibility(
                state.getBoolean(IMAGEVIEW_VISIBILITY_STORAGE_KEY) ?
                        ImageView.VISIBLE : ImageView.INVISIBLE
        );
	}

    public static boolean isIntentAvailable(Context context, String action) {
        final PackageManager packageManager = context.getPackageManager();
        final Intent intent = new Intent(action);
        List<ResolveInfo> list =
                packageManager.queryIntentActivities(intent,
                        PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    private void setBtnListenerOrDisable(
            Button btn,
            Button.OnClickListener onClickListener,
            String intentName
    ) {
        if (isIntentAvailable(this, intentName)) {
            btn.setOnClickListener(onClickListener);
        } else {
            btn.setText(
                    getText(R.string.cannot).toString() + " " + btn.getText());
            btn.setClickable(false);
        }
    }
        
	@Override
	protected void onResume() {
		super.onResume();
		sdf.applyPattern(RemindMe.getDateFormat());
	}

	private void findViews() {
		msgEdit = (EditText) findViewById(R.id.msg_et);
		soundCb = (CheckBox) findViewById(R.id.sound_cb);
		datePicker = (DatePicker) findViewById(R.id.datePicker);
		timePicker = (TimePicker) findViewById(R.id.timePicker);
		fromdateText = (TextView) findViewById(R.id.fromdate_tv);
		todateText = (TextView) findViewById(R.id.todate_tv);
		attimeText = (TextView) findViewById(R.id.attime_tv);
        vs = (ViewSwitcher) findViewById(R.id.view_switcher);
        rg = (RadioGroup) findViewById(R.id.radioGroup);
        rl3 = (RelativeLayout) findViewById(R.id.relativeLayout3);
        rl4 = (RelativeLayout) findViewById(R.id.relativeLayout4);
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        
        minsEdit = (EditText) findViewById(R.id.mins_et);
        hoursEdit = (EditText) findViewById(R.id.hours_et);
        daysEdit = (EditText) findViewById(R.id.days_et);
        monthsEdit = (EditText) findViewById(R.id.months_et);
        yearsEdit = (EditText) findViewById(R.id.years_et);

        Testing = (TextView)findViewById(R.id.testing);
	}
	
	private boolean validate() {
		if (TextUtils.isEmpty(msgEdit.getText())) {
			msgEdit.requestFocus();
			Toast.makeText(this, "Enter a message", Toast.LENGTH_SHORT).show();
			return false;
		}
		if (vs.getDisplayedChild() == 1) {
			if (TextUtils.isEmpty(fromdateText.getText())) {
				Toast.makeText(this, "Specify from date", Toast.LENGTH_SHORT).show();
				return false;
			}
			if (TextUtils.isEmpty(todateText.getText())) {
				Toast.makeText(this, "Specify to date", Toast.LENGTH_SHORT).show();
				return false;
			}
			try {
				if (sdf.parse(fromdateText.getText().toString()).after(sdf.parse(todateText.getText().toString()))) {
					Toast.makeText(this, "From date is after To date!", Toast.LENGTH_SHORT).show();
					return false;
				}
			} catch (ParseException e) {}
			if (TextUtils.isEmpty(attimeText.getText())) {
				Toast.makeText(this, "Specify time", Toast.LENGTH_SHORT).show();
				return false;
			}			
		}
		return true;
	}
	
	/* Save */
	public void create(View v) {
		if (!validate()) return;
		
		Alarm alarm = new Alarm();
		alarm.setName(msgEdit.getText().toString());
		alarm.setSound(soundCb.isChecked());
		AlarmTime alarmTime = new AlarmTime();
		long alarmId = 0;
		
		switch(vs.getDisplayedChild()) {
		case 0: //one time
			alarm.setFromDate(DbHelper.getDateStr(datePicker.getYear(), datePicker.getMonth()+1, datePicker.getDayOfMonth()));
			alarmId = alarm.persist(db);
			
			alarmTime.setAt(DbHelper.getTimeStr(timePicker.getCurrentHour(), timePicker.getCurrentMinute()));
			alarmTime.setAlarmId(alarmId);
			alarmTime.persist(db);
			break;
			
		case 1: //repeating
			alarm.setFromDate(Util.toPersistentDate(fromdateText.getText().toString(), sdf));
			alarm.setToDate(Util.toPersistentDate(todateText.getText().toString(), sdf));
			switch(rg.getCheckedRadioButtonId()) {
			case R.id.radio0: //rule
				alarm.setRule(Util.concat(spinner1.getSelectedItemPosition(), " ", 
											spinner2.getSelectedItemPosition(), " ", 
											spinner3.getSelectedItemPosition()));
				break;
			case R.id.radio1: //interval
				alarm.setInterval(Util.concat(minsEdit.getText(), " ", 
								hoursEdit.getText(), " ", 
								daysEdit.getText(), " ", 
								monthsEdit.getText(), " ", 
								yearsEdit.getText()));
				break;						
			}					
			alarmId = alarm.persist(db);
			
			alarmTime.setAt(Util.toPersistentTime(attimeText.getText().toString()));
			alarmTime.setAlarmId(alarmId);
			alarmTime.persist(db);					
			break;				
		}
		
		Intent service = new Intent(this, AlarmService.class);
		service.putExtra(AlarmMsg.COL_ALARMID, String.valueOf(alarmId));
		service.setAction(AlarmService.POPULATE);
		startService(service);

		finish();
	}
    
	public void onClick(View v) {
		switch (v.getId()) {

            case R.id.yes:
                switch(vs.getDisplayedChild()) {
                    case 1:
                        vs.showNext();
                        break;
                }
                break;
            case R.id.no:
                switch(vs.getDisplayedChild()) {
                    case 0:
                        vs.showNext();
                        break;
                }
                break;
            case R.id.testing:
				showDialog(5);
				break;
			
		case R.id.fromdate_tv:
		case R.id.fromdate_lb:
			showDialog(DIALOG_FROMDATE);
			break;
			
		case R.id.todate_tv:
		case R.id.todate_lb:
			showDialog(DIALOG_TODATE);
			break;
			
		case R.id.attime_tv:
		case R.id.attime_lb:
			showDialog(DIALOG_ATTIME);
			break;


		}
	}

	@Override
	protected Dialog onCreateDialog(final int id) {
		Calendar cal = Calendar.getInstance();
		switch(id) {
		case DIALOG_ATTIME:
			TimePickerDialog.OnTimeSetListener mTimeSetListener =
			    new TimePickerDialog.OnTimeSetListener() {
			        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
						attimeText.setText(Util.getActualTime(hourOfDay, minute));
			        }
			    };
			return new TimePickerDialog(this, mTimeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), RemindMe.is24Hours());
		
		case DIALOG_FROMDATE:
		case DIALOG_TODATE:
			DatePickerDialog.OnDateSetListener dateListener =
			    new DatePickerDialog.OnDateSetListener() {
					public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
						String txt = DbHelper.getDateStr(year, monthOfYear+1, dayOfMonth);
						try {
							txt = sdf.format(DbHelper.sdf.parse(txt));
						} catch (ParseException e) {}
						
						if (id == DIALOG_FROMDATE) {
							fromdateText.setText(txt);
						} else if (id == DIALOG_TODATE) {
							todateText.setText(txt);
						}
					}
				};
			return new DatePickerDialog(this, dateListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
			case 5:
				TimePickerDialog.OnTimeSetListener TimeSetListener =
						new TimePickerDialog.OnTimeSetListener() {
							public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
								String format;
								if (hourOfDay == 0) {

									hourOfDay += 12;

									format = "AM";
								}
								else if (hourOfDay == 12) {

									format = "PM";

								}
								else if (hourOfDay > 12) {

									hourOfDay -= 12;

									format = "PM";

								}
								else {

									format = "AM";
								}


								Testing.setText(format + "\n"+hourOfDay + ":" + minute );
							}
						};
				return new TimePickerDialog(this, TimeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE),false);
				// RemindMe.is24Hours()

		}
		
		return super.onCreateDialog(id);
	}

	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		super.onPrepareDialog(id, dialog);
		
		switch(id) {
		case DIALOG_ATTIME:
			if (!TextUtils.isEmpty(attimeText.getText())) {
				String[] arr = Util.toPersistentTime(attimeText.getText().toString()).split(":");
				((TimePickerDialog)dialog).updateTime(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]));
			}			
			break;
			
		case DIALOG_FROMDATE:
			if (!TextUtils.isEmpty(fromdateText.getText())) {
				String[] arr = Util.toPersistentDate(fromdateText.getText().toString(), sdf).split("-");
				((DatePickerDialog)dialog).updateDate(Integer.parseInt(arr[0]), Integer.parseInt(arr[1])-1, Integer.parseInt(arr[2]));
			}			
			break;
			
		case DIALOG_TODATE:
			if (!TextUtils.isEmpty(todateText.getText())) {
				String[] arr = Util.toPersistentDate(todateText.getText().toString(), sdf).split("-");
				((DatePickerDialog)dialog).updateDate(Integer.parseInt(arr[0]), Integer.parseInt(arr[1])-1, Integer.parseInt(arr[2]));
			}			
			break;
		}
	}	

}
