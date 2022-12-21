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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.text.DecimalFormat;
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
import javax.swing.JTextField;
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

import connectDB.ConnectDB;
import dao.DichVu_DAO;
import dao.TraPhong_DAO;
import entity.DichVu;
import entity.NhanVien;

public class FrameDichVu extends JFrame implements ActionListener, MouseListener {
	private JButton btnThem;
	private JButton btnXoa;
	public static DefaultTableModel tableModel;
	public static JTable table;
	private JButton btnLamMoi;
	private JButton btnTimKiem;
	private JButton btnCapNhat;
	public static JComboBox<String> cmbMaDV;
	public static JComboBox<String> cmbTenDV;
	private JComboBox<String> cmbTinhTrang;
	private JTextField txtGiaMin;
	private JTextField txtGiaMax;
	private JButton btnXuatExcel;
	private JButton btnDocFile;
	private JComboBox<String> cmbTieuChi;
	private JRadioButton radTangDan;
	private JRadioButton radGiamDan;
	private TraPhong_DAO traPhong_dao;
	private static DichVu_DAO dichvu_dao;

	public JPanel createPanelDichVu() {
		// khởi tạo kết nối đến CSDL
		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dichvu_dao = new DichVu_DAO();
		traPhong_dao = new TraPhong_DAO();
		// --------------------------
		Toolkit toolkit = this.getToolkit(); /* Lấy độ phân giải màn hình */
		Dimension d = toolkit.getScreenSize();

		JPanel pnlContentPane = new JPanel();
		pnlContentPane.setBackground(Color.WHITE);
		pnlContentPane.setLayout(null);
		setContentPane(pnlContentPane);

		/*
		 * Chức năng
		 */
		btnThem = new JButton("THÊM MỚI", new ImageIcon("image/them.png"));
		btnThem.setBounds((int) (d.getWidth() - 1080), 15, 165, 45);
		btnThem.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnThem.setBackground(new Color(79, 173, 84));
		btnThem.setForeground(Color.WHITE);
		btnThem.setFocusPainted(false);
		pnlContentPane.add(btnThem);

		btnXoa = new JButton("XÓA", new ImageIcon("image/xoa.png"));
		btnXoa.setBounds((int) (d.getWidth() - 910), 15, 165, 45);
		btnXoa.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnXoa.setBackground(new Color(79, 173, 84));
		btnXoa.setForeground(Color.WHITE);
		btnXoa.setFocusPainted(false);
		pnlContentPane.add(btnXoa);

		btnCapNhat = new JButton("CẬP NHẬT", new ImageIcon("image/capnhat.png"));
		btnCapNhat.setBounds((int) (d.getWidth() - 740), 15, 165, 45);
		btnCapNhat.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnCapNhat.setBackground(new Color(79, 173, 84));
		btnCapNhat.setForeground(Color.WHITE);
		btnCapNhat.setFocusPainted(false);
		pnlContentPane.add(btnCapNhat);

		btnDocFile = new JButton("ĐỌC FILE", new ImageIcon("image/docfile.png"));
		btnDocFile.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnDocFile.setBackground(new Color(79, 173, 84));
		btnDocFile.setForeground(Color.WHITE);
		btnDocFile.setBounds((int) (d.getWidth() - 570), 15, 165, 45);
		btnDocFile.setFocusPainted(false);
		pnlContentPane.add(btnDocFile);

		btnXuatExcel = new JButton("XUẤT EXCEL", new ImageIcon("image/xuatexcel.png"));
		btnXuatExcel.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnXuatExcel.setBackground(new Color(79, 173, 84));
		btnXuatExcel.setForeground(Color.WHITE);
		btnXuatExcel.setBounds((int) (d.getWidth() - 400), 15, 165, 45);
		btnXuatExcel.setFocusPainted(false);
		pnlContentPane.add(btnXuatExcel);
		// Thông tin tìm kiếm
		JPanel pnlTimKiem = new JPanel();
		pnlTimKiem.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), ""));
		pnlTimKiem.setBounds(20, 15, 195, (int) (d.getHeight() - 109));
		pnlTimKiem.setBackground(Color.WHITE);
		pnlTimKiem.setLayout(null);
		pnlContentPane.add(pnlTimKiem);

		JLabel lblMaDV = new JLabel("<html><div style='text-align: center;'>MÃ DỊCH VỤ: </div></html>",
				SwingConstants.CENTER);
		lblMaDV.setOpaque(true);
		lblMaDV.setBackground(new Color(0, 148, 224));
		lblMaDV.setBounds(1, 10, 200, 30);
		lblMaDV.setForeground(Color.WHITE);
		lblMaDV.setFont(new Font("Arial", Font.BOLD, 15));
		pnlTimKiem.add(lblMaDV);
		cmbMaDV = new JComboBox<String>();
		cmbMaDV.setBounds(26, 50, 150, 30);
		cmbMaDV.setBackground(Color.WHITE);
		cmbMaDV.setEditable(true);
		cmbMaDV.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		docDuLieuVaoCmbMaDV();
		AutoCompleteDecorator.decorate(cmbMaDV);
		pnlTimKiem.add(cmbMaDV);
		cmbMaDV.setMaximumRowCount(3);

		JLabel lblTenDV = new JLabel("<html><div style='text-align: center;'>TÊN DỊCH VỤ: </div></html>",
				SwingConstants.CENTER);
		lblTenDV.setOpaque(true);
		lblTenDV.setBackground(new Color(0, 148, 224));
		lblTenDV.setForeground(Color.WHITE);
		lblTenDV.setBounds(1, 100, 193, 30);
		lblTenDV.setFont(new Font("Arial", Font.BOLD, 15));
		pnlTimKiem.add(lblTenDV);
		cmbTenDV = new JComboBox<String>();
		cmbTenDV.setBounds(22, 140, 150, 30);
		cmbTenDV.setBackground(Color.WHITE);
		cmbTenDV.setEditable(true);
		cmbTenDV.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		cmbTenDV.setMaximumRowCount(3);
		docDuLieuVaoCmbTen();
		AutoCompleteDecorator.decorate(cmbTenDV);
		pnlTimKiem.add(cmbTenDV);

		JLabel lblTinhTrang = new JLabel("<html><div style='text-align: center;'>TÌNH TRẠNG: </div></html>",
				SwingConstants.CENTER);
		lblTinhTrang.setBounds(1, 190, 193, 30);
		lblTinhTrang.setOpaque(true);
		lblTinhTrang.setBackground(new Color(0, 148, 224));
		lblTinhTrang.setForeground(Color.WHITE);
		lblTinhTrang.setFont(new Font("Arial", Font.BOLD, 15));
		pnlTimKiem.add(lblTinhTrang);
		String[] tinhtrang = { "Tất cả", "Còn hàng", "Gần hết hàng", "Hết hàng" };
		cmbTinhTrang = new JComboBox<String>(tinhtrang);
		cmbTinhTrang.setBounds(22, 230, 150, 30);
		cmbTinhTrang.setFocusable(false);
		cmbTinhTrang.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlTimKiem.add(cmbTinhTrang);

		JLabel lblGiaTien = new JLabel("<html><div style='text-align: center;'>GIÁ TIỀN: </div></html>",
				SwingConstants.CENTER);
		lblGiaTien.setBounds(1, 280, 193, 30);
		lblGiaTien.setOpaque(true);
		lblGiaTien.setBackground(new Color(0, 148, 224));
		lblGiaTien.setForeground(Color.WHITE);
		lblGiaTien.setFont(new Font("Arial", Font.BOLD, 15));
		pnlTimKiem.add(lblGiaTien);

		JLabel lblMin = new JLabel("Từ: ");
		lblMin.setBounds(22, 320, 150, 30);
		lblMin.setFont(new Font("Arial", Font.PLAIN, 15));
		pnlTimKiem.add(lblMin);
		txtGiaMin = new JTextField();
		txtGiaMin.setBounds(60, 320, 110, 30);
		txtGiaMin.setBackground(Color.WHITE);
		txtGiaMin.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlTimKiem.add(txtGiaMin);
		JLabel lblMax = new JLabel("Đến: ");
		lblMax.setBounds(22, 360, 50, 30);
		lblMax.setFont(new Font("Arial", Font.PLAIN, 15));
		pnlTimKiem.add(lblMax);
		txtGiaMax = new JTextField();
		txtGiaMax.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		txtGiaMax.setBounds(60, 360, 110, 30);
		txtGiaMax.setBackground(Color.WHITE);
		pnlTimKiem.add(txtGiaMax);

		JLabel lblSapXep = new JLabel("<html><div style='text-align: center;'>SẮP XẾP THEO: </div></html>",
				SwingConstants.CENTER);
		lblSapXep.setBounds(1, 410, 193, 30);
		lblSapXep.setOpaque(true);
		lblSapXep.setBackground(new Color(0, 148, 224));
		lblSapXep.setForeground(Color.WHITE);
		lblSapXep.setFont(new Font("Arial", Font.BOLD, 15));
		pnlTimKiem.add(lblSapXep);
		String[] tieuChi = { "Mã dịch vụ", "Tên dịch vụ", "Số lượng tồn", "Giá tiền" };
		cmbTieuChi = new JComboBox<String>(tieuChi);
		cmbTieuChi.setBounds(22, 450, 150, 30);
		cmbTieuChi.setFocusable(false);
		cmbTieuChi.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlTimKiem.add(cmbTieuChi);

		radTangDan = new JRadioButton("Tăng dần");
		radTangDan.setBounds(15, 490, 80, 30);
		radTangDan.setBackground(Color.WHITE);
		radTangDan.setFont(new Font("Arial", Font.ITALIC, 13));
		radTangDan.setSelected(true);
		radGiamDan = new JRadioButton("Giảm dần");
		radGiamDan.setBounds(95, 490, 90, 30);
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
		btnTimKiem.setBounds(14, 540, 170, 45);
		btnTimKiem.setForeground(Color.WHITE);
		btnTimKiem.setFocusPainted(false);
		pnlTimKiem.add(btnTimKiem);

		btnLamMoi = new JButton("LÀM MỚI", new ImageIcon("image/lammoi.png"));
		btnLamMoi.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnLamMoi.setBounds(14, 600, 170, 45);
		btnLamMoi.setBackground(new Color(0, 148, 224));
		btnLamMoi.setForeground(Color.WHITE);
		btnLamMoi.setFocusPainted(false);
		pnlTimKiem.add(btnLamMoi);

		/*
		 * Danh sách dịch vụ
		 */
		JPanel pnlDanhSach = new JPanel();
		pnlDanhSach.setBorder(
				BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "DANH SÁCH DỊCH VỤ: "));
		pnlDanhSach.setBounds(230, 75, (int) (d.getWidth() - 450), (int) (d.getHeight() - 165));
		pnlDanhSach.setBackground(new Color(255, 255, 255));
		pnlDanhSach.setLayout(new GridLayout(1, 0, 0, 0));
		pnlContentPane.add(pnlDanhSach);

		String[] header = { "Mã dịch vụ", "Tên dịch vụ", "Số lượng tồn", "Đơn vị tính", "Giá tiền" };
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
		pnlDanhSach.add(new JScrollPane(table));

		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(DefaultTableCellRenderer.RIGHT);
		table.getColumn("Số lượng tồn").setCellRenderer(rightRenderer);
		table.getColumn("Giá tiền").setCellRenderer(rightRenderer);

		btnCapNhat.addActionListener(this);
		btnThem.addActionListener(this);
		btnXoa.addActionListener(this);
		btnLamMoi.addActionListener(this);
		btnTimKiem.addActionListener(this);
		btnXuatExcel.addActionListener(this);
		btnDocFile.addActionListener(this);
		table.addMouseListener(this);
		docDuLieuDatabaseVaoTable();

		table.setDefaultEditor(Object.class, null);
		table.getTableHeader().setReorderingAllowed(false);
		return pnlContentPane;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnThem)) {
			new FormThemDV().setVisible(true);
		}
		if (o.equals(btnCapNhat)) {
			int r = table.getSelectedRow();
			if (r == -1) {
				JOptionPane.showMessageDialog(this, "Vui lòng chọn dịch vụ cần cập nhật!", "Lỗi",
						JOptionPane.ERROR_MESSAGE);
				return;
			} else {
				List<String> listDV = dichvu_dao.getDichVuDangSuDung();
				for (String maDV : listDV) {
					if (maDV.equals(tableModel.getValueAt(r, 0).toString().trim())) {
						JOptionPane.showMessageDialog(this, "Dịch vụ đang được sử dụng nên không thể cập nhật!", "Lỗi",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				new FormCapNhatDV().setVisible(true);
			}
		}
		if (o.equals(btnXoa)) {
			int r = table.getSelectedRow();
			if (r == -1) {
				JOptionPane.showMessageDialog(this, "Vui lòng chọn dịch vụ cần xóa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
				return;
			}
			List<String> listDV = dichvu_dao.getDichVuDangSuDung();
			for (String maDV : listDV) {
				if (maDV.equals(tableModel.getValueAt(r, 0).toString().trim())) {
					JOptionPane.showMessageDialog(this, "Dịch vụ đang được sử dụng nên không thể xóa!", "Lỗi",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
			int result = JOptionPane.showConfirmDialog(this, "Bạn có chắc sẽ xóa dịch vụ này không?", "Cảnh báo",
					JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			if (result == 0) {
				dichvu_dao.delete(tableModel.getValueAt(r, 0).toString());
				tableModel.removeRow(r);
			}
		}
		if (o.equals(btnLamMoi)) {
			cmbMaDV.setSelectedIndex(0);
			cmbTenDV.setSelectedIndex(0);
			cmbTinhTrang.setSelectedIndex(0);
			txtGiaMin.setText("");
			txtGiaMax.setText("");
			cmbTieuChi.setSelectedIndex(0);
			radTangDan.setSelected(true);
			xoaHetDL();
			docDuLieuDatabaseVaoTable();
		}
		if (o.equals(btnTimKiem)) {
			String maDV = cmbMaDV.getSelectedItem().toString().trim();
			String tenDV = cmbTenDV.getSelectedItem().toString().trim();
			String tinhTrang = cmbTinhTrang.getSelectedItem().toString().trim();
			String giaMin = txtGiaMin.getText();
			String giaMax = txtGiaMax.getText();
			int tieuChi = cmbTieuChi.getSelectedIndex();
			boolean tangDan = radTangDan.isSelected();

			if (!validInput())
				return;
			else {
				xoaHetDL();
				List<DichVu> listDV = dichvu_dao.getTatCaDichVuTonTai();
				if (!maDV.trim().equals("")) {
					List<DichVu> listTemp = new ArrayList<DichVu>();
					for (DichVu dv : listDV) {
						listTemp.add(dv);
					}
					listDV.clear();
					for (DichVu dv : listTemp) {
						if (dv.getMaDichVu().trim().contains(maDV)) {
							listDV.add(dv);
						}
					}
				}
				if (!tenDV.trim().equals("")) {
					List<DichVu> listTemp = new ArrayList<DichVu>();
					for (DichVu dv : listDV) {
						listTemp.add(dv);
					}
					listDV.clear();
					for (DichVu dv : listTemp) {
						if (dv.getTenDichVu().trim().contains(tenDV)) {
							listDV.add(dv);
						}
					}
				}
				if (!tinhTrang.trim().equals("Tất cả")) {
					List<DichVu> listTemp = new ArrayList<DichVu>();
					for (DichVu dv : listDV) {
						listTemp.add(dv);
					}
					listDV.clear();
					if (tinhTrang.equalsIgnoreCase("Còn hàng")) {
						for (DichVu dv : listTemp) {
							if (dv.getSoLuong() > 0)
								listDV.add(dv);
						}
					} else if (tinhTrang.equalsIgnoreCase("Gần hết hàng")) {
						for (DichVu dv : listTemp) {
							if (dv.getSoLuong() > 0 && dv.getSoLuong() < 10)
								listDV.add(dv);
						}
					} else if (tinhTrang.equalsIgnoreCase("Hết hàng")) {
						for (DichVu dv : listTemp) {
							if (dv.getSoLuong() == 0)
								listDV.add(dv);
						}
					}
				}
				if (!giaMin.trim().equals("")) {
					double min = Double.parseDouble(giaMin.trim());
					List<DichVu> listTemp = new ArrayList<DichVu>();
					for (DichVu dv : listDV) {
						listTemp.add(dv);
					}
					listDV.clear();
					for (DichVu dv : listTemp) {
						if (dv.getGiaTien() >= min)
							listDV.add(dv);
					}
				}
				if (!giaMax.trim().equals("")) {
					double max = Double.parseDouble(giaMax.trim());
					List<DichVu> listTemp = new ArrayList<DichVu>();
					for (DichVu dv : listDV) {
						listTemp.add(dv);
					}
					listDV.clear();
					for (DichVu dv : listTemp) {
						if (dv.getGiaTien() <= max)
							listDV.add(dv);
					}
				}
				if (listDV.size() == 0) {
					JOptionPane.showMessageDialog(this, "Không có dịch vụ nào phù hợp với tiêu chí", "Lỗi",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (tieuChi == 0)
					sapXepTheoMaDV(listDV, tangDan);
				else if (tieuChi == 1)
					sapXepTheoTenDV(listDV, tangDan);
				else if (tieuChi == 2)
					sapXepTheoSoLuong(listDV, tangDan);
				else if (tieuChi == 3)
					sapXepTheoGiaTien(listDV, tangDan);
				DecimalFormat df = new DecimalFormat("#,##0.0");
				for (DichVu dv : listDV) {
					tableModel.addRow(new Object[] { dv.getMaDichVu(), dv.getTenDichVu(), dv.getSoLuong(),
							dv.getDonViTinh(), df.format(dv.getGiaTien()) });
				}
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
				if (!(filePath.endsWith(".xls") || filePath.endsWith(".xlsx"))) {
					filePath += ".xls";
				}
				if (xuatExcel(filePath))
					JOptionPane.showMessageDialog(this, "Ghi file thành công!!", "Thành công",
							JOptionPane.INFORMATION_MESSAGE);
				else
					JOptionPane.showMessageDialog(this, "Ghi file thất bại!!", "Lỗi", JOptionPane.ERROR_MESSAGE);
			}
		}
		if (o.equals(btnDocFile)) {
			JFileChooser fileDialog = new JFileChooser() {
				@Override
				protected JDialog createDialog(Component parent) throws HeadlessException {
					JDialog dialog = super.createDialog(parent);
					ImageIcon icon = new ImageIcon("image/logodark.png");
					dialog.setIconImage(icon.getImage());
					return dialog;
				}
			};
			int returnVal = fileDialog.showOpenDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				java.io.File file = fileDialog.getSelectedFile();
				if (file.getName().endsWith(".xls")) {
					docFileExcel(file.getAbsolutePath());
				}
				else {
					JOptionPane.showMessageDialog(this, "Vui lòng chọn file Excel để đọc file", "Lỗi",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		}
	}

	public void sapXepTheoMaDV(List<DichVu> list, boolean tangDan) {
		Collections.sort(list, new Comparator<DichVu>() {
			@Override
			public int compare(DichVu o1, DichVu o2) {
				if (tangDan) {
					if (o1 != null && o2 != null)
						return o1.getMaDichVu().compareToIgnoreCase(o2.getMaDichVu());
					return 0;
				} else {
					if (o1 != null && o2 != null)
						return o2.getMaDichVu().compareToIgnoreCase(o1.getMaDichVu());
					return 0;
				}
			}
		});
	}

	public void sapXepTheoTenDV(List<DichVu> list, boolean tangDan) {
		Collections.sort(list, new Comparator<DichVu>() {
			@Override
			public int compare(DichVu o1, DichVu o2) {
				if (tangDan) {
					if (o1 != null && o2 != null)
						return o1.getTenDichVu().compareToIgnoreCase(o2.getTenDichVu());
					return 0;
				} else {
					if (o1 != null && o2 != null)
						return o2.getTenDichVu().compareToIgnoreCase(o1.getTenDichVu());
					return 0;
				}
			}
		});
	}

	public void sapXepTheoSoLuong(List<DichVu> list, boolean tangDan) {
		Collections.sort(list, new Comparator<DichVu>() {
			@Override
			public int compare(DichVu o1, DichVu o2) {
				if (tangDan) {
					if (o1 != null && o2 != null)
						return Integer.compare(o1.getSoLuong(), o2.getSoLuong());
					return 0;
				} else {
					if (o1 != null && o2 != null)
						return Integer.compare(o2.getSoLuong(), o1.getSoLuong());
					return 0;
				}
			}
		});
	}

	public void sapXepTheoGiaTien(List<DichVu> list, boolean tangDan) {
		Collections.sort(list, new Comparator<DichVu>() {
			@Override
			public int compare(DichVu o1, DichVu o2) {
				if (tangDan) {
					if (o1 != null && o2 != null)
						return Double.compare(o1.getGiaTien(), o2.getGiaTien());
					return 0;
				} else {
					if (o1 != null && o2 != null)
						return Double.compare(o2.getGiaTien(), o1.getGiaTien());
					return 0;
				}
			}
		});
	}

	private boolean validInput() {
		String giaMin = txtGiaMin.getText();
		String giaMax = txtGiaMax.getText();
		if (giaMin.trim().length() > 0) {
			try {
				double x = Double.parseDouble(giaMin);
				if (x <= 0) {
					JOptionPane.showMessageDialog(this, "Giá phải lớn hơn hoặc bằng 0", "Lỗi",
							JOptionPane.ERROR_MESSAGE);
					txtGiaMin.setText("");
					txtGiaMin.requestFocus();
					return false;
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "Error: Giá phải nhập số", "Lỗi", JOptionPane.ERROR_MESSAGE);
				txtGiaMin.setText("");
				txtGiaMin.requestFocus();
				return false;
			}
		}
		if (giaMax.trim().length() > 0) {
			try {
				double x = Double.parseDouble(giaMax);
				if (x <= 0) {
					JOptionPane.showMessageDialog(this, "Giá phải lớn hơn hoặc bằng 0", "Lỗi",
							JOptionPane.ERROR_MESSAGE);
					txtGiaMax.setText("");
					txtGiaMax.requestFocus();
					return false;
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "Error: Giá phải nhập số", "Lỗi", JOptionPane.ERROR_MESSAGE);
				txtGiaMax.setText("");
				txtGiaMax.requestFocus();
				return false;
			}
		}
		if (giaMin.trim().length() > 0 && giaMax.trim().length() > 0) {
			double min = Double.parseDouble(giaMin);
			double max = Double.parseDouble(giaMax);
			if (max < min) {
				JOptionPane.showMessageDialog(this, "Giá MAX không được bé hơn giá MIN", "Lỗi",
						JOptionPane.ERROR_MESSAGE);
				txtGiaMax.requestFocus();
				return false;
			}
		}
		return true;
	}

	public static void docDuLieuVaoCmbTen() {
		List<DichVu> list = dichvu_dao.getTatCaDichVuTonTai();
		cmbTenDV.addItem("");
		for (DichVu dv : list) {
			cmbTenDV.addItem(dv.getTenDichVu());
		}
	}

	public static void docDuLieuVaoCmbMaDV() {
		List<DichVu> list = dichvu_dao.getTatCaDichVuTonTai();
		cmbMaDV.addItem("");
		for (DichVu dv : list) {
			cmbMaDV.addItem(dv.getMaDichVu());
		}
	}

	public static void docDuLieuDatabaseVaoTable() {
		List<DichVu> list = dichvu_dao.getTatCaDichVuTonTai();
		DecimalFormat df = new DecimalFormat("#,##0.0");
		for (DichVu dv : list) {
			tableModel.addRow(new Object[] { dv.getMaDichVu(), dv.getTenDichVu(), dv.getSoLuong(), dv.getDonViTinh(),
					df.format(dv.getGiaTien()) });
		}
	}

	public static void xoaHetDL() {
		DefaultTableModel dm = (DefaultTableModel) table.getModel();
		dm.setRowCount(0);
	}

	@SuppressWarnings("resource")
	public boolean xuatExcel(String filePath) {
		try {
			FileOutputStream fileOut = new FileOutputStream(filePath);
			// Tạo sheet Danh sách dịch vụ
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet worksheet = workbook.createSheet("DANH SÁCH DỊCH VỤ");

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

			cell.setCellValue("DANH SÁCH DỊCH VỤ");
			cell.setCellStyle(styleTenDanhSach);

			String[] header = { "STT", "Mã dịch vụ", "Tên dịch vụ", "Số lượng tồn", "Đơn vị tính", "Giá tiền" };
			for (int i = 1; i < header.length + 1; i++) {
				worksheet.autoSizeColumn(i);
			}
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
							if (j == header.length - 1 || j == header.length - 3) {
								if (j == header.length - 1) {
									String gia[] = tableModel.getValueAt(i, j - 1).toString().split(",");
									String giaTien = "";
									for (int t = 0; t < gia.length; t++)
										giaTien += gia[t];
									cell.setCellValue(Double.parseDouble(giaTien));
								} else
									cell.setCellValue(Integer.parseInt(table.getValueAt(i, j - 1).toString().trim()));
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

	@SuppressWarnings("unused")
	public void docFileExcel(String filePath) {
		List<DichVu> listDVDocTuFile = new ArrayList<DichVu>();
		try {
			FileInputStream iStream = null;
			HSSFWorkbook workbook;
			try {
				iStream = new FileInputStream(filePath);
				workbook = new HSSFWorkbook(iStream);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "File không hợp lệ!!", "Lỗi", JOptionPane.ERROR_MESSAGE);
				return;
			}
			HSSFSheet worksheet = workbook.getSheet("DANH SÁCH DỊCH VỤ");

			if (worksheet == null) {
				JOptionPane.showMessageDialog(this, "File không có worksheet \"DANH SÁCH DỊCH VỤ\"", "Lỗi", JOptionPane.ERROR_MESSAGE);
				workbook.close();
				return;
			}

			String[] title = { "STT", "Mã dịch vụ", "Tên dịch vụ", "Số lượng", "Đơn vị tính", "Giá tiền" };
			HSSFRow row0 = worksheet.getRow(0);
			for (int i = 0; i < title.length; i++) {
				String temp = "";
				try {
					temp = row0.getCell(i).getStringCellValue();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(this, "File không hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
					workbook.close();
					return;
				}
				if (!temp.trim().equals(title[i])) {
					JOptionPane.showMessageDialog(this, "File excel không đúng định dạng", "Lỗi", JOptionPane.ERROR_MESSAGE);
					workbook.close();
					return;
				}
			}

			HSSFRow row = worksheet.getRow(1);
			int i = 1;
			// Đọc dữ liệu vào list
			String temp = "";
			try {
				temp = row.getCell(1).getStringCellValue();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "Dòng "+(i+1)+" file Excel sai định dạng", "Lỗi", JOptionPane.ERROR_MESSAGE);
				workbook.close();
				return;
			}
			while (!temp.trim().equals("")) {
				try {
					String maDV = row.getCell(1).getStringCellValue();
					String tenDV = row.getCell(2).getStringCellValue();
					int soLuong = (int) row.getCell(3).getNumericCellValue();
					String dVT = row.getCell(4).getStringCellValue();
					double giaTien = row.getCell(5).getNumericCellValue();

					DichVu dv = new DichVu(maDV, tenDV, soLuong, dVT, giaTien);
					listDVDocTuFile.add(dv);
					row = worksheet.getRow(++i);
					if (row == null)
						break;
					temp = row.getCell(1).getStringCellValue();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(this, "Dòng "+(i+1)+" file Excel sai định dạng", "Lỗi", JOptionPane.ERROR_MESSAGE);
					workbook.close();
					return;
				}
			}
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (listDVDocTuFile == null) {
			JOptionPane.showMessageDialog(this, "File không hợp lệ!!", "Lỗi", JOptionPane.ERROR_MESSAGE);
			return;
		}
		int flag = 0;
		int kiemTraDVBiBoQua = 0;
		List<DichVu> listDVBoQua = new ArrayList<DichVu>();
		for (DichVu dVDocTuFile : listDVDocTuFile) {
			// Duyệt tất cả dịch vụ trong cơ sở dữ liệu
			List<DichVu> listDVTrongDB = dichvu_dao.getTatCaDichVuTonTai();
			if (listDVTrongDB != null) {
				for (DichVu dVTrongDB : listDVTrongDB) {
					// Kiểm tra có trùng mã hay không
					if (dVTrongDB.getMaDichVu().trim().equals(dVDocTuFile.getMaDichVu().trim())) {
						// Nếu trùng mà đang sử dụng thì bỏ qua và lưu vào danh sách bỏ qua
						int temp = 0;
						List<String> listDV = dichvu_dao.getDichVuDangSuDung();
						for (String maDV : listDV) {
							if (maDV.equals(dVDocTuFile.getMaDichVu().trim())) {
								listDVBoQua.add(dVDocTuFile);
								temp = 1;
								kiemTraDVBiBoQua++;
							}
						}
						if (temp == 0) {
							// Nếu trùng mà không có sử dụng thì cập nhật
							dichvu_dao.update(new DichVu(dVTrongDB.getMaDichVu(), dVDocTuFile.getTenDichVu(),
									dVDocTuFile.getSoLuong() + dVTrongDB.getSoLuong(), dVDocTuFile.getDonViTinh(),
									dVDocTuFile.getGiaTien()));
						}
						temp = 0;
						flag = 1;
						break;
					}
				}
			}
			// Nếu không trùng mã trong csdl thì kiểm tra có đúng định dạng không
			if (flag == 0) {
				// Kiểm tra mã có đúng định dạng DVyxxxxxxx với y từ 1 đến 9,x từ 0 đến 9
				if (dVDocTuFile.getMaDichVu().matches("DV[1-9][0-9]{7}")) {
					// Nếu đúng thì thêm mới dịch vụ
					DichVu dv = new DichVu(dVDocTuFile.getMaDichVu().trim(), dVDocTuFile.getTenDichVu(),
							dVDocTuFile.getSoLuong(), dVDocTuFile.getDonViTinh(), dVDocTuFile.getGiaTien());
					dichvu_dao.create(dv);
				} else {
					// Nếu sai thì bỏ qua và lưu vào danh sách bỏ qua
					listDVBoQua.add(dVDocTuFile);
					kiemTraDVBiBoQua++;
				}

			}
			flag = 0;
		}
		if (kiemTraDVBiBoQua != 0) {
			String pathArray[] = filePath.split("");
			int count=0;
			for (int i = pathArray.length-1; i >=0 ; i--) {
				if(pathArray[i].equals("\\"))
					break;
				else 
					count++;
			}
			String path = filePath.substring(0, filePath.length() - count);

			JOptionPane.showMessageDialog(this,
					"Có " + kiemTraDVBiBoQua + " dịch vụ bị bỏ qua do đang được sử dụng hoặc mã dịch vụ không hợp lệ được lưu ở "
							+ path + "DanhSachDichVuBoQua.xls",
					"Nhắc nhở", JOptionPane.WARNING_MESSAGE);
			xuatExcelDichVuBoQua(listDVBoQua, path);
			JOptionPane.showMessageDialog(this,
					"Đọc file thành công!!",
					"Thành công", JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(this, "Đọc file thành công!!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
		}

		xoaHetDL();
		docDuLieuDatabaseVaoTable();

		cmbTenDV.removeAllItems();
		cmbMaDV.removeAllItems();
		docDuLieuVaoCmbTen();
		docDuLieuVaoCmbMaDV();

	}

	@SuppressWarnings("resource")
	public boolean xuatExcelDichVuBoQua(List<DichVu> listDVBoQua, String filePath) {
		try {
			FileOutputStream fileOut = new FileOutputStream(filePath + "DanhSachDichVuBoQua.xls");
			// Tạo sheet Danh dịch vụ bỏ qua
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet worksheet = workbook.createSheet("DANH SÁCH DỊCH VỤ");

			HSSFRow row;
			HSSFCell cell;

			// Dòng 1 tên các cột
			String[] header = { "STT", "Mã dịch vụ", "Tên dịch vụ", "Số lượng", "Đơn vị tính", "Giá tiền" };
			row = worksheet.createRow(0);
			cell = worksheet.createRow(0).createCell(0);
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
				cell = row.createCell(i);
				cell.setCellValue(header[i]);
				cell.setCellStyle(styleHeader);
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
			int STT = 1;
			int t = 1;
			for (DichVu dv : listDVBoQua) {
				row = worksheet.createRow(t++);
				
				cell = row.createCell(0);
				cell.setCellValue(STT++);
				cell.setCellStyle(styleRow);
				
				cell = row.createCell(1);
				cell.setCellValue(dv.getMaDichVu().trim());
				cell.setCellStyle(styleRow);
				
				cell = row.createCell(2);
				cell.setCellValue(dv.getTenDichVu().trim());
				cell.setCellStyle(styleRow);
				
				cell = row.createCell(3);
				cell.setCellValue(dv.getSoLuong());
				cell.setCellStyle(styleRow);
				
				cell = row.createCell(4);
				cell.setCellValue(dv.getDonViTinh());
				cell.setCellStyle(styleRow);
				
				cell = row.createCell(5);
				cell.setCellValue(dv.getGiaTien());
				cell.setCellStyle(styleRow);
			}
			for (int j = 0; j < header.length; j++) {
				worksheet.autoSizeColumn(j);
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
		if (e.getClickCount() == 2 && table.getSelectedRow() != -1) {
			List<String> listDV = dichvu_dao.getDichVuDangSuDung();
			for (String maDV : listDV) {
				if (maDV.equals(tableModel.getValueAt(r, 0).toString().trim())) {
					JOptionPane.showMessageDialog(this, "Dịch vụ đang được sử dụng nên không thể cập nhật!", "Lỗi",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
			new FormCapNhatDV().setVisible(true);
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
