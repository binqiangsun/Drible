package com.sunbinqiang.drible.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sunbinqiang on 8/18/16.
 */

public class Token implements Parcelable {

    /**
     * access_token : 29ed478ab86c07f1c069b1af76088f7431396b7c4a2523d06911345da82224a0
     * token_type : bearer
     * scope : public write
     */

    private String client_id;
    private String client_secret;
    private String code;
    private String access_token;
    private String token_type;
    private String scope;

    public Token(String client_id, String client_secret, String code){
        this.client_id = client_id;
        this.client_secret = client_secret;
        this.code = code;
    }

    protected Token(Parcel in) {
        access_token = in.readString();
        token_type = in.readString();
        scope = in.readString();
    }

    public static final Creator<Token> CREATOR = new Creator<Token>() {
        @Override
        public Token createFromParcel(Parcel in) {
            return new Token(in);
        }

        @Override
        public Token[] newArray(int size) {
            return new Token[size];
        }
    };

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(access_token);
        dest.writeString(token_type);
        dest.writeString(scope);
    }
}
