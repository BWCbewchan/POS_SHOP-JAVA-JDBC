package dao;

import ConnectDB.KetNoiSQL;
import entity.ChatLieu;
import entity.KhuyenMai;
import entity.KichThuoc;
import entity.MauSac;
import entity.NhaCungCap;
import entity.NhanVien;
import entity.PhanLoai;
import entity.SanPham;
import entity.TaiKhoan;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NhanVienDAO {
	private static ArrayList<NhanVien> dsNV;

	public NhanVienDAO() {
		dsNV = new ArrayList<NhanVien>(10);
	}
	

	public ArrayList<NhanVien> getAllNhanVien() {
		ArrayList<NhanVien> listNhanVien = new ArrayList<>();
		KetNoiSQL.getInstance().connect();;
		Connection conn = KetNoiSQL.getConnection();

		try {
			String sql = "Select * from nhanvien ";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String manv = rs.getString(1);
				String tennv = rs.getString(2);
				Date ns = rs.getDate(3);
				String sdt = rs.getString(4);
				String email = rs.getString(5);
				String cmnd = rs.getString(6);
				boolean gioitinh = rs.getBoolean(7);
				String dc = rs.getString(8);
				boolean cv = rs.getBoolean(9);
				int trangthai = rs.getInt(10);
				NhanVien nv = new NhanVien(manv ,tennv,ns,sdt,email,cmnd,gioitinh,dc,cv,trangthai);
				listNhanVien.add(nv);
			}

		} catch (SQLException ex) {
			Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
		return listNhanVien;
	}
	public NhanVien getNhanVienTheoTen(String name) {
		KetNoiSQL.getInstance();
		Connection conn = KetNoiSQL.getConnection();
		try {

			String sql = "select * from nhanvien where tenNV like (?)";
			PreparedStatement stmt = conn.prepareCall(sql);
			stmt.setString(1, name);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				NhanVien nv = new NhanVien();
				nv.setMaNV(rs.getString(1));
				nv.setTenNV(rs.getString(2));
				nv.setNgaySinh(rs.getDate(3));
				nv.setSDT(rs.getString(4));
				nv.setEmail(rs.getString(5));
				nv.setCMND(rs.getString(6));
				nv.setGioiTinh(rs.getBoolean(7));
				nv.setDiaChi(rs.getString(8));
				nv.setChucVu(rs.getBoolean(9));
				nv.setTrangThai(rs.getInt(10));
				return nv;
			}
		} catch (SQLException ex) {
			Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}
	public ArrayList<NhanVien> getAllNhanVienConHoatDong() {
		ArrayList<NhanVien> listNhanVien = new ArrayList<>();
		KetNoiSQL.getInstance();
		Connection conn = KetNoiSQL.getConnection();

		try {
			String sql = "Select * from nhanvien where trangThai = 0";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String manv = rs.getString(1);
				String tennv = rs.getString(2);
				Date ns = rs.getDate(3);
				String sdt = rs.getString(4);
				String email = rs.getString(5);
				String cmnd = rs.getString(6);
				boolean gioitinh = rs.getBoolean(7);
				String dc = rs.getString(8);
				boolean cv = rs.getBoolean(9);
				int trangthai = rs.getInt(10);
				NhanVien nv = new NhanVien(manv ,tennv,ns,sdt,email,cmnd,gioitinh,dc,cv,trangthai);
				listNhanVien.add(nv);
			}

		} catch (SQLException ex) {
			Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
		return listNhanVien;
	}
	public ArrayList<NhanVien> getAllNhanVienNgungLam() {
		ArrayList<NhanVien> listNhanVien = new ArrayList<>();
		KetNoiSQL.getInstance();
		Connection conn = KetNoiSQL.getConnection();

		try {
			String sql = "  Select * from nhanvien where trangThai = 1";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String manv = rs.getString(1);
				String tennv = rs.getString(2);
				Date ns = rs.getDate(3);
				String sdt = rs.getString(4);
				String email = rs.getString(5);
				String cmnd = rs.getString(6);
				boolean gioitinh = rs.getBoolean(7);
				String dc = rs.getString(8);
				boolean cv = rs.getBoolean(9);
				int trangthai = rs.getInt(10);
				NhanVien nv = new NhanVien(manv ,tennv,ns,sdt,email,cmnd,gioitinh,dc,cv,trangthai);
				listNhanVien.add(nv);
			}

		} catch (SQLException ex) {
			Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
		return listNhanVien;
	}

	public NhanVien getNhanVienByID(String id) {
		KetNoiSQL.getInstance();
		Connection conn = KetNoiSQL.getConnection();
		try {

			String sql = "select * from nhanvien where maNV = (?)";
			PreparedStatement stmt = conn.prepareCall(sql);
			stmt.setString(1, id);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String manv = rs.getString(1);
				String tennv = rs.getString(2);
				Date ns = rs.getDate(3);
				String sdt = rs.getString(4);
				String email = rs.getString(5);
				String cmnd = rs.getString(6);
				boolean gioitinh = rs.getBoolean(7);
//				double gioitinh = rs.getDouble(7);
				String dc = rs.getString(8);
				boolean cv = rs.getBoolean(9);
				int trangthai = rs.getInt(10);
				NhanVien nv = new NhanVien(manv, tennv, ns, sdt, email, cmnd, gioitinh, dc, cv, trangthai);
				return nv;
			}
		} catch (SQLException ex) {
			Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}

	public NhanVien getNhanVienByName(String name) {
		KetNoiSQL.getInstance();
		Connection conn = KetNoiSQL.getConnection();
		try {
			String sql = "SELECT * FROM nhanvien WHERE tenNV COLLATE Vietnamese_CI_AI = ?";
			PreparedStatement stmt = conn.prepareCall(sql);
			stmt.setString(1,name);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String manv = rs.getString(1);
				String tennv = rs.getString(2);
				Date ns = rs.getDate(3);
				String sdt = rs.getString(4);
				String email = rs.getString(5);
				String cmnd = rs.getString(6);
				boolean gioitinh = rs.getBoolean(7);
				String dc = rs.getString(8);
				boolean cv = rs.getBoolean(9);
				int trangthai = rs.getInt(10);
				NhanVien nv = new NhanVien(manv, tennv, ns, sdt, email, cmnd, gioitinh, dc, cv, trangthai);
				return nv;
			}
		} catch (SQLException ex) {
			Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}

	public NhanVien getNhanVienBySdt(String sdt) {
		KetNoiSQL.getInstance();
		Connection conn = KetNoiSQL.getConnection();

		try {
			String sql = "select * from nhanvien where sdt = ?";
			PreparedStatement stmt = conn.prepareCall(sql);
			stmt.setString(1, sdt);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String manv = rs.getString(1);
				String tennv = rs.getString(2);
				Date ns = rs.getDate(3);
				String sdtt = rs.getString(4);
				String email = rs.getString(5);
				String cmnd = rs.getString(6);
				double gioitinh = rs.getDouble(7);
				String dc = rs.getString(8);
				boolean cv = rs.getBoolean(9);
				int trangthai = rs.getInt(10);
				NhanVien nv = new NhanVien();
				return nv;
			}
		} catch (SQLException ex) {
			Logger.getLogger(XuatXuDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}

	public NhanVien getNhanVienByGmail(String gmail) {
		KetNoiSQL.getInstance();
		Connection conn = KetNoiSQL.getConnection();
		try {

			String sql = "select * from nhanvien where email = ?";
			PreparedStatement stmt = conn.prepareCall(sql);
			stmt.setString(1, gmail);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String manv = rs.getString(1);
				String tennv = rs.getString(2);
				Date ns = rs.getDate(3);
				String sdt = rs.getString(4);
				String email = rs.getString(5);
				String cmnd = rs.getString(6);
				double gioitinh = rs.getDouble(7);
				String dc = rs.getString(8);
				boolean cv = rs.getBoolean(9);
				int trangthai = rs.getInt(10);
				NhanVien nv = new NhanVien();
				return nv;
			}
		} catch (SQLException ex) {
			Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}

	
	public static boolean themnhanvien(NhanVien nv) {
		// TODO Auto-generated method stub
		return false;
	}

	public static boolean capNhatnhanvien(String maNVOld, NhanVien NVnew) {
		NhanVien nhanvienOld = new NhanVien(maNVOld);
		if(dsNV.contains(nhanvienOld)){
			nhanvienOld = dsNV.get(dsNV.indexOf(nhanvienOld));
			nhanvienOld.setTenNV(NVnew.getTenNV());
			nhanvienOld.setMaNV(NVnew.getMaNV());
			nhanvienOld.setDiaChi(NVnew.getDiaChi());
			nhanvienOld.setEmail(NVnew.getEmail());
			nhanvienOld.setNgaySinh(NVnew.getNgaySinh());
			return true;
		}
		return false;
}


    
//    
    public int updateOTP(String gmail, String OTP, Timestamp expiredAt){
        KetNoiSQL.getInstance();
        Connection conn = KetNoiSQL.getConnection();
        try {
           
            String sql = "update nhanvien set OTP = ?, expriedAt = ? where email = ?";
            PreparedStatement stmt = conn.prepareCall(sql);
            stmt.setString(1, OTP);
            stmt.setTimestamp(2, expiredAt);
            stmt.setString(3, gmail);
            
            return stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    } 
//    
//    public int xoaNhanVien(String maNhanVien){
//        KetNoiSQL.getInstance();
//        Connection conn = KetNoiSQL.getConnection();
//        try {
//           
//            String sql = "update nhanvien set isDeleted = 1 where maNhanVien = ?";
//            PreparedStatement stmt = conn.prepareCall(sql);
//            stmt.setString(1, maNhanVien); 
//            
////            if(taiKhoan_DAO.xoaTaiKhoan(maNhanVien) != -1);
//            
//            return stmt.executeUpdate();
//        } catch (SQLException ex) {
//            Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return -1;
//
//    }
//    
    public int addNhanVien(NhanVien nhanVien){
        KetNoiSQL.getInstance();
        Connection conn = KetNoiSQL.getConnection();
        try {
            String sql = "insert into nhanvien(maNV, tenNV, ngaySinh, diaChi, sdt, gioiTinh,email, chucVu,trangThai,CMND)"
                + "                 values(?, ?, ?, ?, ?, ?, ?, ? ,?,?)";

            PreparedStatement stmt = conn.prepareCall(sql);
            stmt.setString(1, nhanVien.getMaNV());
            stmt.setString(2, nhanVien.getTenNV());
            stmt.setDate(3, new Date(nhanVien.getNgaySinh().getTime()));
            stmt.setString(4, nhanVien.getDiaChi());
            stmt.setString(5, nhanVien.getSDT());
            stmt.setBoolean(6, nhanVien.isGioiTinh());
           
            stmt.setString(7, nhanVien.getEmail());
            stmt.setBoolean(8, nhanVien.isChucVu());
            System.out.println(nhanVien.getTrangThai());
            stmt.setInt(9, nhanVien.getTrangThai());
            stmt.setString(10, nhanVien.getCMND());
            return stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    
    public int updateNhanVien(NhanVien nhanVien){
        KetNoiSQL.getInstance();
        Connection conn = KetNoiSQL.getConnection();
        System.out.println(nhanVien);
        try {
            String sql = "update nhanvien set tenNV = ?,ngaySinh = ?,sdt = ?,  email = ?, CMND = ?, gioiTinh = ?,diaChi = ?,chucVu = ?, trangThai = ? where maNV = ?";
            
            PreparedStatement stmt = conn.prepareCall(sql);
            
            stmt.setString(1, nhanVien.getTenNV());
            stmt.setDate(2, new Date(nhanVien.getNgaySinh().getTime()));
            stmt.setString(3, nhanVien.getSDT());
            stmt.setString(4, nhanVien.getEmail());
            stmt.setString(5, nhanVien.getCMND());
            stmt.setBoolean(6, nhanVien.isGioiTinh());
            stmt.setString(7, nhanVien.getDiaChi());
            stmt.setBoolean(8, nhanVien.isChucVu());
            stmt.setInt(9, nhanVien.getTrangThai());
            stmt.setString(10, nhanVien.getMaNV());
            return stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    
    
    
    
    public ArrayList<NhanVien> timnv(String manvtensdt,boolean gioitinh ,boolean chucvu) {
    	ArrayList<NhanVien> listnv = new ArrayList<>();
		KetNoiSQL.getInstance().connect();
		Connection conn = KetNoiSQL.getConnection();
		try {

			String sql = "select * from nhanvien where trangThai = 0 and maNV like (?) or tenNV like (?) or SDT like (?) or (gioitinh =(?) and chucVu=(?))";
			PreparedStatement stmt = conn.prepareCall(sql);
			stmt.setString(1, "%" +manvtensdt+"%");
			stmt.setString(2, "%" +manvtensdt+"%");
			stmt.setString(3, "%" +manvtensdt+"%");
			stmt.setBoolean(4,  gioitinh );
			stmt.setBoolean(5,  chucvu );
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				String manv = rs.getString(1);
				String tennv = rs.getString(2);
				Date ns = rs.getDate(3);
				String sdt = rs.getString(4);
				String email = rs.getString(5);
				String cmnd = rs.getString(6);
				boolean gioitinh1 = rs.getBoolean(7);
				String dc = rs.getString(8);
				boolean cv = rs.getBoolean(9);
				int trangthai = rs.getInt(10);
				NhanVien nv = new NhanVien(manv ,tennv,ns,sdt,email,cmnd,gioitinh1,dc,cv,trangthai);
				listnv.add(nv);
			}
			 
		} catch (SQLException ex) {
			Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
		return listnv;
	}
    public int updateMatKhau(String matKhau, String taiKhoan){
        KetNoiSQL.getInstance();
        Connection conn = KetNoiSQL.getConnection();
        
        try {
            String sql = "update taikhoan set matkhau = ?  where tenTaiKhoan = ?";
            
            PreparedStatement stmt = conn.prepareCall(sql);
            
            stmt.setString(1, matKhau);
            stmt.setString(2, taiKhoan);
            
            return stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    
}