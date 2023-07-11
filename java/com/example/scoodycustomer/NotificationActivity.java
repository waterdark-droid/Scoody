package com.example.scoodycustomer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scoodycustomer.Network.GetNotificationTask;
import com.example.scoodycustomer.Network.NetworkAddress;
import com.example.scoodycustomer.configure_dynamic_activity.ConfigureNotificationActivity;
import com.google.android.material.snackbar.Snackbar;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.scoodycustomer.notification_activity_extras.NotificationAdapter;
import com.example.scoodycustomer.notification_activity_extras.NotificationModel;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class NotificationActivity extends AppCompatActivity {
    TextView locationText;
    RecyclerView notificationRecycler;
    NotificationAdapter notificationAdapter;
    TextView emptyNotify;
    ArrayList<NotificationModel> notificationObject;
    String customerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        changeAppTheme();

        customerId = getIntent().getStringExtra("customer_id");
        locationText = (TextView) findViewById(R.id.location_text);

        ConfigureNotificationActivity configureNotificationActivity = new ConfigureNotificationActivity(customerId, locationText);
        configureNotificationActivity.setLocation();

        notificationObject = new ArrayList<NotificationModel>();
        try {
            GetNotificationTask getNotificationTask = new GetNotificationTask();
            NetworkAddress networkAddress = NetworkAddress.getInstance();
            getNotificationTask.setPortIp(networkAddress.getIpAddress(), networkAddress.getPortNo());
            notificationObject = getNotificationTask.execute().get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        notificationRecycler = (RecyclerView) findViewById(R.id.notification_recycle);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        notificationRecycler.setLayoutManager(layoutManager);


        notificationAdapter = new NotificationAdapter(notificationObject);
        notificationRecycler.setAdapter(notificationAdapter);
        emptyNotify = (TextView)findViewById(R.id.empty_notify);
//        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
//            @Override
//            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
//
//                return false;
//            }
//            @Override
//            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//
//                //deleting the notification
//                notificationObject.remove(viewHolder.getBindingAdapterPosition());
//                notificationAdapter.notifyItemRemoved(viewHolder.getBindingAdapterPosition());
//
//                if(notificationObject.size() == 0){
//                    emptyNotify.setText("No new notifications");
//                }
//
//            }
//        }).attachToRecyclerView(notificationRecycler);
    }

    public void changeAppTheme(){
        View decor = NotificationActivity.this.getWindow().getDecorView();
        if(decor.getSystemUiVisibility() != View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR){
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        else{
            decor.setSystemUiVisibility(0);
        }

    }
}