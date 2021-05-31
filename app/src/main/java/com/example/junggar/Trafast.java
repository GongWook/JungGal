package com.example.junggar;

import com.google.gson.annotations.SerializedName;

public class Trafast {
    @SerializedName("summary")
    private Summary summary;

    @SerializedName("path")
    private Double[][] path;

    @SerializedName("section")
    private Section[] section;

    @SerializedName("guide")
    private Guide[] guide;

    public Summary getSummary ()
    {
        return summary;
    }

    public void setSummary (Summary summary)
    {
        this.summary = summary;
    }

    public Double[][] getPath ()
    {
        return path;
    }

    public void setPath (Double[][] path)
    {
        this.path = path;
    }

    public Section[] getSection ()
    {
        return section;
    }

    public void setSection (Section[] section)
    {
        this.section = section;
    }

    public Guide[] getGuide ()
    {
        return guide;
    }

    public void setGuide (Guide[] guide)
    {
        this.guide = guide;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [summary = "+summary+", path = "+path+", section = "+section+", guide = "+guide+"]";
    }
}
