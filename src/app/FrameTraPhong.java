package app;

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
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import connectDB.ConnectDB;
import dao.DatPhong_DAO;
import dao.PhongHat_DAO;
import dao.TraPhong_DAO;
import entity.HoaDon;
import entity.HoaDonDichVu;
import entity.NhanVien;
import entity.PhongHat;

public class FrameTraPhong extends JFrame implements ActionListener, MouseListener {

	public static JTable tableChonPhong;
	private static DefaultTableModel tableModelChonPhong;
	private JTextField txtTenPhong;
	private JTextField txtLoaiPhong;
	private JTextField txtGiaPhong;
	private JTextField txtTienDV;
	private JTextField txtSoGio;
	private JTextField txtTienPhong;
	private JTextField txtNhanVien;
	private JLabel lblTongTienThanhToan;
	private JButton btnThanhToan;
	private JTextField txtThoiGianDen;
	private JTextField txtThoiGianTra;
	private static PhongHat_DAO phongHat_dao;
	private TraPhong_DAO traPhong_dao;
	private static DatPhong_DAO datPhong_DAO;
	private Date thoiGianTraPhongDate;
	public static JComboBox<String> cmbChonPhong;
	private JLabel lblTenKH;
	private JTextField txtKH;
	private JButton btnTimPhong;
	private JButton btnInHoaDon;
	private JTextField txtThanhToan;

	public JPanel createPanelTraPhong() {

		// khởi tạo kết nối đến CSDL
		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		phongHat_dao = new PhongHat_DAO();
		traPhong_dao = new TraPhong_DAO();
		datPhong_DAO = new DatPhong_DAO();
		// ------------------------------

		setTitle("TRẢ PHÒNG");
		setSize(948, 440);
		setLocationRelativeTo(null);
		setResizable(false);

		Toolkit toolkit = this.getToolkit(); /* Lấy độ phân giải màn hình */
		Dimension d = toolkit.getScreenSize();

		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent evt) {
				new FrameDatPhong().setVisible(true);
			}
		});
		JPanel pnlContentPane = new JPanel();
		pnlContentPane.setBackground(Color.WHITE);
		pnlContentPane.setLayout(null);
		setContentPane(pnlContentPane);
		/*
		 * Chọn phòng trả
		 */
		JPanel pnlTraPhong = new JPanel();
		pnlTraPhong.setLayout(new GridLayout(1, 1));
		pnlTraPhong.setBounds(350, 10, (int) (d.getWidth() - 570), (int) (d.getHeight() - 90));
		pnlTraPhong.setBackground(Color.WHITE);
		pnlTraPhong.setBorder(
				BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "CHỌN PHÒNG CẦN TRẢ: "));
		pnlContentPane.add(pnlTraPhong);

		String[] header = { "Mã phòng", "Tên phòng", "Giá/1h", "Loại phòng", "Thời gian đặt phòng", "Tên khách hàng" };
		tableModelChonPhong = new DefaultTableModel(header, 0);
		tableChonPhong = new JTable(tableModelChonPhong) {
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
		tableChonPhong.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		tableChonPhong.setGridColor(getBackground());
		tableChonPhong.setRowHeight(tableChonPhong.getRowHeight() + 20);
		tableChonPhong.setSelectionBackground(new Color(255, 255, 128));
		JTableHeader tableHeader = tableChonPhong.getTableHeader();
		tableHeader.setBackground(new Color(219, 255, 255));
		tableHeader.setFont(new Font("Times New Roman", Font.BOLD, 15));
		tableHeader.setResizingAllowed(false);
		tableHeader.setReorderingAllowed(false);
		tableHeader.setPreferredSize(new Dimension(WIDTH, 30));

		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(DefaultTableCellRenderer.RIGHT);
		tableChonPhong.getColumn("Giá/1h").setCellRenderer(rightRenderer);
		pnlTraPhong.add(new JScrollPane(tableChonPhong));

		// Chọn phòng
		JPanel pnlChonPhong = new JPanel();
		pnlChonPhong.setLayout(null);
		pnlChonPhong.setBounds(20, 18, 315, 102);
		pnlChonPhong.setBackground(Color.WHITE);
		pnlChonPhong.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), ""));
		pnlContentPane.add(pnlChonPhong);

		JLabel lblChonPhong = new JLabel("NHẬP TÊN PHÒNG CẦN TÌM: ", SwingConstants.CENTER);
		lblChonPhong.setBounds(1, 10, 313, 30);
		lblChonPhong.setOpaque(true);
		lblChonPhong.setBackground(new Color(0, 148, 224));
		lblChonPhong.setForeground(Color.WHITE);
		lblChonPhong.setFont(new Font("Arial", Font.BOLD, 15));
		pnlChonPhong.add(lblChonPhong);
		cmbChonPhong = new JComboBox<String>();
		cmbChonPhong.setBounds(40, 53, 200, 32);
		cmbChonPhong.setBackground(Color.WHITE);
		cmbChonPhong.setEditable(true);
		cmbChonPhong.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		AutoCompleteDecorator.decorate(cmbChonPhong);
		pnlChonPhong.add(cmbChonPhong);

		btnTimPhong = new JButton("", new ImageIcon("image/timkiem.png"));
		btnTimPhong.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnTimPhong.setBounds(240, 53, 35, 32);
		btnTimPhong.setBackground(new Color(0, 148, 224));
		btnTimPhong.setFocusPainted(false);
		pnlChonPhong.add(btnTimPhong);

		/*
		 * Thông tin hóa đơn
		 */
		JPanel pnThongTinHoaDon = new JPanel();
		pnThongTinHoaDon.setLayout(null);

