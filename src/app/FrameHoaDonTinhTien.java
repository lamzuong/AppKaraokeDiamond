package app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import connectDB.ConnectDB;
import dao.HoaDonDichVu_DAO;
import dao.HoaDon_DAO;
import entity.HoaDon;
import entity.HoaDonDichVu;

public class FrameHoaDonTinhTien extends JFrame implements ActionListener {

	private DefaultTableModel tableModel;
	private JTable table;
	private JButton btnIn;
	private JLabel lblTenKH;
	private JLabel lblTenNV;
	private JLabel lblThoiGianDat;
	private JLabel lblThoiGianTra;
	private JLabel lblTienDV;
	private JLabel lblTienPhong;
	private JLabel lblMaHoaDon;
	private JLabel lblTongHoaDon;
	private HoaDonDichVu_DAO hoadondv_dao;
	private HoaDon_DAO hoadon_dao;

	public FrameHoaDonTinhTien(String tenKH, String tenNV, String maHD, Date ngayDat, Date ngayTra) {
		// khởi tạo kết nối đến CSDL
		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		hoadon_dao = new HoaDon_DAO();
		hoadondv_dao = new HoaDonDichVu_DAO();
		// ----------------
		setTitle("HÓA ĐƠN TÍNH TIỀN");
		setSize(500, 600);
		setLocationRelativeTo(null);
		setResizable(false);
		ImageIcon icon = new ImageIcon("image/logodark.png");
		setIconImage(icon.getImage());

		JPanel pnlTong = new JPanel(new BorderLayout());

		JPanel pnlTren = new JPanel();
		pnlTren.setBackground(Color.WHITE);
		// Thông tin quán
		JPanel panelTrenGiua = new JPanel(new BorderLayout());
		panelTrenGiua.setBackground(Color.WHITE);

		Box boxTren = Box.createVerticalBox();
		Box b1 = Box.createHorizontalBox();
		Box b2 = Box.createHorizontalBox();
		Box b3 = Box.createHorizontalBox();
		Box b4 = Box.createHorizontalBox();
		Box b5 = Box.createHorizontalBox();
		Box b6 = Box.createHorizontalBox();
		Box b7 = Box.createHorizontalBox();
		Box b8 = Box.createHorizontalBox();
		Box b9 = Box.createHorizontalBox();

		JLabel lblTen = new JLabel("KARAOKE DIAMOND");
		lblTen.setFont(new Font("Times New Roman", Font.BOLD, 18));
		b1.add(lblTen);
		boxTren.add(b1);
		boxTren.add(Box.createVerticalStrut(5));

		JLabel lblDiachi = new JLabel("Địa chỉ: Số 14 Nguyễn Huệ, Phường Bến Nghé, Quận 1, TPHCM");
		lblDiachi.setFont(new Font("Times New Roman", Font.BOLD, 15));
		b2.add(lblDiachi);
		boxTren.add(b2);
		boxTren.add(Box.createVerticalStrut(5));

		JLabel lblSdt = new JLabel("SĐT: 0794861181");
		lblSdt.setFont(new Font("Times New Roman", Font.BOLD, 15));
		b3.add(lblSdt);
		boxTren.add(b3);
		boxTren.add(Box.createVerticalStrut(5));

		JLabel lblHoaDon = new JLabel("HOÁ ĐƠN TÍNH TIỀN");
		lblHoaDon.setFont(new Font("Times New Roman", Font.BOLD, 18));
		b4.add(lblHoaDon);
		boxTren.add(b4);
		boxTren.add(Box.createVerticalStrut(5));
		// Thông tin hoá đơn khách hàng
		JLabel lbl1 = new JLabel("Tên nhân viên:");
		lbl1.setFont(new Font("Times New Roman", Font.BOLD, 15));
		b5.add(lbl1);
		lblTenNV = new JLabel(tenNV);
		lblTenNV.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		b5.add(Box.createHorizontalStrut(88));
		b5.add(lblTenNV);
		b5.add(Box.createHorizontalGlue());
		boxTren.add(b5);
		boxTren.add(Box.createVerticalStrut(5));

		JLabel lbl2 = new JLabel("Tên khách hàng:");
		lbl2.setFont(new Font("Times New Roman", Font.BOLD, 15));
		b6.add(lbl2);
		lblTenKH = new JLabel(tenKH);
		lblTenKH.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		b6.add(Box.createHorizontalStrut(74));
		b6.add(lblTenKH);
		b6.add(Box.createHorizontalGlue());
		boxTren.add(b6);
		boxTren.add(Box.createVerticalStrut(5));

		JLabel lbl3 = new JLabel("Thời gian đặt phòng:");
		lbl3.setFont(new Font("Times New Roman", Font.BOLD, 15));
		b7.add(lbl3);
		SimpleDateFormat df = new SimpleDateFormat("HH:mm dd/MM/yyyy");
		lblThoiGianDat = new JLabel();
		lblThoiGianDat.setText(df.format(ngayDat));
		lblThoiGianDat.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		b7.add(Box.createHorizontalStrut(47));
		b7.add(lblThoiGianDat);
		b7.add(Box.createHorizontalGlue());
		boxTren.add(b7);
		boxTren.add(Box.createVerticalStrut(5));

		JLabel lbl4 = new JLabel("Thời gian trả phòng:");
		lbl4.setFont(new Font("Times New Roman", Font.BOLD, 15));
		b8.add(lbl4);
		lblThoiGianTra = new JLabel();
		lblThoiGianTra.setText(df.format(ngayTra));
		lblThoiGianTra.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		b8.add(Box.createHorizontalStrut(49));
		b8.add(lblThoiGianTra);
		b8.add(Box.createHorizontalGlue());
		boxTren.add(b8);
		boxTren.add(Box.createVerticalStrut(5));

		JLabel lbl5 = new JLabel("Mã hóa đơn:");
		lbl5.setFont(new Font("Times New Roman", Font.BOLD, 15));
		b9.add(lbl5);
		lblMaHoaDon = new JLabel(maHD);
		lblMaHoaDon.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		b9.add(Box.createHorizontalStrut(101));
		b9.add(lblMaHoaDon);
		b9.add(Box.createHorizontalGlue());
		boxTren.add(b9);
		boxTren.add(Box.createVerticalStrut(5));
		panelTrenGiua.add(boxTren, BorderLayout.NORTH);

		// Bảng dịch vụ sử dụng
		JPanel pnlTable = new JPanel(new BorderLayout());

		String[] header = { "Tên dịch vụ", "Số lượng", "Giá tiền", "Thành tiền" };
		tableModel = new DefaultTableModel(header, 0);
		table = new JTable(tableModel);
		table.getColumnModel().getColumn(0).setPreferredWidth(200);
		
		List<HoaDonDichVu> listHDDV = hoadondv_dao.getHoaDonDVTheoMaHDLenTable(maHD);
		DecimalFormat dfMoney = new DecimalFormat("#,##0.0");
		if (listHDDV != null) {
			for (HoaDonDichVu hddv : listHDDV) {
				tableModel.addRow(new Object[] { 
						hddv.getMaDV().getTenDichVu(), hddv.getSoLuong(), 
						dfMoney.format(hddv.getGiaTien()), dfMoney.format(hddv.getThanhTien())
					});
			}
		}

		table.setGridColor(getBackground());
		table.getTableHeader().setBackground(new Color(255, 255, 255));
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setBorder(null);
		table.getTableHeader().setFont(new Font("Times New Roman", Font.PLAIN, 15));
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();

		rightRenderer.setHorizontalAlignment(DefaultTableCellRenderer.RIGHT);
		table.getColumn("Số lượng").setCellRenderer(rightRenderer);
		table.getColumn("Giá tiền").setCellRenderer(rightRenderer);
		table.getColumn("Thành tiền").setCellRenderer(rightRenderer);
		table.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlTable.add(table.getTableHeader(), BorderLayout.NORTH);
		pnlTable.add(table, BorderLayout.CENTER);

		panelTrenGiua.add(pnlTable, BorderLayout.CENTER);

		// Tính tiền
		Box boxDuoi = Box.createVerticalBox();

		Box boxDuoi1 = Box.createHorizontalBox();
		Box boxDuoi2 = Box.createHorizontalBox();
		Box boxDuoi3 = Box.createHorizontalBox();
		Box boxDuoi4 = Box.createHorizontalBox();
		Box boxDuoi5 = Box.createHorizontalBox();
		Box boxDuoi6 = Box.createHorizontalBox();

		HoaDon hd = hoadon_dao.getHoaDonTheoMaHDKhongCoNV(maHD);
		hd.setThoigianDatPhong(ngayDat);
		hd.setThoigianTraPhong(ngayTra);
		
		JLabel lblTongTien1 = new JLabel("Tổng tiền dịch vụ:");
		lblTongTien1.setFont(new Font("Times New Roman", Font.BOLD, 15));
		boxDuoi1.add(lblTongTien1);
		boxDuoi1.add(Box.createHorizontalGlue());
		lblTienDV = new JLabel(dfMoney.format(hd.tinhTongTienDichVu()));
		lblTienDV.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		boxDuoi1.add(lblTienDV);

		JLabel lblTongTien2 = new JLabel("Tổng tiền phòng:");
		lblTongTien2.setFont(new Font("Times New Roman", Font.BOLD, 15));
		boxDuoi2.add(lblTongTien2);
		boxDuoi2.add(Box.createHorizontalGlue());
		lblTienPhong = new JLabel(""+dfMoney.format(hd.tinhTongTienPhong()));
		lblTienPhong.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		boxDuoi2.add(lblTienPhong);

		JLabel lblTong = new JLabel("Tổng hoá đơn:");
		lblTong.setFont(new Font("Times New Roman", Font.BOLD, 18));
		boxDuoi3.add(lblTong);
		boxDuoi3.add(Box.createHorizontalGlue());
		lblTongHoaDon = new JLabel(""+dfMoney.format(hd.tinhTongTienThanhToan()));
		lblTongHoaDon.setFont(new Font("Times New Roman", Font.BOLD, 18));
		boxDuoi3.add(lblTongHoaDon);

		boxTren.add(Box.createVerticalStrut(5));
		JLabel lblCamOn = new JLabel("XIN CẢM ƠN VÀ HẸN GẶP LẠI QUÝ KHÁCH!");
		lblCamOn.setFont(new Font("Times New Roman", Font.BOLD, 18));
		boxDuoi4.add(lblCamOn);
		boxDuoi.add(Box.createVerticalStrut(5));
		boxDuoi.add(boxDuoi1);
		boxDuoi.add(Box.createVerticalStrut(5));
		boxDuoi.add(boxDuoi2);
		boxDuoi.add(Box.createVerticalStrut(5));
		boxDuoi.add(boxDuoi3);
		boxDuoi.add(Box.createVerticalStrut(5));
		boxDuoi.add(boxDuoi4);
		boxDuoi.add(Box.createVerticalStrut(5));
		boxDuoi.add(boxDuoi5);
		boxDuoi.add(Box.createVerticalStrut(5));
		boxDuoi.add(boxDuoi6);

		panelTrenGiua.add(boxDuoi, BorderLayout.SOUTH);
		pnlTren.add(panelTrenGiua);
		pnlTren.setAutoscrolls(true);
		pnlTong.add(new JScrollPane(pnlTren));

		JPanel pnlDuoi = new JPanel();

		btnIn = new JButton("IN HÓA ĐƠN", new ImageIcon("image/inhoadon.png"));
		btnIn.setPreferredSize(new Dimension(160, 35));
		btnIn.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnIn.setBackground(new Color(0, 148, 224));
		btnIn.setForeground(Color.WHITE);
		btnIn.setFocusPainted(false);

		pnlDuoi.add(btnIn);
		pnlDuoi.setBackground(Color.WHITE);
		pnlTong.add(pnlDuoi, BorderLayout.SOUTH);

		add(pnlTong);
		btnIn.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnIn)) {
			JOptionPane.showMessageDialog(this, "In hoá đơn thành công");
		}
	}
}
