package tampilan;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import koneksi.koneksi;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 * Form Presensi Kehadiran Siswa per Kelas & Pertemuan.
 */
public class menu_absenSiswa extends javax.swing.JInternalFrame {

    private Connection con = new koneksi().getConnection();
    private DefaultTableModel tabmode;
    PreparedStatement ps;
    ResultSet rs;
    private boolean sedangMemuat = false;

    public menu_absenSiswa() {
        UITheme.setup();
        initComponents();
        applyModernEnhancements();
        loadcombo();
        kosong();
        aktif();
        datatable();

        tglTemu.addPropertyChangeListener("date", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (!sedangMemuat) {
                    datatable();
                }
            }
        });
        jTemu.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent evt) {
                if (!sedangMemuat) {
                    datatable();
                }
            }
        });
    }

    private void applyModernEnhancements() {
        UITheme.prepareInternalFrame(this);
        UITheme.styleCard(jPanel1);
        UITheme.styleCard(jPanel2);
        UITheme.styleComboBox(cbkelas);
        UITheme.styleDateChooser(tglTemu);
        UITheme.styleSpinner(jTemu);
        UITheme.styleTextField(txtcarisiswa, "Cari NISN atau nama siswa...");
        UITheme.styleActionButton(bsimpan, "SAVE", "/resource/ui_act_save.png");
        UITheme.styleActionButton(bbatal, "RESET", "/resource/ui_act_reset.png");
        UITheme.styleActionButton(print, "PRINT", null);
        UITheme.styleTable(tblsiswa, jScrollPane1);
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
            HashMap<String, Object> parameter = new HashMap<String, Object>();
            parameter.put(net.sf.jasperreports.engine.JRParameter.REPORT_LOCALE, new java.util.Locale("id", "ID"));
            java.io.InputStream logoStream = getClass().getResourceAsStream("/resource/logosekolah.jpeg");
            parameter.put("logo", logoStream);
            parameter.put("paramKelas", kelasYangDipilih);
            parameter.put("paramIdGuru", idGuruLogin);

            JasperPrint jasperPrint = JasperFillManager.fillReport(path, parameter, con);
            JasperViewer.viewReport(jasperPrint, false);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(rootPane, "Gagal mencetak: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    protected void aktif() {
        txtcarisiswa.requestFocus();
    }

    protected void kosong() {
        sedangMemuat = true;
        try {
            jTemu.setValue(Integer.valueOf(1));
            txtcarisiswa.setText("");
            tglTemu.setDate(new Date());

            if (cbkelas.getItemCount() > 0) {
                cbkelas.setSelectedIndex(0);
            }
        } finally {
            sedangMemuat = false;
        }
    }

    protected void datatable() {
        String[] baris = {"NISN", "Nama Siswa", "Jenis Kelamin (L/P)", "Kehadiran"};
        DefaultTableModel modelBaru = new DefaultTableModel(null, baris) {
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

        this.tabmode = modelBaru;
        tblsiswa.setModel(modelBaru);

        if (cbkelas.getSelectedIndex() <= 0 || cbkelas.getSelectedItem() == null) {
            return;
        }

        String cariitem = "%" + txtcarisiswa.getText().trim() + "%";
        String kelasTerpilih = cbkelas.getSelectedItem().toString();
        java.sql.Date sqlDate = (tglTemu.getDate() != null)
                ? new java.sql.Date(tglTemu.getDate().getTime())
                : new java.sql.Date(new Date().getTime());
        int pertemuanKe = ((Number) jTemu.getValue()).intValue();

        try {
            String sql = "SELECT a.nisn, a.nama, a.jkel, COALESCE(ab.status_hadir, 0) AS status_hadir "
                    + "FROM tbl_siswa a "
                    + "JOIN tbl_kelas b ON a.kelas = b.id_kelas "
                    + "LEFT JOIN tbl_absen ab ON a.nisn = ab.nisn AND ab.tanggal = ? AND ab.pertemuan_ke = ? "
                    + "WHERE b.kelas = ? AND (a.nisn LIKE ? OR a.nama LIKE ?) "
                    + "ORDER BY a.nisn ASC";

            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setDate(1, sqlDate);
                pst.setInt(2, pertemuanKe);
                pst.setString(3, kelasTerpilih);
                pst.setString(4, cariitem);
                pst.setString(5, cariitem);

                try (ResultSet hasil = pst.executeQuery()) {
                    while (hasil.next()) {
                        modelBaru.addRow(new Object[]{
                            hasil.getString("nisn"),
                            hasil.getString("nama"),
                            hasil.getString("jkel"),
                            Boolean.valueOf(hasil.getInt("status_hadir") == 1)
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

            String sql = "SELECT DISTINCT k.kelas "
                    + "FROM tbl_kelas k "
                    + "LEFT JOIN tbl_jadwal j ON k.id_kelas = j.kelas "
                    + "WHERE k.kd_guru = ? OR j.kd_guru = ? "
                    + "ORDER BY k.kelas ASC";
            boolean punyaKelas = false;
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setString(1, idGuruLogin);
                pst.setString(2, idGuruLogin);
                try (ResultSet hasil = pst.executeQuery()) {
                    while (hasil.next()) {
                        cbkelas.addItem(hasil.getString("kelas"));
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        pnlBackground = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblSubForm = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        cbkelas = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        tglTemu = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        jTemu = new javax.swing.JSpinner();
        lblHint = new javax.swing.JLabel();
        bsimpan = new javax.swing.JButton();
        bbatal = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        lblTableTitle = new javax.swing.JLabel();
        lblTableSub = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtcarisiswa = new javax.swing.JTextField();
        print = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblsiswa = new javax.swing.JTable();

        setBorder(null);
        setMaximumSize(new java.awt.Dimension(214647, 2147483647));

        pnlBackground.setBackground(new java.awt.Color(241, 245, 249));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(226, 232, 240), 1, true));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(15, 23, 42));
        jLabel1.setText("Detail Pertemuan & Presensi");

        lblSubForm.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblSubForm.setForeground(new java.awt.Color(100, 116, 139));
        lblSubForm.setText("Pilih kelas, tanggal, dan pertemuan tatap muka");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(51, 65, 85));
        jLabel11.setText("Pilih Kelas");

        cbkelas.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        cbkelas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih Kelas --" }));
        cbkelas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbkelasActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 65, 85));
        jLabel7.setText("Tanggal Pertemuan");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 65, 85));
        jLabel8.setText("Pertemuan Ke-");

        jTemu.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jTemu.setModel(new javax.swing.SpinnerNumberModel(1, 1, 48, 1));

        lblHint.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblHint.setForeground(new java.awt.Color(100, 116, 139));
        lblHint.setText("<html>Centang kolom <b>Kehadiran</b> pada tabel di samping untuk menandai siswa yang hadir, lalu klik <b>Simpan</b>.</html>");

        bsimpan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bsimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/ui_act_save.png"))); // NOI18N
        bsimpan.setText("SIMPAN");
        bsimpan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bsimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bsimpanActionPerformed(evt);
            }
        });

        bbatal.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bbatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/ui_act_reset.png"))); // NOI18N
        bbatal.setText("BATAL");
        bbatal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bbatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bbatalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblSubForm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbkelas, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tglTemu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTemu)
                    .addComponent(lblHint, javax.swing.GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(bsimpan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(10, 10, 10)
                        .addComponent(bbatal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(22, 22, 22))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1)
                .addGap(4, 4, 4)
                .addComponent(lblSubForm)
                .addGap(22, 22, 22)
                .addComponent(jLabel11)
                .addGap(6, 6, 6)
                .addComponent(cbkelas, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(jLabel7)
                .addGap(6, 6, 6)
                .addComponent(tglTemu, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(jLabel8)
                .addGap(6, 6, 6)
                .addComponent(jTemu, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblHint, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 90, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bsimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bbatal, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(226, 232, 240), 1, true));

        lblTableTitle.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblTableTitle.setForeground(new java.awt.Color(15, 23, 42));
        lblTableTitle.setText("Daftar Kehadiran Siswa");

        lblTableSub.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblTableSub.setForeground(new java.awt.Color(100, 116, 139));
        lblTableSub.setText("Data kehadiran siswa berdasarkan kelas & pertemuan");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 65, 85));
        jLabel10.setText("Cari:");

        txtcarisiswa.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
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

        print.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        print.setText("PRINT");
        print.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printActionPerformed(evt);
            }
        });

        tblsiswa.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        tblsiswa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "NISN", "Nama Siswa", "Jenis Kelamin (L/P)", "Kehadiran"
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
        tblsiswa.getTableHeader().setReorderingAllowed(false);
        tblsiswa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblsiswaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblsiswa);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 540, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTableTitle)
                            .addComponent(lblTableSub))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                        .addComponent(jLabel10)
                        .addGap(8, 8, 8)
                        .addComponent(txtcarisiswa, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(print)))
                .addGap(22, 22, 22))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblTableTitle)
                        .addGap(2, 2, 2)
                        .addComponent(lblTableSub))
                    .addComponent(jLabel10)
                    .addComponent(txtcarisiswa, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(print, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE)
                .addGap(22, 22, 22))
        );

        javax.swing.GroupLayout pnlBackgroundLayout = new javax.swing.GroupLayout(pnlBackground);
        pnlBackground.setLayout(pnlBackgroundLayout);
        pnlBackgroundLayout.setHorizontalGroup(
            pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBackgroundLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(24, 24, 24))
        );
        pnlBackgroundLayout.setVerticalGroup(
            pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBackgroundLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(24, 24, 24))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlBackground, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlBackground, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
        int pertemuan_ke = ((Number) jTemu.getValue()).intValue();
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
            String sql = "INSERT INTO tbl_absen (nisn, tanggal, pertemuan_ke, status_hadir) "
                    + "VALUES (?, ?, ?, ?) "
                    + "ON DUPLICATE KEY UPDATE status_hadir = VALUES(status_hadir)";
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                for (int i = 0; i < jumlahBaris; i++) {
                    String nisn = model.getValueAt(i, 0).toString();

                    Object objKehadiran = model.getValueAt(i, 3);
                    boolean isChecked = (objKehadiran instanceof Boolean) && ((Boolean) objKehadiran).booleanValue();

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
    private javax.swing.JLabel lblHint;
    private javax.swing.JLabel lblSubForm;
    private javax.swing.JLabel lblTableSub;
    private javax.swing.JLabel lblTableTitle;
    private javax.swing.JPanel pnlBackground;
    private javax.swing.JButton print;
    private javax.swing.JTable tblsiswa;
    private com.toedter.calendar.JDateChooser tglTemu;
    private javax.swing.JTextField txtcarisiswa;
    // End of variables declaration//GEN-END:variables
}
