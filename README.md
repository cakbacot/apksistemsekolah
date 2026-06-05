pull usahakan download .jar yang disini dulu
biar tinggal add ke libraries

pastikan libraries ter add ke libraries masing masing

libraries yang harus ada (selain jdbc)
- flatlaf-3.7.jar
- jcalender-1.4.jar
- jfreechart-1.0.19.jar
- jcommon-1.0.23.jar

setelah pull, lakukan clean and build
cek database 

PENAMBAHAN KERKOM 4 Juni 2026
- database Tabel user -> users

run project on login_user (posible)
//included dashboardtu, menu_siswa, menu_kelas, menu_jadwal, frm_user, tranksaksi [masih belum selesai pengerjaan form tranksaksi]

Update 5 Juni 2026
  1. Tampilan
    .  Menghapus tombol TAMBAH, UBAH, dan HAPUS
    .  Mengganti komponen teks txtpertemuan (JTextField) menjadi jTemu (JSpinner)
    .  Mengubah pengaturan model jTemu (JSpinner) menjadi tipe Number dengan nilai Initial = 1 dan Minimum = 1.
    .  Menambahkan TitledBorder pada Panel atas ("Detail Pertemuan") dan Panel tengah ("Filter & Pencarian").
  2. Kode
    .  Mengubah urutan pemanggilan method. loadcombo() diletakkan di atas datatable() agar data dropdown kelas dimuat lebih dulu sebelum tabel difilter.
    .  Mengubah query dari memanggil id_kelas menjadi kelas (SELECT kelas FROM tbl_kelas GROUP BY kelas) agar sesuai dengan isian di tbl_siswa.
    .  Menambahkan deklarasi modifikasi DefaultTableModel agar kolom index 3 ("Kehadiran") merender Checkbox (Boolean.class).
    .  Menambahkan perintah cbkelas.getSelectedItem().toString() untuk menangkap teks pilihan kelas.
    .  Menambahkan logika if-else pada query SQL untuk menggabungkan filter ComboBox kelas dengan TextField pencarian.
    .  Mengganti baris txtpertemuan.setText("") menjadi jTemu.setValue(1) karena perubahan komponen ke JSpinner.
    .  Menambahkan pemanggilan datatable(); agar tabel me-refresh data saat kelas diganti.
    .  Memindahkan pemanggilan datatable(); dari KeyPressed ke KeyReleased agar fitur Live Search berjalan mulus tanpa delay satu huruf.
    .  [ADD] Event bsimpanActionPerformed:
        -  Menambahkan validasi angka untuk jTemu (JSpinner).
        -  Menambahkan JOptionPane.showConfirmDialog untuk konfirmasi sebelum menyimpan.
        -  Menerapkan logika looping for untuk membaca baris tabel dan mengekstrak nilai Checkbox menjadi 1 atau 0.
        -  Mengimplementasikan PreparedStatement dengan addBatch() dan executeBatch().
        -  Mengubah query INSERT standar menjadi ON DUPLICATE KEY UPDATE (Upsert) untuk menangani kasus siswa telat.
    .  Menambahkan dialog konfirmasi batal, diikuti dengan pemanggilan method kosong() dan datatable() untuk me-reset form.
  3. Databse (SQL)
    .  Menghapus struktur lama dan membuat ulang tabel dengan kolom: id_absen (PK), nisn, tanggal, pertemuan_ke, dan status_hadir.
    .  Menambahkan UNIQUE KEY sesi_unik (nisn, tanggal, pertemuan_ke) pada tbl_absen untuk mencegah data ganda (duplikat) di level   database.
  4. Query SQL
     -- phpMyAdmin SQL Dump
     -- version 5.2.1
     -- https://www.phpmyadmin.net/
     --
     -- Host: 127.0.0.1
     -- Generation Time: Jun 05, 2026 at 01:48 PM
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
      -- Indexes for dumped tables
      --
      
      --
      -- Indexes for table `tbl_absen`
      --
      ALTER TABLE `tbl_absen`
        ADD PRIMARY KEY (`id_absen`),
        ADD UNIQUE KEY `sesi_unik` (`nisn`,`tanggal`,`pertemuan_ke`);
      
      --
      -- AUTO_INCREMENT for dumped tables
      --
      
      --
      -- AUTO_INCREMENT for table `tbl_absen`
      --
      ALTER TABLE `tbl_absen`
        MODIFY `id_absen` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;
      COMMIT;
      
      /*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
      /*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
      /*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
