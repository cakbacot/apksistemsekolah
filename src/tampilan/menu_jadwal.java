/*
 * Manajemen Jadwal Pelajaran - Sistem Manajemen Administrasi SMP 259 Jakarta
 */
package tampilan;

import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.table.DefaultTableModel;
import koneksi.koneksi;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import static tampilan.frm_jadwal.txkg;

/**
 *
 * @author ACER
 */
public class menu_jadwal extends javax.swing.JInternalFrame {
private Connection con = new koneksi().getConnection();
private DefaultTableModel tabmode;
PreparedStatement ps;
ResultSet rs;

    /**
     * Creates new form menu_jadwal
     */
    public menu_jadwal() {
        UITheme.setup();
        initComponents();
        UITheme.prepareInternalFrame(this);
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(kjam, "HH:mm");
        kjam.setEditor(timeEditor);
        kjam.setValue(new java.util.Date());
        applyCustomStyling();
        kosong();
        aktif();
        datatable();
        loadcombo();
    }

    private void applyCustomStyling() {
        UITheme.styleCard(jPanel1);
        UITheme.styleCard(jPanel2);

        UITheme.styleTextField(txidm, "Masukkan ID Mapel (cth: MP01)...");
        UITheme.styleTextField(txmp, "Masukkan nama mata pelajaran...");
        UITheme.styleTextField(txkg, "Pilih kode guru...");
        UITheme.styleTextField(txcari, "Cari berdasarkan ID Mapel atau Mata Pelajaran...");

        UITheme.styleSpinner(kjam);
        UITheme.styleComboBox(ckelas);

        UITheme.styleActionButton(btn_cguru, "SOFT", "/resource/ui_act_search.png");
        UITheme.styleActionButton(bsimpan, "SAVE", "/resource/ui_act_save.png");
        UITheme.styleActionButton(bedit, "EDIT", "/resource/ui_act_edit.png");
        UITheme.styleActionButton(bhapus, "DELETE", "/resource/ui_act_delete.png");
        UITheme.styleActionButton(bbatal, "RESET", "/resource/ui_act_reset.png");
        UITheme.styleActionButton(bcari, "SEARCH", "/resource/ui_act_search.png");
        UITheme.styleActionButton(bcetak, "PRINT", null);

        UITheme.styleTable(tbl_jadwal, jScrollPane1);
    }

    protected void aktif(){
        txidm.requestFocus();
    }

    protected void kosong(){
        txidm.setText("");
        txmp.setText("");
        txkg.setText("");
        kjam.setValue(new java.util.Date());
        ckelas.setSelectedIndex(-1);
    }

