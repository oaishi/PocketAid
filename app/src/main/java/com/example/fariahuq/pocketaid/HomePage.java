package com.example.fariahuq.pocketaid;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class HomePage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private LocationManager locationManager;
    double longitudeBest, latitudeBest;
    private MyDBHandler dbHandler;
    private DatabaseReference ref;
    private DisplayImageOptions options;
    private ImageSize targetSize;
    private String path;
    private ContextWrapper cw;
    private File directoryaid;
    private Criteria criteria;
    private String provider;
    private String frag;
    static final int PICK_CONTACT_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        dbHandler = new MyDBHandler(this, null, null, 1);
        ref = FirebaseDatabase.getInstance().getReference().child("First Aid List");

        options = new DisplayImageOptions.Builder().resetViewBeforeLoading(true)
                .showImageForEmptyUri(getResources().getDrawable(R.drawable.hi))
                .showImageOnFail(getResources().getDrawable(R.drawable.hi))
                .showImageOnLoading(getResources().getDrawable(R.drawable.hi)).build();
        targetSize = new ImageSize(30, 30);
        path = getApplicationContext().getDir("imageDir", Context.MODE_PRIVATE) + "/";
        cw = new ContextWrapper(getApplicationContext());
        directoryaid = cw.getDir("imageDir", Context.MODE_PRIVATE);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        provider = locationManager.getBestProvider(criteria, true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Add a contact when in contacts options
                if(frag.equals("message"))
                {
                    Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
                    pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
                    startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);
                }
                //TODO: send message checking
                /*SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage("01862262563", null, "success",
                        null, null);
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/

            }
        });

        boolean mboolean = false;
        SharedPreferences settings = getSharedPreferences("com.example.fariahuq.pocketaid", 0);
        mboolean = settings.getBoolean("FIRST_RUN", false);
        if (!mboolean) {
            settings = getSharedPreferences("com.example.fariahuq.pocketaid", 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("FIRST_RUN", true);
            editor.commit();
            loaddata();
        }

    }

    private void loadimage(String imageUri, final int count) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.loadImage(imageUri, targetSize, options, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                String pathname = path + Integer.toString(count) + ".jpg";
                File file = new File(pathname);
                Log.i("Image", pathname);
                if (file.exists() == false) {
                    File mypath = new File(directoryaid, "/" + Integer.toString(count) + ".jpg");
                    Log.i("Image", mypath.getAbsolutePath());
                    FileOutputStream fos = null;
                    try {
                        fos = new FileOutputStream(mypath);
                        //loadedImage.compress(null,100,fos);
                        loadedImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
                        //  aid.setImage(pathname);
                        //init[0] = dbHandler.addProducttoaid(aid);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    //testing Firebase database
    private void loaddata() {

        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        GenericTypeIndicator<ArrayList<Aid>> t = new GenericTypeIndicator<ArrayList<Aid>>() {
                        };
                        //Get map of users in datasnapshot
                        ArrayList<Aid> aids = dataSnapshot.getValue(t);
                        int count;
                        for (count = 0; count < aids.size(); count++) {
                            Aid aid = aids.get(count);
                            Log.i("Firebase!", aid.getImage());
                            if (aid.getImage().equals("null") == false) {
                                loadimage(aid.getImage(), count);
                                aid.setImage(Integer.toString(count) + ".jpq");
                            } else
                                aid.setImage("null");
                            // else
                            //   i = dbHandler.addProducttoaid(aid);
                            long i = dbHandler.addProducttoaid(aid);
                            Log.i("Firebase!", String.valueOf(aid.getId()));
                            GenericTypeIndicator<ArrayList<AidItem>> ti = new GenericTypeIndicator<ArrayList<AidItem>>() {
                            };
                            ArrayList<AidItem> ai = dataSnapshot.child(Integer.toString(count) + "/Steps").getValue(ti);
                            for (int countitem = 0; countitem < ai.size(); countitem++) {
                                AidItem aiditem = ai.get(countitem);
                                dbHandler.addProducttoaiditem(aiditem, i);
                                Log.i("Firebase!", aiditem.getTitle() + " - in steps");
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            drawer.closeDrawer(GravityCompat.START);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            HolderOfContact fragment = new HolderOfContact();
            //transaction.replace(R.id.Fragment_Container, fragment);
            //transaction.commit();
            frag = "profile";
            displayToast(frag);
        } else if (id == R.id.nav_checkup) {
            drawer.closeDrawer(GravityCompat.START);
            frag = "checkup";
            displayToast(frag);
        } else if (id == R.id.nav_first_aid) {
            drawer.closeDrawer(GravityCompat.START);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            HolderOfAidList fragment = new HolderOfAidList();
            transaction.replace(R.id.Fragment_Container, fragment);
            transaction.commit();
            frag = "aid";
            displayToast(frag);
        } else if (id == R.id.nav_symptoms) {
            drawer.closeDrawer(GravityCompat.START);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            HolderOfSysmptoms fragment = new HolderOfSysmptoms();
            //transaction.replace(R.id.Fragment_Container, fragment);
            //transaction.commit();
            frag = "symptoms";
            displayToast(frag);
        } else if (id == R.id.nav_tests) {
            drawer.closeDrawer(GravityCompat.START);
            frag = "tests";
            displayToast(frag);
        } else if (id == R.id.nav_reminder) {
            drawer.closeDrawer(GravityCompat.START);
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_message) {
            drawer.closeDrawer(GravityCompat.START);
            /*FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            HolderOfContact fragment = new HolderOfContact();
            transaction.replace(R.id.Fragment_Container, fragment);
            transaction.commit();*/
            frag = "message";
            displayToast(frag);
        } else if (id == R.id.nav_hospitals) {
            drawer.closeDrawer(GravityCompat.START);
            frag = "hospitals";
            displayToast(frag);
        } else if (id == R.id.nav_fav) {
            drawer.closeDrawer(GravityCompat.START);
            frag = "favourite";
            displayToast(frag);
        }
        return true;
    }

    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if (requestCode == PICK_CONTACT_REQUEST) {
            if (resultCode == RESULT_OK) {
                Runnable runny = new Runnable() {
                    @Override
                    public void run() {
                        Uri contactUri = data.getData();
                        String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER};
                        Cursor cursor = getContentResolver()
                                .query(contactUri, projection, null, null, null);
                        cursor.moveToFirst();
                        int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                        String number = cursor.getString(column);
                        Log.i("Number",number);
                        Contact contact = new Contact("a contact",number);
                        dbHandler.addProducttocontact(contact);
                    }
                };
                Thread mythread = new Thread(runny);
                mythread.start();
            }
        }
    }


    //TODO: trying to get location
    private boolean isLocationEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private boolean checkLocation() {
        if (!isLocationEnabled())
            showAlert();
        return isLocationEnabled();
    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enable Location")
                .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " +
                        "use this app")
                .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    }
                });
        dialog.show();
    }

    public void toggleBestUpdates(View view) {
        if (!checkLocation())
            return;
        if (provider != null) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.requestLocationUpdates(provider, 2 * 60 * 1000, 10, locationListenerBest);
                Toast.makeText(this, "Best Provider is " + provider, Toast.LENGTH_LONG).show();
            }
    }

    private final LocationListener locationListenerBest = new LocationListener() {
        public void onLocationChanged(Location location) {
            longitudeBest = location.getLongitude();
            latitudeBest = location.getLatitude();
            Log.i("location", String.valueOf(latitudeBest)+" "+String.valueOf(longitudeBest));
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
        }

        @Override
        public void onProviderEnabled(String s) {
        }

        @Override
        public void onProviderDisabled(String s) {
        }
    };

}
