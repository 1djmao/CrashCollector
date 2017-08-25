package com.idjmao.crashcollector;

import android.app.Application;

import com.idjmao.crashcollectorlib.CrashUtils;
import com.idjmao.crashcollectorlib.ShardPreCollector;
import com.idjmao.crashcollectorlib.SysSettingCollector;


/**
 * Created by 1djmao on 2017/8/18.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        final CrashUtils crashUtils=new CrashUtils.Builder()
                .folderPath("zzzzz")
                .mSettingCollector(new SysSettingCollector(this))
                .mShardPreCollector(new ShardPreCollector(this,new String[]{"pre"}))
                .build();

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                crashUtils.getCrash(t,e);
                System.exit(0);
            }
        });
    }

}
