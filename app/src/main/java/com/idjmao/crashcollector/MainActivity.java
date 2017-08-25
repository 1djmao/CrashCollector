package com.idjmao.crashcollector;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.apkfuns.logutils.LogUtils;
import com.idjmao.crashcollectorlib.SysSettingCollector;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.nullpoint).setOnClickListener(this);
        findViewById(R.id.syssetting).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.nullpoint:

                Integer[] integers=new Integer[2];
                integers[0].toString();
                try {
                } catch (Exception e){
                    LogUtils.i(e);
                }
                break;
            case R.id.syssetting:

                SysSettingCollector collector=new SysSettingCollector(this);
                LogUtils.v(collector.collect());

                break;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("jagdjah","afjagj");
        editor.putLong("long",1200);
        editor.commit();

        SharedPreferences pre=getSharedPreferences("pre",MODE_PRIVATE);
        SharedPreferences.Editor editor1=pre.edit();
        editor1.putLong("long",4500);
        editor1.putBoolean("bool",true);
        editor1.commit();


    }
}
