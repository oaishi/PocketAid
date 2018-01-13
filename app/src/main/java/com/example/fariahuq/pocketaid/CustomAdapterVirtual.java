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

    void ChangeData(int position,String string , int pos) ;
    void ChangeDataDuration(int position,String string , int pos) ;
    void ChangeDataIntensity(int position,String string , int pos) ;
    void ChangeDataTime(int position,String string, int pos) ;
    void ChangeDataOrgan(int position,String string, int pos) ;
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

        public Spinner getSpinner() {
            return spinner;
        }

        public void setSpinner(Spinner spinner) {
            this.spinner = spinner;
        }

        public Spinner getSpinner1() {
            return spinner1;
        }

        public void setSpinner1(Spinner spinner1) {
            this.spinner1 = spinner1;
        }

        public Spinner getSpinner2() {
            return spinner2;
        }

        public void setSpinner2(Spinner spinner2) {
            this.spinner2 = spinner2;
        }

        public Spinner getSpinner3() {
            return spinner3;
        }

        public void setSpinner3(Spinner spinner3) {
            this.spinner3 = spinner3;
        }

        public Spinner getSpinner4() {
            return spinner4;
        }

        public void setSpinner4(Spinner spinner4) {
            this.spinner4 = spinner4;
        }

        public void bind(OnClickGenerationName onClickGeneration ) {

            this.onClickGenerationName = onClickGeneration;
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            //TODO getAdapterPosition()  returns holder's position in the view
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                   onClickGenerationName.ChangeData(getAdapterPosition(),parent.getItemAtPosition(pos).toString(),pos);
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub
                }

            });

            spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                //TODO getAdapterPosition()  returns holder's position in the view
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    onClickGenerationName.ChangeDataDuration(getAdapterPosition(),parent.getItemAtPosition(pos).toString(),pos);
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub
                }

            });

            spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                //TODO getAdapterPosition()  returns holder's position in the view
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    onClickGenerationName.ChangeDataTime(getAdapterPosition(),parent.getItemAtPosition(pos).toString(),pos);
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub
                }

            });

            spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                //TODO getAdapterPosition()  returns holder's position in the view
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                   onClickGenerationName.ChangeDataIntensity(getAdapterPosition(),parent.getItemAtPosition(pos).toString(),pos);
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub
                }

            });

            spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                //TODO getAdapterPosition()  returns holder's position in the view
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    onClickGenerationName.ChangeDataOrgan(getAdapterPosition(),parent.getItemAtPosition(pos).toString(),pos);
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
            public void ChangeData(int position,String string , int pos) {
                mDataSet.get(position).setName(string);
                mDataSet.get(position).setSpinnerpos(pos);
                //notifyDataSetChanged();
            }

            @Override
            public void ChangeDataDuration(int position,String string,int pos) {
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
                mDataSet.get(position).setSpinner1pos(pos);
                //notifyDataSetChanged();
            }

            @Override
            public void ChangeDataIntensity(int position,String string, int pos) {
                mDataSet.get(position).setIntensity(string);
                mDataSet.get(position).setSpinner3pos(pos);
                //notifyDataSetChanged();
            }

            @Override
            public void ChangeDataTime(int position,String string , int pos) {
                mDataSet.get(position).setTime(string);
                mDataSet.get(position).setSpinner2pos(pos);
                //notifyDataSetChanged();
            }

            @Override
            public void ChangeDataOrgan(int position,String string , int pos) {
                mDataSet.get(position).setOrgan(string);
                mDataSet.get(position).setSpinner4pos(pos);
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
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.bind(onClickGenerationName);
        viewHolder.getSpinner().setSelection(mDataSet.get(position).getSpinnerpos());
        viewHolder.getSpinner1().setSelection(mDataSet.get(position).getSpinner1pos());
        viewHolder.getSpinner2().setSelection(mDataSet.get(position).getSpinner2pos());
        viewHolder.getSpinner3().setSelection(mDataSet.get(position).getSpinner3pos());
        viewHolder.getSpinner4().setSelection(mDataSet.get(position).getSpinner4pos());
    }
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public ArrayList<MatrixRow> getValue(){return mDataSet;}
}
