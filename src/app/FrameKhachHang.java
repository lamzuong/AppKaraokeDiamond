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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
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
import dao.KhachHang_DAO;
import dao.TraPhong_DAO;
import entity.HoaDon;
import entity.KhachHang;
import entity.NhanVien;

public class FrameKhachHang extends JFrame implements ActionListener, MouseListener {
	public static DefaultTableModel tableModel;
	public static JTable table;
	private JButton btnTimKiem;
	private JButton btnLamMoi;
	public static JComboBox<String> cmbTimTen;
	public static JComboBox<String> cmbTimCMND;
	public static JComboBox<String> cmbTimSDT;
	public static JComboBox<String> cmbMaKH;
	private JButton btnCapNhat;
	private JButton btnXuatExcel;
	private JComboBox<String> cmbTieuChi;
	private JRadioButton radTangDan;
	private JRadioButton radGiamDan;
	private JButton btnSinhNhat;
	private JDateChooser txtNgaySinh;
	private TraPhong_DAO traPhong_dao;
	private static KhachHang_DAO khachhang_dao;

	public JPanel createPanelKhachHang() {
		// khởi tạo kết nối đến CSDL
		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		khachhang_dao = new KhachHang_DAO();
		traPhong_dao = new TraPhong_DAO();
		// ------------------------------
		Toolkit toolkit = this.getToolkit(); /* Lấy độ phân giải màn hình */
		Dimension d = toolkit.getScreenSize();

		JPanel pnlContentPane = new JPanel();
		pnlContentPane.setBackground(new Color(255, 255, 255));
		pnlContentPane.setLayout(null);
		setContentPane(pnlContentPane);

		btnSinhNhat = new JButton("KHÁCH HÀNG SINH NHẬT HÔM NAY", new ImageIcon("image/sinhnhat.png"));
		btnSinhNhat.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnSinhNhat.setBackground(Color.PINK);
		btnSinhNhat.setForeground(Color.WHITE);
		btnSinhNhat.setBounds((int) (d.getWidth() - 950), 15, 360, 45);
		btnSinhNhat.setFocusPainted(false);
		pnlContentPane.add(btnSinhNhat);

		btnCapNhat = new JButton("CẬP NHẬT", new ImageIcon("image/capnhat.png"));
		btnCapNhat.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnCapNhat.setBackground(new Color(79, 173, 84));
		btnCapNhat.setForeground(Color.WHITE);
		btnCapNhat.setBounds((int) (d.getWidth() - 580), 15, 170, 45);
		btnCapNhat.setFocusPainted(false);
		pnlContentPane.add(btnCapNhat);

		btnXuatExcel = new JButton("XUẤT EXCEL", new ImageIcon("image/xuatexcel.png"));
		btnXuatExcel.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnXuatExcel.setBackground(new Color(79, 173, 84));
		btnXuatExcel.setForeground(Color.WHITE);
		btnXuatExcel.setBounds((int) (d.getWidth() - 400), 15, 170, 45);
		btnXuatExcel.setFocusPainted(false);
		pnlContentPane.add(btnXuatExcel);

		// Chức năng
		JPanel pnlTimKiem = new JPanel();
		pnlTimKiem.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), ""));
		pnlTimKiem.setBounds(20, 15, 195, (int) (d.getHeight() - 109));
		pnlTimKiem.setBackground(Color.WHITE);
		pnlTimKiem.setLayout(null);

		JLabel lblMaKH = new JLabel("<html><div style='text-align: center;'>MÃ KHÁCH HÀNG: </div></html>",
				SwingConstants.CENTER);
		lblMaKH.setOpaque(true);
		lblMaKH.setBackground(new Color(0, 148, 224));
		lblMaKH.setBounds(1, 10, 200, 30);
		lblMaKH.setForeground(Color.WHITE);
		lblMaKH.setFont(new Font("Arial", Font.BOLD, 15));
		pnlTimKiem.add(lblMaKH);
		cmbMaKH = new JComboBox<String>();
		cmbMaKH.setBounds(26, 50, 150, 30);
		cmbMaKH.setBackground(Color.WHITE);
		cmbMaKH.setEditable(true);
		cmbMaKH.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		docDuLieuVaoCmbMaKH();
		AutoCompleteDecorator.decorate(cmbMaKH);
		pnlTimKiem.add(cmbMaKH);
		cmbMaKH.setMaximumRowCount(3);

		JLabel lblTen = new JLabel("<html><div style='text-align: center;'>TÊN KHÁCH HÀNG: </div></html>",
				SwingConstants.CENTER);
		lblTen.setOpaque(true);
		lblTen.setBackground(new Color(0, 148, 224));
		lblTen.setForeground(Color.WHITE);
		lblTen.setBounds(1, 100, 193, 30);
		lblTen.setFont(new Font("Arial", Font.BOLD, 15));
		pnlTimKiem.add(lblTen);
		cmbTimTen = new JComboBox<String>();
		cmbTimTen.setBounds(22, 140, 150, 30);
		cmbTimTen.setBackground(Color.WHITE);
		cmbTimTen.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		cmbTimTen.setEditable(true);
		docDuLieuVaoCmbTen();
		AutoCompleteDecorator.decorate(cmbTimTen);
		cmbTimTen.setMaximumRowCount(3);
		pnlTimKiem.add(cmbTimTen);

		JLabel lblSdt = new JLabel("<html><div style='text-align: center;'>SỐ ĐIỆN THOẠI: </div></html>",
				SwingConstants.CENTER);
		lblSdt.setBounds(1, 190, 193, 30);
		lblSdt.setOpaque(true);
		lblSdt.setBackground(new Color(0, 148, 224));
		lblSdt.setForeground(Color.WHITE);
		lblSdt.setFont(new Font("Arial", Font.BOLD, 15));
		pnlTimKiem.add(lblSdt);
		cmbTimSDT = new JComboBox<String>();
		cmbTimSDT.setBounds(22, 230, 150, 30);
		cmbTimSDT.setBackground(Color.WHITE);
		cmbTimSDT.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		cmbTimSDT.setEditable(true);
		docDuLieuVaoCmbSDT();
		AutoCompleteDecorator.decorate(cmbTimSDT);
		cmbTimSDT.setMaximumRowCount(3);
		pnlTimKiem.add(cmbTimSDT);

		JLabel lblNgaySinh = new JLabel("<html><div style='text-align: center;'>NGÀY SINH: </div></html>",
				SwingConstants.CENTER);
		lblNgaySinh.setBounds(1, 280, 193, 30);
		lblNgaySinh.setOpaque(true);
		lblNgaySinh.setBackground(new Color(0, 148, 224));
		lblNgaySinh.setForeground(Color.WHITE);
		lblNgaySinh.setFont(new Font("Arial", Font.BOLD, 15));
		pnlTimKiem.add(lblNgaySinh);
		txtNgaySinh = new JDateChooser();
		txtNgaySinh.setDateFormatString("yyyy-MM-dd");
		txtNgaySinh.setBounds(22, 320, 150, 30);
		txtNgaySinh.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlTimKiem.add(txtNgaySinh);

		JLabel lblCmnd = new JLabel("<html><div style='text-align: center;'>CMND/CCCD: </div></html>",
				SwingConstants.CENTER);
		lblCmnd.setBounds(1, 370, 193, 30);
		lblCmnd.setOpaque(true);
		lblCmnd.setBackground(new Color(0, 148, 224));
		lblCmnd.setForeground(Color.WHITE);
		lblCmnd.setFont(new Font("Arial", Font.BOLD, 15));
		pnlTimKiem.add(lblCmnd);
		cmbTimCMND = new JComboBox<String>();
		cmbTimCMND.setBounds(22, 410, 150, 30);
		cmbTimCMND.setBackground(Color.WHITE);
		cmbTimCMND.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		cmbTimCMND.setEditable(true);
		docDuLieuVaoCmbCMND();
		AutoCompleteDecorator.decorate(cmbTimCMND);
		cmbTimCMND.setMaximumRowCount(3);
		pnlTimKiem.add(cmbTimCMND);

		JLabel lblSapXep = new JLabel("<html><div style='text-align: center;'>SẮP XẾP THEO: </div></html>",
				SwingConstants.CENTER);
		lblSapXep.setBounds(1, 460, 193, 30);
		lblSapXep.setOpaque(true);
		lblSapXep.setBackground(new Color(0, 148, 224));
		lblSapXep.setForeground(Color.WHITE);
		lblSapXep.setFont(new Font("Arial", Font.BOLD, 15));
		pnlTimKiem.add(lblSapXep);
		String[] tieuChi = { "Mã khách hàng", "Tên khách hàng", "Ngày sinh" };
		cmbTieuChi = new JComboBox<String>(tieuChi);
		cmbTieuChi.setBounds(22, 500, 150, 30);
		cmbTieuChi.setFocusable(false);
		cmbTieuChi.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlTimKiem.add(cmbTieuChi);

		radTangDan = new JRadioButton("Tăng dần");
		radTangDan.setBounds(15, 540, 80, 30);
		radTangDan.setBackground(Color.WHITE);
		radTangDan.setFont(new Font("Arial", Font.ITALIC, 13));
		radTangDan.setSelected(true);
		radGiamDan = new JRadioButton("Giảm dần");
		radGiamDan.setBounds(95, 540, 90, 30);
		radGiamDan.setBackground(Color.WHITE);
		radGiamDan.setFont(new Font("Arial", Font.ITALIC, 13));
		ButtonGroup bg = new ButtonGroup();
		bg.add(radTangDan);
		bg.add(radGiamDan);
		pnlTimKiem.add(radTangDan);
		pnlTimKiem.add(radGiamDan);

		btnTimKiem = new JButton("TÌM KIẾM", new ImageIcon("image/timkiem.png"));
		btnTimKiem.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnTimKiem.setBackground(new Color(0, 148, 224));
		btnTimKiem.setBounds(14, 590, 170, 45);
		btnTimKiem.setForeground(Color.WHITE);
		btnTimKiem.setFocusPainted(false);
		pnlTimKiem.add(btnTimKiem);

		btnLamMoi = new JButton("LÀM MỚI", new ImageIcon("image/lammoi.png"));
		btnLamMoi.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnLamMoi.setBackground(new Color(0, 148, 224));
		btnLamMoi.setBounds(14, 650, 170, 45);
		btnLamMoi.setForeground(Color.WHITE);
		btnLamMoi.setFocusPainted(false);
		pnlTimKiem.add(btnLamMoi);

		pnlContentPane.add(pnlTimKiem);

		// Bảng danh sách
		JPanel pnlKhachHang = new JPanel();
		pnlKhachHang.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),
				"DANH SÁCH KHÁCH HÀNG: "));
		pnlKhachHang.setBounds(230, 75, (int) (d.getWidth() - 450), (int) (d.getHeight() - 165));
		pnlKhachHang.setBackground(new Color(255, 255, 255));
		pnlKhachHang.setLayout(new GridLayout(1, 0, 0, 0));
		pnlContentPane.add(pnlKhachHang);

		String[] header = { "Mã khách hàng", "Tên khách hàng", "CMND/CCCD", "Giới tính", "Ngày sinh", "Liên lạc" };
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
		table.setGridColor(getBackground());
		table.setRowHeight(table.getRowHeight() + 20);
		table.setSelectionBackground(new Color(255, 255, 128));
		JTableHeader tableHeader = table.getTableHeader();
		tableHeader.setBackground(new Color(219, 255, 255));
		tableHeader.setFont(new Font("Times New Roman", Font.BOLD, 15));
		tableHeader.setPreferredSize(new Dimension(WIDTH, 30));
		tableHeader.setResizingAllowed(false);
		pnlKhachHang.add(new JScrollPane(table));

		btnTimKiem.addActionListener(this);
		btnLamMoi.addActionListener(this);
		btnSinhNhat.addActionListener(this);
		btnXuatExcel.addActionListener(this);
		btnCapNhat.addActionListener(this);
		table.addMouseListener(this);

		docDuLieuDatabaseVaoTable();
		table.setDefaultEditor(Object.class, null);
		table.getTableHeader().setReorderingAllowed(false);
		return pnlContentPane;

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnCapNhat)) {
			int r = table.getSelectedRow();
			if (r == -1) {
				JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng cần cập nhật!", "Lỗi",
						JOptionPane.ERROR_MESSAGE);
				return;
			} else {
				new FormCapNhatKH().setVisible(true);
			}
		}
		if (o.equals(btnSinhNhat)) {
			new FrameKhachHangSinhNhat().setVisible(true);
		}
		if (o.equals(btnLamMoi)) {
			cmbMaKH.setSelectedIndex(0);
			cmbTimTen.setSelectedIndex(0);
			cmbTimCMND.setSelectedIndex(0);
			cmbTimSDT.setSelectedIndex(0);
			txtNgaySinh.setDate(null);
			cmbTieuChi.setSelectedIndex(0);
			radTangDan.setSelected(true);
			xoaHetDL();
			docDuLieuDatabaseVaoTable();
		}
		if (o.equals(btnTimKiem)) {
			String maKH = cmbMaKH.getSelectedItem().toString().trim();
			String tenKH = cmbTimTen.getSelectedItem().toString().trim();
			String sdt = cmbTimSDT.getSelectedItem().toString().trim();
			Date ngaySinh = txtNgaySinh.getDate();
			String cmnd = cmbTimCMND.getSelectedItem().toString().trim();
			int tieuChi = cmbTieuChi.getSelectedIndex();
			boolean tangDan = radTangDan.isSelected();

			xoaHetDL();
			List<KhachHang> listKH = khachhang_dao.getTatCaKhachHang();

			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			if (!maKH.trim().equals("")) {
				List<KhachHang> listTemp = new ArrayList<KhachHang>();
				for (KhachHang kh : listKH) {
					listTemp.add(kh);
				}
				listKH.clear();
				for (KhachHang kh : listTemp) {
					if (kh.getMaKH().trim().contains(maKH)) {
						listKH.add(kh);
					}
				}
			}
			if (!tenKH.trim().equals("")) {
				List<KhachHang> listTemp = new ArrayList<KhachHang>();
				for (KhachHang kh : listKH) {
					listTemp.add(kh);
				}
				listKH.clear();
				for (KhachHang kh : listTemp) {
					if (kh.getTenKH().trim().contains(tenKH)) {
						listKH.add(kh);
					}
				}
			}
			if (!sdt.trim().equals("")) {
				List<KhachHang> listTemp = new ArrayList<KhachHang>();
				for (KhachHang kh : listKH) {
					listTemp.add(kh);
				}
				listKH.clear();
				for (KhachHang kh : listTemp) {
					if (kh.getSoDT().trim().contains(sdt)) {
						listKH.add(kh);
					}
				}
			}
			if (ngaySinh != null) {
				List<KhachHang> listTemp = new ArrayList<KhachHang>();
				for (KhachHang kh : listKH) {
					listTemp.add(kh);
				}
				listKH.clear();
				for (KhachHang kh : listTemp) {
					if (df.format(kh.getNgaySinh()).equals(df.format(ngaySinh))) {
						listKH.add(kh);
					}
				}
			}
			if (!cmnd.trim().equals("")) {
				List<KhachHang> listTemp = new ArrayList<KhachHang>();
				for (KhachHang kh : listKH) {
					listTemp.add(kh);
				}
				listKH.clear();
				for (KhachHang kh : listTemp) {
					if (kh.getCmnd().trim().contains(cmnd)) {
						listKH.add(kh);
					}
				}
			}
			if (listKH.size() == 0) {
				JOptionPane.showMessageDialog(this, "Không có khách hàng nào phù hợp với tiêu chí", "Lỗi",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (tieuChi == 0)
				sapXepTheoMaKH(listKH, tangDan);
			else if (tieuChi == 1)
				sapXepTheoTenKH(listKH, tangDan);
			else if (tieuChi == 2)
				sapXepTheoNgaySinh(listKH, tangDan);

			for (KhachHang kh : listKH) {
				tableModel.addRow(new Object[] { kh.getMaKH().trim(), kh.getTenKH().trim(), kh.getCmnd().trim(),
						kh.isGioiTinh() == true ? "Nam" : "Nữ", kh.getNgaySinh(), kh.getSoDT().trim() });
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

	public void sapXepTheoMaKH(List<KhachHang> list, boolean tangDan) {
		Collections.sort(list, new Comparator<KhachHang>() {
			@Override
			public int compare(KhachHang o1, KhachHang o2) {
				if (tangDan) {
					if (o1 != null && o2 != null)
						return o1.getMaKH().compareToIgnoreCase(o2.getMaKH());
					return 0;
				} else {
					if (o1 != null && o2 != null)
						return o2.getMaKH().compareToIgnoreCase(o1.getMaKH());
					return 0;
				}
			}
		});
	}

	public void sapXepTheoTenKH(List<KhachHang> list, boolean tangDan) {
		Collections.sort(list, new Comparator<KhachHang>() {
			@Override
			public int compare(KhachHang o1, KhachHang o2) {
				if (tangDan) {
					if (o1 != null && o2 != null)
						return o1.getTenKH().compareToIgnoreCase(o2.getTenKH());
					return 0;
				} else {
					if (o1 != null && o2 != null)
						return o2.getTenKH().compareToIgnoreCase(o1.getTenKH());
					return 0;
				}
			}
		});
	}

	public void sapXepTheoNgaySinh(List<KhachHang> list, boolean tangDan) {
		Collections.sort(list, new Comparator<KhachHang>() {
			@Override
			public int compare(KhachHang o1, KhachHang o2) {
				if (tangDan) {
					if (o1 != null && o2 != null)
						return o1.getNgaySinh().compareTo(o2.getNgaySinh());
					return 0;
				} else {
					if (o1 != null && o2 != null)
						return o2.getNgaySinh().compareTo(o1.getNgaySinh());
					return 0;
				}
			}
		});
	}

	public static void docDuLieuDatabaseVaoTable() {
		List<KhachHang> list = khachhang_dao.getTatCaKhachHang();
		for (KhachHang kh : list) {
			tableModel.addRow(new Object[] { kh.getMaKH(), kh.getTenKH(), kh.getCmnd(),
					kh.isGioiTinh() == true ? "Nam" : "Nữ", kh.getNgaySinh(), kh.getSoDT() });
		}
	}

	public static void xoaHetDL() {
		DefaultTableModel dm = (DefaultTableModel) table.getModel();
		dm.setRowCount(0);
	}

	public static void docDuLieuVaoCmbTen() {
		List<HoaDon> list = new ArrayList<HoaDon>();
		list = khachhang_dao.getTatCaKhachHangLenTableKH();
		cmbTimTen.addItem("");
		for (HoaDon hd : list) {
			cmbTimTen.addItem(hd.getMaKH().getTenKH());
		}
	}

	public static void docDuLieuVaoCmbCMND() {
		List<HoaDon> list = new ArrayList<HoaDon>();
		list = khachhang_dao.getTatCaKhachHangLenTableKH();
		cmbTimCMND.addItem("");
		for (HoaDon hd : list) {
			cmbTimCMND.addItem(hd.getMaKH().getCmnd());
		}
	}

	public static void docDuLieuVaoCmbSDT() {
		List<HoaDon> list = new ArrayList<HoaDon>();
		list = khachhang_dao.getTatCaKhachHangLenTableKH();
		cmbTimSDT.addItem("");
		for (HoaDon hd : list) {
			cmbTimSDT.addItem(hd.getMaKH().getSoDT());
		}
	}

	public static void docDuLieuVaoCmbMaKH() {
		List<HoaDon> list = new ArrayList<HoaDon>();
		list = khachhang_dao.getTatCaKhachHangLenTableKH();
		cmbMaKH.addItem("");
		for (HoaDon hd : list) {
			cmbMaKH.addItem(hd.getMaKH().getMaKH());
		}
	}

	public boolean xuatExcel(String filePath) {
		try {
			FileOutputStream fileOut = new FileOutputStream(filePath);
			// Tạo sheet Danh sách khách hàng
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet worksheet = workbook.createSheet("DANH SÁCH KHÁCH HÀNG");

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

			cell.setCellValue("DANH SÁCH KHÁCH HÀNG");
			cell.setCellStyle(styleTenDanhSach);

			String[] header = { "STT", "Mã khách hàng", "Tên khách hàng", "CMND/CCCD", "Giới tính", "Ngày sinh",
					"Liên lạc" };
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
						if (table.getValueAt(i, j - 1) != null)
							cell.setCellValue(table.getValueAt(i, j - 1).toString().trim());
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
		if (e.getClickCount() == 2 && table.getSelectedRow() != -1) {
			new FormCapNhatKH().setVisible(true);
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
