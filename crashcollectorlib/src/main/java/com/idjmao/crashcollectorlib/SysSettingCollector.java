package com.idjmao.crashcollectorlib;

import android.content.ContentResolver;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by 1djmao on 2017/8/21.
 */

public class SysSettingCollector {
    Context mContext;

    public SysSettingCollector(Context context) {
        mContext = context;
    }

    public String collect() {
        StringBuilder reslut=new StringBuilder();
        reslut.append("【系统设置状态】--------------------------------------------------------\n");
        reslut.append(collectSystenmSetting());
        reslut.append(collectSecureSetting());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            reslut.append(collectGlobalSetting());
        }

        return reslut.toString();

    }

    /**
     * 全局设置（对所有用户）
     *
     * @return
     */
    private String collectGlobalSetting() {
        StringBuilder result = new StringBuilder();
        result.append("------------全局设置-------------\n");
        try {
            Class<?> globalClass = Class.forName("android.provider.Settings$Global");
            Field[] keys = Settings.System.class.getFields();
            Method getString = globalClass.getMethod("getString", ContentResolver.class, String.class);
            for (Field key : keys) {
                if (key.isAnnotationPresent(Deprecated.class) && key.getType() == String.class) {
                    Object value = Settings.System.getString(mContext.getContentResolver(),
                            (String) key.get(null));
                    if (value != null) {
                        result.append(key.getName() + " = " + value + "\n");
                    }
                }
            }


        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }


        return result.toString();
    }

    /**
     * 系统安全设置
     *
     * @return
     */
    private String collectSecureSetting() {
        StringBuilder result = new StringBuilder();
        result.append("------------系统安全设置-------------\n");
        Field[] keys = Settings.Secure.class.getFields();

        for (Field key : keys) {
            if (key.isAnnotationPresent(Deprecated.class) && key.getType() == String.class && isAuthorized(key)) {
                try {
                    Object value = Settings.Secure.getString(mContext.getContentResolver(),
                            (String) key.get(null));
                    if (value != null) {
                        result.append(key.getName() + " = " + value + "\n");
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return result.toString();
    }

    /**
     * 常规偏好设置
     *
     * @return
     */
    private String collectSystenmSetting() {
        StringBuilder result = new StringBuilder();
        result.append("------------常规设置-------------\n");
        Field[] keys = Settings.System.class.getFields();

        for (Field key : keys) {
            if (key.isAnnotationPresent(Deprecated.class) && key.getType() == String.class) {
                try {
                    Object value = Settings.System.getString(mContext.getContentResolver(),
                            (String) key.get(null));
                    if (value != null) {
                        result.append(key.getName() + " = " + value + "\n");
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return result.toString();
    }

    private boolean isAuthorized(@Nullable Field key) {
        if (key == null || key.getName().startsWith("WIFI_AP")) {
            return false;
        }
        return true;
    }

}
