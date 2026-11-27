-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 27, 2026 at 09:55 AM
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
-- Database: `dbschool_test`
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
  `tgl_lahir` date DEFAULT NULL,
  `alamat` text DEFAULT NULL,
  `no_telp` varchar(13) NOT NULL,
  `jkel` enum('Laki - laki','Perempuan') DEFAULT 'Laki - laki',
  `keahlian` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `guru`
--

INSERT INTO `guru` (`nip_g`, `kd_guru`, `nama`, `password`, `tgl_lahir`, `alamat`, `no_telp`, `jkel`, `keahlian`) VALUES
('1', 'GUR006', 'Dedi Sulistyo, M.Si', '1234567', '1973-12-30', 'Jl. Mawar No. 54, Bekasi', '081789012345', 'Laki - laki', 'Fisika'),
('197503122001011001', 'GUR001', 'Budi Santoso, M.Kom', 'passBudi12', '1975-03-12', 'Jl. Diponegoro No. 45, Jakarta', '081234567890', 'Laki - laki', 'Rekayasa Perangkat Lunak'),
('197610142002031002', 'GUR010', 'Bambang Hermawan, S.H', 'bamsh76', '1976-10-14', 'Jl. Flamboyan No. 33, Tangerang', '082223456789', 'Laki - laki', 'PPKn'),
('197704022001012002', 'GUR019', 'Endah Sulistri, M.Pd', 'endah77pd', '1977-04-02', 'Jl. Manyar No. 11, Depok', '083223456789', 'Perempuan', 'Biologi'),
('197805232003021002', 'GUR002', 'Hendra Wijaya, S.T', 'hen78wij', '1978-05-23', 'Jl. Sudirman No. 12, Bekasi', '081345678901', 'Laki - laki', 'Teknik Komputer Jaringan'),
('197908032004012002', 'GUR015', 'Sri Wahyuni, M.Si', 'sri79msi', '1979-08-03', 'Jl. Elang No. 8, Tangerang', '082778901234', 'Perempuan', 'Kimia'),
('198004142005011004', 'GUR004', 'Rizky Ramadhan, M.Pd', 'rizkypd80', '1980-04-14', 'Jl. Pemuda No. 17, Depok', '081567890123', 'Laki - laki', 'Bahasa Indonesia'),
('198103292006042001', 'GUR011', 'Siti Aminah, M.Pd', 'siti81mpd', '1981-03-29', 'Jl. Teratai No. 15, Jakarta', '082334567890', 'Perempuan', 'Bahasa Inggris'),
('198211052008011003', 'GUR003', 'Ahmad Fauzi, S.Sn', 'fauzi82mm', '1982-11-05', 'Jl. Gajah Mada No. 88, Bogor', '081456789012', 'Laki - laki', 'Multimedia'),
('198306172009011003', 'GUR009', 'Anwar Sadat, M.A', 'anwar83ma', '1983-06-17', 'Jl. Anggrek No. 12, Bogor', '082112345678', 'Laki - laki', 'Pendidikan Agama'),
('198309242008022001', 'GUR018', 'Nurlaila, S.E', 'laila83se', '1983-09-24', 'Jl. Kenari No. 2, Bogor', '083112345678', 'Perempuan', 'Produk Kreatif & Kewiraus'),
('198405072009022003', 'GUR012', 'Dewi Lestari, S.Kom', 'dewi84kom', '1984-05-07', 'Jl. Kamboja No. 40, Bekasi', '082445678901', 'Perempuan', 'Basis Data'),
('198509082010021005', 'GUR005', 'Eko Prasetyo, S.Pd', 'ekoP985', '1985-09-08', 'Jl. Melati No. 3, Tangerang', '081678901234', 'Laki - laki', 'Matematika'),
('198601202010032001', 'GUR016', 'Mega Utami, S.Pd', 'mega86pd', '1986-01-20', 'Jl. Merak No. 14, Jakarta', '082889012345', 'Perempuan', 'Sejarah Indonesia'),
('198710182012012002', 'GUR013', 'Rina Wati, S.T', 'rinawati87', '1987-10-18', 'Jl. Tanjung No. 5, Bogor', '082556789012', 'Perempuan', 'Administrasi Infrastruktu'),
('198807222015011002', 'GUR007', 'Andika Pratama, S.Kom', 'andk88kom', '1988-07-22', 'Jl. Kenanga No. 21, Jakarta', '081890123456', 'Laki - laki', 'Pemrograman Dasar'),
('198902252014022001', 'GUR014', 'Diana Puspita, S.Sn', 'diana89sn', '1989-02-25', 'Jl. Kancil No. 22, Depok', '082667890123', 'Perempuan', 'Desain Grafis'),
('199001152018021001', 'GUR008', 'Fajar Hidayat, S.T', 'fajr90st', '1990-01-15', 'Jl. Dahlia No. 9, Depok', '081901234567', 'Laki - laki', 'Sistem Komputer'),
('199111182019032002', 'GUR020', 'Kartika Sari, S.Kom', 'tika91kom', '1991-11-18', 'Jl. Tulip No. 5, Tangerang', '083334567890', 'Perempuan', 'Pemrograman Web'),
('199207122020012003', 'GUR017', 'Fitriani, S.Pd', 'fitri92pd', '1992-07-12', 'Jl. Rajawali No. 19, Bekasi', '082990123456', 'Perempuan', 'Bimbingan Konseling');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_absen`
--

CREATE TABLE `tbl_absen` (
  `id_absen` int(11) NOT NULL,
  `nisn` varchar(10) NOT NULL,
  `tanggal` date NOT NULL,
  `pertemuan_ke` int(11) NOT NULL,
  `status_hadir` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_absen`
--

