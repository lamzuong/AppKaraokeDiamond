package entity;

public class PhongHat {
	private String maPhong;
	private String tenPhong;
	private double giaPhong;
	private String loaiPhong;
	private boolean trangThai;
	public String getMaPhong() {
		return maPhong;
	}
	public void setMaPhong(String maPhong) {
		this.maPhong = maPhong;
	}
	public String getTenPhong() {
		return tenPhong;
	}
	public void setTenPhong(String tenPhong) {
		this.tenPhong = tenPhong;
	}
	public double getGiaPhong() {
		return giaPhong;
	}
	public void setGiaPhong(double giaPhong) {
		this.giaPhong = giaPhong;
	}
	public String getLoaiPhong() {
		return loaiPhong;
	}
	public void setLoaiPhong(String loaiPhong) {
		this.loaiPhong = loaiPhong;
	}
	public boolean isTrangThai() {
		return trangThai;
	}
	public void setTrangThai(boolean trangThai) {
		this.trangThai = trangThai;
	}
	public PhongHat(String maPhong, String tenPhong, double giaPhong, String loaiPhong, boolean trangThai) {
		super();
		this.maPhong = maPhong;
		this.tenPhong = tenPhong;
		this.giaPhong = giaPhong;
		this.loaiPhong = loaiPhong;
		this.trangThai = trangThai;
	}
	public PhongHat(String maPhong, String tenPhong, double giaPhong, String loaiPhong) {
		super();
		this.maPhong = maPhong;
		this.tenPhong = tenPhong;
		this.giaPhong = giaPhong;
		this.loaiPhong = loaiPhong;
	}
	public PhongHat(String maPhong, String tenPhong, double giaPhong) {
		super();
		this.maPhong = maPhong;
		this.tenPhong = tenPhong;
		this.giaPhong = giaPhong;
	}
	public PhongHat(String maPhong, String tenPhong) {
		super();
		this.maPhong = maPhong;
		this.tenPhong = tenPhong;
	}
	public PhongHat(String maPhong) {
		super();
		this.maPhong = maPhong;
	}
	public PhongHat() {
		super();
	}
	@Override
	public String toString() {
		return "PhongHat [maPhong=" + maPhong + ", tenPhong=" + tenPhong + ", giaPhong=" + giaPhong + ", loaiPhong="
				+ loaiPhong + ", trangThai=" + trangThai + "]";
	}
}
