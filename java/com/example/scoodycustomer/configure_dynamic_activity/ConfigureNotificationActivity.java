package com.example.scoodycustomer.configure_dynamic_activity;

import android.widget.TextView;

public class ConfigureNotificationActivity extends ConfigureActivity{

    TextView locationView;
    private String location;
    public ConfigureNotificationActivity(String customerId, TextView locationView) {
        super(customerId);
        this.locationView = locationView;
    }

    public void setLocation(){
        location = super.customerDataReceivable.get(0);

        locationView.setText(location);
        locationView.setSelected(true);
    }
}
