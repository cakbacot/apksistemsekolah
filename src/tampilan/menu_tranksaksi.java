/*
 * Form Transaksi Pembayaran - Sistem Manajemen Administrasi SMP 259 Jakarta
 */
package tampilan;

import java.awt.event.KeyEvent;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
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

    private TableRowSorter<DefaultTableModel> rowSorter;

    /**
     * Creates new form menu_tranksaksi
     */
    public menu_tranksaksi() {
        UITheme.setup();
        initComponents();
        UITheme.prepareInternalFrame(this);
        applyModernTheme();

        kosong();
        generateNoTransaksi();
        trank.setEnabled(false);
        datatable();
    }

    private void applyModernTheme() {
        UITheme.styleCard(jPanel1);
        UITheme.styleUserProfileBadge(jPanel2);
        UITheme.styleCard(jPanel3);

        UITheme.styleTextField(trank, "Nomor transaksi otomatis");
        UITheme.styleTextField(txtnisn, "Pilih peserta didik...");
        UITheme.styleTextField(txtyear, "Contoh: 2026");
        UITheme.styleTextField(txtCariTrx, "Cari transaksi / NISN / nama...");
        UITheme.styleComboBox(cbmonth);

        UITheme.styleActionButton(bcari, "SOFT", "/resource/ui_act_search.png");
        UITheme.styleActionButton(simpan, "SAVE", "/resource/ui_act_save.png");
        UITheme.styleActionButton(bbatal, "RESET", "/resource/ui_act_reset.png");
        UITheme.styleActionButton(btnCariTrx, "SEARCH", "/resource/ui_act_search.png");
        UITheme.styleActionButton(print, "PRINT", null);

        UITheme.styleTable(tblsiswa, jScrollPane1);
    }

    private void generateNoTransaksi() {
        String time = String.valueOf(System.currentTimeMillis());
        String pendek = time.substring(time.length() - 4);
        trank.setText("TRX-" + pendek);
    }

    private void filterTabelTransaksi() {
        if (rowSorter == null) {
            return;
        }
        String keyword = txtCariTrx.getText().trim();
        if (keyword.isEmpty()) {
            rowSorter.setRowFilter(null);
        } else {
            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + Pattern.quote(keyword)));
        }
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
            String sql = "SELECT t.*, s.nama FROM tbl_transaksi t "
                    + "LEFT JOIN tbl_siswa s ON t.nisn = s.nisn "
                    + "ORDER BY t.no_transaksi DESC";

            java.sql.Statement st = koneksi.koneksi.getConnection().createStatement();
            java.sql.ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("no_transaksi"),
                    rs.getString("nisn"),
                    rs.getString("nama"),
                    rs.getString("bulan_bayar"),
                    rs.getString("tahun_bayar"),
                    rs.getString("jumlah_bayar")
                });
            }
            tblsiswa.setModel(model);
            rowSorter = new TableRowSorter<DefaultTableModel>(model);
            tblsiswa.setRowSorter(rowSorter);
            filterTabelTransaksi();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat tabel: " + e.getMessage());
        }
    }

    public void setSiswa(String nisn, String namaSiswa, String kelasSiswa) {
        txtnisn.setText(nisn);
        nama.setText(namaSiswa);
        kelas.setText(kelasSiswa);

        String k = (kelasSiswa == null) ? "" : kelasSiswa.toUpperCase().trim();

        int nominalBayar = 0;

        if (k.contains("XII")) {
            nominalBayar = 250000;
        } else if (k.contains("XI")) {
            nominalBayar = 200000;
        } else if (k.contains("X")) {
            nominalBayar = 150000;
        } else {
            nominalBayar = 0;
        }

        nominal.setText(String.valueOf(nominalBayar));
    }

    public void cetakData(String text) {
        try {
            String path = "/report/nota.jasper";
            java.util.Map<String, Object> map = new java.util.HashMap<String, Object>();
            map.put("no_transaksi", trank.getText());
            net.sf.jasperreports.engine.JasperPrint printReport = JasperFillManager.fillReport(
                    getClass().getResourceAsStream(path),
                    map,
                    koneksi.koneksi.getConnection()
            );

            JasperViewer.viewReport(printReport, false);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal cetak: " + e.getMessage());
        }
    }

    public void cetak() {
        try {
            String path = "/report/reportkeuangan.jasper";
            java.util.HashMap<String, Object> parameter = new java.util.HashMap<String, Object>();
            net.sf.jasperreports.engine.JasperPrint printReport = JasperFillManager.fillReport(
                    getClass().getResourceAsStream(path),
                    parameter,
                    koneksi.koneksi.getConnection()
            );

            JasperViewer.viewReport(printReport, false);
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

        pnlMain = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        lblTitleLeft = new javax.swing.JLabel();
        lblSubLeft = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        trank = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtnisn = new javax.swing.JTextField();
        bcari = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        nama = new javax.swing.JLabel();
        txt2 = new javax.swing.JLabel();
        kelas = new javax.swing.JLabel();
        txt3 = new javax.swing.JLabel();
        cbmonth = new javax.swing.JComboBox<>();
        txt4 = new javax.swing.JLabel();
        txtyear = new javax.swing.JTextField();
        txt5 = new javax.swing.JLabel();
        nominal = new javax.swing.JLabel();
        simpan = new javax.swing.JButton();
        bbatal = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        lblTitleRight = new javax.swing.JLabel();
        lblSubRight = new javax.swing.JLabel();
        txtCariTrx = new javax.swing.JTextField();
        btnCariTrx = new javax.swing.JButton();
        print = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblsiswa = new javax.swing.JTable();

        setBackground(new java.awt.Color(241, 245, 249));
        setBorder(null);

        pnlMain.setBackground(new java.awt.Color(241, 245, 249));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(226, 232, 240), 1, true));

        lblTitleLeft.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblTitleLeft.setForeground(new java.awt.Color(15, 23, 42));
        lblTitleLeft.setText("Form Transaksi Pembayaran");

        lblSubLeft.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblSubLeft.setForeground(new java.awt.Color(100, 116, 139));
        lblSubLeft.setText("Input pembayaran administrasi peserta didik");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 65, 85));
        jLabel3.setText("No. Transaksi");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 65, 85));
        jLabel1.setText("NISN Peserta Didik");

        bcari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/ui_act_search.png"))); // NOI18N
        bcari.setText("Pilih Siswa");
        bcari.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bcari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bcariActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(248, 250, 252));
        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(226, 232, 240), 1, true));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(100, 116, 139));
        jLabel2.setText("Nama Siswa");

        nama.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        nama.setForeground(new java.awt.Color(15, 23, 42));
        nama.setText("silahkan pilih siswa");

        txt2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txt2.setForeground(new java.awt.Color(100, 116, 139));
        txt2.setText("Kelas");

        kelas.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        kelas.setForeground(new java.awt.Color(15, 23, 42));
        kelas.setText("silahkan pilih siswa");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nama, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kelas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(14, 14, 14))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(nama))
                .addGap(8, 8, 8)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt2)
                    .addComponent(kelas))
                .addGap(12, 12, 12))
        );

        txt3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txt3.setForeground(new java.awt.Color(51, 65, 85));
        txt3.setText("Bulan Tagihan");

        cbmonth.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember" }));

        txt4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txt4.setForeground(new java.awt.Color(51, 65, 85));
        txt4.setText("Tahun Tagihan");

        txt5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txt5.setForeground(new java.awt.Color(51, 65, 85));
        txt5.setText("Total Tagihan (Rp)");

        nominal.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        nominal.setForeground(new java.awt.Color(37, 99, 235));
        nominal.setText("0");

        simpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/ui_act_save.png"))); // NOI18N
        simpan.setText("Simpan & Cetak Nota");
        simpan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simpanActionPerformed(evt);
            }
        });

        bbatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/ui_act_reset.png"))); // NOI18N
        bbatal.setText("Reset");
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
                    .addComponent(lblTitleLeft)
                    .addComponent(lblSubLeft)
                    .addComponent(jLabel3)
                    .addComponent(trank)
                    .addComponent(jLabel1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtnisn)
                        .addGap(8, 8, 8)
                        .addComponent(bcari, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt3)
                            .addComponent(cbmonth, 0, 172, Short.MAX_VALUE))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt4)
                            .addComponent(txtyear, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txt5)
                        .addGap(12, 12, 12)
                        .addComponent(nominal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(simpan, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                        .addGap(10, 10, 10)
                        .addComponent(bbatal, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(22, 22, 22))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(lblTitleLeft)
                .addGap(4, 4, 4)
                .addComponent(lblSubLeft)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addGap(6, 6, 6)
                .addComponent(trank, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel1)
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtnisn, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bcari, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt3)
                    .addComponent(txt4))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbmonth, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtyear, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt5)
                    .addComponent(nominal))
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(simpan, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bbatal, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(226, 232, 240), 1, true));

        lblTitleRight.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblTitleRight.setForeground(new java.awt.Color(15, 23, 42));
        lblTitleRight.setText("Riwayat Transaksi Pembayaran");

        lblSubRight.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblSubRight.setForeground(new java.awt.Color(100, 116, 139));
        lblSubRight.setText("Daftar riwayat pembayaran peserta didik");

        txtCariTrx.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCariTrxKeyPressed(evt);
            }
        });

        btnCariTrx.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/ui_act_search.png"))); // NOI18N
        btnCariTrx.setText("Cari");
        btnCariTrx.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCariTrx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariTrxActionPerformed(evt);
            }
        });

        print.setText("Cetak Laporan");
        print.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printActionPerformed(evt);
            }
        });

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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTitleRight)
                            .addComponent(lblSubRight))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                        .addComponent(print, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txtCariTrx, javax.swing.GroupLayout.DEFAULT_SIZE, 445, Short.MAX_VALUE)
                        .addGap(10, 10, 10)
                        .addComponent(btnCariTrx, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 545, Short.MAX_VALUE))
                .addGap(22, 22, 22))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lblTitleRight)
                        .addGap(2, 2, 2)
                        .addComponent(lblSubRight))
                    .addComponent(print, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCariTrx, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCariTrx, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 390, Short.MAX_VALUE)
                .addGap(22, 22, 22))
        );

        javax.swing.GroupLayout pnlMainLayout = new javax.swing.GroupLayout(pnlMain);
        pnlMain.setLayout(pnlMainLayout);
        pnlMainLayout.setHorizontalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(24, 24, 24))
        );
        pnlMainLayout.setVerticalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(24, 24, 24))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bcariActionPerformed
        pop_up_siswa popup = new pop_up_siswa(this);
        popup.setVisible(true);
    }//GEN-LAST:event_bcariActionPerformed

    private void simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpanActionPerformed
        if (txtnisn.getText().trim().isEmpty() || trank.getText().trim().isEmpty()) {
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
            cetakData(trank.getText());
            kosong();
            generateNoTransaksi();
            datatable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal Simpan: " + e.getMessage());
        }
    }//GEN-LAST:event_simpanActionPerformed

    private void bbatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bbatalActionPerformed
        kosong();
        generateNoTransaksi();
    }//GEN-LAST:event_bbatalActionPerformed

    private void printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printActionPerformed
        cetak();
    }//GEN-LAST:event_printActionPerformed

    private void btnCariTrxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariTrxActionPerformed
        filterTabelTransaksi();
    }//GEN-LAST:event_btnCariTrxActionPerformed

    private void txtCariTrxKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariTrxKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            filterTabelTransaksi();
        }
    }//GEN-LAST:event_txtCariTrxKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bbatal;
    private javax.swing.JButton bcari;
    private javax.swing.JButton btnCariTrx;
    private javax.swing.JComboBox<String> cbmonth;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel kelas;
    private javax.swing.JLabel lblSubLeft;
    private javax.swing.JLabel lblSubRight;
    private javax.swing.JLabel lblTitleLeft;
    private javax.swing.JLabel lblTitleRight;
    private javax.swing.JLabel nama;
    private javax.swing.JLabel nominal;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JButton print;
    private javax.swing.JButton simpan;
    private javax.swing.JTable tblsiswa;
    private javax.swing.JTextField trank;
    private javax.swing.JLabel txt2;
    private javax.swing.JLabel txt3;
    private javax.swing.JLabel txt4;
    private javax.swing.JLabel txt5;
    private javax.swing.JTextField txtCariTrx;
    private javax.swing.JTextField txtnisn;
    private javax.swing.JTextField txtyear;
    // End of variables declaration//GEN-END:variables
}
