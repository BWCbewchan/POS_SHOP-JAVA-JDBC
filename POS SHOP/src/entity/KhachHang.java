package entity;

import java.util.Date;

import dao.KhachHangDAO;
import dao.NhanVienDAO;

public class KhachHang {
	private String maKH, tenKH;
	private String email;
	private String SDT;
	private boolean gioiTinh;
	public KhachHang(String maKH, String tenKH, String email, String sDT, boolean gioiTinh) {
		super();
		this.maKH = maKH;
		this.tenKH = tenKH;
		this.email = email;
		SDT = sDT;
		this.gioiTinh = gioiTinh;
	}
	public KhachHang() {
		super();
	}
	public String getMaKH() {
		return maKH;
	}
	public void setMaKH(String maKH) {
		this.maKH = maKH;
	}
	public String getTenKH() {
		return tenKH;
	}
	public void setTenKH(String tenKH) {
		this.tenKH = tenKH;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSDT() {
		return SDT;
	}
	public void setSDT(String sDT) {
		SDT = sDT;
	}
	public boolean isGioiTinh() {
		return gioiTinh;
	}
	public void setGioiTinh(boolean gioiTinh) {
		this.gioiTinh = gioiTinh;
	}

	public String getAutoID() {
		KhachHangDAO khachHangDAO = new KhachHangDAO();
		String idPrefix = "KH";
		int length = khachHangDAO.doTuBang().size();
		String finalId = idPrefix + String.format("%02d", length + 1);
		return finalId;
	}
	@Override
	public String toString() {
		return "KhachHang [maKH=" + maKH + ", tenKH=" + tenKH + ", email=" + email + ", SDT=" + SDT + ", gioiTinh="
				+ gioiTinh + "]";
	}
	
}
