package app;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.DecimalFormat;
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
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import connectDB.ConnectDB;
import dao.DichVu_DAO;
import dao.HoaDonDichVu_DAO;
import dao.HoaDon_DAO;
import dao.PhongHat_DAO;
import entity.DichVu;
import entity.HoaDon;
import entity.HoaDonDichVu;
import entity.PhongHat;

public class FrameDatDichVu extends JFrame implements ActionListener {

	private JComboBox<String> cmbDanhSachPhong;
	private JButton btnDatDichVu;
	private JButton btnHuyDichVu;
	private JTextField txtSoLuong;
	private JComboBox<String> cmbDanhSachDV;

	private JTable tableListDV;
	private JTable tableHoaDonDV;
	private DefaultTableModel tableModelListDV;
	private DefaultTableModel tableModelHoaDonDV;

	private DichVu_DAO dichvu_dao;
	private PhongHat_DAO phong_dao;
	private HoaDonDichVu_DAO hoadonDV_dao;
	private HoaDon_DAO hoadon_dao;

	public FrameDatDichVu(String tenPhong) {
		// khởi tạo kết nối đến CSDL
		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		phong_dao = new PhongHat_DAO();
		dichvu_dao = new DichVu_DAO();
		hoadonDV_dao = new HoaDonDichVu_DAO();
		hoadon_dao = new HoaDon_DAO();
		// --------------------------
		setTitle("ĐẶT DỊCH VỤ");
		setSize(1300, 410);
		setLocationRelativeTo(null);
		ImageIcon icon = new ImageIcon("image/logodark.png");
		setIconImage(icon.getImage());

		JPanel pnlContentPane = new JPanel();
		pnlContentPane.setBackground(new Color(219, 255, 255));
		pnlContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(pnlContentPane);
		pnlContentPane.setLayout(null);

		JPanel pnlDatDV = new JPanel();
		pnlDatDV.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "ĐẶT DỊCH VỤ: "));
		pnlDatDV.setBounds(10, 10, 1265, 350);
		pnlDatDV.setBackground(new Color(219, 255, 255));
		pnlDatDV.setLayout(null);
		pnlContentPane.add(pnlDatDV);

		JPanel pnlDanhSach = new JPanel();
		pnlDanhSach.setBorder(
				BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "DANH SÁCH DỊCH VỤ: "));
		pnlDanhSach.setBounds(10, 25, 475, 310);
		pnlDanhSach.setBackground(new Color(219, 255, 255));
		pnlDanhSach.setLayout(new GridLayout(1, 0, 0, 0));
		pnlDatDV.add(pnlDanhSach);

		JPanel pnlDatDichVu = new JPanel();
		pnlDatDichVu.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),
				"HÓA ĐƠN DỊCH VỤ CỦA PHÒNG: "));
		pnlDatDichVu.setBounds(716, 25, 539, 310);
		pnlDatDichVu.setBackground(new Color(219, 255, 255));
		pnlDatDichVu.setLayout(new GridLayout(1, 0, 0, 0));
		pnlDatDV.add(pnlDatDichVu);
		// Table
		String[] header = { "Tên dịch vụ", "Số lượng tồn", "Đơn vị tính", "Giá tiền" };
		tableModelListDV = new DefaultTableModel(header, 0);
		tableListDV = new JTable(tableModelListDV) {
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
		tableListDV.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		tableListDV.setGridColor(getBackground());
		tableListDV.setRowHeight(tableListDV.getRowHeight() + 20);
		tableListDV.setSelectionBackground(new Color(255, 255, 128));
		JTableHeader tableHeader = tableListDV.getTableHeader();
		tableHeader.setBackground(new Color(219, 255, 255));
		tableHeader.setFont(new Font("Times New Roman", Font.BOLD, 15));
		tableHeader.setPreferredSize(new Dimension(WIDTH, 30));
		tableHeader.setResizingAllowed(false);

		tableListDV.getColumnModel().getColumn(0).setPreferredWidth(150);
		
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(DefaultTableCellRenderer.RIGHT);
		tableListDV.getColumn("Số lượng tồn").setCellRenderer(rightRenderer);
		tableListDV.getColumn("Giá tiền").setCellRenderer(rightRenderer);

		pnlDanhSach.add(new JScrollPane(tableListDV));

		String[] header2 = { "Tên dịch vụ", "Số lượng", "Đơn vị tính", "Giá tiền", "Thành tiền" };
		tableModelHoaDonDV = new DefaultTableModel(header2, 0);
		tableHoaDonDV = new JTable(tableModelHoaDonDV) {
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
		tableHoaDonDV.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		tableHoaDonDV.setGridColor(getBackground());
		tableHoaDonDV.setRowHeight(tableHoaDonDV.getRowHeight() + 20);
		tableHoaDonDV.setSelectionBackground(new Color(255, 255, 128));
		JTableHeader tableHeader2 = tableHoaDonDV.getTableHeader();
		tableHeader2.setBackground(new Color(219, 255, 255));
		tableHeader2.setFont(new Font("Times New Roman", Font.BOLD, 15));
		tableHeader2.setPreferredSize(new Dimension(WIDTH, 30));
		tableHeader2.setResizingAllowed(false);
		
		tableHoaDonDV.getColumnModel().getColumn(0).setPreferredWidth(150);
		
		tableHoaDonDV.getColumn("Số lượng").setCellRenderer(rightRenderer);
		tableHoaDonDV.getColumn("Giá tiền").setCellRenderer(rightRenderer);
		tableHoaDonDV.getColumn("Thành tiền").setCellRenderer(rightRenderer);

		pnlDatDichVu.add(new JScrollPane(tableHoaDonDV));

		// Combobox
		JPanel pnlCmbPhong = new JPanel();
		pnlCmbPhong.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),
				"NHẬP TÊN PHÒNG CẦN TÌM: "));
		pnlCmbPhong.setBounds(502, 32, 200, 50);
		pnlCmbPhong.setBackground(new Color(219, 255, 255));
		pnlCmbPhong.setLayout(new GridLayout(1, 0, 0, 0));
		cmbDanhSachPhong = new JComboBox<String>();
		cmbDanhSachPhong.setBounds(0, 0, 170, 33);
		cmbDanhSachPhong.setBackground(Color.WHITE);
		cmbDanhSachPhong.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		cmbDanhSachPhong.setEditable(true);
		docDuLieuVaoCmbDsPhong();
		AutoCompleteDecorator.decorate(cmbDanhSachPhong);
		pnlCmbPhong.add(cmbDanhSachPhong);
		pnlDatDV.add(pnlCmbPhong);

		JPanel pnlCmbDV = new JPanel();
		pnlCmbDV.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),
				"NHẬP TÊN DỊCH VỤ CẦN TÌM: "));
		pnlCmbDV.setBounds(502, 92, 200, 50);
		pnlCmbDV.setBackground(new Color(219, 255, 255));
		pnlCmbDV.setLayout(new GridLayout(1, 0, 0, 0));
		cmbDanhSachDV = new JComboBox<String>();
		cmbDanhSachDV.setBounds(0, 0, 170, 33);
		cmbDanhSachDV.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		cmbDanhSachDV.setBackground(Color.WHITE);
		cmbDanhSachDV.setEditable(true);
		docDuLieuVaoCmbDsDichVu();
		AutoCompleteDecorator.decorate(cmbDanhSachDV);
		pnlCmbDV.add(cmbDanhSachDV);
		pnlDatDV.add(pnlCmbDV);

		// Button
		btnDatDichVu = new JButton("ĐẶT DỊCH VỤ");
		btnDatDichVu.setBounds(502, 200, 200, 42);
		btnDatDichVu.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnDatDichVu.setIcon(new ImageIcon("image/datdichvu.png"));
		btnDatDichVu.setBackground(new Color(0, 148, 224));
		btnDatDichVu.setForeground(Color.WHITE);
		btnDatDichVu.setFocusPainted(false);
		pnlDatDV.add(btnDatDichVu);

		btnHuyDichVu = new JButton("HỦY DỊCH VỤ");
		btnHuyDichVu.setBounds(502, 260, 200, 42);
		btnHuyDichVu.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnHuyDichVu.setIcon(new ImageIcon("image/huydatphong.png"));
		btnHuyDichVu.setBackground(new Color(0, 148, 224));
		btnHuyDichVu.setForeground(Color.WHITE);
		btnHuyDichVu.setFocusPainted(false);
		pnlDatDV.add(btnHuyDichVu);

		JLabel lblSoLuong = new JLabel("NHẬP SỐ LƯỢNG: ");
		lblSoLuong.setBounds(515, 150, 220, 42);
		lblSoLuong.setFont(new Font("Tahoma", Font.BOLD, 13));
		pnlDatDV.add(lblSoLuong);
		txtSoLuong = new JTextField();
		txtSoLuong.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		txtSoLuong.setBounds(645, 155, 50, 30);
		pnlDatDV.add(txtSoLuong);
		
		btnDatDichVu.addActionListener(this);
		btnHuyDichVu.addActionListener(this);
		cmbDanhSachDV.addActionListener(this);
		cmbDanhSachPhong.addActionListener(this);
		docDuLieuDatabaseVaoTable();
		if (!tenPhong.equals("")) {
			cmbDanhSachPhong.setSelectedItem(tenPhong.trim());
		}
		tableHoaDonDV.setDefaultEditor(Object.class, null);
		tableListDV.setDefaultEditor(Object.class, null);
		tableHoaDonDV.getTableHeader().setReorderingAllowed(false);
		tableListDV.getTableHeader().setReorderingAllowed(false);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnDatDichVu)) {
			if (!validInput())
				return;
			else {
				int row = tableListDV.getSelectedRow();
				// Kiểm tra chọn phòng và dịch vụ cần đặt chưa
				Object giaTriCmb = cmbDanhSachPhong.getSelectedItem();
				if(giaTriCmb == null || giaTriCmb.toString().trim().equals("")) {
					JOptionPane.showMessageDialog(this, "Vui lòng chọn phòng và dịch vụ cần đặt", "Lỗi",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (row != -1 && giaTriCmb != null && !giaTriCmb.toString().trim().equals("")) {
					int soLuong = Integer.parseInt(txtSoLuong.getText());
					int soLuongDVhientai = Integer.parseInt(tableModelListDV.getValueAt(row, 1).toString().trim());
					if (soLuongDVhientai < soLuong) {
						JOptionPane.showMessageDialog(this, "Không đủ số lượng để đặt", "Lỗi",
								JOptionPane.ERROR_MESSAGE);
						return;
					} else {
						// Lấy hóa đơn từ tên phòng
						String tenPhong = giaTriCmb.toString().trim();
						HoaDon hoadon = new HoaDon();

						List<HoaDon> listHD = hoadon_dao.getTatCaHoaDonChuaTinhTien();
						for (HoaDon hd : listHD) {
							if (hd.getMaPhong().getTenPhong().equals(tenPhong)) {
								hoadon = hd;
								break;
							}
						}
						// Lấy dịch vụ từ tên dịch vụ
						String tenDV = tableModelListDV.getValueAt(row, 0).toString().trim();
						DichVu dichvu = new DichVu();

						List<DichVu> listDV = dichvu_dao.getTatCaDichVu();
						for (DichVu dv : listDV) {
							if (dv.getTenDichVu().equals(tenDV)) {
								dichvu = dv;
								break;
							}
						}
						String giaStr = tableListDV.getValueAt(row, 3).toString().trim();
						String[] gia = giaStr.split(",");
						String giaTien = "";
						for (int i = 0; i < gia.length; i++) {
							giaTien +=  gia[i];
						}
						HoaDonDichVu hddv = new HoaDonDichVu(hoadon, dichvu, soLuong, Double.parseDouble(giaTien));
						DecimalFormat df = new DecimalFormat("#,##0.0");
						int flag = 0;
						int soLuongCu = 0;
						int hangCanSua = 0;
						int rowTableHD = tableHoaDonDV.getRowCount();
						// ktra dịch vụ này có chưa
						for (int i = 0; i < rowTableHD; i++) {
							if (tenDV.trim().equals(tableModelHoaDonDV.getValueAt(i, 0).toString().trim())) {
								flag = 1;
								soLuongCu = Integer.parseInt(tableModelHoaDonDV.getValueAt(i, 1).toString());
								hangCanSua = i;
								break;
							}
						}
						if (flag == 1) {
							HoaDonDichVu hddvDaco = new HoaDonDichVu(hoadon, dichvu, soLuong + soLuongCu, Double.parseDouble(giaTien));
							hoadonDV_dao.updateSoLuong(hddvDaco);
							tableModelHoaDonDV.setValueAt(soLuong + soLuongCu, hangCanSua, 1);
							tableModelHoaDonDV.setValueAt(df.format(hddvDaco.getThanhTien()), hangCanSua, 4);
						} else {
							hoadonDV_dao.create(hddv);
							tableModelHoaDonDV.addRow(new Object[] { hddv.getMaDV().getTenDichVu().trim(),
									hddv.getSoLuong(), hddv.getMaDV().getDonViTinh(), df.format(hddv.getMaDV().getGiaTien()),
									df.format(hddv.getThanhTien()) });
						}
						// Set lại bảng danh sách dịch vụ
						dichvu.setSoLuong(soLuongDVhientai - soLuong);
						dichvu_dao.update(dichvu);
						tableListDV.setValueAt(soLuongDVhientai - soLuong, row, 1);
					}
					JOptionPane.showMessageDialog(this, "Đặt thành công!");
				} else
					JOptionPane.showMessageDialog(this, "Vui lòng chọn phòng và dịch vụ cần đặt", "Lỗi",
							JOptionPane.ERROR_MESSAGE);
			}
		}
		if (o.equals(btnHuyDichVu)) {
			int row = tableHoaDonDV.getSelectedRow();
			Object giaTriCmb = cmbDanhSachPhong.getSelectedItem();
			if(giaTriCmb == null || giaTriCmb.toString().trim().equals("")) {
				JOptionPane.showMessageDialog(this, "Vui lòng chọn phòng và dịch vụ cần đặt", "Lỗi",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (row != -1 && giaTriCmb != null && !giaTriCmb.toString().trim().equals("")) {
				if (!validInput())
					return;
				else {
					int soLuong = Integer.parseInt(txtSoLuong.getText());
					DecimalFormat df = new DecimalFormat("#,##0.0");
					// Lấy hóa đơn từ tên phòng
					String tenPhong = giaTriCmb.toString().trim();
					HoaDon hoadon = new HoaDon();
					List<HoaDon> listHD = hoadon_dao.getTatCaHoaDonChuaTinhTien();
					for (HoaDon hd : listHD) {
						if (hd.getMaPhong().getTenPhong().equals(tenPhong)) {
							hoadon = hd;
							break;
						}
					}
					// Lấy dịch vụ từ tên dịch vụ
					String tenDV = tableHoaDonDV.getValueAt(row, 0).toString().trim();
					DichVu dichvu = new DichVu();
					List<DichVu> listDV = dichvu_dao.getTatCaDichVu();
					for (DichVu dv : listDV) {
						if (dv.getTenDichVu().equals(tenDV)) {
							dichvu = dv;
							break;
						}
					}

					int soLuongCu = Integer.parseInt(tableModelHoaDonDV.getValueAt(row, 1).toString());
					if (soLuong > soLuongCu) {
						JOptionPane.showMessageDialog(this, "Lỗi: Số lượng hủy không được nhiều hơn số lượng đã đặt", "Lỗi",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					String giaStr = tableHoaDonDV.getValueAt(row, 3).toString().trim();
					String[] gia = giaStr.split(",");
					String giaTien = "";
					for (int i = 0; i < gia.length; i++) {
						giaTien +=  gia[i];
					}
					HoaDonDichVu hddv = new HoaDonDichVu(hoadon, dichvu, soLuongCu - soLuong, Double.parseDouble(giaTien));
					hoadonDV_dao.updateSoLuong(hddv);

					tableModelHoaDonDV.setValueAt(soLuongCu - soLuong, row, 1);
					tableModelHoaDonDV.setValueAt(df.format(hddv.getThanhTien()), row, 4);

					// Set lại bảng danh sách dịch vụ
					int soLuongDVhientai = dichvu.getSoLuong();
					dichvu.setSoLuong(soLuongDVhientai + soLuong);
					dichvu_dao.update(dichvu);
					xoaHetDLListDV();
					docDuLieuDatabaseVaoTable();
					// Ktra soluongmoi = 0 thì xóa dịch vụ đó khỏi hóa đơn
					int soLuongMoi = Integer.parseInt(tableHoaDonDV.getValueAt(row, 1).toString());
					if (soLuongMoi < 1) {
						hoadonDV_dao.delete(dichvu.getMaDichVu());
						tableModelHoaDonDV.removeRow(row);
					}
					JOptionPane.showMessageDialog(this, "Hủy thành công!");
				}
			} else
				JOptionPane.showMessageDialog(this, "Vui lòng chọn phòng và dịch vụ cần hủy", "Lỗi",
						JOptionPane.ERROR_MESSAGE);
		}
		if (o.equals(cmbDanhSachDV)) {
			Object giaTriCbo = cmbDanhSachDV.getSelectedItem();
			if (giaTriCbo == null) {
				int itemCount = cmbDanhSachDV.getItemCount();
				docDuLieuVaoCmbDsDichVu();
				for (int i = 0; i < itemCount; i++) {
					cmbDanhSachDV.removeItemAt(0);
				}
				xoaHetDLListDV();
				docDuLieuDatabaseVaoTable();
			} else {
				String tenDV = giaTriCbo.toString().trim();
				if (tenDV.trim().equals("")) {
					int itemCount = cmbDanhSachDV.getItemCount();
					docDuLieuVaoCmbDsDichVu();
					for (int i = 0; i < itemCount; i++) {
						cmbDanhSachDV.removeItemAt(0);
					}
					xoaHetDLListDV();
					docDuLieuDatabaseVaoTable();
				} else {
					xoaHetDLListDV();
					List<DichVu> list = dichvu_dao.getDichVuTheoTen(tenDV);
					for (DichVu dv : list) {
						DecimalFormat df = new DecimalFormat("#,##0");
						tableModelListDV.addRow(new Object[] { dv.getTenDichVu().trim(), df.format(dv.getSoLuong()),
								dv.getDonViTinh().trim(), df.format(dv.getGiaTien()) });
					}
				}
			}
		}
		if (o.equals(cmbDanhSachPhong)) {
			Object giaTriCbo = cmbDanhSachPhong.getSelectedItem();
			if (giaTriCbo == null || giaTriCbo.toString().trim().equals("")) {
				xoaHetDLHoaDonDV();
			} else {
				String tenPhongCbo = giaTriCbo.toString().trim();
				if (tenPhongCbo.trim().equals("")) {

				} else {
					xoaHetDLHoaDonDV();
					// Tìm mã hóa đơn
					String maHD = null;
					List<HoaDon> listHD = hoadon_dao.getTatCaHoaDonChuaTinhTien();
					for (HoaDon hd : listHD) {
						if (hd.getMaPhong().getTenPhong().equals(tenPhongCbo)) {
							maHD = hd.getMaHD();
							break;
						}
					}
					// Hiện lên bảng
					List<HoaDonDichVu> listHDDV = hoadonDV_dao.getHoaDonDVTheoMaHDLenTable(maHD);
					for (HoaDonDichVu dv : listHDDV) {
						DecimalFormat df = new DecimalFormat("#,##0.0");
						tableModelHoaDonDV.addRow(new Object[] { dv.getMaDV().getTenDichVu().trim(),
								dv.getSoLuong(), dv.getMaDV().getDonViTinh(),
								df.format(dv.getMaDV().getGiaTien()), df.format(dv.getThanhTien()) });
					}
				}
			}
		}
	}

	private boolean validInput() {
		String soLuong = txtSoLuong.getText();
		if (soLuong.trim().length() > 0) {
			try {
				int x = Integer.parseInt(soLuong);
				if (x <= 0) {
					JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0", "Lỗi",
							JOptionPane.ERROR_MESSAGE);
					return false;
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "Error: Số lượng phải nhập số", "Lỗi",
						JOptionPane.ERROR_MESSAGE);
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng!!", "Lỗi",
					JOptionPane.ERROR_MESSAGE);
			txtSoLuong.requestFocus();
			return false;
		}
		return true;
	}

	private void docDuLieuDatabaseVaoTable() {
		List<DichVu> list = dichvu_dao.getTatCaDichVuTonTai();
		DecimalFormat df2 = new DecimalFormat("#,##0.0");
		for (DichVu dv : list) {
			tableModelListDV.addRow(new Object[] { dv.getTenDichVu().trim(), dv.getSoLuong(),
					dv.getDonViTinh(), df2.format(dv.getGiaTien()) });
		}
	}

	private void docDuLieuVaoCmbDsPhong() {
		List<PhongHat> list = phong_dao.getPhongTheoTrangThai(true);
		cmbDanhSachPhong.addItem("");
		for (PhongHat ph : list) {
			cmbDanhSachPhong.addItem(ph.getTenPhong().trim());
		}
	}

	private void docDuLieuVaoCmbDsDichVu() {
		List<DichVu> list = dichvu_dao.getTatCaDichVuTonTai();
		cmbDanhSachDV.addItem("");
		for (DichVu dv : list) {
			cmbDanhSachDV.addItem(dv.getTenDichVu().trim());
		}
	}

	private void xoaHetDLListDV() {
		DefaultTableModel dm = (DefaultTableModel) tableListDV.getModel();
		dm.setRowCount(0);
	}

	private void xoaHetDLHoaDonDV() {
		DefaultTableModel dm = (DefaultTableModel) tableHoaDonDV.getModel();
		dm.setRowCount(0);
	}

}