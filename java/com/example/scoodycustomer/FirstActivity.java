package com.example.scoodycustomer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.scoodycustomer.configure_dynamic_activity.ConfigureFirstActivity;

public class FirstActivity extends AppCompatActivity {
    TextView locationText, userNameText;
    ImageButton newDeliveryBtn, notificationBtn, historyBtn, profileBtn;
    private String customerId;

    //private RecyclerView currentOrderRecycler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);


        newDeliveryBtn = (ImageButton) findViewById(R.id.new_delivery_btn);
        notificationBtn = (ImageButton) findViewById(R.id.notification_btn);
        historyBtn = (ImageButton) findViewById(R.id.history_btn);
        profileBtn = (ImageButton) findViewById(R.id.profile_btn);
        locationText = (TextView)findViewById(R.id.location_text);
        userNameText = (TextView) findViewById(R.id.user_name);
        customerId = getIntent().getStringExtra("customer_id");
        changeAppTheme();
        ConfigureFirstActivity configureFirstActivity = new ConfigureFirstActivity(locationText, userNameText, customerId);
        configureFirstActivity.setLocation();
        configureFirstActivity.setUserName();
        //currentOrderRecycler = (RecyclerView) findViewById(R.id.current_order_recycler);



        newDeliveryBtn.setOnClickListener(view -> {
            Intent newDeliveryActivityIntent = new Intent(FirstActivity.this, NewDeliveryActivity.class);
            newDeliveryActivityIntent.putExtra("customer_id", customerId);
            startActivity(newDeliveryActivityIntent);
        });

        notificationBtn.setOnClickListener(v -> {
            Intent notificationActivityIntent = new Intent(FirstActivity.this, NotificationActivity.class);
            notificationActivityIntent.putExtra("customer_id", customerId);
            startActivity(notificationActivityIntent);
        });

        historyBtn.setOnClickListener(v -> {
            Intent historyActivityIntent = new Intent(FirstActivity.this, HistoryActivity.class);
            historyActivityIntent.putExtra("customer_id", customerId);
            startActivity(historyActivityIntent);
        });

        profileBtn.setOnClickListener(v -> {
            Intent profileActivityIntent = new Intent(FirstActivity.this, ProfileActivity.class);
            startActivity(profileActivityIntent);
        });

    }

    public void changeAppTheme(){
        View decor = FirstActivity.this.getWindow().getDecorView();
        if (decor.getSystemUiVisibility() != View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR) {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            decor.setSystemUiVisibility(0);
        }


    }
}