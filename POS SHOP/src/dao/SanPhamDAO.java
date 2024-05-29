package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import ConnectDB.KetNoiSQL;
import entity.ChatLieu;
import entity.KhuyenMai;
import entity.KichThuoc;
import entity.KieuDang;
import entity.MauSac;
import entity.NhaCungCap;
import entity.PhanLoai;
import entity.SanPham;
import entity.XuatXu;

public class SanPhamDAO {
	ArrayList<SanPham> dssp;
	SanPham sp;
	ChatLieuDAO chatLieuDao = new ChatLieuDAO();
	KieuDangDAO kieuDangDao = new KieuDangDAO();
	KichThuocDAO kichThuocDao = new KichThuocDAO();
	MauSacDAO mauSacDAO = new MauSacDAO();
	XuatXuDAO xuatXuDAO = new XuatXuDAO();
	PhanLoaiDAO phanLoaiDAO = new PhanLoaiDAO();
	NhaCungCapDAO nhaCungCapDao = new NhaCungCapDAO();
	KhuyenMaiDAO khuyenMaiDao = new KhuyenMaiDAO();

	public SanPhamDAO() {
		KetNoiSQL.getInstance().connect();
		dssp = new ArrayList<SanPham>();
	}

	public List<SanPham> doTuBang() {
		KetNoiSQL.getInstance().connect();
		try {
			Connection con = KetNoiSQL.getInstance().getConnection();
			String sql = "select maSP, tenSP, maPL, giaNhap,loiTheoPhanTram, maKM, giaBan, maKT,soLuong,maMS, maCL, maNCC, hinhAnh  from SanPham ";
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);

			while (rs.next()) {
				String masp = rs.getString("maSP");
				String ten = rs.getString("tenSP");
				String maloai = rs.getString("maPL");
				float giaNhap = rs.getFloat("giaNhap");
				int loi = rs.getInt("loiTheoPhanTram");
				String maKm = rs.getString("maKM");
				float giaBan = rs.getFloat("giaBan");
				String makt = rs.getString("maKT");
				int sl = rs.getInt("soLuong");
				String mams = rs.getString("maMS");
				String macl = rs.getString("maCL");
				String mancc = rs.getString("maNCC");
				String hinhAnh = rs.getString("hinhAnh");

				PhanLoai phanloai = phanLoaiDAO.getPhanLoai(maloai);
				KhuyenMai khuyenmai = khuyenMaiDao.getKhuyenMai(maKm);
				KichThuoc kt = kichThuocDao.getKichThuoc(makt);
				MauSac ms = mauSacDAO.getMauSac(mams);
				ChatLieu cl = chatLieuDao.getChatLieu(macl);
				NhaCungCap ncc = nhaCungCapDao.getNhaCungCap(mancc);

				SanPham sp = new SanPham(masp, ten, phanloai, giaNhap, loi, khuyenmai, giaBan, kt, sl, ms, cl, ncc,
						hinhAnh);
				dssp.add(sp);
			}

			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dssp;
	}
	public List<SanPham> dsSPBanHang() {
		KetNoiSQL.getInstance().connect();
		try {
			Connection con = KetNoiSQL.getInstance().getConnection();
			String sql = "select maSP, tenSP, maPL, giaNhap,loiTheoPhanTram, maKM, giaBan, maKT,soLuong,maMS, maCL, maNCC, hinhAnh  from SanPham where soLuong > 0";
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);

			while (rs.next()) {
				String masp = rs.getString("maSP");
				String ten = rs.getString("tenSP");
				String maloai = rs.getString("maPL");
				float giaNhap = rs.getFloat("giaNhap");
				int loi = rs.getInt("loiTheoPhanTram");
				String maKm = rs.getString("maKM");
				float giaBan = rs.getFloat("giaBan");
				String makt = rs.getString("maKT");
				int sl = rs.getInt("soLuong");
				String mams = rs.getString("maMS");
				String macl = rs.getString("maCL");
				String mancc = rs.getString("maNCC");
				String hinhAnh = rs.getString("hinhAnh");

				PhanLoai phanloai = phanLoaiDAO.getPhanLoai(maloai);
				KhuyenMai khuyenmai = khuyenMaiDao.getKhuyenMai(maKm);
				KichThuoc kt = kichThuocDao.getKichThuoc(makt);
				MauSac ms = mauSacDAO.getMauSac(mams);
				ChatLieu cl = chatLieuDao.getChatLieu(macl);
				NhaCungCap ncc = nhaCungCapDao.getNhaCungCap(mancc);

				SanPham sp = new SanPham(masp, ten, phanloai, giaNhap, loi, khuyenmai, giaBan, kt, sl, ms, cl, ncc,
						hinhAnh);
				dssp.add(sp);
			}

			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dssp;
	}

