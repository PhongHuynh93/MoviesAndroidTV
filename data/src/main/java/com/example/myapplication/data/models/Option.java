package com.example.myapplication.data.models;

/**
 * Created by user on 9/17/2017.
 */

public class Option {
    public String title;
    public String value;
    public int iconResource;

    public Option(String title, String value, int iconResource) {
        this.title = title;
        this.value = value;
        this.iconResource = iconResource;
    }
}
