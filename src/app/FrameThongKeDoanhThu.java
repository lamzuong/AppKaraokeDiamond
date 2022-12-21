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
import java.text.DateFormat;
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

import com.toedter.calendar.JDateChooser;

import connectDB.ConnectDB;
import dao.HoaDon_DAO;
import dao.TraPhong_DAO;
import entity.HoaDon;
import entity.NhanVien;

public class FrameThongKeDoanhThu extends JFrame implements ActionListener, MouseListener {
	private JComboBox<String> cmbTieuChi;
	private JButton btnTimKiem;
	private JButton btnLamMoi;
	private DefaultTableModel tableModel;
	private JTable table;
	private HoaDon_DAO hoadon_dao;
	private JTextField txtDoanhThu;
	private JRadioButton radHomNay;
	private JRadioButton radMotTuan;
	private JRadioButton radMotThang;
	private JRadioButton radLuaChonKhac;
	private JRadioButton radTangDan;
	private JRadioButton radGiamDan;
	private JButton btnXuatExcel;
	private JButton btnHoaDon;
	private JButton btnDVDaBan;
	private JDateChooser txtNgayMin;
	private JDateChooser txtNgayMax;
	private JComboBox<String> cmbMaHD;
	private TraPhong_DAO traPhong_dao;

	public JPanel createPanelDoanhThu() {
		// khởi tạo kết nối đến CSDL
		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		hoadon_dao = new HoaDon_DAO();
		traPhong_dao = new TraPhong_DAO();
		// ----------------
		Toolkit toolkit = this.getToolkit(); /* Lấy độ phân giải màn hình */
		Dimension d = toolkit.getScreenSize();

		JPanel pnlContentPane = new JPanel();
		pnlContentPane.setBackground(Color.WHITE);
		pnlContentPane.setLayout(null);
		setContentPane(pnlContentPane);
		// Chức năng
		btnHoaDon = new JButton("XEM HÓA ĐƠN", new ImageIcon("image/hoadon.png"));
		btnHoaDon.setBounds((int) (d.getWidth() - 850), 15, 190, 45);
		btnHoaDon.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnHoaDon.setBackground(new Color(79, 173, 84));
		btnHoaDon.setForeground(Color.WHITE);
		btnHoaDon.setFocusPainted(false);
		pnlContentPane.add(btnHoaDon);

		btnDVDaBan = new JButton("DỊCH VỤ ĐÃ BÁN", new ImageIcon("image/dichvu.png"));
		btnDVDaBan.setBounds((int) (d.getWidth() - 650), 15, 240, 45);
		btnDVDaBan.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnDVDaBan.setBackground(new Color(79, 173, 84));
		btnDVDaBan.setForeground(Color.WHITE);
		btnDVDaBan.setFocusPainted(false);
		pnlContentPane.add(btnDVDaBan);

		btnXuatExcel = new JButton("XUẤT EXCEL", new ImageIcon("image/xuatexcel.png"));
		btnXuatExcel.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnXuatExcel.setBackground(new Color(79, 173, 84));
		btnXuatExcel.setForeground(Color.WHITE);
		btnXuatExcel.setBounds((int) (d.getWidth() - 400), 15, 165, 45);
		btnXuatExcel.setFocusPainted(false);
		pnlContentPane.add(btnXuatExcel);
		/*
		 * Lọc
		 */
		JPanel pnlTimKiem = new JPanel();
		pnlTimKiem.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), ""));
		pnlTimKiem.setBounds(20, 15, 195, (int) (d.getHeight() - 149));
		pnlTimKiem.setBackground(Color.WHITE);
		pnlTimKiem.setLayout(null);
		pnlContentPane.add(pnlTimKiem);

		JLabel lblThongKe = new JLabel("<html><div style='text-align: center;'>THỐNG KÊ THEO: </div></html>",
				SwingConstants.CENTER);
		lblThongKe.setOpaque(true);
		lblThongKe.setBackground(new Color(0, 148, 224));
		lblThongKe.setBounds(1, 10, 193, 30);
		lblThongKe.setForeground(Color.WHITE);
		lblThongKe.setFont(new Font("Arial", Font.BOLD, 15));
		pnlTimKiem.add(lblThongKe);
		radHomNay = new JRadioButton("Hôm nay");
		radHomNay.setBounds(20, 50, 100, 30);
		radHomNay.setBackground(Color.WHITE);
		radHomNay.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		radHomNay.setSelected(true);
		radMotTuan = new JRadioButton("1 tuần");
		radMotTuan.setBounds(20, 80, 100, 30);
		radMotTuan.setBackground(Color.WHITE);
		radMotTuan.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		radMotThang = new JRadioButton("1 tháng");
		radMotThang.setBounds(20, 110, 100, 30);
		radMotThang.setBackground(Color.WHITE);
		radMotThang.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		radLuaChonKhac = new JRadioButton("Lựa chọn khác");
		radLuaChonKhac.setBounds(20, 140, 120, 30);
		radLuaChonKhac.setBackground(Color.WHITE);
		radLuaChonKhac.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		ButtonGroup bg = new ButtonGroup();
		bg.add(radHomNay);
		bg.add(radMotTuan);
		bg.add(radMotThang);
		bg.add(radLuaChonKhac);
		pnlTimKiem.add(radHomNay);
		pnlTimKiem.add(radMotTuan);
		pnlTimKiem.add(radMotThang);
		pnlTimKiem.add(radLuaChonKhac);

		JLabel lblNgayMin = new JLabel("Từ: ");
		lblNgayMin.setBounds(30, 175, 120, 30);
		lblNgayMin.setFont(new Font("Arial", Font.PLAIN, 15));
		pnlTimKiem.add(lblNgayMin);
		txtNgayMin = new JDateChooser();
		txtNgayMin.setDateFormatString("yyyy-MM-dd");
		txtNgayMin.setBounds(75, 175, 100, 30);
		txtNgayMin.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlTimKiem.add(txtNgayMin);

		JLabel lblNgayMax = new JLabel("Đến: ");
		lblNgayMax.setBounds(30, 215, 120, 30);
		lblNgayMax.setFont(new Font("Arial", Font.PLAIN, 15));
		pnlTimKiem.add(lblNgayMax);
		txtNgayMax = new JDateChooser();
		txtNgayMax.setDateFormatString("yyyy-MM-dd");
		txtNgayMax.setBounds(75, 215, 100, 30);
		txtNgayMax.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlTimKiem.add(txtNgayMax);

		JLabel lblMaHD = new JLabel("MÃ HÓA ĐƠN:", SwingConstants.CENTER);
		lblMaHD.setOpaque(true);
		lblMaHD.setBackground(new Color(0, 148, 224));
		lblMaHD.setBounds(1, 265, 193, 30);
		lblMaHD.setForeground(Color.WHITE);
		lblMaHD.setFont(new Font("Arial", Font.BOLD, 15));
		pnlTimKiem.add(lblMaHD);
		cmbMaHD = new JComboBox<String>();
		cmbMaHD.setBounds(12, 310, 170, 30);
		cmbMaHD.setBackground(Color.WHITE);
		cmbMaHD.setEditable(true);
		cmbMaHD.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		docDuLieuVaoCmbMaHD();
		AutoCompleteDecorator.decorate(cmbMaHD);
		pnlTimKiem.add(cmbMaHD);
		cmbMaHD.setMaximumRowCount(1);

		JLabel lblSX = new JLabel("SẮP XẾP THEO:", SwingConstants.CENTER);
		lblSX.setOpaque(true);
		lblSX.setBackground(new Color(0, 148, 224));
		lblSX.setBounds(1, 355, 193, 30);
		lblSX.setForeground(Color.WHITE);
		lblSX.setFont(new Font("Arial", Font.BOLD, 15));
		pnlTimKiem.add(lblSX);
		String[] loai = { "Mã hóa đơn", "Tổng số tiền thanh toán", "Tên khách hàng", "Thời gian đặt", "Thời gian trả" };
		cmbTieuChi = new JComboBox<String>(loai);
		cmbTieuChi.setBounds(12, 400, 170, 30);
		cmbTieuChi.setFocusable(false);
		cmbTieuChi.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlTimKiem.add(cmbTieuChi);

		radTangDan = new JRadioButton("Tăng dần");
		radTangDan.setBounds(15, 440, 80, 30);
		radTangDan.setBackground(Color.WHITE);
		radTangDan.setFont(new Font("Arial", Font.ITALIC, 13));
		radTangDan.setSelected(true);
		radGiamDan = new JRadioButton("Giảm dần");
		radGiamDan.setBounds(95, 440, 90, 30);
		radGiamDan.setBackground(Color.WHITE);
		radGiamDan.setFont(new Font("Arial", Font.ITALIC, 13));
		ButtonGroup bg2 = new ButtonGroup();
		bg2.add(radTangDan);
		bg2.add(radGiamDan);
		pnlTimKiem.add(radTangDan);
		pnlTimKiem.add(radGiamDan);

		btnTimKiem = new JButton("TÌM KIẾM", new ImageIcon("image/timkiem.png"));
		btnTimKiem.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnTimKiem.setBackground(new Color(0, 148, 224));
		btnTimKiem.setBounds(13, 490, 170, 45);
		btnTimKiem.setForeground(Color.WHITE);
		btnTimKiem.setFocusPainted(false);
		pnlTimKiem.add(btnTimKiem);

		btnLamMoi = new JButton("LÀM MỚI", new ImageIcon("image/lammoi.png"));
		btnLamMoi.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnLamMoi.setBackground(new Color(0, 148, 224));
		btnLamMoi.setBounds(13, 550, 170, 45);
		btnLamMoi.setForeground(Color.WHITE);
		btnLamMoi.setFocusPainted(false);
		pnlTimKiem.add(btnLamMoi);

		/*
		 * Danh sách hóa đơn
		 */
		JPanel pnlDanhSach = new JPanel();
		pnlDanhSach.setBorder(
				BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "DANH SÁCH HÓA ĐƠN: "));
		pnlDanhSach.setBounds(230, 75, (int) (d.getWidth() - 455), (int) (d.getHeight() - 250));
		pnlDanhSach.setBackground(Color.WHITE);
		pnlDanhSach.setLayout(new GridLayout(1, 0, 0, 0));
		pnlContentPane.add(pnlDanhSach);

		String[] header = { "Mã hóa đơn", "Tên nhân viên", "Tên khách hàng", "Tên phòng", "Thời gian đặt",
				"Thời gian trả", "Số giờ thuê", "Tiền phòng", "Tiền dịch vụ", "Thanh toán" };
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
		pnlDanhSach.add(new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));

		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(120);
		table.getColumnModel().getColumn(1).setPreferredWidth(165);
		table.getColumnModel().getColumn(2).setPreferredWidth(165);
		table.getColumnModel().getColumn(3).setPreferredWidth(140);
		table.getColumnModel().getColumn(4).setPreferredWidth(155);
		table.getColumnModel().getColumn(5).setPreferredWidth(155);
		table.getColumnModel().getColumn(6).setPreferredWidth(80);
		table.getColumnModel().getColumn(7).setPreferredWidth(120);
		table.getColumnModel().getColumn(8).setPreferredWidth(110);
		table.getColumnModel().getColumn(9).setPreferredWidth(90);

		/*
		 * Tổng doanh thu
		 */
		JLabel lblTongDT = new JLabel("<html>TỔNG DOANH THU: </html>");
		lblTongDT.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblTongDT.setBounds(600, (int) (d.getHeight() - 170), 500, 50);
		pnlContentPane.add(lblTongDT);
		txtDoanhThu = new JTextField("0.0 VNĐ");
		txtDoanhThu.setFont(new Font("Times New Roman", Font.BOLD, 25));
		txtDoanhThu.setBounds(850, (int) (d.getHeight() - 170), 400, 50);
		txtDoanhThu.setEditable(false);
		txtDoanhThu.setBorder(BorderFactory.createEmptyBorder());
		txtDoanhThu.setBackground(Color.WHITE);
		pnlContentPane.add(txtDoanhThu);

		btnLamMoi.addActionListener(this);
		btnTimKiem.addActionListener(this);
		btnDVDaBan.addActionListener(this);
		btnHoaDon.addActionListener(this);
		btnXuatExcel.addActionListener(this);
		radHomNay.addActionListener(this);
		radMotTuan.addActionListener(this);
		radMotThang.addActionListener(this);
		radLuaChonKhac.addActionListener(this);
		table.addMouseListener(this);

		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(DefaultTableCellRenderer.RIGHT);
		table.getColumn("Số giờ thuê").setCellRenderer(rightRenderer);
		table.getColumn("Tiền phòng").setCellRenderer(rightRenderer);
		table.getColumn("Tiền dịch vụ").setCellRenderer(rightRenderer);
		table.getColumn("Thanh toán").setCellRenderer(rightRenderer);
		table.setDefaultEditor(Object.class, null);
		table.getTableHeader().setReorderingAllowed(false);

		setEditableJDateChooder(false);
		List<HoaDon> list = hoadon_dao.getHoaDonHomNay();
		docDuLieuDatabaseVaoTableTheoList(list);
		return pnlContentPane;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(radHomNay)) {
			setEditableJDateChooder(false);
		}
		if (o.equals(radMotTuan)) {
			setEditableJDateChooder(false);
		}
		if (o.equals(radMotThang)) {
			setEditableJDateChooder(false);
		}
		if (o.equals(radLuaChonKhac)) {
			setEditableJDateChooder(true);
		}
		if (o.equals(btnLamMoi)) {
			radHomNay.setSelected(true);
			txtNgayMin.setDate(null);
			txtNgayMax.setDate(null);
			cmbMaHD.setSelectedIndex(0);
			cmbTieuChi.setSelectedIndex(0);
			radTangDan.setSelected(true);
			txtDoanhThu.setText("0.0 VNĐ");
			xoaHetDL();
			List<HoaDon> list = hoadon_dao.getHoaDonHomNay();
			docDuLieuDatabaseVaoTableTheoList(list);
		}
		if (o.equals(btnTimKiem)) {
			Date ngayMin = txtNgayMin.getDate();
			Date ngayMax = txtNgayMax.getDate();
			String maHD = cmbMaHD.getSelectedItem().toString().trim();
			int tieuChi = cmbTieuChi.getSelectedIndex();
			boolean tangDan = radTangDan.isSelected();

			xoaHetDL();
			List<HoaDon> list = new ArrayList<HoaDon>();
			if (radHomNay.isSelected()) {
				list = hoadon_dao.getHoaDonHomNay();
			} else if (radMotTuan.isSelected()) {
				list = hoadon_dao.getHoaDon1Tuan();
			} else if (radMotThang.isSelected()) {
				list = hoadon_dao.getHoaDon1Thang();
			} else if (radLuaChonKhac.isSelected()) {
				Date denNgay = txtNgayMax.getDate();
				Date tuNgay = txtNgayMin.getDate();
				if (tuNgay != null && denNgay != null) {
					Date min = new Date(tuNgay.getYear(), tuNgay.getMonth(), tuNgay.getDate());
					Date max = new Date(denNgay.getYear(), denNgay.getMonth(), denNgay.getDate());
					if (min.after(max)) {
						JOptionPane.showMessageDialog(this, "Ngày MIN phải nhỏ hơn ngày MAX", "Lỗi",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				list = hoadon_dao.getTatCaHoaDon();
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				if (ngayMin != null) {
					List<HoaDon> listTemp = new ArrayList<HoaDon>();
					for (HoaDon hd : list) {
						listTemp.add(hd);
					}
					list.clear();
					for (HoaDon hd : listTemp) {
						try {
							String output = df.format(hd.getThoigianTraPhong());
							Date date = new SimpleDateFormat("yyyy-MM-dd").parse(output);
							if (date.compareTo(ngayMin) >= 0) {
								list.add(hd);
							}
						} catch (ParseException e1) {
							e1.printStackTrace();
						}
					}
				}
				if (ngayMax != null) {
					List<HoaDon> listTemp = new ArrayList<HoaDon>();
					for (HoaDon hd : list) {
						listTemp.add(hd);
					}
					list.clear();
					for (HoaDon hd : listTemp) {
						try {
							String output = df.format(hd.getThoigianTraPhong());
							Date date = new SimpleDateFormat("yyyy-MM-dd").parse(output);
							if (date.compareTo(ngayMax) <= 0)
								list.add(hd);
						} catch (ParseException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
			if (!maHD.trim().equals("")) {
				List<HoaDon> listTemp = new ArrayList<HoaDon>();
				for (HoaDon hd : list) {
					listTemp.add(hd);
				}
				list.clear();
				for (HoaDon hd : listTemp) {
					if (hd.getMaHD().trim().contains(maHD)) {
						list.add(hd);
					}
				}
			}
			if (list.size() == 0) {
				txtDoanhThu.setText("0.0 VNĐ");
				JOptionPane.showMessageDialog(this, "Không có hóa đơn nào phù hợp với tiêu chí", "Lỗi",
						JOptionPane.ERROR_MESSAGE);

				return;
			}
			if (tieuChi == 0)
				sapXepTheoMaHD(list, tangDan);
			else if (tieuChi == 1)
				sapXepTheoDoanhThu(list, tangDan);
			else if (tieuChi == 2)
				sapXepTheoTenKH(list, tangDan);
			else if (tieuChi == 3)
				sapXepTheoNgayDat(list, tangDan);
			else if (tieuChi == 4)
				sapXepTheoNgayTra(list, tangDan);

			DecimalFormat dfMoney = new DecimalFormat("#,##0.0");
			for (HoaDon hd : list) {
				tableModel.addRow(new Object[] { hd.getMaHD(), hd.getMaNV().getTenNV(), hd.getMaKH().getTenKH(),
						hd.getMaPhong().getTenPhong(), hd.getThoigianDatPhong(), hd.getThoigianTraPhong(),
						dfMoney.format(hd.getSoGioThue()), dfMoney.format(hd.getTienPhong()),
						dfMoney.format(hd.getTienDichVu()), dfMoney.format(hd.getTongTien()) });
			}
			/*
			 * Tính tổng tiền thanh toán
			 */
			int row = table.getRowCount();

			double total = 0;
			for (int i = 0; i < row; i++) {
				String stringTotal = "";
				String tongTienThanhToan = tableModel.getValueAt(i, 9).toString();
				String a[] = tongTienThanhToan.split(",");
				for (int j = 0; j < a.length; j++) {
					stringTotal += a[j];
				}
				total += Double.parseDouble(stringTotal);
			}
			if (total == 0)
				txtDoanhThu.setText("0.0 VNĐ");
			else
				txtDoanhThu.setText(dfMoney.format(total) + " VNĐ");
		}
		if (o.equals(btnDVDaBan)) {
			if (radHomNay.isSelected())
				new FrameDichVuDaBan("Hôm nay", null, null).setVisible(true);
			else if (radMotTuan.isSelected())
				new FrameDichVuDaBan("Một tuần", null, null).setVisible(true);
			else if (radMotThang.isSelected())
				new FrameDichVuDaBan("Một tháng", null, null).setVisible(true);
			else if (radLuaChonKhac.isSelected()) {
				if (txtNgayMax != null && txtNgayMin == null)
					new FrameDichVuDaBan("Khác", null, txtNgayMax.getDate()).setVisible(true);
				else if (txtNgayMax == null && txtNgayMin != null)
					new FrameDichVuDaBan("Khác", txtNgayMin.getDate(), null).setVisible(true);
				else if (txtNgayMax.getDate() != null && txtNgayMin.getDate() != null)
					new FrameDichVuDaBan("Khác", txtNgayMin.getDate(), txtNgayMax.getDate()).setVisible(true);
				else
					new FrameDichVuDaBan("Khác", null, null).setVisible(true);
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
		if (o.equals(btnHoaDon)) {
			int row = table.getSelectedRow();
			if (row != -1) {
				HoaDon hd = hoadon_dao.getHoaDonTheoMaHD(table.getValueAt(row, 0).toString().trim());
				new FrameHoaDonTinhTien(hd.getMaKH().getTenKH(), hd.getMaNV().getTenNV(), hd.getMaHD(),
						hd.getThoigianDatPhong(), hd.getThoigianTraPhong()).setVisible(true);
			} else
				JOptionPane.showMessageDialog(this, "Chọn hoá đơn cần xem!!", "Lỗi", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void sapXepTheoMaHD(List<HoaDon> list, boolean tangDan) {
		Collections.sort(list, new Comparator<HoaDon>() {
			@Override
			public int compare(HoaDon o1, HoaDon o2) {
				if (tangDan) {
					if (o1 != null && o2 != null)
						return o1.getMaHD().compareToIgnoreCase(o2.getMaHD());
					return 0;
				} else {
					if (o1 != null && o2 != null)
						return o2.getMaHD().compareToIgnoreCase(o1.getMaHD());
					return 0;
				}
			}
		});
	}

	public void sapXepTheoDoanhThu(List<HoaDon> list, boolean tangDan) {
		Collections.sort(list, new Comparator<HoaDon>() {
			@Override
			public int compare(HoaDon o1, HoaDon o2) {
				if (tangDan) {
					if (o1 != null && o2 != null)
						return Double.compare(o1.tinhTongTienThanhToan(), o2.tinhTongTienThanhToan());
					return 0;
				} else {
					if (o1 != null && o2 != null)
						return Double.compare(o2.tinhTongTienThanhToan(), o1.tinhTongTienThanhToan());
					return 0;
				}
			}
		});
	}

	public void sapXepTheoTenKH(List<HoaDon> list, boolean tangDan) {
		Collections.sort(list, new Comparator<HoaDon>() {
			@Override
			public int compare(HoaDon o1, HoaDon o2) {
				if (tangDan) {
					if (o1 != null && o2 != null)
						return o1.getMaKH().getTenKH().compareToIgnoreCase(o2.getMaKH().getTenKH());
					return 0;
				} else {
					if (o1 != null && o2 != null)
						return o2.getMaKH().getTenKH().compareToIgnoreCase(o1.getMaKH().getTenKH());
					return 0;
				}
			}
		});
	}

	public void sapXepTheoNgayDat(List<HoaDon> list, boolean tangDan) {
		Collections.sort(list, new Comparator<HoaDon>() {
			@Override
			public int compare(HoaDon o1, HoaDon o2) {
				if (tangDan) {
					if (o1 != null && o2 != null)
						return o1.getThoigianDatPhong().compareTo(o2.getThoigianDatPhong());
					return 0;
				} else {
					if (o1 != null && o2 != null)
						return o2.getThoigianDatPhong().compareTo(o1.getThoigianDatPhong());
					return 0;
				}
			}
		});
	}

	public void sapXepTheoNgayTra(List<HoaDon> list, boolean tangDan) {
		Collections.sort(list, new Comparator<HoaDon>() {
			@Override
			public int compare(HoaDon o1, HoaDon o2) {
				if (tangDan) {
					if (o1 != null && o2 != null)
						return o1.getThoigianTraPhong().compareTo(o2.getThoigianTraPhong());
					return 0;
				} else {
					if (o1 != null && o2 != null)
						return o2.getThoigianTraPhong().compareTo(o1.getThoigianTraPhong());
					return 0;
				}
			}
		});
	}

	private void xoaHetDL() {
		DefaultTableModel dm = (DefaultTableModel) table.getModel();
		dm.setRowCount(0);
	}

	public void docDuLieuDatabaseVaoTableTheoList(List<HoaDon> list) {
		DecimalFormat df = new DecimalFormat("#,##0.0");
		double tongDoanhThu = 0;
		for (HoaDon hd : list) {
			tableModel.addRow(new Object[] { hd.getMaHD(), hd.getMaNV().getTenNV(), hd.getMaKH().getTenKH(),
					hd.getMaPhong().getTenPhong(), hd.getThoigianDatPhong(), hd.getThoigianTraPhong(),
					df.format(hd.getSoGioThue()), df.format(hd.getTienPhong()), df.format(hd.getTienDichVu()),
					df.format(hd.getTongTien()) });
			tongDoanhThu += hd.getTienPhong();
		}
		if (tongDoanhThu == 0)
			txtDoanhThu.setText("0.0 VNĐ");
		else
			txtDoanhThu.setText(df.format(tongDoanhThu) + " VNĐ");
	}

	public void docDuLieuDatabaseVaoTable() {
		List<HoaDon> list = hoadon_dao.getTatCaHoaDon();
		DecimalFormat df = new DecimalFormat("#,##0.0");
		double tongDoanhThu = 0;
		for (HoaDon hd : list) {
			tableModel.addRow(new Object[] { hd.getMaHD(), hd.getMaNV().getTenNV(), hd.getMaKH().getTenKH(),
					hd.getMaPhong().getTenPhong(), hd.getThoigianDatPhong(), hd.getThoigianTraPhong(),
					df.format(hd.getSoGioThue()), df.format(hd.getTienPhong()), df.format(hd.getTienDichVu()),
					df.format(hd.getTongTien()) });
			tongDoanhThu += hd.getTienPhong();
		}
		if (tongDoanhThu == 0)
			txtDoanhThu.setText("0.0 VNĐ");
		else
			txtDoanhThu.setText(df.format(tongDoanhThu) + " VNĐ");
	}

	public void docDuLieuVaoCmbMaHD() {
		List<HoaDon> list = hoadon_dao.getTatCaHoaDon();
		cmbMaHD.addItem("");
		for (HoaDon hd : list) {
			cmbMaHD.addItem(hd.getMaHD());
		}
	}

	public void setEditableJDateChooder(boolean trangThai) {
		txtNgayMax.setEnabled(trangThai);
		txtNgayMin.setEnabled(trangThai);
	}

	public boolean xuatExcel(String filePath) {
		try {
			FileOutputStream fileOut = new FileOutputStream(filePath);
			// Tạo sheet Danh sách khách hàng
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet worksheet = workbook.createSheet("DANH SÁCH HÓA ĐƠN");

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

			cell.setCellValue("DANH SÁCH HÓA ĐƠN");
			cell.setCellStyle(styleTenDanhSach);

			String[] header = { "STT", "Mã hóa đơn", "Tên nhân viên", "Tên khách hàng", "Tên phòng", "Thời gian đặt",
					"Thời gian trả", "Số giờ thuê", "Tiền phòng", "Tiền dịch vụ", "Thanh toán" };
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
							if (j == header.length - 1 || j == header.length - 2 || j == header.length - 3
									|| j == header.length - 4) {
								String tien[] = tableModel.getValueAt(i, j - 1).toString().split(",");
								String tongTien = "";
								for (int t = 0; t < tien.length; t++)
									tongTien += tien[t];
								cell.setCellValue(Double.parseDouble(tongTien));
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
			int row5 = 4 + table.getRowCount() + 1;
			row = worksheet.createRow(row5);
			cell = row.createCell(7);
			cell.setCellStyle(styleTenDanhSach);
			worksheet.addMergedRegion(new CellRangeAddress(row5, row5, 7, 8));
			cell.setCellValue("TỔNG DOANH THU:");
			cell = row.createCell(9);
			worksheet.addMergedRegion(new CellRangeAddress(row5, row5, 9, 10));
			cell.setCellStyle(styleTenDanhSach);
			cell.setCellValue("" + txtDoanhThu.getText());

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
		int row = table.getSelectedRow();
		if (e.getClickCount() == 2 && table.getSelectedRow() != -1) {
			HoaDon hd = hoadon_dao.getHoaDonTheoMaHD(table.getValueAt(row, 0).toString().trim());
			new FrameHoaDonTinhTien(hd.getMaKH().getTenKH(), hd.getMaNV().getTenNV(), hd.getMaHD(),
					hd.getThoigianDatPhong(), hd.getThoigianTraPhong()).setVisible(true);
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
