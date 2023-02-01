package models;

public class MonHoc {
	private int id;
	private String ten;
	private String tenGiangVien;

	public MonHoc() {
		super();
	}

	public MonHoc(int id, String ten, String tenGiangVien) {
		super();
		this.id = id;
		this.ten = ten;
		this.tenGiangVien = tenGiangVien;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public String getTenGiangVien() {
		return tenGiangVien;
	}

	public void setTenGiangVien(String tenGiangVien) {
		this.tenGiangVien = tenGiangVien;
	}

	@Override
	public String toString() {
		return id + " - " + ten;
	}

}
