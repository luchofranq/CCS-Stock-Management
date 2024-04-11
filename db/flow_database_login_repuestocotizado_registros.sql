-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: flow_database_login
-- ------------------------------------------------------
-- Server version	8.0.35

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
-- Table structure for table `repuestocotizado_registros`
--

DROP TABLE IF EXISTS `repuestocotizado_registros`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `repuestocotizado_registros` (
  `IdRepuestoCotizado` int NOT NULL AUTO_INCREMENT,
  `cantidad` int DEFAULT NULL,
  `precioTotal` decimal(10,2) DEFAULT NULL,
  `tipo` varchar(120) DEFAULT NULL,
  `numeroDeParte` varchar(120) DEFAULT NULL,
  `precioUnitario` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`IdRepuestoCotizado`)
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `repuestocotizado_registros`
--

LOCK TABLES `repuestocotizado_registros` WRITE;
/*!40000 ALTER TABLE `repuestocotizado_registros` DISABLE KEYS */;
INSERT INTO `repuestocotizado_registros` VALUES (74,5,26060.00,'EXTERIOR','81',5212.00),(75,2,102.00,'LOCAL','15',51.00),(76,5,255.00,'LOCAL','15',51.00),(77,1,165.00,'EXTERIOR','8',165.00),(78,5,7500.00,'EXTERIOR','14',1500.00),(79,4,260.00,'LOCAL','20',65.00),(80,5,255.00,'LOCAL','15',51.00);
/*!40000 ALTER TABLE `repuestocotizado_registros` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-04-11 17:44:42
