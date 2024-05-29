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

public class ChiTietHoaDonDAO {
	ArrayList<ChiTietHoaDon> dscthd;
	ChiTietHoaDon cthd;
	SanPhamDAO sanPham_DAO = new SanPhamDAO();

	public ChiTietHoaDonDAO() {
		KetNoiSQL.getInstance().connect();
		dscthd = new ArrayList<ChiTietHoaDon>();
	}

	// Lấy danh sách thông tin Chi Tiết Hóa Đơn từ SQL
	public ArrayList<ChiTietHoaDon> getChiTietHoaDonTheoMaHD(String maHD) {
		try {
			KetNoiSQL.getInstance().connect();
			Connection con = KetNoiSQL.getInstance().getConnection();
			PreparedStatement stmt = null;
			String sql = "Select chd.maSP,sp.tenSP,sp.giaNhap,chd.soLuong,chd.phanTramKhuyenmai,cl.chatLieu,kd.kieuDang,kt.kichThuoc,ms.mauSac,pl.phanLoai, chd.thanhTien\r\n"
					+ "from ChiTietHoaDon chd join sanPham sp on chd.maSP = sp.maSP left join KhuyenMai km on sp.maKM = km.maKM\r\n"
					+ "join ChatLieu cl on sp.maCL = cl.maCL join KieuDang kd on sp.maKD = kd.maKD join KichThuoc kt on sp.maKT = kt.maKT\r\n"
					+ "join MauSac ms on sp.maMS = ms.maMS join PhanLoai pl on sp.maPL = pl.maPL where chd.maHD = ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, maHD);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String maSP = rs.getString(1);
				String tenSP = rs.getString(2);
				Double giaNhap = rs.getDouble(3);
				int soLuong = rs.getInt(4);
				Double phanTramKhuyenMai = rs.getDouble(5);
				String chatLieu = rs.getString(6);
				String kieuDang = rs.getString(7);
				String kichThuoc = rs.getString(8);
				String mauSac = rs.getString(9);
				String phanLoai = rs.getString(10);
				Double thanhTien = rs.getDouble(11);
				ChiTietHoaDon cthd = new ChiTietHoaDon(
						new SanPham(maSP, tenSP, new PhanLoai(null, phanLoai), giaNhap, 0, null, 0,
								new KichThuoc(null, kichThuoc), 0, new MauSac(null, mauSac),
								new ChatLieu(null, chatLieu), null, new KieuDang(null, kieuDang), null, null, 0),
						null, phanTramKhuyenMai, soLuong, thanhTien);
				dscthd.add(cthd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dscthd;
	}
	
	public int addSanPhamVaoHD(ChiTietHoaDon chiTietHoaDon) {
		KetNoiSQL.getInstance().connect();
    try {
    	Connection con = KetNoiSQL.getInstance().getConnection();
        String sql = "Insert into ChiTietHoaDon values(?,?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, chiTietHoaDon.getSanPham().getMaSP());
        ps.setString(2, chiTietHoaDon.getHoaDon().getMaHoaDon());
        ps.setDouble(3, chiTietHoaDon.getPhanTramKhuyenMai());
        ps.setInt(4, chiTietHoaDon.getSoLuong());
        ps.setDouble(5, chiTietHoaDon.getThanhTien());
        return ps.executeUpdate();
    } catch (SQLException ex) {
    	ex.printStackTrace();
    }
    return -1;
	}
    
    public int updateSoLuongSPTrongGio(ChiTietHoaDon chiTietHoaDon) {
		KetNoiSQL.getInstance().connect();
		;
		Connection conn = KetNoiSQL.getConnection();

		try {
			String sql = "update ChiTietHoaDon set soLuong =(?), thanhTien =(?) where maSP =(?) and maHD = (?)";

			PreparedStatement stmt = conn.prepareCall(sql);
			stmt.setInt(1, chiTietHoaDon.getSoLuong());
			stmt.setDouble(2, chiTietHoaDon.getThanhTien());
			stmt.setString(3, chiTietHoaDon.getSanPham().getMaSP());
			stmt.setString(4, chiTietHoaDon.getHoaDon().getMaHoaDon());
			return stmt.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return -1;
    }
    public int	deleteMotSP(ChiTietHoaDon chiTietHoaDon) {
		KetNoiSQL.getInstance().connect();
		;
		Connection conn = KetNoiSQL.getConnection();

		try {
			String sql = "delete ChiTietHoaDon where maSP = (?) and maHD = (?)";

			PreparedStatement stmt = conn.prepareCall(sql);
			stmt.setString(1, chiTietHoaDon.getSanPham().getMaSP());
			stmt.setString(2, chiTietHoaDon.getHoaDon().getMaHoaDon());
			return stmt.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return -1;
    }
    
    public double getTongTien(String id) {
    	double thanhtien = 0;
		try {
			KetNoiSQL.getInstance().connect();
			Connection con = KetNoiSQL.getInstance().getConnection();
			String sql = "select sum(thanhTien) from ChiTietHoaDon where maHD = (?) group by maHD";
			PreparedStatement stmt = con.prepareCall(sql);
			stmt.setString(1, id);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				thanhtien = rs.getDouble(1);
				
			}

			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return thanhtien;
	}
    
    public ArrayList<SanPham>thongKeDanhSachSanPhamVoiSoLuongBanDuoc(String mauSac, String phanLoai, String kichThuoc){
        ArrayList<SanPham> listSanPham = new ArrayList<>();
        KetNoiSQL.getInstance();
        Connection conn = KetNoiSQL.getConnection();
        try {
            String sql = "SELECT\n"
            		+ "    sp.maSP,\n"
            		+ "    SUM(cthd.soLuong) AS SoLuongBan,\n"
            		+ "    SUM(cthd.thanhTien) AS DoanhThu\n"
            		+ "FROM\n"
            		+ "    sanPham AS sp\n"
            		+ "INNER JOIN\n"
            		+ "    ChiTietHoaDon AS cthd ON cthd.maSP = sp.maSP\n"
            		+ "INNER JOIN\n"
            		+ "    KichThuoc AS kt ON sp.maKT = kt.maKT\n"
            		+ "INNER JOIN\n"
            		+ "    MauSac AS ms ON sp.maMS = ms.maMS\n"
            		+ "INNER JOIN\n"
            		+ "    PhanLoai AS pl ON sp.maPL = pl.maPL\n"
            		+ "WHERE\n"
            		+ "    kt.kichThuoc LIKE ?\n"
            		+ "    AND ms.mauSac LIKE ?\n"
            		+ "    AND pl.phanLoai LIKE ?\n"
            		+ "GROUP BY\n"
            		+ "    sp.maSP;\n"
            		+ "";
            
            PreparedStatement stmt = conn.prepareCall(sql);
            stmt.setString(1, "%"+kichThuoc+"%");
            stmt.setString(2, "%"+mauSac+"%");
            stmt.setString(3, "%"+phanLoai+"%");
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                SanPham sp = sanPham_DAO.getSanPhanTheoId(rs.getString(1));
                sp.setSoLuong(rs.getInt(2));
                sp.setGiaNhap(rs.getLong(3));
                listSanPham.add(sp);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return listSanPham;
    }
    
    
    public ArrayList<SanPham>thongKeDanhSachSanPhamVoiSoLuongBanDuoc(String mauSac, String phanLoai, String kichThuoc, String tuNgay, String denNgay){
        ArrayList<SanPham> listSanPham = new ArrayList<>();
        KetNoiSQL.getInstance();
        Connection conn = KetNoiSQL.getConnection();
        try {
            String sql = "SELECT\n"
            		+ "    sanPham.maSP,\n"
            		+ "    SUM(ChiTietHoaDon.soLuong) AS SoLuongBan,\n"
            		+ "    SUM(ChiTietHoaDon.thanhTien) AS DoanhThu\n"
            		+ "FROM\n"
            		+ "    ChiTietHoaDon\n"
            		+ "INNER JOIN\n"
            		+ "    sanPham ON ChiTietHoaDon.maSP = sanPham.maSP\n"
            		+ "INNER JOIN\n"
            		+ "    KichThuoc ON sanPham.maKT = KichThuoc.maKT\n"
            		+ "INNER JOIN\n"
            		+ "    MauSac ON sanPham.maMS = MauSac.maMS\n"
            		+ "INNER JOIN\n"
            		+ "    PhanLoai ON sanPham.maPL = PhanLoai.maPL\n"
            		+ "INNER JOIN\n"
            		+ "    HoaDon ON ChiTietHoaDon.maHD = HoaDon.maHD\n"
            		+ "WHERE\n"
            		+ "    KichThuoc.kichThuoc LIKE ?\n"
            		+ "    AND MauSac.mauSac LIKE ?\n"
            		+ "    AND PhanLoai.phanLoai LIKE ?\n"
            		+ "    AND HoaDon.ngayLap >= ?\n"
            		+ "    AND HoaDon.ngayLap <= ?\n"
            		+ "GROUP BY\n"
            		+ "    sanPham.maSP;\n"
            		+ "";
            
            PreparedStatement stmt = conn.prepareCall(sql);
            stmt.setString(1, "%"+kichThuoc+"%");
            stmt.setString(2, "%"+mauSac+"%");
            stmt.setString(3, "%"+phanLoai+"%");
            stmt.setString(4, tuNgay);
            stmt.setString(5, denNgay);
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                SanPham sp = sanPham_DAO.getSanPhanTheoId(rs.getString(1));
                sp.setSoLuong(rs.getInt(2));
                sp.setGiaNhap(rs.getLong(3));
                listSanPham.add(sp);
            }
        } catch (SQLException ex) {
        	ex.printStackTrace();
        }
        return listSanPham;
    }
    public double tongTienHoaDon(String maHD){
        KetNoiSQL.getConnection();
        Connection conn = KetNoiSQL.getConnection();
        String sql  = "select ChiTietHoaDon.maHD,  tongTien = sum(ChiTietHoaDon.thanhTien) \n" +
                        "from ChiTietHoaDon \n" +
                        "where maHD = ?\n" +
                        "group by ChiTietHoaDon.maHD";        
        try {
            PreparedStatement stmt = conn.prepareCall(sql);
            stmt.setString(1, maHD);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                return rs.getDouble(2);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return -1;
    }
}
