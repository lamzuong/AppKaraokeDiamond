package app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import com.toedter.calendar.JDateChooser;

//import GUI.FrameDatDichVu;
import connectDB.ConnectDB;
import dao.DatPhong_DAO;
import dao.KhachHang_DAO;
import dao.PhongHat_DAO;
import entity.HoaDon;
import entity.KhachHang;
import entity.NhanVien;
import entity.PhongHat;

public class FrameDatPhong extends JFrame implements ActionListener, MouseListener {
	public static  JTextField txtTenKH;
	public static  JTextField txtCmnd;
	public static  JTextField txtLienLac;
	
	public static DefaultTableModel tableModelPhongTrong;
	public static JTable tablePhongTrong;
	public static DefaultTableModel tableModelPhongDaDat;
	public static JTable tablePhongDaDat;
	

	public static String maPhongMoiDat = "";
	public static String maHDMoiDat = "";
	public static String maKHDatPhong = "";
	

	private JButton btnDatPhong;
	private JButton btnHuyDatPhong;
	private JButton btnLamMoi;
	private JButton btnDatDV;
	private JButton btnTimKHCu;
	private JButton btnTimKH;
	
	public static  JDateChooser txtNgaySinh;
	
	public static JComboBox<String> cmbDanhSachSdt;
	public static  JComboBox<String> cmbGioiTinh;
	public static JComboBox<String> cmbTimKhachHang;
	private JComboBox<String> cmbLoaiPhong;

	public static DatPhong_DAO datphong_dao;
	public static KhachHang_DAO khachhang_dao;
	private PhongHat_DAO phong_dao;
	
