CREATE DATABASE  IF NOT EXISTS `dentalium` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `dentalium`;
-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: dentalium
-- ------------------------------------------------------
-- Server version	8.0.34

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
-- Table structure for table `appointment`
--

DROP TABLE IF EXISTS `appointment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `appointment` (
  `patient_id` varchar(10) NOT NULL,
  `doctor_id` int NOT NULL,
  `init_date` datetime NOT NULL,
  `end_date` varchar(255) NOT NULL,
  `observations` varchar(255) DEFAULT NULL,
  `appointment_status_id` int NOT NULL,
  `id` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  KEY `fk_doctor_has_patient_patient2_idx` (`patient_id`),
  KEY `fk_doctor_has_patient_doctor2_idx` (`doctor_id`),
  KEY `fk_appointment_appointment_status1_idx` (`appointment_status_id`),
  CONSTRAINT `fk_appointment_appointment_status1` FOREIGN KEY (`appointment_status_id`) REFERENCES `appointment_status` (`id`),
  CONSTRAINT `fk_doctor_has_patient_doctor2` FOREIGN KEY (`doctor_id`) REFERENCES `doctor` (`college_number`),
  CONSTRAINT `fk_doctor_has_patient_patient2` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`dni`)
) ENGINE=InnoDB AUTO_INCREMENT=554 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointment`
--

