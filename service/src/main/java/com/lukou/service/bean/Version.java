package com.lukou.service.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wangzhicheng on 2017/3/23.
 */

public class Version implements Parcelable {

    private String url;
    private int version;
    private String introduction;

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void setVersion(int version) {

        this.version = version;
    }

    public void setUrl(String url) {

        this.url = url;
    }

    public Version(String url, int version, String introduction) {
        this.url = url;
        this.version = version;
        this.introduction = introduction;
    }

    public static final Creator<Version> CREATOR = new Creator<Version>() {
        @Override
        public Version createFromParcel(Parcel in) {
            return new Version(in);
        }

        @Override
        public Version[] newArray(int size) {
            return new Version[size];
        }
    };

    public String getUrl() {
        return url;
    }

    public int getVersion() {
        return version;
    }

    public String getIntroduction() {
        return introduction;
    }

    private Version(Parcel in) {
        version = in.readInt();
        url = in.readString();
        introduction = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(version);
        dest.writeString(url);
        dest.writeString(introduction);
    }
}
