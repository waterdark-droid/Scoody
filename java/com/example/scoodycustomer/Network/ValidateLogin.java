package com.example.scoodycustomer.Network;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;

public class ValidateLogin extends AsyncTask<Void, Void, Boolean> {
    private String address;
    private int port;

    private OutputStream out;
    private ObjectOutputStream oos;
    private DataInputStream dIn;
    private DataOutputStream dOut;

    private HashMap<Integer, String> loginData;

    public ValidateLogin(HashMap<Integer, String> loginData){
        this.loginData = loginData;
    }

    public void setPortIp(String address, int port){
        this.address = address;
        this.port = port;
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
            Socket validateLoginSocket = new Socket(address, port);
            dOut = new DataOutputStream(validateLoginSocket.getOutputStream());
            dOut.writeInt(NetworkAddress.VALIDATE_MOBILE_NO_PASSWORD);
            out = validateLoginSocket.getOutputStream();
            dIn = new DataInputStream(new BufferedInputStream(validateLoginSocket.getInputStream()));
            if(dIn.readUTF().equals("ack1")){
                oos = new ObjectOutputStream(out);
                oos.writeObject(loginData);
                boolean isInserted = dIn.readBoolean();

                if(isInserted){
                    return true;
                }
            }
            oos.close();
            out.close();
            dIn.close();
            dOut.close();
            validateLoginSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
