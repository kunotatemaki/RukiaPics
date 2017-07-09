package com.rukiasoft.rukiapics.model;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by iRoll on 29/1/17.
 */

public class FlickrResponse {

    @SerializedName("photos")
    @Expose
    private JsonObject photos;

    public JsonObject getPhotos() {
        return photos;
    }

    public void setPhotos(JsonObject photos) {
        this.photos = photos;
    }
}

