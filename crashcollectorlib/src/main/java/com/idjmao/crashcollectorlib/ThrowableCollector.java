package com.idjmao.crashcollectorlib;

import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Created by 1djmao on 2017/8/19.
 */

public class ThrowableCollector {

    public static String collect(Throwable throwable){
        String title="【Exception】------------------------------------------------\n";
        Writer writer=new StringWriter();
        PrintWriter printWriter=new PrintWriter(writer);
        // 如果异常是在 asynctask 中的后台线程抛出的
        //可以通过 getCause 获得实际的异常
        Throwable cause=throwable;

        while (null!=cause){
            cause.printStackTrace(printWriter);
            cause=throwable.getCause();
        }
        String s=writer.toString();
        printWriter.close();
        Log.i("hhhhh", "collect: "+s);
        return title+s;
    }

}
