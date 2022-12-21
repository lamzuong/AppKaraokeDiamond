package app;

import java.awt.Color;
import java.awt.Font;
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
import entity.TaiKhoan;

public class FrameDoiMatKhau extends JFrame implements ActionListener {

	private JPasswordField txtMatkhauCu;
	private JPasswordField txtMatkhauMoi;
	private JPasswordField txtXacNhan;
	private JButton btnDoiMatKhau;
	private DoiMatKhau_DAO doimatkhau_dao;
	private TaiKhoan_DAO taiKhoan_dao;

	public FrameDoiMatKhau() {
		// khởi tạo kết nối đến CSDL
		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		doimatkhau_dao = new DoiMatKhau_DAO();
		taiKhoan_dao = new TaiKhoan_DAO();
		// ------------------------------

		setTitle("ĐỔI MẬT KHẨU");
		setSize(400, 220);
		setLocationRelativeTo(null);
		setResizable(false);
		ImageIcon icon = new ImageIcon("image/logodark.png");
		setIconImage(icon.getImage());

		JPanel pnlContentPane = new JPanel(null);
		pnlContentPane.setBackground(new Color(219, 255, 255));
		setContentPane(pnlContentPane);

		txtMatkhauCu = new JPasswordField();
		txtMatkhauCu.setBounds(150, 15, 204, 30);
		pnlContentPane.add(txtMatkhauCu);

		txtMatkhauMoi = new JPasswordField();
		txtMatkhauMoi.setBounds(150, 50, 204, 30);
		pnlContentPane.add(txtMatkhauMoi);

		txtXacNhan = new JPasswordField();
		txtXacNhan.setBounds(150, 85, 204, 30);
		pnlContentPane.add(txtXacNhan);

		JLabel lblMKcu = new JLabel("MẬT KHẨU CŨ:");
		lblMKcu.setBounds(30, 20, 120, 20);
		lblMKcu.setFont(new Font("Arial", Font.BOLD, 13));
		pnlContentPane.add(lblMKcu);

		JLabel lblMKmoi = new JLabel("MẬT KHẨU MỚI:");
		lblMKmoi.setBounds(30, 55, 120, 20);
		lblMKmoi.setFont(new Font("Arial", Font.BOLD, 13));
		pnlContentPane.add(lblMKmoi);

		JLabel lblXn = new JLabel("XÁC NHẬN:");
		lblXn.setBounds(30, 90, 120, 20);
		lblXn.setFont(new Font("Arial", Font.BOLD, 13));
		pnlContentPane.add(lblXn);

		btnDoiMatKhau = new JButton("ĐỔI MẬT KHẨU");
		btnDoiMatKhau.setBounds(110, 125, 180, 42);
		btnDoiMatKhau.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnDoiMatKhau.setIcon(new ImageIcon("image/matkhau.png"));
		btnDoiMatKhau.setForeground(Color.WHITE);
		btnDoiMatKhau.setBackground(new Color(79, 173, 84));
		btnDoiMatKhau.setFocusPainted(false);

		pnlContentPane.add(btnDoiMatKhau);

		btnDoiMatKhau.addActionListener(this);

	}

	public static void main(String[] args) {
		new FrameDoiMatKhau().setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnDoiMatKhau)) {
			String matKhauCu = txtMatkhauCu.getText().trim();
			String matKhauMoi = txtMatkhauMoi.getText().trim();
			String xacNhan = txtXacNhan.getText().trim();

			TaiKhoan tk = doimatkhau_dao.getTaiKhoanTheoTenTaiKhoan(FrameDangNhap.getTaiKhoan());
			if(!ktraMatKhau())
				return;
			else {
				if (taiKhoan_dao.updateDoiMatKhau(tk, matKhauMoi) == true) {
					JOptionPane.showMessageDialog(this, "Đổi mật khẩu thành công");
					dispose();
				} else {
					JOptionPane.showMessageDialog(this, "Đổi mật khẩu thất bại");
				}
			}
		}
	}

	public boolean ktraMatKhau() {
		String matKhauCu = txtMatkhauCu.getText().trim();
		String matKhauMoi = txtMatkhauMoi.getText().trim();
		String xacNhan = txtXacNhan.getText().trim();

		if (matKhauCu.equals("") || matKhauMoi.equals("") || xacNhan.equals("")) {
			JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ mật khẩu");
			return false;
		}

		TaiKhoan tk = doimatkhau_dao.getTaiKhoanTheoTenTaiKhoan(FrameDangNhap.getTaiKhoan());

		if (!matKhauCu.equals(tk.getMatKhau().trim())) {
			JOptionPane.showMessageDialog(this, "Mật khẩu cũ không chính xác");
			return false;
		}
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
