Camel SFTP / DB Demo
====================

Pre-requisites
==============

* Download and install H2 - http://www.h2database.com/h2-2014-01-18.zip (version 170)

* Open a Terminal and move to the directory bin of H2 database

* Start the engine using the command

    ./h2.sh


Instructions
============

1) Run project locally

* Edit the file resources/config.properties and define your username, password to connect to the SFTP Server

username=yyyyy
password=xxxxx

Change also the location of your SFTP Send/Receive directory

sft_send_path=
sftp_receive_path=

* Create the Send/rceive directories

  mkdir ~/Temp/sftp/receive
  mkdir ~/Temp/sftp/send

* To run the project, open a unix terminal and launch this command

    mvn camel:run

REMARK : The H2 DB must be started before to launch camel:run

* To verify that responseq has been received from SFTP server, run the following select `SELECT * FROM REQUEST`
query and validate that the column response should contain a content

[Result](https://bytebucket.org/cmoulliard/demo-camel-sftp-db/raw/e7a986c9aff1141b2d4480e2f65cc1d5fa91efd1/result.png?token=b117247599f7b8fdbdff9fc71268f5f6099418cf)

2) Run on JBoss Fuse

* Download and install [JBoss Fuse 6.1](https://repository.jboss.org/nexus/content/repositories/ea/org/jboss/fuse/jboss-fuse-full/6.1.0.redhat-372/)
* Open a terminal and move to the bin directory
* Before to start the server, edit the file ../etc/users.properties and uncomment #admin=admin,admin line
* Start JBoss Fuse
    ./fuse
* Setup Fabric server when the Karaf console appears
    fabric:create
* Deploy the profile of the project by running this command after [changing your maven settings.xml file](https://github.com/fabric8io/fabric8/blob/master/docs/mavenPlugin.md#configuring-the-plugin)
    mvn fabric8:deploy
* If the build succeeds, connect to the web console to verify that the profile has been created using this [url](http://localhost:8181/hawtio/index.html#/wiki/branch/1.0/view/fabric/profiles/sftp/db/demo.profile) where you can control
that a profile using `camel, camel-ftp, camel-spring ...` has been created

https://www.dropbox.com/s/t77jljs9rmjdf77/Screenshot%202014-03-24%2014.47.56.png

DB Access
=========

* You can check that a record has been created in the H2 DB as we upload one record when the camel project is created/started

* Your browser should be opened with this address : http://192.168.1.3:8082/

    GenericH2 Server
    URL : jdbc:h2:tcp://localhost/~/Temp/TEST
    User : sa
    Password:


* Verify that a record has been created

    SELECT * FROM REQUEST




