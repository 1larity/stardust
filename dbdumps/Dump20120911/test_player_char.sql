CREATE DATABASE  IF NOT EXISTS `test` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `test`;
-- MySQL dump 10.13  Distrib 5.5.16, for Win32 (x86)
--
-- Host: 192.168.1.16    Database: test
-- ------------------------------------------------------
-- Server version	5.5.20-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `player_char`
--

DROP TABLE IF EXISTS `player_char`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `player_char` (
  `uid` bigint(20) NOT NULL AUTO_INCREMENT,
  `firstname` varchar(40) NOT NULL DEFAULT 'John',
  `surname` varchar(20) NOT NULL DEFAULT 'Smith',
  `stamina` int(11) NOT NULL DEFAULT '1',
  `intelligence` int(11) NOT NULL DEFAULT '1',
  `social` int(11) NOT NULL DEFAULT '1',
  `dexterity` int(11) NOT NULL DEFAULT '1',
  `leadership` int(11) NOT NULL DEFAULT '1',
  `recuperation` int(11) NOT NULL DEFAULT '1',
  `exp` int(11) NOT NULL DEFAULT '0',
  `slots` int(11) DEFAULT '40',
  `player_account_UID` bigint(20) NOT NULL,
  `x` int(11) NOT NULL,
  `y` int(11) NOT NULL,
  `z` int(11) NOT NULL,
  `currentship` bigint(20) DEFAULT '56',
  `hitpoints` int(11) NOT NULL DEFAULT '0',
  `shield` int(11) NOT NULL DEFAULT '0',
  `power` int(11) NOT NULL DEFAULT '0',
  `cpu` int(11) NOT NULL DEFAULT '0',
  `sysx` float NOT NULL,
  `sysy` float NOT NULL,
  `sysz` float NOT NULL,
  `status` int(11) NOT NULL DEFAULT '0',
  `avatar` varchar(20) NOT NULL DEFAULT 'avatar01',
  `race` varchar(20) NOT NULL DEFAULT 'human',
  `sex` varchar(20) NOT NULL DEFAULT 'male',
  `firstattackeruid` bigint(20) DEFAULT '0',
  `firstattacktime` datetime DEFAULT '0000-00-00 00:00:00',
  `lastattackeruid` bigint(20) DEFAULT '0',
  `lastattacktime` datetime DEFAULT '0000-00-00 00:00:00',
  `expvalue` int(11) DEFAULT '1000',
  `creditvalue` int(11) DEFAULT '10000',
  `credits` bigint(20) DEFAULT '0',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `kills` int(11) DEFAULT '0',
  `pvpkills` int(11) DEFAULT '0',
  `yawangle` float DEFAULT NULL,
  `pitchangle` float DEFAULT NULL,
  `lastupdate` datetime DEFAULT NULL,
  `velocity` float DEFAULT '0',
  PRIMARY KEY (`player_account_UID`,`uid`),
  UNIQUE KEY `UID` (`uid`),
  UNIQUE KEY `nameindex` (`firstname`,`surname`),
  KEY `fk_player_char_landscape1` (`x`,`y`,`z`),
  CONSTRAINT `fk_player_char_landscape1` FOREIGN KEY (`x`, `y`, `z`) REFERENCES `landscape` (`x`, `y`, `z`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `fk_player_char_player_account1` FOREIGN KEY (`player_account_UID`) REFERENCES `player_account` (`UID`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=222 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `player_char`
--

LOCK TABLES `player_char` WRITE;
/*!40000 ALTER TABLE `player_char` DISABLE KEYS */;
INSERT INTO `player_char` VALUES (34,'Eviledick','McDick',1,2,3,4,5,6,0,40,6,1,1,10,71,1000,0,0,0,993.385,481.808,241.854,0,'avatar01.jpg','human','male',NULL,NULL,NULL,NULL,1000,10000,0,'0000-00-00 00:00:00',0,0,0,0,'2012-09-06 01:18:01',0),(35,'Evileaubs','',9,8,7,6,5,4,0,40,6,1,1,10,67,1000,0,0,0,1030.96,807.948,526.023,0,'avatar02.jpg','human','male',NULL,NULL,NULL,NULL,1000,10000,0,'0000-00-00 00:00:00',0,0,0,0,'2012-09-06 01:18:01',0),(39,'Moritach','Moonshadow',0,0,0,0,0,0,0,40,6,1,1,10,202,970,0,0,0,529.304,1122.56,619.789,0,'avatar03.jpg','human','male',NULL,NULL,101,'2012-09-05 19:01:54',1000,10000,0,'0000-00-00 00:00:00',0,0,0,0,'2012-09-06 01:18:01',0),(41,'The','Mittani',0,0,0,0,0,0,0,40,6,1,1,10,78,1000,0,0,0,956.488,881.93,-687.947,0,'avatar01.jpg','human','male',NULL,NULL,NULL,NULL,1000,10000,0,'0000-00-00 00:00:00',0,0,0,0,'2012-09-06 01:18:01',0),(42,'Pot','Noodle',2,1,1,1,1,1,400,40,6,1,1,10,79,1000,0,0,0,934.095,1180.95,-13.3621,1,'avatar02.jpg','human','male',NULL,NULL,NULL,NULL,1000,10000,4000,'0000-00-00 00:00:00',4,0,0,0,'2012-09-06 01:18:01',0),(43,'Buzz','Hullbreach',0,0,0,0,0,0,0,40,6,1,1,10,202,1000,0,0,0,204.568,1904.38,99.0961,0,'avatar02.jpg','human','male',NULL,NULL,NULL,NULL,1000,10000,0,'0000-00-00 00:00:00',0,0,0,0,'2012-09-06 01:18:01',0),(44,'Helva','Starshine',0,0,0,0,0,0,0,40,6,1,1,10,81,1000,0,0,0,1819.66,180.239,-189.127,0,'avatar04.jpg','human','male',NULL,NULL,NULL,NULL,1000,10000,0,'0000-00-00 00:00:00',0,0,0,0,'2012-09-06 01:18:01',0),(45,'Morinde','Swiftriver',0,0,0,0,0,0,0,40,6,1,1,10,82,1000,0,0,0,480.955,1123.83,241.419,0,'avatar03.jpg','human','male',NULL,NULL,NULL,NULL,1000,10000,0,'0000-00-00 00:00:00',0,0,0,0,'2012-09-06 01:18:01',0),(46,'Schubert','',0,0,0,0,0,0,0,40,6,1,1,10,83,1000,0,0,0,250.774,1142.79,341.702,0,'avatar02.jpg','human','male',NULL,NULL,NULL,NULL,1000,10000,0,'0000-00-00 00:00:00',0,0,0,0,'2012-09-06 01:18:01',0),(168,'Kaarlo','Hotard',1,1,1,2,1,1,0,40,6,1,1,10,258,1000,0,0,0,0,0,0,0,'avatar00.jpg','Human','Male',NULL,NULL,NULL,NULL,1000,10000,0,'0000-00-00 00:00:00',0,0,0,0,'2012-09-06 01:18:01',0),(194,'Ornian','Commander',1,2,1,1,1,1,198,40,6,1,1,10,284,0,0,0,0,1327.01,-149.851,-376.156,1,'avatar06.jpg','Orinian','Male',0,'0000-00-00 00:00:00',0,'0000-00-00 00:00:00',1000,10000,1848,'2012-08-12 19:04:01',2,0,0,0,'2012-09-06 01:18:01',0),(99,'1','1',1,0,0,0,0,0,0,40,999,1,1,10,56,1000,0,0,0,1000,1010,1000,0,'avatar02.jpg','human','male',NULL,NULL,NULL,NULL,1000,10000,0,'0000-00-00 00:00:00',0,0,0,0,'2012-09-06 01:18:01',0),(48,'Server','',0,0,0,0,0,0,0,40,1000,1,1,10,56,1000,0,0,0,1000,1000,1020,0,'avatar03.jpg','human','male',NULL,NULL,NULL,NULL,1000,10000,0,'0000-00-00 00:00:00',0,0,0,0,'2012-09-06 01:18:01',0),(101,'[GM]','Evile',1,1,1,2,1,1,6640,40,1016,1,1,10,210,1000,0,0,0,160.896,-687.3,-920.764,0,'avatar04.jpg','human','male',NULL,NULL,112,'2012-09-05 19:02:42',1000,10000,65102,'0000-00-00 00:00:00',66,0,0,0,'2012-09-10 22:09:04',0),(104,'bob','test',1,1,1,2,1,1,0,40,1016,1,1,10,56,1000,0,0,0,0,0,0,0,'avatar02.jpg','human','male',NULL,NULL,NULL,NULL,1000,10000,0,'0000-00-00 00:00:00',0,0,0,0,'2012-09-06 01:18:01',0),(108,'test','player',1,1,1,2,1,1,100,40,1016,1,1,10,217,1000,0,0,0,462.577,-1160.7,405.275,0,'avatar00.jpg','Human','Male',NULL,NULL,NULL,NULL,1000,10000,11000,'0000-00-00 00:00:00',1,0,0,0,'2012-09-10 19:19:44',0),(110,'test02','player',1,1,1,2,1,1,0,40,1016,1,1,10,218,1000,0,0,0,1147.88,624.294,1135.44,0,'avatar00.jpg','Human','Male',NULL,NULL,NULL,NULL,1000,10000,0,'0000-00-00 00:00:00',0,0,0,0,'2012-09-10 19:20:52',0),(111,'Telfer','Izard',1,2,1,1,1,1,189,40,1016,1,1,10,219,1000,0,0,0,761.004,186.137,704.983,0,'avatar05.jpg','Orinian','Female',NULL,NULL,NULL,NULL,1000,10000,2142,'0000-00-00 00:00:00',2,0,0,0,'2012-09-10 21:55:03',0),(112,'Merla','Neault',1,1,2,1,1,1,58548588,40,1016,1,1,10,220,1000,0,0,0,-792.184,490.195,-1052.67,0,'avatar08.jpg','Gulhurg','Male',NULL,NULL,191,'2012-09-06 16:10:05',1000,10000,585699109,'0000-00-00 00:00:00',45,0,0,0,'2012-09-11 04:31:21',0),(117,'Dumont','Riveron',1,1,1,2,1,1,0,40,1016,1,1,10,225,1000,0,0,0,785.941,955.411,-618.017,1,'avatar01.jpg','Human','Male',NULL,NULL,NULL,NULL,1000,10000,0,'0000-00-00 00:00:00',0,0,0,0,'2012-09-06 01:18:01',0),(118,'Delray','Lesage',1,1,1,1,1,2,377,40,1016,1,1,10,226,1000,0,0,0,-1214.61,-575.528,-545.344,0,'avatar03.jpg','Jelkek','Female',NULL,NULL,NULL,NULL,1000,10000,3823,'0000-00-00 00:00:00',4,0,0,0,'2012-09-06 01:18:01',0),(190,'Vernon','',1,1,2,1,1,1,9418,40,1016,1,1,10,280,1000,0,0,0,-1033.19,-1044,225.061,0,'avatar08.jpg','Gulhurg','Male',NULL,NULL,191,'2012-09-06 22:51:35',1000,10000,139936,'2012-08-08 16:15:23',95,0,0,0,'2012-09-11 05:07:25',0),(191,'You','',1,1,1,2,1,1,4116,40,1016,1,1,10,281,1000,0,0,0,763.066,438.005,578.054,0,'avatar01.jpg','Human','Male',0,'0000-00-00 00:00:00',112,'2012-09-06 07:07:47',1000,10000,108591,'2012-08-08 17:03:29',41,0,0,0,'2012-09-11 04:36:07',0),(124,'Neuveville','Belgard',1,1,1,2,1,1,0,40,1019,1,1,10,232,1000,0,0,0,0,0,0,0,'avatar04.jpg','Human','Male',NULL,NULL,NULL,NULL,1000,10000,0,'0000-00-00 00:00:00',0,0,0,0,'2012-09-06 01:18:01',0),(119,'Aldrick','Caillier',1,1,1,2,1,1,0,40,1020,1,1,10,227,1000,0,0,0,0,0,0,0,'avatar02.jpg','Human','Male',NULL,NULL,NULL,NULL,1000,10000,0,'0000-00-00 00:00:00',0,0,0,0,'2012-09-06 01:18:01',0),(120,'Charlot','Gal',1,1,1,1,1,2,0,40,1020,1,1,10,228,1000,0,0,0,1095.09,1018.51,874.209,0,'avatar06.jpg','Jelkek','Female',NULL,NULL,NULL,NULL,1000,10000,0,'0000-00-00 00:00:00',0,0,0,0,'2012-09-06 01:18:01',0),(121,'Davin','Theard',1,1,2,1,1,1,0,40,1020,1,1,10,229,1000,0,0,0,204.927,1370.97,226.88,0,'avatar06.jpg','Gulhurg','Male',NULL,NULL,NULL,NULL,1000,10000,0,'0000-00-00 00:00:00',0,0,0,0,'2012-09-06 01:18:01',0),(122,'Bulbous','Parsnip',1,2,1,1,1,1,0,40,1021,1,1,10,230,1000,0,0,0,836.773,66.0741,1239.92,0,'avatar05.jpg','Orinian','Female',NULL,NULL,NULL,NULL,1000,10000,0,'0000-00-00 00:00:00',0,0,0,0,'2012-09-06 01:18:01',0),(123,'Russell','Bou',1,1,1,1,1,2,0,40,1022,1,1,10,231,1000,0,0,0,45887.3,7000.62,998.285,0,'avatar09.jpg','Jelkek','Male',NULL,NULL,NULL,NULL,1000,10000,0,'0000-00-00 00:00:00',0,0,0,0,'2012-09-06 01:18:01',0),(125,'ydius','ntaps',1,2,1,1,1,1,0,40,1024,1,1,10,233,1000,0,0,0,-154997,51085.6,122294,0,'avatar05.jpg','Orinian','Male',NULL,NULL,NULL,NULL,1000,10000,0,'0000-00-00 00:00:00',0,0,0,0,'2012-09-06 01:18:01',0),(127,'big','pilot',1,1,1,2,1,1,0,40,1028,1,1,10,235,1000,0,0,0,0,1103.21,706.877,0,'avatar03.jpg','Human','Male',NULL,NULL,NULL,NULL,1000,10000,0,'0000-00-00 00:00:00',0,0,0,0,'2012-09-06 01:18:01',0),(162,'Deveral','Mally',1,1,1,2,1,1,0,40,1028,1,1,10,252,1000,0,0,0,0,0,0,0,'avatar00.jpg','Human','Male',NULL,NULL,NULL,NULL,1000,10000,0,'0000-00-00 00:00:00',0,0,0,0,'2012-09-06 01:18:01',0),(126,'Bear','empress',1,2,1,1,1,1,0,40,1029,1,1,10,234,1000,0,0,0,0,0,0,0,'avatar04.jpg','Orinian','Female',NULL,NULL,NULL,NULL,1000,10000,0,'0000-00-00 00:00:00',0,0,0,0,'2012-09-06 01:18:01',0),(129,'Jesus','Lizard',1,1,1,2,1,1,0,40,1035,1,1,10,237,1000,0,0,0,0,0,0,0,'avatar04.jpg','Human','Female',NULL,NULL,NULL,NULL,1000,10000,0,'0000-00-00 00:00:00',0,0,0,0,'2012-09-06 01:18:01',0),(130,'Romana','Emard',1,2,1,1,1,1,0,40,1042,1,1,10,238,1000,0,0,0,0,1103.21,706.877,0,'avatar05.jpg','Orinian','Female',NULL,NULL,NULL,NULL,1000,10000,0,'0000-00-00 00:00:00',0,0,0,0,'2012-09-06 01:18:01',0),(144,'Paris','Marre',1,1,1,2,1,1,0,40,1042,1,1,10,242,1000,0,0,0,0,1103.21,706.877,0,'avatar00.jpg','Human','Male',NULL,NULL,NULL,NULL,1000,10000,0,'0000-00-00 00:00:00',0,0,0,0,'2012-09-06 01:18:01',0),(145,'Jordon','Bero',1,1,1,2,1,1,0,40,1042,1,1,10,243,1000,0,0,0,0,0,0,0,'avatar00.jpg','Human','Male',NULL,NULL,NULL,NULL,1000,10000,0,'0000-00-00 00:00:00',0,0,0,0,'2012-09-06 01:18:01',0),(146,'Seymour','Couillard',1,1,1,2,1,1,0,40,1042,1,1,10,244,1000,0,0,0,1082.68,739.409,-338.146,0,'avatar00.jpg','Human','Male',NULL,NULL,NULL,NULL,1000,10000,0,'0000-00-00 00:00:00',0,0,0,0,'2012-09-06 01:18:01',0),(150,'Dillen','Giraud',1,1,1,2,1,1,0,40,1042,1,1,10,245,1000,0,0,0,0,0,0,0,'avatar00.jpg','Human','Male',NULL,NULL,NULL,NULL,1000,10000,0,'0000-00-00 00:00:00',0,0,0,0,'2012-09-06 01:18:01',0),(154,'Romana','Emardx',1,1,1,2,1,1,0,40,1042,1,1,10,246,1000,0,0,0,0,0,0,0,'avatar00.jpg','Human','Male',NULL,NULL,NULL,NULL,1000,10000,0,'0000-00-00 00:00:00',0,0,0,0,'2012-09-06 01:18:01',0),(155,'Romanaz','Emard',1,1,1,2,1,1,0,40,1042,1,1,10,247,1000,0,0,0,634.744,1072.6,506.652,0,'avatar00.jpg','Human','Male',NULL,NULL,NULL,NULL,1000,10000,0,'0000-00-00 00:00:00',0,0,0,0,'2012-09-06 01:18:01',0),(158,'Mappo','Trell',1,1,1,2,1,1,0,40,1044,1,1,10,248,1000,0,0,0,0,0,0,0,'avatar01.jpg','Human','Male',NULL,NULL,NULL,NULL,1000,10000,0,'0000-00-00 00:00:00',0,0,0,0,'2012-09-06 01:18:01',0),(159,'Deathtoll','',1,2,1,1,1,1,0,40,1045,1,1,10,249,1000,0,0,0,776.659,3616.72,637.612,0,'avatar05.jpg','Orinian','Male',NULL,NULL,NULL,NULL,1000,10000,0,'0000-00-00 00:00:00',0,0,0,0,'2012-09-06 01:18:01',0),(160,'scary','death',1,2,1,1,1,1,0,40,1046,1,1,10,250,1000,0,0,0,837.333,537.332,1443.96,0,'avatar05.jpg','Orinian','Male',NULL,NULL,NULL,NULL,1000,10000,0,'0000-00-00 00:00:00',0,0,0,0,'2012-09-06 01:18:01',0),(161,'Ricard','Han',1,1,1,2,1,1,0,40,1047,1,1,10,251,1000,0,0,0,0,0,0,0,'avatar02.jpg','Human','Male',NULL,NULL,NULL,NULL,1000,10000,0,'0000-00-00 00:00:00',0,0,0,0,'2012-09-06 01:18:01',0),(163,'Yi','Sima',1,1,2,1,1,1,0,40,1048,1,1,10,253,1000,0,0,0,0,0,0,0,'avatar09.jpg','Gulhurg','Male',NULL,NULL,NULL,NULL,1000,10000,0,'0000-00-00 00:00:00',0,0,0,0,'2012-09-06 01:18:01',0),(164,'Julien','Sain',1,1,1,2,1,1,0,40,1048,1,1,10,254,1000,0,0,0,0,0,0,0,'avatar00.jpg','Human','Male',NULL,NULL,NULL,NULL,1000,10000,0,'0000-00-00 00:00:00',0,0,0,0,'2012-09-06 01:18:01',0),(165,'fahad','zafar',1,1,1,2,1,1,0,40,1049,1,1,10,255,1000,0,0,0,0,0,0,0,'avatar02.jpg','Human','Male',NULL,NULL,NULL,NULL,1000,10000,0,'0000-00-00 00:00:00',0,0,0,0,'2012-09-06 01:18:01',0),(166,'Baxter','Quigley',1,1,2,1,1,1,0,40,1050,1,1,10,256,1000,0,0,0,0,0,0,0,'avatar05.jpg','Gulhurg','Female',NULL,NULL,NULL,NULL,1000,10000,0,'0000-00-00 00:00:00',0,0,0,0,'2012-09-06 01:18:01',0),(167,'Zailz','Prower',1,1,1,1,1,2,0,40,1054,1,1,10,257,1000,0,0,0,0,0,0,0,'avatar07.jpg','Jelkek','Male',NULL,NULL,NULL,NULL,1000,10000,0,'0000-00-00 00:00:00',0,0,0,0,'2012-09-06 01:18:01',0),(169,'Canain','Minaro',1,1,1,2,1,1,0,40,1056,1,1,10,259,1000,0,0,0,0,0,0,0,'avatar02.jpg','Human','Male',NULL,NULL,NULL,NULL,1000,10000,0,'0000-00-00 00:00:00',0,0,0,0,'2012-09-06 01:18:01',0),(170,'Garrin','Legnon',1,1,1,2,1,1,0,40,1057,1,1,10,260,1000,0,0,0,0,0,0,0,'avatar04.jpg','Human','Male',NULL,NULL,NULL,NULL,1000,10000,0,'0000-00-00 00:00:00',0,0,0,0,'2012-09-06 01:18:01',0),(171,'Armand','Morisette',1,1,1,1,1,2,1850,40,1058,1,1,10,261,1000,0,0,0,274.476,1374.18,-270.718,1,'avatar07.jpg','Jelkek','Male',NULL,NULL,NULL,NULL,1000,10000,19044,'0000-00-00 00:00:00',19,0,337.6,20.7985,'2012-09-09 10:14:40',0),(188,'Idiom','Burst',1,1,1,2,1,1,94,40,1058,1,1,10,278,1000,0,0,0,1328.51,-236.938,-449.55,1,'avatar00.jpg','Human','Male',NULL,NULL,NULL,NULL,1000,10000,1020,'0000-00-00 00:00:00',1,0,0,0,'2012-09-06 01:18:01',0),(172,'Derrall','Allemand',1,1,1,1,1,2,0,40,1059,1,1,10,262,1000,0,0,0,0,0,0,0,'avatar06.jpg','Jelkek','Male',NULL,NULL,NULL,NULL,1000,10000,0,'0000-00-00 00:00:00',0,0,0,0,'2012-09-06 01:18:01',0),(173,'Clovis','Marre',1,1,1,2,1,1,0,40,1061,1,1,10,263,1000,0,0,0,0,0,0,0,'avatar00.jpg','Human','Male',NULL,NULL,NULL,NULL,1000,10000,0,'0000-00-00 00:00:00',0,0,0,0,'2012-09-06 01:18:01',0),(174,'Andrea','L',1,2,1,1,1,1,0,40,1062,1,1,10,264,1000,0,0,0,0,0,0,0,'avatar02.jpg','Orinian','Male',NULL,NULL,NULL,NULL,1000,10000,0,'0000-00-00 00:00:00',0,0,0,0,'2012-09-06 01:18:01',0),(175,'rhys','evans',1,2,1,1,1,1,0,40,1063,1,1,10,265,1000,0,0,0,0,0,0,0,'avatar00.jpg','Orinian','Male',NULL,NULL,NULL,NULL,1000,10000,0,'0000-00-00 00:00:00',0,0,0,0,'2012-09-06 01:18:01',0),(176,'Isnaxur','Exalis',1,2,1,1,1,1,0,40,1064,1,1,10,266,1000,0,0,0,0,0,0,0,'avatar00.jpg','Orinian','Male',NULL,NULL,NULL,NULL,1000,10000,0,'0000-00-00 00:00:00',0,0,0,0,'2012-09-06 01:18:01',0),(177,'shur','mano',1,1,1,2,1,1,0,40,1065,1,1,10,267,1000,0,0,0,0,0,0,0,'avatar02.jpg','Human','Male',NULL,NULL,NULL,NULL,1000,10000,0,'0000-00-00 00:00:00',0,0,0,0,'2012-09-06 01:18:01',0),(178,'belford','scott',1,1,1,2,1,1,0,40,1066,1,1,10,268,1000,0,0,0,110.75,1250.27,1348.41,0,'avatar01.jpg','Human','Male',NULL,NULL,NULL,NULL,1000,10000,0,'0000-00-00 00:00:00',0,0,0,0,'2012-09-06 01:18:01',0),(179,'Brom','King',1,1,1,2,1,1,0,40,1067,1,1,10,269,1000,0,0,0,0,0,0,0,'avatar01.jpg','Human','Male',NULL,NULL,NULL,NULL,1000,10000,0,'0000-00-00 00:00:00',0,0,0,0,'2012-09-06 01:18:01',0),(180,'Solar','wolf',1,1,1,2,1,1,0,40,1068,1,1,10,270,1000,0,0,0,0,0,0,0,'avatar03.jpg','Human','Male',NULL,NULL,NULL,NULL,1000,10000,0,'0000-00-00 00:00:00',0,0,0,0,'2012-09-06 01:18:01',0),(181,'Q','Ball',1,1,1,2,1,1,0,40,1069,1,1,10,271,1000,0,0,0,0,0,0,0,'avatar01.jpg','Human','Male',NULL,NULL,NULL,NULL,1000,10000,0,'0000-00-00 00:00:00',0,0,0,0,'2012-09-06 01:18:01',0),(182,'J\'kor','Rhe\'ka',1,1,1,1,1,2,0,40,1070,1,1,10,272,1000,0,0,0,2533.63,2317.35,1454.23,0,'avatar02.jpg','Jelkek','Male',NULL,NULL,NULL,NULL,1000,10000,0,'0000-00-00 00:00:00',0,0,0,0,'2012-09-06 01:18:01',0),(183,'Snake','',1,1,1,2,1,1,0,40,1071,1,1,10,273,1000,0,0,0,180.882,360.37,540.833,0,'avatar02.jpg','Human','Male',NULL,NULL,NULL,NULL,1000,10000,0,'0000-00-00 00:00:00',0,0,0,0,'2012-09-06 01:18:01',0),(184,'Maxime','Vernier',1,1,1,1,1,2,0,40,1072,1,1,10,274,1000,0,0,0,0,0,0,0,'avatar02.jpg','Jelkek','Male',NULL,NULL,NULL,NULL,1000,10000,0,'0000-00-00 00:00:00',0,0,0,0,'2012-09-06 01:18:01',0),(185,'EAT MY','u no what',1,1,1,2,1,1,0,40,1073,1,1,10,275,1000,0,0,0,0,0,0,0,'avatar00.jpg','Human','Male',NULL,NULL,NULL,NULL,1000,10000,0,'0000-00-00 00:00:00',0,0,0,0,'2012-09-06 01:18:01',0),(186,'Xim','Obirah',1,1,1,2,1,1,0,40,1074,1,1,10,276,1000,0,0,0,0,0,0,0,'avatar02.jpg','Human','Male',NULL,NULL,NULL,NULL,1000,10000,0,'0000-00-00 00:00:00',0,0,0,0,'2012-09-06 01:18:01',0),(187,'Marvinos','',1,1,1,2,1,1,0,40,1075,1,1,10,277,1000,0,0,0,0,0,0,0,'avatar00.jpg','Human','Male',NULL,NULL,NULL,NULL,1000,10000,0,'0000-00-00 00:00:00',0,0,0,0,'2012-09-06 01:18:01',0),(192,'jamba','wamba',1,1,1,2,1,1,0,40,1077,1,1,10,282,0,0,0,0,0,0,0,0,'avatar02.jpg','Human','Male',0,'0000-00-00 00:00:00',0,'0000-00-00 00:00:00',1000,10000,0,'2012-08-08 20:21:25',0,0,0,0,'2012-09-06 01:18:01',0),(193,'Moherowy','Ninja',1,1,1,2,1,1,0,40,1079,1,1,10,283,0,0,0,0,0,0,0,0,'avatar00.jpg','Human','Male',0,'0000-00-00 00:00:00',0,'0000-00-00 00:00:00',1000,10000,0,'2012-08-11 20:42:13',0,0,0,0,'2012-09-06 01:18:01',0),(195,'Travers','Bour',1,1,1,1,1,2,0,40,1080,1,1,10,285,0,0,0,0,0,0,0,0,'avatar06.jpg','Jelkek','Male',0,'0000-00-00 00:00:00',0,'0000-00-00 00:00:00',1000,10000,0,'2012-08-13 04:45:56',0,0,0,0,'2012-09-06 01:18:01',0),(196,'Tyce','Rabalais',1,2,1,1,1,1,0,40,1081,1,1,10,286,0,0,0,0,0,0,0,0,'avatar05.jpg','Orinian','Male',0,'0000-00-00 00:00:00',0,'0000-00-00 00:00:00',1000,10000,0,'2012-08-14 15:37:36',0,0,0,0,'2012-09-06 01:18:01',0),(197,'Lordmilky','The II',1,1,1,2,1,1,0,40,1082,1,1,10,287,0,0,0,0,654.686,539.422,-868.232,1,'avatar00.jpg','Human','Male',0,'0000-00-00 00:00:00',0,'0000-00-00 00:00:00',1000,10000,0,'2012-08-14 23:37:12',0,0,0,0,'2012-09-06 01:18:01',0),(198,'Cooty','Beez',1,2,1,1,1,1,0,40,1083,1,1,10,288,0,0,0,0,138.805,1321.34,-297.569,1,'avatar07.jpg','Orinian','Male',0,'0000-00-00 00:00:00',0,'0000-00-00 00:00:00',1000,10000,0,'2012-08-15 00:15:40',0,0,0,0,'2012-09-06 01:18:01',0),(199,'Mallory','Salvant',1,1,1,2,1,1,0,40,1086,1,1,10,231636,0,0,0,0,0,0,0,0,'avatar07.jpg','Human','Male',0,'0000-00-00 00:00:00',0,'0000-00-00 00:00:00',1000,10000,0,'2012-08-16 23:07:43',0,0,0,0,'2012-09-06 01:18:01',0),(200,'Dariel','Ferron',1,1,1,1,1,2,0,40,1087,1,1,10,523984,0,0,0,0,0,0,0,0,'avatar07.jpg','Jelkek','Male',0,'0000-00-00 00:00:00',0,'0000-00-00 00:00:00',1000,10000,0,'2012-08-17 21:35:33',0,0,0,0,'2012-09-06 01:18:01',0),(201,'Christoph','carter',1,1,2,1,1,1,0,40,1088,1,1,10,1146423,0,0,0,0,0,0,0,0,'avatar00.jpg','Gulhurg','Male',0,'0000-00-00 00:00:00',0,'0000-00-00 00:00:00',1000,10000,0,'2012-08-19 16:30:54',0,0,0,0,'2012-09-06 01:18:01',0),(202,'Liatusil','moody',1,2,1,1,1,1,0,40,1088,1,1,10,1146424,0,0,0,0,0,0,0,0,'avatar02.jpg','Orinian','Male',0,'0000-00-00 00:00:00',0,'0000-00-00 00:00:00',1000,10000,0,'2012-08-19 19:48:59',0,0,0,0,'2012-09-06 01:18:01',0),(203,'John','Carter',1,1,1,2,1,1,0,40,1088,1,1,10,1146425,0,0,0,0,0,0,0,0,'avatar02.jpg','Human','Male',0,'0000-00-00 00:00:00',0,'0000-00-00 00:00:00',1000,10000,0,'2012-08-19 19:50:50',0,0,0,0,'2012-09-06 01:18:01',0),(204,'Dave','DuvVall',1,1,1,2,1,1,0,40,1089,1,1,10,1234476,0,0,0,0,0,0,0,0,'avatar04.jpg','Human','Male',0,'0000-00-00 00:00:00',0,'0000-00-00 00:00:00',1000,10000,0,'2012-08-21 06:56:52',0,0,0,0,'2012-09-06 01:18:01',0),(205,'Lord','Masoretic',1,1,1,2,1,1,0,40,1090,1,1,10,1234477,0,0,0,0,0,0,0,0,'avatar01.jpg','Human','Male',0,'0000-00-00 00:00:00',0,'0000-00-00 00:00:00',1000,10000,0,'2012-08-21 11:59:30',0,0,0,0,'2012-09-06 01:18:01',0),(206,'Monkez','Uncle',1,1,1,1,1,2,0,40,1091,1,1,10,1234478,0,0,0,0,0,0,0,0,'avatar01.jpg','Jelkek','Male',0,'0000-00-00 00:00:00',0,'0000-00-00 00:00:00',1000,10000,0,'2012-08-22 15:09:14',0,0,0,0,'2012-09-06 01:18:01',0),(207,'honossillie','honosssillie',1,1,1,2,1,1,0,40,1092,1,1,10,1234501,0,0,0,0,0,0,0,0,'avatar02.jpg','Human','Male',0,'0000-00-00 00:00:00',0,'0000-00-00 00:00:00',1000,10000,0,'2012-08-24 20:42:00',0,0,0,0,'2012-09-06 01:18:01',0),(208,'Suny','Gustin',1,2,1,1,1,1,0,40,1095,1,1,10,1234502,0,0,0,0,0,0,0,0,'avatar04.jpg','Orinian','Female',0,'0000-00-00 00:00:00',0,'0000-00-00 00:00:00',1000,10000,0,'2012-08-25 20:05:33',0,0,0,0,'2012-09-06 01:18:01',0),(209,'Raynard','Dubuc',1,1,1,2,1,1,0,40,1096,1,1,10,1234517,0,0,0,0,0,0,0,0,'avatar02.jpg','Human','Male',0,'0000-00-00 00:00:00',0,'0000-00-00 00:00:00',1000,10000,0,'2012-08-26 17:22:47',0,0,0,0,'2012-09-06 01:18:01',0),(210,'Yves','Hasbrouck',1,1,1,2,1,1,0,40,1098,1,1,10,1234529,0,0,0,0,0,0,0,0,'avatar00.jpg','Human','Male',0,'0000-00-00 00:00:00',0,'0000-00-00 00:00:00',1000,10000,0,'2012-08-27 23:08:02',0,0,0,0,'2012-09-06 01:18:01',0),(211,'Vatonage','Gol',1,1,1,2,1,1,0,40,1099,1,1,10,1234530,0,0,0,0,0,0,0,0,'avatar02.jpg','Human','Male',0,'0000-00-00 00:00:00',0,'0000-00-00 00:00:00',1000,10000,0,'2012-08-28 00:48:46',0,0,0,0,'2012-09-08 18:41:43',0),(212,'anduin','Elanor',1,1,1,1,1,2,0,40,1100,1,1,10,1235220,0,0,0,0,0,0,0,0,'avatar02.jpg','Jelkek','Male',0,'0000-00-00 00:00:00',0,'0000-00-00 00:00:00',1000,10000,0,'2012-08-29 18:11:24',0,0,0,0,'2012-09-07 10:09:20',0),(221,'Blaze','Gort',1,1,1,2,1,1,0,40,1100,1,1,10,1293844,0,0,0,0,0,0,0,0,'avatar02.jpg','Human','Male',0,'0000-00-00 00:00:00',0,'0000-00-00 00:00:00',1000,10000,0,'2012-09-07 09:12:06',0,0,0,0,'2012-09-07 10:13:34',0),(220,'rob','carter',1,1,1,2,1,1,0,40,1101,1,1,10,1288709,0,0,0,0,318.618,454.549,1277.95,1,'avatar03.jpg','Human','Male',0,'0000-00-00 00:00:00',0,'0000-00-00 00:00:00',1000,10000,0,'2012-09-03 23:27:58',0,0,0,0,'2012-09-06 01:18:01',0),(213,'Herbert','Heroux',1,2,1,1,1,1,0,40,1102,1,1,10,1235223,0,0,0,0,438.251,971.863,618.283,1,'avatar04.jpg','Orinian','Female',0,'0000-00-00 00:00:00',0,'0000-00-00 00:00:00',1000,10000,0,'2012-08-30 22:28:38',0,0,0,0,'2012-09-06 01:18:01',0),(215,'Leonore','Auge',1,1,1,2,1,1,0,40,1106,1,1,10,1235226,0,0,0,0,0,0,0,0,'avatar02.jpg','Human','Male',0,'0000-00-00 00:00:00',0,'0000-00-00 00:00:00',1000,10000,0,'2012-09-01 00:13:50',0,0,0,0,'2012-09-06 01:18:01',0),(214,'Tarlon','Rhaan',1,1,1,2,1,1,0,40,1108,1,1,10,1235225,0,0,0,0,0,0,0,0,'avatar02.jpg','Human','Male',0,'0000-00-00 00:00:00',0,'0000-00-00 00:00:00',1000,10000,0,'2012-08-31 23:33:30',0,0,0,0,'2012-09-06 01:18:01',0),(216,'pinkspider','',1,1,1,2,1,1,0,40,1109,1,1,10,1235227,0,0,0,0,0,0,0,0,'avatar00.jpg','Human','Female',0,'0000-00-00 00:00:00',0,'0000-00-00 00:00:00',1000,10000,0,'2012-09-01 13:14:07',0,0,0,0,'2012-09-06 01:18:01',0),(217,'Raydon','Lory',1,1,1,2,1,1,0,40,1110,1,1,10,1235232,0,0,0,0,0,0,0,0,'avatar00.jpg','Human','Male',0,'0000-00-00 00:00:00',0,'0000-00-00 00:00:00',1000,10000,0,'2012-09-01 15:46:58',0,0,0,0,'2012-09-06 01:18:01',0),(218,'allQaab','TRiPtick',1,1,1,2,1,1,0,40,1111,1,1,10,1235233,0,0,0,0,726.114,133.504,1135.69,1,'avatar00.jpg','Human','Male',0,'0000-00-00 00:00:00',0,'0000-00-00 00:00:00',1000,10000,0,'2012-09-01 17:38:29',0,0,0,0,'2012-09-06 01:18:01',0),(219,'Jet','Lancer',1,2,1,1,1,1,107,40,1112,1,1,10,1235250,0,0,0,0,1357.97,-128.12,-312.801,0,'avatar02.jpg','Orinian','Male',0,'0000-00-00 00:00:00',0,'0000-00-00 00:00:00',1000,10000,1080,'2012-09-03 17:01:05',1,0,0,0,'2012-09-06 01:18:01',0);
/*!40000 ALTER TABLE `player_char` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 trigger racial_bonus before insert on player_char
for each row 
begin
if NEW.race="human" then
    set NEW.dexterity = 2;
elseif NEW.race="jelkek" then
    set NEW.recuperation=2;
elseif NEW.race="orinian" then
    set NEW.intelligence=2;
elseif NEW.race="gulhurg" then
    set NEW.social=2;
    end if;
    end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2012-09-11 13:58:12
