# 在线RSS制作第二版

[在线示例,服务器过期了](http://27.126.180.127:8080)

## 搭建方式

只依赖mysql，导入数据库，已上传rss_push2.sql
再改一下c3p0-config.xml的数据库配置为你自己的数据库配置即可

## 基本信息

* 根据尚硅谷java web教程中的bookstore项目，重新写了Rss在线制作工具，工具的使用方式和feed43是相同的，依旧秉承简单够用的宗旨去开发项目  
* Model:POJO,Controller:Servlet  ,View:Jsp+EL+JSTL  
* 数据库：MYSQL  
* 数据源：C3P0  
* JDBC：DBUtils  
* 事务解决方案：JQuery+Javascript+JSON+Google-GSON  
* 层之间解耦：工厂设计模式  


## 完成情况 since 2019-7-20 

1. 基本的数据库已经建立 √
2. 使用filter开启页面游览事务保护，出问题时回滚 √
3. 基本的游览，分页，新建，删除 √
4. feed在线编辑 √
5. 合并工具rss_generator到工程中来，方便维护 √
6. 后台自动更新任务以及添加删除等 √
7. rss更新逻辑：保存feed每一条entry，自动生成guid，scratchDate，可帮助订阅器更好订阅 √
8. 检查数据库插入查询功能中转义的问题
9. 权限管理，纠结到地是写一个简单的还是引入一个框架
10. 历史rss检索，关键字索引就够了
11. 一键生成opml
12. xpath 生成Rss的方式 (原生xpath转换速度太慢，htmlcleaner xpath方法解析不出，htmlcleaner+原生xpath偶尔有些网站解析不出),jsoup比较厉害，不过xpath规则不通用
13. 解决有些网站无法获取的问题（超时问题比较多），可能再次尝试重连会比较好
14. 支持动态网页的抓取，无头游览器
15. xml链接重写 √
16. 登录持久化

## 数据库按时间倒序查询

SELECT * FROM rss.rssItems order by str_to_date(scratchDate,'%W, %d %b %Y %T') desc;


