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
import entity.DichVu;
import entity.HoaDon;
import entity.HoaDonDichVu;
import entity.KhachHang;
import entity.NhanVien;
import entity.PhongHat;

public class HoaDon_DAO {
	public List<HoaDon> getTatCaHoaDon(){
		List<HoaDon> dshd = new ArrayList<HoaDon>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "select hd.maHoaDon,hd.maKhachHang,k.tenKhachHang,hd.maPhong,p.tenPhong,hd.maNhanVien,nv.tenNhanVien,hd.thoiGianDatPhong,hd.thoiGianTraPhong,hd.giaPhong\r\n"+
					"from HoaDon hd join Phong p on hd.maPhong = p.maPhong\r\n" + 
					"join KhachHang k on hd.maKhachHang = k.maKhachHang\r\n" + 
					"join NhanVien nv on nv.maNhanVien = hd.maNhanVien\r\n" + 
					"where tongTienThanhToan is not NULL";
			Statement statement = con.createStatement();
			// Thực thi câu lệnh SQL trả về đối tượng ResultSet
			ResultSet rs = statement.executeQuery(sql);
			// Duyệt trên kết quả trả về
			while (rs.next()) {
				String maHD = rs.getString(1);
				String maKH = rs.getString(2);
				String tenKH = rs.getString(3).trim();
				String maPH = rs.getString(4);
				String tenPH = rs.getString(5).trim();
				String maNV = rs.getString(6);
				String tenNV = rs.getString(7).trim();
				Date thoiGianDatPhong = rs.getTimestamp(8);
				Date thoiGianTraPhong = rs.getTimestamp(9);
				double giaPhong = rs.getDouble(10);
				List<HoaDonDichVu> list = getHDDVtheoMaHD(maHD);
				KhachHang kh = new KhachHang(maKH,tenKH);
				PhongHat ph = new PhongHat(maPH,tenPH,giaPhong);
				NhanVien nv = new NhanVien(maNV,tenNV);
				HoaDon hd = new HoaDon(maHD, thoiGianDatPhong, thoiGianTraPhong, kh, nv, ph, giaPhong,list);
				dshd.add(hd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dshd;
	}
	public HoaDon getHoaDonTheoMaHDKhongCoNV(String maHoaDon) {
		HoaDon hd = null;
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement = null;
		try {
			String sql = "select hd.maHoaDon,hd.maKhachHang,k.tenKhachHang,hd.maPhong,p.tenPhong,hd.giaPhong\r\n" + 
					"from HoaDon hd   join Phong p on hd.maPhong = p.maPhong \r\n" + 
					"join KhachHang k on hd.maKhachHang = k.maKhachHang\r\n" + 
					"where hd.maHoaDon = ?";
			statement = con.prepareStatement(sql);
			statement.setString(1, maHoaDon);
			// Thực thi câu lệnh SQL trả về đối tượng ResultSet
			ResultSet rs = statement.executeQuery();
			// Duyệt trên kết quả trả về
			while (rs.next()) {
				String maHD = rs.getString(1);
				String maKH = rs.getString(2);
				String tenKH = rs.getString(3).trim();
				String maPH = rs.getString(4);
				String tenPH = rs.getString(5).trim();
				double giaPhong = rs.getDouble(6);
				List<HoaDonDichVu> list = getHDDVtheoMaHD(maHD);
				KhachHang kh = new KhachHang(maKH,tenKH);
				PhongHat ph = new PhongHat(maPH,tenPH,giaPhong);
				hd = new HoaDon(maHD, new Date(), new Date(), kh, null, ph, giaPhong,list);
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
	public HoaDon getHoaDonTheoMaHD(String maHoaDon) {
		HoaDon hd = null;
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement = null;
		try {
			String sql = "select hd.maHoaDon,hd.maKhachHang,k.tenKhachHang,hd.maPhong,p.tenPhong,hd.maNhanVien,nv.tenNhanVien,hd.thoiGianDatPhong,hd.thoiGianTraPhong,hd.giaPhong\r\n"+
					"from HoaDon hd join Phong p on hd.maPhong = p.maPhong\r\n" + 
					"join KhachHang k on hd.maKhachHang = k.maKhachHang\r\n" + 
					"join NhanVien nv on nv.maNhanVien = hd.maNhanVien\r\n" +
					"where maHoaDon = ?";
			statement = con.prepareStatement(sql);
			statement.setString(1, maHoaDon);
			// Thực thi câu lệnh SQL trả về đối tượng ResultSet
			ResultSet rs = statement.executeQuery();
			// Duyệt trên kết quả trả về
			while (rs.next()) {
				String maHD = rs.getString(1);
				String maKH = rs.getString(2);
				String tenKH = rs.getString(3).trim();
				String maPH = rs.getString(4);
				String tenPH = rs.getString(5).trim();
				String maNV = rs.getString(6);
				String tenNV = rs.getString(7).trim();
				Date thoiGianDatPhong = rs.getTimestamp(8);
				Date thoiGianTraPhong = rs.getTimestamp(9);
				double giaPhong = rs.getDouble(10);
				List<HoaDonDichVu> list = getHDDVtheoMaHD(maHD);
				KhachHang kh = new KhachHang(maKH,tenKH);
				PhongHat ph = new PhongHat(maPH,tenPH,giaPhong);
				NhanVien nv = new NhanVien(maNV,tenNV);
				hd = new HoaDon(maHD, thoiGianDatPhong, thoiGianTraPhong, kh, nv, ph, giaPhong,list);
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
	public List<HoaDon> getTatCaHoaDonChuaTinhTien(){
		List<HoaDon> dshd = new ArrayList<HoaDon>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "select hd.maHoaDon,hd.maKhachHang,hd.maPhong,hd.maNhanVien,hd.thoiGianDatPhong,hd.thoiGianTraPhong,p.tenPhong,hd.giaPhong\r\n" + 
					"from HoaDon hd join Phong p on hd.maPhong = p.maPhong\r\n" + 
					" where tongTienThanhToan is NULL";
			Statement statement = con.createStatement();
			// Thực thi câu lệnh SQL trả về đối tượng ResultSet
			ResultSet rs = statement.executeQuery(sql);
			// Duyệt trên kết quả trả về
			while (rs.next()) {
				String maHD = rs.getString(1);
				String maKH = rs.getString(2);
				String maPH = rs.getString(3);
				String maNV = rs.getString(4);
				Date thoiGianDatPhong = rs.getTimestamp(5);
				Date thoiGianTraPhong = rs.getTimestamp(6);
				String tenPhong = rs.getString(7);
				double giaPhong = rs.getDouble(8);
				KhachHang kh = new KhachHang(maKH.trim());
				PhongHat ph = new PhongHat(maPH.trim(),tenPhong.trim());
				NhanVien nv = new NhanVien(maNV);
				HoaDon hd = new HoaDon(maHD.trim(), thoiGianDatPhong, thoiGianTraPhong, kh, nv, ph, giaPhong);
				dshd.add(hd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dshd;
	}
	public List<HoaDonDichVu> getHDDVtheoMaHD(String ma){
		List<HoaDonDichVu> dshd = new ArrayList<HoaDonDichVu>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement = null;
		try {
			String sql = "select h.maHoaDon,h.maDichVu,h.soLuong,d.tenDichVu,h.giaTien,d.donViTinh,d.soLuong"
					+ " from HoaDonDichVu h join DichVu d on d.maDichVu = h.maDichVu where maHoaDon = ?";
			statement = con.prepareStatement(sql);
			statement.setString(1, ma);
			// Thực thi câu lệnh SQL trả về đối tượng ResultSet
			ResultSet rs = statement.executeQuery();
			// Duyệt trên kết quả trả về
			while (rs.next()) {
				String maHD = rs.getString(1);
				String maDV = rs.getString(2);
				int soLuong = rs.getInt(3);
				String tenDV = rs.getString(4);
				double giaTien = rs.getDouble(5);
				String donViTinh = rs.getString(6);
				int soLuongTon = rs.getInt(7);
				HoaDon hd = new HoaDon(maHD);
				DichVu dv = new DichVu(maDV,tenDV,soLuongTon,donViTinh,giaTien);
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
	public List<HoaDon> getHoaDonHomNay() {
		List<HoaDon> dshd = new ArrayList<HoaDon>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "select hd.maHoaDon,hd.maKhachHang,k.tenKhachHang,hd.maPhong,p.tenPhong,hd.maNhanVien,nv.tenNhanVien,hd.thoiGianDatPhong,hd.thoiGianTraPhong,hd.giaPhong\r\n"+
					"from HoaDon hd join Phong p on hd.maPhong = p.maPhong\r\n" + 
					"join KhachHang k on hd.maKhachHang = k.maKhachHang\r\n" + 
					"join NhanVien nv on nv.maNhanVien = hd.maNhanVien\r\n" + 
					"where CONVERT(date,thoiGianTraPhong) = CONVERT(date,GETDATE()) and thoiGianTraPhong is not null";
			Statement statement = con.createStatement();
			// Thực thi câu lệnh SQL trả về đối tượng ResultSet
			ResultSet rs = statement.executeQuery(sql);
			// Duyệt trên kết quả trả về
			while (rs.next()) {
				String maHD = rs.getString(1);
				String maKH = rs.getString(2);
				String tenKH = rs.getString(3).trim();
				String maPH = rs.getString(4);
				String tenPH = rs.getString(5).trim();
				String maNV = rs.getString(6);
				String tenNV = rs.getString(7).trim();
				Date thoiGianDatPhong = rs.getTimestamp(8);
				Date thoiGianTraPhong = rs.getTimestamp(9);
				double giaPhong = rs.getDouble(10);
				List<HoaDonDichVu> list = getHDDVtheoMaHD(maHD);
				KhachHang kh = new KhachHang(maKH,tenKH);
				PhongHat ph = new PhongHat(maPH,tenPH,giaPhong);
				NhanVien nv = new NhanVien(maNV,tenNV);
				HoaDon hd = new HoaDon(maHD, thoiGianDatPhong, thoiGianTraPhong, kh, nv, ph, giaPhong,list);
				dshd.add(hd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dshd;
	}
	public List<HoaDon> getHoaDon1Tuan() {
		List<HoaDon> dshd = new ArrayList<HoaDon>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "select hd.maHoaDon,hd.maKhachHang,k.tenKhachHang,hd.maPhong,p.tenPhong,hd.maNhanVien,nv.tenNhanVien,hd.thoiGianDatPhong,hd.thoiGianTraPhong,hd.giaPhong\r\n"+
					"from HoaDon hd join Phong p on hd.maPhong = p.maPhong\r\n" + 
					"join KhachHang k on hd.maKhachHang = k.maKhachHang\r\n" + 
					"join NhanVien nv on nv.maNhanVien = hd.maNhanVien\r\n" + 
					"where DATEDIFF(day,CONVERT(date,thoiGianTraPhong) , CONVERT(date,GETDATE())) <= 7 and thoiGianTraPhong is not null";
			Statement statement = con.createStatement();
			// Thực thi câu lệnh SQL trả về đối tượng ResultSet
			ResultSet rs = statement.executeQuery(sql);
			// Duyệt trên kết quả trả về
			while (rs.next()) {
				String maHD = rs.getString(1);
				String maKH = rs.getString(2);
				String tenKH = rs.getString(3).trim();
				String maPH = rs.getString(4);
				String tenPH = rs.getString(5).trim();
				String maNV = rs.getString(6);
				String tenNV = rs.getString(7).trim();
				Date thoiGianDatPhong = rs.getTimestamp(8);
				Date thoiGianTraPhong = rs.getTimestamp(9);
				double giaPhong = rs.getDouble(10);
				List<HoaDonDichVu> list = getHDDVtheoMaHD(maHD);
				KhachHang kh = new KhachHang(maKH,tenKH);
				PhongHat ph = new PhongHat(maPH,tenPH,giaPhong);
				NhanVien nv = new NhanVien(maNV,tenNV);
				HoaDon hd = new HoaDon(maHD, thoiGianDatPhong, thoiGianTraPhong, kh, nv, ph, giaPhong,list);
				dshd.add(hd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dshd;
	}
	public List<HoaDon> getHoaDon1Thang() {
		List<HoaDon> dshd = new ArrayList<HoaDon>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "select hd.maHoaDon,hd.maKhachHang,k.tenKhachHang,hd.maPhong,p.tenPhong,hd.maNhanVien,nv.tenNhanVien,hd.thoiGianDatPhong,hd.thoiGianTraPhong,hd.giaPhong\r\n"+
					"from HoaDon hd join Phong p on hd.maPhong = p.maPhong\r\n" + 
					"join KhachHang k on hd.maKhachHang = k.maKhachHang\r\n" + 
					"join NhanVien nv on nv.maNhanVien = hd.maNhanVien\r\n" + 
					"where DATEDIFF(day,CONVERT(date,thoiGianTraPhong) , CONVERT(date,GETDATE())) <= 30 and thoiGianTraPhong is not null";
			Statement statement = con.createStatement();
			// Thực thi câu lệnh SQL trả về đối tượng ResultSet
			ResultSet rs = statement.executeQuery(sql);
			// Duyệt trên kết quả trả về
			while (rs.next()) {
				String maHD = rs.getString(1);
				String maKH = rs.getString(2);
				String tenKH = rs.getString(3).trim();
				String maPH = rs.getString(4);
				String tenPH = rs.getString(5).trim();
				String maNV = rs.getString(6);
				String tenNV = rs.getString(7).trim();
				Date thoiGianDatPhong = rs.getTimestamp(8);
				Date thoiGianTraPhong = rs.getTimestamp(9);
				double giaPhong = rs.getDouble(10);
				List<HoaDonDichVu> list = getHDDVtheoMaHD(maHD);
				KhachHang kh = new KhachHang(maKH,tenKH);
				PhongHat ph = new PhongHat(maPH,tenPH,giaPhong);
				NhanVien nv = new NhanVien(maNV,tenNV);
				HoaDon hd = new HoaDon(maHD, thoiGianDatPhong, thoiGianTraPhong, kh, nv, ph, giaPhong,list);
				dshd.add(hd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dshd;
	}
}
