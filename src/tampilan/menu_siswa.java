/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tampilan;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.util.HashMap;
import koneksi.koneksi;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRGraphics2DExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleGraphics2DExporterOutput;
import net.sf.jasperreports.export.SimpleGraphics2DReportConfiguration;
import net.sf.jasperreports.view.JasperViewer;
/**
 *
 * @author User
 */
public class menu_siswa extends javax.swing.JInternalFrame {
private Connection con = koneksi.getConnection();
private DefaultTableModel tabmode;
String nisTerpilih = "";
    /**
   
     */
    public menu_siswa() {
    initComponents();
    javax.swing.plaf.basic.BasicInternalFrameUI ui = (javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI();
    ui.setNorthPane(null);

    try {
        com.formdev.flatlaf.FlatLightLaf.setup();
    } catch (Exception e) {
    }
    
    javax.swing.JScrollPane scrollUtama = new javax.swing.JScrollPane(getContentPane());
    scrollUtama.setBorder(null);
    scrollUtama.getVerticalScrollBar().setUnitIncrement(16);
    setContentPane(scrollUtama);

    bsimpan.setIcon(new ImageIcon(
    new ImageIcon(getClass().getResource("/resource/save.png"))
        .getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));

bubah.setIcon(new ImageIcon(
    new ImageIcon(getClass().getResource("/resource/edit.png"))
        .getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));

bhapus.setIcon(new ImageIcon(
    new ImageIcon(getClass().getResource("/resource/delete.png"))
        .getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));

bbatal.setIcon(new ImageIcon(
    new ImageIcon(getClass().getResource("/resource/reset.png"))
        .getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
bcari.setIcon(new ImageIcon(
    new ImageIcon(getClass().getResource("/resource/search.png"))
        .getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));

    bsimpan.setBackground(new java.awt.Color(34, 197, 94));
    bsimpan.setForeground(java.awt.Color.WHITE);

    bubah.setBackground(new java.awt.Color(59, 130, 246));
    bubah.setForeground(java.awt.Color.WHITE);

    bhapus.setBackground(new java.awt.Color(239, 68, 68));
    bhapus.setForeground(java.awt.Color.WHITE);

    bbatal.setBackground(new java.awt.Color(107, 114, 128));
    bbatal.setForeground(java.awt.Color.WHITE);

   bcari.setBackground(new java.awt.Color(59, 130, 246));
   bcari.setForeground(java.awt.Color.WHITE);

    bsimpan.setFocusPainted(false);
    bubah.setFocusPainted(false);
    bhapus.setFocusPainted(false);
    bbatal.setFocusPainted(false);

    bsimpan.setIconTextGap(8);
    bubah.setIconTextGap(8);
    bhapus.setIconTextGap(8);
    bbatal.setIconTextGap(8);
    
    bsimpan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    bubah.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    bhapus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    bbatal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

    bsimpan.setIconTextGap(10);
    bubah.setIconTextGap(10);
    bhapus.setIconTextGap(10);
    bbatal.setIconTextGap(10);

    loadJurusan();
    kosong();
    aktif();
    datatable();
    bkartu.setVisible(false);
}
    protected void aktif(){
        txtnisn.requestFocus();
    }
    protected void kosong(){
        txtnisn.setText("");
        txtnm.setText("");
        dctgl.setDate(null);
        txtalamat.setText("");
        txttelp.setText("");
        cbjk.setSelectedIndex(0);
        txtangkatan.setText("");
        txtnmwali.setText("");
        txtnowali.setText("");
        txtcari.setText("");
      
        if (cbjurusan.getItemCount() > 0) {
    cbjurusan.setSelectedIndex(0);
}

if (cbkls.getItemCount() > 0) {
    cbkls.setSelectedIndex(0);
}
        if (bkartu != null) {
            bkartu.setVisible(false);
        }
    }
    
    public void cetakReportByNis() {
        try {
            String nis = txtnisn.getText().trim();
            if (nis.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Silakan klik data siswa pada tabel terlebih dahulu!");
                return;
            }

            // Buat gambar barcode Code128 resolusi tinggi menggunakan ZXing
            BitMatrix bitMatrix = new Code128Writer().encode(nis, BarcodeFormat.CODE_128, 600, 105);
            BufferedImage barcodeImg = MatrixToImageWriter.toBufferedImage(bitMatrix);

            InputStream logoStream = getClass().getResourceAsStream("/resource/logosekolah.jpeg");

            HashMap<String, Object> parameter = new HashMap<>();
            parameter.put("nisn", nis);
            parameter.put("barcode", barcodeImg);
            parameter.put("logo", logoStream);

            Connection conn = koneksi.getConnection();
            InputStream reportStream = getClass().getResourceAsStream("/report/kartunama.jasper");
            JasperPrint jp;
            if (reportStream != null) {
                jp = JasperFillManager.fillReport(reportStream, parameter, conn);
            } else {
                jp = JasperFillManager.fillReport("./src/report/kartunama.jasper", parameter, conn);
            }

            if (jp.getPages().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Data siswa dengan NISN " + nis + " tidak ditemukan!");
                return;
            }

            // Dialog simpan langsung sebagai PDF ukuran standar ID Card (8,6 cm x 5,4 cm)
            String namaBersih = txtnm.getText().trim().replaceAll("[^a-zA-Z0-9_\\- ]", "").replaceAll("\\s+", "_");
            String namaFileDefault = "Kartu_Pelajar_" + nis + (namaBersih.isEmpty() ? "" : "_" + namaBersih) + ".pdf";

            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Simpan Kartu Pelajar (Ukuran ID Card 8,6 x 5,4 cm)");
            chooser.setSelectedFile(new File(chooser.getCurrentDirectory(), namaFileDefault));
            chooser.setFileFilter(new FileNameExtensionFilter("PDF Document (*.pdf)", "pdf"));

            int pilihan = chooser.showSaveDialog(this);
            if (pilihan != JFileChooser.APPROVE_OPTION) {
                return;
            }

            File fileTujuan = chooser.getSelectedFile();
            if (!fileTujuan.getName().toLowerCase(Locale.ROOT).endsWith(".pdf")) {
                fileTujuan = new File(fileTujuan.getParentFile(), fileTujuan.getName() + ".pdf");
            }

            simpanJasperKePdfUkuranKartu(jp, fileTujuan);

            JOptionPane.showMessageDialog(this, "Kartu pelajar berhasil disimpan:\n" + fileTujuan.getAbsolutePath());
            if (Desktop.isDesktopSupported()) {
                try {
                    Desktop.getDesktop().open(fileTujuan);
                } catch (Exception ignore) {
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal mencetak kartu pelajar: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void simpanJasperKePdfUkuranKartu(JasperPrint jp, File outputPdf) throws Exception {
        // Render halaman kartu pada resolusi cetak tinggi (360 DPI -> zoom 5x dari 72 DPI)
        float zoom = 5.0f;
        int imgW = Math.round(jp.getPageWidth() * zoom);
        int imgH = Math.round(jp.getPageHeight() * zoom);

        BufferedImage cardImage = new BufferedImage(imgW, imgH, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = cardImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, imgW, imgH);

        JRGraphics2DExporter exporter = new JRGraphics2DExporter();
        exporter.setExporterInput(new SimpleExporterInput(jp));
        SimpleGraphics2DExporterOutput expOut = new SimpleGraphics2DExporterOutput();
        expOut.setGraphics2D(g2d);
        exporter.setExporterOutput(expOut);

        SimpleGraphics2DReportConfiguration cfg = new SimpleGraphics2DReportConfiguration();
        cfg.setPageIndex(0);
        cfg.setZoomRatio(zoom);
        exporter.setConfiguration(cfg);
        exporter.exportReport();
        g2d.dispose();

        // Konversi piksel RGB & kompresi lossless FlateDecode
        byte[] rawRgb = new byte[imgW * imgH * 3];
        int[] pixels = cardImage.getRGB(0, 0, imgW, imgH, null, 0, imgW);
        int idx = 0;
        for (int argb : pixels) {
            rawRgb[idx++] = (byte) ((argb >> 16) & 0xFF);
            rawRgb[idx++] = (byte) ((argb >> 8) & 0xFF);
            rawRgb[idx++] = (byte) (argb & 0xFF);
        }

        ByteArrayOutputStream compressedBuf = new ByteArrayOutputStream();
        Deflater deflater = new Deflater(Deflater.BEST_COMPRESSION);
        try (DeflaterOutputStream dos = new DeflaterOutputStream(compressedBuf, deflater)) {
            dos.write(rawRgb);
        }
        deflater.end();
        byte[] compressedImg = compressedBuf.toByteArray();

        // Ukuran fisik standar ID Card CR80 / KTP: 85.6 mm x 54.0 mm = 242.65 pt x 153.07 pt
        String pageW = "242.65";
        String pageH = "153.07";
        byte[] contentBytes = ("q " + pageW + " 0 0 " + pageH + " 0 0 cm /Im0 Do Q\n").getBytes(StandardCharsets.US_ASCII);

        ByteArrayOutputStream pdf = new ByteArrayOutputStream();
        pdf.write("%PDF-1.4\n".getBytes(StandardCharsets.US_ASCII));
        pdf.write(new byte[]{'%', (byte) 0xE2, (byte) 0xE3, (byte) 0xCF, (byte) 0xD3, '\n'});

        int[] offsets = new int[6];

        offsets[1] = pdf.size();
        writeAscii(pdf, "1 0 obj\n<< /Type /Catalog /Pages 2 0 R >>\nendobj\n");

        offsets[2] = pdf.size();
        writeAscii(pdf, "2 0 obj\n<< /Type /Pages /Kids [3 0 R] /Count 1 >>\nendobj\n");

        offsets[3] = pdf.size();
        writeAscii(pdf, "3 0 obj\n<< /Type /Page /Parent 2 0 R /MediaBox [0 0 " + pageW + " " + pageH + "] "
                + "/Resources << /XObject << /Im0 4 0 R >> >> /Contents 5 0 R >>\nendobj\n");

        offsets[4] = pdf.size();
        writeAscii(pdf, "4 0 obj\n<< /Type /XObject /Subtype /Image /Width " + imgW + " /Height " + imgH
                + " /ColorSpace /DeviceRGB /BitsPerComponent 8 /Filter /FlateDecode /Length " + compressedImg.length + " >>\nstream\n");
        pdf.write(compressedImg);
        writeAscii(pdf, "\nendstream\nendobj\n");

        offsets[5] = pdf.size();
        writeAscii(pdf, "5 0 obj\n<< /Length " + contentBytes.length + " >>\nstream\n");
        pdf.write(contentBytes);
        writeAscii(pdf, "endstream\nendobj\n");

        int xrefOffset = pdf.size();
        writeAscii(pdf, "xref\n0 6\n0000000000 65535 f \r\n");
        for (int i = 1; i <= 5; i++) {
            writeAscii(pdf, String.format(Locale.US, "%010d 00000 n \r\n", offsets[i]));
        }
        writeAscii(pdf, "trailer\n<< /Size 6 /Root 1 0 R >>\nstartxref\n" + xrefOffset + "\n%%EOF\n");

        try (FileOutputStream fos = new FileOutputStream(outputPdf)) {
            pdf.writeTo(fos);
        }
    }

    private static void writeAscii(ByteArrayOutputStream out, String text) throws IOException {
        out.write(text.getBytes(StandardCharsets.US_ASCII));
    }

    
    public void cetak() {
    try {
        String path = "./src/report/siswaReport.jasper"; 

        HashMap<String, Object> parameter = new HashMap<>();
        parameter.put(net.sf.jasperreports.engine.JRParameter.REPORT_LOCALE, new java.util.Locale("id", "ID"));
        InputStream logoStream = getClass().getResourceAsStream("/resource/logosekolah.jpeg"); 
        if (logoStream == null) {
            System.out.println("GAGAL: File gambar tidak ditemukan di resources!");
        }
        parameter.put("logo", logoStream);
        JasperPrint print = JasperFillManager.fillReport(path, parameter, con);
        JasperViewer.viewReport(print, false);
        
        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(rootPane, "Dokumen gagal dicetak: " + ex.getMessage());
            ex.printStackTrace(); 
        }
    } 
   protected void datatable() {
    Object[] Baris = {
    "NISN","Nama","Tanggal","Alamat","No. Telepon",
    "Jenis Kelamin","Angkatan","Nama Wali","No Wali","Jurusan","Kelas"
     };

    tabmode = new DefaultTableModel(null, Baris);
    tbldatasiswa.setModel(tabmode);

    String cariitem = txtcari.getText();

    try {
        String sql = "SELECT * FROM tbl_siswa WHERE nisn LIKE ? OR nama LIKE ? ORDER BY nisn ASC";
        PreparedStatement stat = con.prepareStatement(sql);

        stat.setString(1, "%" + cariitem + "%");
        stat.setString(2, "%" + cariitem + "%");

        ResultSet hasil = stat.executeQuery();

        while (hasil.next()) {
            tabmode.addRow(new Object[]{
                hasil.getString("nisn"),
                hasil.getString("nama"),
                hasil.getString("tgl_lahir"),
                hasil.getString("alamat"),
                hasil.getString("notelp"),
                hasil.getString("jkel"),
                hasil.getString("angkatan"),
                hasil.getString("nama_wali"),
                hasil.getString("no_wali"),
                hasil.getString("jurusan"),
                hasil.getString("kelas")
            });
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Data gagal dipanggil: " + e);
    }
}
   private void loadKelasByJurusan(String jurusan) {
    try {
        String sql = "SELECT kelas FROM tbl_kelas WHERE kelas LIKE ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, "%" + jurusan + "%");

        ResultSet rs = pst.executeQuery();

        cbkls.removeAllItems();
        cbkls.addItem("-- Pilih Kelas --");

        while (rs.next()) {
            cbkls.addItem(rs.getString("kelas"));
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Gagal load kelas: " + e);
    }
}
   private void loadJurusan() {
    try {
        String sql = "SELECT DISTINCT SUBSTRING_INDEX(kelas, '-', -1) AS jurusan FROM tbl_kelas";
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        cbjurusan.removeAllItems();
        cbjurusan.addItem("-- Pilih Jurusan --");

while (rs.next()) {
    cbjurusan.addItem(rs.getString("jurusan"));
}

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Gagal load jurusan: " + e);
    }
}
    /**
     * Creates new form menu_siswa
     */

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtnisn = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtnm = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtalamat = new javax.swing.JTextArea();
        txttelp = new javax.swing.JTextField();
        cbjk = new javax.swing.JComboBox<>();
        dctgl = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        txtangkatan = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtnmwali = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtnowali = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        cbjurusan = new javax.swing.JComboBox<>();
        cbkls = new javax.swing.JComboBox<>();
        bsimpan = new javax.swing.JButton();
        bubah = new javax.swing.JButton();
        bhapus = new javax.swing.JButton();
        bbatal = new javax.swing.JButton();
        bcetak = new javax.swing.JButton();
        bkartu = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        txtcari = new javax.swing.JTextField();
        bcari = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbldatasiswa = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(null);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        jPanel1.setBackground(java.awt.Color.white);
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel2.setText("NISN");

        txtnisn.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel3.setText("Nama Siswa");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel4.setText("Tanggal Lahir");

        jLabel12.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel12.setText("Alamat");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel5.setText("No. Telepon");

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel6.setText("Jenis Kelamin");

        txtnm.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        txtnm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnmActionPerformed(evt);
            }
        });

        txtalamat.setColumns(20);
        txtalamat.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        txtalamat.setRows(5);
        jScrollPane2.setViewportView(txtalamat);

        txttelp.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        cbjk.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        cbjk.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Laki - Laki", "Perempuan" }));

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel7.setText("Angkatan");

        txtangkatan.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel8.setText("Nama Wali");

        txtnmwali.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel9.setText("No Wali");

        txtnowali.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel10.setText("Jurusan");

        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel11.setText("Kelas");

        cbjurusan.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        cbjurusan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbjurusanActionPerformed(evt);
            }
        });

        cbkls.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        bsimpan.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        bsimpan.setText("Simpan");
        bsimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bsimpanActionPerformed(evt);
            }
        });

