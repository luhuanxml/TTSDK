#TTSDK
## gradle引用方式
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

#
	dependencies {
		 compile 'com.github.luhuanxml:TTSDK:v1.0'
	}

## 冲突解决

###manifest标签下加入：
###xmlns:tools="http://schemas.android.com/tools"

###application标签下加入：
### tools:replace="android:theme,android:icon,android:name,android:allowBackup"

## 方法数超过64k解决方案参见
###[http://www.jianshu.com/p/d714cf9a9b54](http://www.jianshu.com/p/d714cf9a9b54)

## 相关工具初始化，在你的Application中
    @Override
    public void onCreate() {
        super.onCreate();
        _Toast.init(getApplicationContext());
        _Picasso.init(getApplicationContext());
        SP.init(getApplicationContext());
    }



        
