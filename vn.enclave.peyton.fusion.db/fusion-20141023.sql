CREATE DATABASE  IF NOT EXISTS `fusion` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `fusion`;
-- MySQL dump 10.13  Distrib 5.5.40, for debian-linux-gnu (i686)
--
-- Host: localhost    Database: fusion
-- ------------------------------------------------------
-- Server version	5.5.40-0ubuntu0.12.04.1

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
-- Table structure for table `device_templates`
--

DROP TABLE IF EXISTS `device_templates`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `device_templates` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idType` int(11) NOT NULL,
  `idIcon` int(11) NOT NULL,
  `name` varchar(45) NOT NULL,
  `lastModified` datetime NOT NULL,
  `manufacturer` varchar(45) NOT NULL,
  `modelNumber` varchar(45) NOT NULL,
  `version` varchar(45) NOT NULL,
  `deviceDriver` varchar(45) NOT NULL,
  `notes` varchar(45) NOT NULL,
  `certified` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_new_table_1_idx` (`idType`),
  KEY `fk_new_table_2_idx` (`idIcon`),
  CONSTRAINT `fk_device_templates_icons` FOREIGN KEY (`idIcon`) REFERENCES `icons` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_device_templates_types` FOREIGN KEY (`idType`) REFERENCES `device_types` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `device_templates`
--

LOCK TABLES `device_templates` WRITE;
/*!40000 ALTER TABLE `device_templates` DISABLE KEYS */;
INSERT INTO `device_templates` VALUES (1,2,1,'Axis - None-PTZ Models','2014-08-12 23:28:00','Axis','Non-PTZ Models','2.0.1','Cameras','','\0'),(2,2,1,'Axis - PTZ Models','2014-08-06 18:03:00','Axis','PTZ Models','2.0.1','Cameras','','\0'),(3,2,2,'Clare Controls - B Series Models','2014-08-04 18:13:00','Clare Controls','B Series ','2.0.1','Clare Controls','','\0');
/*!40000 ALTER TABLE `device_templates` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `device_types`
--

DROP TABLE IF EXISTS `device_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `device_types` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idModule` int(11) NOT NULL,
  `idIcon` int(11) NOT NULL,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_device_types_icons_idx` (`idIcon`),
  KEY `fk_device_types_modules` (`idModule`),
  CONSTRAINT `fk_device_types_modules` FOREIGN KEY (`idModule`) REFERENCES `modules` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_device_types_icons` FOREIGN KEY (`idIcon`) REFERENCES `icons` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `device_types`
--

LOCK TABLES `device_types` WRITE;
/*!40000 ALTER TABLE `device_types` DISABLE KEYS */;
INSERT INTO `device_types` VALUES (1,1,1,'DVR/NVR'),(2,1,1,'IP Cameras');
/*!40000 ALTER TABLE `device_types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `devices`
--

DROP TABLE IF EXISTS `devices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `devices` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idVersion` int(11) NOT NULL,
  `name` varchar(45) CHARACTER SET big5 NOT NULL,
  `appModule` varchar(45) NOT NULL,
  `deviceType` varchar(45) NOT NULL,
  `physicalLocation` varchar(45) NOT NULL,
  `manufacture` varchar(45) CHARACTER SET big5 COLLATE big5_bin NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_devices_versions_idx` (`idVersion`),
  CONSTRAINT `fk_devices_versions` FOREIGN KEY (`idVersion`) REFERENCES `versions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `devices`
--

