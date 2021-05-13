package com.example.junggar;

import android.app.Application;

public class UserInfoApplication extends Application {

    private String UserId;
    private String UserName;




    public void setGlobalUserId(String UserId){
        this.UserId = UserId;
    }

    public void setGlobalUserName(String UserName){
        this.UserName = UserName;
    }

    public String getGlobalUserId(){
        return UserId;
    }

    private static UserInfoApplication instance = null;

    public static synchronized UserInfoApplication getInstance(){
        if(null == instance){
            instance = new UserInfoApplication();
        }
        return instance;
    }
}
