package daos;

import models.Lop;
import models.SinhVien;
import models.ThongBao;
import utils.ConnectDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class SinhVienDao implements DaoInterface<SinhVien> {

    public String tableName = "sinh_vien";
    public String queryCreate = "INSERT INTO " + tableName + " VALUES(?,?,?,?,?,?,?)";
    public String queryUpdate = "UPDATE " + tableName
            + " SET `ten` = ?,`gioi_tinh`= ?,`ngay_sinh`= ?,`dia_chi`= ? ,`sdt`= ?, `id_lop` = ? WHERE `id` = ?";
    public String queryXoa = "DELETE FROM " + tableName + " WHERE `id` = ?";
    public String queryFindOne = "SELECT * FROM " + tableName + " WHERE `id` = ?";
    public String queryFindByLop = "SELECT * FROM " + tableName + " WHERE `lop` = ?";
    public String queryFindAll = "SELECT * FROM " + tableName;
    // search to all
    public String querySearch = "SELECT * FROM " + tableName + " WHERE `ten` LIKE %?%";
    public String querySearchbyUserId = "";
    public Connection connection = ConnectDatabase.connection;

    private final LopHocDao lopHocDao = new LopHocDao();

    @Override
    public ThongBao create(SinhVien duLieu) throws Exception {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(queryCreate);
            preparedStatement.setInt(1, duLieu.getId());
            preparedStatement.setString(2, duLieu.getTen());
            preparedStatement.setInt(3, duLieu.getGioiTinh());
            preparedStatement.setString(4, duLieu.getNgaySinh());
            preparedStatement.setString(5, duLieu.getDiaChi());
            preparedStatement.setString(6, duLieu.getSdt());
            preparedStatement.setInt(7, duLieu.getLop().getId());
            preparedStatement.executeUpdate();
            return new ThongBao("Thêm thành công", true);
        } catch (Exception e) {
            e.printStackTrace();
            return new ThongBao("Thêm thất bại", false);

        }

    }

    @Override
    public ThongBao update(int id, SinhVien duLieu) throws Exception {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(queryUpdate);
            preparedStatement.setString(1, duLieu.getTen());
            preparedStatement.setInt(2, duLieu.getGioiTinh());
            preparedStatement.setString(3, duLieu.getNgaySinh());
            preparedStatement.setString(4, duLieu.getDiaChi());
            preparedStatement.setString(5, duLieu.getSdt());
            preparedStatement.setInt(6, duLieu.getLop().getId());
            preparedStatement.setInt(7, id);
            System.out.println(preparedStatement);
            //duLieu.getLop().getId() cái này bị nui vì câu lệnh sql ko lấy dc hoặc không truyền dc
            preparedStatement.setInt(7, id);


            preparedStatement.executeUpdate();

            return new ThongBao("Cập nhật thành công", true);
        } catch (Exception e) {
            e.printStackTrace();
            return new ThongBao("Cập nhật thất bại", false);
        }

    }

    @Override
    public ThongBao delete(int id) throws Exception {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(queryXoa);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            return new ThongBao("Xóa thành công", true);
        } catch (Exception e) {
            return new ThongBao("Xóa thất bại", false);
        }

    }

    @Override
    public SinhVien findOne(int id) throws Exception {
        try {
            // tạo câu lệnh truy vấn sql và thực thi bằng preparedStatement
            // (PreparedStatement)
            // PreparedStatement là một lớp trong java.sql, nó được sử dụng để thực thi các
            // câu lệnh SQL có tham số.
            PreparedStatement preparedStatement = connection.prepareStatement(queryFindOne);
            // truyền tham số vào câu lệnh sql bằng phương thức set của preparedStatement
            // thay dấu ? bằng id
            preparedStatement.setInt(1, id);
            // thực thi câu lệnh sql và trả về kết quả
            ResultSet resultSet = preparedStatement.executeQuery();
            // nếu kết quả trả về có dòng thì tạo đối tượng lớp và trả về
            if (resultSet.next()) {
                SinhVien sinhVien = new SinhVien();
                sinhVien.setId(resultSet.getInt("id"));
                sinhVien.setTen(resultSet.getString("ten"));
                sinhVien.setGioiTinh(resultSet.getInt("gioi_tinh"));
                sinhVien.setNgaySinh(resultSet.getString("ngay_sinh"));
                sinhVien.setDiaChi(resultSet.getString("dia_chi"));
                sinhVien.setSdt(resultSet.getString("sdt"));
                sinhVien.setLop(this.lopHocDao.findOne(resultSet.getInt("id_lop")));

                return sinhVien;
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    public ArrayList<Lop> getAllLop() {
        ArrayList<Lop> lops = new ArrayList<>();
        try {
            lops = lopHocDao.findAll();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return lops;

    }

    @Override
    public ArrayList<SinhVien> findAll() throws Exception {
        ArrayList<SinhVien> sinhViens = new ArrayList<>();
        try {
            // tạo câu lệnh truy vấn sql và thực thi bằng preparedStatement
            // (PreparedStatement)
            PreparedStatement preparedStatement = connection.prepareStatement(queryFindAll);
            // thực thi câu lệnh sql và trả về kết quả
            ResultSet resultSet = preparedStatement.executeQuery();
            // tạo mảng monhoc để chứa kết quả

            // duyệt kết quả trả về và thêm vào mảng monhoc next() trả về true nếu còn dòng
            // tiếp theo và false nếu không còn dòng nào
            while (resultSet.next()) {
                // tạo đối tượng môn học
                SinhVien sinhVien = new SinhVien();
                // gán giá trị cho đối tượng môn học
                sinhVien.setId(resultSet.getInt("id"));
                // gán giá trị cho đối tượng môn học
                sinhVien.setTen(resultSet.getString("ten"));
                sinhVien.setGioiTinh(resultSet.getInt("gioi_tinh"));
                sinhVien.setNgaySinh(resultSet.getString("ngay_sinh"));
                sinhVien.setDiaChi(resultSet.getString("dia_chi"));
                sinhVien.setSdt(resultSet.getString("sdt"));
                sinhVien.setLop(this.lopHocDao.findOne(resultSet.getInt("id_lop")));

                // thêm đối tượng môn học vào mảng môn học

                sinhViens.add(sinhVien);
            }
            return sinhViens;
        } catch (Exception e) {
            return null;
        }
    }

    public ArrayList<SinhVien> findByLop(int idLop) throws Exception {
        ArrayList<SinhVien> sinhViens = new ArrayList<>();
        try {
            // tạo câu lệnh truy vấn sql và thực thi bằng preparedStatement
            // (PreparedStatement)
            PreparedStatement preparedStatement = connection.prepareStatement(queryFindByLop);
            preparedStatement.setInt(1, idLop);
            // thực thi câu lệnh sql và trả về kết quả
            ResultSet resultSet = preparedStatement.executeQuery();
            // tạo mảng monhoc để chứa kết quả

            // duyệt kết quả trả về và thêm vào mảng monhoc next() trả về true nếu còn dòng
            // tiếp theo và false nếu không còn dòng nào
            while (resultSet.next()) {
                // tạo đối tượng môn học
                SinhVien sinhVien = new SinhVien();
                // gán giá trị cho đối tượng môn học
                sinhVien.setId(resultSet.getInt("id"));
                // gán giá trị cho đối tượng môn học
                sinhVien.setTen(resultSet.getString("ten"));
                sinhVien.setGioiTinh(resultSet.getInt("gioi_tinh"));
                sinhVien.setNgaySinh(resultSet.getString("ngay_sinh"));
                sinhVien.setDiaChi(resultSet.getString("dia_chi"));
                sinhVien.setSdt(resultSet.getString("sdt"));
                sinhVien.setLop(this.lopHocDao.findOne(resultSet.getInt("id_lop")));

                // thêm đối tượng môn học vào mảng môn học

                sinhViens.add(sinhVien);
            }
            return sinhViens;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public ArrayList<SinhVien> search(String key) throws Exception {
        try {
            String query = "SELECT * FROM " + tableName + " WHERE `ten` LIKE '%" + key + "%'OR" + "`gioi_tinh` LIKE '%"
                    + key + "%'OR" + "`ngay_sinh` LIKE '%" + key + "%'OR" + "`dia_chi` LIKE '%" + key + "%'OR"
                    + "`sdt` LIKE '%" + key + "%'OR" + "`id_lop` LIKE '%" + key + "%'";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<SinhVien> sinhViens = new ArrayList<>();
            while (resultSet.next()) {
                SinhVien sinhVien = new SinhVien();
                sinhVien.setId(resultSet.getInt("id"));
                sinhVien.setTen(resultSet.getString("ten"));
                sinhVien.setGioiTinh(resultSet.getInt("gioi_tinh"));
                sinhVien.setNgaySinh(resultSet.getString("ngay_sinh"));
                sinhVien.setDiaChi(resultSet.getString("dia_chi"));
                sinhVien.setSdt(resultSet.getString("sdt"));
                sinhVien.setLop(this.lopHocDao.findOne(resultSet.getInt("id_lop")));

                sinhViens.add(sinhVien);
            }
            return sinhViens;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
