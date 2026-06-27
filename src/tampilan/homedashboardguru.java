/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tampilan;
import java.sql.Connection;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import koneksi.koneksi;

/**
 *
 * @author User
 */
public class homedashboardguru extends javax.swing.JInternalFrame {
    private Connection conn = new koneksi().getConnection();

    /**
     * Creates new form homedashboardtu
     */
    public homedashboardguru() {
        initComponents();
        
        tkelas.setText(("DIAGRAM KELAS ") + GuruSession.getKelas());
        tampilkanDiagramSiswa();
        tampilkanDiagramAbsensi();
        
    }
    
    
    public void tampilkanDiagramSiswa() {
    DefaultPieDataset dataset = new DefaultPieDataset();

    try {
        if (this.conn == null || this.conn.isClosed()) {
            this.conn = new koneksi().getConnection(); 
        }
        String kg = GuruSession.getKelas();
        if (kg != null && !kg.trim().isEmpty()){

            String sql = "SELECT s.jkel, COUNT(*) as jumlah " +
             "FROM tbl_siswa s " +
             "JOIN tbl_kelas k ON s.kelas = k.id_kelas " +
             "WHERE k.kelas = ? " +
             "GROUP BY s.jkel";
            java.sql.PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, kg);
            java.sql.ResultSet res = ps.executeQuery();

            while (res.next()) {
                String gender = res.getString("jkel");
                int jumlah = res.getInt("jumlah");
                String label = (gender.equalsIgnoreCase("L") || gender.equalsIgnoreCase("Laki - laki")) 
                               ? "Laki - laki" : "Perempuan";
                dataset.setValue(label + " (" + jumlah + ")", jumlah);
            }
        }
        JFreeChart chart = ChartFactory.createPieChart(null, dataset, true, true, false);

        // --- PENGATURAN TRANSPARAN & UKURAN ---
        
        // 1. Menghilangkan Background Chart Utama
        chart.setBackgroundPaint(new java.awt.Color(255, 255, 255, 255)); // Transparan total
        chart.getLegend().setBackgroundPaint(new java.awt.Color(255, 255, 255, 255)); // Legend transparan
        chart.getLegend().setItemPaint(java.awt.Color.BLACK); // Teks legend jadi putih agar kontras

        // 2. Menghilangkan Background Plot (Area Lingkaran)
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setBackgroundPaint(null); // Menghapus warna background plot
        plot.setOutlineVisible(false); // Menghapus garis kotak di sekitar lingkaran
        plot.setShadowPaint(null);     // Menghilangkan bayangan agar lebih flat/minimalis
        
      // 3. Warna Section (Navy & Pink)
        // Kita harus mengambil key/label yang persis sama dengan yang ada di dataset
        for (Object key : dataset.getKeys()) {
            String label = key.toString();
            if (label.startsWith("Laki - laki")) {
                plot.setSectionPaint((Comparable) key, new java.awt.Color(44, 62, 80)); // Navy
            } else if (label.startsWith("Perempuan")) {
                plot.setSectionPaint((Comparable) key, new java.awt.Color(255, 105, 180)); // Pink
            }
        }
        // 5. Render ke Panel dengan Ukuran Otomatis
        ChartPanel chartPanel = new ChartPanel(chart);
        
        // KUNCI: Agar ukuran mengikuti ukuran JPanel di Design
        chartPanel.setPreferredSize(new java.awt.Dimension(panelDiagram.getWidth(), panelDiagram.getHeight()));
        chartPanel.setOpaque(false); // ChartPanel juga dibuat transparan
        
        panelDiagram.removeAll();
        panelDiagram.setLayout(new java.awt.BorderLayout());
        panelDiagram.add(chartPanel, java.awt.BorderLayout.CENTER);
        panelDiagram.validate();

    } catch (Exception e) {
        System.err.println("Gagal load data diagram: " + e.getMessage());
    }
}

