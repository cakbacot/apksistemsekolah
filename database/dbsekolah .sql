-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 04, 2026 at 05:12 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `dbsekolah`
--

-- --------------------------------------------------------

--
-- Table structure for table `guru`
--

CREATE TABLE `guru` (
  `nip_g` varchar(20) NOT NULL,
  `kd_guru` varchar(10) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `password` varchar(12) NOT NULL,
  `tgl_lahir` date NOT NULL,
  `alamat` text NOT NULL,
  `no_telp` varchar(13) NOT NULL,
  `jkel` varchar(15) NOT NULL,
  `keahlian` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `guru`
--

INSERT INTO `guru` (`nip_g`, `kd_guru`, `nama`, `password`, `tgl_lahir`, `alamat`, `no_telp`, `jkel`, `keahlian`) VALUES
('12311', 'G0002', 'Daffa', '321321', '2025-01-06', 'Jalan jalan', '093213', 'Perempuan', 'Tata Boga'),
('12312', 'G0001', 'Rusdy', '123123', '2025-02-12', 'jalann', '08211094', 'Laki - lak', 'RPL'),
('321321', 'G0003', 'Agus', '801231', '2025-07-24', 'Jalannn', '083167', 'Perempuan', 'PBO');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_absen`
--

CREATE TABLE `tbl_absen` (
  `nisn` int(10) NOT NULL,
  `kelas` varchar(10) NOT NULL,
  `kehadiran` int(2) NOT NULL,
  `jumlah` int(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_jadwal`
--

CREATE TABLE `tbl_jadwal` (
  `id_mapel` varchar(8) NOT NULL,
  `nama_mapel` varchar(15) NOT NULL,
  `kd_guru` varchar(10) NOT NULL,
  `jam` time NOT NULL,
  `kelas` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_jadwal`
--

INSERT INTO `tbl_jadwal` (`id_mapel`, `nama_mapel`, `kd_guru`, `jam`, `kelas`) VALUES
('M0001', 'Pemrograman', 'G0001', '07:30:00', 'RPL01'),
('M0003', 'Matematika', 'G0001', '08:00:00', 'RPL01'),
('M0004', 'Masak Aer', 'G0003', '12:30:00', 'TBG02'),
('M0006', 'PBO', 'G0003', '09:29:00', 'RPL02');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_kelas`
--

CREATE TABLE `tbl_kelas` (
  `id_kelas` varchar(10) NOT NULL,
  `kelas` varchar(10) NOT NULL,
  `kd_guru` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_kelas`
--

INSERT INTO `tbl_kelas` (`id_kelas`, `kelas`, `kd_guru`) VALUES
('RPL01', '10-RPL', 'G0001'),
('RPL02', '11-RPL', 'G0003'),
('TBG01', '10-TBG', 'G0003'),
('TBG02', '11-TBG', 'G0002');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_report`
--

CREATE TABLE `tbl_report` (
  `id_report` varchar(15) NOT NULL,
  `jumlah` int(10) NOT NULL,
  `nama` varchar(30) NOT NULL,
  `kelas` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_siswa`
--

CREATE TABLE `tbl_siswa` (
  `nisn` varchar(10) NOT NULL,
  `nama` varchar(30) NOT NULL,
  `tgl_lahir` date NOT NULL,
  `alamat` text NOT NULL,
  `notelp` varchar(15) NOT NULL,
  `jkel` varchar(15) NOT NULL,
  `angkatan` varchar(4) NOT NULL,
  `nama_wali` varchar(25) NOT NULL,
  `no_wali` varchar(15) NOT NULL,
  `jurusan` varchar(20) NOT NULL,
  `kelas` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_siswa`
--

INSERT INTO `tbl_siswa` (`nisn`, `nama`, `tgl_lahir`, `alamat`, `notelp`, `jkel`, `angkatan`, `nama_wali`, `no_wali`, `jurusan`, `kelas`) VALUES
('2023435014', 'Rusdy', '2005-02-23', 'Jalan', '0821111', 'Perempuan', '1', 'Rusdyy', 'rrad', 'TBG', '11-TBG');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_transaksi`
--

CREATE TABLE `tbl_transaksi` (
  `no_transaksi` varchar(10) NOT NULL,
  `tgl` date NOT NULL,
  `nisn` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `nip` int(20) NOT NULL,
  `nama` varchar(25) NOT NULL,
  `password` varchar(20) NOT NULL,
  `lvl` varchar(15) NOT NULL,
  `status` varchar(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`nip`, `nama`, `password`, `lvl`, `status`) VALUES
(1, 'Rusdy', 'admin123', '1', 'Y');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `guru`
--
ALTER TABLE `guru`
  ADD PRIMARY KEY (`nip_g`),
  ADD UNIQUE KEY `kd_guru` (`kd_guru`),
  ADD UNIQUE KEY `kd_guru_2` (`kd_guru`),
  ADD UNIQUE KEY `kd_guru_3` (`kd_guru`),
  ADD KEY `kd_guru_4` (`kd_guru`);

--
-- Indexes for table `tbl_absen`
--
ALTER TABLE `tbl_absen`
  ADD PRIMARY KEY (`nisn`);

--
-- Indexes for table `tbl_jadwal`
--
ALTER TABLE `tbl_jadwal`
  ADD PRIMARY KEY (`id_mapel`);

--
-- Indexes for table `tbl_kelas`
--
ALTER TABLE `tbl_kelas`
  ADD PRIMARY KEY (`id_kelas`);

--
-- Indexes for table `tbl_report`
--
ALTER TABLE `tbl_report`
  ADD PRIMARY KEY (`id_report`);

--
-- Indexes for table `tbl_siswa`
--
ALTER TABLE `tbl_siswa`
  ADD PRIMARY KEY (`nisn`);

--
-- Indexes for table `tbl_transaksi`
--
ALTER TABLE `tbl_transaksi`
  ADD PRIMARY KEY (`no_transaksi`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`nip`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
