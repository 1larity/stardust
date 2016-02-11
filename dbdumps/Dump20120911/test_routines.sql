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
-- Temporary table structure for view `system_resources`
--

DROP TABLE IF EXISTS `system_resources`;
/*!50001 DROP VIEW IF EXISTS `system_resources`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `system_resources` (
  `x` int(11),
  `y` int(11),
  `z` int(11),
  `sysx` int(11),
  `sysy` int(11),
  `sysz` int(11),
  `modelscale` int(11),
  `activation_radius` int(11),
  `name` varchar(45),
  `structname` varchar(45)
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `getrandomfrsurname`
--

DROP TABLE IF EXISTS `getrandomfrsurname`;
/*!50001 DROP VIEW IF EXISTS `getrandomfrsurname`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `getrandomfrsurname` (
  `uid` bigint(20)
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `getrandomfrfemname`
--

DROP TABLE IF EXISTS `getrandomfrfemname`;
/*!50001 DROP VIEW IF EXISTS `getrandomfrfemname`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `getrandomfrfemname` (
  `uid` bigint(20)
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `servertime`
--

DROP TABLE IF EXISTS `servertime`;
/*!50001 DROP VIEW IF EXISTS `servertime`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `servertime` (
  `curtime` time
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `fittedfhips`
--

DROP TABLE IF EXISTS `fittedfhips`;
/*!50001 DROP VIEW IF EXISTS `fittedfhips`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `fittedfhips` (
  `slot_id` int(11),
  `characteruid` bigint(20),
  `item_ID` bigint(20) unsigned,
  `inventoryuid` bigint(20),
  `uid` bigint(20),
  `shiptypeid` bigint(20),
  `defslot01` bigint(20),
  `defslot02` bigint(20),
  `defslot03` bigint(20),
  `defslot04` bigint(20),
  `defslot05` bigint(20),
  `offslot01` bigint(20),
  `offslot02` bigint(20),
  `offslot03` bigint(20),
  `offslot04` bigint(20),
  `offslot05` bigint(20),
  `augslot01` bigint(20),
  `augslot02` bigint(20),
  `augslot03` bigint(20),
  `augslot04` bigint(20),
  `augslot05` bigint(20),
  `charid` bigint(20),
  `inventoryid` bigint(20)
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `getitemcatagory`
--

DROP TABLE IF EXISTS `getitemcatagory`;
/*!50001 DROP VIEW IF EXISTS `getitemcatagory`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `getitemcatagory` (
  `uid` bigint(20),
  `catname` varchar(40),
  `subcatname` varchar(40),
  `item` varchar(40),
  `basevalue` int(11),
  `value_deviation` int(11),
  `scarcity` int(11),
  `mass` int(11),
  `stacks` int(11),
  `description` text,
  `contraband` int(11),
  `recipe` int(11),
  `capacity` int(11),
  `effect` varchar(45),
  `level` int(11),
  `icon` varchar(45),
  `quality` int(11)
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `makefrenchman`
--

DROP TABLE IF EXISTS `makefrenchman`;
/*!50001 DROP VIEW IF EXISTS `makefrenchman`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `makefrenchman` (
  `uid` binary(0),
  `fr` varchar(2),
  `lx` int(11),
  `ly` int(11),
  `lz` int(11),
  `static` int(1),
  `firstname` varchar(20),
  `surname` varchar(20)
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `getrandfullfrmalename`
--

DROP TABLE IF EXISTS `getrandfullfrmalename`;
/*!50001 DROP VIEW IF EXISTS `getrandfullfrmalename`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `getrandfullfrmalename` (
  `firstname` varchar(20),
  `surname` varchar(20)
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `currentcharships`
--

DROP TABLE IF EXISTS `currentcharships`;
/*!50001 DROP VIEW IF EXISTS `currentcharships`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `currentcharships` (
  `uid` bigint(20),
  `firstname` varchar(40),
  `surname` varchar(20),
  `stamina` int(11),
  `intelligence` int(11),
  `social` int(11),
  `dexterity` int(11),
  `leadership` int(11),
  `recuperation` int(11),
  `exp` int(11),
  `slots` int(11),
  `player_account_UID` bigint(20),
  `x` int(11),
  `y` int(11),
  `z` int(11),
  `currentship` bigint(20),
  `hitpoints` int(11),
  `shield` int(11),
  `power` int(11),
  `cpu` int(11),
  `sysname` varchar(14),
  `item` varchar(40),
  `icon` varchar(45),
  `sysx` float,
  `sysy` float,
  `sysz` float,
  `status` int(11),
  `avatar` varchar(20),
  `credits` bigint(20)
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `getrandfullfrfemname`
--

DROP TABLE IF EXISTS `getrandfullfrfemname`;
/*!50001 DROP VIEW IF EXISTS `getrandfullfrfemname`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `getrandfullfrfemname` (
  `firstname` varchar(20),
  `surname` varchar(20)
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `actors`
--

DROP TABLE IF EXISTS `actors`;
/*!50001 DROP VIEW IF EXISTS `actors`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `actors` (
  `uid` bigint(21),
  `firstname` varchar(40),
  `surname` varchar(20),
  `stamina` int(11),
  `intelligence` int(11),
  `social` int(11),
  `dexterity` int(11),
  `leadership` int(11),
  `recuperation` int(11),
  `exp` varchar(45),
  `slots` bigint(11),
  `player_account_UID` bigint(20),
  `x` int(11),
  `y` int(11),
  `z` int(11),
  `sysname` varchar(14),
  `sysx` float,
  `sysy` float,
  `sysz` float,
  `hitpoints` int(11),
  `faction` varchar(45),
  `firstattackeruid` bigint(20),
  `firstattacktime` datetime,
  `lastattackeruid` bigint(20),
  `lastattacktime` datetime,
  `expvalue` int(11),
  `creditvalue` int(11),
  `status` int(11),
  `pitchangle` float,
  `yawangle` float,
  `shipname` varchar(45),
  `lastupdate` datetime,
  `velocity` float
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `structureswithdetails`
--

DROP TABLE IF EXISTS `structureswithdetails`;
/*!50001 DROP VIEW IF EXISTS `structureswithdetails`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `structureswithdetails` (
  `x` int(11),
  `y` int(11),
  `z` int(11),
  `sysx` int(11),
  `sysy` int(11),
  `sysz` int(11),
  `modelscale` int(11),
  `activation_radius` int(11),
  `name` varchar(45),
  `structname` varchar(45)
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `systems`
--

DROP TABLE IF EXISTS `systems`;
/*!50001 DROP VIEW IF EXISTS `systems`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `systems` (
  `sysname` varchar(14),
  `x` int(11),
  `y` int(11),
  `z` int(11),
  `ground` varchar(20),
  `level` int(11),
  `leftlink` tinyint(4),
  `downlink` tinyint(4)
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `frenchmalenames`
--

DROP TABLE IF EXISTS `frenchmalenames`;
/*!50001 DROP VIEW IF EXISTS `frenchmalenames`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `frenchmalenames` (
  `uid` bigint(20),
  `firstname` varchar(20),
  `race` varchar(3),
  `sex` varchar(1)
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `frenchfemnames`
--

DROP TABLE IF EXISTS `frenchfemnames`;
/*!50001 DROP VIEW IF EXISTS `frenchfemnames`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `frenchfemnames` (
  `uid` bigint(20),
  `firstname` varchar(20),
  `race` varchar(3),
  `sex` varchar(1)
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `characterswithoutships`
--

DROP TABLE IF EXISTS `characterswithoutships`;
/*!50001 DROP VIEW IF EXISTS `characterswithoutships`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `characterswithoutships` (
  `noship` bigint(20)
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `getrandomfrmalename`
--

DROP TABLE IF EXISTS `getrandomfrmalename`;
/*!50001 DROP VIEW IF EXISTS `getrandomfrmalename`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `getrandomfrmalename` (
  `uid` bigint(20)
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `getfittingdetails`
--

DROP TABLE IF EXISTS `getfittingdetails`;
/*!50001 DROP VIEW IF EXISTS `getfittingdetails`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `getfittingdetails` (
  `defslot1` varchar(8),
  `invuid` bigint(20),
  `itid` bigint(20) unsigned,
  `shid` bigint(20),
  `chid` bigint(20)
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `chatlist`
--

DROP TABLE IF EXISTS `chatlist`;
/*!50001 DROP VIEW IF EXISTS `chatlist`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `chatlist` (
  `firstname` varchar(40),
  `surname` varchar(20),
  `chattimestamp` timestamp,
  `chatmessage` text,
  `channelname` varchar(20),
  `chatchannel` bigint(20)
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `shipdetails`
--

DROP TABLE IF EXISTS `shipdetails`;
/*!50001 DROP VIEW IF EXISTS `shipdetails`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `shipdetails` (
  `itemid` bigint(20),
  `basehitpoints` int(11),
  `baseshieldpoints` int(11),
  `basepower` int(11),
  `basemaxspeed` int(11),
  `basecpu` int(11),
  `baseaccelration` int(11),
  `baseturnspeed` int(11),
  `defslots` int(11),
  `offslots` int(11),
  `augslots` int(11),
  `category` int(11),
  `subcat` int(11),
  `item` varchar(40),
  `basevalue` int(11),
  `mass` int(11),
  `stacks` int(11),
  `description` text,
  `capacity` int(11),
  `level` int(11),
  `icon` varchar(45),
  `quality` int(11)
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `frenchsurname`
--

DROP TABLE IF EXISTS `frenchsurname`;
/*!50001 DROP VIEW IF EXISTS `frenchsurname`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `frenchsurname` (
  `uid` bigint(20),
  `surname` varchar(20),
  `race` varchar(3)
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `playercharsinsystem`
--

DROP TABLE IF EXISTS `playercharsinsystem`;
/*!50001 DROP VIEW IF EXISTS `playercharsinsystem`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `playercharsinsystem` (
  `uid` bigint(20),
  `firstname` varchar(40),
  `surname` varchar(20),
  `stamina` int(11),
  `intelligence` int(11),
  `social` int(11),
  `dexterity` int(11),
  `leadership` int(11),
  `recuperation` int(11),
  `exp` int(11),
  `slots` int(11),
  `player_account_UID` bigint(20),
  `x` int(11),
  `y` int(11),
  `z` int(11),
  `sysname` varchar(14),
  `sysx` float,
  `sysy` float,
  `sysz` float,
  `hitpoints` int(11),
  `firstattackeruid` bigint(20),
  `firstattacktime` datetime,
  `lastattackeruid` bigint(20),
  `lastattacktime` datetime,
  `expvalue` int(11),
  `creditvalue` int(11),
  `status` int(11),
  `shipname` varchar(40),
  `yawangle` float,
  `pitchangle` float,
  `lastupdate` datetime,
  `velocity` float
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `privatechatlist`
--

DROP TABLE IF EXISTS `privatechatlist`;
/*!50001 DROP VIEW IF EXISTS `privatechatlist`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `privatechatlist` (
  `firstname` varchar(40),
  `surname` varchar(20),
  `chattimestamp` timestamp,
  `chatmessage` text,
  `channelname` varchar(8),
  `chatchannel` bigint(20)
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `getinventory`
--

DROP TABLE IF EXISTS `getinventory`;
/*!50001 DROP VIEW IF EXISTS `getinventory`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `getinventory` (
  `slot_id` int(11),
  `characteruid` bigint(20),
  `inventoryid` bigint(20),
  `uid` bigint(20),
  `category` varchar(40),
  `subcat` varchar(40),
  `item` varchar(40),
  `basevalue` int(11),
  `value_deviation` int(11),
  `scarcity` int(11),
  `mass` int(11),
  `stacks` int(11),
  `description` text,
  `contraband` int(11),
  `recipe` int(11),
  `capacity` int(11),
  `effect` varchar(45),
  `level` int(11),
  `icon` varchar(45),
  `quality` int(11)
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Final view structure for view `system_resources`
--

/*!50001 DROP TABLE IF EXISTS `system_resources`*/;
/*!50001 DROP VIEW IF EXISTS `system_resources`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `system_resources` AS select `structures`.`x` AS `x`,`structures`.`y` AS `y`,`structures`.`z` AS `z`,`structures`.`sysx` AS `sysx`,`structures`.`sysy` AS `sysy`,`structures`.`sysz` AS `sysz`,`structures`.`modelscale` AS `modelscale`,`structures`.`activation_radius` AS `activation_radius`,`structtypes`.`name` AS `name`,`structnames`.`structname` AS `structname` from ((`structures` join `structtypes`) join `structnames`) where ((`structures`.`structtypes_uid` = `structtypes`.`uid`) and (`structures`.`name_uid` = `structnames`.`uidstructnames`)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `getrandomfrsurname`
--

/*!50001 DROP TABLE IF EXISTS `getrandomfrsurname`*/;
/*!50001 DROP VIEW IF EXISTS `getrandomfrsurname`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `getrandomfrsurname` AS select `frenchsurname`.`uid` AS `uid` from `frenchsurname` order by rand() limit 1 */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `getrandomfrfemname`
--

