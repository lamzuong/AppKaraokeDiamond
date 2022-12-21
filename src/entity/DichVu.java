package entity;

public class DichVu {
	private String maDichVu;
	private String tenDichVu;
	private int soLuong;
	private String donViTinh;
	private double giaTien;
	public String getMaDichVu() {
		return maDichVu;
	}

	public void setMaDichVu(String maDichVu) {
		this.maDichVu = maDichVu;
	}

	public String getTenDichVu() {
		return tenDichVu;
	}

	public void setTenDichVu(String tenDichVu) {
		this.tenDichVu = tenDichVu;
	}

	public int getSoLuong() {
		return soLuong;
	}

	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}

	public String getDonViTinh() {
		return donViTinh;
	}

	public void setDonViTinh(String donViTinh) {
		this.donViTinh = donViTinh;
	}

	public double getGiaTien() {
		return giaTien;
	}

	public void setGiaTien(double giaTien) {
		this.giaTien = giaTien;
	}	
	
	public DichVu(String maDichVu, String tenDichVu, int soLuong, String donViTinh, double giaTien) {
		super();
		this.maDichVu = maDichVu;
		this.tenDichVu = tenDichVu;
		this.soLuong = soLuong;
		this.donViTinh = donViTinh;
		this.giaTien = giaTien;
	}
	
	public DichVu(String maDichVu, String tenDichVu, int soLuong, double giaTien) {
		super();
		this.maDichVu = maDichVu;
		this.tenDichVu = tenDichVu;
		this.soLuong = soLuong;
		this.giaTien = giaTien;
	}

	public DichVu(String tenDichVu, int soLuong, String donViTinh, double giaTien) {
		super();
		this.tenDichVu = tenDichVu;
		this.soLuong = soLuong;
		this.donViTinh = donViTinh;
		this.giaTien = giaTien;
	}

	public DichVu(String maDichVu, String tenDichVu) {
		super();
		this.maDichVu = maDichVu;
		this.tenDichVu = tenDichVu;
	}

	public DichVu(String maDichVu) {
		super();
		this.maDichVu = maDichVu;
	}

	public DichVu() {
		super();
	}

	@Override
	public String toString() {
		return "DichVu [maDichVu=" + maDichVu + ", tenDichVu=" + tenDichVu + ", soLuong=" + soLuong + ", donViTinh="
				+ donViTinh + ", giaTien=" + giaTien + "]";
	}
}


	