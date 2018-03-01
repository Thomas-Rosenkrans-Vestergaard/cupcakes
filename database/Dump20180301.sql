CREATE DATABASE  IF NOT EXISTS `cupcakes` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `cupcakes`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: cupcakes
-- ------------------------------------------------------
-- Server version	5.7.20-log

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
-- Table structure for table `bottoms`
--

DROP TABLE IF EXISTS `bottoms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bottoms` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `description` text NOT NULL,
  `price` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bottoms`
--

LOCK TABLES `bottoms` WRITE;
/*!40000 ALTER TABLE `bottoms` DISABLE KEYS */;
INSERT INTO `bottoms` VALUES (1,'Chocolate','Vestibulum ac odio faucibus, ornare justo at, laoreet augue. Nulla dapibus ut lacus quis posuere. Quisque suscipit libero sem, ut vulputate sem pellentesque sit amet. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.',500),(2,'Vanilla','Vestibulum ac odio faucibus, ornare justo at, laoreet augue. Nulla dapibus ut lacus quis posuere. Quisque suscipit libero sem, ut vulputate sem pellentesque sit amet. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.',500),(3,'Nutmeg','Vestibulum ac odio faucibus, ornare justo at, laoreet augue. Nulla dapibus ut lacus quis posuere. Quisque suscipit libero sem, ut vulputate sem pellentesque sit amet. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.',500),(4,'Pistacio','Vestibulum ac odio faucibus, ornare justo at, laoreet augue. Nulla dapibus ut lacus quis posuere. Quisque suscipit libero sem, ut vulputate sem pellentesque sit amet. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.',600),(5,'Almond','Vestibulum ac odio faucibus, ornare justo at, laoreet augue. Nulla dapibus ut lacus quis posuere. Quisque suscipit libero sem, ut vulputate sem pellentesque sit amet. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.',700);
/*!40000 ALTER TABLE `bottoms` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_items`
--

DROP TABLE IF EXISTS `order_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_items` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `order` int(10) unsigned NOT NULL,
  `bottom` int(10) unsigned NOT NULL,
  `topping` int(10) unsigned NOT NULL,
  `amount` int(11) DEFAULT NULL,
  `unit_price` int(10) unsigned NOT NULL,
  `total_price` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `bottom` (`bottom`),
  KEY `topping` (`topping`),
  KEY `fk_order_id` (`order`),
  CONSTRAINT `fk_order_id` FOREIGN KEY (`order`) REFERENCES `orders` (`id`),
  CONSTRAINT `order_items_ibfk_1` FOREIGN KEY (`bottom`) REFERENCES `bottoms` (`id`),
  CONSTRAINT `order_items_ibfk_2` FOREIGN KEY (`topping`) REFERENCES `toppings` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_items`
--

LOCK TABLES `order_items` WRITE;
/*!40000 ALTER TABLE `order_items` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orders` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user` int(10) unsigned NOT NULL,
  `total` int(10) unsigned NOT NULL,
  `comment` text,
  `status` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `user` (`user`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`user`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `presets`
--

DROP TABLE IF EXISTS `presets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `presets` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `description` text NOT NULL,
  `bottom` int(10) unsigned NOT NULL,
  `topping` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `bottom` (`bottom`),
  KEY `topping` (`topping`),
  CONSTRAINT `presets_ibfk_1` FOREIGN KEY (`bottom`) REFERENCES `bottoms` (`id`),
  CONSTRAINT `presets_ibfk_2` FOREIGN KEY (`topping`) REFERENCES `toppings` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `presets`
--

