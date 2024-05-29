package entity;

import dao.PhanLoaiDAO;

public class PhanLoai {
    private String maPhanLoai;
    private String phanLoai;
	public PhanLoai(String maPhanLoai, String phanLoai) {
		super();
		this.maPhanLoai = maPhanLoai;
		this.phanLoai = phanLoai;
	}
	public PhanLoai() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getMaPhanLoai() {
		return maPhanLoai;
	}
	public void setMaPhanLoai(String maPhanLoai) {
		this.maPhanLoai = maPhanLoai;
	}
	public String getPhanLoai() {
		return phanLoai;
	}
	public void setPhanLoai(String phanLoai) {
		this.phanLoai = phanLoai;
	}
	public String getAutoID() {
		PhanLoaiDAO phanLoaiDAO = new PhanLoaiDAO();
		String idPrefix = "PL";
		int length = phanLoaiDAO.getAllPhanLoai().size();
		String finalId = idPrefix + String.format("%02d", length + 1);
		return finalId;
	}
	@Override
	public String toString() {
		return "PhanLoai [maPhanLoai=" + maPhanLoai + ", phanLoai=" + phanLoai + "]";
	}
	
    
}

