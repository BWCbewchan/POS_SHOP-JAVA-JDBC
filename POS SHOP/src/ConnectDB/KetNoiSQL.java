package ConnectDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KetNoiSQL {
    private static Connection conn = null;
    private static KetNoiSQL instance = new KetNoiSQL();
    
    public static KetNoiSQL getInstance(){  
        return instance;
    }
    
     public void connect(){
        String url = "jdbc:sqlserver://localhost:1433;database=Postshop";
        String username = "sa";
        String password = "sapassword";
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException ex) {
            Logger.getLogger(KetNoiSQL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(KetNoiSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void disconect() {
    	if(conn != null) {
    		try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
    public static Connection getConnection(){
        return conn;
    }

}