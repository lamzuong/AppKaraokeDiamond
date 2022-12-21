package app;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

import connectDB.ConnectDB;
import dao.KhachHang_DAO;
import entity.KhachHang;

public class FormCapNhatKH extends JFrame implements ActionListener,KeyListener {
	private JTextField txtTenKH;
	private JComboBox<String> cmbGioiTinh;
	private JTextField txtCmnd;
	private JTextField txtSdt;
	private JButton btnCapNhat;
	private JDateChooser txtNgaySinh;
	private KhachHang_DAO khachhang_dao;

	public FormCapNhatKH() {
		// khởi tạo kết nối đến CSDL
		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		khachhang_dao = new KhachHang_DAO();
		// ------------------------------
		// ------------------------------
		setTitle("CẬP NHẬT KHÁCH HÀNG");
		setSize(400, 420);
		setLocationRelativeTo(null);
		ImageIcon icon = new ImageIcon("image/logodark.png");
		setIconImage(icon.getImage());

		JPanel pnlContentPane = new JPanel();
		pnlContentPane.setBackground(new Color(219, 255, 255));
		pnlContentPane.setLayout(null);
		setContentPane(pnlContentPane);

		JLabel lblTenKH = new JLabel("HỌ TÊN: ");
		lblTenKH.setBounds(55, 36, 120, 20);
		lblTenKH.setFont(new Font("Arial", Font.BOLD, 13));
		pnlContentPane.add(lblTenKH);
		txtTenKH = new JTextField("Nguyễn Văn An");
		txtTenKH.setBounds(180, 28, 150, 30);
		txtTenKH.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlContentPane.add(txtTenKH);

		JLabel lblNgaysinh = new JLabel("NGÀY SINH: ");
		lblNgaysinh.setBounds(55, 92, 120, 20);
		lblNgaysinh.setFont(new Font("Arial", Font.BOLD, 13));
		pnlContentPane.add(lblNgaysinh);
		txtNgaySinh = new JDateChooser();
		txtNgaySinh.setDateFormatString("yyyy-MM-dd");
		txtNgaySinh.setBounds(180, 84, 150, 30);
		txtNgaySinh.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlContentPane.add(txtNgaySinh);

		JLabel lblGioitinh = new JLabel("GIỚI TÍNH: ");
		lblGioitinh.setBounds(55, 148, 120, 20);
		lblGioitinh.setFont(new Font("Arial", Font.BOLD, 13));
		pnlContentPane.add(lblGioitinh);
		String[] gioitinh = { "Nam", "Nữ" };
		cmbGioiTinh = new JComboBox<String>(gioitinh);
		cmbGioiTinh.setBounds(180, 140, 150, 30);
		cmbGioiTinh.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlContentPane.add(cmbGioiTinh);

		JLabel lblCmnd = new JLabel("CMND/CCCD: ");
		lblCmnd.setBounds(55, 204, 120, 20);
		lblCmnd.setFont(new Font("Arial", Font.BOLD, 13));
		pnlContentPane.add(lblCmnd);
		txtCmnd = new JTextField("079201225241");
		txtCmnd.setBounds(180, 196, 150, 30);
		txtCmnd.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlContentPane.add(txtCmnd);

		JLabel lblSdt = new JLabel("LIÊN LẠC:");
		lblSdt.setBounds(55, 260, 80, 14);
		lblSdt.setFont(new Font("Arial", Font.BOLD, 13));
		pnlContentPane.add(lblSdt);
		txtSdt = new JTextField("0905214525");
		txtSdt.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		txtSdt.setBounds(180, 252, 150, 30);
		pnlContentPane.add(txtSdt);

		btnCapNhat = new JButton("CẬP NHẬT KHÁCH HÀNG", new ImageIcon("image/capnhat.png"));
		btnCapNhat.setBounds(70, 310, 240, 45);
		btnCapNhat.setForeground(Color.WHITE);
		btnCapNhat.setBackground(new Color(79, 173, 84));
		btnCapNhat.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnCapNhat.setFocusPainted(false);
		pnlContentPane.add(btnCapNhat);

		btnCapNhat.addActionListener(this);

		int row = FrameKhachHang.table.getSelectedRow();
		txtTenKH.setText(FrameKhachHang.tableModel.getValueAt(row, 1).toString().trim());
		txtCmnd.setText(FrameKhachHang.tableModel.getValueAt(row, 2).toString().trim());
		cmbGioiTinh.setSelectedItem(FrameKhachHang.tableModel.getValueAt(row, 3).toString().trim());
		String ngaySinh = FrameKhachHang.tableModel.getValueAt(row, 4).toString().trim();
		String[] a = ngaySinh.split("-");
		txtNgaySinh
				.setDate(new Date(Integer.parseInt(a[0]) - 1900, Integer.parseInt(a[1]) - 1, Integer.parseInt(a[2])));
		txtSdt.setText(FrameKhachHang.tableModel.getValueAt(row, 5).toString().trim());
		
		txtCmnd.addKeyListener(this);
		txtNgaySinh.addKeyListener(this);
		txtSdt.addKeyListener(this);
		txtTenKH.addKeyListener(this);
		cmbGioiTinh.addKeyListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnCapNhat)) {
			if (!validInput()) {
				return;
			} else {
				String maKH = FrameKhachHang.tableModel.getValueAt(FrameKhachHang.table.getSelectedRow(), 0).toString();
				String tenKH = txtTenKH.getText();
				String sdt = txtSdt.getText();
				String cmnd = txtCmnd.getText();
				Date ngaySinh = txtNgaySinh.getDate();
				String gioiTinh = cmbGioiTinh.getSelectedItem().toString();
				KhachHang kh = new KhachHang(maKH, tenKH, cmnd, gioiTinh == "Nam" ? true : false, sdt, ngaySinh);
				khachhang_dao.update(kh);
				FrameKhachHang.xoaHetDL();
				FrameKhachHang.docDuLieuDatabaseVaoTable();
				/*
				 * Đọc lại vào combobox tìm kh cũ bên đặt phòng
				 */
				String xacDinhDangNhap = FrameDangNhap.getTaiKhoan();
				if (xacDinhDangNhap.substring(0, 2).equals("LT")) {
					int itemCount = FrameDatPhong.cmbTimKhachHang.getItemCount();
					for (int i = 0; i < itemCount; i++) {
						FrameDatPhong.cmbTimKhachHang.removeItemAt(0);
					}
					int itemCount2 = FrameDatPhong.cmbDanhSachSdt.getItemCount();
					for (int i = 0; i < itemCount2; i++) {
						FrameDatPhong.cmbDanhSachSdt.removeItemAt(0);
					}
					FrameDatPhong.docDuLieuVaoCmbDSKH();
					FrameDatPhong.docDuLieuVaoCmbTimKH();
				}
				int itemCount3 = FrameKhachHang.cmbMaKH.getItemCount();
				for (int i = 0; i < itemCount3; i++) {
					FrameKhachHang.cmbMaKH.removeItemAt(0);
				}
				int itemCount4 = FrameKhachHang.cmbTimTen.getItemCount();
				for (int i = 0; i < itemCount4; i++) {
					FrameKhachHang.cmbTimTen.removeItemAt(0);
				}
				int itemCount5 = FrameKhachHang.cmbTimCMND.getItemCount();
				for (int i = 0; i < itemCount5; i++) {
					FrameKhachHang.cmbTimCMND.removeItemAt(0);
				}
				int itemCount6 = FrameKhachHang.cmbTimSDT.getItemCount();
				for (int i = 0; i < itemCount6; i++) {
					FrameKhachHang.cmbTimSDT.removeItemAt(0);
				}
				
				FrameKhachHang.docDuLieuVaoCmbMaKH();
				FrameKhachHang.docDuLieuVaoCmbTen();
				FrameKhachHang.docDuLieuVaoCmbCMND();
				FrameKhachHang.docDuLieuVaoCmbSDT();
				
				dispose();
			}
		}
	}

	private boolean validInput() {
		String tenKH = txtTenKH.getText();
		Date ngaySinh = txtNgaySinh.getDate();
		String cmnd = txtCmnd.getText();
		String sdt = txtSdt.getText();
		if (tenKH.trim().length() > 0) {
			if (!(tenKH.matches("[^\\@\\!\\$\\^\\&\\*\\(\\)]+"))) {
				JOptionPane.showMessageDialog(this, "Tên nhân viên không chứa ký tự đặc biệt", "Lỗi",
						JOptionPane.ERROR_MESSAGE);
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(this, "Tên nhân viên không được để trống", "Lỗi",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if (ngaySinh == null) {
			JOptionPane.showMessageDialog(this, "Ngày sinh không được để trống", "Lỗi",
					JOptionPane.ERROR_MESSAGE);
			return false;
		} else {
			Date ngayHienTai = new Date();
			if (ngayHienTai.getYear() - ngaySinh.getYear() < 18) {
				JOptionPane.showMessageDialog(this, "Nhân viên chưa đủ 18 tuổi", "Lỗi",
						JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		if (cmnd.trim().length() > 0) {
			if (!(cmnd.matches("[0-9]{9}")) && !(cmnd.matches("[0-9]{12}"))) {
				JOptionPane.showMessageDialog(this, "CMND phải gồm 9 hoặc 12 số", "Lỗi",
						JOptionPane.ERROR_MESSAGE);
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(this, "CMND không được để trống", "Lỗi",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if (sdt.trim().length() > 0) {
			if (!(sdt.matches("[0-9]{10,11}"))) {
				JOptionPane.showMessageDialog(this, "Số điện thoại phải gồm 10 đến 11 số", "Lỗi",
						JOptionPane.ERROR_MESSAGE);
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(this, "Số điện thoại không được để trống", "Lỗi",
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