public void tampilkanDiagramAbsensi() {
    DefaultPieDataset dataset = new DefaultPieDataset();

    try {
        if (this.conn == null || this.conn.isClosed()) {
            this.conn = new koneksi().getConnection(); 
        }
        
        String kg = GuruSession.getKelas();
        
        if (kg != null && !kg.trim().isEmpty()) {
            
            // --- UPDATE QUERY ---
            // Kita gabungkan tbl_absen ke tbl_siswa (lewat nisn), lalu ke tbl_kelas (lewat kelas/id_kelas)
            String sql = "SELECT a.status_hadir, COUNT(*) as total " +
                         "FROM tbl_absen a " +
                         "JOIN tbl_siswa s ON a.nisn = s.nisn " +
                         "JOIN tbl_kelas k ON s.kelas = k.id_kelas " +
                         "WHERE k.kelas = ? " +
                         "GROUP BY a.status_hadir";
                         
            java.sql.PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, kg);
            java.sql.ResultSet res = ps.executeQuery();

            int totalHadir = 0;
            int totalTidakHadir = 0;

            while (res.next()) {
                // Menggunakan kolom baru: status_hadir
                int statusAbsen = res.getInt("status_hadir"); 
                int total = res.getInt("total");

                // Asumsi di database Anda: 1 = Hadir, 0 = Tidak Hadir
                if (statusAbsen == 1) {
                    totalHadir += total;
                } else {
                    totalTidakHadir += total;
                }
            }

            // --- PENGAMAN JIKA DATA KOSONG ---
            if (totalHadir == 0 && totalTidakHadir == 0) {
                dataset.setValue("Belum ada data absensi", 1); 
            } else {
                if (totalHadir > 0) dataset.setValue("Hadir (" + totalHadir + ")", totalHadir);
                if (totalTidakHadir > 0) dataset.setValue("Tidak Hadir (" + totalTidakHadir + ")", totalTidakHadir);
            }

            JFreeChart chart = ChartFactory.createPieChart(null, dataset, true, true, false);

            // --- STYLING TRANSPARAN ---
            chart.setBackgroundPaint(new java.awt.Color(255, 255, 255, 255));
            chart.getLegend().setBackgroundPaint(new java.awt.Color(255, 255, 255, 255));
            chart.getLegend().setItemPaint(java.awt.Color.BLACK); 

            PiePlot plot = (PiePlot) chart.getPlot();
            plot.setBackgroundPaint(null);
            plot.setOutlineVisible(false);
            plot.setShadowPaint(null);
            
            // --- PEWARNAAN OTOMATIS ---
            for (Object key : dataset.getKeys()) {
                String label = key.toString().toLowerCase();
                
                if (label.contains("tidak hadir")) {
                    plot.setSectionPaint((Comparable) key, new java.awt.Color(231, 76, 60));  // Merah
                } else if (label.contains("hadir")) {
                    plot.setSectionPaint((Comparable) key, new java.awt.Color(46, 204, 113)); // Hijau
                } else {
                    plot.setSectionPaint((Comparable) key, new java.awt.Color(189, 195, 199)); // Abu-abu
                }
            }
            
            // Teks Label
            plot.setLabelPaint(java.awt.Color.BLACK);
            plot.setLabelFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
            plot.setLabelBackgroundPaint(new java.awt.Color(0, 0, 0, 0));
            plot.setLabelOutlinePaint(null);

            // --- MASUKKAN KE PANEL ---
            // Ingat: Ganti 'pnlAbsen' dengan nama panel Anda sendiri!
            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setPreferredSize(new java.awt.Dimension(pnlAbsen.getWidth(), pnlAbsen.getHeight()));
            chartPanel.setOpaque(false);
            
            pnlAbsen.removeAll();
            pnlAbsen.setLayout(new java.awt.BorderLayout());
            pnlAbsen.add(chartPanel, java.awt.BorderLayout.CENTER);
            pnlAbsen.validate();
            
        }

    } catch (Exception e) {
        System.err.println("Gagal load diagram absensi: " + e.getMessage());
        e.printStackTrace();
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

        jLabel3 = new javax.swing.JLabel();
        tkelas = new javax.swing.JLabel();
        panelDiagram = new javax.swing.JPanel();
        pnlAbsen = new javax.swing.JPanel();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(null);

        jLabel3.setFont(new java.awt.Font("Segoe UI Symbol", 3, 12)); // NOI18N
        jLabel3.setText("DIAGRAM KEHADIRAN");

        tkelas.setFont(new java.awt.Font("Segoe UI Symbol", 3, 12)); // NOI18N

        panelDiagram.setPreferredSize(new java.awt.Dimension(400, 300));
        panelDiagram.setLayout(new java.awt.BorderLayout());

        pnlAbsen.setPreferredSize(new java.awt.Dimension(400, 300));
        pnlAbsen.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tkelas, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelDiagram, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(113, 113, 113)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlAbsen, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(70, 70, 70))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tkelas)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelDiagram, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlAbsen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(108, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel panelDiagram;
    private javax.swing.JPanel pnlAbsen;
    private javax.swing.JLabel tkelas;
    // End of variables declaration//GEN-END:variables
}
