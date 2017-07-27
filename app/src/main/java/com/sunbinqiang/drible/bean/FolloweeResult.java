package com.sunbinqiang.drible.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.lukou.service.account.bean.User;

/**
 * Created by sunbinqiang on 27/07/2017.
 */

public class FolloweeResult implements Parcelable {
    private int id;
    private String created_at;
    private User followee;

    public User getFollowee() {
        return followee;
    }

    protected FolloweeResult(Parcel in) {
        id = in.readInt();
        created_at = in.readString();
        followee = in.readParcelable(User.class.getClassLoader());
    }

    public static final Creator<FolloweeResult> CREATOR = new Creator<FolloweeResult>() {
        @Override
        public FolloweeResult createFromParcel(Parcel in) {
            return new FolloweeResult(in);
        }

        @Override
        public FolloweeResult[] newArray(int size) {
            return new FolloweeResult[size];
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
        dest.writeParcelable(followee, flags);
    }
}
