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
import dao.DichVu_DAO;
import entity.DichVu;

public class FormCapNhatDV extends JFrame implements ActionListener, KeyListener {
	private JTextField txtTenDV;
	private JTextField txtGiaDV;
	private JComboBox<String> cmbDonVi;
	private JTextField txtSoLuong;
	private JButton btnCapNhat;
	private DichVu_DAO dichvu_dao;

	public FormCapNhatDV() {
		// khởi tạo kết nối đến CSDL
		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dichvu_dao = new DichVu_DAO();
		// --------------
		setTitle("CẬP NHẬT DỊCH VỤ");
		setSize(380, 350);
		setLocationRelativeTo(null);
		ImageIcon icon = new ImageIcon("image/logodark.png");
		setIconImage(icon.getImage());

		JPanel pnlContentPane = new JPanel();
		pnlContentPane.setBackground(new Color(219, 255, 255));
		pnlContentPane.setLayout(null);
		setContentPane(pnlContentPane);

		JLabel lblTenDV = new JLabel("TÊN DỊCH VỤ :");
		lblTenDV.setBounds(50, 36, 150, 20);
		lblTenDV.setFont(new Font("Arial", Font.BOLD, 13));
		pnlContentPane.add(lblTenDV);
		txtTenDV = new JTextField("Bia");
		txtTenDV.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		txtTenDV.setBounds(160, 28, 150, 30);
		pnlContentPane.add(txtTenDV);

		JLabel lblGia = new JLabel("GIÁ TIỀN :");
		lblGia.setBounds(50, 90, 150, 20);
		lblGia.setFont(new Font("Arial", Font.BOLD, 13));
		pnlContentPane.add(lblGia);
		txtGiaDV = new JTextField("100000");
		txtGiaDV.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		txtGiaDV.setBounds(160, 82, 150, 30);
		pnlContentPane.add(txtGiaDV);

		JLabel lblLoai = new JLabel("ĐƠN VỊ TÍNH: ");
		lblLoai.setBounds(50, 144, 150, 14);
		lblLoai.setFont(new Font("Arial", Font.BOLD, 13));
		pnlContentPane.add(lblLoai);
		String[] loai = { "Phần", "Lon", "Ly" };
		cmbDonVi = new JComboBox<String>(loai);
		cmbDonVi.setBounds(160, 136, 150, 30);
		cmbDonVi.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlContentPane.add(cmbDonVi);

		JLabel lblSL = new JLabel("SỐ LƯỢNG: ");
		lblSL.setBounds(50, 198, 150, 14);
		lblSL.setFont(new Font("Arial", Font.BOLD, 13));
		pnlContentPane.add(lblSL);
		txtSoLuong = new JTextField("20");
		txtSoLuong.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		txtSoLuong.setBounds(160, 190, 150, 30);
		pnlContentPane.add(txtSoLuong);

		btnCapNhat = new JButton("CẬP NHẬT DỊCH VỤ", new ImageIcon("image/capnhat.png"));
		btnCapNhat.setBounds(70, 240, 220, 45);
		btnCapNhat.setForeground(Color.WHITE);
		btnCapNhat.setBackground(new Color(79, 173, 84));
		btnCapNhat.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnCapNhat.setFocusPainted(false);
		pnlContentPane.add(btnCapNhat);

		int row = FrameDichVu.table.getSelectedRow();
		txtTenDV.setText(FrameDichVu.tableModel.getValueAt(row, 1).toString());
		txtSoLuong.setText(FrameDichVu.tableModel.getValueAt(row, 2).toString());
		cmbDonVi.setSelectedItem(FrameDichVu.tableModel.getValueAt(row, 3).toString());
		String gia[] = FrameDichVu.tableModel.getValueAt(row, 4).toString().split(",");
		String giaTien = "";
		for (int i = 0; i < gia.length; i++)
			giaTien += gia[i];
		txtGiaDV.setText(giaTien);
		btnCapNhat.addActionListener(this);

		txtGiaDV.addKeyListener(this);
		txtSoLuong.addKeyListener(this);
		txtTenDV.addKeyListener(this);
		cmbDonVi.addKeyListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnCapNhat)) {
			if (!validInput() || trungTen()) {
				return;
			} else {
				int row = FrameDichVu.table.getSelectedRow();
				String maDV = FrameDichVu.tableModel.getValueAt(row, 0).toString();
				String tenDV = txtTenDV.getText();
				double giaDV = Double.parseDouble(txtGiaDV.getText());
				int soLuong = Integer.parseInt(txtSoLuong.getText());
				String donViTinh = cmbDonVi.getSelectedItem().toString();
				DichVu dv = new DichVu(maDV, tenDV, soLuong, donViTinh, giaDV);
				dichvu_dao.update(dv);

				FrameDichVu.xoaHetDL();
				FrameDichVu.docDuLieuDatabaseVaoTable();
				int itemCount = FrameDichVu.cmbTenDV.getItemCount();
				for (int i = 0; i < itemCount; i++) {
					FrameDichVu.cmbTenDV.removeItemAt(0);
				}
				FrameDichVu.docDuLieuVaoCmbTen();
				FrameDichVu.table.getSelectionModel().clearSelection();

				dispose();
			}
		}
	}

	private boolean trungTen() {
		int row = FrameDichVu.table.getSelectedRow();
		String tenHienTai = FrameDichVu.tableModel.getValueAt(row, 1).toString().trim();
		String tenDV = txtTenDV.getText().trim();
		List<DichVu> listPhong = dichvu_dao.getTatCaDichVuTonTai();
		for (DichVu dv : listPhong) {
			if (dv.getTenDichVu().trim().equalsIgnoreCase(tenDV)) {
				if (dv.getTenDichVu().trim().equalsIgnoreCase(tenHienTai)) {
					return false;
				}
				JOptionPane.showMessageDialog(this, "Dịch vụ này đã có trong danh sách");
				return true;
			}
		}
		return false;
	}

	private boolean validInput() {
		String tenDV = txtTenDV.getText();
		String giaDV = txtGiaDV.getText();
		String soLuong = txtSoLuong.getText();
		if (tenDV.trim().length() > 0) {
			if (!(tenDV.matches("[^\\@\\!\\$\\^\\&\\*\\(\\)]+"))) {
				JOptionPane.showMessageDialog(this, "Tên dịch vụ không chứa ký tự đặc biệt");
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(this, "Tên dịch vụ không được để trống");
			return false;
		}
		if (giaDV.trim().length() > 0) {
			try {
				double x = Double.parseDouble(giaDV);
				if (x <= 0) {
					JOptionPane.showMessageDialog(this, "Giá dịch vụ phải lớn hơn 0");
					return false;
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "Error: Giá dịch vụ phải nhập số");
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(this, "Giá dịch vụ không được để trống");
			return false;
		}
		if (soLuong.trim().length() > 0) {
			try {
				int x = Integer.parseInt(soLuong);
				if (x <= 0) {
					JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0");
					return false;
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "Error: Số lượng phải nhập số");
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(this, "Số lượng không được để trống");
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
