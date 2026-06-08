/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tampilan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import koneksi.koneksi;

/**
 *
 * @author User
 */
public class menu_nilai extends javax.swing.JInternalFrame {
private Connection con = new koneksi().getConnection();
private DefaultTableModel tabmode;
PreparedStatement ps;
ResultSet rs;

    /**
     * Creates new form menu_nilai
     */
    public menu_nilai() {
        initComponents();
        loadcombo();
        loadcomboMapel();
        loadjenisnilai();
       //datatable();
    }
public void datatable() {
    DefaultTableModel tabmode = new DefaultTableModel();
    tabmode.addColumn("NISN");
    tabmode.addColumn("Nama Siswa");
    tabmode.addColumn("Tugas");
    tabmode.addColumn("UTS");
    tabmode.addColumn("UAS");
    tblsiswa.setModel(tabmode);

    // UBAH IF INI: Hanya wajibkan pilih kelas
    if (cbkelas.getSelectedIndex() <= 0) {
        return;
    }

    String kelasDipilih = cbkelas.getSelectedItem().toString();
    String idGuru = GuruSession.getKdGuru();
    
    // Cek apakah mapel sudah dipilih
    String idMapel = "";
    if (cbmapel.getSelectedIndex() > 0) {
        idMapel = cariIdMapel(cbmapel.getSelectedItem().toString());
    }

    try {
        // Jika Mapel belum dipilih, kita beri nilai kosong agar Left Join tidak error
        String sql = "SELECT s.nisn, s.nama, " +
                     "MAX(CASE WHEN n.jenis_nilai = 'Tugas' THEN n.skor ELSE 0 END) AS Tugas, " +
                     "MAX(CASE WHEN n.jenis_nilai = 'UTS' THEN n.skor ELSE 0 END) AS UTS, " +
                     "MAX(CASE WHEN n.jenis_nilai = 'UAS' THEN n.skor ELSE 0 END) AS UAS " +
                     "FROM tbl_siswa s " +
                     "JOIN tbl_kelas k ON s.kelas = k.id_kelas " +
                     "LEFT JOIN tbl_nilai n ON s.nisn = n.nisn AND n.id_mapel = ? " +
                     "WHERE k.kelas = ? " +
                     "GROUP BY s.nisn, s.nama " +
                     "ORDER BY s.nama ASC";

        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, idMapel); // Jika kosong, dia akan mencari nilai yang tidak punya ID (alias null)
        pst.setString(2, kelasDipilih);
        
        ResultSet rs = pst.executeQuery();

        int count = 0;
        while (rs.next()) {
            tabmode.addRow(new Object[]{
                rs.getString("nisn"),
                rs.getString("nama"),
                rs.getInt("Tugas"),
                rs.getInt("UTS"),
                rs.getInt("UAS")
            });
            count++;
        }
        System.out.println("DEBUG: Ditemukan " + count + " data.");

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Gagal memuat tabel: " + e.getMessage());
        e.printStackTrace();
    }
}
private void loadjenisnilai() {
    cbjnsnilai.removeAllItems();
    cbjnsnilai.addItem("SILAHKAN PILIH JENIS NILAI");
    cbjnsnilai.addItem("Tugas");
    cbjnsnilai.addItem("UTS");
    cbjnsnilai.addItem("UAS");
}
private String cariIdMapel(String namaMapel) {
    String id = "";
    try {
        // Query untuk mencari kode (ID) berdasarkan nama yang dipilih
        String sql = "SELECT id_mapel FROM tbl_jadwal WHERE nama_mapel = ? AND kd_guru = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, namaMapel);
        pst.setString(2, GuruSession.getKdGuru()); // Pastikan guru yang login dicocokkan
        ResultSet rs = pst.executeQuery();
        
        if (rs.next()) {
            id = rs.getString("id_mapel"); // Mengambil ID dari database
        }
    } catch (Exception e) {
        System.out.println("Error cari ID Mapel: " + e.getMessage());
    }
    return id; // Mengembalikan hasil (ID) ke fungsi yang memanggil
}

    
    private void loadcombo() {
        try {
            // 1. Ambil Kode Guru yang sedang login dari GuruSession (Karena 1 package, langsung panggil nama class-nya)
            String idGuruLogin = GuruSession.getKdGuru();

            // 2. Modifikasi Query: Ambil nama kelas berdasarkan kd_guru yang sedang login
            String sql = "SELECT kelas FROM tbl_kelas WHERE kd_guru = '" + idGuruLogin + "' ORDER BY kelas ASC"; 
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            // Bersihkan item lama dan siapkan opsi default
            cbkelas.removeAllItems(); 
            cbkelas.addItem("-- Pilih Kelas --"); 

            boolean punyaKelas = false; // Penanda apakah guru ini memegang suatu kelas

            // 3. Masukkan hasil query ke dalam Combo Box
            while(rs.next()) {
                cbkelas.addItem(rs.getString("kelas")); 
                punyaKelas = true;
            }
            
            // 4. (Opsional) Peringatan jika guru yang login tidak terdaftar di kelas manapun
            if (!punyaKelas && idGuruLogin != null) {
                JOptionPane.showMessageDialog(this, "Anda belum ditugaskan untuk mengajar/wali di kelas manapun.", "Informasi", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal Load Kelas: " + e.getMessage());
        }
    }
    
    private void loadcomboMapel() {
    try {
        String idGuru = GuruSession.getKdGuru();
        String kelasDipilih = cbkelas.getSelectedItem().toString();

        // Query ini akan menghasilkan baris sesuai jumlah mapel yang diajar guru tsb
        String sql = "SELECT j.nama_mapel FROM tbl_jadwal j " +
                     "JOIN tbl_kelas k ON j.kelas = k.id_kelas " +
                     "WHERE j.kd_guru = ? AND k.kelas = ?";
                     
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, idGuru);
        pst.setString(2, kelasDipilih);
        ResultSet rs = pst.executeQuery();

        cbmapel.removeAllItems(); // Bersihkan dulu item lama
        cbmapel.addItem("-- Pilih Mapel --");

        while(rs.next()) {
            // Jika guru mengajar 2 mapel, maka perulangan ini akan berjalan 2x
            // dan menambahkan 2 item ke combobox
            cbmapel.addItem(rs.getString("nama_mapel")); 
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Gagal memuat mapel: " + e.getMessage());
    }
}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblnisn = new javax.swing.JLabel();
        lblnama = new javax.swing.JLabel();
        lblmapel = new javax.swing.JLabel();
        lblguru = new javax.swing.JLabel();
        cbjnsnilai = new javax.swing.JComboBox<>();
        txnilai = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblsiswa = new javax.swing.JTable();
        cbkelas = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        txcari = new javax.swing.JTextField();
        cbmapel = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        save = new javax.swing.JButton();
        change = new javax.swing.JButton();

        jPanel1.setBackground(new java.awt.Color(0, 8, 97));

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("MENU INPUT NILAI");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel2.setText("Nomor Induk Siswa Nasional (NISN) : ");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel3.setText("Nama Siswa : ");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel4.setText("Nama Mapel : ");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel5.setText("Nama Guru Pengampu : ");

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel6.setText("Jenis Nilai : ");

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel7.setText("Nilai Yang diInput : ");

        lblnisn.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        lblnisn.setText("silahkan pilih siswa pada tabel");

        lblnama.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        lblnama.setText("silahkan pilih siswa pada tabel");

        lblmapel.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        lblmapel.setText("silahkan pilih pada tabel");

        lblguru.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        lblguru.setText("silahkan pilih pada tabel");

        cbjnsnilai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        txnilai.setText("nilai");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblguru, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
                    .addComponent(lblmapel, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
                    .addComponent(lblnama, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
                    .addComponent(lblnisn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbjnsnilai, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txnilai))
                .addContainerGap(107, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lblnisn))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lblnama))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(lblmapel))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(lblguru))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cbjnsnilai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txnilai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tblsiswa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblsiswa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblsiswaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblsiswa);

        cbkelas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbkelas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbkelasActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel8.setText("Cari Siswa = ");

        txcari.setText("nilai");

        cbmapel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbmapel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbmapelActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel9.setText("PILIH KELAS");

        jLabel10.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel10.setText("PILIH MATA PELAJARAN");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(jLabel9)
                .addGap(72, 72, 72)
                .addComponent(jLabel10)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 462, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txcari, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(cbkelas, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cbmapel, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10))
                .addGap(4, 4, 4)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbkelas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbmapel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txcari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        save.setText("SIMPAN");
        save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveActionPerformed(evt);
            }
        });

        change.setText("UBAH");
        change.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(change, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(save, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(save)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(change)
                        .addContainerGap())))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblsiswaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblsiswaMouseClicked
    int baris = tblsiswa.getSelectedRow();
    
    if (baris != -1) {
        // 1. Mengambil data dari kolom tabel (berdasarkan index kolom)
        // Index 0: NISN, 1: Nama, 2: Mapel, 3: Tugas, 4: UTS, 5: UAS
        String nisn = tblsiswa.getValueAt(baris, 0).toString();
        String nama = tblsiswa.getValueAt(baris, 1).toString();
        String mapel = cbmapel.getSelectedItem().toString();
        
        // 2. Mengisi Label (Data Identitas)
        lblnisn.setText(nisn);
        lblnama.setText(nama);
        lblmapel.setText(mapel);
        lblguru.setText(GuruSession.getNama()); // Mengambil nama guru dari Session
        
        // 3. Reset/Siapkan Form Input (Opsional tapi disarankan)
        // Agar setelah diklik, guru bisa langsung memilih jenis nilai baru
        cbjnsnilai.setSelectedIndex(0); 
        txnilai.setText("");
        
        // Jika kamu ingin guru bisa langsung update nilai yang ada di tabel,
        // kamu bisa menambahkan logika di sini untuk mengecek isi kolom 3, 4, atau 5.
    }
    }//GEN-LAST:event_tblsiswaMouseClicked

    private void cbkelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbkelasActionPerformed
    if (cbkelas.getSelectedIndex() > 0) {
        loadcomboMapel();
        datatable();
    }
    }//GEN-LAST:event_cbkelasActionPerformed

    private void saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveActionPerformed
