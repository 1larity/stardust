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
-- Table structure for table `avatardefs`
--

DROP TABLE IF EXISTS `avatardefs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `avatardefs` (
  `idavatardefs` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(45) NOT NULL DEFAULT 'Anonymous',
  `imagename` varchar(45) NOT NULL DEFAULT 'avatar01.jpg',
  `race` varchar(20) NOT NULL DEFAULT 'human',
  `sex` varchar(20) NOT NULL DEFAULT 'male',
  PRIMARY KEY (`idavatardefs`),
  UNIQUE KEY `idavatardefs_UNIQUE` (`idavatardefs`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1 COMMENT='Stores graphcal definitions for avatar images should add ava';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `avatardefs`
--

LOCK TABLES `avatardefs` WRITE;
/*!40000 ALTER TABLE `avatardefs` DISABLE KEYS */;
INSERT INTO `avatardefs` VALUES (1,'Anonymous','avatar00.jpg','human','male'),(2,'Grumpy Fanatic','avatar01.jpg','human','male'),(3,'Young Fanatic','avatar02.jpg','human','male'),(4,'Intense Fanatic','avatar03.jpg','human','female'),(5,'Elfin Fanatic','avatar04.jpg','human','female'),(6,'Green Orinian Fanatic','avatar05.jpg','orinian','male'),(7,'Blue Jelkek Fanatic','avatar06.jpg','jelkek','male'),(8,'Red Jelek Fanatic','avatar07.jpg','jelkek','female'),(9,'Purple Gulhurg Military','avatar08.jpg','gulhurg','male'),(10,'GreenGulhurg Military','avatar09.jpg','gulhurg','female');
/*!40000 ALTER TABLE `avatardefs` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2012-09-11 13:58:06
