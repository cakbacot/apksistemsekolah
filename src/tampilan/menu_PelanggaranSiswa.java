package tampilan;

import java.awt.event.KeyEvent;
import java.io.InputStream;
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
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 * Form Catatan Pelanggaran Siswa (Modern Navy & Royal Blue).
 */
public class menu_PelanggaranSiswa extends javax.swing.JInternalFrame {

    private Connection con = new koneksi().getConnection();
    private DefaultTableModel tabmode;
    PreparedStatement ps;
    ResultSet rs;

    public menu_PelanggaranSiswa() {
        UITheme.setup();
        initComponents();
        UITheme.prepareInternalFrame(this);
        applyModernTheme();
        loadcombo();
        loadComboKelas();
        kosong();
        aktif();
        datatable();
    }

    private void applyModernTheme() {
        pnlRoot.setBackground(UITheme.BG_MAIN);
        UITheme.styleCard(jPanel1);
        UITheme.styleCard(jPanel2);

        UITheme.styleTextField(txtnisn, "Masukkan NISN siswa...");

        UITheme.styleTextField(txtnama, "Nama siswa otomatis terisi");
        txtnama.putClientProperty("JTextField.showClearButton", Boolean.FALSE);
        txtnama.putClientProperty("FlatLaf.style",
                "arc: 10; margin: 6,12,6,12; background: #F8FAFC; borderColor: #E2E8F0");

        UITheme.styleTextField(txtkelas, "Kelas otomatis");
        txtkelas.putClientProperty("JTextField.showClearButton", Boolean.FALSE);
        txtkelas.putClientProperty("FlatLaf.style",
                "arc: 10; margin: 6,12,6,12; background: #F8FAFC; borderColor: #E2E8F0");

        UITheme.styleDateChooser(tglIinput);
        UITheme.styleComboBox(cbkategori);
        UITheme.styleTextArea(txtketerangan, jScrollPane2, "Tuliskan kronologi atau keterangan pelanggaran...");
        UITheme.styleTextField(txtcarisiswa, "Ketik NISN atau nama siswa...");

        UITheme.styleActionButton(bcarisiswa, "SOFT", "/resource/ui_act_search.png");
        UITheme.styleActionButton(btnScanNISN, "NAVY", null);
        UITheme.styleActionButton(bsimpan, "SAVE", "/resource/ui_act_save.png");
        UITheme.styleActionButton(bbatal, "RESET", "/resource/ui_act_reset.png");

        UITheme.styleTable(tblpelanggaran, jScrollPane3);
    }
    private void cetakSiswa(String nis) {
        try {
            HashMap<String, Object> param = new HashMap<>();
            param.put("nis", nis);   // String, sesuai parameter di report

            JasperReport jr = JasperCompileManager.compileReport(
                "src/report/reportPersiswa.jrxml");
            param.put(net.sf.jasperreports.engine.JRParameter.REPORT_LOCALE, new java.util.Locale("id", "ID"));
                InputStream logoStream = getClass().getResourceAsStream("/resource/logoSekolah.png"); 
            if (logoStream == null) {
                System.out.println("GAGAL: File gambar tidak ditemukan di resources!");
            }
            param.put("logo", logoStream);
            JasperPrint jp = JasperFillManager.fillReport(jr, param, con);
            JasperViewer.viewReport(jp, false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal cetak: " + e.getMessage());
        }
    }  

    protected void aktif() {
        txtcarisiswa.requestFocus();
    }
    
    private void cetakPerBulan(int bulan, int tahun) {
        try {
            HashMap<String, Object> param = new HashMap<>();
            param.put("bulan", bulan);
            param.put("tahun", tahun);

            JasperReport jr = JasperCompileManager.compileReport(
                "src/report/reportPerbulan.jrxml");   // pakai .jrxml, bukan .jasper
            param.put(net.sf.jasperreports.engine.JRParameter.REPORT_LOCALE, new java.util.Locale("id", "ID"));
            InputStream logoStream = getClass().getResourceAsStream("/resource/logoSekolah.png"); 
        if (logoStream == null) {
            System.out.println("GAGAL: File gambar tidak ditemukan di resources!");
        }
        param.put("logo", logoStream);
            JasperPrint jp = JasperFillManager.fillReport(jr, param, con);
            JasperViewer.viewReport(jp, false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal cetak: " + e.getMessage());
        }
    }
    
    
    private void cetakKelas(String kelas) {
        try {
            HashMap<String, Object> param = new HashMap<>();
            param.put("kelas", kelas);   // String, sesuai parameter di report

            JasperReport jr = JasperCompileManager.compileReport(
                "src/report/reportPerkelas.jrxml");
             param.put(net.sf.jasperreports.engine.JRParameter.REPORT_LOCALE, new java.util.Locale("id", "ID"));
            InputStream logoStream = getClass().getResourceAsStream("/resource/logoSekolah.png"); 
        if (logoStream == null) {
            System.out.println("GAGAL: File gambar tidak ditemukan di resources!");
        }
        param.put("logo", logoStream);
            JasperPrint jp = JasperFillManager.fillReport(jr, param, con);

            if (jp.getPages().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Tidak ada data pelanggaran untuk " + kelas);
                return;
            }

            JasperViewer.viewReport(jp, false);
            // Kalau mau langsung dialog printer tanpa preview:
            // JasperPrintManager.printReport(jp, true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal cetak: " + e.getMessage());
        }
    }

    protected void kosong() {
        txtnisn.setText("");
        txtnama.setText("");
        txtkelas.setText("");
        txtketerangan.setText("");
        txtcarisiswa.setText("");
        tglIinput.setDate(new Date());

        if (cbkategori.getItemCount() > 0) {
            cbkategori.setSelectedIndex(0);
        }
    }

    protected void datatable() {
        String[] baris = {"NIS", "Nama Siswa", "Tanggal", "Kelas", "Kategori", "Keterangan"};
    tabmode = new DefaultTableModel(null, baris) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    tblpelanggaran.setModel(tabmode);

    if (tblpelanggaran.getColumnModel().getColumnCount() >= 6) {
        tblpelanggaran.getColumnModel().getColumn(0).setPreferredWidth(95);
        tblpelanggaran.getColumnModel().getColumn(1).setPreferredWidth(150);
        tblpelanggaran.getColumnModel().getColumn(2).setPreferredWidth(90);
        tblpelanggaran.getColumnModel().getColumn(3).setPreferredWidth(90);
        tblpelanggaran.getColumnModel().getColumn(4).setPreferredWidth(135);
        tblpelanggaran.getColumnModel().getColumn(5).setPreferredWidth(180);
    }

    String cariitem = txtcarisiswa.getText().trim();
    String kelasDipilih = (cbkelas.getSelectedItem() != null)
            ? cbkelas.getSelectedItem().toString().trim()
            : "Semua Kelas";

    try {
        String sql = "SELECT p.id_pelanggaran, p.nisn, s.nama, p.tgl_pelanggaran, "
                + "k.kelas, p.kategori_pelanggaran, p.keterangan "
                + "FROM tbl_pelanggaran p "
                + "JOIN tbl_siswa s ON p.nisn = s.nisn "
                + "JOIN tbl_kelas k ON s.kelas = k.id_kelas "
                + "WHERE (p.nisn LIKE ? OR s.nama LIKE ?) ";

        if (!kelasDipilih.equalsIgnoreCase("Semua Kelas")) {
            sql += "AND k.kelas = ? ";
        }
        sql += "ORDER BY p.tgl_pelanggaran DESC, p.id_pelanggaran DESC";

        try (PreparedStatement stat = con.prepareStatement(sql)) {
            stat.setString(1, "%" + cariitem + "%");
            stat.setString(2, "%" + cariitem + "%");
            if (!kelasDipilih.equalsIgnoreCase("Semua Kelas")) {
                stat.setString(3, kelasDipilih);
            }

            try (ResultSet hasil = stat.executeQuery()) {
                while (hasil.next()) {
                    tabmode.addRow(new Object[]{
                        hasil.getString("nisn"),
                        hasil.getString("nama"),
                        hasil.getDate("tgl_pelanggaran"),
                        hasil.getString("kelas"),
                        hasil.getString("kategori_pelanggaran"),
                        hasil.getString("keterangan")
                    });
                }
            }
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Data pelanggaran gagal dipanggil: " + e.getMessage());
    }
   }

    private void loadcombo() {
        cbkategori.removeAllItems();
        cbkategori.addItem("-- Pilih Kategori --");
        cbkategori.addItem("Terlambat");
        cbkategori.addItem("Tidak Memakai Seragam");
        cbkategori.addItem("Bermain HP di Kelas");
        cbkategori.addItem("Makan di Kelas");
        cbkategori.addItem("Merokok");
        cbkategori.addItem("Bolos Sekolah");
        cbkategori.addItem("Bertengkar");
        cbkategori.addItem("Mencuri");
        cbkategori.addItem("Membawa Senjata Tajam");
    }
    
    private void loadComboKelas() {
    cbkelas.removeAllItems();
    cbkelas.addItem("Semua Kelas");
    try {
        String sql = "SELECT kelas FROM tbl_kelas ORDER BY id_kelas ASC";
        try (Statement st = con.createStatement();
             ResultSet hasil = st.executeQuery(sql)) {
            while (hasil.next()) {
                cbkelas.addItem(hasil.getString("kelas"));
            }
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Gagal memuat data kelas: " + e.getMessage());
    }
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        cbkategori1 = new javax.swing.JComboBox<>();
        pnlRoot = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblFormSub = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtnisn = new javax.swing.JTextField();
        bcarisiswa = new javax.swing.JButton();
        btnScanNISN = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        txtnama = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtkelas = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        tglIinput = new com.toedter.calendar.JDateChooser();
        jLabel14 = new javax.swing.JLabel();
        cbkategori = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtketerangan = new javax.swing.JTextArea();
        bsimpan = new javax.swing.JButton();
        bbatal = new javax.swing.JButton();
        brepPerBulanan = new javax.swing.JButton();
        brepSiswa = new javax.swing.JButton();
        brepKelas = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        lblTableTitle = new javax.swing.JLabel();
        lblTableSub = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtcarisiswa = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblpelanggaran = new javax.swing.JTable();
        cbkelas = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();

        cbkategori1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        cbkategori1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbkategori1ActionPerformed(evt);
            }
        });

        setBackground(new java.awt.Color(241, 245, 249));
        setBorder(null);
        setMaximumSize(new java.awt.Dimension(214647, 2147483647));

        pnlRoot.setBackground(new java.awt.Color(241, 245, 249));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(226, 232, 240), 1, true));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(15, 23, 42));
        jLabel1.setText("Form Catatan Pelanggaran Siswa");

        lblFormSub.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblFormSub.setForeground(new java.awt.Color(100, 116, 139));
        lblFormSub.setText("Pindai kartu pelajar atau cari NISN untuk mencatat pelanggaran");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(51, 65, 85));
        jLabel16.setText("NISN Siswa");

        txtnisn.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtnisn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnisnActionPerformed(evt);
            }
        });

        bcarisiswa.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bcarisiswa.setText("Cari");
        bcarisiswa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bcarisiswa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bcarisiswaActionPerformed(evt);
            }
        });

        btnScanNISN.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnScanNISN.setText("Scan");
        btnScanNISN.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnScanNISN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnScanNISNActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(51, 65, 85));
        jLabel12.setText("Nama Siswa");

        txtnama.setEditable(false);
        txtnama.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtnama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnamaActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(51, 65, 85));
        jLabel13.setText("Kelas");

        txtkelas.setEditable(false);
        txtkelas.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtkelas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtkelasActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(51, 65, 85));
        jLabel9.setText("Tanggal Pelanggaran");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(51, 65, 85));
        jLabel14.setText("Kategori Pelanggaran");

        cbkategori.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        cbkategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbkategoriActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(51, 65, 85));
        jLabel15.setText("Keterangan / Kronologi");

        txtketerangan.setColumns(20);
        txtketerangan.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtketerangan.setRows(3);
        jScrollPane2.setViewportView(txtketerangan);

        bsimpan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bsimpan.setText("Simpan");
        bsimpan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bsimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bsimpanActionPerformed(evt);
            }
        });

        bbatal.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bbatal.setText("Batal / Reset");
        bbatal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bbatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bbatalActionPerformed(evt);
            }
        });

        brepPerBulanan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        brepPerBulanan.setText("Cetak Bulanan");
        brepPerBulanan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        brepPerBulanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                brepPerBulananActionPerformed(evt);
            }
        });

        brepSiswa.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        brepSiswa.setText("Cetak Persiswa");
        brepSiswa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        brepSiswa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                brepSiswaActionPerformed(evt);
            }
        });

        brepKelas.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        brepKelas.setText("Cetak Perkelas");
        brepKelas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        brepKelas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                brepKelasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(brepSiswa, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(brepKelas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblFormSub, javax.swing.GroupLayout.DEFAULT_SIZE, 362, Short.MAX_VALUE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtnisn, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                        .addGap(8, 8, 8)
                        .addComponent(bcarisiswa, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addComponent(btnScanNISN, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtnama)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtkelas, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tglIinput, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)))
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbkategori, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(bsimpan, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                        .addGap(12, 12, 12)
                        .addComponent(bbatal, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE))
                    .addComponent(brepPerBulanan, javax.swing.GroupLayout.DEFAULT_SIZE, 362, Short.MAX_VALUE))
                .addGap(57, 57, 57))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addGap(4, 4, 4)
                .addComponent(lblFormSub)
                .addGap(16, 16, 16)
                .addComponent(jLabel16)
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtnisn, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bcarisiswa, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnScanNISN, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addComponent(jLabel12)
                .addGap(6, 6, 6)
                .addComponent(txtnama, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel9))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtkelas, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tglIinput, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addComponent(jLabel14)
                .addGap(6, 6, 6)
                .addComponent(cbkategori, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel15)
                .addGap(16, 16, 16)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bsimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bbatal, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(brepPerBulanan, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(brepSiswa, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(brepKelas, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(226, 232, 240), 1, true));

        lblTableTitle.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblTableTitle.setForeground(new java.awt.Color(15, 23, 42));
        lblTableTitle.setText("Riwayat Pelanggaran Siswa");

        lblTableSub.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblTableSub.setForeground(new java.awt.Color(100, 116, 139));
        lblTableSub.setText("Daftar catatan pelanggaran tata tertib siswa terbaru");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 65, 85));
        jLabel10.setText("Cari Siswa:");

        txtcarisiswa.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtcarisiswa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcarisiswaActionPerformed(evt);
            }
        });
        txtcarisiswa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcarisiswaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtcarisiswaKeyReleased(evt);
            }
        });

        tblpelanggaran.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "NIS", "Nama Siswa", "Tanggal", "Kategori", "Keterangan"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblpelanggaran.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblpelanggaranMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblpelanggaran);

        cbkelas.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        cbkelas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbkelasActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(51, 65, 85));
        jLabel11.setText("Kelas :");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTableTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTableSub, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(29, 29, 29)
                        .addComponent(txtcarisiswa, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbkelas, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 444, Short.MAX_VALUE))
                .addGap(24, 24, 24))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(lblTableTitle)
                .addGap(4, 4, 4)
                .addComponent(lblTableSub)
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtcarisiswa, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbkelas, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addGap(16, 16, 16)
                .addComponent(jScrollPane3)
                .addGap(24, 24, 24))
        );

        javax.swing.GroupLayout pnlRootLayout = new javax.swing.GroupLayout(pnlRoot);
        pnlRoot.setLayout(pnlRootLayout);
        pnlRootLayout.setHorizontalGroup(
            pnlRootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRootLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(24, 24, 24))
        );
        pnlRootLayout.setVerticalGroup(
            pnlRootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRootLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(pnlRootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(24, 24, 24))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlRoot, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlRoot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtcarisiswaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcarisiswaActionPerformed
        datatable();
    }//GEN-LAST:event_txtcarisiswaActionPerformed

    private void bsimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bsimpanActionPerformed
        simpanPelanggaran();
    }//GEN-LAST:event_bsimpanActionPerformed

    private void bbatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bbatalActionPerformed
        int konfirmasi = JOptionPane.showConfirmDialog(this,
                "Batal melakukan input pelanggaran? Semua isian dan centang akan dikosongkan.",
                "Konfirmasi Batal",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (konfirmasi == JOptionPane.YES_OPTION) {
            kosong();
            datatable();

            JOptionPane.showMessageDialog(this, "Form berhasil dibersihkan.", "Batal", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_bbatalActionPerformed

    private void txtcarisiswaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcarisiswaKeyPressed
        datatable();
    }//GEN-LAST:event_txtcarisiswaKeyPressed

    private void txtcarisiswaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcarisiswaKeyReleased
        datatable();
    }//GEN-LAST:event_txtcarisiswaKeyReleased

    private void bcarisiswaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bcarisiswaActionPerformed
        String nisn = txtnisn.getText().trim();

        if (nisn.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Masukkan NISN terlebih dahulu!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            String sql = "SELECT s.nama, k.kelas FROM tbl_siswa s "
                    + "LEFT JOIN tbl_kelas k ON s.kelas = k.id_kelas "
                    + "WHERE s.nisn = ?";

            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setString(1, nisn);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        txtnama.setText(rs.getString("nama"));
                        txtkelas.setText(rs.getString("kelas"));
                    } else {
                        JOptionPane.showMessageDialog(this, "Siswa dengan NISN " + nisn + " tidak ditemukan", "Peringatan", JOptionPane.WARNING_MESSAGE);
                        txtnama.setText("");
                        txtkelas.setText("");
                    }
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal mencari siswa: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_bcarisiswaActionPerformed

    private void txtnisnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnisnActionPerformed
        bcarisiswaActionPerformed(evt);
    }//GEN-LAST:event_txtnisnActionPerformed

    private void txtkelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtkelasActionPerformed
    }//GEN-LAST:event_txtkelasActionPerformed

    private void txtnamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnamaActionPerformed
    }//GEN-LAST:event_txtnamaActionPerformed

    private void cbkategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbkategoriActionPerformed
    }//GEN-LAST:event_cbkategoriActionPerformed

    private void btnScanNISNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnScanNISNActionPerformed
        frm_ScannerBarcode scanner = new frm_ScannerBarcode(null, true);
        scanner.mulaiScan();
        scanner.setVisible(true);

        String hasilScan = scanner.getHasilScan();

        if (hasilScan != null && !hasilScan.trim().isEmpty()) {
            txtnisn.setText(hasilScan.trim());
            ambilDataSiswa(hasilScan.trim());
        }
    }//GEN-LAST:event_btnScanNISNActionPerformed

    private void brepPerBulananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_brepPerBulananActionPerformed
         String[] daftarBulan = {
            "01 - Januari", "02 - Februari", "03 - Maret", "04 - April",
            "05 - Mei", "06 - Juni", "07 - Juli", "08 - Agustus",
            "09 - September", "10 - Oktober", "11 - November", "12 - Desember"
        };

        // Munculkan pop-up dropdown pilihan bulan
        String bulanTerpilih = (String) JOptionPane.showInputDialog(
            this,
            "Pilih Bulan Laporan:",
            "Cetak Laporan Bulanan",
            JOptionPane.QUESTION_MESSAGE,
            null,
            daftarBulan,
            daftarBulan[0]
        );

        // Jika user menekan OK (tidak klik Cancel)
        if (bulanTerpilih != null) {
            // Munculkan pop-up input tahun (default tahun sekarang)
            String tahunInput = JOptionPane.showInputDialog(this, "Masukkan Tahun (Contoh: 2026):", "2026");

            if (tahunInput != null && !tahunInput.trim().isEmpty()) {
                int bulan = Integer.parseInt(bulanTerpilih.substring(0, 2)); // Ambil 2 angka depan ("01" -> 1)
                int tahun = Integer.parseInt(tahunInput.trim());

                cetakPerBulan(bulan, tahun);
            }
        }
    }//GEN-LAST:event_brepPerBulananActionPerformed

    private void brepSiswaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_brepSiswaActionPerformed
        String nis = txtnisn.getText().trim();
        if (nis.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih data siswa di tabel dulu.");
            return;
        }
        cetakSiswa(nis);
        brepSiswa.setVisible(false);
    }//GEN-LAST:event_brepSiswaActionPerformed

    private void brepKelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_brepKelasActionPerformed
        String kelas = cbkelas.getSelectedItem().toString();
    cetakKelas(kelas);
    }//GEN-LAST:event_brepKelasActionPerformed

    private void cbkategori1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbkategori1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbkategori1ActionPerformed

    private void cbkelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbkelasActionPerformed
        if (cbkelas.getSelectedItem() != null) {
            datatable();
        }// TODO add your handling code here:
    }//GEN-LAST:event_cbkelasActionPerformed

    private void tblpelanggaranMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblpelanggaranMouseClicked
      int baris = tblpelanggaran.getSelectedRow();

        if (baris != -1) {
            String nis = tblpelanggaran.getValueAt(baris, 0).toString();
            String nama = tblpelanggaran.getValueAt(baris, 1).toString();
            String kelas = tblpelanggaran.getValueAt(baris, 2).toString();
            String tgl = tblpelanggaran.getValueAt(baris, 3).toString();
            String kategori = tblpelanggaran.getValueAt(baris, 4).toString();
            String keterangan = tblpelanggaran.getValueAt(baris, 5).toString();

            // Masukkan ke semua field di sebelah kiri
            txtnisn.setText(nis);
            txtnama.setText(nama);
            txtkelas.setText(kelas);
            cbkategori.setSelectedItem(kategori);
            txtketerangan.setText(keterangan);

            // Konversi teks tanggal (yyyy-MM-dd) ke JDateChooser
            try {
                java.util.Date date = new java.text.SimpleDateFormat("yyyy-MM-dd").parse(tgl);
                tglIinput.setDate(date);
            } catch (Exception e) {
                System.err.println("Gagal parsing tanggal: " + e.getMessage());
            }

            // Munculkan tombol cetak laporan per siswa & filter tabel
            brepSiswa.setVisible(true);
            txtcarisiswa.setText(nama);
            datatable();
        }  // TODO add your handling code here:
    }//GEN-LAST:event_tblpelanggaranMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bbatal;
    private javax.swing.JButton bcarisiswa;
    private javax.swing.JButton brepKelas;
    private javax.swing.JButton brepPerBulanan;
    private javax.swing.JButton brepSiswa;
    private javax.swing.JButton bsimpan;
    private javax.swing.JButton btnScanNISN;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbkategori;
    private javax.swing.JComboBox<String> cbkategori1;
    private javax.swing.JComboBox<String> cbkelas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblFormSub;
    private javax.swing.JLabel lblTableSub;
    private javax.swing.JLabel lblTableTitle;
    private javax.swing.JPanel pnlRoot;
    private javax.swing.JTable tblpelanggaran;
    private com.toedter.calendar.JDateChooser tglIinput;
    private javax.swing.JTextField txtcarisiswa;
    private javax.swing.JTextField txtkelas;
    private javax.swing.JTextArea txtketerangan;
    private javax.swing.JTextField txtnama;
    private javax.swing.JTextField txtnisn;
    // End of variables declaration//GEN-END:variables

    private void ambilDataSiswa(String nisn) {
        try {
            java.sql.Connection conn = koneksi.getConnection();

            String sql = "SELECT s.nama, k.kelas AS nama_kelas "
                    + "FROM tbl_siswa s "
                    + "JOIN tbl_kelas k ON s.kelas = k.id_kelas "
                    + "WHERE s.nisn = ?";

            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, nisn);
            java.sql.ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                txtnama.setText(rs.getString("nama"));
                txtkelas.setText(rs.getString("nama_kelas"));
                txtkelas.setEditable(false);
                txtketerangan.requestFocus();
            } else {
                javax.swing.JOptionPane.showMessageDialog(this,
                        "NISN: " + nisn + " tidak terdaftar!",
                        "Peringatan", javax.swing.JOptionPane.WARNING_MESSAGE);
                txtnisn.setText("");
                txtnama.setText("");
                txtkelas.setText("");
            }

            rs.close();
            pst.close();
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void simpanPelanggaran() {
        String nisn = txtnisn.getText().trim();
        if (nisn.isEmpty() || txtnama.getText().trim().isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Silakan Scan kartu siswa atau cari NISN terlebih dahulu!");
            return;
        }

        if (cbkategori.getSelectedIndex() <= 0 || cbkategori.getSelectedItem() == null) {
            javax.swing.JOptionPane.showMessageDialog(this, "Pilih kategori pelanggaran terlebih dahulu!");
            return;
        }
        String kategori = cbkategori.getSelectedItem().toString().trim();

        String keterangan = txtketerangan.getText().trim();

        if (kategori.isEmpty() || keterangan.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Kategori dan Keterangan tidak boleh kosong!");
            return;
        }

        try {
            java.sql.Connection conn = koneksi.getConnection();

            java.sql.Date tgl = (tglIinput.getDate() != null)
                    ? new java.sql.Date(tglIinput.getDate().getTime())
                    : java.sql.Date.valueOf(java.time.LocalDate.now());

            String sql = "INSERT INTO tbl_pelanggaran "
                    + "(nisn, tgl_pelanggaran, kategori_pelanggaran, keterangan) "
                    + "VALUES (?, ?, ?, ?)";

            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, nisn);
            pst.setDate(2, tgl);
            pst.setString(3, kategori);
            pst.setString(4, keterangan);

            int rows = pst.executeUpdate();

            if (rows > 0) {
                javax.swing.JOptionPane.showMessageDialog(this, "Pelanggaran berhasil dicatat!");
                kosongkanFormScan();
            }

            pst.close();
        } catch (java.sql.SQLIntegrityConstraintViolationException e) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "NISN tidak valid / tidak ada di tbl_siswa!",
                    "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Gagal menyimpan: " + e.getMessage());
        }
        datatable();
    }

    private void kosongkanFormScan() {
        txtnisn.setText("");
        txtnama.setText("");
        txtkelas.setText("");
        txtketerangan.setText("");
        tglIinput.setDate(new Date());
        if (cbkategori.getItemCount() > 0) {
            cbkategori.setSelectedIndex(0);
        }
        txtnisn.requestFocus();
    }
}
