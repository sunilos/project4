/*
SQLyog Ultimate v9.02 
MySQL - 5.0.24-community-nt : Database - demo_ors
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`demo_ors` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `demo_ors`;

/*Table structure for table `st_college` */

DROP TABLE IF EXISTS `st_college`;

CREATE TABLE `st_college` (
  `ID` bigint(20) NOT NULL,
  `NAME` varchar(255) default NULL,
  `ADDRESS` varchar(255) default NULL,
  `STATE` varchar(255) default NULL,
  `CITY` varchar(255) default NULL,
  `PHONE_NO` varchar(255) default NULL,
  `CREATED_BY` varchar(255) default NULL,
  `MODIFIED_BY` varchar(255) default NULL,
  `CREATED_DATETIME` datetime default NULL,
  `MODIFIED_DATETIME` datetime default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `st_college` */

insert  into `st_college`(`ID`,`NAME`,`ADDRESS`,`STATE`,`CITY`,`PHONE_NO`,`CREATED_BY`,`MODIFIED_BY`,`CREATED_DATETIME`,`MODIFIED_DATETIME`) values (2,'Truba','Indore','Indore','Indore','999999','Rahul Sahu','Rahul Sahu','2014-07-19 17:42:17','2014-07-19 17:42:17');
insert  into `st_college`(`ID`,`NAME`,`ADDRESS`,`STATE`,`CITY`,`PHONE_NO`,`CREATED_BY`,`MODIFIED_BY`,`CREATED_DATETIME`,`MODIFIED_DATETIME`) values (3,'SVIM','Gumasta Nagar','MP','Indore','9999999999','Rahul Sahu','Rahul Sahu','2014-07-19 17:42:17','2014-07-19 17:42:17');
insert  into `st_college`(`ID`,`NAME`,`ADDRESS`,`STATE`,`CITY`,`PHONE_NO`,`CREATED_BY`,`MODIFIED_BY`,`CREATED_DATETIME`,`MODIFIED_DATETIME`) values (4,'J.I.Y.Indore','Rau','Madhya Pradesh','khargone','1234567890',NULL,NULL,NULL,NULL);
insert  into `st_college`(`ID`,`NAME`,`ADDRESS`,`STATE`,`CITY`,`PHONE_NO`,`CREATED_BY`,`MODIFIED_BY`,`CREATED_DATETIME`,`MODIFIED_DATETIME`) values (5,'J.I.U.Institute','Indore Road Rau','Gujrat','Bhopal','9165254357',NULL,NULL,NULL,NULL);
insert  into `st_college`(`ID`,`NAME`,`ADDRESS`,`STATE`,`CITY`,`PHONE_NO`,`CREATED_BY`,`MODIFIED_BY`,`CREATED_DATETIME`,`MODIFIED_DATETIME`) values (6,'M.I.T.Ujjain','Ujjain Road','Madhya Pradesh','Indore','1234567890',NULL,NULL,NULL,NULL);
insert  into `st_college`(`ID`,`NAME`,`ADDRESS`,`STATE`,`CITY`,`PHONE_NO`,`CREATED_BY`,`MODIFIED_BY`,`CREATED_DATETIME`,`MODIFIED_DATETIME`) values (7,'Oriyantel Tech','Mumbai road','Madhya Predash','Ahemdabad','985658568',NULL,NULL,NULL,NULL);
insert  into `st_college`(`ID`,`NAME`,`ADDRESS`,`STATE`,`CITY`,`PHONE_NO`,`CREATED_BY`,`MODIFIED_BY`,`CREATED_DATETIME`,`MODIFIED_DATETIME`) values (8,'M.D.Mansore','Nimach Road','Mansore','Mansore','7896321450',NULL,NULL,NULL,NULL);
insert  into `st_college`(`ID`,`NAME`,`ADDRESS`,`STATE`,`CITY`,`PHONE_NO`,`CREATED_BY`,`MODIFIED_BY`,`CREATED_DATETIME`,`MODIFIED_DATETIME`) values (9,'M.Y.Univercity','khargone','Madhya Pradesh','khargone','1234567890',NULL,NULL,NULL,NULL);

/*Table structure for table `st_marksheet` */

DROP TABLE IF EXISTS `st_marksheet`;

CREATE TABLE `st_marksheet` (
  `ID` bigint(20) NOT NULL,
  `ROLL_NO` varchar(255) default NULL,
  `STUDENT_ID` bigint(20) default NULL,
  `NAME` varchar(255) default NULL,
  `PHYSICS` int(11) default NULL,
  `CHEMISTRY` int(11) default NULL,
  `MATHS` int(11) default NULL,
  `CREATED_BY` varchar(255) default NULL,
  `MODIFIED_BY` varchar(255) default NULL,
  `CREATED_DATETIME` datetime default NULL,
  `MODIFIED_DATETIME` datetime default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `st_marksheet` */

insert  into `st_marksheet`(`ID`,`ROLL_NO`,`STUDENT_ID`,`NAME`,`PHYSICS`,`CHEMISTRY`,`MATHS`,`CREATED_BY`,`MODIFIED_BY`,`CREATED_DATETIME`,`MODIFIED_DATETIME`) values (1,'1',2,'Manish Sharma',99,99,99,'Rahul Sahu','rahul.sahu@nenosystems.com','2014-07-19 17:45:57','2014-07-21 19:14:20');
insert  into `st_marksheet`(`ID`,`ROLL_NO`,`STUDENT_ID`,`NAME`,`PHYSICS`,`CHEMISTRY`,`MATHS`,`CREATED_BY`,`MODIFIED_BY`,`CREATED_DATETIME`,`MODIFIED_DATETIME`) values (2,'2',2,'Manish Sharma',55,55,55,NULL,NULL,NULL,NULL);
insert  into `st_marksheet`(`ID`,`ROLL_NO`,`STUDENT_ID`,`NAME`,`PHYSICS`,`CHEMISTRY`,`MATHS`,`CREATED_BY`,`MODIFIED_BY`,`CREATED_DATETIME`,`MODIFIED_DATETIME`) values (3,'3',2,'Manish Sharma',77,77,77,NULL,NULL,NULL,NULL);
insert  into `st_marksheet`(`ID`,`ROLL_NO`,`STUDENT_ID`,`NAME`,`PHYSICS`,`CHEMISTRY`,`MATHS`,`CREATED_BY`,`MODIFIED_BY`,`CREATED_DATETIME`,`MODIFIED_DATETIME`) values (4,'4',1,'Rahul Sahu',88,88,85,NULL,NULL,NULL,NULL);
insert  into `st_marksheet`(`ID`,`ROLL_NO`,`STUDENT_ID`,`NAME`,`PHYSICS`,`CHEMISTRY`,`MATHS`,`CREATED_BY`,`MODIFIED_BY`,`CREATED_DATETIME`,`MODIFIED_DATETIME`) values (5,'5',5,'Sivam Kumar',74,74,78,NULL,NULL,NULL,NULL);
insert  into `st_marksheet`(`ID`,`ROLL_NO`,`STUDENT_ID`,`NAME`,`PHYSICS`,`CHEMISTRY`,`MATHS`,`CREATED_BY`,`MODIFIED_BY`,`CREATED_DATETIME`,`MODIFIED_DATETIME`) values (6,'6',6,'Gurpreet Singh',55,55,55,NULL,NULL,NULL,NULL);
insert  into `st_marksheet`(`ID`,`ROLL_NO`,`STUDENT_ID`,`NAME`,`PHYSICS`,`CHEMISTRY`,`MATHS`,`CREATED_BY`,`MODIFIED_BY`,`CREATED_DATETIME`,`MODIFIED_DATETIME`) values (7,'7',5,'Sivam Kumar',82,82,89,NULL,NULL,NULL,NULL);
insert  into `st_marksheet`(`ID`,`ROLL_NO`,`STUDENT_ID`,`NAME`,`PHYSICS`,`CHEMISTRY`,`MATHS`,`CREATED_BY`,`MODIFIED_BY`,`CREATED_DATETIME`,`MODIFIED_DATETIME`) values (8,'9',6,'Gurpreet Singh',88,99,78,NULL,NULL,NULL,NULL);
insert  into `st_marksheet`(`ID`,`ROLL_NO`,`STUDENT_ID`,`NAME`,`PHYSICS`,`CHEMISTRY`,`MATHS`,`CREATED_BY`,`MODIFIED_BY`,`CREATED_DATETIME`,`MODIFIED_DATETIME`) values (9,'10',7,'Viju kumar chandore',77,77,77,NULL,NULL,NULL,NULL);
insert  into `st_marksheet`(`ID`,`ROLL_NO`,`STUDENT_ID`,`NAME`,`PHYSICS`,`CHEMISTRY`,`MATHS`,`CREATED_BY`,`MODIFIED_BY`,`CREATED_DATETIME`,`MODIFIED_DATETIME`) values (10,'11',3,'Vipin Chandore',88,99,79,NULL,NULL,NULL,NULL);
insert  into `st_marksheet`(`ID`,`ROLL_NO`,`STUDENT_ID`,`NAME`,`PHYSICS`,`CHEMISTRY`,`MATHS`,`CREATED_BY`,`MODIFIED_BY`,`CREATED_DATETIME`,`MODIFIED_DATETIME`) values (11,'12',7,'Viju kumar chandore',88,88,88,NULL,NULL,NULL,NULL);
insert  into `st_marksheet`(`ID`,`ROLL_NO`,`STUDENT_ID`,`NAME`,`PHYSICS`,`CHEMISTRY`,`MATHS`,`CREATED_BY`,`MODIFIED_BY`,`CREATED_DATETIME`,`MODIFIED_DATETIME`) values (12,'13',1,'Rahul Sahu',85,88,99,NULL,NULL,NULL,NULL);

/*Table structure for table `st_role` */

DROP TABLE IF EXISTS `st_role`;

CREATE TABLE `st_role` (
  `ID` bigint(20) NOT NULL,
  `NAME` varchar(255) default NULL,
  `DESCRIPTION` varchar(255) default NULL,
  `CREATED_BY` varchar(255) default NULL,
  `MODIFIED_BY` varchar(255) default NULL,
  `CREATED_DATETIME` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `MODIFIED_DATETIME` timestamp NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `st_role` */

insert  into `st_role`(`ID`,`NAME`,`DESCRIPTION`,`CREATED_BY`,`MODIFIED_BY`,`CREATED_DATETIME`,`MODIFIED_DATETIME`) values (1,'Admin','Administrator Role','Admin','Admin','2014-07-19 17:13:36','2014-07-19 17:13:36');
insert  into `st_role`(`ID`,`NAME`,`DESCRIPTION`,`CREATED_BY`,`MODIFIED_BY`,`CREATED_DATETIME`,`MODIFIED_DATETIME`) values (2,'Student','Student Role','Rahul Sahu','Rahul Sahu','2014-07-19 17:19:09','2014-07-19 17:19:09');
insert  into `st_role`(`ID`,`NAME`,`DESCRIPTION`,`CREATED_BY`,`MODIFIED_BY`,`CREATED_DATETIME`,`MODIFIED_DATETIME`) values (3,'College','College Role','Rahul Sahu','Rahul Sahu','2014-07-19 17:19:30','2014-07-19 17:19:30');
insert  into `st_role`(`ID`,`NAME`,`DESCRIPTION`,`CREATED_BY`,`MODIFIED_BY`,`CREATED_DATETIME`,`MODIFIED_DATETIME`) values (4,'KIOSK','KIOSK Role','Rahul Sahu','Rahul Sahu','2014-07-19 17:19:48','2014-07-19 17:19:48');
insert  into `st_role`(`ID`,`NAME`,`DESCRIPTION`,`CREATED_BY`,`MODIFIED_BY`,`CREATED_DATETIME`,`MODIFIED_DATETIME`) values (5,'Test','Test Role','rahul.sahu@nenosystems.com','rahul.sahu@nenosystems.com','2014-07-21 19:34:23','2014-07-21 19:34:23');
insert  into `st_role`(`ID`,`NAME`,`DESCRIPTION`,`CREATED_BY`,`MODIFIED_BY`,`CREATED_DATETIME`,`MODIFIED_DATETIME`) values (6,'Vipin','dsdsfsfsfsf',NULL,NULL,'2014-07-22 11:42:46','2014-07-22 11:42:46');
insert  into `st_role`(`ID`,`NAME`,`DESCRIPTION`,`CREATED_BY`,`MODIFIED_BY`,`CREATED_DATETIME`,`MODIFIED_DATETIME`) values (7,'dsgdfgdgdg','jhf sfskffdssjf dsfsdfjsh',NULL,NULL,'2014-07-22 11:50:51','2014-07-22 11:50:51');
insert  into `st_role`(`ID`,`NAME`,`DESCRIPTION`,`CREATED_BY`,`MODIFIED_BY`,`CREATED_DATETIME`,`MODIFIED_DATETIME`) values (8,'Adminfd','dsfsfsf',NULL,NULL,'2014-07-22 11:52:11','2014-07-22 11:52:11');
insert  into `st_role`(`ID`,`NAME`,`DESCRIPTION`,`CREATED_BY`,`MODIFIED_BY`,`CREATED_DATETIME`,`MODIFIED_DATETIME`) values (9,'sdhdftdfghdgh','test',NULL,NULL,'2014-07-22 11:58:18','2014-07-22 11:58:18');
insert  into `st_role`(`ID`,`NAME`,`DESCRIPTION`,`CREATED_BY`,`MODIFIED_BY`,`CREATED_DATETIME`,`MODIFIED_DATETIME`) values (10,'fghbfgh','fghfghfh',NULL,NULL,'2014-07-22 12:07:56','2014-07-22 12:07:56');

/*Table structure for table `st_user` */

DROP TABLE IF EXISTS `st_user`;

CREATE TABLE `st_user` (
  `ID` bigint(20) NOT NULL,
  `FIRST_NAME` varchar(255) default NULL,
  `LAST_NAME` varchar(255) default NULL,
  `LOGIN` varchar(255) default NULL,
  `PASSWORD` varchar(255) default NULL,
  `DOB` datetime default NULL,
  `MOBILE_NO` varchar(255) default NULL,
  `ROLE_ID` bigint(20) default NULL,
  `UNSUCCESSFUL_LOGIN` int(11) default NULL,
  `GENDER` varchar(255) default NULL,
  `LAST_LOGIN` datetime default NULL,
  `USER_LOCK` varchar(255) default NULL,
  `REGISTERED_IP` varchar(255) default NULL,
  `LAST_LOGIN_IP` varchar(255) default NULL,
  `CREATED_BY` varchar(255) default NULL,
  `MODIFIED_BY` varchar(255) default NULL,
  `CREATED_DATETIME` datetime default NULL,
  `MODIFIED_DATETIME` datetime default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `st_user` */

insert  into `st_user`(`ID`,`FIRST_NAME`,`LAST_NAME`,`LOGIN`,`PASSWORD`,`DOB`,`MOBILE_NO`,`ROLE_ID`,`UNSUCCESSFUL_LOGIN`,`GENDER`,`LAST_LOGIN`,`USER_LOCK`,`REGISTERED_IP`,`LAST_LOGIN_IP`,`CREATED_BY`,`MODIFIED_BY`,`CREATED_DATETIME`,`MODIFIED_DATETIME`) values (1,'Rahul','Sahu','rahul.sahu@nenosystems.com','rr','2014-07-18 00:00:00','8120871155',1,0,'M','2014-07-21 19:34:07','Inactive','0:0:0:0:0:0:0:1','192.168.1.5','Rahul Sahu','Rahul Sahu','2014-07-19 17:39:20','2014-07-19 18:29:23');
insert  into `st_user`(`ID`,`FIRST_NAME`,`LAST_NAME`,`LOGIN`,`PASSWORD`,`DOB`,`MOBILE_NO`,`ROLE_ID`,`UNSUCCESSFUL_LOGIN`,`GENDER`,`LAST_LOGIN`,`USER_LOCK`,`REGISTERED_IP`,`LAST_LOGIN_IP`,`CREATED_BY`,`MODIFIED_BY`,`CREATED_DATETIME`,`MODIFIED_DATETIME`) values (2,'Rakesh','Sen','vipinchandore@gmail.com','rr','2014-07-21 00:00:00','1234567890',2,0,'M','2014-07-19 20:18:49','Inactive','0:0:0:0:0:0:0:1','192.168.1.5','Rahul Sahu','Rahul Sahu','2014-07-19 17:39:20','2014-07-19 20:19:00');
insert  into `st_user`(`ID`,`FIRST_NAME`,`LAST_NAME`,`LOGIN`,`PASSWORD`,`DOB`,`MOBILE_NO`,`ROLE_ID`,`UNSUCCESSFUL_LOGIN`,`GENDER`,`LAST_LOGIN`,`USER_LOCK`,`REGISTERED_IP`,`LAST_LOGIN_IP`,`CREATED_BY`,`MODIFIED_BY`,`CREATED_DATETIME`,`MODIFIED_DATETIME`) values (3,'ramlakhan','chandore','vipinchandorerrr@gmail.com','123','1991-12-05 00:00:00',NULL,2,0,'M',NULL,'Inactive',NULL,NULL,NULL,NULL,NULL,NULL);
insert  into `st_user`(`ID`,`FIRST_NAME`,`LAST_NAME`,`LOGIN`,`PASSWORD`,`DOB`,`MOBILE_NO`,`ROLE_ID`,`UNSUCCESSFUL_LOGIN`,`GENDER`,`LAST_LOGIN`,`USER_LOCK`,`REGISTERED_IP`,`LAST_LOGIN_IP`,`CREATED_BY`,`MODIFIED_BY`,`CREATED_DATETIME`,`MODIFIED_DATETIME`) values (4,'vijay','chouhan','vijay@gmail.com','123','1991-10-05 00:00:00',NULL,1,0,'M',NULL,'Inactive',NULL,NULL,NULL,NULL,NULL,NULL);
insert  into `st_user`(`ID`,`FIRST_NAME`,`LAST_NAME`,`LOGIN`,`PASSWORD`,`DOB`,`MOBILE_NO`,`ROLE_ID`,`UNSUCCESSFUL_LOGIN`,`GENDER`,`LAST_LOGIN`,`USER_LOCK`,`REGISTERED_IP`,`LAST_LOGIN_IP`,`CREATED_BY`,`MODIFIED_BY`,`CREATED_DATETIME`,`MODIFIED_DATETIME`) values (5,'Viju kumar','Soni ji','Viju@gmail.com','123','2006-04-05 00:00:00','9165254357',2,0,'M',NULL,'Inactive',NULL,NULL,NULL,NULL,NULL,NULL);
insert  into `st_user`(`ID`,`FIRST_NAME`,`LAST_NAME`,`LOGIN`,`PASSWORD`,`DOB`,`MOBILE_NO`,`ROLE_ID`,`UNSUCCESSFUL_LOGIN`,`GENDER`,`LAST_LOGIN`,`USER_LOCK`,`REGISTERED_IP`,`LAST_LOGIN_IP`,`CREATED_BY`,`MODIFIED_BY`,`CREATED_DATETIME`,`MODIFIED_DATETIME`) values (6,'vipin','chandore','vipinchandorert@gmail.com','123','1991-10-05 00:00:00',NULL,3,0,'F',NULL,'Inactive',NULL,NULL,NULL,NULL,NULL,NULL);

/*Table structure for table `student` */

DROP TABLE IF EXISTS `student`;

CREATE TABLE `student` (
  `ID` bigint(20) NOT NULL,
  `COLLEGE_ID` bigint(20) default NULL,
  `COLLEGE_NAME` varchar(255) default NULL,
  `FIRST_NAME` varchar(255) default NULL,
  `LAST_NAME` varchar(255) default NULL,
  `DATE_OF_BIRTH` datetime default NULL,
  `MOBILE_NO` varchar(255) default NULL,
  `EMAIL` varchar(255) default NULL,
  `CREATED_BY` varchar(255) default NULL,
  `MODIFIED_BY` varchar(255) default NULL,
  `CREATED_DATETIME` datetime default NULL,
  `MODIFIED_DATETIME` datetime default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `student` */

insert  into `student`(`ID`,`COLLEGE_ID`,`COLLEGE_NAME`,`FIRST_NAME`,`LAST_NAME`,`DATE_OF_BIRTH`,`MOBILE_NO`,`EMAIL`,`CREATED_BY`,`MODIFIED_BY`,`CREATED_DATETIME`,`MODIFIED_DATETIME`) values (1,5,'J.I.U.Institute','Rahul','Sahu','2014-07-17 00:00:00','123456789','ram@gmail.com',NULL,NULL,NULL,NULL);
insert  into `student`(`ID`,`COLLEGE_ID`,`COLLEGE_NAME`,`FIRST_NAME`,`LAST_NAME`,`DATE_OF_BIRTH`,`MOBILE_NO`,`EMAIL`,`CREATED_BY`,`MODIFIED_BY`,`CREATED_DATETIME`,`MODIFIED_DATETIME`) values (2,3,'SVIM','Manish','Sharma','2014-07-18 00:00:00','7896321452','manish@gmail.com',NULL,NULL,NULL,NULL);
insert  into `student`(`ID`,`COLLEGE_ID`,`COLLEGE_NAME`,`FIRST_NAME`,`LAST_NAME`,`DATE_OF_BIRTH`,`MOBILE_NO`,`EMAIL`,`CREATED_BY`,`MODIFIED_BY`,`CREATED_DATETIME`,`MODIFIED_DATETIME`) values (3,5,'J.I.U.Institute','Vipin','Chandore','1991-12-05 00:00:00','9165254357','Vipin@gmail.com',NULL,NULL,NULL,NULL);
insert  into `student`(`ID`,`COLLEGE_ID`,`COLLEGE_NAME`,`FIRST_NAME`,`LAST_NAME`,`DATE_OF_BIRTH`,`MOBILE_NO`,`EMAIL`,`CREATED_BY`,`MODIFIED_BY`,`CREATED_DATETIME`,`MODIFIED_DATETIME`) values (4,6,'M.I.T.Ujjain','Deepak','kumar','1991-10-05 00:00:00','9165254357','d@gmail.com',NULL,NULL,NULL,NULL);
insert  into `student`(`ID`,`COLLEGE_ID`,`COLLEGE_NAME`,`FIRST_NAME`,`LAST_NAME`,`DATE_OF_BIRTH`,`MOBILE_NO`,`EMAIL`,`CREATED_BY`,`MODIFIED_BY`,`CREATED_DATETIME`,`MODIFIED_DATETIME`) values (5,6,'M.I.T.Ujjain','Sivam','Kumar','1012-12-12 00:00:00','7894563210','g@gmail.com',NULL,NULL,NULL,NULL);
insert  into `student`(`ID`,`COLLEGE_ID`,`COLLEGE_NAME`,`FIRST_NAME`,`LAST_NAME`,`DATE_OF_BIRTH`,`MOBILE_NO`,`EMAIL`,`CREATED_BY`,`MODIFIED_BY`,`CREATED_DATETIME`,`MODIFIED_DATETIME`) values (6,8,'M.D.Mansore','Gurpreet','Singh','2011-01-01 00:00:00','123456789','gurpreet@gmail.com',NULL,NULL,NULL,NULL);
insert  into `student`(`ID`,`COLLEGE_ID`,`COLLEGE_NAME`,`FIRST_NAME`,`LAST_NAME`,`DATE_OF_BIRTH`,`MOBILE_NO`,`EMAIL`,`CREATED_BY`,`MODIFIED_BY`,`CREATED_DATETIME`,`MODIFIED_DATETIME`) values (7,3,'SVIM','Viju kumar','chandore','1991-12-05 00:00:00','9165254357','vijaykumar@gmail.com',NULL,NULL,NULL,NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
