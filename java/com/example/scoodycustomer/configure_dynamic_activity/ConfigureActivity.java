package com.example.scoodycustomer.configure_dynamic_activity;

import com.example.scoodycustomer.Network.GetCustomerDetails;
import com.example.scoodycustomer.Network.NetworkAddress;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class ConfigureActivity {
    protected HashMap<Integer, String> customerDataReceivable;
    private String customerId;

    public ConfigureActivity(String customerId){
        this.customerId = customerId;
        try {
            HashMap<Integer, String> customerData = new HashMap<Integer, String>();
            customerData.put(0, customerId);
            NetworkAddress networkAddress = NetworkAddress.getInstance();
            GetCustomerDetails getCustomerDetails = new GetCustomerDetails(customerData);
            getCustomerDetails.setPortIp(networkAddress.getIpAddress(), networkAddress.getPortNo());
            customerDataReceivable = getCustomerDetails.execute().get();
            System.out.println(customerDataReceivable);

        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}