/*!50001 DROP TABLE IF EXISTS `getrandomfrfemname`*/;
/*!50001 DROP VIEW IF EXISTS `getrandomfrfemname`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `getrandomfrfemname` AS select `frenchfemnames`.`uid` AS `uid` from `frenchfemnames` order by rand() limit 1 */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `servertime`
--

/*!50001 DROP TABLE IF EXISTS `servertime`*/;
/*!50001 DROP VIEW IF EXISTS `servertime`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `servertime` AS select curtime() AS `curtime` from `landscape` limit 1 */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `fittedfhips`
--

/*!50001 DROP TABLE IF EXISTS `fittedfhips`*/;
/*!50001 DROP VIEW IF EXISTS `fittedfhips`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `fittedfhips` AS select `inventory`.`slot_id` AS `slot_id`,`inventory`.`characteruid` AS `characteruid`,`inventory`.`item_ID` AS `item_ID`,`inventory`.`uid` AS `inventoryuid`,`shipfitting`.`uid` AS `uid`,`shipfitting`.`shiptypeid` AS `shiptypeid`,`shipfitting`.`defslot01` AS `defslot01`,`shipfitting`.`defslot02` AS `defslot02`,`shipfitting`.`defslot03` AS `defslot03`,`shipfitting`.`defslot04` AS `defslot04`,`shipfitting`.`defslot05` AS `defslot05`,`shipfitting`.`offslot01` AS `offslot01`,`shipfitting`.`offslot02` AS `offslot02`,`shipfitting`.`offslot03` AS `offslot03`,`shipfitting`.`offslot04` AS `offslot04`,`shipfitting`.`offslot05` AS `offslot05`,`shipfitting`.`augslot01` AS `augslot01`,`shipfitting`.`augslot02` AS `augslot02`,`shipfitting`.`augslot03` AS `augslot03`,`shipfitting`.`augslot04` AS `augslot04`,`shipfitting`.`augslot05` AS `augslot05`,`shipfitting`.`charid` AS `charid`,`shipfitting`.`inventoryid` AS `inventoryid` from (`inventory` join `shipfitting`) where (`inventory`.`uid` = `shipfitting`.`inventoryid`) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `getitemcatagory`
--

/*!50001 DROP TABLE IF EXISTS `getitemcatagory`*/;
/*!50001 DROP VIEW IF EXISTS `getitemcatagory`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `getitemcatagory` AS select `item`.`uid` AS `uid`,`catagory`.`catname` AS `catname`,`subcategory`.`subcatname` AS `subcatname`,`item`.`item` AS `item`,`item`.`basevalue` AS `basevalue`,`item`.`value_deviation` AS `value_deviation`,`item`.`scarcity` AS `scarcity`,`item`.`mass` AS `mass`,`item`.`stacks` AS `stacks`,`item`.`description` AS `description`,`item`.`contraband` AS `contraband`,`item`.`recipe` AS `recipe`,`item`.`capacity` AS `capacity`,`item`.`effect` AS `effect`,`item`.`level` AS `level`,`item`.`icon` AS `icon`,`item`.`quality` AS `quality` from ((`item` join `catagory`) join `subcategory`) where ((`item`.`category` = `catagory`.`uid`) and (`item`.`subcat` = `subcategory`.`uid`)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `makefrenchman`
--

/*!50001 DROP TABLE IF EXISTS `makefrenchman`*/;
/*!50001 DROP VIEW IF EXISTS `makefrenchman`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `makefrenchman` AS select NULL AS `uid`,'fr' AS `fr`,`landscape`.`x` AS `lx`,`landscape`.`y` AS `ly`,`landscape`.`z` AS `lz`,0 AS `static`,`getrandfullfrmalename`.`firstname` AS `firstname`,`getrandfullfrmalename`.`surname` AS `surname` from (`landscape` join `getrandfullfrmalename`) order by rand() limit 1 */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `getrandfullfrmalename`
--

