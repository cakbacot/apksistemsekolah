package tampilan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import koneksi.koneksi;

/**
 * Form Penilaian Akademik Siswa (Modern Navy & Royal Blue).
 */
public class menu_nilai extends javax.swing.JInternalFrame {

    private Connection con = new koneksi().getConnection();
    private DefaultTableModel tabmode;
    PreparedStatement ps;
    ResultSet rs;
    private boolean sedangMemuatCombo = false;

    public menu_nilai() {
        UITheme.setup();
        initComponents();
        UITheme.prepareInternalFrame(this);
        applyModernTheme();
        txnilai.setText("");
        txcari.setText("");
        loadjenisnilai();
        loadcombo();
        loadcomboMapel();
        resetFormInput();
        datatable();
    }

    private void applyModernTheme() {
        pnlRoot.setBackground(UITheme.BG_MAIN);
        UITheme.styleCard(jPanel2);
        UITheme.styleCard(jPanel3);
        pnlInfoSiswa.setOpaque(false);
        pnlInfoSiswa.setBorder(null);
        pnlInfoSiswa.putClientProperty("FlatLaf.style",
                "arc: 12; background: #F8FAFC; border: 1,1,1,1,#E2E8F0,1,12");

        UITheme.styleComboBox(cbjnsnilai);
        UITheme.styleComboBox(cbkelas);
        UITheme.styleComboBox(cbmapel);
        UITheme.styleTextField(txnilai, "Masukkan skor (0 - 100)...");
        UITheme.styleTextField(txcari, "Cari NISN atau nama siswa...");

        UITheme.styleActionButton(save, "SAVE", "/resource/ui_act_save.png");
        UITheme.styleActionButton(change, "EDIT", "/resource/ui_act_edit.png");
        UITheme.styleActionButton(breset, "RESET", "/resource/ui_act_reset.png");

        UITheme.styleTable(tblsiswa, jScrollPane1);
    }

    private void resetFormInput() {
        lblnisn.setText("silahkan pilih siswa pada tabel");
        lblnama.setText("silahkan pilih siswa pada tabel");
        if (cbmapel.getSelectedIndex() > 0 && cbmapel.getSelectedItem() != null) {
            lblmapel.setText(cbmapel.getSelectedItem().toString());
        } else {
            lblmapel.setText("silahkan pilih pada tabel");
        }
        String namaGuru = GuruSession.getNama();
        lblguru.setText((namaGuru != null && !namaGuru.isEmpty()) ? namaGuru : "silahkan pilih pada tabel");
        if (cbjnsnilai.getItemCount() > 0) {
            cbjnsnilai.setSelectedIndex(0);
        }
        txnilai.setText("");
    }

