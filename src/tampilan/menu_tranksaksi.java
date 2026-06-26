/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tampilan;


import java.sql.PreparedStatement;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import static koneksi.koneksi.con;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author User
 */
public class menu_tranksaksi extends javax.swing.JInternalFrame {

    /**
     * Creates new form menu_tranksaksi
     */
    public menu_tranksaksi() {
        initComponents();
        kosong();
        String time = String.valueOf(System.currentTimeMillis());
        String pendek = time.substring(time.length() - 4);
        trank.setText("TRX-" + pendek);
        trank.setEnabled(false);
        //cetakData(trank.getText());
        datatable();
    }
    
    public void kosong() {
    txtnisn.setText("");
    trank.setText("");
    nama.setText("silahkan pilih siswa");
    kelas.setText("silahkan pilih siswa");
    txtyear.setText("");
    nominal.setText("0");
}
    public void datatable() {
   DefaultTableModel model = new DefaultTableModel();
    model.addColumn("No Transaksi");
    model.addColumn("NISN");
    model.addColumn("Nama");
    model.addColumn("Bulan");
    model.addColumn("Tahun");
    model.addColumn("Nominal");

    try {
        // Gunakan nama kolom yang tepat (bulan_bayar, tahun_bayar)
        String sql = "SELECT t.*, s.nama FROM tbl_transaksi t " +
                     "LEFT JOIN tbl_siswa s ON t.nisn = s.nisn " +
                     "ORDER BY t.no_transaksi DESC";
        
        java.sql.Statement st = koneksi.koneksi.getConnection().createStatement();
        java.sql.ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getString("no_transaksi"),
                rs.getString("nisn"),
                rs.getString("nama"),
                rs.getString("bulan_bayar"), // Ambil dari kolom yang benar
                rs.getString("tahun_bayar"), // Ambil dari kolom yang benar
                rs.getString("jumlah_bayar")
            });
        }
        tblsiswa.setModel(model);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Gagal memuat tabel: " + e.getMessage());
    }
}
    
   public void setSiswa(String nisn, String namaSiswa, String kelasSiswa) {
    txtnisn.setText(nisn);
    nama.setText(namaSiswa);
    kelas.setText(kelasSiswa);
    
    // Kita bersihkan string agar tidak ada spasi tambahan & huruf besar semua
    String k = kelasSiswa.toUpperCase().trim();
    
    int nominalBayar = 0;
    
    // URUTAN ADALAH KUNCI! 
    // Cek "XII" dulu, baru "XI", terakhir "X"
    if (k.contains("XII")) {
        nominalBayar = 250000;
    } else if (k.contains("XI")) {
        nominalBayar = 200000;
    } else if (k.contains("X")) {
        nominalBayar = 150000;
    } else {
        nominalBayar = 0; // Jika kelas tidak terdeteksi
    }
    
    nominal.setText(String.valueOf(nominalBayar));
}
  public void cetakData(String text) {
    try {
        // Path disesuaikan untuk pembacaan GetResourceAsStream
        String path = "/report/nota.jasper"; 
        
        // PERBAIKAN: Menggunakan tanda < > (bukan kurung biasa)
        java.util.Map<String, Object> map = new java.util.HashMap<>();
        
        // PERBAIKAN: Mengambil data dari textfield 'trank', bukan variabel 'noTrx' yang ghoib
        map.put("no_transaksi", trank.getText());
        
        // PERBAIKAN: Memperbaiki salah ketik package dan menyusun fungsi JasperFillManager dengan benar
        net.sf.jasperreports.engine.JasperPrint printReport = JasperFillManager.fillReport(
            getClass().getResourceAsStream(path), 
            map, 
            koneksi.koneksi.getConnection()
        );
        
        // Menampilkan nota pembayaran
        JasperViewer.viewReport(printReport, false);
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Gagal cetak: " + e.getMessage());
    }
}
  
 public void cetak() {
    try {
        // Menggunakan getResourceAsStream agar path aman saat aplikasi dijalankan
        String path = "/report/reportkeuangan.jasper";
        java.util.HashMap<String, Object> parameter = new java.util.HashMap<>();
        
        // PERBAIKAN: Mengganti variabel 'con' yang null menjadi 'koneksi.koneksi.getConnection()'
        net.sf.jasperreports.engine.JasperPrint print = JasperFillManager.fillReport(
            getClass().getResourceAsStream(path),
            parameter,
            koneksi.koneksi.getConnection() // Menggunakan koneksi terpusat yang sudah pasti aktif
        );
        
        JasperViewer.viewReport(print, false);
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Dokumen Tidak Ada atau Kosong: " + ex.getMessage());
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtnisn = new javax.swing.JTextField();
        bcari = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        trank = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        nama = new javax.swing.JLabel();
        txt2 = new javax.swing.JLabel();
        kelas = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        txt3 = new javax.swing.JLabel();
        cbmonth = new javax.swing.JComboBox<>();
        txt4 = new javax.swing.JLabel();
        txtyear = new javax.swing.JTextField();
        txt5 = new javax.swing.JLabel();
        nominal = new javax.swing.JLabel();
        simpan = new javax.swing.JButton();
        bbatal = new javax.swing.JButton();
        print = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblsiswa = new javax.swing.JTable();

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("NISN");

        bcari.setText("CARI SISWA");
        bcari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bcariActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("NO Tranksaksi");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtnisn, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bcari)
                .addGap(79, 79, 79)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(trank, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtnisn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bcari)
                    .addComponent(jLabel3)
                    .addComponent(trank, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(48, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel2.setText("NAMA SISWA = ");

        nama.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        nama.setText("silahkan pilih siswa");

        txt2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txt2.setText("Kelas =");

        kelas.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        kelas.setText("silahkan pilih siswa");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(nama)
                .addGap(103, 103, 103)
                .addComponent(txt2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(kelas)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(nama)
                    .addComponent(txt2)
                    .addComponent(kelas))
                .addContainerGap(39, Short.MAX_VALUE))
        );

        txt3.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txt3.setText("BULAN ");

        cbmonth.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Januari", "Februari", "Maret", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember" }));

        txt4.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txt4.setText("TAHUN");

        txt5.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txt5.setText("Total Tagihan");

        nominal.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        nominal.setText(".....");

        simpan.setText("Simpan");
        simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simpanActionPerformed(evt);
            }
        });

        bbatal.setText("batal");
        bbatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bbatalActionPerformed(evt);
            }
        });

        print.setText("print");
        print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txt3)
                        .addGap(18, 18, 18)
                        .addComponent(cbmonth, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(123, 123, 123)
                        .addComponent(txt4)
                        .addGap(43, 43, 43)
                        .addComponent(txtyear, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(20, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(txt5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(nominal)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(bbatal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(simpan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(print, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(16, 16, 16))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt3)
                    .addComponent(cbmonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt4)
                    .addComponent(txtyear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt5)
                            .addComponent(nominal)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(simpan)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bbatal)
                .addGap(18, 18, 18)
                .addComponent(print)
                .addContainerGap(56, Short.MAX_VALUE))
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
        jScrollPane1.setViewportView(tblsiswa);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 8, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bcariActionPerformed
pop_up_siswa popup = new pop_up_siswa(this);
        popup.setVisible(true);
    }//GEN-LAST:event_bcariActionPerformed

    private void simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpanActionPerformed
   if (txtnisn.getText().isEmpty() || trank.getText().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Data belum lengkap!");
        return;
    }

    try {
        String sql = "INSERT INTO tbl_transaksi (no_transaksi, nisn, jumlah_bayar, bulan_bayar, tahun_bayar) VALUES (?, ?, ?, ?, ?)";
        java.sql.PreparedStatement ps = koneksi.koneksi.getConnection().prepareStatement(sql);
        
        ps.setString(1, trank.getText());                 
        ps.setString(2, txtnisn.getText());               
        ps.setString(3, nominal.getText());               
        ps.setString(4, cbmonth.getSelectedItem().toString()); 
        ps.setString(5, txtyear.getText());               
        
        ps.executeUpdate();
        JOptionPane.showMessageDialog(this, "Pembayaran Berhasil Disimpan!");
        
        // 1. Cetak data yang BARU SAJA sukses disimpan ke database
        cetakData(trank.getText());
        
        // 2. Kosongkan field inputan
        kosong();
        
        // 3. GENERATE KEMBALI nomor transaksi baru untuk transaksi berikutnya setelah dikosongkan
        String time = String.valueOf(System.currentTimeMillis());
        String pendek = time.substring(time.length() - 4);
        trank.setText("TRX-" + pendek);
        
        // 4. Refresh isi tabel
        datatable(); 
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Gagal Simpan: " + e.getMessage());
    }
    }//GEN-LAST:event_simpanActionPerformed

    private void bbatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bbatalActionPerformed
    kosong();
    }//GEN-LAST:event_bbatalActionPerformed

    private void printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printActionPerformed
    cetak();
    }//GEN-LAST:event_printActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bbatal;
    private javax.swing.JButton bcari;
    private javax.swing.JComboBox<String> cbmonth;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel kelas;
    private javax.swing.JLabel nama;
    private javax.swing.JLabel nominal;
    private javax.swing.JButton print;
    private javax.swing.JButton simpan;
    private javax.swing.JTable tblsiswa;
    private javax.swing.JTextField trank;
    private javax.swing.JLabel txt2;
    private javax.swing.JLabel txt3;
    private javax.swing.JLabel txt4;
    private javax.swing.JLabel txt5;
    private javax.swing.JTextField txtnisn;
    private javax.swing.JTextField txtyear;
    // End of variables declaration//GEN-END:variables
}
