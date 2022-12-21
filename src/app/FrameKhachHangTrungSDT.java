package app;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

public class FrameKhachHangTrungSDT extends JFrame implements MouseListener {
	private DefaultTableModel tableModel;
	private JTable table;
	private KhachHang_DAO khachhang_dao;

	public FrameKhachHangTrungSDT(List<KhachHang> listKH) {
		// khởi tạo kết nối đến CSDL
		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		khachhang_dao = new KhachHang_DAO();
		// ------------------------------
		setTitle("KHÁCH HÀNG TRÙNG SỐ ĐIỆN THOẠI");
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

		for (KhachHang kh : listKH) {
			tableModel.addRow(new Object[] { kh.getMaKH().trim(), kh.getTenKH().trim(), kh.getCmnd().trim(),
					kh.isGioiTinh() == true ? "Nam" : "Nữ", kh.getNgaySinh(), kh.getSoDT().trim() });
		}
		table.addMouseListener(this);
	}

	public static void main(String[] args) {
		new FrameKhachHangTrungSDT(null).setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getClickCount() == 2 && table.getSelectedRow() != -1) {
			int row = table.getSelectedRow();
			FrameDatPhong.txtTenKH.setText(table.getValueAt(row, 1).toString().trim());
			FrameDatPhong.txtCmnd.setText(table.getValueAt(row, 2).toString().trim());
			FrameDatPhong.cmbGioiTinh.setSelectedIndex(table.getValueAt(row, 3).toString().trim() == "Nam" ? 0 : 1);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date date=null;
			try {
				date = df.parse(table.getValueAt(row, 4).toString().trim());
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			FrameDatPhong.txtNgaySinh.setDate(date);
			FrameDatPhong.txtLienLac.setText(table.getValueAt(row, 5).toString().trim());
			
			setVisible(false);
		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
