### 依赖说明
---
本项目使用maven构建.若没有安装,请先到[官网][mvnurl]下载.

### maven安装,环境变量设置
---
1. Windows下面的安装

    下载[最新版][mvnurl],解压到你想保存的目录.  

2. 设置环境变量:  
	1. maven_home=maven解压的目录  
	2. 将%maven_home%\bin加入path环境变量中  

3. 测试maven是否成功

        mvn -v 

### 导入myeclipse注意事项
---
1. 在命令行中,进入项目根目录,运行
  
        mvn eclipse:eclipse  

2. 在myeclipse中file->import->existing maven project,选择项目目录.  

[mvnurl]: http://maven.apache.org/download.cgi