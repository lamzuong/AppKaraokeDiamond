package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import connectDB.ConnectDB;
import entity.HoaDon;
import entity.KhachHang;
import entity.NhanVien;
import entity.PhongHat;

public class DatPhong_DAO {
	public List<PhongHat> getTatCaPhongHatTrong() {
		List<PhongHat> dsph = new ArrayList<PhongHat>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "Select * from Phong where trangThai = 0 and daXoa = 0";
			Statement statement = con.createStatement();
			// Thực thi câu lệnh SQL trả về đối tượng ResultSet
			ResultSet rs = statement.executeQuery(sql);
			// Duyệt trên kết quả trả về
			while (rs.next()) {
				String maPhong = rs.getString(1);
				String tenPhong = rs.getString(2);
				double giaPhong = rs.getDouble(3);
				String loaiPhong = rs.getString(4);

				PhongHat ph = new PhongHat(maPhong, tenPhong, giaPhong, loaiPhong);

				dsph.add(ph);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dsph;
	}

	public List<PhongHat> getTatCaPhongHatTrongTheoLoaiVip() {
		List<PhongHat> dsph = new ArrayList<PhongHat>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "Select * from Phong where trangThai = 0 and loaiPhong = 'VIP' and daXoa = 0";
			Statement statement = con.createStatement();
			// Thực thi câu lệnh SQL trả về đối tượng ResultSet
			ResultSet rs = statement.executeQuery(sql);
			// Duyệt trên kết quả trả về
			while (rs.next()) {
				String maPhong = rs.getString(1).trim();
				String tenPhong = rs.getString(2).trim();
				double giaPhong = rs.getDouble(3);
				String loaiPhong = rs.getString(4).trim();
				PhongHat ph = new PhongHat(maPhong, tenPhong, giaPhong, loaiPhong);
				dsph.add(ph);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dsph;
	}

	public List<PhongHat> getTatCaPhongHatTrongTheoLoaiThuong() {
		List<PhongHat> dsph = new ArrayList<PhongHat>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "Select * from Phong where trangThai = 0 and loaiPhong = N'Thường' and daXoa = 0";
			Statement statement = con.createStatement();
			// Thực thi câu lệnh SQL trả về đối tượng ResultSet
			ResultSet rs = statement.executeQuery(sql);
			// Duyệt trên kết quả trả về
			while (rs.next()) {
				String maPhong = rs.getString(1).trim();
				String tenPhong = rs.getString(2).trim();
				double giaPhong = rs.getDouble(3);
				String loaiPhong = rs.getString(4).trim();
				PhongHat ph = new PhongHat(maPhong, tenPhong, giaPhong, loaiPhong);
				dsph.add(ph);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dsph;
	}

	public List<HoaDon> getTatCaPhongHatDaDat() {
		List<HoaDon> dshd = new ArrayList<HoaDon>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "select hd.maPhong,tenPhong,p.giaPhong,loaiPhong,thoiGianDatPhong,tenKhachHang\r\n"
					+ "from HoaDon hd join Phong p on hd.maPhong = p.maPhong join KhachHang kh on hd.maKhachHang = kh.maKhachHang\r\n"
					+ "where trangThai = 1 and tongTienThanhToan is null order by tenKhachHang";
			Statement statement = con.createStatement();
			// Thực thi câu lệnh SQL trả về đối tượng ResultSet
			ResultSet rs = statement.executeQuery(sql);
			// Duyệt trên kết quả trả về
			while (rs.next()) {
				String maPhong = rs.getString(1);
				String tenPhong = rs.getString(2);
				double giaPhong = rs.getDouble(3);
				String loaiPhong = rs.getString(4);
				Date thoiGianDatPhong = rs.getTimestamp(5);
				String tenKhachHang = rs.getString(6);
				KhachHang makh = new KhachHang("", tenKhachHang, "", true, "");
				PhongHat maPhongHat = new PhongHat(maPhong, tenPhong, giaPhong, loaiPhong);
				HoaDon hd = new HoaDon("", thoiGianDatPhong, thoiGianDatPhong, makh, new NhanVien(), maPhongHat, giaPhong);
				dshd.add(hd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dshd;
	}

	public String getMaKhachHangCuoi() {
		String ma = "";
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "select Top 1 * from KhachHang order by maKhachHang Desc";
			Statement statement = con.createStatement();
			// Thực thi câu lệnh SQL trả về đối tượng ResultSet
			ResultSet rs = statement.executeQuery(sql);
			// Duyệt trên kết quả trả về
			while (rs.next()) {
				ma = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ma;
	}

	public String getMaHoaDonCuoi() {
		String ma = "";
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "select top 1 * from HoaDon order by maHoaDon desc";
			Statement statement = con.createStatement();
			// Thực thi câu lệnh SQL trả về đối tượng ResultSet
			ResultSet rs = statement.executeQuery(sql);
			// Duyệt trên kết quả trả về
			while (rs.next()) {
				ma = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ma;
	}

	public boolean create(HoaDon hd) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			stmt = con.prepareStatement(
					"insert into HoaDon(maHoaDon,maKhachHang,maPhong,thoiGianDatPhong,giaPhong) values(?,?,?,?,?)");
			stmt.setString(1, hd.getMaHD());
			stmt.setString(2, hd.getMaKH().getMaKH());
			stmt.setString(3, hd.getMaPhong().getMaPhong());
			java.sql.Timestamp sqlDate = new java.sql.Timestamp(hd.getThoigianDatPhong().getTime());
			stmt.setTimestamp(4, sqlDate);
			stmt.setDouble(5, hd.getGiaPhong());
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

	public boolean delete(String maPhong) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			stmt = con.prepareStatement("delete HoaDon where maHoaDon = ?");
			stmt.setString(1, maPhong);
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

	public boolean deleteKH(String maKH) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			stmt = con.prepareStatement("delete KhachHang where maKhachHang = ?");
			stmt.setString(1, maKH);
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

	public int getSoLuongHDDVTheoMaHD(String maHD) {
		int soLuong = 0;
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement = null;
		try {
			String sql = "select count(*) from HoaDonDichVu where maHoaDon = ?";
			statement = con.prepareStatement(sql);
			statement.setString(1, maHD);
			// Thực thi câu lệnh SQL trả về đối tượng ResultSet
			ResultSet rs = statement.executeQuery();
			// Duyệt trên kết quả trả về
			while (rs.next()) {
				 soLuong = rs.getInt(1);
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
		return soLuong;
	}
	public int getSoLuongHDCuaKhachHang(String maKH) {
		int soLuong = 0;
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement = null;
		try {
			String sql = "select count(*) from HoaDon where maKhachHang = ?";
			statement = con.prepareStatement(sql);
			statement.setString(1, maKH);
			// Thực thi câu lệnh SQL trả về đối tượng ResultSet
			ResultSet rs = statement.executeQuery();
			// Duyệt trên kết quả trả về
			while (rs.next()) {
				 soLuong = rs.getInt(1);
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
		return soLuong;
	}
}
