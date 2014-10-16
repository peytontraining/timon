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
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `devices`
--

LOCK TABLES `devices` WRITE;
/*!40000 ALTER TABLE `devices` DISABLE KEYS */;
INSERT INTO `devices` VALUES (1,5,'Clare Controls - PTZ Models (IP) (device)','CCTV','IP Cameras','','Clare Controls'),(2,5,'Clare Vision 8 Channel NVR (device)','CCTV','DVR/NVR','','Clare Controls'),(3,5,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic'),(4,5,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic'),(5,5,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic'),(6,7,'Clare Controls - PTZ Models (IP) (device)','CCTV','IP Cameras','','Clare Controls'),(7,7,'Clare Vision 8 Channel NVR (device)','CCTV','DVR/NVR','','Clare Controls'),(8,7,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic'),(9,7,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic'),(10,7,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic'),(16,19,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic'),(17,19,'Clare Controls - PTZ Models (IP) (device)','CCTV','IP Cameras','','Clare Controls'),(18,19,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic'),(19,19,'Clare Vision 8 Channel NVR (device)','CCTV','DVR/NVR','','Clare Controls'),(20,19,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic'),(21,20,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic'),(22,20,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic'),(23,20,'Clare Controls - PTZ Models (IP) (device)','CCTV','IP Cameras','','Clare Controls'),(24,20,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic'),(25,20,'Clare Vision 8 Channel NVR (device)','CCTV','DVR/NVR','','Clare Controls'),(26,21,'Clare Controls - PTZ Models (IP) (device)','CCTV','IP Cameras','','Clare Controls'),(27,21,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic'),(28,21,'Clare Vision 8 Channel NVR (device)','CCTV','DVR/NVR','','Clare Controls'),(29,21,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic'),(30,21,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic'),(31,23,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic'),(32,23,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic'),(33,23,'Clare Controls - PTZ Models (IP) (device)','CCTV','IP Cameras','','Clare Controls'),(34,23,'Clare Vision 8 Channel NVR (device)','CCTV','DVR/NVR','','Clare Controls'),(35,23,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic'),(36,27,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic'),(37,27,'Clare Controls - PTZ Models (IP) (device)','CCTV','IP Cameras','','Clare Controls'),(38,27,'Clare Vision 8 Channel NVR (device)','CCTV','DVR/NVR','','Clare Controls'),(39,27,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic'),(40,27,'Fireplace - On/Off Switch (device)','Fireplace','Fireplace Controller','','Generic');
/*!40000 ALTER TABLE `devices` ENABLE KEYS */;
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
  `gateway` bit(1) NOT NULL DEFAULT b'1' COMMENT 'If true, the gateway uses UUID.',
  `host` varchar(100) NOT NULL DEFAULT '',
  `port` varchar(10) NOT NULL DEFAULT '',
  `uuid` varchar(45) NOT NULL DEFAULT '',
  `note` varchar(200) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_projects_plans_idx` (`idPlan`),
  CONSTRAINT `fk_projects_plans` FOREIGN KEY (`idPlan`) REFERENCES `plans` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `projects`
--

LOCK TABLES `projects` WRITE;
/*!40000 ALTER TABLE `projects` DISABLE KEYS */;
INSERT INTO `projects` VALUES (1,3,'Camera Test','','','','',''),(2,3,'UNKNOWN','','','','',''),(3,3,'UNKNOWN','','','','',''),(4,3,'UNKNOWN','','','','',''),(5,3,'UNKNOWN','','','','',''),(6,3,'UNKNOWN','','','','','');
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
  `deploySource` varchar(45) NOT NULL,
  `saveTime` datetime NOT NULL,
  `targetVersion` varchar(10) NOT NULL,
  `isEditable` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`),
  KEY `fk_versions_projects_idx` (`idProject`),
  CONSTRAINT `fk_versions_projects` FOREIGN KEY (`idProject`) REFERENCES `projects` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `versions`
--

LOCK TABLES `versions` WRITE;
/*!40000 ALTER TABLE `versions` DISABLE KEYS */;
INSERT INTO `versions` VALUES (5,1,'1.0.17','2014-10-05 08:00:00','','2014-10-05 08:00:00','2.x','\0'),(7,1,'1.0.19','2014-10-16 08:41:28','','2014-10-16 08:41:28','2.x',''),(8,2,'1.0.0','2014-10-16 09:43:37','','2014-10-16 09:43:37','2.x',''),(9,3,'1.0.0','2014-10-16 09:43:53','','2014-10-16 09:43:53','2.x',''),(11,3,'1.0.2','2014-10-16 14:10:33','','2014-10-16 14:10:33','2.x',''),(12,1,'1.0.20','2014-10-16 14:11:07','','2014-10-16 14:11:07','2.x',''),(13,1,'1.0.21','2014-10-16 14:11:11','','2014-10-16 14:11:11','2.x',''),(15,1,'1.0.23','2014-10-16 14:11:15','','2014-10-16 14:11:15','2.x',''),(17,5,'1.0.0','2014-10-16 14:28:01','','2014-10-16 14:28:01','2.x',''),(18,1,'1.0.24','2014-10-16 14:35:13','','2014-10-16 14:35:13','2.x',''),(19,1,'1.0.22','2014-10-16 14:35:28','','2014-10-16 14:35:28','2.x',''),(20,1,'1.0.25','2014-10-16 15:05:37','','2014-10-16 15:05:37','2.x',''),(21,1,'1.0.26','2014-10-16 15:05:44','','2014-10-16 15:05:44','2.x',''),(22,2,'1.0.1','2014-10-16 15:06:06','','2014-10-16 15:06:06','2.x',''),(23,1,'1.0.27','2014-10-16 15:25:03','','2014-10-16 15:25:03','2.x',''),(24,6,'1.0.0','2014-10-16 16:49:36','','2014-10-16 16:49:36','2.x',''),(25,1,'1.0.28','2014-10-16 16:49:39','','2014-10-16 16:49:39','2.x',''),(26,1,'1.0.29','2014-10-16 16:49:46','','2014-10-16 16:49:46','2.x',''),(27,1,'1.0.30','2014-10-16 16:49:52','','2014-10-16 16:49:52','2.x','');
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

-- Dump completed on 2014-10-16 17:08:11
