/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package running;

import java.awt.Image;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import tampilan.loginguru;
import tampilan.loginuser;
import koneksi.koneksi;

/**
 *
 * @author User
 */
public class dashboardRunning extends javax.swing.JFrame {
    
    Connection con=koneksi.getConnection();
    PreparedStatement pet;
    ResultSet rs;
    
    /**
     * Creates new form dashboardRunning
     */
    public dashboardRunning() {
       
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
        java.awt.Color warnaAsliUser = new java.awt.Color(0, 153, 204);    // Biru Terang Asli
        java.awt.Color warnaHoverUser = new java.awt.Color(0, 50, 135); // Biru Lebih Muda (Hover)
        

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
        java.awt.Color warnaAsliGuru = new java.awt.Color(0, 102, 204);     
        java.awt.Color warnaHoverGuru = new java.awt.Color(0, 20, 150);  

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


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        loginuser = new javax.swing.JLabel();
        label1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        loginguru = new javax.swing.JLabel();
        label2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        label = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 153, 204));

        loginuser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                loginuserMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginuserMouseEntered(evt);
            }
        });

        label1.setFont(new java.awt.Font("Rockwell Extra Bold", 1, 22)); // NOI18N
        label1.setForeground(new java.awt.Color(255, 255, 255));
        label1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label1.setText("USER");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(loginuser, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
                .addGap(41, 41, 41))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(loginuser, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
                .addGap(41, 41, 41)
                .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(0, 102, 204));

        loginguru.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                loginguruMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginguruMouseEntered(evt);
            }
        });

        label2.setFont(new java.awt.Font("Rockwell Extra Bold", 1, 22)); // NOI18N
        label2.setForeground(new java.awt.Color(255, 255, 255));
        label2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label2.setText("GURU");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(loginguru, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
                .addGap(41, 41, 41))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(loginguru, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
                .addGap(41, 41, 41)
                .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(0, 8, 97));

        label.setFont(new java.awt.Font("Rockwell Extra Bold", 1, 22)); // NOI18N
        label.setForeground(new java.awt.Color(255, 255, 255));
        label.setText("Login");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(335, 335, 335)
                .addComponent(label)
                .addContainerGap(348, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(label, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(106, 106, 106)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(71, 71, 71))
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(57, 57, 57))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void loginuserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginuserMouseClicked
        tampilan.loginuser lu = new tampilan.loginuser(); 
    
    // 2. Tampilkan FormB ke layar
    loginuser.setVisible(true);
    
    // 3. Tutup FormA saat ini (menghapus dari memori)
    this.dispose(); 
    }//GEN-LAST:event_loginuserMouseClicked

    private void loginguruMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginguruMouseClicked
        tampilan.loginguru lg = new tampilan.loginguru(); 
    
    // 2. Tampilkan FormB ke layar
    loginuser.setVisible(true);
    
    // 3. Tutup FormA saat ini (menghapus dari memori)
    this.dispose(); 
    }//GEN-LAST:event_loginguruMouseClicked

    private void loginuserMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginuserMouseEntered
        java.awt.Color warnaAsliUser = new java.awt.Color(0, 153, 204);    // Biru Terang Asli
        java.awt.Color warnaHoverUser = new java.awt.Color(0, 50, 135); // Biru Lebih Muda (Hover)
        jPanel1.setBackground(warnaHoverUser);
    }//GEN-LAST:event_loginuserMouseEntered

    private void loginguruMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginguruMouseEntered
        java.awt.Color warnaAsliGuru = new java.awt.Color(0, 102, 204);     
        java.awt.Color warnaHoverGuru = new java.awt.Color(0, 20, 150);  
        jPanel2.setBackground(warnaHoverGuru);
    }//GEN-LAST:event_loginguruMouseEntered

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(dashboardRunning.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(dashboardRunning.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(dashboardRunning.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(dashboardRunning.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new dashboardRunning().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel label;
    private javax.swing.JLabel label1;
    private javax.swing.JLabel label2;
    private javax.swing.JLabel loginguru;
    private javax.swing.JLabel loginuser;
    // End of variables declaration//GEN-END:variables
}
