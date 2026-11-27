package tampilan;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import javax.swing.JDialog;
import javax.swing.Timer;

/**
 * Dialog Pemindai Kartu Pelajar (Barcode / QR Code) — Modern Navy & Royal Blue.
 */
public class frm_ScannerBarcode extends JDialog {

    private Webcam webcam;
    private WebcamPanel panel;
    private Timer scanTimer;
    private String hasilScan = "";

    public frm_ScannerBarcode(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        UITheme.setup();
        initComponents();
        UITheme.applyWindowIcon(this);

        pnlRoot.setBackground(UITheme.BG_MAIN);
        UITheme.styleCard(pnlCard);
        UITheme.styleSecondaryButton(btnTutup);

        setTitle("Pemindai Kartu Pelajar (Barcode)");
        setSize(620, 505);
        setLocationRelativeTo(parent);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                stopScan();
            }
        });

        // Inisialisasi Kamera
        webcam = Webcam.getDefault();
        if (webcam != null) {
            if (!webcam.isOpen()) {
                webcam.setViewSize(WebcamResolution.VGA.getSize()); // 640x480
            }
            panel = new WebcamPanel(webcam);
            panel.setPreferredSize(new Dimension(544, 314));
            panel.setFPSDisplayed(true);

            panelKamera.setLayout(new java.awt.BorderLayout());
            panelKamera.add(panel, java.awt.BorderLayout.CENTER);
            panelKamera.revalidate();
            panelKamera.repaint();
            lblStatus.setText("Status: Kamera siap — arahkan barcode kartu siswa ke kamera");
        } else {
            lblStatus.setText("Status: Kamera tidak terdeteksi pada perangkat ini");
            javax.swing.JLabel lblNoCam = new javax.swing.JLabel(
                    "Kamera tidak terdeteksi", javax.swing.SwingConstants.CENTER);
            lblNoCam.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
            lblNoCam.setForeground(UITheme.TEXT_ON_NAVY);
            panelKamera.add(lblNoCam, java.awt.BorderLayout.CENTER);
            javax.swing.JOptionPane.showMessageDialog(this, "Kamera tidak terdeteksi!", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method ini akan dipanggil saat tombol "Mulai Scan" ditekan
    public void mulaiScan() {
        if (webcam == null) {
            return;
        }
        if (!webcam.isOpen()) {
            webcam.open();
        }
        lblStatus.setText("Status: Memindai barcode kartu pelajar secara aktif...");

        // Scan setiap 500ms (0.5 detik) untuk menghemat performa CPU
        scanTimer = new javax.swing.Timer(500, new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                if (webcam == null || !webcam.isOpen()) {
                    return;
                }
                BufferedImage image = webcam.getImage();
                if (image != null) {
                    try {
                        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
                        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                        Result result = new MultiFormatReader().decode(bitmap);

                        // Jika barcode terdeteksi
                        hasilScan = result.getText();
                        lblStatus.setText("Status: Barcode berhasil terbaca (" + hasilScan + ")");
                        stopScan(); // Hentikan scan setelah berhasil
                        dispose(); // Tutup dialog
                    } catch (NotFoundException ex) {
                        // Tidak ada barcode di frame ini, abaikan dan lanjutkan loop
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        scanTimer.start();
    }

    private void stopScan() {
        if (scanTimer != null && scanTimer.isRunning()) {
            scanTimer.stop();
        }
        if (webcam != null && webcam.isOpen()) {
            webcam.close();
        }
    }

    // Method publik untuk mendapatkan hasil scan dari form lain
    public String getHasilScan() {
        return hasilScan;
    }

    @Override
    public void dispose() {
        stopScan();
        super.dispose();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlRoot = new javax.swing.JPanel();
        pnlHeader = new javax.swing.JPanel();
        lblHeaderTitle = new javax.swing.JLabel();
        lblHeaderSub = new javax.swing.JLabel();
        pnlCard = new javax.swing.JPanel();
        panelKamera = new javax.swing.JPanel();
        lblStatus = new javax.swing.JLabel();
        btnTutup = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Pemindai Kartu Pelajar (Barcode)");
        setBackground(new java.awt.Color(241, 245, 249));

        pnlRoot.setBackground(new java.awt.Color(241, 245, 249));

        pnlHeader.setBackground(new java.awt.Color(15, 23, 42));

        lblHeaderTitle.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        lblHeaderTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblHeaderTitle.setText("Pemindai Kartu Pelajar (Barcode)");

        lblHeaderSub.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblHeaderSub.setForeground(new java.awt.Color(56, 189, 248));
        lblHeaderSub.setText("Arahkan barcode kartu siswa ke kamera");

        javax.swing.GroupLayout pnlHeaderLayout = new javax.swing.GroupLayout(pnlHeader);
        pnlHeader.setLayout(pnlHeaderLayout);
        pnlHeaderLayout.setHorizontalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeaderLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblHeaderTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblHeaderSub, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(24, 24, 24))
        );
        pnlHeaderLayout.setVerticalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeaderLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(lblHeaderTitle)
                .addGap(2, 2, 2)
                .addComponent(lblHeaderSub)
                .addGap(12, 12, 12))
        );

        pnlCard.setBackground(new java.awt.Color(255, 255, 255));
        pnlCard.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(226, 232, 240), 1, true));

        panelKamera.setBackground(new java.awt.Color(15, 23, 42));
        panelKamera.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(30, 41, 59), 1, true));
        panelKamera.setPreferredSize(new java.awt.Dimension(544, 314));
        panelKamera.setLayout(new java.awt.BorderLayout());

        lblStatus.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblStatus.setForeground(new java.awt.Color(100, 116, 139));
        lblStatus.setText("Status: Menyiapkan kamera pemindai...");

        btnTutup.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTutup.setText("Tutup / Batal");
        btnTutup.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTutup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTutupActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlCardLayout = new javax.swing.GroupLayout(pnlCard);
        pnlCard.setLayout(pnlCardLayout);
        pnlCardLayout.setHorizontalGroup(
            pnlCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCardLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(pnlCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelKamera, javax.swing.GroupLayout.DEFAULT_SIZE, 544, Short.MAX_VALUE)
                    .addGroup(pnlCardLayout.createSequentialGroup()
                        .addComponent(lblStatus, javax.swing.GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE)
                        .addGap(12, 12, 12)
                        .addComponent(btnTutup, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18))
        );
        pnlCardLayout.setVerticalGroup(
            pnlCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCardLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(panelKamera, javax.swing.GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE)
                .addGap(14, 14, 14)
                .addGroup(pnlCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStatus)
                    .addComponent(btnTutup, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout pnlRootLayout = new javax.swing.GroupLayout(pnlRoot);
        pnlRoot.setLayout(pnlRootLayout);
        pnlRootLayout.setHorizontalGroup(
            pnlRootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlRootLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(pnlCard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );
        pnlRootLayout.setVerticalGroup(
            pnlRootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRootLayout.createSequentialGroup()
                .addComponent(pnlHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(pnlCard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlRoot, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlRoot, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTutupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTutupActionPerformed
        dispose();
    }//GEN-LAST:event_btnTutupActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        UITheme.setup();
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                frm_ScannerBarcode dialog = new frm_ScannerBarcode(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnTutup;
    private javax.swing.JLabel lblHeaderSub;
    private javax.swing.JLabel lblHeaderTitle;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JPanel panelKamera;
    private javax.swing.JPanel pnlCard;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JPanel pnlRoot;
    // End of variables declaration//GEN-END:variables
}
