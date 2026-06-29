package tampilan;

public class UserSession {
    private static String nip;
    
    private static String level;
    private static String nama;
    private static String status; 

    public static void setNip(String nip) {
        UserSession.nip = nip;
    }

    public static String getNip() {
        return nip;
    }

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

    public static void setStatus(String status) {
        UserSession.status = status;
    }

    public static String getStatus() {
        return status;
    }
}