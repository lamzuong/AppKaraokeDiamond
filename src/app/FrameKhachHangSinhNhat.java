package app;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import connectDB.ConnectDB;
import dao.KhachHang_DAO;
import entity.KhachHang;

public class FrameKhachHangSinhNhat extends JFrame {
	private DefaultTableModel tableModel;
	private JTable table;
	private KhachHang_DAO khachhang_dao;

	public FrameKhachHangSinhNhat() {
		// khởi tạo kết nối đến CSDL
		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		khachhang_dao = new KhachHang_DAO();
		// ------------------------------
		setTitle("KHÁCH HÀNG SINH NHẬT HÔM NAY");
		setSize(730, 351);
		setLocationRelativeTo(null);
		ImageIcon icon = new ImageIcon("image/logodark.png");
		setIconImage(icon.getImage());

		JPanel pnlContentPane = new JPanel();
		pnlContentPane.setBackground(new Color(255, 255, 255));
		pnlContentPane.setLayout(null);
		setContentPane(pnlContentPane);

		JPanel pnlDVBanChay = new JPanel();
		pnlDVBanChay.setBounds(10, 5, 697, 300);
		pnlDVBanChay.setLayout(new GridLayout(1, 0, 0, 0));
		pnlContentPane.add(pnlDVBanChay);

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
		table.setBounds(10, 5, 667, 300);
		table.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		table.setGridColor(getBackground());
		table.setRowHeight(table.getRowHeight() + 20);
		table.setSelectionBackground(new Color(255, 255, 128));
		JTableHeader tableHeader = table.getTableHeader();
		tableHeader.setBackground(new Color(219, 255, 255));
		tableHeader.setFont(new Font("Times New Roman", Font.BOLD, 15));
		tableHeader.setPreferredSize(new Dimension(WIDTH, 30));
		tableHeader.setResizingAllowed(false);
		pnlDVBanChay.add(new JScrollPane(table));

		table.setDefaultEditor(Object.class, null);
		table.getTableHeader().setReorderingAllowed(false);
		
		List<KhachHang> listKH = khachhang_dao.getKhachHangSinhNhatHomNay();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		for (KhachHang kh : listKH) {
			tableModel.addRow(new Object[] { 
					kh.getMaKH().trim(), kh.getTenKH().trim(), kh.getCmnd().trim(),
					kh.isGioiTinh() == true ? "Nam" : "Nữ", df.format(kh.getNgaySinh()),
					kh.getSoDT().trim() 
				});
		}
	}

	public static void main(String[] args) {
		new FrameKhachHangSinhNhat().setVisible(true);
	}
}
