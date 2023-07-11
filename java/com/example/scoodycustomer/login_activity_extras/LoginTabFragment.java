package com.example.scoodycustomer.login_activity_extras;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.example.scoodycustomer.FirstActivity;
import com.example.scoodycustomer.Network.GetCustomerIdTask;
import com.example.scoodycustomer.Network.NetworkAddress;
import com.example.scoodycustomer.Network.SearchMobileTask;
import com.example.scoodycustomer.Network.ValidateLogin;
import com.example.scoodycustomer.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;


public class LoginTabFragment extends Fragment {
    AppCompatButton loginBtn;
    TextInputEditText mobileNo, password;
    NetworkAddress networkAddress;

    String indiaCode = "+91";
    CheckBox rememberMeChk;
    public static final String SHARED_PREFS = "LOGIN_INFO";


    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup root = (ViewGroup) layoutInflater.inflate(R.layout.login_tab_fragment, container, false);

        mobileNo = (TextInputEditText) root.findViewById(R.id.login_mobile_no);
        password = (TextInputEditText) root.findViewById(R.id.login_password);
        loginBtn = (AppCompatButton) root.findViewById(R.id.loginButton);
        rememberMeChk = (CheckBox) root.findViewById(R.id.remember_me);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        String userName = sharedPreferences.getString("mobile_number", "");
        String mobileNumber = sharedPreferences.getString("user_password","");
        System.out.println("shared prefs username : " + userName);
        System.out.println("shared prefs mobile no : " + mobileNumber);
        if(!userName.equals("") && !mobileNumber.equals("")){
            try {
                loginFunc(userName, mobileNumber);
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setElevation(0);
                try {
                    String mobile_number =  "+91" + mobileNo.getText().toString();
                    String user_password = password.getText().toString();
                    loginFunc(mobileNo.getText().toString(), user_password);
                } catch (ExecutionException | InterruptedException e) {
                    throw new RuntimeException(e);
                }


            }
        });
        return root;
    }

    public boolean validatePassword(String user_password){
        if(user_password.equals("")){
            password.setError("Invalid format");
            return false;
        }
        return true;
    }



    public boolean validateMobileNo(String mobile_number) throws ExecutionException, InterruptedException {
        if(mobile_number.length() == 10){
            networkAddress = NetworkAddress.getInstance();
            SearchMobileTask searchMobileTask = new SearchMobileTask(mobile_number);
            searchMobileTask.setPortIp(networkAddress.getIpAddress(), networkAddress.getPortNo());
            boolean isAvailable = searchMobileTask.execute().get();
            if(isAvailable){
                return true;
            }
            mobileNo.setError("Mobile number does not exists");
        }
        else{
            mobileNo.setError("Invalid format");
        }
        return false;
    }

    public void goToAttract(String mobile_number) throws ExecutionException, InterruptedException {
        NetworkAddress networkAddress1 = NetworkAddress.getInstance();
        GetCustomerIdTask getCustomerIdTask = new GetCustomerIdTask("+91" + mobile_number);
        getCustomerIdTask.setPortIp(networkAddress1.getIpAddress(), networkAddress1.getPortNo());
        String customerId = getCustomerIdTask.execute().get();
        Intent intent = new Intent(getActivity(), FirstActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("customer_id", customerId);
        startActivity(intent);
    }

    private void storedDataUsingSharedPref(String mobileNo, String password){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("mobile_number", mobileNo);
        editor.putString("user_password", password);
        editor.apply();
    }

    private void loginFunc(String mobile_number, String user_password) throws ExecutionException, InterruptedException {
        if(validateMobileNo(mobile_number) && validatePassword(user_password)){
            HashMap<Integer, String> loginData = new HashMap<Integer, String>();

            loginData.put(0,"+91" + mobile_number);
            loginData.put(1, user_password);
            networkAddress = NetworkAddress.getInstance();
            ValidateLogin validateLogin = new ValidateLogin(loginData);
            validateLogin.setPortIp(networkAddress.getIpAddress(), networkAddress.getPortNo());
            boolean isInserted = validateLogin.execute().get();
            if(isInserted){
                if(rememberMeChk.isChecked()){

                    storedDataUsingSharedPref(mobile_number, user_password);
                }
                Toast.makeText(getContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                goToAttract(mobile_number);
            }
            else{
                Toast.makeText(getContext(), "Mobile number or password is Incorrect", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
