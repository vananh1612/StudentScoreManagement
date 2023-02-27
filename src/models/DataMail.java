package models;

public class DataMail {
    private String ten;
    private String code;
    private String email;

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public DataMail(String ten, String code, String email) {
        this.ten = ten;
        this.code = code;
        this.email = email;
    }

    @Override
    public String toString() {
        return "DataMail{" +
                "ten='" + ten + '\'' +
                ", code='" + code + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

