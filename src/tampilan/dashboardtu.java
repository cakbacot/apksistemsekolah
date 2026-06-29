/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tampilan;

import java.awt.Image;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import javax.swing.JOptionPane;
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
       
       com.formdev.flatlaf.FlatLightLaf.setup();
    homedashboardtu h = new homedashboardtu();
    desktop.add(h); 
    
    h.setBorder(null); 
    ((javax.swing.plaf.basic.BasicInternalFrameUI)h.getUI()).setNorthPane(null); 
    
    try {
        h.setMaximum(true); 
    } catch (java.beans.PropertyVetoException e) {}
    
    h.setVisible(true);
    h.tampilkanDiagramSiswa();
    h.tampilkanDiagramGuru();
    
    setFixedIcon(menukelas, "/resource/class.png");
    setFixedIcon(menuguru, "/resource/guru.png");
    setFixedIcon(menusiswa, "/resource/murid.png");
    setFixedIcon(tranksaksi, "/resource/tranksaksi.png");
    setFixedIcon(jdwl,"/resource/jadwal.png");
    setFixedIcon(absenuser,"/resource/user.png");
    setFixedIcon(absenguru,"/resource/logoguru.png");
    sethome(home,"/resource/home.png");
    setleave(keluar,"/resource/keluar.png");
    setadmin(admin,"/resource/admin.png");
    lblWelcome.setText("Selamat Datang, " + UserSession.getNama());
    validasiMenu();
    tampilkanHomeAwal();
    }

    
    public void validasiMenu() {
    String role = UserSession.getLevel(); 
    
    if (role.equals("2")) { 
        menukelas.setEnabled(false);
        menusiswa.setEnabled(false);
        menuguru.setEnabled(false);
        jdwl.setEnabled(false);
        admin.setEnabled(false);
    } else if (role.equals("3")) {
        tranksaksi.setEnabled(false);
        admin.setEnabled(false); 
    }
}
    
private void scaleImage() {
    try {
        ImageIcon iconAwal = new ImageIcon(getClass().getResource("/resource/logo.png"));
        Image imgLama = iconAwal.getImage();
        
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
        desktop.removeAll();
        
        homedashboardtu h = new homedashboardtu();
        h.setBorder(null);
        ((javax.swing.plaf.basic.BasicInternalFrameUI)h.getUI()).setNorthPane(null);
        
        desktop.add(h);
        
        h.setSize(desktop.getWidth(), desktop.getHeight());
        h.setVisible(true);
        
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

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setContentAreaFilled(true);
                btn.setBackground(new java.awt.Color(255, 255, 0)); 
                btn.setForeground(java.awt.Color.BLACK); 
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
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

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setContentAreaFilled(true);
                btn.setBackground(new java.awt.Color(255, 0, 0)); 
                btn.setForeground(java.awt.Color.WHITE); 
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
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
        Image imgBaru = iconAwal.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        btn.setIcon(new ImageIcon(imgBaru));

        btn.setForeground(java.awt.Color.WHITE);
        btn.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setOpaque(false);
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        
        btn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn.setMargin(new java.awt.Insets(10, 10, 10, 10));
        btn.setIconTextGap(20);

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setContentAreaFilled(true);
                btn.setBackground(new java.awt.Color(255, 255, 255)); 
                btn.setForeground(java.awt.Color.BLACK); 
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setContentAreaFilled(false);
                btn.setForeground(java.awt.Color.WHITE);
            }
        });

    } catch (Exception e) {
        System.err.println("Gagal set hover: " + e.getMessage());
    }
}


