package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import connectDB.ConnectDB;
import entity.DichVu;
import entity.HoaDon;
import entity.HoaDonDichVu;
import entity.KhachHang;
import entity.NhanVien;
import entity.PhongHat;

public class TraPhong_DAO {
	public HoaDon getHoaDonTheoMaPhong(String maPhongTra) {
		HoaDon hd = null;
		List<HoaDonDichVu> listHDDV = new ArrayList<HoaDonDichVu>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement = null;
		try {
			String sql = "select hd.giaPhong,thoiGianDatPhong,hd.maKhachHang,tenKhachHang,hd.maPhong,\r\n"
					+ "dv.maDichVu,hddv.giaTien,hddv.soLuong,donViTinh,tenDichVu,hd.maHoaDon\r\n"
					+ "from HoaDon hd left join HoaDonDichVu hddv on hd.maHoaDon = hddv.maHoaDon\r\n"
					+ "left join DichVu dv on hddv.maDichVu = dv.maDichVu join Phong p on hd.maPhong = p.maPhong\r\n"
					+ "join KhachHang k on hd.maKhachHang = k.maKhachHang\r\n"
					+ "where hd.maPhong = ? and tongTienThanhToan is null";
			statement = con.prepareStatement(sql);
			statement.setString(1, maPhongTra);
			// Thực thi câu lệnh SQL trả về đối tượng ResultSet
			ResultSet rs = statement.executeQuery();
			// Duyệt trên kết quả trả về
			while (rs.next()) {
				double giaPhong = rs.getDouble(1);
				Date thoiGianDatPhong = rs.getTimestamp(2);
				String maKH = rs.getString(3);
				String tenKhachHang = rs.getString(4);
				String maPhong = rs.getString(5);
				String maDV = rs.getString(6);
				double giaTien = rs.getDouble(7);
				int soLuongSuDung = rs.getInt(8);
				String donViTinh = rs.getString(9);
				String tenDV = rs.getString(10);
				String maHD = rs.getString(11);
				
				KhachHang maKhachHang = new KhachHang(maKH, tenKhachHang, "", true, "");
				PhongHat maPhongHat = new PhongHat(maPhong, "", giaPhong, "");
				DichVu maDichVu = new DichVu(maDV, tenDV, 0, donViTinh, giaTien);

				HoaDonDichVu hdDichVu = new HoaDonDichVu(hd, maDichVu, soLuongSuDung, giaTien);
				listHDDV.add(hdDichVu);
				hd = new HoaDon(maHD, thoiGianDatPhong, thoiGianDatPhong, maKhachHang, new NhanVien(), maPhongHat,
						 giaPhong, listHDDV);

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
		return hd;
	}

	public boolean update(String maHD, String maNV, Date thoiGianTraPhong, double soGio, double giaPhong, double tienPhong,
			double tienDV, double tongTien) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			stmt = con.prepareStatement("update HoaDon\r\n"
					+ "set maNhanVien=?,thoiGianTraPhong=?,tongSoGioThue=?,giaPhong=?,tongTienPhong=?,tongTienDichVu=?,tongTienThanhToan=?\r\n"
					+ "where maHoaDon=?");
			stmt.setString(1, maNV);

			java.sql.Timestamp sqlDate = new java.sql.Timestamp(thoiGianTraPhong.getTime());
			stmt.setTimestamp(2, sqlDate);
			stmt.setDouble(3, soGio);
			stmt.setDouble(4, giaPhong);
			stmt.setDouble(5, tienPhong);
			stmt.setDouble(6, tienDV);
			stmt.setDouble(7, tongTien);
			stmt.setString(8, maHD);
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

	public NhanVien getNhanVienSuDung(String tenTK) {
		NhanVien nv =null;	
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement = null;
		try {
			String sql = "select maNhanVien,tenNhanVien from NhanVien where taiKhoan=?";
			statement = con.prepareStatement(sql);
			statement.setString(1, tenTK);
			// Thực thi câu lệnh SQL trả về đối tượng ResultSet
			ResultSet rs = statement.executeQuery();
			// Duyệt trên kết quả trả về
			while (rs.next()) {
			
				String maNV = rs.getString(1);
				String tenNV = rs.getString(2);
				nv = new NhanVien(maNV, tenNV);

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
