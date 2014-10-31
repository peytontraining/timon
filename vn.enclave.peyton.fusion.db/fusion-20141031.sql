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
INSERT INTO `device_templates` VALUES (1,2,1,'Axis - None-PTZ Models','2014-08-12 23:28:00','Axis','Non-PTZ Models','2.0.1','Cameras','','\0'),(2,2,1,'Axis - PTZ Models','2014-08-06 18:03:00','Axis','PTZ Models','2.0.1','Cameras','',''),(3,2,2,'Clare Controls - B Series Models','2014-08-04 18:13:00','Clare Controls','B Series ','2.0.1','Clare Controls','','\0');
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
  CONSTRAINT `fk_device_types_icons` FOREIGN KEY (`idIcon`) REFERENCES `icons` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_device_types_modules` FOREIGN KEY (`idModule`) REFERENCES `modules` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
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
  `idIcon` int(11) NOT NULL,
  `name` varchar(45) CHARACTER SET big5 NOT NULL,
  `appModule` varchar(45) NOT NULL,
  `deviceType` varchar(45) NOT NULL,
  `physicalLocation` varchar(45) DEFAULT ' ',
  `manufacturer` varchar(45) CHARACTER SET big5 COLLATE big5_bin NOT NULL,
  `modelNumber` varchar(45) NOT NULL,
  `notes` varchar(200) NOT NULL,
  `version` varchar(45) NOT NULL,
  `lastModified` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_devices_versions_idx` (`idVersion`),
  KEY `fk_devices_icons_idx` (`idIcon`),
  CONSTRAINT `fk_devices_icons` FOREIGN KEY (`idIcon`) REFERENCES `icons` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_devices_versions` FOREIGN KEY (`idVersion`) REFERENCES `versions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=97 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `devices`
--