    public void cetak() {
    try {
        String path = "./src/report/reportJadwal.jasper";

        HashMap<String, Object> parameter = new HashMap<String, Object>();
        parameter.put(net.sf.jasperreports.engine.JRParameter.REPORT_LOCALE, new java.util.Locale("id", "ID"));
        InputStream logoStream = getClass().getResourceAsStream("/resource/logosekolah.jpeg");
        if (logoStream == null) {
            System.out.println("GAGAL: File gambar tidak ditemukan di resources!");
        }
        parameter.put("logo", logoStream);
        JasperPrint print = JasperFillManager.fillReport(path, parameter, con);
        JasperViewer.viewReport(print, false);

        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(rootPane, "Dokumen gagal dicetak: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    protected void datatable(){
        Object[] Baris = {"Id Mapel","Mata Pelajaran","Nama","Jam","Kelas"};
            tabmode = new DefaultTableModel(null,Baris);
            String cariitem = txcari.getText();
            try{
                String sql ="SELECT tbl_jadwal.id_mapel, tbl_jadwal.nama_mapel, tbl_jadwal.kd_guru, guru.nama, tbl_jadwal.jam, tbl_jadwal.kelas, tbl_kelas.kelas " +
                            "FROM tbl_jadwal " +
                            "JOIN guru ON tbl_jadwal.kd_guru = guru.kd_guru " +
                            "JOIN tbl_kelas ON tbl_jadwal.kelas = tbl_kelas.id_kelas " +
                            "WHERE tbl_jadwal.id_mapel LIKE '%"+cariitem+"%' OR tbl_jadwal.nama_mapel LIKE '%"+cariitem+"%' " +
                            "ORDER BY tbl_jadwal.id_mapel ASC";
                Statement stat = con.createStatement();
                ResultSet hasil = stat.executeQuery(sql);
                while (hasil.next()){
                    tabmode.addRow(new Object[]{
                        hasil.getString(1),
                        hasil.getString(2),
                        hasil.getString(4),
                        hasil.getString(5),
                        hasil.getString(7)
                    });
                }
                tbl_jadwal.setModel(tabmode);
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, "data gagal dipanggil"+e);
            }
    }

    private void loadcombo() {
        try {
            String sql = "SELECT id_kelas FROM tbl_kelas";

            java.sql.Connection conn = (Connection)koneksi.getConnection();
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            java.sql.ResultSet rs = pst.executeQuery();
            ckelas.removeAllItems();
            ckelas.addItem("-- Pilih Kelas --");

            while(rs.next()) {
                ckelas.addItem(rs.getString("id_kelas"));
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblSubForm = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txidm = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txmp = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txkg = new javax.swing.JTextField();
        btn_cguru = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        kjam = new javax.swing.JSpinner();
        jLabel6 = new javax.swing.JLabel();
        ckelas = new javax.swing.JComboBox<>();
        bsimpan = new javax.swing.JButton();
        bedit = new javax.swing.JButton();
        bhapus = new javax.swing.JButton();
        bbatal = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        lblTableTitle = new javax.swing.JLabel();
        lblTableSub = new javax.swing.JLabel();
        bcetak = new javax.swing.JButton();
        txcari = new javax.swing.JTextField();
        bcari = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_jadwal = new javax.swing.JTable();

        setBackground(new java.awt.Color(241, 245, 249));
        setBorder(null);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(226, 232, 240), 1, true));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(15, 23, 42));
        jLabel1.setText("Form Jadwal Pelajaran");

        lblSubForm.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblSubForm.setForeground(new java.awt.Color(100, 116, 139));
        lblSubForm.setText("Kelola mata pelajaran, guru pengampu, dan kelas");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 65, 85));
        jLabel2.setText("ID Mata Pelajaran");

        txidm.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 65, 85));
        jLabel3.setText("Mata Pelajaran");

        txmp.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 65, 85));
        jLabel4.setText("Kode Guru Pengampu");

        txkg.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txkg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txkgActionPerformed(evt);
            }
        });

        btn_cguru.setBackground(new java.awt.Color(239, 246, 255));
        btn_cguru.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_cguru.setForeground(new java.awt.Color(37, 99, 235));
        btn_cguru.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/ui_act_search.png"))); // NOI18N
        btn_cguru.setText("Pilih Guru");
        btn_cguru.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_cguru.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cguruActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 65, 85));
        jLabel5.setText("Jam Pelajaran");

        kjam.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        kjam.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(), null, null, java.util.Calendar.AM_PM));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 65, 85));
        jLabel6.setText("Kelas");

        ckelas.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        bsimpan.setBackground(new java.awt.Color(22, 163, 74));
        bsimpan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bsimpan.setForeground(new java.awt.Color(255, 255, 255));
        bsimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/ui_act_save.png"))); // NOI18N
        bsimpan.setText("Simpan");
        bsimpan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bsimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bsimpanActionPerformed(evt);
            }
        });

        bedit.setBackground(new java.awt.Color(37, 99, 235));
        bedit.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bedit.setForeground(new java.awt.Color(255, 255, 255));
        bedit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/ui_act_edit.png"))); // NOI18N
        bedit.setText("Ubah");
        bedit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bedit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                beditActionPerformed(evt);
            }
        });

        bhapus.setBackground(new java.awt.Color(220, 38, 38));
        bhapus.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bhapus.setForeground(new java.awt.Color(255, 255, 255));
        bhapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/ui_act_delete.png"))); // NOI18N
        bhapus.setText("Hapus");
        bhapus.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bhapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bhapusActionPerformed(evt);
            }
        });

        bbatal.setBackground(new java.awt.Color(255, 255, 255));
        bbatal.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bbatal.setForeground(new java.awt.Color(51, 65, 85));
        bbatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/ui_act_reset.png"))); // NOI18N
        bbatal.setText("Batal");
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
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txidm)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txmp)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txkg)
                        .addGap(8, 8, 8)
                        .addComponent(btn_cguru, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(kjam, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ckelas, 0, 198, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(bsimpan, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
                        .addGap(10, 10, 10)
                        .addComponent(bedit, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(bhapus, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
                        .addGap(10, 10, 10)
                        .addComponent(bbatal, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)))
                .addGap(22, 22, 22))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addGap(4, 4, 4)
                .addComponent(lblSubForm)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addGap(6, 6, 6)
                .addComponent(txidm, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel3)
                .addGap(6, 6, 6)
                .addComponent(txmp, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel4)
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txkg, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_cguru, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(kjam, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ckelas, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bsimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bedit, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bhapus, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bbatal, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(226, 232, 240), 1, true));

        lblTableTitle.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblTableTitle.setForeground(new java.awt.Color(15, 23, 42));
        lblTableTitle.setText("Daftar Jadwal Pelajaran");

        lblTableSub.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblTableSub.setForeground(new java.awt.Color(100, 116, 139));
        lblTableSub.setText("Klik baris pada tabel untuk memuat data ke formulir");

        bcetak.setBackground(new java.awt.Color(15, 23, 42));
        bcetak.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bcetak.setForeground(new java.awt.Color(255, 255, 255));
        bcetak.setText("Cetak Laporan");
        bcetak.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bcetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bcetakActionPerformed(evt);
            }
        });

        txcari.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txcari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txcariKeyPressed(evt);
            }
        });

        bcari.setBackground(new java.awt.Color(37, 99, 235));
        bcari.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bcari.setForeground(new java.awt.Color(255, 255, 255));
        bcari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/ui_act_search.png"))); // NOI18N
        bcari.setText("Cari");
        bcari.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bcari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bcariActionPerformed(evt);
            }
        });

        tbl_jadwal.setModel(new javax.swing.table.DefaultTableModel(
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
        tbl_jadwal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_jadwalMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_jadwal);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTableTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblTableSub, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(16, 16, 16)
                        .addComponent(bcetak, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txcari)
                        .addGap(10, 10, 10)
                        .addComponent(bcari, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE))
                .addGap(22, 22, 22))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblTableTitle)
                        .addGap(3, 3, 3)
                        .addComponent(lblTableSub))
                    .addComponent(bcetak, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txcari, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bcari, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(24, 24, 24))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(24, 24, 24))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_cguruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cguruActionPerformed
        pop_guru popup = new pop_guru(txkg);
        popup.loadData();
        popup.setLocationRelativeTo(this);
        popup.setVisible(true);
    }//GEN-LAST:event_btn_cguruActionPerformed

    private void bsimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bsimpanActionPerformed
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm");
        String jamSimpan = sdf.format(kjam.getValue());
        String kls = ckelas.getSelectedItem().toString();
        String sql = "insert into tbl_jadwal values (?,?,?,?,?)";
        try{
            PreparedStatement stat = con.prepareStatement(sql);
            stat.setString(1, txidm.getText());
            stat.setString(2, txmp.getText());
            stat.setString(3, txkg.getText());
            stat.setString(4, jamSimpan);
            stat.setString(5, kls);

            stat.executeUpdate();
            JOptionPane.showMessageDialog(null, "data berhasil disimpan");
            kosong();
            txidm.requestFocus();
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null, "error" + e.getMessage());
            e.printStackTrace();
        }
        datatable();
    }//GEN-LAST:event_bsimpanActionPerformed

    private void beditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_beditActionPerformed
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm");
        String jamSimpan = sdf.format(kjam.getValue());
        String kls = ckelas.getSelectedItem().toString();
        String sql = "update tbl_jadwal set nama_mapel=?, kd_guru=?, jam=?, kelas=? where id_mapel='"+txidm.getText()+"'";
        try {
            PreparedStatement stat = con.prepareStatement(sql);
            stat.setString(1, txmp.getText());
            stat.setString(2, txkg.getText());
            stat.setString(3, jamSimpan);
            stat.setString(4, kls);

            stat.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil diubah");

            kosong();
            txidm.requestFocus();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            e.printStackTrace();
        }
        datatable();
    }//GEN-LAST:event_beditActionPerformed

    private void bhapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bhapusActionPerformed
        int ok = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin ingin menghapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (ok == 0) {
            String sql = "delete from tbl_jadwal where id_mapel=?";
            try {
                PreparedStatement stat = con.prepareStatement(sql);
                stat.setString(1, txidm.getText());

                stat.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data berhasil dihapus");

                kosong();
                txidm.requestFocus();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            }
            datatable();
        }
    }//GEN-LAST:event_bhapusActionPerformed

    private void bbatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bbatalActionPerformed
        kosong();
        datatable();
    }//GEN-LAST:event_bbatalActionPerformed

    private void txcariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txcariKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            datatable();
        }
    }//GEN-LAST:event_txcariKeyPressed

    private void bcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bcariActionPerformed
        datatable();
    }//GEN-LAST:event_bcariActionPerformed

    private void tbl_jadwalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_jadwalMouseClicked
        int baris = tbl_jadwal.getSelectedRow();
        txidm.setText(tbl_jadwal.getValueAt(baris, 0).toString());
        txmp.setText(tbl_jadwal.getValueAt(baris, 1).toString());
       // txkg.setText(tbl_jadwal.getValueAt(baris, 2).toString());
        try {
            String jamTabel = tbl_jadwal.getValueAt(baris, 3).toString();
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm");
            kjam.setValue(sdf.parse(jamTabel));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        ckelas.setSelectedItem(tbl_jadwal.getValueAt(baris, 4).toString());
    }//GEN-LAST:event_tbl_jadwalMouseClicked

    private void txkgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txkgActionPerformed
    }//GEN-LAST:event_txkgActionPerformed

    private void bcetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bcetakActionPerformed
        cetak();
    }//GEN-LAST:event_bcetakActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bbatal;
    private javax.swing.JButton bcari;
    private javax.swing.JButton bcetak;
    private javax.swing.JButton bedit;
    private javax.swing.JButton bhapus;
    private javax.swing.JButton bsimpan;
    private javax.swing.JButton btn_cguru;
    private javax.swing.JComboBox<String> ckelas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner kjam;
    private javax.swing.JLabel lblSubForm;
    private javax.swing.JLabel lblTableSub;
    private javax.swing.JLabel lblTableTitle;
    private javax.swing.JTable tbl_jadwal;
    private javax.swing.JTextField txcari;
    private javax.swing.JTextField txidm;
    public static javax.swing.JTextField txkg;
    private javax.swing.JTextField txmp;
    // End of variables declaration//GEN-END:variables
}
