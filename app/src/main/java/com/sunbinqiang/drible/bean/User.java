package com.sunbinqiang.drible.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

/**
 * Created by sunbinqiang on 16/2/2.
 */
public class User implements Parcelable {
    private int id;
    private String name;
    private String username;
    private String html_url;
    private String avatar_url;
    private String bio;
    private String location;
    /**
     * web : http://www.creativemints.com/
     * twitter : https://twitter.com/creativemints
     */

    private LinksEntity links;
    private int buckets_count;
    private int comments_received_count;
    private int followers_count;
    private int followings_count;
    private int likes_count;
    private int likes_received_count;
    private int projects_count;
    private int rebounds_received_count;
    private int shots_count;
    private int teams_count;
    private boolean can_upload_shot;
    private String type;
    private boolean pro;
    private String buckets_url;
    private String followers_url;
    private String following_url;
    private String likes_url;
    private String projects_url;
    private String shots_url;
    private String teams_url;
    private String created_at;
    private String updated_at;

    protected User(Parcel in) {
        id = in.readInt();
        name = in.readString();
        username = in.readString();
        html_url = in.readString();
        avatar_url = in.readString();
        bio = in.readString();
        location = in.readString();
        buckets_count = in.readInt();
        comments_received_count = in.readInt();
        followers_count = in.readInt();
        followings_count = in.readInt();
        likes_count = in.readInt();
        likes_received_count = in.readInt();
        projects_count = in.readInt();
        rebounds_received_count = in.readInt();
        shots_count = in.readInt();
        teams_count = in.readInt();
        can_upload_shot = in.readByte() != 0;
        type = in.readString();
        pro = in.readByte() != 0;
        buckets_url = in.readString();
        followers_url = in.readString();
        following_url = in.readString();
        likes_url = in.readString();
        projects_url = in.readString();
        shots_url = in.readString();
        teams_url = in.readString();
        created_at = in.readString();
        updated_at = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getHtml_url() {
        return html_url;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public String getBio() {
        return bio;
    }

    public String getLocation() {
        return location;
    }

    public LinksEntity getLinks() {
        return links;
    }

    public int getBuckets_count() {
        return buckets_count;
    }

    public int getComments_received_count() {
        return comments_received_count;
    }

    public int getFollowers_count() {
        return followers_count;
    }

    public int getFollowings_count() {
        return followings_count;
    }

    public int getLikes_count() {
        return likes_count;
    }

    public int getLikes_received_count() {
        return likes_received_count;
    }

    public int getProjects_count() {
        return projects_count;
    }

    public int getRebounds_received_count() {
        return rebounds_received_count;
    }

    public int getShots_count() {
        return shots_count;
    }

    public int getTeams_count() {
        return teams_count;
    }

    public boolean isCan_upload_shot() {
        return can_upload_shot;
    }

    public String getType() {
        return type;
    }

    public boolean isPro() {
        return pro;
    }

    public String getBuckets_url() {
        return buckets_url;
    }

    public String getFollowers_url() {
        return followers_url;
    }

    public String getFollowing_url() {
        return following_url;
    }

    public String getLikes_url() {
        return likes_url;
    }

    public String getProjects_url() {
        return projects_url;
    }

    public String getShots_url() {
        return shots_url;
    }

    public String getTeams_url() {
        return teams_url;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(username);
        dest.writeString(html_url);
        dest.writeString(avatar_url);
        dest.writeString(bio);
        dest.writeString(location);
        dest.writeInt(buckets_count);
        dest.writeInt(comments_received_count);
        dest.writeInt(followers_count);
        dest.writeInt(followings_count);
        dest.writeInt(likes_count);
        dest.writeInt(likes_received_count);
        dest.writeInt(projects_count);
        dest.writeInt(rebounds_received_count);
        dest.writeInt(shots_count);
        dest.writeInt(teams_count);
        dest.writeByte((byte) (can_upload_shot ? 1 : 0));
        dest.writeString(type);
        dest.writeByte((byte) (pro ? 1 : 0));
        dest.writeString(buckets_url);
        dest.writeString(followers_url);
        dest.writeString(following_url);
        dest.writeString(likes_url);
        dest.writeString(projects_url);
        dest.writeString(shots_url);
        dest.writeString(teams_url);
        dest.writeString(created_at);
        dest.writeString(updated_at);
    }

    public static class LinksEntity {
        private String web;
        private String twitter;

        public void setWeb(String web) {
            this.web = web;
        }

        public void setTwitter(String twitter) {
            this.twitter = twitter;
        }

        public String getWeb() {
            return web;
        }

        public String getTwitter() {
            return twitter;
        }
    }

    public String toJsonString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
