package com.nnero.nnero.util;

import android.content.Context;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by NNERO on 16/1/28.
 */
public class CommonUtil {

    /**
     * 转换dip 到px
     *
     * @param context
     * @param dipValue
     * @return
     */
    public static int dipToPx(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * px转dpi
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int pxToDpi(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) ((pxValue - 0.5f) / scale);
    }

    /**
     * 得到设备屏幕的宽度
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 得到设备屏幕的高度
     */
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 计算MD5
     *
     * @param str
     * @return
     */
    public static String MD5(String str) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            byte[] array = messageDigest.digest(str.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
}
