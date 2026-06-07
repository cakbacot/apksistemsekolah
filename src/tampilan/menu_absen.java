/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tampilan;

import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import koneksi.koneksi;

/**
 *
 * @author ACER
 */
public class menu_absen extends javax.swing.JInternalFrame {
private Connection con = new koneksi().getConnection();
private DefaultTableModel tabmode;
PreparedStatement ps;
ResultSet rs;
    /**
     * Creates new form menu_jadwal
     */
    public menu_absen() {
        initComponents();
        loadcombo();
        kosong();
        aktif();
        datatable();
        
    }
    
    protected void aktif(){
        txtcarisiswa.requestFocus();
    }
    
    protected void kosong(){
        jTemu.setValue(1);
        txtcarisiswa.setText("");
        tglTemu.setDate(new Date());
        
        if (cbkelas.getItemCount() > 0) {
            cbkelas.setSelectedIndex(0);
        }
    }
    
    protected void datatable(){
        // 1. Definisikan header/judul kolom (Ada 4 kolom)
    String[] baris = {"NISN", "Nama Siswa", "Jenis Kelamin (L/P)", "Kehadiran"};

    // 2. Buat Custom DefaultTableModel agar kolom ke-3 (Kehadiran) menjadi Checkbox
    DefaultTableModel tabmode = new DefaultTableModel(null, baris) {
        @Override
        public Class<?> getColumnClass(int columnIndex) {
            // Index 3 adalah kolom "Kehadiran" (Dimulai dari 0, 1, 2, 3)
            if (columnIndex == 3) { 
                return Boolean.class; 
            }
            return super.getColumnClass(columnIndex);
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            // Hanya kolom index 3 ("Kehadiran") yang bisa diklik/diedit oleh user
            return column == 3; 
        }
    };

    // 3. Terapkan model yang sudah dimodifikasi ke tabel (sekaligus mengosongkan tabel)
    tblsiswa.setModel(tabmode);

    // 4. Ambil kata kunci pencarian
    String cariitem = txtcarisiswa.getText();
    String kelasTerpilih = "";
    
    if (cbkelas.getSelectedItem() != null) {
            kelasTerpilih = cbkelas.getSelectedItem().toString();
        }
    
    try {
        // 5. Query mengambil data dari tbl_siswa dengan fitur pencarian
        String sql = "SELECT nisn, nama, jkel FROM tbl_siswa " +
                     "WHERE nisn LIKE '%" + cariitem + "%' OR nama LIKE '%" + cariitem + "%' " +
                     "ORDER BY nisn ASC";
        
        // Logika Filter SQL
        if (kelasTerpilih.equals("-- Pilih Kelas --") || kelasTerpilih.isEmpty()) {
            // Jika belum milih kelas, tampilkan semua siswa yang cocok dengan pencarian
            sql = "SELECT nisn, nama, jkel FROM tbl_siswa " +
                  "WHERE (nisn LIKE '%" + cariitem + "%' OR nama LIKE '%" + cariitem + "%') " +
                  "ORDER BY nisn ASC";
        } else {
            // Jika kelas dipilih, filter berdasarkan kelas TERSEBUT dan hasil pencarian
            sql = "SELECT a.nisn, a.nama, a.jkel FROM tbl_siswa a " +
                      "JOIN tbl_kelas b ON a.kelas = b.id_kelas " +
                      "WHERE b.kelas = '" + kelasTerpilih + "' " +
                      "AND (a.nisn LIKE '%" + cariitem + "%' OR a.nama LIKE '%" + cariitem + "%') " +
                      "ORDER BY a.nisn ASC";
        }

        Statement stat = con.createStatement(); // Pastikan 'con' sudah terhubung
        ResultSet hasil = stat.executeQuery(sql);

        // 6. Masukkan data ke dalam tabel baris demi baris
        while (hasil.next()) {
            tabmode.addRow(new Object[]{
                hasil.getString("nisn"), // Index 0: NISN
                hasil.getString("nama"), // Index 1: Nama Siswa
                hasil.getString("jkel"), // Index 2: Jenis Kelamin
                false                    // Index 3: Checkbox Kehadiran (Default: belum dicentang)
            });
        }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Data siswa gagal dipanggil: " + e.getMessage());
        }
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
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblsiswa = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        tglTemu = new com.toedter.calendar.JDateChooser();
        jTemu = new javax.swing.JSpinner();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        cbkelas = new javax.swing.JComboBox<>();
        txtcarisiswa = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        bbatal = new javax.swing.JButton();
        bsimpan = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(214647, 2147483647));

