package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.DichVu;

public class DichVu_DAO {
	public List<DichVu> getTatCaDichVu(){
		List<DichVu> dsdv = new ArrayList<DichVu>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "Select * from DichVu";
			Statement statement = con.createStatement();
			// Thực thi câu lệnh SQL trả về đối tượng ResultSet
			ResultSet rs = statement.executeQuery(sql);
			// Duyệt trên kết quả trả về
			while (rs.next()) {
				String maDichVu = rs.getString(1).trim();
				String tenDichVu = rs.getString(2).trim();
				int soLuong = rs.getInt(3);
				String donViTinh = rs.getString(4).trim();
				double giaTien = rs.getDouble(5);
				DichVu dv = new DichVu(maDichVu, tenDichVu, soLuong, donViTinh, giaTien);
				dsdv.add(dv);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dsdv;
	}
	public List<DichVu> getTatCaDichVuTonTai(){
		List<DichVu> dsdv = new ArrayList<DichVu>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "Select * from DichVu where daXoa = 0";
			Statement statement = con.createStatement();
			// Thực thi câu lệnh SQL trả về đối tượng ResultSet
			ResultSet rs = statement.executeQuery(sql);
			// Duyệt trên kết quả trả về
			while (rs.next()) {
				String maDichVu = rs.getString(1).trim();
				String tenDichVu = rs.getString(2).trim();
				int soLuong = rs.getInt(3);
				String donViTinh = rs.getString(4).trim();
				double giaTien = rs.getDouble(5);
				DichVu dv = new DichVu(maDichVu, tenDichVu, soLuong, donViTinh, giaTien);
				dsdv.add(dv);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dsdv;
	}
	public boolean create(DichVu dv) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			stmt = con.prepareStatement("insert into DichVu values(?,?,?,?,?,?)");
			stmt.setString(1, dv.getMaDichVu());
			stmt.setString(2, dv.getTenDichVu());
			stmt.setInt(3, dv.getSoLuong());
			stmt.setString(4, dv.getDonViTinh());
			stmt.setDouble(5, dv.getGiaTien());
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
			stmt = con.prepareStatement("update DichVu set daXoa = 1 where maDichVu = ?");
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
	
	public boolean update(DichVu dv) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			stmt = con.prepareStatement("update DichVu set tenDichVu=?, soLuong=?, donViTinh=?, giaTien=? where maDichVu=?");
			stmt.setString(1, dv.getTenDichVu());
			stmt.setInt(2, dv.getSoLuong());
			stmt.setString(3, dv.getDonViTinh());
			stmt.setDouble(4, dv.getGiaTien());
			stmt.setString(5, dv.getMaDichVu());
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
	public List<DichVu> getDichVuTheoTen(String ten){
		List<DichVu> dsdv = new ArrayList<DichVu>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement = null;
		try {
			String sql = "select * from DichVu where tenDichVu = ? and daXoa = 0";
			statement = con.prepareStatement(sql);
			statement.setString(1, ten);
			// Thực thi câu lệnh SQL trả về đối tượng ResultSet
			ResultSet rs = statement.executeQuery();
			// Duyệt trên kết quả trả về
			while (rs.next()) {
				String maDichVu = rs.getString(1);
				String tenDichVu = rs.getString(2);
				int soLuong = rs.getInt(3);
				String donViTinh = rs.getString(4);
				double giaTien = rs.getDouble(5);
				DichVu dv = new DichVu(maDichVu, tenDichVu, soLuong, donViTinh, giaTien);
				dsdv.add(dv);
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
		return dsdv;
	}
	public List<DichVu> getDichVuDaBan(String rad, java.sql.Date dateMin, java.sql.Date dateMax){
		List<DichVu> dsdv = new ArrayList<DichVu>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement = null;
		try {
			String sql = "select d.maDichVu,d.tenDichVu,d.giaTien,sum(dv.soLuong)\r\n" + 
					"from HoaDon hd join HoaDonDichVu dv on hd.maHoaDon = dv.maHoaDon\r\n" + 
					"join DichVu d on d.maDichVu = dv.maDichVu\r\n";
			if(rad.equals("Hôm nay")) {
				sql += "where CONVERT(date,thoiGianTraPhong) = CONVERT(date,GETDATE()) and daXoa = 0 and hd.tongTienThanhToan is not null\r\n";
				sql += "group by d.maDichVu,d.tenDichVu,d.giaTien\r\n";
				statement = con.prepareStatement(sql);
			}
				
			else if (rad.equals("Một tuần")) {
				sql += "where DATEDIFF(day,CONVERT(date,thoiGianTraPhong), CONVERT(date,GETDATE())) <= 7 and daXoa = 0 and hd.tongTienThanhToan is not null\r\n";
				sql += "group by d.maDichVu,d.tenDichVu,d.giaTien\r\n";
				statement = con.prepareStatement(sql);
			}
			else if (rad.equals("Một tháng")) {
				sql += "where DATEDIFF(day,CONVERT(date,thoiGianTraPhong), CONVERT(date,GETDATE())) <= 30 and daXoa = 0 and hd.tongTienThanhToan is not null\r\n";
				sql += "group by d.maDichVu,d.tenDichVu,d.giaTien\r\n";
				statement = con.prepareStatement(sql);
			}
			else if (rad.equals("Khác")) {
				if(dateMin == null && dateMax != null) {
					sql += "where convert(date,thoiGianTraPhong) <= ? and daXoa = 0 and hd.tongTienThanhToan is not null\r\n";
					sql += "group by d.maDichVu,d.tenDichVu,d.giaTien\r\n";
					statement = con.prepareStatement(sql);
					statement.setDate(1, dateMax);
				}
				else if(dateMax == null && dateMin != null) {
					sql += "where convert(date,thoiGianTraPhong) >= ? and daXoa = 0 and hd.tongTienThanhToan is not null\r\n";
					sql += "group by d.maDichVu,d.tenDichVu,d.giaTien\r\n";
					statement = con.prepareStatement(sql);
					statement.setDate(1, dateMin);
				}
				else if(dateMax == null && dateMin == null) {
					sql += "where daXoa = 0 and hd.tongTienThanhToan is not null\r\n";
					sql += "group by d.maDichVu,d.tenDichVu,d.giaTien\r\n";
					statement = con.prepareStatement(sql);
				}
				else if(dateMax != null && dateMin != null) {
					sql += "where convert(date,thoiGianTraPhong) >= ? and convert(date,thoiGianTraPhong) <= ? and daXoa = 0 and hd.tongTienThanhToan is not null\r\n";
					sql += "group by d.maDichVu,d.tenDichVu,d.giaTien\r\n";
					statement = con.prepareStatement(sql);
					statement.setDate(1, dateMin);
					statement.setDate(2, dateMax);
				}
			}	
			
			// Thực thi câu lệnh SQL trả về đối tượng ResultSet
			ResultSet rs = statement.executeQuery();
			// Duyệt trên kết quả trả về
			while (rs.next()) {
				String maDichVu = rs.getString(1);
				String tenDichVu = rs.getString(2);
				double giaTien = rs.getDouble(3);
				int soLuong = rs.getInt(4);
				DichVu dv = new DichVu(maDichVu, tenDichVu, soLuong, giaTien);
				dsdv.add(dv);
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
		return dsdv;
	}
	public List<String> getDichVuDangSuDung(){
		List<String> dsdv = new ArrayList<String>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement = null;
		try {
			String sql = "select distinct dv.maDichVu\r\n" + 
					"from HoaDon hd join HoaDonDichVu dv on hd.maHoaDon = dv.maHoaDon\r\n" + 
					"where hd.tongTienThanhToan is null\r\n";
			statement = con.prepareStatement(sql);
			// Thực thi câu lệnh SQL trả về đối tượng ResultSet
			ResultSet rs = statement.executeQuery();
			// Duyệt trên kết quả trả về
			while (rs.next()) {
				String maDichVu = rs.getString(1);
				dsdv.add(maDichVu);
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
		return dsdv;
	}
}
