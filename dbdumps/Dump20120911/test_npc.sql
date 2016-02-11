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
-- Table structure for table `npc`
--

DROP TABLE IF EXISTS `npc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `npc` (
  `UID` bigint(20) NOT NULL AUTO_INCREMENT,
  `race` varchar(45) DEFAULT NULL,
  `x` int(11) NOT NULL DEFAULT '1',
  `y` int(11) NOT NULL DEFAULT '1',
  `z` int(11) NOT NULL DEFAULT '1',
  `is_static` tinyint(1) NOT NULL DEFAULT '1',
  `firstnames_uid` bigint(20) NOT NULL DEFAULT '1',
  `surnames_uid` bigint(20) NOT NULL DEFAULT '1',
  `sysx` float DEFAULT '1000',
  `sysy` float DEFAULT '0',
  `sysz` float DEFAULT '1000',
  `hitpoints` int(11) DEFAULT '2000',
  `faction` varchar(45) DEFAULT 'genericenemy',
  `stamina` int(11) DEFAULT '1',
  `intelligence` int(11) DEFAULT '1',
  `social` int(11) DEFAULT '1',
  `dexterity` int(11) DEFAULT '1',
  `leadership` int(11) DEFAULT '1',
  `recuperation` int(11) DEFAULT '1',
  `exp` varchar(45) DEFAULT NULL,
  `firstattackeruid` bigint(20) NOT NULL DEFAULT '0',
  `firstattacktime` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `lastattackeruid` bigint(20) NOT NULL DEFAULT '0',
  `lastattacktime` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `expvalue` int(11) DEFAULT '0',
  `creditvalue` int(11) DEFAULT '0',
  `status` int(11) DEFAULT '1',
  `yawangle` float DEFAULT '0',
  `pitchangle` float DEFAULT '0',
  `shipname` varchar(45) DEFAULT 'none',
  `lastupdate` datetime DEFAULT '0000-00-00 00:00:00',
  `velocity` float DEFAULT '0',
  UNIQUE KEY `UID` (`UID`),
  KEY `fk_npc_firstnames1` (`firstnames_uid`),
  KEY `fk_npc_surnames1` (`surnames_uid`),
  KEY `x` (`x`,`y`,`z`,`firstnames_uid`,`surnames_uid`),
  CONSTRAINT `fk_npc_firstnames1` FOREIGN KEY (`firstnames_uid`) REFERENCES `firstnames` (`uid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_npc_landscape1` FOREIGN KEY (`x`, `y`, `z`) REFERENCES `landscape` (`x`, `y`, `z`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_npc_surnames1` FOREIGN KEY (`surnames_uid`) REFERENCES `surnames` (`uid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=451 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `npc`
--

LOCK TABLES `npc` WRITE;
/*!40000 ALTER TABLE `npc` DISABLE KEYS */;
INSERT INTO `npc` VALUES (418,'aih',1,1,10,1,7168,1,1080.26,873.343,565.953,500,'aihive',1,1,1,1,1,1,'0',0,'0000-00-00 00:00:00',0,'0000-00-00 00:00:00',100,1000,1,0,0,'shielddisruptor','2012-09-07 01:48:01',0),(420,'aih',1,1,10,1,7168,1,-954.631,-307.341,-1115.45,500,'aihive',1,1,1,1,1,1,'0',0,'0000-00-00 00:00:00',0,'0000-00-00 00:00:00',100,1000,1,0,0,'shielddisruptor','2012-09-07 01:48:01',0),(422,'aih',1,1,10,1,7168,1,639.495,1341.42,-204.043,500,'aihive',1,1,1,1,1,1,'0',0,'0000-00-00 00:00:00',0,'0000-00-00 00:00:00',100,1000,1,0,0,'shielddisruptor','2012-09-07 01:48:01',0),(423,'aih',1,1,10,1,7168,1,955.042,497.388,1044.27,500,'aihive',1,1,1,1,1,1,'0',0,'0000-00-00 00:00:00',0,'0000-00-00 00:00:00',100,1000,1,0,0,'shielddisruptor','2012-09-07 01:48:01',0),(427,'aih',1,1,10,1,7168,1,-160.579,860.672,-1217.97,500,'aihive',1,1,1,1,1,1,'0',0,'0000-00-00 00:00:00',0,'0000-00-00 00:00:00',100,1000,1,0,0,'shielddisruptor','2012-09-07 01:48:01',0),(430,'aih',1,1,10,1,7168,1,730.752,1309.92,-10.1735,500,'aihive',1,1,1,1,1,1,'0',0,'0000-00-00 00:00:00',0,'0000-00-00 00:00:00',100,1000,1,0,0,'shielddisruptor','2012-09-07 01:48:01',0),(431,'aih',1,1,10,1,7168,1,-446.55,-405.453,-1373.39,500,'aihive',1,1,1,1,1,1,'0',0,'0000-00-00 00:00:00',0,'0000-00-00 00:00:00',100,1000,1,0,0,'shielddisruptor','2012-09-07 01:52:14',0),(433,'aih',1,1,10,1,7168,1,-557.713,-375.027,-1341.01,500,'aihive',1,1,1,1,1,1,'0',0,'0000-00-00 00:00:00',0,'0000-00-00 00:00:00',100,1000,1,0,0,'shielddisruptor','2012-09-07 01:54:27',0),(435,'aih',1,1,10,1,7168,1,-385.295,736.422,-1248.69,500,'aihive',1,1,1,1,1,1,'0',0,'0000-00-00 00:00:00',0,'0000-00-00 00:00:00',100,1000,1,0,0,'shielddisruptor','2012-09-07 03:20:22',0),(438,'aih',1,1,10,1,7168,1,-182.785,1487.59,60.5097,500,'aihive',1,1,1,1,1,1,'0',0,'0000-00-00 00:00:00',0,'0000-00-00 00:00:00',100,1000,1,0,0,'shielddisruptor','2012-09-10 01:01:18',0),(439,'aih',1,1,10,1,7168,1,-17.8118,-1369.36,-611.987,500,'aihive',1,1,1,1,1,1,'0',0,'0000-00-00 00:00:00',0,'0000-00-00 00:00:00',100,1000,1,0,0,'shielddisruptor','2012-09-10 01:01:19',0),(440,'aih',1,1,10,1,7168,1,-512.563,-1317.54,-501.368,500,'aihive',1,1,1,1,1,1,'0',0,'0000-00-00 00:00:00',0,'0000-00-00 00:00:00',100,1000,1,0,0,'shielddisruptor','2012-09-10 01:01:19',0),(441,'aih',1,1,10,1,7168,1,364.078,499.664,1366.67,500,'aihive',1,1,1,1,1,1,'0',0,'0000-00-00 00:00:00',0,'0000-00-00 00:00:00',100,1000,1,0,0,'shielddisruptor','2012-09-10 01:01:19',0),(442,'aih',1,1,10,1,7168,1,-229.385,1470.36,188.199,500,'aihive',1,1,1,1,1,1,'0',0,'0000-00-00 00:00:00',0,'0000-00-00 00:00:00',100,1000,1,0,0,'shielddisruptor','2012-09-10 01:01:20',0),(443,'aih',1,1,10,1,7168,1,854.119,1203.82,267.022,500,'aihive',1,1,1,1,1,1,'0',0,'0000-00-00 00:00:00',0,'0000-00-00 00:00:00',100,1000,1,0,0,'shielddisruptor','2012-09-10 01:01:20',0),(444,'aih',1,1,10,1,7168,1,-28.8598,-1081.94,-1038.54,500,'aihive',1,1,1,1,1,1,'0',0,'0000-00-00 00:00:00',0,'0000-00-00 00:00:00',100,1000,1,0,0,'shielddisruptor','2012-09-10 01:01:20',0),(445,'aih',1,1,10,1,7168,1,292.412,426.158,1408.15,500,'aihive',1,1,1,1,1,1,'0',0,'0000-00-00 00:00:00',0,'0000-00-00 00:00:00',100,1000,1,0,0,'shielddisruptor','2012-09-10 01:01:20',0),(446,'aih',1,1,10,1,7168,1,423.636,1190.31,-808.51,500,'aihive',1,1,1,1,1,1,'0',0,'0000-00-00 00:00:00',0,'0000-00-00 00:00:00',100,1000,1,0,0,'shielddisruptor','2012-09-10 01:01:20',0),(447,'aih',1,1,10,1,7168,1,-293.483,1253.75,769.4,500,'aihive',1,1,1,1,1,1,'0',0,'0000-00-00 00:00:00',0,'0000-00-00 00:00:00',100,1000,1,0,0,'shielddisruptor','2012-09-10 01:01:21',0),(448,'aih',1,1,10,1,7168,1,-261.37,727.95,1285.21,500,'aihive',1,1,1,1,1,1,'0',0,'0000-00-00 00:00:00',0,'0000-00-00 00:00:00',100,1000,1,0,0,'shielddisruptor','2012-09-10 01:01:21',0),(449,'aih',1,1,10,1,7168,1,-356.704,-1338.97,-574.385,500,'aihive',1,1,1,1,1,1,'0',0,'0000-00-00 00:00:00',0,'0000-00-00 00:00:00',100,1000,1,0,0,'shielddisruptor','2012-09-10 01:07:00',0),(450,'human',1,1,10,1,7,998,0,0,0,0,'vendor',1,1,1,1,1,1,NULL,0,'0000-00-00 00:00:00',0,'0000-00-00 00:00:00',0,0,1,0,0,'none','0000-00-00 00:00:00',0);
/*!40000 ALTER TABLE `npc` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2012-09-11 13:58:07
