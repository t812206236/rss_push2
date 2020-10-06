First of all, I want to complain about this project. It's a bit too crude. For the database, I only used the c3p0 database thread pool. I wrote the dao layer manually, and the data rollback was also written manually. The xml files of RSS are all string splicing ,even the dependent packages are not managed by maven. However, this project is really enough. You can use this project like feed43. If you have time and want to transfer the project to springboot and optimize it, I will appreciate you very much.

Talk about how to arrange this project
1. First of all you need to have a separate package of tomcat, its java operating environment and the project operating environment should be consistent, and be configured in this place

   <img src="readmePNG/image-20201006094205634.png" alt="image-20201006094205634" style="zoom:50%;" />

2. Right-click the project configuration, you need to configure the running environment of tomcat and java

   <img src="readmePNG/image-20201006094615394.png" alt="image-20201006094615394" style="zoom:50%;" />

   And project facets

   <img src="readmePNG/image-20201006094710882.png" alt="image-20201006094710882" style="zoom:50%;" />

3. Your database connection pool configuration

   <img src="readmePNG/image-20201006094811327.png" alt="image-20201006094811327" style="zoom:50%;" />

   Pay attention to the version of your mysql server, you need to import the mysql-connector-java package corresponding to the version

   <img src="readmePNG/image-20201006095225976.png" alt="image-20201006095225976" style="zoom:50%;" />

4. Import rss_push2.sql, then right-click the project and run, it should be able to run



The running diagram of the project looks like this

<img src="readmePNG/image-20201006095448938.png" alt="image-20201006095448938" style="zoom:50%;" />

<img src="readmePNG/image1.png" style="zoom:50%;" />