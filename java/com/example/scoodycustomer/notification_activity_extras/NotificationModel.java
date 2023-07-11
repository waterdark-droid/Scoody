package com.example.scoodycustomer.notification_activity_extras;

public class NotificationModel {

    public static final int LayoutOne = 0;
    public static final int LayoutTwo = 1;

    //for ordertaken layout
    private String empName, contactNo;
    private int viewType;
    private String product;

    public NotificationModel(int viewType, String empName, String contactNo){ //layoutOne
        this.empName = empName;
        this.contactNo = contactNo;
        this.viewType = viewType;

    }

    public String getEmpName(){
        return empName;
    }
    public String getContactNo(){
        return contactNo;
    }





    //for delivered layout


    public NotificationModel(int viewType, String product){ //layoutTwo
        this.product = product;
        this.viewType = viewType;

    }

    public String getProduct(){
        return product;
    }

    public int getViewType(){
        return viewType;
    }


    public void setViewType(int viewType){
        this.viewType = viewType;
    }
}

