package com.example.scoodycustomer.configure_dynamic_activity;

import android.widget.EditText;
import android.widget.TextView;

public class ConfigureNewDeliveryActivity extends ConfigureActivity{

    TextView locationView;
    EditText sourceEditTxt;

    String location;

    public ConfigureNewDeliveryActivity(String customerId, TextView locationView, EditText sourceEditTxt) {
        super(customerId);
        this.locationView = locationView;
        this.sourceEditTxt = sourceEditTxt;
    }

    public void setLocation(){
        location = super.customerDataReceivable.get(0);

        locationView.setText(location);
        locationView.setSelected(true);
    }

    public void setSourceLocation(){
        sourceEditTxt.setText(super.customerDataReceivable.get(0));
    }
}