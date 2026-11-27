/*
 * Beranda Akademik Guru - SMP 259 Jakarta
 */
package tampilan;

import java.awt.BorderLayout;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Locale;
import koneksi.koneksi;
import org.jfree.chart.ChartPanel;
import org.jfree.data.general.DefaultPieDataset;

/**
 * Halaman beranda ringkasan akademik & kehadiran siswa pada Dashboard Guru.
 */
public class homedashboardguru extends javax.swing.JInternalFrame {

    private Connection conn = new koneksi().getConnection();

    /**
     * Creates new form homedashboardguru
     */
    public homedashboardguru() {
        UITheme.setup();
        initComponents();
        applyCardStyling();
        aturJudulDiagram();
        tampilkanDiagramSiswa();
        tampilkanDiagramAbsensi();
    }

    private void applyCardStyling() {
        UITheme.styleCard(pnlKpiKelas);
        UITheme.styleCard(pnlKpiSiswa);
        UITheme.styleCard(pnlKpiHadir);
        UITheme.styleCard(pnlCardSiswa);
        UITheme.styleCard(pnlCardAbsen);
    }

    private Connection ensureConnection() {
        try {
            if (this.conn == null || this.conn.isClosed()) {
                this.conn = new koneksi().getConnection();
            }
        } catch (Exception e) {
            this.conn = new koneksi().getConnection();
        }
        return this.conn;
    }

    private void aturJudulDiagram() {
        String kg = GuruSession.getKelas();
        String kdGuru = GuruSession.getKdGuru();

        if (kg != null && !kg.trim().isEmpty()) {
            lblValKelas.setText(kg);
            lblSubKpi1.setText("Wali Kelas aktif");
            tkelas.setText("Komposisi Siswa Kelas " + kg);
            jLabel3.setText("Statistik Kehadiran Kelas " + kg);
        } else if (kdGuru != null && !kdGuru.trim().isEmpty()) {
            String daftarKelas = ambilDaftarKelasAmpuan(kdGuru);
            if (!daftarKelas.isEmpty()) {
                lblValKelas.setText(daftarKelas);
                lblSubKpi1.setText("Kelas jadwal mengajar");
                tkelas.setText("Komposisi Siswa (" + daftarKelas + ")");
                jLabel3.setText("Statistik Kehadiran (" + daftarKelas + ")");
            } else {
                lblValKelas.setText("Belum Ada");
                lblSubKpi1.setText("Menunggu jadwal kelas");
                tkelas.setText("Komposisi Siswa Kelas Ampuan");
                jLabel3.setText("Statistik Kehadiran Siswa");
            }
        } else {
            lblValKelas.setText("Semua Kelas");
            lblSubKpi1.setText("Mode pratinjau guru");
            tkelas.setText("Komposisi Siswa Kelas Ampuan");
            jLabel3.setText("Statistik Kehadiran Siswa");
        }
    }