if (lblnisn.getText().equals("silahkan pilih siswa pada tabel") || 
        cbjnsnilai.getSelectedIndex() == 0 || 
        txnilai.getText().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Harap pilih siswa, jenis nilai, dan isi skor!");
        return;
    }

    String nisn = lblnisn.getText();
    String jenisNilai = cbjnsnilai.getSelectedItem().toString();
    int skor = Integer.parseInt(txnilai.getText());
    String idMapel = cariIdMapel(lblmapel.getText()); 
    String idGuru = GuruSession.getKdGuru(); // Ambil kode guru yang login

    try {
        // Cek data
        String cekSql = "SELECT * FROM tbl_nilai WHERE nisn = ? AND id_mapel = ? AND jenis_nilai = ?";
        PreparedStatement psCek = con.prepareStatement(cekSql);
        psCek.setString(1, nisn);
        psCek.setString(2, idMapel);
        psCek.setString(3, jenisNilai);
        ResultSet rs = psCek.executeQuery();

        if (rs.next()) {
            // Update
            String updateSql = "UPDATE tbl_nilai SET skor = ? WHERE nisn = ? AND id_mapel = ? AND jenis_nilai = ?";
            PreparedStatement psUpdate = con.prepareStatement(updateSql);
            psUpdate.setInt(1, skor);
            psUpdate.setString(2, nisn);
            psUpdate.setString(3, idMapel);
            psUpdate.setString(4, jenisNilai);
            psUpdate.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data berhasil diupdate!");
        } else {
            // INSERT (Tambahkan kd_guru di sini)
            String insertSql = "INSERT INTO tbl_nilai (nisn, id_mapel, jenis_nilai, skor, kd_guru) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement psInsert = con.prepareStatement(insertSql);
            psInsert.setString(1, nisn);
            psInsert.setString(2, idMapel);
            psInsert.setString(3, jenisNilai);
            psInsert.setInt(4, skor);
            psInsert.setString(5, idGuru); // Tambahkan ini!
            psInsert.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data berhasil disimpan!");
        }

        datatable(); 
        txnilai.setText("");
        cbjnsnilai.setSelectedIndex(0);

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Gagal menyimpan: " + e.getMessage());
        e.printStackTrace();
    }
        }//GEN-LAST:event_saveActionPerformed

    private void changeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeActionPerformed
    if (lblnisn.getText().equals("silahkan pilih siswa pada tabel") || 
        cbjnsnilai.getSelectedIndex() == 0 || 
        txnilai.getText().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Harap pilih siswa dan isi nilai yang ingin diubah!");
        return;
    }

    String nisn = lblnisn.getText();
    String jenisNilai = cbjnsnilai.getSelectedItem().toString();
    String idMapel = cariIdMapel(lblmapel.getText()); // Ambil ID Mapel
    
    try {
        // Validasi: Apakah skor berupa angka?
        int skor = Integer.parseInt(txnilai.getText());

        // 2. Query Update
        String sql = "UPDATE tbl_nilai SET skor = ? WHERE nisn = ? AND id_mapel = ? AND jenis_nilai = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        
        ps.setInt(1, skor);
        ps.setString(2, nisn);
        ps.setString(3, idMapel);
        ps.setString(4, jenisNilai);
        
        int rowsAffected = ps.executeUpdate();

        if (rowsAffected > 0) {
            JOptionPane.showMessageDialog(this, "Data berhasil diubah!");
            datatable(); // Refresh tabel
            txnilai.setText(""); // Bersihkan input
            cbjnsnilai.setSelectedIndex(0);
        } else {
            JOptionPane.showMessageDialog(this, "Gagal mengubah data. Pastikan data tersebut sudah ada/tersimpan sebelumnya.");
        }

    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Nilai harus berupa angka!");
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        e.printStackTrace();
    }
    }//GEN-LAST:event_changeActionPerformed

    private void cbmapelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbmapelActionPerformed
    if (cbmapel.getSelectedIndex() > 0) {
        datatable(); 
    }
    }//GEN-LAST:event_cbmapelActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbjnsnilai;
    private javax.swing.JComboBox<String> cbkelas;
    private javax.swing.JComboBox<String> cbmapel;
    private javax.swing.JButton change;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblguru;
    private javax.swing.JLabel lblmapel;
    private javax.swing.JLabel lblnama;
    private javax.swing.JLabel lblnisn;
    private javax.swing.JButton save;
    private javax.swing.JTable tblsiswa;
    private javax.swing.JTextField txcari;
    private javax.swing.JTextField txnilai;
    // End of variables declaration//GEN-END:variables
}
