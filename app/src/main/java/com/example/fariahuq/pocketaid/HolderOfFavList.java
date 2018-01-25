package com.example.fariahuq.pocketaid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import java.util.ArrayList;


public class HolderOfFavList extends Fragment {
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    private static final int SPAN_COUNT = 2;
    private static final int DATASET_COUNT = 60;
    private RadioGroup option;

    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }


    protected LayoutManagerType mCurrentLayoutManagerType;
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    protected static RecyclerView aids;
    protected static RecyclerView tests;
    protected static RecyclerView symps;
    protected CustomAdapterFav mAdapter;
    protected CustomAdapterSymptomsFav customAdapterSymptoms;
    protected CustomAdapterTestFav customAdapterTest;
    protected RecyclerView.LayoutManager mLayoutManager;

    private String mParam1;
    private String mParam2;

    public HolderOfFavList() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_holder_of_fav_list, container, false);
        aids = (RecyclerView) view.findViewById(R.id.my_recycle_view_aid);
        tests = (RecyclerView) view.findViewById(R.id.my_recycle_view_test);
        symps = (RecyclerView) view.findViewById(R.id.my_recycle_view_symp);
        option = (RadioGroup)view.findViewById(R.id.option);

        mLayoutManager  = new LinearLayoutManager(getActivity());
        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        if (savedInstanceState != null) {
            // Restore saved layout manager type.
            mCurrentLayoutManagerType = (LayoutManagerType) savedInstanceState
                    .getSerializable(KEY_LAYOUT_MANAGER);
        }

        setRecyclerViewLayoutManager(mCurrentLayoutManagerType);
        setRecyclerViewLayoutManagertest(mCurrentLayoutManagerType);
        setRecyclerViewLayoutManagersymp(mCurrentLayoutManagerType);

        mAdapter = new CustomAdapterFav(container);
        customAdapterSymptoms = new CustomAdapterSymptomsFav(container);
        customAdapterTest = new CustomAdapterTestFav(container);

        aids.setAdapter(mAdapter);
        tests.setAdapter(customAdapterTest);
        symps.setAdapter(customAdapterSymptoms);

        aids.setItemAnimator(new DefaultItemAnimator());
        tests.setItemAnimator(new DefaultItemAnimator());
        symps.setItemAnimator(new DefaultItemAnimator());

        aids.setVisibility(View.VISIBLE);

        option.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId) {
                    case R.id.button:
                        aids.setVisibility(View.VISIBLE);
                        tests.setVisibility(View.GONE);
                        symps.setVisibility(View.GONE);
                        break;
                    case R.id.button1:
                        symps.setVisibility(View.VISIBLE);
                        aids.setVisibility(View.GONE);
                        tests.setVisibility(View.GONE);
                        break;
                    case R.id.button2:
                        tests.setVisibility(View.VISIBLE);
                        aids.setVisibility(View.GONE);
                        symps.setVisibility(View.GONE);
                        break;
                }
            }
        });

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save currently selected layout manager.
        savedInstanceState.putSerializable(KEY_LAYOUT_MANAGER, mCurrentLayoutManagerType);
        super.onSaveInstanceState(savedInstanceState);
    }

    public void setRecyclerViewLayoutManager(LayoutManagerType layoutManagerType) {
        int scrollPosition = 0;

        // If a layout manager has already been set, get current scroll position.
        if (aids.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) aids.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }

        switch (layoutManagerType) {
            case GRID_LAYOUT_MANAGER:
                mLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT);
                mCurrentLayoutManagerType = LayoutManagerType.GRID_LAYOUT_MANAGER;
                break;
            case LINEAR_LAYOUT_MANAGER:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
                break;
            default:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        }

        aids.setLayoutManager(mLayoutManager);
        aids.scrollToPosition(scrollPosition);
    }

    public void setRecyclerViewLayoutManagersymp(LayoutManagerType layoutManagerType) {
        int scrollPosition = 0;

        // If a layout manager has already been set, get current scroll position.
        if (symps.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) symps.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }

        switch (layoutManagerType) {
            case GRID_LAYOUT_MANAGER:
                mLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT);
                mCurrentLayoutManagerType = LayoutManagerType.GRID_LAYOUT_MANAGER;
                break;
            case LINEAR_LAYOUT_MANAGER:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
                break;
            default:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        }

        symps.setLayoutManager(mLayoutManager);
        symps.scrollToPosition(scrollPosition);
    }

    public void setRecyclerViewLayoutManagertest(LayoutManagerType layoutManagerType) {
        int scrollPosition = 0;

        // If a layout manager has already been set, get current scroll position.
        if (tests.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) tests.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }

        switch (layoutManagerType) {
            case GRID_LAYOUT_MANAGER:
                mLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT);
                mCurrentLayoutManagerType = LayoutManagerType.GRID_LAYOUT_MANAGER;
                break;
            case LINEAR_LAYOUT_MANAGER:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
                break;
            default:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        }

        tests.setLayoutManager(mLayoutManager);
        tests.scrollToPosition(scrollPosition);
    }

}
