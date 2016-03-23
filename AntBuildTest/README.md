# Android自动化构建之Ant多渠道打包实践（上）

# 前言

Ant是历史比较悠久的一个自动化构建工具，Android开发者可以通过它来实现自动化构建，也可以实现多渠道打包，关于apk打包的方式一般有Ant、Python、Gradle三种，这三种打包方式都各自有优点和缺点，本篇博文先给大家介绍如何使用Ant来实现自动构建和多渠道发布。

# 开发环境
- Window7
- Ant
- jdk
- android sdk

mac系统下所需要的运行环境也是类似的，我们都需要配置Ant、jdk、sdk的环境变量，我们可以看一下window下是环境变量配了些什么：
**ANT_HOME** : D:\android\apache-ant-1.9.4
**JAVA_HOME** : C:\Program Files\Java\jdk1.6.0_45
**ANDROID_SDK_HOME** : D:\android\adt-bundle-windows-x86_64-20140321\sdk
**PATH**: %JAVA_HOME%/bin;%ANDROID_SDK_HOME%\platform-tools;%ANDROID_SDK_HOME%\tools;%ANT_HOME%\bin;
**CLASSPATH** : .;%JAVA_HOME%\lib\dt.jar;%JAVA_HOME%\tools.jar

以上环境变量配好之后，你才可以进入下一步，不会配？回家吧，开发不适合你。


# 先说APK构建过程

下面来简单描述下apk构建的过程：
1.	使用aapt生成R.java类文件
2.	使用android SDK提供的aidl.exe把.aidl转成Java文件
3.	使用javac命令编译.java类文件生成class文件
4.	使用android SDK提供的dx.bat命令行脚本生成classes.dex文件
5.	使用android SDK提供的aapt.exe生成资源包文件
6.	使用apkBuilder.bat生成未签名的apk安装文件
7.	使用jdk的jarsigner对未签名的包进行apk签名
8.	使用Zipalign工具对apk进行优化

我们从上面的描述可以知道apk具体的步骤和使用到的工具，对应的工具在sdk中都可以找到，自己去翻翻吧，但你会发现新的sdk版本，aapt被放到了**build-tools**目录下，**apkBuilder.bat**文件在tools目录找不到了，你可以去网上去下一个，然后放到tools目录下。为了让大家更清楚apk构建的过程，放上官方的一张图：

![apk构建过程](http://upload-images.jianshu.io/upload_images/224008-7cc7ab08c8841ee5?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

有了这张图，相信大家已经清楚了apk到底是如何生成的吧，不多说了。

# 构建命令详解
-  aapt命令生成R.java文件

示例命令：
```
aapt package -m -J <R.java文件夹> -S <res路径> -I <android.jar路径> -M<AndroidManifest.xml路径>
```

命令解释：

-f    如果编译出来的文件已经存在，强制覆盖
-m  使生成的包的目录存放在-J参数指定的目录
-J    指定生成的R.java 的输出目录 
-S    res文件夹路径
-A    assert文件夹路径
-I     某个版本平台的android.jar的路径
-F    具体指定APK文件的输出


- aidl命令生成.aidl文件

示例命令：
```
aidl -p<framework.aidl路径> -I<src路径> -o<目标文件目录> .aidl文件
```
注意：**命令和路径是没有空格的**。


- javac命令生成.class文件

示例命令：
```
javac -d destdir srcFile
```
参数解释：
-d 指定存放类的文件夹
-bootclasspath  覆盖引导类文件的位置
-encoding 指定源文件使用的编码
-sourcepath 指定查找输入源文件位置

- dx命令生成classes.dex文件
示例命令：
```
dx --dex --output classes.dex  bin/classes/  libs/
```
命令解释：将bin/classes下的class文件和libs下的jar文件编译成classes.dex文件


- aapt生成资源包文件resources.ap_

命令示例：
```
aapt package -m -J <R.java文件夹> -S <res路径> -I <android.jar路径> -A <asset路径> -M<AndroidManifest.xml路径> -F <resources.ap_文件路径>
```

- apkbuilder.bat已经过时了，使用以下方法

示例命令：
```
java -cp <sdklib.jar路径> com.android.sdklib.build.ApkBuilderMain <未签名.apk>  -v -u -z bin\resources.ap_ -f bin\classes.dex -rf src
```


- 通过jarsigner来生成
示例命令：
```
jarsigner -verbose -keystore <keystore路径> -signedjar -<输出签名apk路径> <未签名apk路径> <keystore别名>
```

-  最后一步使用zipalign工具进行apk对齐优化

示例命令：
```
zipalign [-f] [-v] <alignment> infile.apk outfile.apk
```

上面的8个步骤就是实现apk构建的过程，都是通过命令来一步一步实现，要注意每一步生成的东西。


# 小结
本篇博文主要给大家介绍了Android中apk构建的过程，也详细的讲解了每一步具体的命令操作，由于不想一篇把所有东西堆在一起，我将会在下一篇来具体使用Ant脚本实现自动化构建和多渠道打包，大家可以继续关注。


----------
欢迎关注我的公众号：wwjblog

![ wwjblog](http://upload-images.jianshu.io/upload_images/224008-492c5e11865b08bd?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


