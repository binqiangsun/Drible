package com.lukou.service.utils;

import android.content.pm.ApplicationInfo;
import android.text.TextUtils;

import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import com.lukou.service.LibApplication;

/**
 * Created by Xu on 16/4/22.
 */
public class ChannelManager {

    private static final String CHANNEL_PREFIX = "lkchannel";
    private static final String CHANNEL_SPLIT = "@";

    private String channel;

    private static ChannelManager channelManager;

    private ChannelManager() {
        setup();
    }

    public static ChannelManager instance() {
        return new ChannelManager();
    }

    private void setup() {
        ApplicationInfo appinfo = LibApplication.instance().getApplicationInfo();
        String sourceDir = appinfo.sourceDir;
        String ret = "";
        ZipFile zipfile = null;
        try {
            zipfile = new ZipFile(sourceDir);
            Enumeration<?> entries = zipfile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = ((ZipEntry) entries.nextElement());
                String entryName = entry.getName();
                if (!entryName.startsWith("META-INF/"))
                    continue;
                if (entryName.startsWith("META-INF/" + CHANNEL_PREFIX)) {
                    ret = entryName;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (zipfile != null) {
                try {
                    zipfile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        String[] split = ret.split(CHANNEL_SPLIT);
        if (split != null && split.length == 2) {
            channel = split[1];
        } else {
            channel = "unknown_channel";
        }
    }

    public String getChannel() {
        if (TextUtils.isEmpty(channel)) {
            setup();
        }
        return channel;
    }

}
