# CrashCollector
基于 LogUtils 的一个库，把 log 和异常写入文件，供开发者定位问题。获取的信息包括异常信息、线程信息、系统设置、ShardPreference信息。
# 使用
1. 引用
在 build.gradle 中添加
```
repositories {
			maven { url 'https://jitpack.io' }
		}
```
```
dependencies {
		compile 'com.github.1djmao:CrashCollector:1.0.1'
	}
```
2. 新建 Application 子类，在其中捕获未被 try catch 捕获的异常，示例:
```
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        final CrashUtils crashUtils=new CrashUtils.Builder()
                .folderPath("zzh")
                .mSettingCollector(new SysSettingCollector(this))
                .mShardPreCollector(new ShardPreCollector(this,new String[]{"pre"}))
                .build();

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                if (e instanceof SecurityException){
                    LogUtils.i(e);
                    return;
                }
                crashUtils.getCrash(t,e);
                System.exit(0);
            }
        });
    }

}
```
CrashUtils 类共有三个参数：
* folderPath 设置 log 文件夹存储路径，不设置或为 null 时只在 catlog 中打印而不会写入文件
* SysSettingCollector 获取系统设置信息，不设置或为 null 时，将不会打印
* ShardPreCollector 获取 SharedPreferences 信息，构造方法中第二个参数时所有要打印的 SharedPreferences 的 id

3. 在工程中打印 log 使用 LogUtils ，具体使用方法见 [LogUtils](https://github.com/pengwei1024/LogUtils)。
# 注意
1. 自定义的 Application 类要在 AndroidManifest.xml 中注册
2. 当设置了 folderPath 时，请确保，应用具有文件写入权限
