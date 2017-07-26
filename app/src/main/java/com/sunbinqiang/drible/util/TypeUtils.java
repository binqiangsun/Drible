package com.sunbinqiang.drible.util;

/**
 * Created by sunbinqiang on 16/2/3.
 */
public class TypeUtils {
    public static final int HEADER_TYPE = 0x10000000; //最多0x00111111个 Header DealItem
    public static final int FOOTER_TYPE = 0x01000000; //最多0x00111111个 Footer DealItem
    public static final int HEADER_FOOTER_TYPE_MASK = 0x11000000;
    public static final int ERROR_ID = -1;
    public static final int EMPTY_ID = -2;
    public static final int LOADING_ID = -3;
    public static final int ITEM_ID = 0;

    /**
     * 个人主页shot流的类型
     */
    public static final int SHOT_LIKES = 0;
    public static final int SHOT_BUCKETS = 1;
    public static final int SHOT_SHOTS = 2;

}
