package entity;

import java.util.Date;
import java.util.List;

public class HoaDon {
	private String maHD;
	private Date thoigianDatPhong;
	private Date thoigianTraPhong;
	private KhachHang maKH;
	private NhanVien maNV;
	private PhongHat maPhong;
	private double giaPhong;
	List<HoaDonDichVu> hoaDonDichVu;
	private double soGioThue;
	private double tienPhong;
	private double tienDichVu;
	private double tongTien;
	public String getMaHD() {
		return maHD;
	}
	public void setMaHD(String maHD) {
		this.maHD = maHD;
	}
	public Date getThoigianDatPhong() {
		return thoigianDatPhong;
	}
	public void setThoigianDatPhong(Date thoigianDatPhong) {
		this.thoigianDatPhong = thoigianDatPhong;
	}
	public Date getThoigianTraPhong() {
		return thoigianTraPhong;
	}
	public void setThoigianTraPhong(Date thoigianTraPhong) {
		this.thoigianTraPhong = thoigianTraPhong;
	}
	public KhachHang getMaKH() {
		return maKH;
	}
	public void setMaKH(KhachHang maKH) {
		this.maKH = maKH;
	}
	public NhanVien getMaNV() {
		return maNV;
	}
	public void setMaNV(NhanVien maNV) {
		this.maNV = maNV;
	}
	public PhongHat getMaPhong() {
		return maPhong;
	}
	public void setMaPhong(PhongHat maPhong) {
		this.maPhong = maPhong;
	}
	public double getGiaPhong() {
		return giaPhong;
	}
	public void setGiaPhong(double giaPhong) {
		this.giaPhong = giaPhong;
	}
	public List<HoaDonDichVu> getHoaDonDichVu() {
		return hoaDonDichVu;
	}
	public void setHoaDonDichVu(List<HoaDonDichVu> hoaDonDichVu) {
		this.hoaDonDichVu = hoaDonDichVu;
	}
	public double getSoGioThue() {
		return soGioThue;
	}
	public double getTienPhong() {
		return tienPhong;
	}
	public double getTienDichVu() {
		return tienDichVu;
	}
	public double getTongTien() {
		return tongTien;
	}
	public HoaDon(String maHD, Date thoigianDatPhong, Date thoigianTraPhong, KhachHang maKH, NhanVien maNV,
			PhongHat maPhong, double giaPhong, List<HoaDonDichVu> hoaDonDichVu) {
		super();
		this.maHD = maHD;
		this.thoigianDatPhong = thoigianDatPhong;
		this.thoigianTraPhong = thoigianTraPhong;
		this.maKH = maKH;
		this.maNV = maNV;
		this.maPhong = maPhong;
		this.giaPhong = giaPhong;
		this.hoaDonDichVu = hoaDonDichVu;
		this.soGioThue = tinhTongSoGioThue();
		this.tienPhong = tinhTongTienPhong();
		this.tienDichVu = tinhTongTienDichVu();
		this.tongTien = tinhTongTienThanhToan();
	}
	public HoaDon(String maHD, Date thoigianDatPhong, Date thoigianTraPhong, KhachHang maKH, NhanVien maNV,
			PhongHat maPhong, double giaPhong) {
		super();
		this.maHD = maHD;
		this.thoigianDatPhong = thoigianDatPhong;
		this.thoigianTraPhong = thoigianTraPhong;
		this.maKH = maKH;
		this.maNV = maNV;
		this.maPhong = maPhong;
		this.giaPhong = giaPhong;
	}
	public HoaDon(String maHD) {
		super();
		this.maHD = maHD;
	}
	public HoaDon() {
		super();
	}
	public double tinhTongSoGioThue() {
		double khoangCach = thoigianTraPhong.getTime() - thoigianDatPhong.getTime();
		double soGio = (double)khoangCach / (60 * 60 * 1000);
		if(soGio < 0.5) soGio = 0.5;
		return soGio;
	}
	public double tinhTongTienPhong() {
		return tinhTongSoGioThue() * giaPhong;
	}
	public double tinhTongTienDichVu() {
		double tongTien = 0;
		for (HoaDonDichVu hddv : hoaDonDichVu) {
			tongTien += hddv.getThanhTien();
		}
		return tongTien;
	}
	public double tinhTongTienThanhToan() {
		return tinhTongTienPhong() + tinhTongTienDichVu();
	}
	@Override
	public String toString() {
		return "HoaDon [maHD=" + maHD + ", thoigianDatPhong=" + thoigianDatPhong + ", thoigianTraPhong="
				+ thoigianTraPhong + ", maKH=" + maKH + ", maNV=" + maNV + ", maPhong=" + maPhong + ", giaPhong="
				+ giaPhong + ", hoaDonDichVu=" + hoaDonDichVu + ", soGioThue=" + soGioThue + ", tienPhong=" + tienPhong
				+ ", tienDichVu=" + tienDichVu + ", tongTien=" + tongTien + "]";
	}
}
