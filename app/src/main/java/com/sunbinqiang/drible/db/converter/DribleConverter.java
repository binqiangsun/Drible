/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sunbinqiang.drible.db.converter;


import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.sunbinqiang.drible.bean.ImageEntity;
import com.sunbinqiang.drible.bean.User;

import java.util.Date;

public class DribleConverter {
    @TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static User toUser(String userJson) {
        return new Gson().fromJson(userJson, User.class);
    }

    @TypeConverter
    public static String toUserJson(User user) {
        return user.toJsonString();
    }

    @TypeConverter
    public static ImageEntity toImageEntity(String imageJson) {
        return new Gson().fromJson(imageJson, ImageEntity.class);
    }
    
    @TypeConverter
    public static String toImageJson(ImageEntity imageEntity) {
        return imageEntity.toJsonString();
    }


}
