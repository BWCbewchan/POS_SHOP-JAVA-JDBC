package dao;

import javax.swing.JPanel;
import java.awt.Dimension;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JTabbedPane;
import java.awt.Font;
import java.awt.CardLayout;

public class QuanLyThongKe extends JPanel{
	public QuanLyThongKe() {
		setBackground(new Color(255, 255, 255));
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setPreferredSize(new Dimension(934, 685));
		setLayout(new CardLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane, "name_34377947137900");
		
		JPanel pnlSanPham = new JPanel();
		pnlSanPham.setFont(new Font("Arial", Font.BOLD, 15));
		tabbedPane.addTab("Sản Phẩm", null, pnlSanPham, null);
		tabbedPane.setEnabledAt(0, true);
		
		JPanel pnlDoanhThu = new JPanel();
		tabbedPane.addTab("DoanhThu", null, pnlDoanhThu, null);
	}
	
}