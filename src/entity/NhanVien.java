package entity;

import java.util.Date;

public class NhanVien {
	private String maNV;
	private String tenNV;
	private Date ngaySinh;
	private String soDT;
	private String cmnd;
	private boolean gioiTinh;
	private String chucVu;
	private double luong;
	private String caLamViec;
	private TaiKhoan taiKhoan;
	private boolean daXoa;
	public String getMaNV() {
		return maNV;
	}
	public void setMaNV(String maNV) {
		this.maNV = maNV;
	}
	public String getTenNV() {
		return tenNV;
	}
	public void setTenNV(String tenNV) {
		this.tenNV = tenNV;
	}
	public Date getNgaySinh() {
		return ngaySinh;
	}
	public void setNgaySinh(Date ngaySinh) {
		this.ngaySinh = ngaySinh;
	}
	public String getSoDT() {
		return soDT;
	}
	public void setSoDT(String soDT) {
		this.soDT = soDT;
	}
	public String getCmnd() {
		return cmnd;
	}
	public void setCmnd(String cmnd) {
		this.cmnd = cmnd;
	}
	public boolean isGioiTinh() {
		return gioiTinh;
	}
	public void setGioiTinh(boolean gioiTinh) {
		this.gioiTinh = gioiTinh;
	}
	public String getChucVu() {
		return chucVu;
	}
	public void setChucVu(String chucVu) {
		this.chucVu = chucVu;
	}
	public double getLuong() {
		return luong;
	}
	public void setLuong(double luong) {
		this.luong = luong;
	}
	public String getCaLamViec() {
		return caLamViec;
	}
	public void setCaLamViec(String caLamViec) {
		this.caLamViec = caLamViec;
	}
	public TaiKhoan getTaiKhoan() {
		return taiKhoan;
	}
	public void setTaiKhoan(TaiKhoan taiKhoan) {
		this.taiKhoan = taiKhoan;
	}
	public boolean isDaXoa() {
		return daXoa;
	}
	public void setDaXoa(boolean daXoa) {
		this.daXoa = daXoa;
	}
	public NhanVien(String maNV, String tenNV, Date ngaySinh, String soDT, String cmnd, boolean gioiTinh, String chucVu,
			double luong, String caLamViec, TaiKhoan taiKhoan, boolean daXoa) {
		super();
		this.maNV = maNV;
		this.tenNV = tenNV;
		this.ngaySinh = ngaySinh;
		this.soDT = soDT;
		this.cmnd = cmnd;
		this.gioiTinh = gioiTinh;
		this.chucVu = chucVu;
		this.luong = luong;
		this.caLamViec = caLamViec;
		this.taiKhoan = taiKhoan;
		this.daXoa = daXoa;
	}
	public NhanVien(String maNV, String cmnd, TaiKhoan taiKhoan) {
		super();
		this.maNV = maNV;
		this.cmnd = cmnd;
		this.taiKhoan = taiKhoan;
	}
	public NhanVien(String maNV, String tenNV) {
		super();
		this.maNV = maNV;
		this.tenNV = tenNV;
	}
	public NhanVien(String maNV) {
		super();
		this.maNV = maNV;
	}
	public NhanVien() {
		super();
	}
	@Override
	public String toString() {
		return "NhanVien [maNV=" + maNV + ", tenNV=" + tenNV + ", ngaySinh=" + ngaySinh + ", soDT=" + soDT + ", cmnd="
				+ cmnd + ", gioiTinh=" + gioiTinh + ", chucVu=" + chucVu + ", luong=" + luong + ", caLamViec="
				+ caLamViec + ", taiKhoan=" + taiKhoan + ", daXoa=" + daXoa + "]";
	}
}
