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
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `devices`
--

LOCK TABLES `devices` WRITE;
/*!40000 ALTER TABLE `devices` DISABLE KEYS */;
INSERT INTO `devices` VALUES (1,16,1,'Axis - None-PTZ Models','CCTV','IP Cameras',' ','Axis','Non-PTZ Models','This device belongs to version 1.0.15.\nCreated by Timon.','2.0.1','2014-11-03 16:18:48'),(2,17,1,'Axis - None-PTZ Models','CCTV','IP Cameras',' ','Axis','Non-PTZ Models','This device belongs to version 1.0.16.\nCreated by Timon.','2.0.1','2014-11-03 13:22:57'),(3,17,1,'Axis - PTZ Models','CCTV','IP Cameras',' ','Axis','PTZ Models','This device belongs version 1.0.16.\nCreated by Timon.','2.0.1','2014-08-06 18:03:00'),(4,18,1,'Axis - None-PTZ Models','CCTV','IP Cameras',' ','Axis','Non-PTZ Models','This device belongs to version 1.0.17.\nCreated by Timon.','2.0.1','2014-11-03 13:23:19'),(5,18,1,'Axis - PTZ Models','CCTV','IP Cameras',' ','Axis','PTZ Models','This device belongs version 1.0.17.\nCreated by Timon.\nModified by Timon, again.','2.0.1','2014-11-03 16:07:48'),(6,18,1,'Axis - PTZ Models','CCTV','IP Cameras',' ','Axis','PTZ Models','Create by Timon.','2.0.1','2014-08-06 18:03:00'),(7,16,1,'Axis - None-PTZ Models','CCTV','IP Cameras',' ','Axis','Non-PTZ Models','','2.0.1','2014-08-12 23:28:00'),(8,20,1,'Axis - PTZ Models','CCTV','IP Cameras',' ','Axis','PTZ Models','This device belongs version 1.0.17.\nCreated by Timon.\nModified by Timon, again.','2.0.1','2014-11-03 16:07:48'),(9,20,1,'Axis - None-PTZ Models','CCTV','IP Cameras',' ','Axis','Non-PTZ Models','This device belongs to version 1.0.17.\nCreated by Timon.','2.0.1','2014-11-03 13:23:19'),(10,20,1,'Axis - PTZ Models','CCTV','IP Cameras',' ','Axis','PTZ Models','Create by Timon.','2.0.1','2014-08-06 18:03:00'),(11,20,2,'Clare Controls - B Series Models','CCTV','IP Cameras',' ','Clare Controls','B Series ','','2.0.1','2014-08-04 18:13:00');
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `projects`
--

LOCK TABLES `projects` WRITE;
/*!40000 ALTER TABLE `projects` DISABLE KEYS */;
INSERT INTO `projects` VALUES (1,3,'Camera Test','\0','127.0.0.1',8080,'','\0','','','');
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
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `properties`
--

