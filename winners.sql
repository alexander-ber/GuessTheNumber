-- phpMyAdmin SQL Dump
-- version 4.8.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 08, 2018 at 03:05 PM
-- Server version: 5.7.19
-- PHP Version: 7.1.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `game`
--

-- --------------------------------------------------------

--
-- Table structure for table `winners`
--

CREATE TABLE `winners` (
  `id` int(11) NOT NULL,
  `name` varchar(250) NOT NULL,
  `attempts` int(11) NOT NULL,
  `date` varchar(250) NOT NULL,
  `log` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `winners`
--

INSERT INTO `winners` (`id`, `name`, `attempts`, `date`, `log`) VALUES
(69, '2231434', 13, '2018-06-08 17:05:47', '[{\"logID\":1,\"guess\":[1,2,3,4],\"result\":[0]},{\"logID\":2,\"guess\":[5,6,7,8],\"result\":[0,1,0]},{\"logID\":3,\"guess\":[5,6,7,8],\"result\":[0,1,0]},{\"logID\":4,\"guess\":[5,6,7,8],\"result\":[0,1,0]},{\"logID\":5,\"guess\":[5,6,7,8],\"result\":[0,1,0]},{\"logID\":6,\"guess\":[5,1,2,3],\"result\":[0,0]},{\"logID\":7,\"guess\":[1,2,3,8],\"result\":[0,0]},{\"logID\":8,\"guess\":[1,6,2,3],\"result\":[1,0]},{\"logID\":9,\"guess\":[8,6,1,2],\"result\":[0,1,0]},{\"logID\":10,\"guess\":[7,6,1,2],\"result\":[1,0]},{\"logID\":11,\"guess\":[8,6,1,5],\"result\":[0,1,1]},{\"logID\":12,\"guess\":[8,6,2,5],\"result\":[0,1,0,1]},{\"logID\":13,\"guess\":[2,6,8,5],\"result\":[1,1,1,1]}]'),
(71, 'Alexander Berezhnoy', 7, '2018-06-08 18:02:43', '[{\"logID\":1,\"guess\":[1,3,5,7],\"result\":[0,0]},{\"logID\":2,\"guess\":[8,9,0,2],\"result\":[0]},{\"logID\":3,\"guess\":[1,5,2,8],\"result\":[0,0,1]},{\"logID\":4,\"guess\":[1,6,2,8],\"result\":[0,1,1]},{\"logID\":5,\"guess\":[1,6,5,8],\"result\":[0,1,0,1]},{\"logID\":6,\"guess\":[6,1,5,8],\"result\":[0,0,0,1]},{\"logID\":7,\"guess\":[5,6,1,8],\"result\":[1,1,1,1]}]');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `winners`
--
ALTER TABLE `winners`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `winners`
--
ALTER TABLE `winners`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=72;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
