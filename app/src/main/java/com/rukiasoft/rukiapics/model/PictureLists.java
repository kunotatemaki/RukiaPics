package com.rukiasoft.rukiapics.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;


/**
 * Created by Roll on 10/7/17.
 */

public class PictureLists implements Parcelable {
    private List<PicturePojo> listPublished;
    private List<PicturePojo> listTaken;

    public List<PicturePojo> getListPublished() {
        return listPublished;
    }

    public void setListPublished(List<PicturePojo> listPublished) {
        this.listPublished = listPublished;
    }

    public List<PicturePojo> getListTaken() {
        return listTaken;
    }

    public void setListTaken(List<PicturePojo> listTaken) {
        this.listTaken = listTaken;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.listPublished);
        dest.writeTypedList(this.listTaken);
    }

    public PictureLists() {
    }

    protected PictureLists(Parcel in) {
        this.listPublished = in.createTypedArrayList(PicturePojo.CREATOR);
        this.listTaken = in.createTypedArrayList(PicturePojo.CREATOR);
    }

    public static final Parcelable.Creator<PictureLists> CREATOR = new Parcelable.Creator<PictureLists>() {
        @Override
        public PictureLists createFromParcel(Parcel source) {
            return new PictureLists(source);
        }

        @Override
        public PictureLists[] newArray(int size) {
            return new PictureLists[size];
        }
    };
}