    public void datatable() {
        DefaultTableModel tabmode = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabmode.addColumn("NISN");
        tabmode.addColumn("Nama Siswa");
        tabmode.addColumn("Tugas");
        tabmode.addColumn("UTS");
        tabmode.addColumn("UAS");
        tblsiswa.setModel(tabmode);

        if (tblsiswa.getColumnModel().getColumnCount() >= 5) {
            tblsiswa.getColumnModel().getColumn(0).setPreferredWidth(110);
            tblsiswa.getColumnModel().getColumn(1).setPreferredWidth(230);
            tblsiswa.getColumnModel().getColumn(2).setPreferredWidth(75);
            tblsiswa.getColumnModel().getColumn(3).setPreferredWidth(75);
            tblsiswa.getColumnModel().getColumn(4).setPreferredWidth(75);
        }

        if (cbkelas.getSelectedIndex() <= 0 || cbkelas.getSelectedItem() == null) {
            return;
        }

        String kelasDipilih = cbkelas.getSelectedItem().toString();
        String idMapel = "";
        if (cbmapel.getSelectedIndex() > 0 && cbmapel.getSelectedItem() != null) {
            idMapel = cariIdMapel(cbmapel.getSelectedItem().toString(), kelasDipilih);
        }
        String keyword = "%" + txcari.getText().trim() + "%";

        try {
            String sql = "SELECT s.nisn, s.nama, "
                    + "MAX(CASE WHEN n.jenis_nilai = 'Tugas' THEN n.skor ELSE 0 END) AS Tugas, "
                    + "MAX(CASE WHEN n.jenis_nilai = 'UTS' THEN n.skor ELSE 0 END) AS UTS, "
                    + "MAX(CASE WHEN n.jenis_nilai = 'UAS' THEN n.skor ELSE 0 END) AS UAS "
                    + "FROM tbl_siswa s "
                    + "JOIN tbl_kelas k ON s.kelas = k.id_kelas "
                    + "LEFT JOIN tbl_nilai n ON s.nisn = n.nisn AND n.id_mapel = ? "
                    + "WHERE k.kelas = ? AND (s.nisn LIKE ? OR s.nama LIKE ?) "
                    + "GROUP BY s.nisn, s.nama "
                    + "ORDER BY s.nama ASC";

            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setString(1, idMapel);
                pst.setString(2, kelasDipilih);
                pst.setString(3, keyword);
                pst.setString(4, keyword);

                try (ResultSet rs = pst.executeQuery()) {
                    while (rs.next()) {
                        tabmode.addRow(new Object[]{
                            rs.getString("nisn"),
                            rs.getString("nama"),
                            rs.getInt("Tugas"),
                            rs.getInt("UTS"),
                            rs.getInt("UAS")
                        });
                    }
                }
            }

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

    private String cariIdMapel(String namaMapel, String namaKelas) {
        String id = "";
        try {
            String sql = "SELECT j.id_mapel FROM tbl_jadwal j "
                    + "JOIN tbl_kelas k ON j.kelas = k.id_kelas "
                    + "WHERE j.nama_mapel = ? AND j.kd_guru = ? AND k.kelas = ?";
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setString(1, namaMapel);
                pst.setString(2, GuruSession.getKdGuru());
                pst.setString(3, namaKelas);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        id = rs.getString("id_mapel");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error cari ID Mapel: " + e.getMessage());
        }
        return id;
    }

    private void loadcombo() {
        sedangMemuatCombo = true;
        try {
            cbkelas.removeAllItems();
            cbkelas.addItem("-- Pilih Kelas --");

            String idGuruLogin = GuruSession.getKdGuru();
            if (idGuruLogin == null || idGuruLogin.trim().isEmpty()) {
                return;
            }

            String sql = "SELECT DISTINCT k.kelas FROM tbl_jadwal j "
                    + "JOIN tbl_kelas k ON j.kelas = k.id_kelas "
                    + "WHERE j.kd_guru = ? ORDER BY k.kelas ASC";
            boolean punyaKelas = false;
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setString(1, idGuruLogin);
                try (ResultSet rs = pst.executeQuery()) {
                    while (rs.next()) {
                        cbkelas.addItem(rs.getString("kelas"));
                        punyaKelas = true;
                    }
                }
            }

            if (!punyaKelas) {
                JOptionPane.showMessageDialog(this, "Anda belum memiliki jadwal mengajar di kelas manapun.", "Informasi", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal Load Kelas: " + e.getMessage());
        } finally {
            sedangMemuatCombo = false;
        }
    }

    private void loadcomboMapel() {
        sedangMemuatCombo = true;
        try {
            cbmapel.removeAllItems();
            cbmapel.addItem("-- Pilih Mapel --");

            String idGuru = GuruSession.getKdGuru();
            if (idGuru == null || idGuru.trim().isEmpty() || cbkelas.getSelectedIndex() <= 0 || cbkelas.getSelectedItem() == null) {
                return;
            }

            String kelasDipilih = cbkelas.getSelectedItem().toString();
            String sql = "SELECT j.nama_mapel FROM tbl_jadwal j "
                    + "JOIN tbl_kelas k ON j.kelas = k.id_kelas "
                    + "WHERE j.kd_guru = ? AND k.kelas = ? "
                    + "ORDER BY j.nama_mapel ASC";

            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setString(1, idGuru);
                pst.setString(2, kelasDipilih);
                try (ResultSet rs = pst.executeQuery()) {
                    while (rs.next()) {
                        cbmapel.addItem(rs.getString("nama_mapel"));
                    }
                }
            }

            if (cbmapel.getItemCount() == 2) {
                cbmapel.setSelectedIndex(1);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat mapel: " + e.getMessage());
        } finally {
            sedangMemuatCombo = false;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlRoot = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblFormSub = new javax.swing.JLabel();
        pnlInfoSiswa = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lblnisn = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblnama = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblmapel = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblguru = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cbjnsnilai = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        txnilai = new javax.swing.JTextField();
        save = new javax.swing.JButton();
        change = new javax.swing.JButton();
        breset = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        lblTableTitle = new javax.swing.JLabel();
        lblTableSub = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        cbkelas = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        cbmapel = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        txcari = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblsiswa = new javax.swing.JTable();

        setBackground(new java.awt.Color(241, 245, 249));
        setBorder(null);

        pnlRoot.setBackground(new java.awt.Color(241, 245, 249));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(226, 232, 240), 1, true));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(15, 23, 42));
        jLabel1.setText("Form Input Nilai Akademik");

        lblFormSub.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblFormSub.setForeground(new java.awt.Color(100, 116, 139));
        lblFormSub.setText("Pilih siswa pada tabel lalu masukkan skor penilaian");

        pnlInfoSiswa.setBackground(new java.awt.Color(248, 250, 252));
        pnlInfoSiswa.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(226, 232, 240), 1, true));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(100, 116, 139));
        jLabel2.setText("NISN Siswa");

        lblnisn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblnisn.setForeground(new java.awt.Color(15, 23, 42));
        lblnisn.setText("silahkan pilih siswa pada tabel");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(100, 116, 139));
        jLabel3.setText("Nama Siswa");

        lblnama.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblnama.setForeground(new java.awt.Color(15, 23, 42));
        lblnama.setText("silahkan pilih siswa pada tabel");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(100, 116, 139));
        jLabel4.setText("Mata Pelajaran");

        lblmapel.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblmapel.setForeground(new java.awt.Color(51, 65, 85));
        lblmapel.setText("silahkan pilih pada tabel");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(100, 116, 139));
        jLabel5.setText("Guru Pengampu");

        lblguru.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblguru.setForeground(new java.awt.Color(51, 65, 85));
        lblguru.setText("silahkan pilih pada tabel");

        javax.swing.GroupLayout pnlInfoSiswaLayout = new javax.swing.GroupLayout(pnlInfoSiswa);
        pnlInfoSiswa.setLayout(pnlInfoSiswaLayout);
        pnlInfoSiswaLayout.setHorizontalGroup(
            pnlInfoSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInfoSiswaLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(pnlInfoSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10)
                .addGroup(pnlInfoSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblnisn, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                    .addComponent(lblnama, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblmapel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblguru, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(14, 14, 14))
        );
        pnlInfoSiswaLayout.setVerticalGroup(
            pnlInfoSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInfoSiswaLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(pnlInfoSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lblnisn))
                .addGap(10, 10, 10)
                .addGroup(pnlInfoSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lblnama))
                .addGap(10, 10, 10)
                .addGroup(pnlInfoSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(lblmapel))
                .addGap(10, 10, 10)
                .addGroup(pnlInfoSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(lblguru))
                .addGap(14, 14, 14))
        );

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 65, 85));
        jLabel6.setText("Jenis Penilaian");

        cbjnsnilai.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        cbjnsnilai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 65, 85));
        jLabel7.setText("Skor Nilai (0 - 100)");

        txnilai.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        save.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        save.setText("Simpan");
        save.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveActionPerformed(evt);
            }
        });

        change.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        change.setText("Ubah");
        change.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        change.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeActionPerformed(evt);
            }
        });

        breset.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        breset.setText("Reset Pilihan");
        breset.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        breset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bresetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblFormSub, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlInfoSiswa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbjnsnilai, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txnilai)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(save, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                        .addGap(12, 12, 12)
                        .addComponent(change, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE))
                    .addComponent(breset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(24, 24, 24))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addGap(4, 4, 4)
                .addComponent(lblFormSub)
                .addGap(18, 18, 18)
                .addComponent(pnlInfoSiswa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addGap(6, 6, 6)
                .addComponent(cbjnsnilai, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(jLabel7)
                .addGap(6, 6, 6)
                .addComponent(txnilai, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(save, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(change, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(breset, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(226, 232, 240), 1, true));

        lblTableTitle.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblTableTitle.setForeground(new java.awt.Color(15, 23, 42));
        lblTableTitle.setText("Daftar Nilai Siswa");

        lblTableSub.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblTableSub.setForeground(new java.awt.Color(100, 116, 139));
        lblTableSub.setText("Filter kelas dan mata pelajaran untuk menampilkan rekap nilai siswa");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(51, 65, 85));
        jLabel9.setText("Pilih Kelas");

        cbkelas.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        cbkelas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbkelas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbkelasActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 65, 85));
        jLabel10.setText("Pilih Mata Pelajaran");

        cbmapel.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        cbmapel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbmapel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbmapelActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 65, 85));
        jLabel8.setText("Cari Siswa");

        txcari.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txcari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txcariKeyReleased(evt);
            }
        });

        tblsiswa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "NISN", "Nama Siswa", "Tugas", "UTS", "UAS"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTableTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTableSub, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbkelas, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbmapel, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txcari, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)))
                    .addComponent(jScrollPane1))
                .addGap(24, 24, 24))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(lblTableTitle)
                .addGap(4, 4, 4)
                .addComponent(lblTableSub)
                .addGap(16, 16, 16)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(jLabel8))
                .addGap(6, 6, 6)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbkelas, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbmapel, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txcari, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
                .addGap(24, 24, 24))
        );

        javax.swing.GroupLayout pnlRootLayout = new javax.swing.GroupLayout(pnlRoot);
        pnlRoot.setLayout(pnlRootLayout);
        pnlRootLayout.setHorizontalGroup(
            pnlRootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRootLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(24, 24, 24))
        );
        pnlRootLayout.setVerticalGroup(
            pnlRootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRootLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(pnlRootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
            .addComponent(pnlRoot, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblsiswaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblsiswaMouseClicked
        if (cbmapel.getSelectedIndex() <= 0 || cbmapel.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Silakan pilih Mata Pelajaran terlebih dahulu!");
            return;
        }

        int baris = tblsiswa.getSelectedRow();

        if (baris != -1) {
            String nisn = tblsiswa.getValueAt(baris, 0).toString();
            String nama = tblsiswa.getValueAt(baris, 1).toString();
            String mapel = cbmapel.getSelectedItem().toString();

            lblnisn.setText(nisn);
            lblnama.setText(nama);
            lblmapel.setText(mapel);
            lblguru.setText(GuruSession.getNama());
            cbjnsnilai.setSelectedIndex(0);
            txnilai.setText("");
        }
    }//GEN-LAST:event_tblsiswaMouseClicked

    private void cbkelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbkelasActionPerformed
        if (sedangMemuatCombo) {
            return;
        }
        loadcomboMapel();
        resetFormInput();
        datatable();
    }//GEN-LAST:event_cbkelasActionPerformed

    private void saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveActionPerformed
        if (cbkelas.getSelectedIndex() <= 0 || cbmapel.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(this, "Harap pilih Kelas dan Mata Pelajaran terlebih dahulu!");
            return;
        }

        if (lblnisn.getText().equals("silahkan pilih siswa pada tabel")
                || cbjnsnilai.getSelectedIndex() <= 0
                || txnilai.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Harap pilih siswa pada tabel, jenis nilai, dan isi skor!");
            return;
        }

        int skor;
        try {
            skor = Integer.parseInt(txnilai.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Nilai harus berupa angka bulat!");
            return;
        }

        if (skor < 0 || skor > 100) {
            JOptionPane.showMessageDialog(this, "Nilai harus berada pada rentang 0 sampai 100!");
            return;
        }

        String nisn = lblnisn.getText();
        String jenisNilai = cbjnsnilai.getSelectedItem().toString();
        String kelasDipilih = cbkelas.getSelectedItem().toString();
        String idMapel = cariIdMapel(cbmapel.getSelectedItem().toString(), kelasDipilih);
        String idGuru = GuruSession.getKdGuru();

        if (idMapel.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mata pelajaran tidak ditemukan pada jadwal kelas ini!");
            return;
        }

        try {
            String cekSql = "SELECT id_nilai FROM tbl_nilai WHERE nisn = ? AND id_mapel = ? AND jenis_nilai = ?";
            boolean sudahAda;
            try (PreparedStatement psCek = con.prepareStatement(cekSql)) {
                psCek.setString(1, nisn);
                psCek.setString(2, idMapel);
                psCek.setString(3, jenisNilai);
                try (ResultSet rs = psCek.executeQuery()) {
                    sudahAda = rs.next();
                }
            }

            if (sudahAda) {
                String updateSql = "UPDATE tbl_nilai SET skor = ?, kd_guru = ? WHERE nisn = ? AND id_mapel = ? AND jenis_nilai = ?";
                try (PreparedStatement psUpdate = con.prepareStatement(updateSql)) {
                    psUpdate.setInt(1, skor);
                    psUpdate.setString(2, idGuru);
                    psUpdate.setString(3, nisn);
                    psUpdate.setString(4, idMapel);
                    psUpdate.setString(5, jenisNilai);
                    psUpdate.executeUpdate();
                }
                JOptionPane.showMessageDialog(this, "Data berhasil diupdate!");
            } else {
                String insertSql = "INSERT INTO tbl_nilai (nisn, id_mapel, jenis_nilai, skor, kd_guru) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement psInsert = con.prepareStatement(insertSql)) {
                    psInsert.setString(1, nisn);
                    psInsert.setString(2, idMapel);
                    psInsert.setString(3, jenisNilai);
                    psInsert.setInt(4, skor);
                    psInsert.setString(5, idGuru);
                    psInsert.executeUpdate();
                }
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
        if (cbkelas.getSelectedIndex() <= 0 || cbmapel.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(this, "Harap pilih Kelas dan Mata Pelajaran terlebih dahulu!");
            return;
        }

        if (lblnisn.getText().equals("silahkan pilih siswa pada tabel")
                || cbjnsnilai.getSelectedIndex() <= 0
                || txnilai.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Harap pilih siswa dan isi nilai yang ingin diubah!");
            return;
        }

        String nisn = lblnisn.getText();
        String jenisNilai = cbjnsnilai.getSelectedItem().toString();
        String kelasDipilih = cbkelas.getSelectedItem().toString();
        String idMapel = cariIdMapel(cbmapel.getSelectedItem().toString(), kelasDipilih);

        if (idMapel.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mata pelajaran tidak ditemukan pada jadwal kelas ini!");
            return;
        }

        try {
            int skor = Integer.parseInt(txnilai.getText().trim());
            if (skor < 0 || skor > 100) {
                JOptionPane.showMessageDialog(this, "Nilai harus berada pada rentang 0 sampai 100!");
                return;
            }

            String sql = "UPDATE tbl_nilai SET skor = ?, kd_guru = ? WHERE nisn = ? AND id_mapel = ? AND jenis_nilai = ?";
            int rowsAffected;
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, skor);
                ps.setString(2, GuruSession.getKdGuru());
                ps.setString(3, nisn);
                ps.setString(4, idMapel);
                ps.setString(5, jenisNilai);
                rowsAffected = ps.executeUpdate();
            }

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Data berhasil diubah!");
                datatable();
                txnilai.setText("");
                cbjnsnilai.setSelectedIndex(0);
            } else {
                JOptionPane.showMessageDialog(this, "Gagal mengubah data. Pastikan data tersebut sudah ada/tersimpan sebelumnya.");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Nilai harus berupa angka bulat!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            e.printStackTrace();
        }
    }//GEN-LAST:event_changeActionPerformed

    private void bresetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bresetActionPerformed
        resetFormInput();
        tblsiswa.clearSelection();
    }//GEN-LAST:event_bresetActionPerformed

    private void cbmapelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbmapelActionPerformed
        if (sedangMemuatCombo) {
            return;
        }
        resetFormInput();
        datatable();
    }//GEN-LAST:event_cbmapelActionPerformed

    private void txcariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txcariKeyReleased
        datatable();
    }//GEN-LAST:event_txcariKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton breset;
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
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblFormSub;
    private javax.swing.JLabel lblTableSub;
    private javax.swing.JLabel lblTableTitle;
    private javax.swing.JLabel lblguru;
    private javax.swing.JLabel lblmapel;
    private javax.swing.JLabel lblnama;
    private javax.swing.JLabel lblnisn;
    private javax.swing.JPanel pnlInfoSiswa;
    private javax.swing.JPanel pnlRoot;
    private javax.swing.JButton save;
    private javax.swing.JTable tblsiswa;
    private javax.swing.JTextField txcari;
    private javax.swing.JTextField txnilai;
    // End of variables declaration//GEN-END:variables
}
