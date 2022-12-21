package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.DichVu;

public class ThongKeTonKho_DAO {
	public ArrayList<DichVu> getTatCaDichVuGanHet(){
		ArrayList<DichVu> dsdv = new ArrayList<DichVu>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "select * from DichVu where soLuong<10 and soLuong>0 and daXoa = 0";
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
	
	public ArrayList<DichVu> getTatCaDichVuDaHet(){
		ArrayList<DichVu> dsdv = new ArrayList<DichVu>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "select * from DichVu where soLuong = 0 and daXoa = 0";
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
	
}	
