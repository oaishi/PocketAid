/*
* Copyright (C) 2014 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.example.fariahuq.pocketaid;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class HolderOfVirtual extends Fragment {
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    private int i;
    private Button submit;
    private Button yes;
    private Button yes1;
    private Button no;
    private TextView diagnosis;
    private LinearLayout process;
    private LinearLayout decide;

    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }

    protected LayoutManagerType mCurrentLayoutManagerType;

    protected RecyclerView mRecyclerView;
    protected CustomAdapterVirtual mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected ArrayList<MatrixRow> mDataset;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize dataset, this data would usually come from a local content provider or
        // remote server.
        initDataset();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_holder_of_virtual, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycle_view_list);
        process = (LinearLayout)rootView.findViewById(R.id.process);
        decide = (LinearLayout)rootView.findViewById(R.id.decide);
        diagnosis = (TextView)rootView.findViewById(R.id.diagnosis);
        mLayoutManager = new LinearLayoutManager(getActivity());

        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;

        if (savedInstanceState != null) {
            // Restore saved layout manager type.
            mCurrentLayoutManagerType = (LayoutManagerType) savedInstanceState
                    .getSerializable(KEY_LAYOUT_MANAGER);
        }
        setRecyclerViewLayoutManager(mCurrentLayoutManagerType);

        mAdapter = new CustomAdapterVirtual(mDataset);
        // Set CustomAdapter as the adapter for RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
        // END_INCLUDE(initializeRecyclerView)

        submit = (Button)rootView.findViewById(R.id.submit);
        yes = (Button)rootView.findViewById(R.id.yes);
        yes1 = (Button)rootView.findViewById(R.id.yes1);
        no = (Button)rootView.findViewById(R.id.no);
        process.setVisibility(View.GONE);
        decide.setVisibility(View.GONE);
        submit.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                            process.setVisibility(View.VISIBLE);
                                            submit.setVisibility(View.GONE);
                                      }
                                  }
        );


        yes.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          process.setVisibility(View.GONE);
                                          mDataset = mAdapter.getmDataSet();
                                          MatrixRow matrixRow = new MatrixRow(0,0,0,0,0);
                                          mDataset.add(matrixRow);
                                          i++;
                                          mAdapter.notifyDataSetChanged();
                                          submit.setVisibility(View.VISIBLE);
                                      }
                                  }
        );

        yes1.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       process.setVisibility(View.GONE);
                                       decide.setVisibility(View.GONE);
                                       mRecyclerView.setVisibility(View.VISIBLE);
                                       mDataset.clear();
                                       MatrixRow matrixRow = new MatrixRow(0,0,0,0,0);
                                       mDataset.add(matrixRow);
                                       mAdapter.notifyDataSetChanged();
                                       submit.setVisibility(View.VISIBLE);
                                   }
                               }
        );



        no.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          AsyncTaskRunner runner = new AsyncTaskRunner();
                                          mDataset = mAdapter.getmDataSet();
                                          MatrixName entry = new MatrixName();
                                          entry.setItems(mDataset);
                                          runner.execute(entry);
                                      }
                                  }
        );

        return rootView;
    }

    /**
     * Set RecyclerView's LayoutManager to the one given.
     *
     * @param layoutManagerType Type of layout manager to switch to.
     */
    public void setRecyclerViewLayoutManager(LayoutManagerType layoutManagerType) {
        int scrollPosition = 0;

        // If a layout manager has already been set, get current scroll position.
        if (mRecyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }

        switch (layoutManagerType) {
            default:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        }

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(scrollPosition);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save currently selected layout manager.
        savedInstanceState.putSerializable(KEY_LAYOUT_MANAGER, mCurrentLayoutManagerType);
        super.onSaveInstanceState(savedInstanceState);
    }


    private class AsyncTaskRunner extends AsyncTask<MatrixName, String, String> {

        private String resp = "Showing Your Result :\n";
        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(MatrixName... params)  {
            publishProgress("Sleeping..."); // Calls onProgressUpdate()
            ArrayList<MatrixName> matrixNames = new MyDBHandler(getContext(),null,null,1).DatabaseToStringDisease();
            for (int i=0;i<matrixNames.size();i++) {
                double f = matrixNames.get(i).SimilarityCount(params[0]);
                if(f>50.0)
                resp = resp + matrixNames.get(i).getName() + " : " + Double.toString(f) + " %\n" ;
                //resp.concat(matrixNames.get(i).getName() + Double.toString(f)+ "\n");
                //Log.i("checkup",matrixNames.get(i).getName() + " : " + Double.toString(f));
            }
            if(resp.equals("Showing Your Result :\n"))
                resp = "Hurray ! You Are Completely Fit .";
            return resp;
        }


        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation
            progressDialog.dismiss();
            decide.setVisibility(View.VISIBLE);
            process.setVisibility(View.GONE);
            submit.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.GONE);
            diagnosis.setText(resp);
            //finalResult.setText(result);
        }


        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show( getContext() ,
                    "Analyzing Your Data",
                    "Please , Wait A Moment .");
        }


        @Override
        protected void onProgressUpdate(String... text) {

        }
    }

    /**
     * Generates Strings for RecyclerView's adapter. This data would usually come
     * from a local content provider or remote server.
     */
    private void initDataset() {
        mDataset = new ArrayList<>();
        MatrixRow matrixRow = new MatrixRow(0,0,0,0,0);
        mDataset.add(matrixRow);
        i=1;
    }
}
