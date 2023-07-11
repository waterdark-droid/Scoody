package com.example.scoodycustomer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.scoodycustomer.Network.NetworkAddress;
import com.example.scoodycustomer.Network.SendAuthTask;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class OTPActivity extends AppCompatActivity {

    private EditText getOtp1, getOtp2, getOtp3, getOtp4, getOtp5, getOtp6;
    String verificationId;
    TextView mobileNoShow, timeSec, resendBtn,resendPrefix;
    ProgressBar progressBar;
    String mobileNumber, customerId, userName, password;
    AppCompatButton verifyBtn;
    NetworkAddress networkAddress;

    public static final String SHARED_PREFS = "LOGIN_INFO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpactivity);
        changeAppTheme();
        timeSec = (TextView) findViewById(R.id.time_sec);
        resendBtn = (TextView) findViewById(R.id.resend_btn);
        resendPrefix = (TextView) findViewById(R.id.resend_prefix);
        setTimer();

        mobileNoShow = (TextView) findViewById(R.id.mobile_no);
        mobileNumber = getIntent().getStringExtra("mobile_number");
        userName = getIntent().getStringExtra("user_name");
        password = getIntent().getStringExtra("password");
        mobileNoShow.setText(mobileNumber);

        getOtp1 = (EditText) findViewById(R.id.otp1);
        getOtp2 = (EditText) findViewById(R.id.otp2);
        getOtp3 = (EditText) findViewById(R.id.otp3);
        getOtp4 = (EditText) findViewById(R.id.otp4);
        getOtp5 = (EditText) findViewById(R.id.otp5);
        getOtp6 = (EditText) findViewById(R.id.otp6);



        setupOTPInput();
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        verifyBtn = (AppCompatButton) findViewById(R.id.verify_btn);
        verificationId = getIntent().getStringExtra("verification_id");

        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getOtp1.getText().toString().trim().isEmpty()
                        || getOtp2.getText().toString().trim().isEmpty()
                        || getOtp3.getText().toString().trim().isEmpty()
                        || getOtp4.getText().toString().trim().isEmpty()
                        || getOtp5.getText().toString().trim().isEmpty()
                        || getOtp6.getText().toString().trim().isEmpty())
                {
                    Toast.makeText(OTPActivity.this, "Please enter valid OTP code", Toast.LENGTH_SHORT).show();
                    return;
                }
                String otpCode = getOtp1.getText().toString()
                        + getOtp2.getText().toString()
                        + getOtp3.getText().toString()
                        + getOtp4.getText().toString()
                        + getOtp5.getText().toString()
                        +getOtp6.getText().toString();

                if(verificationId != null){
                    progressBar.setVisibility(View.VISIBLE);
                    verifyBtn.setVisibility(View.INVISIBLE);
                    PhoneAuthCredential authCredential = PhoneAuthProvider.getCredential(
                            verificationId,
                            otpCode
                    );

                    FirebaseAuth.getInstance().signInWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressBar.setVisibility(View.GONE);
                            verifyBtn.setVisibility(View.VISIBLE);
                            if(task.isSuccessful()){
                                System.out.println("success");

                                try {
                                    customerId = genCustomerId();
                                    HashMap<Integer, String> authData = new HashMap<Integer, String>();
                                    authData.put(0, userName);
                                    authData.put(1, customerId);
                                    authData.put(2, "+91" + mobileNumber);
                                    authData.put(3, password);
                                    SendAuthTask sendAuthTask = new SendAuthTask(authData);
                                    networkAddress = NetworkAddress.getInstance();
                                    sendAuthTask.setPortIp(networkAddress.getIpAddress(), networkAddress.getPortNo());
                                    boolean isInserted = sendAuthTask.execute().get();
                                    if(isInserted){
                                        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("mobile_number", mobileNumber);
                                        editor.putString("user_password", password);
                                        editor.apply();
                                        Intent intent = new Intent(OTPActivity.this, AddressPage.class);
                                        intent.putExtra("customer_id",customerId);
                                        intent.putExtra("mobile_no", "+91" + mobileNumber);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    }


                                } catch (ExecutionException | InterruptedException e) {
                                    throw new RuntimeException(e);
                                }


                            }
                            else{
                                Toast.makeText(OTPActivity.this, "Verification Code Invalid", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });

        findViewById(R.id.resend_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91" + getIntent().getStringExtra("mobile_number"),
                        60, TimeUnit.SECONDS,
                        OTPActivity.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {

                            }

                            @Override
                            public void onCodeSent(@NonNull String newVerificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                                verificationId = newVerificationId;
                                setTimer();
                                Toast.makeText(OTPActivity.this, "OTP Sent", Toast.LENGTH_SHORT).show();

                            }
                        });
            }
        });
    }

    private void setTimer(){
        new CountDownTimer(60000, 1000){

            @Override
            public void onTick(long millisUntilFinished) {
                resendBtn.setVisibility(View.GONE);
                resendPrefix.setVisibility(View.GONE);
                NumberFormat format = new DecimalFormat("00");
                long sec = (millisUntilFinished / 1000) % 60;
                timeSec.setText("00:" +format.format(sec));
            }

            @Override
            public void onFinish() {
                resendBtn.setVisibility(View.VISIBLE);
                resendPrefix.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    private String genCustomerId(){
        long time = System.currentTimeMillis();
        Random random = new Random();
        int randomInt = random.nextInt(1000000);
        return time + "-" + randomInt;
    }

    private void setupOTPInput(){

        getOtp1.addTextChangedListener(new TextWatcher() {              //For getOtp1
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    getOtp2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        getOtp2.addTextChangedListener(new TextWatcher() {              //For getOtp2
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    getOtp3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        getOtp3.addTextChangedListener(new TextWatcher() {              //For getOtp3
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    getOtp4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        getOtp4.addTextChangedListener(new TextWatcher() {              //For getOtp4
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    getOtp5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        getOtp5.addTextChangedListener(new TextWatcher() {              //For getOtp5
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    getOtp6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });


    }

    public void changeAppTheme(){
        View decor = OTPActivity.this.getWindow().getDecorView();

        if(decor.getSystemUiVisibility() != View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR){
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        else{
            decor.setSystemUiVisibility(0);
        }
    }
}