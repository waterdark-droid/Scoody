package com.example.scoodycustomer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;


import android.view.ViewGroup;
import android.widget.Toast;

import com.example.scoodycustomer.login_activity_extras.LoginAdapter;
import com.example.scoodycustomer.login_activity_extras.LoginTabFragment;
import com.example.scoodycustomer.login_activity_extras.ZoomOutPageTransformer;
import com.google.android.material.tabs.TabLayout;

import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        changeAppTheme();


        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);



        tabLayout.addTab(tabLayout.newTab().setText("Login"));
        tabLayout.addTab(tabLayout.newTab().setText("Sign Up"));

        final LoginAdapter loginAdapter = new LoginAdapter(getSupportFragmentManager(), this, tabLayout.getTabCount());

        viewPager.setAdapter(loginAdapter);

        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                ViewGroup.LayoutParams params = viewPager.getLayoutParams();
                if (tab.getText() == "Login"){

                    params.height = (int) convertDpToPixels(300,LoginActivity.this);
                    viewPager.setLayoutParams(params);

                } else if (tab.getText() == "Sign Up") {

                    params.height = (int) convertDpToPixels(600, LoginActivity.this);
                    viewPager.setLayoutParams(params);
                }

                viewPager.setCurrentItem(tabLayout.getSelectedTabPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }


    public static float convertDpToPixels(float dp, Context context){
        return dp * ((float) context.getResources().getDisplayMetrics().densityDpi/ DisplayMetrics.DENSITY_DEFAULT);
    }

    public void changeAppTheme(){
        View decor = LoginActivity.this.getWindow().getDecorView();
        if(decor.getSystemUiVisibility() != View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR){
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        else{
            decor.setSystemUiVisibility(0);
        }
    }
}