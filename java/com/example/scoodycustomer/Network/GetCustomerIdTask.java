package com.example.scoodycustomer.Network;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class GetCustomerIdTask extends AsyncTask<Void, Void, String> {

    private String mobileNo, address, customerId;
    private int port;
    private DataOutputStream dOut;
    private DataInputStream dIn;

    public GetCustomerIdTask(String mobileNo){
        this.mobileNo = mobileNo;
    }

    public void setPortIp(String address, int port){
        this.address = address;
        this.port = port;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected void onPostExecute(String s) {

    }

    @Override
    protected String doInBackground(Void... voids) {

        try {
            Socket getCustomerIdTaskSocket = new Socket(address, port);
            dOut = new DataOutputStream(getCustomerIdTaskSocket.getOutputStream());
            dOut.writeInt(NetworkAddress.GET_CUSTOMER_ID);
            dIn = new DataInputStream(new BufferedInputStream(getCustomerIdTaskSocket.getInputStream()));

            if(dIn.readUTF().equals("ack1")){
                dOut.writeUTF(mobileNo);
                customerId = dIn.readUTF();
            }
            dOut.close();
            dIn.close();
            getCustomerIdTaskSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return customerId;

    }
}