LOCK TABLES `appointment` WRITE;
/*!40000 ALTER TABLE `appointment` DISABLE KEYS */;
INSERT INTO `appointment` VALUES ('99999999A',123456,'2025-04-22 15:00:00','2025-04-22 15:30:00','Extracción dental',1,553);
/*!40000 ALTER TABLE `appointment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `appointment_seq`
--

DROP TABLE IF EXISTS `appointment_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `appointment_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointment_seq`
--

LOCK TABLES `appointment_seq` WRITE;
/*!40000 ALTER TABLE `appointment_seq` DISABLE KEYS */;
INSERT INTO `appointment_seq` VALUES (651);
/*!40000 ALTER TABLE `appointment_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `appointment_status`
--

DROP TABLE IF EXISTS `appointment_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `appointment_status` (
  `id` int NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointment_status`
--

LOCK TABLES `appointment_status` WRITE;
/*!40000 ALTER TABLE `appointment_status` DISABLE KEYS */;
INSERT INTO `appointment_status` VALUES (1,'PROGRAMADO'),(2,'CONFIRMADO'),(3,'ASISTIDO'),(4,'NO ASISTE'),(5,'RECHAZADO');
/*!40000 ALTER TABLE `appointment_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `appointment_status_seq`
--

DROP TABLE IF EXISTS `appointment_status_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `appointment_status_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointment_status_seq`
--

LOCK TABLES `appointment_status_seq` WRITE;
/*!40000 ALTER TABLE `appointment_status_seq` DISABLE KEYS */;
INSERT INTO `appointment_status_seq` VALUES (1);
/*!40000 ALTER TABLE `appointment_status_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `budget`
--

DROP TABLE IF EXISTS `budget`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `budget` (
  `id` int NOT NULL,
  `date` date DEFAULT NULL,
  `budget_status_id` int NOT NULL,
  `patient_dni` varchar(10) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_budget_budget_status1_idx` (`budget_status_id`),
  KEY `fk_budget_patient1_idx` (`patient_dni`),
  CONSTRAINT `fk_budget_budget_status1` FOREIGN KEY (`budget_status_id`) REFERENCES `budget_status` (`id`),
  CONSTRAINT `fk_budget_patient1` FOREIGN KEY (`patient_dni`) REFERENCES `patient` (`dni`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `budget`
--

LOCK TABLES `budget` WRITE;
/*!40000 ALTER TABLE `budget` DISABLE KEYS */;
INSERT INTO `budget` VALUES (552,'2025-04-11',2,'99999999A');
/*!40000 ALTER TABLE `budget` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `budget_line`
--

DROP TABLE IF EXISTS `budget_line`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `budget_line` (
  `treatment_id` int NOT NULL,
  `budget_id` int NOT NULL,
  `quantity` int NOT NULL,
  `teeth` varchar(255) DEFAULT NULL,
  `discount` float NOT NULL,
  PRIMARY KEY (`treatment_id`,`budget_id`),
  KEY `fk_budget_line_treatment1_idx` (`treatment_id`),
  KEY `fk_budget_line_budget1_idx` (`budget_id`),
  CONSTRAINT `fk_budget_line_budget1` FOREIGN KEY (`budget_id`) REFERENCES `budget` (`id`),
  CONSTRAINT `fk_budget_line_treatment1` FOREIGN KEY (`treatment_id`) REFERENCES `treatment` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `budget_line`
--

LOCK TABLES `budget_line` WRITE;
/*!40000 ALTER TABLE `budget_line` DISABLE KEYS */;
INSERT INTO `budget_line` VALUES (459,552,1,'17',25),(460,552,1,'11,21',0),(461,552,1,'26',0);
/*!40000 ALTER TABLE `budget_line` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `budget_seq`
--

DROP TABLE IF EXISTS `budget_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `budget_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `budget_seq`
--

LOCK TABLES `budget_seq` WRITE;
/*!40000 ALTER TABLE `budget_seq` DISABLE KEYS */;
INSERT INTO `budget_seq` VALUES (651);
/*!40000 ALTER TABLE `budget_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `budget_status`
--

DROP TABLE IF EXISTS `budget_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `budget_status` (
  `id` int NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `budget_status`
--

LOCK TABLES `budget_status` WRITE;
/*!40000 ALTER TABLE `budget_status` DISABLE KEYS */;
INSERT INTO `budget_status` VALUES (1,'PENDIENTE DE EMITIR'),(2,'EMITIDO'),(3,'TRATAMIENTO EN CURSO'),(4,'RECHAZADO POR PACIENTE'),(5,'ANULADO POR DOCTOR'),(6,'TRATAMIENTO FINALIZADO');
/*!40000 ALTER TABLE `budget_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `budget_status_seq`
--

DROP TABLE IF EXISTS `budget_status_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `budget_status_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `budget_status_seq`
--

LOCK TABLES `budget_status_seq` WRITE;
/*!40000 ALTER TABLE `budget_status_seq` DISABLE KEYS */;
INSERT INTO `budget_status_seq` VALUES (1);
/*!40000 ALTER TABLE `budget_status_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `doctor`
--

DROP TABLE IF EXISTS `doctor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `doctor` (
  `college_number` int NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`college_number`),
  KEY `fk_doctor_user1_idx` (`user_id`),
  CONSTRAINT `fk_doctor_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doctor`
--

LOCK TABLES `doctor` WRITE;
/*!40000 ALTER TABLE `doctor` DISABLE KEYS */;
INSERT INTO `doctor` VALUES (123456,704),(243564,705);
/*!40000 ALTER TABLE `doctor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `doctor_assigment`
--

DROP TABLE IF EXISTS `doctor_assigment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `doctor_assigment` (
  `id_patient` varchar(10) NOT NULL,
  `id_doctor` int NOT NULL,
  `since` date NOT NULL,
  `until` date DEFAULT NULL,
  PRIMARY KEY (`id_patient`,`id_doctor`,`since`),
  KEY `fk_doctor_has_patient_patient1_idx` (`id_doctor`),
  KEY `fk_doctor_has_patient_doctor1_idx` (`id_patient`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doctor_assigment`
--

LOCK TABLES `doctor_assigment` WRITE;
/*!40000 ALTER TABLE `doctor_assigment` DISABLE KEYS */;
INSERT INTO `doctor_assigment` VALUES ('12345678A',33493984,'2025-04-10',NULL),('2022234R',1223345,'2025-04-10',NULL),('20228061K',23223,'2025-04-10','2025-04-10'),('20228061K',38484,'2025-04-10','2025-04-10'),('20228061K',1223345,'2025-03-29','2025-04-10'),('20228061K',1223345,'2025-04-10',NULL),('99999999A',123456,'2025-04-11',NULL);
/*!40000 ALTER TABLE `doctor_assigment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medical_study`
--

DROP TABLE IF EXISTS `medical_study`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `medical_study` (
  `id` int NOT NULL,
  `doctor_id` int NOT NULL,
  `patient_id` varchar(10) NOT NULL,
  `study_type_id` int NOT NULL,
  `date` datetime DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_medical_study_study_type1_idx` (`study_type_id`),
  KEY `fk_medical_study_doctor1_idx` (`doctor_id`),
  KEY `fk_medical_study_patient1_idx` (`patient_id`),
  CONSTRAINT `fk_medical_study_doctor1` FOREIGN KEY (`doctor_id`) REFERENCES `doctor` (`college_number`),
  CONSTRAINT `fk_medical_study_patient1` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`dni`),
  CONSTRAINT `fk_medical_study_study_type1` FOREIGN KEY (`study_type_id`) REFERENCES `study_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medical_study`
--

LOCK TABLES `medical_study` WRITE;
/*!40000 ALTER TABLE `medical_study` DISABLE KEYS */;
INSERT INTO `medical_study` VALUES (902,123456,'99999999A',9,'2025-04-11 17:16:09','documents/medical_studies/99999999A/1744384568993_I1000000 (4).dcm'),(903,123456,'99999999A',1,'2025-04-11 17:29:51','documents/medical_studies/99999999A/1744385390733_perfil.jpeg'),(904,123456,'99999999A',2,'2025-04-11 17:29:56','documents/medical_studies/99999999A/1744385395529_frontal.jpeg'),(905,123456,'99999999A',3,'2025-04-11 17:30:09','documents/medical_studies/99999999A/1744385408511_sonrisa.jpeg'),(906,123456,'99999999A',4,'2025-04-11 17:30:19','documents/medical_studies/99999999A/1744385418555_oclusalsuperior.jpeg'),(907,123456,'99999999A',5,'2025-04-11 17:30:43','documents/medical_studies/99999999A/1744385443063_intraoralderecha.jpeg'),(908,123456,'99999999A',6,'2025-04-11 17:30:47','documents/medical_studies/99999999A/1744385447474_intraoralfrontal.jpeg'),(909,123456,'99999999A',7,'2025-04-11 17:30:52','documents/medical_studies/99999999A/1744385452481_intraoralizquierdo.jpeg'),(910,123456,'99999999A',8,'2025-04-11 17:30:57','documents/medical_studies/99999999A/1744385456761_oclusalinferior.jpeg');
/*!40000 ALTER TABLE `medical_study` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medical_study_seq`
--

DROP TABLE IF EXISTS `medical_study_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `medical_study_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medical_study_seq`
--

LOCK TABLES `medical_study_seq` WRITE;
/*!40000 ALTER TABLE `medical_study_seq` DISABLE KEYS */;
INSERT INTO `medical_study_seq` VALUES (1001);
/*!40000 ALTER TABLE `medical_study_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patient`
--

DROP TABLE IF EXISTS `patient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `patient` (
  `dni` varchar(10) NOT NULL,
  `user_id` int NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `postal_code` varchar(255) DEFAULT NULL,
  `municipality` varchar(255) DEFAULT NULL,
  `province` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`dni`),
  KEY `fk_patient_user1_idx` (`user_id`),
  CONSTRAINT `fk_patient_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient`
--

LOCK TABLES `patient` WRITE;
/*!40000 ALTER TABLE `patient` DISABLE KEYS */;
INSERT INTO `patient` VALUES ('11111111A',703,'Avenida de la Aurora, 5','29007','Málaga','Málaga'),('99999999A',702,'Calle Larios, 20','29005','Málaga','Málaga');
/*!40000 ALTER TABLE `patient` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` int NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'ROLE_DENTIST'),(2,'ROLE_PATIENT'),(3,'ROLE_ADMIN');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_seq`
--

DROP TABLE IF EXISTS `role_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_seq`
--

LOCK TABLES `role_seq` WRITE;
/*!40000 ALTER TABLE `role_seq` DISABLE KEYS */;
INSERT INTO `role_seq` VALUES (1);
/*!40000 ALTER TABLE `role_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `study_type`
--

DROP TABLE IF EXISTS `study_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `study_type` (
  `id` int NOT NULL,
  `name` varchar(255) NOT NULL,
  `extension` varchar(255) DEFAULT NULL,
  `content_type` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `study_type`
--

LOCK TABLES `study_type` WRITE;
/*!40000 ALTER TABLE `study_type` DISABLE KEYS */;
INSERT INTO `study_type` VALUES (1,'Perfil','jpeg','image/jpeg'),(2,'Frontal','jpeg','image/jpeg'),(3,'Sonrisa','jpeg','image/jpeg'),(4,'Oclusal superior','jpeg','image/jpeg'),(5,'Intraoral derecha','jpeg','image/jpeg'),(6,'Intraoral frontal','jpeg','image/jpeg'),(7,'Intraoral izquierdo','jpeg','image/jpeg'),(8,'Oclusal inferior','jpeg','image/jpeg'),(9,'DICOM','dcm','application/octet-stream');
/*!40000 ALTER TABLE `study_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `study_type_seq`
--

DROP TABLE IF EXISTS `study_type_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `study_type_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `study_type_seq`
--

LOCK TABLES `study_type_seq` WRITE;
/*!40000 ALTER TABLE `study_type_seq` DISABLE KEYS */;
INSERT INTO `study_type_seq` VALUES (1);
/*!40000 ALTER TABLE `study_type_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `treatment`
--

DROP TABLE IF EXISTS `treatment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `treatment` (
  `id` int NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `unit_price` float DEFAULT NULL,
  `active` smallint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `treatment`
--

LOCK TABLES `treatment` WRITE;
/*!40000 ALTER TABLE `treatment` DISABLE KEYS */;
INSERT INTO `treatment` VALUES (452,'CORONA ZIRCONIO',413,1),(453,'ORTODONCIA INVISALING',4380,1),(454,'RETENCIONES',377,1),(455,'ESTUDIO DE ORTODONCIA',84,1),(456,'IMPLANTE ASTRA TECH EVOLUTION',830,1),(457,'ADITAMIENTOS PROTÉSICOS',195,1),(458,'CORONA SOBRE IMPLANTE',375,1),(459,'INCRUSTACIÓN DE CERÁMICA',250,1),(460,'CARILLA DE COMPOSITE INYECTADO',250,1),(461,'EXTRACCIÓN DENTAL',50,1);
/*!40000 ALTER TABLE `treatment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `treatment_seq`
--

DROP TABLE IF EXISTS `treatment_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `treatment_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `treatment_seq`
--

LOCK TABLES `treatment_seq` WRITE;
/*!40000 ALTER TABLE `treatment_seq` DISABLE KEYS */;
INSERT INTO `treatment_seq` VALUES (551);
/*!40000 ALTER TABLE `treatment_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL,
  `name` varchar(255) NOT NULL,
  `surname_1` varchar(255) NOT NULL,
  `surname_2` varchar(255) DEFAULT NULL,
  `user` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phone` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `active` varchar(255) NOT NULL,
  `role_id` int NOT NULL,
  `valid` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user_role_idx` (`role_id`),
  CONSTRAINT `fk_user_role` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Dentalium','Dentalium','Dentalium','admin','$2y$10$CECZYEZUjcggWxRyi1.vF.e8FfqTFaDJMk3hhkA5kfa5i3h8E4gWu','0','0','1',3,'1'),(702,'Roberto','Moreno','Muñoz','roberto','$2y$10$CECZYEZUjcggWxRyi1.vF.e8FfqTFaDJMk3hhkA5kfa5i3h8E4gWu','999999999','romorenodev@gmail.com','1',2,'1'),(703,'José','Pérez','Pérez','jose.perez','$2y$10$CECZYEZUjcggWxRyi1.vF.e8FfqTFaDJMk3hhkA5kfa5i3h8E4gWu','111111111','jose.perez@gmail.com','1',2,'1'),(704,'Dolores','De la Torre','Marín','dolores','$2y$10$CECZYEZUjcggWxRyi1.vF.e8FfqTFaDJMk3hhkA5kfa5i3h8E4gWu','333333333','dolores.torres@dentalium.es','1',1,'1'),(705,'Luis','Fernández','Fernández','luis','$2y$10$CECZYEZUjcggWxRyi1.vF.e8FfqTFaDJMk3hhkA5kfa5i3h8E4gWu','444444444','luis.fernandez@dentalium.com','1',1,'1');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_seq`
--

DROP TABLE IF EXISTS `user_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_seq`
--

LOCK TABLES `user_seq` WRITE;
/*!40000 ALTER TABLE `user_seq` DISABLE KEYS */;
INSERT INTO `user_seq` VALUES (801);
/*!40000 ALTER TABLE `user_seq` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-04-11 17:37:45
