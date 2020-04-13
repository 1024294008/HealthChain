HealthChain
==============================
本项目是健康数据管理平台的客户端app，提供给用户上传健康数据、购买健康服务、钱包管理、用户信息管理、查看健康数据以及分析健康数据的功能

### 依赖环境
Android 5.0+

### 部署步骤
1. 运行HealthChainServer服务器端
2. 运行mysql服务
3. 运行geth客户端
4. 安装healthchain.apk文件
5. 更改目标ip
   
### 目录结构描述
├─.gradle                                          // 构建信息  
├─.idea                                            // 项目配置信息  
├─app                                              // 项目核心文件  
│  ├─build                                         // 构建文件  
│  ├─libs                                          // 第三方依赖包  
│  └─src                                           // 源代码  
│      ├─androidTest                               // 测试  
│      ├─main                                      // 主文件  
│      │  ├─java                                    
│      │  │  └─cn  
│      │  │      └─edu  
│      │  │          └─seu  
│      │  │              ├─activity                // 存放activity类  
│      │  │              ├─adapter                 // 存放适配器  
│      │  │              ├─common                  // 公共模块  
│      │  │              ├─fragment                // 存放fragment类  
│      │  │              ├─http                    // http模块  
│      │  │              │  ├─HttpHandler  
│      │  │              │  ├─HttpRequest  
│      │  │              │  ├─IHttpApi  
│      │  │              │  ├─RequestAction  
│      │  │              │  └─url  
│      │  │              ├─model                   // 实体模型  
│      │  │              ├─util                    // 工具类  
│      │  │              └─views                   // 自定义的视图  
│      │  └─res                                    // 资源文件  
│      │      ├─anim                               // 动画文件  
│      │      ├─color                              // 颜色文件  
│      │      ├─drawable                           // 图片文件  
│      │      ├─drawable-nodpi  
│      │      ├─drawable-v24  
│      │      ├─layout                             // 布局文件  
│      │      ├─mipmap-anydpi-v26                  // 图片文件  
│      │      ├─mipmap-hdpi  
│      │      ├─mipmap-mdpi  
│      │      ├─mipmap-xhdpi  
│      │      ├─mipmap-xxhdpi  
│      │      ├─mipmap-xxxhdpi  
│      │      └─values                             // 存放字符串、样式、rgb、dimen等信息  
│      └─test                                      // 测试  
|  
├─gradle                                           // 构建工具  
└─healthchain.apk                                  // 项目的安装包  