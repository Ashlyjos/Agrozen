package mca.project.agrozen;

import com.android.volley.toolbox.StringRequest;

public class Product {
    private String pdname, image;
    private String price, phoneno;
    private String location, duration;

    public Product (String pdname,String price,String phoneno,String duration,String loc,String image){

        this.pdname = pdname;
        this.image = image;
        this.phoneno = phoneno;
        this.location = loc;
        this.duration = duration;
        this.price = price;
    }

    public String getpdname() {
        return pdname;
    }

    public void setpdname(String pdname) {
        this.pdname = pdname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getphoneno() {
        return phoneno;
    }

    public void setphoneno(String phoneno) {
        this.phoneno = phoneno;
    }
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}