/*!50001 DROP TABLE IF EXISTS `getrandfullfrmalename`*/;
/*!50001 DROP VIEW IF EXISTS `getrandfullfrmalename`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `getrandfullfrmalename` AS select `firstnames`.`firstname` AS `firstname`,`surnames`.`surname` AS `surname` from (`surnames` join `firstnames`) where ((`surnames`.`uid` = (select `getrandomfrsurname`.`uid` from `getrandomfrsurname`)) and (`firstnames`.`uid` = (select `getrandomfrmalename`.`uid` from `getrandomfrmalename`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `currentcharships`
--

/*!50001 DROP TABLE IF EXISTS `currentcharships`*/;
/*!50001 DROP VIEW IF EXISTS `currentcharships`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `currentcharships` AS select distinct `player_char`.`uid` AS `uid`,`player_char`.`firstname` AS `firstname`,`player_char`.`surname` AS `surname`,`player_char`.`stamina` AS `stamina`,`player_char`.`intelligence` AS `intelligence`,`player_char`.`social` AS `social`,`player_char`.`dexterity` AS `dexterity`,`player_char`.`leadership` AS `leadership`,`player_char`.`recuperation` AS `recuperation`,`player_char`.`exp` AS `exp`,`player_char`.`slots` AS `slots`,`player_char`.`player_account_UID` AS `player_account_UID`,`player_char`.`x` AS `x`,`player_char`.`y` AS `y`,`player_char`.`z` AS `z`,`player_char`.`currentship` AS `currentship`,`player_char`.`hitpoints` AS `hitpoints`,`player_char`.`shield` AS `shield`,`player_char`.`power` AS `power`,`player_char`.`cpu` AS `cpu`,`systems`.`sysname` AS `sysname`,`item`.`item` AS `item`,`item`.`icon` AS `icon`,`player_char`.`sysx` AS `sysx`,`player_char`.`sysy` AS `sysy`,`player_char`.`sysz` AS `sysz`,`player_char`.`status` AS `status`,`player_char`.`avatar` AS `avatar`,`player_char`.`credits` AS `credits` from (((`player_char` join `systems`) join `inventory`) join `item`) where ((`player_char`.`x` = `systems`.`x`) and (`player_char`.`y` = `systems`.`y`) and (`player_char`.`z` = `systems`.`z`) and (`player_char`.`uid` = `inventory`.`characteruid`) and (`item`.`uid` = `inventory`.`item_ID`) and (`player_char`.`currentship` = `inventory`.`uid`)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `getrandfullfrfemname`
--

/*!50001 DROP TABLE IF EXISTS `getrandfullfrfemname`*/;
/*!50001 DROP VIEW IF EXISTS `getrandfullfrfemname`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `getrandfullfrfemname` AS select `firstnames`.`firstname` AS `firstname`,`surnames`.`surname` AS `surname` from (`surnames` join `firstnames`) where ((`surnames`.`uid` = (select `getrandomfrsurname`.`uid` from `getrandomfrsurname`)) and (`firstnames`.`uid` = (select `getrandomfrfemname`.`uid` from `getrandomfrfemname`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `actors`
--

/*!50001 DROP TABLE IF EXISTS `actors`*/;
/*!50001 DROP VIEW IF EXISTS `actors`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `actors` AS select -(`npc`.`UID`) AS `uid`,`firstnames`.`firstname` AS `firstname`,`surnames`.`surname` AS `surname`,`npc`.`stamina` AS `stamina`,`npc`.`intelligence` AS `intelligence`,`npc`.`social` AS `social`,`npc`.`dexterity` AS `dexterity`,`npc`.`leadership` AS `leadership`,`npc`.`recuperation` AS `recuperation`,`npc`.`exp` AS `exp`,40 AS `slots`,1 AS `player_account_UID`,`npc`.`x` AS `x`,`npc`.`y` AS `y`,`npc`.`z` AS `z`,'na' AS `sysname`,`npc`.`sysx` AS `sysx`,`npc`.`sysy` AS `sysy`,`npc`.`sysz` AS `sysz`,`npc`.`hitpoints` AS `hitpoints`,`npc`.`faction` AS `faction`,`npc`.`firstattackeruid` AS `firstattackeruid`,`npc`.`firstattacktime` AS `firstattacktime`,`npc`.`lastattackeruid` AS `lastattackeruid`,`npc`.`lastattacktime` AS `lastattacktime`,`npc`.`expvalue` AS `expvalue`,`npc`.`creditvalue` AS `creditvalue`,`npc`.`status` AS `status`,`npc`.`pitchangle` AS `pitchangle`,`npc`.`yawangle` AS `yawangle`,`npc`.`shipname` AS `shipname`,`npc`.`lastupdate` AS `lastupdate`,`npc`.`velocity` AS `velocity` from ((`npc` join `firstnames` on((`npc`.`firstnames_uid` = `firstnames`.`uid`))) join `surnames` on((`npc`.`surnames_uid` = `surnames`.`uid`))) union select `playercharsinsystem`.`uid` AS `uid`,`playercharsinsystem`.`firstname` AS `firstname`,`playercharsinsystem`.`surname` AS `surname`,`playercharsinsystem`.`stamina` AS `stamina`,`playercharsinsystem`.`intelligence` AS `intelligence`,`playercharsinsystem`.`social` AS `social`,`playercharsinsystem`.`dexterity` AS `dexterity`,`playercharsinsystem`.`leadership` AS `leadership`,`playercharsinsystem`.`recuperation` AS `recuperation`,`playercharsinsystem`.`exp` AS `exp`,`playercharsinsystem`.`slots` AS `slots`,`playercharsinsystem`.`player_account_UID` AS `player_account_UID`,`playercharsinsystem`.`x` AS `x`,`playercharsinsystem`.`y` AS `y`,`playercharsinsystem`.`z` AS `z`,`playercharsinsystem`.`sysname` AS `sysname`,`playercharsinsystem`.`sysx` AS `sysx`,`playercharsinsystem`.`sysy` AS `sysy`,`playercharsinsystem`.`sysz` AS `sysz`,`playercharsinsystem`.`hitpoints` AS `hitpoints`,'player' AS `faction`,`playercharsinsystem`.`firstattackeruid` AS `firstattackeruid`,`playercharsinsystem`.`firstattacktime` AS `firstattacktime`,`playercharsinsystem`.`lastattackeruid` AS `lastattackeruid`,`playercharsinsystem`.`lastattacktime` AS `lastattacktime`,`playercharsinsystem`.`expvalue` AS `expvalue`,`playercharsinsystem`.`creditvalue` AS `creditvalue`,`playercharsinsystem`.`status` AS `status`,`playercharsinsystem`.`pitchangle` AS `pitchangle`,`playercharsinsystem`.`yawangle` AS `yawangle`,`playercharsinsystem`.`shipname` AS `shipname`,`playercharsinsystem`.`lastupdate` AS `lastupdate`,`playercharsinsystem`.`velocity` AS `velocity` from `playercharsinsystem` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `structureswithdetails`
--

/*!50001 DROP TABLE IF EXISTS `structureswithdetails`*/;
/*!50001 DROP VIEW IF EXISTS `structureswithdetails`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `structureswithdetails` AS select `structures`.`x` AS `x`,`structures`.`y` AS `y`,`structures`.`z` AS `z`,`structures`.`sysx` AS `sysx`,`structures`.`sysy` AS `sysy`,`structures`.`sysz` AS `sysz`,`structures`.`modelscale` AS `modelscale`,`structures`.`activation_radius` AS `activation_radius`,`structtypes`.`name` AS `name`,`structnames`.`structname` AS `structname` from ((`structures` join `structtypes`) join `structnames`) where ((`structures`.`structtypes_uid` = `structtypes`.`uid`) and (`structures`.`name_uid` = `structnames`.`uidstructnames`)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `systems`
--

/*!50001 DROP TABLE IF EXISTS `systems`*/;
/*!50001 DROP VIEW IF EXISTS `systems`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `systems` AS select `systemnames`.`sysname` AS `sysname`,`landscape`.`x` AS `x`,`landscape`.`y` AS `y`,`landscape`.`z` AS `z`,`landscape`.`ground` AS `ground`,`landscape`.`level` AS `level`,`landscape`.`leftlink` AS `leftlink`,`landscape`.`downlink` AS `downlink` from (`systemnames` join `landscape`) where ((`systemnames`.`x` = `landscape`.`x`) and (`systemnames`.`y` = `landscape`.`y`) and (`systemnames`.`z` = `landscape`.`z`)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `frenchmalenames`
--

/*!50001 DROP TABLE IF EXISTS `frenchmalenames`*/;
/*!50001 DROP VIEW IF EXISTS `frenchmalenames`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `frenchmalenames` AS select `firstnames`.`uid` AS `uid`,`firstnames`.`firstname` AS `firstname`,`firstnames`.`race` AS `race`,`firstnames`.`sex` AS `sex` from `firstnames` where ((`firstnames`.`race` = 'fr') and (`firstnames`.`sex` = 'm')) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `frenchfemnames`
--

/*!50001 DROP TABLE IF EXISTS `frenchfemnames`*/;
/*!50001 DROP VIEW IF EXISTS `frenchfemnames`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `frenchfemnames` AS select `firstnames`.`uid` AS `uid`,`firstnames`.`firstname` AS `firstname`,`firstnames`.`race` AS `race`,`firstnames`.`sex` AS `sex` from `firstnames` where ((`firstnames`.`race` = 'fr') and (`firstnames`.`sex` = 'f')) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `characterswithoutships`
--

/*!50001 DROP TABLE IF EXISTS `characterswithoutships`*/;
/*!50001 DROP VIEW IF EXISTS `characterswithoutships`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `characterswithoutships` AS select `player_char`.`uid` AS `noship` from (`player_char` left join `shipfitting` on(((`player_char`.`uid` = `shipfitting`.`charid`) and (`shipfitting`.`shiptypeid` < 1)))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `getrandomfrmalename`
--

/*!50001 DROP TABLE IF EXISTS `getrandomfrmalename`*/;
/*!50001 DROP VIEW IF EXISTS `getrandomfrmalename`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `getrandomfrmalename` AS select `frenchmalenames`.`uid` AS `uid` from `frenchmalenames` order by rand() limit 1 */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `getfittingdetails`
--

