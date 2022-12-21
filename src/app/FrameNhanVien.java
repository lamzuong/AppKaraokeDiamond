package app;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import com.toedter.calendar.JDateChooser;

import connectDB.ConnectDB;
import dao.NhanVien_DAO;
import dao.TaiKhoan_DAO;
import dao.TraPhong_DAO;
import entity.NhanVien;

public class FrameNhanVien extends JFrame implements ActionListener, MouseListener {
	private static JComboBox<String> cmbChucVu;
	private static JComboBox<String> cmbCa;
	private JButton btnThem;
	private JButton btnXoa;
	public static DefaultTableModel tableModel;
	public static JTable table;
	private JButton btnTimKiem;
	public static JComboBox<String> cmbTenNV;
	public static JComboBox<String> cmbSdt;
	public static JComboBox<String> cmbCmnd;
	public static JComboBox<String> cmbMaNV;
	private JButton btnLamMoi;
	private JButton btnCapNhat;
	private TaiKhoan_DAO taikhoan_dao;
	private JButton btnXuatExcel;
	private TraPhong_DAO traPhong_dao;
	private static JCheckBox chkDangLam;
	private static JCheckBox chkDaNghiViec;
	private static JDateChooser txtNgaySinh;
	private static NhanVien_DAO nhanvien_dao;
	private static String actor;

