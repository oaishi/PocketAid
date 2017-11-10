package com.example.fariahuq.pocketaid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class HomePage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static RecyclerView.Adapter adapter;
    private MyDBHandler dbHandler;
    private SharedPreferences mPreferences;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<DataModel> data;
    static View.OnClickListener myOnClickListener;
    private static ArrayList<Integer> removedItems;
    private int clickCounter;
    private final String COUNT_KEY = "count";

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

       ////recycle view
       myOnClickListener = new MyOnClickListener(this);

       recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
       recyclerView.setHasFixedSize(true);

       layoutManager = new LinearLayoutManager(this);
       recyclerView.setLayoutManager(layoutManager);
       recyclerView.setItemAnimator(new DefaultItemAnimator());

       data = new ArrayList<DataModel>();
       for (int i = 0; i < MyData.nameArray.length; i++) {
           data.add(new DataModel(
                   MyData.nameArray[i],
                   MyData.versionArray[i],
                   MyData.id_[i],
                   MyData.drawableArray[i]
           ));
       }

       removedItems = new ArrayList<Integer>();

       adapter = new CustomAdapter(data);
       recyclerView.setAdapter(adapter);

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
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("First Aid List");
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get map of users in datasnapshot
                        collectaid((Map<String,Object>) dataSnapshot.getValue());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });
    }

    private void collectaid(Map<String,Object> aids) {

        int count=1;
        //iterate through each user, ignoring their UID
        for (Map.Entry<String, Object> entry : aids.entrySet()){
           // Aid aid = new Aid();
            //Get user map
            Map singleUser = (Map) entry.getValue();
            //aid.setFavourite(0);
            //aid.setTitle((String) singleUser.get("name"));
            Log.i("Firebase!", (String) singleUser.get("name"));
            Log.i("Firebase!", (String)  singleUser.get("image"));
            //aid.setImage((String) singleUser.get("image"));
            count++;
        }

        System.out.println(count);
    }

    private static class MyOnClickListener implements View.OnClickListener {

        private final Context context;

        private MyOnClickListener(Context context) {
            this.context = context;
        }

        @Override
        public void onClick(View v) {
            //removeItem(v);
        }

       /* private void removeItem(View v) {
            int selectedItemPosition = recyclerView.getChildPosition(v);
            RecyclerView.ViewHolder viewHolder
                    = recyclerView.findViewHolderForPosition(selectedItemPosition);
            TextView textViewName
                    = (TextView) viewHolder.itemView.findViewById(R.id.textViewName);
            String selectedName = (String) textViewName.getText();
            int selectedItemId = -1;
            for (int i = 0; i < MyData.nameArray.length; i++) {
                if (selectedName.equals(MyData.nameArray[i])) {
                    selectedItemId = MyData.id_[i];
                }
            }
            removedItems.add(selectedItemId);
            data.remove(selectedItemPosition);
            adapter.notifyItemRemoved(selectedItemPosition);
        }*/
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
            displayToast("profile");
        } else if (id == R.id.nav_first_aid) {
            drawer.closeDrawer(GravityCompat.START);
            displayToast("profile");
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
