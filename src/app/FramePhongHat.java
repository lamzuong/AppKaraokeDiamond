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
import java.text.ParseException;
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
import dao.PhongHat_DAO;
import dao.TraPhong_DAO;
import entity.NhanVien;
import entity.PhongHat;

public class FramePhongHat extends JFrame implements ActionListener, MouseListener {
	private JButton btnThem;
	private JButton btnXoa;
	private JButton btnCapNhat;
	public static DefaultTableModel tableModel;
	public static JTable table;
	private JButton btnLamMoi;
	private JButton btnTimKiem;
	public static JComboBox<String> cmbMaPhong;
	public static JComboBox<String> cmbTenPhong;
	private JTextField txtGiaMin;
	private JTextField txtGiaMax;
	private JComboBox<String> cmbLoaiPhong;
	private JComboBox<String> cmbTrangThai;
	private JButton btnXuatExcel;
	private JComboBox<String> cmbTieuChi;
	private JRadioButton radTangDan;
	private JRadioButton radGiamDan;
	private TraPhong_DAO traPhong_dao;
	private static PhongHat_DAO phong_dao;

	public JPanel createPanelPhongHat() throws ParseException {
		// kh???i t???o k???t n???i ?????n CSDL
		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		phong_dao = new PhongHat_DAO();
		traPhong_dao = new TraPhong_DAO();
		// ----------------
		Toolkit toolkit = this.getToolkit(); /* L???y ????? ph??n gi???i m??n h??nh */
		Dimension d = toolkit.getScreenSize();

		JPanel pnlContentPane = new JPanel();
		pnlContentPane.setBackground(new Color(255, 255, 255));
		pnlContentPane.setLayout(null);
		setContentPane(pnlContentPane);
		/*
		 * Ch???c n??ng
		 */
		btnThem = new JButton("TH??M M???I", new ImageIcon("image/them.png"));
		btnThem.setBounds((int) (d.getWidth() - 910), 15, 165, 45);
		btnThem.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnThem.setBackground(new Color(79, 173, 84));
		btnThem.setForeground(Color.WHITE);
		btnThem.setFocusPainted(false);
		pnlContentPane.add(btnThem);

		btnXoa = new JButton("X??A", new ImageIcon("image/xoa.png"));
		btnXoa.setBounds((int) (d.getWidth() - 740), 15, 165, 45);
		btnXoa.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnXoa.setBackground(new Color(79, 173, 84));
		btnXoa.setForeground(Color.WHITE);
		btnXoa.setFocusPainted(false);
		pnlContentPane.add(btnXoa);

		btnCapNhat = new JButton("C???P NH???T", new ImageIcon("image/capnhat.png"));
		btnCapNhat.setBounds((int) (d.getWidth() - 570), 15, 165, 45);
		btnCapNhat.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnCapNhat.setBackground(new Color(79, 173, 84));
		btnCapNhat.setForeground(Color.WHITE);
		btnCapNhat.setFocusPainted(false);
		pnlContentPane.add(btnCapNhat);

		btnXuatExcel = new JButton("XU???T EXCEL", new ImageIcon("image/xuatexcel.png"));
		btnXuatExcel.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnXuatExcel.setBackground(new Color(79, 173, 84));
		btnXuatExcel.setForeground(Color.WHITE);
		btnXuatExcel.setBounds((int) (d.getWidth() - 400), 15, 165, 45);
		btnXuatExcel.setFocusPainted(false);
		pnlContentPane.add(btnXuatExcel);
		// Th??ng tin t??m ki???m
		JPanel pnlTimKiem = new JPanel();
		pnlTimKiem.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), ""));
		pnlTimKiem.setBounds(20, 15, 195, (int) (d.getHeight() - 109));
		pnlTimKiem.setBackground(new Color(255, 255, 255));
		pnlTimKiem.setLayout(null);
		pnlContentPane.add(pnlTimKiem);
		
		JLabel lblMaPhong = new JLabel("<html><div style='text-align: center;'>M?? PH??NG: </div></html>", SwingConstants.CENTER);
		lblMaPhong.setOpaque(true);
		lblMaPhong.setBackground(new Color(0, 148, 224));
		lblMaPhong.setBounds(1, 10, 200, 30);
		lblMaPhong.setForeground(Color.WHITE);
		lblMaPhong.setFont(new Font("Arial", Font.BOLD, 15));
		pnlTimKiem.add(lblMaPhong);
		cmbMaPhong = new JComboBox<String>();
		cmbMaPhong.setBounds(26, 50, 150, 30);
		cmbMaPhong.setBackground(Color.WHITE);
		cmbMaPhong.setEditable(true);
		cmbMaPhong.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		docDuLieuVaoCmbMaPhong();
		AutoCompleteDecorator.decorate(cmbMaPhong);
		pnlTimKiem.add(cmbMaPhong);
		cmbMaPhong.setMaximumRowCount(3);

		JLabel lblTenPhong = new JLabel("<html><div style='text-align: center;'>T??N PH??NG H??T: </div></html>", SwingConstants.CENTER);
		lblTenPhong.setOpaque(true);
		lblTenPhong.setBackground(new Color(0, 148, 224));
		lblTenPhong.setBounds(1, 100, 193, 30);
		lblTenPhong.setForeground(Color.WHITE);
		lblTenPhong.setFont(new Font("Arial", Font.BOLD, 15));
		pnlTimKiem.add(lblTenPhong);
		cmbTenPhong = new JComboBox<String>();
		cmbTenPhong.setBounds(22, 140, 150, 30);
		cmbTenPhong.setBackground(Color.WHITE);
		cmbTenPhong.setEditable(true);
		cmbTenPhong.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		docDuLieuVaoCmbTenPhong();
		AutoCompleteDecorator.decorate(cmbTenPhong);
		cmbTenPhong.setMaximumRowCount(3);
		pnlTimKiem.add(cmbTenPhong);

		JLabel lblLoaiPhong = new JLabel("<html><div style='text-align: center;'>LO???I PH??NG: </div></html>", SwingConstants.CENTER);
		lblLoaiPhong.setBounds(1, 190, 193, 30);
		lblLoaiPhong.setOpaque(true);
		lblLoaiPhong.setBackground(new Color(0, 148, 224));
		lblLoaiPhong.setForeground(Color.WHITE);
		lblLoaiPhong.setFont(new Font("Arial", Font.BOLD, 15));
		pnlTimKiem.add(lblLoaiPhong);
		String[] loai = { "T???t c???", "VIP", "Th?????ng" };
		cmbLoaiPhong = new JComboBox<String>(loai);
		cmbLoaiPhong.setBounds(22, 230, 150, 30);
		cmbLoaiPhong.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		cmbLoaiPhong.setFocusable(false);
		pnlTimKiem.add(cmbLoaiPhong);

		JLabel lblTrangThai = new JLabel("<html><div style='text-align: center;'>TR???NG TH??I:  </div></html>", SwingConstants.CENTER);
		lblTrangThai.setBounds(1, 280, 193, 30);
		lblTrangThai.setOpaque(true);
		lblTrangThai.setBackground(new Color(0, 148, 224));
		lblTrangThai.setForeground(Color.WHITE);
		lblTrangThai.setFont(new Font("Arial", Font.BOLD, 15));
		pnlTimKiem.add(lblTrangThai);
		String[] trangthai = { "T???t c???", "???? ?????t", "Tr???ng" };
		cmbTrangThai = new JComboBox<String>(trangthai);
		cmbTrangThai.setBounds(22, 320, 150, 30);
		cmbTrangThai.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		cmbTrangThai.setFocusable(false);
		pnlTimKiem.add(cmbTrangThai);

		JLabel lblGiaPhong = new JLabel("<html><div style='text-align: center;'>GI?? PH??NG: </div></html>", SwingConstants.CENTER);
		lblGiaPhong.setBounds(1, 360, 193, 30);
		lblGiaPhong.setOpaque(true);
		lblGiaPhong.setBackground(new Color(0, 148, 224));
		lblGiaPhong.setForeground(Color.WHITE);
		lblGiaPhong.setFont(new Font("Arial", Font.BOLD, 15));
		pnlTimKiem.add(lblGiaPhong);

		JLabel lblGiaMin = new JLabel("T???: ");
		lblGiaMin.setBounds(22, 400, 150, 30);
		lblGiaMin.setFont(new Font("Arial", Font.PLAIN, 15));
		pnlTimKiem.add(lblGiaMin);
		txtGiaMin = new JTextField();
		txtGiaMin.setBounds(60, 400, 110, 30);
		txtGiaMin.setBackground(Color.WHITE);
		txtGiaMin.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlTimKiem.add(txtGiaMin);

		JLabel lblGiaMax = new JLabel("?????n: ");
		lblGiaMax.setBounds(22, 440, 150, 30);
		lblGiaMax.setFont(new Font("Arial", Font.PLAIN, 15));
		pnlTimKiem.add(lblGiaMax);
		txtGiaMax = new JTextField();
		txtGiaMax.setBounds(60, 440, 110, 30);
		txtGiaMax.setBackground(Color.WHITE);
		txtGiaMax.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlTimKiem.add(txtGiaMax);

		JLabel lblSapXep = new JLabel("<html><div style='text-align: center;'>S???P X???P THEO: </div></html>", SwingConstants.CENTER);
		lblSapXep.setBounds(1, 490, 193, 30);
		lblSapXep.setOpaque(true);
		lblSapXep.setBackground(new Color(0, 148, 224));
		lblSapXep.setForeground(Color.WHITE);
		lblSapXep.setFont(new Font("Arial", Font.BOLD, 15));
		pnlTimKiem.add(lblSapXep);
		String[] tieuChi = { "M?? ph??ng", "T??n ph??ng", "Gi?? ph??ng" };
		cmbTieuChi = new JComboBox<String>(tieuChi);
		cmbTieuChi.setBounds(22, 530, 150, 30);
		cmbTieuChi.setFocusable(false);
		cmbTieuChi.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlTimKiem.add(cmbTieuChi);
		
		radTangDan = new JRadioButton("T??ng d???n");
		radTangDan.setBounds(15, 570, 80, 30);
		radTangDan.setBackground(Color.WHITE);
		radTangDan.setFont(new Font("Arial", Font.ITALIC, 13));
		radTangDan.setSelected(true);
		radGiamDan = new JRadioButton("Gi???m d???n");
		radGiamDan.setBounds(95, 570, 90, 30);
		radGiamDan.setBackground(Color.WHITE);
		radGiamDan.setFont(new Font("Arial", Font.ITALIC, 13));
		ButtonGroup bg = new ButtonGroup();
		bg.add(radTangDan);
		bg.add(radGiamDan);
		pnlTimKiem.add(radTangDan);
		pnlTimKiem.add(radGiamDan);
		
		btnTimKiem = new JButton("T??M KI???M", new ImageIcon("image/timkiem.png"));
		btnTimKiem.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnTimKiem.setBackground(new Color(0, 148, 224));
		btnTimKiem.setBounds(14, 620, 170, 45);
		btnTimKiem.setForeground(Color.WHITE);
		btnTimKiem.setFocusPainted(false);
		pnlTimKiem.add(btnTimKiem);
		
		btnLamMoi = new JButton("L??M M???I", new ImageIcon("image/lammoi.png"));
		btnLamMoi.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnLamMoi.setBackground(new Color(0, 148, 224));
		btnLamMoi.setBounds(14, 680, 170, 45);
		btnLamMoi.setForeground(Color.WHITE);
		btnLamMoi.setFocusPainted(false);
		pnlTimKiem.add(btnLamMoi);

		/*
		 * Danh s??ch ph??ng h??t
		 */
		JPanel pnlDanhSach = new JPanel();
		pnlDanhSach.setBorder(
				BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "DANH S??CH PH??NG H??T: "));
		pnlDanhSach.setBounds(230, 75, (int) (d.getWidth() - 450), (int) (d.getHeight() - 165));
		pnlDanhSach.setBackground(new Color(255, 255, 255));
		pnlDanhSach.setLayout(new GridLayout(1, 0, 0, 0));

		String[] header = { "M?? ph??ng", "T??n ph??ng", "Gi??/1h", "Lo???i ph??ng", "Tr???ng th??i" };
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
		pnlDanhSach.add(new JScrollPane(table));
		
		pnlContentPane.add(pnlDanhSach);

		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(DefaultTableCellRenderer.RIGHT);
		table.getColumn("Gi??/1h").setCellRenderer(rightRenderer);

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
			new FormThemPhong().setVisible(true);
		}
		if (o.equals(btnCapNhat)) {
			int r = table.getSelectedRow();
			List<PhongHat> dsPhong = phong_dao.getPhongTheoTrangThai(true);
			if (dsPhong.size() > 0) {
				JOptionPane.showMessageDialog(this, "Kh??ng ???????c c???p nh???t d??? li???u v?? c??n ng?????i s??? d???ng ph??ng!", "L???i",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (r == -1) {
				JOptionPane.showMessageDialog(this, "Vui l??ng ch???n ph??ng c???n c???p nh???t!", "L???i",
						JOptionPane.ERROR_MESSAGE);
				return;
			} else {
				new FormCapNhatPhong().setVisible(true);
			}
		}
		if (o.equals(btnXoa)) {
			int r = table.getSelectedRow();
			if (r == -1) {
				JOptionPane.showMessageDialog(this, "Vui l??ng ch???n ph??ng c???n x??a!", "L???i", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (tableModel.getValueAt(r, 4).toString().trim().equals("???? ?????t")) {
				JOptionPane.showMessageDialog(this, "Kh??ng ???????c x??a ph??ng n??y v?? c?? ng?????i ??ang s??? d???ng ph??ng!", "L???i",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			int result = JOptionPane.showConfirmDialog(this, "B???n c?? ch???c s??? x??a d??ng n??y kh??ng?", "C???nh b??o",
					JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			if (result == 0) {
				phong_dao.delete(tableModel.getValueAt(r, 0).toString());
				tableModel.removeRow(r);
			}
		}
		if (o.equals(btnLamMoi)) {
			cmbTenPhong.setSelectedIndex(0);
			cmbMaPhong.setSelectedIndex(0);
			cmbTrangThai.setSelectedIndex(0);
			cmbLoaiPhong.setSelectedIndex(0);
			txtGiaMin.setText("");
			txtGiaMax.setText("");
			cmbTieuChi.setSelectedIndex(0);
			radTangDan.setSelected(true);
			xoaHetDL();
			docDuLieuDatabaseVaoTable();
		}
		if (o.equals(btnTimKiem)) {
			String maPhong = cmbMaPhong.getSelectedItem().toString().trim();
			String tenPhong = cmbTenPhong.getSelectedItem().toString().trim();
			String loaiPhong = cmbLoaiPhong.getSelectedItem().toString().trim();
			String trangThai = cmbTrangThai.getSelectedItem().toString().trim();
			String giaMin = txtGiaMin.getText();
			String giaMax = txtGiaMax.getText();
			int tieuChi = cmbTieuChi.getSelectedIndex();
			boolean tangDan = radTangDan.isSelected();
			if (!validInput())
				return;
			else {
				xoaHetDL();
				List<PhongHat> list = phong_dao.getTatCaPhongHatTonTai();
				if (!maPhong.trim().equals("")) {
					List<PhongHat> listTemp = new ArrayList<PhongHat>();
					for (PhongHat ph : list) {
						listTemp.add(ph);
					}
					list.clear();
					for (PhongHat ph : listTemp) {
						if (ph.getMaPhong().trim().contains(maPhong)) {
							list.add(ph);
						}
					}
				}
				if (!tenPhong.trim().equals("")) {
					List<PhongHat> listTemp = new ArrayList<PhongHat>();
					for (PhongHat ph : list) {
						listTemp.add(ph);
					}
					list.clear();
					for (PhongHat ph : listTemp) {
						if (ph.getTenPhong().trim().contains(tenPhong)) {
							list.add(ph);
						}
					}
				}
				if (!loaiPhong.trim().equals("T???t c???")) {
					List<PhongHat> listTemp = new ArrayList<PhongHat>();
					for (PhongHat ph : list) {
						listTemp.add(ph);
					}
					list.clear();
					for (PhongHat ph : listTemp) {
						if (ph.getLoaiPhong().trim().equalsIgnoreCase(loaiPhong)) {
							list.add(ph);
						}
					}
				}
				if (!trangThai.trim().equals("T???t c???")) {
					boolean xnTrangThai;
					if (trangThai.equalsIgnoreCase("???? ?????t"))
						xnTrangThai = true;
					else
						xnTrangThai = false;
					List<PhongHat> listTemp = new ArrayList<PhongHat>();
					for (PhongHat ph : list) {
						listTemp.add(ph);
					}
					list.clear();
					for (PhongHat ph : listTemp) {
						if (ph.isTrangThai() == xnTrangThai) {
							list.add(ph);
						}
					}
				}
				if (!giaMin.trim().equals("")) {
					double min = Double.parseDouble(giaMin.trim());
					List<PhongHat> listTemp = new ArrayList<PhongHat>();
					for (PhongHat ph : list) {
						listTemp.add(ph);
					}
					list.clear();
					for (PhongHat ph : listTemp) {
						if (ph.getGiaPhong() >= min)
							list.add(ph);
					}
				}
				if (!giaMax.trim().equals("")) {
					double max = Double.parseDouble(giaMax.trim());
					List<PhongHat> listTemp = new ArrayList<PhongHat>();
					for (PhongHat ph : list) {
						listTemp.add(ph);
					}
					list.clear();
					for (PhongHat ph : listTemp) {
						if (ph.getGiaPhong() <= max)
							list.add(ph);
					}
				}
				if(list.size() == 0) {
					JOptionPane.showMessageDialog(this, "Kh??ng c?? ph??ng h??t n??o ph?? h???p v???i ti??u ch??", "L???i",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(tieuChi == 0)
					sapXepTheoMaPH(list, tangDan);
				else if(tieuChi == 1)
					sapXepTheoTenPH(list, tangDan);
				else if(tieuChi == 2)
					sapXepTheoGiaPH(list, tangDan);
				DecimalFormat df = new DecimalFormat("#,##0.0");
				for (PhongHat ph : list) {
					tableModel.addRow(new Object[] { ph.getMaPhong(), ph.getTenPhong(), df.format(ph.getGiaPhong()),
							ph.getLoaiPhong(), ph.isTrangThai() == false ? "Tr???ng" : "???? ?????t" });
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
				if(!(filePath.endsWith(".xls") || filePath.endsWith(".xlsx"))) {
					filePath += ".xls";
				}
				if (xuatExcel(filePath))
					JOptionPane.showMessageDialog(this, "Ghi file th??nh c??ng!!", "Th??nh c??ng",
							JOptionPane.INFORMATION_MESSAGE);
				else
					JOptionPane.showMessageDialog(this, "Ghi file th???t b???i!!", "L???i", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	public void sapXepTheoMaPH(List<PhongHat> list, boolean tangDan) {
		Collections.sort(list, new Comparator<PhongHat>() {
			@Override
			public int compare(PhongHat o1, PhongHat o2) {
				if (tangDan) {
					if (o1 != null && o2 != null)
						return o1.getMaPhong().compareToIgnoreCase(o2.getMaPhong());
					return 0;
				} else {
					if (o1 != null && o2 != null)
						return o2.getMaPhong().compareToIgnoreCase(o1.getMaPhong());
					return 0;
				}
			}
		});
	}
	public void sapXepTheoTenPH(List<PhongHat> list, boolean tangDan) {
		Collections.sort(list, new Comparator<PhongHat>() {
			@Override
			public int compare(PhongHat o1, PhongHat o2) {
				if (tangDan) {
					if (o1 != null && o2 != null)
						return o1.getTenPhong().compareToIgnoreCase(o2.getTenPhong());
					return 0;
				} else {
					if (o1 != null && o2 != null)
						return o2.getTenPhong().compareToIgnoreCase(o1.getTenPhong());
					return 0;
				}
			}
		});
	}
	public void sapXepTheoGiaPH(List<PhongHat> list, boolean tangDan) {
		Collections.sort(list, new Comparator<PhongHat>() {
			@Override
			public int compare(PhongHat o1, PhongHat o2) {
				if (tangDan) {
					if (o1 != null && o2 != null)
						return Double.compare(o1.getGiaPhong(),o2.getGiaPhong());
					return 0;
				} else {
					if (o1 != null && o2 != null)
						return Double.compare(o2.getGiaPhong(),o1.getGiaPhong());
					return 0;
				}
			}
		});
	}
	private boolean validInput() {
		String txtgiaMin = txtGiaMin.getText();
		String txtgiaMax = txtGiaMax.getText();
		if (txtgiaMin.trim().length() > 0) {
			try {
				double x = Double.parseDouble(txtgiaMin);
				if (x <= 0) {
					JOptionPane.showMessageDialog(this, "Gi?? ph???i l???n h??n ho???c b???ng 0", "L???i",
							JOptionPane.ERROR_MESSAGE);
					txtGiaMin.setText("");
					txtGiaMin.requestFocus();
					return false;
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "Error: Gi?? ph???i nh???p s???", "L???i",
						JOptionPane.ERROR_MESSAGE);
				txtGiaMin.setText("");
				txtGiaMin.requestFocus();
				return false;
			}
		}
		if (txtgiaMax.trim().length() > 0) {
			try {
				double x = Double.parseDouble(txtgiaMax);
				if (x <= 0) {
					JOptionPane.showMessageDialog(this, "Gi?? ph???i l???n h??n ho???c b???ng 0", "L???i",
							JOptionPane.ERROR_MESSAGE);
					txtGiaMax.setText("");
					txtGiaMax.requestFocus();
					return false;
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "Error: Gi?? ph???i nh???p s???", "L???i",
						JOptionPane.ERROR_MESSAGE);
				txtGiaMax.setText("");
				txtGiaMax.requestFocus();
				return false;
			}
		}
		if (txtgiaMin.trim().length() > 0 && txtgiaMax.trim().length() > 0) {
			double min = Double.parseDouble(txtgiaMin);
			double max = Double.parseDouble(txtgiaMax);
			if (max < min) {
				JOptionPane.showMessageDialog(this, "Gi?? MAX kh??ng ???????c b?? h??n gi?? MIN", "L???i",
						JOptionPane.ERROR_MESSAGE);
				txtGiaMax.requestFocus();
				return false;
			}
		}
		return true;
	}

	public static void docDuLieuVaoCmbTenPhong() {
		List<PhongHat> listPhong = phong_dao.getTatCaPhongHatTonTai();
		cmbTenPhong.addItem("");
		for (PhongHat ph : listPhong) {
			cmbTenPhong.addItem(ph.getTenPhong());
		}
	}
	public static void docDuLieuVaoCmbMaPhong() {
		List<PhongHat> listPhong = phong_dao.getTatCaPhongHatTonTai();
		cmbMaPhong.addItem("");
		for (PhongHat ph : listPhong) {
			cmbMaPhong.addItem(ph.getMaPhong());
		}
	}

	public static void xoaHetDL() {
		DefaultTableModel dm = (DefaultTableModel) table.getModel();
		dm.setRowCount(0);
	}

	public static void docDuLieuDatabaseVaoTable() {
		List<PhongHat> list = phong_dao.getTatCaPhongHatTonTai();
		DecimalFormat df = new DecimalFormat("#,##0.0");
		for (PhongHat ph : list) {
			tableModel.addRow(new Object[] { ph.getMaPhong(), ph.getTenPhong(), df.format(ph.getGiaPhong()),
					ph.getLoaiPhong(), ph.isTrangThai() == false ? "Tr???ng" : "???? ?????t" });
		}
	}
	public boolean xuatExcel(String filePath) {
		try {
			FileOutputStream fileOut = new FileOutputStream(filePath);
			// T???o sheet Danh s??ch kh??ch h??ng
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet worksheet = workbook.createSheet("DANH S??CH PH??NG H??T");

			HSSFRow row;
			HSSFCell cell;

			// D??ng 1 t??n
			cell = worksheet.createRow(1).createCell(1);

			HSSFFont newFont = cell.getSheet().getWorkbook().createFont();
			newFont.setBold(true);
			newFont.setFontHeightInPoints((short) 13);
			CellStyle styleTenDanhSach = worksheet.getWorkbook().createCellStyle();
			styleTenDanhSach.setAlignment(HorizontalAlignment.CENTER);
			styleTenDanhSach.setFont(newFont);

			cell.setCellValue("DANH S??CH PH??NG H??T");
			cell.setCellStyle(styleTenDanhSach);

			String[] header = { "STT", "M?? ph??ng", "T??n ph??ng", "Gi??/1h", "Lo???i ph??ng", "Tr???ng th??i" };
			worksheet.addMergedRegion(new CellRangeAddress(1, 1, 1, header.length));

			// D??ng 2 ng?????i l???p
			row = worksheet.createRow(2);

			cell = row.createCell(1);
			cell.setCellValue("Ng?????i l???p:");
			cell = row.createCell(2);

			NhanVien nv = traPhong_dao.getNhanVienSuDung(FrameDangNhap.getTaiKhoan());
			if(nv == null)
				cell.setCellValue("Ch??? qu??n");
			else 
				cell.setCellValue(nv.getTenNV());
			worksheet.addMergedRegion(new CellRangeAddress(2, 2, 2, 3));

			// D??ng 3 ng??y l???p
			row = worksheet.createRow(3);
			cell = row.createCell(1);
			cell.setCellValue("Ng??y l???p:");
			cell = row.createCell(2);
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			cell.setCellValue(df.format(new Date()));
			worksheet.addMergedRegion(new CellRangeAddress(3, 3, 2, 3));

			// D??ng 4 t??n c??c c???t
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

			// Ghi d??? li???u v??o b???ng
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
		if (tableModel.getValueAt(r, 4).toString().trim().equals("???? ?????t")) {
			JOptionPane.showMessageDialog(this, "Kh??ng ???????c c???p nh???t ph??ng n??y v?? c?? ng?????i ??ang s??? d???ng ph??ng!", "L???i",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (e.getClickCount() == 2 && r != -1) {
			new FormCapNhatPhong().setVisible(true);
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
