package com.example.fariahuq.pocketaid;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;

interface OnClickGenerationName {

    void ChangeData(int position,String string);
    void ChangeDataDuration(int position,String string);
    void ChangeDataIntensity(int position,String string);
    void ChangeDataTime(int position,String string);
    void ChangeDataOrgan(int position,String string);
}

public class CustomAdapterVirtual extends RecyclerView.Adapter<CustomAdapterVirtual.ViewHolder> {

    private ArrayList<MatrixRow> mDataSet;
    private OnClickGenerationName onClickGenerationName;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        Spinner spinner;
        Spinner spinner1;
        Spinner spinner2;
        Spinner spinner3;
        Spinner spinner4;
        OnClickGenerationName onClickGenerationName;

        public ViewHolder(View v) {
            super(v);
            spinner = (Spinner) v.findViewById(R.id.spinner);
            spinner1 = (Spinner) v.findViewById(R.id.spinner1);
            spinner2 = (Spinner) v.findViewById(R.id.spinner2);
            spinner3 = (Spinner) v.findViewById(R.id.spinner3);
            spinner4 = (Spinner) v.findViewById(R.id.spinner4);
        }

        public void bind(OnClickGenerationName onClickGeneration ) {

            this.onClickGenerationName = onClickGeneration;
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            //TODO getAdapterPosition()  returns holder's position in the view
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                   onClickGenerationName.ChangeData(getAdapterPosition(),parent.getItemAtPosition(pos).toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub
                }

            });

            spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                //TODO getAdapterPosition()  returns holder's position in the view
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    onClickGenerationName.ChangeDataDuration(getAdapterPosition(),parent.getItemAtPosition(pos).toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub
                }

            });

            spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                //TODO getAdapterPosition()  returns holder's position in the view
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    onClickGenerationName.ChangeDataTime(getAdapterPosition(),parent.getItemAtPosition(pos).toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub
                }

            });

            spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                //TODO getAdapterPosition()  returns holder's position in the view
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                   onClickGenerationName.ChangeDataIntensity(getAdapterPosition(),parent.getItemAtPosition(pos).toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub
                }

            });

            spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                //TODO getAdapterPosition()  returns holder's position in the view
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    onClickGenerationName.ChangeDataOrgan(getAdapterPosition(),parent.getItemAtPosition(pos).toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub
                }

            });
        }

    }

    public CustomAdapterVirtual(ArrayList<MatrixRow> dataSet) {
        mDataSet = dataSet;
        onClickGenerationName = new OnClickGenerationName() {

            @Override
            public void ChangeData(int position,String string) {
                mDataSet.get(position).setName(string);
                //notifyDataSetChanged();
            }

            @Override
            public void ChangeDataDuration(int position,String string) {
                if(string.equals("none"))
                mDataSet.get(position).setDuration(0);
                if(string.equals("Below A Week"))
                    mDataSet.get(position).setDuration(1);
                if(string.equals("Over A Week"))
                    mDataSet.get(position).setDuration(7);
                if(string.equals("A Month"))
                    mDataSet.get(position).setDuration(30);
                if(string.equals("Over Month"))
                    mDataSet.get(position).setDuration(60);
                //notifyDataSetChanged();
            }

            @Override
            public void ChangeDataIntensity(int position,String string) {
                mDataSet.get(position).setIntensity(string);
                //notifyDataSetChanged();
            }

            @Override
            public void ChangeDataTime(int position,String string) {
                mDataSet.get(position).setTime(string);
                //notifyDataSetChanged();
            }

            @Override
            public void ChangeDataOrgan(int position,String string) {
                mDataSet.get(position).setOrgan(string);
                //notifyDataSetChanged();
            }
        };
    }

    public ArrayList<MatrixRow> getmDataSet() {
        return mDataSet;
    }

    public void setmDataSet(ArrayList<MatrixRow> mDataSet) {
        this.mDataSet = mDataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cards_virtual, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.bind(onClickGenerationName);
    }
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public ArrayList<MatrixRow> getValue(){return mDataSet;}
}
