package models;

public class Diem {
	private int id;
	private SinhVien sinhVien;
	private MonHoc monHoc;
	private float chuyenCan;
	private float baiTap;
	private float giuaKy;
	private float cuoiKy;
	private HocKy hocKy;
	private TinhDiem tinhDiem;
	private float diemTrungBinh;
	private String xepLoai;

	public Diem() {
		super();
	}

	public Diem(int id, SinhVien sinhVien, MonHoc monHoc, float chuyenCan, float baiTap, float giuaKy, float cuoiKy,
			HocKy hocKy, TinhDiem tinhDiem, float diemTrungBinh, String xepLoai) {
		super();
		this.id = id;
		this.sinhVien = sinhVien;
		this.monHoc = monHoc;
		this.chuyenCan = chuyenCan;
		this.baiTap = baiTap;
		this.giuaKy = giuaKy;
		this.cuoiKy = cuoiKy;
		this.hocKy = hocKy;
		this.tinhDiem = tinhDiem;
		this.diemTrungBinh = diemTrungBinh;
		this.xepLoai = xepLoai;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public SinhVien getSinhVien() {
		return sinhVien;
	}

	public void setSinhVien(SinhVien sinhVien) {
		this.sinhVien = sinhVien;
	}

	public MonHoc getMonHoc() {
		return monHoc;
	}

	public void setMonHoc(MonHoc monHoc) {
		this.monHoc = monHoc;
	}

	public float getChuyenCan() {
		return chuyenCan;
	}

	public void setChuyenCan(float chuyenCan) {
		this.chuyenCan = chuyenCan;
	}

	public float getBaiTap() {
		return baiTap;
	}

	public void setBaiTap(float baiTap) {
		this.baiTap = baiTap;
	}

	public float getGiuaKy() {
		return giuaKy;
	}

	public void setGiuaKy(float giuaKy) {
		this.giuaKy = giuaKy;
	}

	public float getCuoiKy() {
		return cuoiKy;
	}

	public void setCuoiKy(float cuoiKy) {
		this.cuoiKy = cuoiKy;
	}

	public HocKy getHocKy() {
		return hocKy;
	}

	public void setHocKy(HocKy hocKy) {
		this.hocKy = hocKy;
	}

	public TinhDiem getTinhDiem() {
		return tinhDiem;
	}

	public void setTinhDiem(TinhDiem tinhDiem) {
		this.tinhDiem = tinhDiem;
	}

	public float getDiemTrungBinh() {
		return diemTrungBinh;
	}

	public void setDiemTrungBinh(float diemTrungBinh) {
		this.diemTrungBinh = diemTrungBinh;
	}

	public String getXepLoai() {
		return xepLoai;
	}

	public void setXepLoai(String xepLoai) {
		this.xepLoai = xepLoai;
	}

	@Override
	public String toString() {
		return "Diem [id=" + id + ", sinhVien=" + sinhVien + ", monHoc=" + monHoc + ", chuyenCan=" + chuyenCan
				+ ", baiTap=" + baiTap + ", giuaKy=" + giuaKy + ", cuoiKy=" + cuoiKy + ", hocKy=" + hocKy
				+ ", tinhDiem=" + tinhDiem + ", diemTrungBinh=" + diemTrungBinh + ", xepLoai=" + xepLoai + "]";
	}

	public void tinhDiemTrungBinh() {
		if (tinhDiem.getLoai() == 1) {
			diemTrungBinh = (float) (baiTap * 0.1 + giuaKy * 0.2 + chuyenCan * 0.2 + cuoiKy * 0.5);
		} else if (tinhDiem.getLoai() == 2) {
			diemTrungBinh = (float) (giuaKy * 0.3 + chuyenCan * 0.2 + cuoiKy * 0.5);
		}

	}

	public void tinhXepLoai() {

		if (diemTrungBinh >= 8.5 && diemTrungBinh <= 10.0) {
			xepLoai = "A";
		}

		if (diemTrungBinh >= 7.0 && diemTrungBinh < 8.5) {
			xepLoai = "B";
		}
		if (diemTrungBinh >= 6.5 && diemTrungBinh < 7) {
			xepLoai = "C";
		}
		if (diemTrungBinh >= 4 && diemTrungBinh < 6.5) {
			xepLoai = "D";
		}
		if (diemTrungBinh < 4) {
			xepLoai = "F";
		}

	}
}

