package entity;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dao.KhuyenMaiDAO;

public class KhuyenMai {
	private String maKM, tenKhuyenMai;
	private double phanTramKhuyenMai;
	private Date ngayBatDau;
	private Date ngayKetThuc;
	public KhuyenMai(String maKM, String tenKhuyenMai, double phanTramKhuyenMai, Date ngayBatDau, Date ngayKetThuc) {
		super();
		this.maKM = maKM;
		this.tenKhuyenMai = tenKhuyenMai;
		this.phanTramKhuyenMai = phanTramKhuyenMai;
		this.ngayBatDau = ngayBatDau;
		this.ngayKetThuc = ngayKetThuc;
	}
	public KhuyenMai() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getMaKM() {
		return maKM;
	}
	public void setMaKM(String maKM) {
		this.maKM = maKM;
	}
	public String getTenKhuyenMai() {
		return tenKhuyenMai;
	}
	public void setTenKhuyenMai(String tenKhuyenMai) {
		this.tenKhuyenMai = tenKhuyenMai;
	}
	public double getPhanTramKhuyenMai() {
		return phanTramKhuyenMai;
	}
	public void setPhanTramKhuyenMai(double phanTramKhuyenMai) {
		this.phanTramKhuyenMai = phanTramKhuyenMai;
	}
	public Date getNgayBatDau() {
		return ngayBatDau;
	}
	public void setNgayBatDau(Date ngayBatDau) {
		this.ngayBatDau = ngayBatDau;
	}
	public Date getNgayKetThuc() {
		return ngayKetThuc;
	}
	public void setNgayKetThuc(Date ngayKetThuc) {
		this.ngayKetThuc = ngayKetThuc;
	}
	public void xoaKhuyenMaiKhiHetHan() {
		LocalDate currentDate = LocalDate.now();
		KhuyenMaiDAO ds = new KhuyenMaiDAO();
		List<KhuyenMai> list = ds.doTuBang();
		List<String> danhSachKhuyenMaiHetHan = new ArrayList<>();
		List<SanPham> danhSanPhamHetHanMaKM = new ArrayList<>();
		for (KhuyenMai km : list) {
			java.sql.Date nkt = new java.sql.Date(km.getNgayKetThuc().getTime());
			// Chuyển đổi Date thành LocalDate
            LocalDate endDate = nkt.toLocalDate();
			// Nếu ngày kết thúc lớn hơn hoặc bằng ngày hiện tại
			if (currentDate.isAfter(endDate) || currentDate.isEqual(endDate)) {
				// Thêm các khuyến mãi hết hạn vào danh sách
				danhSachKhuyenMaiHetHan.add(km.getMaKM());
			}
		}
		if (!danhSachKhuyenMaiHetHan.isEmpty()) { // Xóa maKM cho các sản hết hạn khuyến mãi
			for (String maKM : danhSachKhuyenMaiHetHan) {
				danhSanPhamHetHanMaKM = ds.getSanPhanTheoMaKM(maKM);
				 for (SanPham sp : danhSanPhamHetHanMaKM) {
					ds.deleteMaKMChoSanPHamHetHanKM(sp.getMaSP());
				}
				 ds.deleteKhuyenMai(maKM); // Sau khi xóa KM sản phẩm cho sản phẩm thì xóa KM
			}
		}
	}
	
	@Override
	public String toString() {
		return "KhuyenMai [maKM=" + maKM + ", tenKhuyenMai=" + tenKhuyenMai + ", phanTramKhuyenMai=" + phanTramKhuyenMai
				+ ", ngayBatDau=" + ngayBatDau + ", ngayKetThuc=" + ngayKetThuc + "]";
	}

	
}