INSERT INTO `tbl_absen` (`id_absen`, `nisn`, `tanggal`, `pertemuan_ke`, `status_hadir`) VALUES
(1, '0081000001', '2026-06-27', 1, 1),
(2, '0081000005', '2026-06-27', 1, 1),
(3, '0081000030', '2026-06-27', 1, 0),
(4, '0081000040', '2026-06-27', 1, 1),
(5, '0081000051', '2026-06-27', 1, 1),
(6, '0081000055', '2026-06-27', 1, 0),
(7, '0081000080', '2026-06-27', 1, 1),
(8, '0081000090', '2026-06-27', 1, 1),
(9, '0061000019', '2026-10-09', 1, 1),
(10, '0061000029', '2026-10-09', 1, 0),
(11, '0061000043', '2026-10-09', 1, 0),
(12, '0061000069', '2026-10-09', 1, 1),
(13, '0061000079', '2026-10-09', 1, 0),
(14, '0061000093', '2026-10-09', 1, 0),
(15, '0061000016', '2026-10-09', 1, 0),
(16, '0061000017', '2026-10-09', 1, 0),
(17, '0061000018', '2026-10-09', 1, 0),
(19, '0061000020', '2026-10-09', 1, 0),
(20, '0061000027', '2026-10-09', 1, 0),
(21, '0061000028', '2026-10-09', 1, 0),
(23, '0061000036', '2026-10-09', 1, 0),
(24, '0061000037', '2026-10-09', 1, 0),
(25, '0061000038', '2026-10-09', 1, 0),
(27, '0061000044', '2026-10-09', 1, 0),
(28, '0061000047', '2026-10-09', 1, 0),
(29, '0061000050', '2026-10-09', 1, 0),
(30, '0061000066', '2026-10-09', 1, 0),
(31, '0061000067', '2026-10-09', 1, 0),
(32, '0061000068', '2026-10-09', 1, 0),
(34, '0061000070', '2026-10-09', 1, 0),
(35, '0061000077', '2026-10-09', 1, 0),
(36, '0061000078', '2026-10-09', 1, 0),
(38, '0061000086', '2026-10-09', 1, 0),
(39, '0061000087', '2026-10-09', 1, 0),
(40, '0061000088', '2026-10-09', 1, 0),
(42, '0061000094', '2026-10-09', 1, 0),
(43, '0061000097', '2026-10-09', 1, 0),
(44, '0061000100', '2026-10-09', 1, 0),
(45, '0071000009', '2026-10-09', 1, 0),
(46, '0071000010', '2026-10-09', 1, 0),
(47, '0071000011', '2026-10-09', 1, 0),
(48, '0071000012', '2026-10-09', 1, 0),
(49, '0071000013', '2026-10-09', 1, 0),
(50, '0071000014', '2026-10-09', 1, 0),
(51, '0071000015', '2026-10-09', 1, 0),
(52, '0071000024', '2026-10-09', 1, 0),
(53, '0071000025', '2026-10-09', 1, 0),
(54, '0071000026', '2026-10-09', 1, 0),
(55, '0071000033', '2026-10-09', 1, 0),
(56, '0071000034', '2026-10-09', 1, 0),
(57, '0071000035', '2026-10-09', 1, 0),
(58, '0071000041', '2026-10-09', 1, 0),
(59, '0071000042', '2026-10-09', 1, 0),
(60, '0071000046', '2026-10-09', 1, 0),
(61, '0071000049', '2026-10-09', 1, 0),
(62, '0071000059', '2026-10-09', 1, 0),
(63, '0071000060', '2026-10-09', 1, 0),
(64, '0071000061', '2026-10-09', 1, 0),
(65, '0071000062', '2026-10-09', 1, 0),
(66, '0071000063', '2026-10-09', 1, 0),
(67, '0071000064', '2026-10-09', 1, 0),
(68, '0071000065', '2026-10-09', 1, 0),
(69, '0071000074', '2026-10-09', 1, 0),
(70, '0071000075', '2026-10-09', 1, 0),
(71, '0071000076', '2026-10-09', 1, 0),
(72, '0071000083', '2026-10-09', 1, 0),
(73, '0071000084', '2026-10-09', 1, 0),
(74, '0071000085', '2026-10-09', 1, 0),
(75, '0071000091', '2026-10-09', 1, 0),
(76, '0071000092', '2026-10-09', 1, 0),
(77, '0071000096', '2026-10-09', 1, 0),
(78, '0071000099', '2026-10-09', 1, 0),
(79, '0081000001', '2026-10-09', 1, 0),
(80, '0081000002', '2026-10-09', 1, 0),
(81, '0081000003', '2026-10-09', 1, 0),
(82, '0081000004', '2026-10-09', 1, 0),
(83, '0081000005', '2026-10-09', 1, 0),
(84, '0081000006', '2026-10-09', 1, 0),
(85, '0081000007', '2026-10-09', 1, 0),
(86, '0081000008', '2026-10-09', 1, 0),
(87, '0081000021', '2026-10-09', 1, 0),
(88, '0081000022', '2026-10-09', 1, 0),
(89, '0081000023', '2026-10-09', 1, 0),
(90, '0081000030', '2026-10-09', 1, 0),
(91, '0081000031', '2026-10-09', 1, 0),
(92, '0081000032', '2026-10-09', 1, 0),
(93, '0081000039', '2026-10-09', 1, 0),
(94, '0081000040', '2026-10-09', 1, 0),
(95, '0081000045', '2026-10-09', 1, 0),
(96, '0081000048', '2026-10-09', 1, 0),
(97, '0081000051', '2026-10-09', 1, 0),
(98, '0081000052', '2026-10-09', 1, 0),
(99, '0081000053', '2026-10-09', 1, 0),
(100, '0081000054', '2026-10-09', 1, 0),
(101, '0081000055', '2026-10-09', 1, 0),
(102, '0081000056', '2026-10-09', 1, 0),
(103, '0081000057', '2026-10-09', 1, 0),
(104, '0081000058', '2026-10-09', 1, 0),
(105, '0081000071', '2026-10-09', 1, 0),
(106, '0081000072', '2026-10-09', 1, 0),
(107, '0081000073', '2026-10-09', 1, 0),
(108, '0081000080', '2026-10-09', 1, 0),
(109, '0081000081', '2026-10-09', 1, 0),
(110, '0081000082', '2026-10-09', 1, 0),
(111, '0081000089', '2026-10-09', 1, 0),
(112, '0081000090', '2026-10-09', 1, 0),
(113, '0081000095', '2026-10-09', 1, 0),
(114, '0081000098', '2026-10-09', 1, 0),
(127, '0061000019', '2026-10-13', 1, 0),
(128, '0061000029', '2026-10-13', 1, 1),
(129, '0061000043', '2026-10-13', 1, 1),
(130, '0061000069', '2026-10-13', 1, 0),
(131, '0061000079', '2026-10-13', 1, 0),
(132, '0061000093', '2026-10-13', 1, 0);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_absen_guru`
--

CREATE TABLE `tbl_absen_guru` (
  `id_absen` int(11) NOT NULL,
  `kd_guru` varchar(10) DEFAULT NULL,
  `tgl` date DEFAULT NULL,
  `kehadiran` int(1) DEFAULT 0,
  `keterangan` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_absen_guru`
