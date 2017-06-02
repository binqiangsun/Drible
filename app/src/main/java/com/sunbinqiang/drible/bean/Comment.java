package com.sunbinqiang.drible.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sunbinqiang on 6/18/16.
 */

public class Comment implements Parcelable {

    /**
     * id : 5359991
     * body : <p>Really digging your contrast-y style lately... &lt;3</p>
     * likes_count : 0
     * likes_url : https://api.dribbble.com/v1/shots/2784680/comments/5359991/likes
     * created_at : 2016-06-17T17:32:13Z
     * updated_at : 2016-06-17T17:32:13Z
     * user : {"id":337193,"name":"Oscar Manxz","username":"manxz_","html_url":"https://dribbble.com/manxz_","avatar_url":"","bio":"","location":"San Francisco","links":{"web":"http://www.manxz.com","twitter":"https://twitter.com/manxz_"},"buckets_count":5,"comments_received_count":67,"followers_count":373,"followings_count":580,"likes_count":4511,"likes_received_count":1780,"projects_count":0,"rebounds_received_count":2,"shots_count":36,"teams_count":0,"can_upload_shot":true,"type":"Player","pro":true,"buckets_url":"https://api.dribbble.com/v1/users/337193/buckets","followers_url":"https://api.dribbble.com/v1/users/337193/followers","following_url":"https://api.dribbble.com/v1/users/337193/following","likes_url":"https://api.dribbble.com/v1/users/337193/likes","projects_url":"https://api.dribbble.com/v1/users/337193/projects","shots_url":"https://api.dribbble.com/v1/users/337193/shots","teams_url":"https://api.dribbble.com/v1/users/337193/teams","created_at":"2013-05-22T02:42:26Z","updated_at":"2016-06-18T01:16:49Z"}
     */

    private int id;
    private String body;
    private int likes_count;
    private String likes_url;
    private String created_at;
    private String updated_at;
    private User user;

    protected Comment(Parcel in) {
        id = in.readInt();
        body = in.readString();
        likes_count = in.readInt();
        likes_url = in.readString();
        created_at = in.readString();
        updated_at = in.readString();
        user = in.readParcelable(User.class.getClassLoader());
    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getLikes_count() {
        return likes_count;
    }

    public void setLikes_count(int likes_count) {
        this.likes_count = likes_count;
    }

    public String getLikes_url() {
        return likes_url;
    }

    public void setLikes_url(String likes_url) {
        this.likes_url = likes_url;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(body);
        dest.writeInt(likes_count);
        dest.writeString(likes_url);
        dest.writeString(created_at);
        dest.writeString(updated_at);
        dest.writeParcelable(user, flags);
    }
}
