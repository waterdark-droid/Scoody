package com.example.scoodycustomer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import com.example.scoodycustomer.category_button_extras.*;
import com.example.scoodycustomer.configure_dynamic_activity.ConfigureNewDeliveryActivity;

import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class NewDeliveryActivity extends AppCompatActivity
{
    TextView locationText;
    ArrayList<ButtonModel> categoryButtons;
    ButtonAdapter buttonAdapter;
    RecyclerView recyclerView;
    private String customerId;
    boolean[] toggle;
    boolean srcEditToggle;
    //String categoryItems[] = new String[11];
    HashMap<Integer ,String> categoryItems;
    int index;
    int noOfItems = 0;
    int othersPos = 10;
    int selectItemsLimit = 3;
    AppCompatButton othersButton, submitButton;
    ImageButton sourceEdit;
    EditText source, destination, recMobileNo, othersEt, recName;
    TextView sourceReq, destinationReq, recMobNoReq, othersReq;
    final String required = "*Required";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_delivery);
        changeAppTheme();

        othersButton = (AppCompatButton) findViewById(R.id.othersBtn);
        submitButton = (AppCompatButton) findViewById(R.id.submitbtn);
        locationText = (TextView) findViewById(R.id.location_text);
        categoryItems =  new HashMap<Integer, String>();
        customerId = getIntent().getStringExtra("customer_id");

        recyclerView = (RecyclerView) findViewById(R.id.gridView);

        sourceEdit = (ImageButton) findViewById(R.id.source_edit);
        source = (EditText) findViewById(R.id.source_edit_text);
        destination = (EditText) findViewById(R.id.destination_edit_text);
        othersEt = (EditText) findViewById(R.id.others_edit_text);
        recMobileNo = (EditText) findViewById(R.id.receiver_mobile_no_edit_text);
        recName = (EditText) findViewById(R.id.receiver_name_edit_text);

        sourceReq = (TextView) findViewById(R.id.source_required);
        destinationReq = (TextView) findViewById(R.id.destination_required);
        recMobNoReq = (TextView) findViewById(R.id.receiver_mobile_no_required);
        othersReq = (TextView) findViewById(R.id.others_required);

        ConfigureNewDeliveryActivity configureNewDeliveryActivity = new ConfigureNewDeliveryActivity(customerId, locationText, source);
        configureNewDeliveryActivity.setLocation();
        configureNewDeliveryActivity.setSourceLocation();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        toggle = new boolean[11];

        categoryButtons = new ArrayList<ButtonModel>();
        categoryButtons.add(new ButtonModel("Groceries", R.drawable.groceries));
        categoryButtons.add(new ButtonModel("Clothes", R.drawable.clothes));
        categoryButtons.add(new ButtonModel("Food", R.drawable.food_beverage));
        categoryButtons.add(new ButtonModel("Utensils", R.drawable.utensils));
        categoryButtons.add(new ButtonModel("Books", R.drawable.books));
        categoryButtons.add(new ButtonModel("Personal care", R.drawable.grooming_personalcare));
        categoryButtons.add(new ButtonModel("Stationary", R.drawable.stationary));
        categoryButtons.add(new ButtonModel("Electronics", R.drawable.electronics));
        categoryButtons.add(new ButtonModel("Sports", R.drawable.sports));
        categoryButtons.add(new ButtonModel("Gifts", R.drawable.gifts));

        //buttonAdapter = new ButtonAdapter(categoryButtons, this); Or
        buttonAdapter = new ButtonAdapter(categoryButtons, new ButtonClick() {
            @Override
            public void onItemClick(int position, View view) {
                ImageView imageView = view.findViewById(R.id.background);
                ImageView categoryBottom = view.findViewById(R.id.category_bottom);
                TextView productName = view.findViewById(R.id.productName);
                if(noOfItems < selectItemsLimit){

                    if(!toggle[position]) {
                        if(noOfItems >= (selectItemsLimit-1) && !toggle[othersPos]){
                            othersButton.setBackgroundResource(R.drawable.others_button_unselected);
                            othersButton.setAlpha((float)0.5);
                            othersReq.setText("");

                        }
                        makeCategoryButtonSelected(imageView, categoryBottom, productName, position);
                    }
                    else{
                        makeCategoryButtonUnselected(imageView, categoryBottom, position);

                    }
                }
                else{
                    if(toggle[position]){

                        if(!toggle[othersPos] && noOfItems >= (selectItemsLimit-1)){
                            othersButton.setBackgroundResource(R.drawable.others_button_unselected);
                            othersButton.setAlpha((float)0.5);
                            othersReq.setText("");

                        }

                        othersButton.setBackgroundResource(R.drawable.others_button_unselected);
                        othersButton.setAlpha((float)1);

                        makeCategoryButtonUnselected(imageView, categoryBottom, position);
                    }


                }
                //Toast.makeText(NewDeliveryActivity.this, "" + categoryItems, Toast.LENGTH_SHORT).show();

            }
        });
        recyclerView.setItemViewCacheSize(10); //keeps the scrap views in the memory, i.e does not recycle
        recyclerView.setAdapter(buttonAdapter);


        othersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(noOfItems < 3){
                    if(!toggle[othersPos]){
                        makeOthersButtonSelected(v);

                    }
                    else{
                        makeOthersButtonUnselected(v);

                    }
                }
                else{
                    if(toggle[othersPos]){
                        makeOthersButtonUnselected(v);
                    }


                }
                //Toast.makeText(NewDeliveryActivity.this, "" + categoryItems, Toast.LENGTH_SHORT).show();
            }
        });

        sourceEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!srcEditToggle){
                    makeSourceEditTextEnabled();
                }
                else{
                    makeSourceEditTextDisabled();
                }

            }
        });

        destination.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    makeSourceEditTextDisabled();
                }
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(categoryItems.isEmpty()){
                    Toast.makeText(NewDeliveryActivity.this, "Please select at least one category", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(toggle[othersPos] && othersEt.getText().toString().equals("")){
                    othersReq.setText(required);
                    return;
                }
                else if(toggle[othersPos]){
                    System.out.println("Enabled");
                    categoryItems.put(othersPos, othersEt.getText().toString());
                }

                if(source.getText().toString().equals("")){
                    sourceReq.setText(required);
                    return;
                }
                if( destination.getText().toString().equals("")){
                    destinationReq.setText(required);
                    return;
                }
                if(recMobileNo.getText().toString().equals("")){
                    recMobNoReq.setText(required);
                    return;
                }



                Intent confirmDeliveryActivityIntent = new Intent(NewDeliveryActivity.this, ConfirmDeliveryActivity.class);

                confirmDeliveryActivityIntent.putExtra("category", categoryItems);
                confirmDeliveryActivityIntent.putExtra("source", source.getText().toString());
                confirmDeliveryActivityIntent.putExtra("destination", destination.getText().toString());
                confirmDeliveryActivityIntent.putExtra("receiver_no", recMobileNo.getText().toString());
                confirmDeliveryActivityIntent.putExtra("receiver_name", recName.getText().toString());
                confirmDeliveryActivityIntent.putExtra("customer_id", customerId);
                startActivity(confirmDeliveryActivityIntent);
            }
        });


    }


    public void makeSourceEditTextDisabled(){
        source.setEnabled(false);
        sourceEdit.setImageResource(R.drawable.baseline_edit_24);
        sourceEdit.setBackgroundResource(R.drawable.white_edittext_bg);
        srcEditToggle = false;
    }

    public void makeSourceEditTextEnabled(){
        source.setEnabled(true);
        sourceEdit.setImageResource(R.drawable.baseline_done_24);
        sourceEdit.setBackgroundResource(R.drawable.green_bg);
        srcEditToggle = true;
    }

    public void makeOthersButtonSelected(View v){
        v.setBackgroundResource(R.drawable.others_button_selected);
        ((AppCompatButton)v).setTextColor(Color.parseColor("white"));
        othersEt.setEnabled(true);
        othersReq.setText("");
        //categoryItems[othersPos] = "Others";
        categoryItems.put(othersPos, "Others");
        System.out.println("category items : " + categoryItems);
        toggle[othersPos] = true;
        noOfItems +=1;
    }

    public void makeOthersButtonUnselected(View v){
        v.setBackgroundResource(R.drawable.others_button_unselected);
        ((AppCompatButton)v).setTextColor(Color.parseColor("black"));
        othersEt.setEnabled(false);
        //categoryItems[othersPos] = "";
        categoryItems.remove(othersPos);
        toggle[othersPos] = false;
        noOfItems -= 1;
    }

    public void makeCategoryButtonSelected(ImageView imageView, ImageView categoryBottom,TextView productName, int position){
        imageView.setBackgroundResource(R.drawable.category_button_selected);
        categoryBottom.setAlpha((float)0.0);

        //categoryItems[position] = productName.getText().toString();

        categoryItems.put(position, productName.getText().toString());
        toggle[position] = true;
        noOfItems += 1;
    }

    public void makeCategoryButtonUnselected(ImageView imageView, ImageView categoryBottom, int position){
        imageView.setBackgroundResource(R.drawable.category_button_unselected);
        categoryBottom.setAlpha((float)0.75);
        //categoryItems[position] = "";
        categoryItems.remove(position);
        toggle[position] = false;
        noOfItems -= 1;
    }

    private void changeAppTheme(){
        View decor = NewDeliveryActivity.this.getWindow().getDecorView();
        if(decor.getSystemUiVisibility() != View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR){
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        else{
            decor.setSystemUiVisibility(0);
        }

    }

}