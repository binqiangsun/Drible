package com.sunbinqiang.drible.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.lukou.service.account.bean.User;

/**
 * Created by sunbinqiang on 27/07/2017.
 */

public class FollowerResult implements Parcelable{
    private int id;
    private String created_at;
    private User follower;

    public User getFollower() {
        return follower;
    }

    protected FollowerResult(Parcel in) {
        id = in.readInt();
        created_at = in.readString();
        follower = in.readParcelable(User.class.getClassLoader());
    }

    public static final Creator<FollowerResult> CREATOR = new Creator<FollowerResult>() {
        @Override
        public FollowerResult createFromParcel(Parcel in) {
            return new FollowerResult(in);
        }

        @Override
        public FollowerResult[] newArray(int size) {
            return new FollowerResult[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(created_at);
        dest.writeParcelable(follower, flags);
    }
}
