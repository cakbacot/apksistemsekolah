/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tampilan;
import java.awt.Image;
import java.beans.PropertyVetoException;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import koneksi.koneksi;
/**
 *
 * @author User
 */
public class dashboardguru extends javax.swing.JFrame {

    /**
     * Creates new form dashboardguru
     */
    public dashboardguru() {
        initComponents();
        String kelas = GuruSession.getKelas();
        System.out.println(kelas);
        tampilkanHomeAwal();
        this.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        lab_login.setText(("SELAMAT DATANG ")+GuruSession.getSapaan()+ (", ") + GuruSession.getNama());
        setFixedIcon(babsen,"/resource/absen.png");
        sethome(home,"/resource/home.png");
        setFixedIcon(bnilai,"/resource/nilai.png");
        setleave(logout,"/resource/keluar.png");
    }
    
    public void tampilkanHomeAwal() {
    try {
        // Hapus isi desktop jika ada sisa frame lain
        desktop.removeAll();
        
        homedashboardguru h = new homedashboardguru();
        
        // Setting tampilan (tanpa border & bar judul)
        h.setBorder(null);
        ((javax.swing.plaf.basic.BasicInternalFrameUI)h.getUI()).setNorthPane(null);
        
        desktop.add(h);
        
        // Ambil ukuran desktop yang sudah di-render oleh sistem
        h.setSize(desktop.getWidth(), desktop.getHeight());
        h.setVisible(true);
        
        // Panggil diagram (pastikan method ini public di homedashboardtu)
        h.tampilkanDiagramSiswa();
        h.tampilkanDiagramAbsensi();
        
        desktop.repaint();
        desktop.revalidate();
    } catch (Exception e) {
        System.err.println("Gagal memuat home awal: " + e.getMessage());
    }
}

    
   private void setFixedIcon(javax.swing.JButton btn, String path) {
    try {
        ImageIcon iconAwal = new ImageIcon(getClass().getResource(path));
        Image imgBaru = iconAwal.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        btn.setIcon(new ImageIcon(imgBaru));

        // Styling Dasar
        btn.setForeground(java.awt.Color.WHITE);
        btn.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setOpaque(false);
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        
        btn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
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
   private void sethome(javax.swing.JButton btn, String path) {
    try {
        ImageIcon iconAwal = new ImageIcon(getClass().getResource(path));
        Image imgBaru = iconAwal.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        btn.setIcon(new ImageIcon(imgBaru));

        // Styling Dasar
        btn.setForeground(java.awt.Color.WHITE);
        btn.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setOpaque(false);
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        
        btn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn.setMargin(new java.awt.Insets(10, 10, 10, 10));
        btn.setIconTextGap(10);

        // --- CARA MANUAL: MOUSE LISTENER UNTUK HOVER KUNING ---
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                // Saat mouse masuk: Background jadi kuning, teks jadi hitam agar kontras
                btn.setContentAreaFilled(true);
                btn.setBackground(new java.awt.Color(255, 255, 255)); // Putih Terang
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
   
   private void setleave(javax.swing.JButton btn, String path) {
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
                btn.setBackground(new java.awt.Color(255, 0, 0)); //Merah Terang
                btn.setForeground(java.awt.Color.WHITE); 
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
// Method satu untuk semua JLabel dan Gambar
public void scaleImage(javax.swing.JComponent komponenTarget, String pathGambar) {
    try {
        java.net.URL imgURL = getClass().getResource(pathGambar);
        
        if (imgURL == null) {
            System.err.println("Gambar TIDAK ditemukan di path: " + pathGambar);
            return;
        }
        
        javax.swing.ImageIcon icon = new javax.swing.ImageIcon(imgURL);
        java.awt.Image img = icon.getImage();
        
        // Mengambil ukuran dari komponen (bisa JButton / JLabel) dengan jarak aman -6 pixel
        int width = komponenTarget.getWidth() > 0 ? (komponenTarget.getWidth() - 6) : 100;
        int height = komponenTarget.getHeight() > 0 ? (komponenTarget.getHeight() - 6) : 100;
        
        java.awt.Image imgScale = img.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        javax.swing.ImageIcon scaledIcon = new javax.swing.ImageIcon(imgScale);
        
        // CEK NYATA: Apakah komponennya berupa JLabel atau JButton?
        if (komponenTarget instanceof javax.swing.JLabel) {
            ((javax.swing.JLabel) komponenTarget).setIcon(scaledIcon);
        } else if (komponenTarget instanceof javax.swing.JButton) {
            ((javax.swing.JButton) komponenTarget).setIcon(scaledIcon);
        }
        
    } catch (Exception e) {
        System.out.println("Error scale image: " + e.getMessage());
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
        babsen = new javax.swing.JButton();
        bnilai = new javax.swing.JButton();
        logout = new javax.swing.JButton();
        desktop = new javax.swing.JDesktopPane();
        jPanel2 = new javax.swing.JPanel();
        lab_login = new javax.swing.JLabel();
        logo = new javax.swing.JLabel();
        home = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 8, 97));

        jPanel1.setBackground(new java.awt.Color(0, 8, 97));

        babsen.setText("absen");
        babsen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                babsenActionPerformed(evt);
            }
        });

        bnilai.setText("nilai");
        bnilai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bnilaiActionPerformed(evt);
            }
        });

        logout.setForeground(new java.awt.Color(255, 0, 0));
        logout.setText("Log out");
        logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(babsen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bnilai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(logout, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(babsen, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(bnilai, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 365, Short.MAX_VALUE)
                .addComponent(logout)
                .addGap(25, 25, 25))
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
            .addGap(0, 0, Short.MAX_VALUE)
        );
        desktopLayout.setVerticalGroup(
            desktopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel2.setBackground(new java.awt.Color(0, 8, 97));

        lab_login.setFont(new java.awt.Font("Rockwell Extra Bold", 1, 22)); // NOI18N
        lab_login.setForeground(new java.awt.Color(255, 255, 255));

        logo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/logo.png"))); // NOI18N
        logo.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                logoComponentResized(evt);
            }
        });

        home.setText("home");
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
                .addGap(19, 19, 19)
                .addComponent(logo, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(75, 75, 75)
                .addComponent(lab_login, javax.swing.GroupLayout.DEFAULT_SIZE, 786, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(home, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(logo, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(home, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lab_login, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(desktop)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void babsenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_babsenActionPerformed
        // 1. Bersihkan area desktop agar tidak ada form yang tumpang tindih
    desktop.removeAll();
    
    // 2. Buat objek internal frame baru
    panel_absen pa = new panel_absen(desktop);
    
    // 3. Hilangkan border/dekorasi bawaan internal frame jika ingin menyatu mulus dengan dashboard
    // fa.setBorder(null); // Buka komen ini jika ingin menghilangkan border luar JInternalFrame
    
    // 4. Masukkan ke desktop
    desktop.add(pa);
    pa.setVisible(true);
    
    // 5. Set ukuran mengikuti panel penampung agar pas
//    ma.setSize(desktop.getWidth(), desktop.getHeight());
    try {
        // 5. Buat form otomatis memenuhi seluruh area desktop (Full Screen di dalam panel)
        pa.setMaximum(true);
    } catch (java.beans.PropertyVetoException e) {
        // Jika gagal dimaksimalkan, tampilkan pesan error di console
        System.err.println("Gagal memaksimalkan Internal Frame: " + e.getMessage());
    }// Agar muncul di tengah layar       
    }//GEN-LAST:event_babsenActionPerformed

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

    private void logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutActionPerformed
        // 1. Tampilkan konfirmasi (Opsional tapi disarankan)
    int pilih = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin ingin logout?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
    
    if (pilih == JOptionPane.YES_OPTION) {
        // 2. Bersihkan Session (Penting!)
        UserSession.setNama(null);
        
        // 3. Tutup Dashboard
        this.dispose(); 
        
        // 4. Buka kembali form login
        new loginguru().setVisible(true);
    }// TODO add your handling code here:
    }//GEN-LAST:event_logoutActionPerformed

    private void logoComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_logoComponentResized
        scaleImage(logo, "/resource/logo.png");
    }//GEN-LAST:event_logoComponentResized

    private void homeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_homeActionPerformed
        tampilkanHomeAwal();
    }//GEN-LAST:event_homeActionPerformed

    private void bnilaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bnilaiActionPerformed
        // 1. Bersihkan area desktop agar tidak ada form yang tumpang tindih
    desktop.removeAll();
    
    // 2. Buat objek internal frame baru
    menu_nilai mn = new menu_nilai();
    
    // 3. Hilangkan border/dekorasi bawaan internal frame jika ingin menyatu mulus dengan dashboard
    // fa.setBorder(null); // Buka komen ini jika ingin menghilangkan border luar JInternalFrame
    
    // 4. Masukkan ke desktop
    desktop.add(mn);
    mn.setVisible(true);
    
    // 5. Set ukuran mengikuti panel penampung agar pas
//    ma.setSize(desktop.getWidth(), desktop.getHeight());
    try {
        // 5. Buat form otomatis memenuhi seluruh area desktop (Full Screen di dalam panel)
        mn.setMaximum(true);
    } catch (java.beans.PropertyVetoException e) {
        // Jika gagal dimaksimalkan, tampilkan pesan error di console
        System.err.println("Gagal memaksimalkan Internal Frame: " + e.getMessage());
    }// Agar muncul di tengah layar
    }//GEN-LAST:event_bnilaiActionPerformed

  public static void main(String args[]) {
    try {
        // Mengaktifkan tema modern FlatLaf
        com.formdev.flatlaf.FlatLightLaf.setup();
    } catch (Exception ex) {
        System.err.println("Gagal memuat tema FlatLaf");
    }

    // Kode bawaan NetBeans untuk memunculkan form
    java.awt.EventQueue.invokeLater(new Runnable() {
        public void run() {
            new dashboardguru().setVisible(true);
//            new loginguru().setVisible(true);
        }
    });
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton babsen;
    private javax.swing.JButton bnilai;
    private javax.swing.JDesktopPane desktop;
    private javax.swing.JButton home;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lab_login;
    private javax.swing.JLabel logo;
    private javax.swing.JButton logout;
    // End of variables declaration//GEN-END:variables
}
