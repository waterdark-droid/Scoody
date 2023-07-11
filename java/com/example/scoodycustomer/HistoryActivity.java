package com.example.scoodycustomer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.scoodycustomer.configure_dynamic_activity.ConfigureHistoryActivity;
import com.example.scoodycustomer.history_activity_extras.HistoryAdapter;
import com.example.scoodycustomer.history_activity_extras.HistoryModel;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    TextView locationText;
    RecyclerView historyRv;
    String customerId;
    ArrayList<HistoryModel> historyModels;
    int insertBeginning = 0;
    HistoryAdapter historyAdapter;
    String[] productStatus = {"Product Delivered", "Order Cancelled"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        changeAppTheme();
        locationText = (TextView) findViewById(R.id.location_text);
        customerId = getIntent().getStringExtra("customer_id");
        ConfigureHistoryActivity configureHistoryActivity = new ConfigureHistoryActivity(customerId, locationText);
        configureHistoryActivity.setLocation();

        //History Data
        historyModels = new ArrayList<HistoryModel>();
        historyModels.add(insertBeginning, new HistoryModel("Saran R", productStatus[0], "21/01/2023"));
        historyModels.add(insertBeginning, new HistoryModel("Dharshan S.M", productStatus[1], "14/01/2023"));

        historyRv = (RecyclerView) findViewById(R.id.history_rv);
        historyAdapter = new HistoryAdapter(this, historyModels);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        historyRv.setLayoutManager(layoutManager);
        historyRv.setAdapter(historyAdapter);
    }

    public void changeAppTheme(){
        View decor = HistoryActivity.this.getWindow().getDecorView();

        if(decor.getSystemUiVisibility() != View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR){
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        else{
            decor.setSystemUiVisibility(0);
        }



    }
}