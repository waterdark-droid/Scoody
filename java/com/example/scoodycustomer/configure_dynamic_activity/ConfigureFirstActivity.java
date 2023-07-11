package com.example.scoodycustomer.configure_dynamic_activity;

import android.widget.TextView;

public class ConfigureFirstActivity extends ConfigureActivity{
    TextView locationView, userNameView;

    private String location, userName;

    public ConfigureFirstActivity(TextView locationView,TextView userNameView, String customerId) {
        super(customerId);
        this.locationView = locationView;
        this.userNameView = userNameView;
    }

    public void setLocation(){

        location = super.customerDataReceivable.get(0);


        locationView.setText(location);
        locationView.setSelected(true);
    }

    public void setUserName(){
        userName = super.customerDataReceivable.get(1);

        userNameView.setText(userName);
    }
}
