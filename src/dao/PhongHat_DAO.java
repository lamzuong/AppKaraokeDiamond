package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.PhongHat;

public class PhongHat_DAO {
	public List<PhongHat> getTatCaPhongHat(){
		List<PhongHat> dsph = new ArrayList<PhongHat>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "Select * from Phong";
			Statement statement = con.createStatement();
			// Thực thi câu lệnh SQL trả về đối tượng ResultSet
			ResultSet rs = statement.executeQuery(sql);
			// Duyệt trên kết quả trả về
			while (rs.next()) {
				String maPhong = rs.getString(1);
				String tenPhong = rs.getString(2);
				double giaPhong = rs.getDouble(3);
				String loaiPhong = rs.getString(4);
				boolean trangThai = rs.getBoolean(5);
				PhongHat ph = new PhongHat(maPhong, tenPhong, giaPhong, loaiPhong, trangThai);
				dsph.add(ph);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dsph;
	}
	public List<PhongHat> getTatCaPhongHatTonTai(){
		List<PhongHat> dsph = new ArrayList<PhongHat>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "Select * from Phong where daXoa = 0";
			Statement statement = con.createStatement();
			// Thực thi câu lệnh SQL trả về đối tượng ResultSet
			ResultSet rs = statement.executeQuery(sql);
			// Duyệt trên kết quả trả về
			while (rs.next()) {
				String maPhong = rs.getString(1);
				String tenPhong = rs.getString(2);
				double giaPhong = rs.getDouble(3);
				String loaiPhong = rs.getString(4);
				boolean trangThai = rs.getBoolean(5);
				PhongHat ph = new PhongHat(maPhong, tenPhong, giaPhong, loaiPhong, trangThai);
				dsph.add(ph);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dsph;
	}
	public boolean create(PhongHat ph) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			stmt = con.prepareStatement("insert into Phong values(?,?,?,?,?,?)");
			stmt.setString(1, ph.getMaPhong());
			stmt.setString(2, ph.getTenPhong());
			stmt.setDouble(3, ph.getGiaPhong());
			stmt.setString(4, ph.getLoaiPhong());
			stmt.setBoolean(5, ph.isTrangThai());
			stmt.setBoolean(6, false);
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
			stmt = con.prepareStatement("update Phong set daXoa = 1 where maPhong = ?");
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
	public boolean update(PhongHat ph) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			stmt = con.prepareStatement("update Phong set tenPhong=?, giaPhong=?, loaiPhong=? where maPhong=?");
			stmt.setString(1, ph.getTenPhong());
			stmt.setDouble(2, ph.getGiaPhong());
			stmt.setString(3, ph.getLoaiPhong());
			stmt.setString(4, ph.getMaPhong());
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
	public boolean updateTrangThai(String id, boolean trangThai) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			stmt = con.prepareStatement("update Phong set trangThai = ? where maPhong = ?");
			stmt.setBoolean(1, trangThai);
			stmt.setString(2, id);
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
	public List<PhongHat> getPhongTheoTen(String ten){
		ArrayList<PhongHat> dsPhong = new ArrayList<PhongHat>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement = null;
		try {
			String sql = "select * from Phong where tenPhong = ?";
			statement = con.prepareStatement(sql);
			statement.setString(1, ten);
			// Thực thi câu lệnh SQL trả về đối tượng ResultSet
			ResultSet rs = statement.executeQuery();
			// Duyệt trên kết quả trả về
			while (rs.next()) {
				String maPhong = rs.getString(1);
				String tenPhong = rs.getString(2);
				double giaPhong = rs.getDouble(3);
				String loaiPhong = rs.getString(4);
				boolean trangThai = rs.getBoolean(5);
				PhongHat ph = new PhongHat(maPhong, tenPhong, giaPhong, loaiPhong, trangThai);
				dsPhong.add(ph);
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
		return dsPhong;
	}
	public List<PhongHat> getPhongTheoLoaiPhong(String tenLoaiPhong){
		ArrayList<PhongHat> dsPhong = new ArrayList<PhongHat>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement = null;
		try {
			String sql = "select * from Phong where loaiPhong = ?";
			statement = con.prepareStatement(sql);
			statement.setString(1, tenLoaiPhong);
			// Thực thi câu lệnh SQL trả về đối tượng ResultSet
			ResultSet rs = statement.executeQuery();
			// Duyệt trên kết quả trả về
			while (rs.next()) {
				String maPhong = rs.getString(1);
				String tenPhong = rs.getString(2);
				double giaPhong = rs.getDouble(3);
				String loaiPhong = rs.getString(4);
				boolean trangThai = rs.getBoolean(5);
				PhongHat ph = new PhongHat(maPhong, tenPhong, giaPhong, loaiPhong, trangThai);
				dsPhong.add(ph);
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
		return dsPhong;
	}
	public List<PhongHat> getPhongTheoTrangThai(boolean trangthai){
		ArrayList<PhongHat> dsPhong = new ArrayList<PhongHat>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement = null;
		try {
			String sql = "select * from Phong where trangThai = ?";
			statement = con.prepareStatement(sql);
			statement.setBoolean(1, trangthai);
			// Thực thi câu lệnh SQL trả về đối tượng ResultSet
			ResultSet rs = statement.executeQuery();
			// Duyệt trên kết quả trả về
			while (rs.next()) {
				String maPhong = rs.getString(1);
				String tenPhong = rs.getString(2);
				double giaPhong = rs.getDouble(3);
				String loaiPhong = rs.getString(4);
				boolean trangThai = rs.getBoolean(5);
				PhongHat ph = new PhongHat(maPhong, tenPhong, giaPhong, loaiPhong, trangThai);
				dsPhong.add(ph);
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
		return dsPhong;
	}
}
