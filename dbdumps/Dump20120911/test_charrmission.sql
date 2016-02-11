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
-- Table structure for table `charrmission`
--

DROP TABLE IF EXISTS `charrmission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `charrmission` (
  `uid` bigint(20) NOT NULL AUTO_INCREMENT,
  `missionuid` bigint(20) DEFAULT NULL COMMENT 'the id of the missiondef',
  `charuid` bigint(20) DEFAULT NULL COMMENT 'the id of the character this belongs to',
  `accepteddate` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'the date the player accepted this mission',
  `completed` datetime DEFAULT NULL COMMENT 'the date the player completed the mission',
  `inprogress` int(11) DEFAULT '1' COMMENT 'flag to indicate if the mission is in progress',
  `repeat` int(11) DEFAULT '0' COMMENT 'if its a repeatable quest, this is the Nth repeat',
  PRIMARY KEY (`uid`),
  UNIQUE KEY `idcharrmission_UNIQUE` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='log of player missions accepted and completed';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `charrmission`
--

LOCK TABLES `charrmission` WRITE;
/*!40000 ALTER TABLE `charrmission` DISABLE KEYS */;
/*!40000 ALTER TABLE `charrmission` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2012-09-11 13:58:09