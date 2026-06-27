/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tampilan;

/**
 *
 * @author User
 */
public class GuruSession {
   private static String kd_guru;
    private static String nama;
    private static String jkel;
    private static String Nip;
    private static String kelas;

    // Getter & Setter kd_guru
    public static String getKdGuru() {
        return kd_guru;
    }
    public static void setKdGuru(String kdGuru) {
        GuruSession.kd_guru = kdGuru;
    }

    // Getter & Setter nama
    public static String getNama() {
        return nama;
    }
    public static void setNama(String nama) {
        GuruSession.nama = nama;
    }

    // Getter & Setter jkel
    public static String getJkel() {
        return jkel;
    }
    public static void setJkel(String jkel) {
        GuruSession.jkel = jkel;
    }
    
    public static String getNip() {
        return Nip;
    }
    public static void setNip(String Nip) {
        GuruSession.Nip = Nip;
    }
    
    public static String getKelas(){
        return kelas;
    }
    public static void setKelas(String kelas){
        GuruSession.kelas = kelas;
    }
    
    
    // Method pembuat sapaan otomatis
    public static String getSapaan() {
        String panggilan = "";
        
        if (jkel != null) {
            // Harus sama persis dengan ENUM di database: 'Laki - laki'
            if (jkel.equals("Laki - laki")) {
                panggilan = "Bapak ";
            } else if (jkel.equals("Perempuan")) {
                panggilan = "Ibu ";
            }
        }
        
        return panggilan;
    }
}
