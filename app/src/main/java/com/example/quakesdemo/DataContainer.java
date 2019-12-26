package com.example.quakesdemo;

public class DataContainer {
    //section of private data , states
    private String mMag; //this will hold the  mag data from response
    private String mPlace; //this will hold the place data from response
    private long mTime; //this will hold the time after convert it from milliseconds to real string date
    private String mURL;
    //end of private data


    //section of building a constructor
    public DataContainer(String mag, String place, long time, String url) {
        mMag = mag;
        mPlace = place;
        mTime = time;
        mURL = url;
    }

    //end of the constructor section


    //section of getters
    String getMagStrength() {
        return mMag;
    }

    String getPlace() {
        return mPlace;
    }

    long getTime() {
        return mTime;
    }

    String getURL() {
        return mURL;
    }

    //This will be used to handle the URL

}
