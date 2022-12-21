package app;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import connectDB.ConnectDB;
import dao.PhongHat_DAO;
import entity.PhongHat;

public class FormCapNhatPhong extends JFrame implements ActionListener,KeyListener {
	private JTextField txtTenPhong;
	private JTextField txtGiaPhong;
	private JComboBox<String> cmbLoaiPhong;
	private JButton btnCapNhat;
	private PhongHat_DAO phong_dao;

	public FormCapNhatPhong() {
		// khởi tạo kết nối đến CSDL
		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		phong_dao = new PhongHat_DAO();
		// ------------------
		setTitle("CẬP NHẬT PHÒNG");
		setSize(380, 300);
		setLocationRelativeTo(null);
		ImageIcon icon = new ImageIcon("image/logodark.png");
		setIconImage(icon.getImage());

		JPanel pnlContentPane = new JPanel();
		pnlContentPane.setBackground(new Color(219, 255, 255));
		pnlContentPane.setLayout(null);
		setContentPane(pnlContentPane);

		JLabel lblTen = new JLabel("TÊN PHÒNG :");
		lblTen.setBounds(50, 36, 150, 20);
		lblTen.setFont(new Font("Arial", Font.BOLD, 13));
		pnlContentPane.add(lblTen);
		txtTenPhong = new JTextField("Nguyễn Văn An");
		txtTenPhong.setBounds(160, 28, 150, 30);
		txtTenPhong.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlContentPane.add(txtTenPhong);

		JLabel lblGia = new JLabel("GIÁ PHÒNG :");
		lblGia.setBounds(50, 90, 150, 20);
		lblGia.setFont(new Font("Arial", Font.BOLD, 13));
		pnlContentPane.add(lblGia);
		txtGiaPhong = new JTextField("50000");
		txtGiaPhong.setBounds(160, 82, 150, 30);
		txtGiaPhong.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlContentPane.add(txtGiaPhong);

		JLabel lblLoai = new JLabel("LOẠI PHÒNG: ");
		lblLoai.setBounds(50, 144, 150, 14);
		lblLoai.setFont(new Font("Arial", Font.BOLD, 13));
		pnlContentPane.add(lblLoai);
		String[] loai = { "VIP", "Thường" };
		cmbLoaiPhong = new JComboBox<String>(loai);
		cmbLoaiPhong.setBounds(160, 136, 150, 30);
		cmbLoaiPhong.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlContentPane.add(cmbLoaiPhong);

		btnCapNhat = new JButton("CẬP NHẬT PHÒNG", new ImageIcon("image/capnhat.png"));
		btnCapNhat.setBounds(70, 200, 220, 45);
		btnCapNhat.setForeground(Color.WHITE);
		btnCapNhat.setBackground(new Color(79, 173, 84));
		btnCapNhat.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnCapNhat.setFocusPainted(false);
		pnlContentPane.add(btnCapNhat);

		int row = FramePhongHat.table.getSelectedRow();
		txtTenPhong.setText(FramePhongHat.tableModel.getValueAt(row, 1).toString().trim());
		String gia[] = FramePhongHat.tableModel.getValueAt(row, 2).toString().split(",");
		String giaTien = "";
		for (int i = 0; i < gia.length; i++)
			giaTien += gia[i];
		txtGiaPhong.setText(giaTien);
		cmbLoaiPhong.setSelectedItem(FramePhongHat.tableModel.getValueAt(row, 3).toString().trim());

		btnCapNhat.addActionListener(this);
		
		txtGiaPhong.addKeyListener(this);
		txtTenPhong.addKeyListener(this);
		cmbLoaiPhong.addKeyListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnCapNhat)) {
			if (!validInput() || trungTen()) {
				return;
			} else {
				int row = FramePhongHat.table.getSelectedRow();
				String maPhong = FramePhongHat.tableModel.getValueAt(row, 0).toString();
				String tenPhong = txtTenPhong.getText();
				double giaPhong = Double.parseDouble(txtGiaPhong.getText());
				String loaiPhong = cmbLoaiPhong.getSelectedItem().toString();
				PhongHat ph = new PhongHat(maPhong, tenPhong.trim(), giaPhong, loaiPhong.trim(), false);
				phong_dao.update(ph);

				FramePhongHat.xoaHetDL();
				FramePhongHat.docDuLieuDatabaseVaoTable();
				int itemCount = FramePhongHat.cmbTenPhong.getItemCount();
				for (int i = 0; i < itemCount; i++) {
					FramePhongHat.cmbTenPhong.removeItemAt(0);
				}
				int itemCount2 = FramePhongHat.cmbMaPhong.getItemCount();
				for (int i = 0; i < itemCount2; i++) {
					FramePhongHat.cmbMaPhong.removeItemAt(0);
				}
				FramePhongHat.docDuLieuVaoCmbMaPhong();
				FramePhongHat.docDuLieuVaoCmbTenPhong();
				FramePhongHat.table.getSelectionModel().clearSelection();

				dispose();
			}
		}
	}
	private boolean trungTen() {
		int row = FramePhongHat.table.getSelectedRow();
		String tenHienTai = FramePhongHat.tableModel.getValueAt(row, 1).toString().trim();
		String tenPhong = txtTenPhong.getText().trim();
		List<PhongHat> listPhong = phong_dao.getTatCaPhongHatTonTai();
		for (PhongHat ph : listPhong) {
			if(ph.getTenPhong().trim().equalsIgnoreCase(tenPhong)) {
				if (ph.getTenPhong().trim().equalsIgnoreCase(tenHienTai)) {
					return false;
				}
				JOptionPane.showMessageDialog(this, "Phòng này đã có trong danh sách", "Lỗi",
						JOptionPane.ERROR_MESSAGE);
				return true;
			}
		}
		return false;
	}
	private boolean validInput() {
		String tenPhong = txtTenPhong.getText();
		String giaPhong = txtGiaPhong.getText();
		if (tenPhong.trim().length() > 0) {
			if (!(tenPhong.matches("[^\\@\\!\\$\\^\\&\\*\\(\\)]+"))) {
				JOptionPane.showMessageDialog(this, "Tên phòng không chứa ký tự đặc biệt", "Lỗi",
						JOptionPane.ERROR_MESSAGE);
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(this, "Tên phòng không được để trống", "Lỗi",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if (giaPhong.trim().length() > 0) {
			try {
				double x = Double.parseDouble(giaPhong);
				if (x <= 0) {
					JOptionPane.showMessageDialog(this, "Giá phòng phải lớn hơn 0", "Lỗi",
							JOptionPane.ERROR_MESSAGE);
					return false;
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "Error: Giá phòng phải nhập số", "Lỗi",
						JOptionPane.ERROR_MESSAGE);
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(this, "Giá phòng không được để trống", "Lỗi",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			btnCapNhat.doClick();
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
