package entity;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import dao.NhanVienDAO;
import dao.SanPhamDAO;

public class NhanVien {
	private String maNV;
	private String tenNV;
	private Date ngaySinh;
	private String SDT;
	private String email;
	private String CMND;
	private boolean gioiTinh;
	private String diaChi;
	private boolean chucVu;
	private int trangThai;

	public NhanVien(String maNV) {
		super();
		this.maNV = maNV;
		this.tenNV = tenNV;
		this.ngaySinh = ngaySinh;
		this.SDT = SDT;
		this.email = email;
		this.CMND = CMND;
		this.gioiTinh = gioiTinh;
		this.diaChi = diaChi;
		this.chucVu = chucVu;
		this.trangThai = trangThai;
	}

	public NhanVien(String maNV, String tenNV, Date ngaySinh, String sDT, String email, String cMND, boolean gioiTinh,
			String diaChi, boolean chucVu, int trangThai) {
		super();
		this.maNV = maNV;
		this.tenNV = tenNV;
		this.ngaySinh = ngaySinh;
		SDT = sDT;
		this.email = email;
		CMND = cMND;
		this.gioiTinh = gioiTinh;
		this.diaChi = diaChi;
		this.chucVu = chucVu;
		this.trangThai = trangThai;
	}

	public NhanVien(String tenNV, Date ngaySinh, String sDT, String email, boolean gioiTinh, String diaChi,
			boolean chucVu, String maNV) {
		super();
		this.maNV = maNV;
		this.tenNV = tenNV;
		this.ngaySinh = ngaySinh;
		SDT = sDT;
		this.email = email;
		this.gioiTinh = gioiTinh;
		this.diaChi = diaChi;
		this.chucVu = chucVu;
	}

	public NhanVien(String tenNV, boolean chucVu, int trangThai) {
		this.maNV = maNV;
		this.tenNV = tenNV;
		this.chucVu = chucVu;
		this.trangThai = trangThai;
	}

	public NhanVien() {
		// TODO Auto-generated constructor stub
	}

	public String getMaNV() {
		return maNV;
	}

	public void setMaNV(String maNV) {
		this.maNV = maNV;
	}

	public String getTenNV() {
		return tenNV;
	}

	public void setTenNV(String tenNV) {
		this.tenNV = tenNV;
	}

	public Date getNgaySinh() {
		return ngaySinh;
	}

	public void setNgaySinh(Date ngaySinh) {
		this.ngaySinh = ngaySinh;
	}

	public String getSDT() {
		return SDT;
	}

	public void setSDT(String sDT) {
		this.SDT = sDT;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCMND() {
		return CMND;
	}

	public void setCMND(String cMND) {
		this.CMND = cMND;
	}

	public boolean isGioiTinh() {
		return gioiTinh;
	}

	public void setGioiTinh(boolean gioiTinh) {
		this.gioiTinh = gioiTinh;
	}

	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	public boolean isChucVu() {
		return chucVu;
	}

	public void setChucVu(boolean chucVu) {
		this.chucVu = chucVu;
	}

	public int getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(int trangThai) {
		this.trangThai = trangThai;
	}

	public String getAutoID() {
		NhanVienDAO sanPham_DAO = new NhanVienDAO();
		String idPrefix = "NV";
		int length = sanPham_DAO.getAllNhanVien().size();
		String finalId = idPrefix + String.format("%02d", length + 1);
		return finalId;
	}

	@Override
	public String toString() {
		return "NhanVien [maNV=" + maNV + ", tenNV=" + tenNV + ", ngaySinh=" + ngaySinh + ", SDT=" + SDT + ", email="
				+ email + ", CMND=" + CMND + ", gioiTinh=" + gioiTinh + ", diaChi=" + diaChi + ", chucVu=" + chucVu
				+ ", trangThai=" + trangThai + "]";
	}
}
