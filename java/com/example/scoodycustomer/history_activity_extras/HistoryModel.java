package com.example.scoodycustomer.history_activity_extras;

public class HistoryModel {
    private String receiverName, productStatus, receivedDate;

    public HistoryModel(String receiverName, String productStatus, String receivedDate){
        this.receiverName = receiverName;
        this.productStatus = productStatus;
        this.receivedDate = receivedDate;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public String getProductStatus(){
        return productStatus;
    }
    public String getReceivedDate(){
        return receivedDate;
    }
}
