package com.rukiasoft.rukiapics.model;

/**
 * Created by Roll on 8/7/17.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PicturePojo implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("owner")
    @Expose
    private String owner;
    @SerializedName("ownername")
    @Expose
    private String ownername;
    @SerializedName("secret")
    @Expose
    private String secret;
    @SerializedName("server")
    @Expose
    private String server;
    @SerializedName("farm")
    @Expose
    private Integer farm;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("ispublic")
    @Expose
    private Integer ispublic;
    @SerializedName("isfriend")
    @Expose
    private Integer isfriend;
    @SerializedName("isfamily")
    @Expose
    private Integer isfamily;
    @SerializedName("dateupload")
    @Expose
    private String dateupload;
    @SerializedName("datetaken")
    @Expose
    private String datetaken;
    @SerializedName("datetakengranularity")
    @Expose
    private String datetakengranularity;
    @SerializedName("datetakenunknown")
    @Expose
    private String datetakenunknown;
    @SerializedName("url_m")
    @Expose
    private String urlM;
    @SerializedName("height_m")
    @Expose
    private String heightM;
    @SerializedName("width_m")
    @Expose
    private String widthM;

    private long timestamp;

    public PicturePojo() {
        //set timestamp on creation
        timestamp = System.currentTimeMillis();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public Integer getFarm() {
        return farm;
    }

    public void setFarm(Integer farm) {
        this.farm = farm;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getIspublic() {
        return ispublic;
    }

    public void setIspublic(Integer ispublic) {
        this.ispublic = ispublic;
    }

    public Integer getIsfriend() {
        return isfriend;
    }

    public void setIsfriend(Integer isfriend) {
        this.isfriend = isfriend;
    }

    public Integer getIsfamily() {
        return isfamily;
    }

    public void setIsfamily(Integer isfamily) {
        this.isfamily = isfamily;
    }

    public String getDateupload() {
        return dateupload;
    }

    public void setDateupload(String dateupload) {
        this.dateupload = dateupload;
    }

    public String getDatetaken() {
        return datetaken;
    }

    public void setDatetaken(String datetaken) {
        this.datetaken = datetaken;
    }

    public String getDatetakengranularity() {
        return datetakengranularity;
    }

    public void setDatetakengranularity(String datetakengranularity) {
        this.datetakengranularity = datetakengranularity;
    }

    public String getDatetakenunknown() {
        return datetakenunknown;
    }

    public void setDatetakenunknown(String datetakenunknown) {
        this.datetakenunknown = datetakenunknown;
    }

    public String getUrlM() {
        return urlM;
    }

    public void setUrlM(String urlM) {
        this.urlM = urlM;
    }

    public String getHeightM() {
        return heightM;
    }

    public void setHeightM(String heightM) {
        this.heightM = heightM;
    }

    public String getWidthM() {
        return widthM;
    }

    public void setWidthM(String widthM) {
        this.widthM = widthM;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getOwnername() {
        return ownername;
    }

    public void setOwnername(String ownername) {
        this.ownername = ownername;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.owner);
        dest.writeString(this.ownername);
        dest.writeString(this.secret);
        dest.writeString(this.server);
        dest.writeValue(this.farm);
        dest.writeString(this.title);
        dest.writeValue(this.ispublic);
        dest.writeValue(this.isfriend);
        dest.writeValue(this.isfamily);
        dest.writeString(this.dateupload);
        dest.writeString(this.datetaken);
        dest.writeString(this.datetakengranularity);
        dest.writeString(this.datetakenunknown);
        dest.writeString(this.urlM);
        dest.writeString(this.heightM);
        dest.writeString(this.widthM);
        dest.writeLong(this.timestamp);
    }

    protected PicturePojo(Parcel in) {
        this.id = in.readString();
        this.owner = in.readString();
        this.ownername = in.readString();
        this.secret = in.readString();
        this.server = in.readString();
        this.farm = (Integer) in.readValue(Integer.class.getClassLoader());
        this.title = in.readString();
        this.ispublic = (Integer) in.readValue(Integer.class.getClassLoader());
        this.isfriend = (Integer) in.readValue(Integer.class.getClassLoader());
        this.isfamily = (Integer) in.readValue(Integer.class.getClassLoader());
        this.dateupload = in.readString();
        this.datetaken = in.readString();
        this.datetakengranularity = in.readString();
        this.datetakenunknown = in.readString();
        this.urlM = in.readString();
        this.heightM = in.readString();
        this.widthM = in.readString();
        this.timestamp = in.readLong();
    }

    public static final Parcelable.Creator<PicturePojo> CREATOR = new Parcelable.Creator<PicturePojo>() {
        @Override
        public PicturePojo createFromParcel(Parcel source) {
            return new PicturePojo(source);
        }

        @Override
        public PicturePojo[] newArray(int size) {
            return new PicturePojo[size];
        }
    };
}
