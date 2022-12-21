package app;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import connectDB.ConnectDB;
import dao.DoiMatKhau_DAO;
import dao.TaiKhoan_DAO;
import entity.NhanVien;
import entity.TaiKhoan;

public class FrameDatLaiMatKhau extends JFrame implements ActionListener {

	private JPasswordField txtMatKhauMoi;
	private JPasswordField txtXacNhan;
	private JButton btnDoiMatKhau;

	private DoiMatKhau_DAO doimatkhau_dao;
	private TaiKhoan_DAO taikhoan_dao;
	private NhanVien nvDoiMatKhau;

	public FrameDatLaiMatKhau(NhanVien nv) {
		// khởi tạo kết nối đến CSDL
		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		doimatkhau_dao = new DoiMatKhau_DAO();
		taikhoan_dao = new TaiKhoan_DAO();
		// ------------------------------

		setTitle("ĐẶT LẠI MẬT KHẨU");
		setSize(330, 210);
		setLocationRelativeTo(null);
		ImageIcon icon = new ImageIcon("image/logodark.png");
		setIconImage(icon.getImage());

		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent evt) {
				new FrameDangNhap().setVisible(true);
			}
		});

		JPanel pnlContentPane = new JPanel();
		pnlContentPane.setBounds(0, 0, 400, 365);
		pnlContentPane.setBackground(new Color(219, 255, 255));
		pnlContentPane.setLayout(null);
		setContentPane(pnlContentPane);

		JLabel lblMatKhauMoi = new JLabel("MẬT KHẨU MỚI:");
		lblMatKhauMoi.setBounds(25, 25, 120, 20);
		lblMatKhauMoi.setFont(new Font("Arial", Font.BOLD, 13));
		pnlContentPane.add(lblMatKhauMoi);

		txtMatKhauMoi = new JPasswordField();
		txtMatKhauMoi.setBounds(140, 15, 150, 30);
		pnlContentPane.add(txtMatKhauMoi);

		JLabel lblXacNhan = new JLabel("XÁC NHẬN:");
		lblXacNhan.setBounds(25, 65, 120, 14);
		lblXacNhan.setFont(new Font("Arial", Font.BOLD, 13));
		pnlContentPane.add(lblXacNhan);

		txtXacNhan = new JPasswordField();
		txtXacNhan.setBounds(140, 55, 150, 30);
		pnlContentPane.add(txtXacNhan);

		btnDoiMatKhau = new JButton("ĐỔI MẬT KHẨU");
		btnDoiMatKhau.setBounds(70, 105, 180, 42);
		btnDoiMatKhau.setForeground(Color.WHITE);
		btnDoiMatKhau.setBackground(new Color(79, 173, 84));
		btnDoiMatKhau.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnDoiMatKhau.setFocusPainted(false);
		btnDoiMatKhau.setIcon(new ImageIcon("image/matkhau.png"));
		pnlContentPane.add(btnDoiMatKhau);

		btnDoiMatKhau.addActionListener(this);
		
		nvDoiMatKhau = nv;
	}

	public static void main(String[] args) {
		new FrameDatLaiMatKhau(new NhanVien()).setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnDoiMatKhau)) {
			String matKhauMoi = txtMatKhauMoi.getText().trim();
			if (validInput()) {
				TaiKhoan tk = doimatkhau_dao.getTaiKhoanTheoTenTaiKhoan(nvDoiMatKhau.getTaiKhoan().getTenTaiKhoan());
				if (taikhoan_dao.updateDoiMatKhau(tk, matKhauMoi) == true) {
					JOptionPane.showMessageDialog(this, "Đổi mật khẩu thành công");
					new FrameDangNhap().setVisible(true);
					setVisible(false);
				} else {
					JOptionPane.showMessageDialog(this, "Đổi mật khẩu thất bại");
				}
			}
		}
	}

	public boolean validInput() {
		String matKhauMoi = txtMatKhauMoi.getText().trim();
		String xacNhan = txtXacNhan.getText().trim();
		
		if (matKhauMoi.equals("") || xacNhan.equals("")) {
			JOptionPane.showMessageDialog(this, "Mật khẩu mới và xác nhận không được để trống");
			return false;
		}

		TaiKhoan tk = doimatkhau_dao.getTaiKhoanTheoTenTaiKhoan(nvDoiMatKhau.getTaiKhoan().getTenTaiKhoan());

		if (matKhauMoi.matches("(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,20}")) {
			if (!matKhauMoi.equals(xacNhan)) {
				JOptionPane.showMessageDialog(this, "Xác nhận không chính xác");
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(this, "Mật khẩu mới từ 8 đến 20 kí tự gồm cả chữ và số");
			return false;
		}
		return true;
	}

}