package entity;

import java.util.Date;

public class KhachHang {
	private String maKH;
	private String tenKH;
	private String cmnd;
	private boolean gioiTinh;
	private String soDT;
	private Date ngaySinh;
	public String getMaKH() {
		return maKH;
	}
	public void setMaKH(String maKH) {
		this.maKH = maKH;
	}
	public String getTenKH() {
		return tenKH;
	}
	public void setTenKH(String tenKH) {
		this.tenKH = tenKH;
	}
	public String getCmnd() {
		return cmnd;
	}
	public void setCmnd(String cmnd) {
		this.cmnd = cmnd;
	}
	public String getSoDT() {
		return soDT;
	}
	public void setSoDT(String soDT) {
		this.soDT = soDT;
	}
	public boolean isGioiTinh() {
		return gioiTinh;
	}
	public void setGioiTinh(boolean gioiTinh) {
		this.gioiTinh = gioiTinh;
	}
	public Date getNgaySinh() {
		return ngaySinh;
	}
	public void setNgaySinh(Date ngaySinh) {
		this.ngaySinh = ngaySinh;
	}
	public KhachHang(String maKH, String tenKH, String cmnd, boolean gioiTinh, String soDT, Date ngaySinh) {
		super();
		this.maKH = maKH;
		this.tenKH = tenKH;
		this.cmnd = cmnd;
		this.gioiTinh = gioiTinh;
		this.soDT = soDT;
		this.ngaySinh = ngaySinh;
	}
	public KhachHang(String maKH, String tenKH, String cmnd, boolean gioiTinh, String soDT) {
		super();
		this.maKH = maKH;
		this.tenKH = tenKH;
		this.cmnd = cmnd;
		this.gioiTinh = gioiTinh;
		this.soDT = soDT;
	}
	public KhachHang(String maKH, String tenKH) {
		super();
		this.maKH = maKH;
		this.tenKH = tenKH;
	}
	public KhachHang(String maKH) {
		super();
		this.maKH = maKH;
	}
	public KhachHang() {
		super();
	}
	@Override
	public String toString() {
		return "KhachHang [maKH=" + maKH + ", tenKH=" + tenKH + ", cmnd=" + cmnd + ", soDT=" + soDT + ", gioiTinh="
				+ gioiTinh + "]";
	}
}
