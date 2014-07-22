-- MySQL dump 10.13  Distrib 5.6.19, for Win32 (x86)
--
-- Host: localhost    Database: glossary
-- ------------------------------------------------------
-- Server version	5.6.19

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
-- Table structure for table `projects`
--

DROP TABLE IF EXISTS `projects`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `projects` (
  `project_id` int(11) NOT NULL AUTO_INCREMENT,
  `project_name` varchar(128) NOT NULL,
  `project_description` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`project_id`),
  KEY `project_index` (`project_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `projects`
--

LOCK TABLES `projects` WRITE;
/*!40000 ALTER TABLE `projects` DISABLE KEYS */;
INSERT INTO `projects` VALUES (1,'Glossary WebApp','an application to manage\n      definitions of terms specific to certain projects\n      to allow the user to quickly add or review terms in\n      the scope of his/her project.'),(2,'Testing','Testing at MGM RI'),(3,'Tools','Software tools for developers');
/*!40000 ALTER TABLE `projects` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `words`
--

DROP TABLE IF EXISTS `words`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `words` (
  `words_id` int(11) NOT NULL AUTO_INCREMENT,
  `word` varchar(128) NOT NULL,
  `definition` varchar(4096) DEFAULT NULL,
  `notes` varchar(4096) DEFAULT NULL,
  `project_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`words_id`),
  KEY `words_index` (`word`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `words`
--

LOCK TABLES `words` WRITE;
/*!40000 ALTER TABLE `words` DISABLE KEYS */;
INSERT INTO `words` VALUES (1,'glossary','an alphabetical list of terms or words found in or relating to a specific subject, text, or dialect, with explanations; a brief dictionary.','n/a',0),(2,'ajax','Ajax is not a single technology, but a group of technologies. HTML and CSS can be used in combination to mark up and style information. The DOM is accessed with JavaScript to dynamically display - and allow the user to interact with - the information presented.','Used in the glossary application',0),(3,'Message-Oriented Middleware (MOM)','Infrastructure supporting sending and receiving messages between distributed systems.','The messages may contain data, software instructions, or both together. Typically built around a queuing system that stores messages pending delivery.',-1),(4,'Black-Box Testing','Method of software testing that examines the functionality of an application (e.g. what the software does) without peering into its internal structures or workings.','This method of test can be applied to virtually every level of software testing: unit, integration, system and acceptance. The tester is aware of what the software is supposed to do but is not aware of how it does it.',2),(5,'White-Box Testing','Method of testing software that tests internal structures or workings of an application, as opposed to its functionality.','It can test paths within a unit, paths between units during integration, and between subsystems during a system–level test. ',2),(6,'Unit Testing','Unit testing, also known as component testing, refers to tests that verify the functionality of a specific section of code, usually at the function level. In an object-oriented environment, this is usually at the class level, and the minimal unit tests include the constructors and destructors','Unit testing alone cannot verify the functionality of a piece of software, but rather is used to ensure that the building blocks of the software work independently from each other. ',2),(7,'Integration Testing','Integration testing works to expose defects in the interfaces and interaction between integrated components (modules). Progressively larger groups of tested software components corresponding to elements of the architectural design are integrated and tested until the software works as a system','',2),(8,'System Testing','System testing, or end-to-end testing, tests a completely integrated system to verify that it meets its requirements.','a system test might involve testing a logon interface, then creating and editing an entry, plus sending or printing results, followed by summary processing or deletion (or archiving) of entries, then logoff.',2),(9,'Apache JMeter','Can be used as a load testing tool for analyzing and measuring the performance of a variety of services, with a focus on web applications.','JMeter can be used as a unit test tool for JDBC database connections, FTP, LDAP, Webservices, JMS, HTTP, generic TCP connections and OS Native processes',3),(10,'Code Coverage','A measure used to describe the degree to which the source code of a program is tested by a particular test suite.','A program with high code coverage has been more thoroughly tested and has a lower chance of containing software bugs than a program with low code coverage.',2),(11,'SoapUI','An open-source web service testing application for service-oriented architectures (SOA) and representational state transfers (REST).','SoapUI can test SOAP and REST web services, JMS, AMF, as well as make any HTTP(S) and JDBC calls.',3);
/*!40000 ALTER TABLE `words` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-07-22  2:00:53
