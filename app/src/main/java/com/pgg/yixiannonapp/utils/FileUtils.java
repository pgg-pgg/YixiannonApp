package com.pgg.yixiannonapp.utils;

import android.util.Log;

import com.pgg.yixiannonapp.global.GlobalApplication;

import java.io.File;



/**
 * Created by PDD on 2018/3/20.
 */

public class FileUtils {

    public static File getChaheDirectory(){
        File file=new File(GlobalApplication.getInstance().getApplicationContext().getExternalCacheDir(),"MyCache");
        if (!file.exists()){
            boolean b=file.mkdirs();
            Log.e("file", "文件不存在  创建文件    "+b);
        }else {
            Log.e("file", "文件存在");
        }
        return file;
    }
}
