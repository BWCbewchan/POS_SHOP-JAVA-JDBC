package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import ConnectDB.KetNoiSQL;
import entity.MauSac;
import entity.NhanVien;
import entity.TaiKhoan;

public class loginDAO {
	ArrayList<TaiKhoan> dstk = new ArrayList<>();
	TaiKhoan tk;

	public List<TaiKhoan> getTaiKhoan(String tk, String mk) {
		KetNoiSQL.getInstance();
		Connection con = KetNoiSQL.getConnection();
		try {
			String sql = "Select tenTaiKhoan, matKhau, nv.chucVu, nv.tenNV, nv.trangThai from TaiKhoan tk "
					+ "join NhanVien nv on tk.tenTaiKhoan = nv.maNV where tk.tenTaiKhoan = ? and matKhau = ?";
			PreparedStatement stmt = con.prepareCall(sql);
			stmt.setString(1, tk);
			stmt.setString(2, mk);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String tenTK = rs.getString(1);
				String matKhau = rs.getString(2);
				boolean chucVu = rs.getBoolean(3);
				String tenNV = rs.getString(4);
				int trangThai = rs.getInt(5);
				TaiKhoan tk1 = new TaiKhoan(tenTK, matKhau, new NhanVien(tenNV, chucVu, trangThai));
				dstk.add(tk1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (dstk.isEmpty()) {
			return null;
		}
		return dstk;
	}
}