        bubah.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        bubah.setText("Ubah");
        bubah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bubahActionPerformed(evt);
            }
        });

        bhapus.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        bhapus.setText("Hapus");
        bhapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bhapusActionPerformed(evt);
            }
        });

        bbatal.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        bbatal.setText("Batal");
        bbatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bbatalActionPerformed(evt);
            }
        });

        bcetak.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        bcetak.setText("CETAK");
        bcetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bcetakActionPerformed(evt);
            }
        });

        bkartu.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        bkartu.setText("KARTU NAMA");
        bkartu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bkartuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3))
                            .addComponent(jLabel4)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addGap(73, 73, 73))
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGap(61, 61, 61)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dctgl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtnm)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtnisn, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(txttelp)
                            .addComponent(cbjk, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(226, 226, 226))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel11)))
                            .addComponent(bsimpan, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(94, 94, 94)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtnowali)
                                    .addComponent(txtangkatan)
                                    .addComponent(txtnmwali)
                                    .addComponent(cbjurusan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cbkls, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(226, 226, 226))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(bubah, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(bhapus, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(bkartu, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(bbatal, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(bcetak, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtnisn, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtnm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(dctgl, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txttelp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cbjk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtangkatan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtnmwali, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtnowali, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(cbjurusan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(cbkls, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                    .addComponent(bsimpan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bubah, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bhapus))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bbatal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bcetak, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addComponent(bkartu, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel2.setBackground(java.awt.Color.white);
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtcari.setFont(new java.awt.Font("Times New Roman", 2, 18)); // NOI18N
        txtcari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcariKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtcariKeyReleased(evt);
            }
        });
        jPanel2.add(txtcari, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 44, 300, -1));

        bcari.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        bcari.setMargin(new java.awt.Insets(14, 14, 14, 14));
        bcari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bcariActionPerformed(evt);
            }
        });
        jPanel2.add(bcari, new org.netbeans.lib.awtextra.AbsoluteConstraints(321, 43, -1, 30));

        jLabel13.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel13.setText(" Data Siswa");
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 2, 122, -1));

        tbldatasiswa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8", "Title 9", "Title 10", "Title 11"
            }
        ));
        tbldatasiswa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbldatasiswaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbldatasiswa);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 81, 1400, 570));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 48)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("FORM DATA SISWA");
        jLabel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 48))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1871, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(9, 9, 9))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(jLabel1)
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtcariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcariKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcariKeyPressed

    private void txtcariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcariKeyReleased
        // TODO add your handling code here:
        datatable();
    }//GEN-LAST:event_txtcariKeyReleased

    private void bcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bcariActionPerformed
        // TODO add your handling code here:
        datatable();
    }//GEN-LAST:event_bcariActionPerformed

    private void bsimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bsimpanActionPerformed
      if (txtnisn.getText().equals("") ||
        txtnm.getText().equals("") ||
        cbjurusan.getSelectedIndex() == 0 ||
        cbkls.getSelectedIndex() == 0) {

        JOptionPane.showMessageDialog(null, "Data belum lengkap!");
        return;
    }
    String telp = txttelp.getText().replaceAll("[^\\d]", "");
    
    java.util.Date utilDate = dctgl.getDate();
    if (utilDate == null) {
        JOptionPane.showMessageDialog(null, "Tanggal belum dipilih!");
        return;
    }
    if (cbjk.getSelectedIndex() == -1) {
        JOptionPane.showMessageDialog(null, "Pilih jenis kelamin!");
        return;
    }

    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
    if (telp.length() < 10 || telp.length() > 13) {
        JOptionPane.showMessageDialog(null, "No telp tidak valid!");
        return;
    }
    
    try {
        String namaKelas = cbkls.getSelectedItem().toString();
        String idKelas = "";
        String sqlCariID = "SELECT id_kelas FROM tbl_kelas WHERE kelas = '" + namaKelas + "'";
        Statement st = con.createStatement();
        ResultSet rsID = st.executeQuery(sqlCariID);
        if (rsID.next()) {
            idKelas = rsID.getString("id_kelas");
        }

        String sql = "INSERT INTO tbl_siswa (nisn,nama,tgl_lahir,alamat,notelp,jkel,angkatan,nama_wali,no_wali,jurusan,kelas) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement stat = con.prepareStatement(sql);

        stat.setString(1, txtnisn.getText());
        stat.setString(2, txtnm.getText());
        stat.setDate(3, sqlDate);
        stat.setString(4, txtalamat.getText());
        stat.setString(5, telp);
        stat.setString(6, cbjk.getSelectedItem().toString());
        stat.setString(7, txtangkatan.getText());
        stat.setString(8, txtnmwali.getText());
        stat.setString(9, txtnowali.getText());
        stat.setString(10, cbjurusan.getSelectedItem().toString());
       
        stat.setString(11, idKelas);
       

        stat.executeUpdate();
        JOptionPane.showMessageDialog(null, "Data berhasil disimpan");

        kosong();
        datatable();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Gagal simpan: " + e);
    }
    txtnisn.requestFocus();
    }//GEN-LAST:event_bsimpanActionPerformed

    private void bubahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bubahActionPerformed
        try {
        String telp = txttelp.getText().replaceAll("[^\\d]", "");

        if (dctgl.getDate() == null) {
            JOptionPane.showMessageDialog(null, "Tanggal belum dipilih!");
            return;
        }
        java.sql.Date sqlDate = new java.sql.Date(dctgl.getDate().getTime());
        
        if (cbjurusan.getSelectedIndex() == 0 || cbkls.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Pilih jurusan dan kelas!");
            return;
        }

        String namaKelas = cbkls.getSelectedItem().toString();
        String idKelas = "";
        String sqlCariID = "SELECT id_kelas FROM tbl_kelas WHERE kelas = '" + namaKelas + "'";
        Statement st = con.createStatement();
        ResultSet rsID = st.executeQuery(sqlCariID);
        if (rsID.next()) {
            idKelas = rsID.getString("id_kelas");
        }
        

        String sql = "UPDATE tbl_siswa SET nama=?, tgl_lahir=?, alamat=?, notelp=?, jkel=?, angkatan=?, nama_wali=?, no_wali=?, jurusan=?, kelas=? WHERE nisn=?";
        PreparedStatement stat = con.prepareStatement(sql);

        stat.setString(1, txtnm.getText());
        stat.setDate(2, sqlDate);
        stat.setString(3, txtalamat.getText());
        stat.setString(4, telp);
        stat.setString(5, cbjk.getSelectedItem().toString());
        stat.setString(6, txtangkatan.getText());
        stat.setString(7, txtnmwali.getText());
        stat.setString(8, txtnowali.getText());
        stat.setString(9, cbjurusan.getSelectedItem().toString());
        stat.setString(10, idKelas); 
        
        stat.setString(11, txtnisn.getText());
        stat.executeUpdate();

        JOptionPane.showMessageDialog(null, "Data berhasil diubah");

        kosong();
        datatable();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Gagal ubah: " + e);
    }
    }//GEN-LAST:event_bubahActionPerformed

    private void bhapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bhapusActionPerformed
        // TODO add your handling code here:
        int ok = JOptionPane.showConfirmDialog(null, "Hapus data?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (ok == JOptionPane.YES_OPTION) {
            try {
                String sql = "DELETE FROM tbl_siswa WHERE nisn=?";
                PreparedStatement stat = con.prepareStatement(sql);
                stat.setString(1, txtnisn.getText());
                stat.executeUpdate();

                JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
                kosong();
                datatable();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Gagal hapus: " + e);
            }
        }
    }//GEN-LAST:event_bhapusActionPerformed

    private void bbatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bbatalActionPerformed
        // TODO add your handling code here
        kosong();
        datatable();
    }//GEN-LAST:event_bbatalActionPerformed

    private void tbldatasiswaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbldatasiswaMouseClicked
   int bar = tbldatasiswa.getSelectedRow();
   if (bar != -1) {
        // Angka 0 adalah indeks kolom pertama (NIS). Ubah jika posisi kolom NIS berbeda.
        nisTerpilih = tbldatasiswa.getValueAt(bar, 0).toString();
    }
   if (bar == -1) return;
  
    String a = tabmode.getValueAt(bar, 0).toString();
    String b = tabmode.getValueAt(bar, 1).toString();
    Object c = tabmode.getValueAt(bar, 2);
    String d = tabmode.getValueAt(bar, 3).toString();
    String e = tabmode.getValueAt(bar, 4).toString();
    String f = tabmode.getValueAt(bar, 5).toString();
    String g = tabmode.getValueAt(bar, 6).toString();
    String h = tabmode.getValueAt(bar, 7).toString();
    String i = tabmode.getValueAt(bar, 8).toString(); 
    String j = tabmode.getValueAt(bar, 9).toString();
    String k = tabmode.getValueAt(bar, 10).toString();

    txtnmwali.setText(h);
    txtnowali.setText(i);
    cbjurusan.setSelectedItem(j);
    loadKelasByJurusan(j);
    cbkls.setSelectedItem(k);
    txtnisn.setText(a);
    txtnm.setText(b);
    txtalamat.setText(d);
    txttelp.setText(e);
    cbjk.setSelectedItem(f);
    txtangkatan.setText(g);
  
    try {
    if (c != null) {
        dctgl.setDate(java.sql.Date.valueOf(c.toString()));
    }
   } catch (Exception ex) {
     dctgl.setDate(null);
}
    bkartu.setVisible(true);
    }//GEN-LAST:event_tbldatasiswaMouseClicked

    private void cbjurusanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbjurusanActionPerformed
        // TODO add your handling code here:
        Object selected = cbjurusan.getSelectedItem();
        if (selected == null) return;

        String jurusan = selected.toString();
        loadKelasByJurusan(jurusan);
    }//GEN-LAST:event_cbjurusanActionPerformed

    private void txtnmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnmActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnmActionPerformed

    private void bcetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bcetakActionPerformed
        cetak();
    }//GEN-LAST:event_bcetakActionPerformed

    private void bkartuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bkartuActionPerformed
       cetakReportByNis();
       bkartu.setVisible(false);
    }//GEN-LAST:event_bkartuActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bbatal;
    private javax.swing.JButton bcari;
    private javax.swing.JButton bcetak;
    private javax.swing.JButton bhapus;
    private javax.swing.JButton bkartu;
    private javax.swing.JButton bsimpan;
    private javax.swing.JButton bubah;
    private javax.swing.JComboBox<String> cbjk;
    private javax.swing.JComboBox<String> cbjurusan;
    private javax.swing.JComboBox<String> cbkls;
    private com.toedter.calendar.JDateChooser dctgl;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tbldatasiswa;
    private javax.swing.JTextArea txtalamat;
    private javax.swing.JTextField txtangkatan;
    private javax.swing.JTextField txtcari;
    private javax.swing.JTextField txtnisn;
    private javax.swing.JTextField txtnm;
    private javax.swing.JTextField txtnmwali;
    private javax.swing.JTextField txtnowali;
    private javax.swing.JTextField txttelp;
    // End of variables declaration//GEN-END:variables
}
