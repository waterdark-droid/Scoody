package com.example.scoodycustomer.category_button_extras;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scoodycustomer.R;

import java.util.ArrayList;


public class ButtonAdapter extends RecyclerView.Adapter<ButtonAdapter.MyViewHolder> {
    private ArrayList<ButtonModel> buttonList;

    private final ButtonClick buttonClick;
    public ButtonAdapter(ArrayList<ButtonModel> buttonList, ButtonClick buttonClick){

        this.buttonList = buttonList;
        this.buttonClick = buttonClick;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View buttonView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_button, parent, false);

        return new MyViewHolder(buttonView, buttonClick);
    }

    @Override
    public void onBindViewHolder(@NonNull ButtonAdapter.MyViewHolder holder, int position) {
        ButtonModel buttonModel = buttonList.get(position);
        holder.productImage.setImageResource(buttonModel.getImageId());
        holder.productName.setText(buttonModel.getName());
    }

    @Override
    public int getItemCount() {
        return buttonList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView productImage, categoryBottom, background;
        TextView productName;

        MyViewHolder(View convertView, ButtonClick buttonClick){

            super(convertView);

            productImage = convertView.findViewById(R.id.productImage);
            productName = convertView.findViewById(R.id.productName);
            categoryBottom = convertView.findViewById(R.id.category_bottom);
            background = convertView.findViewById(R.id.background);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(buttonClick != null){
                        int pos = getBindingAdapterPosition();

                        if(pos != RecyclerView.NO_POSITION){
                            buttonClick.onItemClick(pos, convertView);
                        }
                    }
                }
            });

        }



    }
}