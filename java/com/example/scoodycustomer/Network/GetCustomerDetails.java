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

public class GetCustomerDetails extends AsyncTask<Void, Void, HashMap<Integer, String>> {

    private String address;
    private HashMap<Integer, String> customerDataSendable, customerDataReceivable;
    private OutputStream out;

    private InputStream in;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private DataInputStream dIn;
    private DataOutputStream dOut;
    private int port;

    public GetCustomerDetails(HashMap<Integer, String> customerDataSendable){
        this.customerDataSendable = customerDataSendable;
    }

    public void setPortIp(String address, int port){
        this.address = address;
        this.port = port;
    }


    @Override
    protected void onPreExecute() {

    }

    @Override
    protected void onPostExecute(HashMap<Integer, String> sArr) {
    }

    @Override
    protected HashMap<Integer, String> doInBackground(Void... voids) {
        try {
            Socket getAddressSocket = new Socket(address, port);
            out = getAddressSocket.getOutputStream();
            dOut = new DataOutputStream(out);   //Make OutputStream first to initialize communication
            dOut.writeInt(NetworkAddress.GET_CUSTOMER_DATA);
            dIn = new DataInputStream(new BufferedInputStream(getAddressSocket.getInputStream()));
            if(dIn.readUTF().equals("ack1")){
                oos = new ObjectOutputStream(out);
                oos.writeObject(customerDataSendable);
                dOut.writeUTF("ack2");
                in = getAddressSocket.getInputStream();
                ois = new ObjectInputStream(in);
                customerDataReceivable = (HashMap<Integer, String>) ois.readObject();
            }

            oos.close();
            out.close();
            dOut.close();
            dIn.close();
            getAddressSocket.close();

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return customerDataReceivable;
    }
}
