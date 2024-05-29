package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ConnectDB.KetNoiSQL;
import entity.ChatLieu;
import entity.ChiTietHoaDon;
import entity.HoaDon;
import entity.KhachHang;
import entity.KhuyenMai;
import entity.KichThuoc;
import entity.KieuDang;
import entity.MauSac;
import entity.NhaCungCap;
import entity.NhanVien;
import entity.PhanLoai;
import entity.SanPham;
import entity.XuatXu;

public class HoaDonDAO {
	ArrayList<HoaDon> dshd;
	HoaDon hd;
	KhachHangDAO khachHangDAO = new KhachHangDAO();
	NhanVienDAO nhanVienDAO = new NhanVienDAO();

	public HoaDonDAO() {
		KetNoiSQL.getInstance().connect();
		dshd = new ArrayList<HoaDon>();
	}

	// Lấy danh sách thông tin hóa hóa đơn từ SQL
	public List<HoaDon> doTuBang() {
		try {
			Connection con = KetNoiSQL.getInstance().getConnection();
			String sql = "select hd.maHD, hd.ngayLap, hd.maNV, nv.tenNV, hd.maKH, kh.tenKH, hd.tongTien\r\n"
					+ "from HoaDon hd join NhanVien nv on hd.maNV = nv.maNV\r\n"
					+ "join KhachHang kh on hd.maKH = kh.maKH where hd.trangThai = 1";
			Statement statement = con.createStatement(); // Thực thi câu lệnh SQL trả về ResulSet.
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				String maHD = rs.getString(1);
				Date ngayLap = rs.getDate(2);
				String maNV = rs.getString(3);
				String tenNV = rs.getString(4);
				String maKH = rs.getString(5);
				String tenKH = rs.getString(6);
				Double tongTien = rs.getDouble(7);
				HoaDon hd = new HoaDon(maHD, ngayLap, new KhachHang(maKH, tenKH, null, null, false),
						new NhanVien(maNV, tenNV, null, null, null, null, false, null, false, 0), 0, tongTien);
				dshd.add(hd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dshd;
	}

	public List<HoaDon> fullhoadon() {
		try {
			Connection con = KetNoiSQL.getInstance().getConnection();
			String sql = "select * from HoaDon";
			Statement statement = con.createStatement(); // Thực thi câu lệnh SQL trả về ResulSet.
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				String maHD = rs.getString(1);
				Date ngayLap = rs.getDate(2);
				String maNV = rs.getString(3);
				String maKH = rs.getString(5);
				int trangthai = rs.getInt(6);
				NhanVien nv = nhanVienDAO.getNhanVienByID(maNV);
				KhachHang kh = khachHangDAO.getKhachHang(maKH);
				HoaDon hd = new HoaDon(maHD, ngayLap, kh, nv, trangthai, trangthai);
				dshd.add(hd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dshd;
	}

	public HoaDon getHDTheoId(String id) {
		try {
			KetNoiSQL.getInstance().connect();
			Connection con = KetNoiSQL.getInstance().getConnection();
			String sql = "select * from HoaDon where maHD =(?)";
			PreparedStatement stmt = con.prepareCall(sql);
			stmt.setString(1, id);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				String mahd = rs.getString(1);
				Date ngaylap = rs.getDate(2);
				String makh = rs.getString(3);
				String manv = rs.getString(4);
				KhachHang kh = khachHangDAO.getKhachHang(makh);
				NhanVien nv = nhanVienDAO.getNhanVienByID(manv);
				HoaDon hd = new HoaDon(mahd, ngaylap, kh, nv);
				return hd;
			}

			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// Tìm kiếm hóa đơn theo từ khóa (maHD, maNV, tenNV, maKH, tenKH)
	public List<HoaDon> getHoaDonTheoTuKhoa(String tukhoa) {
		try {
			KetNoiSQL.getInstance().connect();
			Connection con = KetNoiSQL.getInstance().getConnection();
			String sql = "select hd.maHD, hd.ngayLap, hd.maNV, nv.tenNV, hd.maKH, kh.tenKH, hd.tongTien\r\n"
					+ "from HoaDon hd join NhanVien nv on hd.maNV = nv.maNV\r\n"
					+ "join KhachHang kh on hd.maKH = kh.maKH where hd.trangThai = 1 and hd.maHD like ? or hd.maNV like ? or nv.tenNV like ? or hd.maKH like ? or kh.tenKH like ?";
			PreparedStatement stmt = con.prepareCall(sql);
			stmt.setString(1, "%" + tukhoa + "%");
			stmt.setString(2, "%" + tukhoa + "%");
			stmt.setString(3, "%" + tukhoa + "%");
			stmt.setString(4, "%" + tukhoa + "%");
			stmt.setString(5, "%" + tukhoa + "%");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String maHD = rs.getString(1);
				Date ngayLap = rs.getDate(2);
				String maNV = rs.getString(3);
				String tenNV = rs.getString(4);
				String maKH = rs.getString(5);
				String tenKH = rs.getString(6);
				Double tongTien = rs.getDouble(7);
				HoaDon hd = new HoaDon(maHD, ngayLap, new KhachHang(maKH, tenKH, null, null, false),
						new NhanVien(maNV, tenNV, null, null, null, null, false, null, false, 0), 0, tongTien);
				dshd.add(hd);
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dshd;
	}

	// Tìm danh sách hóa đơn theo tổng tiền
	public List<HoaDon> getHoaDonTheoTongTien(int giaMin, int giaMax) {
		try {
			KetNoiSQL.getInstance().connect();
			Connection con = KetNoiSQL.getInstance().getConnection();
			String sql = "select hd.maHD, hd.ngayLap, hd.maNV, nv.tenNV, hd.maKH, kh.tenKH, hd.tongTien\r\n"
					+ "From HoaDon hd join ChiTietHoaDon cthd on cthd.maHD = hd.maHD join SanPham sp on cthd.maSP = sp.maSP \r\n"
					+ "join NhanVien nv on hd.maNV = nv.maNV join KhachHang kh on hd.maKH = kh.maKH\r\n"
					+ "WHERE hd.trangThai = 1 and hd.tongTien \r\n"
					+ "BETWEEN ? AND ? GROUP BY hd.maHD, hd.ngayLap, hd.maNV, nv.tenNV, hd.maKH, kh.tenKH, hd.tongTien";
			PreparedStatement stmt = con.prepareCall(sql);
			stmt.setInt(1, giaMin);
			stmt.setInt(2, giaMax);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String maHD = rs.getString(1);
				Date ngayLap = rs.getDate(2);
				String maNV = rs.getString(3);
				String tenNV = rs.getString(4);
				String maKH = rs.getString(5);
				String tenKH = rs.getString(6);
				Double tongTien = rs.getDouble(7);
				HoaDon hd = new HoaDon(maHD, ngayLap, new KhachHang(maKH, tenKH, null, null, false),
						new NhanVien(maNV, tenNV, null, null, null, null, false, null, false, 0), 0, tongTien);
				dshd.add(hd);
			}
		} catch (SQLException e) {
			// TODO: handle exception
		}
		return dshd;
	}

	public List<HoaDon> getHDCho() {
		try {
			KetNoiSQL.getInstance().connect();
			Connection con = KetNoiSQL.getInstance().getConnection();
			String sql = "select * from HoaDon where trangthai = 0";
			Statement statement = con.createStatement(); // Thực thi câu lệnh SQL trả về ResulSet.
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				String mahd = rs.getString(1);
				Date ngaylap = rs.getDate(2);
				String makh = rs.getString(3);
				String manv = rs.getString(4);
				KhachHang kh = khachHangDAO.getKhachHang(makh);
				NhanVien nv = nhanVienDAO.getNhanVienByID(manv);
				HoaDon hd = new HoaDon(mahd, ngaylap, kh, nv);
				dshd.add(hd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dshd;
	}

	public int addHoaDon(HoaDon hoaDon) {
		KetNoiSQL.getInstance().connect();
		try {
			Connection con = KetNoiSQL.getInstance().getConnection();
			String sql = "Insert into HoaDon values(?,?,?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(sql);

			ps.setString(1, hoaDon.getAutoID());
			ps.setDate(2, Date.valueOf(hoaDon.getNgayLap().toString()));
			if (hoaDon.getKhachHang() == null) {
				ps.setString(3, "");
			} else {
				ps.setString(3, hoaDon.getKhachHang().getMaKH());
			}

			ps.setString(4, hoaDon.getNhanVien().getMaNV());
			ps.setInt(5, hoaDon.getTrangthai());
			ps.setDouble(6, hoaDon.getTongtien());
			return ps.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return -1;
	}

	// Tìm danh sách hóa đơn theo tháng
	public List<HoaDon> getHoaDonTheoThang(int thang) {
		try {
			KetNoiSQL.getInstance().connect();
			Connection con = KetNoiSQL.getInstance().getConnection();
			String sql = "select hd.maHD, hd.ngayLap, hd.maNV, nv.tenNV, hd.maKH, kh.tenKH, hd.tongTien\r\n"
					+ "from HoaDon hd join NhanVien nv on hd.maNV = nv.maNV\r\n"
					+ "join KhachHang kh on hd.maKH = kh.maKH where hd.trangThai = 1 and MONTH(ngayLap) = ?";
			PreparedStatement stmt = con.prepareCall(sql);
			stmt.setInt(1, thang);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String maHD = rs.getString(1);
				Date ngayLap = rs.getDate(2);
				String maNV = rs.getString(3);
				String tenNV = rs.getString(4);
				String maKH = rs.getString(5);
				String tenKH = rs.getString(6);
				Double tongTien = rs.getDouble(7);
				HoaDon hd = new HoaDon(maHD, ngayLap, new KhachHang(maKH, tenKH, null, null, false),
						new NhanVien(maNV, tenNV, null, null, null, null, false, null, false, 0), 0, tongTien);
				dshd.add(hd);
			}
		} catch (SQLException e) {
			// TODO: handle exception
		}
		return dshd;
	}

	// Tìm danh sách hóa đơn theo năm
	public List<HoaDon> getHoaDonTheoNam(int nam) {
		try {
			KetNoiSQL.getInstance().connect();
			Connection con = KetNoiSQL.getInstance().getConnection();
			String sql = "select hd.maHD, hd.ngayLap, hd.maNV, nv.tenNV, hd.maKH, kh.tenKH, hd.tongTien\r\n"
					+ "from HoaDon hd join NhanVien nv on hd.maNV = nv.maNV\r\n"
					+ "join KhachHang kh on hd.maKH = kh.maKH where hd.trangThai = 1 and YEAR(ngayLap) = ?;";
			PreparedStatement stmt = con.prepareCall(sql);
			stmt.setInt(1, nam);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String maHD = rs.getString(1);
				Date ngayLap = rs.getDate(2);
				String maNV = rs.getString(3);
				String tenNV = rs.getString(4);
				String maKH = rs.getString(5);
				String tenKH = rs.getString(6);
				Double tongTien = rs.getDouble(7);
				HoaDon hd = new HoaDon(maHD, ngayLap, new KhachHang(maKH, tenKH, null, null, false),
						new NhanVien(maNV, tenNV, null, null, null, null, false, null, false, 0), 0, tongTien);
				dshd.add(hd);
			}
		} catch (SQLException e) {
			// TODO: handle exception
		}
		return dshd;
	}

	// Tìm danh sách hóa đơn theo cả tháng và năm
	public List<HoaDon> getHoaDonTheoThangNam(int thang, int nam) {
		try {
			KetNoiSQL.getInstance().connect();
			Connection con = KetNoiSQL.getInstance().getConnection();
			String sql = "select hd.maHD, hd.ngayLap, hd.maNV, nv.tenNV, hd.maKH, kh.tenKH, hd.tongTien\r\n"
					+ "from HoaDon hd join NhanVien nv on hd.maNV = nv.maNV\r\n"
					+ "join KhachHang kh on hd.maKH = kh.maKH where hd.trangThai = 1 and MONTH(hd.ngayLap) = ? and YEAR(ngayLap) = ?;";
			PreparedStatement stmt = con.prepareCall(sql);
			stmt.setInt(1, thang);
			stmt.setInt(2, nam);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String maHD = rs.getString(1);
				Date ngayLap = rs.getDate(2);
				String maNV = rs.getString(3);
				String tenNV = rs.getString(4);
				String maKH = rs.getString(5);
				String tenKH = rs.getString(6);
				Double tongTien = rs.getDouble(7);
				HoaDon hd = new HoaDon(maHD, ngayLap, new KhachHang(maKH, tenKH, null, null, false),
						new NhanVien(maNV, tenNV, null, null, null, null, false, null, false, 0), 0, tongTien);
				dshd.add(hd);
			}
		} catch (SQLException e) {
			// TODO: handle exception
		}
		return dshd;
	}

	// Tìm danh sách hóa đơn theo tổng tiền và tháng
	public List<HoaDon> getHoaDonTheoTongTienThang(int thang, int giaMin, int giaMax) {
		try {
			KetNoiSQL.getInstance().connect();
			Connection con = KetNoiSQL.getInstance().getConnection();
			String sql = "select hd.maHD, hd.ngayLap, hd.maNV, nv.tenNV, hd.maKH, kh.tenKH, hd.tongTien\r\n"
					+ "From HoaDon hd join ChiTietHoaDon cthd on cthd.maHD = hd.maHD join SanPham sp on cthd.maSP = sp.maSP \r\n"
					+ "join NhanVien nv on hd.maNV = nv.maNV join KhachHang kh on hd.maKH = kh.maKH\r\n"
					+ "Where hd.trangThai = 1 and MONTH(hd.ngayLap) = ? AND \r\n" + "hd.tongTien \r\n"
					+ "BETWEEN ? AND ? GROUP BY hd.maHD, hd.ngayLap, hd.maNV, nv.tenNV, hd.maKH, kh.tenKH, hd.tongTien";
			PreparedStatement stmt = con.prepareCall(sql);
			stmt.setInt(1, thang);
			stmt.setInt(2, giaMin);
			stmt.setInt(3, giaMax);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String maHD = rs.getString(1);
				Date ngayLap = rs.getDate(2);
				String maNV = rs.getString(3);
				String tenNV = rs.getString(4);
				String maKH = rs.getString(5);
				String tenKH = rs.getString(6);
				Double tongTien = rs.getDouble(7);
				HoaDon hd = new HoaDon(maHD, ngayLap, new KhachHang(maKH, tenKH, null, null, false),
						new NhanVien(maNV, tenNV, null, null, null, null, false, null, false, 0), 0, tongTien);
				dshd.add(hd);
			}
		} catch (SQLException e) {
			// TODO: handle exception
		}
		return dshd;
	}

	// Tìm danh sách hóa đơn theo tổng tiền và năm
	public List<HoaDon> getHoaDonTheoTongTienNam(int nam, int giaMin, int giaMax) {
		try {
			KetNoiSQL.getInstance().connect();
			Connection con = KetNoiSQL.getInstance().getConnection();
			String sql = "select hd.maHD, hd.ngayLap, hd.maNV, nv.tenNV, hd.maKH, kh.tenKH, hd.tongTien\r\n"
					+ "From HoaDon hd join ChiTietHoaDon cthd on cthd.maHD = hd.maHD join SanPham sp on cthd.maSP = sp.maSP \r\n"
					+ "join NhanVien nv on hd.maNV = nv.maNV join KhachHang kh on hd.maKH = kh.maKH\r\n"
					+ "Where hd.trangThai = 1 and YEAR(hd.ngayLap) = ? AND \r\n" + "hd.tongTien \r\n"
					+ "BETWEEN ? AND ? GROUP BY hd.maHD, hd.ngayLap, hd.maNV, nv.tenNV, hd.maKH, kh.tenKH, hd.tongTien";
			PreparedStatement stmt = con.prepareCall(sql);
			stmt.setInt(1, nam);
			stmt.setInt(2, giaMin);
			stmt.setInt(3, giaMax);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String maHD = rs.getString(1);
				Date ngayLap = rs.getDate(2);
				String maNV = rs.getString(3);
				String tenNV = rs.getString(4);
				String maKH = rs.getString(5);
				String tenKH = rs.getString(6);
				Double tongTien = rs.getDouble(7);
				HoaDon hd = new HoaDon(maHD, ngayLap, new KhachHang(maKH, tenKH, null, null, false),
						new NhanVien(maNV, tenNV, null, null, null, null, false, null, false, 0), 0, tongTien);
				dshd.add(hd);
			}
		} catch (SQLException e) {
			// TODO: handle exception
		}
		return dshd;
	}

	// Tìm danh sách hóa đơn theo tổng tiền, tháng và năm
	public List<HoaDon> getHoaDonTheoTongTienThangNam(int thang, int nam, int giaMin, int giaMax) {
		try {
			KetNoiSQL.getInstance().connect();
			Connection con = KetNoiSQL.getInstance().getConnection();
			String sql = "select hd.maHD, hd.ngayLap, hd.maNV, nv.tenNV, hd.maKH, kh.tenKH, hd.tongTien\r\n"
					+ "From HoaDon hd join ChiTietHoaDon cthd on cthd.maHD = hd.maHD join SanPham sp on cthd.maSP = sp.maSP \r\n"
					+ "join NhanVien nv on hd.maNV = nv.maNV join KhachHang kh on hd.maKH = kh.maKH\r\n"
					+ "Where hd.trangThai = 1 and MONTH(hd.ngayLap) = ? AND YEAR(hd.ngayLap) = ? AND\r\n"
					+ "hd.tongTien \r\n"
					+ "BETWEEN ? AND ? GROUP BY hd.maHD, hd.ngayLap, hd.maNV, nv.tenNV, hd.maKH, kh.tenKH, hd.tongTien";
			PreparedStatement stmt = con.prepareCall(sql);
			stmt.setInt(1, thang);
			stmt.setInt(2, nam);
			stmt.setInt(3, giaMin);
			stmt.setInt(4, giaMax);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String maHD = rs.getString(1);
				Date ngayLap = rs.getDate(2);
				String maNV = rs.getString(3);
				String tenNV = rs.getString(4);
				String maKH = rs.getString(5);
				String tenKH = rs.getString(6);
				Double tongTien = rs.getDouble(7);
				HoaDon hd = new HoaDon(maHD, ngayLap, new KhachHang(maKH, tenKH, null, null, false),
						new NhanVien(maNV, tenNV, null, null, null, null, false, null, false, 0), 0, tongTien);
				dshd.add(hd);
			}
		} catch (SQLException e) {
			// TODO: handle exception
		}
		return dshd;
	}

	// Tìm kiếm hóa đơn theo từ khóa (maHD, maNV, tenNV, maKH, tenKH) và tháng
	public List<HoaDon> getHoaDonTheoTuKhoaThang(String tukhoa, int thang) {
		try {
			KetNoiSQL.getInstance().connect();
			Connection con = KetNoiSQL.getInstance().getConnection();
			String sql = "select hd.maHD, hd.ngayLap, hd.maNV, nv.tenNV, hd.maKH, kh.tenKH, hd.tongTien\r\n"
					+ "from HoaDon hd join NhanVien nv on hd.maNV = nv.maNV\r\n"
					+ "join KhachHang kh on hd.maKH = kh.maKH where hd.trangThai = 1 and (hd.maHD like ? or hd.maNV like ? or nv.tenNV like ? or hd.maKH like ? or kh.tenKH like ?) "
					+ "AND MONTH(hd.ngayLap) = ?";
			PreparedStatement stmt = con.prepareCall(sql);
			stmt.setString(1, "%" + tukhoa + "%");
			stmt.setString(2, "%" + tukhoa + "%");
			stmt.setString(3, "%" + tukhoa + "%");
			stmt.setString(4, "%" + tukhoa + "%");
			stmt.setString(5, "%" + tukhoa + "%");
			stmt.setInt(6, thang);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String maHD = rs.getString(1);
				Date ngayLap = rs.getDate(2);
				String maNV = rs.getString(3);
				String tenNV = rs.getString(4);
				String maKH = rs.getString(5);
				String tenKH = rs.getString(6);
				Double tongTien = rs.getDouble(7);
				HoaDon hd = new HoaDon(maHD, ngayLap, new KhachHang(maKH, tenKH, null, null, false),
						new NhanVien(maNV, tenNV, null, null, null, null, false, null, false, 0), 0, tongTien);
				dshd.add(hd);
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dshd;
	}

	// Tìm kiếm hóa đơn theo từ khóa (maHD, maNV, tenNV, maKH, tenKH) và năm
	public List<HoaDon> getHoaDonTheoTuKhoaNam(String tukhoa, int nam) {
		try {
			KetNoiSQL.getInstance().connect();
			Connection con = KetNoiSQL.getInstance().getConnection();
			String sql = "select hd.maHD, hd.ngayLap, hd.maNV, nv.tenNV, hd.maKH, kh.tenKH, hd.tongTien\r\n"
					+ "from HoaDon hd join NhanVien nv on hd.maNV = nv.maNV\r\n"
					+ "join KhachHang kh on hd.maKH = kh.maKH where hd.trangThai = 1 and (hd.maHD like ? or hd.maNV like ? or nv.tenNV like ? or hd.maKH like ? or kh.tenKH like ?) "
					+ "AND YEAR(hd.ngayLap) = ?";
			PreparedStatement stmt = con.prepareCall(sql);
			stmt.setString(1, "%" + tukhoa + "%");
			stmt.setString(2, "%" + tukhoa + "%");
			stmt.setString(3, "%" + tukhoa + "%");
			stmt.setString(4, "%" + tukhoa + "%");
			stmt.setString(5, "%" + tukhoa + "%");
			stmt.setInt(6, nam);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String maHD = rs.getString(1);
				Date ngayLap = rs.getDate(2);
				String maNV = rs.getString(3);
				String tenNV = rs.getString(4);
				String maKH = rs.getString(5);
				String tenKH = rs.getString(6);
				Double tongTien = rs.getDouble(7);
				HoaDon hd = new HoaDon(maHD, ngayLap, new KhachHang(maKH, tenKH, null, null, false),
						new NhanVien(maNV, tenNV, null, null, null, null, false, null, false, 0), 0, tongTien);
				dshd.add(hd);
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dshd;
	}

	// Tìm kiếm hóa đơn theo từ khóa (maHD, maNV, tenNV, maKH, tenKH) và tổng tiền
	public List<HoaDon> getHoaDonTheoTuKhoaTongTien(String tukhoa, int giaMin, int giaMax) {
		try {
			KetNoiSQL.getInstance().connect();
			Connection con = KetNoiSQL.getInstance().getConnection();
			String sql = "select hd.maHD, hd.ngayLap, hd.maNV, nv.tenNV, hd.maKH, kh.tenKH, hd.tongTien\r\n"
					+ "From HoaDon hd join ChiTietHoaDon cthd on cthd.maHD = hd.maHD join SanPham sp on cthd.maSP = sp.maSP \r\n"
					+ "join NhanVien nv on hd.maNV = nv.maNV join KhachHang kh on hd.maKH = kh.maKH\r\n"
					+ "Where hd.trangThai = 1 and (hd.maHD LIKE ? or hd.maNV LIKE ? or nv.tenNV LIKE ? or hd.maKH LIKE ? OR kh.tenKH LIKE ?) \r\n"
					+ "AND hd.tongTien \r\n"
					+ "BETWEEN ? AND ? GROUP BY hd.maHD, hd.ngayLap, hd.maNV, nv.tenNV, hd.maKH, kh.tenKH, hd.tongTien";
			PreparedStatement stmt = con.prepareCall(sql);
			stmt.setString(1, "%" + tukhoa + "%");
			stmt.setString(2, "%" + tukhoa + "%");
			stmt.setString(3, "%" + tukhoa + "%");
			stmt.setString(4, "%" + tukhoa + "%");
			stmt.setString(5, "%" + tukhoa + "%");
			stmt.setInt(6, giaMin);
			stmt.setInt(7, giaMax);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String maHD = rs.getString(1);
				Date ngayLap = rs.getDate(2);
				String maNV = rs.getString(3);
				String tenNV = rs.getString(4);
				String maKH = rs.getString(5);
				String tenKH = rs.getString(6);
				Double tongTien = rs.getDouble(7);
				HoaDon hd = new HoaDon(maHD, ngayLap, new KhachHang(maKH, tenKH, null, null, false),
						new NhanVien(maNV, tenNV, null, null, null, null, false, null, false, 0), 0, tongTien);
				dshd.add(hd);
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dshd;
	}

	// Tìm kiếm hóa đơn theo từ khóa (maHD, maNV, tenNV, maKH, tenKH), tháng và tổng
	// tiền
	public List<HoaDon> getHoaDonTheoTuKhoaThangTongTien(String tukhoa, int thang, int giaMin, int giaMax) {
		try {
			KetNoiSQL.getInstance().connect();
			Connection con = KetNoiSQL.getInstance().getConnection();
			String sql = "select hd.maHD, hd.ngayLap, hd.maNV, nv.tenNV, hd.maKH, kh.tenKH, hd.tongTien\r\n"
					+ "From HoaDon hd join ChiTietHoaDon cthd on cthd.maHD = hd.maHD join SanPham sp on cthd.maSP = sp.maSP \r\n"
					+ "join NhanVien nv on hd.maNV = nv.maNV join KhachHang kh on hd.maKH = kh.maKH\r\n"
					+ "Where hd.trangThai = 1 and (hd.maHD LIKE ? or hd.maNV LIKE ? or nv.tenNV LIKE ? or hd.maKH LIKE ? OR kh.tenKH LIKE ?) \r\n"
					+ "AND MONTH(hd.ngayLap) = ? AND\r\n" + "hd.tongTien \r\n"
					+ "BETWEEN ? AND ? GROUP BY hd.maHD, hd.ngayLap, hd.maNV, nv.tenNV, hd.maKH, kh.tenKH, hd.tongTien";
			PreparedStatement stmt = con.prepareCall(sql);
			stmt.setString(1, "%" + tukhoa + "%");
			stmt.setString(2, "%" + tukhoa + "%");
			stmt.setString(3, "%" + tukhoa + "%");
			stmt.setString(4, "%" + tukhoa + "%");
			stmt.setString(5, "%" + tukhoa + "%");
			stmt.setInt(6, thang);
			stmt.setInt(7, giaMin);
			stmt.setInt(8, giaMax);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String maHD = rs.getString(1);
				Date ngayLap = rs.getDate(2);
				String maNV = rs.getString(3);
				String tenNV = rs.getString(4);
				String maKH = rs.getString(5);
				String tenKH = rs.getString(6);
				Double tongTien = rs.getDouble(7);
				HoaDon hd = new HoaDon(maHD, ngayLap, new KhachHang(maKH, tenKH, null, null, false),
						new NhanVien(maNV, tenNV, null, null, null, null, false, null, false, 0), 0, tongTien);
				dshd.add(hd);
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dshd;
	}

	// Tìm kiếm hóa đơn theo từ khóa (maHD, maNV, tenNV, maKH, tenKH), năm và tổng
	// tiền
	public List<HoaDon> getHoaDonTheoTuKhoaNamTongTien(String tukhoa, int nam, int giaMin, int giaMax) {
		try {
			KetNoiSQL.getInstance().connect();
			Connection con = KetNoiSQL.getInstance().getConnection();
			String sql = "select hd.maHD, hd.ngayLap, hd.maNV, nv.tenNV, hd.maKH, kh.tenKH, hd.tongTien\r\n"
					+ "From HoaDon hd join ChiTietHoaDon cthd on cthd.maHD = hd.maHD join SanPham sp on cthd.maSP = sp.maSP \r\n"
					+ "join NhanVien nv on hd.maNV = nv.maNV join KhachHang kh on hd.maKH = kh.maKH\r\n"
					+ "Where hd.trangThai = 1 and (hd.maHD LIKE ? or hd.maNV LIKE ? or nv.tenNV LIKE ? or hd.maKH LIKE ? OR kh.tenKH LIKE ?) \r\n"
					+ "AND YEAR(hd.ngayLap) = ? AND\r\n" + "hd.tongTien \r\n"
					+ "BETWEEN ? AND ? GROUP BY hd.maHD, hd.ngayLap, hd.maNV, nv.tenNV, hd.maKH, kh.tenKH, hd.tongTien";
			PreparedStatement stmt = con.prepareCall(sql);
			stmt.setString(1, "%" + tukhoa + "%");
			stmt.setString(2, "%" + tukhoa + "%");
			stmt.setString(3, "%" + tukhoa + "%");
			stmt.setString(4, "%" + tukhoa + "%");
			stmt.setString(5, "%" + tukhoa + "%");
			stmt.setInt(6, nam);
			stmt.setInt(7, giaMin);
			stmt.setInt(8, giaMax);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String maHD = rs.getString(1);
				Date ngayLap = rs.getDate(2);
				String maNV = rs.getString(3);
				String tenNV = rs.getString(4);
				String maKH = rs.getString(5);
				String tenKH = rs.getString(6);
				Double tongTien = rs.getDouble(7);
				HoaDon hd = new HoaDon(maHD, ngayLap, new KhachHang(maKH, tenKH, null, null, false),
						new NhanVien(maNV, tenNV, null, null, null, null, false, null, false, 0), 0, tongTien);
				dshd.add(hd);
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dshd;
	}

	// Tìm kiếm hóa đơn theo từ khóa (maHD, maNV, tenNV, maKH, tenKH), thang, năm và
	// tổng tiền
	public List<HoaDon> getHoaDonTheoTuKhoaThangNamTongTien(String tukhoa, int thang, int nam, int giaMin, int giaMax) {
		try {
			KetNoiSQL.getInstance().connect();
			Connection con = KetNoiSQL.getInstance().getConnection();
			String sql = "select hd.maHD, hd.ngayLap, hd.maNV, nv.tenNV, hd.maKH, kh.tenKH, hd.tongTien\r\n"
					+ "From HoaDon hd join ChiTietHoaDon cthd on cthd.maHD = hd.maHD join SanPham sp on cthd.maSP = sp.maSP \r\n"
					+ "join NhanVien nv on hd.maNV = nv.maNV join KhachHang kh on hd.maKH = kh.maKH\r\n"
					+ "Where hd.trangThai = 1 and (hd.maHD LIKE ? or hd.maNV LIKE ? or nv.tenNV LIKE ? or hd.maKH LIKE ? OR kh.tenKH LIKE ?) \r\n"
					+ "AND MONTH(hd.ngayLap) = ? AND YEAR(hd.ngayLap) = ? AND\r\n" + "hd.tongTien \r\n"
					+ "BETWEEN ? AND ? GROUP BY hd.maHD, hd.ngayLap, hd.maNV, nv.tenNV, hd.maKH, kh.tenKH, hd.tongTien";
			PreparedStatement stmt = con.prepareCall(sql);
			stmt.setString(1, "%" + tukhoa + "%");
			stmt.setString(2, "%" + tukhoa + "%");
			stmt.setString(3, "%" + tukhoa + "%");
			stmt.setString(4, "%" + tukhoa + "%");
			stmt.setString(5, "%" + tukhoa + "%");
			stmt.setInt(6, thang);
			stmt.setInt(7, nam);
			stmt.setInt(8, giaMin);
			stmt.setInt(9, giaMax);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String maHD = rs.getString(1);
				Date ngayLap = rs.getDate(2);
				String maNV = rs.getString(3);
				String tenNV = rs.getString(4);
				String maKH = rs.getString(5);
				String tenKH = rs.getString(6);
				Double tongTien = rs.getDouble(7);
				HoaDon hd = new HoaDon(maHD, ngayLap, new KhachHang(maKH, tenKH, null, null, false),
						new NhanVien(maNV, tenNV, null, null, null, null, false, null, false, 0), 0, tongTien);
				dshd.add(hd);
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dshd;
	}

	// Tìm kiếm hóa đơn theo từ khóa (maHD, maNV, tenNV, maKH, tenKH), thang và năm
	public List<HoaDon> getHoaDonTheoTuKhoaThangNam(String tukhoa, int thang, int nam) {
		try {
			KetNoiSQL.getInstance().connect();
			Connection con = KetNoiSQL.getInstance().getConnection();
			String sql = "select hd.maHD, hd.ngayLap, hd.maNV, nv.tenNV, hd.maKH, kh.tenKH, hd.tongTien\r\n"
					+ "From HoaDon hd join ChiTietHoaDon cthd on cthd.maHD = hd.maHD join SanPham sp on cthd.maSP = sp.maSP \r\n"
					+ "join NhanVien nv on hd.maNV = nv.maNV join KhachHang kh on hd.maKH = kh.maKH\r\n"
					+ "Where hd.trangThai = 1 and (hd.maHD LIKE ? or hd.maNV LIKE ? or nv.tenNV LIKE ? or hd.maKH LIKE ? OR kh.tenKH LIKE ?) \r\n"
					+ "AND MONTH(hd.ngayLap) = ? AND YEAR(hd.ngayLap) = ? \r\n"
					+ "GROUP BY hd.maHD, hd.ngayLap, hd.maNV, nv.tenNV, hd.maKH, kh.tenKH";
			PreparedStatement stmt = con.prepareCall(sql);
			stmt.setString(1, "%" + tukhoa + "%");
			stmt.setString(2, "%" + tukhoa + "%");
			stmt.setString(3, "%" + tukhoa + "%");
			stmt.setString(4, "%" + tukhoa + "%");
			stmt.setString(5, "%" + tukhoa + "%");
			stmt.setInt(6, thang);
			stmt.setInt(7, nam);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String maHD = rs.getString(1);
				Date ngayLap = rs.getDate(2);
				String maNV = rs.getString(3);
				String tenNV = rs.getString(4);
				String maKH = rs.getString(5);
				String tenKH = rs.getString(6);
				Double tongTien = rs.getDouble(7);
				HoaDon hd = new HoaDon(maHD, ngayLap, new KhachHang(maKH, tenKH, null, null, false),
						new NhanVien(maNV, tenNV, null, null, null, null, false, null, false, 0), 0, tongTien);
				dshd.add(hd);
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dshd;
	}

	public int editNVTrongHD(HoaDon hoaDon) {
		KetNoiSQL.getInstance().connect();
		;
		Connection conn = KetNoiSQL.getConnection();

		try {
			String sql = "update HoaDon set maKH = (?) where maHD = (?)";

			PreparedStatement stmt = conn.prepareCall(sql);
			stmt.setString(1, hoaDon.getKhachHang().getMaKH());
			stmt.setString(2, hoaDon.getMaHoaDon());

			return stmt.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return -1;
	}

	public int updateHoaDon(String maHD, int trangthai, double tongtien) {
		KetNoiSQL.getInstance().connect();
		;
		Connection conn = KetNoiSQL.getConnection();

		try {
			String sql = "update HoaDon set tongTien =(?),trangthai =(?) where maHD = (?)";

			PreparedStatement stmt = conn.prepareCall(sql);
			stmt.setDouble(1, tongtien);
			stmt.setInt(2, trangthai);
			stmt.setString(3, maHD);
			return stmt.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return -1;
	}

	public int deleteHoaDon(String maHD) {
		KetNoiSQL.getInstance().connect();
		;
		Connection conn = KetNoiSQL.getConnection();

		try {
			String sql = "delete ChiTietHoaDon where maHD = (?)\r\n";

			PreparedStatement stmt = conn.prepareCall(sql);
			stmt.setString(1, maHD);
			return stmt.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return -1;
	}

	public double getTongTienDaMuaCuaKH(String maKH) {
		try {
			KetNoiSQL.getInstance().connect();
			Connection con = KetNoiSQL.getInstance().getConnection();
			String sql = "Select SUM(tongTien) from HoaDon where trangThai = 1 and maKH = (?) GROUP BY maKH";
			PreparedStatement stmt = con.prepareCall(sql);
			stmt.setString(1, maKH);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				double TongTien = rs.getDouble(1);
				return TongTien;
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public ArrayList<HoaDon> getAllHoaDon(String tenNhanVien, String tenKhachHang) {
		ArrayList<HoaDon> listHoaDon = new ArrayList<>();
		KetNoiSQL.getInstance();
		Connection conn = KetNoiSQL.getConnection();
		try {
			String sql = "SELECT HoaDon.maHD, HoaDon.ngayLap, KhachHang.tenKH, NhanVien.tenNV\n" + "FROM HoaDon\n"
					+ "INNER JOIN KhachHang ON HoaDon.maKH = KhachHang.maKH\n"
					+ "INNER JOIN NhanVien ON HoaDon.maNV = NhanVien.maNV\n"
					+ "WHERE nhanvien.tenNV LIKE ? AND khachHang.tenKH LIKE ? AND maHD NOT LIKE 'HDC%'";
			PreparedStatement stmt = conn.prepareCall(sql);
			stmt.setString(1, "%" + tenNhanVien + "%");
			stmt.setString(2, "%" + tenKhachHang + "%");

			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				nhanVienDAO = new NhanVienDAO();
				khachHangDAO = new KhachHangDAO();
				String maHoaDon = rs.getString(1);
				Date ngayLap = rs.getDate(2);
				KhachHang khachHang = khachHangDAO.getKhachHangTheoTen(rs.getString(3));
				NhanVien nhanVien = nhanVienDAO.getNhanVienTheoTen(rs.getString(4));
				HoaDon hoaDon = new HoaDon(maHoaDon, nhanVien, khachHang, ngayLap);
				listHoaDon.add(hoaDon);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return listHoaDon;
	}

	public ArrayList<HoaDon> getAllHoaDon(String tenKhachHang, String tenNhanVien, String tuNgay, String denNgay) {
		ArrayList<HoaDon> listHoaDon = new ArrayList<>();
		KetNoiSQL.getInstance();
		Connection conn = KetNoiSQL.getConnection();
		try {
			String sql = "SELECT HoaDon.maHD, HoaDon.ngayLap, KhachHang.tenKH, NhanVien.tenNV\n" + "FROM HoaDon\n"
					+ "INNER JOIN KhachHang ON HoaDon.maKH = KhachHang.maKH\n"
					+ "INNER JOIN NhanVien ON HoaDon.maNV = NhanVien.maNV\n"
					+ "where nhanvien.tenNV like ? and khachHang.tenKH like ? and maHD not like 'HDC%' and ngayLap >= ? and ngayLap <= ?";
			PreparedStatement stmt = conn.prepareCall(sql);
			stmt.setString(1, "%" + tenNhanVien + "%");
			stmt.setString(2, "%" + tenKhachHang + "%");
			stmt.setString(3, tuNgay);
			stmt.setString(4, denNgay);

			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String maHoaDon = rs.getString(1);
				Date ngayLap = rs.getDate(2);
				KhachHang khachHang = khachHangDAO.getKhachHangTheoTen(rs.getString(3));
				NhanVien nhanVien = nhanVienDAO.getNhanVienTheoTen(rs.getString(4));
				HoaDon hoaDon = new HoaDon(maHoaDon, nhanVien, khachHang, ngayLap);
				listHoaDon.add(hoaDon);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return listHoaDon;
	}
}
