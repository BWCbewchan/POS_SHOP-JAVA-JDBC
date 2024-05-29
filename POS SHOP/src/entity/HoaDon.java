package entity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dao.ChiTietHoaDonDAO;
import dao.HoaDonDAO;
import dao.SanPhamDAO;

public class HoaDon {
	private String maHoaDon;
	private Date ngayLap;
	private KhachHang khachHang;
	private NhanVien nhanVien;
	private int trangthai;
	private double tongtien;
	
	public HoaDon() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public HoaDon(String maHoaDon, NhanVien nhanVien, KhachHang khachHang, Date ngayLap) {
		super();
		this.maHoaDon = maHoaDon;
		this.ngayLap = ngayLap;
		this.khachHang = khachHang;
		this.nhanVien = nhanVien;
	}

	public HoaDon(String maHoaDon, Date ngayLap, KhachHang khachHang, NhanVien nhanVien, int trangthai,
			double tongtien) {
		super();
		this.maHoaDon = maHoaDon;
		this.ngayLap = ngayLap;
		this.khachHang = khachHang;
		this.nhanVien = nhanVien;
		this.trangthai = trangthai;
		this.tongtien = tongtien;
	}
	
	public HoaDon(String maHoaDon, KhachHang khachHang, NhanVien nhanVien) {
		super();
		this.maHoaDon = maHoaDon;
		this.khachHang = khachHang;
		this.nhanVien = nhanVien;
	}

	public HoaDon(String maHoaDon, Date ngayLap, KhachHang khachHang, NhanVien nhanVien, int trangthai) {
	this.maHoaDon = maHoaDon;
	this.ngayLap = ngayLap;
	this.khachHang = khachHang;
	this.nhanVien = nhanVien;
	this.trangthai = trangthai;
	}

	public HoaDon(String maHoaDon, Date ngayLap, KhachHang khachHang, NhanVien nhanVien) {
		this.maHoaDon = maHoaDon;
		this.ngayLap = ngayLap;
		this.khachHang = khachHang;
		this.nhanVien = nhanVien;
	}

	public String getMaHoaDon() {
		return maHoaDon;
	}

	public void setMaHoaDon(String maHoaDon) {
		// Kiểm tra và xử lý logic kiểm tra maHoaDon
		this.maHoaDon = maHoaDon;
	}

	public Date getNgayLap() {
		return ngayLap;
	}

	public void setNgayLap(Date ngayLap) {
		Date currentDate = new Date();
		if (ngayLap.after(currentDate)) {
			throw new IllegalArgumentException("Hóa đơn phải được lập ngày hiện tại");
		}
		this.ngayLap = ngayLap;
	}

	public KhachHang getKhachHang() {
		return khachHang;
	}

	public void setKhachHang(KhachHang khachHang) {
		// Kiểm tra và xử lý logic kiểm tra khachHang
		this.khachHang = khachHang;
	}

	public NhanVien getNhanVien() {
		return nhanVien;
	}

	public void setNhanVien(NhanVien nhanVien) {
		// Kiểm tra và xử lý logic kiểm tra nhanVien
		this.nhanVien = nhanVien;
	}

//	public List<ChiTietHoaDon> getChiTietHoaDonList() {
//		return chiTietHoaDonList;
//	}

//	public void themChiTietHoaDon(ChiTietHoaDon chiTietHoaDon) {
//		chiTietHoaDonList.add(chiTietHoaDon);
//	}

	public double getTongtien() {
		return tongtien;
	}

	public void setTongtien(double tongtien) {
		this.tongtien = tongtien;
	}

	public double tongTien() {
		double tongTien = 0;
		ChiTietHoaDonDAO ds = new ChiTietHoaDonDAO();
		List<ChiTietHoaDon> list = ds.getChiTietHoaDonTheoMaHD(maHoaDon);
		for (ChiTietHoaDon chiTietHoaDon : list) {
			tongTien += chiTietHoaDon.tinhGiaSauKhuyenMai(chiTietHoaDon.thanhTien());
		}
		return tongTien;
	}
	public int getTrangthai() {
		return trangthai;
	}

	public void setTrangthai(int trangthai) {
		this.trangthai = trangthai;
	}

	public String getAutoID(){
		HoaDonDAO hoaDonDAO = new HoaDonDAO();
        String idPrefix = "HD";
       int length = hoaDonDAO.fullhoadon().size();
       String finalId = idPrefix + String.format("%02d", length + 1);
       return finalId;
	}
	@Override
	public String toString() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		return "HoaDon [maHoaDon=" + maHoaDon + ", ngayLap=" + dateFormat.format(ngayLap) + ", khachHang=" + khachHang
				+ ", nhanVien=" + nhanVien + "]";
	}
}
