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
import dao.ThongKeTonKho_DAO;
import entity.DichVu;

public class FormThemDV extends JFrame implements ActionListener,KeyListener {
	private JButton btnThem;
	private JTextField txtTenDV;
	private JTextField txtGiaDV;
	private JComboBox<String> cmbDonViTinh;
	private JTextField txtSoLuong;
	private DichVu_DAO dichvu_dao;
	private ThongKeTonKho_DAO thongke_DAO;

	public FormThemDV() {
		// khởi tạo kết nối đến CSDL
		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dichvu_dao = new DichVu_DAO();
		thongke_DAO = new ThongKeTonKho_DAO();
		//--------------
		setTitle("THÊM DỊCH VỤ");
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

		JLabel lblGiaDV = new JLabel("GIÁ TIỀN :");
		lblGiaDV.setBounds(50, 90, 150, 20);
		lblGiaDV.setFont(new Font("Arial", Font.BOLD, 13));
		pnlContentPane.add(lblGiaDV);
		txtGiaDV = new JTextField("100000");
		txtGiaDV.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		txtGiaDV.setBounds(160, 82, 150, 30);
		pnlContentPane.add(txtGiaDV);

		JLabel lblDonViTinh = new JLabel("ĐƠN VỊ TÍNH: ");
		lblDonViTinh.setBounds(50, 144, 150, 14);
		lblDonViTinh.setFont(new Font("Arial", Font.BOLD, 13));
		pnlContentPane.add(lblDonViTinh);
		String[] loai = { "Phần", "Lon", "Ly" };
		cmbDonViTinh = new JComboBox<String>(loai);
		cmbDonViTinh.setBounds(160, 136, 150, 30);
		cmbDonViTinh.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlContentPane.add(cmbDonViTinh);

		JLabel lblSoLuong = new JLabel("SỐ LƯỢNG: ");
		lblSoLuong.setBounds(50, 198, 150, 14);
		lblSoLuong.setFont(new Font("Arial", Font.BOLD, 13));
		pnlContentPane.add(lblSoLuong);
		txtSoLuong = new JTextField("20");
		txtSoLuong.setBounds(160, 190, 150, 30);
		txtSoLuong.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlContentPane.add(txtSoLuong);

		btnThem = new JButton("THÊM DỊCH VỤ MỚI", new ImageIcon("image/them.png"));
		btnThem.setBounds(70, 240, 220, 45);
		btnThem.setForeground(Color.WHITE);
		btnThem.setBackground(new Color(79, 173, 84));
		btnThem.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnThem.setFocusPainted(false);
		pnlContentPane.add(btnThem);
		
		btnThem.addActionListener(this);
		
		txtGiaDV.addKeyListener(this);
		txtSoLuong.addKeyListener(this);
		
		txtTenDV.addKeyListener(this);
		cmbDonViTinh.addKeyListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if(o.equals(btnThem)) {
			if (!validInput() || trungTen()) {
				return;
			} else {
				String maDV;
				List<DichVu> listDV = dichvu_dao.getTatCaDichVu();
				if(listDV.size() == 0)
					maDV = "DV10000001";
				else {
					String maDVCuoi = listDV.get(listDV.size()-1).getMaDichVu().trim();
					int layMaSo = Integer.parseInt(maDVCuoi.substring(2, maDVCuoi.length()));
					maDV = "DV" + (layMaSo + 1);
				}
				String tenDV = txtTenDV.getText();
				double giaDV = Double.parseDouble(txtGiaDV.getText());
				int soLuong = Integer.parseInt(txtSoLuong.getText());
				String donViTinh = cmbDonViTinh.getSelectedItem().toString();
				DichVu dv = new DichVu(maDV, tenDV, soLuong, donViTinh, giaDV);
				dichvu_dao.create(dv);
				
				FrameDichVu.xoaHetDL();
				FrameDichVu.docDuLieuDatabaseVaoTable();
				int itemCount = FrameDichVu.cmbTenDV.getItemCount();
				for (int i = 0; i < itemCount; i++) {
					FrameDichVu.cmbTenDV.removeItemAt(0);
				}
				int itemCount2 = FrameDichVu.cmbTenDV.getItemCount();
				for (int i = 0; i < itemCount2; i++) {
					FrameDichVu.cmbMaDV.removeItemAt(0);
				}
				FrameDichVu.docDuLieuVaoCmbTen();
				FrameDichVu.docDuLieuVaoCmbMaDV();
				FrameDichVu.table.getSelectionModel().clearSelection();

				dispose();
			}
		}
	}
	private boolean trungTen() {
		List<DichVu> listDV = dichvu_dao.getTatCaDichVuTonTai();
		String tenDV = txtTenDV.getText();
		for (DichVu dv : listDV) {
			if(dv.getTenDichVu().trim().equalsIgnoreCase(tenDV)) {
				JOptionPane.showMessageDialog(this, "Dịch vụ này đã có trong danh sách", "Lỗi",
						JOptionPane.ERROR_MESSAGE);
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
				JOptionPane.showMessageDialog(this, "Tên dịch vụ không chứa ký tự đặc biệt", "Lỗi",
						JOptionPane.ERROR_MESSAGE);
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(this, "Tên dịch vụ không được để trống", "Lỗi",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if (giaDV.trim().length() > 0) {
			try {
				double x = Double.parseDouble(giaDV);
				if (x <= 0) {
					JOptionPane.showMessageDialog(this, "Giá dịch vụ phải lớn hơn 0", "Lỗi",
							JOptionPane.ERROR_MESSAGE);
					return false;
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "Error: Giá dịch vụ phải nhập số", "Lỗi",
						JOptionPane.ERROR_MESSAGE);
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(this, "Giá dịch vụ không được để trống", "Lỗi",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if (soLuong.trim().length() > 0) {
			try {
				int x = Integer.parseInt(soLuong);
				if (x <= 0) {
					JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0", "Lỗi",
							JOptionPane.ERROR_MESSAGE);
					return false;
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "Error: Số lượng phải nhập số", "Lỗi",
						JOptionPane.ERROR_MESSAGE);
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(this, "Số lượng không được để trống", "Lỗi",
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
