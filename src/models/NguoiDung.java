package models;

public class NguoiDung {
    private int id;
    private String ten;
    private String email;
    private String matKhau;
    private String vaiTro;

    public NguoiDung() {
        super();
    }

    public NguoiDung(int id, String ten, String email, String matKhau, String vaiTro) {
        super();
        this.id = id;
        this.ten = ten;
        this.email = email;
        this.matKhau = matKhau;
        this.vaiTro = vaiTro;
    }

    @Override
    public String toString() {
        return "NguoiDung [id=" + id + ", ten=" + ten + ", email=" + email + ", matKhau=" + matKhau + ", vaiTro="
                + vaiTro + "]";
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(String vaiTro) {
        this.vaiTro = vaiTro;
    }

}