/*!50001 DROP TABLE IF EXISTS `getfittingdetails`*/;
/*!50001 DROP VIEW IF EXISTS `getfittingdetails`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `getfittingdetails` AS select 'defslot1' AS `defslot1`,`inventory`.`uid` AS `invuid`,`inventory`.`item_ID` AS `itid`,`shipfitting`.`uid` AS `shid`,`shipfitting`.`charid` AS `chid` from (`shipfitting` left join `inventory` on((`shipfitting`.`defslot01` = `inventory`.`uid`))) union select 'defslot2' AS `defslot2`,`inventory`.`uid` AS `invuid`,`inventory`.`item_ID` AS `itid`,`shipfitting`.`uid` AS `shid`,`shipfitting`.`charid` AS `chid` from (`shipfitting` left join `inventory` on((`shipfitting`.`defslot02` = `inventory`.`uid`))) union select 'defslot3' AS `defslot3`,`inventory`.`uid` AS `invuid`,`inventory`.`item_ID` AS `itid`,`shipfitting`.`uid` AS `shid`,`shipfitting`.`charid` AS `chid` from (`shipfitting` left join `inventory` on((`shipfitting`.`defslot03` = `inventory`.`uid`))) union select 'defslot4' AS `defslot4`,`inventory`.`uid` AS `invuid`,`inventory`.`item_ID` AS `itid`,`shipfitting`.`uid` AS `shid`,`shipfitting`.`charid` AS `chid` from (`shipfitting` left join `inventory` on((`shipfitting`.`defslot04` = `inventory`.`uid`))) union select 'defslot5' AS `defslot5`,`inventory`.`uid` AS `invuid`,`inventory`.`item_ID` AS `itid`,`shipfitting`.`uid` AS `shid`,`shipfitting`.`charid` AS `chid` from (`shipfitting` left join `inventory` on((`shipfitting`.`defslot05` = `inventory`.`uid`))) union select 'offslot1' AS `offslot1`,`inventory`.`uid` AS `invuid`,`inventory`.`item_ID` AS `itid`,`shipfitting`.`uid` AS `shid`,`shipfitting`.`charid` AS `chid` from (`shipfitting` left join `inventory` on((`shipfitting`.`offslot01` = `inventory`.`uid`))) union select 'offslot2' AS `offslot2`,`inventory`.`uid` AS `invuid`,`inventory`.`item_ID` AS `itid`,`shipfitting`.`uid` AS `shid`,`shipfitting`.`charid` AS `chid` from (`shipfitting` left join `inventory` on((`shipfitting`.`offslot02` = `inventory`.`uid`))) union select 'offslot3' AS `offslot3`,`inventory`.`uid` AS `invuid`,`inventory`.`item_ID` AS `itid`,`shipfitting`.`uid` AS `shid`,`shipfitting`.`charid` AS `chid` from (`shipfitting` left join `inventory` on((`shipfitting`.`offslot03` = `inventory`.`uid`))) union select 'offslot4' AS `offslot4`,`inventory`.`uid` AS `invuid`,`inventory`.`item_ID` AS `itid`,`shipfitting`.`uid` AS `shid`,`shipfitting`.`charid` AS `chid` from (`shipfitting` left join `inventory` on((`shipfitting`.`offslot04` = `inventory`.`uid`))) union select 'offslot5' AS `offslot5`,`inventory`.`uid` AS `invuid`,`inventory`.`item_ID` AS `itid`,`shipfitting`.`uid` AS `shid`,`shipfitting`.`charid` AS `chid` from (`shipfitting` left join `inventory` on((`shipfitting`.`offslot05` = `inventory`.`uid`))) union select 'augslot1' AS `augslot1`,`inventory`.`uid` AS `invuid`,`inventory`.`item_ID` AS `itid`,`shipfitting`.`uid` AS `shid`,`shipfitting`.`charid` AS `chid` from (`shipfitting` left join `inventory` on((`shipfitting`.`augslot01` = `inventory`.`uid`))) union select 'augslot2' AS `augslot2`,`inventory`.`uid` AS `invuid`,`inventory`.`item_ID` AS `itid`,`shipfitting`.`uid` AS `shid`,`shipfitting`.`charid` AS `chid` from (`shipfitting` left join `inventory` on((`shipfitting`.`augslot02` = `inventory`.`uid`))) union select 'augslot3' AS `augslot3`,`inventory`.`uid` AS `invuid`,`inventory`.`item_ID` AS `itid`,`shipfitting`.`uid` AS `shid`,`shipfitting`.`charid` AS `chid` from (`shipfitting` left join `inventory` on((`shipfitting`.`augslot03` = `inventory`.`uid`))) union select 'augslot4' AS `augslot4`,`inventory`.`uid` AS `invuid`,`inventory`.`item_ID` AS `itid`,`shipfitting`.`uid` AS `shid`,`shipfitting`.`charid` AS `chid` from (`shipfitting` left join `inventory` on((`shipfitting`.`augslot04` = `inventory`.`uid`))) union select 'augslot5' AS `augslot5`,`inventory`.`uid` AS `invuid`,`inventory`.`item_ID` AS `itid`,`shipfitting`.`uid` AS `shid`,`shipfitting`.`charid` AS `chid` from (`shipfitting` left join `inventory` on((`shipfitting`.`augslot05` = `inventory`.`uid`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `chatlist`
--

/*!50001 DROP TABLE IF EXISTS `chatlist`*/;
/*!50001 DROP VIEW IF EXISTS `chatlist`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `chatlist` AS select `player_char`.`firstname` AS `firstname`,`player_char`.`surname` AS `surname`,`chatmessages`.`chattimestamp` AS `chattimestamp`,`chatmessages`.`chatmessage` AS `chatmessage`,`chatchannels`.`channelname` AS `channelname`,`chatmessages`.`chatchannel` AS `chatchannel` from ((`chatchannels` join `chatmessages`) join `player_char`) where ((`player_char`.`uid` = `chatmessages`.`senderid`) and (`chatchannels`.`idchatchannels` = `chatmessages`.`chatchannel`)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `shipdetails`
--

/*!50001 DROP TABLE IF EXISTS `shipdetails`*/;
/*!50001 DROP VIEW IF EXISTS `shipdetails`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `shipdetails` AS select `shipstats`.`itemid` AS `itemid`,`shipstats`.`basehitpoints` AS `basehitpoints`,`shipstats`.`baseshieldpoints` AS `baseshieldpoints`,`shipstats`.`basepower` AS `basepower`,`shipstats`.`basemaxspeed` AS `basemaxspeed`,`shipstats`.`basecpu` AS `basecpu`,`shipstats`.`baseaccelration` AS `baseaccelration`,`shipstats`.`baseturnspeed` AS `baseturnspeed`,`shipstats`.`defslots` AS `defslots`,`shipstats`.`offslots` AS `offslots`,`shipstats`.`augslots` AS `augslots`,`item`.`category` AS `category`,`item`.`subcat` AS `subcat`,`item`.`item` AS `item`,`item`.`basevalue` AS `basevalue`,`item`.`mass` AS `mass`,`item`.`stacks` AS `stacks`,`item`.`description` AS `description`,`item`.`capacity` AS `capacity`,`item`.`level` AS `level`,`item`.`icon` AS `icon`,`item`.`quality` AS `quality` from (`shipstats` join `item`) where (`shipstats`.`itemid` = `item`.`uid`) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `frenchsurname`
--

/*!50001 DROP TABLE IF EXISTS `frenchsurname`*/;
/*!50001 DROP VIEW IF EXISTS `frenchsurname`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `frenchsurname` AS select `surnames`.`uid` AS `uid`,`surnames`.`surname` AS `surname`,`surnames`.`race` AS `race` from `surnames` where (`surnames`.`race` = 'fr') */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `playercharsinsystem`
--

