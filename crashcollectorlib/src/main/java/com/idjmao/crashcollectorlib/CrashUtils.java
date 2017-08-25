package com.idjmao.crashcollectorlib;

import android.os.Environment;

import com.apkfuns.log2file.LogFileEngineFactory;
import com.apkfuns.logutils.LogUtils;

/**
 * Created by 1djmao on 2017/8/19.
 */

public class CrashUtils {

    private ShardPreCollector mShardPreCollector;
    private SysSettingCollector mSettingCollector;
    private String folderPath;

    private CrashUtils(Builder builder) {
        mShardPreCollector = builder.mShardPreCollector;
        mSettingCollector = builder.mSettingCollector;
        folderPath = builder.folderPath;

        LogUtils.getLog2FileConfig().configLog2FileEnable(true)
                .configLog2FilePath(Environment.getExternalStorageDirectory().getAbsolutePath()
                        +"/"+folderPath+"/")
                .configLog2FileNameFormat("%d{yyyyMMdd}.txt")
                .configLogFileEngine(new LogFileEngineFactory());

    }

    public void getCrash(Thread thread,Throwable throwable){

        LogUtils.e(throwable);
        LogUtils.e(thread);

        StringBuilder resulet=new StringBuilder();
        if (mSettingCollector!=null){
            resulet.append(mSettingCollector.collect());
        }
        if (mShardPreCollector!=null){
            resulet.append(mShardPreCollector.collect());
        }
        LogUtils.i(resulet);
    }


    public static final class Builder {
        private ShardPreCollector mShardPreCollector;
        private SysSettingCollector mSettingCollector;
        private String folderPath;

        public Builder() {
        }

        public Builder mShardPreCollector(ShardPreCollector val) {
            mShardPreCollector = val;
            return this;
        }

        public Builder mSettingCollector(SysSettingCollector val) {
            mSettingCollector = val;
            return this;
        }

        public Builder folderPath(String val) {
            folderPath = val;
            return this;
        }

        public CrashUtils build() {
            return new CrashUtils(this);
        }
    }
}