--

INSERT INTO `tbl_absen_guru` (`id_absen`, `kd_guru`, `tgl`, `kehadiran`, `keterangan`) VALUES
(1, 'GUR001', '2026-06-27', 1, '');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_absen_user`
--

CREATE TABLE `tbl_absen_user` (
  `id_absen` int(11) NOT NULL,
  `nip` int(20) DEFAULT NULL,
  `tgl` date DEFAULT NULL,
  `kehadiran` int(1) DEFAULT 0,
  `keterangan` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_jadwal`
--

CREATE TABLE `tbl_jadwal` (
  `id_mapel` varchar(10) NOT NULL,
  `nama_mapel` varchar(15) NOT NULL,
  `kd_guru` varchar(10) NOT NULL,
  `jam` time DEFAULT NULL,
  `kelas` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_jadwal`
--

INSERT INTO `tbl_jadwal` (`id_mapel`, `nama_mapel`, `kd_guru`, `jam`, `kelas`) VALUES
('MP001', 'Pemrog Web', 'GUR001', '07:30:00', 'KLS001'),
('MP002', 'Matematika', 'GUR005', '09:45:00', 'KLS001'),
('MP003', 'Fisika', 'GUR006', '07:30:00', 'KLS002'),
('MP004', 'Sist Komputer', 'GUR008', '09:45:00', 'KLS002'),
('MP005', 'Desain Grafis', 'GUR014', '07:30:00', 'KLS003'),
('MP006', 'B. Indonesia', 'GUR004', '10:30:00', 'KLS003'),
('MP007', 'Dasar TKR', 'GUR004', '07:30:00', 'KLS004'),
('MP008', 'Kimia', 'GUR015', '13:00:00', 'KLS004'),
('MP009', 'P_Dasar RPL', 'GUR007', '07:30:00', 'KLS005'),
('MP010', 'Basis Data', 'GUR012', '10:30:00', 'KLS005'),
('MP011', 'B. Inggris', 'GUR011', '13:00:00', 'KLS005'),
('MP012', 'Admin Jaringan', 'GUR002', '07:30:00', 'KLS006'),
('MP013', 'Infra Jaringan', 'GUR013', '10:30:00', 'KLS006'),
('MP014', 'Multimedia 2D', 'GUR003', '07:30:00', 'KLS007'),
('MP015', 'Agama Islam', 'GUR009', '10:30:00', 'KLS007'),
('MP016', 'Sasis TKR', 'GUR006', '07:30:00', 'KLS008'),
('MP017', 'PPKn', 'GUR010', '10:30:00', 'KLS008'),
('MP018', 'Web Lanjut', 'GUR020', '07:30:00', 'KLS009'),
('MP019', 'Produk Kreatif', 'GUR018', '10:30:00', 'KLS009'),
('MP020', 'Keamanan Jari', 'GUR002', '07:30:00', 'KLS010'),
('MP021', 'Sejarah Indo', 'GUR016', '13:00:00', 'KLS010'),
('MP022', 'Animasi 3D', 'GUR003', '07:30:00', 'KLS011'),
('MP023', 'Bimb Konseling', 'GUR017', '11:15:00', 'KLS011'),
('MP024', 'Mesin TKR', 'GUR012', '07:30:00', 'KLS012'),
('MP025', 'Biologi', 'GUR019', '10:30:00', 'KLS012');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_kelas`
--

CREATE TABLE `tbl_kelas` (
  `id_kelas` varchar(10) NOT NULL,
  `kelas` varchar(20) NOT NULL,
  `kd_guru` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_kelas`
--

INSERT INTO `tbl_kelas` (`id_kelas`, `kelas`, `kd_guru`) VALUES
('KLS001', 'X - RPL 1', 'GUR001'),
('KLS002', 'X - TKJ 1', 'GUR002'),
('KLS003', 'X - MM 1', 'GUR003'),
('KLS004', 'X - TKR 1', 'GUR004'),
('KLS005', 'XI - RPL 1', 'GUR007'),
('KLS006', 'XI - TKJ 1', 'GUR008'),
('KLS007', 'XI - MM 1', 'GUR011'),
('KLS008', 'XI - TKR 1', 'GUR005'),
('KLS009', 'XII - RPL 1', 'GUR012'),
('KLS010', 'XII - TKJ 1', 'GUR013'),
('KLS011', 'XII - MM 1', 'GUR014'),
('KLS012', 'XII - TKR 1', 'GUR006');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_nilai`
--

CREATE TABLE `tbl_nilai` (
  `id_nilai` int(11) NOT NULL,
  `nisn` varchar(15) NOT NULL,
  `id_mapel` varchar(10) NOT NULL,
  `kd_guru` varchar(10) NOT NULL,
  `jenis_nilai` varchar(20) NOT NULL,
  `skor` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_pelanggaran`
--

CREATE TABLE `tbl_pelanggaran` (
  `id_pelanggaran` int(11) NOT NULL,
  `nisn` varchar(15) NOT NULL,
  `tgl_pelanggaran` date NOT NULL,
  `kategori_pelanggaran` varchar(255) NOT NULL,
  `keterangan` text NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_pelanggaran`
--

INSERT INTO `tbl_pelanggaran` (`id_pelanggaran`, `nisn`, `tgl_pelanggaran`, `kategori_pelanggaran`, `keterangan`, `created_at`) VALUES
(4, '0061000016', '2026-10-16', 'Merokok', 'asdasd', '2026-10-16 12:45:11'),
(5, '0061000019', '2026-10-16', 'Makan di Kelas', 'aasd', '2026-10-16 13:23:37'),
(6, '0061000018', '2026-05-20', 'Tidak Memakai Seragam', 'asdasd', '2026-10-16 13:24:30');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_report`
--

CREATE TABLE `tbl_report` (
  `id_report` varchar(15) NOT NULL,
  `jumlah` int(3) DEFAULT NULL,
  `nama` varchar(30) DEFAULT NULL,
  `kelas` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_siswa`
--

CREATE TABLE `tbl_siswa` (
  `nisn` varchar(15) NOT NULL,
  `nama` varchar(30) NOT NULL,
  `tgl_lahir` date DEFAULT NULL,
  `alamat` text DEFAULT NULL,
  `notelp` varchar(15) NOT NULL,
  `jkel` enum('Laki - laki','Perempuan') DEFAULT 'Laki - laki',
  `angkatan` varchar(4) NOT NULL,
  `nama_wali` varchar(30) NOT NULL,
  `no_wali` varchar(15) NOT NULL,
  `jurusan` varchar(25) NOT NULL,
  `kelas` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_siswa`
--

INSERT INTO `tbl_siswa` (`nisn`, `nama`, `tgl_lahir`, `alamat`, `notelp`, `jkel`, `angkatan`, `nama_wali`, `no_wali`, `jurusan`, `kelas`) VALUES
('0061000016', 'Panji Lesmana', '2006-01-20', 'Jl. Pemuda No. 16', '081200001141', 'Laki - laki', '2022', 'Indra Lesmana', '081200001142', 'RPL', 'KLS009'),
('0061000017', 'Rafi Alamsyah', '2006-07-12', 'Jl. Diponegoro No. 17', '081200001143', 'Laki - laki', '2022', 'Cecep Alamsyah', '081200001144', 'TKJ', 'KLS010'),
('0061000018', 'Satria Yudha', '2006-09-24', 'Jl. Hasanuddin No. 18', '081200001145', 'Laki - laki', '2022', 'Gatot Yudha', '081200001146', 'Multimedia', 'KLS011'),
('0061000019', 'Taufik Hidayat', '2006-04-02', 'Jl. Imam Bonjol No. 19', '081200001147', 'Laki - laki', '2022', 'Mulyadi Hidayat', '081200001148', 'TKR', 'KLS012'),
('0061000020', 'Vino Bastian', '2006-11-18', 'Jl. Teuku Umar No. 20', '081200001149', 'Laki - laki', '2022', 'Roni Bastian', '081200001150', 'RPL', 'KLS009'),
('0061000027', 'Dandi Saputra', '2006-05-22', 'Jl. Gatot Subroto No. 27', '081200001163', 'Laki - laki', '2022', 'Kusnadi Saputra', '081200001164', 'TKJ', 'KLS010'),
('0061000028', 'Eka Putra', '2006-10-31', 'Jl. Hayam Wuruk No. 28', '081200001165', 'Laki - laki', '2022', 'Sudrajat Putra', '081200001166', 'Multimedia', 'KLS011'),
('0061000029', 'Fahmi Aziz', '2006-08-14', 'Jl. MH Thamrin No. 29', '081200001167', 'Laki - laki', '2022', 'Abdul Aziz', '081200001168', 'TKR', 'KLS012'),
('0061000036', 'Mahendra Siregar', '2006-06-30', 'Jl. Slipi No. 36', '081200001181', 'Laki - laki', '2022', 'Darwin Siregar', '081200001182', 'RPL', 'KLS009'),
('0061000037', 'Nanda Pratama', '2006-12-14', 'Jl. Tomang No. 37', '081200001183', 'Laki - laki', '2022', 'Rian Pratama', '081200001184', 'TKJ', 'KLS010'),
('0061000038', 'Ovan Reza', '2006-03-05', 'Jl. Grogol No. 38', '081200001185', 'Laki - laki', '2022', 'Dedi Reza', '081200001186', 'Multimedia', 'KLS011'),
('0061000043', 'Umar Syarif', '2006-11-15', 'Jl. Rawamangun No. 43', '081200001195', 'Laki - laki', '2022', 'Malik Syarif', '081200001196', 'TKR', 'KLS012'),
('0061000044', 'Vicky Nitinegoro', '2006-04-27', 'Jl. Pramuka No. 44', '081200001197', 'Laki - laki', '2022', 'Sony Nitinegoro', '081200001198', 'RPL', 'KLS009'),
('0061000047', 'Zulfikar Ali', '2006-02-08', 'Jl. Cikini No. 47', '081200001203', 'Laki - laki', '2022', 'Hasan Ali', '081200001204', 'TKJ', 'KLS010'),
('0061000050', 'Dewa Budjana', '2006-05-19', 'Jl. Setiabudi No. 50', '081200001209', 'Laki - laki', '2022', 'Nyoman Budjana', '081200001210', 'Multimedia', 'KLS011'),
('0061000066', 'Prilly Latuconsina', '2006-02-15', 'Jl. Merdeka No. 66', '081200001241', 'Perempuan', '2022', 'Rizal Latuconsina', '081200001242', 'RPL', 'KLS009'),
('0061000067', 'Rachel Vennya', '2006-06-23', 'Jl. Pemuda No. 67', '081200001243', 'Perempuan', '2022', 'Andrea Vennya', '081200001244', 'TKJ', 'KLS010'),
('0061000068', 'Siti Badriah', '2006-11-01', 'Jl. Diponegoro No. 68', '081200001245', 'Perempuan', '2022', 'Muhammad Badriah', '081200001246', 'Multimedia', 'KLS011'),
('0061000069', 'Titi Kamal', '2006-03-19', 'Jl. Hasanuddin No. 69', '081200001247', 'Perempuan', '2022', 'Kamal Mustafa', '081200001248', 'TKR', 'KLS012'),
('0061000070', 'Vanesha Prescilla', '2006-10-25', 'Jl. Imam Bonjol No. 70', '081200001249', 'Perempuan', '2022', 'Iwan Prescilla', '081200001250', 'RPL', 'KLS009'),
('0061000077', 'Dewi Persik', '2006-12-16', 'Jl. Ahmad Yani No. 77', '081200001263', 'Perempuan', '2022', 'Adil Persik', '081200001264', 'TKJ', 'KLS010'),
('0061000078', 'Esther Anastasia', '2006-08-04', 'Jl. Gatot Subroto No. 78', '081200001265', 'Perempuan', '2022', 'Doni Anastasia', '081200001266', 'Multimedia', 'KLS011'),
('0061000079', 'Febby Rastanty', '2006-02-01', 'Jl. Hayam Wuruk No. 79', '081200001267', 'Perempuan', '2022', 'Raston Rastanty', '081200001268', 'TKR', 'KLS012'),
('0061000086', 'Maudy Effrosina', '2006-11-17', 'Jl. Senayan No. 86', '081200001281', 'Perempuan', '2022', 'Ferdi Effrosina', '081200001282', 'RPL', 'KLS009'),
('0061000087', 'Nia Ramadhani', '2006-04-16', 'Jl. Slipi No. 87', '081200001283', 'Perempuan', '2022', 'Priya Ramadhani', '081200001284', 'TKJ', 'KLS010'),
('0061000088', 'Olla Ramlan', '2006-02-15', 'Jl. Tomang No. 88', '081200001285', 'Perempuan', '2022', 'Ramlan Ramlan', '081200001286', 'Multimedia', 'KLS011'),
('0061000093', 'Titi DJ', '2006-05-27', 'Jl. Kelapa Gading No. 93', '081200001295', 'Perempuan', '2022', 'John DJ', '081200001296', 'TKR', 'KLS012'),
('0061000094', 'Ussy Sulistiawaty', '2006-07-13', 'Jl. Rawamangun No. 94', '081200001297', 'Perempuan', '2022', 'Sulaeman Sulistiawaty', '081200001298', 'RPL', 'KLS009'),
('0061000097', 'Yuni Shara', '2006-06-03', 'Jl. Salemba No. 97', '081200001303', 'Perempuan', '2022', 'Trenggono', '081200001304', 'TKJ', 'KLS010'),
('0061000100', 'Citra Scholastika', '2006-03-10', 'Jl. Merdeka No. 100', '081200001309', 'Perempuan', '2022', 'Yuwono Scholastika', '081200001310', 'Multimedia', 'KLS011'),
('0071000009', 'Ilham Syahputra', '2007-02-25', 'Jl. Kartini No. 9', '081200001127', 'Laki - laki', '2023', 'Iwan Syahputra', '081200001128', 'RPL', 'KLS005'),
('0071000010', 'Jaka Perkasa', '2007-11-11', 'Jl. Pattimura No. 10', '081200001129', 'Laki - laki', '2023', 'Bambang Perkasa', '081200001130', 'TKJ', 'KLS006'),
('0071000011', 'Kevin Sanjaya', '2007-06-17', 'Jl. Ki Hajar No. 11', '081200001131', 'Laki - laki', '2023', 'Yanto Sanjaya', '081200001132', 'Multimedia', 'KLS007'),
('0071000012', 'Lucky Wijaya', '2007-08-03', 'Jl. Siliwangi No. 12', '081200001133', 'Laki - laki', '2023', 'Asrul Wijaya', '081200001134', 'TKR', 'KLS008'),
('0071000013', 'Muhammad Rizky', '2007-03-29', 'Jl. Sudirman No. 13', '081200001135', 'Laki - laki', '2023', 'Ahmad Rizky', '081200001136', 'RPL', 'KLS005'),
('0071000014', 'Naufal Abdi', '2007-10-14', 'Jl. Thamrin No. 14', '081200001137', 'Laki - laki', '2023', 'Dedi Abdi', '081200001138', 'TKJ', 'KLS006'),
('0071000015', 'Oki Saputra', '2007-05-07', 'Jl. Merdeka No. 15', '081200001139', 'Laki - laki', '2023', 'Surya Saputra', '081200001140', 'Multimedia', 'KLS007'),
('0071000024', 'Akbar Ramadhan', '2007-09-15', 'Jl. Siliwangi No. 24', '081200001157', 'Laki - laki', '2023', 'Soleh Ramadhan', '081200001158', 'TKR', 'KLS008'),
('0071000025', 'Bima Sakti', '2007-03-10', 'Jl. Pahlawan No. 25', '081200001159', 'Laki - laki', '2023', 'Trisno Sakti', '081200001160', 'RPL', 'KLS005'),
('0071000026', 'Candra Wijaya', '2007-12-01', 'Jl. Ahmad Yani No. 26', '081200001161', 'Laki - laki', '2023', 'Dodi Wijaya', '081200001162', 'TKJ', 'KLS006'),
('0071000033', 'Joni Iskandar', '2007-07-19', 'Jl. Setiabudi No. 33', '081200001175', 'Laki - laki', '2023', 'Ali Iskandar', '081200001176', 'Multimedia', 'KLS007'),
('0071000034', 'Kiki Amara', '2007-09-02', 'Jl. Kemang No. 34', '081200001177', 'Laki - laki', '2023', 'Ruslan Amara', '081200001178', 'TKR', 'KLS008'),
('0071000035', 'Lutfi Hakim', '2007-02-12', 'Jl. Senayan No. 35', '081200001179', 'Laki - laki', '2023', 'Lukman Hakim', '081200001180', 'RPL', 'KLS005'),
('0071000041', 'Soni Wicaksono', '2007-08-18', 'Jl. Sunter No. 41', '081200001191', 'Laki - laki', '2023', 'Joko Wicaksono', '081200001192', 'TKJ', 'KLS006'),
('0071000042', 'Tegar Beriman', '2007-01-09', 'Jl. Kelapa Gading No. 42', '081200001193', 'Laki - laki', '2023', 'Iman Santoso', '081200001194', 'Multimedia', 'KLS007'),
('0071000046', 'Yayan Ruhian', '2007-12-25', 'Jl. Salemba No. 46', '081200001201', 'Laki - laki', '2023', 'Cecep Ruhian', '081200001202', 'TKR', 'KLS008'),
('0071000049', 'Cahyo Utomo', '2007-03-14', 'Jl. Kuningan No. 49', '081200001207', 'Laki - laki', '2023', 'Dwi Utomo', '081200001208', 'RPL', 'KLS005'),
('0071000059', 'Indah Permatasari', '2007-03-05', 'Jl. Kelapa Gading No. 59', '081200001227', 'Perempuan', '2023', 'Nasiruddin', '081200001228', 'RPL', 'KLS005'),
('0071000060', 'Jessica Mila', '2007-12-14', 'Jl. Rawamangun No. 60', '081200001229', 'Perempuan', '2023', 'Bambang Mila', '081200001230', 'TKJ', 'KLS006'),
('0071000061', 'Karin Novilda', '2007-07-21', 'Jl. Pramuka No. 61', '081200001231', 'Perempuan', '2023', 'Sulaiman Novilda', '081200001232', 'Multimedia', 'KLS007'),
('0071000062', 'Lesti Kejora', '2007-08-09', 'Jl. Matraman No. 62', '081200001233', 'Perempuan', '2023', 'Endang Mulyana', '081200001234', 'TKR', 'KLS008'),
('0071000063', 'Maudy Ayunda', '2007-04-24', 'Jl. Salemba No. 63', '081200001235', 'Perempuan', '2023', 'Didit Jasmedi', '081200001236', 'RPL', 'KLS005'),
('0071000064', 'Nabila Syakieb', '2007-10-18', 'Jl. Sudirman No. 64', '081200001237', 'Perempuan', '2023', 'Syakieb Ali', '081200001238', 'TKJ', 'KLS006'),
('0071000065', 'Olivia Jensen', '2007-05-11', 'Jl. Thamrin No. 65', '081200001239', 'Perempuan', '2023', 'Benny Jensen', '081200001240', 'Multimedia', 'KLS007'),
('0071000074', 'Aura Kasih', '2007-02-26', 'Jl. Ki Hajar No. 74', '081200001257', 'Perempuan', '2023', 'Junaedi Kasih', '081200001258', 'TKR', 'KLS008'),
('0071000075', 'Bunga Citra Lestari', '2007-03-22', 'Jl. Siliwangi No. 75', '081200001259', 'Perempuan', '2023', 'Muchlis Lestari', '081200001260', 'RPL', 'KLS005'),
('0071000076', 'Chelsea Olivia', '2007-07-29', 'Jl. Pahlawan No. 76', '081200001261', 'Perempuan', '2023', 'Yakub Olivia', '081200001262', 'TKJ', 'KLS006'),
('0071000083', 'Juwanita Lestari', '2007-01-19', 'Jl. Kuningan No. 83', '081200001275', 'Perempuan', '2023', 'Dadang Lestari', '081200001276', 'Multimedia', 'KLS007'),
('0071000084', 'Kartika Putri', '2007-01-20', 'Jl. Setiabudi No. 84', '081200001277', 'Perempuan', '2023', 'Ibnu Putri', '081200001278', 'TKR', 'KLS008'),
('0071000085', 'Luna Maya', '2007-08-26', 'Jl. Kemang No. 85', '081200001279', 'Perempuan', '2023', 'Uut Maya', '081200001280', 'RPL', 'KLS005'),
('0071000091', 'Ririn Dwi Ariyanti', '2007-11-06', 'Jl. Ancol No. 91', '081200001291', 'Perempuan', '2023', 'Sudirman Ariyanti', '081200001292', 'TKJ', 'KLS006'),
('0071000092', 'Shandy Aulia', '2007-06-23', 'Jl. Sunter No. 92', '081200001293', 'Perempuan', '2023', 'Kemas Aulia', '081200001294', 'Multimedia', 'KLS007'),
('0071000096', 'Wanda Hamidah', '2007-09-21', 'Jl. Matraman No. 96', '081200001301', 'Perempuan', '2023', 'Hamidah Hamidah', '081200001302', 'TKR', 'KLS008'),
('0071000099', 'Angel Karamoy', '2007-01-16', 'Jl. Thamrin No. 99', '081200001307', 'Perempuan', '2023', 'Villy Karamoy', '081200001308', 'RPL', 'KLS005'),
('0081000001', 'Aditya Pratama', '2008-03-12', 'Jl. Sudirman No. 1', '081200001111', 'Laki - laki', '2024', 'Budi Pratama', '081200001112', 'RPL', 'KLS001'),
('0081000002', 'Andhika Wijaya', '2008-07-22', 'Jl. Thamrin No. 2', '081200001113', 'Laki - laki', '2024', 'Hendra Wijaya', '081200001114', 'TKJ', 'KLS002'),
('0081000003', 'Bagus Setiawan', '2008-01-15', 'Jl. Merdeka No. 3', '081200001115', 'Laki - laki', '2024', 'Wawan Setiawan', '081200001116', 'Multimedia', 'KLS003'),
('0081000004', 'Dimas Ramadhan', '2008-10-05', 'Jl. Pemuda No. 4', '081200001117', 'Laki - laki', '2024', 'Agus Ramadhan', '081200001118', 'TKR', 'KLS004'),
('0081000005', 'Eko Prasetyo', '2008-05-19', 'Jl. Diponegoro No. 5', '081200001119', 'Laki - laki', '2024', 'Rudi Prasetyo', '081200001120', 'RPL', 'KLS001'),
('0081000006', 'Fajar Hidayat', '2008-12-30', 'Jl. Hasanuddin No. 6', '081200001121', 'Laki - laki', '2024', 'Syarif Hidayat', '081200001122', 'TKJ', 'KLS002'),
('0081000007', 'Galih Nugraha', '2008-04-14', 'Jl. Imam Bonjol No. 7', '081200001123', 'Laki - laki', '2024', 'Dadang Nugraha', '081200001124', 'Multimedia', 'KLS003'),
('0081000008', 'Hendra Kurniawan', '2008-09-08', 'Jl. Teuku Umar No. 8', '081200001125', 'Laki - laki', '2024', 'Toto Kurniawan', '081200001126', 'TKR', 'KLS004'),
('0081000021', 'Wahyu Hidayat', '2008-02-14', 'Jl. Kartini No. 21', '081200001151', 'Laki - laki', '2024', 'Saiful Hidayat', '081200001152', 'TKJ', 'KLS002'),
('0081000022', 'Yuda Pratama', '2008-08-27', 'Jl. Pattimura No. 22', '081200001153', 'Laki - laki', '2024', 'Heru Pratama', '081200001154', 'Multimedia', 'KLS003'),
('0081000023', 'Zaki Anwar', '2008-06-05', 'Jl. Ki Hajar No. 23', '081200001155', 'Laki - laki', '2024', 'Firman Anwar', '081200001156', 'TKR', 'KLS004'),
('0081000030', 'Gilang Dirga', '2008-11-20', 'Jl. Cikini No. 30', '081200001169', 'Laki - laki', '2024', 'Mansur Dirga', '081200001170', 'RPL', 'KLS001'),
('0081000031', 'Hafiz Ansari', '2008-04-09', 'Jl. Menteng No. 31', '081200001171', 'Laki - laki', '2024', 'Kamal Ansari', '081200001172', 'TKJ', 'KLS002'),
('0081000032', 'Indra Jaya', '2008-01-25', 'Jl. Kuningan No. 32', '081200001173', 'Laki - laki', '2024', 'Sukarya Jaya', '081200001174', 'Multimedia', 'KLS003'),
('0081000039', 'Putra Utama', '2008-10-10', 'Jl. Pluit No. 39', '081200001187', 'Laki - laki', '2024', 'Eko Utama', '081200001188', 'TKR', 'KLS004'),
('0081000040', 'Rian Jaelani', '2008-05-24', 'Jl. Ancol No. 40', '081200001189', 'Laki - laki', '2024', 'Asep Jaelani', '081200001190', 'RPL', 'KLS001'),
('0081000045', 'Wira Yudha', '2008-09-12', 'Jl. Matraman No. 45', '081200001199', 'Laki - laki', '2024', 'Bambang Yudha', '081200001200', 'TKJ', 'KLS002'),
('0081000048', 'Bintang Kejora', '2008-07-07', 'Jl. Menteng No. 48', '081200001205', 'Laki - laki', '2024', 'Agus Kejora', '081200001206', 'Multimedia', 'KLS003'),
('0081000051', 'Anisa Rahma', '2008-04-18', 'Jl. Kemang No. 51', '081200001211', 'Perempuan', '2024', 'Supriyadi', '081200001212', 'RPL', 'KLS001'),
('0081000052', 'Bella Cantika', '2008-08-25', 'Jl. Senayan No. 52', '081200001213', 'Perempuan', '2024', 'Rudianto', '081200001214', 'TKJ', 'KLS002'),
('0081000053', 'Citra Kirana', '2008-02-10', 'Jl. Slipi No. 53', '081200001215', 'Perempuan', '2024', 'Iwan Kirana', '081200001216', 'Multimedia', 'KLS003'),
('0081000054', 'Dinda Hauw', '2008-11-12', 'Jl. Tomang No. 54', '081200001217', 'Perempuan', '2024', 'Kemal Hauw', '081200001218', 'TKR', 'KLS004'),
('0081000055', 'Erika Carlina', '2008-06-03', 'Jl. Grogol No. 55', '081200001219', 'Perempuan', '2024', 'Yusuf Carlina', '081200001220', 'RPL', 'KLS001'),
('0081000056', 'Fitriani Lestari', '2008-01-29', 'Jl. Pluit No. 56', '081200001221', 'Perempuan', '2024', 'Heri Lestari', '081200001222', 'TKJ', 'KLS002'),
('0081000057', 'Gita Gutawa', '2008-05-15', 'Jl. Ancol No. 57', '081200001223', 'Perempuan', '2024', 'Erwin Gutawa', '081200001224', 'Multimedia', 'KLS003'),
('0081000058', 'Hana Saraswati', '2008-09-20', 'Jl. Sunter No. 58', '081200001225', 'Perempuan', '2024', 'Agus Saraswati', '081200001226', 'TKR', 'KLS004'),
('0081000071', 'Wulan Guritno', '2008-04-14', 'Jl. Teuku Umar No. 71', '081200001251', 'Perempuan', '2024', 'Joko Guritno', '081200001252', 'TKJ', 'KLS002'),
('0081000072', 'Yasmin Napper', '2008-11-22', 'Jl. Kartini No. 72', '081200001253', 'Perempuan', '2024', 'Barry Napper', '081200001254', 'Multimedia', 'KLS003'),
('0081000073', 'Zaskia Adya Mecca', '2008-09-08', 'Jl. Pattimura No. 73', '081200001255', 'Perempuan', '2024', 'Eka Mecca', '081200001256', 'TKR', 'KLS004'),
('0081000080', 'Gisella Anastasia', '2008-11-16', 'Jl. MH Thamrin No. 80', '081200001269', 'Perempuan', '2024', 'Alal Anastasia', '081200001270', 'RPL', 'KLS001'),
('0081000081', 'Hesti Purwadinata', '2008-06-15', 'Jl. Cikini No. 81', '081200001271', 'Perempuan', '2024', 'Raden Purwadinata', '081200001272', 'TKJ', 'KLS002'),
('0081000082', 'Isyana Sarasvati', '2008-05-02', 'Jl. Menteng No. 82', '081200001273', 'Perempuan', '2024', 'Sapta Sarasvati', '081200001274', 'Multimedia', 'KLS003'),
('0081000089', 'Pevita Pearce', '2008-10-06', 'Jl. Grogol No. 89', '081200001287', 'Perempuan', '2024', 'Bram Pearce', '081200001288', 'TKR', 'KLS004'),
('0081000090', 'Queenara Jasmine', '2008-03-12', 'Jl. Pluit No. 90', '081200001289', 'Perempuan', '2024', 'Budi Jasmine', '081200001290', 'RPL', 'KLS001'),
('0081000095', 'Valerie Thomas', '2008-04-02', 'Jl. Pramuka No. 95', '081200001299', 'Perempuan', '2024', 'Jeremy Thomas', '081200001300', 'TKJ', 'KLS002'),
('0081000098', 'Zaskia Gotik', '2008-04-27', 'Jl. Sudirman No. 98', '081200001305', 'Perempuan', '2024', 'Sosin Gotik', '081200001306', 'Multimedia', 'KLS003');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_transaksi`
--

CREATE TABLE `tbl_transaksi` (
  `no_transaksi` varchar(10) NOT NULL,
  `tgl` date DEFAULT NULL,
  `nisn` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `nip` int(20) NOT NULL,
  `nama` varchar(25) NOT NULL,
  `password` varchar(20) NOT NULL,
  `lvl` varchar(15) NOT NULL,
  `status` varchar(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`nip`, `nama`, `password`, `lvl`, `status`) VALUES
(1, 'Udin', '1234567', '1', 'Y');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `guru`
--
ALTER TABLE `guru`
  ADD PRIMARY KEY (`nip_g`),
  ADD UNIQUE KEY `kd_guru` (`kd_guru`);

--
-- Indexes for table `tbl_absen`
--
ALTER TABLE `tbl_absen`
  ADD PRIMARY KEY (`id_absen`),
  ADD UNIQUE KEY `sesi_unik` (`nisn`,`tanggal`,`pertemuan_ke`);

--
-- Indexes for table `tbl_absen_guru`
--
ALTER TABLE `tbl_absen_guru`
  ADD PRIMARY KEY (`id_absen`),
  ADD UNIQUE KEY `unik_absen_guru` (`kd_guru`,`tgl`),
  ADD KEY `fk_absen_guru` (`kd_guru`);

--
-- Indexes for table `tbl_absen_user`
--
ALTER TABLE `tbl_absen_user`
  ADD PRIMARY KEY (`id_absen`),
  ADD KEY `fk_absen_user` (`nip`);

--
-- Indexes for table `tbl_jadwal`
--
ALTER TABLE `tbl_jadwal`
  ADD PRIMARY KEY (`id_mapel`),
  ADD KEY `kd_guru` (`kd_guru`),
  ADD KEY `kelas` (`kelas`);

--
-- Indexes for table `tbl_kelas`
--
ALTER TABLE `tbl_kelas`
  ADD PRIMARY KEY (`id_kelas`),
  ADD KEY `kd_guru` (`kd_guru`);

--
-- Indexes for table `tbl_nilai`
--
ALTER TABLE `tbl_nilai`
  ADD PRIMARY KEY (`id_nilai`),
  ADD KEY `nisn` (`nisn`),
  ADD KEY `id_mapel` (`id_mapel`),
  ADD KEY `kd_guru` (`kd_guru`);

--
-- Indexes for table `tbl_pelanggaran`
--
ALTER TABLE `tbl_pelanggaran`
  ADD PRIMARY KEY (`id_pelanggaran`),
  ADD KEY `nisn` (`nisn`);

--
-- Indexes for table `tbl_report`
--
ALTER TABLE `tbl_report`
  ADD PRIMARY KEY (`id_report`),
  ADD KEY `nama` (`nama`),
  ADD KEY `kelas` (`kelas`);

--
-- Indexes for table `tbl_siswa`
--
ALTER TABLE `tbl_siswa`
  ADD PRIMARY KEY (`nisn`),
  ADD KEY `kelas` (`kelas`);

--
-- Indexes for table `tbl_transaksi`
--
ALTER TABLE `tbl_transaksi`
  ADD PRIMARY KEY (`no_transaksi`),
  ADD KEY `nisn` (`nisn`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`nip`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tbl_absen`
--
ALTER TABLE `tbl_absen`
  MODIFY `id_absen` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=133;

--
-- AUTO_INCREMENT for table `tbl_absen_guru`
--
ALTER TABLE `tbl_absen_guru`
  MODIFY `id_absen` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `tbl_absen_user`
--
ALTER TABLE `tbl_absen_user`
  MODIFY `id_absen` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tbl_nilai`
--
ALTER TABLE `tbl_nilai`
  MODIFY `id_nilai` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tbl_pelanggaran`
--
ALTER TABLE `tbl_pelanggaran`
  MODIFY `id_pelanggaran` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `tbl_absen_guru`
--
ALTER TABLE `tbl_absen_guru`
  ADD CONSTRAINT `fk_absen_guru` FOREIGN KEY (`kd_guru`) REFERENCES `guru` (`kd_guru`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `tbl_absen_user`
--
ALTER TABLE `tbl_absen_user`
  ADD CONSTRAINT `fk_absen_user` FOREIGN KEY (`nip`) REFERENCES `users` (`nip`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `tbl_jadwal`
--
ALTER TABLE `tbl_jadwal`
  ADD CONSTRAINT `tbl_jadwal_ibfk_1` FOREIGN KEY (`kd_guru`) REFERENCES `guru` (`kd_guru`) ON UPDATE CASCADE,
  ADD CONSTRAINT `tbl_jadwal_ibfk_2` FOREIGN KEY (`kelas`) REFERENCES `tbl_kelas` (`id_kelas`) ON UPDATE CASCADE;

--
-- Constraints for table `tbl_kelas`
--
ALTER TABLE `tbl_kelas`
  ADD CONSTRAINT `tbl_kelas_ibfk_1` FOREIGN KEY (`kd_guru`) REFERENCES `guru` (`kd_guru`) ON UPDATE CASCADE;

--
-- Constraints for table `tbl_nilai`
--
ALTER TABLE `tbl_nilai`
  ADD CONSTRAINT `tbl_nilai_ibfk_1` FOREIGN KEY (`nisn`) REFERENCES `tbl_siswa` (`nisn`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `tbl_nilai_ibfk_2` FOREIGN KEY (`id_mapel`) REFERENCES `tbl_jadwal` (`id_mapel`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `tbl_nilai_ibfk_3` FOREIGN KEY (`kd_guru`) REFERENCES `guru` (`kd_guru`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `tbl_pelanggaran`
--
ALTER TABLE `tbl_pelanggaran`
  ADD CONSTRAINT `tbl_pelanggaran_ibfk_1` FOREIGN KEY (`nisn`) REFERENCES `tbl_siswa` (`nisn`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
