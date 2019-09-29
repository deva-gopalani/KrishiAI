package com.example.android.greenharvest;

public class EditUserDetails {
    private String password;
    private String saatBara;
    private String crop_type;
    private String soil_type;
    private String name;

    public EditUserDetails() {

    }

    public EditUserDetails(String password, String saatBara, String crop_type, String soil_type, String name){
        this.password=password;
        this.saatBara=saatBara;
        this.crop_type=crop_type;
        this.soil_type=soil_type;
        this.name=name;
    }

    public String getPassword(){return password;}
    public String getSaatBara(){return saatBara;}
    public String getCrop_type(){return crop_type;}
    public String getSoil_type(){return soil_type;}
    public String getName(){return name;}
}