LOCK TABLES `devices` WRITE;
/*!40000 ALTER TABLE `devices` DISABLE KEYS */;
INSERT INTO `devices` VALUES (1,5,2,'Clare Controls - PTZ Models (IP) (device)','CCTV','IP Cameras','','Clare Controls','','','','0000-00-00 00:00:00'),(2,5,1,'Clare Vision 8 Channel NVR (device)','CCTV','DVR/NVR','','Clare Controls','','','','0000-00-00 00:00:00'),(3,5,3,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic','','','','0000-00-00 00:00:00'),(4,5,3,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic','','','','0000-00-00 00:00:00'),(5,5,3,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic','','','','0000-00-00 00:00:00'),(6,7,1,'Clare Controls - PTZ Models (IP) (device)','CCTV','IP Cameras','','Clare Controls','','','','0000-00-00 00:00:00'),(7,7,1,'Clare Vision 8 Channel NVR (device)','CCTV','DVR/NVR','','Clare Controls','','','','0000-00-00 00:00:00'),(8,7,1,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic','','','','0000-00-00 00:00:00'),(9,7,1,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic','','','','0000-00-00 00:00:00'),(10,7,1,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic','','','','0000-00-00 00:00:00'),(16,19,1,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic','','','','0000-00-00 00:00:00'),(17,19,1,'Clare Controls - PTZ Models (IP) (device)','CCTV','IP Cameras','','Clare Controls','','','','0000-00-00 00:00:00'),(18,19,1,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic','','','','0000-00-00 00:00:00'),(19,19,1,'Clare Vision 8 Channel NVR (device)','CCTV','DVR/NVR','','Clare Controls','','','','0000-00-00 00:00:00'),(20,19,1,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic','','','','0000-00-00 00:00:00'),(21,20,1,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic','','','','0000-00-00 00:00:00'),(22,20,1,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic','','','','0000-00-00 00:00:00'),(23,20,1,'Clare Controls - PTZ Models (IP) (device)','CCTV','IP Cameras','','Clare Controls','','','','0000-00-00 00:00:00'),(24,20,1,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic','','','','0000-00-00 00:00:00'),(25,20,1,'Clare Vision 8 Channel NVR (device)','CCTV','DVR/NVR','','Clare Controls','','','','0000-00-00 00:00:00'),(26,21,1,'Clare Controls - PTZ Models (IP) (device)','CCTV','IP Cameras','','Clare Controls','','','','0000-00-00 00:00:00'),(27,21,1,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic','','','','0000-00-00 00:00:00'),(28,21,1,'Clare Vision 8 Channel NVR (device)','CCTV','DVR/NVR','','Clare Controls','','','','0000-00-00 00:00:00'),(29,21,1,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic','','','','0000-00-00 00:00:00'),(30,21,1,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic','','','','0000-00-00 00:00:00'),(31,23,1,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic','','','','0000-00-00 00:00:00'),(32,23,1,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic','','','','0000-00-00 00:00:00'),(33,23,1,'Clare Controls - PTZ Models (IP) (device)','CCTV','IP Cameras','','Clare Controls','','','','0000-00-00 00:00:00'),(34,23,1,'Clare Vision 8 Channel NVR (device)','CCTV','DVR/NVR','','Clare Controls','','','','0000-00-00 00:00:00'),(35,23,1,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic','','','','0000-00-00 00:00:00'),(46,41,1,'Clare Vision 8 Channel NVR (device)','CCTV','DVR/NVR','','Clare Controls','','','','0000-00-00 00:00:00'),(47,41,1,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic','','','','0000-00-00 00:00:00'),(48,41,1,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic','','','','0000-00-00 00:00:00'),(49,41,1,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic','','','','0000-00-00 00:00:00'),(50,41,1,'Clare Controls - PTZ Models (IP) (device)','CCTV','IP Cameras','','Clare Controls','','','','0000-00-00 00:00:00'),(51,55,1,'Clare Controls - PTZ Models (IP) (device)','CCTV','IP Cameras','','Clare Controls','','','','0000-00-00 00:00:00'),(52,55,1,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic','','','','0000-00-00 00:00:00'),(53,55,1,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic','','','','0000-00-00 00:00:00'),(54,55,1,'Clare Vision 8 Channel NVR (device)','CCTV','DVR/NVR','','Clare Controls','','','','0000-00-00 00:00:00'),(55,55,1,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic','','','','0000-00-00 00:00:00'),(56,57,1,'Clare Controls - PTZ Models (IP) (device)','CCTV','IP Cameras','','Clare Controls','','','','0000-00-00 00:00:00'),(57,57,1,'Clare Vision 8 Channel NVR (device)','CCTV','DVR/NVR','','Clare Controls','','','','0000-00-00 00:00:00'),(58,57,1,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic','','','','0000-00-00 00:00:00'),(59,57,1,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic','','','','0000-00-00 00:00:00'),(60,57,1,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic','','','','0000-00-00 00:00:00'),(61,58,1,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic','','','','0000-00-00 00:00:00'),(62,58,1,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic','','','','0000-00-00 00:00:00'),(63,58,1,'Clare Vision 8 Channel NVR (device)','CCTV','DVR/NVR','','Clare Controls','','','','0000-00-00 00:00:00'),(64,58,1,'Clare Controls - PTZ Models (IP) (device)','CCTV','IP Cameras','','Clare Controls','','','','0000-00-00 00:00:00'),(65,58,1,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic','','','','0000-00-00 00:00:00'),(66,5,2,'Axis - PTZ Models','CCTV','IP Cameras','','Axis','','','','0000-00-00 00:00:00'),(67,5,2,'Clare Controls - B Series Models A','CCTV','IP Cameras','','Clare Controls','','','','0000-00-00 00:00:00'),(68,5,2,'Axis - None-PTZ Models B','CCTV','IP Cameras','','Axis','','','','0000-00-00 00:00:00'),(69,5,2,'Clare Controls - B Series Models A','CCTV','IP Cameras','','Clare Controls','','','','0000-00-00 00:00:00'),(70,5,2,'Axis - None-PTZ Models B','CCTV','IP Cameras','','Axis','','','','0000-00-00 00:00:00'),(71,5,2,'Clare Controls - B Series Models A','CCTV','IP Cameras','','Clare Controls','','','','0000-00-00 00:00:00'),(72,5,1,'Axis - PTZ Models Thuan','CCTV','IP Cameras','','Axis','','','','0000-00-00 00:00:00'),(74,53,1,'Axis - PTZ Models','CCTV','IP Cameras','','Axis','PTZ Models','Created by ThuanDT','2.0.1','2014-10-27 11:33:30'),(75,53,2,'Clare Controls - B Series Models','CCTV','IP Cameras','','Clare Controls','B Series ','Created by ThuanDT','2.0.1','2014-10-27 11:34:02'),(76,70,2,'Clare Controls - B Series Models Thuan','CCTV','IP Cameras','','Clare Controls','B Series ','Modified by Timon.','2.0.1','2014-10-31 16:00:58'),(77,70,2,'Clare Controls - B Series Models','CCTV','IP Cameras',NULL,'Clare Controls','B Series ','Created by ThuanDT.\nModified by Timon.','2.0.1','2014-08-04 18:13:00'),(78,66,1,'Axis - PTZ Models','CCTV','IP Cameras',NULL,'Axis','PTZ Models','Thuan','2.0.1','2014-08-06 18:03:00'),(79,66,2,'Clare Controls - B Series Models','CCTV','IP Cameras',NULL,'Clare Controls','B Series ','Thuan','2.0.1','2014-08-04 18:13:00'),(80,65,1,'Axis - PTZ Models','CCTV','IP Cameras',NULL,'Axis','PTZ Models','Thuan','2.0.1','2014-08-06 18:03:00'),(81,70,1,'Axis - None-PTZ Models','CCTV','IP Cameras',NULL,'Axis','Non-PTZ Models','Created and modified by Timon.','2.0.1','2014-08-12 23:28:00'),(82,70,1,'Axis - None-PTZ Models','CCTV','IP Cameras',NULL,'Axis','Non-PTZ Models','Created by ThuanDT.\nModified by Timon.','2.0.1','2014-08-12 23:28:00'),(83,64,2,'Clare Controls - B Series Models','CCTV','IP Cameras',NULL,'Clare Controls','B Series ','','2.0.1','2014-08-04 18:13:00'),(84,64,1,'Axis - PTZ Models','CCTV','IP Cameras',NULL,'Axis','PTZ Models','','2.0.1','2014-08-06 18:03:00'),(85,73,1,'Axis - None-PTZ Models','CCTV','IP Cameras',NULL,'Axis','Non-PTZ Models','Created by ThuanDT.\nModified by Timon.','2.0.1','2014-08-12 23:28:00'),(86,73,2,'Clare Controls - B Series Models','CCTV','IP Cameras',NULL,'Clare Controls','B Series ','Created by ThuanDT.\nModified by Timon.','2.0.1','2014-08-04 18:13:00'),(87,73,2,'Clare Controls - B Series Models','CCTV','IP Cameras','','Clare Controls','B Series ','Modified by Timon.','2.0.1','2014-10-28 10:18:31'),(88,73,1,'Axis - None-PTZ Models','CCTV','IP Cameras',NULL,'Axis','Non-PTZ Models','Created and modified by Timon.','2.0.1','2014-08-12 23:28:00'),(89,74,1,'Axis - None-PTZ Models','CCTV','IP Cameras',NULL,'Axis','Non-PTZ Models','Created by ThuanDT.\nModified by Timon.','2.0.1','2014-08-12 23:28:00'),(90,74,2,'Clare Controls - B Series Models','CCTV','IP Cameras',NULL,'Clare Controls','B Series ','Created by ThuanDT.\nModified by Timon.','2.0.1','2014-08-04 18:13:00'),(91,74,1,'Axis - None-PTZ Models','CCTV','IP Cameras',NULL,'Axis','Non-PTZ Models','Created and modified by Timon.','2.0.1','2014-08-12 23:28:00'),(92,74,2,'Clare Controls - B Series Models','CCTV','IP Cameras','','Clare Controls','B Series ','Modified by Timon.','2.0.1','2014-10-28 10:18:31'),(93,75,2,'Clare Controls - B Series Models Timon','CCTV','IP Cameras',NULL,'Clare Controls','B Series ','Created by ThuanDT.\nModified by Timon.','2.0.1','2014-10-31 16:01:49'),(94,75,2,'Clare Controls - B Series Models Thuan','CCTV','IP Cameras','','Clare Controls','B Series ','Modified by Timon.','2.0.1','2014-10-31 16:42:01'),(95,75,1,'Axis - None-PTZ Models','CCTV','IP Cameras',NULL,'Axis','Non-PTZ Models','Created by ThuanDT.\nModified by Timon.','2.0.1','2014-08-12 23:28:00'),(96,75,1,'Axis - None-PTZ Models','CCTV','IP Cameras',NULL,'Axis','Non-PTZ Models','Created and modified by Timon.','2.0.1','2014-08-12 23:28:00');
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `icons`
--

