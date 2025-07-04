CREATE DATABASE  IF NOT EXISTS `campeonato_brasileiro` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `campeonato_brasileiro`;
-- MySQL dump 10.13  Distrib 8.0.38, for macos14 (arm64)
--
-- Host: localhost    Database: campeonato_brasileiro
-- ------------------------------------------------------
-- Server version	9.3.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `clubes`
--

DROP TABLE IF EXISTS `clubes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clubes` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `active` bit(1) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `stadium_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKiv4lj45ov5y1jufouox7735hq` (`stadium_id`),
  CONSTRAINT `FKiv4lj45ov5y1jufouox7735hq` FOREIGN KEY (`stadium_id`) REFERENCES `estadios` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clubes`
--

LOCK TABLES `clubes` WRITE;
/*!40000 ALTER TABLE `clubes` DISABLE KEYS */;
/*!40000 ALTER TABLE `clubes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estadios`
--

DROP TABLE IF EXISTS `estadios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `estadios` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `capacity` int DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estadios`
--

LOCK TABLES `estadios` WRITE;
/*!40000 ALTER TABLE `estadios` DISABLE KEYS */;
/*!40000 ALTER TABLE `estadios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `partidas`
--

DROP TABLE IF EXISTS `partidas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `partidas` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `away_goals` int DEFAULT NULL,
  `date_time` datetime(6) DEFAULT NULL,
  `home_goals` int DEFAULT NULL,
  `away_club_id` bigint NOT NULL,
  `home_club_id` bigint NOT NULL,
  `stadium_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKfx7jcst3t9dtskphsrous7scv` (`away_club_id`),
  KEY `FKfyddwcay8ar7618qfe94i1sx0` (`home_club_id`),
  KEY `FKgu6urujokts2xlxjnmve23x2h` (`stadium_id`),
  CONSTRAINT `FKfx7jcst3t9dtskphsrous7scv` FOREIGN KEY (`away_club_id`) REFERENCES `clubes` (`id`),
  CONSTRAINT `FKfyddwcay8ar7618qfe94i1sx0` FOREIGN KEY (`home_club_id`) REFERENCES `clubes` (`id`),
  CONSTRAINT `FKgu6urujokts2xlxjnmve23x2h` FOREIGN KEY (`stadium_id`) REFERENCES `estadios` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `partidas`
--

LOCK TABLES `partidas` WRITE;
/*!40000 ALTER TABLE `partidas` DISABLE KEYS */;
/*!40000 ALTER TABLE `partidas` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-07-01  9:45:51
