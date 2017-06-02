package com.lukou.service.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wangzhicheng on 2017/3/23.
 * 负责接收服务器的一些信息
 */

public class Config implements Parcelable {
    private int minVersion;
    private Version version;
    private String contactQQ;
    private String contactText;

    public void setVersion(Version version) {
        this.version = version;
    }

    public void setMinVersion(int minVersion) {
        this.minVersion = minVersion;
    }

    public String getContactQQ() {
        return contactQQ;
    }

    protected Config(Parcel in) {
        minVersion = in.readInt();
        version = in.readParcelable(Version.class.getClassLoader());
        contactQQ = in.readString();
        contactText = in.readString();
    }

    public static final Creator<Config> CREATOR = new Creator<Config>() {
        @Override
        public Config createFromParcel(Parcel in) {
            return new Config(in);
        }

        @Override
        public Config[] newArray(int size) {
            return new Config[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(minVersion);
        dest.writeParcelable(version,flags);
        dest.writeString(contactQQ);
        dest.writeString(contactText);
    }

    public int getMinVersion() {
        return minVersion;
    }

    public Version getVersion() {
        return version;
    }

    public String getContactText() {
        return contactText;
    }
}
