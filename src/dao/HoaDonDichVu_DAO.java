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
import entity.HoaDon;
import entity.HoaDonDichVu;

public class HoaDonDichVu_DAO {
	public List<HoaDonDichVu> getTatCaHoaDonDV(){
		List<HoaDonDichVu> dshd = new ArrayList<HoaDonDichVu>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "Select * from HoaDonDichVu";
			Statement statement = con.createStatement();
			// Thực thi câu lệnh SQL trả về đối tượng ResultSet
			ResultSet rs = statement.executeQuery(sql);
			// Duyệt trên kết quả trả về
			while (rs.next()) {
				String maHD = rs.getString(1);
				String maDV = rs.getString(2);
				int soLuong = rs.getInt(3);
				double giaTien = rs.getDouble(4);
				HoaDon hd = new HoaDon(maHD);
				DichVu dv = new DichVu(maDV);
				HoaDonDichVu hddv = new HoaDonDichVu(hd, dv, soLuong, giaTien);
				dshd.add(hddv);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dshd;
	}
	public boolean create(HoaDonDichVu hddv) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			stmt = con.prepareStatement("insert into HoaDonDichVu values(?,?,?,?,?)");
			stmt.setString(1, hddv.getMaHD().getMaHD());
			stmt.setString(2, hddv.getMaDV().getMaDichVu());
			stmt.setInt(3, hddv.getSoLuong());
			stmt.setDouble(4, hddv.getGiaTien());
			stmt.setDouble(5, hddv.getThanhTien());
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
	public boolean delete(String maDichVu) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			stmt = con.prepareStatement("delete HoaDonDichVu where maDichVu = ?");
			stmt.setString(1, maDichVu);
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
	public boolean updateSoLuong(HoaDonDichVu hd) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			stmt = con.prepareStatement("update HoaDonDichVu set soLuong=?, thanhTien=? where maHoaDon=? and maDichVu=?");
			stmt.setInt(1, hd.getSoLuong());
			stmt.setDouble(2, hd.getThanhTien());
			stmt.setString(3, hd.getMaHD().getMaHD());
			stmt.setString(4, hd.getMaDV().getMaDichVu());
			
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
	public List<HoaDonDichVu> getHoaDonDVTheoMaHD(String maHoaDon){
		List<HoaDonDichVu> dshd = new ArrayList<HoaDonDichVu>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement = null;
		try {
			String sql = "select * from HoaDonDichVu where maHoaDon = ?";
			statement = con.prepareStatement(sql);
			statement.setString(1, maHoaDon);
			// Thực thi câu lệnh SQL trả về đối tượng ResultSet
			ResultSet rs = statement.executeQuery();
			// Duyệt trên kết quả trả về
			while (rs.next()) {
				String maHD = rs.getString(1).trim();
				String maDV = rs.getString(2).trim();
				int soLuong = rs.getInt(3);
				double giaTien = rs.getDouble(4);
				HoaDon hd = new HoaDon(maHD);
				DichVu dv = new DichVu(maDV);
				HoaDonDichVu hddv = new HoaDonDichVu(hd, dv, soLuong, giaTien);
				dshd.add(hddv);
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
		return dshd;
	}

	public List<HoaDonDichVu> getHoaDonDVTheoMaHDLenTable(String maHoaDon){
		List<HoaDonDichVu> dshd = new ArrayList<HoaDonDichVu>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement = null;
		try {
			String sql = "select dv.maDichVu,dv.tenDichVu,hd.soLuong,dv.donViTinh,dv.giaTien,hd.thanhTien,hd.maHoaDon\r\n" + 
					"from HoaDonDichVu hd join DichVu dv on hd.maDichVu = dv.maDichVu\r\n" + 
					" where hd.maHoaDon = ?";
			statement = con.prepareStatement(sql);
			statement.setString(1, maHoaDon);
			// Thực thi câu lệnh SQL trả về đối tượng ResultSet
			ResultSet rs = statement.executeQuery();
			// Duyệt trên kết quả trả về
			while (rs.next()) {
				String maDV = rs.getString(1).trim();
				String tenDV = rs.getString(2).trim();
				int soLuong = rs.getInt(3);
				String donViTinh = rs.getString(4).trim();
				double giaTien = rs.getDouble(5);
				String maHD = rs.getString(7).trim();
				HoaDon hd = new HoaDon(maHD);
				DichVu dv = new DichVu(maDV, tenDV, soLuong, donViTinh, giaTien);
				HoaDonDichVu hddv = new HoaDonDichVu(hd, dv, soLuong, giaTien);
				dshd.add(hddv);
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
		return dshd;
	}
}
