package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import ConnectDB.KetNoiSQL;
import entity.KhuyenMai;
import entity.PhanLoai;
import entity.SanPham;

public class KhuyenMaiDAO {
	ArrayList<KhuyenMai> dskm;
	KhuyenMai km;
	ArrayList<SanPham> dssp;
	SanPham sp;

	public KhuyenMaiDAO() {
		KetNoiSQL.getInstance().connect();
		dskm = new ArrayList<KhuyenMai>();
		dssp = new ArrayList<SanPham>();
	}

	// Lấy danh sách Khuyến Mãi từ SQL
	public List<KhuyenMai> doTuBang() {
		try {
			KetNoiSQL.getInstance().connect();
			Connection con = KetNoiSQL.getConnection();
			String sql = "Select * from KhuyenMai";
			Statement statement = con.createStatement(); // Thực thi câu lệnh SQL trả về ResulSet.
			ResultSet rs = statement.executeQuery(sql);
			// Duyệt kết quả trả về
			while (rs.next()) { // Di chuyển con trỏ xuống bản ghi kế tiếp
				String ma = rs.getString(1);
				Double phanTram = rs.getDouble(2);
				String ten = rs.getString(3);
				Date nbd = rs.getDate(4);
				SimpleDateFormat dataFormat = new SimpleDateFormat("dd-MM-yyyy"); // Chuyển định dang yyyy-MM-dd sang
																					// dd-MM-yyyy
				String ngayBatDau = dataFormat.format(nbd);
				Date nkt = rs.getDate(5);
				String ngayKetThuc = dataFormat.format(nkt); // Chuyển định dang yyyy-MM-dd sang dd-MM-yyyy
				KhuyenMai km = new KhuyenMai(ma, ten, phanTram, nbd, nkt);
				dskm.add(km);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dskm;
	}

	// Lấy danh sách Sản phẩm có maKM = null từ SQL
	public List<SanPham> getSanPhamMaKMIsNull() {
		KetNoiSQL.getInstance().connect();
		try {
			Connection con = KetNoiSQL.getConnection();
			String sql = "Select * from SanPham where maKM is NULL";
			Statement statement = con.createStatement(); // Thực thi câu lệnh SQL trả về ResulSet.
			ResultSet rs = statement.executeQuery(sql);
			// Duyệt kết quả trả về
			while (rs.next()) { // Di chuyển con trỏ xuống bản ghi kế tiếp
				String ma = rs.getString("maSP");
				String ten = rs.getString("tenSP");
				Double giaBan = rs.getDouble("giaBan");
				int loi = rs.getInt("loiTheoPhanTram");
				SanPham sp = new SanPham(ma, ten, null, 0, loi, null, giaBan, null, 0, null, null, null, null);
				dssp.add(sp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dssp;
	}

	// Thêm khuyến mãi
	public boolean createKhuyenMai(KhuyenMai km) {
		KetNoiSQL.getInstance().connect();
		Connection con = KetNoiSQL.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			Statement statement = con.createStatement(); // Thực thi câu lệnh SQL trả về ResulSet.
			stmt = con.prepareStatement("insert into KhuyenMai values(?, ?, ?, ?, ?)");
			stmt.setString(1, km.getMaKM());
			stmt.setDouble(2, km.getPhanTramKhuyenMai());
			stmt.setString(3, km.getTenKhuyenMai());
			stmt.setDate(4, (Date) km.getNgayBatDau());
			stmt.setDate(5, (Date) km.getNgayKetThuc());
			n = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n > 0;
	}

	// Phát sinh maKM tự động
	public String getAuToID() {
		KetNoiSQL.getInstance().connect();
		Connection con = KetNoiSQL.getConnection();
		String maxID = null;
		try {
			String sql = "Select maKM from KhuyenMai where maKM = (Select MAX(maKM) From KhuyenMai)";
			Statement statement = con.createStatement(); // Thực thi câu lệnh SQL trả về ResulSet.
			ResultSet rs = statement.executeQuery(sql);

			if (rs.next()) {
				maxID = rs.getString(1); // Lấy ra mã khuyến mãi lớn nhất cuối cùng
			}
			if (Integer.parseInt(maxID.substring(2)) < 9) {
				maxID = "KM0" + (String.valueOf(Integer.parseInt(maxID.substring(2)) + 1));
			} else {
				maxID = "KM" + (String.valueOf(Integer.parseInt(maxID.substring(2)) + 1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return maxID;
	}

	public KhuyenMai getKhuyenMai(String id) {
		KetNoiSQL.getInstance().connect();
		;
		Connection conn = KetNoiSQL.getConnection();
		try {
			String sql = "select * from KhuyenMai where maKM = ?";
			PreparedStatement stmt = conn.prepareCall(sql);
			stmt.setString(1, id);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String makm = rs.getString(1);
				int ptkm = rs.getInt(2);
				String tenkm = rs.getString(3);
				Date ngayBD = rs.getDate(4);
				Date ngayKT = rs.getDate(5);
				KhuyenMai km = new KhuyenMai(makm, tenkm, ptkm, ngayBD, ngayKT);
				return km;
			}
		} catch (SQLException ex) {
			Logger.getLogger(XuatXuDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;

	}

	public KhuyenMai getKhuyenMaiByPhanTram(int phanTram) {
		KetNoiSQL.getInstance().connect();
		;
		Connection conn = KetNoiSQL.getConnection();

		try {
			String sql = "select * from khuyenmai where phanTramKhuyenMai = ?";
			PreparedStatement stmt = conn.prepareCall(sql);
			stmt.setInt(1, phanTram);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				KhuyenMai khuyenMai = new KhuyenMai();
				khuyenMai.setMaKM(rs.getString(1));
				khuyenMai.setPhanTramKhuyenMai(rs.getInt(2));
				khuyenMai.setTenKhuyenMai(rs.getString(3));
				khuyenMai.setNgayBatDau(rs.getDate(4));
				khuyenMai.setNgayKetThuc(rs.getDate(5));

				return khuyenMai;
			}
		} catch (SQLException ex) {
			Logger.getLogger(XuatXuDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}

	// Tìm sản phẩm theo mã khuyến mãi
	public List<SanPham> getSanPhanTheoMaKM(String maKM) {
		try {
			KetNoiSQL.getInstance().connect();
			Connection con = KetNoiSQL.getInstance().getConnection();
			String sql = "select sp.maSP, sp.tenSP, sp.maKM, km.phanTramKhuyenMai, sp.giaBan, sp.loiTheoPhanTram\r\n"
					+ "from SanPham sp  join KhuyenMai km on km.maKM = sp.maKM\r\n" + "where sp.maKM = ?";
			PreparedStatement stmt = con.prepareCall(sql);
			stmt.setString(1, maKM);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String masp = rs.getString(1);
				String ten = rs.getString(2);
				String makm = rs.getString(3);
				Double phanTramKhuyenMai = rs.getDouble(4);
				Double giaBan = rs.getDouble(5);
				int loi = rs.getInt(6);
				SanPham sp = new SanPham(masp, ten, null, 0, loi,
						new KhuyenMai(makm, null, phanTramKhuyenMai, null, null), giaBan, null, 0, null, null, null, null);
				dssp.add(sp);
			}

			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dssp;
	}

	// Thêm mã khuyến mãi cho sản phẩm và Cập nhật các trường của chương trình
	// khuyến
	// mãi
	public boolean updateMaKMChoSanPHam(KhuyenMai km, String maKM, String maSP) {
		KetNoiSQL.getInstance().connect();
		Connection con = KetNoiSQL.getInstance().getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			stmt = con.prepareStatement("update KhuyenMai set phanTramKhuyenMai = ?, tenKhuyenMai = ?, \r\n"
					+ "ngayBatDau = ?, ngayKetThuc = ? where maKM = ?;\r\n"
					+ "update SanPham set maKM = ? where maSP = ?;");
			stmt.setDouble(1, km.getPhanTramKhuyenMai());
			stmt.setString(2, km.getTenKhuyenMai());
			stmt.setDate(3, (Date) km.getNgayBatDau());
			stmt.setDate(4, (Date) km.getNgayKetThuc());
			stmt.setString(5, km.getMaKM());
			stmt.setString(6, maKM);
			stmt.setString(7, maSP);
			n = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n > 0;
	}

	// Xóa mã khuyến mãi cho sản phẩm và Cập nhật các trường của chương trình khuyến
	// mãi
	public boolean deleteMaKMChoSanPHam(KhuyenMai km, String maSP) {
		KetNoiSQL.getInstance().connect();
		Connection con = KetNoiSQL.getInstance().getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			stmt = con.prepareStatement("update KhuyenMai set phanTramKhuyenMai = ?, tenKhuyenMai = ?, \r\n"
					+ "ngayBatDau = ?, ngayKetThuc = ? where maKM = ?;\r\n"
					+ "update SanPham set maKM = Null where maSP = ?;");
			stmt.setDouble(1, km.getPhanTramKhuyenMai());
			stmt.setString(2, km.getTenKhuyenMai());
			stmt.setDate(3, (Date) km.getNgayBatDau());
			stmt.setDate(4, (Date) km.getNgayKetThuc());
			stmt.setString(5, km.getMaKM());
			stmt.setString(6, maSP);
			n = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n > 0;
	}
	
	// Xóa khuyến mãi cho sản phẩm
	public boolean deleteMaKMChoSanPHamHetHanKM(String maSP) {
		KetNoiSQL.getInstance().connect();
		Connection con = KetNoiSQL.getInstance().getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			stmt = con.prepareStatement("update SanPham set maKM = Null where maSP = ?;");
			stmt.setString(1, maSP);
			n = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n > 0;
	}
	
	// Xóa chương trình khuyến mãi khi không còn sản phẩm được áp khuyến mãi
	public boolean deleteKhuyenMai(String maKM) {
		KetNoiSQL.getInstance().connect();
		Connection con = KetNoiSQL.getInstance().getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			stmt = con.prepareStatement("Delete from KhuyenMai where maKM = ?");
			stmt.setString(1, maKM);
			n = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n > 0;
	}
}
