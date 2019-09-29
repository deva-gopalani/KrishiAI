package com.example.android.greenharvest;

public class Temperature {
    private String tempValue;

    public Temperature()
    {

    }

    public Temperature(String humidityValue){
        /*this.id = id;*/
        this.tempValue=humidityValue;
    }

    public String getTemperature(){return tempValue;}
}
