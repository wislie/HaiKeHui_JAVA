package com.haier.util;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

/**
 * author : Wislie
 * e-mail : 254457234@qq.comn
 * date   : 2020/8/25 7:10 PM
 * desc   :
 * version: 1.0
 */
public class FileUtils {

    /**
     * 根据uri获取 真实的文件路径
     * @param context
     * @param contentURI
     * @return
     */
    public static String getRealPathFromURI(Context context, Uri contentURI) {
        String result;
        Cursor cursor = context.getContentResolver().query(contentURI, null, null, null, null);
        //不能直接调用contentprovider的接口函数，需要使用contentresolver对象，通过URI间接调用contentprovider
        if (cursor == null) {
            // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }
}