package com.example.fariahuq.pocketaid;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Criteria;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

    private MyDBHandler dbHandler;
    private DatabaseReference ref;
    private DisplayImageOptions options;
    private ImageSize targetSize;
    private String path;
    private ContextWrapper cw;
    private File directoryaid;
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
        targetSize = new ImageSize(90, 90);
        path = getApplicationContext().getDir("imageDir", Context.MODE_PRIVATE) + "/";
        cw = new ContextWrapper(getApplicationContext());
        directoryaid = cw.getDir("imageDir", Context.MODE_PRIVATE);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Add a contact when in contacts options
                if(frag.equals("profile"))
                {
                    ArrayList<Contact> contacts = dbHandler.databasetocontact();
                    for(int i=0;i<contacts.size();i++)
                    {
                        Log.i("widget","reading from database - "+ contacts.get(i).getTitle());
                    }
                }
                if(frag.equals("message"))
                {
                    Intent pickContactIntent =
                    new Intent(Intent.ACTION_PICK,
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                    startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);
                }
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
            Loaddata();
        }

    }

    private void Loadimageoflist(String imageUri, final int count) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.loadImage(imageUri, targetSize, options, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                String pathname = path + "aid" + Integer.toString(count) + ".jpg";
                File file = new File(pathname);
                if (file.exists() == false) {
                    File mypath = new File(directoryaid, "/" + "aid" + Integer.toString(count) + ".jpg");
                    FileOutputStream fos = null;
                    try {
                        fos = new FileOutputStream(mypath);
                        loadedImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
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

    private void Loadimageofitem(String imageUri, final int count , final int holder) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.loadImage(imageUri, targetSize, options, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                String pathname = path + "aiditem" + Integer.toString(holder) + Integer.toString(count) + ".jpg";
                File file = new File(pathname);
                if (file.exists() == false) {
                    File mypath = new File(directoryaid, "/" + "aiditem" + Integer.toString(holder) + Integer.toString(count) + ".jpg");
                    FileOutputStream fos = null;
                    try {
                        fos = new FileOutputStream(mypath);
                        loadedImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
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
    private void Loaddata() {

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
                                Loadimageoflist(aid.getImage(), count);
                                aid.setImage("aid"+Integer.toString(count) + ".jpg");
                            } else
                                aid.setImage("null");
                            long i = dbHandler.addProducttoaid(aid);
                            GenericTypeIndicator<ArrayList<AidItem>> ti = new GenericTypeIndicator<ArrayList<AidItem>>() {
                            };
                            ArrayList<AidItem> ai = dataSnapshot.child(Integer.toString(count) + "/Steps").getValue(ti);
                            for (int countitem = 0; countitem < ai.size(); countitem++) {
                                AidItem aiditem = ai.get(countitem);
                                if (aiditem.getImage().equals("null") == false) {
                                    Loadimageofitem(aiditem.getImage(), countitem , count);
                                    aiditem.setImage("aiditem"+Integer.toString(count)+Integer.toString(countitem) + ".jpg");
                                } else
                                    aiditem.setImage("null");
                                dbHandler.addProducttoaiditem(aiditem, i);
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
        } else if (id == R.id.nav_checkup) {
            drawer.closeDrawer(GravityCompat.START);
            frag = "checkup";
        } else if (id == R.id.nav_first_aid) {
            drawer.closeDrawer(GravityCompat.START);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            HolderOfAidList fragment = new HolderOfAidList();
            transaction.replace(R.id.Fragment_Container, fragment);
            transaction.commit();
            frag = "aid";
        } else if (id == R.id.nav_symptoms) {
            drawer.closeDrawer(GravityCompat.START);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            HolderOfSysmptoms fragment = new HolderOfSysmptoms();
            //transaction.replace(R.id.Fragment_Container, fragment);
            //transaction.commit();
            frag = "symptoms";
        } else if (id == R.id.nav_tests) {
            drawer.closeDrawer(GravityCompat.START);
            frag = "tests";
        } else if (id == R.id.nav_reminder) {
            frag = "reminder";
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_message) {
            drawer.closeDrawer(GravityCompat.START);
            /*FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            HolderOfContact fragment = new HolderOfContact();
            transaction.replace(R.id.Fragment_Container, fragment);
            transaction.commit();*/
            frag = "message";
        } else if (id == R.id.nav_hospitals) {
            drawer.closeDrawer(GravityCompat.START);
            frag = "hospitals";
        } else if (id == R.id.nav_fav) {
            drawer.closeDrawer(GravityCompat.START);
            frag = "favourite";
        }
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if (requestCode == PICK_CONTACT_REQUEST) {
            if (resultCode == RESULT_OK) {
                Cursor cursor = null;
                try {
                    String phoneNo = null ;
                    String name = null;
                    Uri uri = data.getData();
                    cursor = getContentResolver().query(uri, null, null, null, null);
                    cursor.moveToFirst();
                    int  phoneIndex =cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    int  nameIndex =cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                    phoneNo = cursor.getString(phoneIndex);
                    name = cursor.getString(nameIndex);
                    Contact contact = new Contact();
                    contact.setTitle(name);
                    contact.setDesc(phoneNo);
                    Toast.makeText(getApplicationContext(),contact.getDesc()+ " "+contact.getTitle(),Toast.LENGTH_LONG).show();
                    dbHandler.addProducttocontact(contact);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
