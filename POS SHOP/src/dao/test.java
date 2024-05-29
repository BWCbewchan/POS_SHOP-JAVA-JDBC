package dao;

import java.util.List;

import ConnectDB.KetNoiSQL;
import entity.HoaDon;
import entity.MauSac;
import entity.NhanVien;
import entity.SanPham;

public class test {
    public static void main(String[] args) {
    	KetNoiSQL.getInstance().connect();
    	NhanVienDAO nhanVienDAO = new NhanVienDAO();
    	NhanVien nv = new NhanVien();
        nv = nhanVienDAO.getNhanVienByName("Đinh Thị C");
        System.out.println(nv.toString());
    }
}
