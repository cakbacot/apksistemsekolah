/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tampilan;
import java.awt.Image;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import koneksi.koneksi;

/**
 *
 * @author User
 */
public class panel_absen extends javax.swing.JInternalFrame {
    
    Connection con=koneksi.getConnection();
    PreparedStatement pet;
    ResultSet rs;
    private javax.swing.JDesktopPane desktop;
    /**
     * Creates new form dashboardRunning
     */
    public panel_absen(javax.swing.JDesktopPane desktopDariDashboard) {
       
       this.desktop = desktopDariDashboard;
        
        initComponents(); 
        
        // Sisa kode kamu yang lain tetap sama...
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                scaleImage();
                scaleImage1();
            }
        });
        aktifkanHoverWarnaPanel();
    }
    
    private void scaleImage() {
    try {
        // Alamat logo kamu
        ImageIcon iconAwal = new ImageIcon(getClass().getResource("/resource/logoguru.png"));
        Image imgLama = iconAwal.getImage();
        
        // Mengambil ukuran lbl_logo saat ini
        int width = absenguru.getWidth();
        int height = absenguru.getHeight();
        
        if (width > 0 && height > 0) {
            Image imgBaru = imgLama.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            absenguru.setIcon(new ImageIcon(imgBaru));
        }
    } catch (Exception e) {
        System.err.println("Error logo: " + e.getMessage());
    }
}
    
    private void scaleImage1() {
    try {
        // Alamat logo kamu
        ImageIcon iconAwal = new ImageIcon(getClass().getResource("/resource/murid.png"));
        Image imgLama = iconAwal.getImage();
        
        // Mengambil ukuran lbl_logo saat ini
        int width = absensiswa.getWidth();
        int height = absensiswa.getHeight();
        
        if (width > 0 && height > 0) {
            Image imgBaru = imgLama.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            absensiswa.setIcon(new ImageIcon(imgBaru));
        }
    } catch (Exception e) {
        System.err.println("Error logo: " + e.getMessage());
    }
}
    private void bukaFormSiswa() {
        desktop.removeAll();
        menu_absenSiswa ma = new menu_absenSiswa();
        desktop.add(ma);
        ma.setVisible(true);
        try {
            ma.setMaximum(true);
        } catch (java.beans.PropertyVetoException e) {
            System.err.println("Gagal memaksimalkan: " + e.getMessage());
        }
        desktop.repaint(); // Menyegarkan tampilan desktop pane
    }
    
    private void bukaFormGuru() {
        desktop.removeAll();
        // Ganti 'menu_absenGuru' sesuai dengan nama class JInternalFrame Guru Anda
        menu_absenGuru mg = new menu_absenGuru(); 
        desktop.add(mg);
        mg.setVisible(true);
        try {
            mg.setMaximum(true);
        } catch (java.beans.PropertyVetoException e) {
            System.err.println("Gagal memaksimalkan: " + e.getMessage());
        }
        desktop.repaint(); // Menyegarkan tampilan desktop pane
    }
    
    private void tampilkanGambar() {
        try {
            // Memasang gambar secara manual dari folder resource
            absensiswa.setIcon(new ImageIcon(getClass().getResource("/resource/user.png")));
            absenguru.setIcon(new ImageIcon(getClass().getResource("/resource/logoguru.png")));
        } catch (Exception e) {
            System.err.println("Gambar tidak ditemukan! Pastikan folder resource sudah benar.");
        }
    }
    
  // --- FITUR HOVER: UBAH WARNA BACKGROUND PANEL ---
    private void aktifkanHoverWarnaPanel() {
        // 1. Warna untuk PANEL USER (jPanel1)
        java.awt.Color warnaAsliSiswa = new java.awt.Color(0, 153, 204);     // Biru Terang Asli
        java.awt.Color warnaHoverSiswa = new java.awt.Color(0, 50, 135); // Biru Lebih Muda (Hover)

        jpanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jpanel1.setBackground(warnaHoverSiswa);
                jpanel1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jpanel1.setBackground(warnaAsliSiswa);
            }

        });

        // 2. Warna untuk PANEL GURU (jPanel2) - Tetap normal seperti sebelumnya
        java.awt.Color warnaAsliGuru = new java.awt.Color(0, 102, 204);     // Biru Gelap Asli
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
        absenguru = new javax.swing.JLabel();
        jpanel1 = new javax.swing.JPanel();
        absensiswa = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        jPanel2.setBackground(new java.awt.Color(0, 8, 97));
        jPanel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel2MouseClicked(evt);
            }
        });

        absenguru.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                absenguruMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                absenguruMouseEntered(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(absenguru, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(41, 41, 41))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(absenguru, javax.swing.GroupLayout.DEFAULT_SIZE, 476, Short.MAX_VALUE)
                .addGap(94, 94, 94))
        );

        jpanel1.setBackground(new java.awt.Color(0, 0, 255));
        jpanel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jpanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jpanel1MouseClicked(evt);
            }
        });

        absensiswa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                absensiswaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                absensiswaMouseEntered(evt);
            }
        });

        javax.swing.GroupLayout jpanel1Layout = new javax.swing.GroupLayout(jpanel1);
        jpanel1.setLayout(jpanel1Layout);
        jpanel1Layout.setHorizontalGroup(
            jpanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpanel1Layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(absensiswa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(41, 41, 41))
        );
        jpanel1Layout.setVerticalGroup(
            jpanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanel1Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(absensiswa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(94, 94, 94))
        );

        jPanel3.setBackground(new java.awt.Color(0, 8, 97));
        jPanel3.setForeground(new java.awt.Color(0, 8, 97));

        jLabel1.setFont(new java.awt.Font("Rockwell Extra Bold", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("MENU ABSENSI");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(253, 253, 253)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 750, Short.MAX_VALUE)
                .addGap(271, 271, 271))
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
                .addComponent(jpanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(154, 154, 154)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(95, 95, 95))
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(39, 39, 39))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void absensiswaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_absensiswaMouseClicked
          bukaFormSiswa();      
    }//GEN-LAST:event_absensiswaMouseClicked

    private void jpanel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jpanel1MouseClicked
        bukaFormSiswa();
    }//GEN-LAST:event_jpanel1MouseClicked

    private void absenguruMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_absenguruMouseClicked
        bukaFormGuru();
    }//GEN-LAST:event_absenguruMouseClicked

    private void jPanel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseClicked
        bukaFormGuru();
    }//GEN-LAST:event_jPanel2MouseClicked

    private void absensiswaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_absensiswaMouseEntered
       java.awt.Color warnaAsliSiswa = new java.awt.Color(0, 153, 204);    // Biru Terang Asli
        java.awt.Color warnaHoverSiswa = new java.awt.Color(0, 50, 135); // Biru Lebih Muda (Hover)
        jpanel1.setBackground(warnaHoverSiswa);
    }//GEN-LAST:event_absensiswaMouseEntered

    private void absenguruMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_absenguruMouseEntered
    java.awt.Color warnaAsliGuru = new java.awt.Color(0, 102, 204);     
        java.awt.Color warnaHoverGuru = new java.awt.Color(0, 20, 150);  
        jPanel2.setBackground(warnaHoverGuru);
    }//GEN-LAST:event_absenguruMouseEntered

//public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(panel_absen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(panel_absen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(panel_absen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(panel_absen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//    try {
//        com.formdev.flatlaf.FlatLightLaf.setup();
//    } catch (Exception ex) {
//    System.err.println("Gagal memuat tema FlatLaf");
//}
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new panel_absen().setVisible(true);
//            }
//        });
//    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel absenguru;
    private javax.swing.JLabel absensiswa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jpanel1;
    // End of variables declaration//GEN-END:variables
}