/*!50001 DROP TABLE IF EXISTS `playercharsinsystem`*/;
/*!50001 DROP VIEW IF EXISTS `playercharsinsystem`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `playercharsinsystem` AS select `player_char`.`uid` AS `uid`,`player_char`.`firstname` AS `firstname`,`player_char`.`surname` AS `surname`,`player_char`.`stamina` AS `stamina`,`player_char`.`intelligence` AS `intelligence`,`player_char`.`social` AS `social`,`player_char`.`dexterity` AS `dexterity`,`player_char`.`leadership` AS `leadership`,`player_char`.`recuperation` AS `recuperation`,`player_char`.`exp` AS `exp`,`player_char`.`slots` AS `slots`,`player_char`.`player_account_UID` AS `player_account_UID`,`player_char`.`x` AS `x`,`player_char`.`y` AS `y`,`player_char`.`z` AS `z`,`systems`.`sysname` AS `sysname`,`player_char`.`sysx` AS `sysx`,`player_char`.`sysy` AS `sysy`,`player_char`.`sysz` AS `sysz`,`player_char`.`hitpoints` AS `hitpoints`,`player_char`.`firstattackeruid` AS `firstattackeruid`,`player_char`.`firstattacktime` AS `firstattacktime`,`player_char`.`lastattackeruid` AS `lastattackeruid`,`player_char`.`lastattacktime` AS `lastattacktime`,`player_char`.`expvalue` AS `expvalue`,`player_char`.`creditvalue` AS `creditvalue`,`player_char`.`status` AS `status`,`item`.`item` AS `shipname`,`player_char`.`yawangle` AS `yawangle`,`player_char`.`pitchangle` AS `pitchangle`,`player_char`.`lastupdate` AS `lastupdate`,`player_char`.`velocity` AS `velocity` from (((`player_char` join `systems`) join `inventory`) join `item`) where ((`player_char`.`x` = `systems`.`x`) and (`player_char`.`y` = `systems`.`y`) and (`player_char`.`z` = `systems`.`z`) and (`player_char`.`uid` = `inventory`.`characteruid`) and (`item`.`uid` = `inventory`.`item_ID`) and (`player_char`.`currentship` = `inventory`.`uid`)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `privatechatlist`
--

/*!50001 DROP TABLE IF EXISTS `privatechatlist`*/;
/*!50001 DROP VIEW IF EXISTS `privatechatlist`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `privatechatlist` AS select `player_char`.`firstname` AS `firstname`,`player_char`.`surname` AS `surname`,`chatmessages`.`chattimestamp` AS `chattimestamp`,`chatmessages`.`chatmessage` AS `chatmessage`,'personal' AS `channelname`,`chatmessages`.`chatchannel` AS `chatchannel` from (`chatmessages` join `player_char`) where (`player_char`.`uid` = `chatmessages`.`senderid`) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `getinventory`
--

/*!50001 DROP TABLE IF EXISTS `getinventory`*/;
/*!50001 DROP VIEW IF EXISTS `getinventory`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `getinventory` AS select `inventory`.`slot_id` AS `slot_id`,`inventory`.`characteruid` AS `characteruid`,`inventory`.`uid` AS `inventoryid`,`item`.`uid` AS `uid`,`catagory`.`catname` AS `category`,`subcategory`.`subcatname` AS `subcat`,`item`.`item` AS `item`,`item`.`basevalue` AS `basevalue`,`item`.`value_deviation` AS `value_deviation`,`item`.`scarcity` AS `scarcity`,`item`.`mass` AS `mass`,`item`.`stacks` AS `stacks`,`item`.`description` AS `description`,`item`.`contraband` AS `contraband`,`item`.`recipe` AS `recipe`,`item`.`capacity` AS `capacity`,`item`.`effect` AS `effect`,`item`.`level` AS `level`,`item`.`icon` AS `icon`,`item`.`quality` AS `quality` from (((`item` join `inventory`) join `catagory`) join `subcategory`) where ((`item`.`uid` = `inventory`.`item_ID`) and (`item`.`category` = `catagory`.`uid`) and (`item`.`subcat` = `subcategory`.`uid`)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2012-09-11 13:58:15
