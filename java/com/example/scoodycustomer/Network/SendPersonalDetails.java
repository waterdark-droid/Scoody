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

public class SendPersonalDetails extends AsyncTask<Void, Void, Boolean> {

    private OutputStream out;
    private ObjectOutputStream oos;
    private DataOutputStream dOut;
    private DataInputStream dIn;
    private String address;
    private int port;
    private HashMap<Integer, String> personalDetails;

    public SendPersonalDetails(HashMap<Integer, String> personalDetails){
        this.personalDetails = personalDetails;
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
            Socket sendPersonalSocket = new Socket(address, port);
            out = sendPersonalSocket.getOutputStream();
            dOut = new DataOutputStream(sendPersonalSocket.getOutputStream());   //Make OutputStream first to initialize communication
            dOut.writeInt(NetworkAddress.STORE_PERSONAL);
            dIn = new DataInputStream(new BufferedInputStream(sendPersonalSocket.getInputStream()));
            if(dIn.readUTF().equals("ack1")){
                oos = new ObjectOutputStream(out);
                oos.writeObject(personalDetails);
                boolean isInserted = dIn.readBoolean();
                if(isInserted){
                    return true;
                }
            }

            oos.close();
            out.close();
            dOut.close();
            dIn.close();
            sendPersonalSocket.close();



        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;

    }
}