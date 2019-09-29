package com.example.android.greenharvest;

public class User {
    private String email;
    //private String password;
    private String saatBara;
    private String crop_type;
    private String soil_type;
    private String name;
    private String lat;
    private String longg;
    private String area;
    private String pest;
    private String pic;

    public User() {

    }

    public User(String email, String saatBara, String crop_type, String soil_type, String name, String lat, String longg, String area, String pest, String pic){
        this.email=email;
        //this.password=password;
        this.saatBara=saatBara;
        this.crop_type=crop_type;
        this.soil_type=soil_type;
        this.name=name;
        this.lat=lat;
        this.longg=longg;
        this.area=area;
        this.pest=pest;
        this.pic=pic;
    }

    public String getEmail(){return email;}
    //public String getPassword(){return password;}
    public String getSaatBara(){return saatBara;}
    public String getCrop_type(){return crop_type;}
    public String getSoil_type(){return soil_type;}
    public String getName(){return name;}
    public String getLat(){return lat;}
    public String getLongg(){return longg;}
    public String getArea(){return area;}
    public String getPest(){return pest;}
    public String getPic(){return pic;}

}