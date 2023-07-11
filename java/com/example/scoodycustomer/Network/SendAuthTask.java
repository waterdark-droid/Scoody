package com.example.scoodycustomer.Network;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;


public class SendAuthTask extends AsyncTask<Void, Void, Boolean>{
    private String address;
    private int portNo;
    private OutputStream out;
    private DataInputStream in;
    private DataOutputStream dOut;
    private ObjectOutputStream oos;
    private HashMap<Integer, String> authData;


    public SendAuthTask(HashMap<Integer, String> authData){
        this.authData = authData;
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
        try{
            Socket sendAuthSocket = new Socket(address, portNo);
            out = sendAuthSocket.getOutputStream();
            dOut = new DataOutputStream(sendAuthSocket.getOutputStream());
            dOut.writeInt(NetworkAddress.STORE_AUTH);
            in = new DataInputStream(new BufferedInputStream(sendAuthSocket.getInputStream()));
            if(in.readUTF().equals("ack1")){
                oos = new ObjectOutputStream(out);
                oos.writeObject(authData);
                boolean isInserted = in.readBoolean();
                if(isInserted){
                    return true;
                }
            }

            oos.close();
            out.close();
            dOut.close();
            in.close();
            sendAuthSocket.close();


        } catch(IOException e ){
            e.printStackTrace();
        }

        return false;
    }

}