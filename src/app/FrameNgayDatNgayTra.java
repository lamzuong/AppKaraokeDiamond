package app;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.sql.SQLException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import connectDB.ConnectDB;
import dao.KhachHang_DAO;
import entity.HoaDon;

public class FrameNgayDatNgayTra extends JFrame {

	private DefaultTableModel tableModel;
	private JTable table;
	private JTextField txtTen;
	private JTextField txtMa;
	private KhachHang_DAO khacHang_DAO;

	public FrameNgayDatNgayTra(String maKH) {
		// khởi tạo kết nối đến CSDL
		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		khacHang_DAO = new KhachHang_DAO();
		// --------------------------
		setTitle("THÔNG TIN KHÁCH HÀNG");
		setSize(505, 350);
		setLocationRelativeTo(null);
		setResizable(false);
		ImageIcon icon = new ImageIcon("image/logodark.png");
		setIconImage(icon.getImage());
		
		JPanel pnlContentPane = new JPanel();
		pnlContentPane.setBounds(0, 0, 400, 365);
		pnlContentPane.setBackground(new Color(255, 255, 255));
		pnlContentPane.setLayout(null);
		setContentPane(pnlContentPane);

		JPanel pnlThongTin = new JPanel();
		pnlThongTin.setLayout(new GridLayout(1, 0, 0, 0));
		pnlThongTin.setBounds(15, 100, 463, 200);
		pnlThongTin.setBackground(new Color(219, 255, 255));
		pnlContentPane.add(pnlThongTin);

		JLabel lblMa = new JLabel("MÃ KHÁCH HÀNG: ");
		lblMa.setBounds(40, 10, 200, 40);
		lblMa.setFont(new Font("Arial", Font.BOLD, 13));
		pnlContentPane.add(lblMa);
		txtMa = new JTextField();
		txtMa.setBounds(180, 12, 200, 30);
		txtMa.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		txtMa.setEditable(false);
		pnlContentPane.add(txtMa);

		JLabel lblTen = new JLabel("TÊN KHÁCH HÀNG: ");
		lblTen.setBounds(40, 50, 200, 40);
		lblTen.setFont(new Font("Arial", Font.BOLD, 13));
		pnlContentPane.add(lblTen);
		txtTen = new JTextField();
		txtTen.setBounds(180, 52, 200, 30);
		txtTen.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		txtTen.setEditable(false);
		pnlContentPane.add(txtTen);

		String[] header = { "STT", "Ngày đặt", "Ngày trả" };
		tableModel = new DefaultTableModel(header, 0);
		table = new JTable(tableModel) {
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component c = super.prepareRenderer(renderer, row, column);
				Color color1 = new Color(220, 220, 220);
				Color color2 = Color.WHITE;
				if (!c.getBackground().equals(getSelectionBackground())) {
					Color coleur = (row % 2 == 0 ? color2 : color1);
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
		pnlThongTin.add(new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));

		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(50);
		table.getColumnModel().getColumn(1).setPreferredWidth(205);
		table.getColumnModel().getColumn(2).setPreferredWidth(205);
		
		table.setDefaultEditor(Object.class, null);
		tableHeader.setReorderingAllowed(false);
		DocDuLieuDatabaseVaoGiaoDien(maKH);
	}

	public void DocDuLieuDatabaseVaoGiaoDien(String maKH) {
		int stt = 1;
		List<HoaDon> list = khacHang_DAO.getHoaDonTheoMaKH(maKH);

		txtMa.setText(list.get(0).getMaKH().getMaKH());
		txtTen.setText(list.get(0).getMaKH().getTenKH());

		for (HoaDon hd : list) {
			if (hd.getThoigianTraPhong() == null) {
				tableModel.addRow(new Object[] { stt, hd.getThoigianDatPhong(), "Chưa trả phòng" });
				stt++;
			} else {
				tableModel.addRow(new Object[] { stt, hd.getThoigianDatPhong(), hd.getThoigianTraPhong() });
				stt++;
			}

		}
	}

}
