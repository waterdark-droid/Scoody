package com.example.scoodycustomer.Network;

public class NetworkAddress {
    private static final int portNo = 14288;
    private static final String ipAddress = "0.tcp.in.ngrok.io";

    private static NetworkAddress networkAddress;
    public static final int FIND_USERNAME = 250;
    public static final int FIND_MOBILE_NO = 251;
    public static final int STORE_AUTH = 252;
    public static final int VALIDATE_MOBILE_NO_PASSWORD = 253;
    public static final int STORE_PERSONAL = 254;
    public static final int GET_CUSTOMER_DATA = 255;
    public static final int GET_CUSTOMER_ID = 256;

    public static final int STORE_ORDER = 257;

    public static final int GET_NOTIFICATION = 450;

    private NetworkAddress(){
    }

    public static NetworkAddress getInstance(){
        if(networkAddress == null){
            networkAddress = new NetworkAddress();
        }
        return networkAddress;
    }
    public int getPortNo() {
        return portNo;
    }

    public String getIpAddress() {
        return ipAddress;
    }
}

