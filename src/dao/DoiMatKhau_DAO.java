package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connectDB.ConnectDB;
import entity.TaiKhoan;

public class DoiMatKhau_DAO {
	public TaiKhoan getTaiKhoanTheoTenTaiKhoan(String tenTK) {
		TaiKhoan tk = null;
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement = null;
		try {
			String sql = "select tk.taiKhoan,matKhau\r\n" + 
					"from NhanVien nv join TaiKhoan tk on nv.taiKhoan = tk.taiKhoan\r\n" + 
					"where nv.taiKhoan = ?";
			statement = con.prepareStatement(sql);
			statement.setString(1, tenTK);
			// Thực thi câu lệnh SQL trả về đối tượng ResultSet
			ResultSet rs = statement.executeQuery();
			// Duyệt trên kết quả trả về
			while (rs.next()) {
				tk = new TaiKhoan(rs.getString(1), rs.getString(2));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return tk;
	}
}
