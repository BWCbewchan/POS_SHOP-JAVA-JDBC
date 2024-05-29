package dao;

import ConnectDB.KetNoiSQL;
import entity.NhaCungCap;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NhaCungCapDAO {

    public NhaCungCapDAO() {
    }
    
    public ArrayList<NhaCungCap> getAllNhaCungCap(){
        ArrayList<NhaCungCap>listNhaCungCap = new ArrayList<>();
        KetNoiSQL.getInstance().connect();
            Connection conn = KetNoiSQL.getConnection(); 
        try {
            String sql = "Select * from NhaCungCap";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                String maNhaCungCap = rs.getString(1);
                String tenNhaCungCap =  rs.getString(2);
                String sdt = rs.getString(3);
                String email = rs.getString(4);
                String diaChi = rs.getString(5);
                NhaCungCap nhaCungCap = new NhaCungCap();
                nhaCungCap.setMaNCC(maNhaCungCap);
                nhaCungCap.setTenNCC(tenNhaCungCap);
                nhaCungCap.setDiaChi(diaChi);
                nhaCungCap.setEmail(email);
                nhaCungCap.setSdt(sdt);
                listNhaCungCap.add(nhaCungCap);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MauSacDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listNhaCungCap;
    }
    
    public NhaCungCap getNhaCungCap(String id){
        KetNoiSQL.getInstance();
        Connection conn = KetNoiSQL.getConnection();
        
        try {
            String sql = "select * from NhaCungCap where maNCC = ?";
            PreparedStatement stmt = conn.prepareCall(sql);
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                NhaCungCap nhaCungCap = new NhaCungCap();
                nhaCungCap.setMaNCC(rs.getString(1));
                nhaCungCap.setTenNCC(rs.getString(2));
                nhaCungCap.setDiaChi(rs.getString(3));
                nhaCungCap.setEmail(rs.getString(4));
                nhaCungCap.setSdt(rs.getString(5));
                return nhaCungCap;
            }
        } catch (SQLException ex) {
            Logger.getLogger(XuatXuDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public ArrayList<NhaCungCap> getNhaCungCapByTen(String tenNhaCungCap){
        KetNoiSQL.getInstance();
        Connection conn = KetNoiSQL.getConnection();
        ArrayList<NhaCungCap> listNhaCungCap = new ArrayList<>();
        try {
            String sql = "select * from NhaCungCap where tenNCC like ?";
            PreparedStatement stmt = conn.prepareCall(sql);
            stmt.setString(1, "%"+tenNhaCungCap+"%");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                NhaCungCap nhaCungCap = new NhaCungCap();
                nhaCungCap.setMaNCC(rs.getString(1));
                nhaCungCap.setTenNCC(rs.getString(2));
                nhaCungCap.setDiaChi(rs.getString(3));
                nhaCungCap.setEmail(rs.getString(4));
                nhaCungCap.setSdt(rs.getString(5));
                listNhaCungCap.add(nhaCungCap);
            }
        } catch (SQLException ex) {
            Logger.getLogger(XuatXuDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listNhaCungCap;
    }
    public ArrayList<NhaCungCap> timKiemNCC(String tenNhaCungCap){
        KetNoiSQL.getInstance();
        Connection conn = KetNoiSQL.getConnection();
        ArrayList<NhaCungCap> listNhaCungCap = new ArrayList<>();
        try {
            String sql = "select * from NhaCungCap where maNCC like ? or tenNCC like ? or SDT like ? or email like ?";
            PreparedStatement stmt = conn.prepareCall(sql);
            stmt.setString(1, "%"+tenNhaCungCap+"%");
            stmt.setString(2, "%"+tenNhaCungCap+"%");
            stmt.setString(3, "%"+tenNhaCungCap+"%");
            stmt.setString(4, "%"+tenNhaCungCap+"%");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                NhaCungCap nhaCungCap = new NhaCungCap();
                nhaCungCap.setMaNCC(rs.getString(1));
                nhaCungCap.setTenNCC(rs.getString(2));
                nhaCungCap.setDiaChi(rs.getString(5));
                nhaCungCap.setEmail(rs.getString(4));
                nhaCungCap.setSdt(rs.getString(3));
                listNhaCungCap.add(nhaCungCap);
            }
        } catch (SQLException ex) {
            Logger.getLogger(XuatXuDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listNhaCungCap;
    }
    
    public int updateNhaCungCap(NhaCungCap nhaCungCap){
        KetNoiSQL.getInstance();
        Connection conn = KetNoiSQL.getConnection();
        
        try {
            String sql = "update NhaCungCap set tenNCC = ?,"
                    + "                          diaChi = ?,"
                    + "                          email = ?,"
                    + "                          sdt = ?"
                    + " where maNCC = ?";
            PreparedStatement stmt = conn.prepareCall(sql);
            stmt.setString(1, nhaCungCap.getTenNCC());
            stmt.setString(2, nhaCungCap.getDiaChi());
            stmt.setString(3, nhaCungCap.getEmail());
            stmt.setString(4, nhaCungCap.getSdt());
            stmt.setString(5, nhaCungCap.getMaNCC());
            return stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(XuatXuDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    
    public int addNhaCungCap(NhaCungCap nhaCungCap){
        KetNoiSQL.getInstance();
        Connection conn = KetNoiSQL.getConnection();    
        try {
            String sql = "insert into NhaCungCap(maNCC, tenNCC, SDT, email, diaChi) values (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareCall(sql);
            stmt.setString(1, nhaCungCap.getMaNCC());
            stmt.setString(2, nhaCungCap.getTenNCC());
            stmt.setString(5, nhaCungCap.getDiaChi());
            stmt.setString(4, nhaCungCap.getEmail());
            stmt.setString(3, nhaCungCap.getSdt());
            return stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(XuatXuDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    } 
    public int editNhaCungCap(NhaCungCap nhaCungCap){
        KetNoiSQL.getInstance();
        Connection conn = KetNoiSQL.getConnection();    
        try {
            String sql = "update NhaCungCap set tenNCC = ? , SDT = ?, email = ?,diaChi = ?  where maNCC = ? ";
            PreparedStatement stmt = conn.prepareCall(sql);
            
            stmt.setString(1, nhaCungCap.getTenNCC());
            stmt.setString(4, nhaCungCap.getDiaChi());
            stmt.setString(3, nhaCungCap.getEmail());
            stmt.setString(2, nhaCungCap.getSdt());
            
            stmt.setString(5, nhaCungCap.getMaNCC());
            return stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(XuatXuDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    } 
        public NhaCungCap getNhaCungCapByName(String name){
        KetNoiSQL.getInstance();
        Connection conn = KetNoiSQL.getConnection();
        
        try {
        String sql = "select * from nhacungcap where tenNCC = ?";
        PreparedStatement stmt = conn.prepareCall(sql);
        stmt.setString(1, name);
        ResultSet rs = stmt.executeQuery();
        while(rs.next()){
            NhaCungCap nhaCungCap = new NhaCungCap();
            nhaCungCap.setMaNCC(rs.getString(1));
            nhaCungCap.setTenNCC(rs.getString(2));
            nhaCungCap.setDiaChi(rs.getString(3));
            nhaCungCap.setEmail(rs.getString(4));
            nhaCungCap.setSdt(rs.getString(5));
            
            return nhaCungCap;
        }
        } catch (SQLException ex) {
            Logger.getLogger(XuatXuDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
