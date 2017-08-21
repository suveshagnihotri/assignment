package com.nira.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nira.android.R;

import java.util.List;

/**
 * Created by Suvesh on 21/08/2017 AD.
 */

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.CompanyViewHolder> {

    private  Context context;
    private List<String>  companyList;
    private  Callbacks mCallbacks;
     public  CompanyAdapter(Context context, List<String> companyList){
         this.context= context;
         this.companyList=companyList;
         Log.d("SuveshAdapter",companyList.size()+"");
     }

    @Override
    public CompanyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_company, parent, false);
        return new CompanyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CompanyViewHolder holder, int position) {
        Log.d("Suvesh",companyList.get(position));
      holder.tvName.setText(companyList.get(position));
    }

    @Override
    public int getItemCount() {
        return companyList.size();
    }

    public  class CompanyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvName;
        public CompanyViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView)itemView.findViewById(R.id.tvName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mCallbacks.onSelectCompany(companyList.get(getAdapterPosition()));
        }
    }
    public void  setCallbacks(Callbacks callbacks) {
        this.mCallbacks = callbacks;
    }

     public  interface Callbacks {
        void onSelectCompany(String company);
    }
}
