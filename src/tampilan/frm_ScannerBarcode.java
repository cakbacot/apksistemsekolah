/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

public class frm_ScannerBarcode extends JDialog {

    private Webcam webcam;
    private WebcamPanel panel;
    private Timer scanTimer;
    private String hasilScan = "";

    public frm_ScannerBarcode(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(parent);
        setTitle("Scanner Barcode / QR Code");
        
        // Inisialisasi Kamera
        webcam = Webcam.getDefault();
        if (webcam != null) {
            webcam.setViewSize(WebcamResolution.VGA.getSize()); // 640x480
            panel = new WebcamPanel(webcam);
            panel.setPreferredSize(WebcamResolution.VGA.getSize());
            panel.setFPSDisplayed(true);
            
            // Tambahkan panel kamera ke frmScan (pastikan Anda punya JPanel bernama 'panelKamera' di design)
            panelKamera.setLayout(new java.awt.BorderLayout());
            panelKamera.add(panel, java.awt.BorderLayout.CENTER);
            panelKamera.revalidate();
            panelKamera.repaint();
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "Kamera tidak terdeteksi!", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method ini akan dipanggil saat tombol "Mulai Scan" ditekan
    public void mulaiScan() {
        if (webcam != null && !webcam.isOpen()) {
            webcam.open();
        }
        
        // Scan setiap 500ms (0.5 detik) untuk menghemat performa CPU
        scanTimer = new javax.swing.Timer(500, e -> {
            BufferedImage image = webcam.getImage();
            if (image != null) {
                try {
                    BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
                    BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                    Result result = new MultiFormatReader().decode(bitmap);
                    
                    // Jika barcode terdeteksi
                    hasilScan = result.getText();
                    stopScan(); // Hentikan scan setelah berhasil
                    this.dispose(); // Tutup dialog
                } catch (NotFoundException ex) {
                    // Tidak ada barcode di frame ini, abaikan dan lanjutkan loop
                } catch (Exception ex) {
                    ex.printStackTrace();
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

        panelKamera = new javax.swing.JPanel();
        btnTutup = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        btnTutup.setText("tutup");
        btnTutup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTutupActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelKameraLayout = new javax.swing.GroupLayout(panelKamera);
        panelKamera.setLayout(panelKameraLayout);
        panelKameraLayout.setHorizontalGroup(
            panelKameraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelKameraLayout.createSequentialGroup()
                .addGap(341, 341, 341)
                .addComponent(btnTutup, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(345, Short.MAX_VALUE))
        );
        panelKameraLayout.setVerticalGroup(
            panelKameraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelKameraLayout.createSequentialGroup()
                .addContainerGap(621, Short.MAX_VALUE)
                .addComponent(btnTutup)
                .addGap(32, 32, 32))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(44, Short.MAX_VALUE)
                .addComponent(panelKamera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelKamera, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
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
            java.util.logging.Logger.getLogger(frm_ScannerBarcode.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frm_ScannerBarcode.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frm_ScannerBarcode.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frm_ScannerBarcode.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
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
    private javax.swing.JPanel panelKamera;
    // End of variables declaration//GEN-END:variables
}
