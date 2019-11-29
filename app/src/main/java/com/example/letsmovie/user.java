package com.example.letsmovie;

public class user {

    String UserID,name;
    Integer credidtcard,expiry,cvv;


    public user(String id, String displayname, String displayphone, String displaycreditcard){

    }

    public user(String userID, String name, Integer credidtcard, Integer expiry, Integer cvv) {
        UserID = userID;
        this.name = name;
        this.credidtcard = credidtcard;
        this.expiry = expiry;
        this.cvv = cvv;
    }

    public String getUserID() {
        return UserID;
    }

    public String getName() {
        return name;
    }

    public Integer getCredidtcard() {
        return credidtcard;
    }

    public Integer getExpiry() {
        return expiry;
    }

    public Integer getCvv() {
        return cvv;
    }
}
