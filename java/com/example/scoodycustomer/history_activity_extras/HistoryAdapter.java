package com.example.scoodycustomer.history_activity_extras;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scoodycustomer.HistoryActivity;
import com.example.scoodycustomer.R;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {

    Context context;
    ArrayList<HistoryModel> historyModels;

    public HistoryAdapter(Context context, ArrayList<HistoryModel> historyModels){
        this.context = context;
        this.historyModels = historyModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        HistoryModel historyModel = historyModels.get(position);
        holder.receiverName.setText(historyModel.getReceiverName());
        holder.productStatus.setText(historyModel.getProductStatus());
        holder.receivedDate.setText(historyModel.getReceivedDate());

        if(holder.productStatus.getText() == "Product Delivered"){
            holder.productStatus.setTextColor(Color.parseColor("green"));
            holder.pointIndicator.setTextColor(Color.parseColor("green"));
        }
        else{
            holder.productStatus.setTextColor(Color.parseColor("red"));
            holder.pointIndicator.setTextColor(Color.parseColor("red"));
        }
    }

    @Override
    public int getItemCount() {
        return historyModels.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView receiverName, productStatus, pointIndicator, receivedDate;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            receiverName = (TextView) itemView.findViewById(R.id.receiver_name);
            productStatus = (TextView) itemView.findViewById(R.id.product_status);
            pointIndicator = (TextView) itemView.findViewById(R.id.point_indication);
            receivedDate = (TextView) itemView.findViewById(R.id.text_date);
        }
    }

}