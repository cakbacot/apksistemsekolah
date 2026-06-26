/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package koneksi;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author ACER
 */
public class koneksi {
public static Connection con;
    public static Statement stm;
    
    private static Connection connection = null;
    
        public static Connection getConnection(){
            if (connection == null) {
                MysqlDataSource data = new MysqlDataSource();
                data.setDatabaseName("dbsekolah");
                data.setUser("root");
                data.setPassword("");
                try{
                    connection = data.getConnection();
                    System.out.println("Koneksi Sukses");
                } catch (SQLException se) {
                    System.out.println("Koneksi gagal " + se);
                }
            }
            return connection;         
        }
    }
    
