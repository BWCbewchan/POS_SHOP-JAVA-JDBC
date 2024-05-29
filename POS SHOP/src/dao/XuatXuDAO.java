package dao;

import ConnectDB.KetNoiSQL;
import entity.XuatXu;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class XuatXuDAO {
    
    public XuatXuDAO() {
    }
    
    public ArrayList<XuatXu>getAllXuatXu(){
        ArrayList<XuatXu>listXuatXu = new ArrayList<>();
        KetNoiSQL.getInstance().connect();
        Connection conn = KetNoiSQL.getConnection();
        
        try {
            String sql = "select * from XuatXu";
            Statement stmt= conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                XuatXu xuatXu = new XuatXu();
                xuatXu.setMaXuatXu(rs.getString(1));
                xuatXu.setXuatXu(rs.getString(2));
                listXuatXu.add(xuatXu);
            }
        } catch (SQLException ex) {
            Logger.getLogger(XuatXuDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listXuatXu;
    }
    
    public XuatXu getXuatXu(String id){
        KetNoiSQL.getInstance();
        Connection conn = KetNoiSQL.getConnection();
        
        try {
        String sql = "select * from XuatXu where maXX = (?)";
        PreparedStatement stmt = conn.prepareCall(sql);
        stmt.setString(1, id);
        ResultSet rs = stmt.executeQuery();
        while(rs.next()){
            XuatXu xuatXu = new XuatXu();
            xuatXu.setMaXuatXu(rs.getString(1));
            xuatXu.setXuatXu(rs.getString(2));
            return xuatXu;
        }
        } catch (SQLException ex) {
            Logger.getLogger(XuatXuDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public int updateXuatXu(XuatXu xuatXu){
        KetNoiSQL.getInstance();
        Connection conn = KetNoiSQL.getConnection();
        
        try {
            String sql = "update XuatXu set xuatXu = (?) where maXX = ?";
            PreparedStatement stmt = conn.prepareCall(sql);
            stmt.setString(1, xuatXu.getXuatXu());
            stmt.setString(2, xuatXu.getMaXuatXu());
            return stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(XuatXuDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    
    public int addXuatXu(XuatXu xuatXu){
        KetNoiSQL.getInstance();
        Connection conn = KetNoiSQL.getConnection();    
        try {
            String sql = "insert into XuatXu(maXX, xuatXu) values (?, ?)";
            PreparedStatement stmt = conn.prepareCall(sql);
            stmt.setString(1, xuatXu.getMaXuatXu());
            stmt.setString(2, xuatXu.getXuatXu());
            return stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(XuatXuDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
        public XuatXu getXuatXuByName(String name){
        KetNoiSQL.getInstance().connect();
        Connection conn = KetNoiSQL.getConnection();
        
        try {
        String sql = "select * from XuatXu where xuatXu = ?";
        PreparedStatement stmt = conn.prepareCall(sql);
        stmt.setString(1, name);
        ResultSet rs = stmt.executeQuery();
        while(rs.next()){
            XuatXu xuatXu = new XuatXu();
            xuatXu.setMaXuatXu(rs.getString(1));
            xuatXu.setXuatXu(rs.getString(2));
            return xuatXu;
        }
        } catch (SQLException ex) {
            Logger.getLogger(XuatXuDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
