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
  - Menghapus tombol TAMBAH, UBAH, dan HAPUS
  - Mengganti komponen teks txtpertemuan (JTextField) menjadi jTemu (JSpinner)
  - Mengubah pengaturan model jTemu (JSpinner) menjadi tipe Number dengan nilai Initial = 1 dan Minimum = 1.
  - Menambahkan TitledBorder pada Panel atas ("Detail Pertemuan") dan Panel tengah ("Filter & Pencarian").
2. Kode
  - Mengubah urutan pemanggilan method. loadcombo() diletakkan di atas datatable() agar data dropdown kelas dimuat lebih dulu sebelum tabel difilter.
  - Mengubah query dari memanggil id_kelas menjadi kelas (SELECT kelas FROM tbl_kelas GROUP BY kelas) agar sesuai dengan isian di tbl_siswa.
  - Menambahkan deklarasi modifikasi DefaultTableModel agar kolom index 3 ("Kehadiran") merender Checkbox (Boolean.class).
  - Menambahkan perintah cbkelas.getSelectedItem().toString() untuk menangkap teks pilihan kelas.
  - Menambahkan logika if-else pada query SQL untuk menggabungkan filter ComboBox kelas dengan TextField pencarian.
  - Mengganti baris txtpertemuan.setText("") menjadi jTemu.setValue(1) karena perubahan komponen ke JSpinner.
  - Menambahkan pemanggilan datatable(); agar tabel me-refresh data saat kelas diganti.
  - Memindahkan pemanggilan datatable(); pakai keyPressed untuk mencari siswa.
  - [ADD] Event bsimpanActionPerformed:
     -  Menambahkan validasi angka untuk jTemu (JSpinner).
     -  Menambahkan JOptionPane.showConfirmDialog untuk konfirmasi sebelum menyimpan.
     -  Menerapkan logika looping for untuk membaca baris tabel dan mengekstrak nilai Checkbox menjadi 1 atau 0.
     -  Mengimplementasikan PreparedStatement dengan addBatch() dan executeBatch().
     -  Mengubah query INSERT standar menjadi ON DUPLICATE KEY UPDATE (Upsert) untuk menangani kasus siswa telat.
     -  Menambahkan dialog konfirmasi batal, diikuti dengan pemanggilan method kosong() dan datatable() untuk me-reset form.
3. Databse (SQL)
  - Menghapus struktur lama dan membuat ulang tabel dengan kolom: id_absen (PK), nisn, tanggal, pertemuan_ke, dan status_hadir.
  - Menambahkan UNIQUE KEY sesi_unik (nisn, tanggal, pertemuan_ke) pada tbl_absen untuk mencegah data ganda (duplikat) di level   database.
4. Query
```sql
DROP TABLE IF EXISTS tbl_absen;

CREATE TABLE tbl_absen (
  id_absen int(11) NOT NULL AUTO_INCREMENT,
  nisn varchar(10) NOT NULL,
  tanggal date NOT NULL,
  pertemuan_ke int(11) NOT NULL,
  status_hadir int(1) NOT NULL,
  PRIMARY KEY (id_absen),
  UNIQUE KEY sesi_unik (nisn, tanggal, pertemuan_ke)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
      
UPDATE 6 JUNI 2026
  - FIXING menu_absen logika sql combo box
  - update logika sql tabel user (user -> users)
  - QUERY TABEL USERS

CREATE TABLE users (
  `nip` int(20) NOT NULL,
  `nama` varchar(25) NOT NULL,
  `password` varchar(20) NOT NULL,
  `lvl` varchar(15) NOT NULL,
  `status` varchar(2) NOT NULL
);


