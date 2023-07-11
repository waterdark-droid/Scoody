package com.example.scoodycustomer.login_activity_extras;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.example.scoodycustomer.Network.NetworkAddress;
import com.example.scoodycustomer.Network.SearchMobileTask;
import com.example.scoodycustomer.Network.SearchUserTask;
import com.example.scoodycustomer.OTPActivity;
import com.example.scoodycustomer.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class SignupTabFragment extends Fragment {
    AppCompatButton signUpBtn;
    TextInputEditText userName, mobileNo, password, confPassword;
    NetworkAddress networkAddress;
    CheckBox termsAndCon;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup root = (ViewGroup) layoutInflater.inflate(R.layout.signup_tab_fragment, container, false);

        userName = (TextInputEditText) root.findViewById(R.id.user_name);
        mobileNo = (TextInputEditText) root.findViewById(R.id.mobile_no);
        password = (TextInputEditText) root.findViewById(R.id.password);
        confPassword = (TextInputEditText) root.findViewById(R.id.confirm_password);
        signUpBtn = (AppCompatButton) root.findViewById(R.id.signupButton);
        termsAndCon = (CheckBox) root.findViewById(R.id.checkBox);
        progressBar = (ProgressBar) root.findViewById(R.id.progressBar);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setElevation(0);

                try {
                    if(validateUserName(userName) && validateMobileNo(mobileNo)  && validatePassword(password)){

                        if(!password.getText().toString().equals(confPassword.getText().toString())){ //checking both the passwords
                            confPassword.requestFocus();
                            Toast.makeText(getContext(), "Password does not match", Toast.LENGTH_SHORT).show();

                            return;
                        }

                        if(!termsAndCon.isChecked()){
                            termsAndCon.requestFocus();
                            Toast.makeText(getContext(), "Please verify Terms and Conditions", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        progressBar.setVisibility(View.VISIBLE);
                        signUpBtn.setVisibility(View.INVISIBLE);

                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                "+91" + mobileNo.getText().toString(),
                                60, TimeUnit.SECONDS,
                                getActivity(),
                                new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                        progressBar.setVisibility(View.GONE);
                                        signUpBtn.setVisibility(View.VISIBLE);
                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {
                                        progressBar.setVisibility(View.GONE);
                                        signUpBtn.setVisibility(View.VISIBLE);
                                        Toast.makeText(getContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();

                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                                        progressBar.setVisibility(View.GONE);
                                        signUpBtn.setVisibility(View.VISIBLE);
                                        Intent intent = new Intent(getActivity(), OTPActivity.class);
                                        intent.putExtra("mobile_number", mobileNo.getText().toString());
                                        intent.putExtra("user_name", userName.getText().toString());
                                        intent.putExtra("password", password.getText().toString());
                                        intent.putExtra("verification_id", verificationId);
                                        startActivity(intent);

                                    }
                                });

                    }
                    else{
                        return;
                    }
                } catch (ExecutionException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        return root;
    }

    public boolean validateMobileNo(TextInputEditText mobileNo) throws ExecutionException, InterruptedException {//checking mobile number format is correct and already exists
        if(mobileNo.getText().toString().length() == 10){
            SearchMobileTask searchMobileTask = new SearchMobileTask(  mobileNo.getText().toString());
            networkAddress = NetworkAddress.getInstance();
            searchMobileTask.setPortIp(networkAddress.getIpAddress(), networkAddress.getPortNo());
            boolean isAvailable = searchMobileTask.execute().get();
            if(isAvailable){
                mobileNo.setError("Mobile Number already exists");
                return false;
            }
            return true;
        }
        mobileNo.setError("Invalid format");
        return false;
    }


    public boolean validatePassword(TextInputEditText password){ //checking password format
        if(password.getText().toString().length() > 6){
            return true;
        }
        Toast.makeText(getContext(), "Minimum 6 characters", Toast.LENGTH_SHORT).show();
        password.requestFocus();
        return false;
    }

    public boolean validateUserName(TextInputEditText userName) throws ExecutionException, InterruptedException { //checking username already exists and in correct format

        if(!userName.getText().toString().equals("")){
            SearchUserTask searchUserTask = new SearchUserTask(userName.getText().toString());
            networkAddress = NetworkAddress.getInstance();
            searchUserTask.setPortIp(networkAddress.getIpAddress(), networkAddress.getPortNo());
            boolean isAvailable = searchUserTask.execute().get();

            if(isAvailable){
                userName.setError("Username already exists");
                return false;
            }
            else{
                for(char a : userName.getText().toString().toCharArray()){
                    if(a == ' '){
                        userName.setError("Space is not allowed");
                        return false;
                    }
                }

                return true;
            }
        }
        else{
            userName.setError("Please specify a value");
            return false;
        }



    }


}