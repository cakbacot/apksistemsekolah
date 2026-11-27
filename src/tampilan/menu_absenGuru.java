package tampilan;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import koneksi.koneksi;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 * Form Presensi Kehadiran Guru.
 */
public class menu_absenGuru extends javax.swing.JInternalFrame {

    private Connection con = new koneksi().getConnection();
    private DefaultTableModel tabmode;
    PreparedStatement ps;
    ResultSet rs;

    public menu_absenGuru() {
        UITheme.setup();
        initComponents();
        applyModernEnhancements();
        kosong();
        datatable();
        aktif();
        aturHakAkses();

        tglTemu.addPropertyChangeListener("date", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                datatable();
            }
        });
    }

    private void applyModernEnhancements() {
        UITheme.prepareInternalFrame(this);
        UITheme.styleCard(jPanel1);
        UITheme.styleCard(pnlTableCard);
        UITheme.styleDateChooser(tglTemu);
        UITheme.styleActionButton(bsimpan, "SAVE", "/resource/ui_act_save.png");
        UITheme.styleActionButton(bbatal, "RESET", "/resource/ui_act_reset.png");
        UITheme.styleActionButton(bprint, "PRINT", null);
        UITheme.styleActionButton(bprint1, "PRINT", null);
        UITheme.styleTable(tblguru, jScrollPane1);
    }

    private void aturHakAkses() {
        bprint.setVisible(false);
        bprint1.setVisible(false);
        String levelAdmin = UserSession.getLevel();
        String sessionGuru = GuruSession.getNip();
        if (sessionGuru != null && !sessionGuru.trim().isEmpty()) {
            bprint.setVisible(true);
        } else if (levelAdmin != null && levelAdmin.equals("1")) {
            bprint1.setVisible(true);
        }
    }

    protected void datatable() {
        String[] baris = {"Kode Guru", "Nama guru", "Kehadiran", "Total Hadir", "Total Tidak Hadir", "Keterangan"};
        tabmode = new DefaultTableModel(null, baris) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 2) {
                    return Boolean.class;
                }
                return super.getColumnClass(columnIndex);
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2 || column == 5;
            }
        };

        tblguru.setModel(tabmode);

        try {
            String guruLogin = GuruSession.getKdGuru();
            boolean filterGuru = (guruLogin != null && !guruLogin.trim().isEmpty());
            if (!filterGuru && UserSession.getLevel() == null) {
                return;
            }

            java.sql.Date tglDipilih = (tglTemu.getDate() != null)
                    ? new java.sql.Date(tglTemu.getDate().getTime())
                    : new java.sql.Date(new Date().getTime());

            String sql = "SELECT u.kd_guru, u.nama, "
                    + "COALESCE(SUM(CASE WHEN a.kehadiran = 1 THEN 1 ELSE 0 END), 0) AS total_hadir, "
                    + "COALESCE(SUM(CASE WHEN a.kehadiran = 0 THEN 1 ELSE 0 END), 0) AS total_tidak_hadir, "
                    + "MAX(CASE WHEN a.tgl = ? THEN a.kehadiran ELSE 0 END) AS hadir_tgl_ini, "
                    + "COALESCE(MAX(CASE WHEN a.tgl = ? THEN a.keterangan ELSE NULL END), '') AS ket_tgl_ini "
                    + "FROM guru u "
                    + "LEFT JOIN tbl_absen_guru a ON u.kd_guru = a.kd_guru "
                    + (filterGuru ? "WHERE u.kd_guru = ? " : "")
                    + "GROUP BY u.kd_guru, u.nama "
                    + "ORDER BY u.kd_guru ASC";
            try (PreparedStatement stat = con.prepareStatement(sql)) {
                stat.setDate(1, tglDipilih);
                stat.setDate(2, tglDipilih);
                if (filterGuru) {
                    stat.setString(3, guruLogin);
                }
                try (ResultSet hasil = stat.executeQuery()) {
                    while (hasil.next()) {
                        Object[] data = {
                            hasil.getString("kd_guru"),
                            hasil.getString("nama"),
                            Boolean.valueOf(hasil.getInt("hadir_tgl_ini") == 1),
                            Integer.valueOf(hasil.getInt("total_hadir")),
                            Integer.valueOf(hasil.getInt("total_tidak_hadir")),
                            hasil.getString("ket_tgl_ini")
                        };
                        tabmode.addRow(data);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal memuat data: " + e.getMessage());
        }
    }

    protected void aktif() {
        tglTemu.requestFocus();
    }

    protected void kosong() {
        tglTemu.setDate(new Date());
    }

    public void cetak() {
        try {
            String path = "./src/report/reportGuruprib.jasper";
            HashMap<String, Object> parameter = new HashMap<String, Object>();
            parameter.put(net.sf.jasperreports.engine.JRParameter.REPORT_LOCALE, new java.util.Locale("id", "ID"));
            java.io.InputStream logoStream = getClass().getResourceAsStream("/resource/logosekolah.jpeg");
            parameter.put("logo", logoStream);
            String guruLogin = GuruSession.getNip();
            parameter.put("paramKdGuru", guruLogin);
            JasperPrint print = JasperFillManager.fillReport(path, parameter, con);
            JasperViewer.viewReport(print, false);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(rootPane, "Dokumen gagal dicetak: " + ex.getMessage());
        }
    }

    public void cetakAbsenGuru() {
        try {
            String path = "./src/report/reportGuru.jasper";

            HashMap<String, Object> parameter = new HashMap<String, Object>();
            parameter.put(net.sf.jasperreports.engine.JRParameter.REPORT_LOCALE, new java.util.Locale("id", "ID"));
            java.io.InputStream logoStream = getClass().getResourceAsStream("/resource/logosekolah.jpeg");
            parameter.put("logo", logoStream);
            JasperPrint print = JasperFillManager.fillReport(path, parameter, con);
            JasperViewer.viewReport(print, false);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(rootPane, "Dokumen gagal dicetak: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlBackground = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblSubForm = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        tglTemu = new com.toedter.calendar.JDateChooser();
        lblHint = new javax.swing.JLabel();
        bsimpan = new javax.swing.JButton();
        bbatal = new javax.swing.JButton();
        pnlTableCard = new javax.swing.JPanel();
        lblTableTitle = new javax.swing.JLabel();
        lblTableSub = new javax.swing.JLabel();
        bprint = new javax.swing.JButton();
        bprint1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblguru = new javax.swing.JTable();

        setBorder(null);

        pnlBackground.setBackground(new java.awt.Color(241, 245, 249));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(226, 232, 240), 1, true));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(15, 23, 42));
        jLabel1.setText("Form Presensi Guru");

        lblSubForm.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblSubForm.setForeground(new java.awt.Color(100, 116, 139));
        lblSubForm.setText("Kelola pencatatan kehadiran harian guru pengajar");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 65, 85));
        jLabel7.setText("Tanggal Presensi");

        lblHint.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblHint.setForeground(new java.awt.Color(100, 116, 139));
        lblHint.setText("<html>Pilih tanggal presensi, centang kolom <b>Kehadiran</b> dan isi <b>Keterangan</b> pada tabel di samping bila diperlukan, lalu klik <b>Simpan</b>.</html>");

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
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tglTemu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblHint, javax.swing.GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE)
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
                .addComponent(jLabel7)
                .addGap(6, 6, 6)
                .addComponent(tglTemu, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblHint, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 220, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bsimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bbatal, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );

        pnlTableCard.setBackground(new java.awt.Color(255, 255, 255));
        pnlTableCard.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(226, 232, 240), 1, true));

        lblTableTitle.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblTableTitle.setForeground(new java.awt.Color(15, 23, 42));
        lblTableTitle.setText("Daftar Kehadiran Guru");

        lblTableSub.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblTableSub.setForeground(new java.awt.Color(100, 116, 139));
        lblTableSub.setText("Rekapitulasi kehadiran dan keterangan harian guru");

        bprint.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bprint.setText("PRINT");
        bprint.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bprint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bprintActionPerformed(evt);
            }
        });

        bprint1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bprint1.setText("PRINT TU");
        bprint1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bprint1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bprint1ActionPerformed(evt);
            }
        });

        tblguru.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        tblguru.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Kode Guru", "Nama Guru", "Kehadiran", "Total Hadir", "Total Tidak Hadir", "Keterangan"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblguru.getTableHeader().setReorderingAllowed(false);
        tblguru.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblguruMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblguru);

        javax.swing.GroupLayout pnlTableCardLayout = new javax.swing.GroupLayout(pnlTableCard);
        pnlTableCard.setLayout(pnlTableCardLayout);
        pnlTableCardLayout.setHorizontalGroup(
            pnlTableCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTableCardLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(pnlTableCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
                    .addGroup(pnlTableCardLayout.createSequentialGroup()
                        .addGroup(pnlTableCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTableTitle)
                            .addComponent(lblTableSub))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
                        .addComponent(bprint)
                        .addGap(8, 8, 8)
                        .addComponent(bprint1)))
                .addGap(22, 22, 22))
        );
        pnlTableCardLayout.setVerticalGroup(
            pnlTableCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTableCardLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlTableCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addGroup(pnlTableCardLayout.createSequentialGroup()
                        .addComponent(lblTableTitle)
                        .addGap(2, 2, 2)
                        .addComponent(lblTableSub))
                    .addComponent(bprint, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bprint1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(pnlTableCard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(24, 24, 24))
        );
        pnlBackgroundLayout.setVerticalGroup(
            pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBackgroundLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlTableCard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void tblguruMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblguruMouseClicked
    }//GEN-LAST:event_tblguruMouseClicked

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

    private void bsimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bsimpanActionPerformed
        if (tblguru.isEditing()) {
            tblguru.getCellEditor().stopCellEditing();
        }

        if (tglTemu.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Pilih tanggal terlebih dahulu!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        DefaultTableModel model = (DefaultTableModel) tblguru.getModel();
        int jumlahBaris = model.getRowCount();

        if (jumlahBaris == 0) {
            JOptionPane.showMessageDialog(this, "Data masih kosong!");
            return;
        }

        int konfirmasi = JOptionPane.showConfirmDialog(
                this,
                "Yakin ingin menyimpan data absensi?",
                "Konfirmasi",
                JOptionPane.YES_NO_OPTION);

        if (konfirmasi != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            java.sql.Date tgl = new java.sql.Date(tglTemu.getDate().getTime());

            String cekSql = "SELECT id_absen FROM tbl_absen_guru WHERE kd_guru = ? AND tgl = ?";
            String updateSql = "UPDATE tbl_absen_guru SET kehadiran = ?, keterangan = ? WHERE kd_guru = ? AND tgl = ?";
            String insertSql = "INSERT INTO tbl_absen_guru (kd_guru, tgl, kehadiran, keterangan) VALUES (?, ?, ?, ?)";

            try (PreparedStatement psCek = con.prepareStatement(cekSql);
                    PreparedStatement psUpdate = con.prepareStatement(updateSql);
                    PreparedStatement psInsert = con.prepareStatement(insertSql)) {

                for (int i = 0; i < jumlahBaris; i++) {
                    String kdGuru = model.getValueAt(i, 0).toString();
                    Object objHadir = model.getValueAt(i, 2);
                    boolean hadir = (objHadir instanceof Boolean) && ((Boolean) objHadir).booleanValue();
                    Object objKet = model.getValueAt(i, 5);
                    String keterangan = (objKet != null) ? objKet.toString().trim() : "";
                    if (keterangan.length() > 50) {
                        keterangan = keterangan.substring(0, 50);
                    }

                    psCek.setString(1, kdGuru);
                    psCek.setDate(2, tgl);
                    boolean sudahAda;
                    try (ResultSet rsCek = psCek.executeQuery()) {
                        sudahAda = rsCek.next();
                    }

                    if (sudahAda) {
                        psUpdate.setInt(1, hadir ? 1 : 0);
                        psUpdate.setString(2, keterangan);
                        psUpdate.setString(3, kdGuru);
                        psUpdate.setDate(4, tgl);
                        psUpdate.executeUpdate();
                    } else {
                        psInsert.setString(1, kdGuru);
                        psInsert.setDate(2, tgl);
                        psInsert.setInt(3, hadir ? 1 : 0);
                        psInsert.setString(4, keterangan);
                        psInsert.executeUpdate();
                    }
                }
            }

            JOptionPane.showMessageDialog(this, "Data absensi berhasil disimpan");
            datatable();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error : " + e.getMessage());
        }
    }//GEN-LAST:event_bsimpanActionPerformed

    private void bprintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bprintActionPerformed
        cetak();
    }//GEN-LAST:event_bprintActionPerformed

    private void bprint1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bprint1ActionPerformed
        cetakAbsenGuru();
    }//GEN-LAST:event_bprint1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bbatal;
    private javax.swing.JButton bprint;
    private javax.swing.JButton bprint1;
    private javax.swing.JButton bsimpan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblHint;
    private javax.swing.JLabel lblSubForm;
    private javax.swing.JLabel lblTableSub;
    private javax.swing.JLabel lblTableTitle;
    private javax.swing.JPanel pnlBackground;
    private javax.swing.JPanel pnlTableCard;
    private javax.swing.JTable tblguru;
    private com.toedter.calendar.JDateChooser tglTemu;
    // End of variables declaration//GEN-END:variables
}
