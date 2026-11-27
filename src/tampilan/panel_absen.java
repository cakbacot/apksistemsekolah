package tampilan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import koneksi.koneksi;

/**
 * Portal pemilihan layanan presensi akademik (Siswa & Guru).
 */
public class panel_absen extends javax.swing.JInternalFrame {

    Connection con = koneksi.getConnection();
    PreparedStatement pet;
    ResultSet rs;
    private JDesktopPane desktop;

    public panel_absen() {
        this(null);
    }

    public panel_absen(JDesktopPane desktopDariDashboard) {
        this.desktop = desktopDariDashboard;
        UITheme.setup();
        initComponents();
        applyModernEnhancements();
    }

    private void applyModernEnhancements() {
        UITheme.prepareInternalFrame(this);

        ImageIcon icSiswa = UITheme.getScaledIcon("/resource/murid.png", 60, 60);
        if (icSiswa != null) {
            absensiswa.setIcon(icSiswa);
        }

        ImageIcon icGuru = UITheme.getScaledIcon("/resource/logoguru.png", 60, 60);
        if (icGuru != null) {
            absenguru.setIcon(icGuru);
        }

        UITheme.makePortalCardInteractive(jpanel1, pnlBadgeSiswa, btnBukaSiswa, new Runnable() {
            @Override
            public void run() {
                bukaFormSiswa();
            }
        });

        UITheme.makePortalCardInteractive(jPanel2, pnlBadgeGuru, btnBukaGuru, new Runnable() {
            @Override
            public void run() {
                bukaFormGuru();
            }
        });
    }

    private void bukaFormSiswa() {
        if (desktop == null) {
            return;
        }
        desktop.removeAll();
        menu_absenSiswa ma = new menu_absenSiswa();
        UITheme.prepareInternalFrame(ma);
        desktop.add(ma);
        ma.setBounds(0, 0, desktop.getWidth(), desktop.getHeight());
        ma.setVisible(true);
        try {
            ma.setMaximum(true);
        } catch (java.beans.PropertyVetoException e) {
            System.err.println("Gagal memaksimalkan: " + e.getMessage());
        }
        desktop.revalidate();
        desktop.repaint();
    }

    private void bukaFormGuru() {
        if (desktop == null) {
            return;
        }
        desktop.removeAll();
        menu_absenGuru mg = new menu_absenGuru();
        UITheme.prepareInternalFrame(mg);
        desktop.add(mg);
        mg.setBounds(0, 0, desktop.getWidth(), desktop.getHeight());
        mg.setVisible(true);
        try {
            mg.setMaximum(true);
        } catch (java.beans.PropertyVetoException e) {
            System.err.println("Gagal memaksimalkan: " + e.getMessage());
        }
        desktop.revalidate();
        desktop.repaint();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlBackground = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblSubtitle = new javax.swing.JLabel();
        jpanel1 = new javax.swing.JPanel();
        pnlBadgeSiswa = new javax.swing.JPanel();
        absensiswa = new javax.swing.JLabel();
        lblTitleSiswa = new javax.swing.JLabel();
        lblDescSiswa = new javax.swing.JLabel();
        btnBukaSiswa = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        pnlBadgeGuru = new javax.swing.JPanel();
        absenguru = new javax.swing.JLabel();
        lblTitleGuru = new javax.swing.JLabel();
        lblDescGuru = new javax.swing.JLabel();
        btnBukaGuru = new javax.swing.JButton();

        setBorder(null);

        pnlBackground.setBackground(new java.awt.Color(241, 245, 249));

        jPanel3.setBackground(new java.awt.Color(241, 245, 249));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(15, 23, 42));
        jLabel1.setText("Pilih Layanan Presensi Akademik");

