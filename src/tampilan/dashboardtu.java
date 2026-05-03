/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tampilan;

import java.awt.Image;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import koneksi.koneksi;

/**
 *
 * @author faisa
 */
public class dashboardtu extends javax.swing.JFrame {
private Connection conn = new koneksi().getConnection();
    /**
     * Creates new form dashboardtu
     */
     
     
    public dashboardtu() {
        com.formdev.flatlaf.FlatLightLaf.setup();
        initComponents();
        this.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        scaleImage(); 
       tampilkanHomeAwal();
       
       com.formdev.flatlaf.FlatLightLaf.setup();
    // Inisialisasi HomeFrame (Isinya diagram kamu)
    homedashboardtu h = new homedashboardtu();
    desktop.add(h); // Masukkan ke JDesktopPane
    
    // --- SETTING AGAR TERLIHAT SEPERTI BACKGROUND ---
    h.setBorder(null); // Hilangkan garis pinggir
    ((javax.swing.plaf.basic.BasicInternalFrameUI)h.getUI()).setNorthPane(null); // Hilangkan judul/bar atas
    
    try {
        h.setMaximum(true); // Paksa ukuran maksimal memenuhi desktop
    } catch (java.beans.PropertyVetoException e) {}
    
    h.setVisible(true);
    h.tampilkanDiagramSiswa();
    h.tampilkanDiagramGuru();
    
    // Set icon tombol menu (Cukup sekali saja di sini)
    setFixedIcon(menukelas, "/resource/class.png");
    setFixedIcon(menuguru, "/resource/guru.png");
    setFixedIcon(menusiswa, "/resource/murid.png");
    setFixedIcon(tranksaksi, "/resource/tranksaksi.png");
    setFixedIcon(home,"/resource/home.png");
    setFixedIcon(keluar,"/resource/keluar.png");
    
    }

// FUNGSI 1: Khusus untuk Logo (JLabel) - Mengikuti ukuran label
private void scaleImage() {
    try {
        // Alamat logo kamu
        ImageIcon iconAwal = new ImageIcon(getClass().getResource("/resource/logo.png"));
        Image imgLama = iconAwal.getImage();
        
        // Mengambil ukuran lbl_logo saat ini
        int width = lbl_logo.getWidth();
        int height = lbl_logo.getHeight();
        
        if (width > 0 && height > 0) {
            Image imgBaru = imgLama.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            lbl_logo.setIcon(new ImageIcon(imgBaru));
        }
    } catch (Exception e) {
        System.err.println("Error logo: " + e.getMessage());
    }
}

public void tampilkanHomeAwal() {
    try {
        // Hapus isi desktop jika ada sisa frame lain
        desktop.removeAll();
        
        homedashboardtu h = new homedashboardtu();
        
        // Setting tampilan (tanpa border & bar judul)
        h.setBorder(null);
        ((javax.swing.plaf.basic.BasicInternalFrameUI)h.getUI()).setNorthPane(null);
        
        desktop.add(h);
        
        // Ambil ukuran desktop yang sudah di-render oleh sistem
        h.setSize(desktop.getWidth(), desktop.getHeight());
        h.setVisible(true);
        
        // Panggil diagram (pastikan method ini public di homedashboardtu)
        h.tampilkanDiagramSiswa();
        h.tampilkanDiagramGuru();
        
        desktop.repaint();
        desktop.revalidate();
    } catch (Exception e) {
        System.err.println("Gagal memuat home awal: " + e.getMessage());
    }
}

private void setFixedIcon(javax.swing.JButton btn, String path) {
    try {
        ImageIcon iconAwal = new ImageIcon(getClass().getResource(path));
        Image imgBaru = iconAwal.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        btn.setIcon(new ImageIcon(imgBaru));

        // Styling Dasar
        btn.setForeground(java.awt.Color.WHITE);
        btn.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setOpaque(false);
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        
        btn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btn.setMargin(new java.awt.Insets(10, 20, 10, 10));
        btn.setIconTextGap(20);

        // --- CARA MANUAL: MOUSE LISTENER UNTUK HOVER KUNING ---
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                // Saat mouse masuk: Background jadi kuning, teks jadi hitam agar kontras
                btn.setContentAreaFilled(true);
                btn.setBackground(new java.awt.Color(255, 255, 0)); // Kuning Terang
                btn.setForeground(java.awt.Color.BLACK); 
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                // Saat mouse keluar: Kembali ke semula (transparan & teks putih)
                btn.setContentAreaFilled(false);
                btn.setForeground(java.awt.Color.WHITE);
            }
        });

    } catch (Exception e) {
        System.err.println("Gagal set hover: " + e.getMessage());
    }
}


