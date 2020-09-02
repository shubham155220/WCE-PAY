package com.abc.wcapp.Model;

public class Products {
    private String Category,Description,Price,Date,Time,pid,Image;

    public Products(String category, String description, String price, String date, String time, String pid,String image) {
        Category = category;
        Description = description;
        Price = price;
        Date = date;
        Time = time;
        this.pid = pid;
        Image = image;
    }
    public Products()
    {

    }

    public String getImage() {
        return Image;
    }

    public String getCategory() {
        return Category;
    }

    public String getDescription() {
        return Description;
    }

    public String getPrice() {
        return Price;
    }

    public String getDate() {
        return Date;
    }

    public String getTime() {
        return Time;
    }

    public String getPid() {
        return pid;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public void setDate(String date) {
        Date = date;
    }

    public void setTime(String time) {
        Time = time;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public void setImage(String image) {
        Image = image;
    }
}
