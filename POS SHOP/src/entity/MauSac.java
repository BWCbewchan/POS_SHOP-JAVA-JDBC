package entity;

import dao.MauSacDAO;
import dao.SanPhamDAO;

public class MauSac {
    private String maMauSac;
    private String mauSac;
   
	public MauSac(String maMauSac, String mauSac) {
		super();
		this.maMauSac = maMauSac;
		this.mauSac = mauSac;
	}
	public MauSac() {
		
	}
	public String getMaMauSac() {
		return maMauSac;
	}
	public void setMaMauSac(String maMauSac) {
		this.maMauSac = maMauSac;
	}
	public String getMauSac() {
		return mauSac;
	}
	public void setMauSac(String mauSac) {
		this.mauSac = mauSac;
	}
	public String getAutoID() {
		MauSacDAO sanPham_DAO = new MauSacDAO();
		String idPrefix = "MS";
		int length = sanPham_DAO.getAllMauSac().size();
		String finalId = idPrefix + String.format("%02d", length + 1);
		return finalId;
	}
	@Override
	public String toString() {
		return "MauSac [maMauSac=" + maMauSac + ", mauSac=" + mauSac + "]";
	}
}

