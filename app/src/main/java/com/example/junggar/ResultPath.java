package com.example.junggar;

import com.google.gson.annotations.SerializedName;

public class ResultPath {
    @SerializedName("code")
    private String code;

    @SerializedName("route")
    private Route route;

    @SerializedName("currentDateTime")
    private String currentDateTime;

    @SerializedName("message")
    private String message;

    public String getCode ()
    {
        return code;
    }

    public void setCode (String code)
    {
        this.code = code;
    }

    public Route getRoute ()
    {
        return route;
    }

    public void setRoute (Route route)
    {
        this.route = route;
    }

    public String getCurrentDateTime ()
    {
        return currentDateTime;
    }

    public void setCurrentDateTime (String currentDateTime)
    {
        this.currentDateTime = currentDateTime;
    }

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [code = "+code+", route = "+route+", currentDateTime = "+currentDateTime+", message = "+message+"]";
    }
}
