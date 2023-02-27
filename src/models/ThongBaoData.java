package models;

public  class ThongBaoData<T> extends ThongBao {
    T data;

    public ThongBaoData(T data) {
        this.data = data;
    }

    public ThongBaoData(String tinNhan, Boolean kiemTra, T data) {
        super(tinNhan, kiemTra);
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return super.toString() + "ThongBaoData{" + "data=" + data + '}';
    }
}