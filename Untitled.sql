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
-- Table structure for table `clube_model`
--

DROP TABLE IF EXISTS `clube_model`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clube_model` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `estado` varchar(2) NOT NULL,
  `nome` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clube_model`
--

LOCK TABLES `clube_model` WRITE;
/*!40000 ALTER TABLE `clube_model` DISABLE KEYS */;
/*!40000 ALTER TABLE `clube_model` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estadio_model`
--

DROP TABLE IF EXISTS `estadio_model`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `estadio_model` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `capacidade` int DEFAULT NULL,
  `cidade` varchar(255) NOT NULL,
  `nome` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estadio_model`
--

LOCK TABLES `estadio_model` WRITE;
/*!40000 ALTER TABLE `estadio_model` DISABLE KEYS */;
/*!40000 ALTER TABLE `estadio_model` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `partida_model`
--

DROP TABLE IF EXISTS `partida_model`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `partida_model` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `data_partida` datetime(6) NOT NULL,
  `clube_mandante_id` bigint NOT NULL,
  `clube_visitante_id` bigint NOT NULL,
  `estadop_id` bigint NOT NULL,
  `data_hora` datetime(6) NOT NULL,
  `gols_mandante` int DEFAULT NULL,
  `gols_visitante` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKb4so3c9m51nkjgfecsf7y0iw2` (`clube_mandante_id`),
  KEY `FK7bgyb1sxqv67ulu0d040ssp6e` (`clube_visitante_id`),
  KEY `FKly9oibxf3r03n9ghu9o3k5t9d` (`estadop_id`),
  CONSTRAINT `FK7bgyb1sxqv67ulu0d040ssp6e` FOREIGN KEY (`clube_visitante_id`) REFERENCES `clube_model` (`id`),
  CONSTRAINT `FKb4so3c9m51nkjgfecsf7y0iw2` FOREIGN KEY (`clube_mandante_id`) REFERENCES `clube_model` (`id`),
  CONSTRAINT `FKly9oibxf3r03n9ghu9o3k5t9d` FOREIGN KEY (`estadop_id`) REFERENCES `estadio_model` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `partida_model`
--

LOCK TABLES `partida_model` WRITE;
/*!40000 ALTER TABLE `partida_model` DISABLE KEYS */;
/*!40000 ALTER TABLE `partida_model` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-11 17:24:00