        lblSubtitle.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblSubtitle.setForeground(new java.awt.Color(100, 116, 139));
        lblSubtitle.setText("Silakan pilih jenis pencatatan kehadiran yang ingin Anda kelola");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblSubtitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(28, 28, 28))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel1)
                .addGap(6, 6, 6)
                .addComponent(lblSubtitle)
                .addGap(20, 20, 20))
        );

        jpanel1.setBackground(new java.awt.Color(255, 255, 255));
        jpanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(226, 232, 240), 1, true));

        pnlBadgeSiswa.setBackground(new java.awt.Color(239, 246, 255));

        absensiswa.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        absensiswa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/ui_sb_siswa.png"))); // NOI18N

        javax.swing.GroupLayout pnlBadgeSiswaLayout = new javax.swing.GroupLayout(pnlBadgeSiswa);
        pnlBadgeSiswa.setLayout(pnlBadgeSiswaLayout);
        pnlBadgeSiswaLayout.setHorizontalGroup(
            pnlBadgeSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(absensiswa, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
        );
        pnlBadgeSiswaLayout.setVerticalGroup(
            pnlBadgeSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(absensiswa, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
        );

        lblTitleSiswa.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblTitleSiswa.setForeground(new java.awt.Color(15, 23, 42));
        lblTitleSiswa.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitleSiswa.setText("Presensi Kehadiran Siswa");

        lblDescSiswa.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblDescSiswa.setForeground(new java.awt.Color(100, 116, 139));
        lblDescSiswa.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDescSiswa.setText("<html><div style='text-align:center;'>Kelola pencatatan kehadiran siswa per kelas,<br>pertemuan tatap muka, dan cetak rekap presensi kelas.</div></html>");

        btnBukaSiswa.setBackground(new java.awt.Color(37, 99, 235));
        btnBukaSiswa.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnBukaSiswa.setForeground(new java.awt.Color(255, 255, 255));
        btnBukaSiswa.setText("Buka Presensi Siswa →");
        btnBukaSiswa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBukaSiswa.setFocusPainted(false);
        btnBukaSiswa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBukaSiswaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpanel1Layout = new javax.swing.GroupLayout(jpanel1);
        jpanel1.setLayout(jpanel1Layout);
        jpanel1Layout.setHorizontalGroup(
            jpanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jpanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(pnlBadgeSiswa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTitleSiswa, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
                    .addComponent(lblDescSiswa, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
                    .addComponent(btnBukaSiswa, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE))
                .addGap(32, 32, 32))
        );
        jpanel1Layout.setVerticalGroup(
            jpanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanel1Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(pnlBadgeSiswa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(lblTitleSiswa, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(lblDescSiswa, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(btnBukaSiswa, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(226, 232, 240), 1, true));

        pnlBadgeGuru.setBackground(new java.awt.Color(239, 246, 255));

        absenguru.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        absenguru.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/ui_guru_56.png"))); // NOI18N

        javax.swing.GroupLayout pnlBadgeGuruLayout = new javax.swing.GroupLayout(pnlBadgeGuru);
        pnlBadgeGuru.setLayout(pnlBadgeGuruLayout);
        pnlBadgeGuruLayout.setHorizontalGroup(
            pnlBadgeGuruLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(absenguru, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
        );
        pnlBadgeGuruLayout.setVerticalGroup(
            pnlBadgeGuruLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(absenguru, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
        );

        lblTitleGuru.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblTitleGuru.setForeground(new java.awt.Color(15, 23, 42));
        lblTitleGuru.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitleGuru.setText("Presensi Kehadiran Guru");

        lblDescGuru.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblDescGuru.setForeground(new java.awt.Color(100, 116, 139));
        lblDescGuru.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDescGuru.setText("<html><div style='text-align:center;'>Catat kehadiran harian guru pengajar, keterangan izin/tugas,<br>dan cetak laporan rekapitulasi kehadiran guru.</div></html>");

        btnBukaGuru.setBackground(new java.awt.Color(37, 99, 235));
        btnBukaGuru.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnBukaGuru.setForeground(new java.awt.Color(255, 255, 255));
        btnBukaGuru.setText("Buka Presensi Guru →");
        btnBukaGuru.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBukaGuru.setFocusPainted(false);
        btnBukaGuru.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBukaGuruActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(pnlBadgeGuru, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTitleGuru, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
                    .addComponent(lblDescGuru, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
                    .addComponent(btnBukaGuru, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE))
                .addGap(32, 32, 32))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(pnlBadgeGuru, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(lblTitleGuru, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(lblDescGuru, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(btnBukaGuru, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
        );

        javax.swing.GroupLayout pnlBackgroundLayout = new javax.swing.GroupLayout(pnlBackground);
        pnlBackground.setLayout(pnlBackgroundLayout);
        pnlBackgroundLayout.setHorizontalGroup(
            pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlBackgroundLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jpanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(24, 24, 24)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(28, 28, 28))
        );
        pnlBackgroundLayout.setVerticalGroup(
            pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBackgroundLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addGroup(pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jpanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(40, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlBackground, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlBackground, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBukaSiswaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBukaSiswaActionPerformed
        bukaFormSiswa();
    }//GEN-LAST:event_btnBukaSiswaActionPerformed

    private void btnBukaGuruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBukaGuruActionPerformed
        bukaFormGuru();
    }//GEN-LAST:event_btnBukaGuruActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel absenguru;
    private javax.swing.JLabel absensiswa;
    private javax.swing.JButton btnBukaGuru;
    private javax.swing.JButton btnBukaSiswa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jpanel1;
    private javax.swing.JLabel lblDescGuru;
    private javax.swing.JLabel lblDescSiswa;
    private javax.swing.JLabel lblSubtitle;
    private javax.swing.JLabel lblTitleGuru;
    private javax.swing.JLabel lblTitleSiswa;
    private javax.swing.JPanel pnlBackground;
    private javax.swing.JPanel pnlBadgeGuru;
    private javax.swing.JPanel pnlBadgeSiswa;
    // End of variables declaration//GEN-END:variables
}
