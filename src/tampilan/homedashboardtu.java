/*
 * Beranda Ringkasan Administrasi - SMP 259 Jakarta
 */
package tampilan;

import java.awt.BorderLayout;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import koneksi.koneksi;
import org.jfree.chart.ChartPanel;
import org.jfree.data.general.DefaultPieDataset;

/**
 * Halaman beranda ringkasan KPI & statistik pada Dashboard Administrasi.
 */
public class homedashboardtu extends javax.swing.JInternalFrame {

    private Connection conn = new koneksi().getConnection();

    /**
     * Creates new form homedashboardtu
     */
    public homedashboardtu() {
        UITheme.setup();
        initComponents();
        applyCardStyling();
        muatRingkasanKpi();
        tampilkanDiagramSiswa();
        tampilkanDiagramGuru();
    }

    private void applyCardStyling() {
        UITheme.styleCard(pnlKpiSiswa);
        UITheme.styleCard(pnlKpiGuru);
        UITheme.styleCard(pnlKpiKelas);
        UITheme.styleCard(pnlKpiJadwal);
        UITheme.styleCard(pnlCardSiswa);
        UITheme.styleCard(pnlCardGuru);
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

    private int hitungBaris(String sql) {
        try {
            Connection c = ensureConnection();
            if (c == null) {
                return 0;
            }
            try (PreparedStatement ps = c.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            System.err.println("Gagal hitung KPI: " + e.getMessage());
        }
        return 0;
    }

    public void muatRingkasanKpi() {
        lblCountSiswa.setText(String.valueOf(hitungBaris("SELECT COUNT(*) FROM tbl_siswa")));
        lblCountGuru.setText(String.valueOf(hitungBaris("SELECT COUNT(*) FROM guru")));
        lblCountKelas.setText(String.valueOf(hitungBaris("SELECT COUNT(*) FROM tbl_kelas")));
        lblCountJadwal.setText(String.valueOf(hitungBaris("SELECT COUNT(*) FROM tbl_jadwal")));
    }

    public void tampilkanDiagramSiswa() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        try {
            Connection c = ensureConnection();
            if (c != null) {
                String sql = "SELECT jkel, COUNT(*) as jumlah FROM tbl_siswa GROUP BY jkel";
                try (PreparedStatement ps = c.prepareStatement(sql);
                     ResultSet res = ps.executeQuery()) {
                    while (res.next()) {
                        String gender = res.getString("jkel");
                        int jumlah = res.getInt("jumlah");
                        String label = (gender != null && (gender.equalsIgnoreCase("L") || gender.equalsIgnoreCase("Laki - laki")))
                                ? "Laki-laki" : "Perempuan";
                        dataset.setValue(label + " (" + jumlah + ")", jumlah);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Gagal memuat diagram siswa: " + e.getMessage());
        }

        Color[] palette;
        if (dataset.getItemCount() == 0) {
            dataset.setValue("Belum ada data", 1);
            palette = new Color[]{new Color(203, 213, 225)};
        } else {
            palette = new Color[]{
                new Color(37, 99, 235),  // Royal Blue
                new Color(14, 165, 233), // Sky Cyan
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

    public void tampilkanDiagramGuru() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        try {
            Connection c = ensureConnection();
            if (c != null) {
                String sql = "SELECT jkel, COUNT(*) as jumlah FROM guru GROUP BY jkel";
                try (PreparedStatement ps = c.prepareStatement(sql);
                     ResultSet res = ps.executeQuery()) {
                    while (res.next()) {
                        String gender = res.getString("jkel");
                        int jumlah = res.getInt("jumlah");
                        String label = (gender != null && gender.toLowerCase().startsWith("l"))
                                ? "Laki-laki" : "Perempuan";
                        dataset.setValue(label + " (" + jumlah + ")", jumlah);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Gagal memuat diagram guru: " + e.getMessage());
        }

        Color[] palette;
        if (dataset.getItemCount() == 0) {
            dataset.setValue("Belum ada data", 1);
            palette = new Color[]{new Color(203, 213, 225)};
        } else {
            palette = new Color[]{
                new Color(15, 23, 42),   // Deep Navy
                new Color(59, 130, 246), // Bright Blue
                new Color(56, 189, 248)
            };
        }

        int w = pnlgr.getWidth() > 0 ? pnlgr.getWidth() : 400;
        int h = pnlgr.getHeight() > 0 ? pnlgr.getHeight() : 280;
        ChartPanel chartPanel = UITheme.createModernRingChartPanel(dataset, palette, w, h);

        pnlgr.removeAll();
        pnlgr.setLayout(new BorderLayout());
        pnlgr.add(chartPanel, BorderLayout.CENTER);
        pnlgr.revalidate();
        pnlgr.repaint();
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
        pnlKpiSiswa = new javax.swing.JPanel();
        lblKpiTitle1 = new javax.swing.JLabel();
        lblCountSiswa = new javax.swing.JLabel();
        lblKpiSub1 = new javax.swing.JLabel();
        pnlKpiGuru = new javax.swing.JPanel();
        lblKpiTitle2 = new javax.swing.JLabel();
        lblCountGuru = new javax.swing.JLabel();
        lblKpiSub2 = new javax.swing.JLabel();
        pnlKpiKelas = new javax.swing.JPanel();
        lblKpiTitle3 = new javax.swing.JLabel();
        lblCountKelas = new javax.swing.JLabel();
        lblKpiSub3 = new javax.swing.JLabel();
        pnlKpiJadwal = new javax.swing.JPanel();
        lblKpiTitle4 = new javax.swing.JLabel();
        lblCountJadwal = new javax.swing.JLabel();
        lblKpiSub4 = new javax.swing.JLabel();
        pnlCardSiswa = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lblSubSiswa = new javax.swing.JLabel();
        panelDiagram = new javax.swing.JPanel();
        pnlCardGuru = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        lblSubGuru = new javax.swing.JLabel();
        pnlgr = new javax.swing.JPanel();

        setBackground(new java.awt.Color(241, 245, 249));
        setBorder(null);

        pnlCanvas.setBackground(new java.awt.Color(241, 245, 249));

        lblHomeTitle.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblHomeTitle.setForeground(new java.awt.Color(15, 23, 42));
        lblHomeTitle.setText("Ringkasan Statistik Sekolah");

        lblHomeSub.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblHomeSub.setForeground(new java.awt.Color(100, 116, 139));
        lblHomeSub.setText("Pantau data peserta didik, tenaga pendidik, rombongan belajar, dan jadwal aktif SMP 259 Jakarta.");

        pnlKpiSiswa.setBackground(new java.awt.Color(255, 255, 255));
        pnlKpiSiswa.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(226, 232, 240), 1, true));

        lblKpiTitle1.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        lblKpiTitle1.setForeground(new java.awt.Color(100, 116, 139));
        lblKpiTitle1.setText("TOTAL PESERTA DIDIK");

        lblCountSiswa.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        lblCountSiswa.setForeground(new java.awt.Color(37, 99, 235));
        lblCountSiswa.setText("0");

        lblKpiSub1.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblKpiSub1.setForeground(new java.awt.Color(100, 116, 139));
        lblKpiSub1.setText("Siswa aktif terdaftar");

        javax.swing.GroupLayout pnlKpiSiswaLayout = new javax.swing.GroupLayout(pnlKpiSiswa);
        pnlKpiSiswa.setLayout(pnlKpiSiswaLayout);
        pnlKpiSiswaLayout.setHorizontalGroup(
            pnlKpiSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlKpiSiswaLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlKpiSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblKpiTitle1, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                    .addComponent(lblCountSiswa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblKpiSub1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );
        pnlKpiSiswaLayout.setVerticalGroup(
            pnlKpiSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlKpiSiswaLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(lblKpiTitle1)
                .addGap(6, 6, 6)
                .addComponent(lblCountSiswa, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(lblKpiSub1)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        pnlKpiGuru.setBackground(new java.awt.Color(255, 255, 255));
        pnlKpiGuru.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(226, 232, 240), 1, true));

        lblKpiTitle2.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        lblKpiTitle2.setForeground(new java.awt.Color(100, 116, 139));
        lblKpiTitle2.setText("TENAGA PENDIDIK");

        lblCountGuru.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        lblCountGuru.setForeground(new java.awt.Color(15, 23, 42));
        lblCountGuru.setText("0");

        lblKpiSub2.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblKpiSub2.setForeground(new java.awt.Color(100, 116, 139));
        lblKpiSub2.setText("Guru pengajar aktif");

        javax.swing.GroupLayout pnlKpiGuruLayout = new javax.swing.GroupLayout(pnlKpiGuru);
        pnlKpiGuru.setLayout(pnlKpiGuruLayout);
        pnlKpiGuruLayout.setHorizontalGroup(
            pnlKpiGuruLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlKpiGuruLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlKpiGuruLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblKpiTitle2, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                    .addComponent(lblCountGuru, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblKpiSub2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );
        pnlKpiGuruLayout.setVerticalGroup(
            pnlKpiGuruLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlKpiGuruLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(lblKpiTitle2)
                .addGap(6, 6, 6)
                .addComponent(lblCountGuru, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(lblKpiSub2)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        pnlKpiKelas.setBackground(new java.awt.Color(255, 255, 255));
        pnlKpiKelas.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(226, 232, 240), 1, true));

        lblKpiTitle3.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        lblKpiTitle3.setForeground(new java.awt.Color(100, 116, 139));
        lblKpiTitle3.setText("ROMBONGAN BELAJAR");

        lblCountKelas.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        lblCountKelas.setForeground(new java.awt.Color(2, 132, 199));
        lblCountKelas.setText("0");

        lblKpiSub3.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblKpiSub3.setForeground(new java.awt.Color(100, 116, 139));
        lblKpiSub3.setText("Ruang kelas tersedia");

        javax.swing.GroupLayout pnlKpiKelasLayout = new javax.swing.GroupLayout(pnlKpiKelas);
        pnlKpiKelas.setLayout(pnlKpiKelasLayout);
        pnlKpiKelasLayout.setHorizontalGroup(
            pnlKpiKelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlKpiKelasLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlKpiKelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblKpiTitle3, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                    .addComponent(lblCountKelas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblKpiSub3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );
        pnlKpiKelasLayout.setVerticalGroup(
            pnlKpiKelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlKpiKelasLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(lblKpiTitle3)
                .addGap(6, 6, 6)
                .addComponent(lblCountKelas, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(lblKpiSub3)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        pnlKpiJadwal.setBackground(new java.awt.Color(255, 255, 255));
        pnlKpiJadwal.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(226, 232, 240), 1, true));

        lblKpiTitle4.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        lblKpiTitle4.setForeground(new java.awt.Color(100, 116, 139));
        lblKpiTitle4.setText("SESI JADWAL PELAJARAN");

        lblCountJadwal.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        lblCountJadwal.setForeground(new java.awt.Color(22, 163, 74));
        lblCountJadwal.setText("0");

        lblKpiSub4.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblKpiSub4.setForeground(new java.awt.Color(100, 116, 139));
        lblKpiSub4.setText("Jadwal KBM aktif");

        javax.swing.GroupLayout pnlKpiJadwalLayout = new javax.swing.GroupLayout(pnlKpiJadwal);
        pnlKpiJadwal.setLayout(pnlKpiJadwalLayout);
        pnlKpiJadwalLayout.setHorizontalGroup(
            pnlKpiJadwalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlKpiJadwalLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlKpiJadwalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblKpiTitle4, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                    .addComponent(lblCountJadwal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblKpiSub4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );
        pnlKpiJadwalLayout.setVerticalGroup(
            pnlKpiJadwalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlKpiJadwalLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(lblKpiTitle4)
                .addGap(6, 6, 6)
                .addComponent(lblCountJadwal, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(lblKpiSub4)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        pnlCardSiswa.setBackground(new java.awt.Color(255, 255, 255));
        pnlCardSiswa.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(226, 232, 240), 1, true));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(15, 23, 42));
        jLabel2.setText("Komposisi Peserta Didik");

        lblSubSiswa.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblSubSiswa.setForeground(new java.awt.Color(100, 116, 139));
        lblSubSiswa.setText("Distribusi siswa laki-laki dan perempuan terdaftar");

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
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblSubSiswa, javax.swing.GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
                    .addComponent(panelDiagram, javax.swing.GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE))
                .addGap(24, 24, 24))
        );
        pnlCardSiswaLayout.setVerticalGroup(
            pnlCardSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCardSiswaLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel2)
                .addGap(2, 2, 2)
                .addComponent(lblSubSiswa)
                .addGap(14, 14, 14)
                .addComponent(panelDiagram, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                .addGap(18, 18, 18))
        );

        pnlCardGuru.setBackground(new java.awt.Color(255, 255, 255));
        pnlCardGuru.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(226, 232, 240), 1, true));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(15, 23, 42));
        jLabel3.setText("Komposisi Tenaga Pendidik");

        lblSubGuru.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblSubGuru.setForeground(new java.awt.Color(100, 116, 139));
        lblSubGuru.setText("Distribusi guru pengajar laki-laki dan perempuan");

        pnlgr.setBackground(new java.awt.Color(255, 255, 255));
        pnlgr.setPreferredSize(new java.awt.Dimension(400, 280));
        pnlgr.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout pnlCardGuruLayout = new javax.swing.GroupLayout(pnlCardGuru);
        pnlCardGuru.setLayout(pnlCardGuruLayout);
        pnlCardGuruLayout.setHorizontalGroup(
            pnlCardGuruLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCardGuruLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(pnlCardGuruLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblSubGuru, javax.swing.GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
                    .addComponent(pnlgr, javax.swing.GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE))
                .addGap(24, 24, 24))
        );
        pnlCardGuruLayout.setVerticalGroup(
            pnlCardGuruLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCardGuruLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel3)
                .addGap(2, 2, 2)
                .addComponent(lblSubGuru)
                .addGap(14, 14, 14)
                .addComponent(pnlgr, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
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
                        .addComponent(pnlKpiSiswa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(pnlKpiGuru, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(pnlKpiKelas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(pnlKpiJadwal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlCanvasLayout.createSequentialGroup()
                        .addComponent(pnlCardSiswa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(22, 22, 22)
                        .addComponent(pnlCardGuru, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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
                    .addComponent(pnlKpiSiswa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlKpiGuru, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlKpiKelas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlKpiJadwal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(pnlCanvasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlCardSiswa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlCardGuru, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel lblCountGuru;
    private javax.swing.JLabel lblCountJadwal;
    private javax.swing.JLabel lblCountKelas;
    private javax.swing.JLabel lblCountSiswa;
    private javax.swing.JLabel lblHomeSub;
    private javax.swing.JLabel lblHomeTitle;
    private javax.swing.JLabel lblKpiSub1;
    private javax.swing.JLabel lblKpiSub2;
    private javax.swing.JLabel lblKpiSub3;
    private javax.swing.JLabel lblKpiSub4;
    private javax.swing.JLabel lblKpiTitle1;
    private javax.swing.JLabel lblKpiTitle2;
    private javax.swing.JLabel lblKpiTitle3;
    private javax.swing.JLabel lblKpiTitle4;
    private javax.swing.JLabel lblSubGuru;
    private javax.swing.JLabel lblSubSiswa;
    private javax.swing.JPanel panelDiagram;
    private javax.swing.JPanel pnlCanvas;
    private javax.swing.JPanel pnlCardGuru;
    private javax.swing.JPanel pnlCardSiswa;
    private javax.swing.JPanel pnlKpiGuru;
    private javax.swing.JPanel pnlKpiJadwal;
    private javax.swing.JPanel pnlKpiKelas;
    private javax.swing.JPanel pnlKpiSiswa;
    private javax.swing.JPanel pnlgr;
    // End of variables declaration//GEN-END:variables
}
