package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.TaiKhoan;

public class TaiKhoan_DAO {
	public List<TaiKhoan> getalltbTaiKhoan(){
		List<TaiKhoan> dstk = new ArrayList<TaiKhoan>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "select * from TaiKhoan";
			Statement statement = con.createStatement();
			// Thực thi câu lệnh SQL trả về đối tượng ResultSet
			ResultSet rs = statement.executeQuery(sql);
			// Duyệt trên kết quả trả về
			while (rs.next()) {
				String tenTaiKhoan = rs.getString(1);
				String matKhau = rs.getString(2);
				TaiKhoan tk = new TaiKhoan(tenTaiKhoan, matKhau);
				dstk.add(tk);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dstk;
	}
	public boolean create(TaiKhoan tk) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			stmt = con.prepareStatement("insert into TaiKhoan values(?,?)");
			stmt.setString(1, tk.getTenTaiKhoan());
			stmt.setString(2, tk.getMatKhau());
			
			n = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return n > 0;
	}
	public boolean delete(String id) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			stmt = con.prepareStatement("delete TaiKhoan where taiKhoan = ?");
			stmt.setString(1, id);
			n = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return n > 0;
	}
	public boolean updateDoiMatKhau(TaiKhoan taikhoan, String matKhauMoi) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			stmt = con.prepareStatement("update TaiKhoan set matKhau=? where taiKhoan=?");

			stmt.setString(1, matKhauMoi);
			stmt.setString(2, taikhoan.getTenTaiKhoan());
			
			n = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return n > 0;
	}
}