	// Tìm sản phẩm theo phân loại
	public List<SanPham> getSanPhanTheoPhanLoai(String name) {
		try {
			KetNoiSQL.getInstance().connect();
			Connection con = KetNoiSQL.getInstance().getConnection();
			String sql = "select maSP, tenSP, maPL, giaNhap,loiTheoPhanTram, maKM, giaBan, maKT,soLuong,maMS, maCL, maNCC, hinhAnh \r\n"
					+ "from SanPham where maPL = (select maPL from PhanLoai where phanLoai = (?))";
			PreparedStatement stmt = con.prepareCall(sql);
			stmt.setString(1, name);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				String masp = rs.getString("maSP");
				String ten = rs.getString("tenSP");
				String maloai = rs.getString("maPL");
				float giaNhap = rs.getFloat("giaNhap");
				int loi = rs.getInt("loiTheoPhanTram");
				String maKm = rs.getString("maKM");
				float giaBan = rs.getFloat("giaBan");
				String makt = rs.getString("maKT");
				int sl = rs.getInt("soLuong");
				String mams = rs.getString("maMS");
				String macl = rs.getString("maCL");
				String mancc = rs.getString("maNCC");
				String hinhAnh = rs.getString("hinhAnh");

				PhanLoai phanloai = phanLoaiDAO.getPhanLoai(maloai);
				KhuyenMai khuyenmai = khuyenMaiDao.getKhuyenMai(maKm);
				KichThuoc kt = kichThuocDao.getKichThuoc(makt);
				MauSac ms = mauSacDAO.getMauSac(mams);
				ChatLieu cl = chatLieuDao.getChatLieu(macl);
				NhaCungCap ncc = nhaCungCapDao.getNhaCungCap(mancc);

				SanPham sp = new SanPham(masp, ten, phanloai, giaNhap, loi, khuyenmai, giaBan, kt, sl, ms, cl, ncc,
						hinhAnh);
				dssp.add(sp);
			}

			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dssp;
	}
	
	// Tìm sản phẩm theo phân loại và maKM là null
		public List<SanPham> getSanPhanTheoPhanLoaiNull(String name) {
			try {
				KetNoiSQL.getInstance().connect();
				Connection con = KetNoiSQL.getInstance().getConnection();
				String sql = "select maSP, tenSP, maPL, giaNhap,loiTheoPhanTram, maKM, giaBan, maKT,soLuong,maMS, maCL, maNCC, hinhAnh \r\n"
						+ "from SanPham where maPL = (select maPL from PhanLoai where phanLoai = ? and maKM is Null)";
				PreparedStatement stmt = con.prepareCall(sql);
				stmt.setString(1, name);
				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					String masp = rs.getString("maSP");
					String ten = rs.getString("tenSP");
					String maloai = rs.getString("maPL");
					float giaNhap = rs.getFloat("giaNhap");
					int loi = rs.getInt("loiTheoPhanTram");
					String maKm = rs.getString("maKM");
					float giaBan = rs.getFloat("giaBan");
					String makt = rs.getString("maKT");
					int sl = rs.getInt("soLuong");
					String mams = rs.getString("maMS");
					String macl = rs.getString("maCL");
					String mancc = rs.getString("maNCC");
					String hinhAnh = rs.getString("hinhAnh");

					PhanLoai phanloai = phanLoaiDAO.getPhanLoai(maloai);
					KhuyenMai khuyenmai = khuyenMaiDao.getKhuyenMai(maKm);
					KichThuoc kt = kichThuocDao.getKichThuoc(makt);
					MauSac ms = mauSacDAO.getMauSac(mams);
					ChatLieu cl = chatLieuDao.getChatLieu(macl);
					NhaCungCap ncc = nhaCungCapDao.getNhaCungCap(mancc);

					SanPham sp = new SanPham(masp, ten, phanloai, giaNhap, loi, khuyenmai, giaBan, kt, sl, ms, cl, ncc,
							hinhAnh);
					dssp.add(sp);
				}

				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return dssp;
		}

