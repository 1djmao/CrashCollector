package com.idjmao.crashcollectorlib;

/**
 * Created by 1djmao on 2017/8/19.
 */

public class ThreadCollector {

    /**
     * 收集线程信息
     * @param thread
     * @return
     */
    public static String collect(Thread thread){
        StringBuilder result=new StringBuilder();
        result.append("【Thread】-------------------------------------------------------\n");
        if (thread!=null){
            result.append("id=").append(thread.getId()).append("\n");
            result.append("name=").append(thread.getName()).append("\n");
            result.append("priority=").append(thread.getPriority()).append("\n");
            if (thread.getThreadGroup()!=null){
                result.append("groupName=").append(thread.getThreadGroup().getName()).append("\n");
            }
        }
        return result.toString();
    }

}
