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
-- Table structure for table `news`
--

DROP TABLE IF EXISTS `news`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `news` (
  `UID` int(11) NOT NULL AUTO_INCREMENT,
  `news_text` varchar(500) NOT NULL DEFAULT 'No subject defined.',
  `news_title` varchar(40) NOT NULL DEFAULT 'No title defined.',
  `news_date` date DEFAULT NULL,
  `news_latest` tinyint(1) NOT NULL DEFAULT '0',
  UNIQUE KEY `UID` (`UID`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `news`
--

LOCK TABLES `news` WRITE;
/*!40000 ALTER TABLE `news` DISABLE KEYS */;
INSERT INTO `news` VALUES (1,'Welcome to the first release of Stardust, the project is still very much early in developement, so please bear with me if stuff crashes, or simply doesnt do anything. If you\'re brave enough to have come this far, thank you, and hopefully you will witness the birth of Stardust, welcome to the ride. if you have any feedback, please send it to digitale001@gmail.com','Stardust Alpha Live','2012-05-20',0),(6,'The official Stardust website is up, it\'s pretty basic at the moment, but be sure to check by for the latest info on Stardust, if you are currently playing the Android version, you can download a desktop version from https://sites.google.com/site/digitaletec/  (this link is also on Google Play) ','Stardust Website up','2012-06-23',0),(7,'You can now create your own personal Stardust account to play with. Basic character creation is also working, allowing you to set the name, race, sex and portrait for your characters.','Account and character creation working.','2012-06-25',0),(9,'Please note it may not be possible to log into Stardust from July 1 to July 3, the security system is being updated, this causes a lot of disruption to the server communications.Thanks for being patient, we will update the news once the work is complete.','Security Updates','2012-07-01',0),(11,'Big client update, get the new client! A very basic chat client, accessible through the pullout menu, now you can chat to other people in game. Multi-player synchronization! you can actually see other players flying in game and blow up their ships! The save account check-box now works, never enter your account details at log-in again!','It takes two to tango.','2012-07-03',0),(12,'All spaceships have been forcefully docked by Galactic LEO as a safety measure. Hostile forces have been seen in the vicinity of Telete III station, posing a risk to local ships, Galactic LEO strongly recommends all pilots dock at friendly space stations when not in command of their ships to prevent loss of life or assets. Telete III Station management are offering bounties for each Hive AI robot destroyed.','Sitting on the dock of the bay.','2012-08-07',0),(13,'Telete III Station Management thanks all pilots for assisting in supressing the Hive AI incursion, our shields are still under attack but hold steady. Pilots now receive salvage rights on all kills, any items recovered from combatant wrecks will be automatically placed in your station warehouse.','Telete III Saved by Brave Pilots!','2012-08-16',0),(14,'New Java game client available, please download the latest version from https://sites.google.com/site/digitaletec/. New loot system running, chat improvements','New Java client available','2012-08-23',0),(15,'Characters created prior to August 2012 have now all received a free in game gift to show appreciation of your brave attempts to play Stardust. At the moment you can only gaze upon the carefully wrapped package as it sits in your inventory, but once my patented box opening code is written, you will be able enjoy all of the boxxy goodness within the package.','Explorer Packages Released','2012-08-28',0),(16,'Yep, I just can\'t stop turning out code. The new client has some very rudimentary(not working) Space-station facilities, improved inventory screen, and almost 200 new items to fill your cargo with, so get slaughtering those evil Hive AIs!','Yet another client update!','2012-08-30',0),(17,'Mandatory client update, old clients are not compatible with the latest server build. Loads of neat updates!','Important Update','2012-09-06',1);
/*!40000 ALTER TABLE `news` ENABLE KEYS */;
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
