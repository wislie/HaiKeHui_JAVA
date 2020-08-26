package com.haier.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

/**
 * author : Wislie
 * e-mail : 254457234@qq.comn
 * date   : 2020/8/25 10:23 AM
 * desc   : 获取应用信息(比如 AndroidManifest.xml 中的 meta-data 配置信息
 * version: 1.0
 */
public class ApplicationInfoUtil {

    /**
     * 读取meta-data的值
     *
     * @param context
     * @param name
     * @return
     */
    public static String readMetaDataIntValue(Context context, String name) {
        try {
            ApplicationInfo appInfo = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            if (appInfo != null) {
                return String.valueOf(appInfo.metaData.getInt(name));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 读取meta-data的值
     *
     * @param context
     * @param name
     * @return
     */
    public static String readMetaDataStringValue(Context context, String name) {
        try {
            ApplicationInfo appInfo = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            if (appInfo != null) {
                return appInfo.metaData.getString(name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
