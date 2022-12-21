package app;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import connectDB.ConnectDB;
import dao.DichVu_DAO;
import entity.DichVu;

public class FrameDichVuDaBan extends JFrame {
	private DefaultTableModel tableModel;
	private JTable table;
	private DichVu_DAO dichvu_dao;

	public FrameDichVuDaBan(String gtriRad, Date ngayMin, Date ngayMax) {
		// khởi tạo kết nối đến CSDL
		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dichvu_dao = new DichVu_DAO();
		// ----------------
		setTitle("DỊCH VỤ BÁN CHẠY");
		setSize(700, 350);
		setLocationRelativeTo(null);
		ImageIcon icon = new ImageIcon("image/logodark.png");
		setIconImage(icon.getImage());

		JPanel pnlContentPane = new JPanel();
		pnlContentPane.setBackground(new Color(255, 255, 255));
		pnlContentPane.setLayout(null);
		setContentPane(pnlContentPane);

		JPanel pnlDVBanChay = new JPanel();
		pnlDVBanChay.setBounds(10, 5, 668, 300);
		pnlDVBanChay.setLayout(new GridLayout(1, 0, 0, 0));
		pnlContentPane.add(pnlDVBanChay);

		String[] header = { "Mã dịch vụ", "Tên dịch vụ", "Giá hiện tại", "Số lượng đặt" };
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

		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(DefaultTableCellRenderer.RIGHT);
		table.getColumn("Giá hiện tại").setCellRenderer(rightRenderer);
		table.getColumn("Số lượng đặt").setCellRenderer(rightRenderer);
		table.setDefaultEditor(Object.class, null);
		table.getTableHeader().setReorderingAllowed(false);

		List<DichVu> listDV = new ArrayList<DichVu>();
		if (gtriRad.equals("Khác")) {
			if(ngayMax != null && ngayMin == null) {
				java.sql.Date dateMax = new java.sql.Date(ngayMax.getYear(), ngayMax.getMonth(), ngayMax.getDate());
				listDV = dichvu_dao.getDichVuDaBan(gtriRad, null, dateMax);
			}
			else if(ngayMax == null && ngayMin != null) {
				java.sql.Date dateMin = new java.sql.Date(ngayMin.getYear(), ngayMin.getMonth(), ngayMin.getDate());
				listDV = dichvu_dao.getDichVuDaBan(gtriRad, dateMin, null);
			}
			else if(ngayMax != null && ngayMin != null)	{
				java.sql.Date dateMax = new java.sql.Date(ngayMax.getYear(), ngayMax.getMonth(), ngayMax.getDate());
				java.sql.Date dateMin = new java.sql.Date(ngayMin.getYear(), ngayMin.getMonth(), ngayMin.getDate());
				listDV = dichvu_dao.getDichVuDaBan(gtriRad, dateMin, dateMax);
			}
			else listDV = dichvu_dao.getDichVuDaBan(gtriRad, null, null);
		}
		else listDV = dichvu_dao.getDichVuDaBan(gtriRad, null, null);
		for (DichVu dv : listDV) {
			tableModel.addRow(new Object[] { dv.getMaDichVu(), dv.getTenDichVu(), dv.getGiaTien(), dv.getSoLuong() });
		}
	}
}
