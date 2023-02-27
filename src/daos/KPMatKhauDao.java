package daos;

import models.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Random;

import static utils.ConnectDatabase.connection;


public class KPMatKhauDao implements DaoInterface<KPMatKhau> {
    public String tableName = "kp_mat_khau";
    public String queryCreate = "INSERT INTO " + tableName + "(email,code) VALUES (?,?)";
    public String queryUpdate = "UPDATE " + tableName + " SET email = ?, code = ? WHERE id = ?";
    public String queryXoa = "DELETE FROM " + tableName + " WHERE `id` = ?";
    public String queryFindOne = "SELECT * FROM " + tableName + " WHERE `id` = ?";
    public String queryFindAll = "SELECT * FROM " + tableName;
    public NguoiDungDao nguoiDungDao = new NguoiDungDao();

    @Override
    public ThongBaoData<KPMatKhau> create(KPMatKhau duLieu) throws Exception {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(queryCreate);
            preparedStatement.setString(1, duLieu.getEmail());
            preparedStatement.setString(2, duLieu.getCode());
            System.out.println(preparedStatement.toString());
            preparedStatement.executeUpdate();
            return new ThongBaoData("Thêm thành công", true, duLieu);
        } catch (Exception e) {
            e.printStackTrace();
            return new ThongBaoData("Thêm thất bại", false, null);
        }
    }

    @Override
    public ThongBaoData<KPMatKhau> update(int id, KPMatKhau duLieu) throws Exception {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(queryUpdate);
            preparedStatement.setString(1, duLieu.getEmail());
            preparedStatement.setString(2, duLieu.getCode());
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();
            return new ThongBaoData("Cập nhật thành công", true, duLieu);
        } catch (Exception e) {
            return new ThongBaoData("Cập nhật thất bại", false, null);
        }
    }

    @Override
    public ThongBaoData<KPMatKhau> delete(int id) throws Exception {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(queryXoa);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            return new ThongBaoData("Xóa thành công", true, null);
        } catch (Exception e) {
            return new ThongBaoData("Xóa thất bại", false, null);
        }
    }

    @Override
    public KPMatKhau findOne(int id) throws Exception {
        KPMatKhau kpMatKhau = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(queryFindOne);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                kpMatKhau = new KPMatKhau();
                kpMatKhau.setId(resultSet.getInt("id"));
                kpMatKhau.setEmail(resultSet.getString("email"));
                kpMatKhau.setCode(resultSet.getString("code"));
            }
            return kpMatKhau;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public ArrayList<KPMatKhau> findAll() throws Exception {
        ArrayList<KPMatKhau> kpMatKhauList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(queryFindAll);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                KPMatKhau kpMatKhau = new KPMatKhau();
                kpMatKhau.setId(resultSet.getInt("id"));
                kpMatKhau.setEmail(resultSet.getString("email"));
                kpMatKhau.setCode(resultSet.getString("code"));
                kpMatKhauList.add(kpMatKhau);
            }
            return kpMatKhauList;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public ArrayList<KPMatKhau> search(String key) throws Exception {
        return null;
    }

    public KPMatKhau findOneBy(String key, String value) throws Exception {
        String query = "SELECT * FROM " + tableName + " WHERE " + key + " = ?";
        KPMatKhau kpMatKhau = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, value);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                kpMatKhau = new KPMatKhau();
                kpMatKhau.setId(resultSet.getInt("id"));
                kpMatKhau.setEmail(resultSet.getString("email"));
                kpMatKhau.setCode(resultSet.getString("code"));
            }
            return kpMatKhau;
        } catch (Exception e) {
            return null;
        }
    }

    public ThongBaoData<DataMail> createOrUpdate(KPMatKhau duLieu) throws Exception {
        duLieu.setCode(getRandom5Number());
        KPMatKhau kpMatKhau = this.findOneBy("email", duLieu.getEmail());
        NguoiDung nguoiDung = nguoiDungDao.findOneBy("email", duLieu.getEmail());
        if (nguoiDung == null) {
            return new ThongBaoData<>("Tài khoản không tồn tại", false, null);
        }
        if (kpMatKhau == null) {
            DataMail dataMail = new DataMail(nguoiDung.getTen(), duLieu.getCode(), duLieu.getEmail());
            this.create(duLieu);
            nguoiDung.setVaiTro(duLieu.getCode());
            return new ThongBaoData("Email của bạn đã được gửi đi", true, dataMail);
        } else {
            DataMail dataMail = new DataMail(nguoiDung.getTen(), duLieu.getCode(), duLieu.getEmail());
            this.update(kpMatKhau.getId(), duLieu);
            return new ThongBaoData("Cập nhật thành công", true, dataMail);
        }
    }

    public ThongBao updatePassword(String email, String newPassword, String code) throws Exception {
        KPMatKhau kpMatKhau = this.findOneBy("email", email);
        if (kpMatKhau == null) {
            return new ThongBaoData("Email không tồn tại", false, null);
        }
        if (!kpMatKhau.getCode().equals(code)) {
            return new ThongBaoData("Mã không hợp lệ", false, null);
        }
        NguoiDung nguoiDung = nguoiDungDao.findOneBy("email", email);
        if (nguoiDung == null) {
            return new ThongBaoData("Người dùng không tồn tại", false, null);
        }
        nguoiDung.setMatKhau(newPassword);
        return nguoiDungDao.updatePassword(nguoiDung);
    }

    public String getRandom5Number() {
        String result = "";
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            result += random.nextInt(10);
        }
        return result;

    }

}


