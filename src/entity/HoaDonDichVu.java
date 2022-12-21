package entity;

public class HoaDonDichVu {
	private HoaDon maHD;
	private DichVu maDV;
	private int soLuong;
	private double giaTien;
	private double thanhTien;
	
	public HoaDon getMaHD() {
		return maHD;
	}
	public void setMaHD(HoaDon maHD) {
		this.maHD = maHD;
	}
	public DichVu getMaDV() {
		return maDV;
	}
	public void setMaDV(DichVu maDV) {
		this.maDV = maDV;
	}
	public int getSoLuong() {
		return soLuong;
	}
	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}
	public double getGiaTien() {
		return giaTien;
	}
	public void setGiaTien(double giaTien) {
		this.giaTien = giaTien;
	}
	public double getThanhTien() {
		return thanhTien;
	}
	public HoaDonDichVu(HoaDon maHD, DichVu maDV, int soLuong, double giaTien) {
		super();
		this.maHD = maHD;
		this.maDV = maDV;
		this.soLuong = soLuong;
		this.giaTien = giaTien;
		this.thanhTien = giaTien * soLuong;
	}
	public HoaDonDichVu(DichVu maDV, int soLuong) {
		super();
		this.maDV = maDV;
		this.soLuong = soLuong;
	}
	public HoaDonDichVu() {
		super();
	}
	@Override
	public String toString() {
		return "HoaDonDichVu [maHD=" + maHD + ", maDV=" + maDV + ", soLuong=" + soLuong + ", giaTien=" + giaTien
				+ ", thanhTien=" + thanhTien + "]";
	}
}
