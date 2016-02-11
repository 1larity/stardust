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
-- Table structure for table `shipfittingbak`
--

DROP TABLE IF EXISTS `shipfittingbak`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `shipfittingbak` (
  `uid` bigint(20) NOT NULL,
  `shiptypeid` bigint(20) DEFAULT NULL,
  `defslot01` bigint(20) DEFAULT NULL,
  `defslot02` bigint(20) DEFAULT NULL,
  `defslot03` bigint(20) DEFAULT NULL,
  `defslot04` bigint(20) DEFAULT NULL,
  `defslot05` bigint(20) DEFAULT NULL,
  `offslot01` bigint(20) DEFAULT NULL,
  `offslot02` bigint(20) DEFAULT NULL,
  `offslot03` bigint(20) DEFAULT NULL,
  `offslot04` bigint(20) DEFAULT NULL,
  `offslot05` bigint(20) DEFAULT NULL,
  `augslot01` bigint(20) DEFAULT NULL,
  `augslot02` bigint(20) DEFAULT NULL,
  `augslot03` bigint(20) DEFAULT NULL,
  `augslot04` bigint(20) DEFAULT NULL,
  `augslot05` bigint(20) DEFAULT NULL,
  `charid` bigint(20) DEFAULT NULL,
  `inventoryid` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='storage for players ship equipment.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shipfittingbak`
--

LOCK TABLES `shipfittingbak` WRITE;
/*!40000 ALTER TABLE `shipfittingbak` DISABLE KEYS */;
INSERT INTO `shipfittingbak` VALUES (0,56,7,8,9,10,11,2,3,4,5,6,12,13,14,15,16,35,1),(0,57,64,11,11,11,11,6,6,6,6,6,16,16,16,16,16,34,53),(0,58,NULL,65,66,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,36,63),(0,72,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,35,67);
/*!40000 ALTER TABLE `shipfittingbak` ENABLE KEYS */;
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