//		pnThongTinHoaDon.setBounds(20, 125, 315, (int) (d.getHeight() - 205));

		pnThongTinHoaDon.setBounds(20, 125, 315, (int) (d.getHeight() - 105));

		pnThongTinHoaDon.setBackground(Color.WHITE);
		pnThongTinHoaDon.setBorder(
				BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "THÔNG TIN HÓA ĐƠN: "));
		pnlContentPane.add(pnThongTinHoaDon);

		JLabel lblNhanVien = new JLabel("TÊN NHÂN VIÊN: ");
		lblNhanVien.setBounds(15, 37, 150, 25);
		lblNhanVien.setFont(new Font("Arial", Font.PLAIN, 13));
		pnThongTinHoaDon.add(lblNhanVien);
		txtNhanVien = new JTextField();
		txtNhanVien.setEditable(false);
		txtNhanVien.setBounds(145, 35, 150, 30);
		txtNhanVien.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		pnThongTinHoaDon.add(txtNhanVien);

		lblTenKH = new JLabel("TÊN KHÁCH HÀNG: ");
		lblTenKH.setBounds(15, 82, 150, 25);
		lblTenKH.setFont(new Font("Arial", Font.PLAIN, 13));
		pnThongTinHoaDon.add(lblTenKH);
		txtKH = new JTextField();
		txtKH.setEditable(false);
		txtKH.setBounds(145, 80, 150, 30);
		txtKH.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		pnThongTinHoaDon.add(txtKH);

		JLabel lblTenPhong = new JLabel("TÊN PHÒNG: ");
		lblTenPhong.setBounds(15, 127, 150, 25);
		lblTenPhong.setFont(new Font("Arial", Font.PLAIN, 13));
		pnThongTinHoaDon.add(lblTenPhong);
		txtTenPhong = new JTextField();
		txtTenPhong.setEditable(false);
		txtTenPhong.setBounds(145, 125, 150, 30);
		txtTenPhong.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		pnThongTinHoaDon.add(txtTenPhong);

		JLabel lblLoaiPhong = new JLabel("LOẠI PHÒNG: ");
		lblLoaiPhong.setBounds(15, 172, 150, 25);
		lblLoaiPhong.setFont(new Font("Arial", Font.PLAIN, 13));
		pnThongTinHoaDon.add(lblLoaiPhong);
		txtLoaiPhong = new JTextField();
		txtLoaiPhong.setEditable(false);
		txtLoaiPhong.setBounds(145, 170, 150, 30);
		txtLoaiPhong.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		pnThongTinHoaDon.add(txtLoaiPhong);

		JLabel lblGiaPhong = new JLabel("GIÁ PHÒNG/1H: ");
		lblGiaPhong.setBounds(15, 217, 150, 25);
		lblGiaPhong.setFont(new Font("Arial", Font.PLAIN, 13));
		pnThongTinHoaDon.add(lblGiaPhong);
		txtGiaPhong = new JTextField();
		txtGiaPhong.setEditable(false);
		txtGiaPhong.setBounds(145, 215, 150, 30);
		txtGiaPhong.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		pnThongTinHoaDon.add(txtGiaPhong);

		JLabel lblThoiGianDen = new JLabel("THỜI GIAN ĐẾN: ");
		lblThoiGianDen.setBounds(15, 262, 150, 25);
		lblThoiGianDen.setFont(new Font("Arial", Font.PLAIN, 13));
		pnThongTinHoaDon.add(lblThoiGianDen);
		txtThoiGianDen = new JTextField();
		txtThoiGianDen.setEditable(false);
		txtThoiGianDen.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		txtThoiGianDen.setBounds(145, 260, 150, 30);
		pnThongTinHoaDon.add(txtThoiGianDen);

		JLabel lblThoiGianTra = new JLabel("THỜI GIAN TRẢ: ");
		lblThoiGianTra.setBounds(15, 307, 150, 25);
		lblThoiGianTra.setFont(new Font("Arial", Font.PLAIN, 13));
		pnThongTinHoaDon.add(lblThoiGianTra);
		txtThoiGianTra = new JTextField();
		txtThoiGianTra.setEditable(false);
		txtThoiGianTra.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		txtThoiGianTra.setBounds(145, 305, 150, 30);
		pnThongTinHoaDon.add(txtThoiGianTra);

		JLabel lblSoGio = new JLabel("TỔNG GIỜ THUÊ: ");
		lblSoGio.setBounds(15, 352, 150, 25);
		lblSoGio.setFont(new Font("Arial", Font.PLAIN, 13));
		pnThongTinHoaDon.add(lblSoGio);
		txtSoGio = new JTextField();
		txtSoGio.setEditable(false);
		txtSoGio.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		txtSoGio.setBounds(145, 350, 150, 30);
		pnThongTinHoaDon.add(txtSoGio);

		JLabel lblTienPhong = new JLabel("TIỀN PHÒNG :");
		lblTienPhong.setBounds(15, 397, 150, 25);
		lblTienPhong.setFont(new Font("Arial", Font.PLAIN, 13));
		pnThongTinHoaDon.add(lblTienPhong);
		txtTienPhong = new JTextField();
		txtTienPhong.setEditable(false);
		txtTienPhong.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		txtTienPhong.setBounds(145, 395, 150, 30);
		pnThongTinHoaDon.add(txtTienPhong);

		JLabel lblTienDV = new JLabel("TIỀN DỊCH VỤ :");
		lblTienDV.setFont(new Font("Arial", Font.PLAIN, 13));
		lblTienDV.setBounds(15, 442, 150, 25);
		pnThongTinHoaDon.add(lblTienDV);
		txtTienDV = new JTextField();
		txtTienDV.setEditable(false);
		txtTienDV.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		txtTienDV.setBounds(145, 440, 150, 30);
		pnThongTinHoaDon.add(txtTienDV);

		JLabel lblThanhToan = new JLabel("THANH TOÁN: ");
		lblThanhToan.setFont(new Font("Arial", Font.PLAIN, 13));
		lblThanhToan.setBounds(15, 487, 150, 30);
		pnThongTinHoaDon.add(lblThanhToan);
		txtThanhToan = new JTextField();
		txtThanhToan.setEditable(false);
		txtThanhToan.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		txtThanhToan.setBounds(145, 485, 150, 30);
		pnThongTinHoaDon.add(txtThanhToan);

		btnInHoaDon = new JButton("IN HÓA ĐƠN");
		btnInHoaDon.setBounds(75, 535, 165, 40);
		btnInHoaDon.setIcon(new ImageIcon("image/inhoadon.png"));
		btnInHoaDon.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnInHoaDon.setBackground(new Color(79, 173, 84));
		btnInHoaDon.setForeground(Color.WHITE);
		btnInHoaDon.setFocusPainted(false);
		pnThongTinHoaDon.add(btnInHoaDon);

		btnThanhToan = new JButton("THANH TOÁN");
		btnThanhToan.setBounds(75, 590, 165, 40);
		btnThanhToan.setIcon(new ImageIcon("image/thanhtoan.png"));
		btnThanhToan.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnThanhToan.setBackground(new Color(79, 173, 84));
		btnThanhToan.setForeground(Color.WHITE);
		btnThanhToan.setFocusPainted(false);
		pnThongTinHoaDon.add(btnThanhToan);

		/*
		 * ADDACTION
		 */
		btnThanhToan.addActionListener(this);
		btnInHoaDon.addActionListener(this);
		btnTimPhong.addActionListener(this);
		tableChonPhong.addMouseListener(this);
		cmbChonPhong.setSelectedIndex(-1);
		cmbChonPhong.addActionListener(this);
		docDuLieuDatabaseVaoTable();
		docDuLieuDatabaseVaoComboBox();
		tableChonPhong.clearSelection();
		tableChonPhong.setDefaultEditor(Object.class, null);

		cmbChonPhong.setSelectedIndex(-1);
		xoaThongTinTrenTextField();
		return pnlContentPane;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnThanhToan)) {
			int row = tableChonPhong.getSelectedRow();
			if (row != -1) {
				HoaDon hd = traPhong_dao.getHoaDonTheoMaPhong(tableChonPhong.getValueAt(row, 0).toString());

				NhanVien nv = traPhong_dao.getNhanVienSuDung(FrameDangNhap.getTaiKhoan());
				String maNhanVien = nv.getMaNV();

				hd.setThoigianTraPhong(thoiGianTraPhongDate);

				double soGio = hd.getSoGioThue();
				double tienPhong = hd.tinhTongTienPhong();
				double tienDV = hd.getTienDichVu();
				double tongTien = hd.tinhTongTienThanhToan();

				String maHD = hd.getMaHD();

				if (traPhong_dao.update(maHD, maNhanVien, thoiGianTraPhongDate, soGio, hd.getGiaPhong(), tienPhong,
						tienDV, tongTien) == true) {
					JOptionPane.showMessageDialog(this, "Thanh toán thành công");
					if (tableChonPhong.getValueAt(row, 0).toString().trim().equals(FrameDatPhong.maPhongMoiDat)) {
						FrameDatPhong.maHDMoiDat = "";
						FrameDatPhong.maKHDatPhong = "";
						FrameDatPhong.maPhongMoiDat = "";
					}
					phongHat_dao.updateTrangThai(tableChonPhong.getValueAt(row, 0).toString().trim(), false);
					tableModelChonPhong.removeRow(row);

					cmbChonPhong.removeAllItems();
					docDuLieuDatabaseVaoComboBox();

					FrameDatPhong.cmbTimKhachHang.removeAllItems();
					FrameDatPhong.docDuLieuVaoCmbTimKH();

					FrameDatPhong.xoaHetDLTablePhongTrong();
					FrameDatPhong.docDuLieuDatabaseVaoTablePhongTrong();

					FrameDatPhong.xoaHetDLTablePhongDaDat();
					FrameDatPhong.docDuLieuDatabaseVaoTablePhongDaDat();

					FrameDatPhong.tablePhongDaDat.clearSelection();
					tableChonPhong.clearSelection();
					xoaThongTinTrenTextField();

				} else {
					JOptionPane.showMessageDialog(this, "Thanh toán không thành công");
				}

			} else {
				JOptionPane.showMessageDialog(this, "Vui lòng chọn phòng cần trả");
			}
		} else if (o.equals(btnTimPhong)) {
			if (cmbChonPhong.getItemCount() > 0 && cmbChonPhong.getSelectedIndex() != -1) {
				String phongDuocChon = cmbChonPhong.getSelectedItem().toString();
				for (int i = 0; i < tableChonPhong.getRowCount(); i++) {
					if (tableChonPhong.getValueAt(i, 1).toString().trim().equals(phongDuocChon)) {
						tableChonPhong.setRowSelectionInterval(i, i);
						layThongTinHoaDon();
						break;
					}
				}
			} else {
				xoaThongTinTrenTextField();
				tableChonPhong.clearSelection();
			}
		} else if (o.equals(btnInHoaDon)) {
			int row = tableChonPhong.getSelectedRow();
			if (row != -1) {
				HoaDon hd = traPhong_dao.getHoaDonTheoMaPhong(tableChonPhong.getValueAt(row, 0).toString().trim());

				NhanVien nv = traPhong_dao.getNhanVienSuDung(FrameDangNhap.getTaiKhoan());

				new FrameHoaDonTinhTien(hd.getMaKH().getTenKH().trim(), nv.getTenNV().trim(), hd.getMaHD().trim(),
						hd.getThoigianDatPhong(), thoiGianTraPhongDate).setVisible(true);

			} else {
				JOptionPane.showMessageDialog(this, "Vui lòng chọn phòng cần in hóa đơn");
			}

		}

	}

	public static void docDuLieuDatabaseVaoTable() {
		DecimalFormat df = new DecimalFormat("#,##0.0");
		List<HoaDon> list = datPhong_DAO.getTatCaPhongHatDaDat();
		for (HoaDon hd : list) {
			tableModelChonPhong.addRow(new Object[] { hd.getMaPhong().getMaPhong().trim(),
					hd.getMaPhong().getTenPhong().trim(), df.format(hd.getMaPhong().getGiaPhong()),
					hd.getMaPhong().getLoaiPhong().trim(), hd.getThoigianDatPhong(), hd.getMaKH().getTenKH().trim() });
		}
	}

	public static void docDuLieuDatabaseVaoComboBox() {
		List<PhongHat> list = phongHat_dao.getPhongTheoTrangThai(true);
		for (PhongHat phongHat : list) {
			cmbChonPhong.addItem(phongHat.getTenPhong().trim());
		}
	}

	public static void xoaHetDLTableChonPhong() {
		DefaultTableModel dm = (DefaultTableModel) tableChonPhong.getModel();
		dm.setRowCount(0);
	}

	public void xoaThongTinTrenTextField() {
		txtNhanVien.setText("");
		txtKH.setText("");
		txtTenPhong.setText("");
		txtLoaiPhong.setText("");
		txtGiaPhong.setText("");
		txtThoiGianDen.setText("");
		txtThoiGianTra.setText("");
		txtSoGio.setText("");
		txtTienPhong.setText("");
		txtTienDV.setText("");
		txtThanhToan.setText("");
		cmbChonPhong.setSelectedIndex(-1);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		layThongTinHoaDon();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	public void layThongTinHoaDon() {
		int row = tableChonPhong.getSelectedRow();
		txtTenPhong.setText(tableChonPhong.getValueAt(row, 1).toString());
		txtLoaiPhong.setText(tableChonPhong.getValueAt(row, 3).toString());

		HoaDon hd = traPhong_dao.getHoaDonTheoMaPhong(tableChonPhong.getValueAt(row, 0).toString());
		txtKH.setText(hd.getMaKH().getTenKH());
		NhanVien nv = traPhong_dao.getNhanVienSuDung(FrameDangNhap.getTaiKhoan());
		txtNhanVien.setText(nv.getTenNV());

		DecimalFormat df = new DecimalFormat("#,##0.0");
		txtGiaPhong.setText(df.format(hd.getGiaPhong()));

		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		thoiGianTraPhongDate = new Date();
		String thoiGianTraPhong = dt.format(thoiGianTraPhongDate);
		hd.setThoigianTraPhong(thoiGianTraPhongDate);

		txtThoiGianDen.setText(dt.format(hd.getThoigianDatPhong()));
		txtThoiGianTra.setText(thoiGianTraPhong);
		txtSoGio.setText(df.format(hd.tinhTongSoGioThue()));
		txtTienPhong.setText(df.format(hd.tinhTongTienPhong()));
		txtTienDV.setText(df.format(hd.tinhTongTienDichVu()));
		txtThanhToan.setText(df.format(hd.tinhTongTienDichVu() + hd.tinhTongSoGioThue() * hd.getGiaPhong()) + " VNĐ");
	}
}
