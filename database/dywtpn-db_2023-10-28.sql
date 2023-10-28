-- phpMyAdmin SQL Dump
-- version 4.4.7
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1:3306
-- Generation Time: Oct 28, 2023 at 12:53 AM
-- Server version: 5.5.43
-- PHP Version: 5.4.41

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `dywtpn`
--

-- --------------------------------------------------------

--
-- Table structure for table `games`
--

CREATE TABLE `games` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `ghost` tinyint(1) NOT NULL DEFAULT '0',
  `mins_played` int(11) NOT NULL,
  `path` varchar(300) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `games`
--

INSERT INTO `games` (`id`, `name`, `ghost`, `mins_played`, `path`) VALUES
(1, 'World of Warcraft', 0, 300, 'A'),
(2, 'Tibia', 1, 220, 'B'),
(3, 'mm', 0, 99, 'mmm'),
(4, 'GTA San Andreas', 0, 181, 'D:\\SteamLibrary\\steamapps\\common\\Grand Theft Auto San Andreas\\gta-sa.exe'),
(5, 'Euro Truck Simulator 2', 1, 5, 'E'),
(6, 'Dragon Age', 1, 120, 'F'),
(7, 'unjuegofantasma', 1, 16, 'fant'),
(8, 'unjuegonormal', 0, 101010, 'dasdas'),
(9, 'Prueba18.47', 1, 33, '1');

-- --------------------------------------------------------

--
-- Table structure for table `games_sessions_history`
--

CREATE TABLE `games_sessions_history` (
  `id` int(11) NOT NULL,
  `game_id` int(11) NOT NULL,
  `datetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `mins` int(11) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=159 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `games_sessions_history`
--

INSERT INTO `games_sessions_history` (`id`, `game_id`, `datetime`, `mins`) VALUES
(139, 1, '2023-10-27 17:54:01', 1),
(140, 4, '2023-10-27 17:58:18', 4),
(141, 4, '2023-10-27 17:58:30', 2),
(142, 4, '2023-10-27 18:35:20', 0),
(143, 4, '2023-10-27 18:35:55', 0),
(144, 2, '2023-10-27 18:36:24', 0),
(145, 4, '2023-10-27 18:37:08', 13),
(146, 4, '2023-10-27 18:37:30', 3),
(147, 4, '2023-10-27 18:37:43', 3),
(148, 4, '2023-10-27 18:38:46', 0),
(149, 4, '2023-10-27 18:40:30', 3),
(150, 6, '2023-10-27 19:56:19', 10),
(151, 5, '2023-10-27 20:10:23', 2),
(152, 9, '2023-10-27 23:18:32', 91),
(153, 9, '2023-10-27 23:32:00', 3),
(154, 9, '2023-10-27 23:33:30', 3),
(155, 4, '2023-10-27 23:35:05', 8),
(156, 4, '2023-10-27 23:35:12', 1),
(157, 9, '2023-10-27 23:47:20', 30),
(158, 9, '2023-10-27 23:47:44', 3);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `games`
--
ALTER TABLE `games`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- Indexes for table `games_sessions_history`
--
ALTER TABLE `games_sessions_history`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id` (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `games`
--
ALTER TABLE `games`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=10;
--
-- AUTO_INCREMENT for table `games_sessions_history`
--
ALTER TABLE `games_sessions_history`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=159;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
