package models;

public class TinhDiem {
    private int loai;
    private String ten;

    public TinhDiem(int loai, String ten) {
        super();
        this.loai = loai;
        this.ten = ten;
    }

    @Override
    public String toString() {
        return loai + " - " + ten;
    }

    public int getLoai() {
        return loai;
    }

    public void setLoai(int loai) {
        this.loai = loai;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }


}
