package com.example.hm.myanimation;

/**
 * Created by Ferenc on 2016.06.08..
 */
public class Title {
    private String platform;
    private int touchedCircle;

    public void setPlatform(int _touchedCircle){
        touchedCircle = _touchedCircle;
        if(_touchedCircle == 1){
            platform = "Android";
        }
        if(_touchedCircle == 2){
            platform = "iOS";
        }
        if(_touchedCircle == 3){
            platform = "Windows";
        }
    }

    public String getPlatform(){
        return platform;
    }

    public int getTouchedCircle(){
        return touchedCircle;
    }
}
