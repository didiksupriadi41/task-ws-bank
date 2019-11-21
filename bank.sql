-- MySQL dump 10.17  Distrib 10.3.18-MariaDB, for Linux (x86_64)
--
-- Host: localhost    Database: bank
-- ------------------------------------------------------
-- Server version	10.3.18-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `account` (
  `account_number` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `balance` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`account_number`),
  UNIQUE KEY `account_account_number_uindex` (`account_number`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `transaction`
--

DROP TABLE IF EXISTS `transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `transaction` (
  `transaction_time` datetime NOT NULL,
  `linked_number` varchar(255) NOT NULL,
  `type` varchar(10) NOT NULL,
  `amount` int(11) NOT NULL DEFAULT 0,
  `account_number` varchar(255) NOT NULL,
  KEY `transaction_account_account_number_fk` (`account_number`),
  CONSTRAINT `transaction_account_account_number_fk` FOREIGN KEY (`account_number`) REFERENCES `account` (`account_number`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `virtual_account`
--

DROP TABLE IF EXISTS `virtual_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `virtual_account` (
  `virtual_number` varchar(255) NOT NULL,
  `account_number` varchar(255) NOT NULL,
  PRIMARY KEY (`virtual_number`),
  UNIQUE KEY `virtual_account_virtual_number_uindex` (`virtual_number`),
  KEY `virtual_account_account_account_number_fk` (`account_number`),
  CONSTRAINT `virtual_account_account_account_number_fk` FOREIGN KEY (`account_number`) REFERENCES `account` (`account_number`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-11-20  1:19:34
-- Initiation data
INSERT INTO account VALUES( '0527481550', 'Didik',  0);
INSERT INTO account VALUES( '0527481551', 'Johanes', 6000000);
INSERT INTO account VALUES( '0527481552', 'Louis',  7000000);
INSERT INTO transaction VALUES(NOW(), '0527481551', 'D', 6000000, '0527481550');
INSERT INTO transaction VALUES(NOW(), '0527481550', 'K', 6000000, '0527481551');
-- Initiation completed
