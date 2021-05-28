package com.example.junggar;

public class itemModel {
    //private String imageResource;
    private int image_marker;
    private String text1;
    private String text2;
    private String text3;

    /*
    public itemModel(String imageResource, String text1, String text2, String text3) {
        this.imageResource = imageResource;
        this.text1 = text1;
        this.text2 = text2;
        this.text3 = text3;
    }

     */

    public itemModel( int image_marker,String text1, String text2, String text3) {
        //this.imageResource = imageResource;
        this.image_marker=image_marker;
        this.text1 = text1;
        this.text2 = text2;
        this.text3 = text3;
    }

    /*
    public String getImageResource() {

        return imageResource;
    }

    public void setImageResource(String imageResource) {

        this.imageResource = imageResource;
    }


     */

    public int getImage_marker(){
        return image_marker;
    }

    public void setImage_marker(int image_marker){
        this.image_marker=image_marker;
    }

    public String getText1() {

        return text1;
    }

    public void setText1(String text1) {

        this.text1 = text1;
    }

    public String getText2() {

        return text2;
    }

    public void setText2(String text2) {

        this.text2 = text2;
    }

    public String getText3() {

        return text3;
    }

    public void setText3(String text3) {

        this.text3 = text3;
    }
}