// * This method is called from within the constructor to initialize the form.
    // * WARNING: Do NOT modify this code. The content of this method is always
    // * regenerated by the Form Editor.
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        lbl_logo = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        home = new javax.swing.JButton();
        desktop = new javax.swing.JDesktopPane();
        jPanel1 = new javax.swing.JPanel();
        menusiswa = new javax.swing.JButton();
        menukelas = new javax.swing.JButton();
        menuguru = new javax.swing.JButton();
        tranksaksi = new javax.swing.JButton();
        keluar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(0, 8, 97));

        lbl_logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/logo.png"))); // NOI18N
        lbl_logo.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                lbl_logoComponentResized(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Rockwell Extra Bold", 3, 22)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("SELAMAT DATANG DI DASHBOARD TATA USAHA");

        home.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        home.setText("HOME");
        home.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                homeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_logo, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(67, 67, 67)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 683, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(home, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(73, 73, 73))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_logo, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(home, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        desktop.setBackground(new java.awt.Color(255, 255, 255));
        desktop.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                desktopComponentResized(evt);
            }
        });

        javax.swing.GroupLayout desktopLayout = new javax.swing.GroupLayout(desktop);
        desktop.setLayout(desktopLayout);
        desktopLayout.setHorizontalGroup(
            desktopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1120, Short.MAX_VALUE)
        );
        desktopLayout.setVerticalGroup(
            desktopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel1.setBackground(new java.awt.Color(0, 8, 97));
        jPanel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        menusiswa.setFont(new java.awt.Font("Segoe UI Symbol", 1, 14)); // NOI18N
        menusiswa.setForeground(new java.awt.Color(255, 255, 255));
        menusiswa.setText("Siswa");
        menusiswa.setBorder(null);
        menusiswa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menusiswa.setFocusPainted(false);
        menusiswa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menusiswaActionPerformed(evt);
            }
        });

        menukelas.setFont(new java.awt.Font("Segoe UI Symbol", 1, 14)); // NOI18N
        menukelas.setForeground(new java.awt.Color(255, 255, 255));
        menukelas.setText("Kelas");
        menukelas.setBorder(null);
        menukelas.setBorderPainted(false);
        menukelas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menukelas.setFocusPainted(false);
        menukelas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menukelasActionPerformed(evt);
            }
        });

        menuguru.setFont(new java.awt.Font("Segoe UI Symbol", 1, 14)); // NOI18N
        menuguru.setForeground(new java.awt.Color(255, 255, 255));
        menuguru.setText("Guru");
        menuguru.setBorder(null);
        menuguru.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuguru.setFocusPainted(false);
        menuguru.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuguruActionPerformed(evt);
            }
        });

        tranksaksi.setFont(new java.awt.Font("Segoe UI Symbol", 1, 14)); // NOI18N
        tranksaksi.setForeground(new java.awt.Color(255, 255, 255));
        tranksaksi.setText("Transaksi");
        tranksaksi.setToolTipText("");
        tranksaksi.setBorder(null);
        tranksaksi.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tranksaksi.setFocusPainted(false);
        tranksaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tranksaksiActionPerformed(evt);
            }
        });

        keluar.setBackground(new java.awt.Color(255, 0, 0));
        keluar.setText("Logout");
        keluar.setToolTipText("");
        keluar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        keluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                keluarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(menukelas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(menusiswa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(menuguru, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tranksaksi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(keluar, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(menukelas, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(menusiswa, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(menuguru, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(tranksaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(keluar)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(desktop))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(desktop)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menukelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menukelasActionPerformed
        // 1. Bersihkan area desktop agar tidak ada form yang tumpang tindih
    desktop.removeAll();
    desktop.repaint();
    
    // 2. Buat objek dari JInternalFrame menu_siswa
    menu_kelas mk = new menu_kelas();
    
    // 3. Masukkan objek tersebut ke dalam JDesktopPane
    desktop.add(mk);
    
    // 4. Tampilkan formnya
    mk.setVisible(true);
    
    try {
        // 5. Buat form otomatis memenuhi seluruh area desktop (Full Screen di dalam panel)
        mk.setMaximum(true);
    } catch (java.beans.PropertyVetoException e) {
        // Jika gagal dimaksimalkan, tampilkan pesan error di console
        System.err.println("Gagal memaksimalkan Internal Frame: " + e.getMessage());
    }// Agar muncul di tengah layar       
    }//GEN-LAST:event_menukelasActionPerformed

    private void menuguruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuguruActionPerformed
    desktop.removeAll();
    desktop.repaint();
    
    // 2. Buat objek dari JInternalFrame menu_siswa
    menu_guru mg = new menu_guru();
    
    // 3. Masukkan objek tersebut ke dalam JDesktopPane
    desktop.add(mg);
    
    // 4. Tampilkan formnya
    mg.setVisible(true);
    
    try {
        // 5. Buat form otomatis memenuhi seluruh area desktop (Full Screen di dalam panel)
        mg.setMaximum(true);
    } catch (java.beans.PropertyVetoException e) {
        // Jika gagal dimaksimalkan, tampilkan pesan error di console
        System.err.println("Gagal memaksimalkan Internal Frame: " + e.getMessage());
    }// Agar muncul di tengah la
    }//GEN-LAST:event_menuguruActionPerformed

    private void tranksaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tranksaksiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tranksaksiActionPerformed

    private void keluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_keluarActionPerformed
dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_keluarActionPerformed

    private void menusiswaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menusiswaActionPerformed
// 1. Bersihkan area desktop agar tidak ada form yang tumpang tindih
    desktop.removeAll();
    desktop.repaint();
    
    // 2. Buat objek dari JInternalFrame menu_siswa
    menu_siswa ms = new menu_siswa();
    
    // 3. Masukkan objek tersebut ke dalam JDesktopPane
    desktop.add(ms);
    
    // 4. Tampilkan formnya
    ms.setVisible(true);
    
    try {
        // 5. Buat form otomatis memenuhi seluruh area desktop (Full Screen di dalam panel)
        ms.setMaximum(true);
    } catch (java.beans.PropertyVetoException e) {
        // Jika gagal dimaksimalkan, tampilkan pesan error di console
        System.err.println("Gagal memaksimalkan Internal Frame: " + e.getMessage());
    }// Agar muncul di tengah layar        // TODO add your handling code here:
    }//GEN-LAST:event_menusiswaActionPerformed

    private void lbl_logoComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_lbl_logoComponentResized
 scaleImage();
    }//GEN-LAST:event_lbl_logoComponentResized

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
   
    }//GEN-LAST:event_formComponentResized

    private void desktopComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_desktopComponentResized
    for (JInternalFrame frame : desktop.getAllFrames()) {
        try {
            // Memaksa setiap frame untuk menyesuaikan diri ke ukuran maksimal desktop yang baru
            frame.setMaximum(true);
        } catch (PropertyVetoException e) {
            System.err.println("Gagal resize internal frame: " + e.getMessage());
        }
    }
    }//GEN-LAST:event_desktopComponentResized

    private void homeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_homeActionPerformed
    tampilkanHomeAwal();
    }//GEN-LAST:event_homeActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
        // Mengaktifkan tema modern FlatLaf
        com.formdev.flatlaf.FlatLightLaf.setup();
    } catch (Exception ex) {
        System.err.println("Gagal memuat tema FlatLaf");
    }
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new dashboardtu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane desktop;
    private javax.swing.JButton home;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton keluar;
    private javax.swing.JLabel lbl_logo;
    private javax.swing.JButton menuguru;
    private javax.swing.JButton menukelas;
    private javax.swing.JButton menusiswa;
    private javax.swing.JButton tranksaksi;
    // End of variables declaration//GEN-END:variables
}
