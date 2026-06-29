/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tampilan;
import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import koneksi.koneksi;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import tampilan.GuruSession;

/**
 *
 * @author User
 */
public class menu_absenGuru extends javax.swing.JInternalFrame {
private Connection con = new koneksi().getConnection();
private DefaultTableModel tabmode;
PreparedStatement ps;
ResultSet rs;
    /**
     * Creates new form menu_loginGuru
     */
    public menu_absenGuru() {
        initComponents();
        javax.swing.plaf.basic.BasicInternalFrameUI ui = (javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI();
    ui.setNorthPane(null);
        String KD = GuruSession.getKdGuru();
        System.out.println(KD);
        datatable();
        aktif();
        kosong();
        aturHakAkses();
    }
    
    private void aturHakAkses() {
    bprint.setVisible(false); 
    bprint1.setVisible(false); 
    String levelAdmin = UserSession.getLevel(); 
    String sessionGuru = GuruSession.getNip(); 
        if (levelAdmin != null && levelAdmin.equals("1")) {
            bprint1.setVisible(true); 

        } else if (sessionGuru != null && !sessionGuru.isEmpty()) {
            bprint.setVisible(true); 
        }
    }
     protected void datatable(){
        String[] baris = {"Kode Guru", "Nama guru", "Kehadiran", "Total Hadir", "Total Tidak Hadir", "Keterangan"};
        tabmode = new DefaultTableModel(null, baris) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 2) { 
                    return Boolean.class; 
                }
                return super.getColumnClass(columnIndex);
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2 || column == 5; 
            }
        };

        tblguru.setModel(tabmode);

        try {
            String guruLogin = GuruSession.getKdGuru();
            String sql = "SELECT u.kd_guru, u.nama, " +
             "COALESCE(SUM(CASE WHEN a.kehadiran = 1 THEN 1 ELSE 0 END), 0) AS total_hadir, " +
             "COALESCE(SUM(CASE WHEN a.kehadiran = 0 THEN 1 ELSE 0 END), 0) AS total_tidak_hadir " +
             "FROM guru u " +
             "LEFT JOIN tbl_absen_guru a ON u.kd_guru = a.kd_guru " +
             "WHERE u.kd_guru = ? " +  
             "GROUP BY u.kd_guru, u.nama"; 
            PreparedStatement stat = con.prepareStatement(sql);
            stat.setString(1, guruLogin);
            ResultSet hasil = stat.executeQuery();

            while (hasil.next()) {
                Object[] data = {
                    hasil.getString("kd_guru"),
                    hasil.getString("nama"),
                    false, 
                    hasil.getInt("total_hadir"),
                    hasil.getInt("total_tidak_hadir"),
                    ""
                };
                tabmode.addRow(data);
            }

        } catch (Exception e) {
            e.printStackTrace(); 
            JOptionPane.showMessageDialog(this, "Gagal memuat data: " + e.getMessage());
        }    }

       
        
    protected void aktif(){
        tglTemu.requestFocus();
    }
    
    protected void kosong(){
       
        tglTemu.setDate(new Date());

    }
    
    public void cetak() {
        try{
            String path="./src/report/reportGuruprib.jasper";
            HashMap parameter = new HashMap();
            String guruLogin = GuruSession.getNip();
            System.out.println("=== DEBUG LOG === Kode Guru dari Session: " + guruLogin); 
            parameter.put("paramKdGuru", guruLogin); 
            JasperPrint print = JasperFillManager.fillReport(path, parameter,con);
            JasperViewer.viewReport(print, false);
        }catch (Exception ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(rootPane,"Dokumen tidak ada" +ex);
        }
    }
    
    public void cetakAbsenGuru() {
    try {
        String path = "./src/report/reportGuru.jasper"; 

        HashMap<String, Object> parameter = new HashMap<>();
        JasperPrint print = JasperFillManager.fillReport(path, parameter, con);
        JasperViewer.viewReport(print, false);
        
        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(rootPane, "Dokumen gagal dicetak: " + ex.getMessage());
            ex.printStackTrace(); 
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

        jScrollPane1 = new javax.swing.JScrollPane();
        tblguru = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        tglTemu = new com.toedter.calendar.JDateChooser();
        bbatal = new javax.swing.JButton();
        bsimpan = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        bprint = new javax.swing.JButton();
        bprint1 = new javax.swing.JButton();

        tblguru.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        tblguru.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblguru.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblguruMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblguru);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Detail Pertemuan", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 16), new java.awt.Color(1, 1, 1))); // NOI18N
        jPanel1.setForeground(new java.awt.Color(1, 1, 1));

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel7.setText("Tanggal  : ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(tglTemu, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(tglTemu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        bbatal.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        bbatal.setText("BATAL");
        bbatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bbatalActionPerformed(evt);
            }
        });

        bsimpan.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        bsimpan.setText("SIMPAN");
        bsimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bsimpanActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Absensi Guru");

        bprint.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        bprint.setText("PRINT");
        bprint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bprintActionPerformed(evt);
            }
        });

        bprint1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        bprint1.setText("PRINT TU");
        bprint1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bprint1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(bprint, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bprint1, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 229, Short.MAX_VALUE)
                        .addComponent(bsimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(bbatal, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(35, 35, 35))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bsimpan)
                    .addComponent(bbatal)
                    .addComponent(bprint)
                    .addComponent(bprint1))
                .addContainerGap(158, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblguruMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblguruMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblguruMouseClicked

    private void bbatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bbatalActionPerformed
        int konfirmasi = JOptionPane.showConfirmDialog(this,
            "Batal melakukan absensi? Semua isian dan centang akan dikosongkan.",
            "Konfirmasi Batal",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);

        if (konfirmasi == JOptionPane.YES_OPTION) {
            kosong();
            datatable(); 

            JOptionPane.showMessageDialog(this, "Form berhasil dibersihkan.", "Batal", JOptionPane.INFORMATION_MESSAGE);
        }        
    }//GEN-LAST:event_bbatalActionPerformed

    private void bsimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bsimpanActionPerformed
        DefaultTableModel model =
            (DefaultTableModel) tblguru.getModel();

    int jumlahBaris = model.getRowCount();

    if (jumlahBaris == 0) {
        JOptionPane.showMessageDialog(this,
                "Data masih kosong!");
        return;
    }

    int konfirmasi = JOptionPane.showConfirmDialog(
            this,
            "Yakin ingin menyimpan data absensi?",
            "Konfirmasi",
            JOptionPane.YES_NO_OPTION);

    if (konfirmasi != JOptionPane.YES_OPTION) {
        return;
    }

    try {

        String sql =
                "INSERT INTO tbl_absen_guru " +
                "(kd_guru,tgl,kehadiran,keterangan) " +
                "VALUES (?,?,?,?)";

        PreparedStatement pst =
                con.prepareStatement(sql);

       java.sql.Date tgl =
            new java.sql.Date(tglTemu.getDate().getTime());

        for (int i = 0; i < jumlahBaris; i++) {

            String nip =
                    model.getValueAt(i, 0).toString();

            boolean hadir =
                    (Boolean) model.getValueAt(i, 2);

            String keterangan =
                    model.getValueAt(i, 5).toString();

            pst.setString(1, nip);
            pst.setDate(2, tgl);
            pst.setInt(3, hadir ? 1 : 0);
            pst.setString(4, keterangan);

            pst.addBatch();
        }

        pst.executeBatch();

        JOptionPane.showMessageDialog(
                this,
                "Data absensi berhasil disimpan");

        datatable();

    } catch (Exception e) {

        JOptionPane.showMessageDialog(
                this,
                "Error : " + e.getMessage());
    }

    }//GEN-LAST:event_bsimpanActionPerformed

    private void bprintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bprintActionPerformed
        cetak();
    }//GEN-LAST:event_bprintActionPerformed

    private void bprint1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bprint1ActionPerformed
        cetakAbsenGuru();
    }//GEN-LAST:event_bprint1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bbatal;
    private javax.swing.JButton bprint;
    private javax.swing.JButton bprint1;
    private javax.swing.JButton bsimpan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblguru;
    private com.toedter.calendar.JDateChooser tglTemu;
    // End of variables declaration//GEN-END:variables
}
