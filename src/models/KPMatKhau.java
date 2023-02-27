package models;

public class KPMatKhau {

    int id;
    String email;
    String code;

    public KPMatKhau(int id, String email, String code) {
        this.id = id;
        this.email = email;
        this.code = code;
    }

    public KPMatKhau() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "KPMatKhau{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
