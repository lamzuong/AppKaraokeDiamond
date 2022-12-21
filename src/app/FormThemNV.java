package app;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

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
import dao.NhanVien_DAO;
import dao.TaiKhoan_DAO;
import entity.NhanVien;
import entity.TaiKhoan;

public class FormThemNV extends JFrame implements ActionListener,KeyListener {
	private JTextField txtTenNV;
	private JComboBox<String> cmbGioiTinh;
	private JTextField txtCmnd;
	private JTextField txtSdt;
	private JComboBox<String> cmbChucVu;
	private JComboBox<String> cmbCa;
	private JTextField txtLuong;
	private JButton btnThem;
	private NhanVien_DAO nhanvien_dao;
	private TaiKhoan_DAO taikhoan_dao;
	private String actor;
	private JDateChooser txtNgaySinh;

	public FormThemNV() {
		// Xác định đăng nhập
		String xacDinhDangNhap = FrameDangNhap.getTaiKhoan();
		if (xacDinhDangNhap.substring(0, 2).equals("CQ"))
			actor = "Chủ quán";
		else actor = "Quản lý";
		// khởi tạo kết nối đến CSDL
		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		nhanvien_dao = new NhanVien_DAO();
		taikhoan_dao = new TaiKhoan_DAO();
		// ------------
		setTitle("THÊM NHÂN VIÊN");
		setSize(570, 360);
		setLocationRelativeTo(null);
		ImageIcon icon = new ImageIcon("image/logodark.png");
		setIconImage(icon.getImage());

		JPanel pnlContentPane = new JPanel();
		pnlContentPane.setBackground(new Color(219, 255, 255));
		pnlContentPane.setLayout(null);
		setContentPane(pnlContentPane);

		JLabel lblTenNV = new JLabel("HỌ TÊN: ");
		lblTenNV.setBounds(18, 36, 120, 20);
		lblTenNV.setFont(new Font("Arial", Font.BOLD, 13));
		pnlContentPane.add(lblTenNV);
		txtTenNV = new JTextField("Nguyễn Văn An");
		txtTenNV.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		txtTenNV.setBounds(118, 28, 150, 30);
		pnlContentPane.add(txtTenNV);

		JLabel lblGioitinh = new JLabel("GIỚI TÍNH: ");
		lblGioitinh.setBounds(18, 92, 120, 20);
		lblGioitinh.setFont(new Font("Arial", Font.BOLD, 13));
		pnlContentPane.add(lblGioitinh);
		String[] gioitinh = { "Nam", "Nữ" };
		cmbGioiTinh = new JComboBox<String>(gioitinh);
		cmbGioiTinh.setBounds(118, 85, 150, 30);
		cmbGioiTinh.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlContentPane.add(cmbGioiTinh);

		JLabel lblCmnd = new JLabel("CMND/CCCD: ");
		lblCmnd.setBounds(18, 147, 120, 20);
		lblCmnd.setFont(new Font("Arial", Font.BOLD, 13));
		pnlContentPane.add(lblCmnd);
		txtCmnd = new JTextField("079201225241");
		txtCmnd.setBounds(118, 139, 150, 30);
		txtCmnd.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlContentPane.add(txtCmnd);
		
		JLabel lblCa = new JLabel("CA LÀM VIỆC: ");
		lblCa.setBounds(18, 204, 120, 14);
		lblCa.setFont(new Font("Arial", Font.BOLD, 13));
		pnlContentPane.add(lblCa);
		String[] ca = { "8:00AM-4:00PM", "4:00PM-12:00AM", "12:00AM-8:00AM" };
		cmbCa = new JComboBox<String>(ca);
		cmbCa.setBounds(118, 196, 150, 30);
		cmbCa.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlContentPane.add(cmbCa);

		JLabel lblNgaysinh = new JLabel("NGÀY SINH: ");
		lblNgaysinh.setBounds(295, 36, 120, 20);
		lblNgaysinh.setFont(new Font("Arial", Font.BOLD, 13));
		pnlContentPane.add(lblNgaysinh);
		txtNgaySinh = new JDateChooser();
		txtNgaySinh.setDateFormatString("yyyy-MM-dd");
		txtNgaySinh.setBounds(385, 30, 150, 30);
		txtNgaySinh.setDate(new Date(1999-1900, 1-1, 1));
		txtNgaySinh.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlContentPane.add(txtNgaySinh);
		
		JLabel lblSdt = new JLabel("SĐT :");
		lblSdt.setBounds(295, 92, 120, 14);
		lblSdt.setFont(new Font("Arial", Font.BOLD, 13));
		pnlContentPane.add(lblSdt);
		txtSdt = new JTextField("0905214525");
		txtSdt.setBounds(385, 84, 150, 30);
		txtSdt.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlContentPane.add(txtSdt);

		JLabel lblChucvu = new JLabel("CHỨC VỤ: ");
		lblChucvu.setBounds(295, 148, 120, 20);
		lblChucvu.setFont(new Font("Arial", Font.BOLD, 13));
		pnlContentPane.add(lblChucvu);
		String[] chucvu = { "Phục vụ", "Lễ tân" };
		cmbChucVu = new JComboBox<String>(chucvu);
		if (actor.equals("Chủ quán"))
			cmbChucVu.addItem("Quản lý");
		cmbChucVu.setBounds(385, 140, 150, 30);
		cmbChucVu.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlContentPane.add(cmbChucVu);
		
		JLabel lblLuong = new JLabel("LƯƠNG: ");
		lblLuong.setBounds(295, 204, 120, 14);
		lblLuong.setFont(new Font("Arial", Font.BOLD, 13));
		pnlContentPane.add(lblLuong);
		txtLuong = new JTextField("5000000");
		txtLuong.setBounds(385, 196, 150, 30);
		txtLuong.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlContentPane.add(txtLuong);

		btnThem = new JButton("THÊM NHÂN VIÊN MỚI", new ImageIcon("image/them.png"));
		btnThem.setBounds(165, 260, 220, 45);
		btnThem.setForeground(Color.WHITE);
		btnThem.setBackground(new Color(79, 173, 84));
		btnThem.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnThem.setFocusPainted(false);
		pnlContentPane.add(btnThem);

		btnThem.addActionListener(this);
		
		txtCmnd.addKeyListener(this);
		txtLuong.addKeyListener(this);
		txtNgaySinh.addKeyListener(this);
		txtSdt.addKeyListener(this);
		txtTenNV.addKeyListener(this);
		cmbCa.addKeyListener(this);
		cmbChucVu.addKeyListener(this);
		cmbGioiTinh.addKeyListener(this);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnThem)) {
			if (!validInput()) {
				return;
			} else {
				String maNV;
				List<NhanVien> listNV = nhanvien_dao.getTatCaNhanVien();
				if (listNV.size() == 0)
					maNV = "NV10000001";
				else {
					String maNVCuoi = listNV.get(listNV.size() - 1).getMaNV().trim();
					int layMaSo = Integer.parseInt(maNVCuoi.substring(2, maNVCuoi.length()));
					maNV = "NV" + (layMaSo + 1);
				}
				String tenNV = txtTenNV.getText();
				String gioiTinh = cmbGioiTinh.getSelectedItem().toString();
				String cmnd = txtCmnd.getText();
				String sdt = txtSdt.getText();
				String chucVu = cmbChucVu.getSelectedItem().toString();
				String caLamViec = cmbCa.getSelectedItem().toString();
				double luong = Double.parseDouble(txtLuong.getText());
				Date ngaySinh = txtNgaySinh.getDate();
				java.sql.Date date = new java.sql.Date(ngaySinh.getYear(), ngaySinh.getMonth(), ngaySinh.getDate());
				
				String taiKhoan = null;
				if (chucVu.equals("Lễ tân") || chucVu.equals("Quản lý")) {
					if (chucVu.equals("Lễ tân"))
						taiKhoan = "LT" + maNV.substring(2, maNV.length());
					else
						taiKhoan = "QL" + maNV.substring(2, maNV.length());
					TaiKhoan tk = new TaiKhoan(taiKhoan, "123");
					NhanVien nv = new NhanVien(maNV, tenNV, date, sdt, cmnd, gioiTinh == "Nam" ? true : false,
							chucVu, luong, caLamViec, tk, false);
					taikhoan_dao.create(tk);
					nhanvien_dao.createNVCoTK(nv);
				} else {
					NhanVien nv = new NhanVien(maNV, tenNV, date, sdt, cmnd, gioiTinh == "Nam" ? true : false,
							chucVu, luong, caLamViec, null, false);
					nhanvien_dao.createNVKhongCoTK(nv);
				}

				FrameNhanVien.xoaHetDL();
				FrameNhanVien.docDuLieuDatabaseVaoTable();
				int itemCount = FrameNhanVien.cmbTenNV.getItemCount();
				for (int i = 0; i < itemCount; i++) {
					FrameNhanVien.cmbTenNV.removeItemAt(0);
				}
				int itemCount2 = FrameNhanVien.cmbMaNV.getItemCount();
				for (int i = 0; i < itemCount2; i++) {
					FrameNhanVien.cmbMaNV.removeItemAt(0);
				}
				int itemCount3 = FrameNhanVien.cmbCmnd.getItemCount();
				for (int i = 0; i < itemCount3; i++) {
					FrameNhanVien.cmbCmnd.removeItemAt(0);
				}
				int itemCount4 = FrameNhanVien.cmbSdt.getItemCount();
				for (int i = 0; i < itemCount4; i++) {
					FrameNhanVien.cmbSdt.removeItemAt(0);
				}
				FrameNhanVien.docDuLieuVaoCmbMaNV();
				FrameNhanVien.docDuLieuVaoCmbTenNV();
				FrameNhanVien.docDuLieuVaoCmbCmnd();
				FrameNhanVien.docDuLieuVaoCmbSdt();
				FrameNhanVien.table.getSelectionModel().clearSelection();
				FrameNhanVien.lamMoiDL();
				dispose();
			}
		}
	}

	private boolean validInput() {
		String tenNV = txtTenNV.getText();
		Date ngaySinh = txtNgaySinh.getDate();
		String cmnd = txtCmnd.getText();
		String sdt = txtSdt.getText();
		String luong = txtLuong.getText();
		if (tenNV.trim().length() > 0) {
			if (!(tenNV.matches("[^\\@\\!\\$\\^\\&\\*\\(\\)]+"))) {
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
		if (luong.trim().length() > 0) {
			try {
				double x = Double.parseDouble(luong);
				if (x <= 0) {
					JOptionPane.showMessageDialog(this, "Lương phải lớn hơn 0", "Lỗi",
							JOptionPane.ERROR_MESSAGE);
					return false;
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "Error: Lương phải nhập số", "Lỗi",
						JOptionPane.ERROR_MESSAGE);
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(this, "Lương không được để trống", "Lỗi",
					JOptionPane.ERROR_MESSAGE);
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
