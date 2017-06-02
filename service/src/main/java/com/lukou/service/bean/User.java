package com.lukou.service.bean;

import com.lukou.service.utils.GsonManager;

/**
 * @author Xu
 */
public class User {

    private long id;

    private String name;

    private String desc;

    private String token;

    private String phone;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public String getToken() {
        return token;
    }

    public String getPhone() {
        return phone;
    }

    public String getBlurPhone(){
        if(phone.length() != 11){
            return phone;
        }
        StringBuilder builder = new StringBuilder(phone);
        return builder.replace(3, 7, "****").toString();
    }

    //转成json 供磁盘持久化
    public String toJsonString() {
        return GsonManager.instance().toJson(this);
    }

}