	public List<SanPham> getDSSPTheoMaSP(String name) {
		try {
			KetNoiSQL.getInstance().connect();
			Connection con = KetNoiSQL.getInstance().getConnection();
			String sql = "select maSP, tenSP, maPL, giaNhap,loiTheoPhanTram, maKM, giaBan, maKT,soLuong,maMS, maCL, maNCC, hinhAnh \r\n"
					+ "from SanPham where soLuong > 0 and maSP like ? or tenSP like ? ";
			PreparedStatement stmt = con.prepareCall(sql);
			stmt.setString(1, "%" + name + "%");
			stmt.setString(2, "%" + name + "%");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				String masp = rs.getString("maSP");
				String ten = rs.getString("tenSP");
				String maloai = rs.getString("maPL");
				float giaNhap = rs.getFloat("giaNhap");
				int loi = rs.getInt("loiTheoPhanTram");
				String maKm = rs.getString("maKM");
				float giaBan = rs.getFloat("giaBan");
				String makt = rs.getString("maKT");
				int sl = rs.getInt("soLuong");
				String mams = rs.getString("maMS");
				String macl = rs.getString("maCL");
				String mancc = rs.getString("maNCC");
				String hinhAnh = rs.getString("hinhAnh");

				PhanLoai phanloai = phanLoaiDAO.getPhanLoai(maloai);
				KhuyenMai khuyenmai = khuyenMaiDao.getKhuyenMai(maKm);
				KichThuoc kt = kichThuocDao.getKichThuoc(makt);
				MauSac ms = mauSacDAO.getMauSac(mams);
				ChatLieu cl = chatLieuDao.getChatLieu(macl);
				NhaCungCap ncc = nhaCungCapDao.getNhaCungCap(mancc);

				SanPham sp = new SanPham(masp, ten, phanloai, giaNhap, loi, khuyenmai, giaBan, kt, sl, ms, cl, ncc,
						hinhAnh);
				dssp.add(sp);
			}

			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dssp;
	}

