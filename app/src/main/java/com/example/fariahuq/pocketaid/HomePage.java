package com.example.fariahuq.pocketaid;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import java.util.ArrayList;

import static org.json.JSONObject.NULL;

public class HomePage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private MyDBHandler dbHandler;
    private SharedPreferences mPreferences;
    private int clickCounter;
    private final String COUNT_KEY = "count";

    private DatabaseReference ref;

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPreferences = getSharedPreferences("com.example.fariahuq.pocketaid", MODE_PRIVATE);
        clickCounter = mPreferences.getInt(COUNT_KEY, 0);

        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

       dbHandler =new MyDBHandler(this,null,null,1);
       ref = FirebaseDatabase.getInstance().getReference().child("First Aid List");

      // if(clickCounter==0)
           loaddata();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    //testing Firebase database
    private void loaddata()
    {

        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        GenericTypeIndicator<ArrayList<Aid>> t = new GenericTypeIndicator<ArrayList<Aid>>() {};
                        //Get map of users in datasnapshot
                        ArrayList<Aid> aids = dataSnapshot.getValue(t);
                        int count;
                        for (count=0;count<aids.size();count++){
                            Aid aid = aids.get(count);
                            long i = dbHandler.addProducttoaid(aid);
                            if(aid!=NULL)
                                Log.i("Firebase!", aid.getTitle());
                            if(aid!=NULL)
                                Log.i("Firebase!", aid.getImage());
                            Log.i("Firebase!", String.valueOf(aid.getId()));
                            GenericTypeIndicator<ArrayList<AidItem>> ti = new GenericTypeIndicator<ArrayList<AidItem>>() {};
                            ArrayList<AidItem> ai = dataSnapshot.child(Integer.toString(count)+"/Steps").getValue(ti);
                            for (int countitem=0;countitem<ai.size();countitem++) {
                                AidItem aiditem = ai.get(countitem);
                                dbHandler.addProducttoaiditem(aiditem,i);
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
            displayToast("profile");

        } else if (id == R.id.nav_checkup) {
            drawer.closeDrawer(GravityCompat.START);
            displayToast("checkup");
        } else if (id == R.id.nav_first_aid) {
            drawer.closeDrawer(GravityCompat.START);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                HolderOfAidList fragment = new HolderOfAidList();
                transaction.replace(R.id.Fragment_Container, fragment);
                transaction.commit();
            displayToast("first aid");
        } else if (id == R.id.nav_symptoms) {
            drawer.closeDrawer(GravityCompat.START);
            displayToast("profile");
        } else if (id == R.id.nav_tests) {
            drawer.closeDrawer(GravityCompat.START);
            displayToast("profile");
        } else if (id == R.id.nav_reminder) {
            drawer.closeDrawer(GravityCompat.START);
            displayToast("profile");
        }else if (id == R.id.nav_message) {
            drawer.closeDrawer(GravityCompat.START);
            displayToast("profile");
        }else if (id == R.id.nav_hospitals) {
            drawer.closeDrawer(GravityCompat.START);
            displayToast("profile");
        }else if (id == R.id.nav_fav) {
            drawer.closeDrawer(GravityCompat.START);
            displayToast("profile");
        }
        return true;
    }

    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
