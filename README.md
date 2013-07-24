webapp
======

Upreader Web Application on Resin 4 application server that uses a custom framework developed by Flavius (somehow similar to Sprin MVC but much simpler).

Technologies used so far:
+ Servlet 3, JSP EL
+ [batooJpa 2.0.1.2](https://github.com/BatooOrg/BatooJPA) as a JPA 2.0 implementation
+ jQuery 1.10.1
+ jQuery Datatables wrapper for Java: [Dandelion Datatables 0.8.14](http://dandelion.github.io/datatables/)
+ [Jackson JSON](http://jackson.codehaus.org/) 2.2.0 for POJO <> JSON conversions
+ [Mustache](http://mustache.github.io/) as a generic templating engine
+ Log4j 1.2.16
+ [jQuery raty](http://wbotelhos.com/raty/) for star ratings
+ Commons FileUpload 1.3
+ Amazon AWS SDK 1.4.7
+ jets3t 0.9.0
+ Crocodoc Java API

Building and running the project on Windows
---------------------------------------------
1. Download and install JDK 1.7
2. Download and install [wampserver](http://www.wampserver.com/en/#download-wrapper) to get a easy-to-use MySQL database and admin console
3. Create the 'upreader' empty atabase using phpMyAdmin
2. Download and unzip [Resin 4.0.36](http://www.caucho.com/download/resin-pro-4.0.36.tar.gz)
3. Install IntelliJ IDEA 12
4. Open the project in IDEA
5. Configure Resin home and JDK
6. Add Resin libraries to project
7. Create a new Resin run configuration using the resin.xml from the resin/config project folder
8. Run Upreader
12. Goto phpMyAdmin and create insert a user in table "users" with email=admin@upreader.com, password=af2d834011da62769576f1430bf5b6e roles=admin). The id is set on auto_increment so it will be added automatically on commit.
11. Goto http://localhost:8080/upreader and login with admin/asdqwe123

Automatic building and packaging using Maven will be added later on.
