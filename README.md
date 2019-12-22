# OnlineExamSystem

一个基于ssm后台框架和完整前端的通用型用户模块

## 项目进度把控

### 第一周（11.25-12.1）

1.开会讨论项目分工等
2.完成项目计划书，软件概要设计等文档撰写
3.完成接口文档，数据库等设计

### 第二周（12.2-12.8）

1.后端完成各层构建
2.前台样式完成
3.接口对接完成

### 第三周（12.9-12.15）

1.进行项目测试并撰写测试文档
2.根据测试结果进行修改
3.攥写项目总结报告及项目展示ppt

## 前后台应用技术

>后端：maven tomcat mysql spring spring-mvc mybatis redis logback junit penggle java-mail
>前端：html css javascript lay-ui ajax

## 项目结构图

>*文件为配置文件，尽量不要改动

```
|-- /.idea/           #*idea配置
|-- /src/
|  |--/test/          #存放java测试代码
|  |-- /java/         #存放java源码
|  |-- /resources/    #存放项目配置文件
|  |-- /webapp/
|     |-- /WEB-INF/
|        |-- web.xml   #webapp配置文件
|-- /target/           #存放编译生成文件
|-- pom.xml            #maven配置信息
|-- outline.md         #项目综述
|-- README.md          #项目简介
|-- 接口配置           #接口配置文件夹
|-- 数据库配置.md      #数据库配置
|-- /assets/           #静态资源，如图片，公共css，js等
|-- /pages/            #存放网页文件，将各网页html，css，js打包存放
```

## 注意事项

1.被封号的用户在一定时间后删除(设置为7天)
2.后端数据库直接存储加密后的密码
3.日志文件存放保留30天
