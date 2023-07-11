package com.example.scoodycustomer.configure_dynamic_activity;

import android.widget.TextView;

public class ConfigureHistoryActivity extends ConfigureActivity{

    TextView locationView;

    public ConfigureHistoryActivity(String customerId, TextView locationView) {
        super(customerId);
        this.locationView = locationView;
    }

    public void setLocation(){
        locationView.setText(super.customerDataReceivable.get(0));
        locationView.setSelected(true);
    }
}

