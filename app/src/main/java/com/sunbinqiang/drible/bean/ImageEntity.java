package com.sunbinqiang.drible.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sunbinqiang on 16/2/2.
 */
public class ImageEntity implements Parcelable {
    private String hidpi;
    private String normal;
    private String teaser;

    protected ImageEntity(Parcel in) {
        hidpi = in.readString();
        normal = in.readString();
        teaser = in.readString();
    }

    public static final Creator<ImageEntity> CREATOR = new Creator<ImageEntity>() {
        @Override
        public ImageEntity createFromParcel(Parcel in) {
            return new ImageEntity(in);
        }

        @Override
        public ImageEntity[] newArray(int size) {
            return new ImageEntity[size];
        }
    };

    public void setHidpi(String hidpi) {
        this.hidpi = hidpi;
    }

    public void setNormal(String normal) {
        this.normal = normal;
    }

    public void setTeaser(String teaser) {
        this.teaser = teaser;
    }

    public String getHidpi() {
        return hidpi;
    }

    public String getNormal() {
        return normal;
    }

    public String getTeaser() {
        return teaser;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(hidpi);
        dest.writeString(normal);
        dest.writeString(teaser);
    }
}