	public JPanel createPanelDatPhong() {
		// khởi tạo kết nối đến CSDL
		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		datphong_dao = new DatPhong_DAO();
		khachhang_dao = new KhachHang_DAO();
		phong_dao = new PhongHat_DAO();
		// ------------------------------

		setTitle("ĐẶT PHÒNG");
		setSize(948, 660);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		Toolkit toolkit = this.getToolkit(); /* Lấy độ phân giải màn hình */
		Dimension d = toolkit.getScreenSize();

		JPanel pnlContentPane = new JPanel();
		pnlContentPane.setBackground(Color.WHITE);
		setContentPane(pnlContentPane);
		pnlContentPane.setLayout(null);

		/*
		 * Thông tin khách hàng
		 */

		JPanel pnlKhachHang = new JPanel();
		pnlKhachHang.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), ""));
		pnlKhachHang.setBounds(20, 15, 195, (int) (d.getHeight() - 109));
		pnlKhachHang.setBackground(new Color(255, 255, 255));
		pnlKhachHang.setLayout(null);
		pnlKhachHang.setPreferredSize(new Dimension(177, 850));
		pnlContentPane.add(pnlKhachHang);

		// KHÁCH HÀNG CŨ
		JLabel lblKHCu = new JLabel("<html><div style='text-align: center;'>KHÁCH HÀNG CŨ: </div></html>",
				SwingConstants.CENTER);
		lblKHCu.setBounds(1, 10, 193, 50);
		lblKHCu.setOpaque(true);
		lblKHCu.setBackground(new Color(255, 128, 0));
		lblKHCu.setForeground(Color.WHITE);
		lblKHCu.setFont(new Font("Arial", Font.BOLD, 15));
		pnlKhachHang.add(lblKHCu);

		JLabel lblSdtKHCu = new JLabel("<html><div style='text-align: center;'>SỐ ĐIỆN THOẠI: </div></html>",
				SwingConstants.CENTER);
		lblSdtKHCu.setBounds(1, 70, 193, 30);
		lblSdtKHCu.setOpaque(true);
		lblSdtKHCu.setBackground(new Color(0, 148, 224));
		lblSdtKHCu.setForeground(Color.WHITE);
		lblSdtKHCu.setFont(new Font("Arial", Font.BOLD, 15));
		pnlKhachHang.add(lblSdtKHCu);
		cmbDanhSachSdt = new JComboBox<String>();
		cmbDanhSachSdt.setBounds(22, 110, 150, 30);
		cmbDanhSachSdt.setBackground(Color.WHITE);
		cmbDanhSachSdt.setEditable(true);
		cmbDanhSachSdt.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		AutoCompleteDecorator.decorate(cmbDanhSachSdt);
		cmbDanhSachSdt.setMaximumRowCount(5);
		pnlKhachHang.add(cmbDanhSachSdt);

		btnTimKHCu = new JButton("TÌM KIẾM", new ImageIcon("image/timkiem.png"));
		btnTimKHCu.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnTimKHCu.setBackground(new Color(0, 148, 224));
		btnTimKHCu.setBounds(22, 150, 150, 40);
		btnTimKHCu.setForeground(Color.WHITE);
		btnTimKHCu.setFocusPainted(false);
		pnlKhachHang.add(btnTimKHCu);

		// KHÁCH HÀNG MỚI
		JLabel lblKHMoi = new JLabel("<html><div style='text-align: center;'>KHÁCH HÀNG MỚI: </div></html>",
				SwingConstants.CENTER);
		lblKHMoi.setBounds(1, 210, 193, 50);
		lblKHMoi.setOpaque(true);
		lblKHMoi.setBackground(new Color(255, 128, 0));
		lblKHMoi.setForeground(Color.WHITE);
		lblKHMoi.setFont(new Font("Arial", Font.BOLD, 15));
		pnlKhachHang.add(lblKHMoi);

		JLabel lblTenKH = new JLabel("<html><div style='text-align: center;'>HỌ TÊN: </div></html>",
				SwingConstants.CENTER);
		lblTenKH.setBounds(1, 270, 193, 30);
		lblTenKH.setOpaque(true);
		lblTenKH.setBackground(new Color(0, 148, 224));
		lblTenKH.setForeground(Color.WHITE);
		lblTenKH.setFont(new Font("Arial", Font.BOLD, 15));
		pnlKhachHang.add(lblTenKH);
		txtTenKH = new JTextField();
		txtTenKH.setBounds(22, 310, 150, 30);
		txtTenKH.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlKhachHang.add(txtTenKH);

		JLabel lblSDT = new JLabel("<html><div style='text-align: center;'>SỐ ĐIỆN THOẠI: </div></html>",
				SwingConstants.CENTER);
		lblSDT.setBounds(1, 350, 193, 30);
		lblSDT.setOpaque(true);
		lblSDT.setBackground(new Color(0, 148, 224));
		lblSDT.setForeground(Color.WHITE);
		lblSDT.setFont(new Font("Arial", Font.BOLD, 15));
		pnlKhachHang.add(lblSDT);
		txtLienLac = new JTextField();
		txtLienLac.setBounds(22, 390, 150, 30);
		txtLienLac.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlKhachHang.add(txtLienLac);

		JLabel lblNgaysinh = new JLabel("<html><div style='text-align: center;'>NGÀY SINH: </div></html>",
				SwingConstants.CENTER);
		lblNgaysinh.setBounds(1, 430, 193, 30);
		lblNgaysinh.setOpaque(true);
		lblNgaysinh.setBackground(new Color(0, 148, 224));
		lblNgaysinh.setForeground(Color.WHITE);
		lblNgaysinh.setFont(new Font("Arial", Font.BOLD, 15));
		pnlKhachHang.add(lblNgaysinh);
		txtNgaySinh = new JDateChooser();
		txtNgaySinh.setDateFormatString("yyyy-MM-dd");
		txtNgaySinh.setBounds(22, 470, 150, 30);
		txtNgaySinh.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlKhachHang.add(txtNgaySinh);

		JLabel lblCmnd = new JLabel("<html><div style='text-align: center;'>CMND/CCCD: </div></html>",
				SwingConstants.CENTER);
		lblCmnd.setBounds(1, 510, 193, 30);
		lblCmnd.setOpaque(true);
		lblCmnd.setBackground(new Color(0, 148, 224));
		lblCmnd.setForeground(Color.WHITE);
		lblCmnd.setFont(new Font("Arial", Font.BOLD, 15));
		pnlKhachHang.add(lblCmnd);
		txtCmnd = new JTextField();
		txtCmnd.setBounds(22, 550, 150, 30);
		txtCmnd.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlKhachHang.add(txtCmnd);

		JLabel lblGioiTinh = new JLabel("<html><div style='text-align: center;'>GIỚI TÍNH: </div></html>",
				SwingConstants.CENTER);
		lblGioiTinh.setBounds(1, 590, 193, 30);
		lblGioiTinh.setOpaque(true);
		lblGioiTinh.setBackground(new Color(0, 148, 224));
		lblGioiTinh.setForeground(Color.WHITE);
		lblGioiTinh.setFont(new Font("Arial", Font.BOLD, 15));
		pnlKhachHang.add(lblGioiTinh);
		String[] gioiTinh = { "Nam", "Nữ" };
		cmbGioiTinh = new JComboBox<String>(gioiTinh);
		cmbGioiTinh.setBounds(22, 630, 150, 30);
		cmbGioiTinh.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlKhachHang.add(cmbGioiTinh);

		btnLamMoi = new JButton("LÀM MỚI", new ImageIcon("image/lammoi.png"));
		btnLamMoi.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnLamMoi.setBackground(new Color(0, 148, 224));
		btnLamMoi.setForeground(Color.WHITE);
		btnLamMoi.setFocusPainted(false);
		btnLamMoi.setBounds(22, 690, 150, 42);
		pnlKhachHang.add(btnLamMoi);

		/*
		 * Danh sách phòng trống
		 */
		JPanel pnlPhongTrong = new JPanel();
		pnlPhongTrong.setBounds(230, 10, (int) (d.getWidth() - 450), 310);
		pnlContentPane.add(pnlPhongTrong);
		pnlPhongTrong.setBackground(Color.WHITE);
		pnlPhongTrong.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),
				"DANH SÁCH PHÒNG TRỐNG: "));
		pnlPhongTrong.setLayout(new GridLayout(1, 0, 0, 0));

		String[] header = { "Mã phòng", "Tên phòng", "Giá/1h", "Loại phòng" };
		tableModelPhongTrong = new DefaultTableModel(header, 0);
		tablePhongTrong = new JTable(tableModelPhongTrong) {
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component c = super.prepareRenderer(renderer, row, column);
				Color color1 = new Color(220, 220, 220);
				Color color2 = Color.WHITE;
				if (!c.getBackground().equals(getSelectionBackground())) {
					Color coleur = (row % 2 == 0 ? color1 : color2);
					c.setBackground(coleur);
					coleur = null;
				}
				return c;
			}
		};
		tablePhongTrong.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		tablePhongTrong.setGridColor(getBackground());
		tablePhongTrong.setRowHeight(tablePhongTrong.getRowHeight() + 20);
		tablePhongTrong.setSelectionBackground(new Color(255, 255, 128));
		JTableHeader tableHeader = tablePhongTrong.getTableHeader();
		tableHeader.setBackground(new Color(219, 255, 255));
		tableHeader.setFont(new Font("Times New Roman", Font.BOLD, 15));
		tableHeader.setPreferredSize(new Dimension(WIDTH, 30));
		tableHeader.setResizingAllowed(false);

		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(DefaultTableCellRenderer.RIGHT);
		tablePhongTrong.getColumn("Giá/1h").setCellRenderer(rightRenderer);

		pnlPhongTrong.add(new JScrollPane(tablePhongTrong));

		// Chức năng
		JPanel pnlChucNang = new JPanel();
		pnlChucNang.setBounds(230, 325, (int) (d.getWidth() - 450), 60);
		pnlChucNang.setBackground(Color.WHITE);
		pnlChucNang.setLayout(null);
		pnlContentPane.add(pnlChucNang);

		btnDatPhong = new JButton("ĐẶT PHÒNG", new ImageIcon("image/datphong.png"));
		btnDatPhong.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnDatPhong.setBackground(new Color(79, 173, 84));
		btnDatPhong.setForeground(Color.WHITE);
		btnDatPhong.setBounds(150, 5, 180, 50);
		btnDatPhong.setFocusPainted(false);
		pnlChucNang.add(btnDatPhong);

		btnHuyDatPhong = new JButton("HUỶ ĐẶT PHÒNG", new ImageIcon("image/huydatphong.png"));
		btnHuyDatPhong.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnHuyDatPhong.setBackground(new Color(79, 173, 84));
		btnHuyDatPhong.setForeground(Color.WHITE);
		btnHuyDatPhong.setBounds(370, 5, 210, 50);
		btnHuyDatPhong.setFocusPainted(false);
		pnlChucNang.add(btnHuyDatPhong);

		JLabel lblLoaiPhong = new JLabel("CHỌN LOẠI PHÒNG: ");
		lblLoaiPhong.setBackground(Color.WHITE);
		lblLoaiPhong.setBounds((int) (d.getWidth() - 900), 15, 160, 30);
		lblLoaiPhong.setFont(new Font("Arial", Font.BOLD, 15));
		pnlChucNang.add(lblLoaiPhong);
		cmbLoaiPhong = new JComboBox<String>();
		cmbLoaiPhong.setBounds((int) (d.getWidth() - 720), 10, 150, 40);
		cmbLoaiPhong.addItem("Tất cả");
		cmbLoaiPhong.addItem("VIP");
		cmbLoaiPhong.addItem("Thường");
		cmbLoaiPhong.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		cmbLoaiPhong.setFocusable(false);
		pnlChucNang.add(cmbLoaiPhong);

		/*
		 * Danh sách phòng đã đặt
		 */
		JPanel pnlPhongDaDat = new JPanel();
		pnlPhongDaDat.setBounds(230, 390, (int) (d.getWidth() - 450), (int) (d.getHeight() - 480));
		pnlContentPane.add(pnlPhongDaDat);
		pnlPhongDaDat.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),
				"DANH SÁCH PHÒNG ĐÃ ĐẶT: "));
		pnlPhongDaDat.setBackground(Color.WHITE);
		pnlPhongDaDat.setLayout(new BorderLayout());

		String[] header2 = { "Mã phòng", "Tên phòng", "Giá/1h", "Loại phòng", "Thời gian đặt phòng", "Tên khách hàng" };
		tableModelPhongDaDat = new DefaultTableModel(header2, 0);
		tablePhongDaDat = new JTable(tableModelPhongDaDat) {
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component c = super.prepareRenderer(renderer, row, column);
				Color color1 = new Color(220, 220, 220);
				Color color2 = Color.WHITE;
				if (!c.getBackground().equals(getSelectionBackground())) {
					Color coleur = (row % 2 == 0 ? color1 : color2);
					c.setBackground(coleur);
					coleur = null;
				}
				return c;
			}
		};
		tablePhongDaDat.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		tablePhongDaDat.setGridColor(getBackground());
		tablePhongDaDat.setRowHeight(tablePhongDaDat.getRowHeight() + 20);
		tablePhongDaDat.setSelectionBackground(new Color(255, 255, 128));
		JTableHeader tableHeader2 = tablePhongDaDat.getTableHeader();
		tableHeader2.setBackground(new Color(219, 255, 255));
		tableHeader2.setFont(new Font("Times New Roman", Font.BOLD, 15));
		tableHeader2.setPreferredSize(new Dimension(WIDTH, 30));
		tableHeader2.setResizingAllowed(false);
		tablePhongDaDat.getColumn("Giá/1h").setCellRenderer(rightRenderer);
		pnlPhongDaDat.add(new JScrollPane(tablePhongDaDat), BorderLayout.CENTER);

		// SOUTH
		JPanel pnlSouthPhongDaDat = new JPanel();
		pnlSouthPhongDaDat.setLayout(null);
		pnlPhongDaDat.add(pnlSouthPhongDaDat, BorderLayout.SOUTH);
		pnlSouthPhongDaDat.setBackground(Color.WHITE);
		pnlSouthPhongDaDat.setPreferredSize(new Dimension(WIDTH, 70));

		btnDatDV = new JButton("ĐẶT DỊCH VỤ", new ImageIcon("image/dichvu.png"));
		btnDatDV.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnDatDV.setBackground(new Color(79, 173, 84));
		btnDatDV.setForeground(Color.WHITE);

		btnDatDV.setBounds((int) (d.getWidth() - 800), 10, 180, 50);
		btnDatDV.setFocusPainted(false);
		pnlSouthPhongDaDat.add(btnDatDV);

		JPanel pnlTimKH = new JPanel();
		pnlTimKH.setBackground(Color.WHITE);
		pnlTimKH.setLayout(null);
		pnlTimKH.setBounds(150, 10, 600, 50);
		pnlSouthPhongDaDat.add(pnlTimKH);

		JLabel lblTen = new JLabel("TÊN KHÁCH HÀNG CẦN TÌM: ");
		lblTen.setBackground(Color.WHITE);
		lblTen.setBounds(1, 10, 220, 30);
		lblTen.setFont(new Font("Arial", Font.BOLD, 15));
		pnlTimKH.add(lblTen);
		cmbTimKhachHang = new JComboBox<String>();
		cmbTimKhachHang.setBackground(Color.WHITE);
		cmbTimKhachHang.setEditable(true);
		cmbTimKhachHang.setBounds(230, 7, 193, 35);
		cmbTimKhachHang.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		docDuLieuVaoCmbTimKH();
		AutoCompleteDecorator.decorate(cmbTimKhachHang);
		pnlTimKH.add(cmbTimKhachHang);

		btnTimKH = new JButton("", new ImageIcon("image/timkiem.png"));
		btnTimKH.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnTimKH.setBounds(423, 7, 37, 35);
		btnTimKH.setBackground(new Color(79, 173, 84));
		btnTimKH.setFocusPainted(false);
		pnlTimKH.add(btnTimKH);

		/*
		 * Bắt sự kiện
		 */
		btnDatPhong.addActionListener(this);
		btnHuyDatPhong.addActionListener(this);
		btnLamMoi.addActionListener(this);
		cmbLoaiPhong.addActionListener(this);
		btnDatDV.addActionListener(this);
		btnTimKHCu.addActionListener(this);
		btnTimKH.addActionListener(this);
		tablePhongDaDat.addMouseListener(this);

		docDuLieuDatabaseVaoTablePhongTrong();
		docDuLieuDatabaseVaoTablePhongDaDat();
		docDuLieuVaoCmbDSKH();
		cmbTimKhachHang.setSelectedIndex(0);
		tablePhongDaDat.setRowSelectionAllowed(true);
		tablePhongDaDat.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		txtTenKH.setText("Nguyễn Văn A");
		txtCmnd.setText("079201001547");
		txtLienLac.setText("0909152412");
		txtNgaySinh.setDate(new Date());
		tablePhongDaDat.setDefaultEditor(Object.class, null);
		tablePhongTrong.setDefaultEditor(Object.class, null);
		tablePhongDaDat.getTableHeader().setReorderingAllowed(false);
		tablePhongTrong.getTableHeader().setReorderingAllowed(false);
		return pnlContentPane;

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();

		if (o.equals(btnLamMoi)) {
			txtTenKH.setText("");
			txtCmnd.setText("");
			txtLienLac.setText("");
			cmbGioiTinh.setSelectedIndex(0);
			txtNgaySinh.setDate(null);
			txtTenKH.requestFocus();
		}
		if (o.equals(btnDatPhong)) {
			// Gán rỗng cho các biến tạm
			if (!maHDMoiDat.equals("") && tablePhongTrong.getRowCount() > 0) {
				maHDMoiDat = "";
				maKHDatPhong = "";
				maPhongMoiDat = "";
			}
			// Kiểm tra thông tin khách hàng
			if (validInputKhachHang()) {
				int row = tablePhongTrong.getSelectedRow();
				if (row != -1) {
					// Lấy thông tin khách hàng
					String tenKH = txtTenKH.getText().trim();
					String cmnd = txtCmnd.getText().trim();
					String lienLac = txtLienLac.getText().trim();
					Date ngaySinh = txtNgaySinh.getDate();

					// Kiểm tra có phải khách hàng cũ
					SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyy");
					List<KhachHang> listKH = khachhang_dao.getTatCaKhachHang();
					int flag = 0;
					for (KhachHang khachHang : listKH) {
						if (khachHang.getTenKH().trim().equals(tenKH) && khachHang.getCmnd().trim().equals(cmnd)
								&& khachHang.getSoDT().trim().equals(lienLac)
								&& df.format(khachHang.getNgaySinh()).equals(df.format(ngaySinh))
								&& khachHang.isGioiTinh() == (cmbGioiTinh.getSelectedItem() == "Nam" ? true : false)) {
							flag = 1;
							maKHDatPhong = khachHang.getMaKH().trim();
							break;
						}
					}

					// Thêm nếu là khách hàng mới
					if (flag == 0) {
						String maKHCuoi = datphong_dao.getMaKhachHangCuoi();
						String maKH = "";
						if (maKHCuoi.equals("")) {
							maKH = "KH10000001";
						} else {
							int layMaSoKH = Integer.parseInt(maKHCuoi.substring(2, maKHCuoi.length()));
							maKH = "KH" + (layMaSoKH + 1);
						}
						maKHDatPhong = maKH;
						KhachHang kh = new KhachHang(maKH, tenKH, cmnd,
								cmbGioiTinh.getSelectedItem() == "Nam" ? true : false, lienLac, ngaySinh);
						khachhang_dao.create(kh);
					}
					// Thêm hóa đơn mới
					Date thoiGianDatPhong = new Date();
					String maHD;
					String maHDCuoi = datphong_dao.getMaHoaDonCuoi();
					if (maHDCuoi.equals(""))
						maHD = "HD10000001";
					else {
						int layMaSoHD = Integer.parseInt(maHDCuoi.substring(2, maHDCuoi.length()));
						maHD = "HD" + (layMaSoHD + 1);
					}
					String maPhong = tablePhongTrong.getValueAt(row, 0).toString();
					String tenPhong = tablePhongTrong.getValueAt(row, 1).toString();
					String giaPhongStr = tablePhongTrong.getValueAt(row, 2).toString();
					String loaiPhong = tablePhongTrong.getValueAt(row, 3).toString();
					String[] gia = giaPhongStr.split(",");
					String giaPhong = "";
					for (int i = 0; i < gia.length; i++) {
						giaPhong += gia[i];
					}
					PhongHat ph = new PhongHat(maPhong, tenPhong, Double.parseDouble(giaPhong), loaiPhong);
					HoaDon hd = new HoaDon(maHD, thoiGianDatPhong, thoiGianDatPhong, new KhachHang(maKHDatPhong),
							new NhanVien(), ph, Double.parseDouble(giaPhong));

					if (datphong_dao.create(hd)) {
						maHDMoiDat = hd.getMaHD();
						maPhongMoiDat = hd.getMaPhong().getMaPhong();
						JOptionPane.showMessageDialog(this, "Đặt phòng thành công");
					}
					// Cập nhật lại cơ sở dữ liệu và các giao diện liên quan
					phong_dao.updateTrangThai(hd.getMaPhong().getMaPhong(), true);

					tableModelPhongTrong.removeRow(row);

					xoaHetDLTablePhongDaDat();
					docDuLieuDatabaseVaoTablePhongDaDat();

					cmbDanhSachSdt.removeAllItems();
					docDuLieuVaoCmbDSKH();

					tablePhongDaDat.clearSelection();

					cmbTimKhachHang.removeAllItems();
					docDuLieuVaoCmbTimKH();

					FrameTraPhong.cmbChonPhong.removeAllItems();
					FrameTraPhong.docDuLieuDatabaseVaoComboBox();
					FrameTraPhong.xoaHetDLTableChonPhong();
					FrameTraPhong.docDuLieuDatabaseVaoTable();

					FrameKhachHang.docDuLieuVaoCmbMaKH();
					AutoCompleteDecorator.decorate(FrameKhachHang.cmbMaKH);
					FrameKhachHang.docDuLieuVaoCmbTen();
					AutoCompleteDecorator.decorate(FrameKhachHang.cmbTimTen);
					FrameKhachHang.docDuLieuVaoCmbCMND();
					AutoCompleteDecorator.decorate(FrameKhachHang.cmbTimCMND);
					FrameKhachHang.docDuLieuVaoCmbSDT();
					AutoCompleteDecorator.decorate(FrameKhachHang.cmbTimSDT);
					FrameKhachHang.xoaHetDL();
					FrameKhachHang.docDuLieuDatabaseVaoTable();
					
					tablePhongDaDat.clearSelection();

				} else
					JOptionPane.showMessageDialog(this, "Vui lòng chọn phòng cần đặt");
			}
		}
		if (o.equals(btnHuyDatPhong)) {
			// Kiểm tra phòng có đặt dịch vụ hay không
			if (datphong_dao.getSoLuongHDDVTheoMaHD(maHDMoiDat) == 0) {
				// Nếu không có đặt dịch vụ thì cho phép hủy đặt phòng
				if (datphong_dao.delete(maHDMoiDat) == true) {
					JOptionPane.showMessageDialog(this, "Huỷ đặt phòng thành công");

					// Cập nhật lại cơ sở dữ liệu và các giao diện liên quan
					phong_dao.updateTrangThai(maPhongMoiDat, false);

					xoaHetDLTablePhongTrong();
					docDuLieuDatabaseVaoTablePhongTrong();
					xoaHetDLTablePhongDaDat();
					docDuLieuDatabaseVaoTablePhongDaDat();
					FrameTraPhong.cmbChonPhong.removeAllItems();
					FrameTraPhong.docDuLieuDatabaseVaoComboBox();
					FrameTraPhong.xoaHetDLTableChonPhong();
					FrameTraPhong.docDuLieuDatabaseVaoTable();
					// xoá khách hàng nếu là khách hàng mới
					if (datphong_dao.getSoLuongHDCuaKhachHang(maKHDatPhong) == 0) {
						datphong_dao.deleteKH(maKHDatPhong);
					}

					// Cập nhật lại giao diện và các biến tạm
					cmbDanhSachSdt.removeAllItems();
					docDuLieuVaoCmbDSKH();
					cmbTimKhachHang.removeAllItems();
					docDuLieuVaoCmbTimKH();
					maHDMoiDat = "";
					maPhongMoiDat = "";
					maHDMoiDat = "";
					
					FrameKhachHang.cmbMaKH.removeAllItems();
					FrameKhachHang.docDuLieuVaoCmbMaKH();
					FrameKhachHang.cmbTimTen.removeAllItems();
					FrameKhachHang.docDuLieuVaoCmbTen();
					FrameKhachHang.cmbTimCMND.removeAllItems();
					FrameKhachHang.docDuLieuVaoCmbCMND();
					FrameKhachHang.cmbTimSDT.removeAllItems();
					FrameKhachHang.docDuLieuVaoCmbSDT();
					FrameKhachHang.xoaHetDL();
					FrameKhachHang.docDuLieuDatabaseVaoTable();
				} else {
					JOptionPane.showMessageDialog(this, "Huỷ đặt phòng không thành công");
				}
				// Nếu có đặt dịch vụ thì không cho hủy đặt phòng
			} else {
				JOptionPane.showMessageDialog(this, "Huỷ đặt phòng không thành công");
			}

		}
		if (o.equals(cmbLoaiPhong)) {
			if (cmbLoaiPhong.getSelectedIndex() == 0) {
				tablePhongTrong.clearSelection();
				xoaHetDLTablePhongTrong();
				if (docDuLieuDatabaseVaoTablePhongTrong() == false) {
					JOptionPane.showMessageDialog(this, "Hết phòng");
					cmbLoaiPhong.setSelectedIndex(0);
					xoaHetDLTablePhongTrong();
					docDuLieuDatabaseVaoTablePhongTrong();
				}
			} else if (cmbLoaiPhong.getSelectedIndex() == 1) {
				tablePhongTrong.clearSelection();
				xoaHetDLTablePhongTrong();
				boolean kq = docDuLieuDatabaseVaoTablePhongTrongTheoLoaiVip();
				if (kq == false) {
					JOptionPane.showMessageDialog(this, "Hết phòng VIP");
					cmbLoaiPhong.setSelectedIndex(0);
					xoaHetDLTablePhongTrong();
					docDuLieuDatabaseVaoTablePhongTrong();
				}
			} else if (cmbLoaiPhong.getSelectedIndex() == 2) {
				tablePhongTrong.clearSelection();
				xoaHetDLTablePhongTrong();
				if (docDuLieuDatabaseVaoTablePhongTrongTheoLoaiThuong() == false) {
					JOptionPane.showMessageDialog(this, "Hết phòng thường");
					cmbLoaiPhong.setSelectedIndex(0);
					xoaHetDLTablePhongTrong();
					docDuLieuDatabaseVaoTablePhongTrong();
				}
			}
		}
		if (o.equals(btnTimKHCu)) {
			if (cmbDanhSachSdt.getItemCount() > 0 && cmbDanhSachSdt.getSelectedIndex() != -1) {
				String lienLac = cmbDanhSachSdt.getSelectedItem().toString().trim();
				List<KhachHang> list = khachhang_dao.getTatCaKhachHang();

				List<KhachHang> listKHTrungSDT = new ArrayList<KhachHang>();

				for (KhachHang khachHang : list) {
					if (khachHang.getSoDT().trim().equals(lienLac.trim())) {
						listKHTrungSDT.add(khachHang);
					}
				}
				btnLamMoi.doClick();
				if (listKHTrungSDT.size() > 1) {
					new FrameKhachHangTrungSDT(listKHTrungSDT).setVisible(true);
				}

				if (listKHTrungSDT.size() == 1) {
					KhachHang kh = listKHTrungSDT.get(0);
					JOptionPane.showMessageDialog(this, "Đây là khách hàng cũ");
					txtTenKH.setText(kh.getTenKH().trim());
					txtCmnd.setText(kh.getCmnd().trim());
					txtLienLac.setText(kh.getSoDT().trim());
					cmbGioiTinh.setSelectedItem(kh.isGioiTinh() == true ? "Nam" : "Nữ");
					txtNgaySinh.setDate(kh.getNgaySinh());
				}

			} else {
				JOptionPane.showMessageDialog(this, "Đây là khách hàng mới");
				btnLamMoi.doClick();
			}
		}
		if (o.equals(btnTimKH)) {
			if (cmbTimKhachHang.getItemCount() > 0 && tablePhongDaDat.getRowCount() > 0) {
				if (cmbTimKhachHang.getSelectedIndex() == 0) {
					tablePhongDaDat.clearSelection();
					return;
				}
				int soDongChon = 0, from = 0, to = 0;

				String tenKH = cmbTimKhachHang.getSelectedItem().toString().trim();
				for (int i = 0; i < tablePhongDaDat.getRowCount(); i++) {
					if (tablePhongDaDat.getValueAt(i, 5).toString().trim().contains(tenKH)) {
						if (from == 0 && soDongChon == 0) {
							from = i;
						}
						to = i;
						soDongChon++;
						tablePhongDaDat.setRowSelectionInterval(i, i);
					}
					if (soDongChon > 1) {
						tablePhongDaDat.setRowSelectionInterval(from, to);
					}
				}
			}
		}

		if (o.equals(btnDatDV)) {
			FrameDatDichVu dv = new FrameDatDichVu("");
			dv.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			dv.setVisible(true);
			dv.setLocationRelativeTo(null);
			dispose();
		}
	}

	@SuppressWarnings("deprecation")
	public boolean validInputKhachHang() {
		String tenKhachHang = txtTenKH.getText().trim();
		String cmnd = txtCmnd.getText().trim();
		String lienLac = txtLienLac.getText().trim();
		Date selectedDate = txtNgaySinh.getDate();

		if (tenKhachHang.length() > 0) {
			if (!(tenKhachHang.matches("[^\\@\\!\\$\\^\\&\\*\\(\\)]+"))) {
				JOptionPane.showMessageDialog(this, "Tên khách hàng không chứa ký tự đặc biệt");
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(this, "Tên khách hàng không được để trống");
			return false;
		}
		if (cmnd.length() > 0) {
			if (!(cmnd.matches("(\\d{9}|\\d{12})"))) {
				JOptionPane.showMessageDialog(this, "CMND gồm 9 hoặc 12 số ");
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(this, "CMND không được để trống");
			return false;
		}
		if (lienLac.length() > 0) {
			if (!(lienLac.matches("(\\d{10,11})"))) {
				JOptionPane.showMessageDialog(this, "Liên lạc gồm 10 hoặc 11 số ");
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(this, "Liên lạc không được để trống");
			return false;
		}
		if (selectedDate == null) {
			JOptionPane.showMessageDialog(this, "Ngày sinh không được để trống");
			return false;
		} else {
			Date ngayHienTai = new Date();
			if (ngayHienTai.getYear() - selectedDate.getYear() < 13) {
				JOptionPane.showMessageDialog(this, "Khách hàng chưa đủ 13 tuổi");
				return false;
			}
		}
		return true;
	}

	public boolean docDuLieuDatabaseVaoTablePhongTrongTheoLoaiVip() {
		List<PhongHat> listPhongTrongTheoLoai = datphong_dao.getTatCaPhongHatTrongTheoLoaiVip();
		DecimalFormat df = new DecimalFormat("#,##0.0");
		for (PhongHat ph : listPhongTrongTheoLoai) {
			tableModelPhongTrong.addRow(new Object[] { ph.getMaPhong().trim(), ph.getTenPhong().trim(),
					df.format(ph.getGiaPhong()), ph.getLoaiPhong().trim() });
		}
		return tablePhongTrong.getRowCount() > 0 ? true : false;

	}

	public boolean docDuLieuDatabaseVaoTablePhongTrongTheoLoaiThuong() {
		List<PhongHat> listPhongTrongTheoLoai = datphong_dao.getTatCaPhongHatTrongTheoLoaiThuong();
		DecimalFormat df = new DecimalFormat("#,##0.0");
		for (PhongHat ph : listPhongTrongTheoLoai) {
			tableModelPhongTrong.addRow(new Object[] { ph.getMaPhong().trim(), ph.getTenPhong().trim(),
					df.format(ph.getGiaPhong()), ph.getLoaiPhong().trim() });
		}
		return tablePhongTrong.getRowCount() > 0 ? true : false;
	}

	public static boolean docDuLieuDatabaseVaoTablePhongTrong() {
		List<PhongHat> listPhongTrong = datphong_dao.getTatCaPhongHatTrong();
		DecimalFormat df = new DecimalFormat("#,##0.0");
		for (PhongHat ph : listPhongTrong) {
			tableModelPhongTrong.addRow(new Object[] { ph.getMaPhong().trim(), ph.getTenPhong().trim(),
					df.format(ph.getGiaPhong()), ph.getLoaiPhong().trim() });
		}
		return tablePhongTrong.getRowCount() > 0 ? true : false;
	}

	public static void docDuLieuDatabaseVaoTablePhongDaDat() {
		DecimalFormat df = new DecimalFormat("#,##0.0");
		List<HoaDon> listPhongDaDat = datphong_dao.getTatCaPhongHatDaDat();
		for (HoaDon hd : listPhongDaDat) {
			tableModelPhongDaDat.addRow(new Object[] { hd.getMaPhong().getMaPhong().trim(),
					hd.getMaPhong().getTenPhong().trim(), df.format(hd.getGiaPhong()),
					hd.getMaPhong().getLoaiPhong().trim(), hd.getThoigianDatPhong(), hd.getMaKH().getTenKH().trim() });
		}
	}

	public static void docDuLieuVaoCmbDSKH() {
		List<String> listSDT = khachhang_dao.getTatCaSDTKhachHang();
		for (String s : listSDT) {
			cmbDanhSachSdt.addItem(s.trim());
		}
	}

	public static void xoaHetDLTablePhongTrong() {
		DefaultTableModel dm = (DefaultTableModel) tablePhongTrong.getModel();
		dm.setRowCount(0);
	}

	public static void xoaHetDLTablePhongDaDat() {
		DefaultTableModel dm = (DefaultTableModel) tablePhongDaDat.getModel();
		dm.setRowCount(0);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getClickCount() == 2 && tablePhongDaDat.getSelectedRow() != -1) {
			int row = tablePhongDaDat.getSelectedRow();
			new FrameDatDichVu(tablePhongDaDat.getValueAt(row, 1).toString().trim()).setVisible(true);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	public static void docDuLieuVaoCmbTimKH() {
		List<HoaDon> listKHDangDatPhong = datphong_dao.getTatCaPhongHatDaDat();
		String tenKH = "";
		cmbTimKhachHang.addItem("");
		for (HoaDon hd : listKHDangDatPhong) {
			if (!hd.getMaKH().getTenKH().trim().equals(tenKH)) {
				cmbTimKhachHang.addItem(hd.getMaKH().getTenKH().trim());
				tenKH = hd.getMaKH().getTenKH().trim();
			}
		}
	}

}