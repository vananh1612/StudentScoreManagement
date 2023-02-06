package models;

public class HocKy {
    private int id;
    private String namHoc;
    private String ten;

    public HocKy() {
        super();
    }

    public HocKy(int id, String namHoc, String ten) {
        super();
        this.id = id;
        this.namHoc = namHoc;
        this.ten = ten;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamHoc() {
        return namHoc;
    }

    public void setNamHoc(String namHoc) {
        this.namHoc = namHoc;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    @Override
    public String toString() {
        return id + " - " + ten + " - " + namHoc;
    }

}
