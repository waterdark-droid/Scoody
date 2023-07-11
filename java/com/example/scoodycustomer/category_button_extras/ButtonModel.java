package com.example.scoodycustomer.category_button_extras;

public class ButtonModel {
    private int image;
    private String name;

    public ButtonModel(String name, int image){
        this.name = name;
        this.image = image;
    }

    public String getName(){
        return name;
    }

    public int getImageId(){
        return image;
    }
}