        tblsiswa.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
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
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblsiswa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblsiswaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblsiswa);
        if (tblsiswa.getColumnModel().getColumnCount() > 0) {
            tblsiswa.getColumnModel().getColumn(0).setResizable(false);
            tblsiswa.getColumnModel().getColumn(1).setResizable(false);
            tblsiswa.getColumnModel().getColumn(3).setResizable(false);
        }

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Detail Pertemuan", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(1, 1, 1))); // NOI18N
        jPanel1.setForeground(new java.awt.Color(1, 1, 1));

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel7.setText("Tanggal Pertemuan : ");

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel8.setText("Pertemuan ke- : ");

        jTemu.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jTemu.setModel(new javax.swing.SpinnerNumberModel(1, 1, 48, 1));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tglTemu, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 295, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTemu, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTemu, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(tglTemu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel1.setText("Absensi Siswa");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Filter & Pencarian", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        cbkelas.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        cbkelas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbkelas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbkelasActionPerformed(evt);
            }
        });

        txtcarisiswa.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        txtcarisiswa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcarisiswaActionPerformed(evt);
            }
        });
        txtcarisiswa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcarisiswaKeyPressed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel11.setText("Pilih Kelas : ");

        jLabel10.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel10.setText("Cari Siswa :");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(jLabel10))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtcarisiswa, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbkelas, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(cbkelas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtcarisiswa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        bbatal.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        bbatal.setText("BATAL");
        bbatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bbatalActionPerformed(evt);
            }
        });

        bsimpan.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        bsimpan.setText("SIMPAN");
        bsimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bsimpanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(429, 429, 429)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bsimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bbatal, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(42, 42, 42))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bsimpan)
                    .addComponent(bbatal))
                .addContainerGap(250, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbkelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbkelasActionPerformed
        datatable();        // TODO add your handling code here:
    }//GEN-LAST:event_cbkelasActionPerformed

    private void txtcarisiswaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcarisiswaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcarisiswaActionPerformed

    private void bsimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bsimpanActionPerformed
    // 1. Validasi Input Dasar
    if (tglTemu.getDate() == null) {
        JOptionPane.showMessageDialog(this, "Pilih tanggal pertemuan terlebih dahulu!", "Peringatan", JOptionPane.WARNING_MESSAGE);
        return;
    }
    int pertemuan_ke = (Integer) jTemu.getValue();
    if (pertemuan_ke <= 0) {
        JOptionPane.showMessageDialog(this, "Pertemuan tidak boleh 0!", "Peringatan", JOptionPane.WARNING_MESSAGE);
    return;
    }

    // 2. Ambil Model Tabel
    DefaultTableModel model = (DefaultTableModel) tblsiswa.getModel();
    int jumlahBaris = model.getRowCount();

    // Validasi jika tabel masih kosong
    if (jumlahBaris == 0) {
        JOptionPane.showMessageDialog(this, "Tabel data siswa masih kosong. Silakan pilih kelas terlebih dahulu!", "Peringatan", JOptionPane.WARNING_MESSAGE);
        return;
    }

    // =========================================================
    // --- TAMBAHAN CROSSCHECK / KONFIRMASI SIMPAN ---
    // =========================================================
    int konfirmasi = JOptionPane.showConfirmDialog(this, 
            "Apakah Anda yakin data kehadiran sudah benar dan ingin disimpan?", 
            "Konfirmasi Simpan", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.QUESTION_MESSAGE);
            
    // Jika user mengeklik "No" (Tidak) atau menutup dialog (X)
    if (konfirmasi != JOptionPane.YES_OPTION) {
        return; // Hentikan proses simpan
    }
    // =========================================================

    // 3. Konversi format tanggal dari JDateChooser ke SQL Date
    java.sql.Date sqlDate = new java.sql.Date(tglTemu.getDate().getTime());

    try {
        // Siapkan Query INSERT
        String sql = "INSERT INTO tbl_absen (nisn, tanggal, pertemuan_ke, status_hadir)" + "VALUES (?, ?, ?, ?)" + "ON DUPLICATE KEY UPDATE status_hadir = VALUES(status_hadir)";
        PreparedStatement pst = con.prepareStatement(sql);

        // 4. Looping untuk membaca isi tabel baris demi baris
        for (int i = 0; i < jumlahBaris; i++) {
            String nisn = model.getValueAt(i, 0).toString();
            
            Object objKehadiran = model.getValueAt(i, 3);
            boolean isChecked = (objKehadiran != null && (Boolean) objKehadiran);
            
            int status_hadir = isChecked ? 1 : 0;

            pst.setString(1, nisn);
            pst.setDate(2, sqlDate);
            pst.setInt(3, pertemuan_ke);
            pst.setInt(4, status_hadir);
            
            pst.addBatch();
        }

        // 5. Eksekusi semua antrean query ke database
        pst.executeBatch();
        
        JOptionPane.showMessageDialog(this, "Data absensi kelas berhasil disimpan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
        
        // 6. Reset form agar bersih untuk pertemuan berikutnya
        kosong();     
        datatable();  
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Gagal menyimpan data absensi:\n" + e.getMessage(), "Error Database", JOptionPane.ERROR_MESSAGE);
    }
// TODO add your handling code here:
    }//GEN-LAST:event_bsimpanActionPerformed

    private void tblsiswaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblsiswaMouseClicked
     // TODO add your handling code here:
    }//GEN-LAST:event_tblsiswaMouseClicked

    private void bbatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bbatalActionPerformed
        // Memunculkan dialog konfirmasi (opsional, tapi disarankan)
    int konfirmasi = JOptionPane.showConfirmDialog(this, 
            "Batal melakukan absensi? Semua isian dan centang akan dikosongkan.", 
            "Konfirmasi Batal", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.QUESTION_MESSAGE);
            
    if (konfirmasi == JOptionPane.YES_OPTION) {
        // Panggil method yang sudah Anda buat untuk mereset tampilan
        kosong();     // Mengosongkan textfield dan tanggal
        datatable();  // Me-refresh tabel agar semua checkbox kembali kosong
        
        JOptionPane.showMessageDialog(this, "Form berhasil dibersihkan.", "Batal", JOptionPane.INFORMATION_MESSAGE);
    }        // TODO add your handling code here:
    }//GEN-LAST:event_bbatalActionPerformed

    private void txtcarisiswaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcarisiswaKeyPressed
        datatable();        // TODO add your handling code here:
    }//GEN-LAST:event_txtcarisiswaKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bbatal;
    private javax.swing.JButton bsimpan;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbkelas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner jTemu;
    private javax.swing.JTable tblsiswa;
    private com.toedter.calendar.JDateChooser tglTemu;
    private javax.swing.JTextField txtcarisiswa;
    // End of variables declaration//GEN-END:variables
}
