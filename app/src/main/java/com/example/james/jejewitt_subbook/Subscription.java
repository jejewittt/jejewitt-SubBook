package com.example.james.jejewitt_subbook;

/**
*Subsciption class
*
* functionality: stores data about a subsciption
* allows for updates
*
* handling - if no date is given, it sets it to the current date
*
 */
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Subscription implements java.io.Serializable{

    private String Name;
    private String comment;
    private Date date;
    private double ammount;


    //four cases of init for different inputs
    //if no date is given, then it uses current date
    public Subscription(String Name,String comment,String date,double ammount) {
        this.Name = Name;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            java.util.Date convertedDate = sdf.parse(date);
            this.date = convertedDate;
        }catch(ParseException ex){
            this.date = new Date(System.currentTimeMillis());
        }
        this.ammount = ammount;
        this.comment = comment;
    }

    public Subscription(String Name,String comment,double ammount) {
        this.Name = Name;
        this.ammount = ammount;
        this.date = new Date(System.currentTimeMillis());
        this.comment = comment;
    }

    //setters and getters for all variables

    public void setDate(String newDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date convertedDate = sdf.parse(newDate);
            this.date = convertedDate;
        }catch(ParseException ex){
            this.date = new Date(System.currentTimeMillis());
        }
    }

    public String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(this.date);
    }

    public void setName(String newName) {
        this.Name = newName;
    }

    public String getName() {
        return this.Name;
    }

    public void setComment(String newComment) {
        this.comment = newComment;
    }

    public String getComment() {
        return this.comment;
    }

    public void setAmmount(Double newAmmount) {
        this.ammount = newAmmount;
    }

    public String getAmmount() {
        return String.format("%.2f", this.ammount); //sent back as a string
    }

}