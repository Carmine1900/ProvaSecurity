-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versione server:              8.0.35 - MySQL Community Server - GPL
-- S.O. server:                  Win64
-- HeidiSQL Versione:            12.5.0.6677
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dump della struttura del database provasecurity
CREATE DATABASE IF NOT EXISTS `provasecurity` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `provasecurity`;

-- Dump della struttura di tabella provasecurity.ruoli
CREATE TABLE IF NOT EXISTS `ruoli` (
  `id` int NOT NULL AUTO_INCREMENT,
  `authority` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dump dei dati della tabella provasecurity.ruoli: ~2 rows (circa)
INSERT INTO `ruoli` (`id`, `authority`) VALUES
	(1, 'admin'),
	(2, 'user');

-- Dump della struttura di tabella provasecurity.users
CREATE TABLE IF NOT EXISTS `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` tinytext,
  `cognome` tinytext,
  `username` varchar(200) DEFAULT NULL,
  `password` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `id_ruolo` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_id_ruolo` (`id_ruolo`),
  CONSTRAINT `fk_id_ruolo` FOREIGN KEY (`id_ruolo`) REFERENCES `ruoli` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dump dei dati della tabella provasecurity.users: ~1 rows (circa)
INSERT INTO `users` (`id`, `nome`, `cognome`, `username`, `password`, `id_ruolo`) VALUES
	(8, 'Andrea', 'Izzo', 'gioia', '$2a$10$1LdcryeCkLm4BK6aKcYyTOnq2C7UY7MZwmlFaiOwlIpmwRylQwgRu', 1);

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
