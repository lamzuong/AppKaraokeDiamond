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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import dao.KhachHang_DAO;
import dao.ThongKeKhachHang_DAO;
import dao.TraPhong_DAO;
import entity.HoaDon;
import entity.KhachHang;
import entity.NhanVien;

public class FrameThongKeKhachHang extends JFrame implements ActionListener, MouseListener {
	private JComboBox<String> cmbTieuChi;
	private JButton btnTimKiem;
	private JButton btnLamMoi;
	private DefaultTableModel tableModel;
	private JTable table;

	private ThongKeKhachHang_DAO thongKeKhachHang_DAO;
	private KhachHang_DAO khachHang_dao;
	private JRadioButton radHomNay;
	private JRadioButton radMotTuan;
	private JRadioButton radMotThang;
	private JRadioButton radLuaChonKhac;
	private JRadioButton radTangDan;
	private JRadioButton radGiamDan;
	private JButton btnXuatExcel;
	private JButton btnSoLanDenQuan;
	private JComboBox<String> cmbMaKH;
	private JDateChooser dateNgayMin;
	private JDateChooser dateNgayMax;
	private TraPhong_DAO traPhong_DAO;

	public JPanel createPanelKhachHang() {
		// khởi tạo kết nối đến CSDL
		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		thongKeKhachHang_DAO = new ThongKeKhachHang_DAO();
		khachHang_dao = new KhachHang_DAO();
		traPhong_DAO = new TraPhong_DAO();
		// ------------------------------
		Toolkit toolkit = this.getToolkit(); /* Lấy độ phân giải màn hình */
		Dimension d = toolkit.getScreenSize();

		JPanel pnlContentPane = new JPanel();
		pnlContentPane.setBackground(Color.WHITE);
		pnlContentPane.setLayout(null);
		setContentPane(pnlContentPane);
		// Chức năng
		btnSoLanDenQuan = new JButton("XEM SỐ LẦN ĐẾN QUÁN", new ImageIcon("image/thongtin.png"));
		btnSoLanDenQuan.setBounds((int) (d.getWidth() - 670), 15, 260, 45);
		btnSoLanDenQuan.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnSoLanDenQuan.setBackground(new Color(79, 173, 84));
		btnSoLanDenQuan.setForeground(Color.WHITE);
		btnSoLanDenQuan.setFocusPainted(false);
		pnlContentPane.add(btnSoLanDenQuan);


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
		dateNgayMin = new JDateChooser();
		dateNgayMin.setDateFormatString("yyyy-MM-dd");
		dateNgayMin.setBounds(75, 175, 100, 30);
		dateNgayMin.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlTimKiem.add(dateNgayMin);

		JLabel lblNgayMax = new JLabel("Đến: ");
		lblNgayMax.setBounds(30, 215, 120, 30);
		lblNgayMax.setFont(new Font("Arial", Font.PLAIN, 15));
		pnlTimKiem.add(lblNgayMax);
		dateNgayMax = new JDateChooser();
		dateNgayMax.setDateFormatString("yyyy-MM-dd");
		dateNgayMax.setBounds(75, 215, 100, 30);
		dateNgayMax.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlTimKiem.add(dateNgayMax);

		JLabel lblMaKH = new JLabel("MÃ KHÁCH HÀNG:", SwingConstants.CENTER);
		lblMaKH.setOpaque(true);
		lblMaKH.setBackground(new Color(0, 148, 224));
		lblMaKH.setBounds(1, 265, 193, 30);
		lblMaKH.setForeground(Color.WHITE);
		lblMaKH.setFont(new Font("Arial", Font.BOLD, 15));
		pnlTimKiem.add(lblMaKH);
		cmbMaKH = new JComboBox<String>();
		cmbMaKH.setBounds(12, 310, 170, 30);
		cmbMaKH.setBackground(Color.WHITE);
		cmbMaKH.setEditable(true);
		cmbMaKH.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		docDuLieuVaoCmbMaKH();
		AutoCompleteDecorator.decorate(cmbMaKH);
		pnlTimKiem.add(cmbMaKH);
		cmbMaKH.setMaximumRowCount(1);

		JLabel lblSX = new JLabel("<html><div style='text-align: center;'>SẮP XẾP THEO: </div></html>",
				SwingConstants.CENTER);
		lblSX.setOpaque(true);
		lblSX.setBackground(new Color(0, 148, 224));
		lblSX.setBounds(1, 355, 193, 30);
		lblSX.setForeground(Color.WHITE);
		lblSX.setFont(new Font("Arial", Font.BOLD, 15));
		pnlTimKiem.add(lblSX);
		String[] loai = { "Mã khách hàng", "Tên khách hàng", "Số lần đặt" };
		cmbTieuChi = new JComboBox<String>(loai);
		cmbTieuChi.setBounds(12, 400, 170, 30);
		cmbTieuChi.setBackground(Color.WHITE);
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
				BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "DANH SÁCH KHÁCH HÀNG: "));
		pnlDanhSach.setBounds(230, 75, (int) (d.getWidth() - 455), (int) (d.getHeight() - 205));
		pnlDanhSach.setBackground(Color.WHITE);
		pnlDanhSach.setLayout(new GridLayout(1, 0, 0, 0));
		pnlContentPane.add(pnlDanhSach);

		String[] header = { "Mã khách hàng", "Tên khách hàng", "Số điện thoại", "Lần cuối cùng đến quán",
				"Số lần đặt" };
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
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(DefaultTableCellRenderer.RIGHT);
		table.getColumn("Số lần đặt").setCellRenderer(rightRenderer);
		
		btnLamMoi.addActionListener(this);
		btnTimKiem.addActionListener(this);
		btnSoLanDenQuan.addActionListener(this);
		btnXuatExcel.addActionListener(this);
		radHomNay.addActionListener(this);
		radMotTuan.addActionListener(this);
		radMotThang.addActionListener(this);
		radLuaChonKhac.addActionListener(this);

		table.addMouseListener(this);

		xoaHetDL();
		List<KhachHang> listKH = thongKeKhachHang_DAO.getKHHomNay();
		docDuLieuDatabaseVaoTable(listKH);
		table.setDefaultEditor(Object.class, null);
		table.getTableHeader().setReorderingAllowed(false);
		setEditableJDateChooder(false);
		cmbMaKH.setSelectedIndex(-1);

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
			setEditableJDateChooder(false);
			radHomNay.setSelected(true);
			cmbTieuChi.setSelectedIndex(0);
			cmbMaKH.setSelectedIndex(-1);
			xoaHetDL();
			List<KhachHang> list = thongKeKhachHang_DAO.getKHHomNay();
			docDuLieuDatabaseVaoTable(list);
			table.clearSelection();
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

		if (o.equals(btnSoLanDenQuan)) {
			int row = table.getSelectedRow();
			if (row != -1) {
				new FrameNgayDatNgayTra(table.getValueAt(row, 0).toString().trim()).setVisible(true);
			} else {
				JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng cần xem", "Lỗi", JOptionPane.ERROR_MESSAGE);
			}
		}

		if (o.equals(btnTimKiem)) {
			List<KhachHang> listKH = new ArrayList<KhachHang>();
			xoaHetDL();
			if (radHomNay.isSelected()) {
				listKH = thongKeKhachHang_DAO.getKHHomNay();
			}
			if (radMotTuan.isSelected()) {
				listKH = thongKeKhachHang_DAO.getKHMotTuan();
			}
			if (radMotThang.isSelected()) {
				listKH = thongKeKhachHang_DAO.getKHMotThang();
			}
			if (radLuaChonKhac.isSelected()) {
				Date denNgay = dateNgayMax.getDate();
				Date tuNgay = dateNgayMin.getDate();
				if(tuNgay != null && denNgay != null) {
					Date min = new Date(tuNgay.getYear(), tuNgay.getMonth(), tuNgay.getDate());
					Date max = new Date(denNgay.getYear(), denNgay.getMonth(), denNgay.getDate());
					if(min.after(max)) {
						JOptionPane.showMessageDialog(this, "Ngày MIN phải nhỏ hơn ngày MAX", "Lỗi",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				listKH = thongKeKhachHang_DAO.getKHTrongKhoangThoiGian(dateNgayMin.getDate(), dateNgayMax.getDate());
			}
			if (cmbMaKH.getSelectedIndex() != 0 && cmbMaKH.getSelectedIndex() != -1) {
				List<KhachHang> listTemp = new ArrayList<KhachHang>();
				for (KhachHang kh : listKH) {
					listTemp.add(kh);
				}
				listKH.clear();
				for (KhachHang kh : listTemp) {
					if (kh.getMaKH().trim().contains(cmbMaKH.getSelectedItem().toString().trim())) {
						listKH.add(kh);
					}
				}
			}
			if (listKH.size() == 0) {
				JOptionPane.showMessageDialog(this, "Không có khách hàng phù hợp với tiêu chí", "Lỗi",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			Map<KhachHang, Integer> mapKH = new HashMap<KhachHang, Integer>();
			for (KhachHang kh : listKH) {
				int soLanDat = thongKeKhachHang_DAO.getTatCaHoaDonTheoMaKH(kh.getMaKH()).size();
				mapKH.put(kh, soLanDat);
			}

			List<Map.Entry<KhachHang, Integer>> listCanSapXep = new ArrayList<Map.Entry<KhachHang, Integer>>();
			listCanSapXep.addAll(mapKH.entrySet());

			// Thứ tự xếp xếp 0 là tăng dần, 1 là giảm dần
			int thuTu = 1;
			if (radTangDan.isSelected())
				thuTu = 0;
			// Tiêu chí sắp xếp
			int tieuChi = cmbTieuChi.getSelectedIndex();

			if (tieuChi == 0)
				sapXepTheoMa(listCanSapXep, thuTu);
			if (tieuChi == 1)
				sapXepTheoTen(listCanSapXep, thuTu);
			if (tieuChi == 2)
				sapXepTheoSoLanDat(listCanSapXep, thuTu);

			SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			for (Entry<KhachHang, Integer> entry : listCanSapXep) {
				String maKH = entry.getKey().getMaKH();
				String tenKH = entry.getKey().getTenKH();
				String sdt = entry.getKey().getSoDT();
				Date thoiGianDatPhongCuoi = thongKeKhachHang_DAO.getHoaDonCuoiCuaKhachHang(entry.getKey().getMaKH())
						.getThoigianDatPhong();
				int soLan = entry.getValue();
				tableModel.addRow(new Object[] { maKH, tenKH, sdt, dt.format(thoiGianDatPhongCuoi), soLan });
			}
		}
	}

	public void sapXepTheoMa(List<Map.Entry<KhachHang, Integer>> list, int theoThuTu) {
		Collections.sort(list, new Comparator<Map.Entry<KhachHang, Integer>>() {
			@Override
			public int compare(Map.Entry<KhachHang, Integer> o1, Map.Entry<KhachHang, Integer> o2) {
				if (theoThuTu == 0)
					return o1.getKey().getMaKH().trim().compareToIgnoreCase(o2.getKey().getMaKH().trim());
				return o2.getKey().getMaKH().trim().compareToIgnoreCase(o1.getKey().getMaKH().trim());

			}
		});
	}

	public void sapXepTheoTen(List<Map.Entry<KhachHang, Integer>> list, int theoThuTu) {
		Collections.sort(list, new Comparator<Map.Entry<KhachHang, Integer>>() {
			@Override
			public int compare(Map.Entry<KhachHang, Integer> o1, Map.Entry<KhachHang, Integer> o2) {
				if (theoThuTu == 0)
					return o1.getKey().getTenKH().trim().compareToIgnoreCase(o2.getKey().getTenKH().trim());
				return o2.getKey().getTenKH().trim().compareToIgnoreCase(o1.getKey().getTenKH().trim());

			}
		});
	}

	public void sapXepTheoSoLanDat(List<Map.Entry<KhachHang, Integer>> list, int theoThuTu) {
		Collections.sort(list, new Comparator<Map.Entry<KhachHang, Integer>>() {
			@Override
			public int compare(Map.Entry<KhachHang, Integer> o1, Map.Entry<KhachHang, Integer> o2) {
				if (theoThuTu == 0)
					return o1.getValue().compareTo(o2.getValue());
				return o2.getValue().compareTo(o1.getValue());
			}
		});
	}

	public void docDuLieuDatabaseVaoTable(List<KhachHang> list) {
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		for (KhachHang khachHang : list) {
			List<HoaDon> hDCuaKhach = thongKeKhachHang_DAO.getTatCaHoaDonTheoMaKH(khachHang.getMaKH());
			Date thoiGianDatPhongCuoi = thongKeKhachHang_DAO.getHoaDonCuoiCuaKhachHang(khachHang.getMaKH())
					.getThoigianDatPhong();
			int soLanDat = hDCuaKhach.size();
			tableModel.addRow(new Object[] { khachHang.getMaKH(), khachHang.getTenKH(), khachHang.getSoDT(),
					dt.format(thoiGianDatPhongCuoi), soLanDat });
		}
	}

	public void xoaHetDL() {
		DefaultTableModel dm = (DefaultTableModel) table.getModel();
		dm.setRowCount(0);
	}
	public void docDuLieuVaoCmbMaKH() {
		List<KhachHang> listKH = khachHang_dao.getTatCaKhachHang();
		for (KhachHang kh : listKH) {
			cmbMaKH.addItem(kh.getMaKH().trim());
		}
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
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getClickCount() == 2 && table.getSelectedRow() != -1) {
			int row = table.getSelectedRow();
			new FrameNgayDatNgayTra(table.getValueAt(row, 0).toString().trim()).setVisible(true);
		}
	}

	public void setEditableJDateChooder(boolean trangThai) {
		dateNgayMax.setDate(null);
		dateNgayMin.setDate(null);
		dateNgayMax.setEnabled(trangThai);
		dateNgayMin.setEnabled(trangThai);
	}

	public boolean xuatExcel(String filePath) {
		try {
			FileOutputStream fileOut = new FileOutputStream(filePath);
			// Tạo sheet Danh sách khách hàng
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet worksheet = workbook.createSheet("THỐNG KÊ SỐ LẦN KHÁCH ĐẾN");

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

			cell.setCellValue("THỐNG KÊ SỐ LẦN KHÁCH ĐẾN");
			cell.setCellStyle(styleTenDanhSach);

			String[] header = { "STT", "Mã khách hàng", "Tên khách hàng", "Số điện thoại", "Lần cuối cùng đến quán",
					"Số lần đặt" };
			for (int i = 1; i < header.length + 1; i++) {
				worksheet.autoSizeColumn(i);
			}
			worksheet.addMergedRegion(new CellRangeAddress(1, 1, 1, header.length));

			// Dòng 2 người lập
			row = worksheet.createRow(2);

			cell = row.createCell(1);
			cell.setCellValue("Người lập:");
			cell = row.createCell(2);

			NhanVien nv = traPhong_DAO.getNhanVienSuDung(FrameDangNhap.getTaiKhoan());
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
							if (j == header.length - 1) {
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

}
