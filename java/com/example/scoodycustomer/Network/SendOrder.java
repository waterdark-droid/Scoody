package com.example.scoodycustomer.Network;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;

public class SendOrder extends AsyncTask<Void, Void, Boolean> {

    private HashMap<Integer, HashMap<Integer, String>> orderDetails;
    private String address;
    private int port;

    private OutputStream out;
    private InputStream in;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private DataInputStream dIn;
    private DataOutputStream dOut;

    public SendOrder(HashMap<Integer, HashMap<Integer, String>> orderDetails){
        this.orderDetails = orderDetails;
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
            System.out.println("Sent 1");
            Socket sendOrderSocket = new Socket(address, port);
            out = sendOrderSocket.getOutputStream();
            dOut = new DataOutputStream(out);
            dOut.writeInt(NetworkAddress.STORE_ORDER);
            dIn = new DataInputStream(new BufferedInputStream(sendOrderSocket.getInputStream()));
            if(dIn.readUTF().equals("ack1")){
                oos = new ObjectOutputStream(out);
                oos.writeObject(orderDetails);
                boolean isInserted = dIn.readBoolean();
                if(isInserted){
                    return true;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}