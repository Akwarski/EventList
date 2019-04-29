package com.example.eventlist.data;

import android.net.Uri;

public class EventData {
    private String title;
    private String data;
    private String description;
    private String imageResource;

    public EventData(String title, String data, String description, String imageResource) {
        this.title = title;
        this.data = data;
        this.description = description;
        this.imageResource = imageResource;
    }

    public String getTitle() {
        return title;
    }

    public String getData() {
        return data;
    }

    public String getDescription() {
        return description;
    }

    public String getImageResource() {
        return imageResource;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageResource(String imageResource) {
        this.imageResource = imageResource;
    }
}