	public JPanel createPanelNhanVien() {
		// Xác định đăng nhập
		String xacDinhDangNhap = FrameDangNhap.getTaiKhoan();
		if (xacDinhDangNhap.substring(0, 2).equals("CQ"))
			actor = "Chủ quán";
		else
			actor = "Quản lý";
		// khởi tạo kết nối đến CSDL
		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		nhanvien_dao = new NhanVien_DAO();
		taikhoan_dao = new TaiKhoan_DAO();
		traPhong_dao = new TraPhong_DAO();
		// ------------------------------
		Toolkit toolkit = this.getToolkit(); /* Lấy độ phân giải màn hình */
		Dimension d = toolkit.getScreenSize();

		JPanel pnlContentPane = new JPanel();
		pnlContentPane.setBackground(new Color(255, 255, 255));
		pnlContentPane.setLayout(null);
		setContentPane(pnlContentPane);

		/*
		 * Chức năng
		 */
		btnThem = new JButton("THÊM MỚI", new ImageIcon("image/them.png"));
		btnThem.setBounds((int) (d.getWidth() - 910), 15, 165, 45);
		btnThem.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnThem.setBackground(new Color(79, 173, 84));
		btnThem.setForeground(Color.WHITE);
		btnThem.setFocusPainted(false);
		pnlContentPane.add(btnThem);

		btnXoa = new JButton("NGHỈ VIỆC", new ImageIcon("image/xoa.png"));
		btnXoa.setBounds((int) (d.getWidth() - 740), 15, 165, 45);
		btnXoa.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnXoa.setBackground(new Color(79, 173, 84));
		btnXoa.setForeground(Color.WHITE);
		btnXoa.setFocusPainted(false);
		pnlContentPane.add(btnXoa);

		btnCapNhat = new JButton("CẬP NHẬT", new ImageIcon("image/capnhat.png"));
		btnCapNhat.setBounds((int) (d.getWidth() - 570), 15, 165, 45);
		btnCapNhat.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnCapNhat.setBackground(new Color(79, 173, 84));
		btnCapNhat.setForeground(Color.WHITE);
		btnCapNhat.setFocusPainted(false);
		pnlContentPane.add(btnCapNhat);

		btnXuatExcel = new JButton("XUẤT EXCEL", new ImageIcon("image/xuatexcel.png"));
		btnXuatExcel.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnXuatExcel.setBackground(new Color(79, 173, 84));
		btnXuatExcel.setForeground(Color.WHITE);
		btnXuatExcel.setBounds((int) (d.getWidth() - 400), 15, 165, 45);
		btnXuatExcel.setFocusPainted(false);
		pnlContentPane.add(btnXuatExcel);

		// Tìm kiếm
		JPanel pnlTimKiem = new JPanel();
		pnlTimKiem.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), ""));
		pnlTimKiem.setBounds(20, 15, 202, (int) (d.getHeight() - 109));
		pnlTimKiem.setBackground(new Color(255, 255, 255));
		pnlTimKiem.setLayout(null);
		pnlContentPane.add(pnlTimKiem);

		JLabel lblMaNV = new JLabel("<html><div style='text-align: center;'>MÃ NHÂN VIÊN: </div></html>",
				SwingConstants.CENTER);
		lblMaNV.setOpaque(true);
		lblMaNV.setBackground(new Color(0, 148, 224));
		lblMaNV.setBounds(1, 10, 200, 30);
		lblMaNV.setForeground(Color.WHITE);
		lblMaNV.setFont(new Font("Arial", Font.BOLD, 15));
		pnlTimKiem.add(lblMaNV);
		cmbMaNV = new JComboBox<String>();
		cmbMaNV.setBounds(26, 50, 150, 30);
		cmbMaNV.setBackground(Color.WHITE);
		cmbMaNV.setEditable(true);
		cmbMaNV.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		docDuLieuVaoCmbMaNV();
		AutoCompleteDecorator.decorate(cmbMaNV);
		pnlTimKiem.add(cmbMaNV);
		cmbMaNV.setMaximumRowCount(3);

		JLabel lblTenNV = new JLabel("<html><div style='text-align: center;'>TÊN NHÂN VIÊN: </div></html>",
				SwingConstants.CENTER);
		lblTenNV.setOpaque(true);
		lblTenNV.setBackground(new Color(0, 148, 224));
		lblTenNV.setBounds(1, 100, 200, 30);
		lblTenNV.setForeground(Color.WHITE);
		lblTenNV.setFont(new Font("Arial", Font.BOLD, 15));
		pnlTimKiem.add(lblTenNV);
		cmbTenNV = new JComboBox<String>();
		cmbTenNV.setBounds(26, 140, 150, 30);
		cmbTenNV.setBackground(Color.WHITE);
		cmbTenNV.setEditable(true);
		cmbTenNV.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		docDuLieuVaoCmbTenNV();
		AutoCompleteDecorator.decorate(cmbTenNV);
		pnlTimKiem.add(cmbTenNV);
		cmbTenNV.setMaximumRowCount(3);

		JLabel lblSdt = new JLabel("<html><div style='text-align: center;'>SỐ ĐIỆN THOẠI: </div></html>",
				SwingConstants.CENTER);
		lblSdt.setOpaque(true);
		lblSdt.setBackground(new Color(0, 148, 224));
		lblSdt.setBounds(1, 190, 200, 30);
		lblSdt.setForeground(Color.WHITE);
		lblSdt.setFont(new Font("Arial", Font.BOLD, 15));
		pnlTimKiem.add(lblSdt);
		cmbSdt = new JComboBox<String>();
		cmbSdt.setBounds(26, 230, 150, 30);
		cmbSdt.setBackground(Color.WHITE);
		cmbSdt.setEditable(true);
		cmbSdt.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		docDuLieuVaoCmbSdt();
		AutoCompleteDecorator.decorate(cmbSdt);
		pnlTimKiem.add(cmbSdt);
		cmbSdt.setMaximumRowCount(3);

		JLabel lblNgaySinh = new JLabel("<html><div style='text-align: center;'>NGÀY SINH: </div></html>",
				SwingConstants.CENTER);
		lblNgaySinh.setBounds(1, 280, 200, 30);
		lblNgaySinh.setOpaque(true);
		lblNgaySinh.setBackground(new Color(0, 148, 224));
		lblNgaySinh.setForeground(Color.WHITE);
		lblNgaySinh.setFont(new Font("Arial", Font.BOLD, 15));
		pnlTimKiem.add(lblNgaySinh);
		txtNgaySinh = new JDateChooser();
		txtNgaySinh.setDateFormatString("yyyy-MM-dd");
		txtNgaySinh.setBounds(26, 320, 150, 30);
		txtNgaySinh.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlTimKiem.add(txtNgaySinh);

		JLabel lblCmnd = new JLabel("<html><div style='text-align: center;'>CMND/CCCD: </div></html>",
				SwingConstants.CENTER);
		lblCmnd.setOpaque(true);
		lblCmnd.setBackground(new Color(0, 148, 224));
		lblCmnd.setBounds(1, 370, 200, 30);
		lblCmnd.setForeground(Color.WHITE);
		lblCmnd.setFont(new Font("Arial", Font.BOLD, 15));
		pnlTimKiem.add(lblCmnd);
		cmbCmnd = new JComboBox<String>();
		cmbCmnd.setBounds(26, 410, 150, 30);
		cmbCmnd.setBackground(Color.WHITE);
		cmbCmnd.setEditable(true);
		cmbCmnd.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		docDuLieuVaoCmbCmnd();
		AutoCompleteDecorator.decorate(cmbCmnd);
		pnlTimKiem.add(cmbCmnd);
		cmbCmnd.setMaximumRowCount(3);

		JLabel lblNoiLamViec = new JLabel("<html><div style='text-align: center;'>NƠI LÀM VIỆC: </div></html>",
				SwingConstants.CENTER);
		lblNoiLamViec.setOpaque(true);
		lblNoiLamViec.setBackground(new Color(0, 148, 224));
		lblNoiLamViec.setForeground(Color.WHITE);
		lblNoiLamViec.setBounds(1, 460, 200, 30);
		lblNoiLamViec.setFont(new Font("Arial", Font.BOLD, 15));
		pnlTimKiem.add(lblNoiLamViec);

		JLabel lblCa = new JLabel("Ca: ");
		lblCa.setBounds(10, 500, 150, 30);
		lblCa.setFont(new Font("Arial", Font.PLAIN, 15));
		pnlTimKiem.add(lblCa);
		String[] ca = { "Tất cả", "8:00AM-4:00PM", "4:00PM-12:00AM", "12:00AM-8:00AM" };
		cmbCa = new JComboBox<String>(ca);
		cmbCa.setBounds(55, 500, 135, 30);
		cmbCa.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		cmbCa.setFocusable(false);
		pnlTimKiem.add(cmbCa);

		JLabel lblChucvu = new JLabel("Chức vụ:  ");
		lblChucvu.setBounds(10, 540, 150, 30);
		lblChucvu.setFont(new Font("Arial", Font.PLAIN, 15));
		;
		pnlTimKiem.add(lblChucvu);
		String[] chucVu = { "Tất cả", "Phục vụ", "Lễ tân" };
		cmbChucVu = new JComboBox<String>(chucVu);
		if (actor.equals("Chủ quán"))
			cmbChucVu.addItem("Quản lý");
		cmbChucVu.setBounds(90, 540, 100, 30);
		cmbChucVu.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		cmbChucVu.setFocusable(false);
		pnlTimKiem.add(cmbChucVu);

		chkDangLam = new JCheckBox("Đang làm");
		chkDangLam.setBounds(10, 580, 85, 30);
		chkDangLam.setFont(new Font("Arial", Font.ITALIC, 13));
		chkDangLam.setBackground(Color.WHITE);
		chkDangLam.setSelected(true);
		pnlTimKiem.add(chkDangLam);
		chkDaNghiViec = new JCheckBox("Đã nghỉ việc");
		chkDaNghiViec.setBounds(97, 580, 97, 30);
		chkDaNghiViec.setFont(new Font("Arial", Font.ITALIC, 13));
		chkDaNghiViec.setSelected(true);
		chkDaNghiViec.setBackground(Color.WHITE);
		pnlTimKiem.add(chkDaNghiViec);

		btnTimKiem = new JButton("TÌM KIẾM", new ImageIcon("image/timkiem.png"));
		btnTimKiem.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnTimKiem.setBackground(new Color(0, 148, 224));
		btnTimKiem.setBounds(17, 630, 170, 45);
		btnTimKiem.setForeground(Color.WHITE);
		btnTimKiem.setFocusPainted(false);
		pnlTimKiem.add(btnTimKiem);

		btnLamMoi = new JButton("LÀM MỚI", new ImageIcon("image/lammoi.png"));
		btnLamMoi.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnLamMoi.setBackground(new Color(0, 148, 224));
		btnLamMoi.setBounds(17, 690, 170, 45);
		btnLamMoi.setForeground(Color.WHITE);
		btnLamMoi.setFocusPainted(false);
		pnlTimKiem.add(btnLamMoi);

		/*
		 * Danh sách nhân viên
		 */
		JPanel pnlDanhSach = new JPanel();
		pnlDanhSach.setBorder(
				BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "DANH SÁCH NHÂN VIÊN: "));
		pnlDanhSach.setBounds(250, 75, (int) (d.getWidth() - 470), (int) (d.getHeight() - 165));
		pnlDanhSach.setBackground(new Color(255, 255, 255));
		pnlDanhSach.setLayout(new GridLayout(1, 0, 0, 0));
		pnlContentPane.add(pnlDanhSach);

		String[] header = { "Mã nhân viên", "Tên nhân viên", "Ngày sinh", "Giới tính", "CMND/CCCD", "SĐT", "Chức vụ",
				"Ca làm việc", "Lương", "Tài khoản", "Trạng thái" };
		tableModel = new DefaultTableModel(header, 0);
		table = new JTable(tableModel) {
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
		table.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		table.setRowHeight(table.getRowHeight() + 20);
		table.setSelectionBackground(new Color(255, 255, 128));
		table.setGridColor(getBackground());
		JTableHeader tableHeader = table.getTableHeader();
		tableHeader.setBackground(new Color(219, 255, 255));
		tableHeader.setFont(new Font("Times New Roman", Font.BOLD, 15));
		tableHeader.setPreferredSize(new Dimension(WIDTH, 30));
		tableHeader.setResizingAllowed(false);
		pnlDanhSach.add(new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));

		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(DefaultTableCellRenderer.RIGHT);
		table.getColumn("Lương").setCellRenderer(rightRenderer);

		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(120);
		table.getColumnModel().getColumn(1).setPreferredWidth(165);
		table.getColumnModel().getColumn(2).setPreferredWidth(87);
		table.getColumnModel().getColumn(4).setPreferredWidth(120);
		table.getColumnModel().getColumn(5).setPreferredWidth(98);
		table.getColumnModel().getColumn(6).setPreferredWidth(80);
		table.getColumnModel().getColumn(7).setPreferredWidth(120);
		table.getColumnModel().getColumn(8).setPreferredWidth(110);
		table.getColumnModel().getColumn(9).setPreferredWidth(90);
		table.getColumnModel().getColumn(10).setPreferredWidth(110);

		btnThem.addActionListener(this);
		btnXoa.addActionListener(this);
		btnCapNhat.addActionListener(this);
		btnLamMoi.addActionListener(this);
		btnTimKiem.addActionListener(this);
		btnXuatExcel.addActionListener(this);
		table.addMouseListener(this);
		table.setDefaultEditor(Object.class, null);
		table.getTableHeader().setReorderingAllowed(false);
		docDuLieuDatabaseVaoTable();

		return pnlContentPane;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnThem)) {
			new FormThemNV().setVisible(true);
		}
		if (o.equals(btnCapNhat)) {
			int r = table.getSelectedRow();
			if (r == -1) {
				JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên cần cập nhật!", "Lỗi",
						JOptionPane.ERROR_MESSAGE);
				return;
			} else {
				if(tableModel.getValueAt(r, 10).toString().trim().equals("Đã nghỉ việc")) {
					JOptionPane.showMessageDialog(this, "Nhân viên đã nghỉ việc nên không được cập nhật!", "Lỗi",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				new FormCapNhatNV().setVisible(true);
			}
		}
		if (o.equals(btnXoa)) {
			int r = table.getSelectedRow();
			if (r == -1) {
				JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên cho nghỉ việc!", "Lỗi",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(tableModel.getValueAt(r, 10).toString().trim().equals("Đã nghỉ việc")) {
				JOptionPane.showMessageDialog(this, "Nhân viên đã nghỉ việc rồi!", "Lỗi",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			int result = JOptionPane.showConfirmDialog(this, "Bạn có chắc sẽ cho nhân viên này nghỉ việc không?",
					"Cảnh báo", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			if (result == 0) {
				String maNV = tableModel.getValueAt(r, 0).toString();
				List<NhanVien> listNV = new ArrayList<NhanVien>();
				if (actor.equals("Chủ quán"))
					listNV = nhanvien_dao.getTatCaNhanVien();
				else
					listNV = nhanvien_dao.getTatCaNhanVienKhongGomQL();
				String tenTaiKhoan = "";
				for (NhanVien nv : listNV) {
					if (nv.getMaNV().equalsIgnoreCase(maNV)) {
						tenTaiKhoan = nv.getTaiKhoan().getTenTaiKhoan();
						break;
					}
				}
				nhanvien_dao.delete(maNV);
				taikhoan_dao.delete(tenTaiKhoan);
				xoaHetDL();
				docDuLieuDatabaseVaoTable();
			}
		}
		if (o.equals(btnLamMoi)) {
			lamMoiDL();
		}
		if (o.equals(btnTimKiem)) {
			String maNV = cmbMaNV.getSelectedItem().toString().trim();
			String tenNV = cmbTenNV.getSelectedItem().toString().trim();
			String sdt = cmbSdt.getSelectedItem().toString().trim();
			Date ngaySinh = txtNgaySinh.getDate();
			String cmnd = cmbCmnd.getSelectedItem().toString().trim();
			String chucVu = cmbChucVu.getSelectedItem().toString().trim();
			String ca = cmbCa.getSelectedItem().toString().trim();
			xoaHetDL();
			List<NhanVien> listNV = new ArrayList<NhanVien>();
			if (actor.equals("Chủ quán"))
				listNV = nhanvien_dao.getTatCaNhanVien();
			else
				listNV = nhanvien_dao.getTatCaNhanVienKhongGomQL();
			if (!maNV.trim().equals("")) {
				List<NhanVien> listTemp = new ArrayList<NhanVien>();
				for (NhanVien nv : listNV) {
					listTemp.add(nv);
				}
				listNV.clear();
				for (NhanVien nv : listTemp) {
					if (nv.getMaNV().trim().contains(maNV)) {
						listNV.add(nv);
					}
				}
			}
			if (!tenNV.trim().equals("")) {
				List<NhanVien> listTemp = new ArrayList<NhanVien>();
				for (NhanVien nv : listNV) {
					listTemp.add(nv);
				}
				listNV.clear();
				for (NhanVien nv : listTemp) {
					if (nv.getTenNV().trim().contains(tenNV)) {
						listNV.add(nv);
					}
				}
			}
			if (!sdt.trim().equals("")) {
				List<NhanVien> listTemp = new ArrayList<NhanVien>();
				for (NhanVien nv : listNV) {
					listTemp.add(nv);
				}
				listNV.clear();
				for (NhanVien nv : listTemp) {
					if (nv.getSoDT().trim().contains(sdt)) {
						listNV.add(nv);
					}
				}
			}
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			if (ngaySinh != null) {
				List<NhanVien> listTemp = new ArrayList<NhanVien>();
				for (NhanVien nv : listNV) {
					listTemp.add(nv);
				}
				listNV.clear();
				for (NhanVien nv : listTemp) {
					if (df.format(nv.getNgaySinh()).equals(df.format(ngaySinh))) {
						listNV.add(nv);
					}
				}
			}
			if (!cmnd.trim().equals("")) {
				List<NhanVien> listTemp = new ArrayList<NhanVien>();
				for (NhanVien nv : listNV) {
					listTemp.add(nv);
				}
				listNV.clear();
				for (NhanVien nv : listTemp) {
					if (nv.getCmnd().trim().contains(cmnd)) {
						listNV.add(nv);
					}
				}
			}
			if (!chucVu.trim().equals("Tất cả")) {
				List<NhanVien> listTemp = new ArrayList<NhanVien>();
				for (NhanVien nv : listNV) {
					listTemp.add(nv);
				}
				listNV.clear();
				for (NhanVien nv : listTemp) {
					if (nv.getChucVu().trim().equalsIgnoreCase(chucVu)) {
						listNV.add(nv);
					}
				}
			}
			if (!ca.trim().equals("Tất cả")) {
				List<NhanVien> listTemp = new ArrayList<NhanVien>();
				for (NhanVien nv : listNV) {
					listTemp.add(nv);
				}
				listNV.clear();
				for (NhanVien nv : listTemp) {
					if (nv.getCaLamViec().trim().equalsIgnoreCase(ca)) {
						listNV.add(nv);
					}
				}
			}
			if (chkDangLam.isSelected() && !chkDaNghiViec.isSelected()) {
				List<NhanVien> listTemp = new ArrayList<NhanVien>();
				for (NhanVien nv : listNV) {
					listTemp.add(nv);
				}
				listNV.clear();
				for (NhanVien nv : listTemp) {
					if (!nv.isDaXoa()) {
						listNV.add(nv);
					}
				}
			}
			if (chkDaNghiViec.isSelected() && !chkDangLam.isSelected()) {
				List<NhanVien> listTemp = new ArrayList<NhanVien>();
				for (NhanVien nv : listNV) {
					listTemp.add(nv);
				}
				listNV.clear();
				for (NhanVien nv : listTemp) {
					if (nv.isDaXoa()) {
						listNV.add(nv);
					}
				}
			}
			if (listNV.size() == 0) {
				JOptionPane.showMessageDialog(this, "Không có nhân viên nào phù hợp với tiêu chí", "Lỗi",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			DecimalFormat dfMoney = new DecimalFormat("#,##0.0");
			for (NhanVien nv : listNV) {
				tableModel.addRow(new Object[] { nv.getMaNV().trim(), nv.getTenNV().trim(), nv.getNgaySinh(),
						nv.isGioiTinh() == true ? "Nam" : "Nữ", nv.getCmnd().trim(), nv.getSoDT().trim(),
						nv.getChucVu().trim(), nv.getCaLamViec().trim(), dfMoney.format(nv.getLuong()),
						nv.getTaiKhoan().getTenTaiKhoan(), nv.isDaXoa() ? "Đã nghỉ việc" : "Đang làm" });
			}
		}
		if (o.equals(btnXuatExcel)) {
			JFileChooser fileDialog = new JFileChooser() {
				@Override
				protected JDialog createDialog(Component parent) throws HeadlessException {
					JDialog dialog = super.createDialog(parent);
					ImageIcon icon = new ImageIcon("image/logodark.png");
					dialog.setIconImage(icon.getImage());
					return dialog;
				}
			};
			FileFilter filter = new FileNameExtensionFilter("Excel(.xls)", ".xls");
			fileDialog.setAcceptAllFileFilterUsed(false);
			fileDialog.addChoosableFileFilter(filter);
			int returnVal = fileDialog.showSaveDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				java.io.File file = fileDialog.getSelectedFile();
				String filePath = file.getAbsolutePath();
				if(!(filePath.endsWith(".xls") || filePath.endsWith(".xlsx"))) {
					filePath += ".xls";
				}
				if (xuatExcel(filePath))
					JOptionPane.showMessageDialog(this, "Ghi file thành công!!", "Thành công",
							JOptionPane.INFORMATION_MESSAGE);
				else
					JOptionPane.showMessageDialog(this, "Ghi file thất bại!!", "Lỗi", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public static void lamMoiDL() {
		cmbCa.setSelectedIndex(0);
		cmbChucVu.setSelectedIndex(0);
		cmbTenNV.setSelectedIndex(0);
		cmbMaNV.setSelectedIndex(0);
		cmbCmnd.setSelectedIndex(0);
		cmbSdt.setSelectedIndex(0);
		txtNgaySinh.setDate(null);
		chkDangLam.setSelected(true);
		chkDaNghiViec.setSelected(true);
		xoaHetDL();
		docDuLieuDatabaseVaoTable();
	}

	public static void xoaHetDL() {
		DefaultTableModel dm = (DefaultTableModel) table.getModel();
		dm.setRowCount(0);
	}

	public static void docDuLieuDatabaseVaoTable() {
		List<NhanVien> listNV = new ArrayList<NhanVien>();
		if (actor.equals("Chủ quán"))
			listNV = nhanvien_dao.getTatCaNhanVien();
		else
			listNV = nhanvien_dao.getTatCaNhanVienKhongGomQL();
		DecimalFormat df = new DecimalFormat("#,##0.0");

		for (NhanVien nv : listNV) {
			tableModel.addRow(new Object[] { nv.getMaNV().trim(), nv.getTenNV().trim(), nv.getNgaySinh(),
					nv.isGioiTinh() == true ? "Nam" : "Nữ", nv.getCmnd().trim(), nv.getSoDT().trim(),
					nv.getChucVu().trim(), nv.getCaLamViec().trim(), df.format(nv.getLuong()),
					nv.getTaiKhoan().getTenTaiKhoan(), nv.isDaXoa() ? "Đã nghỉ việc" : "Đang làm" });
		}
	}

	public static void docDuLieuVaoCmbTenNV() {
		List<NhanVien> listNV = new ArrayList<NhanVien>();
		if (actor.equals("Chủ quán"))
			listNV = nhanvien_dao.getTatCaNhanVien();
		else
			listNV = nhanvien_dao.getTatCaNhanVienKhongGomQL();
		cmbTenNV.addItem("");
		for (NhanVien nv : listNV) {
			cmbTenNV.addItem(nv.getTenNV().trim());
		}
	}

	public static void docDuLieuVaoCmbMaNV() {
		List<NhanVien> listNV = new ArrayList<NhanVien>();
		if (actor.equals("Chủ quán"))
			listNV = nhanvien_dao.getTatCaNhanVien();
		else
			listNV = nhanvien_dao.getTatCaNhanVienKhongGomQL();
		cmbMaNV.addItem("");
		for (NhanVien nv : listNV) {
			cmbMaNV.addItem(nv.getMaNV().trim());
		}
	}

	public static void docDuLieuVaoCmbCmnd() {
		List<NhanVien> listNV = new ArrayList<NhanVien>();
		if (actor.equals("Chủ quán"))
			listNV = nhanvien_dao.getTatCaNhanVien();
		else
			listNV = nhanvien_dao.getTatCaNhanVienKhongGomQL();
		cmbCmnd.addItem("");
		for (NhanVien nv : listNV) {
			cmbCmnd.addItem(nv.getCmnd().trim());
		}
	}

	public static void docDuLieuVaoCmbSdt() {
		List<NhanVien> listNV = new ArrayList<NhanVien>();
		if (actor.equals("Chủ quán"))
			listNV = nhanvien_dao.getTatCaNhanVien();
		else
			listNV = nhanvien_dao.getTatCaNhanVienKhongGomQL();
		cmbSdt.addItem("");
		for (NhanVien nv : listNV) {
			cmbSdt.addItem(nv.getSoDT().trim());
		}
	}

	public boolean xuatExcel(String filePath) {
		try {
			FileOutputStream fileOut = new FileOutputStream(filePath);
			// Tạo sheet Danh sách khách hàng
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet worksheet = workbook.createSheet("DANH SÁCH NHÂN VIÊN");

			HSSFRow row;
			HSSFCell cell;

			// Dòng 1 tên
			cell = worksheet.createRow(1).createCell(1);

			HSSFFont newFont = cell.getSheet().getWorkbook().createFont();
			newFont.setBold(true);
			newFont.setFontHeightInPoints((short) 13);
			CellStyle styleTenDanhSach = worksheet.getWorkbook().createCellStyle();
			styleTenDanhSach.setAlignment(HorizontalAlignment.CENTER);
			styleTenDanhSach.setFont(newFont);

			cell.setCellValue("DANH SÁCH NHÂN VIÊN");
			cell.setCellStyle(styleTenDanhSach);

			String[] header = { "STT", "Mã nhân viên", "Tên nhân viên", "Ngày sinh", "Giới tính", "CMND/CCCD", "SĐT",
					"Chức vụ", "Ca làm việc", "Lương", "Tài khoản", "Trạng thái" };
			worksheet.addMergedRegion(new CellRangeAddress(1, 1, 1, header.length));

			// Dòng 2 người lập
			row = worksheet.createRow(2);

			cell = row.createCell(1);
			cell.setCellValue("Người lập:");
			cell = row.createCell(2);

			NhanVien nv = traPhong_dao.getNhanVienSuDung(FrameDangNhap.getTaiKhoan());
			if (nv == null)
				cell.setCellValue("Chủ quán");
			else
				cell.setCellValue(nv.getTenNV());
			worksheet.addMergedRegion(new CellRangeAddress(2, 2, 2, 3));

			// Dòng 3 ngày lập
			row = worksheet.createRow(3);
			cell = row.createCell(1);
			cell.setCellValue("Ngày lập:");
			cell = row.createCell(2);
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			cell.setCellValue(df.format(new Date()));
			worksheet.addMergedRegion(new CellRangeAddress(3, 3, 2, 3));

			// Dòng 4 tên các cột
			row = worksheet.createRow(4);

			HSSFFont fontHeader = cell.getSheet().getWorkbook().createFont();
			fontHeader.setBold(true);

			CellStyle styleHeader = worksheet.getWorkbook().createCellStyle();
			styleHeader.setFont(fontHeader);
			styleHeader.setBorderBottom(BorderStyle.THIN);
			styleHeader.setBorderTop(BorderStyle.THIN);
			styleHeader.setBorderLeft(BorderStyle.THIN);
			styleHeader.setBorderRight(BorderStyle.THIN);
			styleHeader.setAlignment(HorizontalAlignment.CENTER);

			styleHeader.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
			styleHeader.setFillPattern(FillPatternType.SOLID_FOREGROUND);

			for (int i = 0; i < header.length; i++) {
				cell = row.createCell(i + 1);
				cell.setCellValue(header[i]);
				cell.setCellStyle(styleHeader);
			}

			if (table.getRowCount() == 0) {
				return false;
			}

			HSSFFont fontRow = cell.getSheet().getWorkbook().createFont();
			fontRow.setBold(false);

			CellStyle styleRow = worksheet.getWorkbook().createCellStyle();
			styleRow.setFont(fontRow);
			styleRow.setBorderBottom(BorderStyle.THIN);
			styleRow.setBorderTop(BorderStyle.THIN);
			styleRow.setBorderLeft(BorderStyle.THIN);
			styleRow.setBorderRight(BorderStyle.THIN);

			// Ghi dữ liệu vào bảng
			int STT = 0;
			for (int i = 0; i < table.getRowCount(); i++) {
				row = worksheet.createRow(5 + i);
				for (int j = 0; j < header.length; j++) {
					cell = row.createCell(j + 1);
					if (STT == i) {
						cell.setCellValue(STT + 1);
						STT++;
					} else {
						if (table.getValueAt(i, j - 1) != null) {
							if (j == header.length - 3) {
								String luong[] = tableModel.getValueAt(i, j - 1).toString().split(",");
								String tienLuong = "";
								for (int t = 0; t < luong.length; t++)
									tienLuong += luong[t];
								cell.setCellValue(Double.parseDouble(tienLuong));
							} else
								cell.setCellValue(table.getValueAt(i, j - 1).toString().trim());
						}
					}
					cell.setCellStyle(styleRow);
				}
			}

			for (int i = 1; i < header.length + 1; i++) {
				worksheet.autoSizeColumn(i);
			}

			workbook.write(fileOut);
			workbook.close();
			fileOut.flush();
			fileOut.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int r = table.getSelectedRow();
		if (e.getClickCount() == 2 && r != -1) {
			if(tableModel.getValueAt(r, 10).toString().trim().equals("Đã nghỉ việc")) {
				JOptionPane.showMessageDialog(this, "Nhân viên đã nghỉ việc nên không được cập nhật!", "Lỗi",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			new FormCapNhatNV().setVisible(true);
		}
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
