package com.example.scoodycustomer.Network;

import android.os.AsyncTask;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SearchUserTask extends AsyncTask<Void, Void, Boolean> {
    int portNo;
    String address, stringData;
    DataOutputStream out;
    DataInputStream in;

    public SearchUserTask(String stringData){

        this.stringData = stringData;
    }
    public void setPortIp(String address, int portNo) {
        this.portNo = portNo;
        this.address = address;
    }


    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            System.out.println("ValidateUserName executed 1");
            Socket userNameSocket = new Socket(address, portNo);
            System.out.println("ValidateUserName executed 2");
            in = new DataInputStream(new BufferedInputStream(userNameSocket.getInputStream()));
            out = new DataOutputStream(userNameSocket.getOutputStream());
            out.writeInt(NetworkAddress.FIND_USERNAME);
            out.writeUTF(stringData);
            boolean isAvailable = in.readBoolean();
            if(isAvailable){
                return true;
            }

            in.close();
            out.close();
            userNameSocket.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return false;
    }



}
