package net.dallascricket.scheduleLoader.db.connector;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JtdsSqlExpressInstanceConnect {
    public static void main(String[] args) throws SQLException {
/*        Connection conn = null;
        ResultSet rs = null;
        String url = "jdbc:jtds:sqlserver://sql2k802.discountasp.net;instance=SQLEXPRESS;DatabaseName=SQL2008_739130_dallascrick";
        String driver = "net.sourceforge.jtds.jdbc.Driver";
        String userName = "user2";
        String password = "dcl123";
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, userName, password);
            System.out.println("Connected to the database!!! Getting table list...");
            DatabaseMetaData dbm = conn.getMetaData();
            rs = dbm.getTables(null, null, "%", new String[] { "TABLE" });
            while (rs.next()) { System.out.println(rs.getString("TABLE_NAME")); }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.close();
            rs.close();
        }*/
    	
    	
        Connection conn = null;
        ResultSet rs = null;
        String url = "jdbc:jtds:sqlserver://localhost;integratedSecurity=true;instance=SQLEXPRESS;DatabaseName=TestDB1";
        String driver = "net.sourceforge.jtds.jdbc.Driver";
        String userName = "user2";
        String password = "dcl123";
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url);
            System.out.println("Connected to the database!!! Getting table list...");
            DatabaseMetaData dbm = conn.getMetaData();
            rs = dbm.getTables(null, null, "%", new String[] { "TABLE" });
            while (rs.next()) { System.out.println(rs.getString("TABLE_NAME")); }
            
            
          
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.close();
            rs.close();
        }
    }
}