    private String ambilDaftarKelasAmpuan(String kdGuru) {
        StringBuilder sb = new StringBuilder();
        try {
            Connection c = ensureConnection();
            if (c == null) {
                return "";
            }
            String sql = "SELECT DISTINCT k.kelas FROM tbl_jadwal j "
                    + "JOIN tbl_kelas k ON j.kelas = k.id_kelas "
                    + "WHERE j.kd_guru = ? ORDER BY k.kelas ASC";
            try (PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setString(1, kdGuru);
                try (ResultSet res = ps.executeQuery()) {
                    while (res.next()) {
                        if (sb.length() > 0) {
                            sb.append(", ");
                        }
                        sb.append(res.getString("kelas"));
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Gagal memuat daftar kelas ampuan: " + e.getMessage());
        }
        return sb.toString();
    }

    public void tampilkanDiagramSiswa() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        int totalSiswa = 0;

        try {
            Connection c = ensureConnection();
            String kg = GuruSession.getKelas();
            String kdGuru = GuruSession.getKdGuru();

            if (c != null && kg != null && !kg.trim().isEmpty()) {
                String sql = "SELECT s.jkel, COUNT(*) as jumlah "
                        + "FROM tbl_siswa s "
                        + "JOIN tbl_kelas k ON s.kelas = k.id_kelas "
                        + "WHERE k.kelas = ? "
                        + "GROUP BY s.jkel";
                try (PreparedStatement ps = c.prepareStatement(sql)) {
                    ps.setString(1, kg);
                    try (ResultSet res = ps.executeQuery()) {
                        while (res.next()) {
                            String gender = res.getString("jkel");
                            int jumlah = res.getInt("jumlah");
                            totalSiswa += jumlah;
                            String label = (gender != null && (gender.equalsIgnoreCase("L") || gender.equalsIgnoreCase("Laki - laki")))
                                    ? "Laki-laki" : "Perempuan";
                            dataset.setValue(label + " (" + jumlah + ")", jumlah);
                        }
                    }
                }
            } else if (c != null && kdGuru != null && !kdGuru.trim().isEmpty()) {
                String sql = "SELECT s.jkel, COUNT(*) as jumlah "
                        + "FROM tbl_siswa s "
                        + "JOIN tbl_kelas k ON s.kelas = k.id_kelas "
                        + "WHERE k.id_kelas IN (SELECT DISTINCT j.kelas FROM tbl_jadwal j WHERE j.kd_guru = ?) "
                        + "GROUP BY s.jkel";
                try (PreparedStatement ps = c.prepareStatement(sql)) {
                    ps.setString(1, kdGuru);
                    try (ResultSet res = ps.executeQuery()) {
                        while (res.next()) {
                            String gender = res.getString("jkel");
                            int jumlah = res.getInt("jumlah");
                            totalSiswa += jumlah;
                            String label = (gender != null && (gender.equalsIgnoreCase("L") || gender.equalsIgnoreCase("Laki - laki")))
                                    ? "Laki-laki" : "Perempuan";
                            dataset.setValue(label + " (" + jumlah + ")", jumlah);
                        }
                    }
                }
            } else if (c != null) {
                String sql = "SELECT jkel, COUNT(*) as jumlah FROM tbl_siswa GROUP BY jkel";
                try (PreparedStatement ps = c.prepareStatement(sql);
                     ResultSet res = ps.executeQuery()) {
                    while (res.next()) {
                        String gender = res.getString("jkel");
                        int jumlah = res.getInt("jumlah");
                        totalSiswa += jumlah;
                        String label = (gender != null && (gender.equalsIgnoreCase("L") || gender.equalsIgnoreCase("Laki - laki")))
                                ? "Laki-laki" : "Perempuan";
                        dataset.setValue(label + " (" + jumlah + ")", jumlah);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Gagal memuat diagram siswa guru: " + e.getMessage());
        }

        lblValSiswa.setText(String.valueOf(totalSiswa));

        Color[] palette;
        if (dataset.getItemCount() == 0) {
            dataset.setValue("Belum ada data siswa", 1);
            palette = new Color[]{new Color(203, 213, 225)};
        } else {
            palette = new Color[]{
                new Color(37, 99, 235),
                new Color(14, 165, 233),
                new Color(99, 102, 241)
            };
        }

        int w = panelDiagram.getWidth() > 0 ? panelDiagram.getWidth() : 400;
        int h = panelDiagram.getHeight() > 0 ? panelDiagram.getHeight() : 280;
        ChartPanel chartPanel = UITheme.createModernRingChartPanel(dataset, palette, w, h);

        panelDiagram.removeAll();
        panelDiagram.setLayout(new BorderLayout());
        panelDiagram.add(chartPanel, BorderLayout.CENTER);
        panelDiagram.revalidate();
        panelDiagram.repaint();
    }

    public void tampilkanDiagramAbsensi() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        int totalHadir = 0;
        int totalTidakHadir = 0;

        try {
            Connection c = ensureConnection();
            String kg = GuruSession.getKelas();
            String kdGuru = GuruSession.getKdGuru();

            if (c != null && kg != null && !kg.trim().isEmpty()) {
                String sql = "SELECT a.status_hadir, COUNT(*) as total "
                        + "FROM tbl_absen a "
                        + "JOIN tbl_siswa s ON a.nisn = s.nisn "
                        + "JOIN tbl_kelas k ON s.kelas = k.id_kelas "
                        + "WHERE k.kelas = ? "
                        + "GROUP BY a.status_hadir";
                try (PreparedStatement ps = c.prepareStatement(sql)) {
                    ps.setString(1, kg);
                    try (ResultSet res = ps.executeQuery()) {
                        while (res.next()) {
                            if (res.getInt("status_hadir") == 1) {
                                totalHadir += res.getInt("total");
                            } else {
                                totalTidakHadir += res.getInt("total");
                            }
                        }
                    }
                }
            } else if (c != null && kdGuru != null && !kdGuru.trim().isEmpty()) {
                String sql = "SELECT a.status_hadir, COUNT(*) as total "
                        + "FROM tbl_absen a "
                        + "JOIN tbl_siswa s ON a.nisn = s.nisn "
                        + "JOIN tbl_kelas k ON s.kelas = k.id_kelas "
                        + "WHERE k.id_kelas IN (SELECT DISTINCT j.kelas FROM tbl_jadwal j WHERE j.kd_guru = ?) "
                        + "GROUP BY a.status_hadir";
                try (PreparedStatement ps = c.prepareStatement(sql)) {
                    ps.setString(1, kdGuru);
                    try (ResultSet res = ps.executeQuery()) {
                        while (res.next()) {
                            if (res.getInt("status_hadir") == 1) {
                                totalHadir += res.getInt("total");
                            } else {
                                totalTidakHadir += res.getInt("total");
                            }
                        }
                    }
                }
            } else if (c != null) {
                String sql = "SELECT status_hadir, COUNT(*) as total FROM tbl_absen GROUP BY status_hadir";
                try (PreparedStatement ps = c.prepareStatement(sql);
                     ResultSet res = ps.executeQuery()) {
                    while (res.next()) {
                        if (res.getInt("status_hadir") == 1) {
                            totalHadir += res.getInt("total");
                        } else {
                            totalTidakHadir += res.getInt("total");
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Gagal memuat diagram absensi: " + e.getMessage());
        }

        int totalSemua = totalHadir + totalTidakHadir;
        if (totalSemua > 0) {
            double persen = (totalHadir * 100.0) / totalSemua;
            lblValHadir.setText(String.format(Locale.US, "%.1f%%", persen));
            lblSubKpi3.setText(totalHadir + " hadir dari " + totalSemua + " catatan");
        } else {
            lblValHadir.setText("0%");
            lblSubKpi3.setText("Belum ada sesi presensi");
        }

        Color[] palette;
        if (totalSemua == 0) {
            dataset.setValue("Belum ada data absensi", 1);
            palette = new Color[]{new Color(203, 213, 225)};
        } else {
            java.util.List<Color> cols = new java.util.ArrayList<Color>();
            if (totalHadir > 0) {
                dataset.setValue("Hadir (" + totalHadir + ")", totalHadir);
                cols.add(new Color(22, 163, 74));   // Emerald Green
            }
            if (totalTidakHadir > 0) {
                dataset.setValue("Tidak Hadir (" + totalTidakHadir + ")", totalTidakHadir);
                cols.add(new Color(239, 68, 68));   // Coral Red
            }
            palette = cols.toArray(new Color[cols.size()]);
        }

        int w = pnlAbsen.getWidth() > 0 ? pnlAbsen.getWidth() : 400;
        int h = pnlAbsen.getHeight() > 0 ? pnlAbsen.getHeight() : 280;
        ChartPanel chartPanel = UITheme.createModernRingChartPanel(dataset, palette, w, h);

        pnlAbsen.removeAll();
        pnlAbsen.setLayout(new BorderLayout());
        pnlAbsen.add(chartPanel, BorderLayout.CENTER);
        pnlAbsen.revalidate();
        pnlAbsen.repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlCanvas = new javax.swing.JPanel();
        lblHomeTitle = new javax.swing.JLabel();
        lblHomeSub = new javax.swing.JLabel();
        pnlKpiKelas = new javax.swing.JPanel();
        lblTitleKpi1 = new javax.swing.JLabel();
        lblValKelas = new javax.swing.JLabel();
        lblSubKpi1 = new javax.swing.JLabel();
        pnlKpiSiswa = new javax.swing.JPanel();
        lblTitleKpi2 = new javax.swing.JLabel();
        lblValSiswa = new javax.swing.JLabel();
        lblSubKpi2 = new javax.swing.JLabel();
        pnlKpiHadir = new javax.swing.JPanel();
        lblTitleKpi3 = new javax.swing.JLabel();
        lblValHadir = new javax.swing.JLabel();
        lblSubKpi3 = new javax.swing.JLabel();
        pnlCardSiswa = new javax.swing.JPanel();
        tkelas = new javax.swing.JLabel();
        lblSubSiswa = new javax.swing.JLabel();
        panelDiagram = new javax.swing.JPanel();
        pnlCardAbsen = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        lblSubAbsen = new javax.swing.JLabel();
        pnlAbsen = new javax.swing.JPanel();

        setBackground(new java.awt.Color(241, 245, 249));
        setBorder(null);

        pnlCanvas.setBackground(new java.awt.Color(241, 245, 249));

        lblHomeTitle.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblHomeTitle.setForeground(new java.awt.Color(15, 23, 42));
        lblHomeTitle.setText("Ringkasan Akademik Kelas Ampuan");

        lblHomeSub.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblHomeSub.setForeground(new java.awt.Color(100, 116, 139));
        lblHomeSub.setText("Pantau statistik kelas bimbingan, jumlah peserta didik diampu, dan rasio kehadiran siswa SMP 259 Jakarta.");

        pnlKpiKelas.setBackground(new java.awt.Color(255, 255, 255));
        pnlKpiKelas.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(226, 232, 240), 1, true));

        lblTitleKpi1.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        lblTitleKpi1.setForeground(new java.awt.Color(100, 116, 139));
        lblTitleKpi1.setText("KELAS AMPUAN / WALI KELAS");

        lblValKelas.setFont(new java.awt.Font("Segoe UI", 1, 26)); // NOI18N
        lblValKelas.setForeground(new java.awt.Color(15, 23, 42));
        lblValKelas.setText("-");

        lblSubKpi1.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblSubKpi1.setForeground(new java.awt.Color(100, 116, 139));
        lblSubKpi1.setText("Penugasan kelas aktif");

        javax.swing.GroupLayout pnlKpiKelasLayout = new javax.swing.GroupLayout(pnlKpiKelas);
        pnlKpiKelas.setLayout(pnlKpiKelasLayout);
        pnlKpiKelasLayout.setHorizontalGroup(
            pnlKpiKelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlKpiKelasLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(pnlKpiKelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitleKpi1, javax.swing.GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
                    .addComponent(lblValKelas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblSubKpi1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(22, 22, 22))
        );
        pnlKpiKelasLayout.setVerticalGroup(
            pnlKpiKelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlKpiKelasLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(lblTitleKpi1)
                .addGap(6, 6, 6)
                .addComponent(lblValKelas, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(lblSubKpi1)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        pnlKpiSiswa.setBackground(new java.awt.Color(255, 255, 255));
        pnlKpiSiswa.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(226, 232, 240), 1, true));

        lblTitleKpi2.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        lblTitleKpi2.setForeground(new java.awt.Color(100, 116, 139));
        lblTitleKpi2.setText("PESERTA DIDIK DIAMPU");

        lblValSiswa.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        lblValSiswa.setForeground(new java.awt.Color(37, 99, 235));
        lblValSiswa.setText("0");

        lblSubKpi2.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblSubKpi2.setForeground(new java.awt.Color(100, 116, 139));
        lblSubKpi2.setText("Total siswa pada kelas ampuan");

        javax.swing.GroupLayout pnlKpiSiswaLayout = new javax.swing.GroupLayout(pnlKpiSiswa);
        pnlKpiSiswa.setLayout(pnlKpiSiswaLayout);
        pnlKpiSiswaLayout.setHorizontalGroup(
            pnlKpiSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlKpiSiswaLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(pnlKpiSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitleKpi2, javax.swing.GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
                    .addComponent(lblValSiswa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblSubKpi2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(22, 22, 22))
        );
        pnlKpiSiswaLayout.setVerticalGroup(
            pnlKpiSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlKpiSiswaLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(lblTitleKpi2)
                .addGap(6, 6, 6)
                .addComponent(lblValSiswa, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(lblSubKpi2)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        pnlKpiHadir.setBackground(new java.awt.Color(255, 255, 255));
        pnlKpiHadir.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(226, 232, 240), 1, true));

        lblTitleKpi3.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        lblTitleKpi3.setForeground(new java.awt.Color(100, 116, 139));
        lblTitleKpi3.setText("RASIO KEHADIRAN SISWA");

        lblValHadir.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        lblValHadir.setForeground(new java.awt.Color(22, 163, 74));
        lblValHadir.setText("-");

        lblSubKpi3.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblSubKpi3.setForeground(new java.awt.Color(100, 116, 139));
        lblSubKpi3.setText("Akumulasi sesi presensi kelas");

        javax.swing.GroupLayout pnlKpiHadirLayout = new javax.swing.GroupLayout(pnlKpiHadir);
        pnlKpiHadir.setLayout(pnlKpiHadirLayout);
        pnlKpiHadirLayout.setHorizontalGroup(
            pnlKpiHadirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlKpiHadirLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(pnlKpiHadirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitleKpi3, javax.swing.GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
                    .addComponent(lblValHadir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblSubKpi3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(22, 22, 22))
        );
        pnlKpiHadirLayout.setVerticalGroup(
            pnlKpiHadirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlKpiHadirLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(lblTitleKpi3)
                .addGap(6, 6, 6)
                .addComponent(lblValHadir, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(lblSubKpi3)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        pnlCardSiswa.setBackground(new java.awt.Color(255, 255, 255));
        pnlCardSiswa.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(226, 232, 240), 1, true));

        tkelas.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        tkelas.setForeground(new java.awt.Color(15, 23, 42));
        tkelas.setText("Komposisi Siswa Kelas Ampuan");

        lblSubSiswa.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblSubSiswa.setForeground(new java.awt.Color(100, 116, 139));
        lblSubSiswa.setText("Perbandingan siswa laki-laki dan perempuan");

        panelDiagram.setBackground(new java.awt.Color(255, 255, 255));
        panelDiagram.setPreferredSize(new java.awt.Dimension(400, 280));
        panelDiagram.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout pnlCardSiswaLayout = new javax.swing.GroupLayout(pnlCardSiswa);
        pnlCardSiswa.setLayout(pnlCardSiswaLayout);
        pnlCardSiswaLayout.setHorizontalGroup(
            pnlCardSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCardSiswaLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(pnlCardSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tkelas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblSubSiswa, javax.swing.GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
                    .addComponent(panelDiagram, javax.swing.GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE))
                .addGap(24, 24, 24))
        );
        pnlCardSiswaLayout.setVerticalGroup(
            pnlCardSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCardSiswaLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(tkelas)
                .addGap(2, 2, 2)
                .addComponent(lblSubSiswa)
                .addGap(14, 14, 14)
                .addComponent(panelDiagram, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                .addGap(18, 18, 18))
        );

        pnlCardAbsen.setBackground(new java.awt.Color(255, 255, 255));
        pnlCardAbsen.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(226, 232, 240), 1, true));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(15, 23, 42));
        jLabel3.setText("Statistik Kehadiran Siswa");

        lblSubAbsen.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblSubAbsen.setForeground(new java.awt.Color(100, 116, 139));
        lblSubAbsen.setText("Rasio kehadiran dan ketidakhadiran siswa");

        pnlAbsen.setBackground(new java.awt.Color(255, 255, 255));
        pnlAbsen.setPreferredSize(new java.awt.Dimension(400, 280));
        pnlAbsen.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout pnlCardAbsenLayout = new javax.swing.GroupLayout(pnlCardAbsen);
        pnlCardAbsen.setLayout(pnlCardAbsenLayout);
        pnlCardAbsenLayout.setHorizontalGroup(
            pnlCardAbsenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCardAbsenLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(pnlCardAbsenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblSubAbsen, javax.swing.GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
                    .addComponent(pnlAbsen, javax.swing.GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE))
                .addGap(24, 24, 24))
        );
        pnlCardAbsenLayout.setVerticalGroup(
            pnlCardAbsenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCardAbsenLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel3)
                .addGap(2, 2, 2)
                .addComponent(lblSubAbsen)
                .addGap(14, 14, 14)
                .addComponent(pnlAbsen, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                .addGap(18, 18, 18))
        );

        javax.swing.GroupLayout pnlCanvasLayout = new javax.swing.GroupLayout(pnlCanvas);
        pnlCanvas.setLayout(pnlCanvasLayout);
        pnlCanvasLayout.setHorizontalGroup(
            pnlCanvasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCanvasLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(pnlCanvasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblHomeTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblHomeSub, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlCanvasLayout.createSequentialGroup()
                        .addComponent(pnlKpiKelas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(20, 20, 20)
                        .addComponent(pnlKpiSiswa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(20, 20, 20)
                        .addComponent(pnlKpiHadir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlCanvasLayout.createSequentialGroup()
                        .addComponent(pnlCardSiswa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(22, 22, 22)
                        .addComponent(pnlCardAbsen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(28, 28, 28))
        );
        pnlCanvasLayout.setVerticalGroup(
            pnlCanvasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCanvasLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(lblHomeTitle)
                .addGap(4, 4, 4)
                .addComponent(lblHomeSub)
                .addGap(20, 20, 20)
                .addGroup(pnlCanvasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlKpiKelas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlKpiSiswa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlKpiHadir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(pnlCanvasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlCardSiswa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlCardAbsen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(24, 24, 24))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlCanvas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlCanvas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel lblHomeSub;
    private javax.swing.JLabel lblHomeTitle;
    private javax.swing.JLabel lblSubAbsen;
    private javax.swing.JLabel lblSubKpi1;
    private javax.swing.JLabel lblSubKpi2;
    private javax.swing.JLabel lblSubKpi3;
    private javax.swing.JLabel lblSubSiswa;
    private javax.swing.JLabel lblTitleKpi1;
    private javax.swing.JLabel lblTitleKpi2;
    private javax.swing.JLabel lblTitleKpi3;
    private javax.swing.JLabel lblValHadir;
    private javax.swing.JLabel lblValKelas;
    private javax.swing.JLabel lblValSiswa;
    private javax.swing.JPanel panelDiagram;
    private javax.swing.JPanel pnlAbsen;
    private javax.swing.JPanel pnlCanvas;
    private javax.swing.JPanel pnlCardAbsen;
    private javax.swing.JPanel pnlCardSiswa;
    private javax.swing.JPanel pnlKpiHadir;
    private javax.swing.JPanel pnlKpiKelas;
    private javax.swing.JPanel pnlKpiSiswa;
    private javax.swing.JLabel tkelas;
    // End of variables declaration//GEN-END:variables
}
