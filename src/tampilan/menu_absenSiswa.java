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
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import koneksi.koneksi;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author ACER
 */
public class menu_absenSiswa extends javax.swing.JInternalFrame {
private Connection con = new koneksi().getConnection();
private DefaultTableModel tabmode;
PreparedStatement ps;
ResultSet rs;
    private boolean sedangMemuat = false;

    /**
     * Creates new form menu_jadwal
     */
    public menu_absenSiswa() {
        initComponents();
        javax.swing.plaf.basic.BasicInternalFrameUI ui = (javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);
        loadcombo();
        kosong();
        aktif();
        datatable();

        tglTemu.addPropertyChangeListener("date", evt -> {
            if (!sedangMemuat) {
                datatable();
            }
        });
        jTemu.addChangeListener(evt -> {
            if (!sedangMemuat) {
                datatable();
            }
        });
    }
    
    public void cetak() {
    try {
        if (cbkelas.getSelectedIndex() <= 0 || cbkelas.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Pilih kelas terlebih dahulu!");
            return;
        }

        String kelasYangDipilih = cbkelas.getSelectedItem().toString(); 
        String idGuruLogin = GuruSession.getNip(); 
        if (idGuruLogin == null || idGuruLogin.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Sesi guru tidak ditemukan. Silakan login kembali!");
            return;
        }

        String path = "./src/report/reportSiswa.jasper"; 
        HashMap<String, Object> parameter = new HashMap<>();
        parameter.put(net.sf.jasperreports.engine.JRParameter.REPORT_LOCALE, new java.util.Locale("id", "ID"));
        java.io.InputStream logoStream = getClass().getResourceAsStream("/resource/logosekolah.jpeg");
        parameter.put("logo", logoStream);
        parameter.put("paramKelas", kelasYangDipilih); 
        parameter.put("paramIdGuru", idGuruLogin);     

        JasperPrint print = JasperFillManager.fillReport(path, parameter, con);
        JasperViewer.viewReport(print, false);
        
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(rootPane, "Gagal mencetak: " + ex.getMessage());
            ex.printStackTrace();
        }
    }   
    
    protected void aktif(){
        txtcarisiswa.requestFocus();
    }
    
    protected void kosong(){
        sedangMemuat = true;
        try {
            jTemu.setValue(1);
            txtcarisiswa.setText("");
            tglTemu.setDate(new Date());
            
            if (cbkelas.getItemCount() > 0) {
                cbkelas.setSelectedIndex(0);
            }
        } finally {
            sedangMemuat = false;
        }
    }
    
    protected void datatable(){
    String[] baris = {"NISN", "Nama Siswa", "Jenis Kelamin (L/P)", "Kehadiran"};
    DefaultTableModel tabmode = new DefaultTableModel(null, baris) {
        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if (columnIndex == 3) { 
                return Boolean.class; 
            }
            return super.getColumnClass(columnIndex);
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 3; 
        }
    };

    tblsiswa.setModel(tabmode);

    if (cbkelas.getSelectedIndex() <= 0 || cbkelas.getSelectedItem() == null) {
        return;
    }

    String cariitem = "%" + txtcarisiswa.getText().trim() + "%";
    String kelasTerpilih = cbkelas.getSelectedItem().toString();
    java.sql.Date sqlDate = (tglTemu.getDate() != null)
            ? new java.sql.Date(tglTemu.getDate().getTime())
            : new java.sql.Date(new Date().getTime());
    int pertemuanKe = (Integer) jTemu.getValue();
    