	public SanPham getSanPhanTheoId(String id) {
		try {
			KetNoiSQL.getInstance().connect();
			Connection con = KetNoiSQL.getInstance().getConnection();
			String sql = "select maSP, tenSP, maPL, giaNhap,loiTheoPhanTram, maKM, giaBan, maKT,soLuong,maMS, maCL, maNCC,maKD,maXX, hinhAnh\r\n"
					+ "from SanPham where maSP like (?)";
			PreparedStatement stmt = con.prepareCall(sql);
			stmt.setString(1, id);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				String masp = rs.getString("maSP");
				String ten = rs.getString("tenSP");
				String maloai = rs.getString("maPL");
				float giaNhap = rs.getFloat("giaNhap");
				int loi = rs.getInt("loiTheoPhanTram");
				String maKm = rs.getString("maKM");
				float giaBan = rs.getFloat("giaBan");
				String makt = rs.getString("maKT");
				int sl = rs.getInt("soLuong");
				String mams = rs.getString("maMS");
				String macl = rs.getString("maCL");
				String mancc = rs.getString("maNCC");
				String makd = rs.getString("maKD");
				String maxx = rs.getString("maXX");
				String hinhAnh = rs.getString("hinhAnh");

				PhanLoai phanloai = phanLoaiDAO.getPhanLoai(maloai);
				KhuyenMai khuyenmai = khuyenMaiDao.getKhuyenMai(maKm);
				KichThuoc kt = kichThuocDao.getKichThuoc(makt);
				MauSac ms = mauSacDAO.getMauSac(mams);
				ChatLieu cl = chatLieuDao.getChatLieu(macl);
				NhaCungCap ncc = nhaCungCapDao.getNhaCungCap(mancc);
				KieuDang kd = kieuDangDao.getKieuDang(makd);
				XuatXu xx = xuatXuDAO.getXuatXu(maxx);

				SanPham sp = new SanPham(masp, ten, phanloai, giaNhap, loi, khuyenmai, giaBan, kt, loi, ms, cl, ncc, kd,
						xx, hinhAnh, sl);
				return sp;
			}

			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<SanPham> getSanPhanTheoMaHD(String id) {
		try {
			KetNoiSQL.getInstance().connect();
			Connection con = KetNoiSQL.getInstance().getConnection();
			String sql = "select cthd.maSP, tenSP, maPL, giaNhap,loiTheoPhanTram, maKM, giaBan, maKT, cthd.soLuong,maMS, maCL, maNCC,maKD,maXX, hinhAnh \r\n"
					+ "from ChiTietHoaDon cthd join SanPham sp on cthd.maSP = sp.maSP where maHD = (?)";
			PreparedStatement stmt = con.prepareCall(sql);
			stmt.setString(1, id);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String masp = rs.getString("maSP");
				String ten = rs.getString("tenSP");
				String maloai = rs.getString("maPL");
				float giaNhap = rs.getFloat("giaNhap");
				int loi = rs.getInt("loiTheoPhanTram");
				String maKm = rs.getString("maKM");
				float giaBan = rs.getFloat("giaBan");
				String makt = rs.getString("maKT");
				int sl = rs.getInt("soLuong");
				String mams = rs.getString("maMS");
				String macl = rs.getString("maCL");
				String mancc = rs.getString("maNCC");
				String hinhAnh = rs.getString("hinhAnh");

				PhanLoai phanloai = phanLoaiDAO.getPhanLoai(maloai);
				KhuyenMai khuyenmai = khuyenMaiDao.getKhuyenMai(maKm);
				KichThuoc kt = kichThuocDao.getKichThuoc(makt);
				MauSac ms = mauSacDAO.getMauSac(mams);
				ChatLieu cl = chatLieuDao.getChatLieu(macl);
				NhaCungCap ncc = nhaCungCapDao.getNhaCungCap(mancc);

				SanPham sp = new SanPham(masp, ten, phanloai, giaNhap, loi, khuyenmai, giaBan, kt, sl, ms, cl, ncc,
						hinhAnh);
				dssp.add(sp);
			}

			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dssp;
	}

	public int addSanPham(SanPham sanPham) {
		KetNoiSQL.getInstance().connect();
		try {
			Connection con = KetNoiSQL.getInstance().getConnection();
			String sql = "Insert into sanpham values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(sql);

			ps.setString(1, sanPham.getMaSP());
			ps.setString(2, sanPham.getTenSP());
			ps.setDouble(3, sanPham.getGiaNhap());
			ps.setInt(4, sanPham.getSoLuong());
			ps.setString(5, sanPham.getNhaCungCap().getMaNCC());
			ps.setString(6, null);
			ps.setInt(7, sanPham.getTrangThai());
			ps.setString(8, sanPham.getChatLieu().getMaChatLieu());
			ps.setString(9, sanPham.getKieuDang().getMaKieuDang());
			ps.setString(10, sanPham.getKichThuoc().getMaKichThuoc());
			ps.setString(11, sanPham.getMauSac().getMaMauSac());
			ps.setString(12, sanPham.getXuatXu().getMaXuatXu());
			ps.setString(13, sanPham.getPl().getMaPhanLoai());
			ps.setInt(14, sanPham.getLoi());
			ps.setDouble(15, sanPham.getGiaBan());
			ps.setString(16, sanPham.getHinhAnh());

			return ps.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return -1;
	}

	public int updateSanPham(SanPham sanPham) {
		KetNoiSQL.getInstance().connect();
		;
		Connection conn = KetNoiSQL.getConnection();

		try {
			String sql = "update SanPham set tenSP = ?, giaNhap = ?,soLuong=?,maNCC = ?,\r\n"
					+ "maKM =?, trangThai = ?, maCL = ?,maKD = ?, maMS = ?, maXX = ?,maPL= ?, \r\n"
					+ "loiTheoPhanTram = ?, giaBan= ?,hinhAnh = ? \r\n" 
					+ "where maSP = ?";

			PreparedStatement stmt = conn.prepareCall(sql);
			stmt.setString(1, sanPham.getTenSP());
			stmt.setDouble(2, sanPham.getGiaNhap());
			stmt.setInt(3, sanPham.getSoLuong());
			stmt.setString(4, sanPham.getNhaCungCap().getMaNCC());
			stmt.setString(5, null);
			stmt.setInt(6, sanPham.getTrangThai());
			stmt.setString(7, sanPham.getChatLieu().getMaChatLieu());
			stmt.setString(8, sanPham.getKieuDang().getMaKieuDang());
			stmt.setString(9, sanPham.getMauSac().getMaMauSac());
			stmt.setString(10, sanPham.getXuatXu().getMaXuatXu());
			stmt.setString(11, sanPham.getPl().getMaPhanLoai());
			stmt.setInt(12, sanPham.getLoi());
			stmt.setDouble(13, sanPham.getGiaBan());
			stmt.setString(14, sanPham.getHinhAnh());
			stmt.setString(15, sanPham.getMaSP());

			return stmt.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return -1;
	}
	public int SuaSlSP(int sl, String mahd) {
		KetNoiSQL.getInstance().connect();
		;
		Connection conn = KetNoiSQL.getConnection();

		try {
			String sql = "update SanPham set soLuong = soLuong - (?) where maSP = (?)";

			PreparedStatement stmt = conn.prepareCall(sql);
			stmt.setInt(1, sl);
			stmt.setString(2, mahd);
			return stmt.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return -1;
	}

	/// code của phát




	public ArrayList<SanPham> getAllSanPham() {

		ChatLieuDAO chatLieuDao = new ChatLieuDAO();
		KieuDangDAO kieuDangDao = new KieuDangDAO();
		KichThuocDAO kichThuocDao = new KichThuocDAO();
		MauSacDAO mauSacDAO = new MauSacDAO();
		XuatXuDAO xuatXuDAO = new XuatXuDAO();
		PhanLoaiDAO phanLoaiDAO = new PhanLoaiDAO();
		NhaCungCapDAO nhaCungCapDao = new NhaCungCapDAO();
		KhuyenMaiDAO khuyenMaiDao = new KhuyenMaiDAO();

		ArrayList<SanPham> listSanPham = new ArrayList<>();
		KetNoiSQL.getInstance();
		Connection conn = KetNoiSQL.getConnection();

		try {
			String sql = "SELECT SanPham.maSP, SanPham.tenSP, PhanLoai.phanLoai, KichThuoc.kichThuoc, MauSac.mauSac, SanPham.soLuong, SanPham.giaBan\n"
					+ "FROM SanPham\n" + "JOIN KichThuoc ON SanPham.maKT = KichThuoc.maKT\n"
					+ "JOIN MauSac ON SanPham.maMS = MauSac.maMS\n" + "JOIN PhanLoai ON SanPham.maPL = PhanLoai.maPL;\n"
					+ "";

			PreparedStatement stmt = conn.prepareCall(sql);

			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String masp = rs.getString("maSP");
				String ten = rs.getString("tenSP");
				String maloai = rs.getString("phanLoai");
				String kichthuoc = rs.getString("kichThuoc");
				String mausac = rs.getString("mauSac");
				int sl = rs.getInt("soLuong");
				float giaBan = rs.getFloat("giaBan");
				PhanLoai phanloai = phanLoaiDAO.getPhanLoai(maloai);
				KichThuoc kt = kichThuocDao.getKichThuoc(kichthuoc);
				MauSac ms = mauSacDAO.getMauSac(mausac);
				SanPham sanPham = new SanPham(masp, ten, phanloai, giaBan, kt, sl, ms);
				listSanPham.add(sanPham);
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return listSanPham;
	}

	public ArrayList<SanPham> getAllSanPhamDuoiDinhMuc(int soluong) {

		ChatLieuDAO chatLieuDao = new ChatLieuDAO();
		KieuDangDAO kieuDangDao = new KieuDangDAO();
		KichThuocDAO kichThuocDao = new KichThuocDAO();
		MauSacDAO mauSacDAO = new MauSacDAO();
		XuatXuDAO xuatXuDAO = new XuatXuDAO();
		PhanLoaiDAO phanLoaiDAO = new PhanLoaiDAO();
		NhaCungCapDAO nhaCungCapDao = new NhaCungCapDAO();
		KhuyenMaiDAO khuyenMaiDao = new KhuyenMaiDAO();

		ArrayList<SanPham> listSanPham = new ArrayList<>();
		KetNoiSQL.getInstance();
		Connection conn = KetNoiSQL.getConnection();

		try {
			String sql = "SELECT \n" + "    SanPham.maSP,\n" + "    SanPham.tenSP,\n" + "    PhanLoai.phanLoai,\n"
					+ "    KichThuoc.kichThuoc,\n" + "    MauSac.mauSac,\n" + "    SanPham.soLuong,\n"
					+ "    SanPham.giaBan\n" + "FROM \n" + "    SanPham\n" + "JOIN \n"
					+ "    KichThuoc ON SanPham.maKT = KichThuoc.maKT\n" + "JOIN \n"
					+ "    PhanLoai ON SanPham.maPL = PhanLoai.maPL\n" + "JOIN \n"
					+ "    MauSac ON SanPham.maMS = MauSac.maMS\n" + "WHERE \n" + "    SanPham.soLuong < (?)\n" + "";

			PreparedStatement stmt = conn.prepareCall(sql);
			stmt.setInt(1, soluong);

			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String masp = rs.getString("maSP");
				String ten = rs.getString("tenSP");
				String maloai = rs.getString("phanLoai");
				String kichthuoc = rs.getString("kichThuoc");
				String mausac = rs.getString("mauSac");
				int sl = rs.getInt("soLuong");
				float giaBan = rs.getFloat("giaBan");
				PhanLoai phanloai = phanLoaiDAO.getPhanLoai(maloai);
				KichThuoc kt = kichThuocDao.getKichThuoc(kichthuoc);
				MauSac ms = mauSacDAO.getMauSac(mausac);
				SanPham sanPham = new SanPham(masp, ten, phanloai, giaBan, kt, sl, ms);
				listSanPham.add(sanPham);
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return listSanPham;
	}

	public ArrayList<SanPham> getAllSanPhamVuotDinhMuc(int soluong) {
		ChatLieuDAO chatLieuDao = new ChatLieuDAO();
		KieuDangDAO kieuDangDao = new KieuDangDAO();
		KichThuocDAO kichThuocDao = new KichThuocDAO();
		MauSacDAO mauSacDAO = new MauSacDAO();
		XuatXuDAO xuatXuDAO = new XuatXuDAO();
		PhanLoaiDAO phanLoaiDAO = new PhanLoaiDAO();
		NhaCungCapDAO nhaCungCapDao = new NhaCungCapDAO();
		KhuyenMaiDAO khuyenMaiDao = new KhuyenMaiDAO();

		ArrayList<SanPham> listSanPham = new ArrayList<>();
		KetNoiSQL.getInstance();
		Connection conn = KetNoiSQL.getConnection();

		try {
			String sql = "SELECT \n" + "    SanPham.maSP,\n" + "    SanPham.tenSP,\n" + "    PhanLoai.phanLoai,\n"
					+ "    KichThuoc.kichThuoc,\n" + "    MauSac.mauSac,\n" + "    SanPham.soLuong,\n"
					+ "    SanPham.giaBan\n" + "FROM \n" + "    SanPham\n" + "JOIN \n"
					+ "    KichThuoc ON SanPham.maKT = KichThuoc.maKT\n" + "JOIN \n"
					+ "    PhanLoai ON SanPham.maPL = PhanLoai.maPL\n" + "JOIN \n"
					+ "    MauSac ON SanPham.maMS = MauSac.maMS\n" + "WHERE \n" + "    SanPham.soLuong > (?)\n" + "";

			PreparedStatement stmt = conn.prepareCall(sql);
			stmt.setInt(1, soluong);

			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String masp = rs.getString("maSP");
				String ten = rs.getString("tenSP");
				String maloai = rs.getString("phanLoai");
				String kichthuoc = rs.getString("kichThuoc");
				String mausac = rs.getString("mauSac");
				int sl = rs.getInt("soLuong");
				float giaBan = rs.getFloat("giaBan");
				PhanLoai phanloai = phanLoaiDAO.getPhanLoai(maloai);
				KichThuoc kt = kichThuocDao.getKichThuoc(kichthuoc);
				MauSac ms = mauSacDAO.getMauSac(mausac);
				SanPham sanPham = new SanPham(masp, ten, phanloai, giaBan, kt, sl, ms);
				listSanPham.add(sanPham);
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return listSanPham;
	}
	public SanPham getSanPhamById(String id) {

        ChatLieuDAO chatLieuDao = new ChatLieuDAO();
        KieuDangDAO kieuDangDao = new KieuDangDAO();
        KichThuocDAO kichThuocDao = new KichThuocDAO();
        MauSacDAO mauSacDAO = new MauSacDAO();
        XuatXuDAO xuatXuDAO = new XuatXuDAO();
        PhanLoaiDAO phanLoaiDAO = new PhanLoaiDAO();
        NhaCungCapDAO nhaCungCapDao = new NhaCungCapDAO();
        KhuyenMaiDAO khuyenMaiDao = new KhuyenMaiDAO();

        KetNoiSQL.getInstance();
        Connection con = KetNoiSQL.getConnection();

        try {
            String sql = "select * from sanpham where maSP = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
            	String maSP = rs.getString(1);
	            String tenSP = rs.getString(2);
	            double giaNhap = rs.getDouble(3);
	            int soLuong = rs.getInt(4);
	            
	            String maNCC = rs.getString(5);
                NhaCungCap nhaCungCap = nhaCungCapDao.getNhaCungCap(maNCC);
                
                String maKhuyenMai = rs.getString(6);
                KhuyenMai khuyenMai = khuyenMaiDao.getKhuyenMai(maKhuyenMai);
                
                int trangthai = rs.getInt(7);
                
                String maChatLieu = rs.getString(8);
                ChatLieu chatLieu = chatLieuDao.getChatLieu(maChatLieu);
                
                String maKieuDang = rs.getString(9);
                KieuDang kieuDang = kieuDangDao.getKieuDang(maKieuDang);
                
                String maKichThuoc = rs.getString(10);
                KichThuoc kichThuoc = kichThuocDao.getKichThuoc(maKichThuoc);
                
                String maMauSac = rs.getString(11);
                MauSac ms = mauSacDAO.getMauSac(maMauSac);
                
                String maXuatXu = rs.getString(12);
                XuatXu xuatXu = xuatXuDAO.getXuatXu(maXuatXu);
                
                String maPhanLoai = rs.getString(13);
                PhanLoai phanLoai = phanLoaiDAO.getPhanLoai(maPhanLoai);
                
                int loi = rs.getInt(14);
                double giaban = rs.getDouble(15);
	            String hinhAnh = rs.getString(16);
	            
	            SanPham sanPham = new SanPham(maSP, tenSP, phanLoai, giaNhap, loi, khuyenMai, giaban, kichThuoc, soLuong, ms, chatLieu, nhaCungCap, hinhAnh, trangthai);
                return sanPham;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

	/// code của phát
	public ArrayList<SanPham> topNSanPham() {
		ArrayList<SanPham> listSanPham = new ArrayList<>();
		KetNoiSQL.getInstance();
		Connection conn = KetNoiSQL.getConnection();
		SanPhamDAO sp_DAO = new SanPhamDAO();
		String sql = "SELECT TOP 10\n"
				+ "    sanPham.maSP,\n"
				+ "    SUM(ChiTietHoaDon.soLuong) AS tongSoLuong\n"
				+ "FROM\n"
				+ "    ChiTietHoaDon\n"
				+ "INNER JOIN\n"
				+ "    HoaDon ON ChiTietHoaDon.maHD = HoaDon.maHD\n"
				+ "INNER JOIN\n"
				+ "    sanPham ON ChiTietHoaDon.maSP = sanPham.maSP\n"
				+ "GROUP BY\n"
				+ "    sanPham.maSP\n"
				+ "ORDER BY\n"
				+ "    SUM(ChiTietHoaDon.soLuong) DESC;\n"
				+ "";
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				SanPham sp = sp_DAO.getSanPhamById(rs.getString(1));
				sp.setSoLuong(rs.getInt(2));
				listSanPham.add(sp);

			}
			return listSanPham;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public ArrayList<SanPham> topNSanPhamBanCham() {
		ArrayList<SanPham> listSanPham = new ArrayList<>();
		KetNoiSQL.getInstance();
		Connection conn = KetNoiSQL.getConnection();
		SanPhamDAO sp_DAO = new SanPhamDAO();
		String sql = "SELECT TOP 10\n"
				+ "    sanPham.maSP,\n"
				+ "    SUM(ChiTietHoaDon.soLuong) AS tongSoLuong\n"
				+ "FROM\n"
				+ "    ChiTietHoaDon\n"
				+ "INNER JOIN\n"
				+ "    HoaDon ON ChiTietHoaDon.maHD = HoaDon.maHD\n"
				+ "INNER JOIN\n"
				+ "    sanPham ON ChiTietHoaDon.maSP = sanPham.maSP\n"
				+ "GROUP BY\n"
				+ "    sanPham.maSP\n"
				+ "ORDER BY\n"
				+ "    SUM(ChiTietHoaDon.soLuong) ASC;\n"
				+ "";
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				SanPham sp = sp_DAO.getSanPhamById(rs.getString(1));
				sp.setSoLuong(rs.getInt(2));
				listSanPham.add(sp);

			}
			return listSanPham;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public ArrayList<SanPham> getAllSanPham(String maSanPham, String tenSanPham, String loaiSanPham, String mauSac, String kichCo) {

		ChatLieuDAO chatLieuDao = new ChatLieuDAO();
		KieuDangDAO kieuDangDao = new KieuDangDAO();
		KichThuocDAO kichThuocDao = new KichThuocDAO();
		MauSacDAO mauSacDAO = new MauSacDAO();
		XuatXuDAO xuatXuDAO = new XuatXuDAO();
		PhanLoaiDAO phanLoaiDAO = new PhanLoaiDAO();
		NhaCungCapDAO nhaCungCapDao = new NhaCungCapDAO();
		KhuyenMaiDAO khuyenMaiDao = new KhuyenMaiDAO();

		ArrayList<SanPham> listSanPham = new ArrayList<>();
		KetNoiSQL.getInstance();
		Connection conn = KetNoiSQL.getConnection();

		try {
			String sql = "SELECT sanPham.*\n"
					+ "FROM sanPham\n"
					+ "INNER JOIN KichThuoc ON KichThuoc.maKT = sanPham.maKT\n"
					+ "INNER JOIN MauSac ON MauSac.maMS = sanPham.maMS\n"
					+ "INNER JOIN PhanLoai ON PhanLoai.maPL = sanPham.maPL\n"
					+ "WHERE sanPham.maSP LIKE ? \n"
					+ "    And KichThuoc.kichThuoc LIKE ? \n"
					+ "    AND PhanLoai.phanLoai LIKE ? \n"
					+ "    AND MauSac.mauSac LIKE ? \n"
					+ "    and sanPham.tenSP LIKE ?;\n"
					+ "";

			PreparedStatement stmt = conn.prepareCall(sql);
			stmt.setString(1, "%" + maSanPham + "%");
			stmt.setString(2, "%" + kichCo + "%");
			stmt.setString(3, "%" + loaiSanPham + "%");
			stmt.setString(4, "%" + mauSac + "%");
			stmt.setString(5, "%" + tenSanPham + "%");

			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String maSP = rs.getString(1);
	            String tenSP = rs.getString(2);
	            double giaNhap = rs.getDouble(3);
	            int soLuong = rs.getInt(4);
	            
	            String maNCC = rs.getString(5);
                NhaCungCap nhaCungCap = nhaCungCapDao.getNhaCungCap(maNCC);
                
                String maKhuyenMai = rs.getString(6);
                KhuyenMai khuyenMai = khuyenMaiDao.getKhuyenMai(maKhuyenMai);
                
                int trangthai = rs.getInt(7);
                
                String maChatLieu = rs.getString(8);
                ChatLieu chatLieu = chatLieuDao.getChatLieu(maChatLieu);
                
                String maKieuDang = rs.getString(9);
                KieuDang kieuDang = kieuDangDao.getKieuDang(maKieuDang);
                
                String maKichThuoc = rs.getString(10);
                KichThuoc kichThuoc = kichThuocDao.getKichThuoc(maKichThuoc);
                
                String maMauSac = rs.getString(11);
                MauSac ms = mauSacDAO.getMauSac(maMauSac);
                
                String maXuatXu = rs.getString(12);
                XuatXu xuatXu = xuatXuDAO.getXuatXu(maXuatXu);
                
                String maPhanLoai = rs.getString(13);
                PhanLoai phanLoai = phanLoaiDAO.getPhanLoai(maPhanLoai);
                
                int loi = rs.getInt(14);
                double giaban = rs.getDouble(15);
	            String hinhAnh = rs.getString(16);
	            
	            SanPham sanPham = new SanPham(maSP, tenSP, phanLoai, giaNhap, loi, khuyenMai, giaban, kichThuoc, soLuong, ms, chatLieu, nhaCungCap, hinhAnh, trangthai);
	            listSanPham.add(sanPham);           
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return listSanPham;
	}
}
