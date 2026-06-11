package tampilan;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Image;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import koneksi.koneksi;

/**
 *
 * @author User
 */
public class panel_absen1 extends javax.swing.JFrame {
    
    Connection con=koneksi.getConnection();
    PreparedStatement pet;
    ResultSet rs;
    
    /**
     * Creates new form dashboardRunning
     */
    public panel_absen1() {
       
        initComponents(); 
        setExtendedState(JFrame.MAXIMIZED_HORIZ);
        setVisible(true);
        setResizable(false);
        tampilkanGambar();
        // Jalankan fungsi efek warna hover panel
        aktifkanHoverWarnaPanel();
        scaleImage();
        scaleImage1();
    }
    
    private void scaleImage() {
    try {
        // Alamat logo kamu
        ImageIcon iconAwal = new ImageIcon(getClass().getResource("/resource/logoguru.png"));
        Image imgLama = iconAwal.getImage();
        
        // Mengambil ukuran lbl_logo saat ini
        int width = loginguru.getWidth();
        int height = loginguru.getHeight();
        
        if (width > 0 && height > 0) {
            Image imgBaru = imgLama.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            loginguru.setIcon(new ImageIcon(imgBaru));
        }
    } catch (Exception e) {
        System.err.println("Error logo: " + e.getMessage());
    }
}
    
    private void scaleImage1() {
    try {
        // Alamat logo kamu
        ImageIcon iconAwal = new ImageIcon(getClass().getResource("/resource/user.png"));
        Image imgLama = iconAwal.getImage();
        
        // Mengambil ukuran lbl_logo saat ini
        int width = loginuser.getWidth();
        int height = loginuser.getHeight();
        
        if (width > 0 && height > 0) {
            Image imgBaru = imgLama.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            loginuser.setIcon(new ImageIcon(imgBaru));
        }
    } catch (Exception e) {
        System.err.println("Error logo: " + e.getMessage());
    }
}
    
    private void tampilkanGambar() {
        try {
            // Memasang gambar secara manual dari folder resource
            loginuser.setIcon(new ImageIcon(getClass().getResource("/resource/user.png")));
            loginguru.setIcon(new ImageIcon(getClass().getResource("/resource/logoguru.png")));
        } catch (Exception e) {
            System.err.println("Gambar tidak ditemukan! Pastikan folder resource sudah benar.");
        }
    }
    
  // --- FITUR HOVER: UBAH WARNA BACKGROUND PANEL ---
    private void aktifkanHoverWarnaPanel() {
        // 1. Warna untuk PANEL USER (jPanel1)
        java.awt.Color warnaAsliUser = new java.awt.Color(0, 0, 255);    // Biru Terang Asli
        java.awt.Color warnaHoverUser = new java.awt.Color(0, 10, 135); // Biru Lebih Muda (Hover)
        java.awt.Color warnaKlikUser = new java.awt.Color(0, 0, 150);    // Biru Gelap (Saat Diklik)

        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel1.setBackground(warnaHoverUser);
                jPanel1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel1.setBackground(warnaAsliUser);
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                // Ketika tombol mouse ditekan/diklik, panel berubah menjadi gelap
                jPanel1.setBackground(warnaKlikUser);
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                // Ketika klik dilepas, kembali ke warna hover jika mouse masih di dalam panel
                if (jPanel1.getBounds().contains(evt.getPoint())) {
                    jPanel1.setBackground(warnaHoverUser);
                } else {
                    jPanel1.setBackground(warnaAsliUser);
                }
            }
        });

        // 2. Warna untuk PANEL GURU (jPanel2) - Tetap normal seperti sebelumnya
        java.awt.Color warnaAsliGuru = new java.awt.Color(0, 8, 97);     // Biru Gelap Asli
        java.awt.Color warnaHoverGuru = new java.awt.Color(0, 20, 150);  // Biru Agak Terang Saat Disentuh

        jPanel2.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel2.setBackground(warnaHoverGuru);
                jPanel2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel2.setBackground(warnaAsliGuru);
            }
        });
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        loginguru = new javax.swing.JLabel();
        loginsiswa = new javax.swing.JPanel();
        menu_absenGuru = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        jPanel2.setBackground(new java.awt.Color(0, 8, 97));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(51, Short.MAX_VALUE)
                .addComponent(loginguru, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(loginguru, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(94, Short.MAX_VALUE))
        );

        loginsiswa.setBackground(new java.awt.Color(0, 0, 255));

        javax.swing.GroupLayout loginsiswaLayout = new javax.swing.GroupLayout(loginsiswa);
        loginsiswa.setLayout(loginsiswaLayout);
        loginsiswaLayout.setHorizontalGroup(
            loginsiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, loginsiswaLayout.createSequentialGroup()
                .addContainerGap(51, Short.MAX_VALUE)
                .addComponent(menu_absenGuru, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41))
        );
        loginsiswaLayout.setVerticalGroup(
            loginsiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loginsiswaLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(menu_absenGuru, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(94, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(0, 8, 97));
        jPanel3.setForeground(new java.awt.Color(0, 8, 97));

        jLabel1.setFont(new java.awt.Font("Rockwell Extra Bold", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("MENU ABSENSI");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(253, 253, 253)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel1)
                .addContainerGap(55, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addComponent(loginsiswa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 154, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(95, 95, 95))
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(loginsiswa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(loginguru.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(loginguru.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(loginguru.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(loginguru.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
    try {
        com.formdev.flatlaf.FlatLightLaf.setup();
    } catch (Exception ex) {
    System.err.println("Gagal memuat tema FlatLaf");
}
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new panel_absen().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel loginguru;
    private javax.swing.JPanel loginsiswa;
    private javax.swing.JLabel menu_absenGuru;
    // End of variables declaration//GEN-END:variables
}
