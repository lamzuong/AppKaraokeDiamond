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

public class KhachHang_DAO {
	public List<KhachHang> getTatCaKhachHang() {
		List<KhachHang> dskh = new ArrayList<KhachHang>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "select * from KhachHang";
			Statement statement = con.createStatement();
			// Thực thi câu lệnh SQL trả về đối tượng ResultSet
			ResultSet rs = statement.executeQuery(sql);
			// Duyệt trên kết quả trả về
			while (rs.next()) {
				String maKH = rs.getString(1);
				String tenKH = rs.getString(2);
				String cmnd = rs.getString(3);
				boolean gioiTinh = rs.getBoolean(4);
				String sdt = rs.getString(5);
				Date ngaySinh = rs.getDate(6);
				KhachHang kh = new KhachHang(maKH, tenKH, cmnd, gioiTinh, sdt, ngaySinh);
				dskh.add(kh);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dskh;
	}
	public List<String> getTatCaSDTKhachHang() {
		List<String> listSDT = new ArrayList<String>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "select distinct  soDT from KhachHang";
			Statement statement = con.createStatement();
			// Thực thi câu lệnh SQL trả về đối tượng ResultSet
			ResultSet rs = statement.executeQuery(sql);
			// Duyệt trên kết quả trả về
			while (rs.next()) {
				
				listSDT.add(rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listSDT;
	}
	@SuppressWarnings("deprecation")
	public boolean create(KhachHang kh) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			stmt = con.prepareStatement("insert into KhachHang values(?,?,?,?,?,?)");
			stmt.setString(1, kh.getMaKH());
			stmt.setString(2, kh.getTenKH());
			stmt.setString(3, kh.getCmnd());
			stmt.setBoolean(4, kh.isGioiTinh());
			stmt.setString(5, kh.getSoDT());
			java.sql.Date ngaySQL = null;
			ngaySQL = new java.sql.Date(kh.getNgaySinh().getYear(), kh.getNgaySinh().getMonth(), kh.getNgaySinh().getDate());
			stmt.setDate(6, ngaySQL);
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

	public boolean update(KhachHang kh) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			stmt = con.prepareStatement(
					"update KhachHang set tenKhachHang=?, cmnd=?, gioiTinh=?, soDT=? where maKhachHang=?");
			stmt.setString(1, kh.getTenKH());
			stmt.setString(2, kh.getCmnd());
			stmt.setBoolean(3, kh.isGioiTinh());
			stmt.setString(4, kh.getSoDT());
			stmt.setString(5, kh.getMaKH());
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

	public List<KhachHang> getKHTheoTen(String ten) {
		List<KhachHang> dsKhachHang = new ArrayList<KhachHang>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement = null;
		try {
			String sql = "select * from KhachHang where tenKhachHang = ?";
			statement = con.prepareStatement(sql);
			statement.setString(1, ten);
			// Thực thi câu lệnh SQL trả về đối tượng ResultSet
			ResultSet rs = statement.executeQuery();
			// Duyệt trên kết quả trả về
			while (rs.next()) {
				String maKH = rs.getString(1);
				String tenKH = rs.getString(2);
				String cmnd = rs.getString(3);
				Boolean gioiTinh = rs.getBoolean(4);
				String soDT = rs.getString(5);
				Date ngaySinh = rs.getDate(6);
				KhachHang kh = new KhachHang(maKH, tenKH, cmnd, gioiTinh, soDT, ngaySinh);
				dsKhachHang.add(kh);
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
		return dsKhachHang;
	}
	
	public List<KhachHang> getKhachHangSinhNhatHomNay() {
		List<KhachHang> dsKhachHang = new ArrayList<KhachHang>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement = null;
		try {
			String sql = "select * from KhachHang where month(ngaySinh) = month(getdate())\r\n" 
					+ "and day(ngaySinh) = day(getdate()) and year(ngaySinh) <= year(getdate())";
			statement = con.prepareStatement(sql);
			// Thực thi câu lệnh SQL trả về đối tượng ResultSet
			ResultSet rs = statement.executeQuery();
			// Duyệt trên kết quả trả về
			while (rs.next()) {
				String maKH = rs.getString(1);
				String tenKH = rs.getString(2);
				String cmnd = rs.getString(3);
				boolean gioiTinh = rs.getBoolean(4);
				String soDT = rs.getString(5);
				Date ngaySinh = rs.getDate(6);
				KhachHang kh = new KhachHang(maKH, tenKH, cmnd, gioiTinh, soDT, ngaySinh);
				dsKhachHang.add(kh);
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
		return dsKhachHang;
	}

	public List<HoaDon> getTatCaKhachHangLenTableKH() {
		List<HoaDon> dskh = new ArrayList<HoaDon>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "select k.maKhachHang,k.tenKhachHang,k.cmnd,k.gioiTinh,k.soDT,k.ngaySinh,h.thoiGianDatPhong,h.thoiGianTraPhong,h.giaPhong\r\n"
					+ "from KhachHang k join HoaDon h on k.maKhachHang = h.maKhachHang\r\n"
					+ "where tongTienThanhToan is not null";
			Statement statement = con.createStatement();
			// Thực thi câu lệnh SQL trả về đối tượng ResultSet
			ResultSet rs = statement.executeQuery(sql);
			// Duyệt trên kết quả trả về
			while (rs.next()) {
				String maKH = rs.getString(1);
				String tenKH = rs.getString(2).trim();
				String cmnd = rs.getString(3).trim();
				boolean gioiTinh = rs.getBoolean(4);
				String sdt = rs.getString(5).trim();
				Date ngaySinh = rs.getDate(6);
				Date thoigianDatPhong = rs.getTimestamp(7);
				Date thoigianTraPhong = rs.getTimestamp(8);
				double giaPhong = rs.getDouble(9);
				KhachHang kh = new KhachHang(maKH, tenKH, cmnd, gioiTinh, sdt, ngaySinh);
				HoaDon hd = new HoaDon("", thoigianDatPhong, thoigianTraPhong, kh, null, null, giaPhong);
				dskh.add(hd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dskh;
	}

	public List<HoaDon> getKhachHangTheoTen(String ten) {
		List<HoaDon> dskh = new ArrayList<HoaDon>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement = null;
		try {
			String sql = "select k.maKhachHang,k.tenKhachHang,k.cmnd,k.gioiTinh,k.soDT,k.ngaySinh,h.thoiGianDatPhong,h.thoiGianTraPhong,h.giaPhong\r\n"
					+ "from KhachHang k join HoaDon h on k.maKhachHang = h.maKhachHang\r\n"
					+ "where tongTienThanhToan is not null and k.tenKhachHang = ?";
			statement = con.prepareStatement(sql);
			statement.setString(1, ten);
			// Thực thi câu lệnh SQL trả về đối tượng ResultSet
			ResultSet rs = statement.executeQuery();
			// Duyệt trên kết quả trả về
			while (rs.next()) {
				String maKH = rs.getString(1);
				String tenKH = rs.getString(2).trim();
				String cmnd = rs.getString(3).trim();
				boolean gioiTinh = rs.getBoolean(4);
				String sdt = rs.getString(5).trim();
				Date ngaySinh = rs.getDate(6);
				Date thoigianDatPhong = rs.getTimestamp(7);
				Date thoigianTraPhong = rs.getTimestamp(8);
				double giaPhong = rs.getDouble(9);
				KhachHang kh = new KhachHang(maKH, tenKH, cmnd, gioiTinh, sdt, ngaySinh);
				HoaDon hd = new HoaDon("", thoigianDatPhong, thoigianTraPhong, kh, null, null, giaPhong);
				dskh.add(hd);
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
		return dskh;
	}

	public List<HoaDon> getKhachHangTheoSDT(String sodt) {
		List<HoaDon> dskh = new ArrayList<HoaDon>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement = null;
		try {
			String sql = "select k.maKhachHang,k.tenKhachHang,k.cmnd,k.gioiTinh,k.soDT,k.ngaySinh,h.thoiGianDatPhong,h.thoiGianTraPhong,h.giaPhong\r\n"
					+ "from KhachHang k join HoaDon h on k.maKhachHang = h.maKhachHang\r\n"
					+ "where tongTienThanhToan is not null and k.soDT = ?";
			statement = con.prepareStatement(sql);
			statement.setString(1, sodt);
			// Thực thi câu lệnh SQL trả về đối tượng ResultSet
			ResultSet rs = statement.executeQuery();
			// Duyệt trên kết quả trả về
			while (rs.next()) {
				String maKH = rs.getString(1);
				String tenKH = rs.getString(2).trim();
				String cmnd = rs.getString(3).trim();
				boolean gioiTinh = rs.getBoolean(4);
				String sdt = rs.getString(5).trim();
				Date ngaySinh = rs.getDate(6);
				Date thoigianDatPhong = rs.getTimestamp(7);
				Date thoigianTraPhong = rs.getTimestamp(8);
				double giaPhong = rs.getDouble(9);
				KhachHang kh = new KhachHang(maKH, tenKH, cmnd, gioiTinh, sdt, ngaySinh);
				HoaDon hd = new HoaDon("", thoigianDatPhong, thoigianTraPhong, kh, null, null, giaPhong);
				dskh.add(hd);
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
		return dskh;
	}

	public List<HoaDon> getKhachHangTheoCMND(String CMND) {
		List<HoaDon> dskh = new ArrayList<HoaDon>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement = null;
		try {
			String sql = "select k.maKhachHang,k.tenKhachHang,k.cmnd,k.gioiTinh,k.soDT,k.ngaySinh,h.thoiGianDatPhong,h.thoiGianTraPhong,h.giaPhong\r\n"
					+ "from KhachHang k join HoaDon h on k.maKhachHang = h.maKhachHang\r\n"
					+ "where tongTienThanhToan is not null and k.cmnd = ?";
			statement = con.prepareStatement(sql);
			statement.setString(1, CMND);
			// Thực thi câu lệnh SQL trả về đối tượng ResultSet
			ResultSet rs = statement.executeQuery();
			// Duyệt trên kết quả trả về
			while (rs.next()) {
				String maKH = rs.getString(1);
				String tenKH = rs.getString(2).trim();
				String cmnd = rs.getString(3).trim();
				boolean gioiTinh = rs.getBoolean(4);
				String sdt = rs.getString(5).trim();
				Date ngaySinh = rs.getDate(6);
				Date thoigianDatPhong = rs.getTimestamp(7);
				Date thoigianTraPhong = rs.getTimestamp(8);
				double giaPhong = rs.getDouble(9);
				KhachHang kh = new KhachHang(maKH, tenKH, cmnd, gioiTinh, sdt, ngaySinh);
				HoaDon hd = new HoaDon("", thoigianDatPhong, thoigianTraPhong, kh, null, null, giaPhong);
				dskh.add(hd);
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
		return dskh;
	}

	public List<HoaDon> getKhachHangTheoNgay(java.sql.Date ngayCanTim, boolean isNgayDatPhong) {
		List<HoaDon> dskh = new ArrayList<HoaDon>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement = null;
		try {
			String sql = "";
			if (isNgayDatPhong)
				sql = "select k.maKhachHang,k.tenKhachHang,k.cmnd,k.gioiTinh,k.soDT,k.ngaySinh,h.thoiGianDatPhong,h.thoiGianTraPhong,h.giaPhong\r\n"
						+ "from KhachHang k join HoaDon h on k.maKhachHang = h.maKhachHang\r\n"
						+ "where tongTienThanhToan is not null and convert(date,h.thoiGianDatPhong) = ?";
			else
				sql = "select k.maKhachHang,k.tenKhachHang,k.cmnd,k.gioiTinh,k.soDT,k.ngaySinh,h.thoiGianDatPhong,h.thoiGianTraPhong,h.giaPhong\r\n"
						+ "from KhachHang k join HoaDon h on k.maKhachHang = h.maKhachHang\r\n"
						+ "where tongTienThanhToan is not null and convert(date,h.thoiGianTraPhong) = ?";
			statement = con.prepareStatement(sql);
			statement.setDate(1, ngayCanTim);
			// Thực thi câu lệnh SQL trả về đối tượng ResultSet
			ResultSet rs = statement.executeQuery();
			// Duyệt trên kết quả trả về
			while (rs.next()) {
				String maKH = rs.getString(1);
				String tenKH = rs.getString(2).trim();
				String cmnd = rs.getString(3).trim();
				boolean gioiTinh = rs.getBoolean(4);
				String sdt = rs.getString(5).trim();
				Date ngaySinh = rs.getDate(6);
				Date thoigianDatPhong = rs.getTimestamp(7);
				Date thoigianTraPhong = rs.getTimestamp(8);
				double giaPhong = rs.getDouble(9);
				KhachHang kh = new KhachHang(maKH, tenKH, cmnd, gioiTinh, sdt, ngaySinh);
				HoaDon hd = new HoaDon("", thoigianDatPhong, thoigianTraPhong, kh, null, null, giaPhong);
				dskh.add(hd);
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
		return dskh;
	}
	
	public List<HoaDon> getHoaDonTheoMaKH(String ma) {
		List<HoaDon> dshd = new ArrayList<HoaDon>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement = null;
		try {
			String sql = "select k.maKhachHang,k.tenKhachHang,k.cmnd,k.gioiTinh,k.soDT,k.ngaySinh,h.thoiGianDatPhong,h.thoiGianTraPhong,h.giaPhong\r\n"
					+ "from KhachHang k join HoaDon h on k.maKhachHang = h.maKhachHang\r\n"
					+ "where k.maKhachHang = ?";
			statement = con.prepareStatement(sql);
			statement.setString(1, ma);
			// Thực thi câu lệnh SQL trả về đối tượng ResultSet
			ResultSet rs = statement.executeQuery();
			// Duyệt trên kết quả trả về
			while (rs.next()) {
				String maKH = rs.getString(1);
				String tenKH = rs.getString(2).trim();
				String cmnd = rs.getString(3).trim();
				boolean gioiTinh = rs.getBoolean(4);
				String sdt = rs.getString(5).trim();
				Date ngaySinh = rs.getDate(6);
				Date thoigianDatPhong = rs.getTimestamp(7);
				Date thoigianTraPhong = rs.getTimestamp(8);
				double giaPhong = rs.getDouble(9);
				KhachHang kh = new KhachHang(maKH, tenKH, cmnd, gioiTinh, sdt, ngaySinh);
				HoaDon hd = new HoaDon("", thoigianDatPhong, thoigianTraPhong, kh, null, null, giaPhong);
							
				dshd.add(hd);
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
