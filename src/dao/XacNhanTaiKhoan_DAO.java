package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connectDB.ConnectDB;
import entity.NhanVien;
import entity.TaiKhoan;

public class XacNhanTaiKhoan_DAO {

	public NhanVien getNhanVienTheoTenTaiKhoan(String tenTK) {
		NhanVien nv = null;
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement = null;
		try {
			String sql = "select maNhanVien,cmnd,taiKhoan from NhanVien\r\n" + 
					"where taiKhoan= ?";
			statement = con.prepareStatement(sql);
			statement.setString(1, tenTK);
			// Thực thi câu lệnh SQL trả về đối tượng ResultSet
			ResultSet rs = statement.executeQuery();
			// Duyệt trên kết quả trả về
			while (rs.next()) {
				nv = new NhanVien(rs.getString(1), rs.getString(2),  new TaiKhoan(rs.getString(3)));
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
		return nv;
	}
}
