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

public class FormThemPhong extends JFrame implements ActionListener ,KeyListener{
	private JButton btnThem;
	private JTextField txtTenPhong;
	private JTextField txtGiaPhong;
	private JComboBox<String> cmbLoaiPhong;
	private PhongHat_DAO phong_dao;

	public FormThemPhong() {
		// khởi tạo kết nối đến CSDL
		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		phong_dao = new PhongHat_DAO();
		// ------------------
		setTitle("THÊM PHÒNG HÁT");
		setSize(380, 300);
		setLocationRelativeTo(null);
		ImageIcon icon = new ImageIcon("image/logodark.png");
		setIconImage(icon.getImage());

		JPanel pnlContentPane = new JPanel();
		pnlContentPane.setBackground(new Color(219, 255, 255));
		pnlContentPane.setLayout(null);
		setContentPane(pnlContentPane);

		JLabel lblTenPhong = new JLabel("TÊN PHÒNG :");
		lblTenPhong.setBounds(50, 36, 150, 20);
		lblTenPhong.setFont(new Font("Arial", Font.BOLD, 13));
		pnlContentPane.add(lblTenPhong);
		txtTenPhong = new JTextField("Phòng 1 lầu 1");
		txtTenPhong.setBounds(160, 28, 150, 30);
		txtTenPhong.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlContentPane.add(txtTenPhong);

		JLabel lblGiaPhong = new JLabel("GIÁ PHÒNG/1H:");
		lblGiaPhong.setBounds(50, 90, 150, 20);
		lblGiaPhong.setFont(new Font("Arial", Font.BOLD, 13));
		pnlContentPane.add(lblGiaPhong);
		txtGiaPhong = new JTextField("100000");
		txtGiaPhong.setBounds(160, 82, 150, 30);
		txtGiaPhong.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlContentPane.add(txtGiaPhong);

		JLabel lblLoaiPhong = new JLabel("LOẠI PHÒNG: ");
		lblLoaiPhong.setBounds(50, 144, 150, 14);
		lblLoaiPhong.setFont(new Font("Arial", Font.BOLD, 13));
		pnlContentPane.add(lblLoaiPhong);
		String[] loai = { "VIP", "Thường" };
		cmbLoaiPhong = new JComboBox<String>(loai);
		cmbLoaiPhong.setBounds(160, 136, 150, 30);
		cmbLoaiPhong.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlContentPane.add(cmbLoaiPhong);

		btnThem = new JButton("THÊM PHÒNG MỚI", new ImageIcon("image/them.png"));
		btnThem.setBounds(70, 200, 220, 45);
		btnThem.setForeground(Color.WHITE);
		btnThem.setBackground(new Color(79, 173, 84));
		btnThem.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnThem.setFocusPainted(false);
		pnlContentPane.add(btnThem);

		btnThem.addActionListener(this);
		txtGiaPhong.addKeyListener(this);
		txtTenPhong.addKeyListener(this);
		cmbLoaiPhong.addKeyListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnThem)) {
			if (!validInput() || trungTen()) {
				return;
			} else {
				String maPhong;
				List<PhongHat> listPhong = phong_dao.getTatCaPhongHat();
				if (listPhong.size() == 0)
					maPhong = "PH10000001";
				else {
					String maPHCuoi = listPhong.get(listPhong.size() - 1).getMaPhong().trim();
					int layMaSo = Integer.parseInt(maPHCuoi.substring(2, maPHCuoi.length()));
					maPhong = "PH" + (layMaSo + 1);
				}
				String tenPhong = txtTenPhong.getText();
				double giaPhong = Double.parseDouble(txtGiaPhong.getText());
				String loaiPhong = cmbLoaiPhong.getSelectedItem().toString();
				PhongHat ph = new PhongHat(maPhong, tenPhong, giaPhong, loaiPhong, false);
				phong_dao.create(ph);
				
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
		List<PhongHat> listPhong = phong_dao.getTatCaPhongHatTonTai();
		String tenPhong = txtTenPhong.getText();
		for (PhongHat ph : listPhong) {
			if(ph.getTenPhong().trim().equalsIgnoreCase(tenPhong)) {
				JOptionPane.showMessageDialog(this, "Phòng này đã có trong danh sách");
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
				JOptionPane.showMessageDialog(this, "Tên phòng không chứa ký tự đặc biệt");
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(this, "Tên phòng không được để trống");
			return false;
		}
		if (giaPhong.trim().length() > 0) {
			try {
				double x = Double.parseDouble(giaPhong);
				if (x <= 0) {
					JOptionPane.showMessageDialog(this, "Giá phòng phải lớn hơn 0");
					return false;
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "Error: Giá phòng phải nhập số");
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(this, "Giá phòng không được để trống");
			return false;
		}
		return true;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			btnThem.doClick();
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
