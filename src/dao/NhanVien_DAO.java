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
import entity.NhanVien;
import entity.TaiKhoan;

public class NhanVien_DAO {
	public List<NhanVien> getTatCaNhanVien(){
		List<NhanVien> dsnv = new ArrayList<NhanVien>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "select * from NhanVien";
			Statement statement = con.createStatement();
			// Thực thi câu lệnh SQL trả về đối tượng ResultSet
			ResultSet rs = statement.executeQuery(sql);
			// Duyệt trên kết quả trả về
			while (rs.next()) {
				String maNV = rs.getString(1);
				String tenNV = rs.getString(2);
				Date ngaySinh = rs.getDate(3);
				boolean gioiTinh = rs.getBoolean(4);
				String cmnd = rs.getString(5);
				String sdt = rs.getString(6);
				String chucVu = rs.getString(7);
				String caLamViec = rs.getString(8);
				double luong = rs.getDouble(9);
				String taiKhoan = rs.getString(10);
				TaiKhoan tk = new TaiKhoan(taiKhoan, "123");
				boolean daXoa = rs.getBoolean(11);
				NhanVien nv = new NhanVien(maNV, tenNV, ngaySinh, sdt, cmnd, gioiTinh, chucVu, luong, caLamViec, tk, daXoa);
				dsnv.add(nv);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dsnv;
	}
	public List<NhanVien> getTatCaNhanVienKhongGomQL(){
		List<NhanVien> dsnv = new ArrayList<NhanVien>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "select * from NhanVien where chucVu <> N'Quản lý'";
			Statement statement = con.createStatement();
			// Thực thi câu lệnh SQL trả về đối tượng ResultSet
			ResultSet rs = statement.executeQuery(sql);
			// Duyệt trên kết quả trả về
			while (rs.next()) {
				String maNV = rs.getString(1);
				String tenNV = rs.getString(2);
				Date ngaySinh = rs.getDate(3);
				boolean gioiTinh = rs.getBoolean(4);
				String cmnd = rs.getString(5);
				String sdt = rs.getString(6);
				String chucVu = rs.getString(7);
				String caLamViec = rs.getString(8);
				double luong = rs.getDouble(9);
				String taiKhoan = rs.getString(10);
				TaiKhoan tk = new TaiKhoan(taiKhoan, "123");
				boolean daXoa = rs.getBoolean(11);
				NhanVien nv = new NhanVien(maNV, tenNV, ngaySinh, sdt, cmnd, gioiTinh, chucVu, luong, caLamViec, tk, daXoa);
				dsnv.add(nv);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dsnv;
	}
	public boolean createNVCoTK(NhanVien nv) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			stmt = con.prepareStatement("insert into NhanVien values(?,?,?,?,?,?,?,?,?,?,?)");
			stmt.setString(1, nv.getMaNV());
			stmt.setString(2, nv.getTenNV());
			stmt.setDate(3, (java.sql.Date) nv.getNgaySinh());
			stmt.setBoolean(4, nv.isGioiTinh());
			stmt.setString(5, nv.getCmnd());
			stmt.setString(6, nv.getSoDT());
			stmt.setString(7, nv.getChucVu());
			stmt.setString(8, nv.getCaLamViec());
			stmt.setDouble(9, nv.getLuong());
			stmt.setString(10, nv.getTaiKhoan().getTenTaiKhoan());
			stmt.setBoolean(11, false);
			
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
	public boolean createNVKhongCoTK(NhanVien nv) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			stmt = con.prepareStatement("insert into NhanVien values(?,?,?,?,?,?,?,?,?,?,?)");
			stmt.setString(1, nv.getMaNV());
			stmt.setString(2, nv.getTenNV());
			stmt.setDate(3, (java.sql.Date) nv.getNgaySinh());
			stmt.setBoolean(4, nv.isGioiTinh());
			stmt.setString(5, nv.getCmnd());
			stmt.setString(6, nv.getSoDT());
			stmt.setString(7, nv.getChucVu());
			stmt.setString(8, nv.getCaLamViec());
			stmt.setDouble(9, nv.getLuong());
			stmt.setString(10, null);
			stmt.setBoolean(11, false);
			
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
			stmt = con.prepareStatement("update NhanVien set daXoa = 1, taiKhoan = null where maNhanVien = ?");
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
	public boolean updateNVCoTK(NhanVien nv) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			stmt = con.prepareStatement("update NhanVien set tenNhanVien=?, ngaySinh=?, gioiTinh=?, "
					+ "cmnd=?, soDT=?, chucVu=?, caLamViec=?, luong=?, taiKhoan=? where maNhanVien=?");

			stmt.setString(1, nv.getTenNV());
			stmt.setDate(2, (java.sql.Date) nv.getNgaySinh());
			stmt.setBoolean(3, nv.isGioiTinh());
			stmt.setString(4, nv.getCmnd());
			stmt.setString(5, nv.getSoDT());
			stmt.setString(6, nv.getChucVu());
			stmt.setString(7, nv.getCaLamViec());
			stmt.setDouble(8, nv.getLuong());
			stmt.setString(9, nv.getTaiKhoan().getTenTaiKhoan());
			stmt.setString(10, nv.getMaNV());
			
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
	public boolean updateNVKhongCoTK(NhanVien nv) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			stmt = con.prepareStatement("update NhanVien set tenNhanVien=?, ngaySinh=?, gioiTinh=?, "
					+ "cmnd=?, soDT=?, chucVu=?, caLamViec=?, luong=?, taiKhoan=? where maNhanVien=?");

			stmt.setString(1, nv.getTenNV());
			stmt.setDate(2, (java.sql.Date) nv.getNgaySinh());
			stmt.setBoolean(3, nv.isGioiTinh());
			stmt.setString(4, nv.getCmnd());
			stmt.setString(5, nv.getSoDT());
			stmt.setString(6, nv.getChucVu());
			stmt.setString(7, nv.getCaLamViec());
			stmt.setDouble(8, nv.getLuong());
			stmt.setString(9, null);
			stmt.setString(10, nv.getMaNV());
			
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
	public boolean setNullTK(NhanVien nv) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			stmt = con.prepareStatement("update NhanVien set taiKhoan=null where maNhanVien=?");
			stmt.setString(1, nv.getMaNV());
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
	public List<NhanVien> getNhanVienTheoTen(String ten){
		List<NhanVien> dsNV = new ArrayList<NhanVien>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement = null;
		try {
			String sql = "select nv.maNhanVien,nv.tenNhanVien,nv.ngaySinh,nv.gioiTinh,nv.cmnd,nv.soDT,"
					+ "nv.chucVu,nv.caLamViec,nv.luong,nv.taiKhoan,tk.matKhau "
					+ "from NhanVien nv left join TaiKhoan tk on nv.taiKhoan = tk.taiKhoan where tenNhanVien = ?";
			statement = con.prepareStatement(sql);
			statement.setString(1, ten);
			// Thực thi câu lệnh SQL trả về đối tượng ResultSet
			ResultSet rs = statement.executeQuery();
			// Duyệt trên kết quả trả về
			while (rs.next()) {
				String maNV = rs.getString(1);
				String tenNV = rs.getString(2);
				Date ngaySinh = rs.getDate(3);
				boolean gioiTinh = rs.getBoolean(4);
				String cmnd = rs.getString(5);
				String sdt = rs.getString(6);
				String chucVu = rs.getString(7);
				String caLamViec = rs.getString(8);
				double luong = rs.getDouble(9);
				String taiKhoan = rs.getString(10);
				String matKhau = rs.getString(11);
				TaiKhoan tk = new TaiKhoan(taiKhoan, matKhau);
				boolean daXoa = rs.getBoolean(11);
				NhanVien nv = new NhanVien(maNV, tenNV, ngaySinh, sdt, cmnd, gioiTinh, chucVu, luong, caLamViec, tk, daXoa);
				dsNV.add(nv);
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
		return dsNV;
	}
	public List<NhanVien> getNhanVienTheoCa(String ca){
		List<NhanVien> dsNV = new ArrayList<NhanVien>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement = null;
		try {
			String sql = "select nv.maNhanVien,nv.tenNhanVien,nv.ngaySinh,nv.gioiTinh,nv.cmnd,nv.soDT,"
					+ "nv.chucVu,nv.caLamViec,nv.luong,nv.taiKhoan,tk.matKhau "
					+ "from NhanVien nv left join TaiKhoan tk on nv.taiKhoan = tk.taiKhoan where caLamViec = ?";
			statement = con.prepareStatement(sql);
			statement.setString(1, ca);
			// Thực thi câu lệnh SQL trả về đối tượng ResultSet
			ResultSet rs = statement.executeQuery();
			// Duyệt trên kết quả trả về
			while (rs.next()) {
				String maNV = rs.getString(1);
				String tenNV = rs.getString(2);
				Date ngaySinh = rs.getDate(3);
				boolean gioiTinh = rs.getBoolean(4);
				String cmnd = rs.getString(5);
				String sdt = rs.getString(6);
				String chucVu = rs.getString(7);
				String caLamViec = rs.getString(8);
				double luong = rs.getDouble(9);
				String taiKhoan = rs.getString(10);
				String matKhau = rs.getString(11);
				TaiKhoan tk = new TaiKhoan(taiKhoan, matKhau);
				boolean daXoa = rs.getBoolean(11);
				NhanVien nv = new NhanVien(maNV, tenNV, ngaySinh, sdt, cmnd, gioiTinh, chucVu, luong, caLamViec, tk, daXoa);
				dsNV.add(nv);
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
		return dsNV;
	}
	public List<NhanVien> getNhanVienTheoChucVu(String chucvu){
		List<NhanVien> dsNV = new ArrayList<NhanVien>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement = null;
		try {
			String sql = "select nv.maNhanVien,nv.tenNhanVien,nv.ngaySinh,nv.gioiTinh,nv.cmnd,nv.soDT,"
					+ "nv.chucVu,nv.caLamViec,nv.luong,nv.taiKhoan,tk.matKhau "
					+ "from NhanVien nv left join TaiKhoan tk on nv.taiKhoan = tk.taiKhoan where chucVu = ?";
			statement = con.prepareStatement(sql);
			statement.setString(1, chucvu);
			// Thực thi câu lệnh SQL trả về đối tượng ResultSet
			ResultSet rs = statement.executeQuery();
			// Duyệt trên kết quả trả về
			while (rs.next()) {
				String maNV = rs.getString(1);
				String tenNV = rs.getString(2);
				Date ngaySinh = rs.getDate(3);
				boolean gioiTinh = rs.getBoolean(4);
				String cmnd = rs.getString(5);
				String sdt = rs.getString(6);
				String chucVu = rs.getString(7);
				String caLamViec = rs.getString(8);
				double luong = rs.getDouble(9);
				String taiKhoan = rs.getString(10);
				String matKhau = rs.getString(11);
				TaiKhoan tk = new TaiKhoan(taiKhoan, matKhau);
				boolean daXoa = rs.getBoolean(11);
				NhanVien nv = new NhanVien(maNV, tenNV, ngaySinh, sdt, cmnd, gioiTinh, chucVu, luong, caLamViec, tk, daXoa);
				dsNV.add(nv);
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
		return dsNV;
	}
}
