/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tampilan;

public class UserSession {
    private static String level;
    private static String nama;

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
}

