package com.sunbinqiang.drible.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.sunbinqiang.drible.db.entity.Shot;

/**
 * Created by sunbinqiang on 8/31/16.
 */

public class ShotResult implements Parcelable {
    private int id;
    private String created_at;
    private Shot shot;

    protected ShotResult(Parcel in) {
        id = in.readInt();
        created_at = in.readString();
        shot = in.readParcelable(Shot.class.getClassLoader());
    }

    public static final Creator<ShotResult> CREATOR = new Creator<ShotResult>() {
        @Override
        public ShotResult createFromParcel(Parcel in) {
            return new ShotResult(in);
        }

        @Override
        public ShotResult[] newArray(int size) {
            return new ShotResult[size];
        }
    };

    public int getId() {
        return id;
    }

    public Shot getShot() {
        return shot;
    }

    public String getCreated_at() {
        return created_at;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(created_at);
        dest.writeParcelable(shot, flags);
    }
}
