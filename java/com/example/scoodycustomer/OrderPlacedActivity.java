package com.example.scoodycustomer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.scoodycustomer.configure_dynamic_activity.ConfigureOrderPlacedActivity;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class OrderPlacedActivity extends AppCompatActivity {

    ImageView orderStatus;
    private String customerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_placed);
        changeAppTheme();

        orderStatus = (ImageView) findViewById(R.id.order_successful);

        Animation zoomAnim = AnimationUtils.loadAnimation(this, R.anim.zoom_in);
        customerId = getIntent().getStringExtra("customer_id");
        orderStatus.startAnimation(zoomAnim);
        setTimer();
    }

    private void setTimer(){
        new CountDownTimer(2000, 1000){
            @Override
            public void onTick(long millisUntilFinished) {
                NumberFormat format = new DecimalFormat("00");
                long sec = (millisUntilFinished / 1000) % 60;
            }

            @Override
            public void onFinish() {
                Intent firstActivityIntent = new Intent(OrderPlacedActivity.this, FirstActivity.class);
                firstActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                firstActivityIntent.putExtra("customer_id", customerId);
                startActivity(firstActivityIntent);
            }
        }.start();
    }
    public void changeAppTheme(){
        View decor = OrderPlacedActivity.this.getWindow().getDecorView();
        if(decor.getSystemUiVisibility() != View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR){
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        else{
            decor.setSystemUiVisibility(0);
        }
    }
}