LOCK TABLES `properties` WRITE;
/*!40000 ALTER TABLE `properties` DISABLE KEYS */;
INSERT INTO `properties` VALUES (1,1,'driver','Axis(VAPIX v2)','','Represents the driver info needed to create a camera. The value is constructed.'),(2,1,'address','127.0.0.1','\0','Address of the camera. The value of this property should consist of all the information.'),(3,1,'password','anonymous','\0','User password of the camera.'),(4,1,'user','anonymous','\0','User of the camera.'),(5,1,'camera.type','ip','\0','Type of camera. May be IP or USB.'),(6,2,'password','anonymous','\0','User password of the camera.'),(7,2,'camera.type','ip','\0','Type of camera. May be IP or USB.'),(8,2,'address','127.0.0.1','\0','Address of the camera. The value of this property should consist of all the information.'),(9,2,'user','anonymous','\0','User of the camera.'),(10,2,'driver','Axis(VAPIX v2)','','Represents the driver info needed to create a camera. The value is constructed.'),(11,3,'user','anonymous','\0','User of the camera.'),(12,3,'camera.type','ip','\0','Type of camera. May be IP or USB.'),(13,3,'driver','Axis(VAPIX v2)','','Represents the driver info needed to create a camera. The value is constructed.'),(14,3,'address','127.0.0.1','\0','Address of the camera. The value of this property should consist of all the information.'),(15,3,'password','anonymous','\0','User password of the camera.'),(16,5,'user','anonymous','\0','User of the camera.'),(17,5,'address','127.0.0.1','\0','Address of the camera. The value of this property should consist of all the information.'),(18,4,'user','anonymous','\0','User of the camera.'),(19,4,'driver','Axis(VAPIX v2)','','Represents the driver info needed to create a camera. The value is constructed.'),(20,4,'camera.type','ip','\0','Type of camera. May be IP or USB.'),(21,5,'driver','Axis(VAPIX v2)','','Represents the driver info needed to create a camera. The value is constructed.'),(22,5,'password','anonymous','\0','User password of the camera.'),(23,4,'address','127.0.0.1','\0','Address of the camera. The value of this property should consist of all the information.'),(24,4,'password','anonymous','\0','User password of the camera.'),(25,5,'camera.type','ip','\0','Type of camera. May be IP or USB.'),(26,6,'user','anonymous','\0','User of the camera.'),(27,6,'address','127.0.0.1','\0','Address of the camera. The value of this property should consist of all the information.'),(28,6,'camera.type','ip','\0','Type of camera. May be IP or USB.'),(29,6,'password','anonymous','\0','User password of the camera.'),(30,6,'driver','Axis(VAPIX v2)','','Represents the driver info needed to create a camera. The value is constructed.'),(31,7,'password','anonymous','\0','User password of the camera.'),(32,7,'driver','Axis(VAPIX v2)','','Represents the driver info needed to create a camera. The value is constructed.'),(33,7,'camera.type','ip','\0','Type of camera. May be IP or USB.'),(34,7,'address','127.0.0.1','\0','Address of the camera. The value of this property should consist of all the information.'),(35,7,'user','anonymous','\0','User of the camera.'),(36,10,'driver','Axis(VAPIX v2)','','Represents the driver info needed to create a camera. The value is constructed.'),(37,9,'address','127.0.0.1','\0','Address of the camera. The value of this property should consist of all the information.'),(38,10,'user','anonymous','\0','User of the camera.'),(39,9,'driver','Axis(VAPIX v2)','','Represents the driver info needed to create a camera. The value is constructed.'),(40,8,'user','anonymous','\0','User of the camera.'),(41,8,'address','127.0.0.1','\0','Address of the camera. The value of this property should consist of all the information.'),(42,9,'user','anonymous','\0','User of the camera.'),(43,8,'password','anonymous','\0','User password of the camera.'),(44,9,'password','anonymous','\0','User password of the camera.'),(45,8,'camera.type','ip','\0','Type of camera. May be IP or USB.'),(46,8,'driver','Axis(VAPIX v2)','','Represents the driver info needed to create a camera. The value is constructed.'),(47,10,'password','anonymous','\0','User password of the camera.'),(48,9,'camera.type','ip','\0','Type of camera. May be IP or USB.'),(49,10,'address','127.0.0.1','\0','Address of the camera. The value of this property should consist of all the information.'),(50,10,'camera.type','ip','\0','Type of camera. May be IP or USB.');
/*!40000 ALTER TABLE `properties` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `property_templates`
--

DROP TABLE IF EXISTS `property_templates`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `property_templates` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idDeviceTemplate` int(11) NOT NULL,
  `name` varchar(45) NOT NULL,
  `value` varchar(45) NOT NULL,
  `isMandatory` bit(1) NOT NULL,
  `description` varchar(200) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_property_templates_device_templates_idx` (`idDeviceTemplate`),
  CONSTRAINT `fk_property_templates_device_templates` FOREIGN KEY (`idDeviceTemplate`) REFERENCES `device_templates` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `property_templates`
--

LOCK TABLES `property_templates` WRITE;
/*!40000 ALTER TABLE `property_templates` DISABLE KEYS */;
INSERT INTO `property_templates` VALUES (1,1,'driver','Axis(VAPIX v2)','','Represents the driver info needed to create a camera. The value is constructed.'),(2,1,'address','127.0.0.1','\0','Address of the camera. The value of this property should consist of all the information.'),(3,1,'camera.type','ip','\0','Type of camera. May be IP or USB.'),(4,1,'user','anonymous','\0','User of the camera.'),(5,1,'password','anonymous','\0','User password of the camera.'),(6,2,'driver','Axis(VAPIX v2)','','Represents the driver info needed to create a camera. The value is constructed.'),(7,2,'address','127.0.0.1','\0','Address of the camera. The value of this property should consist of all the information.'),(8,2,'camera.type','ip','\0','Type of camera. May be IP or USB.'),(9,2,'user','anonymous','\0','User of the camera.'),(10,2,'password','anonymous','\0','User password of the camera.');
/*!40000 ALTER TABLE `property_templates` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `versions`
--

LOCK TABLES `versions` WRITE;
/*!40000 ALTER TABLE `versions` DISABLE KEYS */;
INSERT INTO `versions` VALUES (1,1,'1.0.0',NULL,'','2014-11-03 13:19:17','2.x',''),(2,1,'1.0.1',NULL,'','2014-11-03 13:20:00','',''),(3,1,'1.0.2',NULL,'','2014-11-03 13:20:04','',''),(4,1,'1.0.3',NULL,'','2014-11-03 13:20:07','',''),(5,1,'1.0.4',NULL,'','2014-11-03 13:20:10','',''),(6,1,'1.0.5',NULL,'','2014-11-03 13:20:13','',''),(7,1,'1.0.6',NULL,'','2014-11-03 13:20:16','',''),(8,1,'1.0.7',NULL,'','2014-11-03 13:20:18','',''),(9,1,'1.0.8',NULL,'','2014-11-03 13:20:21','',''),(10,1,'1.0.9',NULL,'','2014-11-03 13:20:24','',''),(11,1,'1.0.10',NULL,'','2014-11-03 13:20:27','',''),(12,1,'1.0.11',NULL,'','2014-11-03 13:20:30','',''),(13,1,'1.0.12',NULL,'','2014-11-03 13:20:33','',''),(14,1,'1.0.13',NULL,'','2014-11-03 13:20:36','',''),(15,1,'1.0.14',NULL,'','2014-11-03 13:20:39','',''),(16,1,'1.0.15',NULL,'','2014-11-03 13:20:46','',''),(17,1,'1.0.16','2014-11-03 13:22:07','','2014-11-03 13:22:07','2.x',''),(18,1,'1.0.17','2014-11-03 13:23:05','','2014-11-03 13:23:05','2.x',''),(20,1,'1.0.18','2014-11-03 16:19:57','','2014-11-03 16:19:57','2.x','');
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

-- Dump completed on 2014-11-04 17:13:14
