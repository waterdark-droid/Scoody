package com.example.scoodycustomer.configure_dynamic_activity;

import android.widget.TextView;

import java.util.HashMap;

public class ConfigureConfirmDeliveryActivity extends ConfigureActivity{
    private TextView locationView, priceView, distanceView;
    private String location, sourceLoc, destinLoc;

    private HashMap<Integer, String> categoryItems;
    int price = 0;

    public ConfigureConfirmDeliveryActivity(String customerId, TextView locationView, TextView priceView, TextView distanceView, HashMap<Integer, String> categoryItems) {
        super(customerId);
        this.locationView = locationView;
        this.priceView = priceView;
        this.distanceView = distanceView;
        this.categoryItems = categoryItems;
    }

    public void setLocation(){
        location = super.customerDataReceivable.get(0);
        locationView.setText(location);
        locationView.setSelected(true);
    }

    public void setSourceLoc(String sourceLoc){
        this.sourceLoc = sourceLoc;
    }

    public void setDestinLoc(String destinLoc){
        this.destinLoc = destinLoc;
    }

    public String getOrderLocation(){
        return super.customerDataReceivable.get(2);
    }

    public String getCustomerName(){
        return super.customerDataReceivable.get(3);
    }

    public String getSenderMobileNo(){
        return super.customerDataReceivable.get(4);
    }

    public String getPrice() {

        for(String category : categoryItems.values()){
            switch (category){
                case "Groceries":
                case "Books":
                case "Stationary":
                    price += 20;
                    break;
                case "Clothes":
                case "Utensils":
                case "Personal care":
                    price += 25;
                    break;
                case "Food":
                case "Electronics":
                    price += 30;
                    break;
                case "Gifts":
                    price += 40;
                    break;
                default:
                    price += 50;
                    break;
            }
        }
        return "₹"+ price;

    }

    public String getDistance(){
        return "null";
    }



}
