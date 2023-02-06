package models;

public class Lop {
    private int id;
    private String ten;

    public Lop() {
        super();
    }

    public Lop(int id, String ten) {
        super();
        this.id = id;
        this.ten = ten;
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

    @Override
    public String toString() {
        return this.ten;
    }

    public String toString1() {
        return id + " - " + ten;
    }

}
