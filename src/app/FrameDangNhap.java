package app;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import connectDB.ConnectDB;
import dao.TaiKhoan_DAO;
import entity.TaiKhoan;

public class FrameDangNhap extends JFrame implements ActionListener, KeyListener, MouseListener {
	private static JTextField txtTaiKhoan;
	private JPasswordField txtMatKhau;
	private JButton btnDangNhap;
	private TaiKhoan_DAO taikhoan_dao;

	public FrameDangNhap() {
		// khởi tạo kết nối đến CSDL
		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		taikhoan_dao = new TaiKhoan_DAO();
		// ---------------------------
		setTitle("ĐĂNG NHẬP");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(290, 320);
		setLocationRelativeTo(null);
		ImageIcon icon = new ImageIcon("image/logodark.png");
		setIconImage(icon.getImage());

		JPanel pnlContentPane = new JPanel();
		pnlContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		pnlContentPane.setBackground(Color.WHITE);
		pnlContentPane.setLayout(null);
		setContentPane(pnlContentPane);
		
		JLabel lblTieuDe = new JLabel("ĐĂNG NHẬP");
		lblTieuDe.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblTieuDe.setForeground(Color.BLUE);
		lblTieuDe.setBounds(75, 14, 157, 23);
		pnlContentPane.add(lblTieuDe);
		/*
		 * Tài khoản, mật khẩu
		 */
		JLabel lblTaiKhoan = new JLabel("Tên đăng nhập");
		lblTaiKhoan.setBounds(30, 35, 100, 43);
		lblTaiKhoan.setFont(new Font("Arial", Font.PLAIN, 13));
		pnlContentPane.add(lblTaiKhoan);

		txtTaiKhoan = new JTextField("QL10000001");
		txtTaiKhoan.setBounds(30, 70, 220, 35);
		txtTaiKhoan.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		txtTaiKhoan.setBackground(new Color(245, 245, 245));
		pnlContentPane.add(txtTaiKhoan);

		JLabel lblMatKhau = new JLabel("Mật khẩu");
		lblMatKhau.setBounds(30, 105, 70, 43);
		lblMatKhau.setFont(new Font("Arial", Font.PLAIN, 13));
		pnlContentPane.add(lblMatKhau);

		txtMatKhau = new JPasswordField("123");
		txtMatKhau.setBounds(30, 140, 220, 35);
		txtMatKhau.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		txtMatKhau.setBackground(new Color(245, 245, 245));
		pnlContentPane.add(txtMatKhau);
		/*
		 * Chức năng
		 */
		btnDangNhap = new JButton("ĐĂNG NHẬP");
		btnDangNhap.setBounds(30, 190, 220, 40);
		btnDangNhap.setForeground(new Color(255, 255, 255));
		btnDangNhap.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnDangNhap.setBackground(new Color(24, 119, 242));
		btnDangNhap.setFocusPainted(false);
		pnlContentPane.add(btnDangNhap);

		JLabel lblQuenMatKhau = new JLabel("<HTML><U>Quên mật khẩu?</U></HTML>");
		lblQuenMatKhau.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblQuenMatKhau.setFont(new Font("Tahoma", Font.ITALIC, 11));
		lblQuenMatKhau.setForeground(Color.BLUE);
		lblQuenMatKhau.setBounds(95, 230, 135, 43);
		pnlContentPane.add(lblQuenMatKhau);

		txtTaiKhoan.addKeyListener(this);
		txtMatKhau.addKeyListener(this);
		btnDangNhap.addActionListener(this);
		lblQuenMatKhau.addMouseListener(this);
	}

	public static void main(String[] args) {
		new FrameDangNhap().setVisible(true);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnDangNhap)) {
			String taikhoan = txtTaiKhoan.getText();
			String matkhau = txtMatKhau.getText();

			int flag = 0;
			List<TaiKhoan> listTK = taikhoan_dao.getalltbTaiKhoan();
			for (TaiKhoan tk : listTK) {
				if (tk.getTenTaiKhoan().trim().equals(taikhoan) && tk.getMatKhau().trim().equals(matkhau)) {
					flag = 1;
					break;
				}
			}
			if (flag == 0) {
				JOptionPane.showMessageDialog(this, "Đăng nhập thất bại!!!", "Lỗi",
						JOptionPane.ERROR_MESSAGE);
				txtTaiKhoan.requestFocus();
				return;
			} else {
				if (taikhoan.substring(0, 2).equals("LT")) {
					GUI_NhanVien guiNV = new GUI_NhanVien();
					guiNV.setVisible(true);
					guiNV.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
					guiNV.setLocationRelativeTo(null);
					guiNV.setExtendedState(JFrame.MAXIMIZED_BOTH);
					dispose();
				}
				if (taikhoan.substring(0, 2).equals("QL") || taikhoan.substring(0, 2).equals("CQ")) {
					GUI_QuanLy guiQL;
					try {
						guiQL = new GUI_QuanLy();
						guiQL.setVisible(true);
						guiQL.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
						guiQL.setLocationRelativeTo(null);
						guiQL.setExtendedState(JFrame.MAXIMIZED_BOTH);
						dispose();
					} catch (ParseException e1) {
						e1.printStackTrace();
					}

				}
			}
		}
	}

	public static String getTaiKhoan() {
		return txtTaiKhoan.getText();
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			btnDangNhap.doClick();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		FrameXacNhanTaiKhoan frameXN = new FrameXacNhanTaiKhoan();
		frameXN.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		frameXN.setVisible(true);
		frameXN.setLocationRelativeTo(null);
		dispose();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

}