LOCK TABLES `icons` WRITE;
/*!40000 ALTER TABLE `icons` DISABLE KEYS */;
INSERT INTO `icons` VALUES (1,'vn.enclave.peyton.fusion','icons/cctv.png'),(2,'vn.enclave.peyton.fusion','icons/camera.png'),(3,'vn.enclave.peyton.fusion','icons/fireplace-controller.png');
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
-- Table structure for table `properties`
--

DROP TABLE IF EXISTS `properties`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `properties` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idDevice` int(11) NOT NULL,
  `name` varchar(45) NOT NULL,
  `value` varchar(45) NOT NULL,
  `isMandatory` bit(1) NOT NULL,
  `description` varchar(200) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_properties_devices_idx` (`idDevice`),
  CONSTRAINT `fk_properties_devices` FOREIGN KEY (`idDevice`) REFERENCES `devices` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `properties`
--

LOCK TABLES `properties` WRITE;
/*!40000 ALTER TABLE `properties` DISABLE KEYS */;
INSERT INTO `properties` VALUES (1,76,'driver','Axis(VAPIX v2)','','Represents the driver info needed to create a camera. The value is constructed.'),(2,76,'address','127.0.0.1','\0','Address of the camera. The value of this property should consist of all the information.'),(3,76,'camera.type','ip','\0','Type of camera. May be IP or USB.'),(4,76,'user','anonymous','\0','User of the camera.'),(5,76,'password','anonymous','\0','User password of the camera.'),(6,92,'user','anonymous','\0','User of the camera.'),(7,92,'address','127.0.0.1','\0','Address of the camera. The value of this property should consist of all the information.'),(8,92,'password','anonymous','\0','User password of the camera.'),(9,92,'camera.type','ip','\0','Type of camera. May be IP or USB.'),(10,92,'driver','Axis(VAPIX v2)','','Represents the driver info needed to create a camera. The value is constructed.'),(11,94,'driver','Axis(VAPIX v2)','','Represents the driver info needed to create a camera. The value is constructed.'),(12,94,'camera.type','ip','\0','Type of camera. May be IP or USB.'),(13,94,'user','anonymous','\0','User of the camera.'),(14,94,'address','127.0.0.1','\0','Address of the camera. The value of this property should consist of all the information.'),(15,94,'password','anonymous','\0','User password of the camera.');
/*!40000 ALTER TABLE `properties` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `versions`
--

LOCK TABLES `versions` WRITE;
/*!40000 ALTER TABLE `versions` DISABLE KEYS */;
INSERT INTO `versions` VALUES (5,1,'1.0.17','2014-10-05 08:00:00','','2014-10-05 08:00:00','2.x','\0'),(7,1,'1.0.19','2014-10-16 08:41:28','','2014-10-16 08:41:28','2.x',''),(8,2,'1.0.0','2014-10-16 09:43:37','','2014-10-16 09:43:37','2.x',''),(9,3,'1.0.0','2014-10-16 09:43:53','','2014-10-16 09:43:53','2.x',''),(11,3,'1.0.1','2014-10-16 14:10:33','','2014-10-16 14:10:33','2.x',''),(12,1,'1.0.20','2014-10-16 14:11:07','','2014-10-16 14:11:07','2.x',''),(13,1,'1.0.21','2014-10-16 14:11:11','','2014-10-16 14:11:11','2.x',''),(15,1,'1.0.23','2014-10-16 14:11:15','','2014-10-16 14:11:15','2.x',''),(17,5,'1.0.0','2014-10-16 14:28:01','','2014-10-16 14:28:01','2.x',''),(19,1,'1.0.22','2014-10-16 14:35:28','','2014-10-16 14:35:28','2.x',''),(20,1,'1.0.25','2014-10-16 15:05:37','','2014-10-16 15:05:37','2.x',''),(21,1,'1.0.26','2014-10-16 15:05:44','','2014-10-16 15:05:44','2.x',''),(22,2,'1.0.2','2014-10-16 15:06:06','','2014-10-16 15:06:06','2.x',''),(23,1,'1.0.27','2014-10-16 15:25:03','','2014-10-16 15:25:03','2.x',''),(24,6,'1.0.1','2014-10-16 16:49:36','','2014-10-16 16:49:36','2.x',''),(29,1,'1.0.28','2014-10-17 14:01:41','','2014-10-17 14:01:41','2.x',''),(30,6,'1.0.2',NULL,'','2014-10-17 15:18:52','',''),(31,6,'1.0.3',NULL,'','2014-10-17 15:22:26','',''),(34,6,'1.0.5',NULL,'','2014-10-17 15:24:25','',''),(39,11,'1.0.0',NULL,'','2014-10-17 16:43:47','2.x',''),(40,5,'1.0.1','2014-10-17 17:01:17','','2014-10-17 17:01:17','2.x',''),(41,1,'1.0.29','2014-10-17 17:01:26','','2014-10-17 17:01:26','2.x',''),(42,1,'1.0.31',NULL,'','2014-10-17 17:01:38','',''),(43,12,'1.0.0',NULL,'','2014-10-17 17:01:50','2.x',''),(44,13,'1.0.0',NULL,'','2014-10-20 08:15:15','2.x',''),(45,11,'1.0.1','2014-10-20 08:52:21','','2014-10-20 08:52:21','2.x',''),(46,14,'1.0.0',NULL,'','2014-10-20 09:04:12','2.x',''),(47,11,'1.0.2','2014-10-20 09:05:45','','2014-10-20 09:05:45','2.x',''),(48,11,'1.0.3',NULL,'','2014-10-20 09:10:09','',''),(49,15,'1.0.0',NULL,'','2014-10-20 09:10:24','2.x',''),(50,16,'1.0.0',NULL,'','2014-10-20 09:10:32','2.x',''),(51,16,'1.0.1',NULL,'','2014-10-20 10:49:53','',''),(52,17,'1.0.0',NULL,'','2014-10-20 10:50:08','2.x',''),(53,18,'1.0.0',NULL,'','2014-10-20 16:45:50','2.x',''),(54,19,'1.0.0',NULL,'','2014-10-20 16:54:21','2.x',''),(55,1,'1.0.32','2014-10-21 13:05:28','','2014-10-21 13:05:28','2.x',''),(56,1,'1.0.33',NULL,'','2014-10-21 13:05:40','',''),(57,1,'1.0.34','2014-10-21 13:05:52','','2014-10-21 13:05:52','2.x',''),(58,1,'1.0.35','2014-10-21 13:06:43','','2014-10-21 13:06:43','2.x',''),(59,20,'1.0.0',NULL,'','2014-10-21 13:07:14','2.x',''),(60,21,'1.0.0',NULL,'','2014-10-21 13:08:05','2.x',''),(61,16,'1.0.22',NULL,'','2014-10-21 13:13:25','',''),(62,1,'1.0.18',NULL,'','2014-10-23 15:59:16','',''),(63,1,'1.0.36',NULL,'','2014-10-23 16:01:21','',''),(64,16,'1.0.23',NULL,'','2014-10-23 16:01:30','',''),(65,16,'1.0.24',NULL,'','2014-10-23 16:01:36','',''),(66,16,'1.0.25',NULL,'','2014-10-23 16:01:41','',''),(67,22,'1.0.0',NULL,'','2014-10-23 16:10:45','2.x',''),(68,23,'1.0.0',NULL,'','2014-10-23 16:13:53','2.x',''),(69,24,'1.0.0',NULL,'','2014-10-23 16:23:52','2.x',''),(70,16,'1.0.26',NULL,'','2014-10-23 17:02:11','',''),(73,16,'1.0.27','2014-10-31 15:51:30','','2014-10-31 15:51:30','2.x',''),(74,16,'1.0.28','2014-10-31 15:58:45','','2014-10-31 15:58:45','2.x',''),(75,16,'1.0.29','2014-10-31 16:01:31','','2014-10-31 16:01:31','2.x','');
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

-- Dump completed on 2014-10-31 17:37:51