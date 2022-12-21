package app;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.ParseException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

public class GUI_QuanLy extends JFrame implements ActionListener, MouseListener {
	private JLabel lblDoiMK;
	private JLabel lblDangXuat;

	public GUI_QuanLy() throws ParseException {
		setTitle("KARAOKE DIAMOND");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setSize(1000, 800);
		setLocationRelativeTo(null);
		ImageIcon icon = new ImageIcon("image/logodark.png");
		setIconImage(icon.getImage());
		UIManager.put("TabbedPane.selected", new Color(50,190,255));
		add(createTabbedPane());
	}

	/**
	 * create a JTabbedPane contain tabs
	 * @throws ParseException 
	 */
	private JTabbedPane createTabbedPane() throws ParseException {
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		tabbedPane.setUI(new BasicTabbedPaneUI() {
			@Override
			protected int calculateTabHeight(int tabPlacement, int tabIndex, int fontHeight) {
				return 80;
			}
			@Override
			protected int calculateTabWidth(int tabPlacement, int tabIndex, FontMetrics metrics) {
				return 200;
			}
		});
		tabbedPane.setBackground(new Color(0, 148, 224));
		tabbedPane.setForeground(Color.WHITE);
		tabbedPane.setFont(new Font("Tahoma", Font.BOLD, 13));

		FrameKhachHang frameKH = new FrameKhachHang();
		FrameNhanVien frameNV = new FrameNhanVien();
		FramePhongHat framePH = new FramePhongHat();
		FrameDichVu frameDV = new FrameDichVu();

		/* create JPanel, which is content of tabs */
		JPanel pnlTrangChu = createPanelTrangChu();
		JPanel pnlKhachHang = frameKH.createPanelKhachHang();
		JPanel pnlNhanVien = frameNV.createPanelNhanVien();
		JPanel pnlPhongHat = framePH.createPanelPhongHat();
		JPanel pnlDichVu = frameDV.createPanelDichVu();
		
		JTabbedPane tabThongKe = new JTabbedPane();
		tabThongKe.setBackground(new Color(0, 148, 224));
		tabThongKe.setForeground(Color.WHITE);
		tabThongKe.setFont(new Font("Tahoma", Font.BOLD, 13));
		FrameThongKeDoanhThu frameTKDT = new FrameThongKeDoanhThu();
		FrameThongKeKhachHang frameTKKH = new FrameThongKeKhachHang();
		JPanel pnlTKKhachHang = frameTKKH.createPanelKhachHang();
		JPanel pnlTKDoanhThu = frameTKDT.createPanelDoanhThu();
		
		tabThongKe.addTab("THỐNG KÊ DOANH THU", new ImageIcon("image/doanhthu.png"), pnlTKDoanhThu, "THỐNG KÊ DOANH THU");
		tabThongKe.addTab("THỐNG KÊ SỐ LẦN KHÁCH ĐẾN", new ImageIcon("image/khachhang.png"), pnlTKKhachHang, "THỐNG KÊ SỐ LẦN KHÁCH ĐẾN");
		
		/* add tab with JPanel */
		tabbedPane.addTab("TRANG CHỦ", new ImageIcon("image/trangchu.png"), pnlTrangChu, "TRANG CHỦ");
		tabbedPane.addTab("QUẢN LÝ KHÁCH HÀNG", new ImageIcon("image/khachhang.png"), pnlKhachHang, "QUẢN LÝ KHÁCH HÀNG");
		tabbedPane.addTab("QUẢN LÝ NHÂN VIÊN", new ImageIcon("image/nhanvien.png"), pnlNhanVien, "QUẢN LÝ NHÂN VIÊN");
		tabbedPane.addTab("QUẢN LÝ PHÒNG HÁT", new ImageIcon("image/phonghat.png"), pnlPhongHat, "QUẢN LÝ PHÒNG HÁT");
		tabbedPane.addTab("QUẢN LÝ DỊCH VỤ", new ImageIcon("image/dichvu.png"), pnlDichVu, "QUẢN LÝ DỊCH VỤ");
		tabbedPane.addTab("THỐNG KÊ", new ImageIcon("image/thongke.png"), tabThongKe, "THỐNG KÊ");

		return tabbedPane;
	}

