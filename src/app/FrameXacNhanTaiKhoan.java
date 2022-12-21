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
import javax.swing.JTextField;

import connectDB.ConnectDB;
import dao.XacNhanTaiKhoan_DAO;
import entity.NhanVien;

public class FrameXacNhanTaiKhoan extends JFrame implements ActionListener {
	private JTextField txtMaNV;
	private JTextField txtCMND;
	private JTextField txtTenTK;
	private JButton btnXacNhan;
	private XacNhanTaiKhoan_DAO xnTaikhoan_dao;

	public FrameXacNhanTaiKhoan() {
		// khởi tạo kết nối đến CSDL
		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		xnTaikhoan_dao = new XacNhanTaiKhoan_DAO();
		// ------------------------------

		setTitle("XÁC NHẬN TÀI KHOẢN");
		setSize(350, 220);
		setResizable(false);
		setLocationRelativeTo(null);
		ImageIcon icon = new ImageIcon("image/logodark.png");
		setIconImage(icon.getImage());

		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent evt) {
				new FrameDangNhap().setVisible(true);
			}
		});
		
		JPanel pnlContentPane = new JPanel();
		pnlContentPane.setBounds(15, 0, 410, 365);
		pnlContentPane.setBackground(new Color(219, 255, 255));
		pnlContentPane.setLayout(null);
		setContentPane(pnlContentPane);

		JLabel lblMaNV = new JLabel("MÃ NHÂN VIÊN: ");
		lblMaNV.setBounds(25, 20, 120, 20);
		lblMaNV.setFont(new Font("Arial", Font.BOLD, 13));
		pnlContentPane.add(lblMaNV);

		txtMaNV = new JTextField();
		txtMaNV.setBounds(150, 15, 160, 30);
		txtMaNV.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlContentPane.add(txtMaNV);

		JLabel lblCMND = new JLabel("CMND/CCCD: ");
		lblCMND.setBounds(25, 55, 120, 20);
		lblCMND.setFont(new Font("Arial", Font.BOLD, 13));
		pnlContentPane.add(lblCMND);

		txtCMND = new JTextField();
		txtCMND.setBounds(150, 50, 160, 30);
		txtCMND.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlContentPane.add(txtCMND);

		JLabel lblTenTK = new JLabel("TÊN TÀI KHOẢN:");
		lblTenTK.setBounds(25, 90, 120, 20);
		lblTenTK.setFont(new Font("Arial", Font.BOLD, 13));
		pnlContentPane.add(lblTenTK);

		txtTenTK = new JTextField();
		txtTenTK.setBounds(150, 85, 160, 30);
		txtTenTK.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlContentPane.add(txtTenTK);

		btnXacNhan = new JButton("XÁC NHẬN TÀI KHOẢN");
		btnXacNhan.setBounds(60, 125, 220, 42);
		btnXacNhan.setForeground(Color.WHITE);
		btnXacNhan.setBackground(new Color(79, 173, 84));
		btnXacNhan.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnXacNhan.setFocusPainted(false);
		btnXacNhan.setIcon(new ImageIcon("image/matkhau.png"));
		pnlContentPane.add(btnXacNhan);

		btnXacNhan.addActionListener(this);
	}

	public static void main(String[] args) {
		new FrameXacNhanTaiKhoan().setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();

		if (o.equals(btnXacNhan)) {
			String maNV = txtMaNV.getText().trim();
			String cmnd = txtCMND.getText().trim();
			String tenTK = txtTenTK.getText().trim();
			if (maNV.equals("") || cmnd.equals("") || tenTK.equals("")) {
				JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin");
				return;
			}
			NhanVien nv = xnTaikhoan_dao.getNhanVienTheoTenTaiKhoan(tenTK);
			if(nv != null) {
				if (!(maNV.equals(nv.getMaNV().trim()))) {
					JOptionPane.showMessageDialog(this, "Mã nhân viên không chính xác");
					return;
				}
				if (!(cmnd.equals(nv.getCmnd().trim()))) {
					JOptionPane.showMessageDialog(this, "Chứng minh nhân dân không chính xác");
					return;
				}
				JOptionPane.showMessageDialog(this, "Xác nhận thành công");
				new FrameDatLaiMatKhau(nv).setVisible(true);
				setVisible(false);
			}
			else {
				JOptionPane.showMessageDialog(this, "Tên tài khoản không chính xác");
				return;
			}
		}

	}
}