private void setadmin(javax.swing.JButton btn, String path) {
    try {
        ImageIcon iconAwal = new ImageIcon(getClass().getResource(path));
        Image imgBaru = iconAwal.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        btn.setIcon(new ImageIcon(imgBaru));

        btn.setForeground(java.awt.Color.WHITE);
        btn.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setOpaque(false);
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        
        btn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn.setMargin(new java.awt.Insets(10, 10, 10, 10));
        btn.setIconTextGap(20);

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setContentAreaFilled(true);
                btn.setBackground(new java.awt.Color(0, 255, 98)); 
                btn.setForeground(java.awt.Color.WHITE); 
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
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
        admin = new javax.swing.JButton();
        lblWelcome = new javax.swing.JLabel();
        desktop = new javax.swing.JDesktopPane();
        jPanel1 = new javax.swing.JPanel();
        menusiswa = new javax.swing.JButton();
        menukelas = new javax.swing.JButton();
        menuguru = new javax.swing.JButton();
        tranksaksi = new javax.swing.JButton();
        keluar = new javax.swing.JButton();
        jdwl = new javax.swing.JButton();
        absenguru = new javax.swing.JButton();
        absenuser = new javax.swing.JButton();

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
        home.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                homeActionPerformed(evt);
            }
        });

        admin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminActionPerformed(evt);
            }
        });

        lblWelcome.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        lblWelcome.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_logo, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(77, 77, 77)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 683, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(lblWelcome, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(admin, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addComponent(home, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_logo, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(home, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(admin, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lblWelcome, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
            .addGap(0, 449, Short.MAX_VALUE)
        );

        jPanel1.setBackground(new java.awt.Color(0, 8, 97));
        jPanel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        menusiswa.setFont(new java.awt.Font("Segoe UI Symbol", 1, 14)); // NOI18N
        menusiswa.setForeground(new java.awt.Color(255, 255, 255));
        menusiswa.setText("Siswa");
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

        jdwl.setFont(new java.awt.Font("Segoe UI Symbol", 1, 14)); // NOI18N
        jdwl.setForeground(new java.awt.Color(255, 255, 255));
        jdwl.setText("jadwal");
        jdwl.setToolTipText("");
        jdwl.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jdwl.setFocusPainted(false);
        jdwl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jdwlActionPerformed(evt);
            }
        });

        absenguru.setFont(new java.awt.Font("Segoe UI Symbol", 1, 14)); // NOI18N
        absenguru.setForeground(new java.awt.Color(255, 255, 255));
        absenguru.setText("Absen Guru");
        absenguru.setToolTipText("");
        absenguru.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        absenguru.setFocusPainted(false);
        absenguru.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                absenguruMouseClicked(evt);
            }
        });
        absenguru.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                absenguruActionPerformed(evt);
            }
        });

        absenuser.setFont(new java.awt.Font("Segoe UI Symbol", 1, 14)); // NOI18N
        absenuser.setForeground(new java.awt.Color(255, 255, 255));
        absenuser.setText("Absen User");
        absenuser.setToolTipText("");
        absenuser.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        absenuser.setFocusPainted(false);
        absenuser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                absenuserMouseClicked(evt);
            }
        });
        absenuser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                absenuserActionPerformed(evt);
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
                    .addComponent(keluar, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                    .addComponent(tranksaksi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jdwl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(absenguru, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(absenuser, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jdwl, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tranksaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(absenuser, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(absenguru, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
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
    desktop.removeAll();
    desktop.repaint();

    menu_kelas mk = new menu_kelas();

    desktop.add(mk);

    mk.setVisible(true);
    
    try {
        mk.setMaximum(true);
    } catch (java.beans.PropertyVetoException e) {
        System.err.println("Gagal memaksimalkan Internal Frame: " + e.getMessage());
    }      
    }//GEN-LAST:event_menukelasActionPerformed

    private void menuguruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuguruActionPerformed
    desktop.removeAll();
    desktop.repaint();
    menu_guru mg = new menu_guru();

    desktop.add(mg);

    mg.setVisible(true);
    
    try {
        mg.setMaximum(true);
        } catch (java.beans.PropertyVetoException e) {

            System.err.println("Gagal memaksimalkan Internal Frame: " + e.getMessage());
        }
    }//GEN-LAST:event_menuguruActionPerformed

    private void tranksaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tranksaksiActionPerformed
   desktop.removeAll();
    desktop.repaint();

    menu_tranksaksi mt = new menu_tranksaksi();

    desktop.add(mt);

    mt.setVisible(true);
    
    try {
        mt.setMaximum(true);
        } catch (java.beans.PropertyVetoException e) {
            System.err.println("Gagal memaksimalkan Internal Frame: " + e.getMessage());
        }
    }//GEN-LAST:event_tranksaksiActionPerformed

    private void keluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_keluarActionPerformed
    int pilih = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin ingin logout?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
    
    if (pilih == JOptionPane.YES_OPTION) {
        UserSession.setLevel(null);
        UserSession.setNama(null);
        
        this.dispose(); 
        new loginuser().setVisible(true);
    }
    }//GEN-LAST:event_keluarActionPerformed

    private void menusiswaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menusiswaActionPerformed
    desktop.removeAll();
    desktop.repaint();

    menu_siswa ms = new menu_siswa();

    desktop.add(ms);

    ms.setVisible(true);
    
    try {
        ms.setMaximum(true);
        } catch (java.beans.PropertyVetoException e) {
            System.err.println("Gagal memaksimalkan Internal Frame: " + e.getMessage());
        }
    }//GEN-LAST:event_menusiswaActionPerformed

    private void lbl_logoComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_lbl_logoComponentResized
 scaleImage();
    }//GEN-LAST:event_lbl_logoComponentResized

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
   
    }//GEN-LAST:event_formComponentResized

    private void desktopComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_desktopComponentResized
    for (JInternalFrame frame : desktop.getAllFrames()) {
        try {
            frame.setMaximum(true);
        } catch (PropertyVetoException e) {
            System.err.println("Gagal resize internal frame: " + e.getMessage());
        }
    }
    }//GEN-LAST:event_desktopComponentResized

    private void homeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_homeActionPerformed
    tampilkanHomeAwal();
    }//GEN-LAST:event_homeActionPerformed

    private void adminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminActionPerformed
    frm_user user = new frm_user();
    user.setVisible(true);
    }//GEN-LAST:event_adminActionPerformed

    private void jdwlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jdwlActionPerformed
    desktop.removeAll();
    desktop.repaint();
    menu_jadwal mj = new menu_jadwal();

    desktop.add(mj);

    mj.setVisible(true);
    
    try {
        mj.setMaximum(true);
        } catch (java.beans.PropertyVetoException e) {
            System.err.println("Gagal memaksimalkan Internal Frame: " + e.getMessage());
        }
    }//GEN-LAST:event_jdwlActionPerformed

    private void absenguruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_absenguruActionPerformed
    desktop.removeAll();
    desktop.repaint();

    menu_absenGuru mu = new menu_absenGuru();

    desktop.add(mu);

    mu.setVisible(true);
    
    try {
        mu.setMaximum(true);
    } catch (java.beans.PropertyVetoException e) {
        System.err.println("Gagal memaksimalkan Internal Frame: " + e.getMessage());
    }
    }//GEN-LAST:event_absenguruActionPerformed

    private void absenguruMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_absenguruMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_absenguruMouseClicked

    private void absenuserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_absenuserMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_absenuserMouseClicked

    private void absenuserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_absenuserActionPerformed
     desktop.removeAll();
    desktop.repaint();
    
    menu_absenUser mu = new menu_absenUser();
    
    desktop.add(mu);
    
    mu.setVisible(true);
    
    try {
        mu.setMaximum(true);
        } catch (java.beans.PropertyVetoException e) {
            System.err.println("Gagal memaksimalkan Internal Frame: " + e.getMessage());
        }
    }//GEN-LAST:event_absenuserActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
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
    private javax.swing.JButton absenguru;
    private javax.swing.JButton absenuser;
    private javax.swing.JButton admin;
    private javax.swing.JDesktopPane desktop;
    private javax.swing.JButton home;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton jdwl;
    private javax.swing.JButton keluar;
    private javax.swing.JLabel lblWelcome;
    private javax.swing.JLabel lbl_logo;
    private javax.swing.JButton menuguru;
    private javax.swing.JButton menukelas;
    private javax.swing.JButton menusiswa;
    private javax.swing.JButton tranksaksi;
    // End of variables declaration//GEN-END:variables
}
