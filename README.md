# CrashCollector
基于 LogUtils 的一个库，把 log 和异常写入文件，供开发者定位问题。获取的信息包括异常信息、线程信息、系统设置、ShardPreference信息。
# 使用
1. 引用
在 build.gradle 中添加
```
repositories {
			...
			maven { url 'https://jitpack.io' }
		}
```
```
dependencies {
		compile 'com.github.User:Repo:Tag'
	}
```
2. 新建 Application 子类，
