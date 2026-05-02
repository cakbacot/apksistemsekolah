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
public class homedashboardtu extends javax.swing.JInternalFrame {
    private Connection conn = new koneksi().getConnection();

    /**
     * Creates new form homedashboardtu
     */
    public homedashboardtu() {
        initComponents();
        tampilkanDiagramSiswa();
        tampilkanDiagramGuru();
    }
    
    public void tampilkanDiagramSiswa() {
    DefaultPieDataset dataset = new DefaultPieDataset();

    try {
        if (this.conn == null || this.conn.isClosed()) {
            this.conn = new koneksi().getConnection(); 
        }
        
        String sql = "SELECT jkel, COUNT(*) as jumlah FROM tbl_siswa GROUP BY jkel";
        java.sql.PreparedStatement ps = conn.prepareStatement(sql);
        java.sql.ResultSet res = ps.executeQuery();

        while (res.next()) {
            String gender = res.getString("jkel");
            int jumlah = res.getInt("jumlah");
            String label = (gender.equalsIgnoreCase("L") || gender.equalsIgnoreCase("Laki-laki")) 
                           ? "Laki-laki" : "Perempuan";
            dataset.setValue(label + " (" + jumlah + ")", jumlah);
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
            if (label.startsWith("Laki-laki")) {
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

public void tampilkanDiagramGuru() {
    DefaultPieDataset dataset = new DefaultPieDataset();

    try {
        if (this.conn == null || this.conn.isClosed()) {
            this.conn = new koneksi().getConnection(); 
        }
        
        // Query untuk tabel guru
        String sql = "SELECT jkel, COUNT(*) as jumlah FROM guru GROUP BY jkel";
        java.sql.PreparedStatement ps = conn.prepareStatement(sql);
        java.sql.ResultSet res = ps.executeQuery();

        while (res.next()) {
            String gender = res.getString("jkel");
            int jumlah = res.getInt("jumlah");
            
            // Logika agar variasi tulisan "Laki-laki" tetap masuk kategori yang sama
            String label = (gender.toLowerCase().startsWith("l")) ? "Laki-laki" : "Perempuan";
            dataset.setValue(label + " (" + jumlah + ")", jumlah);
        }

        JFreeChart chart = ChartFactory.createPieChart(null, dataset, true, true, false);

        // --- STYLING TRANSPARAN & WARNA ---
        chart.setBackgroundPaint(new java.awt.Color(255, 255, 255, 255));
        chart.getLegend().setBackgroundPaint(new java.awt.Color(255, 255, 255, 255));
        chart.getLegend().setItemPaint(java.awt.Color.BLACK); // Teks Legend Hitam

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setBackgroundPaint(null);
        plot.setOutlineVisible(false);
        plot.setShadowPaint(null);
        
        // Loop untuk set warna Navy & Pink berdasarkan label
        for (Object key : dataset.getKeys()) {
            String l = key.toString();
            if (l.startsWith("Laki-laki")) {
                plot.setSectionPaint((Comparable) key, new java.awt.Color(0, 51, 102)); 
            } else {
                plot.setSectionPaint((Comparable) key, new java.awt.Color(135, 206, 250)); 
            }
        }
        
        // Teks Label Hitam
        plot.setLabelPaint(java.awt.Color.BLACK);
        plot.setLabelFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        plot.setLabelBackgroundPaint(new java.awt.Color(0, 0, 0, 0));
        plot.setLabelOutlinePaint(null);

        // --- TAMPILKAN KE PANEL pnlgr ---
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(pnlgr.getWidth(), pnlgr.getHeight()));
        chartPanel.setOpaque(false);
        
        pnlgr.removeAll();
        pnlgr.setLayout(new java.awt.BorderLayout());
        pnlgr.add(chartPanel, java.awt.BorderLayout.CENTER);
        pnlgr.validate();

    } catch (Exception e) {
        System.err.println("Gagal load diagram guru: " + e.getMessage());
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
        jLabel2 = new javax.swing.JLabel();
        panelDiagram = new javax.swing.JPanel();
        pnlgr = new javax.swing.JPanel();

        jLabel3.setFont(new java.awt.Font("Segoe UI Symbol", 3, 12)); // NOI18N
        jLabel3.setText("DIAGRAM GURU");

        jLabel2.setFont(new java.awt.Font("Segoe UI Symbol", 3, 12)); // NOI18N
        jLabel2.setText("DIAGRAM SISWA/SISWI");

        panelDiagram.setPreferredSize(new java.awt.Dimension(400, 300));
        panelDiagram.setLayout(new java.awt.BorderLayout());

        pnlgr.setPreferredSize(new java.awt.Dimension(400, 300));
        pnlgr.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelDiagram, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(113, 113, 113)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlgr, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(70, 70, 70))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelDiagram, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlgr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(105, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel panelDiagram;
    private javax.swing.JPanel pnlgr;
    // End of variables declaration//GEN-END:variables
}
