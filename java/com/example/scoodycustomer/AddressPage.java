package com.example.scoodycustomer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.scoodycustomer.Network.NetworkAddress;
import com.example.scoodycustomer.Network.SendPersonalDetails;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class AddressPage extends AppCompatActivity {

    private EditText inpFirstName, inpMiddleName, inpLastName, inpAge, inpHouseNo, inpStreet, inpCity, inpCountry, inpState, inpDistrict, inpPinCode;
    private AppCompatButton submitButton;
    private RadioButton inpMale, inpFemale;
    private RadioGroup genderGroup;
    private String customerId, fullName, age, gender, address, district, state, country, pinCode, mobileNo, location;
    private HashMap<Integer, String> addressMap;
    private ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_page);
        View decor = AddressPage.this.getWindow().getDecorView();
        if(decor.getSystemUiVisibility() != View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR){
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        else{
            decor.setSystemUiVisibility(0);
        }
        customerId = getIntent().getStringExtra("customer_id");
        mobileNo = getIntent().getStringExtra("mobile_no");

        submitButton = (AppCompatButton) findViewById(R.id.submitbtn);
        inpFirstName = (EditText) findViewById(R.id.firstName);
        inpMiddleName = (EditText) findViewById(R.id.middleName);
        inpLastName = (EditText) findViewById(R.id.lastName);
        inpAge = (EditText) findViewById(R.id.age);
        inpMale = (RadioButton) findViewById(R.id.male_radio_button);
        inpFemale = (RadioButton) findViewById(R.id.female_radio_button);
        genderGroup = (RadioGroup) findViewById(R.id.gender_group);
        inpHouseNo = (EditText) findViewById(R.id.houseNo);
        inpStreet = (EditText) findViewById(R.id.streetName);
        inpCity = (EditText) findViewById(R.id.cityName);
        inpCountry = (EditText) findViewById(R.id.countryName);
        inpState = (EditText) findViewById(R.id.stateName);
        inpDistrict = (EditText) findViewById(R.id.districtName);
        inpPinCode = (EditText) findViewById(R.id.pincode);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitButton.setEnabled(false);
                //put progress bar
                if(validateFullName() && validateAge() && validateGender() && validateAddress()){
                    progressBar.setVisibility(View.VISIBLE);
                    submitButton.setVisibility(View.INVISIBLE);

                    System.out.println("entered inside");
                    fullName = genFullName();
                    age = inpAge.getText().toString();
                    gender = getGender();
                    address = genAddress();
                    district = inpDistrict.getText().toString();
                    state = inpState.getText().toString();
                    country = inpCountry.getText().toString();
                    pinCode = inpPinCode.getText().toString();
                    location = inpCity.getText().toString();

                    addressMap = new HashMap<Integer, String>();

                    addressMap.put(0, customerId);
                    addressMap.put(1, fullName);
                    addressMap.put(2, mobileNo);
                    addressMap.put(3, address);
                    addressMap.put(6, age);
                    addressMap.put(7, gender);
                    addressMap.put(8, state);
                    addressMap.put(9, district);
                    addressMap.put(10, pinCode);
                    addressMap.put(11, country);
                    addressMap.put(12, location);

                    try {
                        SendPersonalDetails sendPersonalDetails = new SendPersonalDetails(addressMap);
                        NetworkAddress networkAddress = NetworkAddress.getInstance();
                        sendPersonalDetails.setPortIp(networkAddress.getIpAddress(), networkAddress.getPortNo());
                        System.out.println("before sendPersonalDetails Executed");
                        boolean isInserted = sendPersonalDetails.execute().get();

                        if(isInserted){
                            progressBar.setVisibility(View.GONE);
                            submitButton.setVisibility(View.VISIBLE);

                            Intent firstPageIntent = new Intent(AddressPage.this, FirstActivity.class);
                            firstPageIntent.putExtra("customer_id", customerId);

                            startActivity(firstPageIntent);
                        }

                    } catch (ExecutionException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        });
    }

    private boolean validateFullName(){
        System.out.println("Validate full name executed");
//        if(inpFirstName.getText().toString().isEmpty()){
//            inpFirstName.requestFocus();
//            inpFirstName.setError("Required");
//            return false;
//        }
//        if(inpLastName.getText().toString().isEmpty()){
//            inpLastName.requestFocus();
//            inpLastName.setError("Required");
//            return false;
//        }
        return true;
    }

    private String genFullName(){
        return inpFirstName.getText().toString() + " " + inpMiddleName.getText().toString() + " " + inpLastName.getText().toString();
    }

    private boolean validateAge(){
        System.out.println("Validate age executed");
//        if(inpAge.getText().toString().isEmpty()){
//            inpAge.requestFocus();
//            inpAge.setError("Required");
//            return false;
//        }
//        if(Integer.parseInt(inpAge.getText().toString()) > 100){
//            inpAge.requestFocus();
//            inpAge.setError("Not more than 100");
//            return false;
//        }
        return true;
    }

    private boolean validateGender(){    //check this please
        System.out.println("Validate gender executed");
//        if(!inpMale.isChecked() || !inpFemale.isChecked()){
//            genderGroup.requestFocus();
//            return false;
//        }
        return true;
    }

    private String getGender(){
        if(inpMale.isChecked()){
            return inpMale.getText().toString();
        }
        else if(inpFemale.isChecked()){
            return inpFemale.getText().toString();
        }
        return null;
    }

    private boolean validateAddress(){
        System.out.println("Validate address executed");
//        if(inpHouseNo.getText().toString().isEmpty()){
//            inpHouseNo.requestFocus();
//            inpHouseNo.setError("Required");
//            return false;
//        }
//        if(inpStreet.getText().toString().isEmpty()){
//            inpStreet.requestFocus();
//            inpStreet.setError("Required");
//            return false;
//        }
//        if(inpCity.getText().toString().isEmpty()){
//            inpCity.requestFocus();
//            inpCity.setError("Required");
//            return false;
//        }
//        if(inpCountry.getText().toString().isEmpty()){
//            inpCountry.requestFocus();
//            inpCountry.setError("Required");
//            return false;
//        }
//        if(inpState.getText().toString().isEmpty()){
//            inpState.requestFocus();
//            inpState.setError("Required");
//            return false;
//        }
//        if(inpDistrict.getText().toString().isEmpty()){
//            inpDistrict.requestFocus();
//            inpDistrict.setError("Required");
//            return false;
//        }
//        if(inpPinCode.getText().toString().isEmpty()){
//            inpPinCode.requestFocus();
//            inpPinCode.setError("Required");
//            return false;
//        }

        return true;
    }

    private String genAddress(){
        return inpHouseNo.getText().toString() + ", " + inpStreet.getText().toString() + ", " + inpCity.getText().toString() + " - " + inpPinCode.getText().toString();
    }
}