package daos;

import models.*;
import utils.ConnectDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DiemDao implements DaoInterface<Diem> {
    private final SinhVienDao sinhVienDao = new SinhVienDao();
    private final MonHocDao monHocDao = new MonHocDao();
    private final HocKyDao hocKyDao = new HocKyDao();
    public String tableName = "diem";
    public String queryCreate = "INSERT INTO " + tableName + " VALUES(?,?,?,?,?,?,?,?,?,?,?)";
    public String queryUpdate = "UPDATE " + tableName
            + " SET `id_sinhvien` = ?,`id_monhoc` = ?,`chuyencan`= ?,`baitap`= ?,`giuaky`= ? ,`cuoiky`= ?,`id_hocky`= ?,`tinhdiem`= ?,`diemtrungbinh`= ? ,`xeploai`= ? WHERE `id` = ?";
    public String queryXoa = "DELETE FROM " + tableName + " WHERE `id` = ?";
    public String queryFindOne = "SELECT * FROM " + tableName + " WHERE `id` = ?";
    public String queryFindAll = "SELECT * FROM " + tableName;
    // search to all
    public String querySearch = "SELECT * FROM " + tableName + " WHERE `ten` LIKE %?%";
    public String queryFindOneByHocKy = "SELECT * FROM " + tableName + " WHERE `id_hocky` = ?";
    public String queryFindByUserId = "SELECT * FROM " + tableName + " WHERE `id_sinhvien` = ?";
    public String queryFindDiemBySinhVienAndHocKyAndMonHoc = "SELECT * FROM " + tableName
            + " WHERE `id_sinhvien` = ? AND `id_hocky` = ? AND `id_monhoc` = ? ";
    public Connection connection = ConnectDatabase.connection;

    public int findUserbyId(int id) throws Exception {
        int id1 = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(queryFindByUserId);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                HocKy Hocky = hocKyDao.findOne(resultSet.getInt("id_hocky"));

                id1 = Hocky.getId();
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return id1;
    }

    @Override
    public ThongBao create(Diem duLieu) throws Exception {

        if (this.findDiemBySinhVienAndHocKyAndMonHoc(duLieu.getSinhVien().getId(), duLieu.getHocKy().getId(), duLieu.getMonHoc().getId()) != null) {
            return new ThongBao("Thêm thất bại do sinh viên đã có điểm trước đó", false);
        }

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(queryCreate);
            preparedStatement.setInt(1, duLieu.getId());
            preparedStatement.setInt(2, duLieu.getSinhVien().getId());
            preparedStatement.setInt(3, duLieu.getMonHoc().getId());
            preparedStatement.setFloat(4, duLieu.getChuyenCan());
            preparedStatement.setFloat(5, duLieu.getBaiTap());
            preparedStatement.setFloat(6, duLieu.getGiuaKy());
            preparedStatement.setFloat(7, duLieu.getCuoiKy());
            preparedStatement.setInt(8, duLieu.getHocKy().getId());
            preparedStatement.setInt(9, duLieu.getTinhDiem().getLoai());
            preparedStatement.setFloat(10, duLieu.getDiemTrungBinh());
            preparedStatement.setString(11, duLieu.getXepLoai());

            preparedStatement.executeUpdate();
            return new ThongBao("Thêm thành công", true);
        } catch (Exception e) {
            return new ThongBao("Thêm thất bại", false);
        }

    }

    @Override
    public ThongBao update(int id, Diem duLieu) throws Exception {
        Diem diem = this.findOne(id);
        if (diem == null) {
            return new ThongBao("Không tìm thấy dữ liệu để cập nhật!", false);
        }
        Diem diemCheck = this.findDiemBySinhVienAndHocKyAndMonHoc(duLieu.getSinhVien().getId(), duLieu.getHocKy().getId(), duLieu.getMonHoc().getId());
        if (diemCheck != null && diemCheck.getId() != id) {
            return new ThongBao("Thêm thất bại do sinh viên đã có điểm trước đó", false);
        }
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(queryUpdate);
            preparedStatement.setInt(1, duLieu.getSinhVien().getId());
            preparedStatement.setInt(2, duLieu.getMonHoc().getId());
            preparedStatement.setFloat(3, duLieu.getChuyenCan());
            preparedStatement.setFloat(4, duLieu.getBaiTap());
            preparedStatement.setFloat(5, duLieu.getGiuaKy());
            preparedStatement.setFloat(6, duLieu.getCuoiKy());
            preparedStatement.setInt(7, duLieu.getHocKy().getId());
            preparedStatement.setInt(8, duLieu.getTinhDiem().getLoai());
            preparedStatement.setFloat(9, duLieu.getDiemTrungBinh());
            preparedStatement.setString(10, duLieu.getXepLoai());
            preparedStatement.setInt(11, id);
            preparedStatement.executeUpdate();
            return new ThongBao("Cập nhật thành công", true);
        } catch (Exception e) {
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
    public Diem findOne(int id) throws Exception {
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
                Diem diem = getDiemFromResultSet(resultSet);
                return diem;
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    public ArrayList<SinhVien> getAllSinhVien() {
        ArrayList<SinhVien> sinhViens = new ArrayList<>();
        try {
            sinhViens = sinhVienDao.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sinhViens;

    }

    public ArrayList<MonHoc> getAllMonHoc() {
        ArrayList<MonHoc> monHocs = new ArrayList<>();
        try {
            monHocs = monHocDao.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return monHocs;

    }

    public ArrayList<HocKy> getAllHocKy() {
        ArrayList<HocKy> hocKies = new ArrayList<>();
        try {
            hocKies = hocKyDao.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hocKies;

    }

    @Override
    public ArrayList<Diem> findAll() throws Exception {
        ArrayList<Diem> diems = new ArrayList<>();

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
                Diem diem = getDiemFromResultSet(resultSet);
                diems.add(diem);
            }
            return diems;
        } catch (Exception e) {
            e.printStackTrace();
            return diems;

        }
    }

    @Override
    public ArrayList<Diem> search(String key) throws Exception {
        try {
            String query = "SELECT * FROM " + tableName + " WHERE `id_sinhvien` LIKE '%" + key + "%'OR"
                    + "`id_monhoc` LIKE '%" + key + "%'OR" + "`chuyencan` LIKE '%" + key + "%'OR" + "`baitap` LIKE '%"
                    + key + "%'OR" + "`giuaky` LIKE '%" + key + "%'OR" + "`cuoiky` LIKE '%" + key + "%'OR"
                    + "`id_hocky` LIKE '%" + key + "%'OR" + "`tinhdiem` LIKE '%" + key + "%'OR"
                    + "`diemtrungbinh` LIKE '%" + key + "%'OR" + "`xeploai` LIKE '%" + key + "%'";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Diem> diems = new ArrayList<>();
            while (resultSet.next()) {
                Diem diem = getDiemFromResultSet(resultSet);
                diems.add(diem);
            }
            return diems;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Diem findByHocKy(int id_hocky) throws Exception {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(queryFindOneByHocKy);
            preparedStatement.setInt(1, id_hocky);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Diem diem = getDiemFromResultSet(resultSet);
                return diem;
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    public Diem findDiemBySinhVienAndHocKyAndMonHoc(int idSinhVien, int idHocKy, int idMonHoc) {

        try {
            // tạo câu lệnh truy vấn sql và thực thi bằng preparedStatement
            // (PreparedStatement)
            // PreparedStatement là một lớp trong java.sql, nó được sử dụng để thực thi các
            // câu lệnh SQL có tham số.
            PreparedStatement preparedStatement = connection.prepareStatement(queryFindDiemBySinhVienAndHocKyAndMonHoc);
            // truyền tham số vào câu lệnh sql bằng phương thức set của preparedStatement
            // thay dấu ? bằng id
            preparedStatement.setInt(1, idSinhVien);
            preparedStatement.setInt(2, idHocKy);
            preparedStatement.setInt(3, idMonHoc);
            // thực thi câu lệnh sql và trả về kết qu
            ResultSet resultSet = preparedStatement.executeQuery();
            // nếu kết quả trả về có dòng thì tạo đối tượng lớp và trả về
            if (resultSet.next()) {
                Diem diem = getDiemFromResultSet(resultSet);
                return diem;
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    //get all diem by MonHoc
    public List<Diem> findAllDiemByMonHoc(int idMonHoc) {
        try {
            // tạo câu lệnh truy vấn sql và thực thi bằng preparedStatement
            // (PreparedStatement)
            // PreparedStatement là một lớp trong java.sql, nó được sử dụng để thực thi các
            // câu lệnh SQL có tham số.
            String queryFindAllDiemByMonHoc = "SELECT * FROM diem WHERE id_monhoc = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(queryFindAllDiemByMonHoc);
            // truyền tham số vào câu lệnh sql bằng phương thức set của preparedStatement
            // thay dấu ? bằng id
            preparedStatement.setInt(1, idMonHoc);
            // thực thi câu lệnh sql và trả về kết qu
            ResultSet resultSet = preparedStatement.executeQuery();
            // nếu kết quả trả về có dòng thì tạo đối tượng lớp và trả về
            List<Diem> listDiem = new ArrayList<>();
            while (resultSet.next()) {
                Diem diem = getDiemFromResultSet(resultSet);
                listDiem.add(diem);

            }
            return listDiem;
        } catch (Exception e) {

        }
        return null;
    }

    public Diem getDiemFromResultSet(ResultSet resultSet) throws Exception {

        Diem diem = new Diem();
        diem.setId(resultSet.getInt("id"));
        diem.setSinhVien(this.sinhVienDao.findOne(resultSet.getInt("id_sinhvien")));
        diem.setMonHoc(this.monHocDao.findOne(resultSet.getInt("id_monhoc")));
        diem.setChuyenCan(resultSet.getFloat("chuyencan"));
        diem.setBaiTap(resultSet.getFloat("baitap"));
        diem.setGiuaKy(resultSet.getFloat("giuaky"));
        diem.setCuoiKy(resultSet.getFloat("cuoiky"));
        diem.setHocKy(this.hocKyDao.findOne(resultSet.getInt("id_hocky")));
        diem.setTinhDiem(new TinhDiem(resultSet.getInt("tinhdiem"), ""));
        diem.setDiemTrungBinh(resultSet.getFloat("diemtrungbinh"));
        diem.setXepLoai(resultSet.getString("xeploai"));
        return diem;
    }

}