LOCK TABLES `presets` WRITE;
/*!40000 ALTER TABLE `presets` DISABLE KEYS */;
INSERT INTO `presets` VALUES (1,'Presets','Vestibulum ac odio faucibus, ornare justo at, laoreet augue. Nulla dapibus ut lacus quis posuere. Quisque suscipit libero sem, ut vulputate sem pellentesque sit amet. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.',1,1),(2,'Presets','Vestibulum ac odio faucibus, ornare justo at, laoreet augue. Nulla dapibus ut lacus quis posuere. Quisque suscipit libero sem, ut vulputate sem pellentesque sit amet. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.',1,1),(3,'Presets','Vestibulum ac odio faucibus, ornare justo at, laoreet augue. Nulla dapibus ut lacus quis posuere. Quisque suscipit libero sem, ut vulputate sem pellentesque sit amet. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.',1,1),(4,'Presets','Vestibulum ac odio faucibus, ornare justo at, laoreet augue. Nulla dapibus ut lacus quis posuere. Quisque suscipit libero sem, ut vulputate sem pellentesque sit amet. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.',1,1),(5,'Presets','Vestibulum ac odio faucibus, ornare justo at, laoreet augue. Nulla dapibus ut lacus quis posuere. Quisque suscipit libero sem, ut vulputate sem pellentesque sit amet. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.',1,1);
/*!40000 ALTER TABLE `presets` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `toppings`
--

DROP TABLE IF EXISTS `toppings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `toppings` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `description` text NOT NULL,
  `price` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `toppings`
--

LOCK TABLES `toppings` WRITE;
/*!40000 ALTER TABLE `toppings` DISABLE KEYS */;
INSERT INTO `toppings` VALUES (1,'Chocolate','Vestibulum ac odio faucibus, ornare justo at, laoreet augue. Nulla dapibus ut lacus quis posuere. Quisque suscipit libero sem, ut vulputate sem pellentesque sit amet. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.',500),(2,'Blueberry','Vestibulum ac odio faucibus, ornare justo at, laoreet augue. Nulla dapibus ut lacus quis posuere. Quisque suscipit libero sem, ut vulputate sem pellentesque sit amet. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.',500),(3,'Rasberry','Vestibulum ac odio faucibus, ornare justo at, laoreet augue. Nulla dapibus ut lacus quis posuere. Quisque suscipit libero sem, ut vulputate sem pellentesque sit amet. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.',500),(4,'Crispy','Vestibulum ac odio faucibus, ornare justo at, laoreet augue. Nulla dapibus ut lacus quis posuere. Quisque suscipit libero sem, ut vulputate sem pellentesque sit amet. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.',600),(5,'Strawberry','Vestibulum ac odio faucibus, ornare justo at, laoreet augue. Nulla dapibus ut lacus quis posuere. Quisque suscipit libero sem, ut vulputate sem pellentesque sit amet. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.',600),(6,'Rum/Rasin','Vestibulum ac odio faucibus, ornare justo at, laoreet augue. Nulla dapibus ut lacus quis posuere. Quisque suscipit libero sem, ut vulputate sem pellentesque sit amet. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.',700),(7,'Orange','Vestibulum ac odio faucibus, ornare justo at, laoreet augue. Nulla dapibus ut lacus quis posuere. Quisque suscipit libero sem, ut vulputate sem pellentesque sit amet. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.',800),(8,'Lemon','Vestibulum ac odio faucibus, ornare justo at, laoreet augue. Nulla dapibus ut lacus quis posuere. Quisque suscipit libero sem, ut vulputate sem pellentesque sit amet. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.',800),(9,'Blue cheese','Vestibulum ac odio faucibus, ornare justo at, laoreet augue. Nulla dapibus ut lacus quis posuere. Quisque suscipit libero sem, ut vulputate sem pellentesque sit amet. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.',900);
/*!40000 ALTER TABLE `toppings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `password` varchar(60) NOT NULL,
  `balance` int(10) unsigned NOT NULL DEFAULT '0',
  `role` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`,`username`,`email`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (4,'Thomas','tvestergaard@hotmail.com','$2a$10$hYjNfeJByPpIzPQ8Hs907ONaxWOiStSMYNAdKPg2i0VEgrQ6NGLBC',0,2);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-03-01 22:25:43
