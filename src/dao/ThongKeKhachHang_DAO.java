package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import connectDB.ConnectDB;
import entity.HoaDon;
import entity.KhachHang;
import entity.NhanVien;
import entity.PhongHat;

public class ThongKeKhachHang_DAO {
	public List<HoaDon> getTatCaHoaDonHomNay() {
		List<HoaDon> dshd = new ArrayList<HoaDon>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		try {
			String sql = "select maKhachHang from HoaDon\r\n"
					+ "where convert(date,thoiGianDatPhong) = convert(date,GETDATE())\r\n" + "order by maKhachHang";
			Statement statement = con.createStatement();

			// Thực thi câu lệnh SQL trả về đối tượng ResultSet
			ResultSet rs = statement.executeQuery(sql);
			// Duyệt trên kết quả trả về
			while (rs.next()) {
				HoaDon hd = new HoaDon("", new Date(), new Date(), new KhachHang(rs.getString(1)), new NhanVien(),
						new PhongHat(), 0);
				dshd.add(hd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dshd;
	}

	public int getSoLongHoaDonDaTinhTienTheoMaKH(String maKH) {
		int soLuong = 0;
		;
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement = null;
		try {
			String sql = "select count(*) from HoaDon\r\n"
					+ "where maKhachHang=? and tongTienThanhToan is not NULL and convert(date,thoiGianDatPhong) <> convert(date,GETDATE())";
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

	public List<HoaDon> getTatCaHoaDonTheoMaKH(String maKH) {
		List<HoaDon> dshd = new ArrayList<HoaDon>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement = null;
		try {
			String sql = "select * from HoaDon where maKhachHang = ?";
			statement = con.prepareStatement(sql);
			statement.setString(1, maKH);
			// Thực thi câu lệnh SQL trả về đối tượng ResultSet
			ResultSet rs = statement.executeQuery();
			// Duyệt trên kết quả trả về
			while (rs.next()) {
				dshd.add(new HoaDon());
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

	public List<HoaDon> getTatCaHoaDonDaTinhTien() {
		List<HoaDon> dshd = new ArrayList<HoaDon>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "select hd.maHoaDon,hd.maKhachHang,k.tenKhachHang,hd.maPhong,p.tenPhong,hd.maNhanVien,nv.tenNhanVien,hd.thoiGianDatPhong,hd.thoiGianTraPhong,hd.giaPhong\r\n"
					+ "from HoaDon hd join Phong p on hd.maPhong = p.maPhong\r\n"
					+ "join KhachHang k on hd.maKhachHang = k.maKhachHang\r\n"
					+ "join NhanVien nv on nv.maNhanVien = hd.maNhanVien\r\n"
					+ "where tongTienThanhToan is not NULL order by maKhachHang";

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
				KhachHang kh = new KhachHang(maKH, tenKH);
				PhongHat ph = new PhongHat(maPH, tenPH, giaPhong);
				NhanVien nv = new NhanVien(maNV, tenNV);
				HoaDon hd = new HoaDon(maHD, thoiGianDatPhong, thoiGianTraPhong, kh, nv, ph, giaPhong);
				dshd.add(hd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dshd;
	}

	public List<HoaDon> getTatCaHoaDonKhachHang() {
		List<HoaDon> dshd = new ArrayList<HoaDon>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "select hd.maHoaDon,hd.maKhachHang,k.tenKhachHang,hd.maPhong,p.tenPhong,hd.maNhanVien,nv.tenNhanVien,hd.thoiGianDatPhong,hd.thoiGianTraPhong,hd.giaPhong\r\n"
					+ "from HoaDon hd join Phong p on hd.maPhong = p.maPhong\r\n"
					+ "left join KhachHang k on hd.maKhachHang = k.maKhachHang\r\n"
					+ "left join NhanVien nv on nv.maNhanVien = hd.maNhanVien\r\n" + "order by maKhachHang";

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
				String tenNV = rs.getString(7);
				Date thoiGianDatPhong = rs.getTimestamp(8);
				Date thoiGianTraPhong = rs.getTimestamp(9);
				double giaPhong = rs.getDouble(10);
				KhachHang kh = new KhachHang(maKH, tenKH);
				PhongHat ph = new PhongHat(maPH, tenPH, 0);
				NhanVien nv = new NhanVien(maNV, tenNV);
				HoaDon hd = new HoaDon(maHD, thoiGianDatPhong, thoiGianTraPhong, kh, nv, ph, giaPhong);
				dshd.add(hd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dshd;
	}

	public HoaDon getHoaDonCuoiCuaKhachHang(String maKH) {
		HoaDon hd = null;
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement = null;
		try {
			String sql = "select top(1) * from HoaDon\r\n" + "where maKhachHang= ? \r\n"
					+ "order by thoiGianDatPhong desc";
			statement = con.prepareStatement(sql);
			statement.setString(1, maKH);
			// Thực thi câu lệnh SQL trả về đối tượng ResultSet
			ResultSet rs = statement.executeQuery();
			// Duyệt trên kết quả trả về
			while (rs.next()) {
				hd = new HoaDon();
				hd.setThoigianDatPhong(rs.getTimestamp(5));
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

	public KhachHang getKHTheoMa(String ma) {
		KhachHang kh = null;
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement = null;
		try {
			String sql = "select * from KhachHang where maKhachHang = ?";
			statement = con.prepareStatement(sql);
			statement.setString(1, ma);
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
				kh = new KhachHang(maKH, tenKH, cmnd, gioiTinh, soDT, ngaySinh);
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
		return kh;
	}

	public List<KhachHang> getKHHomNay() {
		List<KhachHang> dskh = new ArrayList<KhachHang>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		try {
			String sql = "select kh.* from  KhachHang kh join HoaDon hd  on kh.maKhachHang= hd.maKhachHang\r\n"
					+ "where CONVERT(date,thoiGianDatPhong)  =  CONVERT(date,getdate())\r\n"
					+ "group by kh.maKhachHang,tenKhachHang,ngaySinh,gioiTinh,cmnd,soDT";
			Statement statement = con.createStatement();
			// Thực thi câu lệnh SQL trả về đối tượng ResultSet
			ResultSet rs = statement.executeQuery(sql);
			// Duyệt trên kết quả trả về
			while (rs.next()) {
				String maKH = rs.getString(1);
				String tenKH = rs.getString(2);
				String cmnd = rs.getString(3);
				Boolean gioiTinh = rs.getBoolean(4);
				String soDT = rs.getString(5);
				Date ngaySinh = rs.getDate(6);
				KhachHang kh = new KhachHang(maKH, tenKH, cmnd, gioiTinh, soDT, ngaySinh);
				dskh.add(kh);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dskh;
	}

	public List<KhachHang> getKHMotTuan() {
		List<KhachHang> dskh = new ArrayList<KhachHang>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		try {
			String sql = "select kh.* from  KhachHang kh join HoaDon hd  on kh.maKhachHang= hd.maKhachHang\r\n"
					+ "where DATEDIFF(day,CONVERT(date,thoiGianDatPhong) , CONVERT(date,GETDATE())) <= 7\r\n"
					+ "group by kh.maKhachHang,tenKhachHang,ngaySinh,gioiTinh,cmnd,soDT";
			Statement statement = con.createStatement();
			// Thực thi câu lệnh SQL trả về đối tượng ResultSet
			ResultSet rs = statement.executeQuery(sql);
			// Duyệt trên kết quả trả về
			while (rs.next()) {
				String maKH = rs.getString(1);
				String tenKH = rs.getString(2);
				String cmnd = rs.getString(3);
				Boolean gioiTinh = rs.getBoolean(4);
				String soDT = rs.getString(5);
				Date ngaySinh = rs.getDate(6);
				KhachHang kh = new KhachHang(maKH, tenKH, cmnd, gioiTinh, soDT, ngaySinh);
				dskh.add(kh);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dskh;
	}

	public List<KhachHang> getKHMotThang() {
		List<KhachHang> dskh = new ArrayList<KhachHang>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		try {
			String sql = "select kh.* from  KhachHang kh join HoaDon hd  on kh.maKhachHang= hd.maKhachHang\r\n"
					+ "where DATEDIFF(day,CONVERT(date,thoiGianDatPhong) , CONVERT(date,GETDATE())) <= 30\r\n"
					+ "group by kh.maKhachHang,tenKhachHang,ngaySinh,gioiTinh,cmnd,soDT";
			Statement statement = con.createStatement();
			// Thực thi câu lệnh SQL trả về đối tượng ResultSet
			ResultSet rs = statement.executeQuery(sql);
			// Duyệt trên kết quả trả về
			while (rs.next()) {
				String maKH = rs.getString(1);
				String tenKH = rs.getString(2);
				String cmnd = rs.getString(3);
				Boolean gioiTinh = rs.getBoolean(4);
				String soDT = rs.getString(5);
				Date ngaySinh = rs.getDate(6);
				KhachHang kh = new KhachHang(maKH, tenKH, cmnd, gioiTinh, soDT, ngaySinh);
				dskh.add(kh);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dskh;
	}

	public List<KhachHang> getKHTrongKhoangThoiGian(Date tuNgay, Date denNgay) {
		List<KhachHang> dskh = new ArrayList<KhachHang>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String sql = "";
		try {
			if (tuNgay != null & denNgay != null) {
				sql = "select kh.* from  KhachHang kh join HoaDon hd  on kh.maKhachHang= hd.maKhachHang\r\n"
						+ "where CONVERT(Date,thoiGianDatPhong) >= '" + df.format(tuNgay)
						+ "' and CONVERT(Date,thoiGianDatPhong)<='" + df.format(denNgay) + "' \r\n"
						+ "group by kh.maKhachHang,tenKhachHang,ngaySinh,gioiTinh,cmnd,soDT";
			} else if (tuNgay == null & denNgay != null) {
				sql = "select kh.* from  KhachHang kh join HoaDon hd  on kh.maKhachHang= hd.maKhachHang\r\n"
						+ "where CONVERT(Date,thoiGianDatPhong)<='" + df.format(denNgay) + "' \r\n"
						+ "group by kh.maKhachHang,tenKhachHang,ngaySinh,gioiTinh,cmnd,soDT";
			} else if (tuNgay != null & denNgay == null) {
				sql = "select kh.* from  KhachHang kh join HoaDon hd  on kh.maKhachHang= hd.maKhachHang\r\n"
						+ "where CONVERT(Date,thoiGianDatPhong) >= '" + df.format(tuNgay) + "' \r\n"
						+ "group by kh.maKhachHang,tenKhachHang,ngaySinh,gioiTinh,cmnd,soDT";
			} else if (tuNgay == null & denNgay == null) {
				sql = "select kh.* from  KhachHang kh join HoaDon hd  on kh.maKhachHang= hd.maKhachHang\r\n"
						+ "group by kh.maKhachHang,tenKhachHang,ngaySinh,gioiTinh,cmnd,soDT";
			}

			Statement statement = con.createStatement();
			// Thực thi câu lệnh SQL trả về đối tượng ResultSet
			ResultSet rs = statement.executeQuery(sql);
			// Duyệt trên kết quả trả về
			while (rs.next()) {
				String maKH = rs.getString(1);
				String tenKH = rs.getString(2);
				String cmnd = rs.getString(3);
				Boolean gioiTinh = rs.getBoolean(4);
				String soDT = rs.getString(5);
				Date ngaySinh = rs.getDate(6);
				KhachHang kh = new KhachHang(maKH, tenKH, cmnd, gioiTinh, soDT, ngaySinh);
				dskh.add(kh);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dskh;

	}
}
