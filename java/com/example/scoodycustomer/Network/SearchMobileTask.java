package com.example.scoodycustomer.Network;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class SearchMobileTask extends AsyncTask<Void, Void, Boolean>{
    private String mobileNo, address;
    private int portNo;
    private DataOutputStream out;
    private DataInputStream in;

    public SearchMobileTask(String mobileNo){
        this.mobileNo = "+91" + mobileNo;
    }


    public void setPortIp(String address, int portNo){
        this.portNo = portNo;
        this.address = address;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {

    }

    @Override
    protected Boolean doInBackground(Void... voids) {

        try {
            System.out.println("ValidateMobileNo executed");
            Socket mobileNoSocket = new Socket(address, portNo);
            in = new DataInputStream(new BufferedInputStream(mobileNoSocket.getInputStream()));
            out = new DataOutputStream(mobileNoSocket.getOutputStream());
            out.writeInt(NetworkAddress.FIND_MOBILE_NO);

            out.writeUTF(mobileNo);
            boolean isAvailable = in.readBoolean();
            if (isAvailable) {
                return true;
            }

            in.close();
            out.close();
            mobileNoSocket.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return false;
    }
}
