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
-- Table structure for table `shipdefs`
--

DROP TABLE IF EXISTS `shipdefs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `shipdefs` (
  `uid` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'the uid of this ship definition',
  `itemid` bigint(20) NOT NULL COMMENT 'the item id of this ship',
  `jet1x` float DEFAULT '99999' COMMENT 'jet location 1 x coord',
  `jet1y` float DEFAULT '99999' COMMENT 'jet location 1 y co-ord',
  `jet1z` float DEFAULT '99999' COMMENT 'jetlocation 2 z co-ord',
  `jet2x` float DEFAULT '99999',
  `jet2y` float DEFAULT '99999',
  `jet2z` float DEFAULT '99999',
  `jet3x` float DEFAULT '99999',
  `jet3y` float DEFAULT '99999',
  `jet3z` float DEFAULT '99999',
  `jet4x` float DEFAULT '99999',
  `jet4y` float DEFAULT '99999',
  `jet4z` float DEFAULT '99999',
  `jet5x` float DEFAULT '99999',
  `jet5y` float DEFAULT '99999',
  `jet5z` float DEFAULT '99999',
  `maingunx` float DEFAULT '99999',
  `mainguny` float DEFAULT '99999',
  `maingunz` float DEFAULT '99999',
  `turret1x` float DEFAULT '99999',
  `turret1y` float DEFAULT '99999',
  `turret1z` float DEFAULT '99999',
  `turret2x` float DEFAULT '99999',
  `turret2y` float DEFAULT '99999',
  `turret2z` float DEFAULT '99999',
  `turret3x` float DEFAULT '99999',
  `turret3y` float DEFAULT '99999',
  `turret3z` float DEFAULT '99999',
  `turret4x` float DEFAULT '99999',
  `turret4y` float DEFAULT '99999',
  `turret4z` float DEFAULT '99999',
  `turret5x` float DEFAULT '99999',
  `turret5y` float DEFAULT '99999',
  `turret5z` float DEFAULT '99999',
  `model` varchar(45) DEFAULT NULL,
  `texture` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1 COMMENT='store the graphical elements for a ship';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shipdefs`
--

LOCK TABLES `shipdefs` WRITE;
/*!40000 ALTER TABLE `shipdefs` DISABLE KEYS */;
INSERT INTO `shipdefs` VALUES (3,71,1.1,-0.2,3,-1.1,-0.2,3,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,'stickleback.obj','destroyer01.png'),(4,72,1.1,-0.2,3,-1.1,-0.2,3,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,'salx.obj','desroyer02.png'),(5,57,0,-0.4,2,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,'squirrel.obj','squirrel.png'),(6,56,0,0.2,1,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,99999,'cynomys.obj','ship.png');
/*!40000 ALTER TABLE `shipdefs` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2012-09-11 13:57:53
