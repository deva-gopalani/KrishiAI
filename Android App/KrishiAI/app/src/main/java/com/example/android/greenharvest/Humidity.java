package com.example.android.greenharvest;

public class Humidity {
    /*private String id;*/
    private String humidityValue;

    public Humidity()
    {

    }

    public Humidity(String humidityValue){
        /*this.id = id;*/
        this.humidityValue=humidityValue;
    }

    public String getHumidity(){return humidityValue;}
    /*public String getId(){return id;}*/
}

