package com.example.test.app.monsoonco.androidchallenge;

import java.io.Serializable;

/**
 * Created by varunsundaramoorthy on 10/22/14.
 */
public class Task implements Serializable{
    private String description;
    private int bgColor;
    private int txtColor;

    public Task(String description,int bgColor,int txtColor){
        this.description=description;
        this.bgColor=bgColor;
        this.txtColor=txtColor;
    }

    public String getDesc(){ return description;}
    public int getBgColor(){ return bgColor;}
    public int getTxtColor(){ return txtColor;}
}
