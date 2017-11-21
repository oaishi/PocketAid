package com.example.fariahuq.pocketaid;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class Description extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private int []rainbow;
    private int pos;
    private static ArrayList<AidItem> aidItems;
    private String tabTitles[];
    private static int count;


    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        rainbow =(this.getResources().getIntArray(R.array.array));
        Bundle extras = getIntent().getExtras();
        pos = extras.getInt("position");
        aidItems = new MyDBHandler(this,null,null,1).databasetostringaiditem(pos+1);
        count = aidItems.size();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        findViewById(R.id.appbar).setBackgroundColor(rainbow[pos%5]);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        final Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
               Bundle bundle = msg.getData();
               String title = bundle.getString("title");
               int i = bundle.getInt("pos");
                tabLayout.addTab(tabLayout.newTab().setText(title));
            }
        };

        //TODO: It doesnt work
         Runnable runnable = new Runnable() {
            public void run() {
                    for (int i = 0; i < count; i++) {
                        tabTitles[i] = aidItems.get(i).getTitle();
                        Message msg = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putString("title", tabTitles[i]);
                        bundle.putInt("pos", i);
                        msg.setData(bundle);
                        handler.sendMessage(msg);
                    }
            }
        };

       //Thread mythread = new Thread(runnable);
       //mythread.start();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_description_page, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_fav) {
            return true;
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
            textView.setText((CharSequence)(aidItems.get(getArguments().getInt(ARG_SECTION_NUMBER)).getDesc()));
            return rootView;
        }
    }

    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        private final ArrayList<CharSequence> mCheeses = new ArrayList<>();
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return count;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return (CharSequence)tabTitles[position];
        }

    }

}
