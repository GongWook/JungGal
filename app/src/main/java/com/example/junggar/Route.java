package com.example.junggar;

import com.google.gson.annotations.SerializedName;

public class Route {
    @SerializedName("trafast")
    private Trafast[] trafast;

    public Trafast[] getTrafast ()
    {
        return trafast;
    }

    public void setTrafast (Trafast[] trafast)
    {
        this.trafast = trafast;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [trafast = "+trafast+"]";
    }
}