    try {
        String sql = "SELECT a.nisn, a.nama, a.jkel, COALESCE(ab.status_hadir, 0) AS status_hadir " +
                     "FROM tbl_siswa a " +
                     "JOIN tbl_kelas b ON a.kelas = b.id_kelas " +
                     "LEFT JOIN tbl_absen ab ON a.nisn = ab.nisn AND ab.tanggal = ? AND ab.pertemuan_ke = ? " +
                     "WHERE b.kelas = ? AND (a.nisn LIKE ? OR a.nama LIKE ?) " +
                     "ORDER BY a.nisn ASC";

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setDate(1, sqlDate);
            pst.setInt(2, pertemuanKe);
            pst.setString(3, kelasTerpilih);
            pst.setString(4, cariitem);
            pst.setString(5, cariitem);

            try (ResultSet hasil = pst.executeQuery()) {
                while (hasil.next()) {
                    tabmode.addRow(new Object[]{
                        hasil.getString("nisn"), 
                        hasil.getString("nama"), 
                        hasil.getString("jkel"), 
                        hasil.getInt("status_hadir") == 1
                    });
                }
            }
        }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Data siswa gagal dipanggil: " + e.getMessage());
        }
    }
    
    private void loadcombo() {
        sedangMemuat = true;
        try {
            cbkelas.removeAllItems(); 
            cbkelas.addItem("-- Pilih Kelas --"); 

            String idGuruLogin = GuruSession.getKdGuru();
            if (idGuruLogin == null || idGuruLogin.trim().isEmpty()) {
                return;
            }

            String sql = "SELECT DISTINCT k.kelas " +
                         "FROM tbl_kelas k " +
                         "LEFT JOIN tbl_jadwal j ON k.id_kelas = j.kelas " +
                         "WHERE k.kd_guru = ? OR j.kd_guru = ? " +
                         "ORDER BY k.kelas ASC"; 
            boolean punyaKelas = false; 
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setString(1, idGuruLogin);
                pst.setString(2, idGuruLogin);
                try (ResultSet rs = pst.executeQuery()) {
                    while (rs.next()) {
                        cbkelas.addItem(rs.getString("kelas")); 
                        punyaKelas = true;
                    }
                }
            }
            
            if (!punyaKelas) {
                JOptionPane.showMessageDialog(this, "Anda belum ditugaskan untuk mengajar/wali di kelas manapun.", "Informasi", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal Load Kelas: " + e.getMessage());
        } finally {
            sedangMemuat = false;
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
        print = new javax.swing.JButton();

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

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Detail Pertemuan", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 16), new java.awt.Color(1, 1, 1))); // NOI18N
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 296, Short.MAX_VALUE)
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
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
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
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtcarisiswaKeyReleased(evt);
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        print.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        print.setText("PRINT");
        print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(print, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bsimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bbatal, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(41, 41, 41))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bsimpan)
                    .addComponent(bbatal)
                    .addComponent(print))
                .addContainerGap(228, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbkelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbkelasActionPerformed
        if (!sedangMemuat) {
            datatable();
        }
    }//GEN-LAST:event_cbkelasActionPerformed

    private void txtcarisiswaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcarisiswaActionPerformed
        datatable();
    }//GEN-LAST:event_txtcarisiswaActionPerformed

    private void bsimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bsimpanActionPerformed
    if (tblsiswa.isEditing()) {
        tblsiswa.getCellEditor().stopCellEditing();
    }

    if (cbkelas.getSelectedIndex() <= 0 || cbkelas.getSelectedItem() == null) {
        JOptionPane.showMessageDialog(this, "Silakan pilih kelas terlebih dahulu!", "Peringatan", JOptionPane.WARNING_MESSAGE);
        return;
    }

    if (tglTemu.getDate() == null) {
        JOptionPane.showMessageDialog(this, "Pilih tanggal pertemuan terlebih dahulu!", "Peringatan", JOptionPane.WARNING_MESSAGE);
        return;
    }
    int pertemuan_ke = (Integer) jTemu.getValue();
    if (pertemuan_ke <= 0) {
        JOptionPane.showMessageDialog(this, "Pertemuan tidak boleh 0!", "Peringatan", JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    DefaultTableModel model = (DefaultTableModel) tblsiswa.getModel();
    int jumlahBaris = model.getRowCount();
    if (jumlahBaris == 0) {
        JOptionPane.showMessageDialog(this, "Tabel data siswa masih kosong!", "Peringatan", JOptionPane.WARNING_MESSAGE);
        return;
    }

    int konfirmasi = JOptionPane.showConfirmDialog(this, 
            "Apakah Anda yakin data kehadiran kelas " + cbkelas.getSelectedItem() + " sudah benar dan ingin disimpan?", 
            "Konfirmasi Simpan", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.QUESTION_MESSAGE);
            
    if (konfirmasi != JOptionPane.YES_OPTION) {
        return; 
    }

    java.sql.Date sqlDate = new java.sql.Date(tglTemu.getDate().getTime());

    try {
        String sql = "INSERT INTO tbl_absen (nisn, tanggal, pertemuan_ke, status_hadir) " +
                     "VALUES (?, ?, ?, ?) " +
                     "ON DUPLICATE KEY UPDATE status_hadir = VALUES(status_hadir)";
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            for (int i = 0; i < jumlahBaris; i++) {
                String nisn = model.getValueAt(i, 0).toString();
                
                Object objKehadiran = model.getValueAt(i, 3);
                boolean isChecked = (objKehadiran instanceof Boolean) && (Boolean) objKehadiran;
                
                int status_hadir = isChecked ? 1 : 0;

                pst.setString(1, nisn);
                pst.setDate(2, sqlDate);
                pst.setInt(3, pertemuan_ke);
                pst.setInt(4, status_hadir);
                
                pst.addBatch();
            }

            pst.executeBatch();
        }
        
        JOptionPane.showMessageDialog(this, "Data absensi kelas berhasil disimpan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
        datatable();  
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Gagal menyimpan data absensi:\n" + e.getMessage(), "Error Database", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_bsimpanActionPerformed

    private void tblsiswaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblsiswaMouseClicked
      // TODO add your handling code here:
    }//GEN-LAST:event_tblsiswaMouseClicked

    private void bbatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bbatalActionPerformed

    int konfirmasi = JOptionPane.showConfirmDialog(this, 
            "Batal melakukan absensi? Semua isian dan centang akan dikosongkan.", 
            "Konfirmasi Batal", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.QUESTION_MESSAGE);
            
    if (konfirmasi == JOptionPane.YES_OPTION) {
        kosong();     
        datatable();  
        
        JOptionPane.showMessageDialog(this, "Form berhasil dibersihkan.", "Batal", JOptionPane.INFORMATION_MESSAGE);
    }
    }//GEN-LAST:event_bbatalActionPerformed

    private void txtcarisiswaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcarisiswaKeyReleased
        datatable();
    }//GEN-LAST:event_txtcarisiswaKeyReleased

    private void printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printActionPerformed
        cetak();
    }//GEN-LAST:event_printActionPerformed

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
    private javax.swing.JButton print;
    private javax.swing.JTable tblsiswa;
    private com.toedter.calendar.JDateChooser tglTemu;
    private javax.swing.JTextField txtcarisiswa;
    // End of variables declaration//GEN-END:variables
}
