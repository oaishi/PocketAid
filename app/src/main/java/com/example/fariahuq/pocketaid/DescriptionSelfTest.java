package com.example.fariahuq.pocketaid;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class DescriptionSelfTest extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private int []rainbow;
    private int pos,fav;
    private static ArrayList<SelfTestItem> selfTests;
    private static int count;
    private String title;
    private MyDBHandler myDBHandler;
    private Menu menu;
    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        rainbow =(this.getResources().getIntArray(R.array.array));
        Bundle extras = getIntent().getExtras();
        pos = extras.getInt("position");
        fav = extras.getInt("favourite");
        title = extras.getString("headline");
        myDBHandler = new MyDBHandler(this,null,null,1);
        selfTests = myDBHandler.DatabaseToStringTestItem(pos+1);
        count = selfTests.size();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        findViewById(R.id.appbar).setBackgroundColor(rainbow[pos%5]);
        toolbar.setTitle(title);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

       for (int i = 0; i < count; i++) {
           tabLayout.addTab(tabLayout.newTab().setText((CharSequence) selfTests.get(i).getTitle()));
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_description_page, menu);
        MenuItem item = menu.findItem(R.id.action_fav);
        if(item!=null && fav==1)
            item.setIcon(R.drawable.ic_fav);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(),HomePage.class);
        intent.putExtra("name","test");
        startActivity(intent);
        finish();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_fav) {
            if(fav==0) {
                item.setIcon(R.drawable.ic_fav);
                fav=1;
            }
            else {
                item.setIcon(R.drawable.ic_unfav);
                fav = 0;
            }
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    myDBHandler.UpdateTest(pos+1,fav);
                }
            };
            Thread thread = new Thread(runnable);
            thread.start();
            //return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_description, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.Description);
            textView.setText((CharSequence)(selfTests.get(getArguments().getInt(ARG_SECTION_NUMBER)).getDesc()));
            ImageView imageView = (ImageView)rootView.findViewById(R.id.imageView2);

            if(!((selfTests.get(getArguments().getInt(ARG_SECTION_NUMBER)).getImage()).equals("null"))) {
                try {
                    String path = "/data/user/0/com.example.fariahuq.pocketaid/app_imageDir" + "/" +
                            selfTests.get(getArguments().getInt(ARG_SECTION_NUMBER)).getImage();
                    File file = new File(path);
                    Bitmap b = BitmapFactory.decodeStream(new FileInputStream(file));
                    imageView.setImageBitmap(b);
                }
                catch (FileNotFoundException e)
                {
                    imageView.setVisibility(View.INVISIBLE);
                    e.printStackTrace();
                }
            }
            else
                imageView.setVisibility(View.INVISIBLE);
            return rootView;
        }
    }

    //same for all
    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return count;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "";
        }

    }

}
