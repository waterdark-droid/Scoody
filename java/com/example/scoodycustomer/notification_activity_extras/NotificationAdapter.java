package com.example.scoodycustomer.notification_activity_extras;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import static com.example.scoodycustomer.notification_activity_extras.NotificationModel.LayoutOne;
import static com.example.scoodycustomer.notification_activity_extras.NotificationModel.LayoutTwo;

import com.example.scoodycustomer.R;


public class NotificationAdapter extends RecyclerView.Adapter {
    ArrayList<NotificationModel> notificationObject;


    @Override
    public int getItemViewType(int position) {
        switch (notificationObject.get(position).getViewType()){
            case 0:
                return LayoutOne;
            case 1:
                return LayoutTwo;
            default:
                return -1;
        }
    }

    public NotificationAdapter(ArrayList<NotificationModel> notificationObject)
    {
        this.notificationObject = notificationObject;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case LayoutOne:
                View layoutOne = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification_ordertaken, parent, false);
                return new OrderTakenViewHolder(layoutOne);
            case LayoutTwo:
                View layoutTwo = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification_delivered, parent, false);
                return new OrderDeliveredViewHolder(layoutTwo);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        NotificationModel notificationModel = notificationObject.get(position);
        switch (notificationObject.get(position).getViewType()){
            case LayoutOne:
                ((OrderTakenViewHolder) holder).empName.setText(notificationModel.getEmpName());
                ((OrderTakenViewHolder) holder).contactNo.setText(notificationModel.getContactNo());
                break;
            case LayoutTwo:
                ((OrderDeliveredViewHolder) holder).product.setText(notificationModel.getProduct());

                break;
            default:
                break;
        }

    }

    @Override
    public int getItemCount() {
        return notificationObject.size();
    }

    static class OrderTakenViewHolder extends RecyclerView.ViewHolder{
        TextView empName, contactNo;

        public OrderTakenViewHolder(View itemView) {
            super(itemView);
            empName = (TextView) itemView.findViewById(R.id.emp_name);
            contactNo = (TextView) itemView.findViewById(R.id.emp_contact_no);

        }
    }

    static class OrderDeliveredViewHolder extends RecyclerView.ViewHolder{

        TextView product;
        public OrderDeliveredViewHolder(View itemView){
            super(itemView);
            product = (TextView) itemView.findViewById(R.id.productName);

        }
    }
}

