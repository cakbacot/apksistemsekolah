package tampilan;

public class UserSession {
    private static String level;
    private static String nama;
    // Tambahkan ini
    private static String status; 

    public static void setLevel(String level) {
        UserSession.level = level;
    }

    public static String getLevel() {
        return level;
    }
    
    public static void setNama(String nama) {
        UserSession.nama = nama;
    }

    public static String getNama() {
        return nama;
    }

    // Tambahkan Method Baru untuk Status
    public static void setStatus(String status) {
        UserSession.status = status;
    }

    public static String getStatus() {
        return status;
    }
}