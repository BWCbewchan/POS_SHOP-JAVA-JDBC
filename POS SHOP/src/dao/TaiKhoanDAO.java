package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import ConnectDB.KetNoiSQL;
import entity.NhaCungCap;
import entity.NhanVien;
import entity.TaiKhoan;

public class TaiKhoanDAO {
	ArrayList<TaiKhoan> dstk;
	TaiKhoan tk;

	public TaiKhoanDAO() {
		dstk = new ArrayList<TaiKhoan>();
	}
	public ArrayList<TaiKhoan> getAllTaiKhoan() {
		ArrayList<TaiKhoan> listTK = new ArrayList<>();
		KetNoiSQL.getInstance().connect();;
		Connection conn = KetNoiSQL.getConnection();

		try {
			String sql = "select tenTaiKhoan,matKhau,tenNV,chucVu from TaiKhoan tk join NhanVien nv on tk.tenTaiKhoan = nv.maNV";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String tenTK = rs.getString(1);
				String mk = rs.getString(2);
				NhanVienDAO nhanVienDAO = new NhanVienDAO();
				NhanVien nv = nhanVienDAO.getNhanVienByID(tenTK);
				TaiKhoan tk = new TaiKhoan(tenTK, mk, nv);
				listTK.add(tk);
			}

		} catch (SQLException ex) {
			Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
		return listTK;
	}
	public ArrayList<TaiKhoan> getAllTaiKhoanTimKiem(String timkiem){
        KetNoiSQL.getInstance();
        Connection conn = KetNoiSQL.getConnection();
        ArrayList<TaiKhoan> listTK = new ArrayList<>();
        try {
            String sql = "select tenTaiKhoan,matKhau,tenNV,chucVu from TaiKhoan tk join NhanVien nv on tk.tenTaiKhoan = nv.maNV where tenTaiKhoan like ? or tenNV like ?";
            PreparedStatement stmt = conn.prepareCall(sql);
            stmt.setString(1, "%"+timkiem+"%");
            stmt.setString(2, "%"+timkiem+"%");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
            	String tenTK = rs.getString(1);
				String mk = rs.getString(2);
				NhanVienDAO nhanVienDAO = new NhanVienDAO();
				NhanVien nv = nhanVienDAO.getNhanVienByID(tenTK);
				TaiKhoan tk = new TaiKhoan(tenTK, mk, nv);
				listTK.add(tk);
            }
        } catch (SQLException ex) {
            Logger.getLogger(XuatXuDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listTK;
    }

	// Lấy email nhân viên theo tên tài khoản
	public String getEmailTheoTenTaiKhoan(String tenTK) {
		try {
			KetNoiSQL.getInstance().connect();
			PreparedStatement stmt = null;
			Connection con = KetNoiSQL.getInstance().getConnection();
			String sql = "Select nv.email from TaiKhoan tk join NhanVien nv on tk.tenTaiKhoan = nv.maNV\r\n"
					+ "where tk.tenTaiKhoan = ?";
			stmt = con.prepareStatement(sql); // Thực thi câu lệnh SQL trả về ResulSet.
			stmt.setString(1, tenTK);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				return rs.getString("email");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// Cập nhật mật khẩu mới cho tài khoản
	public boolean updateMatKhau(String matKhau, String tenTK) {
		KetNoiSQL.getInstance().connect();
		Connection con = KetNoiSQL.getInstance().getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			stmt = con.prepareStatement("update TaiKhoan set matKhau = ? where tenTaiKhoan = ?");
			stmt.setString(1, matKhau);
			stmt.setString(2, tenTK);
			n = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n > 0;
	}
	public int addTaiKhoan(String tk, String mk){
        KetNoiSQL.getInstance();
        Connection conn = KetNoiSQL.getConnection();
        try {
            String sql = "insert into TaiKhoan(tenTaiKhoan,matKhau)"
                + "                 values(?, ?)";

            PreparedStatement stmt = conn.prepareCall(sql);
            stmt.setString(1, tk);
            stmt.setString(2, mk);
            return stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

	// Kiểm tra mật khẩu đã tồn tại
	public String getTenTaiKhoanTheoMatKhau(String matKhau) {
		try {
			KetNoiSQL.getInstance().connect();
			PreparedStatement stmt = null;
			Connection con = KetNoiSQL.getInstance().getConnection();
			String sql = "select tenTaiKhoan from TaiKhoan where matKhau = ?";
			stmt = con.prepareStatement(sql); // Thực thi câu lệnh SQL trả về ResulSet.
			stmt.setString(1, matKhau);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				return rs.getString("tenTaiKhoan");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