	private JPanel createPanelTrangChu() {
		Toolkit toolkit = this.getToolkit(); /* Lấy độ phân giải màn hình */
		Dimension d = toolkit.getScreenSize();

		JPanel pnlContentPane = new JPanel();
		pnlContentPane.setBackground(new Color(204, 0, 0));
		pnlContentPane.setLayout(null);
		
		JLabel lblBackground = new JLabel();
		lblBackground.setBounds(0, 0, (int) d.getWidth(), (int) d.getHeight() - 20);
		lblBackground.setIcon(new ImageIcon("image/trangchu.jpg"));
		lblBackground.setLayout(null);
		pnlContentPane.add(lblBackground);

		ImageIcon imageIcon = new ImageIcon("image/nentrangchu.jpg");
		Image image = imageIcon.getImage();
		Image imageResize = image.getScaledInstance((int) d.getWidth() - 205, (int) d.getHeight() - 290,
				Image.SCALE_SMOOTH);
		JLabel lblBanner = new JLabel(new ImageIcon(imageResize));
		lblBanner.setBounds(0, 60, (int) d.getWidth() - 205, (int) d.getHeight() - 280);
		lblBackground.add(lblBanner);

		/*
		 * Chức năng
		 */

		JLabel lblLogan = new JLabel("WELCOME TO KARAOKE DIAMOND");
		lblLogan.setBounds(20, 0, (int) d.getWidth(), 50);
		lblLogan.setFont(new Font("DialogInput", Font.BOLD, 50));
		lblLogan.setForeground(Color.WHITE);
		lblBackground.add(lblLogan);

		ImageIcon logoIcon = new ImageIcon("image/logowhite.png");
		Image logo = logoIcon.getImage();
		Image logoResize = logo.getScaledInstance(70, 50, Image.SCALE_SMOOTH);
		JLabel lblLogo = new JLabel(new ImageIcon(logoResize));
		lblLogo.setBounds(830, 5, 70, 50);
		lblBackground.add(lblLogo);

		lblDoiMK = new JLabel("<HTML><U>ĐỔI MẬT KHẨU</U></HTML>");
		lblDoiMK.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblDoiMK.setFont(new Font("Tahoma", Font.ITALIC, 15));
		lblDoiMK.setForeground(Color.WHITE);
		lblDoiMK.setBounds((int) d.getWidth() - 100 - 200 - 120, 0, 110, 42);
		lblBackground.add(lblDoiMK);

		lblDangXuat = new JLabel("<HTML><U>ĐĂNG XUẤT</U></HTML>");
		lblDangXuat.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblDangXuat.setFont(new Font("Tahoma", Font.ITALIC, 15));
		lblDangXuat.setForeground(Color.WHITE);
		lblDangXuat.setBounds((int) d.getWidth() - 100 - 200, 0, 150, 42);
		lblBackground.add(lblDangXuat);

		/*
		 * Intro
		 */
		ImageIcon icon1 = new ImageIcon("image/dienthoai.png");
		Image logo1 = icon1.getImage();
		Image logo1Resize = logo1.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
		JLabel lblLogo1 = new JLabel(new ImageIcon(logo1Resize));
		lblLogo1.setBounds(30, (int) d.getHeight() - 214, 32, 32);
		lblBackground.add(lblLogo1);
		JLabel lblLienhe = new JLabel("Booking: (083)7511827");
		lblLienhe.setBounds(70, (int) d.getHeight() - 250, 500, 100);
		lblLienhe.setFont(new Font("DialogInput", Font.BOLD, 25));
		lblLienhe.setForeground(Color.WHITE);
		lblBackground.add(lblLienhe);

		ImageIcon icon2 = new ImageIcon("image/hotline.png");
		Image logo2 = icon2.getImage();
		Image logo2Resize = logo2.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
		JLabel lblLogo2 = new JLabel(new ImageIcon(logo2Resize));
		lblLogo2.setBounds(30, (int) d.getHeight() - 174, 32, 32);
		lblBackground.add(lblLogo2);
		JLabel lblLienhe2 = new JLabel("Hotline: 0794861181");
		lblLienhe2.setBounds(70, (int) d.getHeight() - 210, 500, 100);
		lblLienhe2.setFont(new Font("DialogInput", Font.BOLD, 25));
		lblLienhe2.setForeground(Color.WHITE);
		lblBackground.add(lblLienhe2);

		ImageIcon icon3 = new ImageIcon("image/diachi.png");
		Image logo3 = icon3.getImage();
		Image logo3Resize = logo3.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
		JLabel lblLogo3 = new JLabel(new ImageIcon(logo3Resize));
		lblLogo3.setBounds(30, (int) d.getHeight() - 134, 32, 32);
		lblBackground.add(lblLogo3);
		JLabel lblLienhe3 = new JLabel("Address: Số 14 Nguyễn Huệ, Phường Bến Nghé, Quận 1, TPHCM");
		lblLienhe3.setBounds(70, (int) d.getHeight() - 170, 900, 100);
		lblLienhe3.setFont(new Font("DialogInput", Font.BOLD, 25));
		lblLienhe3.setForeground(Color.WHITE);
		lblBackground.add(lblLienhe3);

		lblDoiMK.addMouseListener(this);
		lblDangXuat.addMouseListener(this);
		return pnlContentPane;

	}

	@Override
	public void actionPerformed(ActionEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Object o = e.getSource();
		if (o.equals(lblDangXuat)) {
			int result = JOptionPane.showConfirmDialog(this, "Bạn có muốn đăng xuất không?", "?!",
					JOptionPane.YES_NO_OPTION);
			if (result == 0) {
				FrameDangNhap frameDN = new FrameDangNhap();
				frameDN.setVisible(true);
				frameDN.setLocationRelativeTo(null);
				dispose();
			}
		}
		if (o.equals(lblDoiMK)) {
			FrameDoiMatKhau frameDoiMK = new FrameDoiMatKhau();
			frameDoiMK.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			frameDoiMK.setVisible(true);
			frameDoiMK.setLocationRelativeTo(null);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
}
