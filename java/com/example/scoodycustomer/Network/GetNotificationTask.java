package com.example.scoodycustomer.Network;

import android.os.AsyncTask;

import com.example.scoodycustomer.notification_activity_extras.NotificationModel;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class GetNotificationTask extends AsyncTask<Void, Void, ArrayList<NotificationModel>> {

    private OutputStream out;
    private InputStream in;
    private DataInputStream dIn;
    private DataOutputStream dOut;

    private ObjectInputStream ois;
    private String address;
    private int port;

    public GetNotificationTask(){

    }

    public void setPortIp(String address, int port){
        this.address = address;
        this.port = port;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected void onPostExecute(ArrayList<NotificationModel> aNotificationModel) {

    }

    @Override
    protected ArrayList<NotificationModel> doInBackground(Void... voids) {
        ArrayList<NotificationModel> notifications = new ArrayList<NotificationModel>();
        try {
            Socket getNotificationSocket = new Socket(address, port);
            dOut = new DataOutputStream(getNotificationSocket.getOutputStream());
            dOut.writeInt(NetworkAddress.GET_NOTIFICATION);
            in = getNotificationSocket.getInputStream();
            dIn = new DataInputStream(in);
            if(dIn.readUTF().equals("ack1")){
                ois = new ObjectInputStream(in);
                ArrayList<HashMap<Integer, String>> getNotificationData = (ArrayList<HashMap<Integer, String>>) ois.readObject();
                System.out.println("Get notifications : " + getNotificationData);
                if(!getNotificationData.isEmpty()) {
                    for (int i = 0; i < getNotificationData.size(); i++) {
                        if (getNotificationData.get(i).get(0).equals("layoutOne")) {
                            notifications.add(0, new NotificationModel(0, getNotificationData.get(i).get(1), getNotificationData.get(i).get(2)));
                        } else {
                            notifications.add(0, new NotificationModel(1, getNotificationData.get(i).get(1)));
                        }
                    }
                    System.out.println("If executed");
                }
            }
            dIn.close();
            dOut.close();
            in.close();
            getNotificationSocket.close();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Notifications : " + notifications);

        return notifications;
    }
}
