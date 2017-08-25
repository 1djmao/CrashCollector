package com.idjmao.crashcollectorlib;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by 1djmao on 2017/8/19.
 */

public class ShardPreCollector {

    private final Context mContext;

    private String [] mShardPreIds;

    public ShardPreCollector(Context context, String[] shardPreIds) {
        mContext = context;
        mShardPreIds = shardPreIds;
    }

    public String collect(){
        StringBuilder result=new StringBuilder();
        result.append("【SharedPreferences】-------------------------------------------------------\n");
        Map<String,SharedPreferences> preMap=new TreeMap<>();

        //默认的 sharedpreferences 信息
        preMap.put("default", PreferenceManager.getDefaultSharedPreferences(mContext));
        //自定义 sharedpreferences
        if (mShardPreIds!=null){
            for (String id:mShardPreIds) {
                preMap.put(id,mContext.getSharedPreferences(id,Context.MODE_PRIVATE));
            }
        }

        for (Map.Entry<String,SharedPreferences> entry:preMap.entrySet()){


            result.append("-----\n");
            String sharePreId=entry.getKey();
            result.append(sharePreId+"\n");
            SharedPreferences prefs=entry.getValue();

            Map<String,?> prefEntries=prefs.getAll();

            if (prefEntries.isEmpty()){
                result.append("empty\n");
                continue;
            }

            for (Map.Entry<String,?> preEntry:prefEntries.entrySet()) {
                Object value=preEntry.getValue();
                result.append(preEntry.getKey()+"=");
                result.append(value==null ? "null" : value.toString());
                result.append("\n");
            }
            result.append("\n");
        }

        return result.toString();
    }


}