LOCK TABLES `devices` WRITE;
/*!40000 ALTER TABLE `devices` DISABLE KEYS */;
INSERT INTO `devices` VALUES (1,5,'Clare Controls - PTZ Models (IP) (device)','CCTV','IP Cameras','','Clare Controls'),(2,5,'Clare Vision 8 Channel NVR (device)','CCTV','DVR/NVR','','Clare Controls'),(3,5,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic'),(4,5,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic'),(5,5,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic'),(6,7,'Clare Controls - PTZ Models (IP) (device)','CCTV','IP Cameras','','Clare Controls'),(7,7,'Clare Vision 8 Channel NVR (device)','CCTV','DVR/NVR','','Clare Controls'),(8,7,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic'),(9,7,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic'),(10,7,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic'),(16,19,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic'),(17,19,'Clare Controls - PTZ Models (IP) (device)','CCTV','IP Cameras','','Clare Controls'),(18,19,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic'),(19,19,'Clare Vision 8 Channel NVR (device)','CCTV','DVR/NVR','','Clare Controls'),(20,19,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic'),(21,20,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic'),(22,20,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic'),(23,20,'Clare Controls - PTZ Models (IP) (device)','CCTV','IP Cameras','','Clare Controls'),(24,20,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic'),(25,20,'Clare Vision 8 Channel NVR (device)','CCTV','DVR/NVR','','Clare Controls'),(26,21,'Clare Controls - PTZ Models (IP) (device)','CCTV','IP Cameras','','Clare Controls'),(27,21,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic'),(28,21,'Clare Vision 8 Channel NVR (device)','CCTV','DVR/NVR','','Clare Controls'),(29,21,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic'),(30,21,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic'),(31,23,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic'),(32,23,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic'),(33,23,'Clare Controls - PTZ Models (IP) (device)','CCTV','IP Cameras','','Clare Controls'),(34,23,'Clare Vision 8 Channel NVR (device)','CCTV','DVR/NVR','','Clare Controls'),(35,23,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic'),(46,41,'Clare Vision 8 Channel NVR (device)','CCTV','DVR/NVR','','Clare Controls'),(47,41,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic'),(48,41,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic'),(49,41,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic'),(50,41,'Clare Controls - PTZ Models (IP) (device)','CCTV','IP Cameras','','Clare Controls'),(51,55,'Clare Controls - PTZ Models (IP) (device)','CCTV','IP Cameras','','Clare Controls'),(52,55,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic'),(53,55,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic'),(54,55,'Clare Vision 8 Channel NVR (device)','CCTV','DVR/NVR','','Clare Controls'),(55,55,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic'),(56,57,'Clare Controls - PTZ Models (IP) (device)','CCTV','IP Cameras','','Clare Controls'),(57,57,'Clare Vision 8 Channel NVR (device)','CCTV','DVR/NVR','','Clare Controls'),(58,57,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic'),(59,57,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic'),(60,57,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic'),(61,58,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic'),(62,58,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic'),(63,58,'Clare Vision 8 Channel NVR (device)','CCTV','DVR/NVR','','Clare Controls'),(64,58,'Clare Controls - PTZ Models (IP) (device)','CCTV','IP Cameras','','Clare Controls'),(65,58,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic');
/*!40000 ALTER TABLE `devices` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `icons`
--

DROP TABLE IF EXISTS `icons`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `icons` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pluginId` varchar(45) NOT NULL,
  `imageFilePath` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `icons`
--

LOCK TABLES `icons` WRITE;
/*!40000 ALTER TABLE `icons` DISABLE KEYS */;
INSERT INTO `icons` VALUES (1,'vn.enclave.peyton.fusion','icons/cctv.png'),(2,'vn.enclave.peyton.fusion','icons/camera.png');
/*!40000 ALTER TABLE `icons` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `modules`
--

DROP TABLE IF EXISTS `modules`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `modules` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `idIcon` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_modules_icons_idx` (`idIcon`),
  CONSTRAINT `fk_modules_icons` FOREIGN KEY (`idIcon`) REFERENCES `icons` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `modules`
--

LOCK TABLES `modules` WRITE;
/*!40000 ALTER TABLE `modules` DISABLE KEYS */;
INSERT INTO `modules` VALUES (1,'CCTV',1);
/*!40000 ALTER TABLE `modules` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plans`
--

DROP TABLE IF EXISTS `plans`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `plans` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plans`
--

LOCK TABLES `plans` WRITE;
/*!40000 ALTER TABLE `plans` DISABLE KEYS */;
INSERT INTO `plans` VALUES (1,'1 Touch Solutions LKN Inc'),(2,'3105 GMD'),(3,'Acoustic Interiors'),(4,'Adapt AV Solutions'),(5,'Advanced Light & Sound'),(6,'AECustom'),(7,'Affinity AV'),(8,'Aim High Audio'),(9,'ALR'),(10,'ANM Loewen Construction'),(11,'Arthur Retenburg Homes');
/*!40000 ALTER TABLE `plans` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `projects`
--

DROP TABLE IF EXISTS `projects`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `projects` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idPlan` int(11) NOT NULL,
  `name` varchar(45) NOT NULL,
  `gateway` bit(1) DEFAULT b'1' COMMENT 'If true, the gateway uses UUID.',
  `host` varchar(100) DEFAULT '',
  `port` int(11) DEFAULT '0',
  `uuid` varchar(45) DEFAULT '',
  `deploymentLocked` bit(1) NOT NULL DEFAULT b'0',
  `note` varchar(200) DEFAULT '',
  `license` varchar(45) DEFAULT '',
  `isEditable` bit(1) NOT NULL DEFAULT b'1',
  PRIMARY KEY (`id`),
  KEY `fk_projects_plans_idx` (`idPlan`),
  CONSTRAINT `fk_projects_plans` FOREIGN KEY (`idPlan`) REFERENCES `plans` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `projects`
--

LOCK TABLES `projects` WRITE;
/*!40000 ALTER TABLE `projects` DISABLE KEYS */;
INSERT INTO `projects` VALUES (1,3,'Camera Test','\0','',8080,'','\0','','',''),(2,3,'UNKNOWN1','','',0,'','\0','','','\0'),(3,3,'UNKNOWN2','','',0,'','\0','','','\0'),(4,3,'UNKNOWN3','','',0,'','\0','','','\0'),(5,3,'UNKNOWN4','','',0,'','\0','','','\0'),(6,3,'UNKNOWN5','','',0,'','\0','','','\0'),(9,3,'UNKNOWN6','\0','localhost',8080,'','\0','','',''),(10,3,'UNKNOWN7','','',0,'','\0','','',''),(11,3,'Speaker','','',0,'','\0','','',''),(12,3,'TV','','',0,'','\0','','',''),(13,3,'Door','','',0,'','\0','','',''),(14,3,'Garden','','',0,'','\0','','',''),(15,3,'Roof','','',0,'','\0','','',''),(16,3,'Window','','',0,'','\0','','',''),(17,3,'UNKNOWN','','',0,'','\0','','',''),(18,3,'UNKNOWN\\9','','',0,'','\0','','',''),(19,3,'UNKNOWN11','','',0,'','\0','','',''),(20,3,'b','','',0,'','\0','','',''),(21,3,'UNKNOWN21','','',0,'','\0','','',''),(22,3,'Gate','','',0,'','\0','','',''),(23,3,'UNKNOWN23','','',0,'','\0','','',''),(24,3,'UNKNOWN24','','',0,'','\0','','','');
/*!40000 ALTER TABLE `projects` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `versions`
--

DROP TABLE IF EXISTS `versions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `versions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idProject` int(11) NOT NULL,
  `name` varchar(45) NOT NULL,
  `deployTime` datetime DEFAULT NULL,
  `deploySource` varchar(45) DEFAULT NULL,
  `saveTime` datetime NOT NULL,
  `targetVersion` varchar(10) DEFAULT '',
  `isEditable` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`),
  KEY `fk_versions_projects_idx` (`idProject`),
  CONSTRAINT `fk_versions_projects` FOREIGN KEY (`idProject`) REFERENCES `projects` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `versions`
--

LOCK TABLES `versions` WRITE;
/*!40000 ALTER TABLE `versions` DISABLE KEYS */;
INSERT INTO `versions` VALUES (5,1,'1.0.17','2014-10-05 08:00:00','','2014-10-05 08:00:00','2.x','\0'),(7,1,'1.0.19','2014-10-16 08:41:28','','2014-10-16 08:41:28','2.x',''),(8,2,'1.0.0','2014-10-16 09:43:37','','2014-10-16 09:43:37','2.x',''),(9,3,'1.0.0','2014-10-16 09:43:53','','2014-10-16 09:43:53','2.x',''),(11,3,'1.0.1','2014-10-16 14:10:33','','2014-10-16 14:10:33','2.x',''),(12,1,'1.0.20','2014-10-16 14:11:07','','2014-10-16 14:11:07','2.x',''),(13,1,'1.0.21','2014-10-16 14:11:11','','2014-10-16 14:11:11','2.x',''),(15,1,'1.0.23','2014-10-16 14:11:15','','2014-10-16 14:11:15','2.x',''),(17,5,'1.0.0','2014-10-16 14:28:01','','2014-10-16 14:28:01','2.x',''),(19,1,'1.0.22','2014-10-16 14:35:28','','2014-10-16 14:35:28','2.x',''),(20,1,'1.0.25','2014-10-16 15:05:37','','2014-10-16 15:05:37','2.x',''),(21,1,'1.0.26','2014-10-16 15:05:44','','2014-10-16 15:05:44','2.x',''),(22,2,'1.0.2','2014-10-16 15:06:06','','2014-10-16 15:06:06','2.x',''),(23,1,'1.0.27','2014-10-16 15:25:03','','2014-10-16 15:25:03','2.x',''),(24,6,'1.0.1','2014-10-16 16:49:36','','2014-10-16 16:49:36','2.x',''),(29,1,'1.0.28','2014-10-17 14:01:41','','2014-10-17 14:01:41','2.x',''),(30,6,'1.0.2',NULL,'','2014-10-17 15:18:52','',''),(31,6,'1.0.3',NULL,'','2014-10-17 15:22:26','',''),(34,6,'1.0.5',NULL,'','2014-10-17 15:24:25','',''),(39,11,'1.0.0',NULL,'','2014-10-17 16:43:47','2.x',''),(40,5,'1.0.1','2014-10-17 17:01:17','','2014-10-17 17:01:17','2.x',''),(41,1,'1.0.29','2014-10-17 17:01:26','','2014-10-17 17:01:26','2.x',''),(42,1,'1.0.31',NULL,'','2014-10-17 17:01:38','',''),(43,12,'1.0.0',NULL,'','2014-10-17 17:01:50','2.x',''),(44,13,'1.0.0',NULL,'','2014-10-20 08:15:15','2.x',''),(45,11,'1.0.1','2014-10-20 08:52:21','','2014-10-20 08:52:21','2.x',''),(46,14,'1.0.0',NULL,'','2014-10-20 09:04:12','2.x',''),(47,11,'1.0.2','2014-10-20 09:05:45','','2014-10-20 09:05:45','2.x',''),(48,11,'1.0.3',NULL,'','2014-10-20 09:10:09','',''),(49,15,'1.0.0',NULL,'','2014-10-20 09:10:24','2.x',''),(50,16,'1.0.0',NULL,'','2014-10-20 09:10:32','2.x',''),(51,16,'1.0.1',NULL,'','2014-10-20 10:49:53','',''),(52,17,'1.0.0',NULL,'','2014-10-20 10:50:08','2.x',''),(53,18,'1.0.0',NULL,'','2014-10-20 16:45:50','2.x',''),(54,19,'1.0.0',NULL,'','2014-10-20 16:54:21','2.x',''),(55,1,'1.0.32','2014-10-21 13:05:28','','2014-10-21 13:05:28','2.x',''),(56,1,'1.0.33',NULL,'','2014-10-21 13:05:40','',''),(57,1,'1.0.34','2014-10-21 13:05:52','','2014-10-21 13:05:52','2.x',''),(58,1,'1.0.35','2014-10-21 13:06:43','','2014-10-21 13:06:43','2.x',''),(59,20,'1.0.0',NULL,'','2014-10-21 13:07:14','2.x',''),(60,21,'1.0.0',NULL,'','2014-10-21 13:08:05','2.x',''),(61,16,'1.0.22',NULL,'','2014-10-21 13:13:25','',''),(62,1,'1.0.18',NULL,'','2014-10-23 15:59:16','',''),(63,1,'1.0.36',NULL,'','2014-10-23 16:01:21','',''),(64,16,'1.0.23',NULL,'','2014-10-23 16:01:30','',''),(65,16,'1.0.24',NULL,'','2014-10-23 16:01:36','',''),(66,16,'1.0.25',NULL,'','2014-10-23 16:01:41','',''),(67,22,'1.0.0',NULL,'','2014-10-23 16:10:45','2.x',''),(68,23,'1.0.0',NULL,'','2014-10-23 16:13:53','2.x',''),(69,24,'1.0.0',NULL,'','2014-10-23 16:23:52','2.x',''),(70,16,'1.0.26',NULL,'','2014-10-23 17:02:11','','');
/*!40000 ALTER TABLE `versions` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-10-23 17:06:55
